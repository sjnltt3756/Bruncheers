package com.bruncheers.user.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bruncheers.config.JwtTokenProvider;
import com.bruncheers.coupon.entity.Coupon;
import com.bruncheers.coupon.repository.CouponRepository;
import com.bruncheers.exception.ExistedUserException;
import com.bruncheers.exception.GhostUserException;
import com.bruncheers.exception.PasswordMismatchException;
import com.bruncheers.exception.UserNotFoundException;
import com.bruncheers.user.dto.NewPasswordDto;
import com.bruncheers.user.dto.PasswordVerificationRequestDto;
import com.bruncheers.user.dto.UserDto;
import com.bruncheers.user.dto.UserLoginDto;
import com.bruncheers.user.dto.UserUpdateDto;
import com.bruncheers.user.entity.User;
import com.bruncheers.user.enums.Role;
import com.bruncheers.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final CouponRepository couponRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider,
			CouponRepository couponRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.couponRepository = couponRepository;
		this.jwtTokenProvider = jwtTokenProvider;
		this.userRepository = userRepository;
	}

	/**
	 * 새로운 사용자를 등록합니다.
	 * 
	 * @param userDto 사용자 정보를 담고 있는 데이터 전송 객체
	 * @return 등록된 사용자를 UserDto로 반환
	 * @throws UserNotFoundException 닉네임 또는 이메일이 중복된 경우 예외 발생
	 */
	@Override
	public UserDto registerUser(UserDto userDto) {
		if (userRepository.existsByUserNickname(userDto.getUserNickname())) {
			throw new UserNotFoundException("닉네임이 중복되었습니다.");
		}
		if (userRepository.existsByUserEmail(userDto.getUserEmail())) {
			throw new UserNotFoundException("이메일이 중복되었습니다.");
		}
		User user = User.toEntity(userDto);
		user.setUserPw(bCryptPasswordEncoder.encode(userDto.getUserPw()));
		user.setRole(Role.USER);
		User savedUser = userRepository.save(user);

		// 새로운 사용자에게 환영 쿠폰 생성
		Coupon coupon = Coupon.builder().coupName("신규 가입 쿠폰").coupDiscount(0.1) // 예시 할인율
				.coupDesc("신규 가입 회원에게 제공되는 10% 할인 쿠폰").coupStatus("1") // 사용 가능 상태
				.user(savedUser).build();
		couponRepository.save(coupon);

		return UserDto.toDto(savedUser);
	}

	/**
	 * 닉네임이 고유한지 확인합니다.
	 * 
	 * @param userNickname 사용자 닉네임
	 * @return 닉네임이 고유하면 true 반환
	 * @throws ExistedUserException 닉네임이 중복된 경우 예외 발생
	 */
	@Override
	public boolean isNicknameUnique(String userNickname) {
		if (!userRepository.existsByUserNickname(userNickname)) {
			return true;
		}
		throw new ExistedUserException("닉네임이 중복되었습니다.");
	}

	/**
	 * 이메일이 고유한지 확인합니다.
	 * 
	 * @param userEmail 사용자 이메일
	 * @return 이메일이 고유하면 true 반환
	 * @throws ExistedUserException 이메일이 중복된 경우 예외 발생
	 */
	@Override
	public boolean isEmailUnique(String userEmail) {
		if (!userRepository.existsByUserEmail(userEmail)) {
			return true;
		}
		throw new ExistedUserException("이메일이 중복되었습니다.");
	}

	/**
	 * 사용자를 삭제합니다.
	 * 
	 * @param userNo 사용자 번호
	 * @throws UserNotFoundException 사용자를 찾을 수 없는 경우 예외 발생
	 */
	@Override
	public void deleteUser(Long userNo) {
		User user = userRepository.findById(userNo).orElseThrow(() -> new UserNotFoundException("회원을 찾을 수 없습니다."));
		userRepository.delete(user);
	}

	/**
	 * 사용자 정보를 업데이트합니다.
	 * 
	 * @param userNo  사용자 번호
	 * @param userDto 업데이트할 사용자 정보
	 * @return 업데이트된 사용자 정보를 UserUpdateDto로 반환
	 * @throws UserNotFoundException 사용자를 찾을 수 없는 경우 예외 발생
	 */
	@Override
	public UserDto updateUser(UserUpdateDto userDto) {
		User user = userRepository.findById(userDto.getUserNo())
				.orElseThrow(() -> new UserNotFoundException("회원을 찾을 수 없습니다."));

		// 새 비밀번호와 새 비밀번호 확인이 일치하는지 확인
		if (!userDto.getUserNewPw().equals(userDto.getUserNewPw2())) {
			throw new IllegalArgumentException("새 비밀번호와 새 비밀번호 확인이 일치하지 않습니다.");
		}
		user.setUserNickname(userDto.getUserNickname());
		user.setUserPw(userDto.getUserPw());
		user.setUserPw(bCryptPasswordEncoder.encode(userDto.getUserNewPw()));
		user.setUserPw(bCryptPasswordEncoder.encode(userDto.getUserNewPw2()));
		user.setUserName(userDto.getUserName());
		user.setUserGender(userDto.getUserGender());
		user.setUserBirth(userDto.getUserBirth());
		user.setUserHp(userDto.getUserHp());

		User updatedUser = userRepository.save(user);
		return UserDto.toDto(updatedUser);
	}

	/**
	 * 생년월일과 전화번호로 이메일을 찾습니다.
	 * 
	 * @param userBirth 사용자 생년월일
	 * @param userHp    사용자 전화번호
	 * @return 사용자의 이메일
	 * @throws UserNotFoundException 사용자를 찾을 수 없는 경우 예외 발생
	 */
	@Override
	public String findEmailByBirthAndHp(String userBirth, String userHp) {
		User user = userRepository.findByUserBirthAndUserHp(userBirth, userHp)
				.orElseThrow(() -> new UserNotFoundException("회원을 찾을 수 없습니다."));
		return user.getUserEmail();
	}

	/**
	 * 이메일과 전화번호로 비밀번호를 찾습니다.
	 * 
	 * @param userEmail 사용자 이메일
	 * @param userHp    사용자 전화번호
	 * @return 사용자의 비밀번호
	 * @throws UserNotFoundException 사용자를 찾을 수 없는 경우 예외 발생
	 */
	@Override
	public String findPwByEmailAndByHp(String userEmail, String userHp) {
		User user = userRepository.findByUserEmailAndUserHp(userEmail, userHp)
				.orElseThrow(() -> new UserNotFoundException("회원을 찾을 수 없습니다."));
		return user.getUserPw();
	}

	/**
	 * 사용자를 로그인합니다.
	 * 
	 * @param userLoginDto 로그인 요청 정보가 담긴 데이터 전송 객체
	 * @return 로그인된 사용자의 정보와 JWT 토큰을 담은 UserDto
	 * @throws ExistedUserException      이메일이 일치하지 않는 경우 예외 발생
	 * @throws PasswordMismatchException 비밀번호가 일치하지 않는 경우 예외 발생
	 */
	@Override
	public UserDto loginUser(UserLoginDto userLoginDto) {
		// 이메일로 사용자 조회
		User user = userRepository.findByUserEmail(userLoginDto.getUserEmail())
				.orElseThrow(() -> new ExistedUserException("이메일이 일치하지 않습니다."));

		// 역할이 GHOST인 경우 예외 발생
		if (user.getRole() == Role.GHOST) {
			throw new GhostUserException("해당 계정은 사용할 수 없습니다.");
		}

		// ADMIN은 비밀번호 암호화 없이 로그인(기존 db)
		if (user.getRole() == Role.ADMIN) {
			if (!userLoginDto.getPassword().equals(user.getUserPw())) {
				throw new PasswordMismatchException("패스워드가 일치하지 않습니다.");
			}
		} else {
			boolean isPasswordMatch = bCryptPasswordEncoder.matches(userLoginDto.getPassword(), user.getUserPw());
			if (!isPasswordMatch) {
				throw new PasswordMismatchException("패스워드가 일치하지 않습니다.");
			}
		}

		// 로그인 성공 시 액세스 토큰 생성
		String accessToken = jwtTokenProvider.createToken(userLoginDto.getUserEmail(), user.getRole());

		// 생성된 토큰을 사용자 정보에 저장
		user.setToken(accessToken);
		return UserDto.toDto(user);
	}

	/**
	 * 비밀번호를 검증하여 로그인 상태를 확인합니다.
	 * 
	 * @param userNo   사용자 번호
	 * @param password 비밀번호 검증 요청 정보가 담긴 데이터 전송 객체
	 * @return 검증된 사용자의 정보를 UserDto로 반환
	 * @throws IllegalArgumentException  비밀번호가 입력되지 않은 경우 예외 발생
	 * @throws UserNotFoundException     사용자를 찾을 수 없는 경우 예외 발생
	 * @throws PasswordMismatchException 비밀번호가 일치하지 않는 경우 예외 발생
	 */
	@Override
	public UserDto verifyLogin(Long userNo, PasswordVerificationRequestDto password) {
		if (password.getPassword() == null || password.getPassword().isEmpty()) {
			throw new IllegalArgumentException("비밀번호를 입력하세요.");
		}

		User user = userRepository.findById(userNo).orElseThrow(() -> new UserNotFoundException("회원이 없습니다."));
		boolean isPasswordMatch = bCryptPasswordEncoder.matches(password.getPassword(), user.getUserPw());
		if (!isPasswordMatch) {
			throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
		}
		return UserDto.toDto(user);
	}

	/**
	 * 사용자 정보를 조회합니다.
	 * 
	 * @param userNo 사용자 번호
	 * @return 사용자의 정보를 UserDto로 반환
	 * @throws UserNotFoundException 사용자를 찾을 수 없는 경우 예외 발생
	 */
	@Override
	public UserDto getUserInfo(Long userNo) {
		User user = userRepository.findById(userNo).orElseThrow(() -> new UserNotFoundException("해당 회원을 찾을 수 없습니다."));
		return UserDto.toDto(user);
	}

	/**
	 * 모든 사용자의 목록을 조회합니다.
	 * 
	 * @return 모든 사용자의 목록을 UserDto 리스트로 반환
	 * @throws UserNotFoundException 사용자가 없는 경우 예외 발생
	 */
	@Override
	public List<UserDto> userList() {
		List<User> userList = userRepository.findAll();
		if (userList.isEmpty()) {
			throw new UserNotFoundException("회원이 없습니다.");
		}
		return userList.stream().map(UserDto::toDto).collect(Collectors.toList());
	}

	/**
	 * 이메일로 사용자를 조회합니다.
	 * 
	 * @param email 사용자 이메일
	 * @return 사용자의 정보를 UserDto로 반환
	 */
	@Override
	public UserDto findUserByEmail(String email) {
		User user = userRepository.findByUserEmail(email)
				.orElseThrow(() -> new UserNotFoundException("회원을 찾을 수 없습니다."));
		return UserDto.toDto(user);
	}

	/**
	 * 새로운 비밀번호로 사용자 비밀번호를 변경합니다.
	 * 
	 * @param password 새로운 비밀번호 정보가 담긴 데이터 전송 객체
	 * @return 업데이트된 사용자 정보를 UserDto로 반환
	 * @throws IllegalArgumentException 새 비밀번호가 일치하지 않는 경우 예외 발생
	 * @throws UserNotFoundException    사용자를 찾을 수 없는 경우 예외 발생
	 */
	@Override
	public UserDto newPassword(NewPasswordDto password) {
		User user = userRepository.findByUserEmail(password.getUserEmail())
				.orElseThrow(() -> new UserNotFoundException("회원을 찾을 수 없습니다."));

		// 새로운 비밀번호가 일치하는지 확인
		if (!password.getUserNewPw().equals(password.getUserNewPw2())) {
			throw new IllegalArgumentException("새 비밀번호가 일치하지 않습니다.");
		}

		user.setUserPw(bCryptPasswordEncoder.encode(password.getUserNewPw()));
		user.setUserPw(bCryptPasswordEncoder.encode(password.getUserNewPw2()));
		User updatedUser = userRepository.save(user);
		return UserDto.toDto(updatedUser);
	}

	/**
	 * 사용자의 역할을 GHOST로 변경합니다.(회원탈퇴서비스)
	 * 
	 * @param userNo 사용자 번호
	 * @return 업데이트된 사용자 정보를 UserDto로 반환
	 * @throws UserNotFoundException 사용자를 찾을 수 없는 경우 예외 발생
	 */
	@Override
	public UserDto changeGhost(Long userNo) {
		User user = userRepository.findById(userNo).orElseThrow(() -> new UserNotFoundException("회원을 찾을 수 없습니다."));
		user.setRole(Role.GHOST);
		userRepository.save(user);
		return UserDto.toDto(user);
	}

	/**
	 * 사용자의 역할을 USER로 변경합니다.(회원복구서비스)
	 * 
	 * @param userEmail 사용자 이메일
	 * @param userHp    사용자 전화번호
	 * @return 업데이트된 사용자 정보를 UserDto로 반환
	 * @throws UserNotFoundException 사용자를 찾을 수 없는 경우 예외 발생
	 */
	@Override
	public UserDto changeUser(String userEmail, String userHp) {
		User user = userRepository.findByUserEmailAndUserHp(userEmail, userHp)
				.orElseThrow(() -> new UserNotFoundException("회원을 찾을 수 없습니다."));
		user.setRole(Role.USER);
		userRepository.save(user);
		return UserDto.toDto(user);
	}
}

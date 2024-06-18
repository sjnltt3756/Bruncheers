package com.bruncheers.user.service;

import java.util.List;

import com.bruncheers.user.dto.NewPasswordDto;
import com.bruncheers.user.dto.PasswordVerificationRequestDto;
import com.bruncheers.user.dto.UserDto;
import com.bruncheers.user.dto.UserLoginDto;
import com.bruncheers.user.dto.UserUpdateDto;

public interface UserService {
	UserDto registerUser(UserDto userDto); // 회원가입

	boolean isNicknameUnique(String uNickname); // 닉네임 중복 확인

	boolean isEmailUnique(String uEmail); // 이메일 중복 확인

	void deleteUser(Long uNo); // 삭제

	String findEmailByBirthAndHp(String uBirth, String uHp); // 생년월일과 휴대폰번호로 이메일 찾기

	UserDto updateUser(UserUpdateDto userDto); // 수정

	String findPwByEmailAndByHp(String uEmail, String uHp); // 이메일과 휴대폰번호로 이메일 찾기

	UserDto loginUser(UserLoginDto userLoginDto); // 로그인

	UserDto getUserInfo(Long uNo); // 회원정보조회

	List<UserDto> userList(); // 유저 리스트 출력

	UserDto verifyLogin(Long userNo, PasswordVerificationRequestDto password); // 비밀번호 확인

	UserDto findUserByEmail(String email); // 이메일로 유저 찾기

	UserDto newPassword(NewPasswordDto password); // 새 패스워드로 변경

	UserDto changeGhost(Long userNo); // roleUSER -> roleGHOST로 변경 (회원탈퇴기능)

	UserDto changeUser(String userEmail, String userHp); // roleGHOST -> roleUSER로 변경 (회원복구기능)

}
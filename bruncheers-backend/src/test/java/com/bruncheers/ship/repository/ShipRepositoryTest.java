package com.bruncheers.ship.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.bruncheers.FinalProjectTeam2BruncheersApplicationTests;
import com.bruncheers.ship.entity.Ship;
import com.bruncheers.user.entity.User;
import com.bruncheers.user.enums.Role;
import com.bruncheers.user.repository.UserRepository;

import jakarta.transaction.Transactional;

class ShipRepositoryTest extends FinalProjectTeam2BruncheersApplicationTests {

	@Autowired
	ShipRepository shipRepository;
	@Autowired
	UserRepository userRepository;
	
	// 배송지 등록
	@Test
	@Transactional
	@Rollback(false)
	@Disabled
	void shipWithUserSave() {
		
		User saveUser = User.builder()
				.userNickname("꺼멍불고기")
				.userPw("1234")
				.userName("김미진")
				.userHp("01012345678")
				.userGender("여")
				.userEmail("123@naver.com")
				.userBirth("19970317")
				.role(Role.USER)
				.build();
		User user = userRepository.save(saveUser);
		
		Ship ship = Ship.builder()
				.sAddr("경기도 용인시")
				.sDef(1)
				.sName("김서리")
				.sNo(9)
				.sPhone("123456789")
				.sReq("검수 잘 부탁드려용")
				.sType("집")
				.sZip("12357")
				.build();
		
		ship.setUser(user);
		shipRepository.save(ship);
		
	}
	
	// 기존 회원에 새로운 배송지 등록
	@Test
	@Transactional
	@Rollback(false)
	void shipSave() throws Exception {
		
		Optional<User> optionalUser = userRepository.findById(2L);
	    if (!optionalUser.isPresent()) {
	        throw new Exception("User not found");
	    }
	    User user = optionalUser.get(); // 실제 User 객체 추출
		
		Ship ship = Ship.builder()
				.sAddr("경기도 용인시")
				.sDef(1)
				.sName("김서리")
				.sNo(9)
				.sPhone("123456789")
				.sReq("검수 잘 부탁드려용")
				.sType("집")
				.sZip("12357")
				.build();
		
		ship.setUser(user);
		shipRepository.save(ship);
		
	}
	
	// 배송지 수정
	@Test
	@Disabled
	@Transactional
	@Rollback(false)
	void shipUpdate() {
		Ship findShip = shipRepository.findById(5).get();
		findShip.setSName("김말하는대로~");
		shipRepository.save(findShip);
	}
	
	// 배송지 삭제
	@Test
	@Disabled
	@Transactional
	@Rollback(false)
	void shipDelete() {
		shipRepository.deleteById(1);
		shipRepository.flush();
	}
	
	// 한 회원의 배송지 전체 조회
	@Test
	@Transactional
	@Rollback(false)
	@Disabled
	void findByUserNo() {
		Optional<User> optionalUser = userRepository.findById(1L);
		assertNotNull(optionalUser);

		if (optionalUser.isPresent()) {
			List<Ship> ships = optionalUser.get().getShips();
			System.out.println(ships);
		}
	}
	
	// 배송지 유형별 목록 조회(집 or 회사)
	@Test
	@Transactional
	@Rollback(false)
	@Disabled
	void findBysNo() {
		List<Ship> ships = shipRepository.findBysType("집");
		System.out.println(ships);
	}
	
}

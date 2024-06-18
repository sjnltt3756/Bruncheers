package com.bruncheers.user.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.bruncheers.user.entity.User;
import com.bruncheers.user.enums.Role;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
class UserRepositoryTest {
	

    @Autowired
   UserRepository userRepository;

    @DisplayName("회원 이메일로 찾기")
    @Test
    @Disabled
    public void testFindByUserEmail() {
    	// Given
    	User user = User.builder()
    			.userEmail("test@example.com")
    			.userPw("password")
    			.userNickname("testNickname")
    			.userName("Test User")
    			.userGender("여")
    			.userBirth("1990-01-01")
    			.userHp("010-1234-5678")
    			.role(Role.USER)
    			.build();
    	userRepository.save(user);
    	
    	// When
    	Optional<User> foundUser = userRepository.findByUserEmail("test@example.com");
    	
    	// Then
    	assertThat(foundUser).isPresent();
    	assertThat(foundUser.get().getUserName()).isEqualTo("Test User");
    	
    }
    @DisplayName("회원 생일과 핸드폰번호로 찾기")
    @Test
    @Disabled
    public void testFindByUserBirthAndUserHp() {
        // Given
        User user = User.builder()
                .userEmail("test@example.com")
                .userPw("password")
                .userNickname("testNickname")
                .userName("Test User")
                .userGender("여")
                .userBirth("1990-01-01")
                .userHp("010-1234-5678")
                .role(Role.USER)
                .build();
        userRepository.save(user);

        // When
        Optional<User> foundUser = userRepository.findByUserBirthAndUserHp("1990-01-01", "010-1234-5678");

        // Then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUserEmail()).isEqualTo("test@example.com");
        
    }

    @DisplayName("회원 이메일과 핸드폰번호로 찾기")
    @Test
    @Disabled
    public void testFindByUserEmailAndUserHp() {
        // Given
        User user = User.builder()
                .userEmail("test@example.com")
                .userPw("password")
                .userNickname("testNickname")
                .userName("Test User")
                .userGender("여")
                .userBirth("1990-01-01")
                .userHp("010-1234-5678")
                .role(Role.USER)
                .build();
        userRepository.save(user);

        // When
        Optional<User> foundUser = userRepository.findByUserEmailAndUserHp("test@example.com", "010-1234-5678");

        // Then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUserNickname()).isEqualTo("testNickname");
    }

    @DisplayName("유저 아이디 중복 여부")
    @Test
    @Disabled
    public void testExistsByUserEmail() {
        // Given
        User user = User.builder()
                .userEmail("test@example.com")
                .userPw("password")
                .userNickname("testNickname")
                .userName("Test User")
                .userGender("남")
                .userBirth("1990-01-01")
                .userHp("010-1234-5678")
                .role(Role.USER)
                .build();
        userRepository.save(user);

        // When
        boolean exists = userRepository.existsByUserEmail("test@example.com");

        // Then
        assertTrue(exists);
    }

    @DisplayName("유저 닉네임 중복여부")
    @Test
    @Disabled
    public void testExistsByUserNickname() {
        // Given
        User user = User.builder()
                .userEmail("test@example.com")
                .userPw("password")
                .userNickname("testNickname")
                .userName("Test User")
                .userGender("남")
                .userBirth("1990-01-01")
                .userHp("010-1234-5678")
                .role(Role.USER)
                .build();
        userRepository.save(user);

        // When
        boolean exists = userRepository.existsByUserNickname("testNickname");

        // Then
        assertTrue(exists);
    }

    @DisplayName("존재하지 않는 이메일 체크")
    @Test
    @Disabled
    public void testNotExistsByUserEmail() {
        // When
        boolean exists = userRepository.existsByUserEmail("nonexistent@example.com");

        // Then
        assertFalse(exists);
    }

    @DisplayName("존재하지 않는 닉네임 체크")
    @Test
    @Disabled
    public void testNotExistsByUserNickname() {
        // When
        boolean exists = userRepository.existsByUserNickname("nonexistentNickname");

        // Then
        assertFalse(exists);
    }
    
    @DisplayName("회원 가입")
    @Test
    @Transactional
    @Rollback(false)
    @Disabled
    void save( ) {
    	User user = User.builder()
                .userEmail("test@naver.com")
                .userPw("password")
                .userNickname("DB연동테스트")
                .userName("테스트")
                .userGender("남")
                .userBirth("1990-01-01")
                .userHp("010-1234-5678")
                .role(Role.USER)
                .build();
        userRepository.save(user);
        
        System.out.println(user);
    	
    }
    @DisplayName("회원 수정")
    @Test
    @Transactional
    @Rollback(false)
    @Disabled
    void update( ) {
    	User findUser = userRepository.findByUserEmail("user2@naver.com").get();
    	System.out.println(findUser);
    	findUser.setUserName("테스트");
    	findUser.setUserNickname("뇸");
    	userRepository.save(findUser);
    }
    
    
    
}

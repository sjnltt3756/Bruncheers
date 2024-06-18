package com.bruncheers.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bruncheers.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUserEmail(String Email); // 유저 이메일로 찾기

	Optional<User> findByUserBirthAndUserHp(String birth, String hp); // 생년월일과 휴대폰번호로 찾기

	Optional<User> findByUserEmailAndUserHp(String email, String hp); // 유저이메일과 휴대폰번호로 찾기

	boolean existsByUserEmail(String Email); // 유저 아이디 중복 여부

	boolean existsByUserNickname(String Nickname); // 유저 닉네임 중복

}

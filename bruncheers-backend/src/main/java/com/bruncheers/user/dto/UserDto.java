package com.bruncheers.user.dto;

import com.bruncheers.user.entity.User;
import com.bruncheers.user.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserDto {
	private Long userNo; // 회원번호
	private String userEmail; // 유저 이메일
	private String userPw; // 유저 비밀번호
	private String userNickname; // 유저 닉네임
	private String userName; // 유저 이름
	private String userGender; // 유저 성별
	private String userBirth; // 유저 생년월일
	private String userHp; // 유저 휴대폰번호
	private Role role; // 유저 역할
	private String token; // 유저토큰값

	public static UserDto toDto(User entity) {
		return UserDto.builder().userNo(entity.getUserNo()).userEmail(entity.getUserEmail()).userPw(entity.getUserPw())
				.userNickname(entity.getUserNickname()).userName(entity.getUserName())
				.userGender(entity.getUserGender()).userBirth(entity.getUserBirth()).userHp(entity.getUserHp())
				.token(entity.getToken()).role(entity.getRole()).build();
	}

}

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

public class UserLoginDto {
	private String userEmail; // 사용자 이메일
	private String password; // 사용자 비밀번호
	/*
	 * private Role role; private String token;
	 */

	public static UserLoginDto toLoginDto(User entity) {
		return UserLoginDto.builder().userEmail(entity.getUserEmail()).password(entity.getUserPw())
				.build();
	}
}

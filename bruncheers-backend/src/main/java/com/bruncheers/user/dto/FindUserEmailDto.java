package com.bruncheers.user.dto;

import lombok.Data;

@Data

public class FindUserEmailDto {
	private String userBirth; 	// 회원 생년월일
	private String userHp;		// 회원 전화번호
	
	/*
	 * public static FindUserEmailDto toUpdateDto(User entity) { return
	 * FindUserEmailDto.builder() .userBirth(entity.getUserBirth())
	 * .userHp(entity.getUserHp()) .build(); }
	 */
}

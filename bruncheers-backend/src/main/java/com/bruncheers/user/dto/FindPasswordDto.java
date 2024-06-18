package com.bruncheers.user.dto;

import lombok.Data;

@Data

public class FindPasswordDto {
	private String userEmail; 	// 회원 이메일
	private String userHp;		// 회원 전화번호
	
	/*
	 * public static FindPasswordDto toUpdateDto(User entity) { return
	 * FindPasswordDto.builder() .userEmail(entity.getUserEmail())
	 * .userHp(entity.getUserHp()) .build(); }
	 */
}


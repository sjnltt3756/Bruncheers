package com.bruncheers.user.dto;

import lombok.Data;

@Data
public class NewPasswordDto {
	private String userEmail;
	private String userNewPw;
	private String userNewPw2;
}

package com.bruncheers.user.dto;

import com.bruncheers.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordVerificationRequestDto {
		
	    private String password;
	    
	    
	    public static PasswordVerificationRequestDto toPasswordDto(User entity) {
			return PasswordVerificationRequestDto.builder().password(entity.getUserPw())
					.build();
		}
}

package com.bruncheers.user.dto;

import com.bruncheers.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateDto {
	private Long userNo;
	private String userEmail; // 사용자이메일
	private String userNickname; // 사용자 닉네임
	private String userPw; 		//사용자 비밀번호
	private String userNewPw; // 사용자 새 비밀번호
	private String userNewPw2; // 사용자 새 비밀번호 확인
	private String userName; // 사용자 이름
	private String userGender; // 사용자 성별
	private String userBirth; // 사용자 생년월일
	private String userHp; // 사용자 휴대폰 번호

	public static UserUpdateDto toUpdateDto(User entity) {
		return UserUpdateDto.builder().userEmail(entity.getUserEmail()).userPw(entity.getUserPw()).userNewPw(entity.getUserPw()).userNewPw2(entity.getUserPw()).userNickname(entity.getUserNickname())
				.userName(entity.getUserName()).userGender(entity.getUserGender()).userBirth(entity.getUserBirth())
				.userHp(entity.getUserHp()).build();
	}
}
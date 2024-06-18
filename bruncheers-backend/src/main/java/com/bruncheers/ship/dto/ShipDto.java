package com.bruncheers.ship.dto;


import com.bruncheers.ship.entity.Ship;
import com.bruncheers.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShipDto {

	
	
	private int sNo;	      //번호
	private String sPhone; 	  //휴대폰번호
	private String sName; 	  //받으실분
	private String sAddr; 	  //받으실곳
	private String sZip;  	  //우편번호
	private String sReq;   	  //요청사항
	private int sDef;   	  //기본배송지여부
	private String sType; //배송지유형
	
	private Long userNo; 	  //회원번호 FK

	
	
	@Override
	public String toString() {
		return "ShipDto [sNo=" + sNo+ ", "
					+ "sPhone=" + sPhone + ","
					+ "sName=" + sName + ", "
					+ "sAddr="+ sAddr + ","
					+ "sZip=" + sZip + ", "
					+ "sReq=" + sReq +", "
					+ "sDef=" + sDef+ ", "
					+ "sType=" + sType + " ]\n";
	}

	
	  public static ShipDto toDto(Ship ship) {
		  User user = ship.getUser();
		  
		  return ShipDto.builder()
				  		.sNo(ship.getSNo())
				  		.sPhone(ship.getSPhone())
				  		.sName(ship.getSName())
				  		.sAddr(ship.getSAddr())
				  		.sZip(ship.getSZip())
				  		.sReq(ship.getSReq())
				  		.sDef(ship.getSDef())
				  		.sType(ship.getSType())
				  		.userNo(user.getUserNo())
				  		.build();
	  }
	 

	
	
}

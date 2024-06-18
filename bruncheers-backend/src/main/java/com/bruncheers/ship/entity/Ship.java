package com.bruncheers.ship.entity;

import java.util.Optional;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.bruncheers.ship.dto.ShipDto;
import com.bruncheers.user.dto.UserDto;
import com.bruncheers.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@DynamicUpdate
@DynamicInsert
@Entity
public class Ship {

	
	@Id
	@SequenceGenerator(name = "ship_s_no_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ship_s_no_SEQ")
	private Integer sNo; 	  //번호

	private String sPhone;	  //휴대폰번호
	private String sName; 	  //받으실분
	private String sAddr; 	  //받으실곳
	private String sZip;  	  //우편번호
	private String sReq;   	  //요청사항
	private int sDef;   	  //기본배송지여부
	private String sType; //배송지유형

	
	
	
	/*****************관계설정*****************/
	//회원
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_no")
	@ToString.Exclude
	private User user;
	

	@Override
	public String toString() {
		return "ShipDto [sNo=" + sNo+ ", "
					+ "sPhone=" + sPhone + ","
					+ "sName=" + sName + ", "
					+ "sAddr="+ sAddr + ","
					+ "sZip=" + sZip + ", "
					+ "sReq=" + sReq +", "
					+ "sDef=" + sDef+ ", "
					+ "sType=" + sType + ","
					+ "userNo=" + user.getUserNo() + " ]\n";
	}

	public static Ship toEntity(ShipDto shipDto, User user) {
		return Ship.builder()
				.sNo(shipDto.getSNo())
				.sName(shipDto.getSName())
				.sPhone(shipDto.getSPhone())
				.sAddr(shipDto.getSAddr())
				.sZip(shipDto.getSZip())
				.sReq(shipDto.getSReq())
				.sDef(shipDto.getSDef())
				.sType(shipDto.getSType())
				.user(user)
				.build();
		
	}
}

package com.bruncheers.ship.service;

import java.util.List;

import com.bruncheers.ship.dto.ShipDto;
import com.bruncheers.ship.entity.Ship;

public interface ShipService {

	// 배송지 입력
	ShipDto createShip(ShipDto shipDto) throws Exception;
	
	// 배송지 수정
	ShipDto updateShip(ShipDto shipDto) throws Exception;
	
	// 배송지 삭제
	void deleteShip(int sNo) throws Exception;
	
	// 한 회원의 배송지 목록리스트
	List<ShipDto> selectByUserUserNo(Long userNo) throws Exception;
	
	// 한 회원의 첫번째 배송지
	ShipDto findFirstShipByUserNo(Long userNo) throws Exception;
	
	// 배송지 번호로 찾기
	ShipDto findById(int sNo) throws Exception;
	
	// 배송지 유형으로 찾기(집 or 회사)
	List<ShipDto> selectBysType(String sType);

}

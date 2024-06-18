package com.bruncheers.ship.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruncheers.exception.ShipNotFoundException;
import com.bruncheers.exception.UserNotFoundException;
import com.bruncheers.ship.dto.ShipDto;
import com.bruncheers.ship.entity.Ship;
import com.bruncheers.ship.repository.ShipRepository;
import com.bruncheers.user.entity.User;
import com.bruncheers.user.repository.UserRepository;

@Service("shipServiceImpl")
public class ShipServiceImpl implements ShipService {

	@Autowired
	ShipRepository shipRepository;
	@Autowired
	UserRepository userRepository;

	// 배송지 등록
	@Override
	public ShipDto createShip(ShipDto shipDto) throws Exception {

		Long userNo = shipDto.getUserNo();

		User user = userRepository.findById(userNo).get();

		Ship ship = shipRepository.save(Ship.toEntity(shipDto, user));
		ShipDto sDto = ShipDto.toDto(ship);
		return sDto;
	}

	// 배송지 수정
	@Override
	public ShipDto updateShip(ShipDto shipDto) throws Exception {
		Optional<Ship> updateShip = shipRepository.findById(shipDto.getSNo());

		if (updateShip.isPresent()) {
			Ship updatedShip = updateShip.get();

			updatedShip.setSAddr(shipDto.getSAddr());
			updatedShip.setSDef(shipDto.getSDef());
			updatedShip.setSName(shipDto.getSName());
			updatedShip.setSPhone(shipDto.getSPhone());
			updatedShip.setSReq(shipDto.getSReq());
			updatedShip.setSType(shipDto.getSType());
			updatedShip.setSZip(shipDto.getSZip());

			Ship savedShip = shipRepository.save(updatedShip);

			return ShipDto.toDto(savedShip);

		} else {
			throw new ShipNotFoundException("배송지 번호: " + shipDto.getSNo() + "를 찾을 수 없습니다.");
		}
	}

	// 배송지 삭제
	@Override
	public void deleteShip(int sNo) throws Exception {
		shipRepository.deleteById(sNo);
	}

	// 회원 한 명의 배송지 목록 조회
	@Override
	public List<ShipDto> selectByUserUserNo(Long userNo) throws Exception {
		Optional<User> optionalUser = userRepository.findById(userNo);

		if (optionalUser.isPresent()) {
			User user = optionalUser.get();

			List<Ship> userShips = user.getShips();
			List<ShipDto> userShipDtos = new ArrayList<>();

			for (Ship ship : userShips) {
				userShipDtos.add(ShipDto.toDto(ship));
			}
			return userShipDtos;

		} else {
			throw new UserNotFoundException("회원번호 " + userNo + "의 배송지목록을 찾을 수 없습니다.");
		}
	}
	
	// 한 회원의 첫번째 배송지 조회
	@Override
	public ShipDto findFirstShipByUserNo(Long userNo) throws Exception {
		   Ship ship = shipRepository.findFirstByUserNoOrderBySNoAsc(userNo);
		    if (ship != null) {
		        return ShipDto.toDto(ship);
		    } else {
		        throw new Exception("해당 사용자의 배송지를 찾을 수 없습니다.");
		    }
	}

	// 배송지 한개 조회
	@Override
	public ShipDto findById(int sNo) throws Exception {
		Optional<Ship> optionalShip = shipRepository.findById(sNo);
		if (optionalShip.isPresent()) {
			Ship ship = optionalShip.get();
			return ShipDto.toDto(ship);
		} else {
			throw new ShipNotFoundException("배송지 번호: " + sNo + "를 찾을 수 없습니다.");
		}
	}

	// 배송지 타입별 목록 조회 (집 or 회사)
	@Override
	public List<ShipDto> selectBysType(String sType) {
		List<Ship> ships = shipRepository.findBysType(sType);
		List<ShipDto> shipDtos = new ArrayList<>();

		for (Ship ship : ships) {
			ShipDto shipDto = ShipDto.toDto(ship);
			shipDtos.add(shipDto);
		}

		return shipDtos;
	}

}

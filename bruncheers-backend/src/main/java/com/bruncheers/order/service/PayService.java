package com.bruncheers.order.service;

import com.bruncheers.order.dto.PayDto;

public interface PayService {

	// 결제 생성
	PayDto creatPay(PayDto payDto) throws Exception;

	// 결제 삭제
	void deletePay(int paNo) throws Exception;

	// 결제 하나 조회
	PayDto findByPay(int paNo) throws Exception;

	// 결제타입에 따른 paNo 찾기
	Integer findBypaType(String paType) throws Exception;

}

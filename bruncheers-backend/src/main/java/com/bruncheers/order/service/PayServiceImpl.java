package com.bruncheers.order.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruncheers.exception.PayNotFoundException;
import com.bruncheers.order.dto.PayDto;
import com.bruncheers.order.entity.Pay;
import com.bruncheers.order.repository.PayRepository;

@Service("payServiceImpl")
public class PayServiceImpl implements PayService {

	@Autowired
	PayRepository payRepository;

	@Override
	public PayDto creatPay(PayDto payDto) throws Exception {
		Pay pay = payRepository.save(Pay.toEntity(payDto));
		PayDto paDto = PayDto.toDto(pay);
		return paDto;
	}

	@Override
	public void deletePay(int paNo) throws Exception {
		payRepository.deleteById(paNo);

	}

	@Override
	public PayDto findByPay(int paNo) throws Exception {
		Optional<Pay> oPay = payRepository.findById(paNo);
		if (oPay.isPresent()) {
			Pay pay = oPay.get();
			return PayDto.toDto(pay);

		} else {
			throw new PayNotFoundException(paNo + "찾을 수 없습니다.");
		}

	}

	@Override
	public Integer findBypaType(String paType) throws Exception {
		return payRepository.findBypaType(paType).getPaNo();
	}

}

package com.bruncheers.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruncheers.cart.repository.CartRepository;
import com.bruncheers.coupon.entity.Coupon;
import com.bruncheers.coupon.repository.CouponRepository;
import com.bruncheers.exception.UserNotFoundException;
import com.bruncheers.order.dto.OrderCreateDto;
import com.bruncheers.order.dto.OrderDto;
import com.bruncheers.order.dto.OrderItemDto;
import com.bruncheers.order.dto.OrderItemWithProductOptionDto;
import com.bruncheers.order.dto.OrderWithAllDto;
import com.bruncheers.order.dto.OrderWithOrderItemDto;
import com.bruncheers.order.entity.Order;
import com.bruncheers.order.entity.OrderItem;
import com.bruncheers.order.entity.Pay;
import com.bruncheers.order.repository.OrderItemRepository;
import com.bruncheers.order.repository.OrderRepository;
import com.bruncheers.order.repository.PayRepository;
import com.bruncheers.product.repository.ProductOptionRepository;
import com.bruncheers.review.dao.ReviewRepository;
import com.bruncheers.review.entity.Review;
import com.bruncheers.user.dto.UserDto;
import com.bruncheers.user.entity.User;
import com.bruncheers.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service("orderServiceImpl")
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private ProductOptionRepository productOptionRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private PayRepository payRepository;

	@Autowired
	private CouponRepository couponRepository;

	@Autowired
	private PayService payService;

	@Autowired
	ReviewRepository reviewRepository;

	/*@Transactional
	@Override
	public Integer ProductOrderSave(OrderCreateDirectDto orderCreateDirectDto) throws Exception {
	
		Optional<User> optionalUser = userRepository.findById(orderCreateDirectDto.getOrderDto().getUserNo());
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException("회원을 찾을 수 없습니다.");
		}
		User user = optionalUser.get();
	
		Optional<Pay> optionalPay = payRepository.findById(orderCreateDirectDto.getOrderDto().getPaNo());
		if (!optionalPay.isPresent()) {
			throw new PayNotFoundException("결제내역을 찾을 수 없습니다.");
		}
	
		Pay pay = optionalPay.get();
	
		payRepository.save(pay);
	
		// 상품 먼저 받기
		ProductOption productOption = productOptionRepository.findById(orderCreateDirectDto.getPoNo()).get();
	
		Order order = orderRepository.save(Order.toEntity(orderCreateDirectDto.getOrderDto(), user, pay));
	
		OrderItem orderItem = OrderItem.builder().oiQty(1).order(order).productOption(productOption).build();
	
		orderItemRepository.save(orderItem);
		
		// 쿠폰 적용
		if (orderCreateDirectDto.getCoupNo() != null) {
			Coupon applyCoup = couponRepository.findById(orderCreateDirectDto.getCoupNo()).get();
			applyCoup.setOrder(order);
			applyCoup.setCoupStatus("0");
			couponRepository.save(applyCoup);
		}
	
		return 1;
	
	}*/
	/***************** 테스트완료 *********************/
	@Override
	public OrderWithOrderItemDto findListByOrderNo(int oNo) throws Exception {
		Order orderEntity = orderRepository.findById(oNo).get();

		Review review = null;

		OrderWithOrderItemDto orderDto = OrderWithOrderItemDto.toDto(orderEntity);
		
		List<OrderItem> orderItemList = orderEntity.getOrderItems();
		
		List<OrderItemWithProductOptionDto> orderItemDtoList = orderDto.getOrderItemDtoList();
		for (OrderItemWithProductOptionDto orderItem : orderItemDtoList) {
			review = reviewRepository.findByOrderItemOiNo(orderItem.getOiNo());
			if (review != null) {
				orderItem.setReviewNo(review.getRNo());
			} else {
				orderItem.setReviewNo(0);
			}
		}

		return orderDto;
	}

	/*
	 * @Override public List<OrderDto> findListByUserId(long userNo) { List<Order>
	 * orderUserList = orderRepository.findByUserNo(userNo); List<OrderDto>
	 * orderDtoUserList = new ArrayList<>(); for (Order order : orderUserList) {
	 * orderDtoUserList.add(OrderDto.toDto(order)); } return orderDtoUserList; }
	 */

	@Transactional
	@Override
	public List<OrderWithOrderItemDto> findListByUserId(Long userNo) {
		List<Order> orderList = orderRepository.findByUserUserNo(userNo);
		List<Review> reviewList = reviewRepository.findByUserUserNo(userNo);
		List<OrderWithOrderItemDto> orderWithOrderItemDtoList = new ArrayList<>();

		for (Order order : orderList) {
			OrderWithOrderItemDto outputOrderDto = OrderWithOrderItemDto.toDto(order);

			List<OrderItemWithProductOptionDto> orderItemDtoList = outputOrderDto.getOrderItemDtoList();
			for (OrderItemWithProductOptionDto orderItemDto : orderItemDtoList) {
				List<Review> reviewNoList = findReviewForOrderItem(orderItemDto.getOiNo(), reviewList);
				for (Review reviewNo : reviewNoList) {
					if (reviewNo != null) {
						orderItemDto.setReviewNo(reviewNo.getRNo());
					} else {
						orderItemDto.setReviewNo(0);

					}
				}

			}

			orderWithOrderItemDtoList.add(outputOrderDto);
		}

		return orderWithOrderItemDtoList;
	}

	private List<Review> findReviewForOrderItem(int oiNo, List<Review> reviewList) {
		List<Review> reviewNoList = new ArrayList<>();
		for (Review review : reviewList) {
			if (review.getOrderItem().getOiNo().equals(oiNo)) {

				reviewNoList.add(review);
			}
		}
		return reviewNoList;

	}

	/*
	 * @Override public List<OrderWithOrderItemDto> findListByUserId(Long userNo) {
	 * List<Order> orderList = orderRepository.findByUserUserNo(userNo);
	 * List<OrderWithOrderItemDto> orderWithOrderItemDtoList = new ArrayList<>();
	 * for (Order order : orderList) {
	 * orderWithOrderItemDtoList.add(OrderWithOrderItemDto.toDto(order)); } return
	 * orderWithOrderItemDtoList; }
	 */

	/*
	 * @Transactional
	 * 
	 * @Override public List<OrderWithOrderItemDto> findListByUserId(Long userNo) {
	 * List<Order> orderList = orderRepository.findByUserUserNo(userNo);
	 * List<Review> reviewList = reviewRepository.findByUser_userNo(userNo);
	 * 
	 * List<OrderWithOrderItemDto> orderWithOrderItemDtoList = new ArrayList<>();
	 * 
	 * for (Order order : orderList) { List<OrderItemWithProductOptionDto>
	 * reviewWithOrderItemDtoList = new ArrayList<>(); OrderWithOrderItemDto
	 * outputOrderDto = (OrderWithOrderItemDto.toDto(order));
	 * 
	 * reviewWithOrderItemDtoList = outputOrderDto.getOrderItemDtoList();
	 * 
	 * for (OrderItemWithProductOptionDto reviewWithOrderItemDto :
	 * reviewWithOrderItemDtoList) { for (int i = 0; i < reviewList.size(); i++) {
	 * if (reviewWithOrderItemDto.getOiNo() ==
	 * reviewList.get(i).getOrderItem().getOiNo()) {
	 * reviewWithOrderItemDto.setReviewNo(reviewList.get(i).getRNo()); } else {
	 * reviewWithOrderItemDto.setReviewNo(0); }
	 * 
	 * }
	 * 
	 * }
	 * 
	 * orderWithOrderItemDtoList.add(outputOrderDto);
	 * 
	 * } System.out.println(orderWithOrderItemDtoList); return
	 * orderWithOrderItemDtoList; }
	 */

	@Override
	public OrderDto deleteOrder(int oNo) throws Exception {
		Order order = orderRepository.findById(oNo).orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));
		orderRepository.deleteById(oNo);
		OrderDto orderDto = OrderDto.toDto(order);
		return orderDto;

	}

	@Override
	public void deleteAllOrder(long userNo) throws Exception {
		/*
		 * List<Order> orderList = orderRepository.findByUserNo(userNo); List<OrderDto>
		 * orderDtoList = new ArrayList<>(); for (Order order : orderList) {
		 * orderDtoList.add(OrderDto.toDto(order)); }
		 * orderRepository.deleteAllInBatch(orderList); return orderDtoList;
		 */

		List<Order> orders = orderRepository.findByUserNo(userNo);

		for (Order order : orders) {
			// 주문에 속한 모든 주문 아이템을 삭제
			List<OrderItem> orderItems = order.getOrderItems();
			for (OrderItem orderItem : orderItems) {
				orderItemRepository.delete(orderItem);
			}

			// 주문 삭제
			orderRepository.delete(order);
		}
	}

	@Override
	@Transactional
	public Integer createOrder(OrderCreateDto orderCreateDto) throws Exception {

		System.out.println(orderCreateDto);

		Optional<User> optionalUser = userRepository.findById(orderCreateDto.getUserNo());
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException("회원을 찾을 수 없습니다.");
		}
		User user = optionalUser.get();

		Integer paNo = payService.findBypaType(orderCreateDto.getPayType());

		Pay pay = payRepository.findById(paNo).get();

		Order order = orderRepository.save(Order.toEntity(orderCreateDto, user, pay));

		List<OrderItem> orderItemList = new ArrayList<>();

		for (Integer poNo : orderCreateDto.getPoNoList()) {
			OrderItem orderItem = OrderItem.builder().oiQty(1).order(order)
					.productOption(productOptionRepository.findById(poNo).get()).build();
			orderItemList.add(orderItem);
		}

		orderItemRepository.saveAll(orderItemList);

		// 쿠폰 적용
		if (orderCreateDto.getCoupNo() != null) {
			Coupon applyCoup = couponRepository.findById(orderCreateDto.getCoupNo()).get();
			applyCoup.setOrder(order);
			applyCoup.setCoupStatus("0");
			couponRepository.save(applyCoup);
		}

		// 만약 카트에서 넘어온 경우 장바구니에 있는 상품들은 지워지게!
		if (orderCreateDto.getCNoList() != null) {
			cartRepository.deleteAllBycNoIn(orderCreateDto.getCNoList());
		}
		;

		return 1;
	}

	@Override
	public List<OrderWithAllDto> finfByAll() {
		List<Order> orderList = orderRepository.findAll();
		List<OrderWithAllDto> orderWithOrderItemDtoList = new ArrayList<>();
		for (Order order : orderList) {
			orderWithOrderItemDtoList.add(OrderWithAllDto.toDto(order));
		}
		return orderWithOrderItemDtoList;
	}

}
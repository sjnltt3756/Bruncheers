
--PRODUCT_CATEGORY INSERT
INSERT INTO product_category (cat_no, cat_name)VALUES (product_category_cat_no_seq.NEXTVAL, '1일1식');
INSERT INTO product_category (cat_no, cat_name) VALUES (product_category_cat_no_seq.NEXTVAL, '1일2식');
INSERT INTO product_category (cat_no, cat_name) VALUES (product_category_cat_no_seq.NEXTVAL, '1일3식');

--PRODUCT INSERT
INSERT INTO product (p_no, p_name, p_price, p_image, p_detail, p_deimg, p_reg, cat_no)
VALUES (product_p_no_seq.NEXTVAL, '1일1식 든든 도시락', 53800, 'do1.jpg', '브런치얼스만의 특별레시피로 제조한 든든하면서 가벼운 프리미엄 도시락', 'do_detail1.jpg', sysdate, 1);
INSERT INTO product (p_no, p_name, p_price, p_image, p_detail, p_deimg, p_reg, cat_no)
VALUES (product_p_no_seq.NEXTVAL, '1일1식 신선 샐러드', 53800, 'sal1.jpg', '브런치얼스의 농장에서 키운 채소들로 만든 건강하고 즐거움 가득 신선 샐러드', 'sal_detail1.jpg', sysdate, 1);
INSERT INTO product (p_no, p_name, p_price, p_image, p_detail, p_deimg, p_reg, cat_no)
VALUES (product_p_no_seq.NEXTVAL, '1일1식 가득 샌드위치', 53800, 'san1.jpg', '브런치얼스의 맛있고 여유있는 한끼, 포만감 가득 샌드위치', 'san_detail1.jpg', sysdate, 1);

INSERT INTO product (p_no, p_name, p_price, p_image, p_detail, p_deimg, p_reg, cat_no)
VALUES (product_p_no_seq.NEXTVAL, '1일2식 든든 도시락', 107800, 'do2.jpg', '브런치얼스만의 특별레시피로 제조한 든든하면서 가벼운 프리미엄 도시락', 'do_detail2.jpg', sysdate, 2);
INSERT INTO product (p_no, p_name, p_price, p_image, p_detail, p_deimg, p_reg, cat_no)
VALUES (product_p_no_seq.NEXTVAL, '1일2식 신선 샐러드', 107800, 'sal2.jpg', '브런치얼스의 농장에서 키운 채소들로 만든 건강하고 즐거움 가득 신선 샐러드', 'sal_detai2.jpg', sysdate, 2);
INSERT INTO product (p_no, p_name, p_price, p_image, p_detail, p_deimg, p_reg, cat_no)
VALUES (product_p_no_seq.NEXTVAL, '1일2식 가득 샌드위치', 107800, 'san2.jpg', '브런치얼스의 맛있고 여유있는 한끼, 포만감 가득 샌드위치', 'san_detail2.jpg', sysdate, 2);

INSERT INTO product (p_no, p_name, p_price, p_image, p_detail, p_deimg, p_reg, cat_no)
VALUES (product_p_no_seq.NEXTVAL, '1일3식 든든 도시락', 143800, 'do3.jpg', '브런치얼스만의 특별레시피로 제조한 든든하면서 가벼운 프리미엄 도시락', 'do_detail13.jpg', sysdate, 3);
INSERT INTO product (p_no, p_name, p_price, p_image, p_detail, p_deimg, p_reg, cat_no)
VALUES (product_p_no_seq.NEXTVAL, '1일3식 신선 샐러드', 143800, 'sal3.jpg', '브런치얼스의 농장에서 키운 채소들로 만든 건강하고 즐거움 가득 신선 샐러드', 'sal_detai3.jpg', sysdate, 3);
INSERT INTO product (p_no, p_name, p_price, p_image, p_detail, p_deimg, p_reg, cat_no)
VALUES (product_p_no_seq.NEXTVAL, '1일3식 가득 샌드위치', 143800, 'san3.jpg', '브런치얼스의 맛있고 여유있는 한끼, 포만감 가득 샌드위치', 'san_detail3.jpg', sysdate, 3);


--PRODUCT_OPTION INSERT
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '1주', 0, 1);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '1주', 0, 2);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '1주', 0, 3);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '2주', 51500, 1);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '2주', 51500, 2);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '2주', 51500, 3);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '3주', 102200, 1);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '3주', 102200, 2);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '3주', 102200, 3);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '4주', 148400, 1);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '4주', 148400, 2);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '4주', 148400, 3);

INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '1주', 0, 4);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '1주', 0, 5);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '1주', 0, 6);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '2주', 107100, 4);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '2주', 107100, 5);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '2주', 107100, 6);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '3주', 198900, 4);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '3주', 198900, 5);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '3주', 198900, 6);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '4주', 275400, 4);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '4주', 275400, 5);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '4주', 275400, 6);

INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '1주', 0, 7);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '1주', 0, 8);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '1주', 0, 9);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '2주', 132800, 7);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '2주', 132800, 8);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '2주', 132800, 9);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '3주', 262700, 7);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '3주', 262700, 8);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '3주', 262700, 9);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '4주', 392700, 7);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '4주', 392700, 8);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '4주', 392700, 9);

-- ORDER , ORDERITEM INSERT
INSERT INTO orders (o_no, o_price, o_name, o_zip, o_addr, o_phone, o_date, o_req, u_no) VALUES (orders_o_no_seq.NEXTVAL, 161400, '홍길동', 12345, '서울시 강남구 역삼동 123번지', '010-1234-5678', SYSDATE, '상품검수 꼼꼼하게 해주세요', 1);
INSERT INTO order_item (oi_no, oi_qty, o_no, po_no) VALUES (order_item_oi_no_seq.NEXTVAL, 1, orders_o_no_seq.CURRVAL, 1);
INSERT INTO order_item (oi_no, oi_qty, o_no, po_no) VALUES (order_item_oi_no_seq.NEXTVAL, 1, orders_o_no_seq.CURRVAL, 2);
INSERT INTO order_item (oi_no, oi_qty, o_no, po_no) VALUES (order_item_oi_no_seq.NEXTVAL, 1, orders_o_no_seq.CURRVAL, 3);

INSERT INTO orders (o_no, o_price, o_name, o_zip, o_addr, o_phone, o_date, o_req, u_no) VALUES (orders_o_no_seq.NEXTVAL, 1073000, '김미진', 17071, '경기도 용인시 기흥구 123번지', '010-9822-7106', SYSDATE, '오이 알러지 있어서 오이 들어간거는 빼주세요 감사합니다.', 2);
INSERT INTO order_item (oi_no, oi_qty, o_no, po_no) VALUES (order_item_oi_no_seq.NEXTVAL, 1, orders_o_no_seq.CURRVAL, 34);
INSERT INTO order_item (oi_no, oi_qty, o_no, po_no) VALUES (order_item_oi_no_seq.NEXTVAL, 1, orders_o_no_seq.CURRVAL, 35);

-- SHIP INSERT
INSERT INTO ship (s_no, s_phone, s_name, s_addr, s_zip, s_req, s_def, s_type, u_no)
VALUES (ship_s_no_seq.NEXTVAL, '010-1234-5678', '홍길동', '서울시 강남구 역삼동 123번지', '12345', '문 앞에 놔주세요', 1, 1, 1);
VALUES (ship_s_no_seq.NEXTVAL, '010-9822-7106', '김미진', '경기도 용인시 기흥구 1123번길', '15478', '배송 시 연락바랍니다', 1, 1, 2);
VALUES (ship_s_no_seq.NEXTVAL, '010-1548-7570', '강기호', '경기도 수원시 영통구 123번길', '17058', '배송 전 문자주세요', 1, 2, 3);
VALUES (ship_s_no_seq.NEXTVAL, '010-9878-1256', '진선미', '경기도 화성시 동탄역로 15-88', '12858', '빠른 배송 부탁드립니다', 1, 2, 4);

-- REVIEW INSERT
INSERT INTO review (r_no, r_star, r_image, r_content, r_reg, p_no, u_no)
VALUES (review_r_no_seq.NEXTVAL, 5, 'redo1_img.jpg', '회사 점심용으로 들고다니는데 정말 좋아요. 전자레인지 1분이면 따뜻하게 먹을 수 있구용', SYSDATE, 1, 1);
VALUES (review_r_no_seq.NEXTVAL, 5, 'resal_img.jpg', '혼자살면 채소 챙겨먹기가 정말 쉽지 않아서 구매하게 됐는데 정말 좋아요. 쓰레기도 많이 안나오고 양도 적당합니다', SYSDATE, 2, 1);
VALUES (review_r_no_seq.NEXTVAL, 2, 'resan1_img.jpg', '사진이랑 달라요.. 샌드위치 배송오면서 험난해서 다이어트가 됐을까요..', SYSDATE, 3, 3);
VALUES (review_r_no_seq.NEXTVAL, 4, 'redo2_img.jpg', '다 좋은데 칸이 분리됐으면 좋겠어요. 차갑게 먹고싶은거까지 돌려지니까 맛이 오묘해요', SYSDATE, 4, 2);
VALUES (review_r_no_seq.NEXTVAL, 1, 'resal2_img.jpg', '채소가 싱싱하지 않아요! 끝에 다 갈변되어서 왔어요ㅜ 한두번이면 이해하겠는데 너무해여', SYSDATE, 5, 2);
VALUES (review_r_no_seq.NEXTVAL, 5, 'resan2_img.jpg', '샌드위치 러버로서 넘 좋아요 가격도 합리적이고 매번 다양하게 와서 좋은데 신메뉴도 조금씩 나왔으면 좋겠어요! 6개월째 같은 루틴으로 먹어서 살짝 물려요오', SYSDATE, 6, 4);
VALUES (review_r_no_seq.NEXTVAL, 5, 'redo3_img.jpg', '맛있음', SYSDATE, 7, 3);
VALUES (review_r_no_seq.NEXTVAL, 5, 'resal3_img.jpg', '주변에서 많이들 먹어서 영업당했는데 좋아요 굿 영양밸러스 굿. 포케도 출시하면 좋을듯', SYSDATE, 8, 1);
VALUES (review_r_no_seq.NEXTVAL, 5, 'resan3_img.jpg', '남편이 샌드위치를 너무 좋아해서 매번 시키는데 좋아요 :)', SYSDATE, 9, 2);
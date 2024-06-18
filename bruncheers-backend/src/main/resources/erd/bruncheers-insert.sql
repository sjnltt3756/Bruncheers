-- USERINFO INSERT
INSERT INTO userinfo (user_no, user_email, user_pw, user_name, user_gender, user_birth, user_hp, user_nickname, role)
VALUES (userinfo_user_no_seq.NEXTVAL, 'admin', 'admin', '관리자', '남', '2020-05-13', '010-1234-5678', '브런치얼스', 'ADMIN');

INSERT INTO userinfo (user_no, user_email, user_pw, user_name, user_gender, user_birth, user_hp, user_nickname, role)
VALUES (userinfo_user_no_seq.NEXTVAL, 'user2@naver.com', '2222', '이가을', '여', '2020-07-18', '010-2345-6789', '코코볼', 'USER');

INSERT INTO userinfo (user_no, user_email, user_pw, user_name, user_gender, user_birth, user_hp, user_nickname, role)
VALUES (userinfo_user_no_seq.NEXTVAL, 'user3@gmail.com', '3333', '김마음', '남', '2018-08-12', '010-3456-7890', '말티즈대장', 'USER');

INSERT INTO userinfo (user_no, user_email, user_pw, user_name, user_gender, user_birth, user_hp, user_nickname, role)
VALUES (userinfo_user_no_seq.NEXTVAL, 'user4@gmail.com', '4444', '유동주', '남', '2018-10-30', '010-4567-8901', '동동주', 'USER');

INSERT INTO userinfo (user_no, user_email, user_pw, user_name, user_gender, user_birth, user_hp, user_nickname, role)
VALUES (userinfo_user_no_seq.NEXTVAL, 'user5@gmail.com', '5555', '유주랑', '여', '2019-07-27', '010-5678-9012', '동주랑', 'USER');

INSERT INTO userinfo (user_no, user_email, user_pw, user_name, user_gender, user_birth, user_hp, user_nickname, role)
VALUES (userinfo_user_no_seq.NEXTVAL, 'user6@gmail.com', '6666', '강홍도', '여', '1992-11-10', '010-3267-2323', '얄루', 'USER');

INSERT INTO userinfo (user_no, user_email, user_pw, user_name, user_gender, user_birth, user_hp, user_nickname, role)
VALUES (userinfo_user_no_seq.NEXTVAL, 'user7@gmail.com', '7777', '김새봄', '남', '2020-05-13', '010-1234-5678', '율잣', 'USER');


--PRODUCT_CATEGORY INSERT
INSERT INTO product_category (cat_no, cat_name)VALUES (product_category_cat_no_seq.NEXTVAL, '1일 1식');
INSERT INTO product_category (cat_no, cat_name) VALUES (product_category_cat_no_seq.NEXTVAL, '1일 2식');
INSERT INTO product_category (cat_no, cat_name) VALUES (product_category_cat_no_seq.NEXTVAL, '1일 3식');

--PRODUCT INSERT
INSERT INTO product (p_no, p_name, p_price, p_image, p_detail, p_deimg, p_reg, cat_no) VALUES (product_p_no_seq.NEXTVAL, '1일1식 든든 도시락', 53800, 'lunchBox.jpg', '브런치얼스만의 특별레시피로 제조한 든든하면서 가벼운 프리미엄 도시락', 'productDetailAll.jpg', sysdate, 1 );
INSERT INTO product (p_no, p_name, p_price, p_image, p_detail, p_deimg, p_reg, cat_no) VALUES (product_p_no_seq.NEXTVAL, '1일1식 신선 샐러드', 53800, 'sal.jpg', '브런치얼스의 농장에서 키운 채소들로 만든 건강하고 즐거움 가득 신선 샐러드', 'productDetailAll.jpg', sysdate, 1);
INSERT INTO product (p_no, p_name, p_price, p_image, p_detail, p_deimg, p_reg, cat_no) VALUES (product_p_no_seq.NEXTVAL, '1일1식 가득 샌드위치', 53800, 'san.png', '브런치얼스의 맛있고 여유있는 한끼, 포만감 가득 샌드위치', 'productDetailAll.jpg', sysdate, 1);

INSERT INTO product (p_no, p_name, p_price, p_image, p_detail, p_deimg, p_reg, cat_no) VALUES (product_p_no_seq.NEXTVAL, '1일2식 든든 도시락', 107800, 'lunchBox.jpg', '브런치얼스만의 특별레시피로 제조한 든든하면서 가벼운 프리미엄 도시락', 'productDetailAll.jpg', sysdate, 2 );
INSERT INTO product (p_no, p_name, p_price, p_image, p_detail, p_deimg, p_reg, cat_no) VALUES (product_p_no_seq.NEXTVAL, '1일2식 신선 샐러드', 107800, 'sal.jpg', '브런치얼스의 농장에서 키운 채소들로 만든 건강하고 즐거움 가득 신선 샐러드', 'productDetailAll.jpg', sysdate, 2);
INSERT INTO product (p_no, p_name, p_price, p_image, p_detail, p_deimg, p_reg, cat_no) VALUES (product_p_no_seq.NEXTVAL, '1일2식 가득 샌드위치', 107800, 'san.png', '브런치얼스의 맛있고 여유있는 한끼, 포만감 가득 샌드위치', 'productDetailAll.jpg', sysdate, 2);

INSERT INTO product (p_no, p_name, p_price, p_image, p_detail, p_deimg, p_reg, cat_no) VALUES (product_p_no_seq.NEXTVAL, '1일3식 든든 도시락', 143800, 'lunchBox.jpg', '브런치얼스만의 특별레시피로 제조한 든든하면서 가벼운 프리미엄 도시락', 'productDetailAll.jpg', sysdate, 3 );
INSERT INTO product (p_no, p_name, p_price, p_image, p_detail, p_deimg, p_reg, cat_no) VALUES (product_p_no_seq.NEXTVAL, '1일3식 신선 샐러드', 143800, 'sal.jpg', '브런치얼스의 농장에서 키운 채소들로 만든 건강하고 즐거움 가득 신선 샐러드', 'productDetailAll.jpg', sysdate, 3);
INSERT INTO product (p_no, p_name, p_price, p_image, p_detail, p_deimg, p_reg, cat_no) VALUES (product_p_no_seq.NEXTVAL, '1일3식 가득 샌드위치', 143800, 'san.png', '브런치얼스의 맛있고 여유있는 한끼, 포만감 가득 샌드위치', 'productDetailAll.jpg', sysdate, 3);


--PRODUCT_OPTION INSERT
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '1주', 0, 1);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '2주', 51500, 1);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '3주', 102200, 1);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '4주', 148400, 1);

INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '1주', 0, 2);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '2주', 51500, 2);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '3주', 102200, 2);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '4주', 148400, 2);

INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '1주', 0, 3);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '2주', 51500, 3);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '3주', 102200, 3);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '4주', 148400, 3);


INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '1주', 0, 4);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '2주', 107100, 4);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '3주', 198900, 4);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '4주', 275400, 4);


INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '1주', 0, 5);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '2주', 107100, 5);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '3주', 198900, 5);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '4주', 275400, 5);


INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '1주', 0, 6);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '2주', 107100, 6);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '3주', 198900, 6);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '4주', 275400, 6);


INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '1주', 0, 7);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '2주', 132800, 7);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '3주', 262700, 7);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '4주', 392700, 7);

INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '1주', 0, 8);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '2주', 132800, 8);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '3주', 262700, 8);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '4주', 392700, 8);


INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '1주', 0, 9);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '2주', 132800, 9);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '3주', 262700, 9);
INSERT INTO product_option (po_no, po_name, po_price, p_no) VALUES (product_option_po_no_seq.NEXTVAL, '4주', 392700, 9);

--CART INSERT
INSERT INTO cart (c_no, c_qty, user_no, po_no) VALUES (cart_c_no_seq.NEXTVAL, 1, 7, 10);
INSERT INTO cart (c_no, c_qty, user_no, po_no) VALUES (cart_c_no_seq.NEXTVAL, 1, 7, 4);

INSERT INTO cart (c_no, c_qty, user_no, po_no) VALUES (cart_c_no_seq.NEXTVAL, 1, 2, 3);
INSERT INTO cart (c_no, c_qty, user_no, po_no) VALUES (cart_c_no_seq.NEXTVAL, 1, 2, 8);

INSERT INTO cart (c_no, c_qty, user_no, po_no) VALUES (cart_c_no_seq.NEXTVAL, 1, 2, 1);
INSERT INTO cart (c_no, c_qty, user_no, po_no) VALUES (cart_c_no_seq.NEXTVAL, 1, 2, 2);

INSERT INTO cart (c_no, c_qty, user_no, po_no) VALUES (cart_c_no_seq.NEXTVAL, 1, 3, 5);
INSERT INTO cart (c_no, c_qty, user_no, po_no) VALUES (cart_c_no_seq.NEXTVAL, 1, 3, 6);

INSERT INTO cart (c_no, c_qty, user_no, po_no) VALUES (cart_c_no_seq.NEXTVAL, 1, 4, 20);
INSERT INTO cart (c_no, c_qty, user_no, po_no) VALUES (cart_c_no_seq.NEXTVAL, 1, 4, 16);

INSERT INTO cart (c_no, c_qty, user_no, po_no) VALUES (cart_c_no_seq.NEXTVAL, 1, 5, 4);
INSERT INTO cart (c_no, c_qty, user_no, po_no) VALUES (cart_c_no_seq.NEXTVAL, 1, 5, 8);

INSERT INTO cart (c_no, c_qty, user_no, po_no) VALUES (cart_c_no_seq.NEXTVAL, 1, 6, 2);
INSERT INTO cart (c_no, c_qty, user_no, po_no) VALUES (cart_c_no_seq.NEXTVAL, 1, 6, 17);

-- PAY INSERT
INSERT INTO pay (pa_no, pa_type)
VALUES (pay_pa_no_seq.NEXTVAL, '카드');
INSERT INTO pay (pa_no, pa_type)
VALUES (pay_pa_no_seq.NEXTVAL, '간편결제');
INSERT INTO pay (pa_no, pa_type)
VALUES (pay_pa_no_seq.NEXTVAL,'계좌이체'); 
INSERT INTO pay (pa_no, pa_type)
VALUES (pay_pa_no_seq.NEXTVAL,'휴대폰');

-- ORDER , ORDERITEM INSERT
INSERT INTO orders (o_no, o_price, o_name, o_zip, o_addr, o_phone, o_date, o_req, o_sreq,o_discountprice, user_no, pa_no) VALUES (orders_o_no_seq.NEXTVAL, 161400, '김새봄', 12345, '서울시 강남구 역삼동 123번지', '010-1234-5678', SYSDATE, '저는 콩밥 못먹어요 빼주세요! 샐러드 야채는 무조건 신선하게 해주세요','문 앞에 놓아주시면 좋겠네용 no bell.. no knock.. plz (˵ •̀ ᴗ - ˵ ) ✧',0, 7,1);
INSERT INTO order_item (oi_no, oi_qty, o_no, po_no) VALUES (order_item_oi_no_seq.NEXTVAL, 1, orders_o_no_seq.CURRVAL, 1);
INSERT INTO order_item (oi_no, oi_qty, o_no, po_no) VALUES (order_item_oi_no_seq.NEXTVAL, 1, orders_o_no_seq.CURRVAL, 5);
INSERT INTO order_item (oi_no, oi_qty, o_no, po_no) VALUES (order_item_oi_no_seq.NEXTVAL, 1, orders_o_no_seq.CURRVAL, 9);

INSERT INTO orders (o_no, o_price, o_name, o_zip, o_addr, o_phone, o_date, o_req, o_sreq,o_discountprice, user_no, pa_no) VALUES (orders_o_no_seq.NEXTVAL, 545300, '이가을', 23453, '서울시 중랑구 면목동 999번지', '010-2345-6789', SYSDATE, '저는 싱겁게 먹는데 간 조절이 되나요..? 된다구 해주세요','난 배송올떄가 제일 설레이더라 특히 벨소리 들릴떄 후욱',0, 2,2);
INSERT INTO order_item (oi_no, oi_qty, o_no, po_no) VALUES (order_item_oi_no_seq.NEXTVAL, 1, orders_o_no_seq.CURRVAL, 1);
INSERT INTO order_item (oi_no, oi_qty, o_no, po_no) VALUES (order_item_oi_no_seq.NEXTVAL, 1, orders_o_no_seq.CURRVAL, 18);
INSERT INTO order_item (oi_no, oi_qty, o_no, po_no) VALUES (order_item_oi_no_seq.NEXTVAL, 1, orders_o_no_seq.CURRVAL, 30);


INSERT INTO orders (o_no, o_price, o_name, o_zip, o_addr, o_phone, o_date, o_req, o_sreq,o_discountprice, user_no, pa_no) VALUES (orders_o_no_seq.NEXTVAL, 428000, '김마음', 23234, '서울시 관악구 신림동 233번지', '010-3456-7890', SYSDATE, '도시락: 불고기/영양밥 x, 샌드위치: 미트볼 샌드위치 x','배송할때 노크하고 뚱이처럼 사-랑해요~ 해주시면 안되용?ㅎ',0, 3,3);
INSERT INTO order_item (oi_no, oi_qty, o_no, po_no) VALUES (order_item_oi_no_seq.NEXTVAL, 1, orders_o_no_seq.CURRVAL, 13);
INSERT INTO order_item (oi_no, oi_qty, o_no, po_no) VALUES (order_item_oi_no_seq.NEXTVAL, 1, orders_o_no_seq.CURRVAL, 18);
INSERT INTO order_item (oi_no, oi_qty, o_no, po_no) VALUES (order_item_oi_no_seq.NEXTVAL, 1, orders_o_no_seq.CURRVAL, 10);


-- SHIP INSERT
INSERT INTO ship (s_no, s_phone, s_name, s_addr, s_zip, s_req, s_def, s_type, user_no)
VALUES (ship_s_no_seq.NEXTVAL, '010-1234-5678', '김새봄', '서울시 강남구 역삼동 123번지', '12345', '문 앞에 놔주세요!', 1, '집', 7);
INSERT INTO ship (s_no, s_phone, s_name, s_addr, s_zip, s_req, s_def, s_type, user_no)
VALUES (ship_s_no_seq.NEXTVAL, '010-1234-5678', '김새봄', '서울시 강남구 역삼동 456번지', '23455', '배송 하고 가실 때 절 회사에서 구출해주시면 안될까요..안되겠죠 죄송합니다..', 0, '회사', 7);

INSERT INTO ship (s_no, s_phone, s_name, s_addr, s_zip, s_req, s_def, s_type, user_no)
VALUES (ship_s_no_seq.NEXTVAL, '010-2345-6789', '이가을', '서울시 중랑구 면목동 999번지', '23453', '회사 앞에 택배보관함 있어요 거기에 넣어주세요', 1, '회사', 2);
INSERT INTO ship (s_no, s_phone, s_name, s_addr, s_zip, s_req, s_def, s_type, user_no)
VALUES (ship_s_no_seq.NEXTVAL, '010-4567-8901', '유동주', '서울 서초구 반포동 435번지', '12344', '선물이에요 제발 조심히 다뤄주세요', 0, '집', 2);

INSERT INTO ship (s_no, s_phone, s_name, s_addr, s_zip, s_req, s_def, s_type, user_no)
VALUES (ship_s_no_seq.NEXTVAL, '010-3456-7890', '김마음', '서울시 관악구 신림동 233번지', '23234', '공동현관 비밀번호 : 1111', 1, '집', 3);

INSERT INTO ship (s_no, s_phone, s_name, s_addr, s_zip, s_req, s_def, s_type, user_no)
VALUES (ship_s_no_seq.NEXTVAL, '010-4567-8901', '유동주', '서울 서초구 반포동 435번지', '12344', '배송 무조건 빨리.', 1, '집', 4);

INSERT INTO ship (s_no, s_phone, s_name, s_addr, s_zip, s_req, s_def, s_type, user_no)
VALUES (ship_s_no_seq.NEXTVAL, '010-5678-9012', '유주랑', '서울 서초구 반포동 435번지', '12344', '벨 누르지 말아주세용', 1, '집', 5);

INSERT INTO ship (s_no, s_phone, s_name, s_addr, s_zip, s_req, s_def, s_type, user_no)
VALUES (ship_s_no_seq.NEXTVAL, '010-3267-2323', '강홍도', '서울시 용산구 이촌동 ', '50434', '문 앞에 놔주세요 벨 절대 xxx', 1, '집', 6);


-- REVIEW INSERT

--INSERT INTO review (r_no, r_star, r_image, r_content, r_reg, p_no,oi_no, user_no)
--VALUES (review_r_no_seq.NEXTVAL, 5, 'redo1_img.jpg', '회사 점심용으로 들고다니는데 정말 좋아요. 전자레인지 1분이면 따뜻하게 먹을 수 있구용', SYSDATE, 1,1, 1);

INSERT INTO review (r_no, r_star, r_image, r_content, r_reg, p_no,oi_no, user_no)
VALUES (review_r_no_seq.NEXTVAL, 5, 'salReview1.png', '혼자살면 채소 챙겨먹기가 정말 쉽지 않아서 구매하게 됐는데 정말 좋아요. 쓰레기도 많이 안나오고 양도 적당합니다', SYSDATE, 2,2, 7);
INSERT INTO review (r_no, r_star, r_image, r_content, r_reg, p_no,oi_no, user_no)
VALUES (review_r_no_seq.NEXTVAL, 2, 'sanReview1.png', '사진이랑 달라요.. 샌드위치 배송오면서 험난해서 다이어트가 됐을까요..', SYSDATE, 3, 3,7);
INSERT INTO review (r_no, r_star, r_image, r_content, r_reg, p_no,oi_no, user_no)
VALUES (review_r_no_seq.NEXTVAL, 4, 'lunchBoxReview1.png', '다 좋은데 칸이 분리됐으면 좋겠어요. 차갑게 먹고싶은거까지 돌려지니까 맛이 오묘해요', SYSDATE, 1,4,2);
INSERT INTO review (r_no, r_star, r_image, r_content, r_reg, p_no,oi_no, user_no)
VALUES (review_r_no_seq.NEXTVAL, 1, 'salReview2.png', '채소가 싱싱하지 않아요! 끝에 다 갈변되어서 왔어요ㅜ 한두번이면 이해하겠는데 너무해여', SYSDATE, 5,5,2);
INSERT INTO review (r_no, r_star, r_image, r_content, r_reg, p_no,oi_no, user_no)
VALUES (review_r_no_seq.NEXTVAL, 5, 'lunchBoxReview2.png', '도시락 러버로서 넘 좋아요 가격도 합리적이고 매번 다양하게 와서 좋은데 신메뉴도 조금씩 나왔으면 좋겠어요! 6개월째 같은 루틴으로 먹어서 살짝 물려요오', SYSDATE, 4,7,3);
INSERT INTO review (r_no, r_star, r_image, r_content, r_reg, p_no,oi_no, user_no)
VALUES (review_r_no_seq.NEXTVAL, 5, 'salReview3.png', '주변에서 많이들 먹어서 영업당했는데 좋아요 굿 영양밸러스 굿. 포케도 출시하면 좋을듯', SYSDATE, 5,8,3);




-- CHATTING INSERT
INSERT INTO chatting (ch_no, ch_name, ch_phone, ch_content) VALUES (chatting_ch_no_seq.NEXTVAL, '김새봄', '010-1234-5678', '제품이 이게 뭔가요.');
INSERT INTO chatting (ch_no, ch_name, ch_phone, ch_content) VALUES (chatting_ch_no_seq.NEXTVAL, '김새봄', '010-1234-5678', '저번에도 이러더니 이번에도 이러시면 어떡하나용');
INSERT INTO chatting (ch_no, ch_name, ch_phone, ch_content) VALUES (chatting_ch_no_seq.NEXTVAL, '이가을', '010-2345-6789', '이 편지는 영국에서부터 시작되어...');
INSERT INTO chatting (ch_no, ch_name, ch_phone, ch_content) VALUES (chatting_ch_no_seq.NEXTVAL, '유주랑', '010-5678-9012', '환불해줘요 저 이제 집밥해먹을거에요 흥!');
INSERT INTO chatting (ch_no, ch_name, ch_phone, ch_content) VALUES (chatting_ch_no_seq.NEXTVAL, '강홍도', '010-3267-2323', '1주만 무료로 체험 서비스 같은거 없나요');

-- BOARD INSERT
INSERT INTO board (b_no, b_title, b_content, b_readcount, b_date, b_groupno, b_step, b_depth, b_category, b_rn, user_no)
VALUES (board_b_no_seq.NEXTVAL, '이제부터 제가 진짜 브런치얼스입니다. 오늘 하루 도시락 공짜', '밑에 카드정보를 입력해주시면 쿠폰이 발급됩니다. 컴컴 얼른 신청하세요', 0, SYSDATE, 1, 1, 0, 1, 0, 7);
INSERT INTO board (b_no, b_title, b_content, b_readcount, b_date, b_groupno, b_step, b_depth, b_category, b_rn, user_no)
VALUES (board_b_no_seq.NEXTVAL, '브런치얼스 계정 해킹하신 고객님 찾습니다.', '다행히 회원 여러분의 카드 정보는 잘 지켜냈습니다. 여러분 이렇게 공짜를 좋아하시면 어떡해요', 0, SYSDATE, 2, 1, 0, 0, 0, 1);
INSERT INTO board (b_no, b_title, b_content, b_readcount, b_date, b_groupno, b_step, b_depth, b_category, b_rn, user_no)
VALUES (board_b_no_seq.NEXTVAL, '브런치얼스가 좋으세요? 디너덜트가 좋으세요?', '난 디너덜트 이거 없어지면 브런치얼스가 지운겁니다.', 0, SYSDATE, 3, 1, 0, 2, 0, 2);
INSERT INTO board (b_no, b_title, b_content, b_readcount, b_date, b_groupno, b_step, b_depth, b_category, b_rn, user_no)
VALUES (board_b_no_seq.NEXTVAL, '배송기사님이 제걸 드시고 계셨어요', '너무 당황스러웠어요...(더보기)', 0, SYSDATE, 4, 1, 0, 3, 0, 2);

-- COUPON INSERT
INSERT INTO coupon (coup_no, coup_name, coup_discount, coup_desc, coup_status, user_no, o_no)
VALUES (coupon_coup_no_seq.NEXTVAL, '신규 가입 쿠폰', 0.1, '신규 가입 회원에게 제공되는 10% 할인 쿠폰', '1', 7 , null);
INSERT INTO coupon (coup_no, coup_name, coup_discount, coup_desc, coup_status, user_no, o_no)
VALUES (coupon_coup_no_seq.NEXTVAL, '신규 가입 쿠폰', 0.1, '신규 가입 회원에게 제공되는 10% 할인 쿠폰', '1', 2, null);
INSERT INTO coupon (coup_no, coup_name, coup_discount, coup_desc, coup_status, user_no, o_no)
VALUES (coupon_coup_no_seq.NEXTVAL, '신규 가입 쿠폰', 0.1, '신규 가입 회원에게 제공되는 10% 할인 쿠폰', '1', 3, null);
INSERT INTO coupon (coup_no, coup_name, coup_discount, coup_desc, coup_status, user_no, o_no)
VALUES (coupon_coup_no_seq.NEXTVAL, '신규 가입 쿠폰', 0.1, '신규 가입 회원에게 제공되는 10% 할인 쿠폰', '1', 4, null);
INSERT INTO coupon (coup_no, coup_name, coup_discount, coup_desc, coup_status, user_no, o_no)
VALUES (coupon_coup_no_seq.NEXTVAL, '신규 가입 쿠폰', 0.1, '신규 가입 회원에게 제공되는 10% 할인 쿠폰', '1', 5, null);
INSERT INTO coupon (coup_no, coup_name, coup_discount, coup_desc, coup_status, user_no, o_no)
VALUES (coupon_coup_no_seq.NEXTVAL, '신규 가입 쿠폰', 0.1, '신규 가입 회원에게 제공되는 10% 할인 쿠폰', '1', 6, null);
INSERT INTO coupon (coup_no, coup_name, coup_discount, coup_desc, coup_status, user_no, o_no)

VALUES (coupon_coup_no_seq.NEXTVAL, '생일 축하 쿠폰', 0.15, '생일을 맞이한 회원에게 제공되는 15% 할인 쿠폰','1', 7, null);
INSERT INTO coupon (coup_no, coup_name, coup_discount, coup_desc, coup_status, user_no, o_no)
VALUES (coupon_coup_no_seq.NEXTVAL, '첫 구매 감사 쿠폰', 0.15, '생일을 맞이한 회원에게 제공되는 15% 할인 쿠폰','1', 7, null);
INSERT INTO coupon (coup_no, coup_name, coup_discount, coup_desc, coup_status, user_no, o_no)
VALUES (coupon_coup_no_seq.NEXTVAL, '재구매 감사 쿠폰', 0.15, '생일을 맞이한 회원에게 제공되는 15% 할인 쿠폰','1', 7, 1);
INSERT INTO coupon (coup_no, coup_name, coup_discount, coup_desc, coup_status, user_no, o_no)
VALUES (coupon_coup_no_seq.NEXTVAL, '첫 구매 감사 쿠폰', 0.05, '첫 구매 고객에게 제공되는 5% 할인 쿠폰', '1', 2, null);
INSERT INTO coupon (coup_no, coup_name, coup_discount, coup_desc, coup_status, user_no, o_no)
VALUES (coupon_coup_no_seq.NEXTVAL, '재구매 감사 쿠폰', 0.05, '재구매 고객에게 제공되는 5% 할인 쿠폰', '1', 3, null);

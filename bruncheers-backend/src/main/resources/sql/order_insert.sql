/*어드민 INSERT */
INSERT into admininfo(a_id,a_pw)values('aaaa','aaaa');
INSERT into admininfo(a_id,a_pw)values('bbbb','bbbb');
INSERT into admininfo(a_id,a_pw)values('cccc','cccc');

/*상품카테고리 INSERT*/
INSERT INTO product_category (cat_no,cat_name)values(1,'1일1식');
INSERT INTO product_category (cat_no,cat_name)values(2,'1일2식');
INSERT INTO product_category (cat_no,cat_name)values(3,'1일3식');

/*상품 INSERT*/
INSERT INTO product (p_no,p_name,p_price,p_image,p_detail,p_deimg,p_reg_dt,p_stock,cat_no,a_id)values(1,'샐러드',8000,'이미지','상품설명','설명이미지',SYSDATE,1,1,'aaaa');
--상품 옵션
INSERT INTO product_option(po_no,po_price,po_stock,po_reg_dt,po_period,p_no) values(1,5000,1,SYSDATE,2,1);

/* 회원 INSERT */
INSERT INTO userinfo(u_id,u_pw,u_name,u_gender,u_birth,u_hp,u_addr,u_nickname,a_id)values('1111','1111','1테스트','남',1111,'010-1111-1111','서울시 강남구','a테스트','aaaa');
INSERT INTO userinfo(u_id,u_pw,u_name,u_gender,u_birth,u_hp,u_addr,u_nickname,a_id)values('2222','2222','2테스트','여',2222,'010-2222-2222','서울시 강남구','b테스트','bbbb');

/*aaaa 주문*/
--상품 상세에서 주문
INSERT INTO orders(o_no,o_price,o_name,o_addr,o_phone,o_date,o_req,u_id)values(orders_o_no_SEQ.nextval,15000,'수빈','경기도','010-7777-7777',SYSDATE,'맛있게해주세요','1111');

--order.. orders..?

--주문 아이템 추가
INSERT INTO order_item(oi_no,oi_qty,o_no,po_no)values(1,1,2,2);
INSERT INTO order_item(oi_no,oi_qty,o_no,po_no)values(2,5,1,1);
INSERT INTO order_item(oi_no,oi_qty,o_no,po_no)values(3,4,3,2);
--INSERT INTO order_item(oi_no,oi_qty,o_no,po_no)values(order_item_oi_no_SEQ.nextval,4,3,2);

--결제 정보 추가
INSERT INTO pay(pa_no,pa_price,pa_type,o_no)values(1,50000,2,1);

/*bbbb 주문*/
--상품 상세에서 주문
INSERT INTO orders(o_no,o_price,o_name,o_addr,o_phone,o_date,o_req,u_id)values('2',34000,'진효','서울','010-3333-3333',SYSDATE,'냠냠','bbbb');
--주문 아이템 추가
INSERT INTO order_item(oi_no,oi_qty,o_no,po_no)values(3,2,1,2);
INSERT INTO order_item(oi_no,oi_qty,o_no,po_no)values(2,10,2,1);
INSERT INTO order_item(oi_no,oi_qty,o_no,po_no)values(1,4,3,2);
--결제 정보 추가
INSERT INTO pay(pa_no,pa_price,pa_type,o_no)values(2,75000,3,2);

/*배송 INSERT*/
INSERT INTO ship(s_no,s_phone,s_name,s_addr,s_addrPost,s_addrDetail,s_req,s_def,s_addrType,u_id) values(1,'010-2222-2222','수빈','어딘가','12345','상세주소','문앞에 놔주세요','2','1','1111');
INSERT INTO ship(s_no,s_phone,s_name,s_addr,s_addrPost,s_addrDetail,s_req,s_def,s_addrType,u_id) values(2,'010-8282-5252','수빈','어딘가','12345','상세주소','문앞에 놔주세요','1','3','2222');
INSERT INTO ship(s_no,s_phone,s_name,s_addr,s_addrPost,s_addrDetail,s_req,s_def,s_addrType,u_id) values(3,'010-5555-2222','수빈','어딘가','12345','상세주소','문앞에 놔주세요','2','1','3333');


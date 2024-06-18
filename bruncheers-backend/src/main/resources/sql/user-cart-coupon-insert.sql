-- USERINFO INSERT
INSERT INTO userinfo (u_no, u_email, u_pw, u_name, u_gender, u_birth, u_hp, u_nickname, a_id)
VALUES (userinfo_u_no_seq.NEXTVAL, 'user1@naver.com', '1111', '김새봄', '남', '2020-05-13', '010-1234-5678', '율잣', 'admin');

INSERT INTO userinfo (u_no, u_email, u_pw, u_name, u_gender, u_birth, u_hp, u_nickname, a_id)
VALUES (userinfo_u_no_seq.NEXTVAL, 'user2@naver.com', '2222', '이가을', '여', '2020-07-18', '010-2345-6789', '코코볼', 'admin');

INSERT INTO userinfo (u_no, u_email, u_pw, u_name, u_gender, u_birth, u_hp, u_nickname, a_id)
VALUES (userinfo_u_no_seq.NEXTVAL, 'user3@gmail.com', '3333', '김마음', '남', '2018-08-12', '010-3456-7890', '말티즈대장', 'admin');

INSERT INTO userinfo (u_no, u_email, u_pw, u_name, u_gender, u_birth, u_hp, u_nickname, a_id)
VALUES (userinfo_u_no_seq.NEXTVAL, 'user4@gmail.com', '4444', '유동주', '남', '2018-10-30', '010-4567-8901', '동동주', 'admin');

INSERT INTO userinfo (u_no, u_email, u_pw, u_name, u_gender, u_birth, u_hp, u_nickname, a_id)
VALUES (userinfo_u_no_seq.NEXTVAL, 'user5@gmail.com', '5555', '유주랑', '여', '2019-07-27', '010-5678-9012', '동주랑', 'admin');

INSERT INTO userinfo (u_no, u_email, u_pw, u_name, u_gender, u_birth, u_hp, u_nickname, a_id)
VALUES (userinfo_u_no_seq.NEXTVAL, 'user5@gmail.com', '6666', '강홍도', '여', '1992-11-10', '010-5678-9012', '얄루', 'admin');

--CART INSERT
INSERT INTO cart (c_no, c_qty, u_no, po_no) VALUES (cart_c_no_seq.NEXTVAL, 2, 1, 1);
INSERT INTO cart (c_no, c_qty, u_no, po_no) VALUES (cart_c_no_seq.NEXTVAL, 1, 1, 2);

INSERT INTO cart (c_no, c_qty, u_no, po_no) VALUES (cart_c_no_seq.NEXTVAL, 1, 2, 1);
INSERT INTO cart (c_no, c_qty, u_no, po_no) VALUES (cart_c_no_seq.NEXTVAL, 1, 2, 3);

INSERT INTO cart (c_no, c_qty, u_no, po_no) VALUES (cart_c_no_seq.NEXTVAL, 2, 3, 3);
INSERT INTO cart (c_no, c_qty, u_no, po_no) VALUES (cart_c_no_seq.NEXTVAL, 3, 3, 4);

INSERT INTO cart (c_no, c_qty, u_no, po_no) VALUES (cart_c_no_seq.NEXTVAL, 2, 4, 1);
INSERT INTO cart (c_no, c_qty, u_no, po_no) VALUES (cart_c_no_seq.NEXTVAL, 1, 4, 4);

INSERT INTO cart (c_no, c_qty, u_no, po_no) VALUES (cart_c_no_seq.NEXTVAL, 2, 5, 2);
INSERT INTO cart (c_no, c_qty, u_no, po_no) VALUES (cart_c_no_seq.NEXTVAL, 1, 5, 3);

INSERT INTO cart (c_no, c_qty, u_no, po_no) VALUES (cart_c_no_seq.NEXTVAL, 1, 6, 4);
INSERT INTO cart (c_no, c_qty, u_no, po_no) VALUES (cart_c_no_seq.NEXTVAL, 1, 6, 2);


-- COUPON INSERT
INSERT INTO coupon (coup_no, coup_name, coup_discount, coup_desc, coup_status, u_no)
VALUES (coupon_coup_no_seq.NEXTVAL, '신규 가입 쿠폰', 10, '신규 가입 회원에게 제공되는 10% 할인 쿠폰', '1', 1);
INSERT INTO coupon (coup_no, coup_name, coup_discount, coup_desc, coup_status, u_no)
VALUES (coupon_coup_no_seq.NEXTVAL, '신규 가입 쿠폰', 10, '신규 가입 회원에게 제공되는 10% 할인 쿠폰', '1', 2);
INSERT INTO coupon (coup_no, coup_name, coup_discount, coup_desc, coup_status, u_no)
VALUES (coupon_coup_no_seq.NEXTVAL, '신규 가입 쿠폰', 10, '신규 가입 회원에게 제공되는 10% 할인 쿠폰', '1', 3);
INSERT INTO coupon (coup_no, coup_name, coup_discount, coup_desc, coup_status, u_no)
VALUES (coupon_coup_no_seq.NEXTVAL, '신규 가입 쿠폰', 10, '신규 가입 회원에게 제공되는 10% 할인 쿠폰', '1', 4);
INSERT INTO coupon (coup_no, coup_name, coup_discount, coup_desc, coup_status, u_no)
VALUES (coupon_coup_no_seq.NEXTVAL, '신규 가입 쿠폰', 10, '신규 가입 회원에게 제공되는 10% 할인 쿠폰', '1', 5);
INSERT INTO coupon (coup_no, coup_name, coup_discount, coup_desc, coup_status, u_no)
VALUES (coupon_coup_no_seq.NEXTVAL, '신규 가입 쿠폰', 10, '신규 가입 회원에게 제공되는 10% 할인 쿠폰', '1', 6);
INSERT INTO coupon (coup_no, coup_name, coup_discount, coup_desc, coup_status, u_no)
VALUES (coupon_coup_no_seq.NEXTVAL, '생일 축하 쿠폰', 15, '생일을 맞이한 회원에게 제공되는 15% 할인 쿠폰','1', 1);
INSERT INTO coupon (coup_no, coup_name, coup_discount, coup_desc, coup_status, u_no)
VALUES (coupon_coup_no_seq.NEXTVAL, '첫 구매 감사 쿠폰', 5, '첫 구매 고객에게 제공되는 5% 할인 쿠폰', '1', 2);
INSERT INTO coupon (coup_no, coup_name, coup_discount, coup_desc, coup_status, u_no)
VALUES (coupon_coup_no_seq.NEXTVAL, '재구매 감사 쿠폰', 5, '재구매 고객에게 제공되는 5% 할인 쿠폰', '1', 3);
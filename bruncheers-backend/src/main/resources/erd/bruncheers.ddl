DROP TABLE userinfo CASCADE CONSTRAINTS;
DROP TABLE orders CASCADE CONSTRAINTS;
DROP TABLE product CASCADE CONSTRAINTS;
DROP TABLE order_item CASCADE CONSTRAINTS;
DROP TABLE ship CASCADE CONSTRAINTS;
DROP TABLE review CASCADE CONSTRAINTS;
DROP TABLE pay CASCADE CONSTRAINTS;
DROP TABLE cart CASCADE CONSTRAINTS;
DROP TABLE chatting CASCADE CONSTRAINTS;
DROP TABLE product_option CASCADE CONSTRAINTS;
DROP TABLE product_category CASCADE CONSTRAINTS;
DROP TABLE board CASCADE CONSTRAINTS;
DROP TABLE coupon CASCADE CONSTRAINTS;

CREATE TABLE userinfo (
    user_no       NUMBER(10) NOT NULL,
    user_email    VARCHAR2(100) NOT NULL,
    user_pw       VARCHAR2(100), 
    user_name     VARCHAR2(30) NOT NULL,
    user_gender   CHAR(10) NOT NULL,
    user_birth    VARCHAR2(15) NOT NULL,
    user_hp       VARCHAR2(20) NOT NULL,
    user_nickname VARCHAR2(20) NOT NULL,
    role       VARCHAR2(20),
   	token VARCHAR2(255)
   
);

DROP SEQUENCE userinfo_user_no_seq;

CREATE SEQUENCE userinfo_user_no_seq NOMAXVALUE NOCACHE NOORDER NOCYCLE;



CREATE TABLE product (
    p_no     NUMBER(20) NOT NULL,
    p_name   VARCHAR2(50) NOT NULL,
    p_price  NUMBER(20) NOT NULL,
    p_image  VARCHAR2(3000) NOT NULL,
    p_detail VARCHAR2(1000) NOT NULL,
    p_deimg  VARCHAR2(3000) NOT NULL,
    p_reg    DATE DEFAULT sysdate NOT NULL,
    cat_no   NUMBER(20) NOT NULL

);

DROP SEQUENCE product_p_no_seq;

CREATE SEQUENCE product_p_no_seq NOMAXVALUE NOCACHE NOORDER NOCYCLE;

CREATE TABLE product_option (
    po_no    NUMBER(20) NOT NULL,
    po_name  VARCHAR2(50) NOT NULL,
    po_price NUMBER(20) NOT NULL,
    p_no     NUMBER(20) NOT NULL
);

DROP SEQUENCE product_option_po_no_seq;

CREATE SEQUENCE product_option_po_no_seq NOMAXVALUE NOCACHE NOORDER NOCYCLE;

CREATE TABLE product_category (
    cat_no   NUMBER(20) NOT NULL,
    cat_name VARCHAR2(50) NOT NULL
);

DROP SEQUENCE product_category_cat_no_seq;

CREATE SEQUENCE product_category_cat_no_seq NOMAXVALUE NOCACHE NOORDER NOCYCLE;


CREATE TABLE cart (
    c_no  NUMBER(20) NOT NULL,
    c_qty NUMBER(20) DEFAULT 1 NOT NULL,
    user_no  NUMBER(10) NOT NULL,
    po_no NUMBER(20) NOT NULL
);

DROP SEQUENCE cart_c_no_seq;

CREATE SEQUENCE cart_c_no_seq NOMAXVALUE NOCACHE NOORDER NOCYCLE;

CREATE TABLE pay (
    pa_no    NUMBER(10) NOT NULL,
    pa_type  VARCHAR2(30) NULL
);

DROP SEQUENCE pay_pa_no_seq;

CREATE SEQUENCE pay_pa_no_seq NOMAXVALUE NOCACHE NOORDER NOCYCLE;

CREATE TABLE orders (
    o_no    NUMBER(10) NOT NULL,
    o_price NUMBER(10) NOT NULL,
    o_name  VARCHAR2(50) NOT NULL,
    o_zip   NUMBER(10) NOT NULL,
    o_addr  VARCHAR2(200) NOT NULL,
    o_phone VARCHAR2(50) NOT NULL,
    o_date  DATE DEFAULT sysdate NOT NULL,
    o_req   VARCHAR2(1000) NULL,
    o_sreq   VARCHAR2(1000) NULL,
    o_discountprice  NUMBER(10),
    user_no    NUMBER(10) NOT NULL,
    pa_no   NUMBER(10) NOT NULL
);

DROP SEQUENCE orders_o_no_seq;

CREATE SEQUENCE orders_o_no_seq NOMAXVALUE NOCACHE NOORDER NOCYCLE;

CREATE TABLE order_item (
    oi_no  NUMBER(10) NOT NULL,
    oi_qty NUMBER(10) NOT NULL,
    o_no   NUMBER(10) NOT NULL,
    po_no  NUMBER(20) NOT NULL
);

DROP SEQUENCE order_item_oi_no_seq;

CREATE SEQUENCE order_item_oi_no_seq NOMAXVALUE NOCACHE NOORDER NOCYCLE;

CREATE TABLE ship (
    s_no    NUMBER(10) NOT NULL,
    s_phone VARCHAR2(50) NOT NULL,
    s_name  VARCHAR2(50) NOT NULL,
    s_addr  VARCHAR2(200) NOT NULL,
    s_zip   VARCHAR2(50) NOT NULL,
    s_req   VARCHAR2(1000) NULL,
    s_def   NUMBER(10) NOT NULL,
    s_type  VARCHAR2(20) NOT NULL,
    user_no NUMBER(10) NOT NULL
);

DROP SEQUENCE ship_s_no_seq;

CREATE SEQUENCE ship_s_no_seq NOMAXVALUE NOCACHE NOORDER NOCYCLE;

CREATE TABLE review (
    r_no      NUMBER(20) NOT NULL,
    r_star    NUMBER(5) DEFAULT 1 NOT NULL,
    r_image   VARCHAR2(3000) NULL,
    r_content VARCHAR2(3000) NOT NULL,
    r_reg     DATE DEFAULT sysdate NOT NULL,
    p_no      NUMBER(20) NOT NULL,
    oi_no     NUMBER(10) NULL,
    user_no   NUMBER(10) NOT NULL
);

DROP SEQUENCE review_r_no_seq;

CREATE SEQUENCE review_r_no_seq NOMAXVALUE NOCACHE NOORDER NOCYCLE;


CREATE TABLE chatting (
    ch_no      NUMBER(10) NOT NULL,
    ch_name    VARCHAR2(50) NOT NULL,
    ch_phone   VARCHAR2(50) NOT NULL,
    ch_content VARCHAR2(3000) NOT NULL
);

DROP SEQUENCE chatting_ch_no_seq;

CREATE SEQUENCE chatting_ch_no_seq NOMAXVALUE NOCACHE NOORDER NOCYCLE;


CREATE TABLE board (
    b_no        NUMBER(10) NOT NULL,
    b_title     VARCHAR2(100) NOT NULL,
    b_content   VARCHAR2(3000) NOT NULL,
    b_readcount NUMBER(10) DEFAULT 0 NOT NULL,
    b_date      DATE DEFAULT sysdate NOT NULL,
    b_groupno   NUMBER(10) NULL,
    b_step      NUMBER(10) DEFAULT 1 NULL,
    b_depth     NUMBER(10) DEFAULT 0 NULL,
    b_category  NUMBER(10) NOT NULL,
    b_rn        NUMBER(10) NULL,
    user_no     NUMBER(10) NOT NULL
);

DROP SEQUENCE board_b_no_seq;

CREATE SEQUENCE board_b_no_seq NOMAXVALUE NOCACHE NOORDER NOCYCLE;

CREATE TABLE coupon (
    coup_no       NUMBER(30) NOT NULL,
    coup_name     VARCHAR2(30) NOT NULL,
    coup_discount NUMBER DEFAULT 10 NOT NULL,
    coup_desc     VARCHAR2(1000) NOT NULL,
    coup_status   VARCHAR2(30) NOT NULL,
    user_no       NUMBER(10) NOT NULL,
    o_no          NUMBER(10)
);

DROP SEQUENCE coupon_coup_no_seq;

CREATE SEQUENCE coupon_coup_no_seq NOMAXVALUE NOCACHE NOORDER NOCYCLE;



ALTER TABLE userinfo ADD CONSTRAINT idx_userinfo_pk PRIMARY KEY ( user_no );
ALTER TABLE userinfo ADD CONSTRAINT uniq_userinfo_email UNIQUE (user_email);


ALTER TABLE product_category ADD CONSTRAINT idx_product_category_pk PRIMARY KEY ( cat_no );

ALTER TABLE product ADD CONSTRAINT idx_product_pk PRIMARY KEY ( p_no );
ALTER TABLE product ADD CONSTRAINT idx_product_fk0 FOREIGN KEY ( cat_no ) REFERENCES product_category ( cat_no );

ALTER TABLE product_option ADD CONSTRAINT idx_product_option_pk PRIMARY KEY ( po_no );
ALTER TABLE product_option ADD CONSTRAINT idx_product_option_fk0 FOREIGN KEY (p_no) REFERENCES product (p_no);

ALTER TABLE cart ADD CONSTRAINT idx_cart_pk PRIMARY KEY ( c_no );
ALTER TABLE cart ADD CONSTRAINT idx_cart_fk0 FOREIGN KEY ( po_no ) REFERENCES product_option ( po_no );
ALTER TABLE cart ADD CONSTRAINT idx_cart_fk1 FOREIGN KEY ( user_no ) REFERENCES userinfo ( user_no );

ALTER TABLE pay ADD CONSTRAINT idx_pay_pk PRIMARY KEY ( pa_no );

ALTER TABLE orders ADD CONSTRAINT idx_orders_pk PRIMARY KEY ( o_no );
ALTER TABLE orders ADD CONSTRAINT idx_orders_fk0 FOREIGN KEY ( user_no ) REFERENCES userinfo ( user_no );
ALTER TABLE orders ADD CONSTRAINT idx_orders_fk1 FOREIGN KEY ( pa_no ) REFERENCES pay ( pa_no );


ALTER TABLE order_item ADD CONSTRAINT idx_order_item_pk PRIMARY KEY ( oi_no ); 
ALTER TABLE order_item ADD CONSTRAINT idx_order_item_fk0 FOREIGN KEY ( po_no ) REFERENCES product_option ( po_no )  on delete cascade ;
ALTER TABLE order_item ADD CONSTRAINT idx_order_item_fk1 FOREIGN KEY ( o_no ) REFERENCES orders ( o_no );
          


ALTER TABLE ship ADD CONSTRAINT idx_ship_pk PRIMARY KEY ( s_no );
ALTER TABLE ship ADD CONSTRAINT idx_ship_fk0 FOREIGN KEY ( user_no ) REFERENCES userinfo ( user_no );
ALTER TABLE ship ADD CONSTRAINT check_s_def CHECK (s_def IN (0, 1));

ALTER TABLE review ADD CONSTRAINT idx_review_pk PRIMARY KEY ( r_no );
ALTER TABLE review ADD CONSTRAINT idx_review_fk0 FOREIGN KEY ( p_no ) REFERENCES product ( p_no );
ALTER TABLE review ADD CONSTRAINT idx_review_fk1 FOREIGN KEY ( user_no ) REFERENCES userinfo ( user_no );
ALTER TABLE review ADD CONSTRAINT idx_review_fk2 FOREIGN KEY ( oi_no ) REFERENCES order_item ( oi_no )  on delete cascade;

           

ALTER TABLE chatting ADD CONSTRAINT pk_chatting PRIMARY KEY ( ch_no );


ALTER TABLE board ADD CONSTRAINT idx_board_pk PRIMARY KEY ( b_no );
ALTER TABLE board ADD CONSTRAINT idx_board_fk0 FOREIGN KEY ( user_no ) REFERENCES userinfo ( user_no );

         

ALTER TABLE coupon ADD CONSTRAINT idx_coupon_pk PRIMARY KEY ( coup_no );
ALTER TABLE coupon ADD CONSTRAINT idx_coupon_fk0 FOREIGN KEY ( user_no ) REFERENCES userinfo ( user_no );
ALTER TABLE coupon ADD CONSTRAINT idx_coupon_fk1 FOREIGN KEY ( o_no ) REFERENCES orders ( o_no );
ALTER TABLE coupon ADD CONSTRAINT chk_coup_status CHECK (coup_status IN ('0', '1'));

           
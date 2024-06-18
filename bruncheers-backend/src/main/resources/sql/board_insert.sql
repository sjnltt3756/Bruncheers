
/***************************************************** insert *****************************************************/
--게시물 쓰기(최상위)
insert into board(b_no,b_title,u_id,b_content,b_groupno,b_step,b_depth) 
    values(board_b_no_SEQ.nextval,
            '게시판타이틀'||board_b_no_SEQ.currval,
            'bbbb',
            '내용입니다'||board_b_no_SEQ.currval,
            board_b_no_SEQ.currval,
            1,
            0
            );

--답글 쓰기
insert into board(b_no,b_title,u_id,b_content,b_groupno,b_step,b_depth) 
    values(board_b_no_SEQ.nextval,
            '게시판타이틀'||board_b_no_SEQ.currval,
            'bbbb',
            '내용입니다'||board_b_no_SEQ.currval,
            2,
            2,
            1
            );
            
            --게시물 쓰기
insert into board
   values(board_b_no_SEQ.nextval,'게시판타이틀1','내용입니다1',0, sysdate ,board_b_no_SEQ.currval,1,0,3,'aaaa','admin');
insert into board
   values(board_b_no_SEQ.nextval,'게시판타이틀2','내용입니다1',0, sysdate ,board_b_no_SEQ.currval,1,0,2,'bbbb','admin');
insert into board
   values(board_b_no_SEQ.nextval,'게시판타이틀3','내용입니다1',0, sysdate ,board_b_no_SEQ.currval,1,0,3,'cccc','admin');
   
   insert into board(b_no,b_title,b_content,b_groupno,b_step,b_depth,b_cartegory,u_id,a_id) 
    values(board_b_no_SEQ.nextval,'답글 타이틀1','답글 내용입니다1',1,2,1,3,'bbbb','admin');
   insert into board(b_no,b_title,b_content,b_groupno,b_step,b_depth,b_cartegory,u_id,a_id) 
    values(board_b_no_SEQ.nextval,'답글 타이틀2','답글 내용입니다1',1,2,1,3,'bbbb','admin');
   
  INSERT INTO userinfo values('aaaa','aaaa','a테스트','남',1995,'010-1111-1111','서울시 강남구','a테스트','admin');
  INSERT INTO userinfo values('bbbb','bbbb','a테스트','여',1997,'010-1212-3333','서울시 광진구','b테스트','admin');
  INSERT INTO userinfo values('cccc','cccc','a테스트','남',2000,'010-5555-5555','서울시 양천구','c테스트','admin');
   
   INSERT INTO admininfo values('admin',1111);
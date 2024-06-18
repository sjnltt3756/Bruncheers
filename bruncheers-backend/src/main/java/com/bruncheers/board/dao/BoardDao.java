package com.bruncheers.board.dao;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bruncheers.board.entity.Board;
import com.bruncheers.board.repository.BoardRepository;



public interface BoardDao {
	// 1.게시물 삭제
		int remove(int bNo) throws Exception;

		// 2.게시물 쓰기
		int create(Board board) throws Exception;
		
		// 3.답글 쓰기
		int createReply(Board board) throws Exception;
		
		// 4.게시물 1개 선택
		Board findBoard(int bNo) throws Exception;
		
		// 5.게시물 리스트 반환(시작번호, 끝번호)
		Page<Board> findBoardList(Pageable pageable) throws Exception;
		
		// 6.게시물 수정
		int update(Board board) throws Exception;
		
		// 7.게시물 조회수 1 증가
		void increaseReadCount(Integer bNo) throws Exception;
		
		// 8.답글 존재여부 확인
		boolean countReplay(Board board) throws SQLException;
		
		// 9.게시물 총 건수 조회, 반환
		int getBoardCount() throws Exception;
	
}





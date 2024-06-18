package com.bruncheers.board.service;

import com.bruncheers.board.dto.BoardDto;
import com.bruncheers.board.dto.BoardListPageMakerDto;
import com.bruncheers.board.entity.Board;

public interface BoardService {



	// 1.게시물 삭제
	int remove(int bNo) throws Exception;

	// 2.게시물 쓰기
	int create(BoardDto board) throws Exception;

	// 3.답글 쓰기
	int createReply(BoardDto board) throws Exception;

	// 4.게시물 1개 선택
	BoardDto findBoard(int bNo) throws Exception;

	void updateHitCount(int bNo) throws Exception;

	// 5.게시물 리스트보기
	BoardListPageMakerDto findBoardList(int currentPage) throws Exception;

	// 6.게시물 수정
	int update(BoardDto board) throws Exception;

}
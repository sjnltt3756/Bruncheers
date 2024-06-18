package com.bruncheers.board.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bruncheers.board.entity.Board;
import com.bruncheers.board.repository.BoardRepository;
import com.bruncheers.board.util.PageMaker;
import com.bruncheers.user.entity.User;

@Repository("boardDaoImplJPA")
public class BoardDaoImplJPA implements BoardDao {

	@Autowired
	BoardRepository boardRepository;

	@Override
	public int remove(int bNo) throws Exception {

		Optional<Board> deleteBoard = boardRepository.findById(bNo);

		if (!deleteBoard.isPresent())
			throw new Exception();

		else {
			boardRepository.deleteById(bNo);
		}

		return 0;
	}

	@Override
	public int create(Board board) throws Exception {

		return boardRepository.save(board).getBNo();
	}

	@Transactional
	@Override
	public int createReply(Board board) throws Exception {
		// 들어오는 board의 원글 PK , 데이터는 댓글게시물
		System.out.println(board);
		Optional<Board> tempBoard = boardRepository.findById(board.getBNo());
		boardRepository.updateStepForReply(tempBoard.get().getBStep(), tempBoard.get().getBGroupno());
		// 내림차순
		board.setBGroupno(tempBoard.get().getBGroupno());
		// 오름차순
		board.setBStep(tempBoard.get().getBStep() + 1);
		board.setBDepth(tempBoard.get().getBDepth() + 1);
		board.setUser(User.builder()
				.userNo(1l)
				.build());
		board.setBNo(0);
		boardRepository.save(board);
		return 0;
	}

	@Override
	public Board findBoard(int bNo) throws Exception {
		Optional<Board> findBoard = boardRepository.findById(bNo);

		return findBoard.get();

	}

	@Override
	public Page<Board> findBoardList(Pageable pageable) throws Exception {
		Page<Board> boardList = boardRepository.findAll(pageable);
		return boardList;

	}

	@Override
	public int update(Board board) throws Exception {
		return boardRepository.save(board).getBNo();
	}

	// 7.게시물 조회수 1 증가
	@Override
	public void increaseReadCount(Integer bNo) throws Exception {
		boardRepository.increaseRecountById(bNo);

	}

	// 8.답글 존재여부 확인
	@Override
	public boolean countReplay(Board board) throws SQLException {
		int rowCount = boardRepository.countReply(board);

		Boolean isExist = false;
		if (rowCount > 1) {
			isExist = true;

		}

		return isExist;
	}

	@Override
	public int getBoardCount() throws Exception {

		return (int) boardRepository.count();
	}

}

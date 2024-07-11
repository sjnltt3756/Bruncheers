
package com.bruncheers.board.service;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bruncheers.board.dao.BoardDao;
import com.bruncheers.board.dto.BoardDto;
import com.bruncheers.board.dto.BoardListPageMakerDto;
import com.bruncheers.board.entity.Board;
import com.bruncheers.board.util.PageMaker;
import com.bruncheers.exception.UserNotFoundException;
import com.bruncheers.user.entity.User;
import com.bruncheers.user.enums.Role;
import com.bruncheers.user.repository.UserRepository;

//import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;

@Service("boardServiceImpl")
public class BoardServiceImpl implements BoardService {

	@Autowired
	@Qualifier("boardDaoImplJPA")
	private BoardDao boardDao;

	@Autowired
	private UserRepository userRepository;

	// 1.게시물 삭제 public
	public int remove(int bNo) throws Exception {

		Board findBoard = boardDao.findBoard(bNo);
		User loginUser = findBoard.getUser();

		// 게시물 카테고리가 0일 경우 관리자만 삭제 가능
		if (findBoard.getBCategory() == Board.CATEGORY_NOTICE) {
			if (findBoard.getUser().getRole() == Role.ADMIN) {
				return boardDao.remove(bNo);
			} else {
				throw new AccessDeniedException("권한이 없습니다.");
			}
		}

		else if (findBoard.getUser().getUserNo().equals(loginUser.getUserNo())) {
			return boardDao.remove(bNo);
		} else {
			throw new AccessDeniedException("다른 회원의 게시물을 삭제할 수 없습니다.");
		}

	}

	// 2.게시물 쓰기
	public int create(BoardDto boardDto) throws Exception {

		Board board = Board.toEntity(boardDto);
		User user = userRepository.findById(boardDto.getUser().getUserNo())
				.orElseThrow(() -> new UserNotFoundException("회원을 찾을 수 없습니다."));
		// 게시물 카테고리가 0일 경우 관리자만 작성 가능

		if (board.getBCategory() == Board.CATEGORY_NOTICE) {
			if (user.getRole() == Role.ADMIN) {
				return boardDao.create(board);
			} else {
				throw new AccessDeniedException("관리자만 공지사항 작성 가능합니다.");
			}
		}

		return boardDao.create(board);
	}

	// 3.답글 쓰기
	public int createReply(BoardDto boardDto) throws Exception {
		Board board = Board.toEntity(boardDto);
		User user = userRepository.findById(boardDto.getUser().getUserNo())
				.orElseThrow(() -> new UserNotFoundException("회원을 찾을 수 없습니다."));

		if (user.getRole() == Role.ADMIN) {
			return boardDao.createReply(board);
		} else {
			throw new AccessDeniedException("관리자만 답글 가능합니다.");
		}

	}

	// 4.게시물 1개 선택
	@Transactional
	public BoardDto findBoard(int bNo) throws Exception {
		updateHitCount(bNo);
		Board board = boardDao.findBoard(bNo);
		// admin login user 권한 확인 필요
		BoardDto boardDto = BoardDto.toDto(board);
		return boardDto;
	}

	public void updateHitCount(int bNo) throws Exception {
		boardDao.increaseReadCount(bNo);
	}

	// 5.게시물 리스트보기
	public BoardListPageMakerDto findBoardList(int currentPage) throws Exception
//login 안했으면 못 봄

	{
		if (currentPage <= 0) {

			throw new AccessDeniedException("잘못된 접근입니다.");
		}

		// 1)전체글의 갯수
		int totalRecordCount = boardDao.getBoardCount();
		// 2)paging계산(PageMaker 유틸클래스)
		PageMaker pageMaker = new PageMaker(totalRecordCount, currentPage);
		// 3)게시물데이타 얻기
		Pageable pageable = PageRequest.of(currentPage - 1, PageMaker.PAGE_SCALE,
				Sort.by("bGroupno").descending().and(Sort.by("bStep").ascending()));
		Page<Board> page = boardDao.findBoardList(pageable);
		List<BoardDto> boardDtoList = new ArrayList<>();
		List<Board> boardList = page.getContent();
		for (Board board : boardList) {
			BoardDto boardDto = BoardDto.toDto(board);
			boardDtoList.add(boardDto);
		}

		BoardListPageMakerDto pageMakerBoardList = new BoardListPageMakerDto();
		pageMakerBoardList.itemList = boardDtoList;
		pageMakerBoardList.pageMaker = pageMaker;
		return pageMakerBoardList;
	}

	// 6.게시물 수정 public
	public int update(BoardDto boardDto) throws Exception {

		// 수정 시 loginuser admin 체크 필요
		Board board = Board.toEntity(boardDto);
		return boardDao.update(board);
	}

}
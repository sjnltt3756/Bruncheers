package com.bruncheers.board.dto;

import java.util.List;

import com.bruncheers.board.entity.Board;
import com.bruncheers.board.util.PageMaker;

public class BoardListPageMakerDto {
	public List<BoardDto> itemList; // 리스트 데이터 콜렉션
	public PageMaker pageMaker;

}

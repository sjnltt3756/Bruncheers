package com.bruncheers.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bruncheers.board.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
	
	Board findBybNo(Integer bNo);

	@Modifying
	@Query("UPDATE Board b SET b.bStep = b.bStep + 1 WHERE b.bStep > :bStep AND b.bGroupno = :bGroupno")
	Integer updateStepForReply(@Param("bStep") int step, @Param("bGroupno") int groupno);

	@Modifying
	@Query("UPDATE Board b SET b.bReadcount = b.bReadcount + 1 WHERE b.bNo = :bNo")
	void increaseRecountById(@Param("bNo") Integer bNo);

	@Query("SELECT COUNT(b) FROM Board b WHERE b.bGroupno = :bGroupno AND b.bDepth >= :bDepth AND b.bStep >= :bStep")
	int countReply(Board board);
	
	@Query("SELECT COUNT(b) FROM Board b")
    int getBoardCount();

}
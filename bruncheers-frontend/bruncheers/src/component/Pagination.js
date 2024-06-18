import React from "react";
import "../css/board.css";
import { Link } from "react-router-dom";

const Pagination = ({ pageMaker }) => {
  const { curPage, blockBegin, blockEnd, prevPage, nextPage, totPage, totBlock, curBlock, nextGroupStartPage,prevGroupStartPage } =
    pageMaker;

  return (
    <div style={{ textAlign: "center" }}>
     {/* 이전 블록 링크 */}
     {curBlock > 1 && (
        <Link to={`/boards/${prevGroupStartPage}`} className='pagination-link'>
          ◀◀
        </Link>
      )}
      &nbsp;&nbsp;
      {/* 이전 페이지 링크 */}
      {prevPage > 0 && (
        <Link to={`/boards/${prevPage}`} className='pagination-link'>
          ◀
        </Link>
      )}
      &nbsp;&nbsp;
      {/* 페이지 번호 링크들 */}
      {[...Array(blockEnd - blockBegin + 1)].map((_, idx) => {
        const pageNum = blockBegin + idx; // pageNum을 0부터 시작하도록 수정
        return (
          <React.Fragment key={pageNum}>
            {curPage === pageNum ? (
              <span className='pagination-link active' style={{ color: "red", fontWeight: "bold" }}>
                {pageNum}
              </span>
            ) : (
              <Link to={`/boards/${pageNum}`} className='pagination-link'>
                <strong>{pageNum}</strong>
              </Link>
            )}
            &nbsp;
          </React.Fragment>
        );
      })}
      {/* 다음 페이지 링크 */}
      {nextPage <= totPage && (
        <>
          <Link to={`/boards/${nextPage}`} className='pagination-link'>
            ▶
          </Link>
          &nbsp;&nbsp;
        </>
      )}
      {/* 다음 그룹 시작 페이지 링크 */}
      {totBlock > curBlock && (
        <Link to={`/boards/${nextGroupStartPage}`} className='pagination-link'>
          ▶▶
        </Link>
      )}
    </div>
  );
};

export default Pagination;

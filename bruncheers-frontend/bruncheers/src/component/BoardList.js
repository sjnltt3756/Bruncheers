import React, { useEffect, useState } from "react";
import "../css/board.css";
import { Link, useParams } from "react-router-dom";
import * as fetchBoard from "../api/fetchBoard.js";
import { BoardItem } from "./BoardItem.js";
import Pagination from "./Pagination.js";
import { BoardReplyItem } from "./BoardReplyItem.js";

function BoardList() {
  const [boards, setBoards] = useState([]);
  const [pageMaker, setPageMaker] = useState(null);
  const { page_no } = useParams();
  const currentPageNo = page_no !== undefined ? page_no : 1; // 기본값을 1로 설정
  const notices = boards.filter((board) => board.bcategory === "공지사항");
  const regulerBords = boards.filter((board) => board.bcategory !== "공지사항");

  useEffect(() => {
    fetchBoard.listBoard(currentPageNo).then((responseJsonObject) => {
      setBoards(responseJsonObject.data.itemList);
      setPageMaker(responseJsonObject.data.pageMaker);
    });
  }, [currentPageNo]);

  return (
    <>
      <div className='bb-container'>
        <table className='b-table'>
          <thead className='b-thead'>
            <tr className='non'>
              <th className='b-th'>분류</th>
              <th className='b-th'>제목</th>
              <th className='b-th'>닉네임</th>
              <th className='b-th'>작성일</th>
              <th className='b-th'>조회수</th>
            </tr>
          </thead>
          <tbody className='b-tbody'>
            {/*  BoardItem.js start */}
            {notices.map((board) => (
              <BoardItem key={board.bno} board={board} style={{ background: "#efefef" }} />
            ))}
            {regulerBords.map((board) =>
              /* 키 값은 가상 돔에서 사용하기 위해 임의적으로 부여 */
              board.bdepth === 0 ? (
                <BoardItem key={board.bno} board={board} />
              ) : board.bdepth === 1 ? (
                <BoardReplyItem key={board.bno} board={board} />
              ) : null
            )}
            {/*  BoardItem.js end   */}
          </tbody>
          <tfoot>
            <tr>
              <td colSpan='4' className='pagination-cell'>
                {pageMaker && <Pagination pageMaker={pageMaker} />}
              </td>
              <td style={{ textAlign: "right" }} colSpan='100%'>
                <Link to='/board_write_form'>
                  <button className='custom-btn btn-11'>글쓰기</button>
                </Link>
              </td>
            </tr>
          </tfoot>
        </table>
      </div>
    </>
  );
}

export default BoardList;

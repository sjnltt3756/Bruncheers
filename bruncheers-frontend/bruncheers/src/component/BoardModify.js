import React, { useEffect, useRef, useState } from "react";
import * as FetchBoard from "../api/fetchBoard.js";
import { Link, useNavigate, useParams } from "react-router-dom";
import { userState } from "../recoil/atom";
import { useRecoilState } from "recoil";
import * as responseMessage from "../api/responseMessage";
import * as responseStatusCode from "../api/responseStatusCode";

function BoardModify() {
  const [userLoginState, setUserLoginState] = useRecoilState(userState); // 로그인한 유저
  const navigate = useNavigate();
  const formRef = useRef();
  const { board_no } = useParams();
  const [board, setBoard] = useState({
    bno: 0,
    brn: 0,
    breadcount: 0,
    bcategory: "",
    btitle: "",
    bdate: "",
    bcontent: "",
    bstep: 0,
    bdepth: 0,
    bgroupno: 0,
    user: userLoginState.data,
  });

  useEffect(() => {
    FetchBoard.viewBoard(board_no)
      .then((responseJsonObject) => {
        setBoard(responseJsonObject.data[0]);
      })
      .catch((error) => {
        navigate("/error");
      });
  }, [board_no]);

  const handleModifyChange = (e) => {
    setBoard({
      ...board,
      [e.target.name]: e.target.value,
    });
  };

  const BCBoardModifyAction = async () => {
    if (formRef.current.btitle.value === "") {
      alert("제목을 입력해주세요");
      formRef.current.btitle.focus();
      return;
    }
    if (formRef.current.bcontent.value === "") {
      alert("내용을 입력해주세요");
      formRef.current.bcontent.focus();
      return;
    }
    try {
      const responseJsonObject = await FetchBoard.updateBoard(board_no, board);
      navigate(`/board_view/${responseJsonObject.data[0].bno}`);
    } catch (error) {
      if (
        error.response &&
        error.response.status === responseStatusCode.UNAUTHORIZED_BOARD
      ) {
        // fetch 오류 처리
        alert("게시물 수정에 실패했습니다.");
        navigate("/error");
      }
    }
  };
  return (
    <form action="board.html" method="post" ref={formRef}>
      <table className="bb-table">
        <tbody className="non">
          <tr className="b-name-s">
            <td className="header-l" style={{ width: "50px" }}>
              분류
            </td>
            <td className="b-title-cat" style={{ width: "130px" }}>
              {board.bcategory}
            </td>
            <td className="header-s" style={{ width: "50px" }}>
              닉네임
            </td>
            <td className="b-title-cat" style={{ width: "150px" }}>
              {board.user.userNickname}
            </td>
            <td className="header-s" style={{ width: "50px" }}>
              등록일
            </td>
            <td className="b-title-cat-r" style={{ width: "150px" }}>
              {board.bdate.substring(0, 10)}
            </td>
          </tr>
          <tr className="b-name">
            <td className="header">제목</td>
          </tr>
          <tr className="non">
            <td className="non">
              <input
                className="b-title-re-t"
                name="btitle"
                type="text"
                value={board.btitle}
                onChange={handleModifyChange}
              />
            </td>
          </tr>
          <tr className="b-name">
            <td className="header">내용</td>
          </tr>
          <tr className="non">
            <td className="non">
              {/* textarea 개행 처리 해야함 (데이터.split('\n').map((line) --> 참고) */}
              <textarea
                name="bcontent"
                className="b-title-re-con"
                value={board.bcontent}
                onChange={handleModifyChange}
              ></textarea>
            </td>
          </tr>
          <tr className="non">
            <td className="non">
              <input
                className="custom-btn btn-11"
                type="button"
                value="수정"
                onClick={BCBoardModifyAction}
              />
              <Link to={"/boards/1"}>
                <input
                  className="custom-btn btn-11"
                  type="button"
                  value="목록"
                />
              </Link>
            </td>
          </tr>
        </tbody>
      </table>
    </form>
  );
}

export default BoardModify;

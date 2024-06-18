import React, { useEffect, useRef, useState } from "react";
import * as FetchBoard from "../api/fetchBoard.js";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { useRecoilState } from "recoil";
import { userState } from "../recoil/atom.js";
import { notify, Toast } from "../util/toast";

function BoardReply() {
  const [userLoginState, setUserLoginState] = useRecoilState(userState); // 로그인한 유저
  const navigate = useNavigate();
  const formRef = useRef();
  const [board, setBoard] = useState({
    bno: 0,
    brn: 0,
    breadcount: 0,
    bcategory: "",
    btitle: "",
    bdate: "",
    bcontent: "",
    bstep: 0,
    bdepth: 1,
    bgroupno: 0,
    user: userLoginState.data,
  });
  const location = useLocation();
  const parentBoard = location.state;
  useEffect(() => {
    if (parentBoard) {
      setBoard({
        ...board,
        bno: parentBoard.parentBoard.bno,
        bcategory: parentBoard.parentBoard.bcategory,
        bgroupno: parentBoard.parentBoard.bgroupno,
        bstep: parentBoard.parentBoard.bstep,
      });
    }
  }, [parentBoard]);

  const handleChangeBoardReplyWriteForm = (e) => {
    setBoard({
      ...board,
      [e.target.name]: e.target.value,
    });
  };

  const BCBoardReplyAction = async (e) => {
    if (formRef.current.btitle.value === "") {
      notify("warning", "제목을 입력해주세요");
      formRef.current.btitle.focus();
      return;
    }
    if (formRef.current.bcontent.value === "") {
      notify("warning", "내용을 입력해주세요");
      formRef.current.bcontent.focus();
      return;
    }
    // board.bcontent의 개행 문자를 유지하면서 <br> 태그로 변환하여 저장
    const contentWithHTMLLineBreaks = board.bcontent;
    // 변환된 내용을 board에 설정
    setBoard({
      ...board,
      bcontent: contentWithHTMLLineBreaks,
    });
    try {
      const responseJsonObject = await FetchBoard.writeReplyBoard(board);

      navigate(`/boards/1`);
    } catch (error) {
      // fetch 오류 처리
      alert("답글 작성에 실패했습니다.");
      navigate("/error");
    }
  };

  return (
    <form action="board.html" method="post" ref={formRef}>
      <Toast />
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
              {userLoginState.data.userNickname}
            </td>
            <td className="header-s" style={{ width: "50px" }}>
              등록일
            </td>
            <td className="b-title-cat-r" style={{ width: "150px" }}>
              {new Date().toISOString().substring(0, 10)}
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
                onChange={handleChangeBoardReplyWriteForm}
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
                className="b-title-re-con"
                name="bcontent"
                value={board.bcontent}
                onChange={handleChangeBoardReplyWriteForm}
              ></textarea>
            </td>
          </tr>
          <tr className="non">
            <td className="non">
              <input
                className="custom-btn btn-11"
                type="button"
                value="등록"
                onClick={BCBoardReplyAction}
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

export default BoardReply;

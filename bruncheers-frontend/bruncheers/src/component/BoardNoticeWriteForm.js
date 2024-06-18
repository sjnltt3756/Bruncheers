import React, { useRef, useState } from "react";
import * as FetchBoard from "../api/fetchBoard.js";
import "../css/board.css";
import { Link, useNavigate } from "react-router-dom";
import { useRecoilState } from "recoil";
import { userState } from "../recoil/atom.js";

function BoardWriteForm() {
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
    bdepth: 0,
    bgroupno: 0,
    user: {
      userNo: userLoginState.data.userNo, // 사용자의 고유 식별자
      role: userLoginState.data.role, // 사용자의 역할
    },
  });
  const handleChangeBoardWriteForm = (e) => {
    setBoard({
      ...board,
      [e.target.name]: e.target.value,
    });
  };

  const BCBoardWriteAction = async () => {
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
    // board.bcontent의 개행 문자를 <br> 태그로 변환하여 저장
    const contentWithHTMLLineBreaks = board.bcontent
      .split("\n")
      .map((line, index) => <p key={index}>{line}</p>);
    // 변환된 내용을 board에 설정
    setBoard({
      ...board,
      bcontent: contentWithHTMLLineBreaks,
    });
    try {
      const responseJsonObject = await FetchBoard.writeNoticeBoard(board);
      navigate(`/board_view/${responseJsonObject.data[0]}`);
    } catch (error) {
      // fetch 오류 처리
      alert("게시물 작성에 실패했습니다.");
      navigate("/error");
    }
  };

  return (
    <form action="board.html" method="post" ref={formRef}>
      <table className="bb-table">
        <tbody className="non">
          <tr className="b-name">
            <td className="header">분류</td>
          </tr>
          <tr style={{ textAlign: "left" }}>
            <td className="select">
              <input
                type="radio"
                id="select"
                name="bcategory"
                value="공지사항"
                onChange={handleChangeBoardWriteForm}
              />
              <label htmlFor="select" style={{ marginRight: "5px" }}>
                공지사항
              </label>
            </td>
          </tr>
          <tr className="b-name">
            <td className="header">제목</td>
          </tr>
          <tr className="non">
            <td className="non">
              <input
                className="b-title"
                name="btitle"
                type="text"
                placeholder="제목을 입력하세요"
                value={board.btitle}
                style={{ backgroundColor: "white" }}
                onChange={handleChangeBoardWriteForm}
              />
            </td>
          </tr>
          <tr className="b-name">
            <td className="header">내용</td>
          </tr>
          <tr className="non">
            <td className="non">
              <textarea
                style={{ backgroundColor: "white", whiteSpace: "pre-line" }}
                name="bcontent"
                value={board.bcontent}
                onChange={handleChangeBoardWriteForm}
                placeholder="내용을 입력하세요"
              ></textarea>
            </td>
          </tr>
          <tr className="non">
            <td className="non">
              <input
                className="custom-btn btn-11"
                type="button"
                value="등록"
                onClick={BCBoardWriteAction}
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

export default BoardWriteForm;
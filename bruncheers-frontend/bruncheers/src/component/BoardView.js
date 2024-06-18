import React, { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import * as fetchBoard from "../api/fetchBoard.js";
import { useRecoilState } from "recoil";
import { userState } from "../recoil/atom.js";
function BoardView() {
  const [userLoginState, setUserLoginState] = useRecoilState(userState); // 로그인한 유저
  const token = localStorage.getItem("token");
  const navigate = useNavigate();
  const { board_no } = useParams();

  const isAdmin = token && userLoginState.data.role === "ADMIN";

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
    (async () => {
      const responseJsonObject = await fetchBoard.viewBoard(board_no);
      setBoard(responseJsonObject.data[0]);
    })();
  }, [board_no]);

  const BCBoardDeleteAction = async () => {
    const isConfirmed = window.confirm("게시물을 삭제하시겠습니까?");
    if (!isConfirmed) {
      return;
    }
    try {
      const responseJsonObject = await fetchBoard.deleteBoard(board_no);
      navigate("/boards/1");
    } catch (error) {
      navigate("/error");
    }
  };

  return (
    <table className='bb-table no-border-collapse'>
      <tbody className='non'>
        <tr className='b-name-s'>
          <td className='header-l' style={{ width: "50px" }}>
            분류
          </td>
          <td className='b-title-cat' style={{ width: "130px" }}>
            {board.bcategory}
          </td>
          <td className='header-s' style={{ width: "50px" }}>
            닉네임
          </td>
          <td className='b-title-cat' style={{ width: "150px" }}>
            {board.user.userNickname}
          </td>
          <td className='header-s' style={{ width: "50px" }}>
            등록일
          </td>
          <td className='b-title-cat-r' style={{ width: "150px" }}>
            {board.bdate.substring(0, 10)}
          </td>
        </tr>
        <tr className='b-name'>
          <td className='header'>제목</td>
        </tr>
        <tr className='non'>
          <td
            className='b-title-re-t'
            style={{ textAlign: "left", padding: "0px 0px 0px 30px ", verticalAlign: "middle" }}
          >
            {board.btitle}
          </td>
        </tr>
        <tr className='b-name'>
          <td className='header'>내용</td>
        </tr>
        <tr className='non'>
          <td className='b-title-re-con' style={{ verticalAlign: "top", padding: "10px 0px 0px 30px" }}>
            {" "}
            {board.bcontent.split("\n").map((line, index) => (
              <div key={index}>{line}</div>
            ))}
          </td>
        </tr>
        <tr className='non'>
          <td className='non'>
            <div className='non'>
              {isAdmin && (
                <Link to={"/board_reply"} state={{ parentBoard: board }}>
                  <input className='custom-btn btn-11' type='button' value='답글' />
                </Link>
              )}
             {(userLoginState.data.userNickname === board.user.userNickname || isAdmin) && (
                <>
                  <Link to={`/board_modify/${board_no}`}>
                    <input className='custom-btn btn-11' type='button' value='수정' />
                  </Link>
                  <input
                    className='custom-btn btn-11'
                    type='button'
                    value='삭제'
                    onClick={BCBoardDeleteAction}
                  />
                </>
              )}
              {userLoginState.data !== board.user && (
                <Link to={"/boards/1"}>
                  <input className='custom-btn btn-11' type='button' value='목록' />
                </Link>
              )}
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  );
}

export default BoardView;
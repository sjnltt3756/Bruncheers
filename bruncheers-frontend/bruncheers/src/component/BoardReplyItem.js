import React from "react";
import reply from "../image/reply.png";
import { Link } from "react-router-dom";
function BoardReplyItem({ board }) {
  const linkStyle = { textDecoration: "none", color: "inherit" };
  return (
    <tr style={{}}>
      <td className="non">&laquo;{board.bcategory}&raquo;</td>
      <td className="non">
        <div style={{ display: "flex", alignItems: "center",  marginLeft: 15}}>
          <img src={reply} alt="Reply icon" style={{ width: 12 }} />
          <Link to={`/board_view/${board.bno}`} style={linkStyle}>{` re ) ${board.btitle}`}</Link>
        </div>
      </td>
      <td className="non">{board.user.userNickname}</td>
      <td className="non">{board.bdate.substring(0, 10)}</td>
      <td className="non">{board.breadcount}</td>
    </tr>
  );
}
export { BoardReplyItem };

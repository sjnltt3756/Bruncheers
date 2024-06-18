import { Link } from "react-router-dom";

function BoardItem({ board }) {
  const itemStyle =
    board.bcategory === "공지사항" ? { background: "#efefef" } : {};
    const linkStyle = { textDecoration: "none", color: "inherit" };
  return (
    <tr className="board_item" style={itemStyle}>
      <td className="non">&laquo;{board.bcategory}&raquo;</td>
      
        <td className="board_item_title" style={{ textAlign: 'left' }}>
        <Link to={`/board_view/${board.bno}`} style={linkStyle}>
          {board.btitle}
          </Link>
        </td>
      
      <td className="board_item_title">{board.user.userNickname}</td>
      <td className="board_item_date">{board.bdate.substring(0, 10)}</td>
      <td className="board_item_bNo">{board.breadcount}</td>
    </tr>
  );
}
export { BoardItem };

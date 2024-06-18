import {Link, useNavigate, useParams} from "react-router-dom";
import * as FetchOrderItem from "../api/fetchOrderItem";
import { useEffect, useState } from "react";

const OrderItem = ({orderItem}) => {
    const { oiNo } = useParams();
    const navigate = useNavigate();
    const [orderItems, setOrderItems] = useState(null)//주문아이템 정보를 저장할 변수 


      useEffect(() => {
        const fetchOrderItems = async () => {
            try {
                const response = await FetchOrderItem.listOrderItem(oiNo);
                setOrderItems(response.data);
            } catch (error) {
        navigate("/error");
      }
        };
        fetchOrderItems(oiNo);
    }, [oiNo]);

    const openReviewWritePopup = () => {
        // 리뷰 작성 팝업을 여는 함수 정의
    };

    if (!orderItems) {
        return <div>Loading...</div>; // 데이터 로딩 중일 때 표시할 내용
    }

      {/* procut 아이템 1개 시작 */}
    return(
        <tr className="product-tr">
         <Link to={`/order_detail/${orderItem.ono}`}>
            <td>023459932${orderItem.ono}</td>
        </Link>
        <td>
         <img src={"./image/lunchBox.jpg"} alt="Product Image" />
        </td>
        <td>
            <span style={{ display: "block" }}>
             브랜치얼스 23종 도시락 1일 2식{orderItem.pname}
            </span>
            <span style={{ display: "block" }}>&#91;옵션 : 1주&#93;</span>
        </td>
        <td>2023-05-16{orderItem.odate}</td>
        <td>595,000원{orderItem.oprice}</td>
        <td>
        <button className="buttTxt" onClick={openReviewWritePopup}>
          리뷰작성
        </button>
      </td>
    </tr>
  );
{/* procut 아이템 1개 끝 */}
};

export { OrderItem };
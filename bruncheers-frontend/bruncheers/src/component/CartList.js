import React, { useEffect, useState } from "react";
import "../css/cartlist.css";
import QuantityCounter from "./QuantityCounter";
import CustomCheckbox from "./CustomCheckbox";
import * as fetchCart from "../api/fetchCart";
import * as responseMessage from "../api/responseMessage";
import * as responseStatusCode from "../api/responseStatusCode";
import { Navigate, useNavigate } from "react-router";
import { Link } from "react-router-dom";
import { useRecoilState } from "recoil";
import { userState } from "../recoil/atom";
import { notify, Toast } from "../util/toast";
function CartList() {
  const [userLoginState, setUserLoginState] = useRecoilState(userState); // 로그인한 유저
  const [isCheckedAll, setIsCheckedAll] = useState(false); // 전체 체크 여부 상태
  const [isCheckedcNo, setIsCheckedcNo] = useState([]); // 개별 체크 상태 배열
  const [carts, setCarts] = useState([]);
  const navigate = useNavigate();
  const [poNoList, setPoNoList] = useState([]);

  /* carts 받기  */
  useEffect(() => {
    if (!userLoginState.data || !userLoginState.data.userNo) {
      navigate("/error");
    } else {
      (async () => {
        const responseJsonObjcet = await fetchCart.listCart(userLoginState.data.userNo);
        setCarts(responseJsonObjcet.data);
      })();
    }
  }, []);

  // 전체 체크 여부 변경 함수
  const handleCheckAll = (isChecked) => {
    setIsCheckedAll(isChecked);
    const updatedCheckedcNo = {};
    carts.forEach((cart) => {
      updatedCheckedcNo[cart.cno] = isChecked;
    });
    setIsCheckedcNo(updatedCheckedcNo);

    // poNoList 업데이트
    const updatedPoNoList = isChecked ? carts.map((cart) => cart.productOption.poNo) : [];
    setPoNoList(updatedPoNoList);
  };

  // 개별 체크 상태 변경 함수
  const handleCheckcNo = (cno, isChecked) => {
    const updatedCheckedcNo = { ...isCheckedcNo, [cno]: isChecked };
    setIsCheckedcNo(updatedCheckedcNo);

    const updatedPoNoList = []; // 새로운 poNoList 배열 생성

    // 선택된 카트들의 poNo를 추출하여 updatedPoNoList에 추가
    carts.forEach((cart) => {
      if (updatedCheckedcNo[cart.cno]) {
        updatedPoNoList.push(cart.productOption.poNo);
      }

      setPoNoList(updatedPoNoList); // poNoList 상태 업데이트
    });

    // 모든 체크박스가 체크되었는지 확인
    const allChecked = carts.every((cart) => updatedCheckedcNo[cart.cno]);
    setIsCheckedAll(allChecked);
  };

  // 선택 삭제
  const handleDeleteCheckedcNo = async () => {
    const cNoList = Object.keys(isCheckedcNo)
      .filter((cno) => isCheckedcNo[cno])
      .map(Number);
    if (cNoList.length === 0) {
      alert("삭제할 항목을 선택해주세요.");
      return;
    }
    try {
      const responseJsonObject = await fetchCart.deleteCheckedCartItems(cNoList);
      if (responseJsonObject.status === responseStatusCode.DELETE_CHECKED_CARTITEM) {
        const updatedCarts = carts.filter((cart) => !cNoList.includes(cart.cno));
        setCarts(updatedCarts);
        setIsCheckedcNo({});
        setIsCheckedAll(false);
      } else {
        notify("error", "삭제에 실패했습니다.");
      }
    } catch (error) {
      navigate("/error");
    }
  };

  /* 개별삭제 */
  const deleteCartItemAction = async (cNo) => {
    try {
      const responseJsonObject = await fetchCart.deleteCartItem(cNo);
      if (responseJsonObject.status == responseStatusCode.DELETE_CARTITEM) {
        const updatedCarts = await fetchCart.listCart(userLoginState.data.userNo);
        setCarts(updatedCarts.data);
        setIsCheckedcNo({});
        setIsCheckedAll(false);
      } else {
        notify("error", "삭제에 실패했습니다.");
      }
    } catch (error) {
      navigate("/error");
    }
  };

  return (
    <>
      <Toast />
      <div
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <div className='productBox'>
          <h2>장바구니</h2>
          {carts.length > 0 ? (
            <table className='table1'>
              <thead>
                <tr>
                  {/* 전체 체크박스 */}
                  <th className='checkbox-th' style={{ verticalAlign: "middle" }}>
                    <CustomCheckbox checked={isCheckedAll} onChange={handleCheckAll} />
                  </th>
                  <th>이미지</th>
                  <th>상품정보</th>
                  {/* <th>수량</th> */}
                  <th>가격</th>
                  <th>선택</th>
                </tr>
              </thead>
              <tbody className='Tbody tbody1'>
                {carts.map((cart) => (
                  <tr className='product-tr' key={cart.cno}>
                    {/* 개별 체크박스 시작 */}
                    <td className='checkbox-td'>
                      <CustomCheckbox
                        checked={isCheckedcNo[cart.cno] || false}
                        onChange={(isChecked) => handleCheckcNo(cart.cno, isChecked)}
                      />
                    </td>
                    {/* 개별 체크박스 끝 */}
                    <td>
                      <img /* src={`../image/${cart.productOption.product.pimage}`} */
                        src={`http://localhost:8080/product/view/${cart.productOption.product.pimage}`}
                        alt='Product Image'
                      />
                    </td>
                    <td>
                      <Link to={`/product_detail/${cart.productOption.product.pno}`} className='custom-link'>
                        <span style={{ display: "block" }}>{cart.productOption.product.pname}</span>
                        <span style={{ display: "block" }}>&#91;옵션 : {cart.productOption.poName}&#93;</span>
                      </Link>
                    </td>
                    <td>{(cart.productOption.poPrice + cart.productOption.product.pprice).toLocaleString()}원</td>
                    <td>
                      <button
                        className='buttTxt'
                        onClick={() => {
                          deleteCartItemAction(cart.cno);
                        }}
                      >
                        삭제
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
              <tfoot>
                <tr>
                  <td colSpan='6' className='bottomButtonsContainer'>
                    <div style={{ textAlign: "left" }}>
                      <button className='bottomBtn' onClick={handleDeleteCheckedcNo}>
                        선택삭제
                      </button>
                      <button
                        className='bottomBtn'
                        style={{ float: "right", marginRight: "60px" }}
                        onClick={() => navigate("/product/catNo/1")}
                      >
                        쇼핑 계속하기
                      </button>
                      <Link
                        to={"/order"}
                        state={{
                          poNoList: poNoList,
                          cnoList: Object.keys(isCheckedcNo),
                        }}
                      >
                        <button
                          className='bottomBtn'
                          style={{ float: "right", marginRight: "20px" }}
                          /*  onClick={handleOrder} */
                        >
                          주문 하기
                        </button>
                      </Link>
                    </div>
                  </td>
                </tr>
              </tfoot>
            </table>
          ) : (
            <>
              <p style={{ marginTop: "30px", textAlign: "center" }}>장바구니에 상품이 없습니다.</p>
              <button className='bottomBtn' style={{ float: "right" }} onClick={() => navigate("/product/catNo/1")}>
                쇼핑 계속하러가기
              </button>
            </>
          )}
        </div>
      </div>
    </>
  );
}

export default CartList;

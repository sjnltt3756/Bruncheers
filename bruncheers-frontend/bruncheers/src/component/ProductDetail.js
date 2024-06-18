import React, { useEffect, useState } from "react";
import { Link, Route, Routes, useNavigate, useParams } from "react-router-dom";
import * as FetchProduct from "../api/fetchProduct";
import * as FetchCart from "../api/fetchCart";
import * as fetchProductOption from "../api/fetchProductOption";
import { useRecoilState } from "recoil";
import { userState } from "../recoil/atom";
import * as responseStatusCode from "../api/responseStatusCode";
import ProductInfo from "./ProductInfo";
import Review from "./Review";
import Guide from "./Guide";
import Modal from "react-modal";
import { notify, Toast } from "../util/toast";

const ProductDetail = () => {
  const { pNo } = useParams();
  const [product, setProduct] = useState({});
  const [poNo, setPoNo] = useState("select");
  const [productOptions, setProductOptions] = useState([]);
  const [selectProductOption, setSelectProductOption] = useState({});
  const [userLoginState] = useRecoilState(userState);
  const [moveIsOpen, setMoveIsOpen] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    (async () => {
      const result = await FetchProduct.viewProduct(pNo);
      setProduct(result.data);
      const responseJsonObjcet = await fetchProductOption.listProductOptionPNo(pNo);
      setProductOptions(responseJsonObjcet.data);
    })();
  }, [pNo]);

  const addCartAction = async () => {
    if (!userLoginState.data || userLoginState.data.userNo == null) {
      // null과 undefined를 모두 체크
      notify("warning", "로그인한 유저만 이용 가능합니다.");
      return;
    }
    const addCart = {
      poNo: poNo,
      userNo: userLoginState.data.userNo,
      cqty: "1",
      cNo: userLoginState.data.cNo,
    };

    const responseJsonObjcet = await FetchCart.addCart(addCart);
    if (responseJsonObjcet.status === responseStatusCode.ADD_CART) {
      OpenMoveModal();
    } else {
      notify("error", "이미 장바구니에 상품이 담겨있습니다.");
    }
  };

  const handleGoCart = () => {
    navigate("/cart");
  };

  const handleOptionChange = (e) => {
    const selectedPoNo = e.target.value;
    setPoNo(selectedPoNo);
    const selectedOption = productOptions.find((productOption) => productOption.poNo == selectedPoNo);
    setSelectProductOption(selectedOption);
  };

  Modal.setAppElement("#container");

  function OpenMoveModal() {
    setMoveIsOpen(true);
  }

  function CloseMoveModal() {
    setMoveIsOpen(false);
  }

  const moveStyles = {
    content: {
      top: "50%",
      left: "50%",
      right: "50%",
      bottom: "auto",
      marginRight: "-50%",
      transform: "translate(-50%, -50%)",
      width: "500px",
      height: "auto",
    },
  };

  const findUser = (event) => {
    event.preventDefault(); // 폼 제출 막기
    if (!userLoginState.data || userLoginState.data.userNo == null) {
      // null과 undefined를 모두 체크
      notify("warning", "로그인한 유저만 구매 가능합니다.");
      return;
    }
    // 유효성 검사가 통과된 경우에만 라우팅 수행
    navigate("/order", { state: { poNoList: [poNo] } });
  };

  return (
    <>
      <Toast />
    {(product&& productOptions && (
      <div style={{ display: "flex", justifyContent: "center", alignItems: "center" }}>
        <div>
            <div className='pd-list'>
              <div className='pd-list1'>
                  <img
                    className='non'
                    src={`http://localhost:8080/product/view/${product.pimage}`}
                    width='400'
                    alt='food'
                  />
              </div>
              <div className='pd-list2'>
                <p className='pname'>{product.pname}</p>
                <div className='pcontent'>{product.pdetail}</div>
                <div className='price-section'>
                  <hr className='pdetail' />
                  <br />
                  <div className='price-row'>
                    <span className='price-label'>판매가</span>
                    {product.pprice && <span className='price-value'>{product.pprice.toLocaleString()}원</span>}
                  </div>
                  <br />

                  <div className='option-section'>
                    <span className='option-label'>기간선택</span>
                    <select onChange={handleOptionChange} style={{ width: "250px", height: "30px" }}>
                      <option value='select'>-[필수]옵션을 선택해주세요-</option>
                      {productOptions.map((productOption) => (
                        <option key={productOption.poNo} value={productOption.poNo}>
                          {productOption.poName} (+
                          {productOption.poPrice.toLocaleString()}원)
                        </option>
                      ))}
                    </select>
                  </div>
                  <br />
                  <br />
                  <hr className='pdetail' />
                  <br />
                  {selectProductOption && poNo !== "select" && (
                    <div className='option-detail'>
                      <p>
                        {selectProductOption.poName}
                        {"  (+" + selectProductOption.poPrice.toLocaleString() + "원)"}
                      </p>
                      <p>{(product.pprice + selectProductOption.poPrice).toLocaleString()}원</p>
                    </div>
                  )}
                  <br />
                  <div className='non' style={{ display: "flex", justifyContent: "right", paddingBottom: "30px" }}>
                    <p className='non' style={{ fontWeight: "bold", fontSize: "13px", marginRight: "10px" }}>
                      총 금액:
                    </p>
                    <p className='non' style={{ fontWeight: "bold", fontSize: "20px" }}>
                      {(product.pprice + ((poNo !== "select" && selectProductOption.poPrice) || 0)).toLocaleString()}원
                    </p>
                  </div>

                  <button className='custom-btnn btn-3' onClick={findUser}>
                    <span className='non'>바로구매</span>
                  </button>

                  <button className='custom-btnn btn-3' onClick={addCartAction}>
                    <span className='non'>장바구니</span>
                  </button>
                </div>
              </div>
            </div>
        </div>
      </div>
        ))}

      <br />
      <div className='pd-cat'>
        <hr />
        <hr className='pdetail' />
        <ul className='non'>
          <li className='non'>
            <Link to='product_info'>상품상세정보</Link>
          </li>
          <li className='non'>
            <Link to='review'>구매후기</Link>
          </li>
          <li className='non'>
            <Link to='guide'>구매가이드</Link>
          </li>
        </ul>
        <hr className='pdetail' />
        <hr />
      </div>
      {/* 이동 선택 모달 시작 */}
      <Modal isOpen={moveIsOpen} onRequestClose={CloseMoveModal} style={moveStyles} contentLabel='moveModal'>
        <div>
          장바구니에 상품이 담겼습니다
          <br />
          장바구니로 이동하시겠습니까?
        </div>
        <br />
        <input
          className='custom-btn btn-11'
          type='button'
          style={{ width: "110px" }}
          onClick={(e) => {
            e.preventDefault();
            handleGoCart();
          }}
          value='장바구니로 이동'
        />
        <input
          className='custom-btn btn-11'
          type='button'
          style={{ width: "110px" }}
          onClick={(e) => {
            e.preventDefault();
            CloseMoveModal();
          }}
          value='계속 구경하기'
        />
      </Modal>
      {/* 이동 선택 모달 끝 */}
      <Routes>
        <Route path='/' exact element={<ProductInfo />} />
        <Route path='product_info' element={<ProductInfo />} />
        <Route path='review' element={<Review />} />
        <Route path='guide' element={<Guide />} />
      </Routes>
    </>
  );
};

export default ProductDetail;

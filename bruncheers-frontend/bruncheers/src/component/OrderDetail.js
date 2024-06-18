import React, { useEffect, useRef, useState } from "react";
import "../css/cartlist.css";
import "../css/orderdetail.css";
import { Link, useLocation, useNavigate, useParams } from "react-router-dom";
import MakeStar from "./MakeStar";
import * as FetchReview from "../api/fetchReview.js";
import Modal from "react-modal";
import * as fetchOrder from "../api/fetchOrder";
import { useRecoilState } from "recoil";
import { userState } from "../recoil/atom";
import * as StatusCode from "../api/responseStatusCode.js";
import { notify, Toast } from "../util/toast";
function OrderDetail() {
  const { oNo } = useParams(); // URL 파라미터 추출
  const [order, setOrder] = useState({ orderItemDtoList: [] }); // 주문 정보 저장
  const navigate = useNavigate();
  const [userLoginState, setUserLoginState] = useRecoilState(userState);

  const [saveOrder, setSaveOrder] = useState([
    {
      userNo: userLoginState.data.userNo,
      paNo: 0,
      orderItemDtoList: [
        {
          oiNo: 0,
          oiQty: 1,
          reviewNo: 0,
          productOption: {
            poNo: 0,
            poPrice: 0,
            poName: "",
            product: {
              catNo: 0,
              pno: 0,
              pimage: "",
              pdeimg: "",
              pname: "",
              pprice: 0,
              pdetail: "",
            },
          },
          ono: 0,
        },
      ],
      ono: 0,
      ophone: "",
      oreq: "",
      oname: "",
      oaddr: "",
      ozip: 0,
      oprice: 0,
    },
  ]);

  Modal.setAppElement("#container");

  /* 리뷰 모달 */
  const reviewStyle = {
    content: {
      top: "50%",
      left: "50%",
      right: "auto",
      bottom: "auto",
      marginRight: "-50%",
      transform: "translate(-50%, -50%)",
      width: "800px",
      height: "890px",
    },
  };

  //리뷰 작성 모달 열기
  const [reviewModalIsOpen, setReviewIsOpen] = useState({});
  const [currentOrderItem, setCurrentOrderItem] = useState(null);

  function OpenReviewModal(orderItem) {
    setCurrentOrderItem(orderItem);
    setReviewIsOpen((prev) => ({ ...prev, [orderItem.oiNo]: true }));
  }

  function closeReviewModal() {
    setCurrentOrderItem(null);
    setReviewIsOpen((prev) => ({
      ...prev,
      [currentOrderItem.oiNo]: false,
    }));
  }

  /* 리뷰 함수 */
  const [selectedRating, setSelectedRating] = useState(0); // 선택된 별점을 폼 상태에 저장
  const [selectedFile, setSelectedFile] = useState([]); // 선택된 파일을 상태에 저장
  const formRef = useRef();
  const updateRef = useRef();

  const initState = {
    rno: 0,
    rstar: 0,
    rImagefileNames: [],
    rimage: "",
    rreg: "",
    rcontent: "",
    orderItem: 0,
    product: "",
    user: userLoginState.data.userNo,
  };

  const [fetching, setFetching] = useState(false);
  const [result, setResult] = useState(null);
  const [review, setReview] = useState({
    ...initState,
  });

  const handleSelectRating = (rating) => {
    setSelectedRating(rating);
    setReview({ ...review, rstar: rating });
  };

  const handleReviewChange = (e) => {
    const { name, value } = e.target;
    setReview({ ...review, [name]: value });
  };

  const handleFileChange = (e) => {
    setSelectedFile(e.target.files);
  };

  // 리뷰 신규 작성!
  const ReviewWriteAction = async () => {
    if (formRef.current.rcontent == "") {
      notify("warning", "내용을 입력해주세요");
      formRef.current.rcontent.focus();
      return;
    }

    const files = selectedFile;
    const formData = new FormData();
    for (let i = 0; i < files.length; i++) {
      formData.append("selectedfile", files[i]);
    }

    const reviewData = {
      ...review,
      rcontent: review.rcontent.split("\n").join("<br>"), // 개행 문자를 <br> 태그로 변환
      orderItem: currentOrderItem.oiNo,
      product: currentOrderItem.productOption.product.pno,
    };

    formData.append("review", new Blob([JSON.stringify(reviewData)], { type: "application/json" }));

    setFetching(true);

    try {
      const reviewResponse = await FetchReview.writeReview(formData);
      setFetching(false);

      const responseJsonObjcet = await fetchOrder.listOrders(userLoginState.data.userNo);
      setOrder(responseJsonObjcet.data);

      setResult(reviewResponse.result);
      if (reviewResponse.status === StatusCode.CREATED_REAVIEW) {
        const findReviewNo = reviewResponse.data; // 반환된 reviewNo

        // 상태 업데이트

        setReviewIsOpen(false); // 모달 닫기
        notify("warning", "리뷰 작성이 완료되었습니다.");
        const responseJsonObject = await fetchOrder.listOrder(oNo);
        setOrder(responseJsonObject.data);
      } else {
        notify("warning", "리뷰 작성에 실패했습니다.");
        const responseJsonObject = await fetchOrder.listOrder(oNo);
        setOrder(responseJsonObject.data);
      }
    } catch (error) {
      setFetching(false);
      navigate("/error");
    }
  };
  /* 리뷰 삭제 */
  const handleDeleteReview = async (oiNo) => {
    const isConfirmd = window.confirm("리뷰를 삭제하시겠습니까?");
    if (!isConfirmd) {
      return;
    }
    try {
      await FetchReview.deleteReview(oiNo);
       const responseJsonObject = await fetchOrder.listOrder(oNo);
      setOrder(responseJsonObject.data);
    } catch (error) {}
  };


  /* 리뷰 함수 끝*/

  /* 주문 상세정보 뿌리기 */
  useEffect(() => {
    (async () => {
      const responseJsonObject = await fetchOrder.listOrder(oNo);
      const findOrder = responseJsonObject.data;
      setOrder(findOrder);
    })();
  }, []);

  return (
    <>
      <Toast />
      {order && (
        <div
          style={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          <div className='productBox'>
            <h2>주문 상세 내역</h2>
            {/* 주문번호, 주문날짜 시작 */}
            <div className='order-index'>
              <div className='order-index-box'>
                <span>주문번호</span>
                <span>{order.ono}</span>
              </div>
              <div className='order-index-box'>
                <span>주문날짜</span>
                <span>{order.odate && order.odate.substring(0, 10)}</span>
              </div>
            </div>
            {/* 주문번호, 주문날짜 끝 */}
            <table className='Table table1 orderTable'>
              <thead>
                <tr>
                  <th
                    style={{
                      writingMode: "horizontal-tb",
                      textOrientation: "mixed",
                      whiteSpace: "nowrap",
                    }}
                  ></th>
                  <th>이미지</th>
                  <th>상품정보</th>
                  <th>주문날짜</th>
                  <th>가격</th>
                  <th>선택</th>
                </tr>
              </thead>
              {/* 주문한 상품 시작 */}
              <tbody className='Tbody tbody1'>
                {order.orderItemDtoList &&
                  order.orderItemDtoList.length > 0 &&
                  order.orderItemDtoList.map((item, index) => (
                    <tr className='product-tr' key={index}>
                      <td></td>
                      <td>
                        <img
                          /*       src={
                          "/image/" +
                          (item.productOption &&
                            item.productOption.product &&
                            item.productOption.product.pimage)
                        } */
                          src={`http://localhost:8080/product/view/${item.productOption.product.pimage}`}
                          alt='Product Image'
                        />
                      </td>
                      <td>
                        <Link to={`/product_detail/${item.productOption.product.pno}`} className='custom-link'>
                          <span style={{ display: "block" }}>
                            {item.productOption && item.productOption.product && item.productOption.product.pname}
                          </span>
                          <span style={{ display: "block" }}>
                            &#91;옵션 : {item.productOption && item.productOption.poName}&#93;
                          </span>
                        </Link>
                      </td>
                      <td>{order.odate && order.odate.substring(0, 10)}</td>
                      <td>{(item.productOption.product.pprice + item.productOption.poPrice).toLocaleString()}원</td>
                      <td>
                        <div style={{ marginBottom: "5px" }}>
                          {/* 주문 항목의 PK가 0인 경우에만 리뷰 작성 버튼을 보여줍니다. */}
                          {item.reviewNo === 0 && (
                            <button name='buttReview1' onClick={() => OpenReviewModal(item)}>
                              리뷰작성
                            </button>
                          )}
                        </div>
                        <div style={{ marginBottom: "5px" }}>
                          {/* 리뷰 삭제 버튼 */}
                          {item.reviewNo !== 0 && (
                            <button name='buttReview2' onClick={() => handleDeleteReview(item.oiNo)}>
                              리뷰삭제
                            </button>
                          )}
                        </div>
                        {/* 리뷰모달 시작 */}
                        <Modal
                          isOpen={reviewModalIsOpen[item.oiNo] || false}
                          onRequestClose={closeReviewModal}
                          style={reviewStyle}
                          contentLabel='reviewModal'
                        >
                          <>
                            <div className='p-4'>
                              <h4 className='mb-4'>리뷰작성</h4>
                              <form className='needs-validation' noValidate ref={formRef}>
                                {/* 리뷰 시작 */}
                                <div className='row g-3'>
                                  {/* 작성자 시작 */}
                                  <div className='col-12'>
                                    <label htmlFor='userNickname' className='form-label'>
                                      닉네임
                                    </label>
                                    <div className='input-group has-validation'>
                                      <input
                                        type='text'
                                        className='form-control'
                                        id='userNickname'
                                        placeholder=''
                                        name='userNickname'
                                        value={userLoginState.data.userNickname}
                                        required
                                        disabled
                                      />
                                    </div>
                                  </div>
                                  {/* 받는사람 끝 */}
                                  {/* 별점 시작 */}
                                  <div className='form-group '>
                                    <label htmlFor='' className='form-label' style={{ marginTop: "10px" }}>
                                      별점
                                    </label>
                                    <div className='input-group'>
                                      <MakeStar onSelectRating={handleSelectRating} />{" "}
                                      {/* 별점 컴포넌트를 렌더링하고 선택된 별점 정보를 전달 */}
                                    </div>
                                  </div>
                                  {/* 별점 끝 */}
                                  {/* 리뷰 내용 시작 */}
                                  <div className='form-group'>
                                    <label
                                      htmlFor='reviewTextarea'
                                      className='form-label'
                                      style={{ marginTop: "10px" }}
                                    >
                                      내용
                                    </label>
                                    <textarea
                                      id='reviewTextarea '
                                      className='reviewTextarea form-control'
                                      name='rcontent'
                                      placeholder='최대 1000자까지 가능합니다.'
                                      maxLength='1000'
                                      style={{ height: "300px" }}
                                      value={review.rcontent}
                                      onChange={handleReviewChange}
                                    />
                                  </div>
                                  {/* 리뷰 내용 끝 */}
                                  {/* 사진 등록 시작 */}
                                  <div className='mt-5 form-group'>
                                    <label className='form-label'>사진을 등록해주세요</label>
                                    <div className='rounded-md border bg-gray-50 p-4 shadow-md w-24 h-24 mt-2'>
                                      <label
                                        htmlFor='upload'
                                        className='justify-center h-full flex flex-col items-center gap-2 cursor-pointer'
                                      ></label>
                                      <input
                                        id='upload'
                                        type='file'
                                        name='selectedfile'
                                        className='hidden'
                                        multiple='multiple'
                                        onChange={handleFileChange} // 파일 선택 시 handleFileChange 함수 호출
                                      />
                                    </div>
                                  </div>
                                  {/* 사진 등록 끝 */}
                                  <div className='btn-group'>
                                    <button
                                      type='button'
                                      className='coup-table-btn btn-delete'
                                      onClick={closeReviewModal}
                                    >
                                      취소
                                    </button>
                                    <button
                                      type='button'
                                      className='coup-table-btn btn-choose'
                                      onClick={() => ReviewWriteAction(order.ono)}
                                      disabled={fetching}
                                    >
                                      등록
                                    </button>
                                  </div>
                                </div>
                              </form>
                            </div>
                          </>
                        </Modal>
                        {/* 리뷰모달 끝 */}
                      </td>
                    </tr>
                  ))}
              </tbody>
            </table>
            {/* 주문한 상품 끝 */}
            {/* 배송지 정보 시작 */}
            <h5 className='shipTitle'>배송지 정보</h5>
            <table className=' ship-table'>
              <thead></thead>
              <tbody>
                <tr>
                  <th scope='row'>이름</th>
                  <td>{order.oname}</td>
                </tr>
                <tr>
                  <th scope='row'>연락처</th>
                  <td>{order.ophone} </td>
                </tr>
                <tr>
                  <th scope='row'>배송지 주소</th>
                  <td>
                    ({order.ozip}) &nbsp; {order.oaddr}
                  </td>
                </tr>
                <tr>
                  <th scope='row'>배송 요청사항</th>
                  <td>{order.osreq}</td>
                </tr>
                <tr>
                  <th scope='row'>주문 요청사항</th>
                  <td>{order.oreq}</td>
                </tr>
              </tbody>
              <tfoot></tfoot>
            </table>
            {/* 배송지 정보 끝 */}
            {/* 최종 결제 정보 시작 */}
            <h5 className='payTitle'>최종 결제 정보</h5>
            <table className=' pay-table'>
              <thead></thead>
              <tbody>
                <tr>
                  <th scope='row'>상품 합계</th>
                  <td>
                    {order.oprice !== null && order.odiscountprice !== null ? (
                      <span>{(order.oprice + order.odiscountprice).toLocaleString()}원</span>
                    ) : null}
                  </td>
                </tr>
                <tr>
                  <th scope='row'>할인 금액</th>
                  <td>
                    {order.odiscountprice !== undefined && order.odiscountprice !== null ? (
                      order.odiscountprice === 0 ? (
                        <span>없음</span>
                      ) : (
                        <span>{order.odiscountprice.toLocaleString()}원</span>
                      )
                    ) : null}
                  </td>
                </tr>
                <tr>
                  <th scope='row'>최종 결제 금액</th>
                  <td style={{ fontFamily: "GmarketSansTTFBold" }}>
                    {order.oprice ? order.oprice.toLocaleString() + "원" : null}
                  </td>
                </tr>
                <tr>
                  <th scope='row'>결제수단</th>
                  <td>{order.paType}</td>
                </tr>
              </tbody>
              <tfoot></tfoot>
            </table>
            {/* 최종 결제 정보 끝 */}
            {/* 하단 버튼 */}
            <Link to={`/product/catNo/${1}`}>
              <button className='bottomBtn' style={{ marginLeft: "auto" }}>
                쇼핑 계속하기
              </button>
            </Link>
            {/* 하단 버튼 */}
          </div>
        </div>
      )}
      ;
    </>
  );
}

export default OrderDetail;

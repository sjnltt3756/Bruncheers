import "../css/cartlist.css";
import "../css/orderlist.css";
import React, { useEffect, useRef, useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import * as fetchOrder from "../api/fetchOrder";
import * as FetchReview from "../api/fetchReview.js";
import MakeStar from "./MakeStar";
import Modal from "react-modal";
import { useRecoilState } from "recoil";
import { userState } from "../recoil/atom";
import * as StatusCode from "../api/responseStatusCode.js";

import { notify, Toast } from "../util/toast";
function OrderList() {
  const [userLoginState, setUserLoginState] = useRecoilState(userState);
  const [orders, setOrder] = useState([]);

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
  const navigate = useNavigate();

  const fetchOrderList = async () => {
    try {
      const responseJsonObjcet = await fetchOrder.listOrders(
        userLoginState.data.userNo
      );
      if (responseJsonObjcet.data && Array.isArray(responseJsonObjcet.data)) {
        setOrder(responseJsonObjcet.data);
      } else {
        setOrder([]); // 데이터가 없을 경우 빈 배열로 설정
      }
    } catch (error) {}
  };

  useEffect(() => {
    fetchOrderList(); // 컴포넌트가 처음 렌더링될 때 주문 목록을 가져옵니다.
    window.onload = fetchOrderList;
  }, []);

  /* 모달 공통 */

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

  // 리뷰 업데이트 모달 열기
  const [showUpdateModal, setShowUpdateModal] = useState({});
  const openUpdateModal = async (orderItem) => {
    try {
      const reviewResponse = await FetchReview.viewReview(orderItem.oiNo);

      if (reviewResponse.status === StatusCode.READ_REVIEW) {
        setCurrentOrderItem(orderItem);
        setShowUpdateModal((prev) => ({ ...prev, [orderItem.oiNo]: true }));
        setReview(reviewResponse.data);
      } else {
        notify("error", "리뷰 정보를 불러오는데 실패했습니다.");
      }
    } catch (error) {
      navigate("/error");
    }
  };

  function closeUpdateModal() {
    setCurrentOrderItem(null);
    setShowUpdateModal((prev) => ({
      ...prev,
      [currentOrderItem.oiNo]: false,
    }));
  }

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
  const [updateSelectedFile, setUpdateSelectedFile] = useState([]); // 선택된 파일을 상태에 저장
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
  const handleUpdateFileChange = (e) => {
    setUpdateSelectedFile(e.target.files);
  };

  const deleteOldImages = (imageName) => {
    const resultFileNames = review.uploadFileNames.filter(
      (fileName) => fileName !== imageName
    );
    review.uploadFileNames = resultFileNames;
    setReview({ ...review });
  };

  // 리뷰 신규 작성!
  const ReviewWriteAction = async (orderId) => {
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

    formData.append(
      "review",
      new Blob([JSON.stringify(reviewData)], { type: "application/json" })
    );

    setFetching(true);

    try {
      const reviewResponse = await FetchReview.writeReview(formData);
      setFetching(false);

      const responseJsonObjcet = await fetchOrder.listOrders(
        userLoginState.data.userNo
      );
      setOrder(responseJsonObjcet.data);

      setResult(reviewResponse.result);
      if (reviewResponse.status === StatusCode.CREATED_REAVIEW) {
        const findReviewNo = reviewResponse.data; // 반환된 reviewNo

        // 현재 상태의 복사본을 만듭니다.
        const updatedOrders = orders.map((order) => {
          if (order.ono === orderId) {
            return {
              ...order,
              orderItemDtoList: order.orderItemDtoList.map((item) => {
                if (
                  item.oiNo === currentOrderItem.oiNo &&
                  item.reviewNo === 0
                ) {
                  return { ...item, reviewNo: findReviewNo };
                }
                return item;
              }),
            };
          }
          return order;
        });

        // 상태 업데이트

        setSaveOrder(updatedOrders);
        setReviewIsOpen(false); // 모달 닫기
        notify("warning", "리뷰 작성이 완료되었습니다.");
        navigate("/order_list");
      } else {
        notify("warning", "리뷰 작성에 실패했습니다.");
      }
    } catch (error) {
      navigate("/error");
      setFetching(false);
    }
  };

  // 리뷰 수정
  const ReviewUpdateAction = async () => {
    if (updateRef.current.rcontent == "") {
      notify("warning", "내용을 입력해주세요");
      updateRef.current.rcontent.focus();
      return;
    }

    const files = updateSelectedFile;
    const formData = new FormData();
    for (let i = 0; i < files.length; i++) {
      formData.append("updateSelectedfile", files[i]);
    }

    const reviewData = {
      ...review,
      rcontent: review.rcontent.split("\n").join("<br>"), // 개행 문자를 <br> 태그로 변환
      orderItem: currentOrderItem.oiNo,
      product: currentOrderItem.productOption.product.pno,
    };

    formData.append(
      "review",
      new Blob([JSON.stringify(reviewData)], { type: "application/json" })
    );
    setFetching(true);

    try {
      const reviewResponse = await FetchReview.writeReview(formData);

      if (reviewResponse.status === StatusCode.READ_REVIEW) {
        // 상태 업데이트;

        setShowUpdateModal(false); // 모달 닫기
        notify("warning", "리뷰 수정이 완료되었습니다.");
        const responseJsonObjcet = await fetchOrder.listOrders(
          userLoginState.data.userNo
        );
        setOrder(responseJsonObjcet.data);
      } else {
        notify("warning", "리뷰 작성에 실패했습니다.");
      }
    } catch (error) {
      navigate("/error");
      setFetching(false);
    }
  };

  /* 리뷰 삭제 */
  const handleDeleteReview = async (oiNo) => {
    const isConfirmd = window.confirm("리뷰를 삭제하시겠습니까?");
    if (!isConfirmd) {
      return;
    }
    try {
      const responseJsonObject = await FetchReview.deleteReview(oiNo);
      const responseJsonObjcet = await fetchOrder.listOrders(
        userLoginState.data.userNo
      );
      setOrder(responseJsonObjcet.data);
    } catch (error) {}
  };

  /* 리뷰 함수 끝*/

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
        <div className="productBox">
          <h2>주문내역</h2>
          {/* 기간 시작 */}
          {/*  <div className="order-filter">
            <div className="order-filter-period">
              <div className="order-filter-period__tab">
                <button
                  type="button"
                  className="order-filter-period__tab__button"
                  data-period="1year"
                >
                  최근 1년
                </button>
                <button
                  type="button"
                  className="order-filter-period__tab__button"
                  data-period="1week"
                >
                  1주일
                </button>
                <button
                  type="button"
                  className="order-filter-period__tab__button"
                  data-period="1month"
                >
                  1개월
                </button>
                <button
                  type="button"
                  className="order-filter-period__tab__button"
                  data-period="3month"
                >
                  3개월
                </button>
              </div>
              <div className="order-filter-period__date">
                <div className="order-filter-period__input-wrap">
                  <input
                    type="date"
                    className="order-filter-period__input"
                    name="dt_fr_input"
                    value=""
                    placeholder="-"
                  />
                </div>
                <div className="order-filter-period__input-wrap">
                  <input
                    type="date"
                    className="order-filter-period__input"
                    name="dt_to_input"
                    value=""
                    placeholder="-"
                  />
                </div>
              </div>
            </div>
          </div> */}
          {/*  기간 끝*/}
          <table className="Table table1">
            <thead>
              <tr>
                <th
                  style={{
                    writingMode: "horizontal-tb",
                    textOrientation: "mixed",
                    whiteSpace: "nowrap",
                  }}
                >
                  주문번호
                </th>
                <th>이미지</th>
                <th>상품정보</th>
                <th>주문날짜</th>
                <th>가격</th>
                <th>선택</th>
              </tr>
            </thead>
            <tbody className="Tbody tbody1">
              {/* procut 아이템 1개 시작 */}
              {orders &&
                orders.map((order) =>
                  order.orderItemDtoList.map((orderItem) => (
                    <tr className="product-tr" key={orderItem.oiNo}>
                      <td>
                        <Link
                          to={`/order_detail/${order.ono}`}
                          className="custom-link"
                        >
                          {order.ono}
                        </Link>
                      </td>
                      <td>
                        <img
                          /*  src={
                          "./image/" + orderItem.productOption.product.pimage
                        } */
                          src={`http://localhost:8080/product/view/${orderItem.productOption.product.pimage}`}
                          alt="Product Image"
                        />
                      </td>
                      <td>
                        <Link
                          to={`/product_detail/${orderItem.productOption.product.pno}`}
                          className="custom-link"
                        >
                          <span style={{ display: "block" }}>
                            {orderItem.productOption.product.pname}{" "}
                            {/* 상품 이름과 같은 상세 정보를 출력 */}
                          </span>
                          <span style={{ display: "block" }}>
                            &#91;옵션 : {orderItem.productOption.poName}&#93;
                          </span>
                        </Link>
                      </td>
                      <td>{order.odate.substring(0, 10)}</td>
                      <td>
                        {(
                          orderItem.productOption.product.pprice +
                          orderItem.productOption.poPrice
                        ).toLocaleString()}
                        원
                      </td>
                      <td>
                        <div style={{ marginBottom: "5px" }}>
                          {/* 주문 항목의 PK가 0인 경우에만 리뷰 작성 버튼을 보여줍니다. */}
                          {orderItem.reviewNo === 0 && (
                            <button
                              name="buttReview1"
                              onClick={() => OpenReviewModal(orderItem)}
                            >
                              리뷰작성
                            </button>
                          )}
                        </div>
                        <div style={{ marginBottom: "5px" }}>
                          {/* 리뷰 수정 버튼 */}
                          {/*  {orderItem.reviewNo !== 0 && (
                            <button
                              name="buttReview3"
                              onClick={() => openUpdateModal(orderItem)}
                            >

                              리뷰수정
                            </button>
                          )} */}
                        </div>
                        <div style={{ marginBottom: "5px" }}>
                          {/* 리뷰 삭제 버튼 */}
                          {orderItem.reviewNo !== 0 && (
                            <button
                              name="buttReview2"
                              onClick={() => handleDeleteReview(orderItem.oiNo)}
                            >
                              리뷰삭제
                            </button>
                          )}
                        </div>
                        <div style={{ marginBottom: "5px" }}>
                          {/* 리뷰 작성 모달 시작 */}
                          <Modal
                            isOpen={reviewModalIsOpen[orderItem.oiNo] || false}
                            onRequestClose={closeReviewModal}
                            style={reviewStyle}
                            contentLabel="reviewModal"
                          >
                            <>
                              <div className="p-4">
                                <h4 className="mb-4">리뷰작성</h4>
                                <form
                                  className="needs-validation"
                                  noValidate
                                  ref={formRef}
                                >
                                  {/* 리뷰 시작 */}
                                  <div className="row g-3">
                                    {/* 작성자 시작 */}
                                    <div className="col-12">
                                      <label
                                        htmlFor="userNickname"
                                        className="form-label"
                                      >
                                        닉네임
                                      </label>
                                      <div className="input-group has-validation">
                                        <input
                                          type="text"
                                          className="form-control"
                                          id="userNickname"
                                          placeholder=""
                                          name="userNickname"
                                          value={
                                            userLoginState.data.userNickname
                                          }
                                          required
                                          disabled
                                        />
                                      </div>
                                    </div>
                                    {/* 받는사람 끝 */}
                                    {/* 별점 시작 */}
                                    <div className="form-group ">
                                      <label
                                        htmlFor=""
                                        className="form-label"
                                        style={{ marginTop: "10px" }}
                                      >
                                        별점
                                      </label>
                                      <div className="input-group">
                                        <MakeStar
                                          onSelectRating={handleSelectRating}
                                        />{" "}
                                        {/* 별점 컴포넌트를 렌더링하고 선택된 별점 정보를 전달 */}
                                      </div>
                                    </div>
                                    {/* 별점 끝 */}
                                    {/* 리뷰 내용 시작 */}
                                    <div className="form-group">
                                      <label
                                        htmlFor="reviewTextarea"
                                        className="form-label"
                                        style={{ marginTop: "10px" }}
                                      >
                                        내용
                                      </label>
                                      <textarea
                                        id="reviewTextarea "
                                        className="reviewTextarea form-control"
                                        name="rcontent"
                                        placeholder="최대 1000자까지 가능합니다."
                                        maxLength="1000"
                                        style={{ height: "300px" }}
                                        value={review.rcontent}
                                        onChange={handleReviewChange}
                                      />
                                    </div>
                                    {/* 리뷰 내용 끝 */}
                                    {/* 사진 등록 시작 */}
                                    <div className="mt-5 form-group">
                                      <label className="form-label">
                                        사진을 등록해주세요
                                      </label>
                                      <div className="rounded-md border bg-gray-50 p-4 shadow-md w-24 h-24 mt-2">
                                        <label
                                          htmlFor="upload"
                                          className="justify-center h-full flex flex-col items-center gap-2 cursor-pointer"
                                        ></label>
                                        <input
                                          id="upload"
                                          type="file"
                                          name="selectedfile"
                                          className="hidden"
                                          onChange={handleFileChange} // 파일 선택 시 handleFileChange 함수 호출
                                        />
                                      </div>
                                    </div>
                                    {/* 사진 등록 끝 */}
                                    <div className="btn-group">
                                      <button
                                        type="button"
                                        className="coup-table-btn btn-delete"
                                        onClick={closeReviewModal}
                                      >
                                        취소
                                      </button>
                                      <button
                                        type="button"
                                        className="coup-table-btn btn-choose"
                                        onClick={() =>
                                          ReviewWriteAction(order.ono)
                                        }
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
                        </div>

                        <div style={{ marginBottom: "5px" }}>
                          {/* 리뷰수정 모달 시작 */}
                          <Modal
                            isOpen={showUpdateModal[orderItem.oiNo] || false}
                            onRequestClose={closeUpdateModal}
                            style={reviewStyle}
                            contentLabel="updateReviewModal"
                          >
                            {review && (
                              <div className="p-4">
                                <h4 className="mb-4">리뷰 수정</h4>
                                <form
                                  className="needs-validation"
                                  noValidate
                                  ref={updateRef}
                                >
                                  <div className="row g-3">
                                    <div className="col-12">
                                      <label
                                        htmlFor="userNickname"
                                        className="form-label"
                                      >
                                        닉네임
                                      </label>
                                      <div className="input-group has-validation">
                                        <input
                                          type="text"
                                          className="form-control"
                                          id="userNickname"
                                          value={review.user}
                                          required
                                          disabled
                                        />
                                      </div>
                                    </div>
                                    <div className="form-group">
                                      <label
                                        htmlFor=""
                                        className="form-label"
                                        style={{ marginTop: "10px" }}
                                      >
                                        별점
                                      </label>
                                      <div className="input-group">
                                        <MakeStar
                                          onSelectRating={handleSelectRating}
                                          initialRating={review.rstar}
                                        />
                                      </div>
                                    </div>
                                    <div className="form-group">
                                      <label
                                        htmlFor="reviewTextarea"
                                        className="form-label"
                                        style={{ marginTop: "10px" }}
                                      >
                                        내용
                                      </label>
                                      <textarea
                                        id="reviewTextarea"
                                        className="reviewTextarea form-control"
                                        name="rcontent"
                                        placeholder="최대 1000자까지 가능합니다."
                                        maxLength="1000"
                                        style={{ height: "300px" }}
                                        value={review.rcontent}
                                        onChange={handleReviewChange}
                                      />
                                    </div>
                                    <div className="mt-5 form-group">
                                      <label className="form-label">
                                        사진을 등록해주세요
                                      </label>
                                      <div className="rounded-md border bg-gray-50 p-4 shadow-md w-24 h-24 mt-2">
                                        <label
                                          htmlFor="upload"
                                          className="justify-center h-full flex flex-col items-center gap-2 cursor-pointer"
                                        ></label>
                                        <input
                                          id="upload"
                                          type="file"
                                          name="updateSelectedfile"
                                          className="hidden"
                                          onChange={handleUpdateFileChange}
                                        />
                                      </div>
                                    </div>
                                    <div className="btn-group">
                                      <button
                                        type="button"
                                        className="coup-table-btn btn-delete"
                                        onClick={closeUpdateModal}
                                      >
                                        취소
                                      </button>
                                      <button
                                        type="button"
                                        className="coup-table-btn btn-choose"
                                        onClick={() =>
                                          ReviewUpdateAction(review.orderItem)
                                        }
                                        disabled={fetching}
                                      >
                                        등록
                                      </button>
                                    </div>
                                  </div>
                                </form>
                              </div>
                            )}
                          </Modal>
                          {/* 리뷰모달 끝 */}
                        </div>

                        {/* 추가적으로 필요한 버튼이 있다면 여기에 계속 추가 */}
                      </td>
                    </tr>
                  ))
                )}
              {/* procut 아이템 1개 끝 */}
            </tbody>
            {/* 하단 버튼 */}
            <tfoot>
              <tr>
                <td colSpan="6" className="bottomButtonsContainer">
                  <div style={{ textAlign: "left" }}>
                    <Link to={`/product/catNo/${1}`}>
                      <button className="bottomBtn" style={{ float: "right" }}>
                        쇼핑 계속하기
                      </button>
                    </Link>
                  </div>
                </td>
              </tr>
            </tfoot>
            {/* 하단 버튼 */}
          </table>
        </div>
      </div>
    </>
  );
}

export default OrderList;
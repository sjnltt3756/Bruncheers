import { useDaumPostcodePopup } from "react-daum-postcode";
import React, { useEffect, useRef, useState } from "react";
import * as FetchCoupon from "../api/fetchCoupon";
import * as FetchShip from "../api/fetchShip";
import "../css/order.css";
import "../css/shippopup.css";
import "../css/couponpopup.css";
import Modal from "react-modal";
import * as FetchOrder from "../api/fetchOrder";
import { useLocation, useNavigate, useParams } from "react-router";
import * as responseStatusCode from "../api/responseStatusCode";
import * as fetchProductOption from "../api/fetchProductOption";
import { useRecoilState } from "recoil";
import { userState } from "../recoil/atom";
import { notify, Toast } from "../util/toast";
import { loadPaymentWidget, ANONYMOUS } from "@tosspayments/payment-widget-sdk";
import { nanoid } from "nanoid";
import { orderHpRegex } from "../util/regex";
function Order() {
  const [userLoginState, setUserLoginState] = useRecoilState(userState);

  const navigate = useNavigate();
  const formRef = useRef();
  const orderFormRef = useRef();
  const location = useLocation();
  const [selectedCoupon, setSelectedCoupon] = useState(null); // 선택된 쿠폰이 뭔지!
  const [productOption, setProductOptions] = useState([]); // 상품 옵션 정보
  const { poNoList = [], cnoList = [] } = location.state || {
    poNoList: [],
    cnoList: [],
  };
  // 넘어오는 장바구니 번호, 상품 옵션 번호 리스트들
  const [discountPrice, setDiscountPrice] = useState(0); // 할인 가격
  const [originalOPrice, setOriginalOPrice] = useState(0); // 할인 전 주문 가격
  const [discountedOPrice, setDiscountedOPrice] = useState(0); // 할인 후 주문 가격

  const [order, setOrder] = useState({
    oname: "",
    firstPartse: "010",
    secondPartse: "",
    thirdPartse: "",
    ophone: "",
    ozip: "",
    oaddr: "",
    osreq: "",
    oreq: "",
    ostype: "",
    cnoList: [],
    poNoList: [],
    userNo: userLoginState.data.userNo,
    oprice: "",
    odiscountprice: "",
    coupNo: "",
    payType: "",
  });

  /* 주문 폼 요소 onchange 메소드 */
  const handleChangeOrderSaveForm = (e) => {
    setOrder({
      ...order,
      [e.target.name]: e.target.value,
    });
  };

  /* 상품 옵션 불러오기 */
  useEffect(() => {
    (async () => {
      const responseJsonObjcet = await fetchProductOption.listProductOption(poNoList);
      setProductOptions(responseJsonObjcet.data || []);
    })();
  }, []);

  /*  테스트 */
  useEffect(() => {
    (async () => {
      let totalOPrice = 0;

      if (productOption && productOption.length > 0) {
        productOption.forEach((option) => {
          let orderItemPrice = option.product.pprice;
          orderItemPrice += option.poPrice;
          totalOPrice += orderItemPrice;
        });

        setOriginalOPrice(totalOPrice);
      }
    })();
  }, [productOption]);

  /* 모달 공통 */

  Modal.setAppElement("#container");

  /* 쿠폰 모달 시작 */
  const CouponStyles = {
    content: {
      top: "50%",
      left: "50%",
      right: "auto",
      bottom: "auto",
      marginRight: "-50%",
      transform: "translate(-50%, -50%)",
      width: "900px",
      height: "auto",
    },
  };

  function couponModalOpen() {
    setCoupIsOpen(true);
  }

  function closeCouponModal() {
    setCoupIsOpen(false);
  }

  const [couponModalIsOpen, setCoupIsOpen] = React.useState(false);
  const [coupons, setCoupons] = useState([]);

  useEffect(() => {
    (async () => {
      const responseJsonObjcet = await FetchCoupon.listCoupon(userLoginState.data.userNo);
      setCoupons(responseJsonObjcet.data || []);
    })();
  }, []);

  // 쿠폰 선택 시 호출되는 함수
  const handleCouponSelect = (coupon) => {
    setSelectedCoupon(coupon.coupNo);

    const calculatedDiscountPrice = originalOPrice * coupon.coupdiscount;

    setDiscountPrice(calculatedDiscountPrice);

    const calculatedDiscountedOPrice = originalOPrice - calculatedDiscountPrice;
    setDiscountedOPrice(calculatedDiscountedOPrice);
  };

  /* 배송 모달 시작 */
  const ShipStyles = {
    content: {
      top: "50%",
      left: "50%",
      right: "auto",
      bottom: "auto",
      marginRight: "-50%",
      transform: "translate(-50%, -50%)",
      width: "550px",
      height: "auto",
    },
  };

  const [shipModalIsOpen, setShipIsOpen] = React.useState(false);
  const [updateShipModalIsOpen, setUpdateShipIsOpen] = React.useState(false);
  const [newShipModalIsOpen, setNewShipIsOpen] = React.useState(false);

  /***************************** 배송지 리스트 *****************************/
  const [ships, setShips] = useState([]);
  async function shipModalOpen() {
    try {
      const responseJsonObjcet = await FetchShip.viewShipByUser(userLoginState.data.userNo);

      setShips(responseJsonObjcet.data);
      setShipIsOpen(true);
    } catch (error) {
      navigate("/error");
    }
  }
  function closeShipModal() {
    setShipIsOpen(false);
  }

  /***************************** 배송지 수정 *****************************/
  const [updateShip, setUpdateShip] = useState({
    sno: "",
    sname: "테스트",
    sphone: "010-7895-2000",
    szip: 25879,
    saddr: "제주특별자치도 제주시",
    sreq: "알잘딱깔센 구현됐으면 좋겠다..",
  });

  async function updateShipModalOpen(sNo) {
    const responseJsonObjcet = await FetchShip.viewShip(sNo);
    setUpdateShip(responseJsonObjcet.data);
    setUpdateShipIsOpen(true);
  }

  useEffect(() => {}, [updateShip]);

  const [firstPart, setFirstPart] = useState("");
  const [secondPart, setSecondPart] = useState("");
  const [thirdPart, setThirdPart] = useState("");

  useEffect(() => {
    // 전화번호가 변경될 때마다 세 부분으로 나누어 설정
    if (updateShip.sphone) {
      const parts = updateShip.sphone.split("-");
      setFirstPart(parts[0]);
      setSecondPart(parts[1]);
      setThirdPart(parts[2]);
    }
  }, [updateShip.sphone]);

  function updateShipModalClose() {
    setUpdateShipIsOpen(false);
  }

  const onChangeUpdateShip = (e) => {
    const { value, name } = e.target;
    setUpdateShip((updateShip) => ({
      ...updateShip,
      [name]: value,
    }));
  };

  const optionChange = (e) => {
    const { value } = e.target;
    if (value === "sReq-input") {
      setShowTextarea(true); // 텍스트 영역 표시
    } else {
      setShowTextarea(false); // 텍스트 영역 숨김
      setUpdateShip((prevState) => ({
        ...prevState,
        sreq: value, // 직접 입력이 아닌 경우 sreq 업데이트
      }));
    }
  };

  const handleTextareaChange = (e) => {
    const { value } = e.target;
    setUpdateShip((prevState) => ({
      ...prevState,
      sreq: value, // 텍스트 영역 내용으로 sreq 업데이트
    }));
  };

  const ShipModifyAction = async () => {
    const updatedPhoneNumber = `${firstPart}-${secondPart}-${thirdPart}`;
    if (formRef.current.sname.value === "") {
      notify("warning", "이름을 입력해주세요");
      formRef.current.sname.focus();
      return;
    }
    if (formRef.current.secondPart.value === "") {
      notify("warning", "번호를 입력해주세요");
      formRef.current.secondPart.focus();
      return;
    }
    if (formRef.current.thirdPart.value === "") {
      notify("warning", "번호를 입력해주세요");
      formRef.current.thirdPart.focus();
      return;
    }
    if (formRef.current.stype.value === "") {
      notify("warning", "배송지 유형을 입력해주세요");
      formRef.current.stype.focus();
      return;
    }
    if (formRef.current.saddr.value === "") {
      notify("warning", "주소를 입력해주세요");
      formRef.current.saddr.focus();
      return;
    }

    const updatedShip = {
      ...updateShip,
      sphone: updatedPhoneNumber,
    };

    const responseJsonObject = await FetchShip.updateShip(updatedShip.sno, updatedShip);
    updateShipModalClose();
    shipModalOpen();
  };

  /***************************** 배송지 등록 *****************************/
  const [saveShip, setSaveShip] = useState({
    sno: "",
    sname: "",
    sphone: "",
    saddr: "",
    sreq: "",
    stype: "",
    userNo: userLoginState.data.userNo,
  });

  function newShipModalOpen() {
    setSaveShip({
      sname: "",
      sphone: "",
      saddr: "",
      sreq: "",
      stype: "",
      userNo: userLoginState.data.userNo,
    });
    setFirstPartSS("010");
    setSecondPartSS("");
    setThirdPartSS("");
    setNewShipIsOpen(true);
  }
  function newShipModalClose() {
    setNewShipIsOpen(false);
  }

  const [firstPartSS, setFirstPartSS] = useState("010");
  const [secondPartSS, setSecondPartSS] = useState("");
  const [thirdPartSS, setThirdPartSS] = useState("");

  const onChangeSaveShip = (e) => {
    const { value, name } = e.target;
    if (name === "secondPartSS") {
      setSecondPartSS(value);
    } else if (name === "thirdPartSS") {
      setThirdPartSS(value);
    } else {
      setSaveShip((prevState) => ({
        ...prevState,
        [name]: value,
      }));
    }
  };

  useEffect(() => {
    // 전화번호가 변경될 때마다 세 부분으로 나누어 설정
    if (saveShip.sphone) {
      const parts = saveShip.sphone.split("-");
      setFirstPartSS(parts[0]);
      setSecondPartSS(parts[1]);
      setThirdPartSS(parts[2]);
    }
  }, [saveShip.sphone]);

  const ShipSaveAction = async () => {
    const updatedPhoneNumber = `${firstPartSS}-${secondPartSS}-${thirdPartSS}`;
    const updatedSaveShip = { ...saveShip, sphone: updatedPhoneNumber };

    if (formRef.current.sname.value === "") {
      notify("warning", "받는사람을 입력해주세요");
      formRef.current.sname.focus();
      return;
    }
    if (formRef.current.secondPartSS.value === "") {
      notify("warning", "번호를 입력해주세요");
      formRef.current.secondPartSS.focus();
      return;
    }
    if (formRef.current.thirdPartSS.value === "") {
      notify("warning", "번호를 입력해주세요");
      formRef.current.thirdPartSS.focus();
      return;
    }
    if (formRef.current.stype.value === "") {
      notify("warning", "배송지 유형을 입력해주세요");
      formRef.current.stype.focus();
      return;
    }
    if (formRef.current.saddr.value === "") {
      notify("warning", "주소를 입력해주세요");
      formRef.current.saddr.focus();
      return;
    }

    try {
      await FetchShip.saveShip(updatedSaveShip);
      newShipModalClose();
      shipModalOpen();
    } catch (error) {
      navigate("/error");
    }
  };

  /***************************** 배송지 삭제 *****************************/
  const handleDeleteShip = async (sNo) => {
    const isConfirmed = window.confirm("배송지를 삭제하시겠습니까?");
    if (!isConfirmed) {
      return;
    }
    try {
      await FetchShip.removeShip(sNo);
      const updatedShips = ships.filter((ship) => ship.sno !== sNo); // 삭제된 배송지를 제외한 새로운 목록 생성
      setShips(updatedShips); // 새로운 배송지 목록으로 상태 업데이트
    } catch (error) {
      navigate("/error");
    }
  };
  /***************************** 배송지 선택  *****************************/
  const [selectedShip, setSelectedShip] = useState({
    sname: "",
    sphone: "",
    szip: "",
    saddr: "  ",
    stype: "",
    sreq: "",
  });

  const [selectedsecondPartse, setselectedSecondPartse] = useState("");
  const [selectedthirdPartse, setselectedThirdPartse] = useState("");

  const handleSelectShip = async (sNo) => {
    try {
      const responseJsonObjcet = await FetchShip.viewShip(sNo);
      setSelectedShip(responseJsonObjcet.data);
      if (responseJsonObjcet.data.sphone) {
        const parts = responseJsonObjcet.data.sphone.split("-");
        setselectedSecondPartse(parts[1]);
        setselectedThirdPartse(parts[2]);

        setOrder({
          ...order,
          oname: responseJsonObjcet.data.sname,
          oaddr: responseJsonObjcet.data.saddr,
          ozip: responseJsonObjcet.data.szip,
          secondPartse: parts[1],
          thirdPartse: parts[2],
          ostype: responseJsonObjcet.data.stype,
        });
      }
    } catch (error) {
      navigate("/error");
    }
    closeShipModal();
  };
  /************** (체크박스)기본배송지 불러오기  **************/
  const [isDefaultChecked, setIsDefaultChecked] = useState(false);
  const initialDefaultShip = {
    sname: "",
    sphone: "",
    szip: "",
    saddr: "",
    stype: "",
    sreq: "",
  };
  const [defaultShip, setDefaultShip] = useState(initialDefaultShip);
  const [firstPartde, setFirstPartde] = useState("010");
  const [secondPartde, setSecondPartde] = useState("");
  const [thirdPartde, setThirdPartde] = useState("");

  useEffect(() => {
    if (defaultShip.sphone) {
      const parts = defaultShip.sphone.split("-");
      setFirstPartde(parts[0]);
      setSecondPartde(parts[1]);
      setThirdPartde(parts[2]);
    } else {
      setFirstPartde("010");
      setSecondPartde("");
      setThirdPartde("");
    }
  }, [defaultShip]);

  const handleCheckboxChange = async (e) => {
    const isChecked = e.target.checked;
    setIsDefaultChecked(isChecked);

    if (isChecked) {
      try {
        const responseJsonObjcet = await FetchShip.viewShipByUserFirstShip(userLoginState.data.userNo);

        setOrder({
          ...order,
          oname: responseJsonObjcet.data.sname,
          oaddr: responseJsonObjcet.data.saddr,
          ozip: responseJsonObjcet.data.szip,
          secondPartse: responseJsonObjcet.data.sphone.split("-")[1],
          thirdPartse: responseJsonObjcet.data.sphone.split("-")[2],
          ostype: responseJsonObjcet.data.stype,
        });

        setDefaultShip(responseJsonObjcet.data);

        if (responseJsonObjcet.data.sphone) {
        }
      } catch (error) {
        navigate("/error");
      }
    } else {
      setDefaultShip(initialDefaultShip);
      setOrder({
        ...order,
        oname: "",
        oaddr: "",
        ozip: "",
        secondPartse: "",
        thirdPartse: "",
        ostype: "",
      });
    }
  };
  /**********************************************************************/

  /* Daum api open */
  const open = useDaumPostcodePopup("//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js");

  /* 배송요구사항 직접입력 선택 시 */
  const [showTextarea, setShowTextarea] = useState(false);

  const handleChange = (e) => {
    const { value } = e.target;
    if (value === "sReq-input") {
      setShowTextarea(true);
    } else {
      setShowTextarea(false);
      setOrder((order) => ({
        ...order,
        osreq: value,
      }));
    }
  };
  /* 배송지 api */
  const handleComplete = (data) => {
    let address = data.address;
    let zonecode = data.zonecode;
    let extraAddress = "";

    if (data.addressType === "R") {
      if (data.bname !== "") {
        extraAddress += data.bname;
      }
      if (data.buildingName !== "") {
        extraAddress += extraAddress !== "" ? `, ${data.buildingName}` : data.buildingName;
      }
      address += extraAddress !== "" ? ` (${extraAddress})` : "";
    }

    setUpdateShip({
      ...updateShip,
      szip: zonecode,
      saddr: address,
    });
    setSaveShip({
      ...saveShip,
      szip: zonecode,
      saddr: address,
    });
    setSelectedShip({
      ...selectedShip,
      szip: zonecode,
      saddr: address,
    });
    setDefaultShip({
      ...selectedShip,
      szip: zonecode,
      saddr: address,
    });

    setOrder({
      ...order,
      ozip: zonecode,
      oaddr: address,
    });
  };

  const handleClick = () => {
    open({ onComplete: handleComplete });
  };

  /* 결제 핸들링 api*/
  const widgetClientKey = "test_gck_docs_Ovk5rk1EwkEbP0W43n07xlzm";
  const customerKey = "test_sk_Z1aOwX7K8mXnGADKMqBBryQxzvNP";

  const [paymentWidget, setPaymentWidget] = useState(null);
  const paymentMethodsWidgetRef = useRef(null);
  const [price, setPrice] = useState(1000);

  const phone = `${order.firstPartse}-${order.secondPartse}-${order.thirdPartse}`;

  useEffect(() => {
    const fetchPaymentWidget = async () => {
      try {
        const loadedWidget = await loadPaymentWidget(widgetClientKey, customerKey);
        setPaymentWidget(loadedWidget);
      } catch (error) {
        navigate("/error");
      }
    };

    fetchPaymentWidget();
  }, []);

  useEffect(() => {
    if (!paymentWidget) {
      return;
    }

    const paymentMethodsWidget = paymentWidget.renderPaymentMethods(
      "#payment-widget",
      { value: price },
      { variantKey: "DEFAULT" }
    );

    paymentWidget.renderAgreement("#agreement", { variantKey: "AGREEMENT" });

    paymentMethodsWidgetRef.current = paymentMethodsWidget;
  }, [paymentWidget, price]);

  useEffect(() => {
    const paymentMethodsWidget = paymentMethodsWidgetRef.current;

    if (!paymentMethodsWidget) {
      return;
    }

    paymentMethodsWidget.updateAmount(price);
  }, [price]);

  const handlePaymentRequest = async () => {
    /******  주문 유효성 검사 */
    if (orderFormRef.current.oname.value === "") {
      notify("warning", "받는사람을 입력해주세요");
      orderFormRef.current.oname.focus();
      return;
    }
    if (orderFormRef.current.secondPartse.value === "") {
      notify("warning", "전화번호를 입력해주세요");
      orderFormRef.current.secondPartse.focus();
      return;
    }
    if (orderFormRef.current.ozip.value === "") {
      notify("warning", "우편번호를 입력해주세요");
      orderFormRef.current.ozip.focus();
      return;
    }
    if (orderFormRef.current.oaddr.value === "") {
      notify("warning", "주소를 입력해주세요");
      orderFormRef.current.oaddr.focus();
      return;
    }

    if (!paymentWidget) {
      return;
    }

    const updatedOrder = {
      ...order,
      ophone: phone,
      oprice: discountedOPrice || originalOPrice,
      odiscountprice: discountPrice,
      payType: "",
      poNoList: poNoList,
      cnoList: cnoList,
      coupNo: selectedCoupon ? selectedCoupon : null,
    };

    setOrder(updatedOrder);
    setPrice(discountedOPrice || originalOPrice);
    try {
      const selectedPaymentMethod = paymentMethodsWidgetRef.current.getSelectedPaymentMethod();
      const paymentType = selectedPaymentMethod ? selectedPaymentMethod.method : null;

      const orderToSubmit = {
        ...updatedOrder,
        payType: paymentType,
      };

      paymentWidget
        .requestPayment({
          // 결제 정보 파라미터
          orderId: nanoid(),
          orderName: `${productOption[0].product.pname} 외 ${productOption.length}종`,
          customerName: `${userLoginState.data.userNickname}`,
        })
        .then(async function (data) {
          try {
            // 주문 생성!!
            await FetchOrder.createOrder(orderToSubmit);
            navigate(`/order_success`);
          } catch (error) {
            if (error.code === "USER_CANCEL") {
              // 결제 고객이 결제창을 닫았을 때 에러 처리
            } else if (error.code === "INVALID_CARD_COMPANY") {
              // 유효하지 않은 카드 코드에 대한 에러 처리
            }
          }
        });
    } catch (error) {
      window.location.href = `${window.location.origin}/error`;
    }
  };

  return (
    <>
      <div className='container'>
        <main>
          <div className='py-5 text-center'>
            <h2>주문/결제</h2>
            <p className='lead'>주문 전 공지사항에 있는 유의사항을 참고해주세요</p>
          </div>

          {/* 결제정보 시작 */}
          <div className='row g-5'>
            <div className='col-md-5 col-lg-4 order-md-last'>
              <h4 className='d-flex justify-content-between align-items-center mb-3'>
                <span className='text-primary'>결제 정보</span>
                <span className='badge bg-primary rounded-pill'>{poNoList.length}</span>
              </h4>
              <ul className='list-group mb-3'>
                <li className='list-group-item d-flex justify-content-between lh-sm'>
                  <div>
                    <h6 className='my-3'>주문 상품</h6>
                  </div>
                  <span className='text-body-secondary' style={{ lineHeight: "3" }}>
                    {originalOPrice.toLocaleString()}원
                  </span>
                </li>
                <li className='list-group-item d-flex justify-content-between bg-body-tertiary'>
                  <div className='text-success'>
                    <h6 className='my-3'>할인</h6>
                  </div>
                  <span className='text-success' style={{ lineHeight: "3" }}>
                    −{discountPrice.toLocaleString()}원
                  </span>
                </li>
                <li className='list-group-item d-flex justify-content-between pt-3 pb-3'>
                  <span>최종 결제 금액</span>
                  <strong>
                    {" "}
                    {selectedCoupon ? discountedOPrice.toLocaleString() : originalOPrice.toLocaleString()}원
                  </strong>
                </li>
              </ul>
              {/* 결제정보 끝 */}

              {/* 할인쿠폰 시작*/}
              <div>
                <form className='card'>
                  <a className='btn btn-secondary' onClick={couponModalOpen} value={selectedCoupon}>
                    쿠폰 조회/목록
                  </a>
                </form>
                <Modal
                  isOpen={couponModalIsOpen}
                  onRequestClose={closeCouponModal}
                  style={CouponStyles}
                  contentLabel='CoupModal'
                >
                  <>
                    <h1 className='coupTitle' style={{ paddingBottom: "5px" }}>
                      적용 가능 쿠폰 리스트
                    </h1>
                    <div className='coupContent'>
                      <p style={{ paddingBottom: "3px" }}>해당 주문에 적용 가능한 쿠폰 입니다. 쿠폰을 선택해주세요.</p>
                      <p>저희 브런치얼스는 전 상품에 쿠폰이 적용됩니다! </p>
                      <table className='coup-table'>
                        <thead>
                          <tr>
                            <th scope='col'>쿠폰명</th>
                            <th scope='col'>쿠폰설명</th>
                            <th scope='col'>할인율</th>
                            <th scope='col'>할인금액</th>
                          </tr>
                        </thead>
                        <tbody>
                          {/* 쿠폰 아이템 시작 */}
                          {coupons.map((coupon) => {
                            return (
                              <tr key={coupon.coupNo}>
                                <td className='left'>
                                  <input
                                    type='radio'
                                    className='coup_radio'
                                    name='coup_radio'
                                    onChange={() => handleCouponSelect(coupon)} // 쿠폰 선택 시 handleCouponSelect 함수 호출
                                  />
                                  <span className='coup_span'>{coupon.coupName}</span>
                                </td>
                                <td>{coupon.coupDesc}</td>
                                <td>{Math.round(coupon.coupdiscount * 100)}%</td>
                                <td className='discount_price'>
                                  {Math.floor(parseInt(originalOPrice) * coupon.coupdiscount).toLocaleString()}원
                                </td>
                              </tr>
                            );
                          })}
                          {/* 쿠폰 아이템 끝 */}
                        </tbody>
                      </table>
                      <div className='btn-group'>
                        <a className='coup-table-btn btn-delete' onClick={closeCouponModal}>
                          취소
                        </a>
                        <a className='coup-table-btn btn-choose' onClick={closeCouponModal}>
                          쿠폰선택
                        </a>
                      </div>
                    </div>
                  </>
                </Modal>
              </div>
              {/* 할인쿠폰 끝 */}
            </div>
            <div className='col-md-7 col-lg-8'>
              {/* 배송지 시작 */}
              <h4 className='mb-4'>배송지</h4>
              {/******************************************  폼의 시작  ******************************************/}
              <form ref={orderFormRef}>
                <div className='row g-3'>
                  {/* 받는사람 시작 */}
                  <div className='col-12'>
                    <label htmlFor='username' className='form-label'>
                      받는사람
                    </label>
                    <div className='input-group'>
                      <input
                        type='text'
                        className='form-control'
                        id='username'
                        name='oname'
                        value={order.oname}
                        onChange={handleChangeOrderSaveForm}
                      />
                    </div>
                  </div>
                  {/* 받는사람 끝 */}
                  {/* 휴대폰 번호 시작 */}
                  <div className='col-12'>
                    <label htmlFor='hp' className='form-label'>
                      휴대전화
                    </label>
                    <div className='input-group'>
                      <input
                        type='number'
                        className='form-control'
                        id='firstPartse'
                        name='firstPartse'
                        value={order.firstPartse}
                        readOnly
                      />
                      <span style={{ lineHeight: "3" }}> &nbsp;-&nbsp;</span>
                      <input
                        type='number'
                        className='form-control'
                        id='hp2'
                        name='secondPartse'
                        value={order.secondPartse}
                        onChange={handleChangeOrderSaveForm}
                        onInput={(e) => {
                          if (e.target.value.length > 4) {
                            e.target.value = e.target.value.slice(0, 4);
                          }
                        }}
                      />
                      <span style={{ lineHeight: "3" }}> &nbsp;-&nbsp;</span>
                      <input
                        type='number'
                        className='form-control'
                        id='hp3'
                        name='thirdPartse'
                        value={order.thirdPartse}
                        onChange={handleChangeOrderSaveForm}
                        onInput={(e) => {
                          if (e.target.value.length > 4) {
                            e.target.value = e.target.value.slice(0, 4);
                          }
                        }}
                      />
                    </div>
                  </div>
                  {/* 휴대폰 번호  끝 */}

                  {/* 주소 시작 */}
                  <div style={{ display: "flex", alignItems: "center" }}>
                    <label htmlFor='address' className='form-label' style={{ marginRight: "40px", marginBottom: "0" }}>
                      주소
                    </label>
                    <button className='addrList' type='button' onClick={shipModalOpen}>
                      배송지 목록
                    </button>
                  </div>
                  {/*★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★*/}
                  {/* 배송지 목록 모달 시작 */}
                  <Modal
                    isOpen={shipModalIsOpen}
                    onRequestClose={closeShipModal}
                    style={ShipStyles}
                    contentLabel='shipModal'
                  >
                    <div className='ship-popup-wrap popup-address-list'>
                      <h1 className='popup-tit'>배송지 목록</h1>
                      <a className='ship-link' onClick={newShipModalOpen}>
                        <span>신규 배송지 등록</span>
                      </a>
                      {/* 배송지 목록 */}
                      <div className='list-dlv-addr'>
                        {ships && Array.isArray(ships) && ships.length > 0 ? (
                          ships.map((ship, index) => (
                            <div key={ship.sno} className='ship-addr-info'>
                              <input type='hidden' id='' value='' />
                              <div className='addr-name'>
                                <span className='name'>{ship.sname}</span>
                                <span>&nbsp;({ship.stype})&nbsp;</span>
                                {index === 0 && <span className='ship-label delv reserv'>기본배송지</span>}
                              </div>
                              <span className='phone-no'>{ship.sphone}</span>
                              <div className='txt-addr'>
                                ({ship.szip}) {ship.saddr}
                              </div>
                              <div className='ship-btn-group'>
                                <div className='btn-left'>
                                  <a
                                    className='ship-btn btn-sm btn-default'
                                    onClick={() => {
                                      updateShipModalOpen(ship.sno);
                                    }}
                                  >
                                    수정
                                  </a>
                                  {index > 0 && (
                                    <a
                                      href=''
                                      className='ship-btn btn-sm btn-default'
                                      onClick={(e) => {
                                        handleDeleteShip(ship.sno);
                                        e.preventDefault();
                                      }}
                                    >
                                      삭제
                                    </a>
                                  )}
                                </div>
                                <div className='btn-right'>
                                  <a
                                    href=''
                                    className='ship-btn btn-sm btn-accent'
                                    onClick={(e) => {
                                      handleSelectShip(ship.sno);
                                      e.preventDefault();
                                    }}
                                  >
                                    선택
                                  </a>
                                </div>
                              </div>
                              <br />
                            </div>
                          ))
                        ) : (
                          <div>배송지 정보가 없습니다.</div>
                        )}
                      </div>
                      {/* 배송지 목록 */}
                      {/* 하단 페이징  */}

                      <div className='ship-paging'>
                        <button type='button' className='page is-active'>
                          1
                        </button>
                      </div>
                      {/* 하단 페이징  */}
                    </div>
                  </Modal>
                  {/* 배송지 목록 모달 끝 */}
                  {/*★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★*/}
                  {/* 신규 배송지 등록 모달 시작 */}
                  <Modal
                    isOpen={newShipModalIsOpen}
                    onRequestClose={newShipModalClose}
                    style={ShipStyles}
                    contentLabel='newShipModal'
                  >
                    <div className='p-4'>
                      <h4 className='mb-4'>신규 배송지</h4>
                      <form ref={formRef}>
                        {/* 배송지 시작 */}
                        <div className='row g-3'>
                          {/* 받는사람 시작 */}
                          <div className='col-12'>
                            <label htmlFor='username' className='form-label'>
                              받는사람
                            </label>
                            <div className='input-group'>
                              <input
                                type='text'
                                className='form-control'
                                id='username'
                                name='sname'
                                value={saveShip.sname}
                                onChange={onChangeSaveShip}
                              />
                            </div>
                          </div>
                          {/* 받는사람 끝 */}
                          {/* 휴대폰 번호 시작 */}
                          <div className='col-12'>
                            <label htmlFor='hp' className='form-label'>
                              휴대전화
                            </label>
                            <div className='input-group'>
                              <input
                                type='number'
                                className='form-control'
                                id='firstPartSS'
                                value={firstPartSS}
                                readOnly
                              />
                              <span style={{ lineHeight: "3" }}>&nbsp;-&nbsp;</span>
                              <input
                                type='number'
                                className='form-control'
                                name='secondPartSS'
                                value={secondPartSS}
                                onChange={onChangeSaveShip}
                                onInput={(e) => {
                                  if (e.target.value.length > 4) {
                                    e.target.value = e.target.value.slice(0, 4);
                                  }
                                }}
                              />
                              <span style={{ lineHeight: "3" }}>&nbsp;-&nbsp;</span>
                              <input
                                type='number'
                                className='form-control'
                                name='thirdPartSS'
                                value={thirdPartSS}
                                onChange={onChangeSaveShip}
                                onInput={(e) => {
                                  if (e.target.value.length > 4) {
                                    e.target.value = e.target.value.slice(0, 4);
                                  }
                                }}
                              />
                            </div>
                          </div>
                          {/* 휴대폰 번호  끝 */}
                          {/* 주소 시작 */}
                          <div style={{ alignItems: "center" }}>
                            <label
                              htmlFor='address'
                              className='form-label'
                              style={{
                                marginRight: "40px",
                                marginBottom: "10px",
                              }}
                            >
                              배송지 유형
                            </label>
                            <input
                              type='text'
                              className='form-control'
                              id='shiptype'
                              name='stype'
                              value={saveShip.stype}
                              onChange={onChangeSaveShip}
                              maxLength='10'
                            />
                          </div>
                          <div style={{ display: "flex", alignItems: "center" }}>
                            <label
                              htmlFor='address'
                              className='form-label'
                              style={{ marginRight: "40px", marginBottom: "0" }}
                            >
                              주소
                            </label>
                          </div>
                          <div className='input-group'>
                            <input
                              type='number'
                              className='form-control'
                              placeholder='우편번호'
                              id='addrPost'
                              name='szip'
                              value={saveShip.szip}
                              readOnly
                            />
                            <button
                              className='input-group-text '
                              onClick={(e) => {
                                handleClick();
                                e.preventDefault();
                              }}
                            >
                              주소찾기
                            </button>
                          </div>
                          <div className='form-group'>
                            <input
                              type='text'
                              className='form-control'
                              placeholder='주소'
                              id='addr'
                              name='saddr'
                              value={saveShip.saddr}
                              onChange={onChangeSaveShip}
                            />
                          </div>
                          {/* 주소 끝 */}
                          <div className='form-group'>
                            <select
                              className='form-select'
                              id='sReq'
                              style={{ color: "rgba(33, 37, 41, 0.75)" }}
                              onChange={optionChange}
                            >
                              <option value=''>&lt; 배송요청사항 (선택사항) &gt; </option>
                              <option value='배송 전에 미리 연락바랍니다'>배송 전에 미리 연락바랍니다</option>
                              <option value='부재 시 경비실에 맡겨주세요.'>부재 시 경비실에 맡겨주세요.</option>
                              <option value='부재 시 문 앞에 놓아주세요.'>부재 시 문 앞에 놓아주세요.</option>
                              <option value='빠른 배송 부탁드립니다.'>빠른 배송 부탁드립니다.</option>
                              <option value='택배함에 보관해 주세요.'>택배함에 보관해 주세요.</option>
                              <option value='sReq-input'>직접 입력하기</option>
                            </select>
                            {showTextarea && (
                              <textarea
                                id='sReqTextarea '
                                className='sReqTextarea form-control'
                                placeholder='최대 50자까지 가능합니다.'
                                maxLength='50'
                                style={{ marginTop: "10px" }}
                                onChange={handleTextareaChange}
                              />
                            )}
                          </div>
                          <div className='btn-group'>
                            <a className='coup-table-btn btn-delete' onClick={newShipModalClose}>
                              취소
                            </a>
                            <a className='coup-table-btn btn-choose' onClick={ShipSaveAction}>
                              등록
                            </a>
                          </div>
                        </div>
                      </form>
                    </div>
                  </Modal>
                  {/* 신규 배송지 등록 모달 끝 */}
                  {/*★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★*/}
                  {/* 배송지 수정 모달 시작 */}
                  <Modal
                    isOpen={updateShipModalIsOpen}
                    onRequestClose={updateShipModalClose}
                    style={ShipStyles}
                    contentLabel='updateShipModal'
                  >
                    <div className='p-4'>
                      <h4 className='mb-4'>배송지 수정</h4>
                      <form ref={formRef}>
                        {/* 배송지 시작 */}
                        <div className='row g-3'>
                          {/* 받는사람 시작 */}
                          <div className='col-12'>
                            <label htmlFor='username' className='form-label'>
                              받는사람
                            </label>
                            <div className='input-group'>
                              <input
                                type='text'
                                className='form-control'
                                id='username'
                                name='sname'
                                value={updateShip.sname}
                                onChange={onChangeUpdateShip}
                              />
                            </div>
                          </div>
                          {/* 받는사람 끝 */}
                          {/* 휴대폰 번호 시작 */}
                          <div className='col-12'>
                            <label htmlFor='hp' className='form-label'>
                              휴대전화
                            </label>
                            <div className='input-group'>
                              <input
                                type='number'
                                className='form-control'
                                id='firstPart'
                                name='firstPart'
                                value={firstPart}
                                readOnly
                              />
                              <span style={{ lineHeight: "3" }}>&nbsp;-&nbsp;</span>
                              <input
                                type='number'
                                className='form-control'
                                id='updatehp2'
                                name='secondPart'
                                value={secondPart}
                                onChange={(e) => setSecondPart(e.target.value)}
                                onInput={(e) => {
                                  if (e.target.value.length > 4) {
                                    e.target.value = e.target.value.slice(0, 4);
                                  }
                                }}
                              />
                              <span style={{ lineHeight: "3" }}>&nbsp;-&nbsp;</span>
                              <input
                                type='number'
                                className='form-control'
                                id='updatehp3'
                                name='thirdPart'
                                value={thirdPart}
                                onChange={(e) => setThirdPart(e.target.value)}
                                onInput={(e) => {
                                  if (e.target.value.length > 4) {
                                    e.target.value = e.target.value.slice(0, 4);
                                  }
                                }}
                              />
                            </div>
                          </div>
                          {/* 휴대폰 번호  끝 */}

                          {/* 주소 시작 */}
                          <div style={{ alignItems: "center" }}>
                            <label
                              htmlFor='address'
                              className='form-label'
                              style={{
                                marginRight: "40px",
                                marginBottom: "10px",
                              }}
                            >
                              배송지 유형
                            </label>
                            <input
                              type='text'
                              className='form-control'
                              id='shiptype'
                              name='stype'
                              value={updateShip.stype}
                              onChange={onChangeUpdateShip}
                              maxLength='10'
                            />
                          </div>
                          <div style={{ display: "flex", alignItems: "center" }}>
                            <label
                              htmlFor='address'
                              className='form-label'
                              style={{ marginRight: "40px", marginBottom: "0" }}
                            >
                              주소
                            </label>
                          </div>
                          <div className='input-group'>
                            <input
                              type='number'
                              className='form-control'
                              id='addrPost'
                              name='szip'
                              value={updateShip.szip}
                              readOnly
                            />
                            <button
                              className='input-group-text '
                              onClick={(e) => {
                                handleClick();
                                e.preventDefault();
                              }}
                            >
                              주소찾기
                            </button>
                          </div>
                          <div className='form-group'>
                            <input
                              type='text'
                              className='form-control'
                              id='addr'
                              name='saddr'
                              value={updateShip.saddr}
                              onChange={onChangeUpdateShip}
                            />
                          </div>
                          <div className='form-group'>
                            <select
                              className='form-select'
                              id='sReq'
                              style={{ color: "rgba(33, 37, 41, 0.75)" }}
                              onChange={optionChange}
                            >
                              <option value='sreq'>{updateShip.sreq}</option>
                              <option value=''>&lt; 배송요청사항 (선택사항) &gt;</option>
                              <option value='배송 전에 미리 연락바랍니다'>배송 전에 미리 연락바랍니다</option>
                              <option value='부재 시 경비실에 맡겨주세요.'>부재 시 경비실에 맡겨주세요.</option>
                              <option value='부재 시 문 앞에 놓아주세요.'>부재 시 문 앞에 놓아주세요.</option>
                              <option value='빠른 배송 부탁드립니다.'>빠른 배송 부탁드립니다.</option>
                              <option value='택배함에 보관해 주세요.'>택배함에 보관해 주세요.</option>
                              <option value='sReq-input'>직접 입력하기</option>
                            </select>
                            {showTextarea && (
                              <textarea
                                id='sReqTextarea '
                                className='sReqTextarea form-control'
                                placeholder='최대 50자까지 가능합니다.'
                                maxLength='50'
                                style={{ marginTop: "10px" }}
                                onChange={handleTextareaChange}
                              />
                            )}
                          </div>
                          <div className='btn-group'>
                            <a className='coup-table-btn btn-delete' onClick={updateShipModalClose}>
                              취소
                            </a>
                            <a className='coup-table-btn btn-choose' onClick={ShipModifyAction}>
                              수정완료
                            </a>
                          </div>
                        </div>
                      </form>
                    </div>
                  </Modal>
                  {/* 배송지 수정 모달 끝 */}
                  {/*★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★*/}
                  <div className='form-group'>
                    <input
                      type='text'
                      className='form-control'
                      id='stype'
                      name='ostype'
                      placeholder='배송지유형'
                      value={order.ostype}
                      onChange={handleChangeOrderSaveForm}
                    />
                  </div>
                  <div className='input-group'>
                    <input
                      type='number'
                      className='form-control'
                      id='addrPost'
                      name='ozip'
                      placeholder='우편번호'
                      value={order.ozip}
                      onChange={handleChangeOrderSaveForm}
                    />
                    <button
                      className='input-group-text '
                      onClick={(e) => {
                        handleClick();
                        e.preventDefault();
                      }}
                    >
                      주소찾기
                    </button>
                  </div>
                  <div className='form-group'>
                    <input
                      type='text'
                      className='form-control'
                      id='addr'
                      name='oaddr'
                      placeholder='주소'
                      value={order.oaddr}
                      onChange={handleChangeOrderSaveForm}
                    />
                    <p style={{marginTop:"5px",fontSize:"13px",color:"#556B2F"}}>&nbsp;* 상세 주소까지 기재 바랍니다.</p>
                  </div>
                  <div className='form-group'>
                    <select
                      className='form-select'
                      id='sReq'
                      name='osreq'
                      style={{ color: "rgba(33, 37, 41, 0.75)" }}
                      onChange={handleChange}
                    >
                      <option value=''>&lt; 배송요청사항 (선택사항) &gt; </option>
                      <option value='배송 전에 미리 연락바랍니다'>배송 전에 미리 연락바랍니다</option>
                      <option value='부재 시 경비실에 맡겨주세요.'>부재 시 경비실에 맡겨주세요.</option>
                      <option value='부재 시 문 앞에 놓아주세요.'>부재 시 문 앞에 놓아주세요.</option>
                      <option value='빠른 배송 부탁드립니다.'>빠른 배송 부탁드립니다.</option>
                      <option value='택배함에 보관해 주세요.'>택배함에 보관해 주세요.</option>
                      <option value='sReq-input'>직접 입력하기</option>
                    </select>
                    {showTextarea && (
                      <textarea
                        id='sReqTextarea '
                        className='sReqTextarea form-control'
                        placeholder='최대 50자까지 가능합니다.'
                        maxLength='50'
                        name='osreq'
                        style={{ marginTop: "10px", height: "70px" }}
                        onChange={handleChangeOrderSaveForm}
                      />
                    )}
                  </div>
                  {/* 선택사항 ( 체크박스) 시작*/}

                  <div className='form-check' style={{ marginLeft: "10px" }}>
                    <input
                      type='checkbox'
                      className='form-check-input'
                      id='same-address'
                      checked={isDefaultChecked}
                      onChange={handleCheckboxChange}
                    />
                    <label className='form-check-label' htmlFor='same-address'>
                      기본 배송지 불러오기
                    </label>
                  </div>
                  {/* 선택사항 ( 체크박스) 끝 */}
                  {/* 주문요청사항 시작 */}
                  <div className='form-group'>
                    <label htmlFor='orderReqTextarea' className='form-label' style={{ marginTop: "10px" }}>
                      주문요청사항
                    </label>
                    <textarea
                      id='orderReqTextarea'
                      name='oreq'
                      className='orderReqTextarea form-control'
                      placeholder='최대 100자까지 가능합니다.'
                      maxLength='100'
                      style={{ height: "100px" }}
                      value={order.oreq}
                      onChange={handleChangeOrderSaveForm}
                    />
                  </div>
                  {/* 주문요청사항 끝*/}
                  {/* 주소 끝 */}
                </div>
                {/* 배송지 끝 */}
                {/* 상품정보시작 */}
                <hr className='my-4' />
                <h4 className='mb-3'>상품정보</h4>
                <table className='table order-table'>
                  <thead>
                    <tr>
                      <th scope='col'>상품정보</th>
                      <th scope='col'>상품옵션</th>
                      <th scope='col'>가격</th>
                      <th scope='col'></th>
                    </tr>
                  </thead>

                  {/* 상품1개 */}
                  <tbody className='order-product-tbody'>
                    {productOption &&
                      productOption.length > 0 &&
                      productOption.map((option) => (
                        <tr key={option.poNo}>
                          <td>
                            <div className='media'>
                              <div className='d-flex'>
                                <img
                                  /*  src={"./image/" + option.product.pimage} */
                                  src={`http://localhost:8080/product/view/${option.product.pimage}`}
                                  alt='Product Image'
                                />
                                <div className='media-body p-2'>
                                  <p>{option.product.pname}</p>
                                </div>
                              </div>
                            </div>
                          </td>
                          <td>적용기간 {option.poName}</td>
                          <td>
                            {/* option.product의 가격이 존재하고 유효할 때 */}
                            {option.product && option.product.pprice
                              ? // option.product의 가격을 표시하고, 만약 option.pOPrice가 존재한다면 더하여 표시
                                (option.product.pprice + (option.poPrice ? option.poPrice : 0)).toLocaleString()
                              : ""}
                            원
                          </td>
                          <td>
                            {/* <button
                          className="buttTxt"
                          onClick={(e) => {
                            e.preventDefault();
                          }}
                        >
                          삭제

                        </button> */}
                          </td>
                        </tr>
                      ))}
                  </tbody>
                  {/* 상품1개 끝 */}

                  <tfoot className='order-product-tfoot' style={{ lineHeight: "50px" }}>
                    <tr>
                      <td></td>
                      {/* <td></td> */}
                      <td>총 주문 금액</td>
                      <td>{originalOPrice.toLocaleString()}원</td>
                    </tr>
                  </tfoot>
                </table>

                {/* 상품정보 끝 */}

                {/* 결제 시작 */}

                <hr className='my-4' />
                <h4 className='mb-3'>결제</h4>
                {/* 결제 UI, 이용약관 UI 영역 */}
                <div id='payment-widget' />
                <div id='agreement' />
                <hr className='my-4' />
                {/* 제출 버튼 */}
                <button
                  className='w-100 btn btn-primary btn-lg'
                  onClick={(e) => {
                    handlePaymentRequest();
                    e.preventDefault();
                  }}
                >
                  결제하기
                </button>

                {/* 제출 버튼 */}
              </form>
            </div>
          </div>
        </main>
      </div>
    </>
  );
}

export default Order;

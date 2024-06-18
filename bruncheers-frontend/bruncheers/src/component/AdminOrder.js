import React, { useEffect, useState } from "react";
import * as fetchAdmin from "../api/fetchAdmin";
import Modal from "react-modal";
import { userState } from "../recoil/atom";
import { useRecoilState } from "recoil";
import { useNavigate } from "react-router";

function AdminOrder() {
  const [userLoginState, setUserLoginState] = useRecoilState(userState);
  const [orderList, setOrderList] = useState([]);
  const [selectedOrder, setSelectedOrder] = useState(null);
  const [selectedRecipient, setSelectedRecipient] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const resultJsonObject = await fetchAdmin.listAllOrders();
        if (resultJsonObject && resultJsonObject.data) {
          setOrderList(resultJsonObject.data);
        }
      } catch (error) {
        navigate("/error");
      }
    };

    fetchOrders();
  }, []);

  /* 모달 공통 */
  Modal.setAppElement("#container");

  /* 주문자 상세정보 모달 */
  const OrderUserStyles = {
    content: {
      top: "50%",
      left: "50%",
      right: "auto",
      bottom: "auto",
      marginRight: "-50%",
      transform: "translate(-50%, -50%)",
      width: "500px",
      height: "auto",
    },
  };

  const [orderUserModalIsOpen, setOrderUserModalIsOpen] = useState(false);

  function OpenOrderUserModal(order) {
    setSelectedOrder(order);
    setOrderUserModalIsOpen(true);
  }
  function CloseOrderUserModal() {
    setOrderUserModalIsOpen(false);
  }

  /* 수령자 상세정보 모달 */
  const recipientStyles = {
    content: {
      top: "50%",
      left: "50%",
      right: "auto",
      bottom: "auto",
      marginRight: "-50%",
      transform: "translate(-50%, -50%)",
      width: "500px",
      height: "auto",
    },
  };

  const [recipientModalIsOpen, setRecipientModalIsOpen] = useState(false);

  function OpenRecipientModal(order) {
    setSelectedRecipient(order);
    setRecipientModalIsOpen(true);
  }
  function CloseRecipientModal() {
    setRecipientModalIsOpen(false);
  }

  // orderList 데이터를 병합된 형태로 변환
  const mergeOrders = (orderList) => {
    const mergedOrders = [];
    const seen = new Map();

    orderList.forEach((order) => {
      if (!seen.has(order.ono)) {
        seen.set(order.ono, { ...order, orderItemDtoList: [] });
        mergedOrders.push(seen.get(order.ono));
      }
      seen.get(order.ono).orderItemDtoList.push(...order.orderItemDtoList);
    });

    return mergedOrders;
  };

  const mergedOrderList = mergeOrders(orderList);

  function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
  }

  return (
    <>
      <div className="p-3">
        <h2 className="productTitle">전체 주문 내역</h2>
        <div className="p-container">
          <div className="category-group">
            <div className="category-header">
              <h6 className="catTitle">전체: {orderList.length}건 조회</h6>
            </div>
            <div className="product-list-r">
              <table className="product-table">
                <thead>
                  <tr>
                    <th>주문번호</th>
                    <th>주문일</th>
                    <th>주문상품</th>
                    <th>상품금액</th>
                    <th>할인금액</th>
                    <th>주문자</th>
                    <th>수령자</th>
                    <th>총주문액</th>
                    <th>결제방법</th>
                  </tr>
                </thead>
                <tbody>
                  {mergedOrderList.length > 0 ? (
                    mergedOrderList.map((order) => {
                      const rowspan = order.orderItemDtoList.length;
                      return order.orderItemDtoList.map((item, index) => (
                        <tr key={`${order.ono}-${index}`}>
                          {index === 0 && (
                            <>
                              <td rowSpan={rowspan}>{order.ono}</td>
                              <td rowSpan={rowspan}>
                                {order.odate.substring(0, 10)}
                              </td>
                            </>
                          )}
                          <td>{item.productOption.product.pname}</td>
                          <td>{numberWithCommas(item.productOption.product.pprice)}</td>
                          {index === 0 && (
                            <>
                              <td rowSpan={rowspan}>{order.odiscountprice}</td>
                              <td rowSpan={rowspan}>
                                <div
                                  className="cursor"
                                  onClick={() => OpenOrderUserModal(order)}
                                >
                                  {order.userName}
                                </div>
                              </td>
                              <td rowSpan={rowspan}>
                                <div
                                  className="cursor"
                                  onClick={() => OpenRecipientModal(order)}
                                >
                                  {order.oname}
                                </div>
                              </td>
                              <td rowSpan={rowspan}>{numberWithCommas(order.oprice)}</td>
                              <td rowSpan={rowspan}> {order.paType}</td>
                            </>
                          )}
                        </tr>
                      ));
                    })
                  ) : (
                    <tr>
                      <td
                        colSpan="9"
                        style={{
                          textAlign: "center",
                          padding: "50px",
                        }}
                      >
                        주문내역이 존재하지 않습니다.
                      </td>
                    </tr>
                  )}
                </tbody>
              </table>
              {/**************************** 주문자 정보 모달 시작 ****************************/}
              <Modal
                isOpen={orderUserModalIsOpen}
                onRequestClose={CloseOrderUserModal}
                style={OrderUserStyles}
                contentLabel="orderUserModal"
              >
                <div className="p-4">
                  <h5 className="mb-4">
                    {selectedOrder && `${selectedOrder ? selectedOrder.userName : ""}님의 회원정보`}
                  </h5>
                  <div className="row g-3">
                    <div className="col-12">
                      <label htmlFor="username" className="form-label">
                        이름
                      </label>
                      <div className="input-group has-validation">
                        <input
                          type="text"
                          className="form-control"
                          id="username"
                          name="sname"
                          value={selectedOrder ? selectedOrder.userName : ""}
                          readOnly
                        />
                      </div>
                    </div>
                    <div className="col-12">
                      <label htmlFor="username" className="form-label">
                        이메일
                      </label>
                      <div className="input-group has-validation">
                        <input
                          type="text"
                          className="form-control"
                          id="username"
                          name="sname"
                          value={selectedOrder ? selectedOrder.userEmail : ""}
                          readOnly
                        />
                      </div>
                    </div>
                    <div className="col-12">
                      <label htmlFor="hp" className="form-label">
                        휴대전화
                      </label>
                      <div className="input-group has-validation">
                        <input
                          type="text"
                          className="form-control"
                          id="firstPart"
                          value={
                            selectedOrder && selectedOrder.userHp
                              ? selectedOrder.userHp.substring(0, 3)
                              : ""
                          }
                          readOnly
                        />
                        <span style={{ lineHeight: "3" }}>&nbsp;-&nbsp;</span>
                        <input
                          type="text"
                          className="form-control"
                          id="updatehp2"
                          name="secondPart"
                          value={
                            selectedOrder && selectedOrder.userHp
                              ? selectedOrder.userHp.substring(4, 8)
                              : ""
                          }
                          readOnly
                        />
                        <span style={{ lineHeight: "3" }}>&nbsp;-&nbsp;</span>
                        <input
                          type="text"
                          className="form-control"
                          id="updatehp3"
                          name="thirdPart"
                          value={
                            selectedOrder && selectedOrder.userHp
                              ? selectedOrder.userHp.substring(9, 13)
                              : ""
                          }
                          readOnly
                        />
                      </div>
                    </div>
                    <div style={{ alignItems: "center" }}>
                      <label
                        htmlFor="address"
                        className="form-label"
                        style={{
                          marginRight: "40px",
                          marginBottom: "10px",
                        }}
                      >
                        주문
                      </label>
                      <table className="orderbyuser-table">
                        <thead>
                          <tr>
                            <th>주문번호</th>
                            <th>주문일시</th>
                            <th>주문상품</th>
                          </tr>
                        </thead>
                        <tbody>
                          {selectedOrder &&
                          selectedOrder.orderItemDtoList &&
                          selectedOrder.orderItemDtoList.length > 0 ? (
                            selectedOrder.orderItemDtoList.map(
                              (orderItem, index) => (
                                <tr key={index}>
                                  {index === 0 && (
                                    <>
                                      <td
                                        style={{ verticalAlign: "middle" }}
                                        rowSpan={
                                          selectedOrder.orderItemDtoList.length
                                        }
                                      >
                                        {selectedOrder.ono}
                                      </td>
                                      <td
                                        style={{ verticalAlign: "middle" }}
                                        rowSpan={
                                          selectedOrder.orderItemDtoList.length
                                        }
                                      >
                                        {selectedOrder.odate &&
                                          selectedOrder.odate.substring(0, 10)}
                                      </td>
                                    </>
                                  )}
                                  <td>
                                    {orderItem.productOption.product.pname}
                                  </td>
                                </tr>
                              )
                            )
                          ) : (
                            <tr>
                              <td colSpan="3">주문 상품이 없습니다.</td>
                            </tr>
                          )}
                        </tbody>
                      </table>
                    </div>
                    <div className="btn-group">
                      <div
                        className="coup-table-btn btn-delete"
                        onClick={CloseOrderUserModal}
                      >
                        닫기
                      </div>
                    </div>
                  </div>
                </div>
              </Modal>
              {/***************************** 주문자 정보 모달 끝 *****************************/}
              {/***************************** 수령자 정보 모달 시작 *****************************/}
              <Modal
                isOpen={recipientModalIsOpen}
                onRequestClose={CloseRecipientModal}
                style={recipientStyles}
                contentLabel="recipientModal"
              >
                <div className="p-4">
                  <h5 className="mb-4">
                    수령자 {selectedRecipient ? selectedRecipient.oname : ""}
                    님의 상세 배송지
                  </h5>
                  <div className="row g-3">
                    <div className="col-12">
                      <label
                        htmlFor="username"
                        className="
                                          form-label"
                      >
                        받는사람
                      </label>
                      <div className="input-group has-validation">
                        <input
                          type="text"
                          className="form-control"
                          id="username"
                          name="sname"
                          value={
                            selectedRecipient ? selectedRecipient.oname : ""
                          }
                          readOnly
                        />
                      </div>
                    </div>
                    <div className="col-12">
                      <label htmlFor="hp" className="form-label">
                        휴대전화
                      </label>
                      <div className="input-group has-validation">
                        <input
                          type="text"
                          className="form-control"
                          id="firstPart"
                          value={
                            selectedRecipient && selectedRecipient.ophone
                              ? selectedRecipient.ophone.substring(0, 3)
                              : ""
                          }
                          readOnly
                        />
                        <span style={{ lineHeight: "3" }}>&nbsp;-&nbsp;</span>
                        <input
                          type="text"
                          className="form-control"
                          id="updatehp2"
                          name="secondPart"
                          value={
                            selectedRecipient && selectedRecipient.ophone
                              ? selectedRecipient.ophone.substring(4, 8)
                              : ""
                          }
                          readOnly
                          required
                        />
                        <span style={{ lineHeight: "3" }}>&nbsp;-&nbsp;</span>
                        <input
                          type="text"
                          className="form-control"
                          id="updatehp3"
                          name="thirdPart"
                          value={
                            selectedRecipient && selectedRecipient.ophone
                              ? selectedRecipient.ophone.substring(9, 13)
                              : ""
                          }
                          readOnly
                          required
                        />
                      </div>
                    </div>
                    <div
                      style={{
                        display: "flex",
                        alignItems: "center",
                      }}
                    >
                      <label
                        htmlFor="address"
                        className="form-label"
                        style={{
                          marginRight: "40px",
                          marginBottom: "0",
                        }}
                      >
                        주소
                      </label>
                    </div>
                    <div className="input-group has-validation">
                      <input
                        type="text"
                        className="form-control"
                        id="addrPost"
                        name="szip"
                        value={selectedRecipient ? selectedRecipient.ozip : ""}
                        readOnly
                        required
                      />
                    </div>
                    <div className="form-group">
                      <input
                        type="text"
                        className="form-control"
                        id="addr"
                        name="saddr"
                        value={selectedRecipient ? selectedRecipient.oaddr : ""}
                        readOnly
                        required
                      />
                    </div>
                    <div
                      style={{
                        alignItems: "center",
                      }}
                    >
                      <label
                        htmlFor="address"
                        className="form-label"
                        style={{
                          marginRight: "40px",
                          marginBottom: "10px",
                        }}
                      >
                        주문 요청사항
                      </label>
                      <input
                        type="text"
                        className="form-control"
                        id="shiptype"
                        name="stype"
                        value={selectedRecipient ? selectedRecipient.oreq : ""}
                        readOnly
                        required
                      />
                    </div>
                    <div
                      style={{
                        alignItems: "center",
                      }}
                    >
                      <label
                        htmlFor="address"
                        className="form-label"
                        style={{
                          marginRight: "40px",
                          marginBottom: "10px",
                        }}
                      >
                        배송 요청사항
                      </label>
                      <input
                        type="text"
                        className="form-control"
                        id="shiptype"
                        name="stype"
                        value={selectedRecipient ? selectedRecipient.osreq : ""}
                        readOnly
                        required
                      />
                    </div>
                    <div className="btn-group">
                      <div
                        className="coup-table-btn btn-delete"
                        onClick={CloseRecipientModal}
                      >
                        닫기
                      </div>
                    </div>
                  </div>
                </div>
              </Modal>
              {/***************************** 수령자 정보 모달 끝 *****************************/}
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default AdminOrder;

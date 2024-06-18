import * as fetchAdmin from "../api/fetchAdmin";
import * as fetchProduct from "../api/fetchProduct";
import React, { useEffect, useState } from 'react'
import "../css/admin.css";
import "../assets/brand/admin.svg"
import { useNavigate } from "react-router";

function AdminMain() {

  const [orderList, setOrderList] = useState([]);
  const [users, setUsers] = useState([]);
  const [products, setProducts] = useState([]);
  const navigate = useNavigate();

/* 주문리스트 받아오기 */
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

/* 회원리스트 받아오기 */
useEffect(() => {
  (async () => {
    try {
      const responseJsonObjcet = await fetchAdmin.listUser();
      setUsers(responseJsonObjcet.data || []);
    } catch (error) {
        navigate("/error");
        setUsers([]);
    }
  })();
}, []);

  /* 상품리스트 받아오기 */
  useEffect(() => {
    (async () => {
      try {
        const responseJsonObjcet = await fetchProduct.listProduct();
        setProducts(responseJsonObjcet.data || []);
      } catch (error) {
        navigate("/error");
        setUsers([]);
      }
    })();
  }, []);


  return (
    <>
  <div className="d-flex justify-content-center align-items-center flex-column pt-3 pb-2 mb-3 border-bottom">
    <h5 className="h4 mb-3"> 브런치를 즐기는 브런치어, 그들을 위하여, <br/>Bruncheers With Admin</h5>
      <div className="btn-group me-2">
      </div>
  </div>

  <div className="d-flex justify-content-center">
    <div className="box-wrapper">
    {users && products && orderList && (
    <>
      <div className="shadow-top-left">브런치얼스의 <br/>회원 수: {users.length}명</div>
      <div className="shadow-top-left">브런치얼스의 <br/>상품 수: {products.length}건</div>
      <div className="shadow-top-left">브런치얼스의 <br/>주문 수: {orderList.length}건 </div>
    </>
  )}
    </div>
  </div>
</>

  )
}


export default AdminMain;
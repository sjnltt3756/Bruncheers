import { Route, Routes, useNavigate } from "react-router-dom";

import "./css/styles.css";
/* import "./assets/js/color-modes-xxxxx.js"; */
import "./assets/dist/css/bootstrap.min.css";
import "./assets/dist/js/bootstrap.bundle.min.js";


import Header from "./layout/Header.js";
import Navigation from "./layout/Navigation";
import Footer from "./layout/Footer";
import CartList from "./component/CartList";
import Order from "./component/Order";
import OrderList from "./component/OrderList.js";
import OrderDetail from "./component/OrderDetail.js";
import { BCMain } from "./component/BCMain";
import Login from "./component/Login";
import Register from "./component/Register.js";
import MyPage from "./component/MyPage";
import Product from "./component/Product";

import ProductDetail from "./component/ProductDetail";
import ProductInfo from "./component/ProductInfo";
import Review from "./component/Review";
import Guide from "./component/Guide";
import Board from "./component/BoardList.js";
import BoardWriteForm from "./component/BoardWriteForm";
import BoardView from "./component/BoardView";
import BoardReply from "./component/BoardReply";
import BoardModify from "./component/BoardModify";

import BoardList from "./component/BoardList.js";
import ReviewWriteForm from "./component/ReviewWriteForm.js";
import React, { useEffect, useState } from "react";
import AdminDashBoard from "./component/AdminDashBoard.js";
import AdminMain from "./component/AdminMain.js";
import Error from "./component/Error.js";
import RequireAdmin from "./component/RequireAdmin.js";
import OrderSuccess from "./component/OrderSuccess.js";


function App() {
 
  const [productNumber, setProductNumber] = useState([]);
  
  return (
    <>
        <Routes>
          <Route path="/login" element={<><Header/><Login/><Footer/></>}/>
          <Route path='/register' element={<><Header/><Register /><Footer /></>}/>
          <Route path="/mypage" element={<><Header/><MyPage/><Footer/></>}/>

              {/* 카테고리 관련 라우터 */}
              <Route index element={<><Header/><Navigation/><BCMain/><Footer/></>}/>
              <Route path="/main" element={<><Header/><Navigation/><BCMain/><Footer/></>}/>
              <Route path="/product/catNo/:catNo" element={<><Header/><Navigation/><Product/><Footer/></>}/>
              <Route path="/boards" element={<><Header/><Navigation/><BoardList/><Footer/></>}/>
              <Route path="/boards/:page_no" element={<><Header/><Navigation/><BoardList/><Footer/></>}/>
              <Route path="/board_write_form" element={<><Header/><Navigation/><BoardWriteForm/><Footer/></>}/>
              <Route path="/board_view/:board_no" element={<><Header/><Navigation/><BoardView/><Footer/></>}/>
              <Route path="/board_reply" element={<><Header/><Navigation/><BoardReply/><Footer/></>}/>
              <Route path="/board_modify/:board_no" element={<><Header/><Navigation/><BoardModify/><Footer/></>}/>
              <Route path="/product_detail/:pNo/*" element={<><Header/><Navigation/><ProductDetail/><Footer/></>}/>
              {/* <Route path="/product_info" element={<><Header/><Navigation/><ProductDetail/><ProductInfo/><Footer/></>}/>
              <Route path="/review" element={<><Header/><Navigation/><ProductDetail/><Review/><Footer/></>}/>
              <Route path="/guide" element={<><Header/><Navigation/><ProductDetail/><Guide/><Footer/></>}/> */}
              <Route path="/cart" element={ <><Header/><Navigation/><CartList/><Footer/></>}/>
              <Route path="/order" element={<><Header/><Navigation/><Order/><Footer/></>}/>
              <Route path="/order_list" element={ <><Header/><Navigation/><OrderList/><Footer/></>}/>
              <Route path="/review_write_form" element={ <ReviewWriteForm   productNumber={productNumber} setProductNumber={setProductNumber} />}/>
              <Route path="/order_detail/:oNo" element={ <><Header/><Navigation/><OrderDetail/><Footer/></>}/>
              <Route path="/admin/*" element={<><RequireAdmin><Header/><AdminDashBoard/></RequireAdmin></>}/>
              <Route path="/error" element={<Error/>}/>
              <Route path="/order_success" element={<><Header/><Navigation/><OrderSuccess/><Footer/></>}/>
              <Route path="*" element={<Error/>}></Route>
          </Routes>
    </>
  )};
export default App;
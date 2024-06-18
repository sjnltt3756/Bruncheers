import React, { useEffect } from "react";
import logo from "../image/logo/logo.png";
import adminLogo from "../image/logo/adminLogo.png";
import { Link, useNavigate } from "react-router-dom";
import "../css/styles.css";
import { useRecoilState, useResetRecoilState } from "recoil";
import { userState } from "../recoil/atom";
import * as userApi from "../api/fetchUser";
import { useCookies } from "react-cookie";

function Header() {
  const [userLoginState, setUserLoginState] = useRecoilState(userState);
  const resetUserLoginState = useResetRecoilState(userState);
  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  const handleLogout = () => {
    resetUserLoginState();
    localStorage.removeItem("token");
    localStorage.removeItem("recoil-persist");
    removeCookie("authorize_access_token");
    navigate("/login");
  };

  const [cookies, setCookie, removeCookie] = useCookies(["authorize_access_token"]);
  const kakaoToken = cookies.authorize_access_token;

  useEffect(() => {
    const kakaoLogin = async () => {
      const response = await userApi.kakaoLogin(kakaoToken);
      setUserLoginState({ data: response });
      localStorage.setItem("token", response.token);
    };
    if ((token || kakaoToken) && !userLoginState.data) {
      kakaoLogin();
    }
  }, []);

  const isAdmin = token && userLoginState.data.role === "ADMIN";
  const isUser = token && userLoginState.data.role === "USER";
  return (
    <div className='shopheader'>
      <div className='headerNavigation'>
        {isAdmin && (
          <>
            <ul>
              <li>
                <Link to='/mypage'>{userLoginState.data.userNickname} 님 (Admin)</Link>
              </li>
              <li>
                <Link to='/admin'>ADMIN</Link>
              </li>
              <li>
                <Link to='/main'>SHOP</Link>
              </li>
              <li>
                <Link to='/mypage'>MY PAGE</Link>
              </li>
              <li>
                <Link to='/' onClick={handleLogout}>
                  LOGOUT
                </Link>
              </li>
            </ul>
            <Link to={"/admin"}>
              <img src={adminLogo} />
            </Link>
          </>
        )}
        {isUser && (
          <>
            <ul>
              <li>
                <Link to='/mypage'>{userLoginState.data.userNickname} 님</Link>
              </li>
              <li>
                <Link to='/mypage'>MY PAGE</Link>
              </li>
              <li>
                <Link to='/cart'>CART</Link>
              </li>
              <li>
                <Link to='/order_list'>ORDERLIST</Link>
              </li>
              <li>
                <Link to='/' onClick={handleLogout}>
                  LOGOUT
                </Link>
              </li>

            </ul>
            <Link to={"/"}>
              <img src={logo} />
            </Link>
          </>
        )}
        {!token && (
          <>
            <ul>
              <li>
                <Link to='/login'>LOGIN</Link>
              </li>
              <li>
                <Link to='/register'>SIGN UP</Link>
                <div className='speech-bubble'>
                  <h2 className='headerH2'>10% Coupon</h2>
                </div>
              </li>
            </ul>
            <Link to={"/"}>
              <img src={logo} />
            </Link>
          </>
        )}
      </div>
    </div>
  );
}

export default Header;

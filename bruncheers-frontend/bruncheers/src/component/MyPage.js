import { Link, useNavigate } from "react-router-dom";
import "../css/form.css";
import React, { useEffect, useRef, useState } from "react";
import Modal from "react-modal";
import * as userApi from "../api/fetchUser";
import { useRecoilState, useResetRecoilState } from "recoil";
import { userState } from "../recoil/atom";
import * as responseStatusCode from "../api/responseStatusCode";
import * as responseMessage from "../api/responseMessage";
import { useCookies } from "react-cookie";
import { notify, Toast } from "../util/toast";
import Error from "./Error";

function MyPage() {
  const [userLoginState, setUserLoginState] = useRecoilState(userState);
  const [cookies, setCookie, removeCookie] = useCookies(["authorize_access_token"]);
  const resetUserLoginState = useResetRecoilState(userState);
  const navigate = useNavigate();
  const formRef = useRef();
  const [nicknameChecked, setNicknameChecked] = useState(false); // 닉네임 중복 확인 상태
  const [userData, setUserData] = useState({
    userNo: "",
    userEmail: "",
    userPw: "",
    userNewPw: "",
    userNewPw2: "",
    userNickname: "",
    userBirth: "",
    userHp: "",
    userGender: "",
  });

  useEffect(() => {
    if (!userLoginState.data || !userLoginState.data.userNo) {
      navigate("/error");
    } else {
      userApi.userView(userLoginState.data.userNo).then((fetchedUserData) => {
        setUserData(fetchedUserData.data);
      });
    }
  }, []);

  const BCMypageModifyAction = async (e) => {
    e.preventDefault();
    if (!nicknameChecked) {
      notify("warning", "닉네임 중복 확인을 해주세요.");
      formRef.current.userNickname.focus();
      return;
    }
    if (formRef.current.userNewPw.value === "") {
      notify("warning", "새 비밀번호를 입력하세요.");
      formRef.current.userNewPw.focus();
      return;
    }
    if (formRef.current.userNewPw2.value === "") {
      notify("warning", "새 비밀번호를 입력하세요.");
      formRef.current.userNewPw2.focus();
      return;
    }
    if (formRef.current.userNewPw.value !== formRef.current.userNewPw2.value) {
      notify("warning", "새 비밀번호와 새 비밀번호 확인이 일치하지 않습니다.");
      formRef.current.userNewPw.focus();
      return;
    }

    userApi.userModifyAction(userData).then((responseJsonObject) => {
      switch (responseJsonObject.status) {
        case responseStatusCode.UPDATE_USER:
          notify("success", "수정 성공!");
          // 사용자 정보 업데이트
          setUserLoginState({ data: { ...userLoginState.data, ...userData } }); // 변경
          allModalClose();
          break;
        default:
          notify("error", "수정이 실패했습니다.");
          break;
      }
    });
  };

  const handleCheckNicknameUnique = async () => {
    if (formRef.current.userNickname.value === "") {
      notify("warning", "닉네임을 입력하십시요.");
      formRef.current.userNickname.focus();
      return;
    }
    const response = await userApi.checkNicknameUnique(userData.userNickname);
    const message =
      response.status === responseStatusCode.NICKNAME_AVAILABLE ? "사용 가능한 닉네임입니다." : "중복된 닉네임입니다.";
    const type = response.status === responseStatusCode.NICKNAME_AVAILABLE ? "success" : "warning";
    notify(type, message);
    setNicknameChecked(response.status === responseStatusCode.NICKNAME_AVAILABLE); // 중복 확인 상태 업데이트
  };

  const handleModifyChange = (e) => {
    const { name, value } = e.target;
    setUserData((prevUserData) => ({
      ...prevUserData,
      [name]: value,
    }));
  };

  const handleDeleteUser = async (e) => {
    e.preventDefault();
    await userApi.changeGhost(userLoginState.data.userNo);
    localStorage.removeItem("token");
    localStorage.removeItem("recoil-persist");
    removeCookie("authorize_access_token");
    resetUserLoginState();
    deleteModalClose();
    notify("success", "회원 탈퇴가 완료되었습니다.");
    navigate("/main");
  };

  /** 비밀번호 확인 기능시작 */

  const [password, setPassword] = useState({
    password: "",
  });

  const handleChangePassword = (e) => {
    setPassword({
      ...password,
      [e.target.name]: e.target.value,
    });
  };
  const handleSocial = (e) => {
    e.preventDefault();
    if (cookies.authorize_access_token) {
      notify("warning", "소셜 로그인 유저는 수정할 수 없습니다.");
    } else {
      pwModalOpen();
    }
  };
  const handlePassword = (e) => {
    e.preventDefault();
    userApi.verifyLogin(userLoginState.data.userNo, password.password).then((responseJsonObject) => {
      if (responseJsonObject.status === responseStatusCode.SUCCESS_PASSWORD_MISMATCH_USER) {
        userModifyModalOpen();
      } else {
        notify("warning", "비밀번호가 일치하지 않습니다.");
      }
    });
  };

  /** 비밀번호 확인 기능 끝 */

  /* 모달 공통 */
  Modal.setAppElement("#container");

  /* 비밀번호 입력 모달 */
  const CheackUserStyles = {
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

  /* 회원탈퇴 알림 모달 */
  const deleteUserStyles = {
    content: {
      top: "50%",
      left: "50%",
      right: "50%",
      bottom: "auto",
      marginRight: "-50%",
      transform: "translate(-50%, -50%)",
      width: "400px",
      height: "200px",
    },
  };

  /* 회원정보 수정 모달 */
  const UserModifyStyles = {
    content: {
      top: "50%",
      left: "50%",
      right: "50%",
      bottom: "auto",
      marginRight: "-50%",
      transform: "translate(-50%, -50%)",
      width: "600px",
      height: "800px",
    },
  };

  function pwModalOpen() {
    setPwIsOpen(true);
  }
  function pwModalClose() {
    setPwIsOpen(false);
  }
  function deleteModalOpen() {
    setDeleteIsOpen(true);
  }
  function deleteModalClose() {
    setDeleteIsOpen(false);
  }
  function userModifyModalOpen() {
    setUserModifyIsOpen(true);
  }
  // function userModifyModalClose() {
  //   setUserModifyIsOpen(false);
  // }

  function allModalClose() {
    setUserModifyIsOpen(false);
    setPwIsOpen(false);
  }

  const [pwModalIsOpen, setPwIsOpen] = React.useState(false);
  const [deleteModalIsOpen, setDeleteIsOpen] = React.useState(false);
  const [userModifyModalIsOpen, setUserModifyIsOpen] = React.useState(false);

  if (!userData) {
    return <div>Loading...</div>; // 사용자 정보가 로딩되지 않은 경우 로딩 표시
  }

  return (
    <>
      <div id='m-container'>
        <Toast />
        <h2 style={{ textAlign: "center", paddingBottom: "40px" }}>MY PAGE</h2>

        <form action='' method='post' className='mf'>
          <div className='m-name'>이메일</div>
          <div className='m-input-block'>
            <input type='text' className='input' value={userData.userEmail} readOnly />
          </div>
          <div className='m-name'>닉네임</div>
          <div className='m-input-block'>
            <input type='text' className='input' value={userData.userNickname} readOnly />
          </div>
          <div className='m-name'>비밀번호</div>
          <div className='m-input-block'>
            <input type='password' className='input' value={userData.userPw} readOnly />
          </div>
          <div className='m-name'>이름</div>
          <div className='m-input-block'>
            <input type='text' className='input' value={userData.userName} readOnly />
          </div>
          <div className='m-name'>성별</div>
          <div className='m-input-block'>
            <input type='text' value={userData.userGender} readOnly />
          </div>
          <div className='m-name'>생년월일</div>
          <div className='m-input-block'>
            <input type='text' className='input' value={userData.userBirth} readOnly />
          </div>
          <div className='m-name'>전화번호</div>
          <div className='m-input-block'>
            <input type='text' className='input' value={userData.userHp} readOnly />
          </div>
          <div style={{ marginTop: "30px" }}>
            <button className='modify__btn' onClick={handleSocial}>
              수정
            </button>
            {/* 비밀번호 입력 모달 시작 */}
            <Modal isOpen={pwModalIsOpen} onRequestClose={pwModalClose} style={CheackUserStyles} contentLabel='PwModal'>
              <div id='m-container' style={{ marginTop: "100px", textAlign: "center" }}>
                <ul className='links'>
                  <li className='non' style={{ fontSize: "14px", fontWeight: "bold" }}>
                    현재 비밀번호를 입력해주세요
                  </li>
                </ul>
                <form action='' method='post' className='mf'>
                  <div className='input__block'>
                    <input
                      type='password'
                      placeholder='비밀번호'
                      className='input'
                      name='password'
                      onChange={handleChangePassword}
                      value={password.password}
                    />
                  </div>
                  <div style={{ display: "flex", justifyContent: "center", marginTop: "30px" }}>
                    <button className='custom-btn btn-11' style={{ marginRight: "15px" }} onClick={handlePassword}>
                      확인
                    </button>
                    <button
                      className='custom-btn btn-11'
                      onClick={(e) => {
                        pwModalClose(e.preventDefault());
                      }}
                    >
                      취소
                    </button>
                  </div>
                </form>
              </div>
            </Modal>
            {/* 비밀번호 입력 모달 끝 */}
            {/* 회원정보 수정 모달 시작 */}
            <Modal
              isOpen={userModifyModalIsOpen}
              onRequestClose={allModalClose}
              style={UserModifyStyles}
              contentLabel='PwModal'
            >
              <div id='m-container'>
                <h2 style={{ textAlign: "center", paddingBottom: "40px" }}>MY PAGE MODIFY </h2>
                <h6 style={{ textAlign: "center", paddingBottom: "40px" }}>닉네임과 비밀번호만 수정 가능합니다.</h6>
                <form method='post' className='mf' ref={formRef}>
                  <div className='m-name'>이메일</div>
                  <div className='m-input-block'>
                    <input
                      type='text'
                      className='input'
                      onChange={handleModifyChange}
                      value={userData.userEmail}
                      readOnly
                    />
                  </div>
                  <div className='m-name'>닉네임</div>
                  <div style={{ display: "flex" }}>
                    <div className='m-input-block'>
                      <input
                        style={{ width: "305px" }}
                        type='text'
                        className='input'
                        name='userNickname'
                        onChange={handleModifyChange}
                        value={userData.userNickname}
                      />
                    </div>
                    &nbsp;&nbsp;
                    <button
                      style={{ marginTop: "20px" }}
                      className='custom-btn btn-11'
                      type='button'
                      onClick={handleCheckNicknameUnique}
                    >
                      중복 확인
                    </button>
                  </div>
                  <div className='m-name'>현재 비밀번호</div>
                  <div className='m-input-block'>
                    <input
                      type='password'
                      className='input'
                      value={userData.userPw}
                      onChange={handleModifyChange}
                      readOnly
                    />
                  </div>
                  <div className='m-name'>새 비밀번호</div>
                  <div className='m-input-block'>
                    <input
                      type='password'
                      className='input'
                      name='userNewPw'
                      onChange={handleModifyChange}
                      value={userData.userNewPw}
                    />
                  </div>
                  <div className='m-name'>새 비밀번호 확인</div>
                  <div className='m-input-block'>
                    <input
                      type='password'
                      className='input'
                      onChange={handleModifyChange}
                      name='userNewPw2'
                      value={userData.userNewPw2}
                    />
                  </div>
                  <div className='m-name'>이름</div>
                  <div className='m-input-block'>
                    <input
                      type='text'
                      className='input'
                      onChange={handleModifyChange}
                      value={userData.userName}
                      name='userName'
                      readOnly
                    />
                  </div>
                  <div className='m-name'>성별</div>
                  <div className='m-input-block'>
                    <input
                      className='non'
                      type='text'
                      value={userData.userGender}
                      onChange={handleModifyChange}
                      name='userGender'
                      readOnly
                      style={{ textAlign: "left" }}
                    />
                  </div>
                  <div className='m-name'>생년월일</div>
                  <div className='non'>
                    <div className='m-input-block'>
                      <input
                        type='text'
                        className='input'
                        onChange={handleModifyChange}
                        value={userData.userBirth}
                        name='userBirth'
                        readOnly
                      />
                    </div>
                  </div>
                  <div className='m-name'>전화번호</div>
                  <div className='m-input-block'>
                    <input
                      type='text'
                      className='input'
                      onChange={handleModifyChange}
                      value={userData.userHp}
                      name='userHp'
                      readOnly
                    />
                  </div>
                  <div style={{ display: "flex", justifyContent: "center", marginTop: "30px" }}>
                    <button
                      className='custom-btn btn-11'
                      onClick={BCMypageModifyAction}
                      style={{ marginRight: "15px" }}
                    >
                      수정
                    </button>
                    <button className='custom-btn btn-11' onClick={allModalClose}>
                      취소
                    </button>
                  </div>
                </form>
              </div>
            </Modal>
            {/* 회원정보 모달 끝 */}

            <button
              className='remove__btn'
              onClick={(e) => {
                deleteModalOpen(e.preventDefault());
              }}
            >
              탈퇴하기
            </button>
            {/* 비밀번호 입력 모달 시작 */}
            <Modal
              isOpen={deleteModalIsOpen}
              onRequestClose={deleteModalClose}
              style={deleteUserStyles}
              contentLabel='deleteUserModal'
            >
              <div id='m-container' style={{ marginTop: "50px", textAlign: "center" }}>
                <ul className='links'>
                  <li className='non' style={{ fontSize: "14px", fontWeight: "bold" }}>
                    브런치얼스 회원을 탈퇴하시겠습니까?
                  </li>
                </ul>
                <div style={{ display: "flex", justifyContent: "center", marginTop: "30px" }}>
                  <button className='custom-btn btn-11' style={{ marginRight: "15px" }} onClick={handleDeleteUser}>
                    예
                  </button>
                  <button
                    className='custom-btn btn-11'
                    onClick={(e) => {
                      deleteModalClose(e.preventDefault());
                    }}
                  >
                    아니오
                  </button>
                </div>
              </div>
            </Modal>
            {/* 비밀번호 입력 모달 끝 */}
          </div>
        </form>
      </div>
    </>
  );
}

export default MyPage;

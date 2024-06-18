import React, { useRef, useState } from "react";
import Modal from "react-modal";
import { Link, useNavigate } from "react-router-dom";
import "../css/form.css";
import kakao from "../image/kakaoLogin.png";
import { userLoginAction, findUserId, findUserPw, newPassword, changeUser } from "../api/fetchUser";
import * as responseStatusCode from "../api/responseStatusCode";
import * as responseMessage from "../api/responseMessage";
import { useRecoilState } from "recoil";
import { userState } from "../recoil/atom";
import { REDIRECT_URI, REST_API_KEY } from "../util/kakao";
import { notify, Toast } from "../util/toast";
import { birthRegex, emailRegex, phoneRegex } from "../util/regex";

function Login() {
  const [userLoginState, setUserLoginState] = useRecoilState(userState);
  const [loginUser, setLoginUser] = useState({
    userNo: "",
    userEmail: "",
    userPw: "",
    userNickname: "",
    userBirth: "",
    userHp: "",
    userGender: "",
  });
  const [findIdForm, setFindIdForm] = useState({
    userBirth: "",
    userHp: "",
  });
  const [findPwForm, setFindPwForm] = useState({
    userEmail: "",
    userHp: "",
  });
  const [returnUserForm, setReturnUserForm] = useState({
    userEmail: "",
    userHp: "",
  });

  const [foundId, setFoundId] = useState("");
  const [foundPw, setFoundPw] = useState("");
  const [newPasswords, setNewPasswords] = useState({
    userNewPw: "",
    userNewPw2: "",
  });
  const formRef = useRef();
  const navigate = useNavigate();

  /* 모달 공통 */
  const [findIdIsOpen, setFindIdIsOpen] = React.useState(false);
  const [findPwIsOpen, setFindPwIsOpen] = React.useState(false);
  const [foundIdIsOpen, setFoundIdIsOpen] = React.useState(false);
  const [newPwIsOpen, setNewPwIsOpen] = React.useState(false);
  const [returnUserIsOpen, setReturnUserIsOpen] = React.useState(false);

  Modal.setAppElement("#container");

  /* 아이디 및 비번 찾기 모달 */
  const FindStyles = {
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

  function findIdModalOpen() {
    setFindIdIsOpen(true);
  }
  function findIdModalClose() {
    setFindIdIsOpen(false);
  }
  function findPwModalOpen() {
    setFindPwIsOpen(true);
  }
  function findPwModalClose() {
    setFindPwIsOpen(false);
  }

  function foundIdModalOpen() {
    setFoundIdIsOpen(true);
  }
  function foundIdModalClose() {
    setFoundIdIsOpen(false);
  }
  function newPwModalOpen() {
    setNewPwIsOpen(true);
  }
  function newPwModalClose() {
    setNewPwIsOpen(false);
  }
  function returnUserModalOpen() {
    setReturnUserIsOpen(true);
  }
  function returnUserModalClose() {
    setReturnUserIsOpen(false);
  }

  function allIdModalClose() {
    setFoundIdIsOpen(false);
    setFindIdIsOpen(false);
  }
  function allPwModalClose() {
    setNewPwIsOpen(false);
    setFindPwIsOpen(false);
  }

  const handleChangeLoginForm = (e) => {
    setLoginUser({
      ...loginUser,
      [e.target.name]: e.target.value,
    });
  };
  const BCUserLoginAction = async (event) => {
    if (formRef.current.userEmail.value === "") {
      notify("warning", "이메일을 입력하십시요.");
      formRef.current.userEmail.focus();
      return;
    }
    if (formRef.current.password.value === "") {
      notify("warning", "비밀번호를 입력하십시요.");
      formRef.current.password.focus();
      return;
    }

    const responseJsonObject = await userLoginAction(loginUser);
    setUserLoginState(responseJsonObject);

    switch (responseJsonObject.status) {
      case responseStatusCode.LOGIN_SUCCESS:
        navigate("/main");
        notify("success", "로그인 성공!");
        break;

      default:
        break;
    }
  };

  const handleChangeFindIdForm = (e) => {
    setFindIdForm({
      ...findIdForm,
      [e.target.name]: e.target.value,
    });
  };

  const handleChangeFindPwForm = (e) => {
    const { name, value } = e.target;
    setFindPwForm({
      ...findPwForm,
      [name]: value,
    });
  };
  const handleReturnUserForm = (e) => {
    const { name, value } = e.target;
    setReturnUserForm({
      ...returnUserForm,
      [name]: value,
    });
  };

  const handleFindId = async (event) => {
    event.preventDefault();
    const { userBirth, userHp } = findIdForm;
    // 유효성 검사: 생년월일과 휴대전화 번호가 비어 있는지 확인
    if (!userBirth.trim() || !userHp.trim()) {
      // 에러 메시지 표시
      notify("warning", "생년월일과 휴대전화 번호를 모두 입력해주세요.");
      return;
    }

    // 생년월일 형식(YYYYMMDD) 유효성 검사
    if (!birthRegex.test(userBirth)) {
      // 에러 메시지 표시
      notify("warning", "생년월일은 8자리 숫자로 입력해주세요.");
      return;
    }

    // 휴대전화 번호 형식 유효성 검사
    if (!phoneRegex.test(userHp)) {
      // 에러 메시지 표시
      notify("warning", "휴대전화 번호를 올바른 형식으로 입력해주세요. (000-0000-0000)");
      return;
    }
    const responseJsonObject = await findUserId(findIdForm.userBirth, findIdForm.userHp);
    if (responseJsonObject.status === responseStatusCode.FIND_ID_SUCCESS) {
      setFoundId(responseJsonObject.data);
      foundIdModalOpen();
    } else {
      notify("warning", "아이디를 찾을 수 없습니다.");
    }
    setFindIdForm({
      userBirth: "",
      userHp: "",
    });
  };

  const handleFindPw = async (event) => {
    event.preventDefault();
    const { userEmail, userHp } = findPwForm;
    // 유효성 검사: 생년월일과 휴대전화 번호가 비어 있는지 확인
    if (!userEmail.trim() || !userHp.trim()) {
      // 에러 메시지 표시
      notify("warning", "이메일과 휴대전화 번호를 모두 입력해주세요.");
      return;
    }

    // 생년월일 형식(YYYYMMDD) 유효성 검사
    if (!emailRegex.test(userEmail)) {
      // 에러 메시지 표시
      notify("warning", "이메일을 올바른 형식으로 입력해주세요.");
      return;
    }

    // 휴대전화 번호 형식 유효성 검사
    if (!phoneRegex.test(userHp)) {
      // 에러 메시지 표시
      notify("warning", "휴대전화 번호를 올바른 형식으로 입력해주세요. (000-0000-0000)");
      return;
    }
    const responseJsonObject = await findUserPw(findPwForm.userEmail, findPwForm.userHp);
    if (responseJsonObject.status === responseStatusCode.FIND_PW_SUCCESS) {
      setFoundPw(responseJsonObject.data);
      newPwModalOpen();
    } else {
      notify("error", "비밀번호를 찾을 수 없습니다.");
    }
    setFindPwForm({
      userEmail: findPwForm.userEmail,
      userHp: "",
    });
  };
  const handleReturnUser = async (event) => {
    event.preventDefault();
    const { userEmail, userHp } = returnUserForm;
    // 유효성 검사: 생년월일과 휴대전화 번호가 비어 있는지 확인
    if (!userEmail.trim() || !userHp.trim()) {
      // 에러 메시지 표시
      notify("warning", "이메일과 휴대전화 번호를 모두 입력해주세요.");
      return;
    }

    // 생년월일 형식(YYYYMMDD) 유효성 검사
    if (!emailRegex.test(userEmail)) {
      // 에러 메시지 표시
      notify("warning", "이메일을 올바른 형식으로 입력해주세요.");
      return;
    }

    // 휴대전화 번호 형식 유효성 검사
    if (!phoneRegex.test(userHp)) {
      // 에러 메시지 표시
      notify("warning", "휴대전화 번호를 올바른 형식으로 입력해주세요. (000-0000-0000)");
      return;
    }
    const responseJsonObject = await changeUser(userEmail, userHp);
    if (responseJsonObject.status === responseStatusCode.UPDATE_USER) {
      setReturnUserForm(responseJsonObject.data);
      returnUserModalClose();
      notify("success", "무사히 복구완료되었어요~");
    } else {
      notify("error", "회원을 찾을 수 없습니다.");
    }
    setReturnUserForm({
      userEmail: "",
      userHp: "",
    });
  };

  const kakaoLogin = () => {
    window.location.href = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`;
  };

  const newPasswordUpdate = async (e) => {
    e.preventDefault();
    // 유효성 검사: 새 비밀번호가 비어 있는지 확인
    if (!newPasswords.userNewPw.trim()) {
      // 에러 메시지 표시
      notify("warning", "새 비밀번호를 입력해주세요.");
      return;
    }
    if (newPasswords.newPassword !== newPasswords.newPassword2) {
      notify("warning", "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
      return;
    }
    const responseJsonObject = await newPassword({
      userEmail: findPwForm.userEmail,
      userNewPw: newPasswords.userNewPw,
      userNewPw2: newPasswords.userNewPw2,
    });
    switch (responseJsonObject.status) {
      case responseStatusCode.UPDATE_USER:
        notify("success", "비밀번호가 성공적으로 변경되었습니다.");
        allPwModalClose();
        break;
      default:
        notify("error", "비밀번호 변경에 실패했습니다.");
        break;
    }
  };

  const handleModifyChange = (e) => {
    const { name, value } = e.target;
    setNewPasswords((prevUserData) => ({
      ...prevUserData,
      [name]: value,
    }));
  };

  return (
    <>
      <Toast />
      <div id='m-container'>
        <div className='user-form-center'>
          <h2>LOGIN</h2>

          <ul className='links'>
            <li
              className='non'
              style={{
                fontSize: "14px",
                marginBottom: "10px",
                marginTop: "10px",
              }}
            >
              브런치얼스 회원이 아니신가요?
            </li>
            <Link to={"/register"}>
              <p
                style={{
                  fontWeight: "bold",
                  fontSize: "13px",
                  color: " #6d6c6c",
                }}
              >
                Sign Up
              </p>
            </Link>
          </ul>
        </div>
        <form method='post' className='foruserform mf' ref={formRef}>
          <div className='first-input input__block first-input__block'>
            <input
              type='text'
              name='userEmail'
              placeholder='이메일'
              className='input'
              value={loginUser.userEmail}
              onChange={handleChangeLoginForm}
            />
          </div>
          <div className='input__block'>
            <input
              type='password'
              name='password'
              placeholder='비밀번호'
              className='input'
              value={loginUser.password}
              onChange={handleChangeLoginForm}
            />
          </div>
          <button
            className='signin__btn'
            onClick={(e) => {
              BCUserLoginAction(e.preventDefault());
            }}
          >
            Login
          </button>
        </form>
        <div style={{ textAlign: "center", marginTop: "20px" }}>
          <button className='find__btn' onClick={findIdModalOpen}>
            이메일 찾기
          </button>
          <button className='find__btn' onClick={findPwModalOpen}>
            비밀번호 찾기
          </button>
        </div>
        {/* 이메일 찾기 모달 시작 */}
        <Modal isOpen={findIdIsOpen} onRequestClose={findIdModalClose} style={FindStyles} contentLabel='findIdModal'>
          <li className='non' style={{ fontSize: "18px", listStyleType: "none" }}>
            이메일 찾기
          </li>
          <form className='foruserform mf'>
            <div className='first-input input__block first-input__block'>
              <input
                type='text'
                name='userBirth'
                placeholder='생년월일'
                className='input'
                value={findIdForm.userBirth}
                onChange={handleChangeFindIdForm}
              />
            </div>
            <div className='first-input input__block first-input__block'>
              <input
                type='text'
                name='userHp'
                placeholder='휴대전화'
                className='input'
                value={findIdForm.userHp}
                onChange={handleChangeFindIdForm}
              />
            </div>
            <div className='findfound' style={{ display: "flex", justifyContent: "center" }}>
              <button
                type='button'
                className='custom-btn btn-11'
                style={{ width: "70px", height: "30px", textAlign: "center", padding: "0px" }}
                onClick={handleFindId}
              >
                찾기
              </button>
              <button
                type='button'
                className='custom-btn btn-11'
                style={{ width: "70px", height: "30px", textAlign: "center", padding: "0px" }}
                onClick={findIdModalClose}
              >
                닫기
              </button>
            </div>
          </form>
        </Modal>
        {/* 이메일 찾기 모달 끝 */}
        {/* 비밀번호 찾기 모달 시작 */}
        <Modal isOpen={findPwIsOpen} onRequestClose={findPwModalClose} style={FindStyles} contentLabel='findPwModal'>
          <li className='non' style={{ fontSize: "18px", listStyleType: "none" }}>
            비밀번호 찾기
          </li>
          <form onSubmit={handleFindPw} className='foruserform mf'>
            <div className='first-input input__block first-input__block'>
              <input
                type='text'
                name='userEmail'
                placeholder='이메일'
                className='input'
                value={findPwForm.userEmail}
                onChange={handleChangeFindPwForm}
              />
            </div>
            <div className='first-input input__block first-input__block'>
              <input
                type='text'
                name='userHp'
                placeholder='휴대전화'
                className='input'
                value={findPwForm.userHp}
                onChange={handleChangeFindPwForm}
              />
            </div>
            <div className='findfound' style={{ display: "flex", justifyContent: "center" }}>
              <button
                type='submit'
                className='custom-btn btn-11'
                style={{ width: "70px", height: "30px", textAlign: "center", padding: "0px" }}
              >
                찾기
              </button>
              <button
                type='button'
                className='custom-btn btn-11'
                style={{ width: "70px", height: "30px", textAlign: "center", padding: "0px" }}
                onClick={findPwModalClose}
              >
                닫기
              </button>
            </div>
          </form>
        </Modal>
        {/* 비밀번호 찾기 모달 끝 */}
        {/* 이메일 확인 모달 시작 */}
        <Modal isOpen={foundIdIsOpen} onRequestClose={foundIdModalClose} style={FindStyles} contentLabel='foundIdModal'>
          <div>
            사용자의 이메일은 <strong style={{ color: "royalblue" }}>{foundId}</strong>입니다
          </div>
          <br />
          <input className='custom-btn btn-11' type='button' onClick={allIdModalClose} value='확인' />
        </Modal>
        {/* 이메일 확인 모달 끝 */}
        {/* 비밀번호 확인 모달 시작 */}
        <Modal isOpen={newPwIsOpen} onRequestClose={newPwModalClose} style={FindStyles} contentLabel='foundPwModal'>
          <li className='non' style={{ fontSize: "18px", listStyleType: "none" }}>
            새 비밀번호
          </li>
          <form onSubmit={handleFindPw} className='foruserform mf'>
            <div className='m-name'>새 비밀번호</div>
            <div className='first-input input__block first-input__block'>
              <input
                type='password'
                name='userNewPw'
                className='input'
                value={newPasswords.userNewPw}
                onChange={handleModifyChange}
              />
            </div>
            <div className='m-name'>새 비밀번호 확인</div>
            <div className='first-input input__block first-input__block'>
              <input
                type='password'
                name='userNewPw2'
                className='input'
                value={newPasswords.userNewPw2}
                onChange={handleModifyChange}
              />
            </div>
            <input className='custom-btn btn-11' type='button' onClick={newPasswordUpdate} value='확인' />
          </form>
        </Modal>
        {/* 비밀번호 확인 모달 끝 */}
        <br />
        <div className='separator'>
          <p>OR</p>
        </div>
        <div className='sns-login-button-container'>
          <img style={{ cursor: "Pointer" }} className='no' src={kakao} onClick={kakaoLogin} />
        </div>
        <button className='find__btn' style={{ marginLeft: "210px" }} onClick={returnUserModalOpen}>
          계정 복구하기
        </button>
        {/* 계정복구 모달 시작 */}
        <Modal
          isOpen={returnUserIsOpen}
          onRequestClose={returnUserModalClose}
          style={FindStyles}
          contentLabel='findPwModal'
        >
          <li className='non' style={{ fontSize: "18px", listStyleType: "none" }}>
            계정 복구
          </li>
          <form onSubmit={handleFindPw} className='foruserform mf'>
            <div className='first-input input__block first-input__block'>
              <input
                type='text'
                name='userEmail'
                placeholder='이메일'
                className='input'
                value={returnUserForm.userEmail}
                onChange={handleReturnUserForm}
              />
            </div>
            <div className='first-input input__block first-input__block'>
              <input
                type='text'
                name='userHp'
                placeholder='휴대전화'
                className='input'
                value={returnUserForm.userHp}
                onChange={handleReturnUserForm}
              />
            </div>
            <div className='findfound' style={{ display: "flex", justifyContent: "center" }}>
              <button
                type='submit'
                className='custom-btn btn-11'
                style={{ width: "70px", height: "30px", textAlign: "center", padding: "0px" }}
                onClick={handleReturnUser}
              >
                복구
              </button>
              <button
                type='button'
                className='custom-btn btn-11'
                style={{ width: "70px", height: "30px", textAlign: "center", padding: "0px" }}
                onClick={returnUserModalClose}
              >
                닫기
              </button>
            </div>
          </form>
        </Modal>
        {/*계정복구 모달 끝 */}
      </div>
    </>
  );
}

export default Login;

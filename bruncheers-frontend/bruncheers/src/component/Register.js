import React, { useRef, useState } from "react";
import "../css/form.css";
import { Link, useNavigate } from "react-router-dom";
import * as responseStatusCode from "../api/responseStatusCode.js";
import { checkEmailUnique, checkNicknameUnique, userWriteAction } from "../api/fetchUser.js";
import { birthRegex, emailRegex, phoneRegex } from "../util/regex.js";
import { notify, Toast } from "../util/toast";

import "react-toastify/dist/ReactToastify.css";

function Register() {
  const navigate = useNavigate();
  const formRef = useRef();
  const [user, setUser] = useState({
    userEmail: "",
    userNickname: "",
    userPw: "",
    userPw2: "",
    userName: "",
    userGender: "",
    userBirth: "",
    userHp: "",
  });

  const [emailChecked, setEmailChecked] = useState(false); // 이메일 중복 확인 상태
  const [nicknameChecked, setNicknameChecked] = useState(false); // 닉네임 중복 확인 상태
  const BCUserCreateAction = async () => {
    if (!emailChecked) {
      notify("warning", "이메일 중복 확인을 해주세요.");
      formRef.current.userEmail.focus();
      return;
    }
    if (!nicknameChecked) {
      notify("warning", "닉네임 중복 확인을 해주세요.");
      formRef.current.userNickname.focus();
      return;
    }
    if (formRef.current.userPw.value === "") {
      notify("warning", "비밀번호를 입력하십시요.");
      formRef.current.userPw.focus();
      return;
    }
    if (formRef.current.userPw2.value === "") {
      notify("warning", "비밀번호확인을 입력하십시요.");
      formRef.current.userPw2.focus();
      return;
    }
    if (formRef.current.userName.value === "") {
      notify("warning", "이름을 입력하십시요.");
      formRef.current.userName.focus();
      return;
    }
    if (formRef.current.userBirth.value === "") {
      notify("warning", "생년월일을 입력하십시요.");
      formRef.current.userBirth.focus();
      return;
    }
    if (!birthRegex.test(user.userBirth)) {
      notify("warning", "올바른 생년월일 형식이 아닙니다.");
      return;
    }
    if (formRef.current.userHp.value === "") {
      notify("warning", "전화번호를 입력하십시요.");
      formRef.current.userHp.focus();
      return;
    }

    if (!phoneRegex.test(user.userHp)) {
      notify("warning", "올바른 전화번호 형식이 아닙니다.");
      return;
    }

    if (formRef.current.userPw.value !== formRef.current.userPw2.value) {
      notify("warning", "비밀번호와 비밀번호확인은 일치하여야합니다.");
      formRef.current.userPw.focus();
      formRef.current.userPw.select();
      return;
    }

    // 이메일과 닉네임 중복 확인이 모두 완료되었을 때만 회원가입 처리
    const responseJsonObject = await userWriteAction(user);
    switch (responseJsonObject.status) {
      case responseStatusCode.CREATED_USER:
        navigate("/login");
        break;
      case responseStatusCode.LOGIN_FAIL_NOT_FOUND_USER:
        notify("error", "회원가입 실패!");
        break;
      default:
        break;
    }
  };

  const handleCheckEmailUnique = async () => {
    if (formRef.current.userEmail.value === "") {
      notify("warning", "이메일을 입력하십시요.");
      formRef.current.userEmail.focus();
      return;
    }
    if (!emailRegex.test(user.userEmail)) {
      notify("warning", "올바른 이메일 형식이 아닙니다.");
      return;
    }
    const response = await checkEmailUnique(user.userEmail);
    const message =
      response.status === responseStatusCode.EMAIL_AVAILABLE ? "사용 가능한 이메일입니다." : "중복된 이메일입니다.";
    const type = response.status === responseStatusCode.EMAIL_AVAILABLE ? "success" : "warning";
    notify(type, message);
    setEmailChecked(response.status === responseStatusCode.EMAIL_AVAILABLE); // 중복 확인 상태 업데이트
  };
  const handleCheckNicknameUnique = async () => {
    if (formRef.current.userNickname.value === "") {
      notify("warning", "닉네임을 입력하십시요.");
      formRef.current.userNickname.focus();
      return;
    }
    const response = await checkNicknameUnique(user.userNickname);
    const message =
      response.status === responseStatusCode.NICKNAME_AVAILABLE ? "사용 가능한 닉네임입니다." : "중복된 닉네임입니다.";
    const type = response.status === responseStatusCode.NICKNAME_AVAILABLE ? "success" : "warning";
    notify(type, message);
    setNicknameChecked(response.status === responseStatusCode.NICKNAME_AVAILABLE); // 중복 확인 상태 업데이트
  };

  const handleChangeUserWriteForm = (e) => {
    setUser({
      ...user,
      [e.target.name]: e.target.value,
    });
  };

  return (
    <div id='m-container'>
      <Toast />
      <div className='user-form-center'>
        <h2>SIGN UP</h2>

        <ul className='links'>
          <li className='non' style={{ fontSize: "14px", marginBottom: "10px", marginTop: "10px" }}>
            브런치얼스 회원이세요?
          </li>
          <Link to={"/login"}>
            <p
              className='non'
              style={{ fontWeight: "bold", fontSize: "13px", color: " #6d6c6c", marginBottom: "50px" }}
            >
              Login
            </p>
          </Link>
        </ul>
      </div>

      <form action='' method='post' className='mf' ref={formRef}>
        <div className='m-name'>이메일</div>
        <div style={{ display: "flex" }}>
          <div className='first-input input__block first-input__block'>
            <input
              style={{ width: "305px" }}
              type='text'
              className='input'
              name='userEmail'
              value={user.userEmail}
              onChange={handleChangeUserWriteForm}
            />
          </div>
          &nbsp;&nbsp;
          <button
            style={{ marginTop: "10px" }}
            className='custom-btn btn-11'
            type='button'
            onClick={handleCheckEmailUnique}
          >
            중복 확인
          </button>
        </div>
        <div className='m-name'>닉네임</div>
        <div style={{ display: "flex" }}>
          <div className='first-input input__block first-input__block'>
            <input
              style={{ width: "305px" }}
              type='text'
              className='input'
              name='userNickname'
              value={user.userNickname}
              onChange={handleChangeUserWriteForm}
            />
          </div>
          &nbsp;&nbsp;
          <button
            style={{ marginTop: "10px" }}
            className='custom-btn btn-11'
            type='button'
            onClick={handleCheckNicknameUnique}
          >
            중복 확인
          </button>
        </div>
        <div className='m-name'>비밀번호</div>

        <div className='input__block'>
          <input
            type='password'
            className='input'
            name='userPw'
            value={user.userPw}
            onChange={handleChangeUserWriteForm}
          />
        </div>
        <div className='m-name'>비밀번호 확인</div>
        <div className='input__block'>
          <input
            type='password'
            className='input'
            name='userPw2'
            value={user.userPw2}
            onChange={handleChangeUserWriteForm}
          />
        </div>
        <div className='m-name'>이름</div>
        <div className='first-input input__block first-input__block'>
          <input
            type='text'
            className='input'
            name='userName'
            value={user.userName}
            onChange={handleChangeUserWriteForm}
          />
        </div>
        <div className='m-name'>성별</div>
        <div className='select' style={{ marginLeft: "30px", marginBottom: "20px" }}>
          <input
            className='non'
            type='radio'
            id='select'
            name='userGender'
            value='남'
            checked={user.userGender === "남"}
            onChange={handleChangeUserWriteForm}
          />
          <label className='non' htmlFor='select'>
            남자
          </label>
          <input
            className='non'
            type='radio'
            id='select2'
            name='userGender'
            value='여'
            checked={user.userGender === "여"}
            onChange={handleChangeUserWriteForm}
          />
          <label className='non' htmlFor='select2'>
            여자
          </label>
        </div>
        <div className='m-name'>생년월일 (8자리 숫자)</div>

        <div className='first-input input__block first-input__block'>
          <input
            type='text'
            className='input'
            name='userBirth'
            value={user.userBirth}
            onChange={handleChangeUserWriteForm}
          />
        </div>
        <div className='m-name'>휴대전화 (xxx-xxxx-xxxx)</div>
        <div className='first-input input__block first-input__block'>
          <input type='text' className='input' name='userHp' value={user.userHp} onChange={handleChangeUserWriteForm} />
        </div>
        <button
          className='signup__btn'
          onClick={(e) => {
            BCUserCreateAction(e.preventDefault());
          }}
        >
          회원가입
        </button>
      </form>
    </div>
  );
}

export default Register;

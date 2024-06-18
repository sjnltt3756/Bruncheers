import { notify } from "../util/toast";
const BACKEND_SERVER = "http://localhost:8080";

//회원탈퇴
export const userDeleteAction = async (userNo) => {
  const response = await fetch(`${BACKEND_SERVER}/users/delete/${userNo}`, {
    method: "DELETE",
  });
  const responseJsonObject = await response.json();
  return responseJsonObject;
};

//회원정보
export const userView = async (userNo) => {
  const response = await fetch(`${BACKEND_SERVER}/users/userInfo/${userNo}`, {
    method: "GET",
  });
  const responseJsonObject = await response.json();
  return responseJsonObject;
};
//회원가입
export const userWriteAction = async (sendJsonObject) => {
  const response = await fetch(`${BACKEND_SERVER}/users/register`, {
    method: "POST",
    headers: {
      "Content-type": "application/json",
    },
    body: JSON.stringify(sendJsonObject),
  });
  const resultJsonObject = await response.json();
  return resultJsonObject;
};
//정보수정
export const userModifyAction = async (sendJsonObject) => {
  const response = await fetch(
    `${BACKEND_SERVER}/users/update/${sendJsonObject.userNo}`,

    {
      method: "PUT",
      headers: {
        "Content-type": "application/json",
      },
      body: JSON.stringify(sendJsonObject),
    }
  );
  const resultJsonObject = await response.json();
  return resultJsonObject;
};

//로그인
export const userLoginAction = async (sendJsonObject) => {
  const response = await fetch(`${BACKEND_SERVER}/users/login`, {
    method: "POST",
    headers: {
      "Content-type": "application/json",
    },
    body: JSON.stringify(sendJsonObject),
  });

  if (!response.ok) {
    notify("warning", "아이디 또는 비밀번호가 일치하지 않습니다.");
    return sendJsonObject; // 실패 응답 반환
  }
  const resultJsonObject = await response.json();
  const userToken = resultJsonObject.data.token;

  // 토큰이 존재한다면 헤더에 추가
  if (userToken) {
    localStorage.setItem("token", userToken); // 로컬 스토리지에 토큰 저장

    // 헤더에 토큰 추가
    const headers = {
      "Content-type": "application/json",
      Authorization: `${userToken}`,
    };
    const responseWithToken = await fetch(`${BACKEND_SERVER}/users/login`, {
      method: "POST",
      headers,
      body: JSON.stringify(sendJsonObject),
    });
    return await responseWithToken.json();
  }

  return resultJsonObject;
};

// 아이디 찾기
export const findUserId = async (userBirth, userHp) => {
  const response = await fetch(`${BACKEND_SERVER}/users/find/id?userBirth=${userBirth}&userHp=${userHp}`, {
    method: "GET",
  });
  const responseJsonObject = await response.json();
  return responseJsonObject;
};

// 비밀번호 찾기
export const findUserPw = async (userEmail, userHp) => {
  const response = await fetch(`${BACKEND_SERVER}/users/find/pw?userEmail=${userEmail}&userHp=${userHp}`, {
    method: "GET",
  });
  const responseJsonObject = await response.json();
  return responseJsonObject;
};

// 닉네임 중복 확인
export const checkNicknameUnique = async (userNickname) => {
  const response = await fetch(`${BACKEND_SERVER}/users/nickname-unique?userNickname=${userNickname}`, {
    method: "GET",
  });
  const responseJsonObject = await response.json();
  return responseJsonObject;
};

// 이메일 중복 확인
export const checkEmailUnique = async (userEmail) => {
  const response = await fetch(`${BACKEND_SERVER}/users/email-unique?userEmail=${userEmail}`, {
    method: "GET",
  });
  const responseJsonObject = await response.json();
  return responseJsonObject;
};

export const verifyLogin = async (userNo, sendJsonObject) => {
  const response = await fetch(`/users/verify-login/${userNo}`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(sendJsonObject),
  });
  const resultJsonObject = await response.json();
  return resultJsonObject;
};

// 카카오정보
export const kakaoLogin = async (kakaoToken) => {
  const response = await fetch(`${BACKEND_SERVER}/api/kakao_userinfo_with_token?authorize_access_token=${kakaoToken}`, {
    method: "GET",
    headers: {
      "Content-Type": "x-www-form-urlencoded; charset=UTF-8",
      Authorization: `${kakaoToken}`,
    },
  });
  const responseJsonObject = await response.json();
  return responseJsonObject;
};
//비밀번호 찾기 후 새 비밀번호입력
export const newPassword = async (sendJsonObject) => {
  const response = await fetch(`/users/updatePassword`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json; charset=UTF-8",
    },
    body: JSON.stringify(sendJsonObject),
  });

  if (response.ok) {
    const responseData = await response.json();
    return responseData;
  }
};
//유령회원 변경
export const changeGhost = async (userNo, sendJsonObject) => {
  const response = await fetch(`/users/${userNo}/role/ghost`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json; charset=UTF-8",
    },
    body: JSON.stringify(sendJsonObject),
  });
  const responseJsonObject = await response.json();
  return responseJsonObject;
};

//유령회원 복구
export const changeUser = async (userEmail, userHp) => {
  const response = await fetch(`/users/role/user?userEmail=${userEmail}&userHp=${userHp}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json; charset=UTF-8",
    },
  });

  const responseJsonObject = await response.json();
  return responseJsonObject;
};

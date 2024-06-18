import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

// 성공 메시지에 대한 스타일
const successToastStyle = {
  backgroundColor: "rgba(92, 184, 92, 0.4)", // 성공 메시지 배경색
  color: "black", // 성공 메시지 텍스트 색상
  fontSize: "1rem",
  fontWeight: "bold",
};

// 에러 메시지에 대한 스타일
const errorToastStyle = {
  backgroundColor: "rgba(255, 127, 80, 0.4)", // 에러 메시지 배경색
  color: "black", // 에러 메시지 텍스트 색상
  fontSize: "1rem",
  fontWeight: "bold",
};

// 워닝 메시지에 대한 스타일
const warningToastStyle = {
  backgroundColor: "rgba(240, 173, 78, 0.4)", // 워닝 메시지 배경색
  color: "black", // 워닝 메시지 텍스트 색상
  fontSize: "1rem",
  fontWeight: "bold",
};

export const notify = (type, text) => {
  switch (type) {
    case "default":
      toast(text);
      break;
    case "success":
      toast.success(text, { style: successToastStyle });
      break;
    case "warning":
      toast.warning(text, { style: warningToastStyle });
      break;
    case "error":
      toast.error(text, { style: errorToastStyle });
      break;
  }
};

export const Toast = () => {
  return (
    <ToastContainer
      position='top-center'
      autoClose={1000}
      hideProgressBar
      newestOnTop={false}
      closeOnClick
      rtl={false}
      pauseOnFocusLoss={false}
      draggable
      pauseOnHover={false}
      theme='light'
    />
  );
};

/* <ToastContainer
        position='top-center' // 알람 위치 지정
        autoClose={1500} // 자동 off 시간
        hideProgressBar={false} // 진행시간바 숨김
        closeOnClick // 클릭으로 알람 닫기
        rtl={false} // 알림 좌우 반전
        draggable // 드래그 가능
        pauseOnHover // 마우스를 올리면 알람 정지
        theme='light'
        limit={1} // 알람 개수 제한
      /> */

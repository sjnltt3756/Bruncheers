import React from "react";
import { useNavigate } from "react-router";

function OrderSuccess() {
  const navigate = useNavigate();

  const containerStyle = {
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
    justifyContent: "center",
    height: "50vh",
    textAlign: "center",
  };
  return (
    <>
      <div style={containerStyle}>
        <img
          src="../image/success.png"
          style={{ width: "50px", paddingBottom: "10px" }}
        />
        <p style={{ marginTop: "20px", marginBottom: "10px" }}>
          주문이 완료되었습니다.
        </p>
        <div style={{ fontSize: "13px", marginBottom: "30px" }}>
          <p>
            고객님의 소중한 식사, 브런치얼스가 정성스럽게 준비해서 보내드릴게요.
            ٩( ᐛ )و
          </p>
        </div>
        <button
          style={{
            marginBottom: "5px",
            fontSize: "14px",
          }}
          className="bottomBtn"
          onClick={() => navigate("/main")}
        >
          메인으로 이동
        </button>
      </div>
    </>
  );
}

export default OrderSuccess;

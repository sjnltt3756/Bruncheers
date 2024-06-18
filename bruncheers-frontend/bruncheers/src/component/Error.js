import React from 'react'
import error from "../image/404.png"
import { useNavigate } from 'react-router'

function Error() {
  const navigate = useNavigate();

  const containerStyle = {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    height: '100vh',
    textAlign: 'center'
  };

  return (
    <div style={containerStyle}>
      <img src={error} style={{width:"600px"}}/>
      <p style={{marginTop:"20px",marginBottom:"10px"}}>죄송합니다. 현재 찾을 수 없는 페이지를 요청하셨습니다.</p>
      <p style={{marginBottom:"5px",fontSize: "14px"}}>페이지의 주소가 잘못 입력되었거나,<br/>
      주소가 변경 혹은 삭제되어 요청하신 페이지를 찾을 수 없습니다.</p>
      <br/>
      <div style={{fontSize: "13px", marginBottom:"30px"}}>
      <p>Sorry. Page not found.<br/>
      The page you were looking for appears to have been moved.<br/>
      deleted or does not exist.</p>
      </div>
      <button className='find__btn' onClick={(e) => {
              navigate("/main");
              e.preventDefault();
            }}>
            GO HOME
          </button>
    </div>
  )
}

export default Error
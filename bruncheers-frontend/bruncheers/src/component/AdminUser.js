import React, { useEffect, useState } from "react";
import * as fetchAdmin from "../api/fetchAdmin";
import { useNavigate } from "react-router";

function AdminUser() {
  const [users, setUsers] = useState([]);
  const navigate = useNavigate();

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

  return (
    <>
    {(users&&
      <div className="p-3">
        <h2 className="productTitle">브런치얼스 회원</h2>
        <div className="p-container">
          <div className="user-group">
            <div className="user-header">
              <h5 className="userTitle">총 회원: {users.length}명</h5>
            </div>
            <div>
            {users.length === 0 ? (
                <p>회원 정보가 없습니다.</p>
              ) : (
              <table className="user-table">
                <thead>
                  <tr>
                    <th>회원번호</th>
                    <th>이메일</th>
                    <th>닉네임</th>
                    <th>이름</th>
                    <th>성별</th>
                    <th>생년월일</th>
                    <th>핸드폰번호</th>
                  </tr>
                </thead>
                <tbody>
                 {users.map((user) => {
                    return ( 
                      <tr key={user.userNo}>
                        <td>{user.userNo}</td>
                        <td>{user.userEmail}</td>
                        <td>{user.userNickname}</td>
                        <td>{user.userName}</td>
                        <td>{user.userGender}</td>
                        <td>{user.userBirth}</td>
                        <td>{user.userHp}</td>
                      </tr>
                  );
                  })} 
                </tbody>
              </table>
          )}
            </div>
          </div>
        </div>
      </div>
      )}
    </>
  );
}

export default AdminUser;

import React from "react";
import { Navigate } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { userState } from "../recoil/atom";

function RequireAdmin({ children }) {
  const userLoginState = useRecoilValue(userState);
  const token = localStorage.getItem("token");
  const isAdmin = token && userLoginState.data.role === "ADMIN";

  if (!isAdmin) {
    return <Navigate to="/error" replace />;
  }

  return children;
}

export default RequireAdmin;

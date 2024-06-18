import { useRecoilState } from "recoil";
import { userState } from "../recoil/atom";
const BACKEND_SERVER = "http://localhost:8080";

const token = localStorage.getItem("token");

const deleteOrder = async (oNo) => {
  const response = await fetch(`${BACKEND_SERVER}/order/${oNo}`, {
    method: "DELETE",
  });
  const responseJsonObjcet = await response.json();
  return responseJsonObjcet;
};

const deleteAllOrder = async (userNo) => {
  const response = await fetch(`${BACKEND_SERVER}/order/orderDeleteAll/${userNo}`, {
    method: "DELETE",
  });
  const responseJsonObjcet = await response.json();
  return responseJsonObjcet;
};

/* const createOrderDirect = async(sendJsonObject)=>{
    const response = await fetch(`${BACKEND_SERVER}/order/direct`,{
        method: "POST",
        headers:{
            "Content-Type": "application/json",
        },
        body: JSON.stringify(sendJsonObject),
    });
    const resultJsonObject = await response.json();
    return resultJsonObject;
}; */

const createOrder = async (sendJsonObject) => {
  const response = await fetch(`${BACKEND_SERVER}/order`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(sendJsonObject),
  });
  const resultJsonObject = await response.json();
  return resultJsonObject;
};

const listOrder = async (oNo) => {
  const response = await fetch(`${BACKEND_SERVER}/order/${oNo}`, {
    method: "GET",
  });
  const responseJsonObjcet = await response.json();
  return responseJsonObjcet;
};

const listUserOrder = async (userNo) => {
  const response = await fetch(`${BACKEND_SERVER}/order/userNo/${userNo}`, {
    method: "GET",
  });
  const responseJsonObjcet = await response.json();
  return responseJsonObjcet;
};

const listOrders = async (userNo) => {
  const response = await fetch(`${BACKEND_SERVER}/order/userNo/${userNo}`, {
    method: "GET",
  });
  const resultJsonObject = await response.json();
  return resultJsonObject;
};

export { deleteOrder, deleteAllOrder, createOrder, listOrder, listUserOrder, listOrders };

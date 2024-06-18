import axios from "axios";

const BACKEND_SERVER = "http://localhost:8080";

const token = localStorage.getItem("token");

//모든 회원 조회
export const listUser = async () => {
  const response = await fetch(`${BACKEND_SERVER}/admin/user/list`, {
    method: "GET",
    headers: { Authorization: "Bearer " + `${token}` },
  });
  const responseJsonObject = await response.json();
  return responseJsonObject;
};

// 전체 주문리스트(order+orderItem+product) 조회
export const listAllOrders = async () => {
  const response = await fetch(`${BACKEND_SERVER}/admin/order/list`, {
    method: "GET",
    headers: { Authorization: "Bearer " + `${token}` },
  });
  const resultJsonObject = await response.json();
  return resultJsonObject;
};

// 상품등록
export const saveProduct = async (product) => {
  const header = {
    headers: {
      "Content-Type": "multipart/form-data",
      Authorization: "Bearer " + `${token}`,
    },
  };
  const response = await axios.post(`${BACKEND_SERVER}/admin/product`, product, header);
  return response.data;
};

//상품수정 
export const updateProduct = async (product) => {
  const header = {
    headers: {
      "Content-Type": "multipart/form-data",
      Authorization: "Bearer " + `${token}`,
    },
  };
  const response = await axios.put(`${BACKEND_SERVER}/admin/product/update`, product, header);
  return response.data;
};

// 상품 삭제
export const deleteProduct = async (pNo) => {
  const response = await fetch(`${BACKEND_SERVER}/admin/product/${pNo}`, {
    method: "DELETE",
    headers: { Authorization: "Bearer " + `${token}` },
  });
};
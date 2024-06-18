/* const BACKEND_SERVER = "http://localhost:8080"; */
const BACKEND_SERVER = ""
const addCart = async (sendJsonObject) => {
  const response = await fetch(`${BACKEND_SERVER}/cart`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(sendJsonObject),
  });
  const resultJsonObject = await response.json();
  return resultJsonObject;
};

const listCart = async (userNo) => {
  const response = await fetch(`${BACKEND_SERVER}/cart/${userNo}`, {
    method: "GET",
  });
  const resultJsonObject = await response.json();
  return resultJsonObject;
};

const deleteCartItem = async (cNo) => {
  const response = await fetch(`${BACKEND_SERVER}/cart/${cNo}`, {
    method: "DELETE",
  });
  const responseJsonObjcet = await response.json();
  return responseJsonObjcet;
};

const deleteCheckedCartItems = async (cNoList) => {
  const response = await fetch(`${BACKEND_SERVER}/cart`, {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(cNoList),
  });
  const resultJsonObject = await response.json();
  return resultJsonObject;
};

export { addCart, listCart, deleteCartItem, deleteCheckedCartItems};

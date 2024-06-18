/* const BACKEND_SERVER = "http://localhost:8080"; */
const BACKEND_SERVER = ""
const listCoupon = async (userNo) => {
  const response = await fetch(`${BACKEND_SERVER}/coupons/${userNo}`, {
    method: "GET",
  });
  const resultJsonObject = await response.json();
  return resultJsonObject;
};

const createCoupon = async (sendJsonObject) => {
  const response = await fetch(`${BACKEND_SERVER}/coupon`, {
    method: "POST",
    headers: {
      "Content-Type": "appplicatuon/json",
    },
    body: JSON.stringify(sendJsonObject),
  });
  const resultJsonObject = await response.json();
  return resultJsonObject;
};

const deleteCoupon = async (coupNo) => {
  const response = await fetch(`${BACKEND_SERVER}/coupon/${coupNo}`, {
    method: "DELETE",
  });
  const responseJsonObject = await response.json();
  return responseJsonObject;
};

const deleteCoupons = async (userNo) => {
  const response = await fetch(`${BACKEND_SERVER}/coupons/${userNo}`, {
    method: "DELETE",
  });
  const responseJsonObject = await response.json();
  return responseJsonObject;
};


export {listCoupon,createCoupon,deleteCoupon,deleteCoupons}
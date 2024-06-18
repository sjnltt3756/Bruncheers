import { json } from "react-router";
import axios from "axios";

const BACKEND_SERVER = "http://localhost:8080";

const listReview = async () => {
  const response = await fetch(`${BACKEND_SERVER}/review`);
  const resultJsonObject = await response.json();
  return resultJsonObject;
};

const listReviewPNo = async (product_no) => {
  const response = await fetch(`${BACKEND_SERVER}/review/${product_no}`, {
    method: "GET",
  });
  const responseJsonObjcet = await response.json();
  return responseJsonObjcet;
}

const viewReview = async (orderItem_no) => {
  const response = await fetch(
    `${BACKEND_SERVER}/review_view/${orderItem_no}`,
    {
      method: "GET",
    }
  );
  const resultJsonObject = await response.json();
  return resultJsonObject;
};
const writeReview = async (review) => {
  const header = { headers: { "Content-Type": "multipart/form-data" } };
  const res = await axios.post(`${BACKEND_SERVER}/review`, review, header);
  return res.data;
};

const writeReplyBoard = async (sendJsonObject) => {
  const response = await fetch(`${BACKEND_SERVER}/comment`, {
    method: "POST",
    headers: {
      "Content-type": "application/json",
    },
    body: JSON.stringify(sendJsonObject),
  });
  const resultJsonObject = await response.json();
  return resultJsonObject;
};

const updateReview = async (oiNo, review) => {
  const header = { headers: { "Content-Type": "multipart/form-data" } };
  const res = await axios.put(
    `${BACKEND_SERVER}/review/${oiNo}`,
    review,
    header
  );
  return res.data;
};

const deleteReview = async (orderItem_no) => {
  const response = await fetch(`${BACKEND_SERVER}/review/${orderItem_no}`,{
    method: "DELETE",
  });
  const resultJsonObject = await response.json();
  return resultJsonObject;
}


const deleteBoard = async (board_no) => {
  const response = await fetch(`${BACKEND_SERVER}/board/${board_no}`, {
    method: "DELETE",
  });
  const resultJsonObject = await response.json();
  return resultJsonObject;
};

export {
  listReview,
  listReviewPNo,
  viewReview,
  writeReview,
  updateReview,
  deleteReview,
  writeReplyBoard,
  deleteBoard,
  BACKEND_SERVER,
};

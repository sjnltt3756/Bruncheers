const BACKEND_SERVER = "http://localhost:8080";


const token = localStorage.getItem("token");

const listBoard = async (page_no) => {
  const response = await fetch(`${BACKEND_SERVER}/boards/${page_no}`);
  const resultJsonObject = await response.json();
  return resultJsonObject;
};
const viewBoard = async (board_no) => {
  const response = await fetch(`${BACKEND_SERVER}/board_view/${board_no}`, {
    method: "GET",
  });
  const resultJsonObject = await response.json();
  return resultJsonObject;
};
const writeBoard = async (sendJsonObject) => {
  const response = await fetch(`${BACKEND_SERVER}/board`, {
    method: "POST",
    headers: {
      "Content-type": "application/json",
    },
    body: JSON.stringify(sendJsonObject),
  });
  const resultJsonObject = await response.json();
  return resultJsonObject;
};

const writeReplyBoard = async (sendJsonObject) => {
  const response = await fetch(`${BACKEND_SERVER}/admin/board/comment`, {
    method: "POST",
    headers: {
      "Content-type": "application/json",
      Authorization: "Bearer " + `${token}`,
    },
    body: JSON.stringify(sendJsonObject),
  });
  const resultJsonObject = await response.json();
  return resultJsonObject;
};

const writeNoticeBoard = async (sendJsonObject) => {
  const response = await fetch(`${BACKEND_SERVER}/admin/notice`, {
    method: "POST",
    headers: {
      "Content-type": "application/json",
      Authorization: "Bearer " + `${token}`,
    },
    body: JSON.stringify(sendJsonObject),
  });
  const resultJsonObject = await response.json();
  return resultJsonObject;
};

const updateBoard = async (board_no, sendJsonObject) => {
  const response = await fetch(`${BACKEND_SERVER}/board/${board_no}`, {
    method: "PUT",
    headers: {
      "Content-type": "application/json",
    },
    body: JSON.stringify(sendJsonObject),
  });
  const resultJsonObject = await response.json();
  return resultJsonObject;
};

const deleteBoard = async (board_no) => {
  const response = await fetch(`${BACKEND_SERVER}/board/${board_no}`, {
    method: "DELETE",
  });
  const resultJsonObject = await response.json();
  return resultJsonObject;
};


export { listBoard, viewBoard, writeBoard, updateBoard, writeReplyBoard, deleteBoard, writeNoticeBoard };

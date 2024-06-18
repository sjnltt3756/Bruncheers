const BACKEND_SERVER = "http://localhost:8080";

const listProduct = async () => {
  const response = await fetch(`${BACKEND_SERVER}/product`);
  const resultJsonObject = await response.json();
  return resultJsonObject;
};

const viewProduct = async (pNo) => {
  const response = await fetch(`${BACKEND_SERVER}/product/${pNo}`, {
    method: "GET",
  });
  const resultJsonObject = await response.json();
  return resultJsonObject;
};

const viewCatNoProduct = async (catNo) => {
  const response = await fetch(`${BACKEND_SERVER}/product/catNo/${catNo}`, {
    method: "GET",
  });
  const resultJsonObject = await response.json();
  return resultJsonObject;
};

const viewPoNoProduct = async (poNo) => {
  const response = await fetch(`${BACKEND_SERVER}/product/poNo/${poNo}`, {
    method: "GET",
  });
  const resultJsonObject = await response.json();
  return resultJsonObject;
};

const writeProduct = async (sendJsonObject) => {
  const response = await fetch(`${BACKEND_SERVER}/product`, {
    method: "POST",
    headers: {
      "Content-type": "application/json",
    },
    body: JSON.stringify(sendJsonObject),
  });
  const resultJsonObject = await response.json();
  return resultJsonObject;
};

const updateProduct = async (sendJsonObject) => {
  const response = await fetch(`${BACKEND_SERVER}/product`, {
    method: "PUT",
    headers: {
      "Content-type": "application/json",
    },
    body: JSON.stringify(sendJsonObject),
  });
  const resultJsonObject = await response.json();
  return resultJsonObject;
};

const deleteProduct = async (pNo) => {
  const response = await fetch(`${BACKEND_SERVER}/product/${pNo}`, {
    method: "DELETE",
  });
  const resultJsonObject = await response.json();
  return resultJsonObject;
};

const getProductWithPcat = async () => {
  const response = await fetch(`${BACKEND_SERVER}/product/pcat`, {
    method: "GET",
  });
  const resultJsonObject = await response.json();
  return resultJsonObject;
};

export {
  listProduct,
  viewProduct,
  viewCatNoProduct,
  viewPoNoProduct,
  writeProduct,
  updateProduct,
  deleteProduct,
  getProductWithPcat,
};

/* const BACKEND_SERVER = "http://localhost:8080"; */
const BACKEND_SERVER = "";
const listProductCategory = async () => {
  const response = await fetch(`${BACKEND_SERVER}/productCat`, {
    method: "GET",
  });
  const resultJsonObject = await response.json();
  return resultJsonObject;
};

const listProductCategoryWithProduct = async () => {
  const response = await fetch(`${BACKEND_SERVER}/productCat/product`, {
    method: "GET",
  });
  const resultJsonObject = await response.json();
  return resultJsonObject;
};

const savePCat = async (sendJsonObject) => {
  const response = await fetch(`${BACKEND_SERVER}/productCat`,{
    method: "POST",
      headers: {
        "Content-type": "application/json",
      },
      body: JSON.stringify(sendJsonObject),
    });
    const resultJsonObject = await response.json();
    return resultJsonObject;
};

const updatePCat = async (sendJsonObject) => {
  const response = await fetch(`${BACKEND_SERVER}/productCat`,{
    method: "PUT",
      headers: {
        "Content-type": "application/json",
      },
      body: JSON.stringify(sendJsonObject),
    });
    const resultJsonObject = await response.json();
    return resultJsonObject;
};

const deletePCat = async (catNo) => {
  const response = await fetch(`${BACKEND_SERVER}/productCat/${catNo}`,{
    method: "DELETE",
    });
    const resultJsonObject = await response.json();
    return resultJsonObject;
};


export { listProductCategory, listProductCategoryWithProduct, savePCat, updatePCat, deletePCat};

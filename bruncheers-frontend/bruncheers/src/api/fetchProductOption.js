const BACKEND_SERVER = "http://localhost:8080";

const token = localStorage.getItem("token");

const writeProductOption = async (sendJsonObject) => {
    const response = await fetch(`${BACKEND_SERVER}/productOption/pono`, {
      method: "POST",
      headers: {
        "Content-type": "application/json",
        "Authorization": "Bearer "+`${token}`
      },
      body: JSON.stringify(sendJsonObject),
    });
    const resultJsonObject = await response.json();
    return resultJsonObject;
  };

const deleteProductOption = async (poNo) => {
    const response = await fetch(`${BACKEND_SERVER}/productOption/poNo/${poNo}`, {
      method: "DELETE",
      headers:{"Authorization": "Bearer "+`${token}`}
    });
    const resultJsonObject = await response.json();
    return resultJsonObject;
};

const updateProductOption = async (sendJsonObject) => {
  const response = await fetch(`${BACKEND_SERVER}/productOption/poNo`,{
    method:"PUT",
    headers: {
      'Content-Type':'application/json',
      "Authorization": "Bearer "+`${token}`
  },
  body: JSON.stringify(sendJsonObject),
  })
  const resultJsonObject = await response.json();
  return resultJsonObject;
}


const listProductOption = async (poNoList) => {
    const queryString = poNoList.map(poNo => `poNo=${poNo}`).join('&');
    const response = await fetch(`${BACKEND_SERVER}/productOption/api/productOptions?${queryString}`, {
        method: "GET",
      headers:{"Authorization": "Bearer "+`${token}`}
    });
    const resultJsonObject = await response.json();
    return resultJsonObject;
};

const listProductOptionPNo = async (pNo) => {
  const response = await fetch(`${BACKEND_SERVER}/productOption/api/productOptionPNo/${pNo}`, {
      method: "GET",
      headers:{"Authorization": "Bearer "+`${token}`}
  });
  const resultJsonObject = await response.json();
  return resultJsonObject;
};



export {
    writeProductOption,deleteProductOption,listProductOption,listProductOptionPNo, updateProductOption
};
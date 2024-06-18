const BACKEND_SERVER = "http://localhost:8080";

const insertOrderItem = async(sendJsonObject)=>{
    const response = await fetch(`${BACKEND_SERVER}/oi`,{
        method: "POST",
        headers:{
            "Content-Type": "application/json",
        },
        body: JSON.stringify(sendJsonObject),
    });
    const resultJsonObject = await response.json();
    return resultJsonObject;
};

const deleteOrderItem = async (oiNo) => {
    const response = await fetch(`${BACKEND_SERVER}/oi/${oiNo}`,{
        method: "DELETE",
    });
    const responseJsonObjcet = await response.json();
    return responseJsonObjcet;
};

const listOrderItem = async(oiNo) =>{
    const response = await fetch(`${BACKEND_SERVER}/oi/${oiNo}`,{
        method: "GET",
    });
    const responseJsonObjcet = await response.json();
    console.log(responseJsonObjcet)
    return responseJsonObjcet;
};

const listOrderItems = async(oNo) =>{
    const response = await fetch(`${BACKEND_SERVER}/oi/${oNo}/ois`,{
        method: "GET",
    });
    const responseJsonObjcet = await response.json();
    return responseJsonObjcet;
};

export{insertOrderItem,deleteOrderItem,listOrderItem,listOrderItems};
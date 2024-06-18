const BACKEND_SERVER = "http://192.168.15.31:8080";
const listGuest = async ()=>{
    const response = await fetch(`${BACKEND_SERVER}/guests`);
    const resultJsonObject = await response.json();
    return resultJsonObject;
}
const viewGuest = async (guest_no)=>{
    const response = await fetch(`${BACKEND_SERVER}/guests/${guest_no}`, {
        method: "GET",
      });
      const resultJsonObject = await response.json();
    return resultJsonObject;  
}
const removeGuest=async (guest_no)=>{
    const response=await fetch(`${BACKEND_SERVER}/guests/${guest_no}`,{
        method:"DELETE"
    });
    const responseJsonObject = await response.json();
    return responseJsonObject;
}
const writeGuest= async (sendJsonObject)=>{
    const response=await fetch(`${BACKEND_SERVER}/guests`,
        {
            method:'POST',
            headers:{
                'Content-Type':'application/json'
            },
            body:JSON.stringify(sendJsonObject)
        }
    );
    const resultJsonObject = await response.json();
    return resultJsonObject;
}
const updateGuest= async (sendJsonObject)=>{
    const response=await fetch(`${BACKEND_SERVER}/guests/${sendJsonObject.guestNo}`,
        {
            method:'PUT',
            headers:{
                'Content-Type':'application/json'
            },
            body:JSON.stringify(sendJsonObject)
        }
    );
    const resultJsonObject = await response.json();
    return resultJsonObject;
}


export {listGuest,viewGuest,removeGuest,writeGuest,updateGuest}
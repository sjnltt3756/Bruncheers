const BACKEND_SERVER = "http://localhost:8080";


// 회원 배송지 목록 조회
const viewShipByUser = async (userNo) => {
    const response = await fetch(`${BACKEND_SERVER}/ship/${userNo}/ships`,{
        method: 'GET'
    });
    const resultJsonObject = await response.json();
    return resultJsonObject;
}

// 회원의 기본배송지 조회
const viewShipByUserFirstShip = async (userNo) => {
    const response = await fetch(`${BACKEND_SERVER}/ship/${userNo}/firstShip`,{
        method: 'GET'
    });
    const resultJsonObject = await response.json();
    return resultJsonObject;
}

const viewShip = async (sNo) => {
    const response = await fetch(`${BACKEND_SERVER}/ship/${sNo}`,{
        method: 'GET'
    });
    const resultJsonObject = await response.json();
    console.log(resultJsonObject)
    return resultJsonObject;
}

// 타입별 배송지 목록 조회
const viewShipType = async (sType) => {
    const response = await fetch(`${BACKEND_SERVER}/ship/type/${sType}/ships`, {
        method: 'GET'
    });
    const resultJsonObject = await response.json();
    return resultJsonObject;
}

const removeShip = async (sNo) => {
    const response = await fetch(`${BACKEND_SERVER}/ship/${sNo}`, {
        method: 'DELETE'
    });
}

const saveShip = async (sendJsonObject) => {
    const response = await fetch(`${BACKEND_SERVER}/ship`,
        {
            method: 'POST',
            headers: {
                'Content-Type' : 'application/json'
            },
            body: JSON.stringify(sendJsonObject)
        }
    );
    const resultJsonObject = await response.json();
    return resultJsonObject;
}

const updateShip = async(sNo,sendJsonObject) => {
    const response = await fetch(`${BACKEND_SERVER}/ship/${sNo}`,
        {
            method: 'PUT',
            headers: {
                'Content-Type':'application/json'
            },
            body: JSON.stringify(sendJsonObject)
        }
    );
    const resultJsonObject = await response.json();
    return resultJsonObject;
}

export {viewShipByUser, viewShipByUserFirstShip, viewShip, viewShipType, removeShip, saveShip, updateShip}
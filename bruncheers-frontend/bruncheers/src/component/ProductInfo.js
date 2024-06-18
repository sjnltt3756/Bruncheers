import React from "react";
import productDetail from "../image/productDetailAll.jpg";

function ProductInfo() {
  return (
    <div className="non" style={{textAlign:'center'}}>
      <div className="non">
        <img
          className="non"
          src={productDetail}
          style={{ width: 800, height: 3150 }}
          alt=""
        />
      </div>
    </div>
  );
}

export default ProductInfo;

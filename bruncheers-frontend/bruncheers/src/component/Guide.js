import React from "react";
import guide from "../image/guide.png";

function Guide() {
  return (
    <div className="non" style={{ textAlign: "center" }}>
      <div className="non">
        <img
          src={guide}
          style={{ margin: 10, width: 800, height: 1050 }}
          alt=""
          className="non"
        />
      </div>
    </div>
  );
}

export default Guide;

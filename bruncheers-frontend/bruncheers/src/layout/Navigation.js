import React from "react";
import { Link } from "react-router-dom";

function Navigation() {
  return (
    <div className='shop-top-navigation'>
      <ul>
        <li>
          <Link to={`/product/catNo/${1}`}>&emsp;1일 1식&emsp;</Link>
        </li>
        <li>
          <Link to={`/product/catNo/${2}`}>&emsp;1일 2식&emsp;</Link>
        </li>
        <li>
          <Link to={`/product/catNo/${3}`}>&emsp;1일 3식&emsp;</Link>
        </li>
        <li>
          <Link to='/boards/1'>&emsp;소통 농장&emsp;</Link>
        </li>
      </ul>
    </div>
  );
}

export default Navigation;

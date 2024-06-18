import React, { useState, useEffect } from "react";
import "../css/product.css";
import { Link, useNavigate, useParams } from "react-router-dom";
import * as FetchProduct from "../api/fetchProduct";

const ProductPage = () => {
  const { catNo } = useParams(); // URL 파라미터 추출
  const [products, setProducts] = useState([]);
  const navigate = useNavigate();
  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const result = await FetchProduct.viewCatNoProduct(catNo);
        setProducts(result.data);
      } catch (error) {
        navigate("/error");
      }
    };

    fetchProducts();
  }, [catNo]);

  return (
    <div className='p-container'>
      <div className='product-list-r'>
        <ul className='horizontal-list'>
          {products.map((product) => {
            return (
              <li key={product.pno} className='non'>
                <div className='product-list'>
                  <Link to={`/product_detail/${product.pno}`}>
                    <img
                      className='non'
                      /* src={"/image/" + product.pimage} */
                      src={`http://localhost:8080/product/view/${product.pimage}`}
                      width='400px'
                      alt={product.pname}
                      style={{ borderRadius: "5%" }}
                    />
                  </Link>
                  <div className='product-list'>
                    <span className='non'>
                      <Link to={`/product_detail/${product.pno}`}>
                        <b style={{ color: "#49a149" }}>{product.pname}</b>
                      </Link>
                    </span>
                  </div>
                  <div className='product-list'>
                    <span className='p-content'>{product.pdetail}</span>
                    <div className='p-content-divider'></div>
                  </div>
                  <div className='product-list'>
                    {/* if 문을 이용하여 조건에 따라 렌더링 */}
                    {product.pprice && product.pno > 0 && (
                      <span className='p-ori-price' style={{ display: "block" }}></span>
                    )}
                    <span className='p-price' style={{ display: "block" }}>
                      {product.pprice.toLocaleString()}원
                    </span>
                  </div>
                </div>
              </li>
            );
          })}
        </ul>
      </div>
    </div>
  );
};

export default ProductPage;

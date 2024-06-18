import React from "react";
import "../css/admin.css";
import "../css/product.css";
import { Navigate, Route, Routes } from "react-router";
import AdminMain from "./AdminMain";
import AdminProduct from "./AdminProduct";
import AdminUser from "./AdminUser";
import AdminOrder from "./AdminOrder";
import { Link } from "react-router-dom";

function AdminDashBoard() {
  return (
    <>
     {/*   <header
        className="navbar sticky-top bg-dark flex-md-nowrap p-0 shadow"
      >
        <a
          className="navbar-brand col-md-3 col-lg-2 me-0 px-3 fs-6 text-white"
          href="#"
        >
          브런치얼스(Bruncheers)
        </a>
      </header> */}

      <div className="container-fluid">
        <div className="row">
        <div className="sidebar col-md-3 col-lg-2 p-0">
            <div
              className="offcanvas-md offcanvas-end"
              tabIndex="-1"
              id="sidebarMenu"
              aria-labelledby="sidebarMenuLabel"
            >
              <div className="offcanvas-header">
                <h5 className="offcanvas-title" id="sidebarMenuLabel">
                  Company name
                </h5>
                <button
                  type="button"
                  className="btn-close"
                  data-bs-dismiss="offcanvas"
                  data-bs-target="#sidebarMenu"
                  aria-label="Close"
                ></button>
              </div>
              <div className="offcanvas-body d-md-flex flex-column p-0 pt-lg-3 overflow-y-auto">
                <ul className="nav flex-column">
                  <Link to={"main"}>
                  <li className="nav-item">
                    <div
                      className="nav-link d-flex align-items-center gap-2 active"
                      aria-current="page"
                      href="#"
                    >
                      <svg className="bi">
                        <use href="#house-fill" />
                      </svg>
                      메인
                    </div>
                  </li>
                  </Link>
                  <Link to={"order"}>

                  <li className="nav-item">
                    <div
                      className="nav-link d-flex align-items-center gap-2"
                      href="#"
                    >
                      <svg className="bi">
                        <use href="#file-earmark" />
                      </svg>
                      주문
                    </div>
                  </li>
                  </Link>
                  <Link to={"product"}>

                  <li className="nav-item">
                    <div
                      className="nav-link d-flex align-items-center gap-2"
                      href="#"
                    >
                      <svg className="bi">
                        <use href="#cart" />
                      </svg>
                      상품
                    </div>
                  </li>
                  </Link>
                  <Link to={"user"}>
                  <li className="nav-item">
                    <div
                      className="nav-link d-flex align-items-center gap-2"
                      href="/admin/user"
                    >
                      <svg className="bi">
                        <use href="#people" />
                      </svg>
                      회원
                    </div>
                  </li>
                  </Link>
                </ul>
              </div>
            </div>
          </div>
          <main className="col-md-9 ms-sm-auto col-lg-10 px-md-4">
            <Routes>
              <Route
                path="/"
                exact
                element={<Navigate to="/admin/main"></Navigate>}
              />
              <Route path="main" element={<AdminMain />} />
              <Route path="product" element={<AdminProduct />} />
              <Route path="user" element={<AdminUser />} />
              <Route path="order" element={<AdminOrder />} />
            </Routes>
          </main>
        </div>
      </div>
    </>
  );
}

export default AdminDashBoard;

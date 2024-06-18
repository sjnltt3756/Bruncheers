import React, { useState, useEffect, useRef } from "react";
import Modal from "react-modal";
import * as fetchProductCat from "../api/fetchProductCat";
import * as fetchProductOption from "../api/fetchProductOption";
import * as fetchAdmin from "../api/fetchAdmin";
import { useNavigate } from "react-router";
import * as StatusCode from "../api/responseStatusCode.js";
import { notify, Toast } from "../util/toast";
function AdminProduct() {
  const [productCategoryList, setProductCategoryList] = useState([]);
  const [currentProduct, setCurrentProduct] = useState(null);
  const [currentCat, setCurrentCat] = useState({ catNo: 0, catName: "" });
  const [productCategoryWithProductList, setProductCategoryWithProductList] = useState([]);

  const formRef = useRef();
  const productSaveFormRef = useRef();
  const productUpdateFormRef = useRef();
  const navigate = useNavigate();

  //상품 카테고리 등록
  const handleCreate = async () => {
    try {
      const CreateCategory = { ...currentCat };
      const response = await fetchProductCat.savePCat(CreateCategory);
      const responseJsonObjcet = await fetchProductCat.listProductCategoryWithProduct();
      setProductCategoryWithProductList(responseJsonObjcet.data);
      ClosePCatSaveModal();
    } catch (error) {
      navigate("/error");
    }
  };

  // 상품 카테고리 수정 버튼 클릭했을 때 호출되는 함수
  const handleUpdate = async () => {
    try {
      const updatedCategory = { ...currentCat };
      const response = await fetchProductCat.updatePCat(updatedCategory);
      const responseJsonObjcet = await fetchProductCat.listProductCategoryWithProduct();
      setProductCategoryWithProductList(responseJsonObjcet.data);
      closePCatModifyModal();
    } catch (error) {
      navigate("/error");
    }
  };

  // 상품 카테고리 삭제
  const handleDeleteCat = async (catNo) => {
    try {
      const response = await fetchProductCat.deletePCat(catNo);

      if (response) {
        setProductCategoryList((prevList) => prevList.filter((cat) => cat.catNo !== catNo));
        if (currentCat?.catNo === catNo) {
          setCurrentCat(null);
        }
        loadProductCategoryList();

        const responseJsonObjcet = await fetchProductCat.listProductCategoryWithProduct();
        setProductCategoryWithProductList(responseJsonObjcet.data);
      } else {
        notify("warning", "삭제 실패");
      }
    } catch (error) {
      navigate("/error");
    }
  };

  // 페이지가 처음 로드될 때 카테고리 리스트를 불러오기
  useEffect(() => {
    loadProductCategoryList();
  }, []);

  // 카테고리 리스트 다시 불러오는 함수
  const loadProductCategoryList = async () => {
    try {
      const response = await fetchProductCat.listProductCategory();
      setProductCategoryList(response.data);
    } catch (error) {
      navigate("/error");
    }
  };

  Modal.setAppElement("#container");

  /* 상품 카테고리 받아오기 */
  useEffect(() => {
    (async () => {
      const responseJsonObjcet = await fetchProductCat.listProductCategory();
      setProductCategoryList(responseJsonObjcet.data);
    })();
  }, []);

  /* 상품 좀 제발 받아오자 제발제발제발 */
  useEffect(() => {
    (async () => {
      const responseJsonObjcet = await fetchProductCat.listProductCategoryWithProduct();
      setProductCategoryWithProductList(responseJsonObjcet.data);
    })();
  }, []);

  /* 상품 등록 모달 */
  const [productInsertModalIsOpen, setProductInsertIsOpen] = useState(false);

  function OpenProductInsertModal() {
    setProductInsertIsOpen(true);
  }

  function closeProductInsertModal() {
    setProductInsertIsOpen(false);
  }

  const productModalStyle = {
    content: {
      top: "50%",
      left: "50%",
      right: "auto",
      bottom: "auto",
      marginRight: "-50%",
      transform: "translate(-50%, -50%)",
      width: "800px",
      height: "890px",
    },
  };

  /* 상품등록 */
  const [savedProduct, setSavedProduct] = useState({
    pdetail: "",
    pname: "",
    pprice: 0,
    pimage: "",
    pdeimg: "",
    catNo: "",
  });

  /* 상품등록 폼 요소 onchange 메소드 */
  const handleChangeProductSaveForm = (e) => {
    if (e.target.name === "catNo") {
      // 카테고리 선택이면 카테고리 넘버 업데이트
      setSavedProduct({
        ...savedProduct,
        [e.target.name]: e.target.value,
      });
    } else {
      setSavedProduct({
        ...savedProduct,
        [e.target.name.split("save")[1]]: e.target.value,
      });
    }
  };

  const [selectedpimgFile, setSelectedpimgFile] = useState([]); // 선택된 파일을 상태에 저장
  const [selectedpdimgFile, setSelectedpdimgFile] = useState([]); // 선택된 파일을 상태에 저장

  const handlepimgFileChange = (e) => {
    setSelectedpimgFile(e.target.files);
  };
  const handlepdeimgFileChange = (e) => {
    setSelectedpdimgFile(e.target.files);
  };

  // 상품 등록
  const saveProductAction = async () => {
    // 상품 등록 유효성 검사
    if (productSaveFormRef.current.catNo.value === "카테고리를 선택하세요.") {
      notify("warning", "카테고리를 선택해주세요.");
      productSaveFormRef.current.catNo.focus();
      return;
    }
    if (productSaveFormRef.current.savepname.value === "") {
      notify("warning", "상품이름을 입력해주세요.");
      productSaveFormRef.current.savepname.focus();
      return;
    }
    if (productSaveFormRef.current.savepprice.value === "") {
      notify("warning", "가격을 입력해주세요.");
      productSaveFormRef.current.savepprice.focus();
      return;
    }
    if (productSaveFormRef.current.savepdetail.value === "") {
      notify("warning", "상품설명을 입력해주세요.");
      productSaveFormRef.current.savepdetail.focus();
      return;
    }

    if (!selectedpimgFile[0]) {
      notify("warning", "상품 이미지를 등록해주세요.");
      return;
    }
    if (!selectedpdimgFile[0]) {
      notify("warning", "상품 설명 이미지를 등록해주세요.");
      return;
    }

    const formData = new FormData();
    formData.append("pImage", selectedpimgFile[0]);
    formData.append("pDeimg", selectedpdimgFile[0]);

    formData.append("product", new Blob([JSON.stringify(savedProduct)], { type: "application/json" }));

    const responseJsonObjcet = await fetchAdmin.saveProduct(formData);
    if (responseJsonObjcet.status === StatusCode.CREATE_PRODUCT) {
      setProductInsertIsOpen(false);
      notify("success", "상품 등록이 완료되었습니다.");
      const responseJsonObjcet = await fetchProductCat.listProductCategoryWithProduct();
      setProductCategoryWithProductList(responseJsonObjcet.data);
    }
  };
  /* 상품 수정 모달 */
  const [productModifyModalIsOpen, setProductModifyIsOpen] = useState({});
  function OpenProductModifyModal(product, prevCatNo) {
    setCurrentProduct(product);
    setUpdateProduct({
      pno:product.pno,
      catNo: prevCatNo,
      pname: product.pname,
      pprice: product.pprice,
      pdetail: product.pdetail,
      pimage: product.pimage,
      pdeimg: product.pdeimg,
    });
    setProductModifyIsOpen((prev) => ({ ...prev, [product.pno]: true }));
  }

  function closeProductModifyModal() {
    setCurrentProduct(null);
    setProductModifyIsOpen((prev) => ({
      ...prev,
      [currentProduct.pno]: false,
    }));
  }

  /* 상품 수정  */
  const [updateProduct, setUpdateProduct] = useState({
    pno:"",
    pdetail: "",
    pname: "",
    pprice: 0,
    pimage: "",
    pdeimg: "",
    catNo: "",
    preg: new Date() 
  });

  /* 상품수정 폼 요소 onchange 메소드 */
  const handleChangeProductUpdateForm = (e) => {
    if (e.target.name === "updatepname") {
      setCurrentProduct({
        ...currentProduct,
        pname: "",
      });
    }
    if (e.target.name === "updatepprice") {
      setCurrentProduct({
        ...currentProduct,
        pprice: 0,
      });
    }
    if (e.target.name === "updatepdetail") {
      setCurrentProduct({
        ...currentProduct,
        pdetail: "",
      });
    }
    if (e.target.name === "updatepdetail") {
      setCurrentProduct({
        ...currentProduct,
        pdetail: "",
      });
    }
    if (e.target.name === "catNo") {
      // 카테고리 선택이면 카테고리 넘버 업데이트
      setUpdateProduct({
        ...updateProduct,
        [e.target.name]: e.target.value,
      });
    } else {
      setUpdateProduct({
        ...updateProduct,
        [e.target.name.split("update")[1]]: e.target.value,
      });
    }
  };

  const updateProductAction = async () => {
    // 상품 등록 유효성 검사
    if (productUpdateFormRef.current.catNo.value === "카테고리를 선택하세요.") {
      alert("카테고리를 선택해주세요.");
      productUpdateFormRef.current.catNo.focus();
      return;
    }
    if (productUpdateFormRef.current.updatepname.value === "") {
      alert("상품이름을 입력해주세요.");
      productUpdateFormRef.current.updatepname.focus();
      return;
    }
    if (productUpdateFormRef.current.updatepprice.value === "") {
      alert("가격을 입력해주세요.");
      productUpdateFormRef.current.updatepprice.focus();
      return;
    }
    if (productUpdateFormRef.current.updatepdetail.value === "") {
      alert("상품설명을 입력해주세요.");
      productUpdateFormRef.current.updatepdetail.focus();
      return;
    }

    const currentTime = new Date();

    setUpdateProduct({
      ...updateProduct,
      preg:currentTime,
    });

    const formData = new FormData();
    formData.append("pImage", selectedpimgFile[0]);
    formData.append("pDeimg", selectedpdimgFile[0]);

    formData.append(
      "product",
      new Blob([JSON.stringify(updateProduct)], { type: "application/json" })
    );

    console.log(updateProduct)

     const responseJsonObjcet = await fetchAdmin.saveProduct(formData);
    if (responseJsonObjcet.status === StatusCode.CREATE_PRODUCT) {
      setProductModifyIsOpen(false);
      alert("상품 수정이 완료되었습니다.");
      const responseJsonObjcet =
        await fetchProductCat.listProductCategoryWithProduct();
      setProductCategoryWithProductList(responseJsonObjcet.data);
    }  
  };

  /*  상품 삭제 */
  const deleteProductAction = async (pno) => {
    // eslint-disable-next-line no-restricted-globals
    const res = confirm("정말 삭제하시겠습니까?");
    if (res) {
      await fetchAdmin.deleteProduct(pno);
      const responseJsonObjcet = await fetchProductCat.listProductCategoryWithProduct();
      setProductCategoryWithProductList(responseJsonObjcet.data);
    }
  };

  const productModifyStyle = {
    content: {
      top: "50%",
      left: "50%",
      right: "auto",
      bottom: "auto",
      marginRight: "-50%",
      transform: "translate(-50%, -50%)",
      width: "800px",
      height: "890px",
    },
  };

  /* 카테고리 등록 모달 */
  const [pCatSaveModalIsOpen, setpCatSaveModalIsOpen] = useState(false);

  function OpenPCatSaveModal() {
    setpCatSaveModalIsOpen(true);
  }
  function ClosePCatSaveModal() {
    setpCatSaveModalIsOpen(false);
  }

  /* 카테고리 수정 모달 */
  const [pCatModifyModifyModalIsOpen, setpCatModifyModifyModalIsOpen] = useState(false);

  function OpenPCatModifyModal(productcategory) {
    setCurrentCat(productcategory);
    setpCatModifyModifyModalIsOpen((prev) => ({
      ...prev,
      [productcategory.catNo]: true,
    }));
  }

  function closePCatModifyModal() {
    setProductOptionIsOpen(false);
    setCurrentCat(null);
    setpCatModifyModifyModalIsOpen((prev) => ({
      ...prev,
      [currentCat.catNo]: false,
    }));
  }

  const pCatModifyModalStyle = {
    content: {
      top: "50%",
      left: "50%",
      right: "auto",
      bottom: "auto",
      marginRight: "-50%",
      transform: "translate(-50%, -50%)",
      width: "300px",
      height: "200px",
    },
  };

  /* 옵션 관리 모달 */
  const [productOptionIsOpen, setProductOptionIsOpen] = useState({});

  function OpenProductOptionModal(product) {
    setCurrentProduct(product);
    setProductOptionIsOpen((prev) => ({
      ...prev,
      [product.pno]: true,
    }));
  }

  function closeProductOptionModal() {
    setProductOptionIsOpen((prev) => ({
      ...prev,
      [currentProduct.pno]: false,
    }));
    setCurrentProduct(null);
  }
  const productOptionModalStyle = {
    content: {
      top: "50%",
      left: "50%",
      right: "auto",
      bottom: "auto",
      marginRight: "-50%",
      transform: "translate(-50%, -50%)",
      width: "auto",
      height: "auto",
    },
  };

  /* 상품 옵션 삭제 */
  const PoDeleteAction = async (poNo) => {
    await fetchProductOption.deleteProductOption(poNo);

    // 삭제 후 목록에서도 사라지게
    setCurrentProduct((prevProduct) => ({
      ...prevProduct,
      productOptionList: prevProduct.productOptionList.filter((option) => option.poNo !== poNo),
    }));
  };

  /* 상품 옵션 수정 */
  const [isModifyList, setIsModifyList] = useState({});

  function toggleModify(poNo) {
    setIsModifyList((prev) => ({
      ...prev,
      [poNo]: !prev[poNo],
    }));
  }

  function handleInputChange(event, productOption) {
    const { name, value } = event.target;
    setCurrentProduct((prevProduct) => ({
      ...prevProduct,
      productOptionList: prevProduct.productOptionList.map((option) => {
        if (option.poNo === productOption.poNo) {
          return {
            ...option,
            [name]: value,
          };
        }
        return option;
      }),
    }));
  }

  const handleProductOptionUpdate = async (productOption) => {
    try {
      const updatedOption = await fetchProductOption.updateProductOption(productOption);
    } catch (error) {
      navigate("/error");
    }
  };

  /* 옵션 등록 모달 */
  const [saveProductOptionIsOpen, setSaveProductOptionIsOpen] = useState();

  function OpenSaveProductOptionModal(parentPno) {
    setCurrentProduct((prev) => ({
      ...prev,
      pno: parentPno,
    }));
    setSaveProductOptionIsOpen(true);
  }
  function CloseSaveProductOptionModal() {
    setSaveProductOptionIsOpen(false);
  }

  function AllCloseModal() {
    setSaveProductOptionIsOpen(false);
    setProductOptionIsOpen(false);
  }
  const poSaveModalStyle = {
    content: {
      top: "50%",
      left: "50%",
      right: "auto",
      bottom: "auto",
      marginRight: "-50%",
      transform: "translate(-50%, -50%)",
      width: "300px",
      height: "300px",
    },
  };

  /* 상품 옵션 등록 */
  const [poName, setPoName] = useState("");
  const [poPrice, setPoPrice] = useState("");

  const saveProductOptionAction = async () => {
    const parentPno = currentProduct.pno;

    try {
      const sendJsonObject = {
        poName: poName,
        poPrice: poPrice,
        pno: parentPno,
      };

      const responseJsonObject = await fetchProductOption.writeProductOption(sendJsonObject);

      AllCloseModal();
      setPoName("");
      setPoPrice("");
    } catch (error) {
      navigate("/error");
    }
  };

  /* 상품 카테고리 등록 */

  return (
    <>
      <Toast/>
      <div className="p-3">
        <h2 className="productTitle">상품 리스트</h2>
        <p className="pt-desc">** 상품 관련 변경 시 기록 꼭 해놓기</p>
        <button className="admin-product-btn " onClick={OpenProductInsertModal}>
          상품등록
        </button>
        <button
          className="admin-product-btn"
          onClick={OpenPCatSaveModal}
          style={{ marginLeft: "10px" }}
        >
          카테고리 등록
        </button>
        {/* 상품 등록 모달 시작 */}
        <Modal
          isOpen={productInsertModalIsOpen}
          onRequestClose={closeProductInsertModal}
          style={productModalStyle}
          contentLabel="productInsertModal"
        >
          <div className="p-4">
            <h4 className="mb-4">상품등록</h4>
            <form ref={productSaveFormRef}>
              <div className="row g-3">
                {/* 상품 카테고리 선택 */}
                <div className="col-12">
                  <label htmlFor="catNo" className="form-label">
                    카테고리 선택
                  </label>
                  <div className="form-group">
                    <select
                      className="form-select"
                      id="catNo"
                      name="catNo"
                      value={savedProduct.catNo}
                      onChange={handleChangeProductSaveForm}
                      style={{ color: "rgba(33, 37, 41, 0.75)" }}
                    >
                      <option>카테고리를 선택하세요.</option>
                      {productCategoryList.map((productCategory) => (
                        <option
                          key={productCategory.catNo}
                          value={productCategory.catNo}
                        >
                          {productCategory.catName}
                        </option>
                      ))}
                    </select>
                  </div>
                </div>
                {/* 상품이름 */}
                <div className="col-12">
                  <label htmlFor="savepname" className="form-label">
                    상품이름
                  </label>
                  <div className="input-group has-validation">
                    <input
                      type="text"
                      className="form-control"
                      id="savepname"
                      name="savepname"
                      onChange={handleChangeProductSaveForm}
                      required
                    />
                  </div>
                </div>
                {/* 상품가격 */}
                <div className="col-12">
                  <label htmlFor="savepprice" className="form-label">
                    상품가격
                  </label>
                  <div className="input-group has-validation">
                    <input
                      type="number"
                      className="form-control"
                      id="savepprice"
                      name="savepprice"
                      placeholder="*숫자만 기입"
                      onChange={handleChangeProductSaveForm}
                      required
                    />
                  </div>
                </div>
                {/* 상품 설명 시작 */}
                <div className="form-group">
                  <label
                    htmlFor="savepdetail"
                    className="form-label"
                    style={{ marginTop: "10px" }}
                  >
                    상품설명
                  </label>
                  <textarea
                    id="savepdetail"
                    className="pDetailTextarea form-control"
                    name="savepdetail"
                    maxLength="1000"
                    style={{ height: "100px" }}
                    onChange={handleChangeProductSaveForm}
                  />
                </div>
                {/* 사진 등록 시작 */}
                <div className="mt-5 form-group">
                  <label className="form-label">
                    상품 이미지를 등록해주세요
                  </label>
                  <p style={{ fontSize: "13px" }}>
                    {" "}
                    * 사진은 500X500 으로 미리 준비해서 넣어주세요.{" "}
                  </p>
                  <div className="rounded-md border bg-gray-50 p-4 shadow-md w-24 h-24 mt-2">
                    <label
                      htmlFor="savepimage"
                      className="justify-center h-full flex flex-col items-center gap-2 cursor-pointer"
                    ></label>
                    <input
                      id="savepimage"
                      type="file"
                      name="savepimage"
                      className="hidden"
                      onChange={handlepimgFileChange}
                    />
                  </div>
                </div>
                {/* 사진 등록 끝 */}
                {/* 사진 등록 시작 */}
                <div className="mt-5 form-group">
                  <label className="form-label">
                    상품 설명 이미지를 등록해주세요
                  </label>
                  <div className="rounded-md border bg-gray-50 p-4 shadow-md w-24 h-24 mt-2">
                    <label
                      htmlFor="savepdeimg"
                      className="justify-center h-full flex flex-col items-center gap-2 cursor-pointer"
                    ></label>
                    <input
                      id="savepdeimg"
                      type="file"
                      name="savepdeimg"
                      className="hidden"
                      onChange={handlepdeimgFileChange}
                    />
                  </div>
                </div>
                {/* 사진 등록 끝 */}

                <div className="btn-group">
                  <a
                    className="coup-table-btn btn-delete"
                    onClick={closeProductInsertModal}
                  >
                    취소
                  </a>
                  <a
                    className="coup-table-btn btn-choose"
                    onClick={(e) => {
                      saveProductAction();
                      e.preventDefault();
                    }}
                  >
                    등록
                  </a>
                </div>
              </div>
            </form>
          </div>
        </Modal>
        {/* 상품 등록 모달 끝 */}
        {/* 카테고리 등록 모달 시작 */}
        <Modal
          isOpen={pCatSaveModalIsOpen}
          onRequestClose={ClosePCatSaveModal}
          style={pCatModifyModalStyle}
          contentLabel="pCatSaveModal"
        >
          <div className="col-12">
            <label htmlFor="pName" className="form-label">
              카테고리 이름
            </label>
            <div className="input-group has-validation">
              <input
                type="text"
                className="form-control"
                id="pName"
                name="pName"
                value={currentCat?.catName || ""}
                onChange={(e) =>
                  setCurrentCat((prev) => ({
                    ...prev,
                    catName: e.target.value,
                  }))
                }
                required
              />
            </div>
            <div className="btn-group">
              <a
                className="coup-table-btn btn-delete"
                onClick={ClosePCatSaveModal}
              >
                취소
              </a>
              <a className="coup-table-btn btn-choose" onClick={handleCreate}>
                등록
              </a>
            </div>
          </div>
        </Modal>
        {/* 카테고리 등록 모달 끝 */}
        {/* 카테고리 별 상품 시작 */}
        {Object.entries(productCategoryWithProductList).map(
          ([catNo, category]) => (
            <div key={catNo} className="p-container">
              <div className="category-group">
                <div className="category-header">
                  <h3 className="catTitle">카테고리: {category.catName}</h3>
                  <div>
                    <button
                      className="admin-product-btn admin-pcat-btn"
                      onClick={() => handleDeleteCat(category.catNo)}
                    >
                      카테고리 삭제
                    </button>
                    <button
                      className="admin-product-btn admin-pcat-btn "
                      onClick={() => {
                        OpenPCatModifyModal(category);
                      }}
                    >
                      카테고리 수정
                    </button>
                    {/* 카테고리 수정 모달 시작 */}
                    <Modal
                      isOpen={
                        pCatModifyModifyModalIsOpen[category.catNo] || false
                      }
                      onRequestClose={closePCatModifyModal}
                      style={pCatModifyModalStyle}
                      contentLabel="pCatModifyModal"
                    >
                      <div className="col-12">
                        <label htmlFor="pName" className="form-label">
                          카테고리 이름
                        </label>
                        <div className="input-group has-validation">
                          <input
                            type="text"
                            className="form-control"
                            id="pName"
                            name="pName"
                            value={currentCat?.catName || ""}
                            onChange={(e) =>
                              setCurrentCat((prev) => ({
                                ...prev,
                                catName: e.target.value,
                              }))
                            }
                            required
                          />
                        </div>
                        <div className="btn-group">
                          <a
                            className="coup-table-btn btn-delete"
                            onClick={closePCatModifyModal}
                          >
                            취소
                          </a>
                          <a
                            className="coup-table-btn btn-choose"
                            onClick={handleUpdate}
                          >
                            수정
                          </a>
                        </div>
                      </div>
                    </Modal>
                    {/* 카테고리 수정 모달 끝 */}
                  </div>
                </div>
                <div className="product-list-r">
                  <table className="product-table">
                    <thead>
                      <tr>
                        <th>이미지</th>
                        <th>상품명</th>
                        <th>상품 설명</th>
                        <th>상품 등록일</th>
                        <th>판매가</th>
                        <th></th>
                      </tr>
                    </thead>
                    <tbody>
                      {category.productDtoList.length > 0 ? (
                        category.productDtoList.map((product) => (
                          <tr key={product.pno}>
                            <td>
                              <img
                                /* src={"/image/" + product.pimage} */
                                src={`http://localhost:8080/product/view/${product.pimage}`}
                                alt={product.pname}
                              />
                            </td>
                            <td>
                              <b>{product.pname}</b>
                            </td>
                            <td className="p-content">{product.pdetail}</td>
                            <td>{product.preg.substring(0, 10)}</td>
                            <td className="p-price">
                              {product.pprice.toLocaleString()}원
                            </td>
                            <td>
                              <div
                                style={{
                                  display: "flex",
                                  flexDirection: "column",
                                  gap: "10px",
                                }}
                              >
                                <button
                                  className="admin-product-btn"
                                  onClick={() =>
                                    OpenProductModifyModal(
                                      product,
                                      category.catNo
                                    )
                                  }
                                >
                                  상품수정
                                </button>
                                {/* 상품 수정 모달 시작 */}
                                <Modal
                                  isOpen={
                                    productModifyModalIsOpen[product.pno] ||
                                    false
                                  }
                                  onRequestClose={closeProductModifyModal}
                                  style={productModifyStyle}
                                  contentLabel="productModifyModal"
                                >
                                  {currentProduct &&
                                    currentProduct.pno === product.pno && (
                                      <div className="p-4">
                                        <h4 className="mb-4">상품수정</h4>
                                        <form
                                          ref={productUpdateFormRef}
                                          className="needs-validation"
                                          noValidate
                                        >
                                          <div className="row g-3">
                                            {/* 상품 카테고리 선택 */}
                                            <div className="col-12">
                                              <label
                                                htmlFor="catNo"
                                                className="form-label"
                                              >
                                                카테고리 선택
                                              </label>
                                              <div className="form-group">
                                                <select
                                                  className="form-select"
                                                  name="catNo"
                                                  id="catNo"
                                                  style={{
                                                    color:
                                                      "rgba(33, 37, 41, 0.75)",
                                                  }}
                                                  value={updateProduct.catNo ||category.catNo}
                                                  onChange={
                                                    handleChangeProductUpdateForm
                                                  }
                                                >
                                                  <option>
                                                    카테고리를 선택하세요.
                                                  </option>
                                                  {productCategoryList.map(
                                                    (productCategory) => (
                                                      <option
                                                        key={
                                                          productCategory.catNo
                                                        }
                                                        value={
                                                          productCategory.catNo
                                                        }
                                                      >
                                                        {
                                                          productCategory.catName
                                                        }
                                                      </option>
                                                    )
                                                  )}
                                                </select>
                                              </div>
                                            </div>
                                            {/* 상품이름 */}
                                            <div className="col-12">
                                              <label
                                                htmlFor="pName"
                                                className="form-label"
                                              >
                                                상품이름
                                              </label>
                                              <div className="input-group has-validation">
                                                <input
                                                  type="text"
                                                  className="form-control"
                                                  id="updatepname"
                                                  name="updatepname"
                                                  value={
                                                    updateProduct.pname ||
                                                    currentProduct.pname
                                                  }
                                                  onChange={
                                                    handleChangeProductUpdateForm
                                                  }
                                                  required
                                                />
                                              </div>
                                            </div>
                                            {/* 상품가격 */}
                                            <div className="col-12">
                                              <label
                                                htmlFor="pPrice"
                                                className="form-label"
                                              >
                                                상품가격
                                              </label>
                                              <div className="input-group has-validation">
                                                <input
                                                  type="number"
                                                  className="form-control"
                                                  id="updatepprice"
                                                  name="updatepprice"
                                                  placeholder="*숫자만 기입"
                                                  value={
                                                    updateProduct.pprice ||
                                                    currentProduct.pprice
                                                  }
                                                  onChange={
                                                    handleChangeProductUpdateForm
                                                  }
                                                  required
                                                />
                                              </div>
                                            </div>
                                            {/* 상품 설명 시작 */}
                                            <div className="form-group">
                                              <label
                                                htmlFor="Textarea"
                                                className="form-label"
                                                style={{ marginTop: "10px" }}
                                              >
                                                상품설명
                                              </label>
                                              <textarea
                                                id="updatepdetail"
                                                className="pDetailTextarea form-control"
                                                name="updatepdetail"
                                                maxLength="1000"
                                                style={{ height: "100px" }}
                                                value={
                                                  updateProduct.pdetail ||
                                                  currentProduct.pdetail
                                                }
                                                onChange={
                                                  handleChangeProductUpdateForm
                                                }
                                              />
                                            </div>
                                            {/* 사진 등록 시작 */}
                                            <div className="mt-5 form-group">
                                              <label className="form-label">
                                                상품 이미지를 등록해주세요
                                              </label>
                                              <p style={{ fontSize: "13px" }}>
                                                * 사진은 500X500 으로 미리
                                                준비해서 넣어주세요.{" "}
                                              </p>
                                              <div className="rounded-md border bg-gray-50 p-4 shadow-md w-24 h-24 mt-2">
                                                <label
                                                  htmlFor="updatepimage"
                                                  className="justify-center h-full flex flex-col items-center gap-2 cursor-pointer"
                                                ></label>
                                                <input
                                                  id="updatepimage"
                                                  name="updatepimage"
                                                  type="file"
                                                  className="hidden"
                                                  multiple="multiple"
                                                  onChange={
                                                    handlepdeimgFileChange
                                                  }
                                                />
                                              {/*   <img
                                                  alt="product"
                                                  src={`http://localhost:8080/product/view/${product.pimage}`}
                                                /> */}
                                              </div>
                                            </div>
                                            {/* 사진 등록 끝 */}
                                            {/* 사진 등록 시작 */}
                                            <div className="mt-5 form-group">
                                              <label className="form-label">
                                                상품 설명 이미지를 등록해주세요
                                              </label>
                                              <div className="rounded-md border bg-gray-50 p-4 shadow-md w-24 h-24 mt-2">
                                                <label
                                                  htmlFor="updatepdeimg"
                                                  className="justify-center h-full flex flex-col items-center gap-2 cursor-pointer"
                                                ></label>
                                                <input
                                                  id="updatepdeimg"
                                                  type="file"
                                                  name="updatepdeimg"
                                                  className="hidden"
                                                  multiple="multiple"
                                                  onChange={
                                                    handlepdeimgFileChange
                                                  }
                                                />
                                               {/*  <img
                                                  alt="product"
                                                  src={`http://localhost:8080/product/view/${product.pimage}`}
                                                /> */}
                                              </div>
                                            </div>
                                            {/* 사진 등록 끝 */}

                                            <div className="btn-group">
                                              <a
                                                className="coup-table-btn btn-delete"
                                                onClick={
                                                  closeProductModifyModal
                                                }
                                              >
                                                취소
                                              </a>

                                              <a
                                                className="coup-table-btn btn-choose"
                                                onClick={(e) => {
                                                  updateProductAction();
                                                  e.preventDefault();
                                                }}
                                              >
                                                등록
                                              </a>
                                            </div>
                                          </div>
                                        </form>
                                      </div>
                                    )}
                                </Modal>
                                {/* 상품 수정 모달 끝 */}

                                <button
                                  className="admin-product-btn"
                                  onClick={(e) => {
                                    deleteProductAction(product.pno);
                                  }}
                                >
                                  상품삭제
                                </button>
                                <button
                                  className="admin-product-btn"
                                  onClick={() => {
                                    OpenProductOptionModal(product);
                                  }}
                                >
                                  옵션관리
                                </button>
                                {/* 옵션 관리 모달 시작 */}
                                <Modal
                                  isOpen={
                                    productOptionIsOpen[product.pno] || false
                                  }
                                  onRequestClose={closeProductOptionModal}
                                  style={productOptionModalStyle}
                                  contentLabel="productOptionModal"
                                >
                                  <div className="product-option-table-container">
                                    <button
                                      className="admin-product-option-btn"
                                      style={{ margin: "0 0 15px 0" }}
                                      onClick={() =>
                                        OpenSaveProductOptionModal(product.pno)
                                      }
                                    >
                                      추가
                                    </button>
                                    <table
                                      className="product-option-table"
                                      style={{ minWidth: "700px" }}
                                    >
                                      <thead>
                                        <tr>
                                          <th>옵션이름</th>
                                          <th>옵션가격</th>
                                          <th></th>
                                        </tr>
                                      </thead>
                                      <tbody>
                                        {currentProduct &&
                                          currentProduct.pno === product.pno &&
                                          currentProduct.productOptionList.map(
                                            (productOption) => {
                                              const isModify =
                                                isModifyList[
                                                  productOption.poNo
                                                ] || false;
                                              return (
                                                <tr key={productOption.poNo}>
                                                  <td
                                                    style={{
                                                      backgroundColor: isModify
                                                        ? "#fff"
                                                        : "#f0f0f0",
                                                    }}
                                                  >
                                                    <input
                                                      id="poName"
                                                      type="text"
                                                      name="poName"
                                                      value={
                                                        productOption.poName
                                                      }
                                                      disabled={!isModify}
                                                      onChange={(event) =>
                                                        handleInputChange(
                                                          event,
                                                          productOption
                                                        )
                                                      }
                                                    />
                                                  </td>
                                                  <td
                                                    style={{
                                                      backgroundColor: isModify
                                                        ? "#fff"
                                                        : "#f0f0f0",
                                                      border: isModify
                                                        ? "1px solid #ddd"
                                                        : "1px solid #ccc",
                                                    }}
                                                  >
                                                    <input
                                                      id="poPrice"
                                                      type="text"
                                                      name="poPrice"
                                                      value={
                                                        productOption.poPrice
                                                      }
                                                      disabled={!isModify}
                                                      onChange={(event) =>
                                                        handleInputChange(
                                                          event,
                                                          productOption
                                                        )
                                                      }
                                                    />
                                                  </td>
                                                  <td>
                                                    <button
                                                      className="admin-product-option-btn"
                                                      onClick={() => {
                                                        toggleModify(
                                                          productOption.poNo
                                                        );
                                                        if (isModify) {
                                                          handleProductOptionUpdate(
                                                            productOption
                                                          );
                                                        }
                                                      }}
                                                    >
                                                      {isModify
                                                        ? "완료"
                                                        : "수정"}
                                                    </button>
                                                    <button
                                                      className="admin-product-option-btn"
                                                      onClick={() =>
                                                        PoDeleteAction(
                                                          productOption.poNo
                                                        )
                                                      }
                                                    >
                                                      삭제
                                                    </button>
                                                  </td>
                                                </tr>
                                              );
                                            }
                                          )}
                                      </tbody>
                                      <tfoot></tfoot>
                                    </table>
                                    <div className="btn-group">
                                      <a
                                        className="coup-table-btn btn-delete"
                                        onClick={closeProductOptionModal}
                                      >
                                        닫기
                                      </a>
                                    </div>
                                  </div>
                                </Modal>
                                {/* 옵션 관리 모달 끝 */}
                                {/* 옵션 등록 모달 시작 */}
                                <Modal
                                  isOpen={saveProductOptionIsOpen}
                                  onRequestClose={CloseSaveProductOptionModal}
                                  style={poSaveModalStyle}
                                  contentLabel="saveProductOptionModal"
                                >
                                  <form ref={formRef}>
                                    <div className="col-12">
                                      <label
                                        htmlFor="poName"
                                        className="form-label"
                                      >
                                        옵션이름
                                      </label>
                                      <div className="input-group has-validation">
                                        <input
                                          type="text"
                                          className="form-control"
                                          id="poName"
                                          name="poName"
                                          value={poName}
                                          onChange={(e) =>
                                            setPoName(e.target.value)
                                          }
                                          required
                                        />
                                      </div>
                                      <br />
                                      <div className="col-12">
                                        <label
                                          htmlFor="poPrice"
                                          className="form-label"
                                        >
                                          옵션가격
                                        </label>
                                        <div className="input-group has-validation">
                                          <input
                                            type="text"
                                            className="form-control"
                                            id="poPrice"
                                            name="poPrice"
                                            value={poPrice}
                                            onChange={(e) =>
                                              setPoPrice(e.target.value)
                                            }
                                            required
                                          />
                                        </div>
                                      </div>
                                      <div className="btn-group">
                                        <a
                                          className="coup-table-btn btn-delete"
                                          onClick={CloseSaveProductOptionModal}
                                        >
                                          취소
                                        </a>
                                        <a
                                          className="coup-table-btn btn-choose"
                                          onClick={saveProductOptionAction}
                                        >
                                          등록
                                        </a>
                                      </div>
                                    </div>
                                  </form>
                                </Modal>
                                {/* 옵션 등록 모달 끝 */}
                              </div>
                            </td>
                          </tr>
                        ))
                      ) : (
                        <tr>
                          <td
                            colSpan="7"
                            style={{ textAlign: "center", padding: "50px" }}
                          >
                            상품이 없습니다.
                          </td>
                        </tr>
                      )}
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          )
        )}
        {/* 카테고리 별 상품 끝 */} 
      </div>
    </>
  );
}

export default AdminProduct;
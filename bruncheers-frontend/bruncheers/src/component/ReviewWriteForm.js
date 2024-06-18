import React, { useRef, useState } from "react";
import MakeStar from "./MakeStar";
import * as FetchReview from "../api/fetchReview.js";
import { useNavigate } from "react-router";
import { notify, Toast } from "../util/toast";
function ReviewWriteForm({ productNumber, setProductNumber }) {
  const [selectedRating, setSelectedRating] = useState(0); // 선택된 별점을 폼 상태에 저장
  const [selectedFile, setSelectedFile] = useState([]); // 선택된 파일을 상태에 저장
  const formRef = useRef();
  const uploadRef = useRef();
  const navigate = useNavigate();

  const initState = {
    rno: 0,
    rstar: 0,
    rimage: [],
    rreg: "",
    rcontent: "",
    orderItem: 8,
    product: 4,
    user: 1,
    /*  orderItem: {
      oiNo: 9,
      oiQty: 1,
      oNo: 3,
      poNo: 10,
      reviewNo: 2,
    },

    product: {
      pno: 2,
      pName: "1일 1식 든든 도시락",
      pPrice: 53800,
      pImage: "sal.jpg",
      pDetail:
        "브런치얼스의 농장에서 키운 채소들로 만든 건강하고 즐거움 가득 신선 샐러드",
      pDeimg: "",
      pReg: "",
      catNo: 3,
    },
    user: {
      userNo: 1, // 사용자의 고유 식별자
      userEmail: "admin",
      userPw: 1111,
      userNickname: "브런치얼스",
      userName: "관리자",
      userGender: "남",
      userBitrh: "2020-05-13",
      userHp: "010-1234-5678",
      role: "ADMIN", // 사용자의 역할
    }, */
  };

  const [fetching, setFetching] = useState(false);
  const [result, setResult] = useState(null);
  const [review, setReview] = useState({
    ...initState,
  });

  const handleSelectRating = (rating) => {
    setSelectedRating(rating);
    setReview({ ...review, rstar: rating });
  };

  const handleReviewChange = (e) => {
    const { name, value } = e.target;
    setReview({ ...review, [name]: value });
  };

  const handleFileChange = (e) => {
    setSelectedFile(e.target.files[0]);
  };

  const ReviewWriteAction = async () => {
    if (!review.rcontent) {
      notify("warning", "내용을 입력해주세요");
      formRef.current.rcontent.focus();
      return;
    }
    const files = uploadRef.current.files;
    const formData = new FormData();
    for (let i = 0; i < files.length; i++) {
      formData.append("selectedfile", files[i]);
    }

    const reviewData = {
      ...review,
      rcontent: review.rcontent.split("\n").join("<br>"), // 개행 문자를 <br> 태그로 변환
    };

    formData.append("review", new Blob([JSON.stringify(reviewData)], { type: "application/json" }));
    /* 
    Object.keys(reviewData).forEach((key) => {
      if (typeof reviewData[key] === "object") {
        formData.append(key, JSON.stringify(reviewData[key]));
      } else {
        formData.append(key, reviewData[key]);
      }
    }); */

    setFetching(true);

    try {
      const responseJsonObject = await FetchReview.writeReview(formData);
      setFetching(false);
      setResult(responseJsonObject.result);
    } catch (error) {
      navigate("/error");
      setFetching(false);
    }
  };

  return (
    <>
      <Toast />
      {/* 변경 팝업 - 사이즈 : 572 * 570 */}
      <div className='p-4'>
        <h4 className='mb-4'>리뷰작성</h4>
        <form className='needs-validation' noValidate ref={formRef}>
          {/* 리뷰 시작 */}
          <div className='row g-3'>
            {/* 작성자 시작 */}
            <div className='col-12'>
              <label htmlFor='userNickname' className='form-label'>
                닉네임
              </label>
              <div className='input-group has-validation'>
                <input
                  type='text'
                  className='form-control'
                  id='userNickname'
                  name='userNickname'
                  value={"asdff"}
                  required
                  disabled
                />
              </div>
            </div>
            {/* 작성자 끝 */}
            {/* 별점 시작 */}
            <div className='form-group'>
              <label htmlFor='' className='form-label' style={{ marginTop: "10px" }}>
                별점
              </label>
              <div className='input-group'>
                <MakeStar onSelectRating={handleSelectRating} />{" "}
                {/* 별점 컴포넌트를 렌더링하고 선택된 별점 정보를 전달 */}
              </div>
            </div>
            {/* 별점 끝 */}
            {/* 리뷰 내용 시작 */}
            <div className='form-group'>
              <label htmlFor='reviewTextarea' className='form-label' style={{ marginTop: "10px" }}>
                내용
              </label>
              <textarea
                id='reviewTextarea'
                className='reviewTextarea form-control'
                name='rcontent'
                placeholder='최대 1000자까지 가능합니다.'
                maxLength='1000'
                style={{ height: "300px" }}
                value={review.rcontent}
                onChange={handleReviewChange}
              />
            </div>
            {/* 리뷰 내용 끝 */}
            {/* 사진 등록 시작 */}
            <div className='mt-5 form-group'>
              <label className='form-label'>사진을 등록해주세요</label>
              <div className='rounded-md border bg-gray-50 p-4 shadow-md w-24 h-24 mt-2'>
                <label
                  htmlFor='upload'
                  className='justify-center h-full flex flex-col items-center gap-2 cursor-pointer'
                >
                  <input
                    ref={uploadRef}
                    id='upload'
                    type='file'
                    name='selectedfile'
                    className='hidden'
                    onChange={handleFileChange} // 파일 선택 시 handleFileChange 함수 호출
                  />
                </label>
              </div>
            </div>
            {/* 사진 등록 끝 */}
            <div className='btn-group'>
              <button type='button' className='coup-table-btn btn-delete' onClick={() => setProductNumber(null)}>
                취소
              </button>
              <button
                type='button'
                className='coup-table-btn btn-choose'
                onClick={ReviewWriteAction}
                disabled={fetching}
              >
                등록
              </button>
            </div>
          </div>
        </form>
      </div>
    </>
  );
}

export default ReviewWriteForm;

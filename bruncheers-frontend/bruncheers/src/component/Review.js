import React, { useEffect, useState } from "react";
import * as fetchReview from "../api/fetchReview.js";
import reviewImag from "../image/rImg.jpg";
import "../css/review.css";
import Pagination from "./Pagination.js";
import { useParams } from "react-router";

function Review() {
  const { pNo } = useParams(); // URL 파라미터 추출..? 
  const [showReview, setShowReview] = useState({});
  const [reviews, setReviews] = useState([]);

  useEffect(() => {
    fetchReview.listReviewPNo(pNo).then((responseJsonObject)=>{
      setReviews(responseJsonObject.data);
    })
  },[pNo]);
  
/* //리뷰전체리스트 
   useEffect(() => {
    fetchReview.listReview().then((responseJsonObject) => {
      setReviews(responseJsonObject.data);
      // 리뷰 목록이 업데이트될 때마다 리뷰 상태를 초기화합니다.
      const initialStates = {};
      responseJsonObject.data.forEach((review) => {
        initialStates[review.rno] = false;
      });
      setShowReview(initialStates);
    });
    
  }, []);  */

  const truncateReview = (text) => {
    if (text.length > 15) {
      return `${text.substring(0, 15)}....`;
    }
    return text;
  };

  const toggleFold = (rno) => {
    setShowReview((showReview) => ({
      ...showReview,
      [rno]: !showReview[rno],
    }));
  };
  
  const getStars = (rating) => {
    return '★'.repeat(rating) + '☆'.repeat(5 - rating);
  };

  const calculateAverageRating = (reviews) => {
    if (reviews.length === 0) return 0;
    const totalRating = reviews.reduce((sum, review) => sum + review.rstar, 0);
    const averageRating = totalRating / reviews.length;
    return averageRating;
  };


  return (
    <table className="fold-table">
      <thead className="non">
        <tr
          className="r-header"
          style={{
            textAlign: "left",
            padding: "0.4em",
          }}
        >
          <th className="non"> * 제목</th>
          <th className="non"> * 닉네임</th>
          <th className="non"> * 등록일</th>
          <th className="non">
            <span className="visible-big">* 별점&nbsp;&#40;{calculateAverageRating(reviews)}&#41;</span>
          </th>
        </tr>
      </thead>
      <tbody className="non">
        {reviews.map((review) => (
          <React.Fragment key={review.rno}>
            <tr
              className={`view ${showReview[review.rno] ? "open" : ""}`}
              onClick={() => toggleFold(review.rno)}
            >
              <td className="non">
                <p>{truncateReview(review.rcontent)}</p>
              </td>
              <td className="pcs">
                <span>{review.user}</span>
              </td>
              <td className="cur">{review.rreg.substring(0, 10)}</td>
              <td className="non">{getStars(review.rstar)}</td>
            </tr>
            {showReview[review.rno] && (
              <tr className="fold open" >
                <td>
                  <div style={{ alignItems: "center"}}>
                    <img
                     /*  src={`/image/${review.rimage}`} */
                     src={`http://localhost:8080/product/view/${review.rimage}`}
                      alt=""
                      style={{
                        width: "200px",
                        height: "200px",
                      }}
                    />
                  </div>
                  </td>
                   <td style={{ verticalAlign: 'middle' }} colSpan="3">
                    <p> {review.rcontent} </p>
                  </td>
              </tr>
            )}
          </React.Fragment>
        ))}
      </tbody>
    </table>
  );
}

export default Review;

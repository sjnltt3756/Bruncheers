import { Link } from "react-router-dom";
import main1 from "../image/main1.png";
import main2 from "../image/main2.png";

function BCMain() {
  return (
    <>
      {/* 홈페이지 메인 이미지 시작 */}
      <div id="myCarousel" className="carousel slide mb-6" data-bs-ride="carousel" >
        <div className="carousel-indicators" >
          <button
            type="button"
            data-bs-target="#myCarousel"
            data-bs-slide-to="0"
            className="active"
            aria-current="true"
            aria-label="Slide 1"
          ></button>
          <button
            type="button"
            data-bs-target="#myCarousel"
            data-bs-slide-to="1"
            aria-label="Slide 2"
          ></button>
          <button
            type="button"
            data-bs-target="#myCarousel"
            data-bs-slide-to="2"
            aria-label="Slide 3"
          ></button>
        </div>
        <div className="carousel-inner" >
          <div className="carousel-item active text-center" >
            <img
              src="./image/mainsal.png"
              className="d-block mx-auto opacity-image"
              style={{ maxWidth: "100%", height: "auto" }}
              width="1500"
              height="700"
            />
            <div className="container" >
              <div className="carousel-caption text-end mb-4">
                <h5 style={{color:"#fff", marginBottom:"10px"}}>매일 새롭게 업데이트 되는 식단</h5>
                <p className="opacity-75" style={{color:"#fff",  marginBottom:"10px"}}>
                  영양과 맛의 균형을 모두 잡은<br/>브런치얼스만의 도시락을 만나보세요.
                </p>
                <Link to={`/product/catNo/${1}`}>
                  <p className="main-button">
                    보러가기
                  </p>
                  </Link>
              </div>
            </div>
          </div>
          <div className="carousel-item">
            <img
              src="./image/welcome.png"
              className="d-block mx-auto opacity-image"
              style={{ maxWidth: "100%", height: "auto" }}
              width="1500"
              height="700"
            />
            <div className="container">
              <div className="carousel-caption text-center mb-4" style={{marginRight:"400px"}}>
                <h5 style={{color:"#000", marginBottom:"30px"}}>바쁜 일상 속<br/>브런치얼스가 스며들어버렸다</h5>
                <p style={{color:"#000",  marginBottom:"480px"}}>
                  (재구매 회원 대상)<br/>브런치얼스의 할인 쿠폰 놓치지 마세요<br/>저희도 고객님 놓치지 않을게요 ..(˘̩̩̩ε˘̩ƪ)
                </p>
                
              </div>
            </div>
          </div>
          <div className="carousel-item">
            <img
              src="./image/main.jpg"
              className="d-block mx-auto opacity-image"
              style={{ maxWidth: "100%", height: "auto" }}
              width="1500"
              height="700"
            />
            <div className="container">
              <div className="carousel-caption text-end mb-4">
                <h5 style={{color:"#646464", marginBottom:"10px"}}>신규 회원은 언제나 환영이야!</h5>
                <p style={{color:"#787878",  marginBottom:"10px"}}>
                  새로 가입한 회원에게<br/>전상품 10% 할인쿠폰을 드려요 ( ˶ˆᗜˆ˵ )
                </p>
                  <Link to={"/register"}>
                  <p className="main-button">
                    가입하고 쿠폰받기
                  </p>
                  </Link>
              </div>
            </div>
          </div>
        </div>
        <button
          className="carousel-control-prev"
          type="button"
          data-bs-target="#myCarousel"
          data-bs-slide="prev"
        >
          <span className="carousel-control-prev-icon" aria-hidden="true"></span>
          <span className="visually-hidden">Previous</span>
        </button>
        <button
          className="carousel-control-next"
          type="button"
          data-bs-target="#myCarousel"
          data-bs-slide="next"
        >
          <span className="carousel-control-next-icon" aria-hidden="true"></span>
          <span className="visually-hidden">Next</span>
        </button>
      </div>
      {/* 홈페이지 메인 이미지 끝 */}
      <br/>
      {/* 간단한 홍보 */}
      <ul style={{ display: "flex",
      flexDirection: "column",
      justifyContent: "center",
      alignItems: "center",
      listStyleType: "none",
      padding: 0}}>
       <li style={{width: "100%", display: "flex", justifyContent: "center", padding:"40px 0 0 0"}}>
        <img src={main1} style={{width: "100%", maxWidth: "1500px"}} alt="Main 1"/>
      </li>
      </ul>
      {/* 동그라미 이미지 */}
      <div className="container" style={{ display: "flex", justifyContent: "center", alignItems: "center", height: "30vh" }}>
        <div className="col-12 text-style" style={{ textAlign: "center" }}>
          <h2 className="text-style">나에게 맞는 식사</h2>
          <p className="text-style">자유롭게 선택하세요. 원하는 때, 원하는 만큼 챙겨드릴게요</p>
        </div>
      </div>

      <div className="container marketing mt-3">
        <div className="row">
          <div className="col-lg-4 text-center mt-3">
            <svg className="bd-placeholder-img rounded-circle" width="140" height="140">
              <g className="image-container">
                <Link to={`/product/catNo/${1}`}>
                <image href="./image/salad.png" x="0" y="0" height="140" width="140"/>
              </Link>
              </g>
            </svg>
            <h2 className="fw-normal mt-3">1일 1식</h2>
            <p>원하는 때에 하루 한 끼<br />건강함을 담은 식단을 받아보세요.</p>
          </div>
          <div className="col-lg-4 text-center mt-3">
            <svg className="bd-placeholder-img rounded-circle" width="140" height="140">
              <g className="image-container">
              <Link to={`/product/catNo/${2}`}>
                <image href="./image/lunchBox.jpg" x="0" y="0" height="140" width="140"/>
              </Link>
              </g>
            </svg>
            <h2 className="fw-normal mt-3">1일 2식</h2>
            <p>하루 두 번<br />든든함을 담아 배송해드려요.</p>
          </div>
          <div className="col-lg-4 text-center mt-3">
            <svg className="bd-placeholder-img rounded-circle" width="140" height="140">
              <g className="image-container">
              <Link to={`/product/catNo/${3}`}>
                <image href="./image/sandwich.jpg" x="0" y="0" height="140" width="140"/>
              </Link>
              </g>
            </svg>
            <h2 className="fw-normal mt-3">1일 3식</h2>
            <p>바쁜 하루를 걱정없이<br />브랜치얼스와 함께.</p>
          </div>
        </div>
        <br/>
        <br/>
      <li style={{width: "100%", display: "flex", justifyContent: "center", padding:"100px 0 0 0"}}>
        <img src={main2} style={{width: "100%", maxWidth: "1500px"}} alt="Main 2"/>
      </li>
      </div>
    </>
  );
}
export { BCMain };


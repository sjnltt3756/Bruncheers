import React from "react";
import '../css/styles.css';

function Footer() {
  return (
    <div id="footer">
      <div id="infoBoxLinkInfo">
        <p>
          <strong>HOME</strong>
        </p>
        <p>이용안내</p>
        <p>이용약관</p>
        <p>
          <strong>개인정보처리방침</strong>
        </p>
      </div>
      <div id="infoBoxCsInfo">
        <p>
          <strong>CUSTOMER CENTER</strong>
        </p>
        <p style={{fontSize: '25px'}}><strong>02-6255-8002</strong></p>
        <p>- 업무시간: 10:00~17:00&nbsp;&#40;점심시간: 12:00~13:00&#41;</p>
        <p>- 주말 및 공휴일 휴무</p>
        <p>
          <strong>
            &#91;유선상담은 통화량이 많아, 연결이 어려울 수 있습니다&#93;
          </strong>
        </p>
      </div>
      <div id="infoBoxOfficeInfo">
        <p>
          <strong>OFFICE INFO</strong>
        </p>
        <p>
          회사명: &#40;주&#41;브런치얼스 &emsp;&emsp;대표: 김미진, 김진효
          &emsp;&emsp;사업자등록번호 : 124-012-00405
        </p>
        <p>
          주소: 서울특별시 강남구 테헤란로 124 4층 &#40;역삼동, 삼원타워&#41;
        </p>
        <p>
          개인정보관리책임자: 이승빈 &emsp;&emsp; E-Mail :bruncheers@samwon.com
        </p>
        <br />
        <p>
          Copyright©<strong>bruncheers.co.kr</strong>All rights reserved.
        </p>
      </div>
    </div>
  );
}
export default Footer;

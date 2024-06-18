# 프로젝트 소개

</br>

 <div align="center"> 
   
![header](https://capsule-render.vercel.app/api?type=rounded&color=CCE2AB&height=200&section=header&text=Bruncheers!&fontSize=90&animation=twinkling&fontColor=71B450)

*Bruncheers 브런치얼스*   
브런치를 즐기는 브런치어, 그들의 건강을 위하여 치얼스!

</div>

</br>

바쁜 일상을 보내는 사람들에게 맛있고 건강한 한 끼를 제공하고 싶은 마음에 만든 도시락 배달 웹 애플리케이션 입니다.   
웹페이지를 통해 원하는 식사(도시락, 샌드위치, 샐러드)와 기간을 선택하여 주문하면 해당 기간동안 배달을 받을 수 있는 시스템입니다.


</br>

### 개발 기간 및 인원
23.05.03 ~ 23.06.07 (5주)   
프론트엔드, 백엔드 5명

</br></br>

## 팀원

|김미진|김진효|원주하|지민수|정수빈|이승빈|
|:---:|:---:|:---:|:---:|:---:|:---:|
|UI, SHIP|UI, COUPON, CART|BOARD, REVIEW|USER, SEQURITY|ORDER, PRODUCT|PPT|


</br></br>

# ERD 구조
[ERD Cloud 에서 둘러보기](https://www.erdcloud.com/d/PWCDL9jRzPqCQTZTf)

![브런치얼스 erd](https://github.com/2023-12-JAVA-DEVELOPER-149/final-project-team2/assets/152963643/e6a03e8c-3b21-4bfb-85da-70937a898f3d)

</br>

# 개발 일정

![브런치얼스(Bruncheers)_파이널 프로젝트_발표_PPT_3](https://github.com/2023-12-JAVA-DEVELOPER-149/final-project-team2/assets/152963643/a7ea9a1a-ef9b-4ab7-97d1-2cdd8ca909f0)

</br>

# 기술 스택

![기술 스택](https://github.com/2023-12-JAVA-DEVELOPER-149/final-project-team2/assets/152963643/b878c6cf-092c-47b6-b080-9885f934d192)

</br>

## 기능 소개

### [ 회원 ]
#### 회원가입
- 이메일, 닉네임, 비밀번호, 비밀번호 확인, 이름, 성별, 생년월일, 휴대전화번호 입력 후 회원가입 가능   
- 이메일과 닉네임은 중복확인 버튼을 통해 DB에 저장되어있는 이메일과 닉네임이 있을 경우에는 회원가입이 불가(예외처리 및 유효성검사)(DB에 중복유저가 없을 경우에 회원가입 가능)   
- 회원가입 시 신규회원 쿠폰 지급   
- 회원가입 완료되면 로그인페이지로 이동   
#### 로그인
- 이메일 / 비밀번호 입력 후 로그인 가능   
- 로그인 시 액세스 토큰 발급   
- 회원가입 이후 로그인 가능   
  - 이메일찾기
    - 생년월일과 휴대전화번호를 기입하고 찾기를 누르면 저장되어있는 유저의 이메일 출력   
    - 기존 유저가 아닌 경우 예외처리 및 아이디를 찾을 수 없다는 유효성검사 출력   
  - 비밀번호찾기
    - 이메일과 휴대전화번호를 기입하고 찾기를 누르면 저장되어있는 유저일 경우 새로운 비밀번호 입력 모달이 띄워짐   
    - 기존 회원이 아닌 경우 예외처리 및 유효성검사 출력   
    - 새 비밀번호와 새 비밀번호 확인을 기입하고 수정완료 되면 비밀번호가 암호화되어 저장됨   
    - 이후 변경한 비밀번호로 로그인 가능   
  - 카카오 로그인
    - 카카오 로그인 API 를 사용하여 로그인 가능   
    - 카카오로그인으로 회원가입 시 카카오정보로 자동 회원가입   
  - 계정 복구하기
    - 탈퇴한 유저의 계정의 ROLE을 USER로 변경하여 다시 로그인 가능   
    - 이메일과 휴대폰번호를 기입하여 복구 가능   
    - 기존 유저가 아닌 경우 예외처리 및 유효성 검사   
#### 회원정보 수정
- 로그인한 유저의 정보를 출력   
- 수정버튼 클릭시 비밀번호 입력 모달 창 → 비밀번호 입력 후 수정 모달로 이동   
- 수정 모달에 로그인한 유저 정보 출력   
- 닉네임과 비밀번호만 수정 가능   
- 닉네임은 중복확인 이후 중복일 경우에는 수정 안됨   
- 닉네임만 변경할 경우 새 비밀번호를 기존 비밀번호로 입력 후 수정   
- 비밀번호 변경 시 암호화되어 DB에 저장   
#### 탈퇴
- 회원정보 수정 페이지에서 탈퇴하기를 누르면 '브런치얼스 회원을 탈퇴하시겠습니까?' 라는 모달창 출력   
- '예'를 클릭 한 경우 user의 role이 ghost로 변경되어 로그인 불가능 (유령계정)   

</br></br>

### [ 상품 ]
#### 상품 목록
- 메인페이지에서 상품 카테고리를 클릭하면 상품 카테고리에 해당하는 상품의 이미지, 상품설명, 가격이 출력되게 구현   
- 상품 카테고리 페이지에서 상품 이미지, 상품이름 클릭하면 해당 상품의 상세 페이지로 이동   
#### 상품 상세
- 상품 상세페이지에서 해당 상품의 상세 내역(상품명, 이미지, 가격, 상품설명)을 볼 수 있으며, 구매후기 및 구매가이드 내용 또한 확인 가능   
- 기간선택을 클릭하면 해당 상품의 옵션 선택 가능   
- 상품 옵션을 선택하면 기존 상품 가격에 옵션 가격을 더한 값으로 총 금액이 나오도록 구현   
- '바로구매' 버튼을 클릭 할 시 주문 페이지로 해당 상품의 정보를 넘겨 해당 상품을 바로 구매할 수 있도록 구현   
- '장바구니' 버튼을 클릭 할 시 '장바구니에 상품이 담겼습니다. 장바구니로 이동하시겠습니까?' 라는 모달이 뜨며 '장바구니 이동' 및 '계속 구경하기' 버튼으로 원하는 곳 이동 가능   
- '장바구니로 이동' 버튼을 클릭 할 시 장바구니 페이지로 해당 상품의 정보가 넘어가며 '계속 구경하기' 버튼을 클릭 할 시 현재 상품 페이지에 머무를 수 있도록 구현    

</br></br>

### [ 장바구니 ]
####  상품에서 장바구니 담기
- 상품에서 옵션을 선택하고 장바구니 버튼을 누르면 장바구니에 원하는 상품 담기 가능   
- 장바구니에 동일한 상품이 담겨있을 시 "이미 장바구니에 상품이 담겨 있습니다" 라는 메시지 출력 (react-toastify 이용)    
#### 장바구니 삭제
- 체크박스를 이용하여 장바구니에 담긴 상품 전체 선택 가능, 부분 선택 가능   
- 선택삭제를 누르면 선택된 체크박스에 해당하는 상품이 장바구니에서 삭제되며, 별도의 삭제 버튼으로 개별 삭제도 가능   
#### 주문하기 
- 주문하기 버튼을 누르면 체크박스로 선택된 상품들이 주문으로 넘어가며 페이지 이동   

</br></br>

### [ 주문 ]
#### 주문 진행
- 주문 페이지에서 주문 할 상품의 정보(상품이름, 이미지, 옵션, 가격) 확인 가능   
- 입력하지 않은 부분이 있을 시 유효성 검사가 진행되며, 정보를 모두 입력 한 후에 결제하기가 가능하도록 구현   
#### 주문 내역
- 주문 내역 페이지에서 그동안 주문한 내역(주문번호, 이미지, 상품, 주문날짜, 가격)을 간략하게 확인할 수 있으며 리뷰 등록 및 삭제 가능   
- '주문번호'를 클릭시 주문의 상세페이지로 이동하고 '상품이름'을 클릭할 시 해당 상품의 상세 페이지로 이동   
#### 주문 상세
- 주문 상세페이지에는 주문 한 건의 정보(주문한 상품 정보, 배송지 정보, 결제 정보)를 확인할 수 있으며 상세페이지에서도 리뷰 등록 및 삭제 가능   

</br></br>

### [ 배송 ]
#### 주문 시 배송지 입력
- '배송지 목록'을 통해 원하는 배송지를 선택할 수 있으며, 배송지 선택 시 폼 요소에 불러오기 가능   
- '기본 배송지'가 존재할 경우 '기본 배송지 불러오기' 버튼을 클릭하여 폼 요소에 불러오기 가능   
- 다음 API를 통해 주소를 검색하여 입력 가능
#### 배송지 목록
- 배송지 목록을 클릭 할 시 배송지 목록 모달이 뜨며, 저장된 회원의 배송지 목록 확인 가능   
#### 신규 배송지 등록
- 신규 배송지 등록 버튼을 클릭 시 신규 배송지를 입력 할 수 있는 모달이 뜨며 신규 배송지 등록 가능   
- 배송지 최초 입력시 '기본 배송지'로 등록될 수 있도록 구현   
#### 배송지 수정, 삭제
- 배송지 목록 모달에서 배송지 수정 및 삭제 가능   
#### 유효성 검사
- 배송지 필수 입력 요소(수령인, 휴대전화, 주소)를 입력하지 않으면 진행 불가 (배송요청사항 제외)   


</br></br>

### [ 쿠폰 ]
#### 쿠폰 생성 
- 회원가입을 하면 신규회원 대상으로 쿠폰 지급   
#### 쿠폰 적용 
- 주문하기 페이지에서 '쿠폰 조회/목록' 버튼을 통하여 쿠폰 조회 가능      
- Radio를 통해 한 개의 쿠폰만 적용 가능 (적용하기 전 주문 상품에 적용될 할인 금액 확인 가능)   
- 쿠폰 선택을 누르면 쿠폰이 적용된 가격으로 최종 결제 금액 출력      
- 고객이 결제까지 진행하여 주문이 완료되면 적용된 쿠폰은 다시 사용 불가   

</br></br>

### [ 결제 ]
- 토스페이먼트 API를 사용하여 소비자가 다양한 결제 방법을 선택 가능    
- '결제하기' 버튼을 눌러 결제 진행 후 결제가 최종적으로 완료되면 주문이 생성되며 주문 완료 페이지로 이동   

</br></br>

### [ 게시판 ]
#### 게시판 리스트
- 최신글 순으로 게시판 리스트 확인 가능, 답글의 경우 부모 글의 밑에 출력될 수 있도록 구현   
- 글을 클릭할 때 마다(게시판 글 조회 시) 그 글의 조회수가 증가   
#### 게시판 상세
- 작성자는 게시판 리스트에서 작성한 글의 내용, 작성자, 작성일자, 카테고리 확인이 가능하며 관리자의 답글에서도 같은 방식으로 확인 가능   
- 다만 작성자는 관리자의 답글에서 읽기와 목록 버튼만 활용 가능하며 관리자는 삭제, 수정 모두 이용 가능   
#### 공지사항 상단 고정   
- 작성된 공지사항은 게시판 목록 상단에 고정될 수 있도록 배치     
#### 게시판 작성
- ADMIN만 '공지사항' 카테고리 선택하여 게시물 작성 가능, 일반 회원은 '상품문의', '배송문의', '기타' 카테고리 중 선택하여 게시판 작성      
- 게시판 내용의 경우 map function을 활용하여 띄어쓰기와 줄 바꿈이 모두 적용된 html 형식으로 저장      
#### 게시판 수정
- 작성 글은 작성자만 수정 가능하며 제목과 내용 수정 후 변경된 내용으로 저장, 변경 후 저장된 내용이 클라이언트에게 출력됨  
#### 게시판 삭제
- 작성자만 해당 글 삭제 요청 가능하며 버튼 클릭 시 삭제   
#### 게시판 댓글 작성
- ADMIN만 회원의 게시판 글에 답글을 달 수 있음, 답글의 경우 제목 앞에 Return key 이미지랑 같이 출력하여 답글임이 구분이 가능하도록 설정    
  

</br></br>

### [ 리뷰 ]
#### 리뷰 리스트 
- 작성된 리뷰의 경우 해당 구입 상품의 상세페이지에서 확인 가능   
- 출력된 리뷰는 아코디언 형태로 나열되며 클릭하면 상세 화면이 열림   
- 작성한 제목, 닉네임, 등록일, 별점 확인이 가능   
#### 리뷰 버튼 활성화
- 주문 작성 후 주문 내역, 주문상세에서 리뷰 작성 버튼이 활성화, 클릭하여 작성 가능   
#### 리뷰 작성
- 리뷰 작성에서 작성하는 회원 닉네임, 별점으로 평점 선택, 리뷰 콘텐츠 작성, 개인이 업로드하고자 하는 이미지 첨부하여 저장 가능   
- 리뷰 작성 후 리뷰가 작성되었다는 알림이 클라이언트에게 출력   
#### 리뷰 삭제
- 리뷰 삭제의 경우 리뷰 작성 후 리뷰 작성 버튼이 삭제 버튼으로 활성화되어 기능 구현   
- 클릭 후 '리뷰 삭제하시겠습니까?' 확인 요청 후 확인 클릭 시 삭제 가능   


</br></br>

### [ 관리자 ]
- 관리자 아이디로만 관리자 페이지 접근 가능   
- 관리자 메인 페이지에서 회원의 수, 상품 수, 주문 수 각 합계 확인 가능   
#### 주문
- 관리자 주문 페이지에서 회원들의 주문정보(주문번호, 주문일, 주문상품, 상품금액, 할인금액, 주문자, 수령자, 총 주문액, 결제방법)를 담은 전체 주문 내역 확인 가능   
- '주문자'의 이름을 클릭하면 주문자의 회원정보 모달이 뜨며 해당 회원의 상세 정보(이름,이메일,휴대전화,주문번호,주문일시,주문상품) 확인 가능   
- '수령자'의 이름을 클릭하면 수령자의 상세 배송지 모달이 뜨며 배송지 상세 정보(받는사람, 휴대전화, 주소, 주문요청사항, 배송요청사항) 확인 가능   
#### 상품 관리 
- 상품 보기   
  - 관리자 페이지에서 상품을 클릭하면 기존에 등록되어 있는 '상품카테고리', '상품정보', '상품옵션' 조회 가능   
- 카테고리 관리   
  - 카테고리 신규 등록, 수정, 삭제 가능   
  - 카테고리 삭제시 해당 카테고리의 상품, 상품 옵션도 같이 삭제   
- 상품 관리   
  - 상품 등록   
    - 카테고리 선택으로 기존에 존재하는 카테고리 확인, 선택 가능   
    - 상품 정보(카테고리, 상품이름, 상품가격, 상품설명, 상품이미지, 상품설명이미지)를 하나라도 채우지 않으면 유효성 검사로 등록 불가   
    - 상품 이미지와 상품 설명 이미지 등록 가능   
    - 등록된 상품은 관리자 상품 페이지와 홈페이지의 상품 페이지에 바로 게시   
  - 상품 수정 및 삭제   
    - 상품 수정 시 일단 해당 상품의 정보가 보여지며, 원하는 부분 수정 가능   
    - 상품 삭제 버튼을 누르면 "정말 삭제하시겠습니까?"라는 확인 요청 후 확인을 누를 시 상품이 삭제   
- 상품 옵션 관리   
  - 상품옵션 신규등록, 수정, 삭제 가능    
 #### 회원 관리 
- 관리자 페이지에서 회원을 클릭하면 전체 회원 정보 조회 가능


</br></br>


# API 명세
|Domain|URL|HttpMethod|description|
|---|---|---|---|
|**User**|/admin/user/list|<code>GET</code>|회원 전체리스트 조회|
||/users/register|<code>POST</code>|회원가입|
||/users/login|<code>POST</code>|로그인|
||/users/find/id|<code>GET</code>|아이디 찾기|
||/users/find/pw|<code>GET</code>|비밀번호 찾기|
||/users/nickname-unique|<code>GET</code>|닉네임 중복확인|
||/users/email-unique|<code>GET</code>|이메일 중복확인|
||/users/update/{userNo}|<code>PUT</code>|회원수정|
||/users/{userNo}|<code>DELETE</code>|회원삭제|
||/users/userInfo/{userNo}|<code>GET</code>|회원정보 조회|
||/users/verify-login/{userNo}|<code>POST</code>|비밀번호 확인|
||/users/updatePassword|<code>PUT</code>|비밀번호 수정|
||/users/{userNo}/role/ghost|<code>PUT</code>|탈퇴(ROLE변경)|
||/users/role/user|<code>PUT</code>|복구(ROLE변경)|
|**Board**|/boards/{page_no}|<code>GET</code>|게시판 전체리스트 조회|
||/board_view/{board_no}|<code>GET</code>|게시판 상세보기|
||/board|<code>POST</code>|게시판 작성|
||/board/{board_no}|<code>PUT</code>|게시판 수정|
||/board/{board_no}|<code>DELETE</code>|게시판 삭제|
||/admin/notice|<code>POST</code>|공지사항 작성|
||/admin/board/comment|<code>POST</code>|게시판 댓글쓰기|
|**Cart**|/cart|<code>POST</code>|장바구니 추가|
||/cart/{userNo}|<code>GET</code>|장바구니 조회|
||/cart/{cNo}|<code>DELETE</code>|장바구니 아이템 한개 삭제|
||/cart|<code>DELETE</code>|장바구니 체크 된 상품들 삭제|
|**Coupon**|/coupon|<code>POST</code>|쿠폰생성|
||/coupons/{userNo}|<code>GET</code>|쿠폰조회|
||/coupon/{coupNo}|<code>DELETE</code>|쿠폰 1개삭제|
||/coupons/{userNo}|<code>DELETE</code>|쿠폰 전체삭제|
|**Order**|/order/{oNo}|<code>DELETE</code>|주문 한건삭제|
||/order/orderDeleteAll/{userNo}|<code>DELETE</code>|주문 전체삭제|
||/order|<code>POST</code>|주문생성|
||/order/{oNo}|<code>GET</code>|주문상세|
||/orde/userNo/{userNo}|<code>GET</code>|주문리스트 조회|
||/admin/order/list|<code>GET</code>|주문 전체리스트 조회|
|**OrderItem**|/oi|<code>POST</code>|주문아이템 추가|
||/oi/{oiNo}|<code>DELETE</code>|주문아이템 삭제|
||/oi/{oiNo}|<code>GET</code>|주문아이템 조회|
||/oi/{oNo}/ois|<code>GET</code>|한 주문의 주문아이템 목록 조회|
|**Pay**|/pay|<code>POST</code>|결제방식 등록|
||/pay/{paNo}|<code>DELETE</code>|결제방식 삭제|
||/pay/{paNo}|<code>GET</code>|결제방식 한개 조회|
||/pay/pay/{paType}|<code>GET</code>|결제방식으로 결제번호 조회|
|**Product**|/product|<code>GET</code>|상품 전체조회|
||/product/{pNo}|<code>GET</code>|상품번호로 조회|
||/productcatNo/{catNo}|<code>GET</code>|상품카테고리번호로 조회|
||/product/poNo/{poNo}|<code>GET</code>|상품옵션번호로 조회|
||/product/pcat|<code>GET</code>|상품&상품카테고리 조회|
||/product/view/{fileName}|<code>GET</code>|상품이미지 조회|
||/admin/product|<code>POST</code>|상품등록|
||/admin/product/update|<code>PUT</code>|상품수정|
||/admin/product/{pNo}|<code>DELETE</code>|상품삭제|
|**ProductCategory**|/productCat|<code>POST</code>|상품카테고리 등록|
||/productCat|<code>PUT</code>|상품카테고리 수정|
||/productCat/{catNo}|<code>DELETE</code>|상품카테고리 삭제|
||/productCat|<code>GET</code>|상품카테고리 조회|
||/productCat/product|<code>GET</code>|상품카테고리&상품 조회|
|**ProductOption**|/productOption/pono|<code>POST</code>|상품옵션 등록|
||/productOption/poNo/{poNo}|<code>DELETE</code>|상품옵션 삭제|
||/productOption/poNo|<code>PUT</code>|상품옵션 수정|
||/productOption/api/productOptions|<code>GET</code>|상품옵션으로 상품리스트 조회|
||/productOption/api/productOptionPNo/{pNo}|<code>GET</code>|상품옵션리스트로 상품번호 조회|
|**Review**|/review|<code>GET</code>|리뷰 전체리스트 조회|
||/view/{fileName}|<code>GET</code>|리뷰이미지 조회|
||/review/{product_no}|<code>GET</code>|한 상품의 리뷰리스트 조회|
||/review_view/{orderItem_no}|<code>GET</code>|한 주문아이템의 리뷰 상세|
||/review|<code>POST</code>|리뷰 작성|
||/review/{oiNo}|<code>PUT</code>|리뷰 수정|
||/review/{oiNo}|<code>DELETE</code>|리뷰 삭제|
||/review_rate/{product_no}|<code>GET</code>|한 상품의 리뷰 평점 평균|
|**Ship**|/ship|<code>POST</code>|배송지 등록|
||/ship/{sNo}|<code>PUT</code>|배송지 수정|
||/ship/{sNo}|<code>DELETE</code>|배송지 삭제|
||/ship/{userNo}/ships|<code>GET</code>|배송지 목록 조회|
||/ship/{userNo}/firstShip|<code>POST</code>|기본배송지 조회|
||/ship/{sNo}|<code>GET</code>|배송지 한개 조회|
||/shiptype/{sType}/ships|<code>GET</code>|배송지 유형별 조회|


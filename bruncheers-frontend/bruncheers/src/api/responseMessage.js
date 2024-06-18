// 회원(User) 1000번
export const LOGIN_SUCCESS = "회원 로그인 성공";
export const READ_USER = "회원 정보 조회 성공";

export const FIND_ID_SUCCESS = "아이디 찾기 성공";
export const FIND_ID_FAIL = "아이디 찾기 실패";
export const FIND_PW_SUCCESS = "비밀번호 찾기 성공";
export const FIND_PW_FAIL = "비밀번호 찾기 실패";

export const CREATED_USER = "회원 가입 성공";
export const UPDATE_USER = "회원 정보 수정 성공";
export const DELETE_USER = "회원 탈퇴 성공";
export const LOGOUT_USER = "회원 로그 아웃";
export const LOGIN_FAIL_NOT_FOUND_USER = "회원을 찾을 수 없습니다.";
export const LOGIN_FAIL_PASSWORD_MISMATCH_USER = "회원패쓰워드불일치";
export const SUCCESS_PASSWORD_MISMATCH_USER = "패스워드 일치";
export const CREATE_FAIL_EXISTED_EMAIL = "회원이메일중복";
export const EMAIL_AVAILABLE = "이메일 사용 가능";
export const CREATE_FAIL_EXISTED_NICKNAME = "회원닉네임중복";
export const NICKNAME_AVAILABLE = "닉네임 사용 가능";
export const UNAUTHORIZED_USER = "인증받지않은요청입니다.";

/* 상품(Product(4110 번대), ProductOption(4120 번대), ProductCategory(4130 번대)) 4000번대 */
	
export const CREATE_PRODUCT = "상품 등록 성공";
	
export const READ_PRODUCTCATEGORY = "상품 카테고리 조회 성공";
export const CREATE_PRODUCTCATEGORY = "상품 카테고리 등록 성공";
export const UPDATE_PRODUCTCATEGORY = "상품 카테고리 수정 성공";
export const DELETE_PRODUCTCATEGORY = "상품 카테고리 삭제 성공";

/* 게시판 5000번 */
export const READ_BOARD_LIST = "게시판 리스트 불러오기 성공";
export const READ_BOARD = "게시판 조회 성공";
export const CREATED_BOARD = "게시판 생성 성공";
export const UPDATE_BOARD = "게시판 수정 성공";
export const DELETE_BOARD = "게시판 삭제 성공";
export const CREATED_COMMENT = "게시판 답글 생성 성공";

export const RETRIEVE_FAIL_BOARD_LIST = "게시판 리스트를 불러올 수 없습니다";
export const RETRIEVE_FAIL_NOT_FOUND_BOARD = "게시판을 찾을 수 없습니다";
export const CREATE_FAIL_BOARD = "게시판 작성을 할 수 없습니다";
export const UNAUTHORIZED_BOARD = "인증받지 않은 요청입니다";

/* 쿠폰 6000번 */
export const CREATED_COUP = "쿠폰 생성 성공";
export const READ_COUP = "쿠폰 조회 성공";
export const DELETE_COUP = "쿠폰 삭제 성공";
export const DELETE_ALL_COUP = "쿠폰 전체 삭제 성공";
export const APPLY_COUP = "쿠폰 적용 성공";
export const NOT_FOUND_COUP = "쿠폰을 찾을 수 없습니다";

/* 카트 7000번 */
export const ADD_CART = "장바구니에 아이템이 담겼습니다.";
export const READ_CART = "장바구니 조회 성공";
export const DELETE_CARTITEM = "장바구니에 담긴 상품 하나가 삭제되었습니다.";
export const DELETE_CHECKED_CARTITEM = "선택하신 상품이 삭제되었습니다.";
export const DUPLICATED_CARTITEM = "이미 장바구니에 있는 상품입니다.";

/* 배송지 8000번 */
export const CREATED_SHIP = "배송지 등록 성공";
export const UPDATE_SHIP = "배송지 수정 성공";
export const DELETE_SHIP = "배송지 삭제 성공";
export const READ_SHIP = "배송지 조회 성공";
export const READ_SHIP_BY_USER = "회원의 전체 배송지 목록 조회 성공";
export const READ_SHIP_BY_STYPE = "유형별 배송지 조회 성공";

export const APPLY_SHIP = "배송지 적용 성공";
export const NOT_FOUND_SHIP = "배송지 조회 불가";

// 리뷰(Review) 9000번
export const READ_REVIEW_LIST = "리뷰 전체 리스트 조회 성공";
export const READ_REVIEW = "리뷰 상세 조회 성공";
export const READ_PRODUCT_REVIEW_RIST = "상품 별 리뷰 리스트 조회 성공";
export const READ_REVIEW_RATING = "상품 별 리뷰 평점 조회";
export const UPDATE_REVIEW = "리뷰 수정 성공";
export const DELETE_REVIEW = "리뷰 삭제 성공";
export const CREATED_REAVIEW = "리뷰 작성 성공";

export const RETRIEVE_FAIL_REVIEW_LIST = "리뷰 리스트를 불러올 수 없습니다";
export const RETRIEVE_FAIL_REVIEW = "리뷰를 불러올 수 없습니다";
export const RETRIEVE_FAIL_NOT_FOUND_REVIEW = "리뷰를 찾을 수 없습니다";
export const CREATE_FAIL_REVIEW = "리뷰 생성에 실패했습니다";
export const UNAUTHORIZED_REVIEW = "인증받지 않은 요청입니다";

// 회원(User) 1000번
export const LOGIN_SUCCESS = 1000;
export const READ_USER = 1100;
export const FIND_ID_SUCCESS = 1110;
export const FIND_ID_FAIL = 1111;
export const FIND_PW_SUCCESS = 1120;
export const FIND_PW_FAIL = 1121;
export const CREATED_USER = 1200;
export const UPDATE_USER = 1300;
export const DELETE_USER = 1400;
export const LOGOUT_USER = 1500;
export const LOGIN_FAIL_NOT_FOUND_USER = 1600;
export const LOGIN_FAIL_PASSWORD_MISMATCH_USER = 1700;
export const SUCCESS_PASSWORD_MISMATCH_USER = 1710;
export const CREATE_FAIL_EXISTED_EMAIL = 1800;
export const EMAIL_AVAILABLE = 1801;
export const CREATE_FAIL_EXISTED_NICKNAME = 1810;
export const NICKNAME_AVAILABLE = 1811;
export const UNAUTHORIZED_USER = 1900;

/* 상품(Product(4110 번대), ProductOption(4120 번대), ProductCategory(4130 번대)) 4000번대 */
export const CREATE_PRODUCT = 4111;

export const READ_PRODUCTCATEGORY = 4130;
export const CREATE_PRODUCTCATEGORY = 4131;
export const UPDATE_PRODUCTCATEGORY = 4132;
export const DELETE_PRODUCTCATEGORY = 4133;

/* 게시판 5000번 */
export const READ_BOARD_LIST = 5000;
export const READ_BOARD = 5100;
export const CREATED_BOARD = 2300;
export const UPDATE_BOARD = 5300;
export const DELETE_BOARD = 5400;
export const CREATED_COMMENT = 5500;

export const RETRIEVE_FAIL_BOARD_LIST = 5600;
export const RETRIEVE_FAIL_NOT_FOUND_BOARD = 5700;
export const CREATE_FAIL_BOARD = 5800;
export const UNAUTHORIZED_BOARD = 5900;

/* 쿠폰 6000번 */
export const CREATED_COUP = 6100;
export const READ_COUP = 6200;
export const DELETE_COUP = 6300;
export const DELETE_ALL_COUP = 6400;
export const APPLY_COUP = 6500;
export const NOT_FOUND_COUP = 6600;

/* 카트  7000번 */
export const ADD_CART = 7100;
export const READ_CART = 7200;
export const DELETE_CARTITEM = 7300;
export const DELETE_CHECKED_CARTITEM = 7400;
export const DUPLICATED_CARTITEM = 7500;

/* 배송지 8000번 */
export const CREATED_SHIP = 8100;
export const UPDATE_SHIP = 8200;
export const DELETE_SHIP = 8300;
export const READ_SHIP = 8400;
export const READ_SHIP_BY_USER = 8500;
export const READ_SHIP_BY_STYPE = 8600;

export const APPLY_SHIP = 8700;
export const NOT_FOUND_SHIP = 8800;
// 리뷰(Review) 9000번
export const READ_REVIEW_LIST = 9000;
export const READ_REVIEW = 9100;
export const READ_PRODUCT_REVIEW_RIST = 9200;
export const READ_REVIEW_RATING = 9300;
export const UPDATE_REVIEW = 9400;
export const DELETE_REVIEW = 9500;
export const CREATED_REAVIEW = 9600;

export const RETRIEVE_FAIL_REVIEW_LIST = 9700;
export const RETRIEVE_FAIL_REVIEW = 9800;
export const RETRIEVE_FAIL_NOT_FOUND_REVIEW = 9900;
export const CREATE_FAIL_REVIEW = 9990;
export const UNAUTHORIZED_REVIEW = 9999;

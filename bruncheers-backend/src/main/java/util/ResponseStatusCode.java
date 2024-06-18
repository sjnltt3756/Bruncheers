package util;

public class ResponseStatusCode {

	// 회원(User) 1000번
	public static final int LOGIN_SUCCESS = 1000;
	public static final int READ_USER = 1100;
	public static final int READ_USERLIST = 1110;

	public static final int FIND_ID_SUCCESS = 1110;
	public static final int FIND_PW_SUCCESS = 1120;

	public static final int CREATED_USER = 1200;
	public static final int UPDATE_USER = 1300;
	public static final int DELETE_USER = 1400;
	public static final int LOGOUT_USER = 1500;
	public static final int LOGIN_FAIL_NOT_FOUND_USER = 1600;
	public static final int LOGIN_FAIL_PASSWORD_MISMATCH_USER = 1700;
	public static final int SUCCESS_PASSWORD_MISMATCH_USER = 1710;

	public static final int CREATE_FAIL_EXISTED_EMAIL = 1800;
	public static final int EMAIL_AVAILABLE = 1801;

	public static final int CREATE_FAIL_EXISTED_NICKNAME = 1810;
	public static final int NICKNAME_AVAILABLE = 1811;

	public static final int UNAUTHORIZED_USER = 1900;

	// 상품(Product(4110 번대), ProductOption(4120 번대), ProductCategory(4130 번대)) 4000번대
	
	public static final int CREATE_PRODUCT = 4111;
	
	public static final int READ_PRODUCTCATEGORY = 4130;
	public static final int CREATE_PRODUCTCATEGORY = 4131;
	public static final int UPDATE_PRODUCTCATEGORY = 4132;
	public static final int DELETE_PRODUCTCATEGORY = 4133;

	// 게시판(Board) 5000번
	public static final int READ_BOARD_LIST = 5000;
	public static final int READ_BOARD = 5100;
	public static final int CREATED_BOARD = 2300;
	public static final int UPDATE_BOARD = 5300;
	public static final int DELETE_BOARD = 5400;
	public static final int CREATED_COMMENT = 5500;

	public static final int RETRIEVE_FAIL_BOARD_LIST = 5600;
	public static final int RETRIEVE_FAIL_NOT_FOUND_BOARD = 5700;
	public static final int CREATE_FAIL_BOARD = 5800;
	public static final int UNAUTHORIZED_BOARD = 5900;

	// 쿠폰(Coupon) 6000번
	public static final int CREATED_COUP = 6100;
	public static final int READ_COUP = 6200;
	public static final int DELETE_COUP = 6300;
	public static final int DELETE_ALL_COUP = 6400;

	public static final int APPLY_COUP = 6500;
	public static final int NOT_FOUND_COUP = 6600;

	// 카트(Cart) 7000번
	public static final int ADD_CART = 7100;
	public static final int READ_CART = 7200;
	public static final int DELETE_CARTITEM = 7300;
	public static final int DELETE_CHECKED_CARTITEM = 7400;

	public static final int DUPLICATED_CARTITEM = 7500;

	// 배송지(Ship) 8000번
	public static final int CREATED_SHIP = 8100;
	public static final int UPDATE_SHIP = 8200;
	public static final int DELETE_SHIP = 8300;
	public static final int READ_SHIP = 8400;
	public static final int READ_SHIP_BY_USER = 8500;
	public static final int READ_SHIP_BY_USER_FIRST_SHIP = 8600;
	public static final int READ_SHIP_BY_STYPE = 8700;

	public static final int APPLY_SHIP = 8800;
	public static final int NOT_FOUND_SHIP = 8900;

	// 리뷰(Review) 9000번
	public static final int READ_REVIEW_LIST = 9000;
	public static final int READ_REVIEW = 9100;
	public static final int READ_PRODUCT_REVIEW_RIST = 9200;
	public static final int READ_REVIEW_RATING = 9300;
	public static final int UPDATE_REVIEW = 9400;
	public static final int DELETE_REVIEW = 9500;
	public static final int CREATED_REAVIEW = 9600;

	public static final int RETRIEVE_FAIL_REVIEW_LIST = 9700;
	public static final int RETRIEVE_FAIL_REVIEW = 9800;
	public static final int RETRIEVE_FAIL_NOT_FOUND_REVIEW = 9900;
	public static final int CREATE_FAIL_REVIEW = 9990;
	public static final int UNAUTHORIZED_REVIEW = 9999;

}

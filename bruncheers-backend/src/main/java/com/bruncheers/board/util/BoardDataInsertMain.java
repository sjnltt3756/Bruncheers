package com.bruncheers.board.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BoardDataInsertMain {

    public static void main(String[] args) {
        String url = "jdbc:oracle:thin:@124.198.47.195:1521:xe"; // Replace with your database URL
        String username = "jdeveloper09"; // Replace with your database username
        String password = "jdeveloper09"; // Replace with your database password

        Connection con = null;
        try {
            con = DriverManager.getConnection(url, username, password);
            try (Statement stmt = con.createStatement()) {
                // 트랜잭션 시작
                con.setAutoCommit(false);

                // 대량의 데이터 삽입
                for (int i = 1; i <= 500; i++) {
                    stmt.addBatch(
                            "INSERT INTO board (b_no, b_title, b_content, b_readcount, b_date, b_groupno, b_step, b_depth, b_category, b_rn, user_no)\n"
                            + "VALUES (board_b_no_seq.NEXTVAL, '브런치얼스가 좋으세요? 디너덜트가 좋으세요?', '난 디너덜트 이거 없어지면 브런치얼스가 지운겁니다.', 0, SYSDATE, 3, 1, 0, 2, 0, 2)");
                }

                // 배치 실행
                int[] updateCounts = stmt.executeBatch();
                System.out.println("query 수:" + updateCounts.length);

                // 커밋
                con.commit();
                System.out.println("success commit!!!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (con != null) {
                    // 롤백
                    con.rollback();
                    System.out.println("rollback !!!");
                }
            } catch (SQLException e1) {
                System.out.println("rollback fail!!!");
            }
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

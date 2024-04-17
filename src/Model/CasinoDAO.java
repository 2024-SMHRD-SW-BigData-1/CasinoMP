package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CasinoDAO {

	Connection conn = null;
	PreparedStatement psmt = null;
	ResultSet rs = null;

	// 데이터베이스와의 동적로딩/권한확인
	public void dbOpen() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			String id = "campus_24SW_BIG_p1_1";
			String pw = "smhrd1";
			String url = "jdbc:oracle:thin:@project-db-campus.smhrd.com:1524:xe";

			conn = DriverManager.getConnection(url, id, pw);
		} catch (ClassNotFoundException e) {
			System.out.println("동적 로딩 실패");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("권한 확인 실패");
			e.printStackTrace();
		}

	}

	// 데이터베이스 자원 반납
	public void dbClose() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (psmt != null) {
				psmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 플레이어 로그인 일치/불일치 확인
	public CasinoDTO playerLogin(CasinoDTO dto) { // id, pw

		int cnt = 0;
		dbOpen(); // 동적로딩
		String sql = "select * from casino_user where id=? and pw=?";
		try {
			psmt = conn.prepareStatement(sql);

			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getPw());

			rs = psmt.executeQuery();
			
			if (rs.next()) {
				String id = rs.getString("ID");
				String pw = rs.getString("PW");
				String nick = rs.getString("NICK");
				String phoneNumber = rs.getString("PHONE_NUMBER");
				int chip = rs.getInt("CHIP");
				int blackjack = rs.getInt("BLACKJACK");
				int slot = rs.getInt("SLOT");
				int holdem = rs.getInt("HOLDEM");

				dto = new CasinoDTO(id, pw, nick, phoneNumber, chip, blackjack, slot, holdem);
			}

		} catch (SQLException e) {
			System.out.println("SQL문 오류!");
		} finally {
			dbClose();
		}
		
		return dto;
	}

	// 회원가입기능구현
	public int insert(CasinoDTO dto) {

		int cnt = 0;
		dbOpen();
		try {

			String sql = "INSERT INTO CASINO_USER VALUES (?, ?, ?, ?, 3000, 0, 0, 0)";

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getPw());
			psmt.setString(3, dto.getNick());
			psmt.setString(4, dto.getPhoneNumber());

			cnt = psmt.executeUpdate(); // 최종 데이터베이스 연결값

		} catch (SQLException e) {
			System.out.println("권한연결실패");
			e.printStackTrace();
		} finally {
			dbClose();
		}

		return cnt;
	}
	
	public CasinoDTO findPlayer(CasinoDTO dto) {
		int cnt = 0;
		dbOpen(); // 동적로딩
		String sql = "select * from casino_user where phone_number = ?";
		try {
			psmt = conn.prepareStatement(sql);

			psmt.setString(1, dto.getPhoneNumber());

			rs = psmt.executeQuery();
			
			if (rs.next()) {
				String id = rs.getString("ID");
				String pw = rs.getString("PW");
				String nick = rs.getString("NICK");
				String phoneNumber = rs.getString("PHONE_NUMBER");
				int chip = rs.getInt("CHIP");
				int blackjack = rs.getInt("BLACKJACK");
				int slot = rs.getInt("SLOT");
				int holdem = rs.getInt("HOLDEM");

				dto = new CasinoDTO(id, pw);
			}

		} catch (SQLException e) {
			System.out.println("SQL문 오류!");
		} finally {
			dbClose();
		}
		
		return dto;
		
	}
	
	
	
	
	
	
	
}
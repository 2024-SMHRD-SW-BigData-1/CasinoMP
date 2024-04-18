package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class CasinoDAO {

	// ============== 블랙잭=================
	public ArrayList<String> playerCard;
	public ArrayList<String> dealerCard;
	public int playerSum; // 플레이어 카드 총합
	public int dealerSum; // 딜러 카드 총합
	public int jungbok;
	public int chip;
	public int bet; // 베팅할 금액
	public Random rd;
	public String drow1; // 문양 결정
	public String drow2; // 숫자 결정
	public String stop; // Stop 여부
	public String resume; // 게임을 계속할지 여부

	public String[] suit;
	public String[] ranks;

	// ====================홀덤====================
	public int chip2;
	public String[] suit2;
	public String[] ranks2;
	public String conti;
	public ArrayList<String> player;
	public ArrayList<String> com;
	public ArrayList<String> comCard;
	public int jungbok2;
	public int bet2;
	public String die;
	public String drow3;
	public String drow4;
	public ArrayList<String> SumPlayer;
	public ArrayList<String> SumCom;
	public int suitCount[]; // 카드 문양 별 (스,다,하,클) 개수 카운트
	public int maxSuit; // 개수 중 가장 많은 문양을 알아내기 위한 변수
	public int rankCount[]; // 숫자 개수. 14번째는 마운틴 판별을 위해 A를 위한 행 추가
	public int isStrait; // 연속된 숫자의 개수를 구하는 함수
	public String compareCount[]; // 위해 생성
	public String jokbo[];
	public double playerJokbo; // 플레이어의 족보 저장. 기본은 하이카드(12)
	public double comJokbo;
	public int fh;
	public int tp; // 투페어 중복 검출 방지
	public int op; // 원페어 중복 검출 방지
	// ==================== 슬롯 ========================

	private int chip3;
	private double bet3;
	private double minus;
	private String prob[];
	private String hit[];
	private int per[];
	private String slot[][];
	private String play;

	// ====================================================

	Connection conn = null;
	PreparedStatement psmt = null;
	ResultSet rs = null;

	Scanner sc = new Scanner(System.in);

	public CasinoDAO() {
		playerCard = new ArrayList<String>();
		dealerCard = new ArrayList<String>();
		playerSum = 0;
		dealerSum = 0;
		jungbok = 0;
		chip = 3000;
		bet = 0;
		rd = new Random();
		suit = new String[] { "Spades", "Diamonds", "Hearts", "Clubs" };
		ranks = new String[] { "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King" };

		// =============홀덤===================
		chip2 = 3000;
		suit2 = new String[] { "Spade", "Diamond", "Heart", "Club" };
		ranks2 = new String[] { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
		conti = null;
		player = new ArrayList<String>();
		com = new ArrayList<String>();
		comCard = new ArrayList<String>();
		jungbok2 = 0;
		bet2 = 0;
		die = null;
		drow3 = null;
		drow4 = null;
		SumPlayer = new ArrayList<String>();
		SumCom = new ArrayList<String>();
		suitCount = new int[] { 0, 0, 0, 0 }; // 카드 문양 별 (스,다,하,클) 개수 카운트
		maxSuit = 0; // 개수 중 가장 많은 문양을 알아내기 위한 변수
		rankCount = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; // 숫자 개수. 14번째는 마운틴 판별을 위해 A를 위한 행 추가
		isStrait = 0; // 연속된 숫자의 개수를 구하는 함수
		compareCount = new String[] { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" }; // 숫자 개수
		// 파악을
		// 위해 생성
		jokbo = new String[] { "§★로얄 스트레이트 플러쉬★§", "§☆스트레이트 플러쉬☆§", "§포카드§", "※풀하우스※", "○플러쉬○", "▲마운틴 스트레이트▲",
				"＆백스트레이트＆", "↑스트레이트↑", "＊트리플＊", "투페어", "원페어", "하이카드..." };
		playerJokbo = 12; // 플레이어의 족보 저장. 기본은 하이카드(12)
		comJokbo = 12;
		fh = 0;
		tp = 0; // 투페어 중복 검출 방지
		op = 0; // 원페어 중복 검출 방지

		// ============== 슬롯 ===============

		chip3 = 3000;
		bet3 = 0;
		minus = 0;
		play = null;

		prob = new String[] { "777", "77", "77", "7", "7", "7", "bar", "bar", "수박", "수박", "수박", "수박", "수박", "종", "종",
				"종", "종", "종", "종", "종", "사과", "사과", "사과", "사과", "사과", "사과", "사과", "사과", "체리", "체리", "체리" };
		hit = new String[] { "777", "77", "7", "bar", "수박", "종", "사과", "체리" };
		per = new int[] { 70, 50, 30, 20, 15, 12, 9, 8, 6 };
		slot = new String[3][3]; 
	}

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

	// 플레이어찾기 구현
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

	// 게임이 끝난뒤 UPDATE
	public CasinoDTO updatePlayer(CasinoDTO dto) {
		int cnt = 0;
		dbOpen(); // 동적로딩
		String sql = "update casino_user set chip = ?, blackjack = ? where id = ?";
		try {
			psmt = conn.prepareStatement(sql);
			// ? 인자 채워주기
			psmt.setInt(1, dto.getChip());
			psmt.setInt(2, dto.getBlackjack());
			psmt.setString(3, dto.getId());

			// psmt에 있는 쿼리문을 실행/결과값 처리
			cnt = psmt.executeUpdate();
			// executeUpdate - 쿼리문을 실행, 영향을 받은 행이 있는지 없는지 int자료형의 결과값
			if (cnt > 0) {
				System.out.println("플레이어 업데이트 성공");
			} else {
				System.out.println("플레이어 업데이트 실패");
			}

		} catch (SQLException e) {
			System.out.println("SQL문 오류!");
		} finally {
			dbClose();
		}

		return dto;

	}

	// 칩별랭킹출력
	public ArrayList<CasinoDTO> rankChip() {
		ArrayList<CasinoDTO> list = new ArrayList<>();

		dbOpen(); // 동적로딩

		String sql = "select * from casino_user order by chip desc";

		try {
			psmt = conn.prepareStatement(sql);

			rs = psmt.executeQuery();

			while (rs.next()) {
				String id = rs.getString("ID");
				String pw = rs.getString("PW");
				String nick = rs.getString("NICK");
				String phoneNumber = rs.getString("PHONE_NUMBER");
				int chip = rs.getInt("CHIP");
				int blackjack = rs.getInt("BLACKJACK");
				int slot = rs.getInt("SLOT");
				int holdem = rs.getInt("HOLDEM");

				CasinoDTO dto = new CasinoDTO(id, pw, nick, phoneNumber, chip, blackjack, slot, holdem);
				list.add(dto);
			}

		} catch (SQLException e) {
			System.out.println("sql문 예외상황 발생");
			e.printStackTrace();
		} finally {
			dbClose();
		}

		return list;
	}

	// BLACKJACK 랭킹출력
	public ArrayList<CasinoDTO> rankGameBJ() {
		ArrayList<CasinoDTO> list = new ArrayList<>();

		dbOpen(); // 동적로딩

		String sql = "select * from casino_user order by blackjack desc";

		try {
			psmt = conn.prepareStatement(sql);

			rs = psmt.executeQuery();

			while (rs.next()) {
				String id = rs.getString("ID");
				String pw = rs.getString("PW");
				String nick = rs.getString("NICK");
				String phoneNumber = rs.getString("PHONE_NUMBER");
				int chip = rs.getInt("CHIP");
				int blackjack = rs.getInt("BLACKJACK");
				int slot = rs.getInt("SLOT");
				int holdem = rs.getInt("HOLDEM");

				CasinoDTO dto = new CasinoDTO(id, pw, nick, phoneNumber, chip, blackjack, slot, holdem);
				list.add(dto);
			}

		} catch (SQLException e) {
			System.out.println("sql문 예외상황 발생");
			e.printStackTrace();
		} finally {
			dbClose();
		}

		return list;
	}

	// 슬롯 랭킹출력
	public ArrayList<CasinoDTO> rankGameSlot() {
		ArrayList<CasinoDTO> list = new ArrayList<>();

		dbOpen(); // 동적로딩

		String sql = "select * from casino_user order by slot desc";

		try {
			psmt = conn.prepareStatement(sql);

			rs = psmt.executeQuery();

			while (rs.next()) {
				String id = rs.getString("ID");
				String pw = rs.getString("PW");
				String nick = rs.getString("NICK");
				String phoneNumber = rs.getString("PHONE_NUMBER");
				int chip = rs.getInt("CHIP");
				int blackjack = rs.getInt("BLACKJACK");
				int slot = rs.getInt("SLOT");
				int holdem = rs.getInt("HOLDEM");

				CasinoDTO dto = new CasinoDTO(id, pw, nick, phoneNumber, chip, blackjack, slot, holdem);
				list.add(dto);
			}

		} catch (SQLException e) {
			System.out.println("sql문 예외상황 발생");
			e.printStackTrace();
		} finally {
			dbClose();
		}

		return list;
	}

	// 홀덤 랭킹출력
	public ArrayList<CasinoDTO> rankGameHD() {
		ArrayList<CasinoDTO> list = new ArrayList<>();

		dbOpen(); // 동적로딩

		String sql = "select * from casino_user order by holdem desc";

		try {
			psmt = conn.prepareStatement(sql);

			rs = psmt.executeQuery();

			while (rs.next()) {
				String id = rs.getString("ID");
				String pw = rs.getString("PW");
				String nick = rs.getString("NICK");
				String phoneNumber = rs.getString("PHONE_NUMBER");
				int chip = rs.getInt("CHIP");
				int blackjack = rs.getInt("BLACKJACK");
				int slot = rs.getInt("SLOT");
				int holdem = rs.getInt("HOLDEM");

				CasinoDTO dto = new CasinoDTO(id, pw, nick, phoneNumber, chip, blackjack, slot, holdem);
				list.add(dto);
			}

		} catch (SQLException e) {
			System.out.println("sql문 예외상황 발생");
			e.printStackTrace();
		} finally {
			dbClose();
		}

		return list;
	}

	public void playBlackJack(CasinoDTO dto) {

	}

	// 게임마다 변수 초기화
	public void startGame() {
		playerCard.clear(); // 매판 카드가 들어갈 배열을 clear
		dealerCard.clear();
		playerSum = 0; // 매판 카드의 합 값 초기화
		dealerSum = 0;
		stop = null; // 매판 선택 초기화
		resume = null; // 게임을 계속할지 여부 초기화
		drow1 = null;
		drow2 = null;
	}

	public int betting(CasinoDTO dto) {
		chip = dto.getChip();
		System.out.println("베팅할 칩의 개수를 입력해주세요. ");
		bet = sc.nextInt(); // 베팅할 금액

		return bet;
	}

	// 카드를 나눠주는 메소드
	public void dealCards() {
		for (int j = 0; j < 2; j++) { // 플레이어 카드 및 딜러 카드 2장씩 뽑겠다
			while (true) { // 중복 여부 검사 후 중복이 아니면 플레이어 카드에 입력
				jungbok = 0;
				drow1 = suit[rd.nextInt(4)];
				drow2 = ranks[rd.nextInt(13)];
				for (int i = 0; i < playerCard.size() / 2; i++) { // 이전 플레이어 카드와 비교
					if (playerCard.get(i * 2).equals(drow1) && playerCard.get(i * 2 + 1).equals(drow2)) {
						jungbok = 1;
					}
				}
				if (jungbok == 0) { // 카드 값 입력
					playerCard.add(drow1);
					playerCard.add(drow2);
					break;
				}
			}
			while (true) { // 중복 여부 검사 후 중복이 아니면 딜러 카드에 입력
				jungbok = 0;
				drow1 = suit[rd.nextInt(4)];
				drow2 = ranks[rd.nextInt(13)];
				for (int i = 0; i < playerCard.size() / 2; i++) { // 카드를 이전 플레이어 카드와 비교
					if (playerCard.get(i * 2).equals(drow1) && playerCard.get(i * 2 + 1).equals(drow2)) {
						jungbok = 1;
					}
				}
				for (int i = 0; i < dealerCard.size() / 2; i++) { // 카드를 이전 딜러카드와 비교
					if (dealerCard.get(i * 2).equals(drow1) && dealerCard.get(i * 2 + 1).equals(drow2)) {
						jungbok = 1;
					}
				}
				if (jungbok == 0) { // 카드 값 입력
					dealerCard.add(drow1);
					dealerCard.add(drow2);
					break;
				}
			}
		}
	}

	// 플레이어, 딜러 카드 합계 계산
	public void cardsSum() {
		for (int i = 0; i < 2; i++) { // 각각 카드 확인 후 Sum
			for (int j = 0; j < ranks.length; j++) {
				if (playerCard.get(i * 2 + 1).equals(ranks[j])) {
					if (j >= 10) {
						playerSum += 10;
					} else {
						playerSum += j + 1;
					}
				}
				if (dealerCard.get(i * 2 + 1).equals(ranks[j])) {
					if (j >= 10) {
						dealerSum += 10;
					} else {
						dealerSum += j + 1;
					}
				}
			}
		}
	}

	// 플레이어, 딜러 초반2장 카드 출력
	public void viewPlayer() {
		System.out.println("플레이어가 뽑은 카드 : " + playerCard.get(0) + " " + playerCard.get(1) + ", " + playerCard.get(2)
				+ " " + playerCard.get(3) + "\n플레이어카드 총 합 : " + playerSum);
		System.out.println("딜러가 뽑은 카드 : " + dealerCard.get(0) + " " + dealerCard.get(1) + ", " + dealerCard.get(2) + " "
				+ dealerCard.get(3) + "\n딜러카드총 합 : " + dealerSum);
	}

	public void viewAll() {
		for (String s : playerCard) {
			System.out.print(s + " ");
		}
		System.out.println();
		for (String d : dealerCard) {
			System.out.print(d + " ");
		}
	}

	public String bust() {
		// 카드 2장을 받고 난 이후 게임 진행
		// 둘중 하나의 합계가 21이 넘으면 버스트되어 종료!
		// stop을 stop으로 return
		stop = "init";
		if (dealerSum > 21 || playerSum > 21) {
			stop = "버스트";
		}
		return stop;
	}

	public int gamePlay() {
		return playerSum;
	}

	// 위에서 나온 코드 반복. 플레이어 카드 1장뽑기
	public String gamePlay2() {
		stop = "init";
		jungbok = 0;
		drow1 = suit[rd.nextInt(4)];
		drow2 = ranks[rd.nextInt(13)];
		for (int i = 0; i < playerCard.size() / 2; i++) {
			if (playerCard.get(i * 2).equals(drow1) && playerCard.get(i * 2 + 1).equals(drow2)) {
				jungbok = 1;
			}
		}
		for (int i = 0; i < dealerCard.size() / 2; i++) {
			if (dealerCard.get(i * 2).equals(drow1) && dealerCard.get(i * 2 + 1).equals(drow2)) {
				jungbok = 1;
			}
		}
		if (jungbok == 0) {
			playerCard.add(drow1);
			playerCard.add(drow2);
			for (int j = 0; j < ranks.length; j++) {
				if (playerCard.get(playerCard.size() - 1).equals(ranks[j])) {
					if (j >= 10) {
						playerSum += 10;
					} else {
						playerSum += j + 1;
					}
					System.out.println("플레이어가 뽑은 카드 : " + playerCard.get(playerCard.size() - 2)
							+ playerCard.get(playerCard.size() - 1) + "\n총 합 : " + playerSum);
				}
			}
			stop = "stop";
			return stop;
		}
		return stop;
	}

	public int getDealerSum() {
		return dealerSum;
	}

	public String dealerPlay() {
		stop = "init";
		jungbok = 0;
		drow1 = suit[rd.nextInt(4)];
		drow2 = ranks[rd.nextInt(13)];
		for (int i = 0; i < playerCard.size() / 2; i++) {
			if (playerCard.get(i * 2).equals(drow1) && playerCard.get(i * 2 + 1).equals(drow2)) {
				jungbok = 1;
			}
		}
		for (int i = 0; i < dealerCard.size() / 2; i++) {
			if (dealerCard.get(i * 2).equals(drow1) && dealerCard.get(i * 2 + 1).equals(drow2)) {
				jungbok = 1;
			}
		}
		if (jungbok == 0) {
			dealerCard.add(drow1);
			dealerCard.add(drow2);
			stop = "stop";
			return stop;
		}
		return stop;
	}

	public void dealerPlay2() {
		for (int j = 0; j < ranks.length; j++) {
			if (dealerCard.get(dealerCard.size() - 1).equals(ranks[j])) {
				if (j >= 10) {
					dealerSum += 10;
				} else {
					dealerSum += j + 1;
				}
				System.out.println("딜러가 뽑은 카드 : " + dealerCard.get(dealerCard.size() - 2)
						+ dealerCard.get(dealerCard.size() - 1) + "\n총 합 : " + dealerSum);
			}
		}
	}

	// 플레이어가 stop한 상태 이후에 딜러가 17보다 작으면 클때까지 뽑음.
	public void afterPlayerStop() {
		jungbok = 0;
		drow1 = suit[rd.nextInt(4)];
		drow2 = ranks[rd.nextInt(13)];
		for (int i = 0; i < playerCard.size() / 2; i++) {
			if (playerCard.get(i * 2).equals(drow1) && playerCard.get(i * 2 + 1).equals(drow2)) {
				jungbok = 1;
			}
		}
		for (int i = 0; i < dealerCard.size() / 2; i++) {
			if (dealerCard.get(i * 2).equals(drow1) && dealerCard.get(i * 2 + 1).equals(drow2)) {
				jungbok = 1;
			}
		}
	}

	public int getJungbok() {
		return jungbok;
	}

	public void afterPlayerStop2() {
		if (jungbok == 0) {
			dealerCard.add(drow1);
			dealerCard.add(drow2);
			for (int j = 0; j < ranks.length; j++) {
				if (dealerCard.get(dealerCard.size() - 1).equals(ranks[j])) {
					if (j >= 10) {
						dealerSum += 10;
						dealerSum += j + 1;
					}
				}
			}
			System.out.println("딜러가 뽑은 카드 : " + dealerCard.get(dealerCard.size() - 2)
					+ dealerCard.get(dealerCard.size() - 1) + "\n총 합 : " + dealerSum);
		}
	}

	public String endGame(int bet, String resume) {
		resume = null;
		System.out.println("플레이어의 합 : " + playerSum + " 딜러의 합 : " + dealerSum);
		if ((playerSum >= 22 && dealerSum >= 22) || (playerSum == dealerSum && playerSum <= 21)) { // 무승부 조건
			System.out.println("무승부!! 베팅한 금액을 되돌려받습니다!\n현재 보유 금액 : " + chip + "\n계속하시겠습니까?(Y/N)");
			resume = sc.next();
			if (resume.equals("N")) {
				return resume;
			}
		} else if ((playerSum >= 22 && dealerSum < 22) || ((dealerSum < 22) && (playerSum < dealerSum))) { // 패배 조건
			chip -= bet;
			System.out.println("플레이어 패배!! 베팅한 금액을 잃습니다!\n현재 보유 금액 : " + chip + "\n계속하시겠습니까?(Y/N)");
			resume = sc.next();
			if (resume.equals("N")) {
				return resume;
			}
		} else { // 승리 조건
			chip += bet;
			System.out.println("플레이어 승리!! 베팅한 금액만큼 받습니다!\n현재 보유 금액 : " + chip + "\n계속하시겠습니까?(Y/N)");
			resume = sc.next();
			if (resume.equals("N")) {
				System.out.println("게임을 종료합니다.");
				return resume;
			}
		}
		if (chip - bet < 0) {
			System.out.println("보유한 칩이 부족합니다. 게임을 종료합니다.");
			resume = "N";
			return resume;
		}
		return resume;
	}

	public int getChip() {
		return chip;
	}

	// ======================홀덤===================

	public void chogihwa() {
		player.clear();
		com.clear();
		comCard.clear();
		SumCom.clear();
		SumPlayer.clear();
		for (int i = 0; i < suitCount.length; i++) {
			suitCount[i] = 0;
		}
		for (int i = 0; i < rankCount.length; i++) {
			rankCount[i] = 0;
		}
		playerJokbo = 12; // 플레이어의 족보 저장. 기본은 하이카드(12)
		comJokbo = 12;
	}

	public void getPlayerCard() {
		while (true) { // 플레이어 카드 뽑기
			jungbok2 = 0;
			drow3 = suit2[rd.nextInt(4)]; // 임의의 색상 뽑기
			drow4 = ranks2[rd.nextInt(13)]; // 임의의 숫자 뽑기
			for (int i = 0; i < player.size() / 2; i++) { // 중복 여부 확인
				if (player.get(i * 2).equals(drow3) && player.get(i * 2 + 1).equals(drow4)) {
					jungbok2 = 1; // 중복일때 1
				}
			}
			if (jungbok2 == 0) { // 플레이어 카드 적용. 0 == 중복이 없을 때.
				player.add(drow3);
				player.add(drow4);
				break;
			}
		}
	}

	public void getComCard() {
		while (true) {
			jungbok2 = 0;
			drow3 = suit2[rd.nextInt(4)]; // 임의의 색상 뽑기
			drow4 = ranks2[rd.nextInt(13)]; // 임의의 숫자 뽑기
			for (int i = 0; i < player.size() / 2; i++) { // 플레이어 카드와 중복 여부 확인
				if (player.get(i * 2).equals(drow3) && player.get(i * 2 + 1).equals(drow4)) {
					jungbok2 = 1;
				}
			}
			for (int i = 0; i < com.size() / 2; i++) { // 컴퓨터 카드와 중복 여부 확인
				if (com.get(i * 2).equals(drow3) && com.get(i * 2 + 1).equals(drow4)) {
					jungbok2 = 1;
				}
			}
			if (jungbok2 == 0) { // 컴퓨터 카드로 적용
				com.add(drow3);
				com.add(drow4);
				break;
			}
		}
	}

	public void printPlayerCard() {
		System.out.println(
				"플레이어 카드 : " + player.get(0) + " " + player.get(1) + "," + player.get(2) + " " + player.get(3));
	}

	public void getCommonCard() { // 첫 공통카드 3장 뽑기
		while (true) {
			jungbok2 = 0;
			drow3 = suit2[rd.nextInt(4)];
			drow4 = ranks2[rd.nextInt(13)];
			for (int i = 0; i < player.size() / 2; i++) { // com,player 카드와 중복여부 비교
				if (com.get(i * 2).equals(drow3) && com.get(i * 2 + 1).equals(drow4)) {
					jungbok2 = 1;
				}
				if (player.get(i * 2).equals(drow3) && player.get(i * 2 + 1).equals(drow4)) {
					jungbok2 = 1;
				}
			}
			for (int i = 0; i < comCard.size() / 2; i++) { // 공용 카드와 중복여부 비교
				if (comCard.get(i * 2).equals(drow3) && comCard.get(i * 2 + 1).equals(drow4)) {
					jungbok2 = 1;
				}
			}
			if (jungbok2 == 0) {
				comCard.add(drow3);
				comCard.add(drow4);
				break;
			}
		}
	}

	public void printCommonCard() {
		System.out.print("공통 카드 : ");
		for (int i = 0; i < comCard.size() / 2; i++) {
			System.out.print(comCard.get(i * 2) + " " + comCard.get(i * 2 + 1) + ", ");
		}
	}

	public void SumCard() {
		SumPlayer.addAll(player);
		SumCom.addAll(com);
		SumPlayer.addAll(comCard);
		SumCom.addAll(comCard);
	}

	public void isPlayerFlush() {
		// 족보 판정
		// 1. 로티플, 스티플, 플러쉬 판정 (플레이어부터 진행)
		for (int i = 0; i < SumPlayer.size() / 2; i++) {
			for (int j = 0; j < suitCount.length; j++) { // 문양 같은게 몇개 있는지 구하는 식
				if (SumPlayer.get(i * 2).equals(suit2[j])) {
					suitCount[j]++;
				}
			}
		}
		System.out.print("플레이어 : ");
		for (int i = 1; i < suitCount.length; i++) { // 가장 많은수의 문양을 구하는 변수 maxRank)
			if (suitCount[i] > suitCount[i - 1]) {
				maxSuit = i; // 0은 스페이드, 1은 다, 2는 하, 3은 클
			}
		}
		for (int i = 0; i < suitCount.length; i++) { // 판정하기 위해 문양 개수 세는중
			if (suitCount[i] >= 5) { // 플러쉬로 판정이 나면(같은 문양이 5개 이상이면)
				for (int j = SumPlayer.size() / 2 - 1; j >= 0; j--) { // 스티플, 로티플 판정 시작
					if (!SumPlayer.get(j * 2).equals(suit2[maxSuit])) { // 문양 다른 카드들 삭제
						SumPlayer.remove(j * 2 + 1);
						SumPlayer.remove(j * 2);
					}
				}
				for (int j = 0; j < SumPlayer.size() / 2; j++) { // 숫자 같은게 몇개 있는지 구하는 식
					for (int k = 0; k < compareCount.length; k++) { // 비교할 카드수만큼 반복
						if (SumPlayer.get(j * 2 + 1).equals(compareCount[k])) { // 숫자 인덱스가
							rankCount[k]++;
						}
					}
				}
				for (int j = rankCount.length - 1; j >= 0; j--) { // 스트레이트 판별 중
					if (rankCount[j] != 0) { // rank에 있는 숫자열 중 0이 아닐때
						isStrait++; // strait 개수가 올라감
						if (isStrait >= 5) { // 5개가 넘으면 스트레이트로 판정
							if (j == rankCount.length - 1) {
								System.out.println(suit2[maxSuit] + jokbo[0]);
								playerJokbo = 1;
								break; // 판별완료. 포문 나가기
							} else {
								System.out.println(compareCount[j] + " " + jokbo[1]);
								playerJokbo = 2 - 0.01 * j;
								break; // 판별완료. 포문 나가기
							}
						}
					} else {
						isStrait = 0; // 스트레이트 아닐때 숫자 초기화
					}
				}
				for (int j = rankCount.length - 1; j >= 0; j--) { // 높은 숫자가 이기도록 족보숫자를 더 낮게 설정
					if (rankCount[j] != 0) {
						System.out.println(compareCount[j] + " " + jokbo[4]);
						playerJokbo = 5 - 0.01 * j;
						break;
					}
				}

			}
		}
	}

	public void isPlayerStrait() {
		// 2. 스트레이트 계열
		for (int i = 0; i < SumPlayer.size() / 2; i++) {
			for (int j = 0; j < compareCount.length; j++) { // 숫자 같은게 몇개 있는지 구하는 식
				if (SumPlayer.get(i * 2 + 1).equals(compareCount[j])) {
					rankCount[j]++;
				}
			}
		}
		if (playerJokbo == 12) {
			for (int i = rankCount.length - 1; i >= 0; i--) { // 스트레이트 판별 중. 6개 이상 방지를 위해 높은수 -> 낮은수로 진행
				if (rankCount[i] != 0) { // rank에 있는 숫자열 중 0이 아닐때
					isStrait++; // strait 개수가 올라감
					if (isStrait >= 5) { // 5개가 넘으면 스트레이트로 판정
						if (i == 9) {
							System.out.println(jokbo[5]); // 순차적으로 내려오다가 j = 9면 AKQJ10이므로 마운틴
							playerJokbo = 6;
							break; // 판별완료. 포문 나가기
						} else if (i == 0) {
							System.out.println(jokbo[6]); // j = 0이라면 5432A이므로 백스트레이트
							playerJokbo = 7;
							break; // 판별완료. 포문 나가기
						} else {
							System.out.println(ranks2[i + 4] + " " + jokbo[7]); // 나머지는 가장 높은 숫자의 스트레이트
							playerJokbo = 8 - 0.01 * i; // 스트레이트 끼리는 높은 숫자의 값에 따라 승패 비교가 가능
							break;
						}
					}
				} else {
					isStrait = 0; // 스트레이트 아닐때 숫자 초기화
				}
			}
		}
	}

	public void isPlayerHighPair() {
		// 3. 페어 계열 (포카드, 풀하우스, 트리플부터 검출)
		fh = 0;
		if (playerJokbo == 12) {
			for (int i = rankCount.length - 1; i >= 0; i--) {
				if (rankCount[i] == 4) { // 같은 숫자가 4개면
					System.out.println(compareCount[i] + " " + jokbo[2]); // 포카드
					playerJokbo = 3 - 0.01 * i;
				}
				if (rankCount[i] == 3) { // 같은 숫자가 3개일때
					for (int j = 0; j < rankCount.length; j++) { // 풀하우스 가능성 확인
						if (rankCount[j] == 2) { // 트리플 + 페어
							System.out.println(compareCount[i] + " " + jokbo[3]); // 풀하우스
							playerJokbo = 4 - 0.01 * i; // rank에 따라 높이 달라지는 거 반영
							fh = 1;
							break;
						}
					}
				}
			}
			for (int i = rankCount.length - 1; i >= 0; i--) {
				if (fh != 1 && rankCount[i] == 3) {
					System.out.println(compareCount[i] + " " + jokbo[8]);
					playerJokbo = 9 - 0.01 * i;
				}
			}
		}
	}

	public void isPlayerLowPair() {
		// 4. 투페어 이하 검출
		tp = 0; // 투페어 중복 검출 방지
		op = 0; // 원페어 중복 검출 방지
		if (playerJokbo == 12) {
			for (int i = rankCount.length - 1; i >= 1; i--) { // 높은수부터 페어 검사
				if (rankCount[i] == 2) { // 페어가 발견되었을때
					for (int j = rankCount.length - 1; j >= 0; j--) { // 높은 수부터 검사
						if (rankCount[j] == 2 && j != i) { // 또다른 페어가 발견되면 (서로 다른 페어)
							System.out.println(compareCount[i] + ", " + compareCount[j] + " " + jokbo[9]); // 투페어
							playerJokbo = 10 - 0.01 * i - 0.001 * j; // rank에 따라 높이 달라지는 거 반영
							tp = 1;
							break;
						} else if (j == 1 && tp != 1 && op != 1) { // 발견되지 않고 끝까지 간다면
							System.out.println(compareCount[i] + " " + jokbo[10]); // 원페어
							playerJokbo = 11 - 0.01 * i - 0.001 * j; // rank에 따라 높이 달라지는 거 반영
							op = 1;
							break;
						}
					}
					if (tp == 1) {
						break; // A,A 투페어가 나오는 경우 방지
					}
				}
			}
		}
	}

	public void isPlayerHighcard() {
		// 5. 하이카드 검출
		if (playerJokbo == 12) {
			for (int i = rankCount.length - 1; i >= 0; i--) {
				if (rankCount[i] == 1 && i == 13) {
					System.out.println("A " + jokbo[11]);
					playerJokbo = 12 - 0.01 * i;
					break;
				} else if (rankCount[i] == 1) {
					System.out.println(ranks2[i] + " " + jokbo[11]);
					playerJokbo = 12 - 0.01 * i;
					break;
				}
			}
		}
	}

	public void comChogihwa() {
		for (int i = 0; i < suitCount.length; i++) {
			suitCount[i] = 0;
		}
		for (int i = 0; i < rankCount.length; i++) {
			rankCount[i] = 0;
		}
		maxSuit = 0;
		isStrait = 0;
	}

	public void isComFlush() {
		for (int i = 0; i < suitCount.length; i++) {
			suitCount[i] = 0;
		}
		for (int i = 0; i < SumCom.size() / 2; i++) {
			for (int j = 0; j < suitCount.length; j++) { // 문양 같은게 몇개 있는지 구하는 식
				if (SumCom.get(i * 2).equals(suit2[j])) {
					suitCount[j]++;
				}
			}
		}

		// 족보 판정
		// 1. 로티플, 스티플, 플러쉬 판정
		System.out.println(
				"플레이어 카드 : " + player.get(0) + " " + player.get(1) + "," + player.get(2) + " " + player.get(3));
		System.out.println("딜러 카드 : " + com.get(0) + " " + com.get(1) + "," + com.get(2) + " " + com.get(3));
		System.out.print("딜러 : ");
		for (int i = 1; i < suitCount.length; i++) { // 가장 많은수의 문양을 구하는 변수 maxRank)
			if (suitCount[i] > suitCount[i - 1]) {
				maxSuit = i; // 0은 스페이드, 1은 다, 2는 하, 3은 클
			}
		}
		for (int i = 0; i < suitCount.length; i++) { // 판정하기 위해 문양 개수 세는중
			if (suitCount[i] >= 5) { // 플러쉬로 판정이 나면(같은 문양이 5개 이상이면)
				for (int j = SumCom.size() / 2 - 1; j >= 0; j--) { // 스티플, 로티플 판정 시작
					if (!SumCom.get(j * 2).equals(suit2[maxSuit])) { // 문양 다른 카드들 삭제
						SumCom.remove(j * 2 + 1);
						SumCom.remove(j * 2);
					}
				}
				for (int j = 0; j < SumCom.size() / 2; j++) { // 숫자 같은게 몇개 있는지 구하는 식
					for (int k = 0; k < compareCount.length; k++) { // 비교할 카드수만큼 반복
						if (SumCom.get(j * 2 + 1).equals(compareCount[k])) { // 숫자 인덱스가
							rankCount[k]++;
						}
					}
				}
				for (int j = rankCount.length - 1; j >= 0; j--) { // 스트레이트 판별 중
					if (rankCount[j] != 0) { // rank에 있는 숫자열 중 0이 아닐때
						isStrait++; // strait 개수가 올라감
						if (isStrait >= 5) { // 5개가 넘으면 스트레이트로 판정
							if (j == rankCount.length - 1) {
								System.out.println(suit2[maxSuit] + jokbo[0]);
								comJokbo = 1;
								break; // 판별완료. 포문 나가기
							} else {
								System.out.println(compareCount[j] + " " + jokbo[1]);
								comJokbo = 2 - 0.01 * j;
								break; // 판별완료. 포문 나가기
							}
						}
					} else {
						isStrait = 0; // 스트레이트 아닐때 숫자 초기화
					}
				}
				for (int j = rankCount.length - 1; j >= 0; j--) { // 높은 숫자가 이기도록 족보숫자를 더 낮게 설정
					if (rankCount[j] != 0) {
						System.out.println(compareCount[j] + " " + jokbo[4]);
						comJokbo = 5 - 0.01 * j;
						break;
					}
				}
			}
		}

	}

	public void isComStrait() {
		// 2. 스트레이트 계열
		for (int i = 0; i < SumCom.size() / 2; i++) {
			for (int j = 0; j < compareCount.length; j++) { // 숫자 같은게 몇개 있는지 구하는 식
				if (SumCom.get(i * 2 + 1).equals(compareCount[j])) {
					rankCount[j]++;
				}
			}
		}
		if (comJokbo == 12) {
			for (int i = rankCount.length - 1; i >= 0; i--) { // 스트레이트 판별 중. 6개 이상 방지를 위해 높은수 -> 낮은수로 진행
				if (rankCount[i] != 0) { // rank에 있는 숫자열 중 0이 아닐때
					isStrait++; // strait 개수가 올라감
					if (isStrait >= 5) { // 5개가 넘으면 스트레이트로 판정
						if (i == 9) {
							System.out.println(jokbo[5]); // 순차적으로 내려오다가 j = 9면 AKQJ10이므로 마운틴
							comJokbo = 6;
							break; // 판별완료. 포문 나가기
						} else if (i == 0) {
							System.out.println(jokbo[6]); // j = 0이라면 5432A이므로 백스트레이트
							comJokbo = 7;
							break; // 판별완료. 포문 나가기
						} else {
							System.out.println(compareCount[i + 4] + " " + jokbo[7]); // 나머지는 가장 높은 숫자의 스트레이트
							comJokbo = 8 - 0.01 * i; // 스트레이트 끼리는 높은 숫자의 값에 따라 승패 비교가 가능
							break;
						}
					}
				} else {
					isStrait = 0; // 스트레이트 아닐때 숫자 초기화
				}
			}
		}
	}

	public void isComHighpair() {
		// 3. 페어 계열 (포카드, 풀하우스, 트리플부터 검출)
		fh = 0;
		if (comJokbo == 12) {
			for (int i = rankCount.length - 1; i >= 0; i--) {
				if (rankCount[i] == 4) { // 같은 숫자가 4개면
					System.out.println(compareCount[i] + " " + jokbo[2]); // 포카드
					comJokbo = 3 - 0.01 * i;
				}
				if (rankCount[i] == 3) { // 같은 숫자가 3개일때
					for (int j = 0; j < rankCount.length; j++) { // 풀하우스 가능성 확인
						if (rankCount[j] == 2) { // 트리플 + 페어
							System.out.println(compareCount[i] + " " + jokbo[3]); // 풀하우스
							comJokbo = 4 - 0.01 * i; // rank에 따라 높이 달라지는 거 반영
							fh = 1;
							break;
						}
					}
				}
			}
			for (int i = rankCount.length - 1; i >= 0; i--) {
				if (fh != 1 && rankCount[i] == 3) {
					System.out.println(compareCount[i] + " " + jokbo[8]);
					comJokbo = 9 - 0.01 * i;
				}
			}
		}
	}

	public void isComLowpair() {
		// 4. 투페어 이하 검출
		tp = 0; // 투페어 중복 검출 방지
		op = 0; // 원페어 중복 검출 방지
		if (comJokbo == 12) {
			for (int i = rankCount.length - 1; i >= 1; i--) { // 높은수부터 페어 검사
				if (rankCount[i] == 2) { // 페어가 발견되었을때
					for (int j = rankCount.length - 1; j >= 0; j--) { // 높은 수부터 검사
						if (rankCount[j] == 2 && j != i) { // 또다른 페어가 발견되면 (서로 다른 페어)
							System.out.println(compareCount[i] + ", " + compareCount[j] + " " + jokbo[9]); // 투페어
							comJokbo = 10 - 0.01 * i - 0.001 * j; // rank에 따라 높이 달라지는 거 반영
							tp = 1;
							break;
						} else if (j == 1 && tp != 1 && op != 1) { // 발견되지 않고 끝까지 간다면
							System.out.println(compareCount[i] + " " + jokbo[10]); // 원페어
							comJokbo = 11 - 0.01 * i - 0.001 * j; // rank에 따라 높이 달라지는 거 반영
							op = 1;
							break;
						}
					}
					if (tp == 1) {
						break; // A,A 투페어가 나오는 경우 방지
					}
				}
			}
		}
	}

	public void isComHighcard() {
		// 5. 하이카드 검출
		if (comJokbo == 12) {
			for (int i = rankCount.length - 1; i >= 0; i--) {
				if (rankCount[i] == 1 && i == 13) {
					System.out.println("A " + jokbo[11]);
					comJokbo = 12 - 0.01 * i;
					break;
				} else if (rankCount[i] == 1) {
					System.out.println(ranks2[i] + " " + jokbo[11]);
					comJokbo = 12 - 0.01 * i;
					break;
				}
			}
		}
	}

	public void judge() {
		if (playerJokbo < comJokbo) {
			chip2 += bet2 * 4;
			System.out.println("플레이어 승리!\n보유 칩수 : " + chip2);
		} else if (playerJokbo > comJokbo) {
			chip2 -= bet2 * 4;
			System.out.println("딜러 승리!\n보유 칩수 : " + chip2);
		} else {
			System.out.println("무승부!");
		}
	}

	public void getBettingHD(CasinoDTO dto) {
		chip2 = dto.getChip();
		while (true) {
			System.out.print("베팅할 금액을 설정하세요!! (최대 1000, 총 베팅 횟수 4회) \n베팅 금액 :");
			bet2 = sc.nextInt();
			if (bet2 * 4 > chip2) {
				System.out.println("보유칩이 부족합니다.");
			} else
				break;
		}
	}

	public boolean keepBetting() {
		boolean keep = true;
		System.out.print("계속 베팅하시겠습니까?(Y/N)>> ");
		die = sc.next(); // Die 기능 구현
		if (die.equals("N")) {
			chip2 -= bet2;
			keep = false;
		}
		return keep;
	}

	public boolean keepBetting2() {
		boolean keep = true;
		System.out.print("계속 베팅하시겠습니까?(Y/N)>> ");
		die = sc.next();
		if (die.equals("N")) {
			chip2 -= bet2 * 2;
			keep = false;
		}
		return keep;
	}

	public boolean keepBetting3() {
		boolean keep = true;
		System.out.print("계속 베팅하시겠습니까?(Y/N)>> ");
		die = sc.next();
		if (die.equals("N")) {
			chip2 -= bet2 * 3;
			keep = false;
		}
		return keep;
	}

	public boolean keepBetting4() {
		boolean keep = true;
		System.out.print("계속 베팅하시겠습니까?(Y/N)>> ");
		die = sc.next();
		if (die.equals("N")) {
			chip2 -= bet2 * 3;
			keep = false;
		}
		return keep;
	}

	public boolean compareChip() {
		boolean keep = true;
		if (chip2 < bet2 * 4) {
			System.out.println("보유 칩이 부족합니다. 게임을 종료합니다.");
			keep = false;
		}
		return keep;
	}

	public boolean playMore() {
		boolean keep = true;
		System.out.print("한판 더 하시겠습니까? (Y/N) : ");
		conti = sc.next();
		if (conti.equals("N")) {
			keep = false;
		}
		return keep;

	}

	// 게임이 끝난뒤 UPDATE
	public CasinoDTO updatePlayer3(CasinoDTO dto) {
		int cnt = 0;
		dbOpen(); // 동적로딩
		String sql = "update casino_user set chip = ?, holdem = ? where id = ?";
		try {
			psmt = conn.prepareStatement(sql);
			// ? 인자 채워주기
			psmt.setInt(1, dto.getChip());
			psmt.setInt(2, dto.getHoldem());
			psmt.setString(3, dto.getId());

			// psmt에 있는 쿼리문을 실행/결과값 처리
			cnt = psmt.executeUpdate();
			// executeUpdate - 쿼리문을 실행, 영향을 받은 행이 있는지 없는지 int자료형의 결과값
			if (cnt > 0) {
				System.out.println("플레이어 업데이트 성공");
			} else {
				System.out.println("플레이어 업데이트 실패");
			}

		} catch (SQLException e) {
			System.out.println("SQL문 오류!");
		} finally {
			dbClose();
		}

		return dto;

	}

	public int getChip2() {
		return chip2;
	}

	// ======================= 슬롯 ======================

	public void betSlot(CasinoDTO dto) {
		chip3 = dto.getChip();
		System.out.println("베팅 금액을 입력해주세요 \n현재 보유 칩 : "+chip3+"\n베팅할 금액 >> ");
		bet3 = sc.nextDouble();
		minus = bet3;
	}
	
	public void slotReset() {
		 play = null;
         bet3 = minus;
	}
	
	public void printSlot() {
		for (int i = 0; i < slot.length; i++) {
            for (int j = 0; j < slot[0].length; j++) {
               slot[i][j] = prob[rd.nextInt(prob.length)];
               System.out.print(slot[i][j] + "\t");
            }
            System.out.println();
         }
	}
	
	public void compareSlot() {
		for (int i = 0; i < slot.length; i++) {
            if (slot[i][0].equals(slot[i][1]) && slot[i][1].equals(slot[i][2])) {
               for (int j = 0; j < hit.length; j++) {
                  if (slot[i][0].equals(hit[j])) {
                     System.out.println(hit[j] + " 당첨!! " + per[j] + "배 획득!");
                     bet3 *= per[j];
                  }
               }
            } else if (slot[i][0].equals("체리")) {
               bet3 *= 0.2;
               System.out.println("체리 당첨!! 1.2배 획득!");
            }
         }
	}
	
	public void chipResult() {
		 if (bet3 == minus) {
	            chip3 -= bet3;
	         } else {
	            chip3 += bet3;
	         }
	}
	
	public boolean endSlot() {
		boolean keep = true;
        if (chip3 < bet3) {
           System.out.println("칩이 부족합니다. 게임을 종료합니다");
           keep = false;
        }
        return keep;
	}
	
	public boolean exitSlot() {
		boolean keep = true;
		System.out.println("보유 칩 개수 : " + chip3);
		System.out.println("게임을 종료하시려면 exit을 입력해주세요");
        play = sc.next();
        if (play.equalsIgnoreCase("exit")) {
           keep = false;
        }
        return keep;
	}
	
	public int getChip3() {
		return chip3;
	}
	
	// 게임이 끝난뒤 UPDATE
		public CasinoDTO updatePlayer4(CasinoDTO dto) {
			int cnt = 0;
			dbOpen(); // 동적로딩
			String sql = "update casino_user set chip = ?, slot = ? where id = ?";
			try {
				psmt = conn.prepareStatement(sql);
				// ? 인자 채워주기
				psmt.setInt(1, dto.getChip());
				psmt.setInt(2, dto.getSlot());
				psmt.setString(3, dto.getId());

				// psmt에 있는 쿼리문을 실행/결과값 처리
				cnt = psmt.executeUpdate();
				// executeUpdate - 쿼리문을 실행, 영향을 받은 행이 있는지 없는지 int자료형의 결과값
				if (cnt > 0) {
					System.out.println("플레이어 업데이트 성공");
				} else {
					System.out.println("플레이어 업데이트 실패");
				}

			} catch (SQLException e) {
				System.out.println("SQL문 오류!");
			} finally {
				dbClose();
			}

			return dto;

		}
		
	

}
// Model: BlackJackGame.java
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Model {
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
	
	// DB 동적로딩, 권한연결 준비
	Connection conn = null;
	PreparedStatement psmt = null;

	Scanner sc = new Scanner(System.in);

	public Model() {
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

	public int betting() {
		System.out.println("베팅할 칩의 개수를 입력해주세요. (현재 보유 칩 : " + chip + " )");
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
		for(String s : playerCard) {
			System.out.print(s+" ");
		}
		System.out.println();
		for(String d : dealerCard) {
			System.out.print(d+" ");
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
                       } else {
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
	
	public int insert(BlackJackDTO dto) {
		int cnt = 0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String id = "hr";
			String pw = "hr";
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			
			conn = DriverManager.getConnection(url, id, pw);
			
			String sql = "INSERT INTO BLACKJACK VALUES (?, ?, ?)";
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getName());
			psmt.setInt(2,  dto.getCountChip());
			psmt.setInt(3,  dto.getCountPlay());
			
			cnt = psmt.executeUpdate(); // 최종 데이터베이스 연결값
			
		} catch (ClassNotFoundException e) {
			System.out.println("동적로딩 실패!");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("권한연결실패");
			e.printStackTrace();
		}finally {
			try {
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		return cnt;
	}
}

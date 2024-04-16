package Main;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class practice {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		Random rd = new Random();
		int chip = 3000;
		System.out.println("베팅할 칩의 개수를 입력해주세요. (현재 보유 칩 : " + chip + " )");
		int bet = sc.nextInt();
		String[] suit = { "Spades", "Diamonds", "Hearts", "Clubs" };
		String[] ranks = { "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King" };
		ArrayList<String> playerCard = new ArrayList<String>();
		ArrayList<String> dealerCard = new ArrayList<String>();
		int playerSum = 0;
		int dealerSum = 0;
		int jungbok = 0;
		String drow1;
		String drow2;
		String stop;
		String resume;
		while (true) {
			playerCard.clear();
			dealerCard.clear();
			playerSum = 0;
			dealerSum = 0;
			stop = null;
			resume = null;
			for (int j = 0; j < 2; j++) {
				while (true) {
					jungbok = 0;
					drow1 = suit[rd.nextInt(4)];
					drow2 = ranks[rd.nextInt(13)];
					for (int i = 0; i < playerCard.size() / 2; i++) {
						if (playerCard.get(i * 2).equals(drow1) && playerCard.get(i * 2 + 1).equals(drow2)) {
							jungbok = 1;
						}
					}
					if (jungbok == 0) {
						playerCard.add(drow1);
						playerCard.add(drow2);
						break;
					}
				}
				while (true) {
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
						break;
					}
				}
			}
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < ranks.length; j++) {
					if (playerCard.get(i * 2 + 1).equals(ranks[j])) {
						playerSum += j + 1;
					}
					if (dealerCard.get(i * 2 + 1).equals(ranks[j])) {
						dealerSum += j + 1;
					}
				}
			}
			System.out.println("플레이어가 뽑은 카드 : " + playerCard.get(0) + playerCard.get(1) + ", " + playerCard.get(2)
					+ playerCard.get(3) + "\n총 합 : " + playerSum);
			System.out.println("딜러가 뽑은 카드 : " + dealerCard.get(0) + dealerCard.get(1) + ", " + dealerCard.get(2)
					+ dealerCard.get(3) + "\n총 합 : " + dealerSum);
			while (true) {
				if (playerSum <= 21) {
					System.out.print("Stop하시려면 S를 입력해주세요. >> ");
					stop = sc.next();
					if (stop.equals("S")) {
						break;
					}
				}
				if (playerSum > 21) {
					break;
				}
				while (true) {
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
								playerSum += j + 1;
								System.out.println("플레이어가 뽑은 카드 : " + playerCard.get(playerCard.size() - 2)
										+ playerCard.get(playerCard.size() - 1) + "\n총 합 : " + playerSum);
							}
						}
						break;
					}
				}
				if (dealerSum < 17) {
					while (true) {
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
							break;
						}
					}
					for (int j = 0; j < ranks.length; j++) {
						if (dealerCard.get(dealerCard.size() - 1).equals(ranks[j])) {
							dealerSum += j + 1;
							System.out.println("딜러가 뽑은 카드 : " + dealerCard.get(dealerCard.size() - 2)
									+ dealerCard.get(dealerCard.size() - 1) + "\n총 합 : " + dealerSum);
						}
					}
				}
			}
			while (true) {
				if (dealerSum < 17) {
					while (true) {
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
							for (int j = 0; j < ranks.length; j++) {
								if (dealerCard.get(dealerCard.size() - 1).equals(ranks[j])) {
									dealerSum += j + 1;
								}
							}
							System.out.println("딜러가 뽑은 카드 : " + dealerCard.get(dealerCard.size() - 2)
									+ dealerCard.get(dealerCard.size() - 1) + "\n총 합 : " + dealerSum);
							break;
						}
					}
				} else {
					break;
				}
			}
			System.out.println("플레이어의 합 : " + playerSum + " 딜러의 합 : " + dealerSum);
			if ((playerSum >= 22 && dealerSum >= 22) || (playerSum == dealerSum && playerSum <= 21)) {
				System.out.println("무승부!! 베팅한 금액을 되돌려받습니다!\n현재 보유 금액 : "+chip+"\n계속하시겠습니까?(Y/N)");
				resume = sc.next();
				if (resume.equals("N")) {
					break;
				}
			} else if ((playerSum >= 22 && dealerSum < 22) || ((dealerSum < 22) && (playerSum < dealerSum))) {
				chip -= bet;
				System.out.println("플레이어 패배!! 베팅한 금액을 잃습니다!\n현재 보유 금액 : "+chip+"\n계속하시겠습니까?(Y/N)");
				resume = sc.next();
				if (resume.equals("N")) {
					break;
				}
			} else {
				chip += bet;
				System.out.println("플레이어 승리!! 베팅한 금액만큼 받습니다!\n현재 보유 금액 : "+chip+"\n계속하시겠습니까?(Y/N)");
				resume = sc.next();
				if (resume.equals("N")) {
					System.out.println("게임을 종료합니다.");
					break;
				}
			}
			if(chip - bet <0) {
				System.out.println("보유한 칩이 부족합니다. 게임을 종료합니다.");
				break;
			}
		}

	}
}
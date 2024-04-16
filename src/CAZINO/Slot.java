package CAZINO;

import java.util.Random;
import java.util.Scanner;

public class Slot {
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		Random rd = new Random();

		int chip = 3000;
		System.out.println("슬롯게임에 오신걸 환영합니다!\n베팅 금액을 입력해주세요 (최대 2000)\n현재 보유 칩 : "+chip+"\n베팅할 금액 >> ");
		Double bet = sc.nextDouble();
		Double minus = bet;
		String play = null;
		String prob[] = { "777", "77", "77", "7", "7", "7", "BAR", "BAR", "수박", "수박", "수박", "수박", "수박", "종", "종", "종",
				"종", "종", "종", "종", "사과", "사과", "사과", "사과", "사과", "사과", "사과", "사과", "체리", "체리", "체리"};
		String slot[][] = new String[3][3];
		String hit[] = { "777", "77", "7", "BAR", "수박", "종", "사과", "체리" };
		int per[] = { 50, 40, 30, 15, 10, 8, 6, 4, 8 };
		while (true) {
			play = null;
			bet = minus;
			for (int i = 0; i < slot.length; i++) {
				for (int j = 0; j < slot[0].length; j++) {
					slot[i][j] = prob[rd.nextInt(prob.length)];
					System.out.print(slot[i][j] + "\t");
				}
				System.out.println();
			}
			for (int i = 0; i < slot.length; i++) {
				if (slot[i][0].equals(slot[i][1]) && slot[i][1].equals(slot[i][2])) {
					for (int j = 0; j < hit.length; j++) {
						if (slot[i][0].equals(hit[j])) {
							System.out.println(hit[j] + " 당첨!! " + per[j] + "배 획득!");
							bet *= per[j];
						}
					}
				} else if (slot[i][0].equals("체리")) {
					bet *= 0.2;
					System.out.println("체리 당첨!! 1.2배 획득!");
				}
			}
			if (bet == minus) {
				chip -= bet;
			} else {
				chip += bet;
			}
			System.out.println("보유 칩 개수 : " + chip);
			if (chip < bet) {
				System.out.println("칩이 부족합니다. 게임을 종료합니다");
				break;
			}
			System.out.println("게임을 종료하시려면 exit을 입력해주세요");
			play = sc.next();
			if (play.equalsIgnoreCase("exit")) {
				break;
			}
		}
	}
}

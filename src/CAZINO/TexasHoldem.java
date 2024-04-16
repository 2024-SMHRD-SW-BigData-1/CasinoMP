package CAZINO;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TexasHoldem {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		Random rd = new Random();

		int chip = 3000;
		String[] suit = { "Spades", "Diamonds", "Hearts", "Clubs" };
		String[] ranks = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
		System.out.println("==== 텍사스 홀덤에 오신걸 환영합니다!! ====");
		System.out.print("베팅할 금액을 설정하세요!! (최대 1000, 최대 3회 베팅)\n베팅 금액 :");
		int bet = sc.nextInt();
		String conti = null;
		ArrayList<String> player = new ArrayList<String>();
		ArrayList<String> com = new ArrayList<String>();
		ArrayList<String> comCard = new ArrayList<String>();
		int jungbok = 0;
		String die = null;
		while (true) {
			System.out.println("게임을 시작합니다."); // 게임 실행
			player.clear();
			com.clear();
			comCard.clear();
			while (true) { // 게임 실행 전체 반복문
				for (int j = 0; j < 2; j++) { // 플레이어 카드 2장 뽑기 위한 for문
					while (true) { // 플레이어 카드 뽑기
						jungbok = 0;
						String drow1 = suit[rd.nextInt(4)]; // 임의의 색상 뽑기
						String drow2 = ranks[rd.nextInt(13)]; // 임의의 숫자 뽑기
						for (int i = 0; i < player.size() / 2; i++) { // 중복 여부 확인
							if (player.get(i * 2).equals(drow1) && player.get(i * 2 + 1).equals(drow2)) {
								jungbok = 1; // 중복일때 1
							}
						}
						if (jungbok == 0) { // 플레이어 카드 적용. 0 == 중복이 없을 때.
							player.add(drow1);
							player.add(drow2);
							break;
						}
					}
				}
				for (int j = 0; j < 2; j++) { // 컴퓨터 카드 뽑기
					while (true) {
						jungbok = 0;
						String drow1 = suit[rd.nextInt(4)]; // 임의의 색상 뽑기
						String drow2 = ranks[rd.nextInt(13)]; // 임의의 숫자 뽑기
						for (int i = 0; i < player.size() / 2; i++) { // 플레이어 카드와 중복 여부 확인
							if (player.get(i * 2).equals(drow1) && player.get(i * 2 + 1).equals(drow2)) {
								jungbok = 1;
							}
						}
						for (int i = 0; i < com.size() / 2; i++) { // 컴퓨터 카드와 중복 여부 확인
							if (com.get(i * 2).equals(drow1) && com.get(i * 2 + 1).equals(drow2)) {
								jungbok = 1;
							}
						}
						if (jungbok == 0) { // 컴퓨터 카드로 적용
							com.add(drow1);
							com.add(drow2);
							break;
						}
					}
				}
				System.out.println(
						"플레이어 카드 : " + player.get(0) + " " + player.get(1) + "," + player.get(2) + " " + player.get(3));
				System.out.print("계속배팅하시겠습니까?(Y/N)>> ");
				die = sc.next(); // Die 기능 구현
				if (die.equals("N")) {
					break;
				}
				for (int j = 0; j < 3; j++) { // 공용카드 3장을 뽑는 과정
					while (true) {
						jungbok = 0;
						String drow1 = suit[rd.nextInt(4)];
						String drow2 = ranks[rd.nextInt(13)];
						for (int i = 0; i < player.size() / 2; i++) { // com,player 카드와 중복여부 비교
							if (com.get(i * 2).equals(drow1) && com.get(i * 2 + 1).equals(drow2)) {
								jungbok = 1;
							}
							if (player.get(i * 2).equals(drow1) && player.get(i * 2 + 1).equals(drow2)) {
								jungbok = 1;
							}
						}
						for (int i = 0; i < comCard.size() / 2; i++) { // 공용 카드와 중복여부 비교
							if (comCard.get(i * 2).equals(drow1) && comCard.get(i * 2 + 1).equals(drow2)) {
								jungbok = 1;
							}
						}
						if (jungbok == 0) {
							comCard.add(drow1);
							comCard.add(drow2);
							break;
						}
					}
				}
				System.out.print("공통 카드 : ");
				for (int i = 0; i < comCard.size() / 2; i++) {
					System.out.print(comCard.get(i * 2) + " " + comCard.get(i * 2 + 1) + ", ");
				}
				System.out.print("계속배팅하시겠습니까?(Y/N)>> ");
				die = sc.next();
				if (die.equals("N")) {
					break;
				}
				while (true) {
					jungbok = 0;
					String drow1 = suit[rd.nextInt(4)];
					String drow2 = ranks[rd.nextInt(13)];
					for (int i = 0; i < player.size() / 2; i++) { // com,player 카드와 중복여부 비교
						if (com.get(i * 2).equals(drow1) && com.get(i * 2 + 1).equals(drow2)) {
							jungbok = 1;
						}
						if (player.get(i * 2).equals(drow1) && player.get(i * 2 + 1).equals(drow2)) {
							jungbok = 1;
						}
					}
					for (int i = 0; i < comCard.size() / 2; i++) { // 공용 카드와 중복여부 비교
						if (comCard.get(i * 2).equals(drow1) && comCard.get(i * 2 + 1).equals(drow2)) {
							jungbok = 1;
						}
					}
					if (jungbok == 0) {
						comCard.add(drow1);
						comCard.add(drow2);
						break;
					}

				}
				System.out.print("공통 카드 : ");
				for (int i = 0; i < comCard.size() / 2; i++) {
					System.out.print(comCard.get(i * 2) + " " + comCard.get(i * 2 + 1) + ", ");
				}
				System.out.print("계속배팅하시겠습니까?(Y/N)>> ");
				die = sc.next();
				if (die.equals("N")) {
					break;
				}
				while (true) {
					jungbok = 0;
					String drow1 = suit[rd.nextInt(4)];
					String drow2 = ranks[rd.nextInt(13)];
					for (int i = 0; i < player.size() / 2; i++) { // com,player 카드와 중복여부 비교
						if (com.get(i * 2).equals(drow1) && com.get(i * 2 + 1).equals(drow2)) {
							jungbok = 1;
						}
						if (player.get(i * 2).equals(drow1) && player.get(i * 2 + 1).equals(drow2)) {
							jungbok = 1;
						}
					}
					for (int i = 0; i < comCard.size() / 2; i++) { // 공용 카드와 중복여부 비교
						if (comCard.get(i * 2).equals(drow1) && comCard.get(i * 2 + 1).equals(drow2)) {
							jungbok = 1;
						}
					}
					if (jungbok == 0) {
						comCard.add(drow1);
						comCard.add(drow2);
						break;
					}

				}
				System.out.print("공통 카드 : ");
				for (int i = 0; i < comCard.size() / 2; i++) {
					System.out.print(comCard.get(i * 2) + " " + comCard.get(i * 2 + 1) + ", ");
				}
				System.out.println("게임을 중단하려면 D를 입력해주세요.");
				die = sc.next();
				if (die.equals("D")) {
					break;
				}

				// 승리 판별
				ArrayList<String> SumPlayer = new ArrayList<String>(player);
				SumPlayer.addAll(comCard);
				ArrayList<String> SumCom = new ArrayList<String>(com);
				SumCom.addAll(comCard);
				int suitCount[] = { 0, 0, 0, 0 }; // 카드 문양 별 (스,다,하,클) 개수 카운트
				int maxSuit = 0; // 개수 중 가장 많은 문양을 알아내기 위한 변수
				int rankCount[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; // 숫자 개수. 14번째는 마운틴 판별을 위해 A를 위한 행 추가
				int isStrait = 0; // 연속된 숫자의 개수를 구하는 함수
				String compareCount[] = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" }; // 숫자
																													// 개수
																													// 파악
																													// 중복을
																													// 위해
																													// 생성
				String jokbo[] = { "§★로얄 스트레이트 플러쉬★§", "§☆스트레이트 플러쉬☆§", "§포카드§", "※풀하우스※", "○플러쉬○", "▲마운틴 스트레이트▲",
						"＆백스트레이트＆", "↑스트레이트↑", "＊트리플＊", "투페어", "원페어", "하이카드..." };
				double playerJokbo = 12; // 플레이어의 족보 저장. 기본은 하이카드(12)
				double comJokbo = 12; // 컴퓨터도 마찬가지
				// 카드 문양기준으로 먼저 판별
				for (int i = 0; i < SumPlayer.size() / 2; i++) {
					for (int j = 0; j < suitCount.length; j++) { // 문양 같은게 몇개 있는지 구하는 식
						if (SumPlayer.get(i * 2).equals(suit[j])) {
							suitCount[j]++;
						}
					}
				}

				// 족보 판정
				// 1. 로티플, 스티플, 플러쉬 판정 (플레이어부터 진행)
				System.out.print("플레이어 : ");
				for (int i = 1; i < suitCount.length; i++) { // 가장 많은수의 문양을 구하는 변수 maxRank)
					if (suitCount[i] > suitCount[i - 1]) {
						maxSuit = i; // 0은 스페이드, 1은 다, 2는 하, 3은 클
					}
				}
				for (int i = 0; i < suitCount.length; i++) { // 판정하기 위해 문양 개수 세는중
					if (suitCount[i] >= 5) { // 플러쉬로 판정이 나면(같은 문양이 5개 이상이면)
						for (int j = SumPlayer.size() / 2 - 1; j >= 0; j--) { // 스티플, 로티플 판정 시작
							if (!SumPlayer.get(j * 2).equals(suit[maxSuit])) { // 문양 다른 카드들 삭제
								SumPlayer.remove(j*2 + 1);
								SumPlayer.remove(j*2);
							}
						}
						for (int j = 0; j < SumPlayer.size() / 2; j++) { // 숫자 같은게 몇개 있는지 구하는 식
							for (int k = 0; k < compareCount.length; k++) { // 비교할 카드수만큼 반복
								if (SumPlayer.get(j * 2 + 1).equals(compareCount[k])) { // 숫자 인덱스가
									rankCount[k]++;
								}
							}
						}
						for (int j = rankCount.length-1;j >=0; j--) { // 스트레이트 판별 중
							if (rankCount[j] != 0) { // rank에 있는 숫자열 중 0이 아닐때
								isStrait++; // strait 개수가 올라감
								if (isStrait >= 5) { // 5개가 넘으면 스트레이트로 판정
									if (j == rankCount.length - 1) {
										System.out.println(suit[maxSuit] + jokbo[0]);
										playerJokbo = 1;
										break; // 판별완료. 포문 나가기
									} else {
										System.out.println(compareCount[j]+" " + jokbo[1]);
										playerJokbo = 2 - 0.01*j;
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
									System.out.println(ranks[i + 4] + " " + jokbo[7]); // 나머지는 가장 높은 숫자의 스트레이트
									playerJokbo = 8 - 0.01 * i; // 스트레이트 끼리는 높은 숫자의 값에 따라 승패 비교가 가능
									break;
								}
							}
						} else {
							isStrait = 0; // 스트레이트 아닐때 숫자 초기화
						}
					}
				}
				// 3. 페어 계열 (포카드, 풀하우스, 트리플부터 검출)
				int fh = 0;
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
				// 4. 투페어 이하 검출
				int tp = 0; // 투페어 중복 검출 방지
				int op = 0; // 원페어 중복 검출 방지
				if (playerJokbo == 12) {
					for (int i = rankCount.length - 1; i >= 0; i--) { // 높은수부터 페어 검사
						if (rankCount[i] == 2) { // 페어가 발견되었을때
							for (int j = rankCount.length - 1; j >= 0; j--) { // 높은 수부터 검사
								if (rankCount[j] == 2 && j != i) { // 또다른 페어가 발견되면 (서로 다른 페어)
									System.out.println(compareCount[i] + ", " + compareCount[j] + " " + jokbo[9]); // 투페어
									playerJokbo = 10 - 0.01 * i - 0.001 * j; // rank에 따라 높이 달라지는 거 반영
									tp = 1;
									break;
								} else if (j == 0 && tp != 1 && op != 1) { // 발견되지 않고 끝까지 간다면
									System.out.println(compareCount[i] + " " + jokbo[10]); // 원페어
									playerJokbo = 11 - 0.01 * i - 0.001 * j; // rank에 따라 높이 달라지는 거 반영
									op = 1;
									break;
								}
							}
							if(tp == 1) {
								break;	//A,A 투페어가 나오는 경우 방지
							}
						}
					}
				}
				// 5. 하이카드 검출
				if (playerJokbo == 12) {
					for (int i = rankCount.length - 1; i >= 0; i--) {
						if (rankCount[i] == 1 && i == 13) {
							System.out.println("A " + jokbo[11]);
							playerJokbo = 12 - 0.01 * i;
							break;
						} else if (rankCount[i] == 1) {
							System.out.println(ranks[i] + " " + jokbo[11]);
							playerJokbo = 12 - 0.01 * i;
							break;
						}
					}
				}
				// 컴퓨터 카드
				// 카드 문양기준으로 먼저 판별
				maxSuit = 0;
				isStrait = 0;
				for (int i = 0; i < rankCount.length; i++) {
					rankCount[i] = 0;
				}
				for (int i = 0; i < suitCount.length; i++) {
					suitCount[i] = 0;
				} // 기본 값들 초기화
				for (int i = 0; i < SumCom.size() / 2; i++) {
					for (int j = 0; j < suitCount.length; j++) { // 문양 같은게 몇개 있는지 구하는 식
						if (SumCom.get(i * 2).equals(suit[j])) {
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
							if (!SumCom.get(j * 2).equals(suit[maxSuit])) { // 문양 다른 카드들 삭제
								SumCom.remove(j*2 + 1);
								SumCom.remove(j*2);
							}
						}
						for (int j = 0; j < SumCom.size() / 2; j++) { // 숫자 같은게 몇개 있는지 구하는 식
							for (int k = 0; k < compareCount.length; k++) { // 비교할 카드수만큼 반복
								if (SumCom.get(j * 2 + 1).equals(compareCount[k])) { // 숫자 인덱스가
									rankCount[k]++;
								}
							}
						}
						for (int j = rankCount.length-1; j >= 0; j--) { // 스트레이트 판별 중
							if (rankCount[j] != 0) { // rank에 있는 숫자열 중 0이 아닐때
								isStrait++; // strait 개수가 올라감
								if (isStrait >= 5) { // 5개가 넘으면 스트레이트로 판정
									if (j == rankCount.length - 1) {
										System.out.println(suit[maxSuit] + jokbo[0]);
										comJokbo = 1;
										break; // 판별완료. 포문 나가기
									} else {
										System.out.println(compareCount[j]+" " + jokbo[1]);
										comJokbo = 2 - 0.01*j;
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
				// 4. 투페어 이하 검출
				tp = 0;
				op = 0;
				if (comJokbo == 12) {
					for (int i = rankCount.length - 1; i >= 0; i--) { // 높은수부터 페어 검사
						if (rankCount[i] == 2) { // 페어가 발견되었을때
							for (int j = rankCount.length - 1; j >= 0; j--) { // 높은 수부터 검사
								if (rankCount[j] == 2 && j != i && i != 0 && tp != 1) { // 또다른 페어가 발견되면 (서로 다른 페어)
									System.out.println(compareCount[i] + ", " + compareCount[j] + " " + jokbo[9]); // 투페어
									comJokbo = 10 - 0.01 * i - 0.001 * j; // rank에 따라 높이 달라지는 거 반영
									tp = 1;
									break;
								} else if (j == 0 && tp != 1 && op == 1) { // 발견되지 않고 끝까지 간다면
									System.out.println(compareCount[i] + " " + jokbo[10]); // 원페어
									comJokbo = 11 - 0.01 * i - 0.001 * j; // rank에 따라 높이 달라지는 거 반영
									op = 1;
									break;
								}
							}
						}
					}
				}
				// 5. 하이카드 검출
				if (comJokbo == 12) {
					for (int i = rankCount.length - 1; i >= 0; i--) {
						if (rankCount[i] == 1 && i == 13) {
							System.out.println("A " + jokbo[11]);
							comJokbo = 11 - 0.01 * i;
							break;
						} else if (rankCount[i] == 1) {
							System.out.println(ranks[i] + " " + jokbo[11]);
							comJokbo = 11 - 0.01 * i;
							break;
						}
					}
				}
				if (playerJokbo < comJokbo) {
					chip += bet*3;
					System.out.println("플레이어 승리!\n보유 칩수 : "+chip);
				} else if (playerJokbo > comJokbo) {
					chip -= bet*3;
					System.out.println("딜러 승리!\n보유 칩수 : "+chip);
				} else {
					System.out.println("무승부!");
				}
				break;
			}
			if(chip < bet*3) {
				System.out.println("보유 칩이 부족합니다. 게임을 종료합니다.");
				break;
			}
			System.out.println("계속 하시겠습니까? (Y/N)");
			conti = sc.next();
			if (conti.equals("N")) {
				break;
			}
		}
	}
}

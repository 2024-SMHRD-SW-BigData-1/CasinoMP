package View;

import java.util.Scanner;

import Controller.Controller;
import Model.CasinoDTO;

public class CasinoMain {

	public static void main(String[] args) {

		Controller con = new Controller();
		// 음악구현
		boolean playmusic = false;
		
System.out.println(" .--..--..--..--..--..--..--..--..--..--..--..--..--..--..--..--..--. \r\n"
		+ "/ .. \\.. \\.. \\.. \\.. \\.. \\.. \\.. \\.. \\.. \\.. \\.. \\.. \\.. \\.. \\.. \\.. \\\r\n"
		+ "\\ \\/\\ `'\\ `'\\ `'\\ `'\\ `'\\ `'\\ `'\\ `'\\ `'\\ `'\\ `'\\ `'\\ `'\\ `'\\ `'\\ \\/ /\r\n"
		+ " \\/ /`--'`--'`--'`--'`--'`--'`--'`--'`--'`--'`--'`--'`--'`--'`--'\\/ / \r\n"
		+ " / /\\                                                            / /\\ \r\n"
		+ "/ /\\ \\  __        _______ _     ____ ___  __  __   _____ ___    / /\\ \\\r\n"
		+ "\\ \\/ /  \\ \\      / / ____| |   / ___/ _ \\|  \\/  | |_   _/ _ \\   \\ \\/ /\r\n"
		+ " \\/ /    \\ \\ /\\ / /|  _| | |  | |  | | | | |\\/| |   | || | | |   \\/ / \r\n"
		+ " / /\\     \\ V  V / | |___| |__| |__| |_| | |  | |   | || |_| |   / /\\ \r\n"
		+ "/ /\\ \\     \\_/\\_/  |_____|_____\\____\\___/|_|  |_|   |_| \\___/   / /\\ \\\r\n"
		+ "\\ \\/ /                                                          \\ \\/ /\r\n"
		+ " \\/ /              ____                  _       _               \\/ / \r\n"
		+ " / /\\             / ___| _   _ _ __ ___ (_)_ __ ( )___           / /\\ \r\n"
		+ "/ /\\ \\            \\___ \\| | | | '_ ` _ \\| | '_ \\|// __|         / /\\ \\\r\n"
		+ "\\ \\/ /             ___) | |_| | | | | | | | | | | \\__ \\         \\ \\/ /\r\n"
		+ " \\/ /             |____/ \\__,_|_| |_| |_|_|_| |_| |___/          \\/ / \r\n"
		+ " / /\\                                                            / /\\ \r\n"
		+ "/ /\\ \\              ____    _    ____ ___ _   _  ___            / /\\ \\\r\n"
		+ "\\ \\/ /             / ___|  / \\  / ___|_ _| \\ | |/ _ \\           \\ \\/ /\r\n"
		+ " \\/ /             | |     / _ \\ \\___ \\| ||  \\| | | | |           \\/ / \r\n"
		+ " / /\\             | |___ / ___ \\ ___) | || |\\  | |_| |           / /\\ \r\n"
		+ "/ /\\ \\             \\____/_/   \\_\\____/___|_| \\_|\\___/           / /\\ \\\r\n"
		+ "\\ \\/ /                                                          \\ \\/ /\r\n"
		+ " \\/ /                                                            \\/ / \r\n"
		+ " / /\\.--..--..--..--..--..--..--..--..--..--..--..--..--..--..--./ /\\ \r\n"
		+ "/ /\\ \\.. \\.. \\.. \\.. \\.. \\.. \\.. \\.. \\.. \\.. \\.. \\.. \\.. \\.. \\.. \\/\\ \\\r\n"
		+ "\\ `'\\ `'\\ `'\\ `'\\ `'\\ `'\\ `'\\ `'\\ `'\\ `'\\ `'\\ `'\\ `'\\ `'\\ `'\\ `'\\ `' /\r\n"
		+ " `--'`--'`--'`--'`--'`--'`--'`--'`--'`--'`--'`--'`--'`--'`--'`--'`--' ");
		// 로그인창 구현
		Scanner sc = new Scanner(System.in);
		CasinoDTO dto = new CasinoDTO();
		System.out.println("======================================================");
		while (true) {
			playmusic = con.musicPlayLobby(playmusic);
			// 로그인 [1]번 / 게임종료 [4]를 누를때까지는 계속 로그인창을 반복실행
			System.out.println("CASINO 게임에 오신걸 환영합니다. 실행하실 메뉴를 선택해주세요");
			System.out.print("[1] 로그인 [2] 회원가입 [3] 계정찾기 [4] 게임종료   >>    ");
			int choice = sc.nextInt();
			if (choice == 1) {
				System.out.println();
				System.out.println("================ 로그인 ================");
				System.out.print("아이디를 입력해주세요 : ");
				String player_id = sc.next();
				System.out.print("비밀번호를 입력해주세요 : ");
				String player_pw = sc.next();
				// 아이디, 비밀번호를 입력받고
				dto = new CasinoDTO(player_id, player_pw);

				// 결과값을 return해서 일치할시 게임시작 -- break;
				boolean isLogin = con.playerLogin(dto);
				dto = con.playerLogin2(dto);
				// 결과값이 일치하면 while문 종료
				// 결과값이 일치하지 않을시 다시 로그인창 실행
				if (isLogin == true)
					break;
			} else if (choice == 2) {
				// insert 구현하기
				System.out.println();
				System.out.println("================ 회원가입 ================");
				System.out.print("사용하실 ID를 입력해주세요 : ");
				String player_id = sc.next();
				System.out.print("사용하실 PW를 입력해주세요 : ");
				String player_pw = sc.next();
				System.out.print("사용하실 닉네임을 입력해주세요 : ");
				String nickname = sc.next();
				System.out.print("휴대폰번호를 입력해주세요 : ");
				String phoneNumber = sc.next();

				// 닉네임 아이디는 중복검사를 실행함
				dto = new CasinoDTO(player_id, player_pw, nickname, phoneNumber);
				con.insert(dto);
				break;
			} else if (choice == 3) {
				System.out.println();
				System.out.println("================ 계정 찾기 ================");
				System.out.print("회원정보에 있던 휴대폰번호를 입력해주세요 : ");
				String ph = sc.next();

				dto = new CasinoDTO(ph);
				con.findPlayer(dto);

			} else if (choice == 4) {
				System.out.println("게임을 종료합니다.");
				System.exit(0);
			} else
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
		}
		System.out.println("======================================================");

		while (true) {
			System.out.println();
			System.out.println("[1] BLACKJACK [2] SLOT [3] HOLDEM [4] 랭킹보기 [5] 게임종료");
			System.out.print("실행하실 메뉴를 선택해주세요 : ");
			System.out.println();
			int choice2 = sc.nextInt();
			if(choice2 == 1) {
				// 로비음악멈추기 
				con.musicStop(playmusic);
				con.playBlackJack(dto);
				break;
			}else if(choice2 == 2) {
				// 로비음악멈추기 
				con.musicStop(playmusic);
				con.playSlot(dto);
				break;
			}else if(choice2 == 3) {
				// 로비음악멈추기 
				con.musicStop(playmusic);
				con.playHoldem(dto);
				break;
			}else if(choice2 == 4) {
				System.out.println("================= 랭킹  =================");
				System.out.print("[1] CHIP 랭킹 [2] 게임별 랭킹  >> ");
				int rk = sc.nextInt();
				if(rk == 1) {
					// blackjack랭킹 출력
					System.out.println();
					con.rankChip();
				}else if(rk == 2) {
					System.out.println();
					con.rankGameBJ();
					con.rankGameSlot();
					con.rankGameHD();
				}else
					System.out.println("잘못된 입력입니다.");
			}else if(choice2 == 5) {
				System.out.println("게임을 종료합니다.");
				System.exit(0);
			}else
				System.out.println("잘못된 입력입니다.");
		}

	}
}













package Controller;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import Model.CasinoDAO;
import Model.CasinoDTO;

public class Controller {

	CasinoDAO dao = new CasinoDAO();
	Random rd = new Random();

	// 로그인
	public boolean playerLogin(CasinoDTO dto) {
		dto = dao.playerLogin(dto);

		boolean isLogin = false;
		if (dto.getPhoneNumber() == null) {
			System.out.println("플레이어 정보가 일치하지 않습니다.");
		} else {
			System.out.println("플레이어 " + dto.getId() + " 로그인성공!");
			isLogin = true;
		}

		return isLogin;
	}
	
	public CasinoDTO playerLogin2(CasinoDTO dto) {
		dto = dao.playerLogin(dto);

		return dto;
	}

	// 회원가입
	public void insert(CasinoDTO dto) {
		int cnt = dao.insert(dto);

		if (cnt > 0) {
			System.out.println("플레이어 등록 성공");
		} else {
			System.out.println("플레이어 등록 실패");
		}
	}

	// 계정찾기
	public void findPlayer(CasinoDTO dto) {
		dto = dao.findPlayer(dto);

		if (dto.getId() == null)
			System.out.println("찾으시는 아이디와 비밀번호가 존재하지 않습니다.");
		else
			System.out.println("아이디 : " + dto.getId() + "    " + "비밀번호 : " + dto.getPw());
	}

	// 칩별 랭킹출력
	public void rankChip() {
		ArrayList<CasinoDTO> list = dao.rankChip();

		System.out.println("[CHIP 랭킹]");
		System.out.println();
		System.out.println("닉네임 \t\tID \t\t칩개수 \t\t블랙잭 \t\t슬롯  \t\t홀덤");
		System.out.println("=================================================================================");
		for (int i = 0; i < list.size(); i++) {
			System.out.print(list.get(i).getNick() + "\t\t");
			System.out.print(list.get(i).getId() + "\t\t");
			System.out.print(list.get(i).getChip() + "\t\t");
			System.out.print(list.get(i).getBlackjack() + "\t\t");
			System.out.print(list.get(i).getSlot() + "\t\t");
			System.out.println(list.get(i).getHoldem() + "\t\t");
		}
		System.out.println("=================================================================================");

	}

	// 블랙잭 랭킹 출력
	public void rankGameBJ() {
		ArrayList<CasinoDTO> list = dao.rankGameBJ();

		System.out.println("[BLACKJACK 판수 랭킹]");
		System.out.println();
		System.out.println("닉네임 \t\tID \t\t칩개수 \t\t블랙잭");
		System.out.println("==================================================================");
		for (int i = 0; i < list.size(); i++) {
			System.out.print(list.get(i).getNick() + "\t\t");
			System.out.print(list.get(i).getId() + "\t\t");
			System.out.print(list.get(i).getChip() + "\t\t");
			System.out.println(list.get(i).getBlackjack() + "\t\t");
		}
		System.out.println("==================================================================");
	}

	// 슬롯 랭킹 출력
	public void rankGameSlot() {
		ArrayList<CasinoDTO> list = dao.rankGameSlot();

		System.out.println("[SLOT 판수 랭킹]");
		System.out.println();
		System.out.println("닉네임 \t\tID \t\t칩개수 \t\t슬롯");
		System.out.println("==================================================================");
		for (int i = 0; i < list.size(); i++) {
			System.out.print(list.get(i).getNick() + "\t\t");
			System.out.print(list.get(i).getId() + "\t\t");
			System.out.print(list.get(i).getChip() + "\t\t");
			System.out.println(list.get(i).getSlot() + "\t\t");
		}
		System.out.println("==================================================================");
	}

	// 홀덤 랭크게임 출력
	public void rankGameHD() {
		ArrayList<CasinoDTO> list = dao.rankGameHD();

		System.out.println("[HOLDEM 판수 랭킹]");
		System.out.println();
		System.out.println("닉네임 \t\tID \t\t칩개수 \t\t홀덤");
		System.out.println("==================================================================");
		for (int i = 0; i < list.size(); i++) {
			System.out.print(list.get(i).getNick() + "\t\t");
			System.out.print(list.get(i).getId() + "\t\t");
			System.out.print(list.get(i).getChip() + "\t\t");
			System.out.println(list.get(i).getHoldem() + "\t\t");
		}
		System.out.println("==================================================================");
	}

	public void playBlackJack(CasinoDTO dto) {
		Scanner sc = new Scanner(System.in);
		Random rd = new Random();

		String keepPlaying = null;
		String isBust = null;
		String play2 = null;
		String dealP = null;
		String name = null;
		int choice = 0;

		// DB저장 변수
		int countChip = dto.getChip(); // 칩개수
		int countPlay = dto.getBlackjack(); // 플레이횟수

		int chip = dto.getChip();

		// 베팅할 금액 입력받기
		int bet = dao.betting();

		while (true) {
			System.out.println("==============BlackJack 게임을 시작하겠습니다===============");

			dao.startGame(); // Model 변수 초기화

			// 플레이어와 딜러가 카드를 2장씩 뽑는다.
			dao.dealCards();

			// 플레이어, 딜러 카드의 합계 계산
			dao.cardsSum();

			// 플레이어, 딜러 카드 첫2장 출력
			dao.viewPlayer();

			// GamePlay진행
			while (true) {
				// 딜러합계, 플레이어합계가 21이넘으면 종료
				isBust = dao.bust();
				if (isBust.equals("버스트")) {
					break;
				}
				// 플레이어의 합계를 받고 게임지속여부선택
				int cnt = dao.gamePlay();
				if (cnt <= 21) { // 플레이어의 게임 지속 여부 선택
					System.out.print("Stop하시려면 S를 입력해주세요. >> ");
					String keep = sc.next();
					if (keep.equals("S")) {
						break;
					}
				}

				// 게임진행

				while (true) {
					play2 = dao.gamePlay2();
					if (play2.equals("stop"))
						break;
				}
				// 딜러총합이 17보다 작다면
				// while문 실행 -- dealerPlay2()
				// 지금까지의 딜러카드들 출력
				int cnt2 = dao.getDealerSum();
				if (cnt2 < 17) {
					while (true) {
						dealP = dao.dealerPlay();
						if (dealP.equals("stop"))
							break;
					}
					dao.dealerPlay2();
				}
			}

			// 플레이어가 stop한 상태 이후에 딜러가 17보다 작으면 클때까지 뽑음.
			while (true) {
				int cnt3 = dao.getDealerSum();
				if (cnt3 < 17) {
					while (true) {
						dao.afterPlayerStop();
						// 중복이 없으면
						int jb = dao.getJungbok();
						if (jb == 0) {
							dao.afterPlayerStop2();
							break;
						}
					}
				} else
					break;
			}

			countPlay++;
			// 결과 출력 후 게임종료 할건지 묻는 메소드
			String resume = dao.endGame(bet, keepPlaying);
			if (resume.equals("N")) {
				countChip = dao.getChip();
				break;
			}

		}

		String id = dto.getId();

		dto = new CasinoDTO(countChip, countPlay, id);
		dao.updatePlayer(dto);

	}

	public void playHoldem(CasinoDTO dto) {
		// DB저장 변수
		int countChip = dto.getHoldem(); // 칩개수
		System.out.println("======");
		int countPlay2 = dto.getHoldem(); // 플레이횟수

		int chip = dto.getChip();

		// 베팅할 금액 입력받기
		dao.getBettingHD();
		
		while(true) {
			System.out.println("==============Holdem 게임을 시작하겠습니다===============");
			
			dao.chogihwa();
			while(true) {
				
				dao.chogihwa();
	            for (int j = 0; j < 2; j++) { // 플레이어 카드 2장 뽑기 위한 for문
	            	dao.getPlayerCard();
	            }
	            for (int j = 0; j < 2; j++) { // 컴퓨터 카드 뽑기
	            	dao.getComCard();
	            }            
	            dao.printPlayerCard();
	            
	            boolean isKeep = dao.keepBetting();
	            if(isKeep ==false)
	            	break;
	            for (int j = 0; j < 3; j++) { // 공용카드 3장을 뽑는 과정
	                dao.getCommonCard();
	             }
	             dao.printCommonCard();
	             boolean isKeep2 = dao.keepBetting2();
	             if(isKeep2 ==false)
	             	break;
	             dao.getCommonCard();
	             dao.printCommonCard();
	             boolean isKeep3 =dao.keepBetting3();
	             if(isKeep3 ==false)
	              	break;
	             dao.getCommonCard();
	             dao.printCommonCard();
	             boolean isKeep4 = dao.keepBetting4();
	             if(isKeep4 ==false)
	               	break;
	             // 승리 판별
	             dao.SumCard();
	             dao.isPlayerFlush();
	             dao.isPlayerStrait();
	             dao.isPlayerHighPair();
	             dao.isPlayerLowPair();
	             dao.isPlayerHighcard();
	             
	             dao.comChogihwa();
	             dao.isComFlush();
	             dao.isComStrait();
	             dao.isComHighpair();
	             dao.isComLowpair();
	             dao.isComHighcard();
	             // 승리 판별
	             dao.judge();
	             break;
			}
			boolean isKeep5 = dao.compareChip();
            if(isKeep5 == false)
            	break;
            
            countPlay2++;
            boolean isKeep6 = dao.playMore();
            if(isKeep6 == false) {
            	countChip = dao.getChip2();
            	break;
            }
		}
		
		String id = dto.getId();

		dto = new CasinoDTO(countChip, id, countPlay2);
		dao.updatePlayer3(dto);
	}

}

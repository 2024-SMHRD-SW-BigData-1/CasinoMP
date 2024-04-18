
package CAZINO;

import java.util.Scanner;

import Controller.BlackJackController;
import Model.BlackJackDTO;
import Model.Model;

public class BlackJackGame {
	public static void main(String[] args) {
		// 컨트롤러 생성자 생성
		BlackJackController controller = new BlackJackController();
		Model md = new Model();
		Scanner sc = new Scanner(System.in);
		String keepPlaying = null;
		String isBust = null;
		String play2 = null;
		String dealP = null;
		
		// DB저장 변수
		int countChip = 0; // 칩개수
		int countPlay = 0; // 플레이횟수

		// 게임을시작하면
		// 매판 카드가 들어갈 배열을 clear
		// 매판 카드의 합 값 초기화
		// 매판 선택 초기화
		// 사용자 닉네임 입력
		System.out.print("사용하실 이름을 입력해주세요 : ");
		String name = sc.next();
		// 플레이어의 닉네임을 playerName ArrayList에 저장

		// 베팅할 금액 입력받기
		int bet = controller.betting();

		while (true) {
			System.out.println("==============BlackJack 게임을 시작하겠습니다===============");
			
			controller.startGame(); // Model 변수 초기화

			// 플레이어와 딜러가 카드를 2장씩 뽑는다.
			controller.dealCards();

			// 플레이어, 딜러 카드의 합계 계산
			controller.cardsSum();

			// 플레이어, 딜러 카드 첫2장 출력
			controller.viewPlayer();

			// GamePlay진행
			while (true) {
				// 딜러합계, 플레이어합계가 21이넘으면 종료
				isBust = controller.bust();
				if (isBust.equals("버스트")) {
					break;
				}
				// 플레이어의 합계를 받고 게임지속여부선택
				int cnt = controller.gamePlay();
				if (cnt <= 21) { // 플레이어의 게임 지속 여부 선택
					System.out.print("Stop하시려면 S를 입력해주세요. >> ");
					String keep = sc.next();
					if (keep.equals("S")) {
						break;
					}
				}
				
				// 게임진행
				
				while (true) {
					play2 = controller.gamePlay2();
					if (play2.equals("stop"))
						break;
				}
				// 딜러총합이 17보다 작다면
				// while문 실행 -- dealerPlay2()
				// 지금까지의 딜러카드들 출력
				int cnt2 = controller.getDealerSum();
				if (cnt2 < 17) {
					while (true) {
						dealP = controller.dealerPlay();
						if (dealP.equals("stop"))
							break;
					}
					controller.dealerPlay2();
				}
			}
			
			// 플레이어가 stop한 상태 이후에 딜러가 17보다 작으면 클때까지 뽑음.
			while(true) {
				int cnt3 = controller.getDealerSum();
				if(cnt3<17) {
					while(true) {
						controller.afterPlayerStop();		
						// 중복이 없으면
						int jb = controller.getJunbok();
						if(jb == 0) {
							controller.afterPlayerStop2();
							break;
						}
					}
				}else
					break;;
			}

			countPlay++;
			// 결과 출력 후 게임종료 할건지 묻는 메소드
			String resume = controller.endGame(bet, keepPlaying);
			if (resume.equals("N")) {
				countChip = controller.getChip();
				break;
			}
				
		}
		
		// 게임이 끝난뒤 결과를 DB에 저장
		BlackJackDTO dto = new BlackJackDTO(name, countChip, countPlay);
		
		controller.insert(dto, name);

	}
}

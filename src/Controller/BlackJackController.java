package Controller;

import Model.BlackJackDTO;
import Model.Model;

public class BlackJackController {
	Model md = new Model();

	public BlackJackController() {
	}

	public void startGame() {
		// 게임마다 변수 초기화
		md.startGame();
	}
	
	
	public int betting() {
		int bet = md.betting();
		
		return bet;
	}
	
	public void dealCards() {
		// 카드를 2장씩 나눠가지는 메소드
		md.dealCards();
	}
	
	public void cardsSum() {
		md.cardsSum();
	}
	
	public void viewPlayer() {
		md.viewPlayer();
	}
	
	public void viewAll() {
		md.viewAll();
	}
	
	public String bust() {
		String stop = md.bust();
		
		return stop;
	}
	
	public int gamePlay() {
		int cnt = md.gamePlay();
		
		return cnt;
	}
	
	public String gamePlay2() {
		String play2 = md.gamePlay2();
		
		return play2;
	}
	
	
	public int getDealerSum() {
		int cnt = md.getDealerSum();
		
		return cnt;
	}
	
	public String dealerPlay() {
		String stop = md.dealerPlay();
		
		return stop;
	}
	
	public void dealerPlay2() {
		md.dealerPlay2();
	}
	
	public void afterPlayerStop() {
		md.afterPlayerStop();
	}	
	
	public void afterPlayerStop2() {
		md.afterPlayerStop2();
	}	
	
	public int getJunbok() {
		int cnt = md.getJungbok();
		
		return cnt;
	}

	public String endGame(int bet, String resume) {
		String keepPlaying = md.endGame(bet, resume);
		
		return keepPlaying;
	}
	
	//칩개수 반환
	public int getChip() {
		int cnt = md.getChip();
		
		return cnt;
	}
	
	// 게임종료 후 플레이어 정보를 DB에 insert
	public void insert(BlackJackDTO dto, String name) {
		int cnt = md.insert(dto);
		
		if (cnt > 0) {
			System.out.println("Player " + name + " 점수 등록 성공");
		} else {
			System.out.println("Player " + name + " 점수 등록 실패");
		}
	}
	
}
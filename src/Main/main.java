package Main;

import java.util.Random;
import java.util.Scanner;

public class main {
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		Random rd = new Random();
		System.out.println("베팅할 칩과 모드를 입력하세요.");
		int chip = sc.nextInt();
		int select = sc.nextInt();
		int mode[] = {16,17,18,19,20,21};
		double bet[] = {1.05,1.15,1.3,1.5,1.75,2.05};
		String getCard = null;
		String suit[] = { "Spades", "Diamonds", "Hearts", "Clubs" };
		String[] ranks = { "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King" };
		int cardSum = 0;
		int index1 = 0;
		int index2 = 0;
		while(true) {
			index1 = rd.nextInt(4);
			index2 = rd.nextInt(12);
			cardSum += index2+1;
			System.out.println("뽑은 카드 "+suit[index1]+" "+ranks[index2]+"\n목표 점수 : "+mode[select-1]+" 현재 점수 : "+ cardSum);
			if(cardSum>=22) {
				System.out.println("버스트 되었습니다!");
				break;
			}
			System.out.println("계속 하시겠습니까?(Y/N)");
			getCard = sc.next();
			if (getCard.equals("N")) {
				break;
			}
		}
		if(cardSum>=mode[select-1] && cardSum <=21) {
			System.out.println("플레이어 승리! "+(int)(bet[select-1]*chip)+"개의 칩을 획득했습니다!");
		}else{
			System.out.println("플레이어 패배!\n"+chip+"개의 칩을 잃었습니다!");
		}
		
	}
	
}

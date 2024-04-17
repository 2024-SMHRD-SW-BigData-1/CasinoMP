package View;

import java.util.Scanner;

import Controller.Controller;
import Model.CasinoDTO;

public class CasinoMain {

	public static void main(String[] args) {
		
		Controller con = new Controller();
		
		// 로그인창 구현
		Scanner sc = new Scanner(System.in);
		System.out.println("======================================================");
		while(true) {
			// 로그인 [1]번 / 게임종료 [4]를 누를때까지는 계속 로그인창을 반복실행
			System.out.println("CASINO 게임에 오신걸 환영합니다. 실행하실 메뉴를 선택해주세요");
			System.out.print("[1] 로그인 [2] 회원가입 [3] 계정찾기 [4] 게임종료   >>    ");
			int choice = sc.nextInt();
			if(choice ==1) {
				System.out.println("================ 로그인 ================");
				System.out.print("아이디를 입력해주세요 : ");
				String player_id = sc.next();
				System.out.print("비밀번호를 입력해주세요 : ");
				String player_pw = sc.next();
				// 아이디, 비밀번호를 입력받고
				CasinoDTO dto = new CasinoDTO(player_id, player_pw);
				
				// 결과값을 return해서 일치할시 게임시작 -- break;
				boolean isLogin = con.playerLogin(dto);
				// 결과값이 일치하면 while문 종료
				// 결과값이 일치하지 않을시 다시 로그인창 실행
				if(isLogin == true)
					break;				
			}else if(choice == 2) {
				// insert 구현하기
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
				CasinoDTO dto = new CasinoDTO(player_id,player_pw,nickname,phoneNumber);
				con.insert(dto);								
				break;
			}else if(choice == 3) {
				System.out.println("================ 계정 찾기 ================");
				System.out.print("회원정보에 있던 휴대폰번호를 입력해주세요 : ");
				String ph = sc.next();
				
				CasinoDTO dto = new CasinoDTO(ph);
				con.findPlayer(dto);
				
			}else if(choice == 4) {
				System.out.println("게임을 종료합니다.");
				System.exit(0);
			}else 
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");			
		}		
		System.out.println("======================================================");
		
		
	}
}
package Controller;

import Model.CasinoDAO;
import Model.CasinoDTO;

public class Controller {

	CasinoDAO dao = new CasinoDAO();

	// 로그인
	public boolean playerLogin(CasinoDTO dto) {
		int cnt  = dao.playerLogin(dto);
		boolean isLogin = false;
		
		if(cnt==1) {
			System.out.println("로그인성공!");
			isLogin = true;
		}else
			System.out.println("플레이어 정보가 일치하지 않습니다.");
		
		return isLogin;
	}

	// 회원가입
	public void insert(CasinoDTO dto) {
		int cnt = dao.insert(dto);

		if (cnt > 0) {
			System.out.println("학생 등록 성공");
		} else {
			System.out.println("학생 등록 실패");
		}
	}
	
	// 계정찾기
	public void findPlayer(CasinoDTO dto) {
		//dao.findPlayer();
	}
}
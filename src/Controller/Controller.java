package Controller;

import Model.CasinoDAO;
import Model.CasinoDTO;

public class Controller {

	CasinoDAO dao = new CasinoDAO();

	// 로그인
	public boolean playerLogin(CasinoDTO dto) {
		dto = dao.playerLogin(dto);
		boolean isLogin = false;
		
		if(dto.getPhoneNumber()==null) {
			System.out.println("플레이어 정보가 일치하지 않습니다.");			
		}else {
			System.out.println("플레이어 "+dto.getId()+" 로그인성공!");
			isLogin = true;
		}

		return isLogin;
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
}
package Model;

public class CasinoDTO {

	// 플레이어테이블
	private String id; // 회원아이디
	private String pw; // 회원비밀번호
	private String nick; // 회원닉네임
	private String phoneNumber; // 회원전화번호
	private int chip; // 보유칩개수
	private int blackjack; // 블랙잭플레이횟수
	private int slot; // 슬롯플레이횟수
	private int holdem; // 홀덤플레이횟수

	public CasinoDTO() {
	}

	public CasinoDTO(String id, String pw, String nick, String phoneNumber, int chip, int blackjack, int slot, int holdem) {
		this.id = id;
		this.pw = pw;
		this.nick = nick;
		this.phoneNumber = phoneNumber;
		this.chip = chip;
		this.blackjack = blackjack;
		this.slot = slot;
		this.holdem = holdem;
	}
	
	public CasinoDTO(String id, String pw, String nick, String phoneNumber) {
		this.id = id;
		this.pw = pw;
		this.nick = nick;
		this.phoneNumber = phoneNumber;
	}

	public CasinoDTO(String id, String pw) {
		this.id = id;
		this.pw = pw;
	}

	public CasinoDTO(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	// getter, setter
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getChip() {
		return chip;
	}

	public void setChip(int chip) {
		this.chip = chip;
	}

	public int getBlackjack() {
		return blackjack;
	}

	public void setBlackjack(int blackjack) {
		this.blackjack = blackjack;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	public int getHoldem() {
		return holdem;
	}

	public void setHoldem(int holdem) {
		this.holdem = holdem;
	}

}
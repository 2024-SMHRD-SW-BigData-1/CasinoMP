package Model;

public class BlackJackDTO {

	private String name; // 플레이어이름
	private int countChip; // 칩개수
	private int countPlay; // 플레이한 횟수

	public BlackJackDTO(String name, int countChip, int countPlay) {
		this.name = name;
		this.countChip = countChip;
		this.countPlay = countPlay;		
	}

	// getter, setter
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCountChip() {
		return countChip;
	}

	public void setCountChip(int countChip) {
		this.countChip = countChip;
	}

	public int getCountPlay() {
		return countPlay;
	}

	public void setCountPlay(int countPlay) {
		this.countPlay = countPlay;
	}
	
	

}

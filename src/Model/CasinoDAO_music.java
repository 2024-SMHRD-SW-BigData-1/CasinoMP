package Model;

import javazoom.jl.player.MP3Player;

public class CasinoDAO_music {
   MP3Player mp3 = new MP3Player();

   public void start_music() {
      mp3.play("C:/Users/smhrd/Desktop/casino_music/The Entertainer - E's Jammy Jams.mp3");
   }
   
   public void stop_music() {
      mp3.stop();
   }
   
   public void start_slot() {
      mp3.play("C:/Users/smhrd/Desktop/casino_music/Slot Machine Sound.mp3");
   }
   
   public void start_card() {
      mp3.play("C:/Users/smhrd/Desktop/casino_music/806307_포커카드 섞는 소리.mp3");
   }
   
}
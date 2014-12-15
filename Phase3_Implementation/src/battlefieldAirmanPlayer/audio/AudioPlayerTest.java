package battlefieldAirmanPlayer.audio;

public class AudioPlayerTest {

	public static void main(String[] args) {

		String audioFilePath = "01.wav";
		//double percentage = 50;

		//AudioPlayer.playPercentage(audioFilePath, percentage);
		
		double time = 115;

		AudioPlayer.playTime(audioFilePath, time);
	}

}

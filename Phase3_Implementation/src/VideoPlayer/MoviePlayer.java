package VideoPlayer;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class MoviePlayer extends Application {

	public static void main (String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Group root = new Group();

		Media media = new Media("file:///Users/ebonilla/Downloads/Sintel.mp4");
		//Media media = new Media("file:///Users/ebonilla/Documents/Rowan Masters/spawncamping-nemesis/Phase3_Implementation/src/VideoPlayer/Sintel.mp4");
		//Media media = new Media("file:///Phase3_Implementation/src/VideoPlayer/Sintel.mp4");
		MediaPlayer player = new MediaPlayer(media);
		MediaView view = new MediaView(player);
		
		root.getChildren().add(view);
		Scene scene = new Scene(root, 1250, 545, Color.BLACK);
		stage.setScene(scene);
		stage.show();
		
		player.play();
	}

}

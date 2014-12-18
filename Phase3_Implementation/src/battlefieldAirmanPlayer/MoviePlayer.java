package battlefieldAirmanPlayer;

import java.io.File;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import javax.swing.JFrame;


//public class MoviePlayer extends Application {
public class MoviePlayer {
	private static boolean stopRequested = false;
  private final static File file = new File("BattlefieldVideo/SampleSoldierVideo.mp4");
  //private final static File file = new File("BattlefieldVideo/Medal_of_Honor_Xbox_360_Gameplay_footage.mp4");
  //private final static File file = new File("BattlefieldVideo/Sintel.mp4");
  private final static String MEDIA_URL = file.toURI().toString();
	static Media media = new Media(MEDIA_URL);
	static final MediaPlayer player = new MediaPlayer(media);
	//MediaView view = new MediaView(player);
	static public MediaPlayer globalPlayer = player;
	static int videoWidth = player.getMedia().getWidth();
    static int videoHeight = player.getMedia().getHeight();



/*
	public static void main (String[] args) {
		launch(args);
	}
*/
    private static void initAndShowGUI() {
        // This method is invoked on the EDT thread
        JFrame frame = new JFrame("Swing and JavaFX");
        final JFXPanel fxPanel = new JFXPanel();
        frame.add(fxPanel);
        frame.setSize(1295, 545);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel);
            }
       });
    }

    public static void initFX(JFXPanel fxPanel) {
        // This method is invoked on the JavaFX thread
        Scene scene = createScene();
        fxPanel.setScene(scene);
    }

    private static Scene createScene() {
        Group  root  =  new  Group();
/*
  		Scene  scene  =  new  Scene(root, Color.ALICEBLUE);
        Text  text  =  new  Text();

        text.setX(40);
        text.setY(100);
        text.setFont(new Font(25));
        text.setText("Welcome JavaFX!");

        root.getChildren().add(text);
*/
		//Media media = new Media("file:///Users/ebonilla/Downloads/SampleSoldierVideo.mp4");
		//final MediaPlayer player = new MediaPlayer(media);
		MediaView view = new MediaView(player);

		final VBox vbox = new VBox();
		final Slider slider = new Slider();
		vbox.getChildren().add(slider);

		root.getChildren().add(view);
		root.getChildren().add(vbox);

		//Scene scene = new Scene(root, videoWidth, videoHeight, Color.BLACK);
		Scene scene = new Scene(root, 650, 650, Color.BLACK);
		//stage.setScene(scene);
		//stage.show();

		//player.play();
		//playMovie(player);
	    player.setOnPlaying(new Runnable() {
	            @Override
              public void run() {
	                if (stopRequested) {
	                    player.pause();
	                    stopRequested = false;
	                    System.out.println("Stop Requested");
	                } else {
	                	System.out.println("Set play button to pause");
	                    //playButton.setText("||");
	                }
	            }
	        });


        player.setOnPaused(new Runnable() {
            @Override
            public void run() {
                System.out.println("onPaused");
                //playButton.setText(">");
            }
        });


		player.setOnReady(new Runnable() {
			@Override
			public void run() {
				System.out.println("onReady");

				int w = player.getMedia().getWidth();
                int h = player.getMedia().getHeight();

				vbox.setMinSize(w, 100);
				//vbox.alignmentProperty(CENTER);
                //vbox.setTranslateY(h - 100);

				slider.setMin(0.0);
				slider.setValue(0.0);
				slider.setMax(player.getTotalDuration().toSeconds());
				slider.setShowTickLabels(true);
				slider.setShowTickMarks(true);
				slider.setMajorTickUnit(10);
				slider.setMinorTickCount(5);
				slider.setBlockIncrement(10);
			}
		});

		player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
			@Override
			public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration current) {
				slider.setValue(current.toSeconds());
				double value = BattlefieldAirmanGui.sliderProgress.getValue();
				double currentTime = value/1000;
				slider.setValue(currentTime);
				//player.setStartTime(new Duration(value));// TODO FIXME set video to slider
				//System.out.println("Video slider value"+ player.getStatus());
			}
		});

		slider.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                player.seek(Duration.seconds(slider.getValue()));
            }
        });

        return (scene);
    }

    public static void playMovie(MediaPlayer mp) {
    	mp.play();
    }

    public static void pauseMovie(MediaPlayer mp) {
    	mp.pause();
    }

    public static MediaPlayer getMediaPlayer() {
    	return globalPlayer;
    }
}

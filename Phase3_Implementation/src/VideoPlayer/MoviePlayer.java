package VideoPlayer;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


//public class MoviePlayer extends Application {
public class MoviePlayer {
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
        frame.setSize(300, 200);
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
		Media media = new Media("file:///Users/ebonilla/Downloads/Sintel.mp4");
		MediaPlayer player = new MediaPlayer(media);
		MediaView view = new MediaView(player);

		root.getChildren().add(view);
		Scene scene = new Scene(root, 1250, 545, Color.BLACK);
		//stage.setScene(scene);
		//stage.show();

		player.play();
		
        return (scene);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initAndShowGUI();
            }
        });
    }
/*
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

		JFrame frame = new JFrame("VideoPlayer");
        //frame.getContentPane().add(view, BorderLayout.CENTER);
        frame.getContentPane().
        frame.setPreferredSize(new Dimension(640, 480));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
		
		player.play();
	}
*/
}

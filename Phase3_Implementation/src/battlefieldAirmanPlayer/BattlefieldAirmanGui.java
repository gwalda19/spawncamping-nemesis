package battlefieldAirmanPlayer;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.MediaPlayer;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ReviewerInformation.ReviewerDatabase;
import ReviewerInformation.ReviewerInformation;
import battlefieldAirmanPlayer.audio.SoundPlayer;

/**
 *  BattlefieldAirmanGui
 *
 *  This is the main class for the battlefield airman playback software.
 *  This class generates the main window and the associated panels that are
 *  housed in the JFrame.
 *
 *  @author Emmanuel Bonilla
 *  @author Sean Fast
 *  @author David Gwalthney
 *  @author Michael Norris
 *  @author David Shanline
 *
 */
public class BattlefieldAirmanGui extends JFrame implements DataPointObserver {

	private static JFrame frame;
	private static ArrayList<GPSDataPoint> masterList = new ArrayList<GPSDataPoint>();
	private ArrayList<GPSDataPoint> filteredHomeRunArrayList = new ArrayList<GPSDataPoint>();
	private JPanel contentPane;
	private final BattlefieldAirmanMapGraphicsSurface baMapGraphicsSurf = new BattlefieldAirmanMapGraphicsSurface(masterList);
	private JComboBox cmbCamera;
	private JComboBox cmbAudio;
	private JList listGPSData;


  private static PassWordDialog          pass_dialog_box;
  private static ReviewerDatabase        reviewer_database;
  private static ReviewerInformation     reviewer_logged_in;
  private static Boolean    not_first_time = new Boolean(false);
  private static SoundPlayer player;
  private File file;
  private int selectedAudioIndex;
  protected double percent;
  public static JSlider sliderProgress;
  private JSplitPane spSliderButtonsTB;

	/**
	 *  Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
      public void run() {
				try {
				  frame = new JFrame();

					masterList = ReadCSV.read();
			    reviewer_database = new ReviewerDatabase("BA_Username_List.csv");

			    /*
			    final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
			    service.scheduleWithFixedDelay(new Runnable()
			    {
			      @Override
            public void run()
			      {
			        System.out.println(new Date());
			        checkForTimeout();
			      }
			    }, 0, 10, TimeUnit.SECONDS);

			    pass_dialog_box = new PassWordDialog(frame, true, reviewer_database);
			    pass_dialog_box.setVisible(true);
			    */
			    reviewer_database.setLoggedInUser("gwalthney");  //TODO: comment out when the block above is in.
			    reviewer_logged_in = reviewer_database.getReviewerInformation();

					frame = new BattlefieldAirmanGui(masterList);
			    frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BattlefieldAirmanGui(final ArrayList<GPSDataPoint> masterList) {
		setExtendedState(frame.MAXIMIZED_BOTH);//TODO:SF TEST
		setTitle("Battlefield Airman Player");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		baMapGraphicsSurf.registerDataPointObserver(this);

		JSplitPane spTopBottom = new JSplitPane();
		spTopBottom.setResizeWeight(1.0);//TODO:SF TEST
		spTopBottom.setOrientation(JSplitPane.VERTICAL_SPLIT);
		contentPane.add(spTopBottom, BorderLayout.CENTER);

		// initialize SoundPlayer
		file = new File("BattlefieldAudio/00.wav");   // This is the file we'll be playing
		try {
			player = new SoundPlayer(file, false);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		} catch (MidiUnavailableException e1) {
			e1.printStackTrace();
		} catch (InvalidMidiDataException e1) {
			e1.printStackTrace();
		}
		JSplitPane spTopLR = new JSplitPane();
		spTopLR.setResizeWeight(0.5);
		spTopBottom.setLeftComponent(spTopLR);

		JPanel pnlVideo = new JPanel();
		spTopLR.setLeftComponent(pnlVideo);

		JLabel Label1 = new JLabel("VIDEO WILL GO HERE");
		//pnlVideo.add(Label1);
        final JFXPanel fxPanel = new JFXPanel();
		pnlVideo.add(fxPanel);

		Platform.runLater(new Runnable() {
            @Override
            public void run() {
                MoviePlayer.initFX(fxPanel);
            }
	    });

		JScrollPane pnlMap = new JScrollPane(baMapGraphicsSurf);
		spTopLR.setRightComponent(pnlMap);
		//pnlMap.setViewportView(baMapGraphicsSurf);
		//pnlMap.setAutoScrolls(true);

		JSplitPane spBottomTB = new JSplitPane();
		spTopBottom.setRightComponent(spBottomTB);

		JPanel pnlLoggedInUser = new UserPanel(reviewer_logged_in);
		spBottomTB.setLeftComponent(pnlLoggedInUser);
		pnlLoggedInUser.setLayout(new BorderLayout(0, 0));

		UserPanel user_panel = new UserPanel(reviewer_logged_in);
    pnlLoggedInUser.add(user_panel);
		//JLabel lblNewLabel_1 = new JLabel("LOGGED IN USER INFO GOES HERE");
		//pnlLoggedInUser.add(lblNewLabel_1, BorderLayout.SOUTH);

		spSliderButtonsTB = new JSplitPane();
		spSliderButtonsTB.setOrientation(JSplitPane.VERTICAL_SPLIT);
		spSliderButtonsTB.setResizeWeight(0.5);
		spBottomTB.setRightComponent(spSliderButtonsTB);

		JPanel pnlControls = new JPanel();
		spSliderButtonsTB.setRightComponent(pnlControls);
		pnlControls.setLayout(new GridLayout(1, 6, 0, 0));

		JButton btnRewind = new JButton("|<<");
		btnRewind.addActionListener(new ActionListener() {
			@Override
      public void actionPerformed(ActionEvent arg0) {
				System.out.println("|<< pressed");
			}
		});
		pnlControls.add(btnRewind);

		JButton btnPlayPause = new JButton(">/II");
		btnPlayPause.addActionListener(new ActionListener() {
			@Override
      public void actionPerformed(ActionEvent arg0) {
				System.out.println(">/II pressed");
				MediaPlayer mp = MoviePlayer.getMediaPlayer();
				MoviePlayer.playMovie(mp);
				if (mp.getStatus() != MediaPlayer.Status.PLAYING) {
					MoviePlayer.playMovie(mp);
				}
				else {
					MoviePlayer.pauseMovie(mp);
				}

				if (player.playing) {
					player.stop();
				} else {
					player.play();
				}
				sliderProgress = player.progress;
				sliderProgress.setPaintLabels(true);
				sliderProgress.setPaintTicks(true);
				spSliderButtonsTB.setLeftComponent(sliderProgress);
				sliderProgress.addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent arg0) {
						JSlider source = (JSlider)arg0.getSource();
						if (!source.getValueIsAdjusting()) {
							double value = source.getValue();
							double max = source.getMaximum();
							percent = (value/max)*100.0;
							filter((int)percent);
						}
					}
				});


			}
		});
		pnlControls.add(btnPlayPause);

		JButton btnFastForward = new JButton(">>|");
		btnFastForward.addActionListener(new ActionListener() {
			@Override
      public void actionPerformed(ActionEvent arg0) {
				System.out.println(">>| pressed");
			}
		});
		pnlControls.add(btnFastForward);

		cmbCamera = new JComboBox();
		cmbCamera.addActionListener(new ActionListener() {
			@Override
      public void actionPerformed(ActionEvent arg0) {
				System.out.println("Camera for: " + cmbCamera.getSelectedItem()  + " selected.");
			}
		});
		cmbCamera.setModel(new DefaultComboBoxModel(new String[] {"Camera - Choose One", "Sean Fast", "David Gwalthney", "David Shanline", "Michael Norris", "Emmanuel Bonilla"}));
		pnlControls.add(cmbCamera);

		cmbAudio = new JComboBox();
		cmbAudio.addActionListener(new ActionListener() {
			@Override
      public void actionPerformed(ActionEvent arg0) {
				selectedAudioIndex =  cmbAudio.getSelectedIndex();
				player.stop();
				file = new File("BattlefieldAudio/0"+selectedAudioIndex+".wav");   // This is the file we'll be playing
				try {
					player = new SoundPlayer(file, false);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (UnsupportedAudioFileException e) {
					e.printStackTrace();
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				} catch (MidiUnavailableException e) {
					e.printStackTrace();
				} catch (InvalidMidiDataException e) {
					e.printStackTrace();
				}
			}
		});
		cmbAudio.setModel(new DefaultComboBoxModel(new String[] {"Audio - Choose One", "Sean Fast", "David Gwalthney", "David Shanline", "Michael Norris", "Emmanuel Bonilla"}));
		pnlControls.add(cmbAudio);

		JScrollPane scrollPane = new JScrollPane();
		pnlControls.add(scrollPane);

		listGPSData = new JList();
		listGPSData.addListSelectionListener(new ListSelectionListener() {
			@Override
      public void valueChanged(ListSelectionEvent arg0) {
				System.out.println("GPS Data for: " + listGPSData.getSelectedValuesList() + " selected.");
			}
		});
		scrollPane.setViewportView(listGPSData);
		listGPSData.setVisibleRowCount(3);
		listGPSData.setModel(new AbstractListModel() {
			String[] values = new String[] {"GPS Data - Choose Any/All", "Sean Fast", "David Gwalthney", "David Shanline", "Michael Norris", "Emmanuel Bonilla"};
			@Override
      public int getSize() {
				return values.length;
			}
			@Override
      public Object getElementAt(int index) {
				return values[index];
			}
		});

		//JSlider sliderProgress = new JSlider();
		sliderProgress = player.progress;
		sliderProgress.setPaintLabels(true);
		sliderProgress.setPaintTicks(true);
		spSliderButtonsTB.setLeftComponent(sliderProgress);
		sliderProgress.addChangeListener(new ChangeListener() {
			@Override
      public void stateChanged(ChangeEvent arg0) {
			    JSlider source = (JSlider)arg0.getSource();
			    if (!source.getValueIsAdjusting()) {
					double value = source.getValue();
					double max = source.getMaximum();
					percent = (value/max)*100.0;
					filter((int)percent);
			    } else {
			    	double value = source.getValue();
					double max = source.getMaximum();
					percent = (value/max)*100.0;
					filter((int)percent);
			    }
			}
		});
	}

	@Override
	public void update() {
		//updateStatsTextHoverOff(0);
	}

	@Override
	public void update(int hrIndex) {
		//updateStatsTextHoverOn(hrIndex);
	}


	@Override
	public void update(ArrayList<GPSDataPoint> homeRunListArrayList) {
		// TODO Auto-generated method stub

	}

  public void filter(int endPoint){
			//display filtered stats in text area when Filter button is pressed
			filteredHomeRunArrayList.clear();
			//try{
				filteredHomeRunArrayList = FilterGPSDataPointList.filter(masterList, endPoint);
				//update baseball field gui to display only filtered home run locations
				baMapGraphicsSurf.updateBattlefieldAirmanMapGraphicsSurface(filteredHomeRunArrayList);
				//jtaStats.setText(Integer.toString(filteredHomeRunArrayList.size()));//DEBUG
				//display only stats of filtered home runs in text area
				//displayFilteredStats();
			//}catch (ParseException e) {
			 // e.printStackTrace();
			//}
  }

  /**
   *  After the timer has expired it will call this function.
   *  This will lock the screen and make the user re-enter his
   *  password to continue.
   */
  private static void checkForTimeout()
  {
    if( not_first_time )
    {
      pass_dialog_box.activateLock(frame);
      frame.setVisible(true);
    }
    not_first_time = true;
  }

}

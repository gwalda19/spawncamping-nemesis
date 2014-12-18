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
private JSlider sliderProgress;
private JSplitPane spSliderButtonsTB;

	/**
	 * Launch the application.
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
		file = new File("0"+1+".wav");   // This is the file we'll be playing
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
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("|<< pressed");
			}
		});
		pnlControls.add(btnRewind);

		JButton btnPlayPause = new JButton(">/II");
		btnPlayPause.addActionListener(new ActionListener() {
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
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(">>| pressed");
			}
		});
		pnlControls.add(btnFastForward);

		cmbCamera = new JComboBox();
		cmbCamera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Camera for: " + cmbCamera.getSelectedItem()  + " selected.");
			}
		});
		cmbCamera.setModel(new DefaultComboBoxModel(new String[] {"Camera - Choose One", "Sean Fast", "David Gwalthney", "David Shanline", "Michael Norris", "Emmanuel Bonilla"}));
		pnlControls.add(cmbCamera);

		cmbAudio = new JComboBox();
		cmbAudio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectedAudioIndex =  cmbAudio.getSelectedIndex();
				player.stop();
				file = new File("0"+selectedAudioIndex+".wav");   // This is the file we'll be playing
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



//	private static final long serialVersionUID = 1L;
//	private final JPanel contentPane;
//	private final JTextArea jtaStats;
//	private final JComboBox comboboxBatterTeam = new JComboBox();
//	private final JComboBox comboboxPitcherTeam = new JComboBox();
//	private final JComboBox comboboxBatter = new JComboBox();
//	private final JComboBox comboboxPitcher = new JComboBox();
//	private final JComboBox comboboxInning = new JComboBox();
//	private final JComboBox comboboxGameOutcome = new JComboBox();
//	private final JComboBox comboboxOuts = new JComboBox();
//	private final JComboBox comboboxBalls = new JComboBox();
//	private final JComboBox comboboxStrikes = new JComboBox();
//	private final JComboBox comboboxTimeOfDay = new JComboBox();
//	private final JComboBox comboboxPhilliesScore = new JComboBox();
//	private final JComboBox comboboxVisitorsScore = new JComboBox();
//	private final JComboBox comboboxRunsScored = new JComboBox();
//	private final JComboBox comboboxLRHandedBatter = new JComboBox();
//	private final JComboBox comboboxStartDate = new JComboBox();
//	private final JComboBox comboboxEndDate = new JComboBox();
//	private static ArrayList<GPSDataPoint> masterList = new ArrayList<GPSDataPoint>();
//	private ArrayList<GPSDataPoint> filteredHomeRunArrayList = new ArrayList<GPSDataPoint>();
//	private final ArrayList<GPSDataPoint> backupFilteredHomeRunArrayList = new ArrayList<GPSDataPoint>();
//	private final BattlefieldAirmanMapGraphicsSurface hrGraphicsSurf = new BattlefieldAirmanMapGraphicsSurface(masterList);
//	private final PitchLocationGraphicsSurface locationGraphSurf = new PitchLocationGraphicsSurface(masterList);
//	private final JTextField jtfDistanceMax;
//	private final JTextField jtfDistanceMin;
//
//  private static PassWordDialog passDialog;
//
//
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			@Override
//      public void run() {
//				try {
//					masterList = ReadCSV.read();
//
//					//Uncomment out to add the password dialog in.
//					//JFrame frame = new JFrame();
//					//passDialog = new PassWordDialog(frame, true);
//			    //passDialog.setVisible(true);
//
//					//Remove the below line when the above lines are back in.
//					BattlefieldAirmanGui frame = new BattlefieldAirmanGui(masterList);
//			    frame = new BattlefieldAirmanGui(masterList);
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//
//	/**
//	 * Create the frame.
//	 */
//	public BattlefieldAirmanGui(final ArrayList<GPSDataPoint> masterList) {
//		setTitle("Home Run Stats");
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 1200, 900);
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		contentPane.setLayout(new BorderLayout(0, 0));
//		setContentPane(contentPane);
//
//		hrGraphicsSurf.registerHomeRunObserver(this);
//		hrGraphicsSurf.registerHomeRunObserver(locationGraphSurf);
//		locationGraphSurf.registerStrikeZoneObserver(hrGraphicsSurf);
//
//
//		//ADD SWING GUI ELEMENTS TO JFRAME
//		JSplitPane splitPane = new JSplitPane();
//		contentPane.add(splitPane, BorderLayout.CENTER);
//
//		JSplitPane splitPane_1 = new JSplitPane();
//		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
//		splitPane.setLeftComponent(splitPane_1);
//
//		JScrollPane scrollPaneController = new JScrollPane();
//		splitPane_1.setLeftComponent(scrollPaneController);
//
//		final JPanel pnlController = new JPanel();
//		scrollPaneController.setViewportView(pnlController);
//		pnlController.setLayout(new GridLayout(0, 2, 0, 0));
//
//		JLabel lblFilterBy = new JLabel("Filter Data By:");
//		lblFilterBy.setFont(new Font("Tahoma", Font.BOLD, 11));
//		pnlController.add(lblFilterBy);
//
//		JLabel lblFilterBySpacer = new JLabel("");
//		pnlController.add(lblFilterBySpacer);
//
//		//populate distance into textfield after sorting numerically
//		//ArrayList<Integer> distanceArrayTemp = CreateLists.createDistanceList(masterList);
//		//final Integer[] distanceArray = distanceArrayTemp.toArray(new Integer[distanceArrayTemp.size()]);
//		//java.util.Arrays.sort(distanceArray);
//
//		JLabel lblDistanceMin = new JLabel("Minimum Distance");
//		pnlController.add(lblDistanceMin);
//
//		jtfDistanceMin = new JTextField();
//		//jtfDistanceMin.setText(distanceArray[0].toString());
//		pnlController.add(jtfDistanceMin);
//		jtfDistanceMin.setColumns(10);
//
//		JLabel lblDistanceMax = new JLabel("Maximum Distance");
//		pnlController.add(lblDistanceMax);
//
//		jtfDistanceMax = new JTextField();
//		//jtfDistanceMax.setText(distanceArray[distanceArray.length - 1].toString());
//		pnlController.add(jtfDistanceMax);
//		jtfDistanceMax.setColumns(10);
//
//		JLabel lblStartDate = new JLabel("Start Date");
//		pnlController.add(lblStartDate);
//
//		//populate date into combobox after sorting numerically
//		//ArrayList<String> dateArrayTemp = CreateLists.createDateList(masterList);
//		//final String[] dateArray = dateArrayTemp.toArray(new String[dateArrayTemp.size()]);
//		//java.util.Arrays.sort(dateArray);
//
//		//comboboxStartDate = new JComboBox(dateArray);
//		pnlController.add(comboboxStartDate);
//
//		//TODO: add code to force input to date format only
//
//		JLabel lblEndDate = new JLabel("End Date");
//		pnlController.add(lblEndDate);
//
//		//comboboxEndDate = new JComboBox(dateArray);
//		//comboboxEndDate.setSelectedIndex(dateArray.length - 1);
//		pnlController.add(comboboxEndDate);
//
//		JLabel lblBatterTeam = new JLabel("Batter Team");
//		pnlController.add(lblBatterTeam);
//
//		//populate batter team names into combobox after sorting alphabetically
//		//ArrayList<String> batterTeamsTemp = CreateLists.createBatterTeamList(masterList);
//		//String[] batterTeams = batterTeamsTemp.toArray(new String[batterTeamsTemp.size()]);
//		//java.util.Arrays.sort(batterTeams);
//		//comboboxBatterTeam = new JComboBox(batterTeams);
//		pnlController.add(comboboxBatterTeam);
//
//		JLabel lblPitcherTeam = new JLabel("Pitcher Team");
//		pnlController.add(lblPitcherTeam);
//
//		//populate pitcher team names into combobox after sorting alphabetically
//		//ArrayList<String> pitcherTeamsTemp = CreateLists.createPitcherTeamList(masterList);
//		//String[] pitcherTeams = pitcherTeamsTemp.toArray(new String[pitcherTeamsTemp.size()]);
//		//java.util.Arrays.sort(pitcherTeams);
//		//comboboxPitcherTeam = new JComboBox(pitcherTeams);
//		pnlController.add(comboboxPitcherTeam);
//
//		JLabel lblBatter = new JLabel("Batter");
//		pnlController.add(lblBatter);
//
//		//ArrayList<String> batterFullNameTemp = CreateLists.createBatterFullNameList(masterList);
//		//String[] batterFullNames = batterFullNameTemp.toArray(new String[batterFullNameTemp.size()]);
//		//java.util.Arrays.sort(batterFullNames);
//		//comboboxBatter = new JComboBox(batterFullNames);
//		pnlController.add(comboboxBatter);
//
//		JLabel lblPitcher = new JLabel("Pitcher");
//		pnlController.add(lblPitcher);
//
//		//ArrayList<String> pitcherFullNameTemp = CreateLists.createPitcherFullNameList(masterList);
//		//String[] pitcherFullNames = pitcherFullNameTemp.toArray(new String[pitcherFullNameTemp.size()]);
//		//java.util.Arrays.sort(pitcherFullNames);
//		//comboboxPitcher = new JComboBox(pitcherFullNames);
//		pnlController.add(comboboxPitcher);
//
//		JLabel lblInning = new JLabel("Inning");
//		pnlController.add(lblInning);
//
//		//populate innings into combobox after sorting numerically
//		//ArrayList<Integer> inningsTemp = CreateLists.createInningsList(masterList);
//		//Collections.sort(inningsTemp);
//		//inningsTemp.add(0, 999);
//		//Integer[] innings = inningsTemp.toArray(new Integer[inningsTemp.size()]);
//		//comboboxInning = new JComboBox(innings);
//		pnlController.add(comboboxInning);
//
//		JLabel lblGameOutcome = new JLabel("Game Outcome:");
//		pnlController.add(lblGameOutcome);
//
//		String[] outcomes = {"ALL", "Win", "Loss"};
//		//comboboxGameOutcome = new JComboBox(outcomes);
//		pnlController.add(comboboxGameOutcome);
//
//		JLabel lblOuts = new JLabel("Outs");
//		pnlController.add(lblOuts);
//
//		Integer[] outs = {999, 0, 1, 2};
//		//comboboxOuts = new JComboBox(outs);
//		//comboboxOuts.setSelectedIndex(-1);
//		pnlController.add(comboboxOuts);
//
//		JLabel lblBalls = new JLabel("Balls");
//		pnlController.add(lblBalls);
//
//		Integer[] balls = {999, 0, 1, 2, 3};
//		//comboboxBalls = new JComboBox(balls);
//		//comboboxBalls.setSelectedIndex(-1);
//		pnlController.add(comboboxBalls);
//
//		JLabel lblStrikes = new JLabel("Strikes");
//		pnlController.add(lblStrikes);
//
//		Integer[] strikes = {999, 0, 1, 2};
//		//comboboxStrikes = new JComboBox(strikes);
//		//comboboxStrikes.setSelectedIndex(-1);
//		pnlController.add(comboboxStrikes);
//
//		JLabel lblTimeOfDay = new JLabel("Time of Day");
//		pnlController.add(lblTimeOfDay);
//
//		String[] tod = {"ALL", "Day", "Night"};
//		//comboboxTimeOfDay = new JComboBox(tod);
//		pnlController.add(comboboxTimeOfDay);
//
//		JLabel lblPhilliesScore = new JLabel("Phillies Score");
//		pnlController.add(lblPhilliesScore);
//
//		//populate phillies scores into combobox after sorting numerically
//		//ArrayList<Integer> philliesScoreTemp = CreateLists.createPhilliesScoreList(masterList);
//		//Collections.sort(philliesScoreTemp);
//		//philliesScoreTemp.add(0, 999);
//		//Integer[] philliesScore = philliesScoreTemp.toArray(new Integer[philliesScoreTemp.size()]);
//		//comboboxPhilliesScore = new JComboBox(philliesScore);
//		pnlController.add(comboboxPhilliesScore);
//
//		JLabel lblVisitorsScore = new JLabel("Visitors Score");
//		pnlController.add(lblVisitorsScore);
//
//		//populate visitors scores into combobox after sorting numerically
//		//ArrayList<Integer> visitorScoreTemp = CreateLists.createVisitorScoreList(masterList);
//		//Collections.sort(visitorScoreTemp);
//		//visitorScoreTemp.add(0, 999);
//		//Integer[] visitorScore = visitorScoreTemp.toArray(new Integer[visitorScoreTemp.size()]);
//		//comboboxVisitorsScore = new JComboBox(visitorScore);
//		pnlController.add(comboboxVisitorsScore);
//
//		JLabel lblRunsScored = new JLabel("Runs Scored");
//		pnlController.add(lblRunsScored);
//
//		Integer[] runsScored = {999, 1, 2, 3, 4};
//		//comboboxRunsScored = new JComboBox(runsScored);
//		//comboboxRunsScored.setSelectedIndex(-1);
//		pnlController.add(comboboxRunsScored);
//
//		JLabel lblLRHandedBatter = new JLabel("L/R Handed Batter");
//		pnlController.add(lblLRHandedBatter);
//
//		String[] lrhb = {"ALL", "Left", "Right"};
//		//comboboxLRHandedBatter = new JComboBox(lrhb);
//		pnlController.add(comboboxLRHandedBatter);
//
//		JLabel lblResetSpacer = new JLabel("");
//		pnlController.add(lblResetSpacer);
//
//		JLabel lblSubmitSpacer = new JLabel("");
//		pnlController.add(lblSubmitSpacer);
//
//		JButton btnReset = new JButton("Reset");
//		pnlController.add(btnReset);
//
//		JButton btnFilter = new JButton("Filter");
//		pnlController.add(btnFilter);
//
//	//	JPanel pnlStrikeZone = new JPanel();
//		splitPane_1.setRightComponent(locationGraphSurf);
//
//		JSplitPane splitPane_2 = new JSplitPane();
//		splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
//		splitPane.setRightComponent(splitPane_2);
//
//	//	JPanel pnlStadium = new JPanel();
//		splitPane.setDividerLocation(400);
//		splitPane_1.setDividerLocation(505);
//		splitPane_2.setDividerLocation(750);
//
//
//		//	splitPane_2.setLeftComponent(hrGraphicsSurf);
//    	JScrollPane scrollpaneField = new JScrollPane();
//    //	scrollpaneField.add(hrGraphicsSurf);
//    //	scrollpaneField.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//		splitPane_2.setLeftComponent(scrollpaneField);
//	//	scrollpaneField.setPreferredSize(new Dimension(1300,300));
//		scrollpaneField.setViewportView(hrGraphicsSurf);
//
//
//		JScrollPane scrollpaneStats = new JScrollPane();
//		splitPane_2.setRightComponent(scrollpaneStats);
//
//
//		jtaStats = new JTextArea();
//		jtaStats.setEditable(false);
//		scrollpaneStats.setViewportView(jtaStats);
//		filter();
//
//		btnReset.addActionListener(new ActionListener() {
//			@Override
//      public void actionPerformed(ActionEvent arg0) {
//				//reset gui components to initial data here when Reset button is pressed
//				//jtfDistanceMin.setText(distanceArray[0].toString());
//				//jtfDistanceMax.setText(distanceArray[distanceArray.length - 1].toString());
//				comboboxStartDate.setSelectedIndex(0);
//				//comboboxEndDate.setSelectedIndex(dateArray.length - 1);
//				comboboxBatterTeam.setSelectedIndex(0);
//				comboboxPitcherTeam.setSelectedIndex(0);
//				comboboxBatter.setSelectedIndex(0);
//				comboboxPitcher.setSelectedIndex(0);
//				comboboxInning.setSelectedIndex(0);
//				comboboxGameOutcome.setSelectedIndex(0);
//				comboboxOuts.setSelectedIndex(0);
//				comboboxBalls.setSelectedIndex(0);
//				comboboxStrikes.setSelectedIndex(0);
//				comboboxTimeOfDay.setSelectedIndex(0);
//				comboboxPhilliesScore.setSelectedIndex(0);
//				comboboxVisitorsScore.setSelectedIndex(0);
//				comboboxRunsScored.setSelectedIndex(0);
//				comboboxLRHandedBatter.setSelectedIndex(0);
//
//				//updateStatsTextHoverOn(0); //DEBUG: test stubbed code for mousehoveron, remove this later on
//			}
//		});
//
//		btnFilter.addActionListener(new ActionListener() {
//			@Override
//      public void actionPerformed(ActionEvent arg0){
//			filter();
//			}
//		});
//	}
//
//	//function to display stats from filteredHomeRunArrayList in stats text area
//	/*
//	public void displayFilteredStats() {
//		//jtaStats.setText(Integer.toString(filteredHomeRunArrayList.size()));//DEBUG
//		//iterate through filtered home run array, build string to display in jta
//		String columnsHeader = "HR#\tDISTANCE\tBATTER\tBATTER TEAM\tPITCHER\tPITCHER TEAM\t\tINNING\tRBIS\n";
//		String filteredStats = "";
//
//		for (GPSDataPoint hr: filteredHomeRunArrayList){ //traverse arraylist
//			//build homerun data for each homerun into string
//			//TODO: add more stats to stat window
//			//filteredStats += hr.getHomeRunId() + "\t";
//			//filteredStats += hr.getDistance() + "\t";
//			//filteredStats += hr.getBatterLastName() + "\t";
//			//filteredStats += hr.getBatterTeam() + "\t";
//			//filteredStats += hr.getPitcherLastName() + "\t";
//			//filteredStats += hr.getPitcherTeam() + "\t\t";
//			//filteredStats += hr.getInning() + "\t";
//			//filteredStats += hr.getRunsBattedIn() + "\t";
//			//filteredStats += "\n";
//
//		}
//
//		jtaStats.setText(columnsHeader + filteredStats); //display built string in text area
//	}
//	*/
//
//	//function to display only stats of homerun you hover over
//	public void updateStatsTextHoverOn(int hr_array_index){
//		backupFilteredHomeRunArrayList.clear(); //clear backup arraylist
//		backupFilteredHomeRunArrayList.addAll(filteredHomeRunArrayList);//save off filteredarraylist into backup arraylist (backupFilteredHomeRunArrayList)
//		filteredHomeRunArrayList.clear();//clear filteredarraylist
//		filteredHomeRunArrayList.add(backupFilteredHomeRunArrayList.get(hr_array_index));//add element of backuparraylist(hr_array_index) to filteredarraylist
//		//displayFilteredStats();//run displayfilteredstats on new filteredarraylist
//		filteredHomeRunArrayList.addAll(backupFilteredHomeRunArrayList);//copy backupfilteredarraylist into filteredarraylist
//
//	}
//
//	//function to display stats of all filtered homeruns again after hover is removed
//	public void updateStatsTextHoverOff(int hr_array_index){
//		filteredHomeRunArrayList.clear(); //clear filteredarraylist
//		filteredHomeRunArrayList.addAll(backupFilteredHomeRunArrayList);//copy backupfilteredarraylist into filteredarraylist
//		//displayFilteredStats();//run displayfilteredstats on new filteredarraylist (which is old filteredarraylist before mousehover)
//
//	}
//
//
//    public void filter(){
//			//display filtered stats in text area when Filter button is pressed
//			filteredHomeRunArrayList.clear();
//			//build filter string array from gui component fields
//			String[] filterString = new String[20];
//			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
//			Date startDate;
//			Date endDate;
//			//try{
//			  //startDate = df.parse(comboboxStartDate.getSelectedItem().toString());
//			  //endDate = df.parse(comboboxEndDate.getSelectedItem().toString());
//
//			  //if (startDate.compareTo(endDate)>0){
//			    //startDate is after endDate
//				  //JOptionPane.showMessageDialog(contentPane,
//						    //"Start Date occurs after End Date.",
//						    //"Date Range Error",
//						    //JOptionPane.ERROR_MESSAGE);
//			  //} else if(Integer.parseInt(jtfDistanceMax.getText()) < Integer.parseInt(jtfDistanceMin.getText())){
//				  //maxdistance is less than min distance
//				  //JOptionPane.showMessageDialog(contentPane,
//						    //"Distance Max is less than Distance Min.",
//						    //"Distance Range Error",
//						    //JOptionPane.ERROR_MESSAGE);
//			  //}
//			  //else {
//			    //startDate is before endDate
//					//filterString[0] = (String) comboboxStartDate.getSelectedItem();
//					//filterString[1] = (String) comboboxEndDate.getSelectedItem();
//					//filterString[2] = (String) comboboxBatterTeam.getSelectedItem();
//					//filterString[3] = (String) comboboxPitcherTeam.getSelectedItem();
//					//filterString[4] = (String) comboboxBatter.getSelectedItem();
//					//filterString[5] = (String) comboboxPitcher.getSelectedItem();
//					//filterString[6] = comboboxInning.getSelectedItem().toString();
//					//filterString[7] = (String) comboboxGameOutcome.getSelectedItem();
//					//filterString[8] = comboboxOuts.getSelectedItem().toString();
//					//filterString[9] = comboboxBalls.getSelectedItem().toString();
//					//filterString[10] = comboboxStrikes.getSelectedItem().toString();
//					//filterString[11] = (String) comboboxTimeOfDay.getSelectedItem();
//					//filterString[12] = comboboxPhilliesScore.getSelectedItem().toString();
//					//filterString[13] = comboboxVisitorsScore.getSelectedItem().toString();
//					//filterString[14] = comboboxRunsScored.getSelectedItem().toString();
//					//filterString[15] = comboboxLRHandedBatter.getSelectedItem().toString();
//					//filterString[16] = jtfDistanceMin.getText();
//					//filterString[17] = jtfDistanceMax.getText();
//
//					//just for debugging, traverse string array and build single string from it
////					String debug = "";
////					for (String s: filterString) {
////						debug = debug + s + " ";
////					}
////					jtaStats.setText(debug);
//					//END just for debugging
//
//					//ArrayList<HomeRun> HomeRunArrayList = new ArrayList<HomeRun>();
//
//					//call filter method from filter class and pass filter string
//					filteredHomeRunArrayList = FilterGPSDataPointList.filter(masterList, filterString);
//					//update baseball field gui to display only filtered home run locations
//					hrGraphicsSurf.updateBattlefieldAirmanMapGraphicsSurface(filteredHomeRunArrayList);
//					locationGraphSurf.updatePitchLocationGraphicsSurface(filteredHomeRunArrayList);
//					//jtaStats.setText(Integer.toString(filteredHomeRunArrayList.size()));//DEBUG
//					//display only stats of filtered home runs in text area
//					//displayFilteredStats();
//			//}
//			//}catch (ParseException e) {
//			  //e.printStackTrace();
//			//}
//    }
//
//}

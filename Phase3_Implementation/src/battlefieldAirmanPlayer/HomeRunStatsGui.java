package battlefieldAirmanPlayer;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class HomeRunStatsGui extends JFrame implements HomeRunObserver{

	/**
	 *
	 */
	//this is a test
	private static final long serialVersionUID = 1L;
	private final JPanel contentPane;
	private final JTextArea jtaStats;
	private final JComboBox comboboxBatterTeam;
	private final JComboBox comboboxPitcherTeam;
	private final JComboBox comboboxBatter;
	private final JComboBox comboboxPitcher;
	private final JComboBox comboboxInning;
	private final JComboBox comboboxGameOutcome;
	private final JComboBox comboboxOuts;
	private final JComboBox comboboxBalls;
	private final JComboBox comboboxStrikes;
	private final JComboBox comboboxTimeOfDay;
	private final JComboBox comboboxPhilliesScore;
	private final JComboBox comboboxVisitorsScore;
	private final JComboBox comboboxRunsScored;
	private final JComboBox comboboxLRHandedBatter;
	private final JComboBox comboboxStartDate;
	private final JComboBox comboboxEndDate;
	private static ArrayList<HomeRun> masterList = new ArrayList<HomeRun>();
	private ArrayList<HomeRun> filteredHomeRunArrayList = new ArrayList<HomeRun>();
	private final ArrayList<HomeRun> backupFilteredHomeRunArrayList = new ArrayList<HomeRun>();
	private final HomeRunGraphicsSurface hrGraphicsSurf = new HomeRunGraphicsSurface(masterList);
	private final PitchLocationGraphicsSurface locationGraphSurf = new PitchLocationGraphicsSurface(masterList);
	private final JTextField jtfDistanceMax;
	private final JTextField jtfDistanceMin;

  private static PassWordDialog passDialog;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
      public void run() {
				try {
					masterList = ReadCSV.read();

					//Uncomment out to add the password dialog in.
					//JFrame frame = new JFrame();
					//passDialog = new PassWordDialog(frame, true);
			    //passDialog.setVisible(true);

					//Remove the below line when the above lines are back in.
					HomeRunStatsGui frame = new HomeRunStatsGui(masterList);
			    frame = new HomeRunStatsGui(masterList);
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
	public HomeRunStatsGui(final ArrayList<HomeRun> masterList) {
		setTitle("Home Run Stats");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 900);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		hrGraphicsSurf.registerHomeRunObserver(this);
		hrGraphicsSurf.registerHomeRunObserver(locationGraphSurf);
		locationGraphSurf.registerStrikeZoneObserver(hrGraphicsSurf);


		//ADD SWING GUI ELEMENTS TO JFRAME
		JSplitPane splitPane = new JSplitPane();
		contentPane.add(splitPane, BorderLayout.CENTER);

		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setLeftComponent(splitPane_1);

		JScrollPane scrollPaneController = new JScrollPane();
		splitPane_1.setLeftComponent(scrollPaneController);

		final JPanel pnlController = new JPanel();
		scrollPaneController.setViewportView(pnlController);
		pnlController.setLayout(new GridLayout(0, 2, 0, 0));

		JLabel lblFilterBy = new JLabel("Filter Data By:");
		lblFilterBy.setFont(new Font("Tahoma", Font.BOLD, 11));
		pnlController.add(lblFilterBy);

		JLabel lblFilterBySpacer = new JLabel("");
		pnlController.add(lblFilterBySpacer);

		//populate distance into textfield after sorting numerically
		ArrayList<Integer> distanceArrayTemp = CreateLists.createDistanceList(masterList);
		final Integer[] distanceArray = distanceArrayTemp.toArray(new Integer[distanceArrayTemp.size()]);
		java.util.Arrays.sort(distanceArray);

		JLabel lblDistanceMin = new JLabel("Minimum Distance");
		pnlController.add(lblDistanceMin);

		jtfDistanceMin = new JTextField();
		jtfDistanceMin.setText(distanceArray[0].toString());
		pnlController.add(jtfDistanceMin);
		jtfDistanceMin.setColumns(10);

		JLabel lblDistanceMax = new JLabel("Maximum Distance");
		pnlController.add(lblDistanceMax);

		jtfDistanceMax = new JTextField();
		jtfDistanceMax.setText(distanceArray[distanceArray.length - 1].toString());
		pnlController.add(jtfDistanceMax);
		jtfDistanceMax.setColumns(10);

		JLabel lblStartDate = new JLabel("Start Date");
		pnlController.add(lblStartDate);

		//populate date into combobox after sorting numerically
		ArrayList<String> dateArrayTemp = CreateLists.createDateList(masterList);
		final String[] dateArray = dateArrayTemp.toArray(new String[dateArrayTemp.size()]);
		java.util.Arrays.sort(dateArray);

		comboboxStartDate = new JComboBox(dateArray);
		pnlController.add(comboboxStartDate);

		//TODO: add code to force input to date format only

		JLabel lblEndDate = new JLabel("End Date");
		pnlController.add(lblEndDate);

		comboboxEndDate = new JComboBox(dateArray);
		comboboxEndDate.setSelectedIndex(dateArray.length - 1);
		pnlController.add(comboboxEndDate);

		JLabel lblBatterTeam = new JLabel("Batter Team");
		pnlController.add(lblBatterTeam);

		//populate batter team names into combobox after sorting alphabetically
		ArrayList<String> batterTeamsTemp = CreateLists.createBatterTeamList(masterList);
		String[] batterTeams = batterTeamsTemp.toArray(new String[batterTeamsTemp.size()]);
		java.util.Arrays.sort(batterTeams);
		comboboxBatterTeam = new JComboBox(batterTeams);
		pnlController.add(comboboxBatterTeam);

		JLabel lblPitcherTeam = new JLabel("Pitcher Team");
		pnlController.add(lblPitcherTeam);

		//populate pitcher team names into combobox after sorting alphabetically
		ArrayList<String> pitcherTeamsTemp = CreateLists.createPitcherTeamList(masterList);
		String[] pitcherTeams = pitcherTeamsTemp.toArray(new String[pitcherTeamsTemp.size()]);
		java.util.Arrays.sort(pitcherTeams);
		comboboxPitcherTeam = new JComboBox(pitcherTeams);
		pnlController.add(comboboxPitcherTeam);

		JLabel lblBatter = new JLabel("Batter");
		pnlController.add(lblBatter);

		ArrayList<String> batterFullNameTemp = CreateLists.createBatterFullNameList(masterList);
		String[] batterFullNames = batterFullNameTemp.toArray(new String[batterFullNameTemp.size()]);
		java.util.Arrays.sort(batterFullNames);
		comboboxBatter = new JComboBox(batterFullNames);
		pnlController.add(comboboxBatter);

		JLabel lblPitcher = new JLabel("Pitcher");
		pnlController.add(lblPitcher);

		ArrayList<String> pitcherFullNameTemp = CreateLists.createPitcherFullNameList(masterList);
		String[] pitcherFullNames = pitcherFullNameTemp.toArray(new String[pitcherFullNameTemp.size()]);
		java.util.Arrays.sort(pitcherFullNames);
		comboboxPitcher = new JComboBox(pitcherFullNames);
		pnlController.add(comboboxPitcher);

		JLabel lblInning = new JLabel("Inning");
		pnlController.add(lblInning);

		//populate innings into combobox after sorting numerically
		ArrayList<Integer> inningsTemp = CreateLists.createInningsList(masterList);
		Collections.sort(inningsTemp);
		inningsTemp.add(0, 999);
		Integer[] innings = inningsTemp.toArray(new Integer[inningsTemp.size()]);
		comboboxInning = new JComboBox(innings);
		pnlController.add(comboboxInning);

		JLabel lblGameOutcome = new JLabel("Game Outcome:");
		pnlController.add(lblGameOutcome);

		String[] outcomes = {"ALL", "Win", "Loss"};
		comboboxGameOutcome = new JComboBox(outcomes);
		pnlController.add(comboboxGameOutcome);

		JLabel lblOuts = new JLabel("Outs");
		pnlController.add(lblOuts);

		Integer[] outs = {999, 0, 1, 2};
		comboboxOuts = new JComboBox(outs);
		//comboboxOuts.setSelectedIndex(-1);
		pnlController.add(comboboxOuts);

		JLabel lblBalls = new JLabel("Balls");
		pnlController.add(lblBalls);

		Integer[] balls = {999, 0, 1, 2, 3};
		comboboxBalls = new JComboBox(balls);
		//comboboxBalls.setSelectedIndex(-1);
		pnlController.add(comboboxBalls);

		JLabel lblStrikes = new JLabel("Strikes");
		pnlController.add(lblStrikes);

		Integer[] strikes = {999, 0, 1, 2};
		comboboxStrikes = new JComboBox(strikes);
		//comboboxStrikes.setSelectedIndex(-1);
		pnlController.add(comboboxStrikes);

		JLabel lblTimeOfDay = new JLabel("Time of Day");
		pnlController.add(lblTimeOfDay);

		String[] tod = {"ALL", "Day", "Night"};
		comboboxTimeOfDay = new JComboBox(tod);
		pnlController.add(comboboxTimeOfDay);

		JLabel lblPhilliesScore = new JLabel("Phillies Score");
		pnlController.add(lblPhilliesScore);

		//populate phillies scores into combobox after sorting numerically
		ArrayList<Integer> philliesScoreTemp = CreateLists.createPhilliesScoreList(masterList);
		Collections.sort(philliesScoreTemp);
		philliesScoreTemp.add(0, 999);
		Integer[] philliesScore = philliesScoreTemp.toArray(new Integer[philliesScoreTemp.size()]);
		comboboxPhilliesScore = new JComboBox(philliesScore);
		//comboboxPhilliesScore.setSelectedIndex(-1);
		pnlController.add(comboboxPhilliesScore);

		JLabel lblVisitorsScore = new JLabel("Visitors Score");
		pnlController.add(lblVisitorsScore);

		//populate visitors scores into combobox after sorting numerically
		ArrayList<Integer> visitorScoreTemp = CreateLists.createVisitorScoreList(masterList);
		Collections.sort(visitorScoreTemp);
		visitorScoreTemp.add(0, 999);
		Integer[] visitorScore = visitorScoreTemp.toArray(new Integer[visitorScoreTemp.size()]);
		comboboxVisitorsScore = new JComboBox(visitorScore);
		//comboboxVisitorsScore.setSelectedIndex(-1);
		pnlController.add(comboboxVisitorsScore);

		JLabel lblRunsScored = new JLabel("Runs Scored");
		pnlController.add(lblRunsScored);

		Integer[] runsScored = {999, 1, 2, 3, 4};
		comboboxRunsScored = new JComboBox(runsScored);
		//comboboxRunsScored.setSelectedIndex(-1);
		pnlController.add(comboboxRunsScored);

		JLabel lblLRHandedBatter = new JLabel("L/R Handed Batter");
		pnlController.add(lblLRHandedBatter);

		String[] lrhb = {"ALL", "Left", "Right"};
		comboboxLRHandedBatter = new JComboBox(lrhb);
		pnlController.add(comboboxLRHandedBatter);

		JLabel lblResetSpacer = new JLabel("");
		pnlController.add(lblResetSpacer);

		JLabel lblSubmitSpacer = new JLabel("");
		pnlController.add(lblSubmitSpacer);

		JButton btnReset = new JButton("Reset");
		pnlController.add(btnReset);

		JButton btnFilter = new JButton("Filter");
		pnlController.add(btnFilter);

	//	JPanel pnlStrikeZone = new JPanel();
		splitPane_1.setRightComponent(locationGraphSurf);

		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setRightComponent(splitPane_2);

	//	JPanel pnlStadium = new JPanel();
		splitPane.setDividerLocation(400);
		splitPane_1.setDividerLocation(505);
		splitPane_2.setDividerLocation(750);


		//	splitPane_2.setLeftComponent(hrGraphicsSurf);
    	JScrollPane scrollpaneField = new JScrollPane();
    //	scrollpaneField.add(hrGraphicsSurf);
    //	scrollpaneField.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		splitPane_2.setLeftComponent(scrollpaneField);
	//	scrollpaneField.setPreferredSize(new Dimension(1300,300));
		scrollpaneField.setViewportView(hrGraphicsSurf);


		JScrollPane scrollpaneStats = new JScrollPane();
		splitPane_2.setRightComponent(scrollpaneStats);


		jtaStats = new JTextArea();
		jtaStats.setEditable(false);
		scrollpaneStats.setViewportView(jtaStats);
		filter();

		btnReset.addActionListener(new ActionListener() {
			@Override
      public void actionPerformed(ActionEvent arg0) {
				//reset gui components to initial data here when Reset button is pressed
				jtfDistanceMin.setText(distanceArray[0].toString());
				jtfDistanceMax.setText(distanceArray[distanceArray.length - 1].toString());
				comboboxStartDate.setSelectedIndex(0);
				comboboxEndDate.setSelectedIndex(dateArray.length - 1);
				comboboxBatterTeam.setSelectedIndex(0);
				comboboxPitcherTeam.setSelectedIndex(0);
				comboboxBatter.setSelectedIndex(0);
				comboboxPitcher.setSelectedIndex(0);
				comboboxInning.setSelectedIndex(0);
				comboboxGameOutcome.setSelectedIndex(0);
				comboboxOuts.setSelectedIndex(0);
				comboboxBalls.setSelectedIndex(0);
				comboboxStrikes.setSelectedIndex(0);
				comboboxTimeOfDay.setSelectedIndex(0);
				comboboxPhilliesScore.setSelectedIndex(0);
				comboboxVisitorsScore.setSelectedIndex(0);
				comboboxRunsScored.setSelectedIndex(0);
				comboboxLRHandedBatter.setSelectedIndex(0);

				//updateStatsTextHoverOn(0); //DEBUG: test stubbed code for mousehoveron, remove this later on
			}
		});

		btnFilter.addActionListener(new ActionListener() {
			@Override
      public void actionPerformed(ActionEvent arg0){
			filter();
			}
		});
	}

	//function to display stats from filteredHomeRunArrayList in stats text area
	public void displayFilteredStats() {
		//jtaStats.setText(Integer.toString(filteredHomeRunArrayList.size()));//DEBUG
		//iterate through filtered home run array, build string to display in jta
		String columnsHeader = "HR#\tDISTANCE\tBATTER\tBATTER TEAM\tPITCHER\tPITCHER TEAM\t\tINNING\tRBIS\n";
		String filteredStats = "";

		for (HomeRun hr: filteredHomeRunArrayList){ //traverse arraylist
			//build homerun data for each homerun into string
			//TODO: add more stats to stat window
			filteredStats += hr.getHomeRunId() + "\t";
			filteredStats += hr.getDistance() + "\t";
			filteredStats += hr.getBatterLastName() + "\t";
			filteredStats += hr.getBatterTeam() + "\t";
			filteredStats += hr.getPitcherLastName() + "\t";
			filteredStats += hr.getPitcherTeam() + "\t\t";
			filteredStats += hr.getInning() + "\t";
			filteredStats += hr.getRunsBattedIn() + "\t";
			filteredStats += "\n";

		}

		jtaStats.setText(columnsHeader + filteredStats); //display built string in text area
	}

	//function to display only stats of homerun you hover over
	public void updateStatsTextHoverOn(int hr_array_index){
		backupFilteredHomeRunArrayList.clear(); //clear backup arraylist
		backupFilteredHomeRunArrayList.addAll(filteredHomeRunArrayList);//save off filteredarraylist into backup arraylist (backupFilteredHomeRunArrayList)
		filteredHomeRunArrayList.clear();//clear filteredarraylist
		filteredHomeRunArrayList.add(backupFilteredHomeRunArrayList.get(hr_array_index));//add element of backuparraylist(hr_array_index) to filteredarraylist
		displayFilteredStats();//run displayfilteredstats on new filteredarraylist
		filteredHomeRunArrayList.addAll(backupFilteredHomeRunArrayList);//copy backupfilteredarraylist into filteredarraylist

	}

	//function to display stats of all filtered homeruns again after hover is removed
	public void updateStatsTextHoverOff(int hr_array_index){
		filteredHomeRunArrayList.clear(); //clear filteredarraylist
		filteredHomeRunArrayList.addAll(backupFilteredHomeRunArrayList);//copy backupfilteredarraylist into filteredarraylist
		displayFilteredStats();//run displayfilteredstats on new filteredarraylist (which is old filteredarraylist before mousehover)

	}

	@Override
	public void update() {
		updateStatsTextHoverOff(0);
	}

	@Override
	public void update(int hrIndex) {
//		updateStatsTextHoverOff(hrIndex);
		updateStatsTextHoverOn(hrIndex);
	}


	@Override
	public void update(ArrayList<HomeRun> homeRunListArrayList) {
		// TODO Auto-generated method stub

	}

    public void filter(){
			//display filtered stats in text area when Filter button is pressed
			filteredHomeRunArrayList.clear();
			//build filter string array from gui component fields
			String[] filterString = new String[20];
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			Date startDate;
			Date endDate;
			try{
			  startDate = df.parse(comboboxStartDate.getSelectedItem().toString());
			  endDate = df.parse(comboboxEndDate.getSelectedItem().toString());

			  if (startDate.compareTo(endDate)>0){
			    //startDate is after endDate
				  JOptionPane.showMessageDialog(contentPane,
						    "Start Date occurs after End Date.",
						    "Date Range Error",
						    JOptionPane.ERROR_MESSAGE);
			  } else if(Integer.parseInt(jtfDistanceMax.getText()) < Integer.parseInt(jtfDistanceMin.getText())){
				  //maxdistance is less than min distance
				  JOptionPane.showMessageDialog(contentPane,
						    "Distance Max is less than Distance Min.",
						    "Distance Range Error",
						    JOptionPane.ERROR_MESSAGE);
			  }
			  else {
			    //startDate is before endDate
					filterString[0] = (String) comboboxStartDate.getSelectedItem();
					filterString[1] = (String) comboboxEndDate.getSelectedItem();
					filterString[2] = (String) comboboxBatterTeam.getSelectedItem();
					filterString[3] = (String) comboboxPitcherTeam.getSelectedItem();
					filterString[4] = (String) comboboxBatter.getSelectedItem();
					filterString[5] = (String) comboboxPitcher.getSelectedItem();
					filterString[6] = comboboxInning.getSelectedItem().toString();
					filterString[7] = (String) comboboxGameOutcome.getSelectedItem();
					filterString[8] = comboboxOuts.getSelectedItem().toString();
					filterString[9] = comboboxBalls.getSelectedItem().toString();
					filterString[10] = comboboxStrikes.getSelectedItem().toString();
					filterString[11] = (String) comboboxTimeOfDay.getSelectedItem();
					filterString[12] = comboboxPhilliesScore.getSelectedItem().toString();
					filterString[13] = comboboxVisitorsScore.getSelectedItem().toString();
					filterString[14] = comboboxRunsScored.getSelectedItem().toString();
					filterString[15] = comboboxLRHandedBatter.getSelectedItem().toString();
					filterString[16] = jtfDistanceMin.getText();
					filterString[17] = jtfDistanceMax.getText();

					//just for debugging, traverse string array and build single string from it
//					String debug = "";
//					for (String s: filterString) {
//						debug = debug + s + " ";
//					}
//					jtaStats.setText(debug);
					//END just for debugging

					//ArrayList<HomeRun> HomeRunArrayList = new ArrayList<HomeRun>();

					//call filter method from filter class and pass filter string
					filteredHomeRunArrayList = FilterHomeRunArrayList.filter(masterList, filterString);
					//update baseball field gui to display only filtered home run locations
					hrGraphicsSurf.updateHomeRunGraphicsSurface(filteredHomeRunArrayList);
					locationGraphSurf.updatePitchLocationGraphicsSurface(filteredHomeRunArrayList);
					//jtaStats.setText(Integer.toString(filteredHomeRunArrayList.size()));//DEBUG
					//display only stats of filtered home runs in text area
					displayFilteredStats();
			}
			}catch (ParseException e) {
			  e.printStackTrace();
			}
    }

}

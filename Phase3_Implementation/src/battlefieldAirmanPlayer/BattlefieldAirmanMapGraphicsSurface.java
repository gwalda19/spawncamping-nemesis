package battlefieldAirmanPlayer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComponent;


public class BattlefieldAirmanMapGraphicsSurface extends JComponent implements StrikeZoneObserver
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	private int x1 = 0;
	private int y1 = 0;
	private int mouseOverRepaintCount = 0;
	private Image field = null;
	private boolean mouseOverDataPoint = false;
	//private boolean mouseOverStrikeZone = false;
	private ArrayList<GPSDataPoint> myDataPointList;
	private ArrayList<GPSDataPoint> myDataPointList2;// = new ArrayList<HomeRun>();
	private ArrayList<DataPointObserver> homeRunObservers = new ArrayList<DataPointObserver>();
	private GPSDataPoint dp;
	private String HOMETEAM = "Phillies";
	private AudioInputStream audioInputStream = null;
	private Clip clip = null;
//	List<Integer> l2;// = new ArrayList<Integer>(l1); //A new arrayList.

	@SuppressWarnings("unchecked")
	public BattlefieldAirmanMapGraphicsSurface(ArrayList<GPSDataPoint> dataPointList)
	{
		//for (GPSDataPoint dataPoint: myDataPointList){
			//x1 = dataPoint.getXPos();
			//y1 = dataPoint.getYPos();
			//System.out.println("datapoint: " + x1 + ", " + y1);
		//}
		myDataPointList = (ArrayList<GPSDataPoint>) dataPointList.clone();
		myDataPointList2 = dataPointList;
		this.addMouseMotionListener(new MouseMotionListener()
		{
			public void mouseDragged(MouseEvent e)
			{
				//x2 = e.getX();
				//y2 = e.getY();
			}
			
			public void mouseMoved(MouseEvent e)
			{
				x1 = e.getX();
				y1 = e.getY();
				mouseOverDataPoint(x1,y1);

			}
		});
		this.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getButton() == MouseEvent.BUTTON3) { 
					notifyHomeRunObserversMouseRightClicked();
				}
				else{
					if (arg0.getButton() == MouseEvent.BUTTON1) {
						x1 = arg0.getX();
						y1 = arg0.getY();
						System.out.println("Location: " + x1 +", " + y1);
					}
					if(isMouseOverDataPoint()){
						/*
						if (hr.getBatterTeam().equals("Phillies"))
						{
							initializePhilliesSound();
							clip.start();
						}
						else
						{
							initializeVisitorSound();
							clip.start();
						}
						*/
						notifyHomeRunObserversMouseClicked();
					}
					else{
						//clip.stop();
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	//	initializeSound();
	}//End HomeRunGraphicsSurface constructor
	
	
	/**
	 *Function: paint
	 *Description: paint gets called when ever there is a resizing of the screen
	 *			   This function displays the image of Citizen's Bank Park (CBP)
	 *             and paints the home runs on the display based on inputs from the GUI
	 */
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;	
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if (field == null)
			field = getImage("basic_map.jpg");
		g2d.drawImage(field, 0, 0, 750, 740, this);
		
		// This is the diameter of the home run that is displayed
		int diameter = 10;
		int dataPointCount = 0; 
		//int hrAwayTeamCount = 0; 
		
		for (GPSDataPoint dataPoint: myDataPointList){
			//Draw a red home run dot at x,y position
			//if (dataPoint.getBatterTeam().equals(HOMETEAM)){
				//g2d.setColor(Color.red);
				//g2d.fillOval(
						//dataPoint.getXPos() - (diameter / 2),
						//dataPoint.getYPos() - (diameter / 2),
						//diameter, diameter);
				//dataPointCount++;
				
			//}
			//else{
				//g2d.setColor(Color.blue);
				//g2d.fillOval(
						//dataPoint.getXPos() - (diameter / 2),
						//dataPoint.getYPos() - (diameter / 2),
						//diameter, diameter);
				//hrAwayTeamCount++;
			//}
			g2d.setColor(Color.red);
			g2d.fillOval(
					dataPoint.getXPos() - (diameter / 2),
					dataPoint.getYPos() - (diameter / 2),
					diameter, diameter);
			dataPointCount++;
			//Draw a black ring around home run dot
			g2d.setColor(Color.black);
			g2d.drawOval(
					dataPoint.getXPos() - (diameter / 2),
					dataPoint.getYPos() - (diameter / 2),
					diameter, diameter);
		}//End for	
		
		
		
		//int xpos = getWidth() - (getWidth()*29/30);
		//int ypos = getHeight() - (getHeight()*24/25);
		//int offset1 = 15;
		//g2d.setFont(new Font("Serif", Font.BOLD, 14));
		//g2d.drawString("Home Runs Displayed: " + myDataPointList.size(), xpos, ypos);
		//xpos += 5;
		//g2d.setColor(Color.red);
		//g2d.fillOval(
				//xpos - (diameter / 2),
				//(ypos += offset1) - (diameter / 2),
				//diameter, diameter);
		//g2d.drawString("- " + HOMETEAM + " " + dataPointCount, xpos + 8, ypos);	
		
		//g2d.setColor(Color.blue);
		//g2d.fillOval(
				//xpos - (diameter / 2),
				//(ypos += offset1)- (diameter / 2),
				//diameter, diameter);
		//g2d.drawString("- Visitors " + hrAwayTeamCount, xpos + 8, ypos);	

			
		// If mouse is over home run, then display extra data for 
		// the home run
		/*
		 * We will need this class. Just commenting it out for now until I get the proper CSV file
		 * and proper parsing finished
		if (isMouseOverDataPoint()){
			g2d.setFont(new Font("Serif", Font.BOLD, 20));
			int x1pos = getWidth() - getWidth()/4;
			int y1pos = getHeight()/25;
			int offset = 12;
			g2d.setColor(Color.yellow);
			g2d.fillOval(
					hr.getXPos() - (diameter / 2),
					hr.getYPos() - (diameter / 2),
					diameter, diameter);
			g2d.setColor(Color.black);
			g2d.drawString(hr.getBatterLastName(), hr.getXPos() + 15, hr.getYPos() + 5);
			g2d.drawString(Integer.toString(hr.getDistance()) + " ft", hr.getXPos() + 15, hr.getYPos() + 20);
			
			g2d.setFont(new Font("Serif", Font.BOLD, 14));
			g2d.drawString("Home Run Number: " + hr.getHomeRunId(), x1pos, y1pos);
			g2d.drawString("Batter: " + hr.getBatterFirstName() +" " + hr.getBatterLastName(), x1pos, y1pos += offset);
			g2d.drawString("Team: " + hr.getBatterTeam(), x1pos, y1pos += offset);
			g2d.drawString("Home Run Distance: " + Integer.toString(hr.getDistance()) + " ft", x1pos, y1pos += offset);
			
			//displayHomeRunInfo(g2d, diameter);
			notifyHomeRunObserversMouseOver();
		}
		*/

		/*
		 * We probably won't need this for BA..
		if (mouseOverStrikeZone == true){
			g2d.setFont(new Font("Serif", Font.BOLD, 20));
			int x1pos = getWidth() - getWidth()/4;
			int y1pos = getHeight()/25;
			int offset = 12;
			g2d.setColor(Color.yellow);
			g2d.fillOval(
					hr.getXpos() - (diameter / 2),
					hr.getYpos() - (diameter / 2),
					diameter, diameter);
			g2d.setColor(Color.black);
			g2d.drawString(hr.getBatterLastName(), hr.getXpos() + 15, hr.getYpos() + 5);
			g2d.drawString(Integer.toString(hr.getDistance()) + " ft", hr.getXpos() + 15, hr.getYpos() + 20);
			
			g2d.setFont(new Font("Serif", Font.BOLD, 14));
			g2d.drawString("Home Run Number: " + hr.getHomeRunId(), x1pos, y1pos);
			g2d.drawString("Batter: " + hr.getBatterFirstName() +" " + hr.getBatterLastName(), x1pos, y1pos += offset);
			g2d.drawString("Team: " + hr.getBatterTeam(), x1pos, y1pos += offset);
			g2d.drawString("Home Run Distance: " + Integer.toString(hr.getDistance()) + " ft", x1pos, y1pos += offset);
			/*
			g2d.setFont(new Font("Serif", Font.BOLD, 20));
			int x1pos = getWidth() - getWidth()/4;
			int y1pos = getHeight()/25;
			int offset = 12;
			g2d.setColor(Color.yellow);
			g2d.fillOval(
					hr.getXpos() - (diameter / 2),
					hr.getYpos() - (diameter / 2),
					diameter, diameter);
		}
		*/
		//displayHomeRunInfo(g2d, diameter);		
	}//End paint
	
	/*
		public void displayHomeRunInfo(Graphics2D g2d, int diameter){
			g2d.setFont(new Font("Serif", Font.BOLD, 20));
			int x1pos = getWidth() - getWidth()/4;
			int y1pos = getHeight()/25;
			int offset = 12;
			g2d.setColor(Color.yellow);
			g2d.fillOval(
					hr.getXpos() - (diameter / 2),
					hr.getYpos() - (diameter / 2),
					diameter, diameter);
			g2d.setColor(Color.black);
			g2d.drawString(hr.getBatterLastName(), hr.getXpos() + 15, hr.getYpos() + 5);
			g2d.drawString(Integer.toString(hr.getDistance()) + " ft", hr.getXpos() + 15, hr.getYpos() + 20);
			
			g2d.setFont(new Font("Serif", Font.BOLD, 14));
			g2d.drawString("Home Run Number: " + hr.getHomeRunId(), x1pos, y1pos);
			g2d.drawString("Batter: " + hr.getBatterFirstName() +" " + hr.getBatterLastName(), x1pos, y1pos += offset);
			g2d.drawString("Team: " + hr.getBatterTeam(), x1pos, y1pos += offset);
			g2d.drawString("Home Run Distance: " + Integer.toString(hr.getDistance()) + " ft", x1pos, y1pos += offset);
			
		}
	*/
		
	/**
	 *Function to determine if the mouse is over a home run.
	 */
	public void mouseOverDataPoint(int x, int y){
		boolean callRepaint = false;
		int delta = 5;
		//for (GPSDataPoint hr1: myHomeRunList){ //traverse arraylist
			//if(((x > (hr1.getXpos() - delta )) && (x < (hr1.getXpos() + delta))) &&
			//((y > (hr1.getYpos() - delta)) && (y < (hr1.getYpos() + delta)))){
				//mouseOverRepaintCount = 0;
				//callRepaint = true;
				//setMouseOverHomeRun(true);
				//hr = hr1;
				//break;
			//}
		//setMouseOverHomeRun(false);
		//}
		
		// If this is the first time the mouse is outside the 
		// mouse over area of a home run repaint the GUI to remove
		// the data that was displayed next to the home run that the
		// mouse position was on.
		if(mouseOverRepaintCount == 1){
		
			repaint();
			notifyHomeRunObservers();
		}
		// If the mouse is outside the boundaries of a home run
		// increment mouseOverRepaintCount so the image and home runs
		// are not repainted on every mouse movement update.
		mouseOverRepaintCount++;

		// If the mouse is over a home run repaint the image and 
		// home run data along with the added data the corresponds
		// to the home run the mouse is on.
		if (callRepaint){
			repaint();
		}
	}	
	
	/**
	 *Function: getImage
	 *Description: 
	 */
	public Image getImage(String path)
	{
		Image tempImage = null;
		try
		{
			java.net.URL imageURL = BattlefieldAirmanMapGraphicsSurface.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		}
		catch (Exception e)
		{
			System.out.println("An error occured - " + e.getMessage());
		}
		return tempImage;
	}
	
	/**
	 *Function: updateHomeRunGraphicsSurface
	 *Description: 
	 */
	@SuppressWarnings("unchecked")
	public void updateBattlefieldAirmanMapGraphicsSurface(ArrayList<GPSDataPoint> dataPointList){
		myDataPointList2 = dataPointList;
		myDataPointList = (ArrayList<GPSDataPoint>) dataPointList.clone();
		repaint();
	}
	
	/**
	 *Function: setMouseOverHomeRun
	 *Description: 
	 */
	public void setMouseOverHomeRun(boolean mouseOver){
		mouseOverDataPoint = mouseOver;
	}
	
	/**
	 *Function: isMouseOverHomeRun
	 *Description: 
	 */
	public boolean isMouseOverDataPoint(){
		if(mouseOverDataPoint){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 *Function: registerHomeRunObserver
	 *Description: 
	 */
	public void registerHomeRunObserver(DataPointObserver hrObserver){
		homeRunObservers.add(hrObserver);
	}
	

	/**
	 *Function: notifyHomeRunObserversMouseClick
	 *Description: 
	 */
	public void notifyHomeRunObserversMouseClicked(){
		for (DataPointObserver hrObserver: homeRunObservers){			
			hrObserver.update(myDataPointList2.indexOf(dp));
		}
	}

	/**
	 *Function: notifyHomeRunObserversMouseClick
	 *Description: 
	 */
	public void notifyHomeRunObserversMouseRightClicked(){
		for (DataPointObserver hrObserver: homeRunObservers){			
			hrObserver.update();
		}
	}

	/**
	 *Function: notifyHomeRunObserversMouseOver
	 *Description: 
	 */
	public void notifyHomeRunObserversMouseOver(){
		ArrayList<GPSDataPoint> hrList = new ArrayList<GPSDataPoint>();
		hrList.add(dp);
		for (DataPointObserver hrObserver: homeRunObservers){
			hrObserver.update(hrList);
		}
	}

	/**
	 *Function: notifyHomeRunObservers
	 *Description: 
	 */
	public void notifyHomeRunObservers(){
		ArrayList<GPSDataPoint> hrList = new ArrayList<GPSDataPoint>();
		hrList = myDataPointList;
		for (DataPointObserver hrObserver: homeRunObservers){
			hrObserver.update(hrList);
		}
	}
	
	/**
	 *Function: initializePhilliesSound
	 *Description: 
	 */
	public void initializePhilliesSound(){
		try {	
			String soundName = "baseball.wav";    
			audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			clip.open(audioInputStream);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	}//End intitializePhilliesSound
	
	/**
	 *Function: initializeVisitorSound
	 *Description: 
	 */
	public void initializeVisitorSound(){
		try {	
			String soundName = "baseball_boo.wav";    
			audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			clip.open(audioInputStream);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	}//End initializeVisitorSound
		
	/**
	 *Function: update
	 *Description: This function called by the 
	 *   PitchLocationGraphicsSurface to highlight
	 *   the home run on the field that matches the 
	 *   moused over home run in the pitch location 
	 *   window 
	 */
	@Override
	public void update(GPSDataPoint hr) {
		this.dp = hr;
		//mouseOverStrikeZone = true;	
		repaint();
	}


	/**
	 *Function: update
	 *Description: This function called by the 
	 *   PitchLocationGraphicsSurface to remove the highlighted
	 *   home run on the field that matched the home that was
	 *   previously moused over  
	 */
	@Override
	public void update() {
		//mouseOverStrikeZone = false;
		repaint();
	}
}//End class HomeRunGraphicsSurface
	


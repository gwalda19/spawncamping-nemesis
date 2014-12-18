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

/**
 *  BattlefieldAirmanMapGraphicsSurface
 *
 *  Draws the GPS location and the battlefield airman positions.
 *
 *  @author Emmanuel Bonilla
 *  @author Sean Fast
 *  @author David Gwalthney
 *  @author Michael Norris
 *  @author David Shanline
 *
 */
public class BattlefieldAirmanMapGraphicsSurface extends JComponent
{

	private static final long serialVersionUID = 1L;
	private int x1 = 0;
	private int y1 = 0;
	private int mouseOverRepaintCount = 0;
	private Image map = null;
	private boolean mouseOverDataPoint = false;
	//private boolean mouseOverStrikeZone = false;
	private ArrayList<GPSDataPoint> myDataPointList;
	private ArrayList<GPSDataPoint> myDataPointList2;// = new ArrayList<HomeRun>();
	private final ArrayList<DataPointObserver> dataPointObservers = new ArrayList<DataPointObserver>();
	private GPSDataPoint dp;
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
			@Override
      public void mouseDragged(MouseEvent e)
			{
				//x2 = e.getX();
				//y2 = e.getY();
			}

			@Override
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
					notifyDataPointObserversMouseRightClicked();
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
	 *  Function: paint
	 *  Description: paint gets called when ever there is a resizing of the screen
	 */
	@Override
  public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (map == null)
			map = getImage("basic_map.jpg");
		g2d.drawImage(map, 0, 0, 750, 740, this);

		// This is the diameter of the home run that is displayed
		int diameter = 10;
		int stream1DataPointCount = 0;
		int stream2DataPointCount = 0;
		int stream3DataPointCount = 0;
		//int hrAwayTeamCount = 0;

		for (GPSDataPoint dataPoint: myDataPointList){
			//Draw a red home run dot at x,y position
			if (dataPoint.getBoxId() == 1){
				g2d.setColor(Color.red);
				g2d.fillOval(
						dataPoint.getXPos() - (diameter / 2),
						dataPoint.getYPos() - (diameter / 2),
						diameter, diameter);
				stream1DataPointCount++;
			}
			else if (dataPoint.getBoxId() == 2){
				g2d.setColor(Color.blue);
				g2d.fillOval(
						dataPoint.getXPos() - (diameter / 2),
						dataPoint.getYPos() - (diameter / 2),
						diameter, diameter);
				stream2DataPointCount++;
			}
			else if (dataPoint.getBoxId() == 3){
					g2d.setColor(Color.green);
					g2d.fillOval(
							dataPoint.getXPos() - (diameter / 2),
							dataPoint.getYPos() - (diameter / 2),
							diameter, diameter);
					stream3DataPointCount++;
			}
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

		if (isMouseOverDataPoint()){
			g2d.setFont(new Font("Serif", Font.BOLD, 20));
			int x1pos = getWidth() - getWidth()/4;
			int y1pos = getHeight()/25;
			int offset = 12;
			g2d.setColor(Color.yellow);
			g2d.fillOval(
					dp.getXPos() - (diameter / 2),
					dp.getYPos() - (diameter / 2),
					diameter, diameter);
			g2d.setColor(Color.black);
			//g2d.drawString(dp.getBoxId(), dp.getXPos() + 15, dp.getYPos() + 5);
			//g2d.drawString(Integer.toString(dp.getDistance()) + " ft", dp.getXPos() + 15, dp.getYPos() + 20);

			g2d.setFont(new Font("Serif", Font.BOLD, 14));
			g2d.drawString("Box Number: " + dp.getBoxId(), x1pos, y1pos);
			g2d.drawString("Latitude: " + dp.getLatitude(), x1pos, y1pos += offset);
			g2d.drawString("Longitude: " + dp.getLongitude(), x1pos, y1pos += offset);
			g2d.drawString("Data Point Number: " + dp.getPointId(), x1pos, y1pos += offset);

			//displayHomeRunInfo(g2d, diameter);
			notifyDataPointObserversMouseOver();
		}

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
		for (GPSDataPoint dp1: myDataPointList){ //traverse arraylist
			if(((x > (dp1.getXPos() - delta )) && (x < (dp1.getXPos() + delta))) &&
			((y > (dp1.getYPos() - delta)) && (y < (dp1.getYPos() + delta)))){
				mouseOverRepaintCount = 0;
				callRepaint = true;
				setMouseOverDataPoint(true);
				dp = dp1;
				break;
			}
		setMouseOverDataPoint(false);
		}

		// If this is the first time the mouse is outside the
		// mouse over area of a data point repaint the GUI to remove
		// the data that was displayed next to the data point that the
		// mouse position was on.
		if(mouseOverRepaintCount == 1){

			repaint();
			notifyDataPointObservers();
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
	public void setMouseOverDataPoint(boolean mouseOver){
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
	public void registerDataPointObserver(DataPointObserver dpObserver){
		dataPointObservers.add(dpObserver);
	}


	/**
	 *Function: notifyHomeRunObserversMouseClick
	 *Description:
	 */
	public void notifyHomeRunObserversMouseClicked(){
		for (DataPointObserver hrObserver: dataPointObservers){
			hrObserver.update(myDataPointList2.indexOf(dp));
		}
	}

	/**
	 *Function: notifyHomeRunObserversMouseClick
	 *Description:
	 */
	public void notifyDataPointObserversMouseRightClicked(){
		for (DataPointObserver dpObserver: dataPointObservers){
			dpObserver.update();
		}
	}

	/**
	 *Function: notifyHomeRunObserversMouseOver
	 *Description:
	 */
	public void notifyDataPointObserversMouseOver(){
		ArrayList<GPSDataPoint> dpList = new ArrayList<GPSDataPoint>();
		dpList.add(dp);
		for (DataPointObserver dpObserver: dataPointObservers){
			dpObserver.update(dpList);
		}
	}

	/**
	 *Function: notifyHomeRunObservers
	 *Description:
	 */
	public void notifyDataPointObservers(){
		ArrayList<GPSDataPoint> dpList = new ArrayList<GPSDataPoint>();
		dpList = myDataPointList;
		for (DataPointObserver dpObserver: dataPointObservers){
			dpObserver.update(dpList);
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

}//End class HomeRunGraphicsSurface



package battlefieldAirmanPlayer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.ListIterator;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;


//Class PitchLocationGraphicsSurface
public class PitchLocationGraphicsSurface extends JComponent implements HomeRunObserver
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<StrikeZoneObserver> strikeZoneObservers = new ArrayList<StrikeZoneObserver>();
	private Image zone = null;
	private ArrayList<HomeRun> homeRunList;
	private boolean mouseOverHomeRun = false;
	private int mouseOverRepaintCount = 0;
	private HomeRun homeRun;
	private HomeRun hr;
	private int x1;
	private int y1;
    private float xoffsetMouseHr = 0;
    private float zoffsetMouseHr = 0;


	public PitchLocationGraphicsSurface(ArrayList<HomeRun> homeRunList)
	{
		this.homeRunList = homeRunList;
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
				//System.out.println(x1 + " " + y1);
				mouseOverHomeRun(x1,y1);

			}
		});	
	}//End PitchLocationGraphicsSurface Constructor

	/**mouseOverHomeRun
	 *Function to determine if the mouse is over a home run.
	 */
	public void mouseOverHomeRun(int x, int y){
		boolean callRepaint = false;
		int xposition = 199;
		int zposition = 155;
        
        
		int delta = 3;
		ListIterator<HomeRun> iter = homeRunList.listIterator();
		while (iter.hasNext()) {
			hr = iter.next();
			
			double xpitch = (-1 * hr.getPitchXPos());
			double zpitch = (hr.getPitchZPos() - 2.5);
			xoffsetMouseHr = ((xposition + (float)(xpitch * 68)));
			zoffsetMouseHr = (zposition + (float)(zpitch * -32 ));			
			
			if(((x > (xoffsetMouseHr - delta )) && (x < (xoffsetMouseHr + delta))) &&
			((y > (zoffsetMouseHr /*- delta*/)) && (y < (zoffsetMouseHr + delta + 2
					)))){
				mouseOverRepaintCount = 0;
				callRepaint = true;
				setMouseOverHomeRun(true);
				notifyStrikeZoneObserversMouseOver();
				break;
			}
			setMouseOverHomeRun(false);
		}
		
		// If this is the first time the mouse is outside the 
		// mouse over area of a home run repaint the GUI to remove
		// the data that was displayed next to the home run that the
		// mouse position was on.
		if(mouseOverRepaintCount == 1){
		
			repaint();
			notifyStrikeZoneObservers();
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
	 *Function: setMouseOverHomeRun
	 *Description: 
	 */
	public void setMouseOverHomeRun(boolean mouseOver){
		mouseOverHomeRun = mouseOver;
	}
	
	/**
	 *Function: isMouseOverHomeRun
	 *Description: 
	 */
	public boolean isMouseOverHomeRun(){
		if(mouseOverHomeRun){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 *Function: notifyStrikeZoneObserversMouseOver
	 *Description: 
	 */
	public void notifyStrikeZoneObserversMouseOver(){
		for (StrikeZoneObserver strikeZoneObserver: strikeZoneObservers)  {
			strikeZoneObserver.update(hr);
		}
	}

	/**
	 *Function: notifyStrikeZoneObservers
	 */
	public void notifyStrikeZoneObservers(){
		for (StrikeZoneObserver strikeZoneObserver: strikeZoneObservers)  {
			strikeZoneObserver.update();
		}
	}
		
	//MB made changes to getImage, drawImage, diameter, if statement around setColor, changed fillOval to fill to handle floats.
	//Displays the pitch locator picture and all pitch locations
	public void paint(Graphics g)
	{
	    boolean left_handed = false;
	    boolean right_handed = false;
	    
	    Graphics2D g2d = (Graphics2D) g;	
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setFont(new Font("Serif", Font.BOLD, 20));			
		
		ListIterator<HomeRun> stance_iter = homeRunList.listIterator();
		while (stance_iter.hasNext()) 
		{
			homeRun = stance_iter.next();
		
			if (homeRun.getStance().equals("Left"))
			{
				left_handed = true;
			}
			else 
			{
				right_handed = true;
			}
			
		}
		
		//Determines if all painted home runs are from the same batter stance
		//If so display left or right handed batter
		//If not display basic image
		if (right_handed == true && left_handed == false)
		{
			zone = getImage("SZ_right_400.jpg");
			g2d.drawImage(zone, 0, 0, 400, 266, this);
		}
		else if (right_handed == false && left_handed == true)
		{

			zone = getImage("SZ_left_400.jpg");
			g2d.drawImage(zone, 0, 0, 400, 266, this);
		}	
		else
		{
			zone = getImage("SZ_blank_400.jpg");
			g2d.drawImage(zone, 0, 0, 400, 266, this);
		}
		
		// This is the diameter of the ellipse
		int diameter = 5;
		int xposition = 199;
		int zposition = 155;
        float xoffset = 0;
        float zoffset = 0;
        
		//Traverse home run list and use the below algorithm to determine
		//the exact pitch location
		ListIterator<HomeRun> iter = homeRunList.listIterator();
		while (iter.hasNext()) {
			homeRun = iter.next();
			double xpitch = (-1 * homeRun.getPitchXPos());
			double zpitch = (homeRun.getPitchZPos() - 2.5);
			xoffset = ((xposition + (float)(xpitch * 68)));
			zoffset = (zposition + (float)(zpitch * -32 ));

			if (homeRun.getBatterTeam().equals("Phillies")){
			g2d.setColor(Color.red);
			}
			else{
				g2d.setColor(Color.blue);
			}
			
			g2d.fill(new Ellipse2D.Float((float) (xoffset), (float) ((zoffset)),	diameter, diameter));
		}
				
		//If a home run is moused over highlight it in green
		if (isMouseOverHomeRun()){		
			diameter = 8;
			g2d.setColor(Color.green);
			g2d.fill(new Ellipse2D.Float((float) (xoffsetMouseHr), (float) ((zoffsetMouseHr)), diameter, diameter));
		}			
	}//End paint
	
	public Image getImage(String path)
	{
		Image tempImage = null;
		try
		{
			java.net.URL imageURL = PitchLocationGraphicsSurface.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		}
		catch (Exception e)
		{
			System.out.println("An error occured - " + e.getMessage());
		}
		return tempImage;
	}
	
	public void updatePitchLocationGraphicsSurface(ArrayList<HomeRun> homeRunList){
		this.homeRunList = homeRunList;
		repaint();
	}
	
	public void registerStrikeZoneObserver(StrikeZoneObserver strikeZoneObserver){
		strikeZoneObservers.add(strikeZoneObserver);
	}

	@Override
	public void update() {
		repaint();
	}

	@Override
	public void update(ArrayList<HomeRun> hrList) {
		this.homeRunList = hrList;
		repaint();
	}

	@Override
	public void update(int hrIndex) {
		// TODO Auto-generated method stub
		
	}
	
}

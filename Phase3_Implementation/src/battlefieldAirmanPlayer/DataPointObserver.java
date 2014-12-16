package battlefieldAirmanPlayer;

import java.util.ArrayList;

public interface DataPointObserver {
	public void update();
	public void update(int dataPointIndex);
	public void update(ArrayList<GPSDataPoint> dataPointListArrayList);
}

package homeRunStats;

import java.util.ArrayList;

public interface HomeRunObserver {
	public void update();
	public void update(int hrIndex);
	public void update(ArrayList<HomeRun> homeRunListArrayList);
}

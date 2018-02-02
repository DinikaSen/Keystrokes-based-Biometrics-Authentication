import java.util.ArrayList;


public class Verification {

	ArrayList<Double> statistics = null;
	
	public Verification(){
		statistics = new ArrayList<Double>();
	}
	
	public void addEntry(double stdDev){
		statistics.add(stdDev);
	}
	
	public double getEntry(int index){
		return statistics.get(index);
	}
}

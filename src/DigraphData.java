import java.util.ArrayList;


public class DigraphData {
	
	public static ArrayList<Digraph> constructDigraphs(String phrase, ArrayList<Character> chars){
		
		ArrayList<Digraph> digraphs = new ArrayList<Digraph>();
		for(int i = 0 ; i<phrase.trim().length()-1; i++){
			Digraph digraphHold = new Digraph(phrase.charAt(i),'^',0);
			Digraph digraphRnP = new Digraph(phrase.charAt(i),phrase.charAt(i+1),0);
			digraphs.add(digraphHold);
			digraphs.add(digraphRnP);
		}
		digraphs.add(new Digraph(phrase.charAt(phrase.trim().length()-1),'^',0));
		return computeLatency(digraphs,chars);
	}
	
	private static ArrayList<Digraph> computeLatency(ArrayList<Digraph> digraphs, ArrayList<Character> chars) {
		int count = 1;
		for(int i = 0 ; i < digraphs.size() ; i++){
			if(i%2 == 0){
				long latency = chars.get(i/2).getReleaseTime() - chars.get(i/2).getPressTime() ;
				digraphs.get(i).setLatency(latency);
			} else {
				long latency = chars.get(i-count+1).getPressTime() - chars.get(i-count).getPressTime();
				digraphs.get(i).setLatency(latency);
				count++;
			}
		}
		return digraphs;
	}
	
	
	public static UserString constructStringProfile(ArrayList<ArrayList<Digraph>> trainingData){
		
		UserString profile = new UserString();
		for(int i = 0; i < trainingData.get(0).size(); i++){
			UserStringData entry = new UserStringData();
			entry.setDigraph(trainingData.get(0).get(i));
			double meanLatency = 0;
			double sumOfX = 0;
			double sumOfXSquared = 0;
			
			for(int j = 0 ; j < trainingData.size(); j++){
				sumOfX += trainingData.get(j).get(i).getLatency();
				sumOfXSquared += Math.pow(trainingData.get(j).get(i).getLatency(), 2);
			}
			
			entry.setSumOfX(sumOfX);
			meanLatency = sumOfX / Defaults.getDefaultAttempts();
			entry.setMeanLatency(meanLatency);
			entry.setSumOfX(sumOfX);
			entry.setSumOfSquaredX(sumOfXSquared);
			
			profile.addProfileEntry(entry);
		}
		
		return profile;
	}
}


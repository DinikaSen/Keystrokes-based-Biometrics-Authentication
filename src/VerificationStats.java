import java.util.ArrayList;


public class VerificationStats {

	public Verification generateVerificationStatistics(User user, UserString profile, ArrayList<Digraph> digraphs){
		
		Verification statistics = new Verification();
		
		for(int i = 0; i < digraphs.size(); i++){
			
			double meanLetancy = profile.getStringProfileEntries().get(i).getMeanLatency();
			double sumOfX = profile.getStringProfileEntries().get(i).getSumOfX();
			double sumOfXSquared = profile.getStringProfileEntries().get(i).getSumOfSquaredX();
			int logins = user.getLoginNo();
			double stdDev = Math.sqrt((sumOfXSquared/logins)-(Math.pow((sumOfX/logins), 2)));
			Verification verificationEntry  = new Verification();
			verificationEntry.addEntry(stdDev);
			statistics.addEntry(stdDev);
		}
		return statistics;
	}
	
	public  boolean verify(User user, UserString profile, ArrayList<Digraph> digraphs){
	
		Verification stats =  generateVerificationStatistics(user, profile, digraphs);
		int match = 0;
		int outOf = 0;
		for(int i = 0; i < digraphs.size(); i++){
			double meanLetancy = profile.getStringProfileEntries().get(i).getMeanLatency();
			double digraphLetancy = digraphs.get(i).getLatency();
			double stdDev = stats.getEntry(i);
			outOf++;
            if((digraphLetancy >= (meanLetancy - stdDev)) && (digraphLetancy <= (meanLetancy + stdDev))){
				match++;
                System.out.println("meanLatency = "+meanLetancy+" digraphLatency = "+digraphLetancy+" allowed deviation = "+stdDev+" actual = "+(meanLetancy-digraphLetancy)+" MATCH");
			}	else{
                System.out.println("meanLatency = "+meanLetancy+" digraphLatency = "+digraphLetancy+" allowed deviation = "+stdDev+" actual = "+(meanLetancy-digraphLetancy)+" NOT");
            }
		}
		float matches=match;
		System.out.println("matched "+matches+" out of "+outOf);
		System.out.println(matches / (digraphs.size()));
		if ((matches / (digraphs.size())) > 0.50)
			return true;
		return false;
	}
	
}

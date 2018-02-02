import java.util.ArrayList;


public class UserString {
	
private ArrayList<UserStringData> userString = null;
	
	
	public UserString(){
		userString = new ArrayList<UserStringData>();
	}

	public ArrayList<UserStringData> getStringProfileEntries() {
		return userString;
	}
	
	public void setStringProfileEntries(ArrayList<UserStringData> profileEntries) {
		this.userString = profileEntries;
	}
	
	public void addProfileEntry(UserStringData entry ){ 		
		userString.add(entry);
	}
}

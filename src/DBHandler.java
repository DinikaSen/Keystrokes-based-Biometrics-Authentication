import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DBHandler {

	public static boolean createUserTable(){
		Connection con = SqliteConnection.getDBConnection();
		Statement stat = null;

		try {
			stat = con.createStatement();
			String query = "CREATE  TABLE IF NOT EXISTS \"main\".\"USER\" (\"ID\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , \"USERNAME\" VARCHAR(50) NOT NULL , \"PASSWORD\" VARCHAR(50) NOT NULL , \"LOGIN_NO\" INTEGER NOT NULL  DEFAULT 5)";
			stat.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean createProfileTable(){
		Connection con = SqliteConnection.getDBConnection();
		Statement stat = null;

		try {
			stat = con.createStatement();
			String query = "CREATE  TABLE  IF NOT EXISTS \"main\".\"PROFILE\" (\"ID\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , \"USER_ID\" INTEGER NOT NULL , \"DIGRAPH\" VARCHAR(50) NOT NULL , \"LATENCY_MEAN\" DOUBLE NOT NULL , \"SUM_OF_X\" DOUBLE NOT NULL , \"SUM_OF_SQUARED_X\" DOUBLE NOT NULL)";
			stat.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static User findByUsername(String userName){
		
		Connection con = SqliteConnection.getDBConnection();
		Statement stat = null;
		ResultSet rs = null;
		User user = null;
	
		try {		 
			stat = con.createStatement();
			String query = "SELECT * FROM USER WHERE USERNAME ='" + userName + "'";
			rs = stat.executeQuery(query);
			while(rs.next()){
				String username = rs.getString("USERNAME");
				String password = rs.getString("PASSWORD");
				int id = rs.getInt("ID");
				int loginNo = rs.getInt("login_no");	
				user = new User();
				user.setId(id);
				user.setUserName(username);
				user.setPassword(password);
				user.setLoginNo(loginNo);
			}
			if(user == null){
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public static boolean insertNewUser(User user){
		String userName = user.getUserName();
		String password = user.getPassword();
		Connection con = SqliteConnection.getDBConnection();
		Statement stat = null;
		int result = 0;
		
		try {
			stat = con.createStatement();
			String query = "INSERT INTO USER (USERNAME,PASSWORD) VALUES('" + userName + "','" + password + "')";
			result = stat.executeUpdate(query);
			if (result == 1) {
				ResultSet rs = stat.executeQuery("SELECT ID FROM USER WHERE USERNAME = '"+ userName + "'");
				while (rs.next())
					user.setId(rs.getInt("ID"));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean insertUserSignatureProfile(User user){
        UserSignature userProfile = user.getProfile();
        UserString stPhraseProfile = userProfile.getPhraseProfile();
		boolean r = insertStringProfile(stPhraseProfile, user.getId());
		if(r)
			return true;
		return false;
	}
	
	private static boolean insertStringProfile(UserString profile, int userId){
		
		Connection con = SqliteConnection.getDBConnection();
		Statement stat = null;
		int result = 0;
		
		try {
			for(int i = 0 ; i <profile.getStringProfileEntries().size(); i ++){
				
				double meanLatency = profile.getStringProfileEntries().get(i).getMeanLatency();
				double sumOfX = profile.getStringProfileEntries().get(i).getSumOfX();
				double sumOfXSq = profile.getStringProfileEntries().get(i).getSumOfSquaredX();
				String digraphDataString = "";
				if (profile.getStringProfileEntries().get(i).getDigraph().getCharTwo() != '^'){
					digraphDataString += profile.getStringProfileEntries().get(i).getDigraph().getCharOne()+"-"+
								profile.getStringProfileEntries().get(i).getDigraph().getCharTwo();
				} else
					digraphDataString += profile.getStringProfileEntries().get(i).getDigraph().getCharOne();
				
				  
				String query = "INSERT INTO PROFILE (USER_ID,DIGRAPH,LATENCY_MEAN,SUM_OF_X,SUM_OF_SQUARED_X) " +
						"VALUES('" + userId + "','" + digraphDataString + "','" + meanLatency + "','" + sumOfX + "','" + sumOfXSq + "')";
				stat = con.createStatement();
				result = stat.executeUpdate(query);
				if(result != 1)
					return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}

	public static UserSignature loadUserSignatureProfile(User user){
		
		UserSignature profile = new UserSignature();
		profile.setPhraseProfile(getStringProfile(user.getId()));
		return profile;
	}
	
	private static UserString getStringProfile(int userID) {
		
		UserString strProfile = new UserString();
		
		Connection con = SqliteConnection.getDBConnection();
		Statement stat = null;
		ResultSet rs = null;
		
		try {
				  
			String query = "SELECT * FROM PROFILE WHERE USER_ID = '"+userID+"' ORDER BY ID ASC";
						
			stat = con.createStatement();
			rs = stat.executeQuery(query);
			
			while(rs.next()){
				
				UserStringData entry = new UserStringData();
				
				String digraphStr = rs.getString("DIGRAPH");
				entry.setId(rs.getInt("ID"));
				entry.setDigraph(constructDigraph(digraphStr));
				entry.setMeanLatency(rs.getDouble("LATENCY_MEAN"));
				entry.setSumOfX(rs.getDouble("SUM_OF_X"));
				entry.setSumOfSquaredX(rs.getDouble("SUM_OF_SQUARED_X"));
				
				strProfile.addProfileEntry(entry);
				
				System.out.println(rs.getInt("ID") + "  "+entry);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return strProfile;
	}
	
	private static Digraph constructDigraph(String digraphStr){
		
		Digraph digraph = null;
		
		if(digraphStr.length() == 1)
			return new Digraph(digraphStr.charAt(0),'^');
		else if(digraphStr.length() == 3)
			return new Digraph(digraphStr.charAt(0),digraphStr.charAt(2));
		
		return null;
	}
}

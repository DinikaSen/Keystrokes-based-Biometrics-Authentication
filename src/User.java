
public class User {

	private int id ;
	private String userName;
	private String password;
	
	private int loginNo;
	private UserSignature profile;
		
	public User(){};
	
	public int getLoginNo() {
		return loginNo;
	}

	public void setLoginNo(int loginNo) {
		this.loginNo = loginNo;
	}
	
	public User(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName(){
		return userName;
	}
	
	public void setUserName(String userName){
		this.userName= userName;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public UserSignature getProfile(){
		return profile;
	}
	
	public void setProfile(UserSignature profile){
		this.profile = profile;
	}
}

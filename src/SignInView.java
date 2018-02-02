import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class SignInView {

	private JFrame frame;
	private JTextField username;
	private JPasswordField pswd;
	private JTextField typing;
	
	private int phraseCount1 = 0, phraseCount2 = 0;
	public ArrayList<Character> phraseCharacters = new ArrayList<Character>();

	/**
	 * Launch the application.
	 */
	public static void showSignIn() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignInView window = new SignInView();
					window.frame.setVisible(true);
					window.frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SignInView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 361, 315);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblUsername = new JLabel("Username ");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblUsername.setBounds(52, 72, 76, 40);
		frame.getContentPane().add(lblUsername);
		
		username = new JTextField();
		username.setColumns(10);
		username.setBounds(126, 80, 187, 27);
		frame.getContentPane().add(username);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPassword.setBounds(52, 118, 65, 27);
		frame.getContentPane().add(lblPassword);
		
		pswd = new JPasswordField();
		pswd.setColumns(10);
		pswd.setBounds(126, 118, 187, 27);
		frame.getContentPane().add(pswd);
		
		JLabel label_2 = new JLabel("Keystroke Dynamics based User Authentication ");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_2.setBounds(10, 19, 335, 50);
		frame.getContentPane().add(label_2);
		
		JLabel label = new JLabel("Type the following phrase");
		label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label.setBounds(52, 156, 204, 14);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("\""+Defaults.getDefaultPhrase()+"\"");
		label_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		label_1.setBounds(55, 172, 242, 25);
		frame.getContentPane().add(label_1);
		
		typing = new JTextField();
		typing.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ev) {
				char ch = ev.getKeyChar();
				if(ch==KeyEvent.VK_ENTER){
					String userName = username.getText();
					String password = pswd.getText();
					String stPhrase = typing.getText();

					if(verifyFormTextFields()){
                        User user = DBHandler.findByUsername(userName);

						if(user == null)
							retryAgain("Username does not exist..!!");
						else {
							if(verifyUserCredentials(user))
							{
								ArrayList<Digraph> stPhraseDigraphs = DigraphData.constructDigraphs(Defaults.getDefaultPhrase(), phraseCharacters);

								UserSignature userProfile = DBHandler.loadUserSignatureProfile(user);

								VerificationStats handler = new VerificationStats();

								boolean valid = handler.verify(user, userProfile.getPhraseProfile(), stPhraseDigraphs);

								if(valid){
									JOptionPane.showMessageDialog(null, "Keystroke characteristics matched\nYou are successfully logged in..!!");
									frame.setVisible(false);
									new MainView().displayMainView();
								}else{
									retryAgain("Keystroke characteristics do not match\nTry again..!!");
								}
							}
						}
					}

				}
				if(ev.getKeyCode() == 8 || ev.getKeyCode() == 46){
					retryAgain("You are not allowed to use delete or backspace\nPlease re-enter phrase");
					return;
				}
				if (phraseCount1 <= Defaults.getDefaultPhrase().length()) {
					Character c = new Character(ch);
					c.setPressTime(ev.getWhen());
					phraseCharacters.add(c);
					System.out.println(ch + "  press" + ev.getWhen());
					phraseCount1++;
				} else {
					retryAgain("Phrase length exceeded.\nTry again..!!");
					return;
					}
				}
			@Override
			public void keyReleased(KeyEvent ev) {
				char ch = ev.getKeyChar();
				if (ch == Defaults.getDefaultPhrase().charAt(phraseCount2)) {
					System.out.println(ch + "  release" + ev.getWhen());
					phraseCharacters.get(phraseCount2).setReleaseTime(ev.getWhen());
					phraseCount2++;
					}
				}
			});
		typing.setColumns(10);
		typing.setBounds(35, 200, 278, 27);
		frame.getContentPane().add(typing);
		
		JButton btnSignIn = new JButton("Sign In");
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String userName = username.getText();
				String password = pswd.getText();
				String stPhrase = typing.getText();
				
				if(verifyFormTextFields()){
					
					User user = DBHandler.findByUsername(userName);
					
					if(user == null)
						retryAgain("Username does not exist..!!");
					else {
						if(verifyUserCredentials(user))
						{
							ArrayList<Digraph> stPhraseDigraphs = DigraphData.constructDigraphs(Defaults.getDefaultPhrase(), phraseCharacters);
							
							UserSignature userProfile = DBHandler.loadUserSignatureProfile(user);
							
							VerificationStats handler = new VerificationStats();
				
							boolean valid = handler.verify(user, userProfile.getPhraseProfile(), stPhraseDigraphs);
							
							if(valid){
								user.setLoginNo(user.getLoginNo() + 1);
								JOptionPane.showMessageDialog(null, "Keystroke characteristics matched\nYou are successfully logged in..!!");
								frame.setVisible(false);
								new MainView().displayMainView();
							}else{
								retryAgain("Keystroke characteristics do not match\nTry again..!!");
							}
						}
					}
				}
			}
		});
		
		btnSignIn.setBounds(73, 238, 89, 23);
		frame.getContentPane().add(btnSignIn);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
                new MainView().displayMainView();
			}
		});
		btnCancel.setBounds(182, 238, 89, 23);
		frame.getContentPane().add(btnCancel);
	}
	

	private void retryAgain(String message){
		JOptionPane.showConfirmDialog(null, message, "Error", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
		frame.setVisible(false);
		(new SignInView()).showSignIn();
	}
	
	private boolean verifyFormTextFields() {
		
		String userName = username.getText();
		String password = pswd.getText();
		String stPhrase = typing.getText();
		
		boolean flag = false;
		
		if(userName != null && userName.trim().length()>0){
			
			if(password != null && password.trim().length()>0){
				if(stPhrase != null && stPhrase.trim().length()>0){
					flag = true;
				} else {
					retryAgain("Enter phrase.");
					flag = false;
				}
				
			} else {
				retryAgain("Enter password");
				flag = false;
			}
			
		} else{
			
			retryAgain("Enter Username.");
			flag = false;
		}
	
		return flag ;
	}
	
	private boolean verifyUserCredentials(User user){
		
		if(user == null)
			return false;
		
		if(!user.getPassword().equals(pswd.getText())) {
			retryAgain("The password is incorrect, please retry again.");
			return false;
		}
		
		if(!user.getUserName().equals(username.getText())) {
			retryAgain("The Username is incorrect, please retry again.");
			return false;
		}

		if(!(Defaults.getDefaultPhrase()).equals(typing.getText())){
			retryAgain("The phrase is incorrect, please type the given text.");
			return false ;
		}
			
		return true;
	}

}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class TrainView {

	private JFrame frame;
	private JTextField phraseField;
	private User user = null;
	 
	private ArrayList<Character> phraseChars = null;
	
	private int phraseCount1 = 0, phraseCount2 = 0;
	
	private static ArrayList<ArrayList<Digraph>> phraseTrainingData = new ArrayList<ArrayList<Digraph>>();
	
	private static int attempts = 1;

	/**
	 * Launch the application.
	 */
	public void displayView() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrainView window = new TrainView(user);
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
	public TrainView(User user) {
		initialize();
		this.user = user;
		
		phraseChars = new ArrayList<Character>();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 350, 215);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Training Attempt "+attempts);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(97, 11, 200, 25);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblTypeTheFollowing = new JLabel("Type the following phrase");
		lblTypeTheFollowing.setBounds(10, 42, 194, 14);
		frame.getContentPane().add(lblTypeTheFollowing);
		
		JLabel lblthisIsTo = new JLabel("\""+Defaults.getDefaultPhrase()+"\"");
		lblthisIsTo.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		lblthisIsTo.setBounds(55, 63, 242, 25);
		frame.getContentPane().add(lblthisIsTo);
		
		phraseField = new JTextField();
		phraseField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ev) {
				char ch = ev.getKeyChar();
				
				if(ev.getKeyCode() == 8 || ev.getKeyCode() == 46){
					retryAgain("You are not allowed to use delete or backspace.\nRetry this attempt..!!");
					return;
				}
				else if (phraseCount1 < Defaults.getDefaultPhrase().length()) {
					if (ch == Defaults.getDefaultPhrase().charAt(phraseCount1)) {
						Character c = new Character(ch);
						c.setPressTime(ev.getWhen());
						phraseChars.add(c);
						phraseCount1++;

					} else {
						retryAgain("Invalid character..!\nRetry this training attempt");
						return;
					}
				} else {
					retryAgain("Invalid phrase..!\nRetry this training attempt");
					return;	
				}
			}
			@Override
			public void keyReleased(KeyEvent ev) {
				char ch = ev.getKeyChar();
				if(ch == Defaults.getDefaultPhrase().charAt(phraseCount2))
				{
					phraseChars.get(phraseCount2).setReleaseTime(ev.getWhen());
					phraseCount2++;
				}
			}
		});
		
		phraseField.setBounds(20, 91, 293, 32);
		frame.getContentPane().add(phraseField);
		phraseField.setColumns(10);
		
		final JButton btnNext;
		btnNext = new JButton();
		btnNext.setText("Next");
		btnNext.setBounds(64, 134, 89, 23);
		frame.getContentPane().add(btnNext);
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isValidPhrase()) {
					phraseTrainingData.add(DigraphData.constructDigraphs(Defaults.getDefaultPhrase(),phraseChars));
					if (attempts < Defaults.getDefaultAttempts()){
						attempts++;
						frame.setVisible(false);
						(new TrainView(user)).displayView();
					}
					else {
						UserString phraseProfile = DigraphData.constructStringProfile(phraseTrainingData);
						UserSignature userProfile = new UserSignature();
						userProfile.setPhraseProfile(phraseProfile);
						user.setProfile(userProfile);
						user.setLoginNo(5);

						if (DBHandler.insertNewUser(user) && DBHandler.insertUserSignatureProfile(user)){
							JOptionPane.showMessageDialog(null, "Your typing pattern is recorded successfully.");
							frame.setVisible(false);
							MainView window = new MainView();
							window.displayMainView();
						} else {}
						}
					} else {
						JOptionPane.showConfirmDialog(null, "You entered an invalid character.\n Correct Phrase : "+Defaults.getDefaultPhrase()+"\nRetry this attempt","Error", JOptionPane.CLOSED_OPTION,JOptionPane.ERROR_MESSAGE);
						frame.setVisible(false);
						(new TrainView(user)).displayView();
					}}} );
				
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				attempts=1;
				frame.setVisible(false);
				new MainView().displayMainView();
			}
		});
		btnCancel.setBounds(163, 134, 89, 23);
		frame.getContentPane().add(btnCancel);
	}

	private boolean isValidPhrase(){
		
		if(phraseField.getText() == null || phraseField.getText().trim().length() == 0 || (!phraseField.getText().equals(Defaults.getDefaultPhrase()))){
			return false;
		}
		
		return true;
	}
	
	private void retryAgain(String message){
		JOptionPane.showConfirmDialog(null,
		message, "Error",
		JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
		frame.setVisible(false);
		(new TrainView(user)).displayView();
	}
}



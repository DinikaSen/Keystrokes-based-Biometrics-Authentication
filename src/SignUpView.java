import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SignUpView {

	private JFrame frame;
	private JTextField textUname;
	private JPasswordField textPassword;
	private JPasswordField textRePassword;

	/**
	 * Launch the application.
	 */
	public static void newSignUpView() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUpView window = new SignUpView();
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
	public SignUpView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 331);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblTopic = new JLabel("Keystroke Dynamics based User Authentication ");
		lblTopic.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTopic.setBounds(68, 11, 335, 50);
		frame.getContentPane().add(lblTopic);
		
		JLabel lblUsername = new JLabel("Username (5-10 characters)");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblUsername.setBounds(41, 99, 165, 40);
		frame.getContentPane().add(lblUsername);
		
		textUname = new JTextField();
		textUname.setBounds(216, 107, 187, 27);
		frame.getContentPane().add(textUname);
		textUname.setColumns(10);
		
		JLabel lblCreateNewAccount = new JLabel("Create New Account");
		lblCreateNewAccount.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCreateNewAccount.setBounds(41, 59, 200, 50);
		frame.getContentPane().add(lblCreateNewAccount);
		
		JLabel labelPW = new JLabel("Password (8-15 characters)");
		labelPW.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelPW.setBounds(41, 144, 165, 27);
		frame.getContentPane().add(labelPW);
		
		textPassword = new JPasswordField();
		textPassword.setColumns(10);
		textPassword.setBounds(216, 145, 187, 27);
		frame.getContentPane().add(textPassword);
		
		JLabel lblReenterPassword = new JLabel("Re-enter Password");
		lblReenterPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblReenterPassword.setBounds(41, 182, 165, 27);
		frame.getContentPane().add(lblReenterPassword);
		
		textRePassword = new JPasswordField();
		textRePassword.setColumns(10);
		textRePassword.setBounds(216, 182, 187, 27);
		frame.getContentPane().add(textRePassword);
		
		JButton toTraining = new JButton("Proceed");
		toTraining.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(textUname.getText().trim().equals("") || textPassword.getText().trim().equals("") ||
						textRePassword.getText().trim().equals("")){
					
					JOptionPane.showConfirmDialog(null, "Please fill all required fields","Error",JOptionPane.CLOSED_OPTION,JOptionPane.ERROR_MESSAGE);
				}
				else if(validateFieldSizes()== false){
					
				}
				else if(!textPassword.getText().equals(textRePassword.getText())){
					JOptionPane.showConfirmDialog(null, "Re-entered password does not match..!!, \n Please type it again correctly.","Error",JOptionPane.CLOSED_OPTION,JOptionPane.ERROR_MESSAGE);
					textPassword.setText("");
					textRePassword.setText("");
					textPassword.requestFocus();
				}
				else {
					if(DBHandler.findByUsername(textUname.getText())== null){
						
						User user = new User(textUname.getText(),textPassword.getText());
						JOptionPane.showMessageDialog(null,"You are now in the training mode.\nPlease re-enter the given text correctly for "+Defaults.getDefaultAttempts()+" times.\n"+
									"Feel free to type in your normal way of typing.\nDo not change your normal behaviour.\nYour unique typing signature will be recorded to authenticate you when you sign in again.");
                        frame.setVisible(false);
                        TrainView tw = new TrainView(user);
                        tw.displayView();
					} else {
						
						JOptionPane.showMessageDialog(null,"Username already exists..!!\nTry a different username","Kamosi Message",JOptionPane.OK_OPTION);
	                    textUname.setText("");
	                    textPassword.setText("");
	                    textRePassword.setText("");
	                    textUname.requestFocus();
					}
					
				}
			}});
		toTraining.setBounds(219, 258, 119, 23);
		frame.getContentPane().add(toTraining);
		
		JLabel lblNewLabel = new JLabel("Click on 'Proceed' to enter training mode to save your typing pattern");
		lblNewLabel.setBounds(23, 209, 401, 50);
		frame.getContentPane().add(lblNewLabel);

		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    frame.setVisible(false);
				new MainView().displayMainView();
			}
		});
		btnNewButton.setBounds(91, 258, 119, 23);
		frame.getContentPane().add(btnNewButton);
	}
	
private boolean validateFieldSizes(){
		
		if(textUname.getText().length()<5 || textUname.getText().length() > 10){
			JOptionPane.showConfirmDialog(null, "Username should have 5-10 characters","Error",JOptionPane.CLOSED_OPTION,JOptionPane.ERROR_MESSAGE);
            textUname.setText("");
			textUname.requestFocus();
			return false;
		}			
		
		if(textPassword.getText().length() < 8 || textPassword.getText().length() > 15){
			JOptionPane.showConfirmDialog(null, "Password should have 8-15 characters","Error",JOptionPane.CLOSED_OPTION,JOptionPane.ERROR_MESSAGE);
			textPassword.setText("");
			textRePassword.setText("");
			textPassword.requestFocus();
			
			return false;
		}
			
		return true;
	}
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainView{

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public void displayMainView() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView window = new MainView();
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
	public MainView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 247);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, 0, 434, 208);
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SignUpView newView= new SignUpView();
				newView.newSignUpView();
				frame.setVisible(false);
			}
		});
		btnSignUp.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSignUp.setBackground(Color.LIGHT_GRAY);
		btnSignUp.setBounds(155, 132, 104, 30);
		frame.getContentPane().add(btnSignUp);
		
		JButton btnNewButton = new JButton("Sign In");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SignInView newView= new SignInView();
				newView.showSignIn();
				frame.setVisible(false);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBackground(Color.LIGHT_GRAY);
		btnNewButton.setBounds(155, 83, 104, 30);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblTopic = new JLabel("Keystroke Dynamics based User Authentication ");
		lblTopic.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTopic.setBounds(21, 11, 390, 50);
		frame.getContentPane().add(lblTopic);
		lblNewLabel.setIcon(new ImageIcon("keystrokes.jpg"));
		frame.getContentPane().add(lblNewLabel);
			
	}
	
}

package pollutionproject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginView extends JFrame {
	private JButton btnLogin;
	private JButton btnInit;
	private JPasswordField passText;
	private JTextField userText;

	public LoginView() {
		setTitle("Database");
		setSize(280, 150);

		setResizable(false);
		setLocation(800, 450);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// pane
		JPanel panel = new JPanel();
		placeLoginPanel(panel);
		// add
		add(panel);
		// visible
		setVisible(true);
	}

	public void placeLoginPanel(JPanel panel) {

		panel.setLayout(null);
		JLabel userLabel = new JLabel("SCHEMA");
		userLabel.setBounds(10, 10, 80, 25);
		panel.add(userLabel);

		JLabel passLabel = new JLabel("PW");
		passLabel.setBounds(10, 40, 80, 25);
		panel.add(passLabel);

		userText = new JTextField(20);
		userText.setBounds(100, 10, 160, 25);
		panel.add(userText);

		passText = new JPasswordField(20);
		passText.setBounds(100, 40, 160, 25);
		panel.add(passText);
		passText.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isLoginCheck();
			}
		});

		btnInit = new JButton("Reset");
		btnInit.setBounds(10, 80, 100, 25);
		panel.add(btnInit);
		btnInit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				userText.setText("");
				passText.setText("");
			}
		});

		btnLogin = new JButton("CREATE");
		btnLogin.setBounds(160, 80, 100, 25);
		panel.add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isLoginCheck();
			}
		});
	}

	public void isLoginCheck() {
		
		if (userText.getText() != "" && new String(passText.getPassword()) !="") {
			// 비지 않았다면 진행한다.

			// 디비연결, 디비연결 후 디비클래스에서 메인창 메소드 창 띄우기
			DB.createDatabase(userText.getText(), new String(passText.getPassword()));
			//JOptionPane.showMessageDialog(null, "Success");
		} else {
			JOptionPane.showMessageDialog(null, "Faild");
		}
	}

}

package Frames;
import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Entries.*;

public class Login extends JFrame implements ActionListener, FocusListener, MouseListener {
    JTextField usernameField;
    JPasswordField passwordField;
    JLabel titleLabel, subtitleLabel, usernameLabel, passwordLabel, signup, imagelbl, usernameUnderline, passwordUnderline;
    JButton loginBtn;
    JCheckBox rememberMe;
    ImageIcon icon, img;

    public Login() {
        super("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 550);
        setLocationRelativeTo(null);
        initComponents();

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(33, 150, 243));
        add(panel);

        titleLabel = new JLabel("Log In", SwingConstants.CENTER);
        titleLabel.setBounds(700, 50, 200, 40);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel);

        subtitleLabel = new JLabel("Hello! Let's get started", SwingConstants.CENTER);
        subtitleLabel.setBounds(700, 90, 200, 20);
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(subtitleLabel);

        usernameLabel = new JLabel("User Name");
        usernameLabel.setBounds(700, 150, 200, 25);
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(usernameLabel);

        usernameField = new JTextField("Enter your username or email");
        usernameField.setBounds(700, 180, 200, 30);
        usernameField.setForeground(Color.WHITE);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 12));
        usernameField.setOpaque(false);
        usernameField.setBorder(null);
        usernameField.setCaretColor(Color.WHITE); 
        usernameField.addFocusListener(this);
        panel.add(usernameField);

        usernameUnderline = new JLabel();
        usernameUnderline.setBounds(700, 210, 200, 1);
        usernameUnderline.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        panel.add(usernameUnderline);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(700, 220, 200, 25);
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(passwordLabel);

        passwordField = new JPasswordField("Enter your Password");
        passwordField.setBounds(700, 250, 200, 30);
        passwordField.setForeground(Color.WHITE);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 12));
        passwordField.setOpaque(false);
        passwordField.setBorder(null);
        passwordField.setCaretColor(Color.WHITE); 
        passwordField.setEchoChar((char) 0);
        passwordField.addFocusListener(this);
        panel.add(passwordField);

        passwordUnderline = new JLabel();
        passwordUnderline.setBounds(700, 280, 200, 1);
        passwordUnderline.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        panel.add(passwordUnderline);

        rememberMe = new JCheckBox("REMEMBER ME");
        rememberMe.setBounds(700, 290, 120, 20);
        rememberMe.setForeground(Color.WHITE);
        rememberMe.setBackground(new Color(33, 150, 243));
        rememberMe.setFont(new Font("Arial", Font.PLAIN, 10));
        panel.add(rememberMe);

        loginBtn = new JButton("LOGIN");
        loginBtn.setBounds(700, 320, 200, 40);
        loginBtn.setBackground(Color.WHITE);
        loginBtn.setForeground(Color.BLACK);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 14));
        loginBtn.addActionListener(this);
        loginBtn.addMouseListener(this);
        panel.add(loginBtn);

        signup = new JLabel("Don't have an account? SIGN UP", SwingConstants.CENTER);
        signup.setBounds(700, 370, 200, 20);
        signup.setForeground(Color.WHITE);
        signup.setFont(new Font("Arial", Font.PLAIN, 12));
        signup.addMouseListener(this);
        panel.add(signup);
        
        img = new ImageIcon("./images/Shop.jpg");
        imagelbl = new JLabel(img);
        imagelbl.setBounds(0, 0, 635, 550);
        panel.add(imagelbl);
    }

    public void initComponents() 
    {
        icon = new ImageIcon("E:/java/java project/E-commarce/images/ShopIcon.jpg");
        setIconImage(icon.getImage());
    }
	
	public void mousePressed(MouseEvent me) {}
    public void mouseReleased(MouseEvent me) {}
	
	public void mouseEntered(MouseEvent me) {
        if (me.getSource() == loginBtn) {
            loginBtn.setBackground(Color.WHITE);
        }
    }
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() == signup) {
            this.setVisible(false);
            Register r1 = new Register(this);
            r1.setVisible(true);
        }
    }
    public void mouseExited(MouseEvent me) {
        if (me.getSource() == loginBtn) {
            loginBtn.setBackground(Color.WHITE);
        }
    }
	
	
	public void focusGained(FocusEvent e) {
        if (e.getSource() == usernameField) {
            if (usernameField.getText().equals("Enter your username or email")) {
                usernameField.setText("");
            }
        } else if (e.getSource() == passwordField) {
            if (new String(passwordField.getPassword()).equals("Enter your Password")) {
                passwordField.setText("");
                passwordField.setEchoChar('*');
            }
        }
    }

    public void focusLost(FocusEvent e) {
        if (e.getSource() == usernameField) {
            if (usernameField.getText().isEmpty()) {
                usernameField.setText("Enter your username or email");
                usernameField.setForeground(Color.WHITE);
            }
        } else if (e.getSource() == passwordField) {
            if (new String(passwordField.getPassword()).isEmpty()) {
                passwordField.setText("Enter your Password");
                passwordField.setForeground(Color.WHITE);
                passwordField.setEchoChar((char) 0);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginBtn) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            boolean isValid = true;
            
			if (isValid) {
                Account loginAccount = new Account(username, password);
                if (loginAccount.getAccount(username, password)) {
                    JOptionPane.showMessageDialog(this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    setVisible(false);
                    Homepage h1=new Homepage(username, this);
					h1.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (username.isEmpty() || username.equals("Enter your username or email")) {
                JOptionPane.showMessageDialog(this, "Username is required!", "Error", JOptionPane.ERROR_MESSAGE);
                
            }
            if (password.isEmpty() || password.equals("Enter your Password")) {
                JOptionPane.showMessageDialog(this, "Password is required!", "Error", JOptionPane.ERROR_MESSAGE);
                
            }
        }
    }
    
}
package Frames;
import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Entries.*;

public class Register extends JFrame implements ActionListener {
    JPanel panel;
    JLabel namlbl, passlbl, nameError, passError, imagelbl;
    JTextField nmfld;
    JPasswordField passfld;
    ImageIcon icon, img;
    JButton signup, back;
    JFrame loginFrame;

    public Register(JFrame loginFrame) {
        super("Registration");
        this.loginFrame = loginFrame;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(850, 550);
        setLocationRelativeTo(null);
        initComponents();

        panel = new JPanel();
        panel.setLayout(null);

        namlbl = new JLabel("User Name");
        namlbl.setBounds(300, 140, 100, 50);
        panel.add(namlbl);

        nmfld = new JTextField();
        nmfld.setBounds(300, 180, 200, 25);
        panel.add(nmfld);

        nameError = new JLabel("Name is required!!");
        nameError.setForeground(Color.RED);
        nameError.setBounds(300, 200, 200, 20);
        nameError.setVisible(false);
        panel.add(nameError);

        passlbl = new JLabel("Password");
        passlbl.setBounds(300, 210, 100, 50);
        panel.add(passlbl);

        passfld = new JPasswordField();
        passfld.setBounds(300, 250, 200, 25);
        panel.add(passfld);

        passError = new JLabel("Password is required!!");
        passError.setForeground(Color.RED);
        passError.setBounds(300, 270, 200, 20);
        passError.setVisible(false);
        panel.add(passError);

        signup = new JButton("SignUp");
        signup.setBounds(300, 300, 80, 30);
        signup.addActionListener(this);
        panel.add(signup);

        back = new JButton("Back");
        back.setBounds(420, 300, 80, 30);
        back.addActionListener(this);
        panel.add(back);

        add(panel);
		
		img = new ImageIcon("./images/shopping.jpg");
        imagelbl = new JLabel(img);
        imagelbl.setBounds(0, 0, 850, 550);
        panel.add(imagelbl);
    }

    public void initComponents() 
   {
    icon = new ImageIcon("E:/java/java project/E-commarce/images/ShopIcon.jpg");
    setIconImage(icon.getImage());
   }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == back) {
            setVisible(false);
            loginFrame.setVisible(true);
        } else if (ae.getSource() == signup) {
            String username = nmfld.getText();
            String password = new String(passfld.getPassword());
            boolean hasError = false;

            if (username.isEmpty()) {
                nameError.setVisible(true);
                hasError = true;
            } else {
                nameError.setVisible(false);
            }

            if (password.isEmpty()) {
                passError.setVisible(true);
                hasError = true;
            } else {
                passError.setVisible(false);
            }

            if (hasError) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Account checkAccount = new Account(username, password);
                if (checkAccount.getAccount(username, password)) {
                    JOptionPane.showMessageDialog(this, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Account newAccount = new Account(username, password);
                    newAccount.addAccount();
                    JOptionPane.showMessageDialog(this, "Registration Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    setVisible(false);
                    loginFrame.setVisible(true);
                }
            }
        }
    }
}
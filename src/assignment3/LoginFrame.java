package assignment3;
import java.awt.*;
import javax.swing.*;
public class LoginFrame extends JFrame {
    final JPasswordField passwordField;
    final JTextField usernameField;
    final JLabel usernameLabel, passwordLabel;
    final JButton loginButton, saveChange;
    String username, password;
    User currentUser;
    UserMenuFrame userFrame;

    LibraryUI libUI;
    public LoginFrame() {
        super("Menu login");
        setLayout(new FlowLayout());
        usernameField = new JTextField("abemwell0",18);
        passwordField = new JPasswordField("eocIGxRnCW",18);
        
        usernameLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");
        loginButton = new JButton("Log in");
        saveChange = new JButton("Commit Change");
        
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
        add(saveChange);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(300, 120);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    
}

package sr.unasat.bewakingsBedrijf.app.scherm.algemeen;

import sr.unasat.bewakingsBedrijf.app.databaseConnection.Login.LogInDatabase;
import sr.unasat.bewakingsBedrijf.app.databaseConnection.Login.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jair on 5/27/2016.
 */
public class LogInScherm extends JDialog{

    LogInDatabase logInDatabase= null;

    JLabel logoIcon= new JLabel();
    JLabel logInLabel= new JLabel();
    JTextField usernameField= new JTextField();
    JPasswordField passwordField= new JPasswordField();
    JButton logInBut= new JButton();
    JPanel panel= new JPanel();
    ImageIcon logo= new ImageIcon(getClass().getResource("22.png"));

    public LogInScherm(){

        logoIcon.setIcon(logo);
        logInLabel.setText("Log In");
        usernameField.setColumns(15);
        passwordField.setColumns(15);

        logInBut.setBackground(new Color(13, 168, 225));
        logInBut.setText("Log In");

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc= new GridBagConstraints();

        gbc.gridx=0;
        gbc.gridy=0;
        gbc.insets.set(0,0,100,0);
        panel.add(logoIcon, gbc);

        gbc.insets.set(5,5,5,5);
        gbc.gridx=0;
        gbc.gridy=-1;
        panel.add(logInLabel, gbc);

        gbc.gridx=0;
        gbc.gridy=-2;
        panel.add(usernameField, gbc);

        gbc.gridx=0;
        gbc.gridy=-3;
        panel.add(passwordField, gbc);

        gbc.gridx=0;
        gbc.gridy=-4;
        gbc.insets.set(50,0,0,0);
        panel.add(logInBut, gbc);

        add(panel);

        panel.setBackground(new Color(29,130,191));

        setResizable(false);
        setSize(400,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);

        validate();

        logInBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = new Login();
                String userName = usernameField.getText();
                String passWord= passwordField.getText();

                if (logInDatabase == null) {
                    logInDatabase = new LogInDatabase();
                }

                if (!logInDatabase.isInitialised()) {
                    logInDatabase.initialiseer();
                }

                if (logInDatabase.isInitialised()) {



                    if (userName.toString().equals(logInDatabase.selectLogIn().getUsername())){
                        if(passWord.toString().equals(logInDatabase.selectLogIn().getPassword())){

                            new StartScherm();
                            setVisible(false);

                        }else{
                            JOptionPane.showMessageDialog(panel,"wrong password","Log In failed", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
        }});
    }

}

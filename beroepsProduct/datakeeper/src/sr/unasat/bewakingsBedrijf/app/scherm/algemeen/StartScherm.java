package sr.unasat.bewakingsBedrijf.app.scherm.algemeen;

import sr.unasat.bewakingsBedrijf.app.scherm.bewaker.BewakerScherm;
import sr.unasat.bewakingsBedrijf.app.scherm.post.PostScherm;
import sr.unasat.bewakingsBedrijf.app.scherm.rooster.RoosterScherm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jair on 5/27/2016.
 */
public class StartScherm extends JPanel {

    JFrame frame= new JFrame();
    JPanel panel= new JPanel();
    JButton bewakerBut= new JButton("bewaker");
    JButton postBut= new JButton("post");
    JButton roosterBut= new JButton("rooster");

    ImageIcon iconBew = new ImageIcon(getClass().getResource("bewaker.png"));
    ImageIcon iconRoo= new ImageIcon(getClass().getResource("rooster.png"));
    ImageIcon iconPost= new ImageIcon(getClass().getResource("post.png"));
    public StartScherm(){


        bewakerBut.setIcon(iconBew);
        roosterBut.setIcon(iconRoo);
        postBut.setIcon(iconPost);


        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc= new GridBagConstraints();
        gbc.insets.set(50,10,50,10);


        gbc.gridx=1;
        panel.add(bewakerBut,gbc);



        gbc.gridx=1;
        gbc.gridy= 1;
        panel.add(postBut,gbc);


        gbc.gridx=1;
        gbc.gridy=-2;
        panel.add(roosterBut,gbc);
        panel.setBackground(new Color(7, 84, 127));


        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setResizable(false);
        frame.setSize(400,500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        bewakerBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BewakerScherm();
                frame.setVisible(false);
            }
        });

        roosterBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RoosterScherm();

                frame.setVisible(false);

            }
        });

        postBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new PostScherm();

                frame.setVisible(false);
            }
        });
    }
}

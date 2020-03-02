package sr.unasat.bewakingsBedrijf.app.scherm.bewaker;

import sr.unasat.bewakingsBedrijf.app.databaseConnection.bewaker.Bewaker;
import sr.unasat.bewakingsBedrijf.app.databaseConnection.bewaker.BewakerDatabase;
import sr.unasat.bewakingsBedrijf.app.databaseConnection.rooster.RoosterDatabase;
import sr.unasat.bewakingsBedrijf.app.scherm.algemeen.StartScherm;
import sr.unasat.bewakingsBedrijf.app.scherm.rooster.RoosterScherm;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by Jair on 5/31/2016.
 */
public class BewakerScherm extends  JFrame {

    BewakerDatabase bewakerDatabase = null;

    JPanel viewAllPan = new JPanel(new BorderLayout()), viewBewakerPan= new JPanel(new BorderLayout()),
            updateBewakerPan= new JPanel(new BorderLayout()), newBewakerPan= new JPanel(new BorderLayout()),
            deleteBewakerPan= new JPanel(new BorderLayout()), homePan= new JPanel();

    JTabbedPane jTabbedPane= new JTabbedPane(SwingConstants.LEFT);

    ArrayList  bewakerArrayList= new ArrayList<String>(); 
    ArrayList<Bewaker> bewakerArraylistObject= new ArrayList();
    ArrayList <Integer> bewakerArrayListID = new ArrayList();

    private JList outputList;
    private DefaultListModel listModel;
    private DefaultTableModel listTableModel;
    private JTable outputTable;
    ImageIcon allIcon= new ImageIcon(getClass().getResource("allBew.png"));
    ImageIcon deleteIcon= new ImageIcon(getClass().getResource("deleteBew.png"));
    ImageIcon newIcon= new ImageIcon(getClass().getResource("newBew.png"));
    ImageIcon updateIcon= new ImageIcon(getClass().getResource("updateBew.png"));
    ImageIcon viewIcon= new ImageIcon(getClass().getResource("viewBew.png"));
    ImageIcon homeIcon= new ImageIcon(getClass().getResource("home.png"));


    public BewakerScherm(){

        jTabbedPane.addTab("all bewakers",allIcon,viewAllPan);
        jTabbedPane.addTab("view bewaker",viewIcon, viewBewakerPan);
        jTabbedPane.addTab("update bewaker",updateIcon ,updateBewakerPan);
        jTabbedPane.addTab("new Bewaker",newIcon, newBewakerPan);
        jTabbedPane.addTab("delete Bewaker",deleteIcon, deleteBewakerPan);
        jTabbedPane.addTab("home",homeIcon, homePan);
        viewAllPanel();


        getContentPane().setBackground(new Color(7, 84, 127));
        jTabbedPane.setBackground(new Color(148, 178, 173));
        add(jTabbedPane);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(900,500);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        jTabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (viewAllPan.isShowing()){
                    viewAllPanel();
                }else{
                    viewAllPan.removeAll();
                }

                if (viewBewakerPan.isShowing()){
                    viewBewakerPanel();
                }else {
                    viewBewakerPan.removeAll();
                }

                if (updateBewakerPan.isShowing()){
                    updateBewakerPanel();
                }else{
                    updateBewakerPan.removeAll();
                }
                if (newBewakerPan.isShowing()){
                    newBewakerPanel();
                }else {
                    newBewakerPan.removeAll();
                }

                if (homePan.isShowing()){

                    setVisible(false);
                    new StartScherm();
                }
                if (deleteBewakerPan.isShowing()){
                    deleteBewakerPanel();
                }else{
                    deleteBewakerPan.removeAll();
                    deleteBewakerPanel();
                }

            }
        });
    }

    public void viewAllPanel(){
        JPanel outputPanel= new JPanel();
        JButton viewAllBut= new JButton("view all");

        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));

        listTableModel = new DefaultTableModel();
        String[] colnames = {"bewaker", "adress", "mail", "phone"};
        Vector colnamesV = new Vector(Arrays.asList(colnames));

        outputTable = new JTable(null, colnamesV);
        JScrollPane tablePanel = new JScrollPane(outputTable);
        tablePanel.setPreferredSize(new Dimension(800, 150));
        outputPanel.add(tablePanel);

        outputTable.setBackground(new Color(148, 178, 173));


        viewAllPan.add(outputPanel, BorderLayout.CENTER);
        viewAllPan.add(viewAllBut, BorderLayout.SOUTH);

        viewAllBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (bewakerDatabase == null){
                    bewakerDatabase = new BewakerDatabase();
                }

                if (!bewakerDatabase.isInitialised()){
                    bewakerDatabase.initialiseer();
                }

                if (bewakerDatabase.isInitialised()){
                    bewakerDatabase.selectAll();
                }


                listTableModel = (DefaultTableModel) outputTable.getModel();
                listTableModel.setRowCount(0);

                Iterator itrTable = bewakerDatabase.selectAll().iterator();
                while (itrTable.hasNext()) {
                    Bewaker record = (Bewaker) itrTable.next();

                    String[] colData = new String[4];
                    //{"id", "bewaker", "adres", "mail", "telefoon"};
                    //colData[0] = Integer.valueOf(record.getId()).toString();
                    colData[0] = record.getBewaker_naam();
                    colData[1] = record.getBewaker_adres();
                    colData[2] = record.getBewaker_mail();
                    colData[3] = record.getBewaker_telefoon();


                    listTableModel.addRow(colData);
                }
                outputTable.setModel(listTableModel);
            }

        });
    }

    public void viewBewakerPanel(){
        buildArrays();
        //{"bewaker", "adres", "mail", "telefoon"};

        JPanel uperPanel=new JPanel(), outputPanel= new JPanel(new GridBagLayout());
        JButton findBut = new JButton("find");

        JLabel nameLabel=new JLabel("name : "),namefield=new JLabel(), adressLabel=new JLabel("adress : "),
                adressField=new JLabel(), mailLabel=new JLabel("mail : "), mailField=new JLabel(),
                phoneLabel=new JLabel("phone : "), phoneField=new JLabel();

        JComboBox jComboBox= new JComboBox(new DefaultComboBoxModel(bewakerArrayList.toArray()));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets.set(30,30,30,30);

        gbc.gridx=0;
        gbc.gridy=0;
        outputPanel.add(nameLabel,gbc);

        gbc.gridx=1;
        outputPanel.add(namefield,gbc);


        gbc.gridx=0;
        gbc.gridy=-1;
        outputPanel.add(adressLabel, gbc);

        gbc.gridx=1;
        outputPanel.add(adressField,gbc);


        gbc.gridx=0;
        gbc.gridy=-2;
        outputPanel.add(mailLabel,gbc);

        gbc.gridx=1;
        outputPanel.add(mailField,gbc);

        gbc.gridx=0;
        gbc.gridy=-3;
        outputPanel.add(phoneLabel,gbc);

        gbc.gridx=1;
        outputPanel.add(phoneField,gbc);


        uperPanel.add(jComboBox);
        uperPanel.add(findBut);

        uperPanel.setBackground(new Color(137,191,198));
        outputPanel.setBackground(new Color(137,191,198));

        viewBewakerPan.add(uperPanel, BorderLayout.NORTH);
        viewBewakerPan.add(outputPanel, BorderLayout.CENTER);

        findBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = jComboBox.getSelectedIndex();

                int record = bewakerArrayListID.get(i);

                if (bewakerDatabase == null) {
                    bewakerDatabase = new BewakerDatabase();
                }

                // Initialiseer de bewakerDatabase alleen als het moet
                if (!bewakerDatabase.isInitialised()) {
                    bewakerDatabase.initialiseer();
                }

                // Alleen als de bewakeren database is geinitialiseerd dan verder
                if (bewakerDatabase.isInitialised()) {


                    namefield.setText(bewakerDatabase.selectBewaker(record).getBewaker_naam());
                    adressField.setText(bewakerDatabase.selectBewaker(record).getBewaker_adres());
                    mailField.setText(bewakerDatabase.selectBewaker(record).getBewaker_mail());
                    phoneField.setText(bewakerDatabase.selectBewaker(record).getBewaker_telefoon());
                }

            }});
    }

    public void updateBewakerPanel(){

        buildArrays();
        //{"bewaker", "adres", "mail", "telefoon"};

        JPanel uperPanel=new JPanel(), outputPanel= new JPanel(new GridBagLayout()),updatePanel= new JPanel();
        JButton findBut = new JButton("find"), updateBut= new JButton("update");

        JLabel nameLabel=new JLabel("name : "), adressLabel=new JLabel("adress : "), mailLabel=new JLabel("mail : "), phoneLabel=new JLabel("phone : ");

        JTextField namefield = new JTextField(15),adressField  = new JTextField(15),mailField = new JTextField(15),phoneField = new JTextField(15);
        
        JComboBox jComboBox= new JComboBox(new DefaultComboBoxModel(bewakerArrayList.toArray()));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets.set(30,30,30,30);

        gbc.gridx=0;
        gbc.gridy=0;
        outputPanel.add(nameLabel,gbc);

        gbc.gridx=1;
        outputPanel.add(namefield,gbc);


        gbc.gridx=0;
        gbc.gridy=-1;
        outputPanel.add(adressLabel, gbc);

        gbc.gridx=1;
        outputPanel.add(adressField,gbc);


        gbc.gridx=0;
        gbc.gridy=-2;
        outputPanel.add(mailLabel,gbc);

        gbc.gridx=1;
        outputPanel.add(mailField,gbc);

        gbc.gridx=0;
        gbc.gridy=-3;
        outputPanel.add(phoneLabel,gbc);

        gbc.gridx=1;
        outputPanel.add(phoneField,gbc);

        updatePanel.add(updateBut,gbc);
        
        uperPanel.add(jComboBox);
        uperPanel.add(findBut);


        uperPanel.setBackground(new Color(137,191,198));
        outputPanel.setBackground(new Color(137,191,198));
        updatePanel.setBackground(new Color(137,191,198));

        updateBewakerPan.add(uperPanel, BorderLayout.NORTH);
        updateBewakerPan.add(outputPanel, BorderLayout.CENTER);
        updateBewakerPan.add(updatePanel, BorderLayout.SOUTH);

        findBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = jComboBox.getSelectedIndex();

                int record = bewakerArrayListID.get(i);

                if (bewakerDatabase == null) {
                    bewakerDatabase = new BewakerDatabase();
                }

                // Initialiseer de bewakerDatabase alleen als het moet
                if (!bewakerDatabase.isInitialised()) {
                    bewakerDatabase.initialiseer();
                }

                // Alleen als de bewakeren database is geinitialiseerd dan verder
                if (bewakerDatabase.isInitialised()) {


                    namefield.setText(bewakerDatabase.selectBewaker(record).getBewaker_naam());
                    adressField.setText(bewakerDatabase.selectBewaker(record).getBewaker_adres());
                    mailField.setText(bewakerDatabase.selectBewaker(record).getBewaker_mail());
                    phoneField.setText(bewakerDatabase.selectBewaker(record).getBewaker_telefoon());
                }

            }});
        updateBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int i = jComboBox.getSelectedIndex();

                bewakerArraylistObject.get(i).setBewaker_naam(namefield.getText());
                bewakerArraylistObject.get(i).setBewaker_adres(adressField.getText());
                bewakerArraylistObject.get(i).setBewaker_mail(mailField.getText());
                bewakerArraylistObject.get(i).setBewaker_telefoon(phoneField.getText());

               if (!namefield.getText().isEmpty()) {
                   if (!adressField.getText().isEmpty()) {
                       if (!phoneField.getText().isEmpty()) {
                           if (!mailField.getText().isEmpty()) {

                               if (bewakerDatabase == null) {
                                   bewakerDatabase = new BewakerDatabase();
                               }
                               if (!bewakerDatabase.isInitialised()) {
                                   bewakerDatabase.initialiseer();
                               }

                               if (bewakerDatabase.isInitialised()) {
                                   bewakerDatabase.updateBewaker(bewakerArraylistObject.get(i));

                                   JOptionPane.showMessageDialog(updateBewakerPan,"bewaker updated");
                                   updateBewakerPan.removeAll();
                                   updateBewakerPanel();
                               }


                               }else{JOptionPane.showMessageDialog(updateBewakerPan, "mail is empty");}
                       }else{JOptionPane.showMessageDialog(updateBewakerPan, "phone is empty");}
                   }else {JOptionPane.showMessageDialog(updateBewakerPan, "adress is empty");}
               }else{JOptionPane.showMessageDialog(updateBewakerPan,"name is empty");}


            }});
    }

    public void newBewakerPanel(){
        JPanel outputPanel= new JPanel(new GridBagLayout()),updatePanel= new JPanel();
        JButton saveBut= new JButton("save");

        JLabel nameLabel=new JLabel("name : "), adressLabel=new JLabel("adress : "), mailLabel=new JLabel("mail : "), phoneLabel=new JLabel("phone : ");

        JTextField namefield = new JTextField(15),adressField  = new JTextField(15),mailField = new JTextField(15),phoneField = new JTextField(15);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets.set(30,30,30,30);

        gbc.gridx=0;
        gbc.gridy=0;
        outputPanel.add(nameLabel,gbc);

        gbc.gridx=1;
        outputPanel.add(namefield,gbc);


        gbc.gridx=0;
        gbc.gridy=-1;
        outputPanel.add(adressLabel, gbc);

        gbc.gridx=1;
        outputPanel.add(adressField,gbc);


        gbc.gridx=0;
        gbc.gridy=-2;
        outputPanel.add(mailLabel,gbc);

        gbc.gridx=1;
        outputPanel.add(mailField,gbc);

        gbc.gridx=0;
        gbc.gridy=-3;
        outputPanel.add(phoneLabel,gbc);

        gbc.gridx=1;
        outputPanel.add(phoneField,gbc);

        updatePanel.add(saveBut,gbc);

        outputPanel.setBackground(new Color(137,191,198));
        updatePanel.setBackground(new Color(137,191,198));

        newBewakerPan.add(outputPanel, BorderLayout.CENTER);
        newBewakerPan.add(updatePanel, BorderLayout.SOUTH);

        saveBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Bewaker bewaker= new Bewaker();
                bewaker.setBewaker_naam(namefield.getText());
                bewaker.setBewaker_adres(adressField.getText());
                bewaker.setBewaker_mail(mailField.getText());
                bewaker.setBewaker_telefoon(phoneField.getText());

                if (!namefield.getText().isEmpty()) {
                    if (!adressField.getText().isEmpty()) {
                        if (!phoneField.getText().isEmpty()) {
                            if (!mailField.getText().isEmpty()) {

                                if (bewakerDatabase == null) {
                                    bewakerDatabase = new BewakerDatabase();
                                }
                                if (!bewakerDatabase.isInitialised()) {
                                    bewakerDatabase.initialiseer();
                                }

                                if (bewakerDatabase.isInitialised()) {
                                    bewakerDatabase.insertBewaker(bewaker);

                                    JOptionPane.showMessageDialog(updateBewakerPan,"bewaker saved");

                                    newBewakerPan.removeAll();
                                    newBewakerPanel();
                                }


                            }else{JOptionPane.showMessageDialog(updateBewakerPan, "mail is empty");}
                        }else{JOptionPane.showMessageDialog(updateBewakerPan, "phone is empty");}
                    }else {JOptionPane.showMessageDialog(updateBewakerPan, "adress is empty");}
                }else{JOptionPane.showMessageDialog(updateBewakerPan,"name is empty");}

            }
        });
    }

    public  void deleteBewakerPanel(){

        buildArrays();
        //{"bewaker", "adres", "mail", "telefoon"};

        JPanel uperPanel=new JPanel(), outputPanel= new JPanel(new GridBagLayout()),updatePanel= new JPanel();
        JButton findBut = new JButton("find"), deleteBut= new JButton("delete");

        JLabel nameLabel=new JLabel("name : "), adressLabel=new JLabel("adress : "), mailLabel=new JLabel("mail : "), phoneLabel=new JLabel("phone : ");

        JLabel namefield = new JLabel(),adressField  = new JLabel(),mailField = new JLabel(),phoneField = new JLabel();

        JComboBox jComboBox= new JComboBox(new DefaultComboBoxModel(bewakerArrayList.toArray()));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets.set(30,30,30,30);

        gbc.gridx=0;
        gbc.gridy=0;
        outputPanel.add(nameLabel,gbc);

        gbc.gridx=1;
        outputPanel.add(namefield,gbc);


        gbc.gridx=0;
        gbc.gridy=-1;
        outputPanel.add(adressLabel, gbc);

        gbc.gridx=1;
        outputPanel.add(adressField,gbc);


        gbc.gridx=0;
        gbc.gridy=-2;
        outputPanel.add(mailLabel,gbc);

        gbc.gridx=1;
        outputPanel.add(mailField,gbc);

        gbc.gridx=0;
        gbc.gridy=-3;
        outputPanel.add(phoneLabel,gbc);

        gbc.gridx=1;
        outputPanel.add(phoneField,gbc);

        updatePanel.add(deleteBut,gbc);

        uperPanel.add(jComboBox);
        uperPanel.add(findBut);

        uperPanel.setBackground(new Color(137,191,198));
        outputPanel.setBackground(new Color(137,191,198));
        updatePanel.setBackground(new Color(137,191,198));

        deleteBewakerPan.add(uperPanel, BorderLayout.NORTH);
        deleteBewakerPan.add(outputPanel, BorderLayout.CENTER);
        deleteBewakerPan.add(updatePanel, BorderLayout.SOUTH);

        findBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = jComboBox.getSelectedIndex();

                int record = bewakerArrayListID.get(i);

                if (bewakerDatabase == null) {
                    bewakerDatabase = new BewakerDatabase();
                }

                // Initialiseer de bewakerDatabase alleen als het moet
                if (!bewakerDatabase.isInitialised()) {
                    bewakerDatabase.initialiseer();
                }

                // Alleen als de bewakeren database is geinitialiseerd dan verder
                if (bewakerDatabase.isInitialised()) {


                    namefield.setText(bewakerDatabase.selectBewaker(record).getBewaker_naam());
                    adressField.setText(bewakerDatabase.selectBewaker(record).getBewaker_adres());
                    mailField.setText(bewakerDatabase.selectBewaker(record).getBewaker_mail());
                    phoneField.setText(bewakerDatabase.selectBewaker(record).getBewaker_telefoon());
                }

            }});
        deleteBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int i = jComboBox.getSelectedIndex();


                    if (JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                        int record = bewakerArrayListID.get(i);

                        RoosterDatabase roosterDatabase = new RoosterDatabase();

                        if (!roosterDatabase.isInitialised()) {
                            roosterDatabase.initialiseer();
                        }

                        if (roosterDatabase.isInitialised()) {
                            int lastindex = roosterDatabase.selectAll().get(roosterDatabase.selectAll().size() - 1).getId();
                            for (int a = 0; a < lastindex + 1; a++) {
                                if (bewakerDatabase.selectBewaker(record).getId() == roosterDatabase.selectRooster(a).getBewaker_fk()) {
                                    roosterDatabase.deleteRooster(a);
                                }

                            }

                        // bewaker.bewaker database instantie is alleen 1 keer nodig
                        if (bewakerDatabase == null) {
                            bewakerDatabase = new BewakerDatabase();
                        }

                        // Initialiseer de bewakerDatabase alleen als het moet
                        if (!bewakerDatabase.isInitialised()) {
                            bewakerDatabase.initialiseer();
                        }

                        // Alleen als de bewakeren database is geinitialiseerd dan verder
                        if (bewakerDatabase.isInitialised()) {
                            bewakerDatabase.deleteBewaker(record);


                                    deleteBewakerPan.removeAll();
                                    deleteBewakerPanel();
                                    JOptionPane.showMessageDialog(deleteBewakerPan, "bewaker deleted");

                            } else {
                                JOptionPane.showMessageDialog(deleteBewakerPan, "bewaker not deleted");
                            }

                        }}
            }});
    }
    
    public void buildArrays(){

        bewakerArraylistObject.clear();
        bewakerArrayList.clear();
        bewakerArrayListID.clear();

        if (bewakerDatabase == null) {
            bewakerDatabase = new BewakerDatabase();
        }

        if (!bewakerDatabase.isInitialised()) {
            bewakerDatabase.initialiseer();
        }

        Iterator<Bewaker> itrBewaker = bewakerDatabase.selectAll().iterator();
        while (itrBewaker.hasNext()) {
            Bewaker i = (Bewaker) itrBewaker.next();

            bewakerArraylistObject.add(i);
            bewakerArrayList.add(i.getBewaker_naam());
            bewakerArrayListID.add(i.getId());

        }
    }
    

}

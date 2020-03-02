package sr.unasat.bewakingsBedrijf.app.scherm.post;

import sr.unasat.bewakingsBedrijf.app.databaseConnection.post.Post;
import sr.unasat.bewakingsBedrijf.app.databaseConnection.post.PostDatabase;
import sr.unasat.bewakingsBedrijf.app.databaseConnection.rooster.RoosterDatabase;
import sr.unasat.bewakingsBedrijf.app.scherm.algemeen.StartScherm;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by Jair on 6/2/2016.
 */
public class PostScherm extends  JFrame{

    PostDatabase postDatabase = null;

    JPanel viewAllPan = new JPanel(new BorderLayout()), viewPostPan = new JPanel(new BorderLayout()),
            updatePostPan= new JPanel(new BorderLayout()), newPostPan= new JPanel(new BorderLayout()),
            deletePostPan= new JPanel(new BorderLayout()), homePan= new JPanel();

    JTabbedPane jTabbedPane= new JTabbedPane(SwingConstants.LEFT);

    ArrayList PostArrayList= new ArrayList<String>();
    ArrayList<Post> PostArraylistObject= new ArrayList();
    ArrayList <Integer> PostArrayListID = new ArrayList();

    private JList outputList;
    private DefaultListModel listModel;
    private DefaultTableModel listTableModel;
    private JTable outputTable;
    ImageIcon allIcon= new ImageIcon(getClass().getResource("allPost.png"));
    ImageIcon deleteIcon= new ImageIcon(getClass().getResource("delete.png"));
    ImageIcon newIcon= new ImageIcon(getClass().getResource("newPost.png"));
    ImageIcon updateIcon= new ImageIcon(getClass().getResource("updatePost.png"));
    ImageIcon viewIcon= new ImageIcon(getClass().getResource("viewPost.png"));
    ImageIcon homeIcon= new ImageIcon(getClass().getResource("home.png"));


    public PostScherm(){

        jTabbedPane.addTab("all Posts",allIcon,viewAllPan);
        jTabbedPane.addTab("view Post",viewIcon, viewPostPan);
        jTabbedPane.addTab("update Post",updateIcon ,updatePostPan);
        jTabbedPane.addTab("new Post",newIcon, newPostPan);
        jTabbedPane.addTab("delete Post",deleteIcon, deletePostPan);
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

                if (viewPostPan.isShowing()){
                    viewPostPanel();
                }else {
                    viewPostPan.removeAll();
                }

                if (updatePostPan.isShowing()){
                    updatePostPanel();
                }else{
                    updatePostPan.removeAll();
                }
                if (newPostPan.isShowing()){
                    newPostPanel();
                }else {
                    newPostPan.removeAll();
                }

                if (homePan.isShowing()){

                    setVisible(false);
                    new StartScherm();
                }
                if (deletePostPan.isShowing()){
                    deletePostPanel();
                }else{
                    deletePostPan.removeAll();
                    deletePostPanel();
                }

            }
        });
    }

    public void viewAllPanel(){
        JPanel outputPanel= new JPanel();
        JButton viewAllBut= new JButton("view all");

        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));

        listTableModel = new DefaultTableModel();
        String[] colnames = {"adress", "mail", "phone"};
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

                if (postDatabase == null){
                    postDatabase = new PostDatabase();
                }

                if (!postDatabase.isInitialised()){
                    postDatabase.initialiseer();
                }

                if (postDatabase.isInitialised()){
                    postDatabase.selectAll();
                }


                listTableModel = (DefaultTableModel) outputTable.getModel();
                listTableModel.setRowCount(0);

                Iterator itrTable = postDatabase.selectAll().iterator();
                while (itrTable.hasNext()) {
                    Post record = (Post) itrTable.next();

                    String[] colData = new String[4];
                    //{"id", "Post", "adres", "mail", "telefoon"};
                    //colData[0] = Integer.valueOf(record.getId()).toString();
                    //colData[0] = record.getPost_naam();
                    colData[0] = record.getPost_adres();
                    colData[1] = record.getPost_mail();
                    colData[2] = record.getPost_telefoon();


                    listTableModel.addRow(colData);
                }
                outputTable.setModel(listTableModel);
            }

        });
    }

    public void viewPostPanel(){
        buildArrays();
        //{"Post", "adres", "mail", "telefoon"};

        JPanel uperPanel=new JPanel(), outputPanel= new JPanel(new GridBagLayout());
        JButton findBut = new JButton("find");

        JLabel nameLabel=new JLabel("post : "),namefield=new JLabel(), adressLabel=new JLabel("adress : "),
                adressField=new JLabel(), mailLabel=new JLabel("mail : "), mailField=new JLabel(),
                phoneLabel=new JLabel("phone : "), phoneField=new JLabel();

        JComboBox jComboBox= new JComboBox(new DefaultComboBoxModel(PostArrayList.toArray()));

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

        viewPostPan.add(uperPanel, BorderLayout.NORTH);
        viewPostPan.add(outputPanel, BorderLayout.CENTER);

        findBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = jComboBox.getSelectedIndex();

                int record = PostArrayListID.get(i);

                if (postDatabase == null) {
                    postDatabase = new PostDatabase();
                }

                // Initialiseer de postDatabase alleen als het moet
                if (!postDatabase.isInitialised()) {
                    postDatabase.initialiseer();
                }

                // Alleen als de Posten database is geinitialiseerd dan verder
                if (postDatabase.isInitialised()) {


                    namefield.setText(postDatabase.selectPost(record).getPost_naam());
                    adressField.setText(postDatabase.selectPost(record).getPost_adres());
                    mailField.setText(postDatabase.selectPost(record).getPost_mail());
                    phoneField.setText(postDatabase.selectPost(record).getPost_telefoon());
                }

            }});
    }

    public void updatePostPanel(){

        buildArrays();
        //{"Post", "adres", "mail", "telefoon"};

        JPanel uperPanel=new JPanel(), outputPanel= new JPanel(new GridBagLayout()),updatePanel= new JPanel();
        JButton findBut = new JButton("find"), updateBut= new JButton("update");

        JLabel nameLabel=new JLabel("post : "), adressLabel=new JLabel("adress : "), mailLabel=new JLabel("mail : "), phoneLabel=new JLabel("phone : ");

        JTextField namefield = new JTextField(15),adressField  = new JTextField(15),mailField = new JTextField(15),phoneField = new JTextField(15);

        JComboBox jComboBox= new JComboBox(new DefaultComboBoxModel(PostArrayList.toArray()));

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

        updatePostPan.add(uperPanel, BorderLayout.NORTH);
        updatePostPan.add(outputPanel, BorderLayout.CENTER);
        updatePostPan.add(updatePanel, BorderLayout.SOUTH);

        findBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = jComboBox.getSelectedIndex();

                int record = PostArrayListID.get(i);

                if (postDatabase == null) {
                    postDatabase = new PostDatabase();
                }

                // Initialiseer de postDatabase alleen als het moet
                if (!postDatabase.isInitialised()) {
                    postDatabase.initialiseer();
                }

                // Alleen als de Posten database is geinitialiseerd dan verder
                if (postDatabase.isInitialised()) {


                    namefield.setText(postDatabase.selectPost(record).getPost_naam());
                    adressField.setText(postDatabase.selectPost(record).getPost_adres());
                    mailField.setText(postDatabase.selectPost(record).getPost_mail());
                    phoneField.setText(postDatabase.selectPost(record).getPost_telefoon());
                }

            }});
        updateBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int i = jComboBox.getSelectedIndex();

                PostArraylistObject.get(i).setPost_naam(namefield.getText());
                PostArraylistObject.get(i).setPost_adres(adressField.getText());
                PostArraylistObject.get(i).setPost_mail(mailField.getText());
                PostArraylistObject.get(i).setPost_telefoon(phoneField.getText());

                if (!namefield.getText().isEmpty()) {
                    if (!adressField.getText().isEmpty()) {
                        if (!phoneField.getText().isEmpty()) {
                            if (!mailField.getText().isEmpty()) {

                                if (postDatabase == null) {
                                    postDatabase = new PostDatabase();
                                }
                                if (!postDatabase.isInitialised()) {
                                    postDatabase.initialiseer();
                                }

                                if (postDatabase.isInitialised()) {
                                    postDatabase.updatePost(PostArraylistObject.get(i));

                                    JOptionPane.showMessageDialog(updatePostPan,"Post updated");
                                    updatePostPan.removeAll();
                                    updatePostPanel();
                                }


                            }else{JOptionPane.showMessageDialog(updatePostPan, "mail is empty");}
                        }else{JOptionPane.showMessageDialog(updatePostPan, "phone is empty");}
                    }else {JOptionPane.showMessageDialog(updatePostPan, "adress is empty");}
                }else{JOptionPane.showMessageDialog(updatePostPan,"post is empty");}


            }});
    }

    public void newPostPanel(){
        JPanel outputPanel= new JPanel(new GridBagLayout()),updatePanel= new JPanel();
        JButton saveBut= new JButton("save");

        JLabel nameLabel=new JLabel("post : "), adressLabel=new JLabel("adress : "), mailLabel=new JLabel("mail : "), phoneLabel=new JLabel("phone : ");

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

        newPostPan.add(outputPanel, BorderLayout.CENTER);
        newPostPan.add(updatePanel, BorderLayout.SOUTH);

        saveBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Post Post= new Post();
                Post.setPost_naam(namefield.getText());
                Post.setPost_adres(adressField.getText());
                Post.setPost_mail(mailField.getText());
                Post.setPost_telefoon(phoneField.getText());

                if (!namefield.getText().isEmpty()) {
                    if (!adressField.getText().isEmpty()) {
                        if (!phoneField.getText().isEmpty()) {
                            if (!mailField.getText().isEmpty()) {

                                if (postDatabase == null) {
                                    postDatabase = new PostDatabase();
                                }
                                if (!postDatabase.isInitialised()) {
                                    postDatabase.initialiseer();
                                }

                                if (postDatabase.isInitialised()) {
                                    postDatabase.insertPost(Post);

                                    JOptionPane.showMessageDialog(updatePostPan,"Post saved");

                                    newPostPan.removeAll();
                                    newPostPanel();
                                }


                            }else{JOptionPane.showMessageDialog(updatePostPan, "mail is empty");}
                        }else{JOptionPane.showMessageDialog(updatePostPan, "phone is empty");}
                    }else {JOptionPane.showMessageDialog(updatePostPan, "adress is empty");}
                }else{JOptionPane.showMessageDialog(updatePostPan,"post is empty");}

            }
        });
    }

    public  void deletePostPanel(){

        buildArrays();
        //{"Post", "adres", "mail", "telefoon"};

        JPanel uperPanel=new JPanel(), outputPanel= new JPanel(new GridBagLayout()),updatePanel= new JPanel();
        JButton findBut = new JButton("find"), deleteBut= new JButton("delete");

        JLabel nameLabel=new JLabel("post : "), adressLabel=new JLabel("adress : "), mailLabel=new JLabel("mail : "), phoneLabel=new JLabel("phone : ");

        JLabel namefield = new JLabel(),adressField  = new JLabel(),mailField = new JLabel(),phoneField = new JLabel();

        JComboBox jComboBox= new JComboBox(new DefaultComboBoxModel(PostArrayList.toArray()));

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

        deletePostPan.add(uperPanel, BorderLayout.NORTH);
        deletePostPan.add(outputPanel, BorderLayout.CENTER);
        deletePostPan.add(updatePanel, BorderLayout.SOUTH);

        findBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = jComboBox.getSelectedIndex();

                int record = PostArrayListID.get(i);

                if (postDatabase == null) {
                    postDatabase = new PostDatabase();
                }

                // Initialiseer de postDatabase alleen als het moet
                if (!postDatabase.isInitialised()) {
                    postDatabase.initialiseer();
                }

                // Alleen als de Posten database is geinitialiseerd dan verder
                if (postDatabase.isInitialised()) {


                    namefield.setText(postDatabase.selectPost(record).getPost_naam());
                    adressField.setText(postDatabase.selectPost(record).getPost_adres());
                    mailField.setText(postDatabase.selectPost(record).getPost_mail());
                    phoneField.setText(postDatabase.selectPost(record).getPost_telefoon());
                }

            }});
        deleteBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int i = jComboBox.getSelectedIndex();


                if (JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                    int record = PostArrayListID.get(i);

                    RoosterDatabase roosterDatabase = new RoosterDatabase();

                    if (!roosterDatabase.isInitialised()) {
                        roosterDatabase.initialiseer();
                    }

                    if (roosterDatabase.isInitialised()) {
                        int lastindex = roosterDatabase.selectAll().get(roosterDatabase.selectAll().size() - 1).getId();
                        for (int a = 0; a < lastindex + 1; a++) {
                            if (postDatabase.selectPost(record).getId() == roosterDatabase.selectRooster(a).getPost_fk()) {
                                roosterDatabase.deleteRooster(a);
                            }

                        }

                        // Post.Post database instantie is alleen 1 keer nodig
                        if (postDatabase == null) {
                            postDatabase = new PostDatabase();
                        }

                        // Initialiseer de postDatabase alleen als het moet
                        if (!postDatabase.isInitialised()) {
                            postDatabase.initialiseer();
                        }

                        // Alleen als de Posten database is geinitialiseerd dan verder
                        if (postDatabase.isInitialised()) {
                            postDatabase.deletePost(record);


                            deletePostPan.removeAll();
                            deletePostPanel();
                            JOptionPane.showMessageDialog(deletePostPan, "Post deleted");

                        } else {
                            JOptionPane.showMessageDialog(deletePostPan, "Post not deleted");
                        }

                    }}
            }});
    }

    public void buildArrays(){

        PostArraylistObject.clear();
        PostArrayList.clear();
        PostArrayListID.clear();

        if (postDatabase == null) {
            postDatabase = new PostDatabase();
        }

        if (!postDatabase.isInitialised()) {
            postDatabase.initialiseer();
        }

        Iterator<Post> itrPost = postDatabase.selectAll().iterator();
        while (itrPost.hasNext()) {
            Post i = (Post) itrPost.next();

            PostArraylistObject.add(i);
            PostArrayList.add(i.getPost_naam());
            PostArrayListID.add(i.getId());

        }
    }



}

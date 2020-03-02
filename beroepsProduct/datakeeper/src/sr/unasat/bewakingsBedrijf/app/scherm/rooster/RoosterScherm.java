package sr.unasat.bewakingsBedrijf.app.scherm.rooster;

//import javafx.scene.shape.Circle;
import sr.unasat.bewakingsBedrijf.app.databaseConnection.bewaker.Bewaker;
import sr.unasat.bewakingsBedrijf.app.databaseConnection.bewaker.BewakerDatabase;
import sr.unasat.bewakingsBedrijf.app.databaseConnection.dag.Dag;
import sr.unasat.bewakingsBedrijf.app.databaseConnection.dag.DagDatabase;
import sr.unasat.bewakingsBedrijf.app.databaseConnection.post.Post;
import sr.unasat.bewakingsBedrijf.app.databaseConnection.post.PostDatabase;
import sr.unasat.bewakingsBedrijf.app.databaseConnection.rooster.Rooster;
import sr.unasat.bewakingsBedrijf.app.databaseConnection.rooster.RoosterDatabase;
import sr.unasat.bewakingsBedrijf.app.scherm.algemeen.StartScherm;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.interfaces.RSAKey;
import java.util.*;

/**
 * Created by Jair on 5/16/2016.
 */
public class RoosterScherm extends JFrame {

    RoosterDatabase roosterDatabase = null;
    PostDatabase postDatabase = null;
    BewakerDatabase bewakerDatabase = null;
    DagDatabase dagDatabase = null;

    JPanel bewakerRoosterPan = new JPanel(new BorderLayout()), allRoosterPan = new JPanel(), homePan = new JPanel();

    JTabbedPane jTabbedPane = new JTabbedPane(SwingConstants.LEFT);

    ArrayList<Rooster> roosterArrayListObject = new ArrayList<>();
    ArrayList<Integer> roosterArrayListID = new ArrayList<>();

    ArrayList<Post> postArrayListObject = new ArrayList<>();
    ArrayList<String> postArraylist = new ArrayList<>();

    ArrayList<Dag> dagArrayListObject = new ArrayList<>();
    ArrayList<String> dagtArraylist = new ArrayList<>();

    ArrayList<Bewaker> bewakerArrayListObject = new ArrayList<>();

    ArrayList<String> bewakerArrayListNames = new ArrayList<>();

    JComboBox dagBox, postBox, bewakerBox;

    private DefaultTableModel listTableModel;
    private JTable outputTable;
    ImageIcon icon = new ImageIcon(getClass().getResource("bewaker.png"));
    ImageIcon homeIcon = new ImageIcon(getClass().getResource("home.png"));
    ImageIcon roostersIcon = new ImageIcon(getClass().getResource("rooster.png"));

    public RoosterScherm() {
        //jTabbedPane.setBackground(new Color(10,126,192));
        //jTabbedPane.setIconAt(0,icon);
        // jTabbedPane.add(" all roosters ",allRoosterPan);
        //jTabbedPane.add(" bewaker rooster ",bewakerRoosterPan);

        jTabbedPane.setBackground(new Color(148, 178, 173));
        jTabbedPane.addTab(" all roosters ", roostersIcon, allRoosterPan);
        jTabbedPane.addTab("bewaker rooster", icon, bewakerRoosterPan);

        jTabbedPane.addTab("home", homeIcon, homePan);
        allroostersPanel();
        add(jTabbedPane);
        getContentPane().setBackground(new Color(7, 84, 127));

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setSize(900, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        jTabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                if (allRoosterPan.isShowing()) {

                    allroostersPanel();
                } else {
                    allRoosterPan.removeAll();
                }

                if (bewakerRoosterPan.isShowing()) {
                    bewakerRoosterPanel();
                } else {
                    bewakerRoosterPan.removeAll();
                }
                if (homePan.isShowing()) {
                    setVisible(false);
                    new StartScherm();
                }
            }
        });
    }

    public void allroostersPanel() {

        buildArrays();

        JPanel buttonPanel = new JPanel(new BorderLayout()), outputPanel = new JPanel(), viewAllButPanel = new JPanel(), buttonPanelOtther = new JPanel(new GridBagLayout());
        JButton viewAllBut = new JButton("view all"), deleteBut = new JButton("delete selected rooster"),
                updateBut = new JButton("update selected rooster"), addBut = new JButton("add new rooster"),
                saveBut = new JButton("save added rooster");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets.set(20, 20, 20, 20);

        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));

        listTableModel = new DefaultTableModel();
        String[] colnames = {"id", "bewaker", "post", "dag", "shift"};
        Vector colnamesV = new Vector(Arrays.asList(colnames));

        outputTable = new JTable(null, colnamesV);
        JScrollPane tablePanel = new JScrollPane(outputTable);
        tablePanel.setPreferredSize(new Dimension(800, 150));
        outputPanel.add(tablePanel);

        outputTable.setBackground(new Color(148, 178, 173));

        //creating comboboxes for table cell editors


        dagBox = new JComboBox(new DefaultComboBoxModel<>(dagtArraylist.toArray()));
        postBox = new JComboBox(new DefaultComboBoxModel<>(postArraylist.toArray()));
        bewakerBox = new JComboBox(new DefaultComboBoxModel<>(bewakerArrayListNames.toArray()));

        //{"id","bewaker", "post", "dag","shift"};

        TableColumn bewColumn = outputTable.getColumnModel().getColumn(1);
        bewColumn.setCellEditor(new DefaultCellEditor(bewakerBox));

        TableColumn postColumn = outputTable.getColumnModel().getColumn(2);
        postColumn.setCellEditor(new DefaultCellEditor(postBox));

        TableColumn dagColumn = outputTable.getColumnModel().getColumn(3);
        dagColumn.setCellEditor(new DefaultCellEditor(dagBox));


        viewAllButPanel.add(viewAllBut);

        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanelOtther.add(addBut, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonPanelOtther.add(saveBut, gbc);

        gbc.gridx = 2;
        buttonPanelOtther.add(updateBut, gbc);

        gbc.gridx = 3;
        buttonPanelOtther.add(deleteBut, gbc);

        buttonPanelOtther.setBackground(new Color(7, 84, 127));
        viewAllButPanel.setBackground(new Color(7, 84, 127));


        buttonPanel.add(viewAllButPanel, BorderLayout.NORTH);
        buttonPanel.add(buttonPanelOtther, BorderLayout.CENTER);
        allRoosterPan.setLayout(new BorderLayout());
        allRoosterPan.add(buttonPanel, BorderLayout.NORTH);
        allRoosterPan.add(outputPanel, BorderLayout.CENTER);


        viewAllBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectAllBut();
            }
        });

        addBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listTableModel.addRow(new Object[]{null, null, null, null});
            }
        });

        saveBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    if (outputTable.getValueAt(outputTable.getSelectedRow(), 0).toString() != null) {

                        JOptionPane.showMessageDialog(saveBut, " be sure to select the new row before saving");
                    }
                } catch (Exception e1) {

                    //if(listTableModel.getValueAt(i, 2).equals(postArrayListObject.get(i).getDag()))
                    try {
                        Rooster rooster = new Rooster();
                        roosterDatabase = new RoosterDatabase();
                        bewakerDatabase = new BewakerDatabase();
                        postDatabase = new PostDatabase();

                        if (!bewakerDatabase.isInitialised()) {
                            bewakerDatabase.initialiseer();
                        }
                        if (!roosterDatabase.isInitialised()) {
                            roosterDatabase.initialiseer();
                        }
                        if (!postDatabase.isInitialised()) {
                            postDatabase.initialiseer();
                        }
                        if (!dagDatabase.isInitialised()) {
                            dagDatabase.initialiseer();
                        }

                        // {"id","bewaker", "post", "dag","shift"};

                        buildArrays();
                        int selectedRow = outputTable.getSelectedRow();
                        for (int i = 0; i < bewakerArrayListObject.size(); i++) {

                            if (outputTable.getCellEditor(selectedRow, 1).getCellEditorValue().toString().equals(bewakerArrayListObject.get(i).getBewaker_naam())) {

                                rooster.setBewaker_fk(bewakerDatabase.selectAll().get(i).getId());
                                i = bewakerArrayListObject.size() + 1;
                            }
                        }


                        for (int i = 0; i < postArrayListObject.size(); i++) {
                            if (outputTable.getCellEditor(selectedRow, 2).getCellEditorValue().toString().equals(postArrayListObject.get(i).getPost_naam())) {


                                rooster.setPost_fk(postDatabase.selectAll().get(i).getId());
                                i = postArrayListObject.size() + 1;
                            }
                        }

                        for (int i = 0; i < dagArrayListObject.size(); i++) {
                            if (outputTable.getCellEditor(selectedRow, 3).getCellEditorValue().toString().equals(dagArrayListObject.get(i).getDag())) {

                                rooster.setDag_fk(dagDatabase.selectAll().get(i).getId());
                                i = dagArrayListObject.size() + 1;
                            }
                        }
                        // {"id","bewaker", "post", "dag","shift"};

                        if (!roosterDatabase.isInitialised()) {
                            roosterDatabase.initialiseer();
                        }

                        // Alleen als de roosteren database is geinitialiseerd dan verder
                        if (roosterDatabase.isInitialised()) {
                            roosterDatabase.insertRooster(rooster);
                            JOptionPane.showMessageDialog(saveBut, "rooster succesfully added");

                        }


                        // Initialiseer de studentDatabase alleen als het moet

                    } catch (Exception e2) {
                        JOptionPane.showMessageDialog(saveBut, " error ?????");
                        System.out.println(e2);
                    }
                }

            }
        });

        updateBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                //if(listTableModel.getValueAt(i, 2).equals(postArrayListObject.get(i).getDag()))

                Rooster rooster = new Rooster();
                roosterDatabase = new RoosterDatabase();
                bewakerDatabase = new BewakerDatabase();
                postDatabase = new PostDatabase();

                if (!bewakerDatabase.isInitialised()) {
                    bewakerDatabase.initialiseer();
                }
                if (!roosterDatabase.isInitialised()) {
                    roosterDatabase.initialiseer();
                }
                if (!postDatabase.isInitialised()) {
                    postDatabase.initialiseer();
                }
                // {"id","bewaker", "post", "dag","shift"};
                buildArrays();
                int selectedRow = outputTable.getSelectedRow();
                for (int i = 0; i < bewakerArrayListObject.size(); i++) {

                    if (outputTable.getCellEditor(selectedRow, 1).getCellEditorValue().toString().equals(bewakerArrayListObject.get(i).getBewaker_naam())) {

                        rooster.setBewaker_fk(bewakerDatabase.selectAll().get(i).getId());
                        i = bewakerArrayListObject.size() + 1;
                    }
                }


                for (int i = 0; i < postArrayListObject.size(); i++) {
                    if (outputTable.getCellEditor(selectedRow, 2).getCellEditorValue().toString().equals(postArrayListObject.get(i).getPost_naam())) {


                        rooster.setPost_fk(postDatabase.selectAll().get(i).getId());
                        i = postArrayListObject.size() + 1;
                    }
                }

                for (int i = 0; i < dagArrayListObject.size(); i++) {
                    if (outputTable.getCellEditor(selectedRow, 3).getCellEditorValue().toString().equals(dagArrayListObject.get(i).getDag())) {

                        rooster.setDag_fk(dagDatabase.selectAll().get(i).getId());
                        i = dagArrayListObject.size() + 1;
                    }
                }
                int i = outputTable.getSelectedRow();
                Object roosterIdObject = outputTable.getValueAt(i, 0);
                String roosterIdString = roosterIdObject.toString();

                int roosterID = Integer.parseInt(roosterIdString);

                rooster.setId(roosterID);

                // Initialiseer de roosterDatabase alleen als het moet
                if (!roosterDatabase.isInitialised()) {
                    roosterDatabase.initialiseer();
                }

                if (roosterDatabase.isInitialised()) {
                    roosterDatabase.updateRooster(rooster);
                    JOptionPane.showMessageDialog(updateBut, "rooster update succesful");
                } else {
                    JOptionPane.showMessageDialog(updateBut, "rooster update succesful");
                }

            }
        });

        deleteBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int result = JOptionPane.showConfirmDialog(null,
                        "Are you sure to delete rooster?", null, JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    int i = outputTable.getSelectedRow();
                    Object roosterIdObject = outputTable.getValueAt(i, 0);
                    String roosterIdString = roosterIdObject.toString();

                    int roosterID = Integer.parseInt(roosterIdString);

                    if (roosterDatabase == null) {
                        roosterDatabase = new RoosterDatabase();
                    }

                    // Initialiseer de roosterDatabase alleen als het moet
                    if (!roosterDatabase.isInitialised()) {
                        roosterDatabase.initialiseer();
                    }

                    // Alleen als de studenten database is geinitialiseerd dan verder
                    if (roosterDatabase.isInitialised()) {
                        roosterDatabase.deleteRooster(roosterID);

                        listTableModel.removeRow(i);

                        JOptionPane.showMessageDialog(deleteBut, "rooster deleted");
                        allRoosterPan.removeAll();
                        allroostersPanel();
                        selectAllBut();
                    }
                } else {
                    JOptionPane.showMessageDialog(deleteBut, "rooster is not deleted");
                }

            }
        });

    }

    public void bewakerRoosterPanel() {

        JPanel upperPanel = new JPanel(new GridBagLayout());
        JPanel tablePanel = new JPanel();
        JButton go = new JButton("go");

        upperPanel.setBackground(new Color(7, 84, 127));
        tablePanel.setBackground(new Color(7, 84, 127));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets.set(5, 0, 5, 0);

        JComboBox jComboBox = new JComboBox(new DefaultComboBoxModel(bewakerArrayListNames.toArray()));

        JLabel viewRoosterLabel = new JLabel("select bewaker to view rooster");
        viewRoosterLabel.setForeground(Color.white);

        listTableModel = new DefaultTableModel();
        String[] colnames = {"post", "dag", "tijd"};
        Vector colnamesV = new Vector(Arrays.asList(colnames));
        JTable outputTable = new JTable(null, colnamesV);
        JScrollPane tableScroll = new JScrollPane(outputTable);
        tablePanel.setPreferredSize(new Dimension(800, 150));
        tablePanel.add(tableScroll);

        outputTable.setBackground(new Color(148, 178, 173));
        gbc.gridx = 0;
        gbc.gridy = 0;
        upperPanel.add(viewRoosterLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = -1;
        upperPanel.add(jComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = -2;
        upperPanel.add(go, gbc);

        bewakerRoosterPan.add(upperPanel, BorderLayout.NORTH);
        bewakerRoosterPan.add(tablePanel, BorderLayout.CENTER);

        go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buildArrays();

                int i = jComboBox.getSelectedIndex();
                int record = bewakerArrayListObject.get(i).getId();

                if (roosterDatabase == null) {
                    roosterDatabase = new RoosterDatabase();
                }

                if (!roosterDatabase.isInitialised()) {
                    roosterDatabase.initialiseer();
                }

                if (roosterDatabase.isInitialised()) {

                    listTableModel = (DefaultTableModel) outputTable.getModel();
                    listTableModel.setRowCount(0);

                    if (!bewakerDatabase.isInitialised()) {
                        bewakerDatabase.initialiseer();
                    }


                    int lastindex = roosterArrayListObject.get(roosterArrayListObject.size() - 1).getId();
                    for (int a = 0; a < lastindex + 1; a++) {
                        if (bewakerDatabase.selectBewaker(record).getId() == roosterDatabase.selectRooster(a).getBewaker_fk()) {

                            String[] colData = new String[3];
                            colData[0] = roosterDatabase.selectRooster(a).getPost().getPost_naam();
                            colData[1] = roosterDatabase.selectRooster(a).getDag().getDag();
                            colData[2] = roosterDatabase.selectRooster(a).getDag().getShift();

                            listTableModel.addRow(colData);
                        }
                    }

                    outputTable.setModel(listTableModel);

                }
            }
        });

    }

    public void selectAllBut() {

        if (roosterDatabase == null) {
            roosterDatabase = new RoosterDatabase();
        }

        // Initialiseer de roosterDatabase alleen als het moet
        if (!roosterDatabase.isInitialised()) {
            roosterDatabase.initialiseer();
        }

        if (roosterDatabase.isInitialised()) {
            java.util.List outputList = roosterDatabase.selectAll();

            listTableModel = (DefaultTableModel) outputTable.getModel();
            listTableModel.setRowCount(0);
            Iterator itrTable = outputList.iterator();
            while (itrTable.hasNext()) {
                Rooster record = (Rooster) itrTable.next();

                String[] colData = new String[5];


                colData[0] = Integer.valueOf(record.getId()).toString();
                colData[1] = record.getBewaker().getBewaker_naam().toString();
                colData[2] = record.getPost().getPost_naam().toString();
                colData[3] = record.getDag().getDag().toString();
                colData[4] = record.getDag().getShift().toString();


                listTableModel.addRow(colData);
            }
            outputTable.setModel(listTableModel);
        }
    }

    public void homePanel() {


    }

    public void buildArrays() {

        roosterArrayListID.clear();
        roosterArrayListObject.clear();
        bewakerArrayListNames.clear();
        bewakerArrayListObject.clear();
        postArraylist.clear();
        postArrayListObject.clear();
        dagtArraylist.clear();
        dagArrayListObject.clear();

        if (roosterDatabase == null) {
            roosterDatabase = new RoosterDatabase();
        }

        if (!roosterDatabase.isInitialised()) {
            roosterDatabase.initialiseer();
        }

        if (postDatabase == null) {
            postDatabase = new PostDatabase();
        }

        if (!postDatabase.isInitialised()) {
            postDatabase.initialiseer();
        }

        if (bewakerDatabase == null) {
            bewakerDatabase = new BewakerDatabase();
        }

        if (!bewakerDatabase.isInitialised()) {
            bewakerDatabase.initialiseer();
        }

        if (dagDatabase == null) {
            dagDatabase = new DagDatabase();
        }

        if (!dagDatabase.isInitialised()) {
            dagDatabase.initialiseer();
        }

        // creating the roosterArraylists


        Iterator<Rooster> itrRooster = roosterDatabase.selectAll().iterator();
        while (itrRooster.hasNext()) {
            Rooster i = (Rooster) itrRooster.next();

            roosterArrayListObject.add(i);

        }


        //creating the postArraylists

        Iterator<Post> itrPost = postDatabase.selectAll().iterator();
        while (itrPost.hasNext()) {
            Post i = (Post) itrPost.next();

            postArrayListObject.add(i);
            postArraylist.add(i.getPost_naam());

        }


        //creating the bewakerArraylists
        Iterator<Bewaker> itrBewaker = bewakerDatabase.selectAll().iterator();
        while (itrBewaker.hasNext()) {
            Bewaker i = (Bewaker) itrBewaker.next();

            bewakerArrayListObject.add(i);
            bewakerArrayListNames.add(i.getBewaker_naam());

        }

        //creating the dagLists
        Iterator<Dag> itrDag = dagDatabase.selectAll().iterator();
        while (itrDag.hasNext()) {
            Dag i = (Dag) itrDag.next();

            dagArrayListObject.add(i);
            dagtArraylist.add(i.getDag());

        }
    }

}
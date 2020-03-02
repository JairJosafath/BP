package sr.unasat.bewakingsBedrijf.app.databaseConnection.rooster;
import sr.unasat.bewakingsBedrijf.app.databaseConnection.bewaker.Bewaker;
import sr.unasat.bewakingsBedrijf.app.databaseConnection.bewaker.BewakerDatabase;
import sr.unasat.bewakingsBedrijf.app.databaseConnection.dag.DagDatabase;
import sr.unasat.bewakingsBedrijf.app.databaseConnection.post.Post;
import sr.unasat.bewakingsBedrijf.app.databaseConnection.post.PostDatabase;

import java.io.BufferedWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jair on 5/16/2016.
 */
public class RoosterDatabase {
    private Connection connection=null;
    private boolean initialised= false;
    Bewaker bewaker = new Bewaker();
    Post post = new Post();

    BewakerDatabase bewakerDatabase= new BewakerDatabase();
    PostDatabase postDatabase= new PostDatabase();
    DagDatabase dagDatabase= new DagDatabase();


    public void initialiseer() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/bewakingsbedrijf?user=root&password=181292");

            initialised = true;

        } catch (ClassNotFoundException e) {
            System.out.println("error bij initialiseren, class niet gevonden");
        } catch (SQLException e) {
            System.out.println("sql fout ontstaan");
            e.printStackTrace();
        }
    }

    public List <Rooster> selectAll(){

        List outputList = new ArrayList();
        Statement statement = null;
        ResultSet resultSet = null;
        if (bewakerDatabase == null){
            bewakerDatabase = new BewakerDatabase();
        }

        if (!bewakerDatabase.isInitialised()){
            bewakerDatabase.initialiseer();
        }
        if (postDatabase == null){
            postDatabase = new PostDatabase();
        }

        if (!postDatabase.isInitialised()){
            postDatabase.initialiseer();
        }
        if (dagDatabase == null){
            dagDatabase = new DagDatabase();
        }

        if (!dagDatabase.isInitialised()){
            dagDatabase.initialiseer();
        }



        try{
            statement = connection.createStatement();

            resultSet = statement.executeQuery("select * from bewakingsbedrijf.rooster");

            while (resultSet.next()) {

                Rooster rooster= new Rooster();

                rooster.setId(resultSet.getInt("id"));

                rooster.setDag_fk(resultSet.getInt("dag_fk"));
                rooster.setDag(dagDatabase.selectRecord(rooster.getDag_fk()));


                rooster.setBewaker_fk(resultSet.getInt("bewaker_fk"));
                rooster.setBewaker(bewakerDatabase.selectBewaker(resultSet.getInt("bewaker_fk")));

                rooster.setPost_fk(resultSet.getInt("post_fk"));
                rooster.setPost(postDatabase.selectPost(resultSet.getInt("post_fk")));

                outputList.add(rooster);
            }


        }catch (SQLException e){
            System.out.println("fout bij selectAll method");
        }
        return outputList;
    }

    public Rooster selectRooster(int roosterid){
        PreparedStatement preparedStatement  = null;
        ResultSet resultSet = null;
        Rooster rooster= new Rooster();

        if (bewakerDatabase == null){
            bewakerDatabase = new BewakerDatabase();
        }

        if (!bewakerDatabase.isInitialised()){
            bewakerDatabase.initialiseer();
        }
        if (postDatabase == null){
            postDatabase = new PostDatabase();
        }

        if (!postDatabase.isInitialised()){
            postDatabase.initialiseer();
        }
        try {

            preparedStatement = connection.prepareStatement("select * from bewakingsbedrijf.rooster where id = ?");

            preparedStatement.setInt(1, roosterid);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                rooster.setId(resultSet.getInt("id"));
                rooster.setDag_fk(resultSet.getInt("dag_fk"));
                rooster.setDag(dagDatabase.selectRecord(rooster.getDag_fk()));

                rooster.setBewaker_fk(resultSet.getInt("bewaker_fk"));
                rooster.setBewaker(bewakerDatabase.selectBewaker(resultSet.getInt("bewaker_fk")));

                rooster.setPost_fk(resultSet.getInt("post_fk"));
                rooster.setPost(postDatabase.selectPost(resultSet.getInt("post_fk")));



                System.out.println(rooster);

            }
        } catch (SQLException e) {
            System.out.println("Er is een SQL fout ontstaan tijdens de select statement!");
        }

        return rooster;
    }

    public int updateRooster(Rooster rooster){

        PreparedStatement preparedStatement  = null;
        int result = 0;

        try {
            preparedStatement = connection.prepareStatement("update rooster set " +
                    " dag_fk = ?," +
                    "bewaker_fk = ?, " +
                    "post_fk = ? " +
                    " where id = ?");
            preparedStatement.setInt(1,rooster.getDag_fk());

            preparedStatement.setInt(4,rooster.getId());
            preparedStatement.setInt(2, rooster.getBewaker_fk());
            preparedStatement.setInt(3,rooster.getPost_fk());

            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Er is een SQL fout tijdens het updaten van een rooster!");
            e.printStackTrace();
        }
        return result;
    }

    public int deleteRooster(int roosterid){

        PreparedStatement preparedStatement  = null;
        int result = 0;

        try {
            preparedStatement = connection.prepareStatement("delete from rooster where id = ?");
            preparedStatement.setInt(1, roosterid);

            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Er is een SQL fout tijdens deleten van een rooster!");
            e.printStackTrace();
        }

        return result;

    }

    public int insertRooster(Rooster rooster){
        PreparedStatement psRooster  = null;

        int result = 0;


        try {

            psRooster = connection.prepareStatement("insert into rooster values (NULL,?,?,?)");

            psRooster.setInt(1, rooster.getBewaker_fk());
            psRooster.setInt(2, rooster.getPost_fk());
            psRooster.setInt(3,rooster.getDag_fk());


            result = psRooster.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Er is een SQL fout tijdens het inserten van een nieuwe rooster!");
            e.printStackTrace();
        }

        return result;
    }

    public void terminate(){
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Er is een SQL fout tijdens sluiten van de connectie!");
            e.printStackTrace();
        }
    }


    public boolean isInitialised() {
        return initialised;
    }

    public void setInitialised(boolean initialised) {
        this.initialised = initialised;
    }
}

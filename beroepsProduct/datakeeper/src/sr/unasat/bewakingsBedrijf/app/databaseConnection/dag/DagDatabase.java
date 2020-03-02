package sr.unasat.bewakingsBedrijf.app.databaseConnection.dag;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jair on 5/30/2016.
 */
public class DagDatabase {

    private Connection connect = null;
    private boolean initialised = false;

    public void initialiseer() {

        try {

            // Onderstaande zoekt de juist class op uit de library en laad het in JVM
            Class.forName("com.mysql.jdbc.Driver");
            // De connectie wordt vervolgens gemaakt naar de database middels de juiste authenticatie
            connect = DriverManager.getConnection("jdbc:mysql://localhost/bewakingsbedrijf?user=root&password=181292");

            initialised = true;

        } catch (ClassNotFoundException e) {
            System.out.println("De class is niet gevonden!");
        } catch (SQLException e) {
            System.out.println("Er is een SQL fout ontstaan!");
            e.printStackTrace();
        }
    }

    public List<Dag> selectAll(){

        List outputList = new ArrayList();
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Construeer een statement voor het uitvoeren van een SQL Query
            statement = connect.createStatement();
            // Voer de SQL statement uit en verzamel de output in de resultset
            resultSet = statement.executeQuery("select * from bewakingsbedrijf.dag");

            while (resultSet.next()) {
                Dag dag= new Dag();

                dag.setId(resultSet.getInt("id"));

                dag.setDag(resultSet.getString("dag"));

                dag.setShift(resultSet.getString("shift"));

                //System.out.println(dag);
                outputList.add(dag);
            }
        } catch (SQLException e) {
            System.out.println("Er is een SQL fout ontstaan tijdens de select * statement!");
        }

        return outputList;
    }

    public Dag selectRecord(int recordId){

        PreparedStatement preparedStatement  = null;
        ResultSet resultSet = null;
        Dag dag = new Dag();

        try {
            // Statements allow to issue SQL queries to the database
            preparedStatement = connect.prepareStatement("select * from bewakingsbedrijf.dag where id = ?");
            // Result set get the result of the SQL query
            preparedStatement.setInt(1, recordId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // It is possible to get the columns via name
                // also possible to get the columns via the column number
                // which starts at 1
                // e.g. resultSet.getSTring(2);

                // Maak een student instantie en print deze instantie
                dag.setId( resultSet.getInt("id"));

                dag.setDag(resultSet.getString("dag"));

                dag.setShift(resultSet.getString("shift"));


                //System.out.println(dag);
            }
        } catch (SQLException e) {
            System.out.println("Er is een SQL fout ontstaan tijdens de select statement!");
        }

        return dag;
    }

    public boolean isInitialised() {
        return initialised;
    }

    public void setInitialised(boolean initialised) {
        this.initialised = initialised;
    }

    public void terminate(){
        try {
            connect.close();
        } catch (SQLException e) {
            System.out.println("Er is een SQL fout tijdens sluiten van de connectie!");
            e.printStackTrace();
        }
    }

}

package sr.unasat.bewakingsBedrijf.app.databaseConnection.bewaker;

/**
 * Created by Jair on 5/16/2016.
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BewakerDatabase {

    private Connection connection=null;
    private boolean initialised= false;

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

    public List <Bewaker> selectAll(){

        List outputList = new ArrayList();
        Statement statement = null;
        ResultSet resultSet = null;

        try{
            statement = connection.createStatement();

            resultSet = statement.executeQuery("select * from bewakingsbedrijf.bewaker");

            while (resultSet.next()) {

                Bewaker bewaker= new Bewaker();
                bewaker.setId(resultSet.getInt("id"));
                bewaker.setBewaker_naam(resultSet.getString("bewaker_naam"));
                bewaker.setBewaker_adres(resultSet.getString("bewaker_adres"));
                bewaker.setBewaker_mail(resultSet.getString("bewaker_mail"));
                bewaker.setBewaker_telefoon(resultSet.getString("bewaker_telefoon"));

                System.out.println(bewaker);
                outputList.add(bewaker);
            }


        }catch (SQLException e){
            System.out.println("fout bij selectAll method");
        }
        return outputList;
    }

    public Bewaker selectBewaker(int bewakerId){
        PreparedStatement preparedStatement  = null;
        ResultSet resultSet = null;
        Bewaker bewaker= new Bewaker();

        try {

            preparedStatement = connection.prepareStatement("select * from bewakingsbedrijf.bewaker where id = ?");

            preparedStatement.setInt(1, bewakerId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                bewaker.setId(resultSet.getInt("id"));
                bewaker.setBewaker_naam(resultSet.getString("bewaker_naam"));
                bewaker.setBewaker_adres(resultSet.getString("bewaker_adres"));
                bewaker.setBewaker_mail(resultSet.getString("bewaker_mail"));
                bewaker.setBewaker_telefoon(resultSet.getString("bewaker_telefoon"));

                System.out.println(bewaker);

            }
        } catch (SQLException e) {
            System.out.println("Er is een SQL fout ontstaan tijdens de select statement!");
        }

        return bewaker;
    }

    public int updateBewaker(Bewaker bewaker){

        PreparedStatement preparedStatement  = null;
        int result = 0;

        try {
            preparedStatement = connection.prepareStatement("update bewaker set " +
                    " bewaker_naam = ?, " +
                    " bewaker_adres = ?, " +
                    " bewaker_mail = ?, " +
                    " bewaker_telefoon = ? " +
                    " where id = ?");

            preparedStatement.setString(1,bewaker.getBewaker_naam());
            preparedStatement.setString(2,bewaker.getBewaker_adres());
            preparedStatement.setString(3,bewaker.getBewaker_mail());
            preparedStatement.setString(4,bewaker.getBewaker_telefoon());
            preparedStatement.setInt(5, bewaker.getId());

            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Er is een SQL fout tijdens het updaten van een bewaker!");
            e.printStackTrace();
        }
        return result;
    }

    public int deleteBewaker(int bewakerId){

        PreparedStatement preparedStatement  = null;
        int result = 0;

        try {
            preparedStatement = connection.prepareStatement("delete from bewaker where id = ?");
            preparedStatement.setInt(1, bewakerId);

            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Er is een SQL fout tijdens deleten van een bewaker!");
            e.printStackTrace();
        }

        return result;

    }

    public int insertBewaker(Bewaker bewaker){
        PreparedStatement preparedStatement  = null;
        int result = 0;

        try {
            preparedStatement = connection.prepareStatement("insert into bewaker values (NULL, ?,?,?,?)");

            preparedStatement.setString(1,bewaker.getBewaker_naam());
            preparedStatement.setString(2,bewaker.getBewaker_adres());
            preparedStatement.setString(3,bewaker.getBewaker_mail());
            preparedStatement.setString(4,bewaker.getBewaker_telefoon());

            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Er is een SQL fout tijdens het inserten van een nieuwe bewaker!");
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

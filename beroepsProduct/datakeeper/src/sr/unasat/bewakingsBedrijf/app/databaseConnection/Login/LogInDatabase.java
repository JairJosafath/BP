package sr.unasat.bewakingsBedrijf.app.databaseConnection.Login;

import java.sql.*;

/**
 * Created by Jair on 5/27/2016.
 */
public class LogInDatabase {

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

    public Login selectLogIn(){
        PreparedStatement preparedStatement  = null;
        ResultSet resultSet = null;
        Login login= new Login();

        try {

            preparedStatement = connection.prepareStatement("select * from bewakingsbedrijf.administrators where id = ?");

            preparedStatement.setInt(1, 3);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {


                login.setUsername(resultSet.getString("username"));
                login.setPassword(resultSet.getString("password"));


            }
        } catch (SQLException e) {
            System.out.println("Er is een SQL fout ontstaan tijdens de select statement!");
        }

        return login;
    }

    public boolean isInitialised() {
        return initialised;
    }

    public void setInitialised(boolean initialised) {
        this.initialised = initialised;
    }

    public void terminate(){
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Er is een SQL fout tijdens sluiten van de connectie!");
            e.printStackTrace();
        }
    }
}


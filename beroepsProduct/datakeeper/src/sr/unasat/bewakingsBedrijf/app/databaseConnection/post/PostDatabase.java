package sr.unasat.bewakingsBedrijf.app.databaseConnection.post;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jair on 5/16/2016.
 */
public class PostDatabase {

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

    public List<Post> selectAll(){

        List outputList = new ArrayList();
        Statement statement = null;
        ResultSet resultSet = null;

        try{
            statement = connection.createStatement();

            resultSet = statement.executeQuery("select * from bewakingsbedrijf.post");

            while (resultSet.next()) {

               Post post = new Post();
                post.setId(resultSet.getInt("id"));
                post.setPost_naam(resultSet.getString("post_naam"));
                post.setPost_adres(resultSet.getString("post_adres"));
                post.setPost_mail(resultSet.getString("post_mail"));
                post.setPost_telefoon(resultSet.getString("post_telefoon"));

                System.out.println(post);
                outputList.add(post);
            }


        }catch (SQLException e){
            System.out.println("fout bij selectAll method");
        }
        return outputList;
    }

    public Post selectPost(int postid){
        PreparedStatement preparedStatement  = null;
        ResultSet resultSet = null;
        Post post= new Post();

        try {

            preparedStatement = connection.prepareStatement("select * from bewakingsbedrijf.post where id = ?");

            preparedStatement.setInt(1, postid);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                post.setId(resultSet.getInt("id"));

                post.setPost_naam(resultSet.getString("post_naam"));
                post.setPost_adres(resultSet.getString("post_adres"));
                post.setPost_mail(resultSet.getString("post_mail"));
                post.setPost_telefoon(resultSet.getString("post_telefoon"));

                System.out.println(post);

            }
        } catch (SQLException e) {
            System.out.println("Er is een SQL fout ontstaan tijdens de select statement!");
        }

        return post;
    }

    public int updatePost(Post post){

        PreparedStatement preparedStatement  = null;
        int result = 0;

        try {
            preparedStatement = connection.prepareStatement("update post set " +
                    " post_naam = ?, " +
                    " post_adres = ?, " +
                    " post_mail = ?, " +
                    " post_telefoon = ? " +
                    " where id = ?");

            preparedStatement.setString(1,post.getPost_naam());
            preparedStatement.setString(2,post.getPost_adres());
            preparedStatement.setString(3,post.getPost_mail());
            preparedStatement.setString(4,post.getPost_telefoon());
            preparedStatement.setInt(5, post.getId());

            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Er is een SQL fout tijdens het updaten van een post!");
            e.printStackTrace();
        }
        return result;
    }

    public int deletePost(int postid){

        PreparedStatement preparedStatement  = null;
        int result = 0;

        try {
            preparedStatement = connection.prepareStatement("delete from post where id = ?");
            preparedStatement.setInt(1, postid);

            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Er is een SQL fout tijdens deleten van een post!");
            e.printStackTrace();
        }

        return result;

    }

    public int insertPost(Post post){
        PreparedStatement preparedStatement  = null;
        int result = 0;

        try {
            preparedStatement = connection.prepareStatement("insert into post values (NULL, ?,?,?,?)");

            preparedStatement.setString(1,post.getPost_naam());
            preparedStatement.setString(2,post.getPost_adres());
            preparedStatement.setString(3,post.getPost_mail());
            preparedStatement.setString(4,post.getPost_telefoon());

            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Er is een SQL fout tijdens het inserten van een nieuwe post!");
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

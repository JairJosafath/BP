package sr.unasat.bewakingsBedrijf.app.databaseConnection.post;

import java.util.Scanner;

/**
 * Created by Jair on 5/16/2016.
 */
public class PostApp {

    Scanner userInput= new Scanner(System.in);
    private PostDatabase postDatabase= null;

    public void startApp(PostApp postApp) {

        // Er is een variabele nodig om te stoppen met de applicatie
        boolean stoppen = false;
        // Er is een selectie variabele nodig om de selectie van de gebruiker te onthouden
        int selectie = 0;
        // De Scanner instantie is nodig om system input te lezen.
        userInput = new Scanner(System.in);

        while(stoppen == false){
            // Print het menu
            postApp.printMenu();

            // Wacht tot de gebruiker een selectie heeft gemaakt
            System.out.print("Selectie : ");
            selectie = userInput.nextInt(); // getting a String value

            switch(selectie){
                case 0:
                    // Stop de applicatie door de while loop te stoppen
                    stoppen = true;
                    postApp.stoppen();
                    break;
                case 1:
                    postApp.doeFullSelectStatement();
                    break;
                case 2:
                    postApp.doeSelectStatement();
                    break;
                case 3:
                    postApp.doeInsertStatement();
                    break;
                case 4:
                    postApp.doeDeleteStatement();
                    break;
                case 5:
                    postApp.doeUpdateStatement();
                    break;

                default:
            }
        }
    }

    public void printMenu() {
        System.out.println("................................");
        System.out.println("Mijn eerste Database Applicatie.");
        System.out.println("Type 0 om te stoppen.");
        System.out.println("Type 1 voor full select statement.");
        System.out.println("Type 2 voor select statement.");
        System.out.println("Type 3 voor insert statement.");
        System.out.println("Type 4 voor delete statement.");
        System.out.println("Type 5 voor update statement.");


        System.out.println("................................");
    }

    public void doeFullSelectStatement() {


        if (postDatabase == null){
            postDatabase = new PostDatabase();
        }

        if (!postDatabase.isInitialised()){
            postDatabase.initialiseer();
        }

        if (postDatabase.isInitialised()){
            postDatabase.selectAll();
        }
    }

    public void doeSelectStatement() {

        System.out.print("Tik id in van record : ");
        int record =  userInput.nextInt(); // getting a int value

        if (postDatabase == null){
            postDatabase = new PostDatabase();
        }

        if (!postDatabase.isInitialised()){
            postDatabase.initialiseer();
        }

        if (postDatabase.isInitialised()){
            postDatabase.selectPost(record);
        }
    }

    public void doeInsertStatement() {

        Post post = new Post();

        System.out.print("Tik de waarde van 'naam' : ");
        String naam = userInput.next();
        post.setPost_naam(naam);

        System.out.print("Tik de waarde van 'adres' : ");
        String adres = userInput.next();
        post.setPost_adres(adres);

        System.out.print("Tik de waarde van 'mail' : ");
        String mail = userInput.next();
        post.setPost_mail(mail);

        System.out.println("Tik de waarde van 'telefoonnummer' : ");
        post.setPost_telefoon(userInput.next());


        if (postDatabase == null){
            postDatabase = new PostDatabase();
        }

        if (!postDatabase.isInitialised()){
            postDatabase.initialiseer();
        }

        if (postDatabase.isInitialised()){
            postDatabase.insertPost(post);
        }

    }

    public void doeDeleteStatement() {

        System.out.print("Tik id in van post : ");
        int postid = userInput.nextInt();

        if (postDatabase == null){
            postDatabase = new PostDatabase();
        }

        if (!postDatabase.isInitialised()){
            postDatabase.initialiseer();
        }

        if (postDatabase.isInitialised()){
            postDatabase.deletePost(postid);
        }
    }

    public void doeUpdateStatement() {

        System.out.print("Tik id in van record : ");
        int postid = userInput.nextInt(); // getting a int value
        Post post = null;

        // Haal de student.Student op
        // student.Student database instantie is alleen 1 keer nodig
        if (postDatabase == null){
            postDatabase = new PostDatabase();
        }

        // Initialiseer de studentDatabase alleen als het moet
        if (!postDatabase.isInitialised()){
            postDatabase.initialiseer();
        }

        // Alleen als de studenten database is geinitialiseerd dan verder
        if (postDatabase.isInitialised()){
            post = postDatabase.selectPost(postid);
        }

        post.setId(postid);

        if (post != null){

            System.out.println("Type 1 voor naam.");
            System.out.println("Type 2 voor adres.");
            System.out.println("Type 3 voor mail.");
            System.out.println("Type 4 voor telefoon nummer.");
            System.out.print("Selectie : ");
            int column = userInput.nextInt(); // getting a int value

            switch(column){
                case 1:
                    System.out.print("Tik de nieuwe waarde van 'naam' : ");
                    String naam = userInput.next();
                    post.setPost_naam(naam);
                    break;
                case 2:
                    System.out.print("Tik de nieuwe waarde van 'adres' : ");
                    String adres = userInput.next();
                    post.setPost_adres(adres);
                    break;
                case 3:
                    System.out.print("Tik de nieuwe waarde van 'mail' : ");
                    String mail = userInput.next();
                    post.setPost_mail(mail);
                    break;
                case 4:
                    System.out.print("Tik de nieuwe waarde van 'telefoon nummer' : ");
                    String  telefoon = userInput.next();
                    post.setPost_telefoon(telefoon);
                    break;
                default:
            }

            // Alleen als de studenten database is geinitialiseerd dan verder
            if (postDatabase.isInitialised()){
                postDatabase.updatePost(post);
            }
        }
    }

    public void stoppen() {
        if (postDatabase != null){
            if (postDatabase.isInitialised()){
                postDatabase.terminate();
            }
        }
    }
}

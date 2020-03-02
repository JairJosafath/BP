package sr.unasat.bewakingsBedrijf.app.databaseConnection.bewaker;


import org.apache.commons.lang3.builder.ToStringBuilder;
import sr.unasat.bewakingsBedrijf.app.databaseConnection.post.Post;

/**
 * Created by Jair on 5/16/2016.
 */
public class Bewaker {

    int id;

    String bewaker_naam;
    String bewaker_adres;
    String bewaker_mail;
    String bewaker_telefoon;

    Post post;


    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getBewaker_naam() {
        return bewaker_naam;
    }

    public void setBewaker_naam(String bewaker_naam) {
        this.bewaker_naam = bewaker_naam;
    }

    public String getBewaker_adres() {
        return bewaker_adres;
    }

    public void setBewaker_adres(String bewaker_adres) {
        this.bewaker_adres = bewaker_adres;
    }

    public String getBewaker_mail() {
        return bewaker_mail;
    }

    public void setBewaker_mail(String bewaker_mail) {
        this.bewaker_mail = bewaker_mail;
    }

    public String getBewaker_telefoon() {
        return bewaker_telefoon;
    }

    public void setBewaker_telefoon(String bewaker_telefoon) {
        this.bewaker_telefoon = bewaker_telefoon;
    }



    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }

}
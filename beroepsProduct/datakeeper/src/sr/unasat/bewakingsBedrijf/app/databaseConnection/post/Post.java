package sr.unasat.bewakingsBedrijf.app.databaseConnection.post;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Jair on 5/16/2016.
 */
public class Post {

    int id;
    String post_naam;
    String post_adres;
    String post_mail;
    String post_telefoon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPost_naam() {
        return post_naam;
    }

    public void setPost_naam(String post_naam) {
        this.post_naam = post_naam;
    }

    public String getPost_adres() {
        return post_adres;
    }

    public void setPost_adres(String post_adres) {
        this.post_adres = post_adres;
    }

    public String getPost_mail() {
        return post_mail;
    }

    public void setPost_mail(String post_mail) {
        this.post_mail = post_mail;
    }

    public String getPost_telefoon() {
        return post_telefoon;
    }

    public void setPost_telefoon(String post_telefoon) {
        this.post_telefoon = post_telefoon;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}
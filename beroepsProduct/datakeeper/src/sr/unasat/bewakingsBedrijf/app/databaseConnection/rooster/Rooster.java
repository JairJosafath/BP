package sr.unasat.bewakingsBedrijf.app.databaseConnection.rooster;

import org.apache.commons.lang3.builder.ToStringBuilder;
import sr.unasat.bewakingsBedrijf.app.databaseConnection.bewaker.Bewaker;
import sr.unasat.bewakingsBedrijf.app.databaseConnection.dag.Dag;
import sr.unasat.bewakingsBedrijf.app.databaseConnection.post.Post;

/**
 * Created by Jair on 5/16/2016.
 */
public class Rooster {

    int id;

    Dag dag;
    int dag_fk;

    Bewaker bewaker;
    int bewaker_fk;

    Post post;
    int post_fk;

    public Bewaker getBewaker() {
        return bewaker;
    }

    public void setBewaker(Bewaker bewaker) {
        this.bewaker = bewaker;
    }

    public int getBewaker_fk() {
        return bewaker_fk;
    }

    public void setBewaker_fk(int bewaker_fk) {
        this.bewaker_fk = bewaker_fk;
    }

    public Dag getDag() {
        return dag;
    }

    public void setDag(Dag dag) {
        this.dag = dag;
    }

    public int getDag_fk() {
        return dag_fk;
    }

    public void setDag_fk(int dag_fk) {
        this.dag_fk = dag_fk;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public int getPost_fk() {
        return post_fk;
    }

    public void setPost_fk(int post_fk) {
        this.post_fk = post_fk;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }

}
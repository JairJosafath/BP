package sr.unasat.bewakingsBedrijf.app.databaseConnection.dag;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Jair on 5/30/2016.
 */
public class Dag {
    int id;
    String dag;
    String shift;

    public String getDag() {
        return dag;
    }

    public void setDag(String dag) {
        this.dag = dag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }

}

package sr.unasat.bewakingsBedrijf.app.databaseConnection.Login;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
* Created by Jair on 6/1/2016.
*/
public class Login{

    String username;
    String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}

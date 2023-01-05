package abc.dao;

import java.util.HashMap;

public class LoginDao {
    private HashMap<String, String> users = new HashMap<String, String>();
    
    public LoginDao() {

        // initialize mock user db
        users.put("username", "password");
    }

    public boolean validateLogin(String username, String password) {
        if (isValidUser(username)) {
            return users.get(username).equals(password);
        } 
        return false;
    }
    
    public boolean isValidUser(String username) {
        return users.keySet().contains(username);
    }
}
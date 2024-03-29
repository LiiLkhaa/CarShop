package android.upem.carshop.models;

import android.widget.EditText;

import org.json.JSONObject;

public class User {
    private long id;
    private String name;
    private String email;
    private String password;


    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;

    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String toJSON(){
        return "    {\n" +
                "        \"name\": \"" + this.name + "\",\n" +
                "        \"email\": \"" + this.email + "\",\n" +
                "        \"password\": \"" + this.password + "\"\n" +
                "    }";
    }

    public static User UserParserJSON(JSONObject json){
        try {
            return new User(json.getString("name"),json.getString("email"),json.getString("password"));
        }
        catch (Exception e){

        }
        return null;
    }
    
}

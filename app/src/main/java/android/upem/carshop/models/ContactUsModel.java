package android.upem.carshop.models;

import org.json.JSONObject;

public class ContactUsModel {

    private long id;
    private String email;
    private String message;
    private String full_name;
    private String phone;

    public ContactUsModel() {
    }

    public ContactUsModel(String email, String message, String full_name, String phone) {
        this.email = email;
        this.message = message;
        this.full_name = full_name;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String toJSON(){
        return "    {\n" +
                "        \"email\": \"" + this.email + "\",\n" +
                "        \"message\": \"" + this.message + "\",\n" +
                "        \"full_name\": \"" + this.full_name + "\",\n" +
                "        \"phone\": \"" + this.phone + "\"\n" +
                "    }";
    }

    public static ContactUsModel ContactParserJSON(JSONObject json){
        try {
            return new ContactUsModel(json.getString("email"),json.getString("message"),json.getString("full_name"),json.getString("phone"));
        }
        catch (Exception e){

        }
        return null;
    }
}

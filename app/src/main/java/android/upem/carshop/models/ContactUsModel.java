package android.upem.carshop.models;

import org.json.JSONObject;

public class ContactUsModel {

    private long id;
    private String fullName;
    private String email;
    private String phone;
    private String message;

    public ContactUsModel() {
    }

    public ContactUsModel(String fullName, String email, String phone, String message) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toJSON(){
        return "    {\n" +
                "        \"fullName\": \"" + this.fullName + "\",\n" +
                "        \"email\": \"" + this.email + "\",\n" +
                "        \"phone\": \"" + this.phone + "\",\n" +
                "        \"message\": \"" + this.message + "\"\n" +
                "    }";
    }

    public static ContactUsModel ContactParserJSON(JSONObject json){
        try {
            return new ContactUsModel(json.getString("fullName"),json.getString("email"),json.getString("phone"),json.getString("message"));
        }
        catch (Exception e){

        }
        return null;
    }
}

package android.upem.carshop.models;

import org.json.JSONObject;

public class Checkout {

    private long id;
    private String fullname;
    private String adress;
    private long zipcode;
    private String city;
    private String creditcard;
    private int ccv;
    private String expdate;
    private String email;

    public Checkout(String fullname, String adress, long zipcode, String city, String creditcard, int ccv, String expdate,String email) {
        this.fullname = fullname;
        this.adress = adress;
        this.zipcode = zipcode;
        this.city = city;
        this.creditcard = creditcard;
        this.ccv = ccv;
        this.expdate = expdate;
        this.email=email;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getCcv() {
        return ccv;
    }

    public void setCcv(int ccv) {
        this.ccv = ccv;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCreditcard() {
        return creditcard;
    }

    public void setCreditcard(String creditcard) {
        this.creditcard = creditcard;
    }

    public String getExpdate() {
        return expdate;
    }

    public void setExpdate(String expdate) {
        this.expdate = expdate;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public long getZipcode() {
        return zipcode;
    }

    public void setZipcode(long zipcode) {
        this.zipcode = zipcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toJSON(){
        return "    {\n" +
                "        \"fullname\": \"" + this.fullname + "\",\n" +
                "        \"adresse\": \"" + this.adress + "\",\n" +
                "        \"zipcode\": \"" + this.zipcode + "\",\n" +
                "        \"city\": \"" + this.city + "\",\n" +
                "        \"creditcard\": \"" + this.creditcard + "\",\n" +
                "        \"ccv\": \"" + this.ccv + "\",\n" +
                "        \"expdate\": \"" + this.expdate + "\",\n" +
                "        \"email\": \"" + this.email + "\"\n" +
                "    }";
    }

    public static Checkout UserParserJSON(JSONObject json){
        try {
            return new Checkout(json.getString("fullname"),json.getString("adresse"),json.getInt("zipcode"),json.getString("city"),json.getString("creditcard"),json.getInt("ccv"),json.getString("expdate"),json.getString("email"));
        }
        catch (Exception e){

        }
        return null;
    }
}

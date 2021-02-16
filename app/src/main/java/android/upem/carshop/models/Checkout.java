package android.upem.carshop.models;

import org.json.JSONObject;

public class Checkout {

    private long id;
    private String fullname;
    private String adress;
    private long zipcode;
    private String city;
    private long creditcard;
    private int ccv;
    private String expdate;

    public Checkout(String fullname, String adress, long zipcode, String city, long creditcard, int ccv, String expdate) {
        this.fullname = fullname;
        this.adress = adress;
        this.zipcode = zipcode;
        this.city = city;
        this.creditcard = creditcard;
        this.ccv = ccv;
        this.expdate = expdate;
    }

    public Checkout() {
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

    public long getCreditcard() {
        return creditcard;
    }

    public void setCreditcard(long creditcard) {
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

    public String toJSON(){
        return "    {\n" +
                "        \"fullname\": \"" + this.fullname + "\",\n" +
                "        \"adresse\": \"" + this.adress + "\",\n" +
                "        \"zipcode\": \"" + this.zipcode + "\",\n" +
                "        \"city\": \"" + this.city + "\",\n" +
                "        \"creditcard\": \"" + this.creditcard + "\",\n" +
                "        \"ccv\": \"" + this.ccv + "\",\n" +
                "        \"expdate\": \"" + this.expdate + "\"\n" +
                "    }";
    }

    public static Checkout UserParserJSON(JSONObject json){
        try {
            return new Checkout(json.getString("fullname"),json.getString("adresse"),json.getInt("zipcode"),json.getString("city"),json.getInt("creditcard"),json.getInt("ccv"),json.getString("expdate"));
        }
        catch (Exception e){

        }
        return null;
    }
}

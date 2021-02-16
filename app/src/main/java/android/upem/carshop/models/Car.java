package android.upem.carshop.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class Car {
    private Long id;
    private String name;
    private String model;
    private String img;
    private String price;
    private String description;
    private String adress;
    private double prisfix;
    public Car() {
    }

    public Car(Long id, String name, String model, String img, String price, String description,String adress) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.img = img;
        this.price = price;
        this.description = description;
        this.adress = adress;
        this.prisfix=Double.parseDouble(price);
    }


    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public String getImg() {
        return img;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public double getPrisfix() {
        return prisfix;
    }

    public static Car CarParserJson(JSONObject json){
        try {
            return new Car(Long.parseLong( json.getString("id")),json.getString("name"),json.getString("model"),json.getString("img"),json.getString("price"),json.getString("description"),json.getString("adress"));
        } catch (JSONException e) {
            return  null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Double.compare(car.prisfix, prisfix) == 0 &&
                Objects.equals(id, car.id) &&
                Objects.equals(name, car.name) &&
                Objects.equals(model, car.model) &&
                Objects.equals(img, car.img) &&
                Objects.equals(price, car.price) &&
                Objects.equals(description, car.description) &&
                Objects.equals(adress, car.adress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, model, img, price, description, adress);
    }
}

package android.upem.carshop.models;

import org.json.JSONObject;

public class Car {
    private Long id;
    private String name;
    private String model;
    private String img;
    private double price;
    private String description;

    public Car() {
    }

    public Car(Long id, String name, String model, String img, double price, String description) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.img = img;
        this.price = price;
        this.description = description;
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

    public void setPrice(double price) {
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

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }


    public static Car CarParserJson(JSONObject json){
        return new Car();
    }
}

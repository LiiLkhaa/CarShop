package android.upem.carshop.models;

public class Car {
    private Long id;
    private String name;
    private String model;
    private byte[] img;
    private double price;
    private String description;

    public Car() {
    }

    public Car(Long id, String name, String model, byte[] img, double price, String description) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.img = img;
        this.price = price;
        this.description = description;
    }
    public Car(Long id, String name, String model, Object img, double price, String description) {
        this.id = id;
        this.name = name;
        this.model = model;
        //this.img =(byte[]) img;
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

    public void setImg(byte[] img) {
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

    public byte[] getImg() {
        return img;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}

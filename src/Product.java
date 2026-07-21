import enums.Brand;
import enums.Category;

import java.util.List;

public class Product {

    // fields:
    final private String productId;
    private String name;
    private Category category;
    private Brand brand;
    private double price;
    private int quantity;
    private float rating;
    private boolean wireless;
    private boolean onSale;
    private int warrantyYears;
    private String colour;
    private List<String> tags;
    private String description;
    private String displayImage;

    // ** REFACTOR THOUGHTS **
    // I will most likely have to do something like:
    // private final DreamProduct keywords;

    // constructor
    Product (String productId, String name, Category category, Brand brand, double price, int quantity,
             float rating, boolean wireless, boolean onSale, int warrantyYears, String colour,
             List<String> tags, String description, String displayImage){

        this.productId = productId;
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.rating = rating;
        this.wireless = wireless;
        this.onSale = onSale;
        this.warrantyYears = warrantyYears;
        this.colour = colour;
        this.tags = tags;
        this.description = description;
        this.displayImage = displayImage;
        // ** REFACTOR THOUGHTS **
        // keywords = new DreamProduct(name,category,brand,tags,minPrice,maxPrice)
    };

    // getters/setters:
    // I will only do the getters I know I will need in here 100% first,
    // because I am not sure about the shared ones:

    // also make sure to add Javadoc comments here:
    public String getProductId() {return productId;}

    public double getPrice() {return price;}
    
    public int getQuantity() {return quantity;}
    
    public float getRating() {return rating;}

    public boolean isWireless() {return wireless;}

    public boolean getIsOnSale() {return onSale;}

    public int getWarrantyYears() {return warrantyYears;}

    public String getColour() {return colour;}

    public String getDisplayImage() {return displayImage;}

    // methods:
}

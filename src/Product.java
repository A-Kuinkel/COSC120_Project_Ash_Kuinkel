import java.util.ArrayList;
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
    private Integer warrantyYears;
    private String colour;
    private List<String> tags;
    private String description;
    private String displayImage;

    // ** REFACTOR THOUGHTS **
    // I will most likely have to do something like:
    // private final DreamProduct keywords;

    /**
     * A constructor which will help us when instantiating the
     *
     * @param productId
     * @param name
     * @param category
     * @param brand
     * @param price
     * @param quantity
     * @param rating
     * @param wireless
     * @param onSale
     * @param warrantyYears
     * @param colour
     * @param tags
     * @param description
     * @param displayImage
     */
    public Product(String productId, String name, Category category, Brand brand, double price, int quantity,
                   float rating, boolean wireless, boolean onSale, Integer warrantyYears, String colour,
                   List<String> tags, String description, String displayImage) {

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
    }

    ;

    // getters/setters:
    // I will only do the getters I know I will need in here 100% first,
    // because I am not sure about the shared ones:

    // also make sure to add Javadoc comments here:
    public String getProductId() {
        return productId;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getRating() {
        return rating;
    }

    public boolean isWireless() {
        return wireless;
    }

    public boolean getIsOnSale() {
        return onSale;
    }

    public Integer getWarrantyYears() {
        return warrantyYears;
    }

    public String getColour() {
        return colour;
    }

    public String getDisplayImage() {
        return displayImage;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public Brand getBrand() {
        return brand;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getTags() {
        return new ArrayList<>(tags);
    }

    // methods:
    public String getProductInfo() {
        return "This product's ID is: " + productId +
                "\nMost importantly, it is currently " + (onSale ? "ON SALE!" : "not on sale.") +
                "\nIt's price is " + price +
                "\nThis product is " + (wireless ? "wireless." : "not wireless.") +
                "\nGet it Quick! There are only " + quantity + " of these remaining!" +
                "\nOur customers have rated this product " + rating + "." +
                "\nIt comes with a " + warrantyYears + " year warranty." +
                "The product's colour is " + colour + ".";
    }

    ;
}

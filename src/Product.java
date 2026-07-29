import java.util.ArrayList;
import java.util.List;

/*
 ** TODO: FINISH OF JAVADOC COMMENTS **
 */

/**
 * Explain this class briefly & what it does....
 */
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
     * A constructor which will help us when instantiating the Product Class.
     * Without our constructor declaration, java compiler will just generate a default
     * constructor w/out any args..... NOT what we want!
     * @param productId -> a unique string value which is the ID of the product. Each product must have an ID.
     * @param name -> a String for the name of the product, e.g. Apple MacBook Air 2 2021.
     * @param category -> an ENUM type CATEGORY in which the product belongs, i.e. mouse, laptops etc.
     * @param brand -> another ENUM type BRAND which the product belongs to.
     * @param price -> a double value representing the price of the product.
     * @param quantity -> an int value to represent the amount of stock available for a certain product.
     * @param rating -> a float value displaying the "ratings" that the users have given to the specific product.
     * @param wireless -> a boolean value to specify if the product is wireless or not.
     * @param onSale -> another boolean value used to help notify users of whether the item is on sale or not.
     * @param warrantyYears -> an int value describing the time period of warranty for the product.
     * @param colour -> a String value used to describe the colour of the product.
     * @param tags -> a List of Strings 'tags' that are relevant to the product. Very useful for finding relevant search results.
     * @param description -> a String to simply enter a description of the product.
     * @param displayImage -> a String value which will store the filepath to the display image of the specific product.
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

    /**
     * the unique productID of each product.
     * @return the productID.
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Price (2 d.p.).
     * @return the price of the product to 2 d.p.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Quantity of available products.
     * @return the amount of stock that business has for the product.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     *
     * @return
     */
    public float getRating() {
        return rating;
    }

    /**
     *
     * @return
     */
    public boolean isWireless() {
        return wireless;
    }

    /**
     *
     * @return
     */
    public boolean getIsOnSale() {
        return onSale;
    }

    /**
     *
     * @return
     */
    public Integer getWarrantyYears() {
        return warrantyYears;
    }

    /**
     *
     * @return
     */
    public String getColour() {
        return colour;
    }

    /**
     *
     * @return
     */
    public String getDisplayImage() {
        return displayImage;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public Category getCategory() {
        return category;
    }

    /**
     *
     * @return
     */
    public Brand getBrand() {
        return brand;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Tags related to the product (useful for relevance during searching of products).
     * @return a copy of the tags list, so that it is not accidentally modified (due to mutability).
     */
    public List<String> getTags() {
        return new ArrayList<>(tags);
    }

    // methods:

    /**
     *
     * @return
     */
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

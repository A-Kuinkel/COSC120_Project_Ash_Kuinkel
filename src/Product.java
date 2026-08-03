import java.util.ArrayList;
import java.util.List;

/**
 * The class (blueprint) for our product dictating what information a product should contain &
 * provides a convenient way to access our products.
 */
public class Product {

    // so the ones initialised in dreamproduct, don't need to be duplicated here i think...
    // instead we should call the DreamProduct class which is like the aggregation here..
    final private String productId;
    private final String name;
    private double price;
    private int quantity;
    private final float rating;
    private Integer warrantyYears;
    private String description;
    private String displayImage;

    // ** REFACTOR THOUGHTS **
    // I will most likely have to do something like:
    // private final DreamProduct keywords;

    /**
     * A constructor which will help us when instantiating the Product Class.
     * Without our constructor declaration, java compiler will just generate a default
     * constructor w/out any args..... NOT what we want!
     *
     * @param productId     -> a unique string value which is the ID of the product. Each product must have an ID.
     * @param name          -> a String for the name of the product, e.g. Apple MacBook Air 2 2021.
     * @param price         -> a double value representing the price of the product.
     * @param quantity      -> an int value to represent the amount of stock available for a certain product.
     * @param rating        -> a float value displaying the "ratings" that the users have given to the specific product.
     * @param warrantyYears -> an int value describing the time period of warranty for the product.
     * @param description   -> a String to simply enter a description of the product.
     * @param displayImage  -> a String value which will store the filepath to the display image of the specific product.
     */
    public Product(String productId, String name, double price, int quantity,
                   float rating, Integer warrantyYears, String description, String displayImage) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.warrantyYears = warrantyYears;
        this.rating = rating;
        this.description = description;
        this.displayImage = displayImage;
        // ** REFACTOR THOUGHTS **
        // keywords = new DreamProduct(name,category,brand,tags,minPrice,maxPrice)
    }

    // I will only do the getters I know I will need in here 100% first,
    // because I am not sure about the shared ones:

    /**
     * the unique productID of each product.
     *
     * @return the productID.
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Product name.
     *
     * @return the name associated with the product.
     */
    public String getName() {
        return name;
    }

    /**
     * Price (2 d.p.).
     *
     * @return the price of the product to 2 d.p.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Quantity of available products.
     *
     * @return the amount of stock that business has for the product.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * What other customers have rated the product.
     *
     * @return the overall rating of the product out of 5.
     */
    public float getRating() {
        return rating;
    }

    /**
     * How many years of warranty the product comes with.
     *
     * @return the warranty time period the product comes with.
     */
    public Integer getWarrantyYears() {
        return warrantyYears;
    }

    /**
     * A brief description of some specific product.
     *
     * @return the description of the product.
     */
    public String getDescription() {
        return description;
    }

    /**
     * The image associated with the product on display.
     *
     * @return the display image of the specific product.
     */
    public String getDisplayImage() {
        return displayImage;
    }

    // setters for fields that may be changed after product created, e.g. whether on sale or not, the price maybe,
    // quantity as well, tags is debatable ( but I doubt it'd change, so leave it...), description could possibly be
    // updated so include a setter for this & finally the display image...

    /**
     * Setter method to help change the price of the product as needed.
     *
     * @param price -> a double value representing the new value we wish to assign to the price of the product.
     */
    public void setProductPrice(double price) {
        this.price = price;
    }

    /**
     * Setter method to update quantity of product as needed.
     *
     * @param quantity -> int value to represent the updated available quantity of the product
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Setter method to change the product description as needed.
     *
     * @param description -> a String which will feature the new description of the product.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Setter method used to update the display image of a product.
     *
     * @param displayImage -> a String which will contain the location of file for new displayImage for the product.
     */
    public void setDisplayImage(String displayImage) {
        this.displayImage = displayImage;
    }

    /**
     * Product information (for the customer to see) of a specific product.
     *
     * @return a nicely formatted String with the product information.
     */
    public String getProductInfo() {
        return "This product's ID is: " + productId +
                "\nMost importantly, it is currently " + (onSale ? "ON SALE!" : "not on sale.") +
                "\nIt's price is " + price +
                "\nThis product is " + (wireless ? "wireless." : "not wireless.") +
                "\nGet it Quick! There are only " + quantity + " of these remaining!" +
                "\nOur customers have rated this product " + rating + "." +
                "\nIt comes with " + (warrantyYears == null ? "no specifically listed warranty." : ("a" + warrantyYears + " year warranty.")) +
                "\nThe product's colour is " + colour + ".";
    }
}

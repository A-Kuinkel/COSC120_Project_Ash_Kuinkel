/**
 * @author Ash Kuinkel (akuinke3@myune.edu.au)
 * created for COSC120 Assignment Task 3 (Trimester 2, 2026)
 *
 * Git repository Link: https://github.com/A-Kuinkel/COSC120_Project_Ash_Kuinkel
 */

import java.util.List;

/**
 * The class (blueprint) for our product dictating what information a product should contain &
 * provides a convenient way to access our products. This idea of this class is adapted from the COSC120
 * Lectures (starting from) Lecture 4 Video 1. However, specific references are contained within the Javadoc
 * for the methods.
 */
public class Product {

    // so the ones initialised in dream product, don't need to be duplicated here; aggregation in action.
    private final String productId;
    private final String name;
    private double price;
    private int quantity;
    private final float rating;
    private final Integer warrantyYears;
    private String description;
    private String displayImage;
    private final DreamProduct productSearchableCharacteristics; // unlikely that somebody is searching for product
    // by e.g. quantity or productId, they're most likely searching by name, brand, category etc. hence why I named this
    // variable productSearchableCharacteristics.

    /**
     * A constructor which will help us when instantiating the Product Class.
     * Without our constructor declaration, java compiler will just generate a default
     * constructor w/out any args..... NOT what we want! This constructor is adapted from the COSC120 Lectures,
     * Lecture 6 Video 2 Geek.java lines (36-45).
     *
     * @param productId     -> a unique string value which is the ID of the product. Each product must have an ID.
     * @param name          -> a String for the name of the product, e.g. Apple MacBook Air 2 2021.
     * @param price         -> a double value representing the price of the product.
     * @param quantity      -> an int value to represent the amount of stock available for a certain product.
     * @param rating        -> a float value displaying the "ratings" that the users have given to the specific product.
     * @param warrantyYears -> an int value describing the time period of warranty for the product.
     * @param description   -> a String to simply enter a description of the product.
     * @param displayImage  -> a String value which will store the filepath to the display image of the specific product.
     * @param productSearchableCharacteristics -> an instance of the DreamProduct class which stores the searchable
     *                                         attributes associated with a product.
     */
    public Product(String productId, String name, double price, int quantity, float rating,
                   Integer warrantyYears, String description, String displayImage,
                   DreamProduct productSearchableCharacteristics) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.warrantyYears = warrantyYears;
        this.rating = rating;
        this.description = description;
        this.displayImage = displayImage;
        this.productSearchableCharacteristics = productSearchableCharacteristics;
    }

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

    /**
     *
     * @return
     */
    public DreamProduct getProductSearchableCharacteristics() {return productSearchableCharacteristics;}

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
     * Product information (for the customer to see) of a specific product. Note that the idea of this method is
     * adapted from the COSC120 Lectures throughout the entire lifecycle of development; as a proper reference:
     * COSC120 Lecture 6 Video 2 Geek.java lines (121-124).
     *
     * @return a nicely formatted String with the product information.
     */
    public String getProductInfo() {
        // instead of doing this everytime we need to access the onSale:
        // boolean onSale = (boolean) this.getProductSearchableCharacteristics().getAllProductAttributes().get(ProductAttributes.ON_SALE);
        // I'll just create like a method/getter in the DreamProduct.java instead... which will also defend against
        // value mismatch errors:

        // Using our getter methods we made in DreamProduct now makes this job so much easier:
        boolean onSale = this.productSearchableCharacteristics.isOnSale();
        boolean wireless = this.productSearchableCharacteristics.isWireless();
        String colour = this.getProductSearchableCharacteristics().getColour();
        Category category = this.getProductSearchableCharacteristics().getCategory();
        Brand brand = this.getProductSearchableCharacteristics().getBrand();
        List<String> tags = this.getProductSearchableCharacteristics().getTags();

        return "\nProduct ID: " + productId +
                "\nName: " + name +
                "\nOnSale: " + (onSale ? "ON SALE!" : "not on sale") +
                "\nPrice: " + "$" +price +
                "\nWireless: " + (wireless ? "WIRELESS!" : "not wireless") +
                "\nIn Stock: " + quantity +
                "\nRating: " + rating +
                "\nWarranty: " + (warrantyYears == null ? "no listed warranty." : (warrantyYears + " year warranty")) +
                "\nColour: " + colour +
                "\nCategory: " + category +
                "\nBrand: " + brand +
                "\nGet similar results to this product by searching tags: " + tags + "\n";
    }
}

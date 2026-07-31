import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class DreamProduct {

    private final Category category;
    private final Brand brand;
    private List<String> tags;
    private final double minPrice;
    private final double maxPrice;
    private final boolean mustBeWireless;
    private final float minRating;


    // this constructor will be used when a user is selecting/creating their dream product....
    public DreamProduct (String name, Category category, Brand brand,
                  List<String> tags, int minPrice, int maxPrice, boolean mustBeWireless,  float minRating) {
        this.category = category;
        this.brand = brand;
        if (tags!=null) this.tags = new ArrayList<>(tags);
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.mustBeWireless = mustBeWireless;
        this.minRating = minRating;
    };

    // also we need one to assign the existing products with their own sort of attributes that are able to be compared
    // against rather than having a user search for their dream products for example....


    public Category getCategory() {return category;}
    public Brand getBrand() {return brand;}
    public List<String> getTags() {return new  ArrayList<>(tags);}
    public double getMinPrice() {return minPrice;}
    public double getMaxPrice() {return maxPrice;}
    public boolean isMustBeWireless() {return mustBeWireless;}
    public float getMinRating() {return minRating;}


    // methods
    public boolean productMatchesDreamProductCriteria( ){
        /**TODO: COMPLETE THIS METHOD**/
        return false;
    }
}

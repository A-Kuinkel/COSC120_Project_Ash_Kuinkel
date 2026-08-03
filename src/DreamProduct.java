import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 */
public class DreamProduct {

    private final double minPrice;
    private final double maxPrice;
    private final float minRating;
    private final Integer minWarrantyYears;
    private final Map<ProductAttributes, Object> productAttributesMap;


    // this constructor will be used when a user is selecting/creating their dream product....
    public DreamProduct(int minPrice, int maxPrice, float minRating, Integer minWarrantyYears, Map<ProductAttributes, Object> productAttributesMap) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.minRating = minRating;
        this.minWarrantyYears = minWarrantyYears;
        this.productAttributesMap = new LinkedHashMap<>(productAttributesMap);
    }

    // also we need one to assign the existing products with their own sort of attributes that are able to be compared
    // against rather than having a user search for their dream products for example....
    public DreamProduct(Map<ProductAttributes, Object> productAttributesMap) {
        this.minPrice = -1;
        this.maxPrice = -1;
        this.minRating = -1;
        this.minWarrantyYears = -1;
        this.productAttributesMap = new LinkedHashMap<>(productAttributesMap);
    }


    public double getMinPrice() {
        return minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public float getMinRating() {
        return minRating;
    }

    public Map<ProductAttributes, Object> getAllProductAttributes() {
        return new LinkedHashMap<>(productAttributesMap);
    }

    public String getAllDreamProductFeatures() {
        /**TODO: COMPLETE THIS METHOD**/
        // here we have to return in a nice format,the desirable/dream characteristics of our products...
        return "";
    }

    // methods
    public boolean productMatchesDreamProductFeatures() {
        /**TODO: COMPLETE THIS METHOD**/
        // if both happen to be a Collection e.g. set, list etc. (e.g. for tags) then check if the user dream & product
        // contain any same keywords & show the user that product, e.g if user dream product has the word "Gaming" then
        // we check for our tags only since thats the one that features the collection dataset type & go through all
        // products and check which also have the tags "Gaming" & for the ones that do have that tag, return true else
        // we return false to indicate that the current product does not match....
        return false;
    }
}

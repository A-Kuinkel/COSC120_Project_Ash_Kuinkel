import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class DreamProduct {


    private final double minPrice;
    private final double maxPrice;
    private final float minRating;
    private final Map<ProductAttribute, Object> productAttributesMap;


    // this constructor will be used when a user is selecting/creating their dream product....
    public DreamProduct(int minPrice, int maxPrice, float minRating, Map<ProductAttribute, Object> productAttributesMap) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.minRating = minRating;
        this.productAttributesMap = new LinkedHashMap<>(productAttributesMap);
    }

    // also we need one to assign the existing products with their own sort of attributes that are able to be compared
    // against rather than having a user search for their dream products for example....
    public DreamProduct(Map<ProductAttribute, Object> productAttributesMap) {
        this.minPrice = -1;
        this.maxPrice = -1;
        this.minRating = -1;
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

    public Map<ProductAttribute, Object> getAllProductAttributes() {
        return new LinkedHashMap<>(productAttributesMap);
    }

    public String getAllDreamProductFeatures(){
    /**TODO: COMPLETE THIS METHOD**/
    return "";
    }

    // methods
    public boolean productMatchesDreamProductFeatures() {
        /**TODO: COMPLETE THIS METHOD**/
        return false;
    }
}

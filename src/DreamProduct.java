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
    private final Integer minWarrantyYears;
    private final Map<ProductAttributes, Object> productAttributesMap;


    // this constructor will be used when a user is selecting/creating their dream product....
    public DreamProduct(double minPrice, double maxPrice, float minRating, Integer minWarrantyYears, Map<ProductAttributes, Object> productAttributesMap) {
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

    public boolean isOnSale() {
        // When creating an instance of this class, somebody could set/ or somehow this value may end up being null or any other value somehow....
        // if we get a null/other value here then that's no good as it'll throw an error, so I think this is enough to guard against
        // it:
        Object isOnSaleValue = this.getAllProductAttributes().get(ProductAttributes.ON_SALE);
        boolean onSale = false;
        if (isOnSaleValue instanceof Boolean) {
            // we have the additional (boolean) in front of our statement because remember we store it inside our map as an Object,
            // so to convert it back to a primitive we must cast it to a boolean, like this:
            onSale = (boolean) isOnSaleValue;
        }
        ;
        return onSale;
    }

    public boolean isWireless() {
        Object isWirelessValue = this.getAllProductAttributes().get(ProductAttributes.WIRELESS);
        boolean wireless = false;
        if (isWirelessValue instanceof Boolean) {
            wireless = (boolean) isWirelessValue;
        }
        return wireless;
    }

    public String getColour() {
        Object colourValue = this.getAllProductAttributes().get(ProductAttributes.COLOUR);
        String colour = "N/A";
        if (colourValue instanceof String) {
            colour = (String) colourValue;
        }
        return colour;
    }

    public List<String> getTags() {
        Object tagsValue = this.getAllProductAttributes().get(ProductAttributes.TAGS);
        List<String> tags = new ArrayList<>();
        if (tagsValue instanceof List<?> list) {
            for (Object tag : list) {
                if (tag instanceof String string) {
                    tags.add(string);
                }
            }
        }
        return tags;
    }

    public Brand getBrand() {
        Object brandValue = this.getAllProductAttributes().get(ProductAttributes.BRAND);
        Brand brand = null;
        if (brandValue instanceof Brand someBrand) {
            brand = someBrand;
        }
        return brand;
    }

    public Category getCategory() {
        Object categoryValue = this.getAllProductAttributes().get(ProductAttributes.CATEGORY);
        Category category = null;
        if (categoryValue instanceof Category someCat) {
            category = someCat;
        }
        return category;
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

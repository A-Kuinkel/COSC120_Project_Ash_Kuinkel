import java.util.*;

/**
 * The class (blueprint) for our DreamProduct, which informs users/developers of the information
 * that the user is able to search for a product with & also provides two important helper methods
 * one to output the dream features of a product & one to see whether a product matches the user
 * dream product...
 */
public class DreamProduct {

    private final double minPrice;
    private final double maxPrice;
    private final float minRating;
    private final Integer minWarrantyYears;
    private final Map<ProductAttributes, Object> productAttributesMap;


    // this constructor will be used when a user is selecting/creating their dream product....

    /**
     * A constructor which will be used when a user is selecting/creating their dream product.
     * These are all fields the user is able to search by.
     * @param minPrice ->
     * @param maxPrice ->
     * @param minRating ->
     * @param minWarrantyYears ->
     * @param productAttributesMap ->
     */
    public DreamProduct(double minPrice, double maxPrice, float minRating, Integer minWarrantyYears, Map<ProductAttributes, Object> productAttributesMap) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.minRating = minRating;
        this.minWarrantyYears = minWarrantyYears;
        this.productAttributesMap = new LinkedHashMap<>(productAttributesMap);
    }

    // also we need one to assign the existing products with their own sort of attributes that are able to be compared
    // against rather than having a user search for their dream products for example....

    /**
     *
     * @param productAttributesMap ->
     */
    public DreamProduct(Map<ProductAttributes, Object> productAttributesMap) {
        this.minPrice = -1;
        this.maxPrice = -1;
        this.minRating = -1;
        this.minWarrantyYears = -1;
        this.productAttributesMap = new LinkedHashMap<>(productAttributesMap);
    }

    /**
     *
     * @return
     */
    public double getMinPrice() {
        return minPrice;
    }

    /**
     *
     * @return
     */
    public double getMaxPrice() {
        return maxPrice;
    }

    /**
     *
     * @return
     */
    public float getMinRating() {
        return minRating;
    }

    /**
     *
     * @return
     */
    public Map<ProductAttributes, Object> getAllProductAttributes() {
        return new LinkedHashMap<>(productAttributesMap);
    }

    /**
     *
     * @param productAttribute
     * @return
     */
    public Object getProductAttribute(ProductAttributes productAttribute) { return getAllProductAttributes().get(productAttribute); }

    /**
     *
     * @return
     */
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

    /**
     *
     * @return
     */
    public boolean isWireless() {
        Object isWirelessValue = this.getAllProductAttributes().get(ProductAttributes.WIRELESS);
        boolean wireless = false;
        if (isWirelessValue instanceof Boolean) {
            wireless = (boolean) isWirelessValue;
        }
        return wireless;
    }

    /**
     *
     * @return
     */
    public String getColour() {
        Object colourValue = this.getAllProductAttributes().get(ProductAttributes.COLOUR);
        String colour = "N/A";
        if (colourValue instanceof String) {
            colour = (String) colourValue;
        }
        return colour;
    }

    /**
     *
     * @return
     */
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

    /**
     *
     * @return
     */
    public Brand getBrand() {
        Object brandValue = this.getAllProductAttributes().get(ProductAttributes.BRAND);
        Brand brand = null;
        if (brandValue instanceof Brand someBrand) {
            brand = someBrand;
        }
        return brand;
    }

    /**
     *
     * @return
     */
    public Category getCategory() {
        Object categoryValue = this.getAllProductAttributes().get(ProductAttributes.CATEGORY);
        Category category = null;
        if (categoryValue instanceof Category someCat) {
            category = someCat;
        }
        return category;
    }

    /**
     *
     * @return
     */
    public String getAllDreamProductFeatures() {
        // here we have to return in a nice format,the desirable/dream characteristics of our products...
        StringBuilder  allDreamProductFeatures = new StringBuilder();

        // looping through all of the keys within the map
        for (ProductAttributes productAttributes : productAttributesMap.keySet()) {
            allDreamProductFeatures.append("\n").append(productAttributes).append(":")
                    .append(productAttributesMap.get(productAttributes));
        }
        return allDreamProductFeatures.toString();
        // so this will return the products Dream features e.g.
        // BRAND : SAMSUNG
        // ON_SALE : TRUE etc...
    }

    // check if the Map of the Product matches with the user's dream product:
    /**
     *
     * @param aRealProductCharacteristics
     * @return
     */
    public boolean productMatchesDreamProductFeatures(DreamProduct aRealProductCharacteristics) {
        /**TODO: COMPLETE THIS METHOD**/
        // if both happen to be a Collection e.g. set, list etc. (e.g. for tags) then check if the user dream & product
        // contain any same keywords & show the user that product, e.g if user dream product has the word "Gaming" then
        // we check for our tags only since thats the one that features the collection dataset type & go through all
        // products and check which also have the tags "Gaming" & for the ones that do have that tag, return true else
        // we return false to indicate that the current product does not match....

        // first comparing the
        for (ProductAttributes mapKeys : aRealProductCharacteristics.getAllProductAttributes().keySet()){
            // is this map Key something the user specified in their search? If yes then proceed else just return product matches:
            if(this.productAttributesMap.containsKey(mapKeys)){
                // check if tags/brands etc. match:
                Object userWantedProduct = getProductAttribute(mapKeys);
                Object actualProduct = aRealProductCharacteristics.getProductAttribute(mapKeys);

                if(userWantedProduct instanceof Collection<?> && actualProduct instanceof Collection<?>){
                    Set<Object> thingsInCommon = new HashSet<>((Collection<?>) actualProduct);
                    thingsInCommon.retainAll((Collection<?>) userWantedProduct);
                    if(thingsInCommon.isEmpty()){return false;}
                }
                // if the user says they want, e.g. category laptops, we must only return the true on the products with category laptops & false for everything else:
                else if (!userWantedProduct.equals(actualProduct)){
                    return false;
                }
            }
        }
        return true;
    }
}

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

    /**
     * A constructor which will be used when a user is selecting/creating their dream product.
     * These are all fields the user is able to search by.
     * @param minPrice -> The user's selection of the minPrice their potential dream product should have.
     * @param maxPrice -> The user's selection of the maxPrice their potential dream product should have.
     * @param minRating -> The user's selection of the minRating their potential dream product should have.
     * @param minWarrantyYears -> The user's selection of the min. number of warranty their dream product should have.
     * @param productAttributesMap -> Map containing the additional attributes that user may search by including, e.g.
     *                                colour, brand, category etc.
     */
    public DreamProduct(double minPrice, double maxPrice, float minRating, Integer minWarrantyYears, Map<ProductAttributes, Object> productAttributesMap) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.minRating = minRating;
        this.minWarrantyYears = minWarrantyYears;
        this.productAttributesMap = new LinkedHashMap<>(productAttributesMap);
    }

    /**
     * A constructor which will be used when assigning existing products, with their own set of dream attributes which
     * are able to be compared...
     *
     * @param productAttributesMap -> A map of the product's comparable attributes, e.g. brand, category etc.
     */
    public DreamProduct(Map<ProductAttributes, Object> productAttributesMap) {
        this.minPrice = -1;
        this.maxPrice = -1;
        this.minRating = -1;
        this.minWarrantyYears = -1;
        this.productAttributesMap = new LinkedHashMap<>(productAttributesMap);
    }

    /**
     * The minimum price user has selected for their potential dream product.
     *
     * @return the minimum price.
     */
    public double getMinPrice() {
        return minPrice;
    }

    /**
     * The maximum price user has selected for their potential dream product.
     *
     * @return the maximum price.
     */
    public double getMaxPrice() {
        return maxPrice;
    }

    /**
     * The minimum rating user has selected for their potential dream product.
     *
     * @return the minimum rating.
     */
    public float getMinRating() {
        return minRating;
    }

    /**
     * The other product Attributes that the user may've searched by for their potential dream product.
     *
     * @return the linked hashmap of productAttributes
     */
    public Map<ProductAttributes, Object> getAllProductAttributes() {
        return new LinkedHashMap<>(productAttributesMap);
    }

    /**
     *
     * @param productAttribute -> a specific searchable attribute of product, e.g. brand, category etc.
     *
     * @return the  searchable product attribute.
     */
    public Object getProductAttribute(ProductAttributes productAttribute) { return getAllProductAttributes().get(productAttribute); }

    /**
     * Convenient method to check if product is onSale or not. Convenient because we define this only once
     * here & can use it within different parts of the program.
     *
     * @return true if the product is on sale else, return false.
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
        return onSale;
    }

    /**
     * Convenient method to check if product is wireless.
     *
     * @return true if product is wireless, else return false.
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
     * Convenient method to check the colour of the product.
     *
     * @return the colour of the product.
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
     * Convenient method to return all the tags associated with a specific product.
     *
     * @return the tags of the featured product.
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
     *  Convenient method to check the brand of a product.
     *
     * @return the brand that the product belongs to.
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
     * Convenient method to check the category of a product.
     *
     * @return the category that the product belongs to.
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
     * Allows for a well formatted and easily readable string containing a dream product's features/attributes.
     * Lists all features/attributes of the product on their own separate lines.
     *
     * @return the product attributes
     */
    public String getAllDreamProductFeatures() {
        // here we have to return in a nice format,the desirable/dream characteristics of our products...
        StringBuilder  allDreamProductFeatures = new StringBuilder();

        // looping through all the keys within the map
        for (ProductAttributes productAttributes : productAttributesMap.keySet()) {
            allDreamProductFeatures.append("\n").append(productAttributes).append(":")
                    .append(productAttributesMap.get(productAttributes));
        }
        return allDreamProductFeatures.toString();
        // so this will return the products Dream features e.g.
        // BRAND : SAMSUNG
        // ON_SALE : TRUE etc...
    }

    /**
     * Compares the user's dream product which they searched for to an existing product and it's
     * attributes. If the user's specifications match some product/products exactly then we know
     * that these are most likely the user's dream products.
     *
     * @param aRealProductCharacteristics -> the real characteristics/attributes an existing product holds.
     *
     * @return true if all the user's dream attributes is indeed matching with the real products characteristics/attributes.
     */
    public boolean productMatchesDreamProductFeatures(DreamProduct aRealProductCharacteristics) {
        // if both happen to be a Collection e.g. set, list etc. (e.g. for tags) then check if the user dream & product
        // contain any same keywords & show the user that product, e.g. if user dream product has the word "Gaming" then
        // we check for our tags only since that's the one that features the collection dataset type & go through all
        // products and check which also have the tags "Gaming" & for the ones that do have that tag, return true else
        // we return false to indicate that the current product does not match....

        // first comparing the two product attributes:
        for (ProductAttributes mapKeys : aRealProductCharacteristics.getAllProductAttributes().keySet()){
            // is this map Key something the user specified in their search? If yes then proceed else just return product matches:
            if(this.productAttributesMap.containsKey(mapKeys)){
                // check if tags/brands etc. match:
                Object userWantedProduct = getProductAttribute(mapKeys);
                Object actualProduct = aRealProductCharacteristics.getProductAttribute(mapKeys);

                // we only ever go to this if block, if we are comparing the tags since that's the only attribute in the
                // format of Collection:
                if(userWantedProduct instanceof Collection<?> && actualProduct instanceof Collection<?>){
                    Set<Object> thingsInCommon = new HashSet<>((Collection<?>) actualProduct);
                    thingsInCommon.retainAll((Collection<?>) userWantedProduct);
                    if(thingsInCommon.isEmpty()){return false;}
                }

                // for everything else, besides the tags, if the user says they want, e.g. category laptops,
                // we must only return the true on the products with category laptops & false for everything else:
                else if (!userWantedProduct.equals(actualProduct)){
                    return false;
                }
            }
        }
        return true;
    }
}

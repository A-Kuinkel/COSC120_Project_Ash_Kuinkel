/**
 * @author Ash Kuinkel (akuinke3@myune.edu.au)
 * created for COSC120 Assignment Task 3 (Trimester 2, 2026)
 *
 * Git repository Link: https://github.com/A-Kuinkel/COSC120_Project_Ash_Kuinkel
 */

import java.util.*;

/**
 * The class which is used to create & hold the database information of our products, dynamically during
 * the entire life of the program.
 */
public class AllProducts {

    // A hashmap, where each product is stored with id as key, we can make this final as it takes in the products
    // from the file at the start of the program once:
    private final Map<String,Product> productMap = new HashMap<>();

    /**
     * Method to add a product to the productMap (holding every product within the dataset). Although the implementation
     * differs by quite a bit because of the nature of the data-structure, this is one of the methods required by the
     * rubric, the idea of this is inspired by the COSC120 Lecture 7 Video 2 AllGeeks.java addGeek() lines (24).
     *
     * @param product -> an instance of the product class, i.e. a real product from the dataset
     */
    public void addProductsToDataStructure(Product product){
        // this one should be relatively easy, we just add the product to e.g. a map,set etc.
        // whatever ds we decide to go with.
        String id = product.getProductId();
        productMap.put(id,product);
    }

    /**
     * A method to check if a Product matches the user's dream product criteria. The core architectural implementation
     * of this method is adapted from the COSC120 Lectures, Lecture 7 Video 2 AllGeeks.java findDreamGeek()
     * lines (47-57).
     *
     * @param dreamProduct -> An instance of the DreamProduct class, representing the characteristics of the user's dream
     *                        product.
     *
     * @return the list of Products containing all the products that have met the criteria to be user's dream product.
     */
    // method to compare Product in the data structure to user dream object (param):
    public List<Product> compareProductToUserDreamProduct(DreamProduct dreamProduct){
        // Seems to be one way to approach this, get the product from the data structure, compare it to dreamProduct
        // using our matches method from DreamProduct class, i.e. going onto comparing the attributes specified by the
        // user, against some real product within the database file.
        List<Product> matchingProducts = new ArrayList<>();
        System.out.println("User dream attributes searched: " + dreamProduct.getAllProductAttributes());
        for (Product product : productMap.values()){
            // checking for match against the user's searchable attribute requirements:
            // i.e. if user selects "I don't mind" for everything & doesn't enter anything, we'd expect just like '{}'
            if(product.getProductSearchableCharacteristics().productMatchesDreamProductFeatures(dreamProduct)){

                // checking to ensure that the product fits within the user's budget, minRating, warranty wishes etc.
                // also note that we don't check for the attributes inside the ProductAttributes Map, as those have
                // already been handled within the .productMatchesDreamProductFeatures() method in DreamProduct class.

                boolean matchesBudget = (dreamProduct.getMinPrice() == null || product.getPrice() >= dreamProduct.getMinPrice()) &&
                        (dreamProduct.getMaxPrice() == null || product.getPrice() <= dreamProduct.getMaxPrice()); // if the dream product
                // min/max price is null, that means user skipped the price selection, so just return everything to them.

                // if the dreamProduct rating is null, that means user doesn't care about the rating, so again, we return
                // everything back to them:
                boolean matchesRating = dreamProduct.getMinRating() == null || product.getRating() >= dreamProduct.getMinRating();
                boolean matchesWarranty = dreamProduct.getMinWarrantyYears() == null
                        || (product.getWarrantyYears() != null
                        && product.getWarrantyYears() >= dreamProduct.getMinWarrantyYears()); // Some warranty values in
                        // our db file are null, and the user may not care about warranty... so we must just handle those
                        // cases as well by checking for null.

                if(matchesBudget && matchesRating && matchesWarranty){
                    matchingProducts.add(product);
                }
            }
        }
        return matchingProducts;
    }


    /**
     * A method to enable a user to search for a product by its name.
     *
     * @param productName -> The user's search term (in the form of a string).
     *
     * @return the list of Products containing all the products which match the user's search.
     */
    public List<Product> searchProductByName(String productName){
        // get the exact name the user searched, convert it to lowercase remove all trailing/beginning whitespace and
        // simply check with a .contains() method.
        List<Product> matchingProducts = new ArrayList<>();

        for (Product product : productMap.values()){
            if(product.getName().toLowerCase().contains(productName.toLowerCase())){
                matchingProducts.add(product);
            }
        }
        return matchingProducts;
    }

    /**
     * Method to fetch any product within the Hashmap (containing all products from db) by its id.
     *
     * @param productId -> The id of the product we wish to fetch.
     *
     * @return the individual product.
     */
    public Product getProductById(String productId){
        // this is useful when we want to quickly retrieve a product, e.g. if user selects a product from the matches
        // they got, then we should immediately return the info of that product screen, like amazon once you click on a
        // product it takes you to a page with the display image and everything...
        return productMap.getOrDefault(productId, null);
    }

    // These following methods are all used to dynamically fetch the drop-down menus when the user is searching by
    // these attributes from the database... makes it so we don't have to hard code everything in the drop-downs
    /**
     * Method to retrieve all the unique tags that our products are listed with.
     *
     * @return the unique tags within the HashMap storing our products.
     */
    public Set<String> getAllUniqueTags(){
        Set<String> uniqueTags = new HashSet<>();
        for (Product product : productMap.values()){
            // for every product, loop inside the tags list & only add the unique tags:
            for (String thisProductsTags : product.getProductSearchableCharacteristics().getTags()){
                uniqueTags.add(thisProductsTags.trim());
            }
        }
        uniqueTags = uniqueTags.isEmpty() ? null : uniqueTags;
        return uniqueTags;
    }

    /**
     * Method to retrieve all the unique colours that our products are available in.
     *
     * @return all the unique colours within the HashMap containing our products.
     */
    public Set<String> getAllUniqueColours(){
        Set<String> uniqueColours = new HashSet<>();
        for (Product product : productMap.values()){
            // skip if NA:
            if(!product.getProductSearchableCharacteristics().getColour().equalsIgnoreCase("NA")){
            uniqueColours.add(product.getProductSearchableCharacteristics().getColour());}
        }
        uniqueColours = uniqueColours.isEmpty() ? null : uniqueColours;
        return uniqueColours;
    }

    /**
     * Method ot retrieve all the unique warranty time periods that products may have.
     *
     * @return all the unique warranty years within the HashMap.
     */
    public Set<Integer> getAllUniqueWarrantyYears(){
        Set<Integer> uniqueWarrantyYears = new HashSet<>();
        for (Product product : productMap.values()){
            // if the warranty is null, just skip it:
            if (product.getWarrantyYears() != null){
                uniqueWarrantyYears.add(product.getWarrantyYears());
            }
        }
        return uniqueWarrantyYears;
    }


}

import java.util.*;

/**
 *
 */
public class AllProducts {

    // use an appropriate data structure here to store/access the Product class Objects.... I think like a list would
    // be good.... in the examples from the lecture they use a hashset, but is that really necessary? I mean there is
    // actually no duplicate items & even if two products had the same names, their id's would differ.
    // **EDIT**: Now im thinking a hashmap, where each product is stored with id as key would be better for this...
    Map<String,Product> productMap = new HashMap<>();

    // method to add Products to our data structure:

    /**
     * Method to add a product to the productMap (holding every product within the dataset)
     *
     * @param product -> an instance of the product class, i.e. a real product from the dataset
     */
    public void addProductsToDataStructure(Product product){
        // this one should be relatively easy, we just add the product to e.g. a map,set etc. whatever ds we decide to go with.
        String id = product.getProductId();
        productMap.put(id,product);
    }

    /**
     * A method to check if a Product matches the user's dream product criteria.
     *
     * @param dreamProduct -> An instance of the DreamProduct class, representing the characteristics of the user's dream
     *                        product.
     *
     * @return the list of Products containing all the products that have met the criteria to be user's dream product.
     */
    // method to compare Product in the data structure to user dream object (param):
    public List<Product> compareProductToUserDreamProduct(DreamProduct dreamProduct){
        // well there seems as if there is only one way to approach this, get the product from the data structure,
        // compare it to the dreamProduct using our matches method from DreamProduct class, i.e. going onto comparing
        // the attributes specified by the user, against some real product within the database file. I originally thought
        // that I may return like a boolean for this method... but reading the rubric again it does explicitly mention
        // that we need to return a collection of our products...
        List<Product> matchingProducts = new ArrayList<>();
        for (Product product : productMap.values()){
            // checking for match against the user's searchable attribute requirements:
            if(product.getProductSearchableCharacteristics().productMatchesDreamProductFeatures(dreamProduct)){
                // checking to ensure that the product fits within the user's budget, minRating, warranty wishes etc.
                // also note that we don't check for the attributes inside the ProductAttributes Map, as those have
                // already been handled within the .productMatchesDreamProductFeatures() method in DreamProduct class.
                boolean matchesBudget = (dreamProduct.getMinPrice() == -1 || product.getPrice() >= dreamProduct.getMinPrice()) &&
                        (dreamProduct.getMaxPrice() == -1 || product.getPrice() <= dreamProduct.getMaxPrice()); // if the dream product
                // min/max price is -1, that means user skipped the price selection, so just return everything to them.
                // if the dreamProduct rating is -1, that means user doesn't care about the rating, so again, we return
                // everything back to them:
                boolean matchesRating = dreamProduct.getMinRating() == -1 || product.getRating() >= dreamProduct.getMinRating();
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

    // method to search a product by its name... e.g. user may not care for brand or category, they just want laptops...

    /**
     * A method to enable a user to search for a product by its name.
     *
     * @param productName -> The user's search term (in the form of a string).
     *
     * @return the list of Products containing all the products which match the user's search.
     */
    public List<Product> searchProductByName(String productName){
        // TODO: FINISH OFF THIS METHOD;
        // the simplest way I think we can do this is just get the exact name the user searched... convert it to lowercase
        // remove all trailing/beginning whitespace and simply check with a .contains() method... However, I think making
        // use of the tags here is also very important... in some way, for a cool similar items factor or something....
        // It's probably also best if e.g. user searches for something that doesn't exist we let them know like, we couldn't
        // find the exact thing you're looking for, but here are some more items you may like:.... maybe that'd be a good
        // place to make use of tags, but the simpler option may just be to give the user this we couldn't find message then
        // just return all products...
        // this reveals something more interesting that I seemed to have skipped over before, the product list will hold the
        // entire database content the entire time program is running, so all the products we have in our file will be held
        // by the data structure in this file (which will be called in the main file, ByteBazaar).
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
        // TODO: finish off this method
        // this is useful when we want to quickly retrieve a product, e.g. if user selects a product from the matches
        // they got, then we should immediately return the info of that product screen, like amazon once you click on a
        // product it takes you to a page with the display image and everything...
        return productMap.get(productId);
    }

    // These following methods are all used to dynamically fetch the drop-down menus when the user is searching by
    // these attributes from the database... we don't have to hard code everything in the drop-downs, because what if
    // the db changes? Then we'd have to change what we hard-coded, whilst it will probably be fine for this task,
    // what if we had 1000 entries we needed to change? Nobody is doing that, it's bad practice, so we must create these
    // dynamic methods. & e.g. if user wants to filter product by brand or anything, everything is very easy with these
    // dynamic methods:

    /**
     * Method to retrieve all the unique brands associated with our products.
     *
     * @return all the unique brands present within the HashMap.
     */
    public Set<Brand> getAllUniqueBrands(){
        Set<Brand> uniqueBrands = new HashSet<>();
        // all our brand values in dataset are populated with appropriate values, no null, n/a or anything so we don't
        // really have to do an explicit check here...
        for (Product product : productMap.values()){
            uniqueBrands.add(product.getProductSearchableCharacteristics().getBrand());
        }
        return uniqueBrands;
    }

    /**
     * Method to retrieve all the unique categories that our products belong to.
     *
     * @return the unique categories within the HashMap.
     */
    public Set<Category> getAllUniqueCategories(){
        Set<Category> uniqueCategories = new HashSet<>();
        // same with categories, our dataset doesn't have like null or anything for categories, so no need for an explicit
        // check here.
        for (Product product : productMap.values()){
            uniqueCategories.add(product.getProductSearchableCharacteristics().getCategory());
        }
        return uniqueCategories;
    }

    /**
     * Method to retrieve all the unique tags that our products are listed with.
     *
     * @return the unique tags within the HashMap storing our products.
     */
    public Set<String> getAllUniqueTags(){
        Set<String> uniqueTags = new HashSet<>();
        for (Product product : productMap.values()){
            // for every product, loop inside the tags list & only add the unique tags, this is important else im pretty
            // sure we'll get like one tag saying ['rgb','Gaming','Wireless'] & maybe like ['rgb','Gaming', 'High performance']
            // and to avoid that we only add like rgb,gaming,high performance,wireless within the set:
            for (String thisProductsTags : product.getProductSearchableCharacteristics().getTags()){
                uniqueTags.add(thisProductsTags);
            }
            // also IntelliJ suggests that we can write this second for loop logic in one line using:
            // uniqueTags.addAll(product.getProductSearchableCharacteristics().getTags());
            // but I argue my line is much more readable & easy to follow for me coming back to this code later on
            // down the line, hence why I have kept my loop.
        }
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
            // skip if N/A:
            if(!product.getProductSearchableCharacteristics().getColour().equalsIgnoreCase("N/A")){
            uniqueColours.add(product.getProductSearchableCharacteristics().getColour());}
        }
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
            // if the warranty is null, just skip it, no need to add a null section, it's much better to have the user
            // not select anything regarding warranty if they don't care, rather than having to select null on a drop-down
            // menu:
            if (product.getWarrantyYears() != null){
                uniqueWarrantyYears.add(product.getWarrantyYears());
            }
        }
        return uniqueWarrantyYears;
    }


}

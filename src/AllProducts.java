import java.util.ArrayList;
import java.util.List;

/**
 * The rubric specifically says to follow these instructions for this class:
 * The Registry class contains:
 *  1) an appropriate field (data structure) to store and access Domain class objects
 *  2) a method to add Domain class objects to the data structure
 *  3) a method to compare Domain class objects in the field (data structure) to a user's 'dream' object (parameter),
 *  returning an appropriate collection of matching Domain class objects.
 *  4) any other methods as necessary for your program to run
 */
public class AllProducts {

    // use an appropriate data structure here to store/access the Product class Objects.... I think like a list would
    // be good.... in the examples from the lecture they use a hashset, but is that really necessary? I mean there is
    // actually no duplicate items & even if two products had the same names, their id's would differ.
    List<Product> productList = new ArrayList<>();

    // method to add Products to our data structure:
    public void addProductsToDataStructure(Product product){
        // this one should be relatively easy, we just add the product to e.g. a map,set etc. whatever ds we decide to go with.
        productList.add(product);
    }

    // method to compare Product in the data structure to user dream object (param):
    public List<Product> compareProductToUserDreamProduct(DreamProduct dreamProduct){
        // well there seems as if there is only one way to approach this, get the product from the data structure,
        // compare it to the dreamProduct using our matches method from DreamProduct class, i.e. going onto comparing
        // the attributes specified by the user, against some real product within the database file. I originally thought
        // that I may return like a boolean for this method... but reading the rubric again it does explicitly mention
        // that we need to return a collection of our products...
        List<Product> matchingProducts = new ArrayList<>();
        for (Product product : productList){
            // checking for match against the user's searchable attribute requirements:
            if(product.getProductSearchableCharacteristics().productMatchesDreamProductFeatures(dreamProduct)){
                // checking to ensure that the product fits within the user's budget, minRating, warranty wishes etc.
                // also note that we don't check for the attributes inside the ProductAttributes Map, as those have
                // already been handled within the .productMatchesDreamProductFeatures() method in DreamProduct class.
                boolean matchesBudget = product.getPrice() >= dreamProduct.getMinPrice() &&
                        product.getPrice() <= dreamProduct.getMaxPrice();
                boolean matchesRating = product.getRating() > dreamProduct.getMinRating();
                boolean matchesWarranty = product.getWarrantyYears() > dreamProduct.getMinWarrantyYears();

                if(matchesBudget && matchesRating && matchesWarranty){
                    matchingProducts.add(product);
                }
            }
        }
        return matchingProducts;
    }

    // method to search a product by its name... e.g. user may not care for brand or category, they just want laptops...
    public List<Product> searchProductByName(String productName){
        // TODO: finish off this method
        // the simplest way I think we can do this is just get the exact name the user searched... convert it to lowercase
        // remove all trailing/beginning whitespace and simply check with a .contains() method... However, I think making
        // use of the tags here is also very important... in some way, for a cool similar items factor or something....
        // It's probably also best if e.g. user searches for something that doesn't exist we let them know like, we couldn't
        // find the exact thing you're looking for, but here are some more items you may like:.... maybe that'd be a good
        // place to make use of tags, but the simpler option may just be to give the user this we couldn't find message then
        // just return all products...
        return null;
    }

    public Product getProductById(String productId){
        // TODO: finish off this method
        // this is useful when we want to quickly retrieve a product, e.g. if user selects a product from the matches
        // they got, then we should immediately return the info of that product screen, like amazon once you click on a
        // product it takes you to a page with the display image and everything...
        return null;
    }

    // These following methods are all used to dynamically fetch the drop-down menus when the user is searching by
    // these attributes from the database... we don't have to hard code everything in the drop-downs, because what if
    // the db changes?
    public List<Brand> getAllUniqueBrands(){
        return null;
    }

    public List<Category> getAllUniqueCategories(){
        return null;
    }

    public List<String> getAllUniqueTags(){
        return null;
    }

    public List<String> getAllUniqueColours(){
        return null;
    }

    public List<Integer> getAllUniqueWarrantyYears(){
        return null;
    }


}

/**
 * @author Ash Kuinkel (akuinke3@myune.edu.au)
 * created for COSC120 Assignment Task 3 (Trimester 2, 2026)
 *
 * Git repository Link: https://github.com/A-Kuinkel/COSC120_Project_Ash_Kuinkel
 */

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;

/**
 * The main class of ByteBazaar application. Like conductor in an orchestra, it conducts what functionality of the
 * program the end-user sees based on their actions. It mustn't however, implement the specific functionalities i.e.
 * authentication functionality, as those jobs will be delegated to the other classes. Adheres to the principle of
 * 'methods/classes' should be specialists in OO Design. Furthermore, as required by the task AI assistance was used
 * to help create the database file for this project; Prompt: Generate a list of like 40-50 products that would be
 * suitable for a computer store selling like accessories, computer parts etc. Make sure you generate dummy id's, name,
 * category,brand etc... in a similar format to this. Model: ChatGPT, Response: product entreis for db file.
 */
public class ByteBazaar {

    private static final String productsFilePath = "src/allProducts.txt";
    private static AllProducts allProducts; // note that this is not final, because we are directly assigning the value
    // of this in the main method. Declaring a variable as `final` tells java that we do not want to change/modify the
    // variable, so declaring allProducts as final & assigning it to a value later on will cause problems.
    private static final Authentication authentication = new Authentication();

    /**
     * The main method of our program, which is what the user interacts with. It provides the initial entry point
     * dialog as well as the different options available & delegates further domain-specific jobs to the other classes.
     *
     * @param args - this is a param that java takes, if we wish to pass arguments through the CLI to our program
     * whilst it runs.
     * @throws IOException - Since we are trying to load info from our database file, there is the possibility that
     * the database file is missing/contains errors etc. our main method will catch that error & throw it out notifying
     * the developer/user the database file is corrupt/malformed in some way.
     */
    public static void main(String[] args) throws IOException {
        allProducts = loadAllProducts(); // allProducts is assigned to the return value of the loadAllProductsfunction()
        // which will return an instance of our AllProducts class & more importantly will fetch all products from our db
        // file.

        int userSelectedOption;
        boolean programRunning = true;

        // this page will be displayed until programRunning is false; of course the user will be able to redirect to
        // other pages, but they will always end up at this main screen again. This is the INTENDED program behaviour.
        do {
            String onboardingScreenUserInput = JOptionPane.showInputDialog(null,"""
                    Welcome to ByteBazaar!
                    The home of affordable computer hardware parts & accessories
                    from all your favourite brands.
                    
                    Please select one of the following options:
                    1. Search for a product.
                    2. Signup for an account with ByteBazaar to become a member and order online.
                    3. Login to my ByteBazaar account.
                    4. Logout of my ByteBazaar account.
                    5. View cart & Orders.
                    6. Exit program.
                    
                    If you would like to place orders, see your cart or maybe even
                    request products for us to sell, you must first login to your account.
                    
                    Please enter an integer, i.e. 1,2,3 etc. reflective of your intended choice.
                    
                    """);

            // gracefully handling when user presses the `x` or cancel:
            if (onboardingScreenUserInput == null) {
                System.exit(0);
                break;
            }

            try {
                userSelectedOption = Integer.parseInt(onboardingScreenUserInput);

                switch (userSelectedOption) {
                    case 1:
                        // prompt user to enter their search with searchMenu(), treating the typed search differently to
                        // the drop-downs etc. & then process that search & return results with process results function.
                        Map<String, Object> userSearchFilters = searchMenu();
                        if (userSearchFilters != null) {
                            DreamProduct userCriteria = (DreamProduct) userSearchFilters.get("userCriteria");
                            String searchTerm = (String) userSearchFilters.get("searchTerm");

                            processMatchResults(searchTerm, userCriteria);
                        }
                        break;
                    case 2:
                        authentication.signup();
                        break;
                    case 3:
                        authentication.login();
                        break;
                    case 4:
                        authentication.logout();
                        break;
                    case 5:
                        // call the CheckoutScreen with this authentication state, CheckOutScreen whilst using
                        // authentication mustn't create its own new authentication instance:
                        new CheckoutScreen(authentication).cartAndOrderScreen();
                        break;
                    case 6:
                        JOptionPane.showMessageDialog(null, "Thank you for using ByteBazaar!");
                        programRunning = false;
                        break;
                    default:
                        JOptionPane.showMessageDialog(null,
                                "Please select an integer from the following options provided!");
                        break;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Oops..., we didn't recognise that." +
                        " Please enter an integer from the following options provided!");
            }
        } while (programRunning);
    }

    /**
     * This method is designed to fetch each entry from the database file, skipping all invalid product entries
     * rather than stopping entire execution of program because one product line was unexpected. All the valid products
     * undergo an assignment of their 'dream' features & will be passed into the data structure from our AllProducts
     * class. Although the implementation differs, the main architectural idea of this method is sourced from COSC120
     * Lecture 7 Video 2 SeekAGeek.java loadGeeks() lines (46-47,162-165,181,183-184). Also, AI assistance was used for
     * a small portion of this method; Prompt used: how to split everything by commas, except commas in square
     * brackets java, model: Google AI overview (so pretty sure Gemini), suggestion provided: (?![^\[\]]*+]).
     *
     * @return -> instance of AllProducts class which holds the entire product dataset for the lifecycle of the program.
     * @throws IOException -> if we are not able to load the file.
     */
    private static AllProducts loadAllProducts() throws IOException {
        AllProducts currLoadedProducts = new AllProducts(); // this is where we create an instantiate our AllProducts
        // class
        Path productsFile = Path.of(productsFilePath);

        List<String> lines = Files.readAllLines(productsFile);
        lines.removeFirst(); // the first line isn't a product entry so we are able to discard it for our purposes here.

        for (String line : lines) {
            try {
                // originally was doing just line.split(",") and was wondering why when testing I was only getting singular
                // values for the tags etc. & I think it's splitting up the tags inside the brackets as well... so I had to
                // search up regex that makes sure to split the commas, but avoids doing so if the commas are within square
                // brackets.... & Google AI overview suggested this:
                String[] productInfo = line.split(",(?![^\\[\\]]*+])");

                // we have 14 specified columns, so if this entry doesn't have it, it should be invalid:
                if (productInfo.length != 14){
                    String msg = "Invalid row in database file...skipping:\n " + line;
                    JOptionPane.showMessageDialog(null, msg);
                    continue;
                }
                String productId = productInfo[0].trim();
                String productName = productInfo[1].trim();
                // our dataset contains valid categories, but if we ever get an entry that say doesn't contain an
                // appropriate category/brand etc., we add the check just in case:
                Category productCategory;
                try {
                    productCategory = Category.valueOf(productInfo[2].trim().toUpperCase());
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null,
                            "Product with an invalid category detected: "
                                    + productInfo[2].trim().toUpperCase() + "... skipping.");
                    continue;
                }

                Brand productBrand;
                try {
                    productBrand = Brand.valueOf(productInfo[3].trim().toUpperCase());
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null,
                            "Product with an invalid brand detected: "
                                    + productInfo[3].trim().toUpperCase() + "... skipping.");
                    continue;
                }

                // need to add try catch statements for these parse statements to handle NFEs:
                double productPrice;
                int productQuantity;
                float productRating;
                try {
                    productPrice = Double.parseDouble(productInfo[4].trim());
                    productQuantity = Integer.parseInt(productInfo[5].trim());
                    productRating = Float.parseFloat(productInfo[6].trim());

                    if (productPrice < 0) {
                        JOptionPane.showMessageDialog(null,
                                "Product with an invalid price detected: "
                                        + productInfo[4].trim() + "... skipping.");
                        continue;
                    }

                    if (productQuantity < 0) {
                        JOptionPane.showMessageDialog(null,
                                "Product with an invalid quantity detected: "
                                        + productInfo[5].trim() + "... skipping.");
                        continue;
                    }

                    if (productRating < 0 || productRating > 5) {
                        JOptionPane.showMessageDialog(null,
                                "Product with an invalid rating detected: "
                                        + productInfo[6].trim() + "... skipping.");
                        continue;
                    }

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null,
                            "Product with an invalid number detected: " + line + "... skipping.");
                    continue;
                }

                String wireless = productInfo[7].trim();
                // validation check to make sure wireless only contains values yes & no:
                if (!wireless.equalsIgnoreCase("yes") && !wireless.equalsIgnoreCase("no")) {
                    String msg = "Product with invalid wireless value detected: " + wireless + "... skipping.";
                    JOptionPane.showMessageDialog(null,msg);
                    continue;
                }
                boolean productIsWireless = wireless.equalsIgnoreCase("yes");

                String productSaleValue = productInfo[8].trim();
                // validation check to make sure on Sale only contains values yes & no:
                if (!productSaleValue.equalsIgnoreCase("yes") &&
                        !productSaleValue.equalsIgnoreCase("no")) {
                    String msg = "Product with invalid sale value detected: " + productSaleValue + "... skipping.";
                    JOptionPane.showMessageDialog(null,msg);
                    continue;
                }
                boolean productOnSale = productInfo[8].trim().equalsIgnoreCase("yes");

                // some of the warranty values are null... so we need the validation check here:
                Integer productWarrantyYears = null;
                String productWarranty = productInfo[9].trim();

                boolean checkWarrantyForNA = productWarranty.equalsIgnoreCase("NA")
                        || productWarranty.equalsIgnoreCase("N/A");

                if (!checkWarrantyForNA && !productWarranty.isEmpty()) {
                    try {
                        productWarrantyYears = Integer.parseInt(productWarranty);

                        if (productWarrantyYears < 0 || productWarrantyYears > 100) {
                            JOptionPane.showMessageDialog(null,
                                    "Product with an invalid warranty detected: "
                                            + line + "... skipping.");
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null,
                                "Product with an invalid warranty detected: "
                                        + productWarranty + "... skipping.");
                        continue;
                    }
                }

                String productColour = productInfo[10].trim();

                String formattedTagFromFile = productInfo[11].replace("[", "")
                        .replace("]", "");

                List<String> productTags = new ArrayList<>();
                for (String tag: formattedTagFromFile.split(",")) {
                    productTags.add(tag.trim());
                }

                String productDescription = productInfo[12].trim();
                String productDisplayImage = productInfo[13].trim();

                // these following steps are crucial. We are assigning each product from the db file their 'dream'
                // features that they will be assigned to using the constructor from DreamProduct which has the values
                // of like minPrice as null. This ensures that we are able to match these products features & return
                // them if they match when a user searches for their dream product.
                Map<ProductAttributes, Object> existingProductAttributes = new LinkedHashMap<>();

                existingProductAttributes.put(ProductAttributes.CATEGORY, productCategory);
                existingProductAttributes.put(ProductAttributes.BRAND, productBrand);
                existingProductAttributes.put(ProductAttributes.TAGS, productTags);
                existingProductAttributes.put(ProductAttributes.WIRELESS, productIsWireless);
                existingProductAttributes.put(ProductAttributes.COLOUR, productColour);
                existingProductAttributes.put(ProductAttributes.ON_SALE, productOnSale);

                DreamProduct addDreamFeaturesToExistingProduct = new DreamProduct(existingProductAttributes);

                Product productInstance = new Product(productId, productName, productPrice, productQuantity, productRating,
                        productWarrantyYears, productDescription, productDisplayImage, addDreamFeaturesToExistingProduct);

                // & now we can finally add our product fetched from the file (& formatted) to our HashMap data structure
                // in AllProducts class, as that is what holds the dataset for the entire lifecycle of the program:
                currLoadedProducts.addProductsToDataStructure(productInstance);
            } catch (Exception e) {
                // I guess we can make the argument that this exception is too broad, but we've already caught a wide
                // range of errors in this method, this will help us pin down an unexpected error that may occur during
                // the process of loading the products from db file to our AllProducts class to hold.
                JOptionPane.showMessageDialog(null,
                        "Error whilst attempting to load products: " + e);
            }
        }
        return currLoadedProducts;
    }

    /**
     * Method which displays the search menu screen to the user, which is where they search for a product. It validates
     * all their input & contains the final filtered search criteria + search term. Although the implementation is
     * greatly different, the fundamental idea of having a search function to collect the users input as well as
     * validating this etc. is adapted from COSC120 Lecture 7 Video 2 SeekAGeek.java lines (245-255,272,274). More
     * importantly, a discussion on stack overflow:
     * <a href="https://stackoverflow.com/questions/6555040/multiple-input-in-joptionpane-showinputdialog">...</a>
     * was referenced. The lectures displayed the search menu asking end user questions one at a time. Although I found
     * out later that the solution to this was also shown in Week 11 content, I thought it would be better UX to just
     * ask all of these questions in one window. Furthermore, AI Assistance was also used here during the development
     * process, for lines (414-417) Prompt: Add a debugging print statement to catch the warranty bad value being passed.
     * Model: Google AI, response: System.out.println("FAILED VALUE: [" + userSelectedWarranty + "]");
     *
     * @return -> HashMap containing the user's complete search, i.e. search term + their selected criteria, ready to
     *            be passed as args to processMatchResults() method.
     */
    // I think having a separate method to process these search results & call our
    // DreamProduct class etc. would be a good idea... according to the "methods should be specialists" philosophy.
    private static Map<String, Object> searchMenu() {
        while (true) {
            // screen display elements:
            JTextField mainSearchBar = new JTextField();

            JComboBox<Object> categoriesDropDown = new JComboBox<>();
            for (Category category : Category.values()) {
                categoriesDropDown.addItem(category);
            }
            categoriesDropDown.addItem("I don't mind");

            JComboBox<Object> brandDropDown = new JComboBox<>();
            for (Brand brand : Brand.values()) {
                brandDropDown.addItem(brand);
            }
            brandDropDown.addItem("I don't mind");

            JTextField minPrice = new JTextField();
            JTextField maxPrice = new JTextField();

            // default the slider initial value to 1:
            JSlider minRating = new JSlider(0, 5, 1);
            minRating.setMajorTickSpacing(1);
            minRating.setPaintLabels(true);

            LinkedList<Object> uniqueWarranty = new LinkedList<>(allProducts.getAllUniqueWarrantyYears());
            for (int i = 0; i < uniqueWarranty.size(); i++) {
                if (uniqueWarranty.get(i).equals(1)) {
                    uniqueWarranty.set(i, uniqueWarranty.get(i).toString() + " year");
                } else if (!uniqueWarranty.get(i).equals(0)) {
                    uniqueWarranty.set(i, uniqueWarranty.get(i).toString() + " years");
                }
            }
            String[] warranty = processDropDownToStringArr(uniqueWarranty);
            JComboBox<String> warrantyDropDown = new JComboBox<>(warranty);
            JCheckBox skipMinRating = new JCheckBox("Skip Rating Selection");

            LinkedList<Object> uniqueColours = new LinkedList<>(allProducts.getAllUniqueColours());
            String[] colours = processDropDownToStringArr(uniqueColours);
            JComboBox<String> colourDropDown = new JComboBox<>(colours);

            LinkedList<Object> uniqueTags = new LinkedList<>(allProducts.getAllUniqueTags());
            String[] tags = processDropDownToStringArr(uniqueTags);
            JComboBox<String> tagDropDown = new JComboBox<>(tags);

            JCheckBox onSaleCheckBox = new JCheckBox("Product must be on Sale");
            JCheckBox wirelessCheckBox = new JCheckBox("Product must be wireless");

            Object[] componentsOnScreen = {
                    "Search for a product by name:\n", mainSearchBar,
                    "\nCategory:", categoriesDropDown, "\nBrand:", brandDropDown,
                    "\nMin Price:", minPrice, "\nMax Price:", maxPrice, "\nMinimum Product Rating:", minRating, "\n",
                    skipMinRating,"\nColour:", colourDropDown, "\nTags:", tagDropDown, "\nMinimum Warranty Period:",
                    warrantyDropDown, "\n", onSaleCheckBox, "\n", wirelessCheckBox
            };

            int option = JOptionPane.showConfirmDialog(null, componentsOnScreen,
                    "ByteBazaar Search Menu", JOptionPane.OK_CANCEL_OPTION);

            // if the user didn't click ok just get them out of the search menu...
            if (option != JOptionPane.OK_OPTION) {
                return null;
            }

            String searchText = mainSearchBar.getText();

            // This is the robustness in action, bad input for these drop-downs are impossible:
            Object category = categoriesDropDown.getSelectedItem();
            if ("I don't mind".equals(category)) {
                category = null;
            } else {
                category = (Category) categoriesDropDown.getSelectedItem();
            }

            Object brand = brandDropDown.getSelectedItem();
            if ("I don't mind".equals(brand)) {
                brand = null;
            } else {
                brand = (Brand) brandDropDown.getSelectedItem();
            }

            String userSelectedWarranty = (String) warrantyDropDown.getSelectedItem();
            Integer userSelectedMinWarrantyYears = null;
            // warranty validation/processing logic:
            if (userSelectedWarranty != null && !("I don't mind".equals(userSelectedWarranty))) {
                try {
                    // so here we remove the year/years that we previously added for the user to see...
                    if (userSelectedWarranty.contains("years")) {
                        userSelectedWarranty = userSelectedWarranty.replace("years", "").trim();
                    } else if (userSelectedWarranty.contains("year")) {
                        userSelectedWarranty = userSelectedWarranty.replace("year", "").trim();
                    }

                    userSelectedMinWarrantyYears = Integer.parseInt(userSelectedWarranty);

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null,
                            "Oops... Invalid Warranty Period Detected!");
                }
            }

            // remember everything that is null means the user doesn't care about that criteria. Meaning, our
            // I don't mind options should also translate to null as the user doesn't care about those.
            String userSelectedColour = (String) colourDropDown.getSelectedItem();
            if ("I don't mind".equals(userSelectedColour)) {
                userSelectedColour = null;
            }
            String userSelectedTags = (String) tagDropDown.getSelectedItem();
            Set<String> selectedTags = new HashSet<>();
            if ("I don't mind".equals(userSelectedTags)) {
                userSelectedTags = null;
            }
            if (userSelectedTags != null){ selectedTags = Set.of(userSelectedTags);} // this is needed to turn our
            // selected tags into a collection element instead of just a single string...we need this because
            // we process the tags as a collection element + our tags should go into the condition that requires
            // collections of our DreamProduct matches class.

            Double userSelectedMinPrice = null;
            Double userSelectedMaxPrice = null;
            // validation logic for the input user may enter:
            try {
                String userInputForMinPrice = minPrice.getText().trim();
                if (!userInputForMinPrice.isEmpty()) {
                    userSelectedMinPrice = Double.valueOf(minPrice.getText());
                }

                String userInputForMaxPrice = maxPrice.getText().trim();
                if (!userInputForMaxPrice.isEmpty()) {
                    userSelectedMaxPrice = Double.valueOf(maxPrice.getText());
                }

                if ((userSelectedMinPrice != null && userSelectedMinPrice < 0)
                        || (userSelectedMaxPrice != null && userSelectedMaxPrice < 0)) {
                    JOptionPane.showMessageDialog(null,
                            """
                                    Oops! Invalid Input Detected!
                                    Please ensure your values for the min & max prices are not negative.
                                    """);
                    continue;
                }

                if ((userSelectedMinPrice != null && userSelectedMaxPrice != null) &&
                        (userSelectedMinPrice > userSelectedMaxPrice)) {
                    JOptionPane.showMessageDialog(null,
                            """
                                    Oops! Invalid Input Detected!
                                    Please ensure your value for min price is less than your value for max price.
                                    """);
                    continue;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        """
                                Oops! Invalid Input Detected!
                                Please ensure you have entered an appropriate integer value for the min & max prices.
                                """);
                continue;
            }

            // user is only able to slide the slider from 0-5... or skip the rating entirely.
            Float userSelectedMinRating = null;
            if (!skipMinRating.isSelected()) {
                userSelectedMinRating = (float) minRating.getValue();
            }

            Boolean userSelectedOnSale = onSaleCheckBox.isSelected() ? Boolean.TRUE : null;
            Boolean userSelectedWireless = wirelessCheckBox.isSelected() ? Boolean.TRUE : null;

            Map<ProductAttributes, Object> userChosenProductAttributes = new LinkedHashMap<>();
            // only add the criteria/attributes the user cares about.
            if (category != null) {
                userChosenProductAttributes.put(ProductAttributes.CATEGORY, category);
            }
            if (brand != null) {
                userChosenProductAttributes.put(ProductAttributes.BRAND, brand);

            }
            if (userSelectedTags != null) {
                userChosenProductAttributes.put(ProductAttributes.TAGS, selectedTags);
            }
            if (userSelectedColour != null) {
                userChosenProductAttributes.put(ProductAttributes.COLOUR, userSelectedColour);
            }
            if (userSelectedOnSale != null) {
                userChosenProductAttributes.put(ProductAttributes.ON_SALE, userSelectedOnSale);
            }
            if (userSelectedWireless != null) {
                userChosenProductAttributes.put(ProductAttributes.WIRELESS, userSelectedWireless);
            }

            // map to store our complete search. The reason this is a HashMap unlike the lectures which just returns
            // an instance of our dream product is because here we have the additional element of search term too.
            // We must always try to match the user's search text + criteria... not just criteria & not just search text
            // independently.
            Map<String, Object> userCompleteSearch = new HashMap<>();
            DreamProduct userDreamProductFeatures = new DreamProduct(userSelectedMinPrice, userSelectedMaxPrice,
                    userSelectedMinRating, userSelectedMinWarrantyYears, userChosenProductAttributes);
            userCompleteSearch.put("searchTerm", searchText);
            userCompleteSearch.put("userCriteria", userDreamProductFeatures);
            return userCompleteSearch;
        }
    }

    /**
     * Method which accepts the users search as params (both the searchbar text + criteria) & handles all cases when
     * matches are found, when matches are not found & when the user closes the dialog, displaying the appropriate
     * response/message to the user. Note that the idea to have this as a separate function for this as well as the
     * purpose of this function has been adapted from COSC120 Lecture 7 Video 2 SeekAGeek.java lines (380-399). Also,
     * the logic which sets the matching product to the ones shared by both the search text & the criteria is something
     * which I derived. However, the thought came to me only after being exposed to 'retainAll' method throughout the
     * whole COSC120 Lectures, as a proper reference COSC120 Lecture 7 Video 2 DreamGeek.java lines (87-88).
     *
     * @param userSearchByName -> The text user has searched by in the search bar field.
     * @param userCriteria -> All the criteria user has selected for their product to contain, i.e. category, brand etc.
     */
    private static void processMatchResults(String userSearchByName, DreamProduct userCriteria) {
        // holds matches:
        Set<Product> matchingProducts = new HashSet<>();

        // if the user hasn't specified a name, we can just filter by criteria only:
        if (userSearchByName.trim().isEmpty()) {
            matchingProducts.addAll(allProducts.compareProductToUserDreamProduct(userCriteria));
        } else {
            List<Product> matchingNameProducts = allProducts.searchProductByName(userSearchByName);
            List<Product> matchingCriteriaProducts = allProducts.compareProductToUserDreamProduct(userCriteria);
            Set<Product> intersect = new HashSet<>(matchingNameProducts);
            intersect.retainAll(matchingCriteriaProducts);
            matchingProducts.addAll(intersect);
        }

        if (matchingProducts.isEmpty()) {
            // this is the case to handle when no matches are found and also shows different outputs based on if the
            // user is logged in or not.
            if (authentication.isLoggedIn()) {
                int requestItem = JOptionPane.showConfirmDialog(null, """
                        Oops! We couldn't find anything that matches your search criteria exactly ˙◠˙
                        Since you are a member of ByteBazaar, we may be able to request this item from
                        our manufacturer... We will inform you by phone as soon as this item is in stock.
                        Thanks for understanding!
                        
                        Would you like us to request this item & contact you once it is available?
                        """, "ByteBazaar", JOptionPane.YES_NO_OPTION);
                if (requestItem == JOptionPane.YES_OPTION) {
                    saveCustomProductRequestsToFile(userCriteria,userSearchByName);
                    JOptionPane.showMessageDialog(null, """
                                    Thank you, your request has been processed & we will inform you by mobile
                                    for any further updates on your request.
                                    """,
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, """
                        Oops! We couldn't find anything that matches your search criteria exactly ˙◠˙
                        
                        Because you are not a member of ByteBazaar, we aren't able to request this item
                        from our manufacturer for you. If you do wish for it to be requested, please ensure
                        you sign up & become a member with us.
                        
                        Thanks for understanding!
                        """);
            }
        } else {
            // this is the case to handle when matching products are found.
            System.out.println(matchingProducts.size() + " matching products found!");
            String msg = " We found " + matchingProducts.size() + " products matching your search criteria!\n"
                    + " You will be shown each product in a new window.\n To exit the view, please press the 'X' cancel" +
                    " button on the top right of the window.\n";
            int option = JOptionPane.showConfirmDialog(null, msg, "ByteBazaar",
                    JOptionPane.OK_CANCEL_OPTION);
            if (option != JOptionPane.OK_OPTION) {return;} // case to handle when user closes dialog

            for (Product product : matchingProducts) {
                String productInfo = product.getProductInfo() + "\n **Please Click the 'X' if you would like to exit**"
                        + "\n Would you like to add this item to your cart?";
                int userSelectedOption = JOptionPane.showConfirmDialog(null, productInfo,
                        "ByteBazaar", JOptionPane.YES_NO_OPTION);
                if (userSelectedOption == JOptionPane.CLOSED_OPTION) {
                    break; // case to handle when user closes dialog
                }
                if (userSelectedOption == JOptionPane.YES_OPTION) {
                    if (authentication.isLoggedIn()) {
                        authentication.getCart().addProductToCart(product);
                        JOptionPane.showMessageDialog(null, """
                                Product has been added to cart! You can checkout & order from the cart screen.
                                """);
                    } else {
                        JOptionPane.showMessageDialog(null, """
                                You must be logged in to add products to your cart, please log in now!
                                """);
                    }
                }
            }
        }
    }

    /**
     * Method to save the user's order to a txt file as required by the task rubric.
     * Note that this method is adapted from the Exemplarsample code MenuSearcher.java
     * submitOrder() lines (177-178,185-189).
     *
     * @param order -> because this method will be called as soon as order is placed, we pass the specific order
     *              as a param & our order's toString() method will help in the formatting of the order being written
     *              to the txt file.
     */
    public static void saveOrderToFile(Order order) {
        // let's append the exact time/date now to the fileName, once again... not that it matters here, but
        // I would classify it as good practice... because it makes sure we avoid duplicates.
        LocalDateTime now = LocalDateTime.now();
        String date = now.toString().replace("-","").replace(":","")
                .replace(".","");

        String filePath = (order.getUserInfo().fullName().replace(" ","-")) + date + ".txt";
        Path path = Path.of(filePath);

        String orderString = order.toString();

        try {
            Files.writeString(path,orderString);
        } catch (IOException e) {
            String msg = " Oops... we couldn't place your order.\n" + " Error: " + e.getMessage();
            JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Method to save the logged-in user's custom request to a txt file. This method is also adapted from the Exemplar-
     * sample code MenuSearcher.java submitOrder() lines (177-178,185-189).
     *
     * @param requestedProduct -> the user's search criteria they wanted a product to match when searching for their
     *                         dream product.
     * @param search -> the user's search term when searching for a specific product.
     */
    // Originally I attempted to figure out a way in which we could feed the custom request to our saveOrderToFile()
    // method. However, after some thoughts & troubles, I realised it will be much easier to create a separate
    // specialist method to fulfill this task of saving custom requests to a file.
    public static void saveCustomProductRequestsToFile(DreamProduct requestedProduct,String search) {
        System.out.println("User requested features:\n" + requestedProduct.getAllDreamProductFeatures());

        // if the user skipped all criteria in the search screen then we'd expect the values of the user's DreamProduct
        // to be null. It also means that the requested product values will be empty here. Hence, in a real world scena-
        // rio we would just inform the people @ the store that the user hasn't selected any criteria:
        String userRequests = requestedProduct.getAllDreamProductFeatures().trim().isEmpty()
                ? ("User skipped all selections! Contacting them may help clarify any ambiguity.")
                : requestedProduct.getAllDreamProductFeatures();
        String searchTerm = search.trim();
        String userCustomRequest = "** Custom Request **\n" + "\nName: " + authentication.getSignedInUser().fullName() +
                "\nPhone: " + authentication.getSignedInUser().phoneNumber() + "\nSearch term: " + searchTerm + "\n"
                + userRequests;

        LocalDateTime now = LocalDateTime.now();
        String date = now.toString().replace("-","").replace(":","")
                .replace(".","");

        String filePath = (authentication.getSignedInUser().fullName().replace(" ","-"))+
                "custom-request-" + date + ".txt";
        Path path = Path.of(filePath);

        try{
            Files.writeString(path,userCustomRequest);
        } catch (IOException e) {
            String msg = " Oops... we couldn't process your request.\n" + " Error: " + e.getMessage();
            JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    // helper method

    /**
     * A helper method which helps add the 'I don't mind' option\ to our drop-down lists easily. Extracted as a method
     * because we do use these for multiple drop-downs.
     *
     * @param dropDownItems -> the raw selection items which belong to the
     * @return a string arr containing the entire drop-down list in its proper format, ready to be displayed.
     */
    private static String[] processDropDownToStringArr(LinkedList<Object> dropDownItems) {
        // Easily + Efficiently add the 'I don't mind method' using java's addFirst method for linked list:
        dropDownItems.addLast("I don't mind");
        // conversion into String[] in format combo box drop-down likes:
        String[] stringArr = new String[dropDownItems.size()];

        for (int i = 0; i < dropDownItems.size(); i++) {
            stringArr[i] = dropDownItems.get(i).toString();
        }
        return stringArr;
    }
}

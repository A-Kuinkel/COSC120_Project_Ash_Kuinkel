import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;

public class ByteBazaar {

    private static final String productsFilePath = "src/allProducts.txt";
    private static final AllProducts allProducts = new AllProducts();
    public static boolean loggedIn = false; // switch to track if user is logged in or not
    // gets updated on signup/login/logout etc.
    private static String userStoredPass; // only one .txt file allowed, so the password will float around
    // as a variable
    private static User tempUserAccountInfoHolder;
    private static User signedInUser;
    private static Cart cart;
    private static Order order;

    public static void main(String[] args) throws IOException {

        // our load all should be right here... we need the product dataset for almost all cases:
        // i.e. allProducts = loadAllProducts();
        try {
            loadAllProducts();
        } catch (IOException e) {
            System.out.println("The page requested could not be loaded. **ERROR**: \n" + e);
        }

        // boolean to track if tempUserAccountInfoHolder logged in or not throughout the entire program:
        int userSelectedOption = 0;
        boolean programRunning = true;

        do {
            String onboardingScreenUserInput = JOptionPane.showInputDialog("""
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

            if (onboardingScreenUserInput == null) {
                System.exit(0);
                programRunning = false;
                break;
            }

            try {
                userSelectedOption = Integer.parseInt(onboardingScreenUserInput);

                switch (userSelectedOption) {
                    case 1:
                        // take to first page
                        Map<String, Object> userSearchFilters = searchMenu();
                        if (userSearchFilters != null) {
                            DreamProduct userCriteria = (DreamProduct) userSearchFilters.get("userCriteria");
                            String searchTerm = (String) userSearchFilters.get("searchTerm");

                            processMatchResults(searchTerm, userCriteria);
                        }
                        break;
                    case 2:
                        // take to second page
                        signup();
                        break;
                    case 3:
                        // take to third page
                        login();
                        break;
                    case 4:
                        // take to fourth page
                        logout();
                        break;
                    case 5:
                        cartAndOrderScreen();
                        break;
                    case 6:
                        // take to fifth page
                        JOptionPane.showMessageDialog(null, "Thank you for using ByteBazaar!");
                        programRunning = false;
                        break;
                    default:
                        JOptionPane.showMessageDialog(null,
                                "Please select an integer from the following options provided!");
                        break;
                }
                ;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Oops..., we didn't recognise that." +
                        " Please enter an integer from the following options provided!");
            }
        } while (programRunning);
    }

    // so the behaviour we want with this is to go through every product within our db file, validate
    // the product; i.e. make sure it has non-negative price etc. if it is not valid, just skip over
    // the current product row/line... because I think having one row break the entire flow of the
    // program may not be so good.
    private static void loadAllProducts() throws IOException {
        System.out.println("Loading ByteBazaar products...\n");
        Path productsFile = Path.of(productsFilePath);

        List<String> lines = Files.readAllLines(productsFile);
        lines.removeFirst();

        for (String line : lines) {
            try {
                // originally was doing just line.split(",") and was wondering why when testing I was only getting singular
                // values for the tags etc. & I think it's splitting up the tags inside the brackets as well... so I had to
                // search up regex that makes sure to split the commas, but avoids doing so if the commas are within square
                // brackets.... & gemini provided me with this regex:
                String[] productInfo = line.split(",(?![^\\[\\]]*+\\])");

                String productId = productInfo[0].trim();
                String productName = productInfo[1].trim();
                // our dataset contains valid categories, but if we ever get an entry that say doesn't contain an
                // appropriate category, we add the check just in case:
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
                    productBrand = Brand.valueOf(productInfo[3]);
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null,
                            "Product with an invalid brand detected: "
                                    + productInfo[3].trim().toUpperCase() + "... skipping.");
                    continue;
                }

                // need to add try catch statements for these parse statements to handle NFEs:
                double productPrice = 0;
                int productQuantity = 0;
                float productRating = 0;
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

                boolean productIsWireless = productInfo[7].trim().equalsIgnoreCase("yes");
                boolean productOnSale = productInfo[8].trim().equalsIgnoreCase("yes");

                // some of the warranty values are null... so, ADD VALIDATION CHECK HERE:
                Integer productWarrantyYears = null;
                String productWarranty = productInfo[9].trim();

                boolean CheckWarrantyForNA = productWarranty.equalsIgnoreCase("NA")
                        || productWarranty.equalsIgnoreCase("N/A");

                if (!CheckWarrantyForNA && !productWarranty.isEmpty()) {
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
                allProducts.addProductsToDataStructure(productInstance);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Error whilst attempting to load products: " + e);
            }
        }

    }

    // How I'm envisioning this right now is that we will have the user press option '1' on the main screen to go into
    // the search for a product section & then this is where the user will be presented with the search options - where
    // they are able to search for their dream product. Ideally I'd want like a search bar looking field, include some
    // checkboxes, drop down menus etc. all within the one page, that way it just looks much better than having one
    // input in one box etc... & of course, I think having a separate method to process these search results & call our
    // DreamProduct class etc. would be a good idea... according to the "methods should be specialists" philosophy.
    // Used this as a reference for having multiple input fields in one sort of window:
    // https://stackoverflow.com/questions/6555040/multiple-input-in-joptionpane-showinputdialog
    private static Map<String, Object> searchMenu() {
        while (true) {
            JTextField mainSearchBar = new JTextField();

            JComboBox<Object> categoriesDropDown = new JComboBox<>();
            categoriesDropDown.addItem("I don't mind");
            for (Category category : Category.values()) {
                categoriesDropDown.addItem(category);
            }

            JComboBox<Object> brandDropDown = new JComboBox<>();
            brandDropDown.addItem("I don't mind");
            for (Brand brand : Brand.values()) {
                brandDropDown.addItem(brand);
            }

            JTextField minPrice = new JTextField();
            JTextField maxPrice = new JTextField();

            JSlider minRating = new JSlider(0, 5, 1);
            minRating.setMajorTickSpacing(1);
            minRating.setPaintLabels(true);

            LinkedList<Object> uniqueWarranty = new LinkedList<>(allProducts.getAllUniqueWarrantyYears());
            for (int i = 0; i < uniqueWarranty.size(); i++) {
                uniqueWarranty.set(i, uniqueWarranty.get(i).toString() + (i == 0 ? " year" : " years"));
            }
            String[] warranty = processDropDownToStringArr(uniqueWarranty);
            JComboBox<String> warrantyDropDown = new JComboBox<>(warranty);

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
                    "\nMin Price:", minPrice, "\nMax Price:", maxPrice, "\nMinimum Product Rating:", minRating,
                    "\nColour:", colourDropDown, "\nTags:", tagDropDown, "\nMinimum Warranty Period:", warrantyDropDown,
                    "\n", onSaleCheckBox, "\n", wirelessCheckBox

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
                    System.out.println("FAILED VALUE: [" + userSelectedWarranty + "]");
                    JOptionPane.showMessageDialog(null,
                            "Oops... Invalid Warranty Period Detected!");
                }
            }

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

            // user is only able to slide the slider to whole numbers from 0-5...
            int userSelectedMinRating = minRating.getValue();

            Boolean userSelectedOnSale = onSaleCheckBox.isSelected() ? Boolean.TRUE : null;
            Boolean userSelectedWireless = wirelessCheckBox.isSelected() ? Boolean.TRUE : null;

            Map<ProductAttributes, Object> userChosenProductAttributes = new LinkedHashMap<>();
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

            Map<String, Object> userCompleteSearch = new HashMap<>();
            DreamProduct userDreamProductFeatures = new DreamProduct(userSelectedMinPrice, userSelectedMaxPrice,
                    userSelectedMinRating, userSelectedMinWarrantyYears, userChosenProductAttributes);
            userCompleteSearch.put("searchTerm", searchText);
            userCompleteSearch.put("userCriteria", userDreamProductFeatures);
            return userCompleteSearch;
        }
    }

    // I actually now think we should return like a map from the searchMenu() method.. like a hashmap that
    // contains like:
    //    Key:                      Value:
    // searchTerm -> Stores the search term that must match.
    // userCriteria -> Stores instance of dream product class (representing users wish for like minPrice etc.)

    private static void processMatchResults(String userSearchByName, DreamProduct userCriteria) {
        // if user's product name search doesn't match any products, I guess we can just like say there wasn't
        // a specific match with the name that you searched for, but here are products that match your other
        // criteria & if other criteria isn't selected as well then maybe just say like "we couldn't find anything
        // with that specified name or criteria combination, please search again with different terms."

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
            if (loggedIn) {
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
            System.out.println(matchingProducts.size() + " matching products found!");
            String msg = " We found " + matchingProducts.size() + " products matching your search criteria!\n"
                    + " You will be shown each product in a new window.\n To exit the view, please press the 'X' cancel" +
                    " button on the top right of the window.\n";
            int option = JOptionPane.showConfirmDialog(null, msg, "ByteBazaar",
                    JOptionPane.OK_CANCEL_OPTION);
            if (option != JOptionPane.OK_OPTION) {return;}

            for (Product product : matchingProducts) {
                String productInfo = product.getProductInfo() + "\n **Please Click the 'X' if you would like to exit**"
                        + "\n Would you like to add this item to your cart?";
                int userSelectedOption = JOptionPane.showConfirmDialog(null, productInfo,
                        "ByteBazaar", JOptionPane.YES_NO_OPTION);
                if (userSelectedOption == JOptionPane.CLOSED_OPTION) {
                    break;
                }
                if (userSelectedOption == JOptionPane.YES_OPTION) {
                    if (loggedIn) {
                        cart.addProductToCart(product);
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

// with these methods (i.e, signup, login, logout),how im thinking it would work is that we can just save the tempUserAccountInfoHolder
// credentials to a data structure... on signup (& yes that includes a password) & then have the tempUserAccountInfoHolder login whilst
// validating that the password/username they entered is correct, if correct we can just flip a boolean variable
// called like loggedIn to true & that will remain true for the entire length of the program until the tempUserAccountInfoHolder closes
// the program, or decides to log out manually (in which case the loggedIn value will be set to false).

// Try & see if we can have these methods work like forms, i.e. all inputs in one page rather than one window
// for every input... just looks much nicer that way. I will have to see if this is possible first.
// Use this as reference: https://stackoverflow.com/questions/6555040/multiple-input-in-joptionpane-showinputdialog

    private static void signup() {
        // get the tempUserAccountInfoHolder to enter some basic details.... maybe we can somehow incorporate passwords onto here as well but
        // how the passwords would work is that it would just be kept in a data structure & be held temporarily for the
        // current session. This is the only way we are able to enable passwords whilst only keeping the one product txt
        // file... If we were allowed to have another txt file, we could make one called like allUsers & possibly
        // save just plain-text passwords to it... but we can't with just one txt... no way passwords can be stored
        // in a product db:
        while (true) {
            JTextField fNameInput = new JTextField();
            JTextField lNameInput = new JTextField();
            JTextField eMailInput = new JTextField();
            JPasswordField passInput = new JPasswordField();
            JTextField pNumInput = new JTextField();
            JTextField sAddrInput = new JTextField();
            String introText = """
                    **SIGNUP FORM**
                    
                      Thank you for signing up & becoming a member with ByteBazaar.
                      By creating an account, you are able to order our products online!
                      Please fill out the form on the next page appropriately.
                    """;

            Object[] options = {introText, "\n", " First Name:\n", fNameInput, "\n", "Last Name:\n", lNameInput, "\n", "Email:\n", eMailInput,
                    "\n", "Password (Must be 8-30 chars long):\n", passInput, "\n",
                    "Phone Number (Must be 7-15 digits long):\n", pNumInput, "\n", "Shipping Address:\n", sAddrInput};

            int option = JOptionPane.showConfirmDialog(null, options, "Signup",
                    JOptionPane.OK_CANCEL_OPTION);

            if (option != JOptionPane.OK_OPTION) {
                return;
            }

            String firstName = fNameInput.getText().trim();
            String lastName = lNameInput.getText().trim();
            String email = eMailInput.getText().trim();
            String password = new String(passInput.getPassword());// lets not trim password..
            String phoneNumber = pNumInput.getText().trim();
            String shippingAddress = sAddrInput.getText().trim();

            // validation logic to make sure we don't get bad input from user:
            if (firstName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a valid first name.");
                continue;
            } else if (!checkIfLettersOnly(firstName)) {
                JOptionPane.showMessageDialog(null, """
                        Oops! Found invalid input, please enter your first name in letters only.
                        """);
                continue;
            }

            if (lastName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a valid last name.");
                continue;
            } else if (!checkIfLettersOnly(lastName)) {
                JOptionPane.showMessageDialog(null, """
                        Oops! Found invalid input, please enter your last name in letters only.
                        """);
                continue;
            }

            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a valid email.");
                continue;
            } else if (!email.contains("@") || !email.contains(".")) {
                JOptionPane.showMessageDialog(null, """
                        Oops! Found invalid input; Email must contain '@' & '.' symbols.
                        """);
                continue;
            }

            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Please enter a valid password for your account.");
                continue;
            } else if (password.length() < 8) {
                JOptionPane.showMessageDialog(null,
                        "Password must be at least 8 characters long.");
                continue;
            } else if (password.length() > 30) {
                JOptionPane.showMessageDialog(null,
                        "Password must be at most 30 characters long.");
                continue;
            }

            // storing our password:
            userStoredPass = password;

            if (phoneNumber.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Invalid phone number, please enter a valid phone number.");
                continue;
            } else if (!checkIfDigitsOnly(phoneNumber)) {
                JOptionPane.showMessageDialog(null, """
                        Oops! Found invalid phone number; Phone number must contain digits only.");
                        """);
                continue;
            } else if (phoneNumber.length() < 7) {
                JOptionPane.showMessageDialog(null, """
                        Oops! Found invalid input; Phone number must be at least 7 characters long.");
                        """);
                continue;
            } else if (phoneNumber.length() > 15) {
                JOptionPane.showMessageDialog(null, """
                        Oops! Found invalid input; Phone number must be at most 15 characters long.");
                        """);
                continue;
            }

            if (shippingAddress.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Please enter a valid shipping address");
                continue;
            }

            tempUserAccountInfoHolder = new User(firstName, lastName, email, userStoredPass,
                    phoneNumber, shippingAddress);
            JOptionPane.showMessageDialog(null, "Successfully signed up!");
            return;
        }
    }

    private static void login() {
        // im thinking  call the tempUserAccountInfoHolder record save info to it for a data structure to hold...

        // note that a validation to check for like @ or . in the email isn't really needed for login
        // method, as this is validated during the signin & for login, we can just check if email exists
        // only:
        // Also I need to guard against people trying to log in before signing up; because right
        // now it will just throw an error saying like Cannot invoke User.email() because
        // ByteBazaar.tempUserAccountInfoHolder is null....
        if (tempUserAccountInfoHolder == null) {
            JOptionPane.showMessageDialog(null, """
                    You must first create an account before you can login!
                    
                    Please return to the sign up page, sign up for an account & then login.
                    """);
            return;
        }

        while (true) {
            JTextField eMailInput = new JTextField();
            JPasswordField passInput = new JPasswordField();
            String introText = """
                    ** LOGIN FORM **
                    
                    After logging in, you will be able to place orders,
                    view cart and update your information.
                    
                    Please note once again that, this is session based,
                    i.e. your information is retained until you close
                    the program. Upon reopening, you will need to sign
                    up for an account & log in again. Thanks for under-
                    standing :)
                    """;

            Object[] components = {
                    introText, "\n", "Email:\n", eMailInput, "\n", "Password:\n", passInput, "\n"
            };

            int option = JOptionPane.showConfirmDialog(null, components, "Login",
                    JOptionPane.OK_CANCEL_OPTION);
            if (option != JOptionPane.OK_OPTION) {
                return;
            }

            String email = eMailInput.getText().trim();
            String password = new String(passInput.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Please enter a valid email + password combination.");
                continue;
            }

            // checking the password & email... note this is also a security concept, here... we shouldn't inform the
            // user if the password was incorrect or the email was incorrect, else an attacker's life will be much
            // easier.... really not so necessary here, but good to add:
            if (email.equals(tempUserAccountInfoHolder.email()) &&
                    comparePasswords(password)) {
                loggedIn = true;
                signedInUser = tempUserAccountInfoHolder;
                // creating a cart/order for the session:
                cart = new Cart(signedInUser);
                order = new Order(signedInUser);
                JOptionPane.showMessageDialog(null, "Successfully logged in!");
                return;
            }

            JOptionPane.showMessageDialog(null,
                    "Invalid Credentials, please try again.");
        }
    }

    private static void logout() {
        if (signedInUser == null) {
            JOptionPane.showMessageDialog(null, """
                    Please ensure that you are logged in first, before attempting to sign out!
                    """);
            return;
        }
        loggedIn = false;
        signedInUser = null;
        cart = null;
        order = null;
        JOptionPane.showMessageDialog(null, "Successfully logged out!");
    }

    private static void cartAndOrderScreen() {
        if (signedInUser == null) {
            JOptionPane.showMessageDialog(null, """
                    Please ensure that you are logged in first, before attempting to view cart & orders!
                    """);
            return;
        }

        int cartScreenSelectedOption;
        String mainScreen = JOptionPane.showInputDialog(null, """
                This is where you are able to access your cart & place orders!
                
                Please select one of the following options:
                1. View my Cart
                2. Clear my Cart
                3. View my Orders
                
                Please enter an integer, i.e. 1,2,3 reflective of your intended choice:
                
                """);
        if (mainScreen == null) {
            System.exit(0);
        }
        try {
            cartScreenSelectedOption = Integer.parseInt(mainScreen);
            switch (cartScreenSelectedOption) {
                case 1:
                    if (cart.isEmpty()) {
                        JOptionPane.showMessageDialog(null, """
                                Nothing to see here, please add some products to cart & come back.
                                """);
                        return;
                    }
                    String cartOverview = cart.toString() + "\n TOTAL: $" + cart.getTotalPrice() +
                            "\n Would you like to checkout & place an order?\n";
                    int orderInput = JOptionPane.showConfirmDialog(null, cartOverview, "Cart",
                            JOptionPane.YES_NO_OPTION);
                    if (JOptionPane.YES_OPTION == orderInput) {
                        for (Product product : cart.getProducts()) {
                            order.addItemsToOrder(product);
                        }
                        //TODO: ADD THE WRITE TO FILE METHOD
                        saveOrderToFile(order);
                        JOptionPane.showMessageDialog(null, """
                                Order successfully placed! Items will be dispatched & you will be notified.
                                We deeply thank you for choosing us, ByteBazaar.
                                """, "Order Confirmed", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                case 2:
                    if (cart.isEmpty()) {
                        JOptionPane.showMessageDialog(null, """
                                You cannot clear an empty cart, please ensure that you have products in your cart,
                                before attempting to clear the cart.
                                """);
                        return;
                    }
                    int clearCartOption = JOptionPane.showConfirmDialog(null, """
                            Are you sure you want to clear your cart?
                            """, "Clear Cart", JOptionPane.YES_NO_OPTION);
                    if (JOptionPane.YES_OPTION == clearCartOption) {
                        cart.clearCart();
                    }
                    JOptionPane.showMessageDialog(null, "Cart successfully cleared!");
                    break;
                case 3:
                    if (order.isEmpty()) {
                        JOptionPane.showMessageDialog(null, """
                                Nothing to see here, please confirm an order & come back.
                                """);
                        return;
                    }
                    JOptionPane.showMessageDialog(null, order.toString(), "My Orders",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Oops..., we didn't recognise that." +
                    " Please enter an integer from the following options provided!");
        }
    }

    // I spent quite a bit of time, but I don't see any way we can just feed custom request to this method...
    // so if the user enters a custom request, my thought is to just have another method which handles the
    // writing of custom requests to the file...
    public static void saveOrderToFile(Order order) {
        // let's append the exact time/date now to the fileName, once again... not that it matters here, but
        // I would classify it as good practice... because it makes sure we avoid duplicates.
        LocalDate now = LocalDate.now();
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

    public static void saveCustomProductRequestsToFile(DreamProduct requestedProduct,String search) {
        System.out.println("User requested features:\n" + requestedProduct.getAllDreamProductFeatures());

        String userRequests = requestedProduct.getAllDreamProductFeatures().trim().isEmpty()
                ? ("User skipped all selections! Contacting them may help clarify any ambiguity.")
                : requestedProduct.getAllDreamProductFeatures();
        String searchTerm = search.trim();
        String userCustomRequest = "** Custom Request **\n" + "\nName: " + signedInUser.fullName() +
                "\nPhone: " + signedInUser.phoneNumber() + "\nSearch term: " + searchTerm + "\n"
                + userRequests;

        LocalDate now = LocalDate.now();
        String date = now.toString().replace("-","").replace(":","")
                .replace(".","");

        String filePath = (signedInUser.fullName().replace(" ","-")) + date + ".txt";
        Path path = Path.of(filePath);

        try{
            Files.writeString(path,userCustomRequest);
        } catch (IOException e) {
            String msg = " Oops... we couldn't process your request.\n" + " Error: " + e.getMessage();
            JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
// helper methods:

    private static String[] processDropDownToStringArr(LinkedList<Object> dropDownItems) {
        // linked list has O(1) insertion im fairly sure at the beginning, not that it matters much here, but I
        // just thought we could like add this "I don't mind" option at the beginning easily.
        dropDownItems.addFirst("I don't mind"); // turns out java has an addFirst method, even better!
        // conversion into String[] in format combo box drop-down likes:
        String[] stringArr = new String[dropDownItems.size()];

        for (int i = 0; i < dropDownItems.size(); i++) {
            stringArr[i] = dropDownItems.get(i).toString();
        }
        return stringArr;
    }

    private static boolean checkIfLettersOnly(String userInput) {
        for (int i = 0; i < userInput.length(); i++) {
            if (!Character.isLetter(userInput.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkIfDigitsOnly(String userInput) {
        for (int i = 0; i < userInput.length(); i++) {
            if (!Character.isDigit(userInput.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean comparePasswords(String userInput) {
        String storedPass = userStoredPass;
        return storedPass.equals(userInput);
    }
}

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ByteBazaar {

    private static final String productsFilePath = "src/allProducts.txt";
    private static final AllProducts allProducts = new AllProducts();
    public static boolean loggedIn = false; // switch to track if user is logged in or not
    // gets updated on signup/login/logout etc.
    private static String userStoredHashedPassword; // only one .txt file allowed, so the password will float around
    // as a variable
    private static User tempUserAccountInfoHolder;
    private static User signedInUser;

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
                    5. Exit program.
                    
                    If you would like to place orders, see your cart or maybe even
                    update your information, you must first login to your account.
                    
                    Please enter an integer, i.e. 1,2 or 3 reflective of your intended choice.
                    
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
                        System.out.println("First page!");
                        break;
                    case 2:
                        // take to second page
                        System.out.println("Second page!");
                        signup();
                        break;
                    case 3:
                        // take to third page
                        System.out.println("Third page!");
                        login();
                        break;
                    case 4:
                        // take to fourth page
                        System.out.println("Fourth page!");
                        logout();
                        break;
                    case 5:
                        // take to fifth page
                        System.out.println("Fifth page!");
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
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null,
                                "Product with an invalid warranty detected: "
                                        + productWarranty + "... skipping.");
                    }
                }

                String productColour = productInfo[10].trim();

                String formattedTagFromFile = productInfo[11].replace("[", "")
                        .replace("]", "");

                // the Arrays.asList logic is doing the same thing as this:
                // for (String tag : formattedTagFromFile.split(",")) {
                //   productTags.add(tag);
                // }
                /** May need to change this to set to remove any possible duplicates e.g. a product may have
                 * [rgb,rgb] (twice):
                 */
                List<String> productTags = new ArrayList<>(Arrays.asList(formattedTagFromFile.split(",")));

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

                System.out.println(productInstance.getProductInfo());
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
    private static void searchMenu() {

    }

    // with these methods (i.e, signup, login, logout),how im thinking it would work is that we can just save the tempUserAccountInfoHolder
    // credentials to a data structure... on signup (& yes that includes a password) & then have the tempUserAccountInfoHolder login whilst
    // validating that the password/username they entered is correct, if correct we can just flip a boolean variable
    // called like loggedIn to true & that will remain true for the entire length of the program until the tempUserAccountInfoHolder closes
    // the program, or decides to log out manually (in which case the loggedIn value will be set to false).

    // TODO: Try & see if we can have these methods work like forms, i.e. all inputs in one page rather than one window
    // for every input... just looks much nicer that way. I will have to see if this is possible first.
    // Use this as reference: https://stackoverflow.com/questions/6555040/multiple-input-in-joptionpane-showinputdialog

    private static void signup() {
        // get the tempUserAccountInfoHolder to enter some basic details.... maybe we can somehow incorporate passwords onto here as well but
        // how the passwords would work is that it would just be kept in a data structure & be held temporarily for the
        // current session. This is the only way we are able to enable passwords whilst only keeping the one product txt
        // file... If we were allowed to have another txt file, we could make one called like allUsers & possibly
        // save hashed passwords to it... but we can't with just one txt... no way passwords can be stored in a product
        // db:
        String signupFormScreen;
        String lastName;
        String email;
        String password;
        String phoneNumber;
        String shippingAddress;

        signupFormScreen = JOptionPane.showInputDialog("""
                 **SIGNUP FORM**
                 Thank you for signing up & becoming a member with ByteBazaar.
                 By creating an account, you are able to order our products online!
                 Please fill out the form, one question at a time appropriately:
                \s
                 What is your first name?\s
                \s""");

        // don't skip past this until the user enters a valid first name, or closes program/cancels:
        while (signupFormScreen == null || signupFormScreen.trim().isEmpty() ||
                !checkIfLettersOnly(signupFormScreen)) {

            // user has pressed cancel/x so just get them out:
            if (signupFormScreen == null) {
                return;
            }

            if (signupFormScreen.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a valid first name.");
            } else if (!checkIfLettersOnly(signupFormScreen)) {
                JOptionPane.showMessageDialog(null, """
                        Oops! Found invalid input, please enter your first name in letters only.
                        """);
            }

            signupFormScreen = JOptionPane.showInputDialog("""
                     **SIGNUP FORM**
                     Thank you for signing up & becoming a member with ByteBazaar.
                     By creating an account, you are able to order our products online!
                     Please fill out the form, one question at a time appropriately:
                    \s
                     What is your first name?\s
                    \s""");
        }

        // further validation logic for each field:
        lastName = JOptionPane.showInputDialog("""
                What is your last name?
                """);

        while (lastName == null || lastName.trim().isEmpty()
                || !checkIfLettersOnly(lastName)) {

            if (lastName == null) {
                return;
            }

            if (lastName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a valid last name.");
            } else if (!checkIfLettersOnly(lastName)) {
                JOptionPane.showMessageDialog(null, """
                        Oops! Found invalid input, please enter your last name in letters only.
                        """);
            }

            lastName = JOptionPane.showInputDialog("""
                    What is your last name?
                    """);
        }

        email = JOptionPane.showInputDialog("""
                       What is your email?
                """);

        while (email == null || email.trim().isEmpty() || !email.contains("@") || !email.contains(".")) {

            if (email == null) {
                return;
            }

            if (email.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a valid email.");
            } else if (!email.contains("@") || !email.contains(".")) {
                JOptionPane.showMessageDialog(null, """
                        Oops! Found invalid input; Email must contain '@' & '.' symbols.
                        """);
            }

            email = JOptionPane.showInputDialog("""
                           What is your email?
                    """);
        }

        password = JOptionPane.showInputDialog("""
                (Note that your info will not be saved after the program is closed, so you
                will need to signup again with your information).
                
                Please enter a password for your account (8 chars min, 30 chars max):
                """);

        while (password == null || password.trim().isEmpty() || password.length() < 8 || password.length() > 30) {
            if (password == null) {
                return;
            }

            if (password.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Please enter a valid password for your account.");
            } else if (password.length() < 8) {
                JOptionPane.showMessageDialog(null,
                        "Password must be at least 8 characters long.");
            } else {
                JOptionPane.showMessageDialog(null,
                        "Password must be at most 30 characters long.");
            }

            password = JOptionPane.showInputDialog("""
                    (Note that your info will not be saved after the program is closed, so you
                    will need to signup again with your information).
                    
                    Please enter a password for your account (8 chars min, 30 chars max):
                    """);
        }
        // storing our password:
        userStoredHashedPassword = hashPassword(password);

        phoneNumber = JOptionPane.showInputDialog("""
                 Please enter your phone number (7 chars min, 15 chars max):
                """);

        while (phoneNumber == null || phoneNumber.trim().isEmpty()
                || !checkIfDigitsOnly(phoneNumber) || phoneNumber.length() < 7 || phoneNumber.length() > 15) {
            if (phoneNumber == null) {
                return;
            }

            if (phoneNumber.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Invalid phone number, please enter a valid phone number.");
            } else if(!checkIfDigitsOnly(phoneNumber)) {
                JOptionPane.showMessageDialog(null, """
                        Oops! Found invalid phone number; Phone number must contain digits only.");
                        """);
            }else if (phoneNumber.length() < 7) {
                JOptionPane.showMessageDialog(null, """
                        Oops! Found invalid input; Phone number must be at least 7 characters long.");
                        """);
            } else if (phoneNumber.length() > 15) {
                JOptionPane.showMessageDialog(null, """
                        Oops! Found invalid input; Phone number must be at most 15 characters long.");
                        """);
            }

            phoneNumber = JOptionPane.showInputDialog("""
                     Please enter your phone number (7 chars min, 15 chars max):
                    """);
        }

        shippingAddress = JOptionPane.showInputDialog("""
                Please enter your shipping address:
                """);

        while (shippingAddress == null || shippingAddress.trim().isEmpty()) {
            if (shippingAddress == null) {
                return;
            }

            JOptionPane.showMessageDialog(null,
                    "Please enter a valid shipping address");

            shippingAddress = JOptionPane.showInputDialog("""
                    Please enter your shipping address:
                    """);
        }

        tempUserAccountInfoHolder = new User(signupFormScreen, lastName, email, userStoredHashedPassword,
                phoneNumber, shippingAddress);
        JOptionPane.showMessageDialog(null, "Successfully signed up!");
    }

    private static void login() {
        // TODO
        // im thinking  call the tempUserAccountInfoHolder record save info to it for a data structure to hold...
        String email;
        String password;

        // note that a validation to check for like @ or . in the email isn't really needed for login
        // method, as this is validated during the signin & for login, we can just check if email exists
        // only:
        // TODO: I need to guard against people trying to login before signing up; because right
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

            email = JOptionPane.showInputDialog(null, """
                    **LOGIN FORM**
                    After logging in, you will be able to place orders,
                    view cart and update your information.
                    
                    Please note once again that, this is session based,
                    i.e. your information is retained until you close
                    the program. Upon reopening, you will need to sign
                    up for an account & log in again. Thanks for under-
                    standing :)
                    
                    Please enter the email for your account:
                    """);
            if (email == null) {
                return;
            }

            password = JOptionPane.showInputDialog(null,
                    "Please enter the password for your account:");
            if (password == null) {
                return;
            }

            // checking the password & email... note this is also a security concept, here... we shouldn't inform the
            // user if the password was incorrect or the email was incorrect, else an attacker's life will be much
            // easier.... really not so necessary here, but good to add:
            if (email.equals(tempUserAccountInfoHolder.email()) &&
                    comparePasswords(password)) {
                loggedIn = true;
                signedInUser =  tempUserAccountInfoHolder;
                JOptionPane.showMessageDialog(null, "Successfully logged in!");
                return;
            }

            JOptionPane.showMessageDialog(null,
                    "Invalid Credentials, please try again.");
        }
    }

    private static void logout() {
        if(signedInUser == null){
            JOptionPane.showMessageDialog(null, """
                    Please ensure that you are logged in first, before attempting to sign out!
                    """);
            return;
        }
        loggedIn = false;
        signedInUser =  null;
        JOptionPane.showMessageDialog(null, "Successfully logged out!");
    }

    // helper methods:

    /**
     *
     * @param userInput
     * @return
     */
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

    // TODO: We should probably just store password in a variable throughout the
    // lifecycle of the program, it may be better to replicate some security by
    // introducing a hashing method, although not a super secure one...:

    // note that here I asked google to just give me an extended alphaNumeric array in Java:
    private static final String extendedAlphaNumericChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "abcdefghijklmnopqrstuvwxyz"
            + "0123456789"
            + "!@#$%^&*()-_=+[]{}|;:,.<>?/";


    public static String hashPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            return "";
        }

        int hashIntNum = 0;

        /**
         * important thing here to talk about here is my integer overflow with the nested loop like this:
         * or even why my original value of 337 was a bad choice....
         *  for  (int i = 0; i < password.length(); i++) {
         *             for (int j = 0; j < extendedAlphaNumericChars.length(); j++) {
         *                 if (j % 2 == 0) {
         *                     hashIntNum = (hashIntNum * 97) + password.charAt(i) + extendedAlphaNumericChars.charAt(j);
         *                 }
         *             }
         *         }
         */
        // also there's a lot to talk about here in regard to this loop; i.e. it uses modulo with the ascii val of
        // the current char of password, so e.g. if tempUserAccountInfoHolder plaintext pass start with e; ascii of like 101 % 88 = 13....
        // everything is falling in between the 88 char thing we set out earlier (extAlphaNums)... So then we just use
        // this index, i.e. index 13 and grab the 12th element from the extAlphNums string.. which would be like 'M'
        // (also 77 in ascii b10) in this case... then we just get our hashIntNum to do like (for first iteration ofc):
        // 0 * 97 + 101 + 77 = 178 etc. either way it's good cause the calculation is completely dependent on tempUserAccountInfoHolder's
        // password choice... it will change for every password letter they enter...
        for (int i = 0; i < password.length(); i++) {

            int indexForChar = password.charAt(i) % extendedAlphaNumericChars.length();
            char alphaNumChar = extendedAlphaNumericChars.charAt(indexForChar);

            hashIntNum = (hashIntNum * 97) + password.charAt(i) + alphaNumChar;
        }
        System.out.println("FINAL INT HASH: " + hashIntNum);

        StringBuilder hashedPassword = new StringBuilder(Integer.toHexString(hashIntNum).toUpperCase());
        System.out.println("HASHED PASSWORD: " + hashedPassword);

        for (int i = 0; i < password.length(); i++) {
            int indexForChar = password.charAt(i) % extendedAlphaNumericChars.length();

            hashedPassword.append(extendedAlphaNumericChars.charAt(indexForChar));

        }

        System.out.println("HASHED PASSWORD v2: " + hashedPassword);

        // convert back to list:
        List<Character> characters = new ArrayList<>();

        for (char c : hashedPassword.toString().toCharArray()) {
            characters.add(c);
        }

        Random random = new Random(238746);

        Collections.shuffle(characters, random);

        StringBuilder finalHashedResult = new StringBuilder();
        for (Character character : characters) {
            finalHashedResult.append(character);
        }

        System.out.println("FINAL HASHED RESULT: " + finalHashedResult);

        return finalHashedResult.toString();
    }

    private static boolean comparePasswords(String userInput) {
        String hashedPsw = hashPassword(userInput);
        String storedHashedPsw = userStoredHashedPassword;
        return hashedPsw.equals(storedHashedPsw);
    }
}

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ByteBazaar {

    private static final String productsFilePath = "src/allProducts.txt";
    private static final AllProducts allProducts = new AllProducts();

    public static void main(String[] args) throws IOException {

        // our load all should be right here... we need the product dataset for almost all cases:
        // i.e. allProducts = loadAllProducts();
        try {
            loadAllProducts();
        } catch (IOException e) {
            System.out.println("The page requested could not be loaded. **ERROR**: \n" + e);
        }

        int userSelectedOption = 0;
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
                    
                    If you would like to see the status of your orders & see your cart,
                    or update your information, you must first login to your account.
                    
                    Please enter an integer, i.e. 1,2 or 3 reflective of your intended choice.
                    
                    """);

            if (onboardingScreenUserInput == null) {
                System.exit(0);
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
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Please select an integer from the following options provided!");
                        break;
                }
                ;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Oops..., we didn't recognise that." +
                        " Please enter an integer from the following options provided!");
            }
        } while (userSelectedOption != 1 && userSelectedOption != 2 && userSelectedOption != 3);
    }

    private static void loadAllProducts() throws IOException {
        System.out.println("Loading ByteBazaar products...\n");
        Path productsFile = Path.of(productsFilePath);

        List<String> lines = Files.readAllLines(productsFile);
        lines.removeFirst();

        for (String line : lines) {
            // originally was doing just line.split(",") and was wondering why when testing I was only getting singular
            // values for the tags etc. & I think it's splitting up the tags inside the brackets as well... so I had to
            // search up regex that makes sure to split the commas, but avoids doing so if the commas are within square
            // brackets.... & gemini provided me with this regex:
            String[] productInfo = line.split(",(?![^\\[\\]]*+\\])");

            String productId = productInfo[0];
            String productName = productInfo[1];
            // our dataset contains valid categories, but if we ever get an entry that say doesn't contain an
            // appropriate category, we add the check just in case:
            Category productCategory = Category.valueOf(productInfo[2].trim().toUpperCase());
            Brand productBrand = Brand.valueOf(productInfo[3]);

            // need to add try catch statements for these parse statements to handle NFEs:
            double productPrice = Double.parseDouble(productInfo[4]);
            int productQuantity = Integer.parseInt(productInfo[5]);
            float productRating = Float.parseFloat(productInfo[6]);

            boolean productIsWireless = productInfo[7].equalsIgnoreCase("yes");
            boolean productOnSale = productInfo[8].equalsIgnoreCase("yes");
            // some of the warranty values are null... so, ADD VALIDATION CHECK HERE:
            Integer productWarrantyYears = productInfo[9].equalsIgnoreCase("NA") ? null : Integer.parseInt(productInfo[9]);
            String productColour = productInfo[10];

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

            String productDescription = productInfo[12];
            String productDisplayImage = productInfo[13];

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
        }

    }

    // with these methods (i.e, signup, login, logout),how im thinking it would work is that we can just save the user
    // credentials to a data structure... on signup (& yes that includes a password) & then have the user login whilst
    // validating that the password/username they entered is correct, if correct we can just flip a boolean variable
    // called like loggedIn to true & that will remain true for the entire length of the program until the user closes
    // the program, or decides to log out manually (in which case the loggedIn value will be set to false).

    private static void signup() {
        // get the user to enter some basic details.... maybe we can somehow incorporate passwords onto here as well but
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
        //TODO: ADD VALIDATION TO MAKE SURE WE CATCH BAD INPUT ON THESE FIELDS:
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
                !checkIfNameIsAlphabetLetter(signupFormScreen)) {

            // user has pressed cancel/x so just get them out:
            if (signupFormScreen == null) {
                return;
            }

            JOptionPane.showMessageDialog(null, "Oops! Found invalid input, please enter " +
                    "your first name in letters only.");

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
                || !checkIfNameIsAlphabetLetter(lastName)) {

            if (lastName == null) {
                return;
            }

            JOptionPane.showMessageDialog(null, "Oops! Found invalid input, please enter " +
                    "your last name in letters only.");

            lastName = JOptionPane.showInputDialog("""
                    What is your last name?
                    """);
        }

        email = JOptionPane.showInputDialog("""
                       What is your email?
                """);

        while (email == null || email.trim().isEmpty()) {

            if (email == null) {
                return;
            }

            // TODO: regex check to check for @ & . in the email...
            if (!email.contains("@") || !email.contains(".")) {
                JOptionPane.showMessageDialog(null,
                        "Email must contain an '@' & '.' symbols.");
            }

            JOptionPane.showMessageDialog(null,
                    "Invalid email address, please enter a valid email address.");

            email = JOptionPane.showInputDialog("""
                           What is your email?
                    """);
        }

        password = JOptionPane.showInputDialog("""
                (Note that your info will not be saved after the program is closed, so you
                will need to signup again with your information).
                
                Please enter a password for your account (8 chars min, 30 chars max):
                """);

        while (password == null || password.trim().isEmpty()) {
            if (password == null) {
                return;
            }

            if (password.length() < 8) {
                JOptionPane.showMessageDialog(null,
                        "Password must be at least 8 characters long.");
            } else if (password.length() > 30) {
                JOptionPane.showMessageDialog(null,
                        "Password must be at most 30 characters long.");
            }

            JOptionPane.showMessageDialog(null,
                    "Please enter a valid password for your account.");

            password = JOptionPane.showInputDialog("""
                    (Note that your info will not be saved after the program is closed, so you
                    will need to signup again with your information).
                    
                    Please enter a password for your account (8 chars min, 30 chars max):
                    """);
        }

        phoneNumber = JOptionPane.showInputDialog("""
                 Please enter your phone number (7 chars min, 15 chars max):
                """);

        while (phoneNumber == null || phoneNumber.trim().isEmpty()
                || !checkIfDigitsOnly(phoneNumber)) {
            if (phoneNumber == null) {
                return;
            }

            JOptionPane.showMessageDialog(null,
                    "Invalid phone number, please enter a valid phone number.");

            if (phoneNumber.length() < 7) {
                JOptionPane.showMessageDialog(null,
                        "Phone number must be at least 7 characters long.");
            } else if (phoneNumber.length() > 15) {
                JOptionPane.showMessageDialog(null,
                        "Phone number must be at most 15 characters long.");
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


        }
    }

    private static void login() {
        // TODO
        // im thinking instead of password we just get them to enter their info, etc... & call the user record save it
        // to a data structure to hold
    }

    private static void logout() {

    }

    // helper methods:

    /**
     *
     * @param userInput
     * @return
     */
    private static boolean checkIfNameIsAlphabetLetter(String userInput) {
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
}

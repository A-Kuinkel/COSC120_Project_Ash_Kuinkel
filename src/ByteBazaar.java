import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ByteBazaar {

    private static final String productsFilePath = "src/allProducts.txt";


    public static void main(String[] args) {

        // our load all should be right here... we need the product dataset for almost all cases:
        // i.e. allProducts = loadAllProducts();

        int userSelectedOption = 0;
        do {
            String onboardingScreenUserInput = JOptionPane.showInputDialog("""
                    Welcome to ByteBazaar!
                    The home of affordable computer hardware parts & accessories
                    from all your favourite brands.
                    
                    Please select one of the following options:
                    1. Browse our list of products.
                    2. Signup for an account with ByteBazaar to become a member and order online.
                    3. Login to my ByteBazaar account.
                    
                    If you would like to see the status of your orders & see your cart,
                    you must first login to your account.
                    
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
                        try {
                            loadAllProducts();
                        } catch (IOException e) {
                            System.out.println("The page requested could not be loaded. **ERROR**: \n" + e);
                        }
                        break;
                    case 2:
                        // take to second page
                        System.out.println("Second page!");
                        break;
                    case 3:
                        // take to third page
                        System.out.println("Third page!");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Please select an integer from the following options provided!");
                        break;
                };
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Oops..., we didn't recognise that." +
                        " Please enter an integer from the following options provided!");
            }
        } while (userSelectedOption != 1 && userSelectedOption != 2 && userSelectedOption != 3);
    }

    private static void loadAllProducts() throws IOException {
        System.out.println("Loading ByteBazaar products...");
        Path productsFile = Path.of(productsFilePath);

        List<String> lines = Files.readAllLines(productsFile);
        lines.removeFirst();

        for(String line: lines){
            String[] productInfo = line.split(",");

            String productId = productInfo[0];
            String productName = productInfo[1];
            // our dataset contains valid categories, but if we ever get an entry that say doesn't contain an
            // appropriate category, we add the check just in case:
            Category productCategory = Category.valueOf(productInfo[2]);
            Brand productBrand = Brand.valueOf(productInfo[3]);

            // need to add try catch statements for these parse statements to handle NFEs:
            double productPrice = Double.parseDouble(productInfo[4]);
            int productQuantity = Integer.parseInt(productInfo[5]);
            float productRating = Float.parseFloat(productInfo[6]);

            boolean productIsWireless = productInfo[7].equalsIgnoreCase("yes");
            boolean productOnSale = productInfo[8].equalsIgnoreCase("yes");
            // some of the warranty values are null... so, ADD VALIDATION CHECK HERE:
            Integer productWarrantyYears = productInfo[9].equalsIgnoreCase("N/A") ? Integer.parseInt(productInfo[9]) : null;
            String productColour = productInfo[10];

            String formattedTagFromFile = productInfo[11].replace("[","")
                                                         .replace("]","");

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

            DreamProduct existingProductFeatures = new DreamProduct()

            Product productInstance =  new Product(productId,productName,productPrice,productQuantity,productRating,productWarrantyYears,productDescription,productDisplayImage);
            System.out.println(filteredProducts);
        }

    }
}

/**
 * import java.util.List;
 * import java.util.ArrayList;
 * import java.util.Arrays;
 *
 * class Main {
 *     public static void main(String[] args) {
 *         String productTagFromFile = "[Creator, High Performance]";
 *
 *         List<String> tagsArr = new ArrayList<>();
 *         for (String tag : productTagFromFile
 *                           .replace("[","")
 *                           .replace("]","")
 *                           .split(",")){
 *             System.out.println(tag.trim());
 *             tagsArr.add(tag.trim());
 *         }
 *         System.out.println("Tags arr:" + tagsArr);
 *
 *         String[] correctFormat = new String[tagsArr.size()];
 *
 *         for (int i = 0; i< tagsArr.size(); i++ ){
 *             correctFormat[i] = tagsArr.get(i);
 *         }
 *
 *         System.out.println("Maybe the correct format? " + Arrays.toString(correctFormat));
 *     }
 * }
 */

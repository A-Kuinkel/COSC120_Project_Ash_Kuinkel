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

            String productName = productInfo[1];
            String productPrice = productInfo[4];
            float productRating = Float.parseFloat(productInfo[6]);
            boolean productIsWireless = productInfo[7].equalsIgnoreCase("yes");
            boolean productOnSale = productInfo[8].equalsIgnoreCase("yes");
            // some of the warranty values are null... so, ADD VALIDATION CHECK HERE:
            Integer productWarrantyYears = productInfo[9].equalsIgnoreCase("N/A") ? Integer.parseInt(productInfo[9]) : null;
            String productColour = productInfo[10];
            String productDescription = productInfo[12];
            String productDisplayImage = productInfo[13];

            List<String> filteredProducts = new ArrayList<>();
            filteredProducts.add(productInfo[0]);
            filteredProducts.add(productName);
            filteredProducts.add(productPrice);
            System.out.println(filteredProducts);
        }

    }
}

/**
 * @author Ash Kuinkel (akuinke3@myune.edu.au)
 * created for COSC120 Assignment Task 3 (Trimester 2, 2026)
 *
 * Git repository Link: https://github.com/A-Kuinkel/COSC120_Project_Ash_Kuinkel
 */

import java.util.ArrayList;
import java.util.List;

/**
 * The session-based cart class. The job of this class is to manage the user's cart. When the user logs-in we assign
 * a new instance of the Cart class to them & then this cart is able to add products, clear etc. Note that since the
 * assignment says we are only allowed one txt file, we cannot save the users cart after the program is closed.
 */
public class Cart {

    // fields
    private final List<Product> products = new ArrayList<>(); //
    private final User user; // declared as final because

    /**
     *
     * @param user
     */
    Cart (User user) {
        this.user = user;
    }

    // getters/setters

    /**
     * Method which
     * @return
     */
    public List<Product> getProducts() {return new ArrayList<>(products);}

    /**
     *
     * @return
     */
    public User getUser() {return user;}

    // methods

    /**
     * Method to add a product to the user's cart. Called from the program class.
     * @param product represents the product that is to be added to the cart.
     */
    public void addProductToCart(Product product) {
        products.add(product);
    }

    /**
     * Method to clear the user's cart {uses the convenient .clear() method for ArrayLists in Java}. Program
     * features clear cart method only rather than a method to remove a singular product from cart for simplicity.
     */
    public void clearCart() {
        products.clear();
    }

    /**
     * Method to calculate the total price of all the products within the cart.
     * @return the total price of all the items which the user is required to pay.
     */
    public double getTotalPrice() {
        double total = 0;
        for (Product product : products) total += product.getPrice();
        return total;
    }

    /**
     * Method to check if the user's cart is empty at a certain moment, i.e. no products in cart.
     * @return true if the Cart is empty else return false.
     */
    public boolean isEmpty(){
        return products.isEmpty();
    }

    /**
     *
     * @return the well-formatted string containing the user's cart (to be displayed in JOptionPne window).
     */
    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        for (Product  product : products) {
            String productName =  product.getName();
            double productPrice = product.getPrice();
            String msg = "\n Product: " + productName + "\n Cost: " + productPrice
                    + "\n =======================================================";
            stringBuilder.append(msg);
        }

        return "**CART OVERVIEW**\n" + stringBuilder;
    }

}

/**
 * @author Ash Kuinkel (akuinke3@myune.edu.au)
 * created for COSC120 Assignment Task 3 (Trimester 2, 2026)
 *
 * Git repository Link: https://github.com/A-Kuinkel/COSC120_Project_Ash_Kuinkel
 */

import javax.swing.*;

/**
 * This class contains the check-out screen functionality. It exists independently of the main program class ByteBazaar
 * to ensure that the main program is what controls the overall flow of program & calls other methods rather than
 * implementing specific functionality itself.
 */
public class CheckoutScreen {
    private final Authentication authentication;

    /**
     * Builds the checkout screen using the authentication object which is passed in from the program class. Note
     * that we do not create a new instance of authentication here, simply just use the one passed in.
     * @param authentication -> which is the authentication object we use to be able to access the cart/order of the
     *                       user.
     */
    public CheckoutScreen(Authentication authentication) {
        this.authentication = authentication;
    }

    /**
     * Method which displays the options for the user in the check-out screen as well as responds with the appropriate
     * output. The implementation of this is quite similar to the main method in program class. However, the difference
     * with this class is that the functionality of what is to be done for each selected option is something this method
     * should know/do. Besides this, there aren't many comments for this class as I feel like the code in every line is
     * very clear especially because of the naming of methods.
     */
    public void cartAndOrderScreen() {
        if (authentication.getSignedInUser() == null) {
            JOptionPane.showMessageDialog(null, """
                    Please ensure that you are logged in first, before attempting to view cart & orders!
                    """);
            return;
        }

        int cartScreenSelectedOption;
        String mainScreen = JOptionPane.showInputDialog(null, """
                This is where you are able to access your cart & place orders!
                
                Please select one of the following options:
                1. View my Cart (& check out)
                2. Clear my Cart
                3. View my Orders
                
                Please enter an integer, i.e. 1,2,3 reflective of your intended choice:
                
                """);
        if (mainScreen == null) {
            return;
        }
        try {
            cartScreenSelectedOption = Integer.parseInt(mainScreen);
            switch (cartScreenSelectedOption) {
                case 1:
                    if (authentication.getCart().isEmpty()) {
                        JOptionPane.showMessageDialog(null, """
                                Nothing to see here, please add some products to cart & come back.
                                """);
                        return;
                    }
                    String cartOverview = authentication.getCart().toString() + "\n TOTAL: $" +
                            authentication.getCart().getTotalPrice() +
                            "\n Would you like to checkout & place an order?\n";
                    int orderInput = JOptionPane.showConfirmDialog(null, cartOverview, "Cart",
                            JOptionPane.YES_NO_OPTION);
                    if (JOptionPane.YES_OPTION == orderInput) {
                        // if yes -> user does want to place the order -> delegate responsibility to order class:
                        for (Product product : authentication.getCart().getProducts()) {
                            authentication.getOrder().addItemsToOrder(product);
                        }
                        ByteBazaar.saveOrderToFile(authentication.getOrder());
                        JOptionPane.showMessageDialog(null, """
                                Order successfully placed! Items will be dispatched & you will be notified.
                                We deeply thank you for choosing us, ByteBazaar.
                                """, "Order Confirmed", JOptionPane.INFORMATION_MESSAGE);
                        // clearing cart after order placed... we don't want user to accidentally double pay for these
                        // items by accident or something, if they want, they can add the same products to cart again:
                        authentication.getCart().clearCart();
                    }
                    break;
                case 2:
                    if (authentication.getCart().isEmpty()) {
                        JOptionPane.showMessageDialog(null, """
                                You cannot clear an empty cart, please ensure that you have products in your cart,
                                before attempting to clear the cart
                                """);
                        return;
                    }
                    int clearCartOption = JOptionPane.showConfirmDialog(null, """
                            Are you sure you want to clear your cart?
                            """, "Clear Cart", JOptionPane.YES_NO_OPTION);
                    if (JOptionPane.YES_OPTION == clearCartOption) {
                        authentication.getCart().clearCart();
                        JOptionPane.showMessageDialog(null, "Cart successfully cleared!");
                    }
                    break;
                case 3:
                    if (authentication.getOrder().isEmpty()) {
                        JOptionPane.showMessageDialog(null, """
                                Nothing to see here, please confirm an order & come back.
                                """);
                        return;
                    }
                    JOptionPane.showMessageDialog(null, authentication.getOrder().toString(),
                            "My Orders", JOptionPane.INFORMATION_MESSAGE);
                    break;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Oops..., we didn't recognise that." +
                    " Please enter an integer from the following options provided!");
        }
    }
}

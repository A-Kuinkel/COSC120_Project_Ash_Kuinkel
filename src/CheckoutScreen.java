import javax.swing.*;

/**
 *
 */
public class CheckoutScreen {
    private final Authentication authentication;

    public CheckoutScreen(Authentication authentication) {
        this.authentication = authentication; // because we declared our Authentication as static, IntelliJ
        // recommends that we
    }

    /**
     *
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
                        for (Product product : authentication.getCart().getProducts()) {
                            authentication.getOrder().addItemsToOrder(product);
                        }
                        ByteBazaar.saveOrderToFile(authentication.getOrder());
                        JOptionPane.showMessageDialog(null, """
                                Order successfully placed! Items will be dispatched & you will be notified.
                                We deeply thank you for choosing us, ByteBazaar.
                                """, "Order Confirmed", JOptionPane.INFORMATION_MESSAGE);
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

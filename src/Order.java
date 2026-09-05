/**
 * @author Ash Kuinkel (akuinke3@myune.edu.au)
 * created for COSC120 Assignment Task 3 (Trimester 2, 2026)
 *
 * Git repository Link: https://github.com/A-Kuinkel/COSC120_Project_Ash_Kuinkel
 */

import java.util.ArrayList;
import java.util.List;

/**
 * The session-based order class which manages the user's order. Like the cart functionality, upon user log in, a new
 * instance of the order class is created which is able to add items to an order & return order info. However, in
 * contrast to the cart, the order is saved to a txt file when the user confirms/places the order.
 */
public class Order {

    private final User userInfo; // contains the information of the user the order is associated with. Final as this
    // is session-based, meaning it won't change.

    private final List<Product> itemsInfo = new ArrayList<>(); // holds the items of the order

    /**
     * Constructs an order for a specific user. This order will be associated with the user until they close the
     * program.
     * @param userInfo the information of the specific user.
     */
    Order(User userInfo) {
        this.userInfo = userInfo;
    }

    /**
     * Method which retrieves the user who placed the order's information.
     * @return an instance of the User class containing the user information for the order.
     */
    public User getUserInfo() {return userInfo;}

    /**
     * Method which allows end-users to add products they would like to order, to their order.
     * @param product a product which is ordered by the user, to be delivered to the user.
     */
    public void addItemsToOrder(Product product) {itemsInfo.add(product);}

    /**
     * Method to check if an order has been placed by the user.
     * @return true if there is no current order placed by the user, else return false to indicate there is an order.
     */
    public boolean isEmpty() {return itemsInfo.isEmpty();}

    /**
     * Method to override the original toString() method for this class to display user's order in custom format.
     * @return the well-formatted string containing the user's full order.
     */
    @Override
    public String toString() {

        StringBuilder holdFormattedItemsInfo = new StringBuilder();

        for (Product product : itemsInfo){
            holdFormattedItemsInfo.append(product.getName()).append(",\n ");
        }

        return "\n**ORDER OVERVIEW**\n" + "\n" + "**BUYER INFORMATION**\n" + "Buyer Full Name: "
                + userInfo.fullName() + "\n" + "Buyer Email: " + userInfo.email() + "\n" + "Buyer Phone Number: "
                + userInfo.phoneNumber() + "\n" + "Buyer Shipping Address: " + userInfo.shippingAddress() + "\n" +
                "\n**Items: **\n" + holdFormattedItemsInfo;
    }


}

/**
 * @author Ash Kuinkel (akuinke3@myune.edu.au)
 * created for COSC120 Assignment Task 3 (Trimester 2, 2026)
 *
 * Git repository Link: https://github.com/A-Kuinkel/COSC120_Project_Ash_Kuinkel
 */

import java.util.ArrayList;
import java.util.List;

public class Order {

    // fields:
    private final User userInfo;
    private final List<Product> itemsInfo = new ArrayList<>();

    Order(User userInfo) {
        this.userInfo = userInfo;
    }

    public User getUserInfo() {return userInfo;}
    public void addItemsToOrder(Product product) {itemsInfo.add(product);}

    public boolean isEmpty() {return itemsInfo.isEmpty();}

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

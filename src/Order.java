import java.util.ArrayList;
import java.util.List;

public class Order {

    // fields:
    private final User userInfo;
    private final List<Product> itemsInfo;

    Order(User userInfo, List<Product> itemsInfo) {
        this.userInfo = userInfo;
        this.itemsInfo = itemsInfo;
    }

    // getters/setters:
    public User getUserInfo() {return userInfo;}
    public List<Product> getItemsInfo() {return itemsInfo;}

    // methods:

    @Override
    public String toString() {

        StringBuilder holdFormattedItemsInfo = new StringBuilder();

        for (Product product : itemsInfo){
            holdFormattedItemsInfo.append(product.getProductInfo());
        }

        return "\n**ORDER OVERVIEW**\n" + "\n" + "**BUYER INFORMATION**\n" + "Buyer Full Name: "
                + userInfo.fullName() + "\n" + "Buyer Email: " + userInfo.email() + "\n" + "Buyer Phone Number: "
                + userInfo.phoneNumber() + "\n" + "Buyer Shipping Address: " + userInfo.shippingAddress() + "\n" +
                "\n**PRODUCT INFORMATION**\n" + holdFormattedItemsInfo;
    }


}

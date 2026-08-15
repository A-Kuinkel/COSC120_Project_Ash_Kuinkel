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

    public String getOrderOverview(){
        // TODO
        return "";
    }


}

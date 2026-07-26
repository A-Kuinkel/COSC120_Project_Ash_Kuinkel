import java.util.ArrayList;
import java.util.List;

public class Order {

    // fields:
    private List<User> userInfo;
    private List<Product> itemsInfo;

    Order(List<User> userInfo, List<Product> itemsInfo) {
        this.userInfo = userInfo;
        this.itemsInfo = itemsInfo;
    }

    // getters/setters:
    public List<User> getUserInfo() {return userInfo;}
    public List<Product> getItemsInfo() {return itemsInfo;}


}

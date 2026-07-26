import java.util.ArrayList;
import java.util.List;

public class Cart {

    // make sure we do this so that user cart is stored in users.txt file, i.e. their cart is stored as part of their info
    // and when they log in, their cart will be reflective of the items they want.... meaning upon updates, we should also
    // change contents of the file...

    // fields
    List<Product> products;
    User user;

    Cart (List<Product> products, User user) {
        this.products = new ArrayList<>(products); // hmm is this correct? Because we actually do want to end up changing the cart not just a copy of it.
        this.user = user;
    }

    // getters/setters
    public List<Product> getProducts() {return products;}
    public User getUser() {return user;}

    // methods
    public int getTotalPrice(){return 0;} // ** TO DO **

}

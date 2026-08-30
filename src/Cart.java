import java.util.ArrayList;
import java.util.List;

public class Cart {

    // The source code is only meant to have like one txt file... so this means that what I was thinking before
    // shouldn't really be the way to approach it. Better to just have the cart remember only the current running
    // session because there isn't really anywhere appropriate we can save the user's cart/order info without creating
    // another .txt db file.

    // fields
    List<Product> products;
    User user;

    Cart (List<Product> products, User user) {
        this.products = new ArrayList<>(products);
        this.user = user;
    }

    // getters/setters
    public List<Product> getProducts() {return products;}
    public User getUser() {return user;}

    // methods
    public void addProductToCart(Product product) {
        products.add(product);
    }

    public void removeProductFromCart(Product product) {
        products.remove(product);
    }

    public void clearCart() {
        products.clear();
    }

    public double getTotalPrice() {
        double total = 0;
        for (Product product : products) total += product.getPrice();
        return total;
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        for (Product  product : products) {
            stringBuilder.append(product.getProductInfo());
        }

        return "\n**CART OVERVIEW**\n" + stringBuilder;
    }

}

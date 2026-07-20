import enums.Brand;
import enums.Category;

public class Product {

    // fields:
    final private int id;
    private String name;
    private float price;
    private String description;
    private int quantity;
    Category category;
    Brand brand;

    // constructor
    Product(int id, String name, float Price, String description,
            int quantity, Category category, Brand brand){
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.category = category;
        this.brand = brand;
    }

    // getters/setters:

    // methods:
}

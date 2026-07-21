import enums.Brand;
import enums.Category;

import java.util.ArrayList;
import java.util.List;

public class DreamProduct {

    // fields
    private String name;
    private Category category;
    private Brand brand;
    private List<String> tags;
    private final int minPrice;
    private final int maxPrice;

    //constructor
    DreamProduct (String name, Category category, Brand brand,
                  List<String> tags, int minPrice, int maxPrice) {
        this.name = name;
        this.category = category;
        this.brand = brand;
        if (tags!=null) this.tags = new ArrayList<>(tags);
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    };

    // getters/setters

    // methods
}

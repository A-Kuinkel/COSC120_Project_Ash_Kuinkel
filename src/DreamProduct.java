import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class DreamProduct {

    // fields
    private String name;
    private Category category;
    private Brand brand;
    private List<String> tags;
    private final double minPrice;
    private final double maxPrice;
    private final boolean mustBeWireless;

    //constructor
    DreamProduct (String name, Category category, Brand brand,
                  List<String> tags, int minPrice, int maxPrice, boolean mustBeWireless) {
        this.name = name;
        this.category = category;
        this.brand = brand;
        if (tags!=null) this.tags = new ArrayList<>(tags);
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.mustBeWireless = mustBeWireless;
    };

    // getters/setters

    // methods
}

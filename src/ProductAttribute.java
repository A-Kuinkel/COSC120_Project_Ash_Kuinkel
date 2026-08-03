public enum ProductAttribute {

    CATEGORY,BRAND,TAGS,WIRELESS,COLOUR,ON_SALE;

    public String toString(){
        return switch (this){
            case CATEGORY -> "Category";
            case BRAND -> "Brand";
            case TAGS -> "Tags";
            case WIRELESS -> "Wireless";
            case COLOUR -> "Colour";
            case ON_SALE -> "On Sale";
        };
    }
}

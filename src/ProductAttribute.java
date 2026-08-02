public enum ProductAttribute {

    CATEGORY,BRAND,TAGS,WIRELESS;

    public String toString(){
        return switch (this){
            case CATEGORY -> "Category";
            case BRAND -> "Brand";
            case TAGS -> "Tags";
            case WIRELESS -> "Wireless";
        };
    }
}

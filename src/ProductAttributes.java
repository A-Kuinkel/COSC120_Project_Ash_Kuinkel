/**
 * @author Ash Kuinkel (akuinke3@myune.edu.au)
 * created for COSC120 Assignment Task 3 (Trimester 2, 2026)
 *
 * Git repository Link: https://github.com/A-Kuinkel/COSC120_Project_Ash_Kuinkel
 */

public enum ProductAttributes {

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

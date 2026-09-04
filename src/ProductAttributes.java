/**
 * @author Ash Kuinkel (akuinke3@myune.edu.au)
 * created for COSC120 Assignment Task 3 (Trimester 2, 2026)
 *
 * Git repository Link: https://github.com/A-Kuinkel/COSC120_Project_Ash_Kuinkel
 */

/**
 * The ProductAttributes enum which contains shareable attributes existing in both a dream product & product. The idea
 * of this is adapted from the COSC120 Lecture 7 Video 2 Criteria.java lines (7-21).
 */
public enum ProductAttributes {

    CATEGORY,BRAND,TAGS,WIRELESS,COLOUR,ON_SALE;

    /**
     * This is a toString() method, implemented so that the productAttribute enums can be expressed in a prettier manner.
     * @return just a nicer string version of the productAttributes.
     */
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

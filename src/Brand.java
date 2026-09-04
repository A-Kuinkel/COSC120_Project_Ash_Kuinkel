/**
 * @author Ash Kuinkel (akuinke3@myune.edu.au)
 * created for COSC120 Assignment Task 3 (Trimester 2, 2026)
 *
 * Git repository Link: https://github.com/A-Kuinkel/COSC120_Project_Ash_Kuinkel
 */

/**
 * The Brand enum which contains the different brands available for our products. Note that the idea of this
 * is adapted from the COSC120 Lecture 7 Video 2 Gender.java lines (6-15).
 */
public enum Brand {
    // our list of brands:
    AMD, ASUS, APPLE, COOLERMASTER, DELL, HP, INTEL, LENOVO, LG, LOGITECH, MSI, NVIDIA, SAMSUNG;

    /**
     * This is a toString() method, so that the brand enums can be expressed in a more aesthetically pleasing way.
     * @return just a nicer string version of the brand name e.g. 'Cooler Master' instead of 'COOLERMASTER'.
     */
    @Override
    public String toString(){
        return switch (this){
            case AMD -> "AMD";
            case ASUS -> "ASUS";
            case APPLE -> "Apple";
            case COOLERMASTER -> "Cooler Master";
            case DELL -> "Dell";
            case HP -> "HP";
            case INTEL -> "Intel";
            case LENOVO -> "Lenovo";
            case LG -> "LG";
            case LOGITECH -> "Logitech";
            case MSI -> "MSI";
            case NVIDIA -> "NVIDIA";
            case SAMSUNG -> "Samsung";
        };
    }
}

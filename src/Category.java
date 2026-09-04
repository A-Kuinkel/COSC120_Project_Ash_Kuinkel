/**
 * @author Ash Kuinkel (akuinke3@myune.edu.au)
 * created for COSC120 Assignment Task 3 (Trimester 2, 2026)
 *
 * Git repository Link: https://github.com/A-Kuinkel/COSC120_Project_Ash_Kuinkel
 */

/**
 * The Category enum which contains the different categories available for our products. Note that the idea of this
 * is adapted from the COSC120 Lecture 7 Video 2 Religion.java lines (10-25).
 */
public enum Category {
    // categories, i.e. laptop, mouse, keyboards etc
    LAPTOPS, MOUSE, KEYBOARD, MONITOR, DESKTOP, GPU, CPU, RAM, PSU, CASE, COOLING;

    /**
     * This is a toString() method, implemented so that the category enums can be expressed in a more pretty way.
     * @return just a nicer string version of the category name e.g. 'Prebuilt Desktops' instead of just 'DESKTOP'.
     */
    @Override
    public String toString(){
        return switch (this) {
            case MOUSE -> "Mouse";
            case LAPTOPS -> "Laptops";
            case KEYBOARD -> "Keyboards";
            case MONITOR -> "Monitors";
            case DESKTOP -> "Prebuilt Desktops";
            case GPU -> "GPU";
            case CPU -> "CPU";
            case RAM -> "RAM";
            case PSU -> "PSU";
            case CASE -> "PC Cases";
            case COOLING -> "Cooling";
        };
    }
}

package enums;

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

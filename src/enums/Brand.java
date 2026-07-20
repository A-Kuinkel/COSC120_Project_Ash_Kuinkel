package enums;

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

public static void main(String[] args){
    Car car1 = new Car("Mazda","red",false);

    System.out.println(car1.toString());
}

public static class Car{
    private final String brand;
    private final String colour;
    private final boolean is_4wd;

    public Car(String brand, String colour, boolean is_4wd){
        this.brand = brand;
        this.colour = colour;
        this.is_4wd = is_4wd;
    }
}



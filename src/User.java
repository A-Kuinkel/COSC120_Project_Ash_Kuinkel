public class User {

    private String firstName;
    private String lastName;
    private String contactEmail;
    private int contactPhoneNumber;
    private String deliveryAddress;

    User(String firstName, String lastName, String contactEmail, int contactPhoneNumber, String deliveryAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactEmail = contactEmail;
        this.contactPhoneNumber = contactPhoneNumber;
        this.deliveryAddress = deliveryAddress;
    }

    // getters/setters:
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public String getContactEmail() {return contactEmail;}
    public int getContactPhoneNumber() {return contactPhoneNumber;}
    public String getDeliveryAddress() {return deliveryAddress;}
}

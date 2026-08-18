public record User (String firstName, String lastName, String email, String password, String phoneNumber, String shippingAddress){

    public String fullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String email() {
        return email;
    }

    @Override
    public String phoneNumber() {
        return phoneNumber;
    }

    @Override
    public String shippingAddress() {
        return shippingAddress;
    }
}

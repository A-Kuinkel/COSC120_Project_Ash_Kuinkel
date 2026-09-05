/**
 * @author Ash Kuinkel (akuinke3@myune.edu.au)
 * created for COSC120 Assignment Task 3 (Trimester 2, 2026)
 *
 * Git repository Link: https://github.com/A-Kuinkel/COSC120_Project_Ash_Kuinkel
 */

public record User (String firstName, String lastName, String email, String password, String phoneNumber,
                    String shippingAddress){

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

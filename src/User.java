/**
 * @author Ash Kuinkel (akuinke3@myune.edu.au)
 * created for COSC120 Assignment Task 3 (Trimester 2, 2026)
 *
 * Git repository Link: https://github.com/A-Kuinkel/COSC120_Project_Ash_Kuinkel
 */

/**
 * The user record. Initially a User class was constructed, however finding that a lot of the code is just simple
 * boilerplate which could've been handled better with a record, the 'User' class transitioned from class to record.
 * This record defines the fields/attributes that each 'User' must contain during the signup. The idea that a record
 * could come in handy rather than a class & the implementation of this is adapted from COSC120 Lecture 3 Video 2
 * GeekV5.java lines (15-38).
 *
 * @param firstName -> the user's first name
 * @param lastName -> the user's last name
 * @param email -> the user's email.
 * @param password -> the user's password.
 * @param phoneNumber -> the user's phone number.
 * @param shippingAddress -> the user's shipping address (where the order will be delivered).
 */
public record User (String firstName, String lastName, String email, String password, String phoneNumber,
                    String shippingAddress){

    /**
     * Method to output the fullName of the user. The fullName is quite handy to have, so we build a method for this,
     * but note that the record already handles fields, constructors, getters.
     * @return the fullName of the user, combining both the first & last names with a space.
     */
    public String fullName() {
        return firstName + " " + lastName;
    }
}

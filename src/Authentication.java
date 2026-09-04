/**
 * @author Ash Kuinkel (akuinke3@myune.edu.au)
 * created for COSC120 Assignment Task 3 (Trimester 2, 2026)
 *
 * Git repository Link: https://github.com/A-Kuinkel/COSC120_Project_Ash_Kuinkel
 */

import javax.swing.*;

/**
 * The Authentication class which reflects the permissions the user has for the program, i.e. whether they are able to
 * place orders, send custom requests etc. Note that the authentication of this program is session-based. Every time
 * you run the program you must sign up & login. This class is the middleman for accessing the functionality of our
 * order/cart classes, as both of these i.e. order/cart is associated with the one user.
 */
public class Authentication {
    private boolean loggedIn = false; // switch to track if user is logged in or not
    // gets updated on signup/login/logout etc.
    private String userStoredPass; // only one .txt file allowed, so the password will float around
    // as a variable
    private User tempUserAccountInfoHolder; // temporarily stores the user account info after signup & before login
    private User signedInUser;
    private Cart cart; // not final as this is subject to change
    private Order order; // not final as this is subject to change

    /**
     * Method which tracks the login status of the current user.
     * @return -> true if the user is logged in else return false if the user is not logged in.
     */
    public boolean isLoggedIn() {return loggedIn;}

    /**
     * Method to retrieve the information of the logged-in user.
     * @return -> the instance of the User class with the User's info if signed in, else returns null.
     */
    public User getSignedInUser() {return signedInUser;}

    /**
     * Method to access the cart of the current logged-in user.
     * @return -> the logged-in users cart including the products that are within the cart currently. If there are no
     * products in the cart, then an empty list is handed back & this scenario is handled in CheckOutScreen class.
     */
    public Cart getCart() {return cart;}

    /**
     * Method which fetches the user's order.
     * @return -> the order which the user has placed. If no order has been place yet, like the cart, an empty list is
     * handed back & this scenario has also been handled in the CheckoutScreen class.
     */
    public Order getOrder() {return order;}

    /**
     *
     */
    public void signup() {
        // get the tempUserAccountInfoHolder to enter some basic details.... maybe we can somehow incorporate passwords onto here as well but
        // how the passwords would work is that it would just be kept in a data structure & be held temporarily for the
        // current session. This is the only way we are able to enable passwords whilst only keeping the one product txt
        // file... If we were allowed to have another txt file, we could make one called like allUsers & possibly
        // save just plain-text passwords to it... but we can't with just one txt... no way passwords can be stored
        // in a product db:
        while (true) {
            JTextField fNameInput = new JTextField();
            JTextField lNameInput = new JTextField();
            JTextField eMailInput = new JTextField();
            JPasswordField passInput = new JPasswordField();
            JTextField pNumInput = new JTextField();
            JTextField sAddrInput = new JTextField();
            String introText = """
                    **SIGNUP FORM**
                    
                      Thank you for signing up & becoming a member with ByteBazaar.
                      By creating an account, you are able to order our products online!
                      Please fill out the form on the next page appropriately.
                    """;

            Object[] options = {introText, "\n", " First Name:\n", fNameInput, "\n", "Last Name:\n", lNameInput, "\n", "Email:\n", eMailInput,
                    "\n", "Password (Must be 8-30 chars long):\n", passInput, "\n",
                    "Phone Number (Must be 7-15 digits long):\n", pNumInput, "\n", "Shipping Address:\n", sAddrInput};

            int option = JOptionPane.showConfirmDialog(null, options, "Signup",
                    JOptionPane.OK_CANCEL_OPTION);

            if (option != JOptionPane.OK_OPTION) {
                return;
            }

            String firstName = fNameInput.getText().trim();
            String lastName = lNameInput.getText().trim();
            String email = eMailInput.getText().trim();
            String password = new String(passInput.getPassword());// lets not trim password..
            String phoneNumber = pNumInput.getText().trim();
            String shippingAddress = sAddrInput.getText().trim();

            // validation logic to make sure we don't get bad input from user:
            if (firstName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a valid first name.");
                continue;
            } else if (!checkIfLettersOnly(firstName)) {
                JOptionPane.showMessageDialog(null, """
                        Oops! Found invalid input, please enter your first name in letters only.
                        """);
                continue;
            }

            if (lastName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a valid last name.");
                continue;
            } else if (!checkIfLettersOnly(lastName)) {
                JOptionPane.showMessageDialog(null, """
                        Oops! Found invalid input, please enter your last name in letters only.
                        """);
                continue;
            }

            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a valid email.");
                continue;
            } else if (!email.contains("@") || !email.contains(".")) {
                JOptionPane.showMessageDialog(null, """
                        Oops! Found invalid input; Email must contain '@' & '.' symbols.
                        """);
                continue;
            }

            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Please enter a valid password for your account.");
                continue;
            } else if (password.length() < 8) {
                JOptionPane.showMessageDialog(null,
                        "Password must be at least 8 characters long.");
                continue;
            } else if (password.length() > 30) {
                JOptionPane.showMessageDialog(null,
                        "Password must be at most 30 characters long.");
                continue;
            }

            // storing our password:
            userStoredPass = password;

            if (phoneNumber.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Invalid phone number, please enter a valid phone number.");
                continue;
            } else if (!checkIfDigitsOnly(phoneNumber)) {
                JOptionPane.showMessageDialog(null, """
                        Oops! Found invalid phone number; Phone number must contain digits only.");
                        """);
                continue;
            } else if (phoneNumber.length() < 7) {
                JOptionPane.showMessageDialog(null, """
                        Oops! Found invalid input; Phone number must be at least 7 characters long.");
                        """);
                continue;
            } else if (phoneNumber.length() > 15) {
                JOptionPane.showMessageDialog(null, """
                        Oops! Found invalid input; Phone number must be at most 15 characters long.");
                        """);
                continue;
            }

            if (shippingAddress.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Please enter a valid shipping address");
                continue;
            }

            tempUserAccountInfoHolder = new User(firstName, lastName, email, userStoredPass,
                    phoneNumber, shippingAddress);
            JOptionPane.showMessageDialog(null, "Successfully signed up!");
            return;
        }
    }

    /**
     *
     */
    public void login() {
        // im thinking  call the tempUserAccountInfoHolder record save info to it for a data structure to hold...

        // note that a validation to check for like @ or . in the email isn't really needed for login
        // method, as this is validated during the signin & for login, we can just check if email exists
        // only:
        // Also I need to guard against people trying to log in before signing up; because right
        // now it will just throw an error saying like Cannot invoke User.email() because
        // ByteBazaar.tempUserAccountInfoHolder is null....
        if (tempUserAccountInfoHolder == null) {
            JOptionPane.showMessageDialog(null, """
                    You must first create an account before you can login!
                    
                    Please return to the sign up page, sign up for an account & then login.
                    """);
            return;
        }

        while (true) {
            JTextField eMailInput = new JTextField();
            JPasswordField passInput = new JPasswordField();
            String introText = """
                    ** LOGIN FORM **
                    
                    After logging in, you will be able to place orders,
                    view cart and update your information.
                    
                    Please note once again that, this is session based,
                    i.e. your information is retained until you close
                    the program. Upon reopening, you will need to sign
                    up for an account & log in again. Thanks for under-
                    standing :)
                    """;

            Object[] components = {
                    introText, "\n", "Email:\n", eMailInput, "\n", "Password:\n", passInput, "\n"
            };

            int option = JOptionPane.showConfirmDialog(null, components, "Login",
                    JOptionPane.OK_CANCEL_OPTION);
            if (option != JOptionPane.OK_OPTION) {
                return;
            }

            String email = eMailInput.getText().trim();
            String password = new String(passInput.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Please enter a valid email + password combination.");
                continue;
            }

            // checking the password & email... note this is also a security concept, here... we shouldn't inform the
            // user if the password was incorrect or the email was incorrect, else an attacker's life will be much
            // easier.... really not so necessary here, but good to add:
            if (email.equals(tempUserAccountInfoHolder.email()) &&
                    comparePasswords(password)) {
                loggedIn = true;
                signedInUser = tempUserAccountInfoHolder;
                // creating a cart/order for the session:
                cart = new Cart(signedInUser);
                order = new Order(signedInUser);
                JOptionPane.showMessageDialog(null, "Successfully logged in!");
                return;
            }

            JOptionPane.showMessageDialog(null,
                    "Invalid Credentials, please try again.");
        }
    }

    /**
     *
     */
    public void logout() {
        if (signedInUser == null) {
            JOptionPane.showMessageDialog(null, """
                    Please ensure that you are logged in first, before attempting to sign out!
                    """);
            return;
        }
        loggedIn = false;
        signedInUser = null;
        cart = null;
        order = null;
        JOptionPane.showMessageDialog(null, "Successfully logged out!");
    }

    // helper methods:

    /**
     * Method which checks if the passed in string param features only letters.
     * @param userInput -> user string input which is to be checked.
     * @return true if the string contains letters only, else return false.
     */
    private static boolean checkIfLettersOnly(String userInput) {
        for (int i = 0; i < userInput.length(); i++) {
            // it turns out that java has a method Character.isLetter() which works out pretty well here:
            if (!Character.isLetter(userInput.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method which checks if the passed in string param features only digits:
     * @param userInput -> user string input which is to be checked.
     * @return true if the string contains digits only, else return false.
     */
    private static boolean checkIfDigitsOnly(String userInput) {
        for (int i = 0; i < userInput.length(); i++) {
            // like the Character.isLetter(), java also has the .isDigit() method, so we use this to check:
            if (!Character.isDigit(userInput.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method which compares the password the user has set for their account against the current guess.
     * @param userInput -> the user's current guess attempt at the password.
     * @return -> true if the user has entered the correct matching password, else return false.
     */
    private boolean comparePasswords(String userInput) {
        String storedPass = userStoredPass;
        return storedPass.equals(userInput);
    }
}

import javax.swing.*;

public class ByteBazaar {

    public static void main(String[] args) {

        int userSelectedOption = 0;
        do {
            String onboardingScreenUserInput = JOptionPane.showInputDialog("""
                    Welcome to ByteBazaar!
                    The home of affordable computer hardware parts & accessories
                    from all your favourite brands.
                    
                    Please select one of the following options:
                    1. Browse our list of products.
                    2. Signup for an account with ByteBazaar to become a member and order online.
                    3. Login to my ByteBazaar account.
                    
                    If you would like to see the status of your orders & see your cart,
                    you must first login to your account.
                    
                    Please enter an integer, i.e. 1,2 or 3 reflective of your intended choice.
                    
                    """);

            if (onboardingScreenUserInput == null) {
                System.exit(0);
            }

            try {
                userSelectedOption = Integer.parseInt(onboardingScreenUserInput);

                switch (userSelectedOption) {
                    case 1:
                        // take to first page
                        System.out.println("First page!");
                        break;
                    case 2:
                        // take to second page
                        System.out.println("Second page!");
                        break;
                    case 3:
                        // take to third page
                        System.out.println("Third page!");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Please select an integer from the following options provided!");
                        break;
                };
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Oops..., we didn't recognise that." +
                        " Please enter an integer from the following options provided!");
            }
        } while (userSelectedOption != 1 && userSelectedOption != 2 && userSelectedOption != 3);
    }
}

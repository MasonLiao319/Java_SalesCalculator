import javax.swing.JOptionPane;

public class SalesCalculator {

    // Main method to start the sales calculator application
    public void start() {
        String choice = ""; // User's choice to continue or not
        int count = 0; // Counter for processed salespeople
        double sumOfSales = 0; // Total sales across all salespeople
        double averageSale; // Average sale value
        int mostSoldType = 0; // Item type that is most sold
        int mostSoldNumber = 0; // Number of items sold for the most sold item type
        String topSalesPerson = ""; // Name of the top salesperson
        double topSalesAmount = 0; // Sales amount of the top salesperson

        int[] totalItemsSold = new int[4]; // Array to store total items sold for each item type

        //Defines the location of the dialog box. If pass null, the dialog box will be centered on the screen.
        JOptionPane.showMessageDialog(null, "Welcome to the Sales Calculator application.");

        // main loop (do-while) iterates over each salesperson, processing their information until exit
        do {
            // Input salesperson's first name
            String firstName = getAlphabeticInput("Please enter the first name of the salesperson:");

            if (firstName == null) {
                choice = getStringInput("Do you want to process another salesperson? (Y/N):");
                while (!choice.equalsIgnoreCase("Y") && !choice.equalsIgnoreCase("N")) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter Y or N.");
                    choice = getStringInput("Do you want to process another salesperson? (Y/N):");
                }
                if (!choice.equalsIgnoreCase("Y")) {
                    break; // User pressed Cancel or chose not to proceed, exit the loop
                } else {
                    continue; // User wants to process another salesperson, restart the loop
                }
            }

            // Input salesperson's last name
            String lastName = getAlphabeticInput("Please enter the last name of the salesperson:");

            if (lastName == null) {
                choice = getStringInput(" Do you want to process another salesperson? (Y/N):");
                while (!choice.equalsIgnoreCase("Y") && !choice.equalsIgnoreCase("N")) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter Y or N.");
                    choice = getStringInput("Do you want to process another salesperson? (Y/N):");
                }
                if (!choice.equalsIgnoreCase("Y")) {
                    break;
                } else {
                    continue;
                }
            }


            double totalSalesCurrent = 0;//total sales amount for the current salesperson,

            // Input the number of items sold for each item type
            for (int i = 1; i <= 4; i++) {
                Integer number = getPositiveIntInput("Please enter the number of items sold for item type " + i + ":");

                // Check if the user pressed Cancel, if yes, prompt to process another salesperson
                if (number == null) {
                    choice = getStringInput("Do you want to process another salesperson? (Y/N):");
                    while (!choice.equalsIgnoreCase("Y") && !choice.equalsIgnoreCase("N")) {
                        JOptionPane.showMessageDialog(null, "Invalid input. Please enter Y or N.");
                        choice = getStringInput("Do you want to process another salesperson? (Y/N):");
                    }
                    break; // User pressed Cancel, break from the inner loop
                }

                // Update sales statistics
                totalItemsSold[i - 1] += number; // -1 from the index to correctly access the corresponding element in the array.
                totalSalesCurrent += number * getItemValue(i);

                // Update most sold item type
                if (totalItemsSold[i - 1] > mostSoldNumber) {
                    mostSoldType = i;
                    mostSoldNumber = totalItemsSold[i - 1];
                }
            }

            // Check if the user chose not to enter another salesperson
            if (choice.equalsIgnoreCase("N")) {
                break; // User chose not to enter another salesperson
            }

            // Calculate and display weekly earnings for the salesperson
            double weeklyEarnings = calculateWeeklyEarnings(totalSalesCurrent);

            JOptionPane.showMessageDialog(null,
                    String.format("Weekly earning for %s %s is $%.2f.", firstName, lastName, weeklyEarnings));

            // Update top salesperson information
            if (totalSalesCurrent >= topSalesAmount) {
                topSalesPerson = firstName + " " + lastName;
                topSalesAmount = totalSalesCurrent;
            }

            // Update total sales and processed salespeople count
            sumOfSales += totalSalesCurrent;
            count++;

            // Prompt to process another salesperson
            choice = getStringInput("Do you want to process another salesperson? (Y/N):");
            while (!choice.equalsIgnoreCase("Y") && !choice.equalsIgnoreCase("N")) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter Y or N.");
                choice = getStringInput("Do you want to process another salesperson? (Y/N):");
            }
        } while (choice.equalsIgnoreCase("Y")); // do-while loop.

        // Display summary information
        JOptionPane.showMessageDialog(null, "You have processed " + count + " salespeople.");

        if (count > 0) {  //if at least one salesperson has been processed.
            int totalNumberOfItemsSold = 0;
            for (int itemsSold : totalItemsSold) {  // element : array
                totalNumberOfItemsSold += itemsSold;
            }  //reference:Enhanced for loop: https://stackoverflow.com/questions/18410035/ways-to-iterate-over-a-list-in-java

            averageSale = totalNumberOfItemsSold > 0 ? sumOfSales / totalNumberOfItemsSold : 0;
            //?: This is the ternary operator, which is a shorthand way of writing an if-else statement.
            //If the condition (totalNumberOfItemsSold > 0) is true, this expression calculates the average sale by dividing sumOfSales by totalNumberOfItemsSold.
            //0: If the condition is false (totalNumberOfItemsSold is not greater than zero), the value assigned to averageSale is zero.
            //reference:https://stackoverflow.com/questions/798545/what-is-the-java-operator-called-and-what-does-it-do

            JOptionPane.showMessageDialog(null, "Sales Activity Report:\n" +
                    String.format("The sum of all sales is $%.2f.\n", sumOfSales) +
                    String.format("The average value of a sale is $%.2f.\n", averageSale) +
                    "The item type that is most sold is " + mostSoldType + ".\n" +
                    "The name of the top salesperson is " + topSalesPerson + ".\n" +
                    String.format("The sales amount of the top salesperson is $%.2f.\n", topSalesAmount));
        } else {
            JOptionPane.showMessageDialog(null, "No sales were processed.");
        }
    }

    // Method to get a string input from the user with validation
    private static String getStringInput(String message) {
        String input = JOptionPane.showInputDialog(message);
        while (input == null || input.trim().isEmpty()) { //.trim():  It returns a new string with leading and trailing whitespace removed
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid string.");
            input = JOptionPane.showInputDialog(message);
        }
        return input;
    }

    // Method to get alphabetic input from the user with validation
    private static String getAlphabeticInput(String message) {
        String input = JOptionPane.showInputDialog(message);
        while (input != null && !input.matches("^[a-zA-Z #-<>]+$")) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter alphabetic characters only.");
            input = JOptionPane.showInputDialog(message);
        }
        return input;
    }


    // Method to get a positive integer input from the user with validation
    private static Integer getPositiveIntInput(String message) {
        String input;
        do {
            input = JOptionPane.showInputDialog(message);
            if (input == null) {
                // Return null if the user presses the cancel button
                return null;
            }
            // Check if the user input matches the regular expression for digits
            if (!input.matches("\\d+")) {
                // Show an error message if the input is not valid
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a positive integer.");
            }
        } while (!input.matches("\\d+")); // Repeat the loop until the input is valid
        // Return the input converted to an integer
        return Integer.valueOf(input);
    }
    //reference:https://stackoverflow.com/questions/2841550/what-does-d-mean-in-a-regular-expression#:~:text=d%20is%20called%20a%20character%20class%20and%20will,So%20d%2B%20means%20match%201%20or%20more%20digits.

    // Method to get the value of an item based on its type
    private static double getItemValue(int itemType) {
        switch (itemType) {
            case 1:
                return 239.99;
            case 2:
                return 129.75;
            case 3:
                return 99.95;
            case 4:
                return 350.89;
            default:
                return 0;
        }
    }

    // Method to calculate weekly earnings based on total sales
    private static double calculateWeeklyEarnings(double totalSales) {
        double commission = 0.09 * totalSales;
        return commission + 200;
    }
}


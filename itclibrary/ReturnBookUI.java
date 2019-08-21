import java.util.Scanner;

// Initiate to fix "ReturnBookUI.java" file
public class ReturnBookUI {

    public static enum UIState { INITIALISED, READY, INSPECTING, COMPLETED }; //Create an enum "UIState" and define states as INITIALISED, READY, INSPECTING, and COMPLETED
    private UIState state;  // Create an instance of "UIState" enum and name it as "state"

    private ReturnBookControl returnBookControl; // Create an object of ReturnBookControl class to use the functionality of ReturnBookControl
    private Scanner input; // Create an object of Scanner to take an input from user

    // Create a constructor of ReturnBookUI class which takes ReturnBookControl object and sets basic setting for the class which are as follow
    // This method requires ReturnBookControl object to set the user interface of ReturnBookControl class by using their method "setUI"
    // It sets state as INITIALISED and initialize the scanner object
    public ReturnBookUI(ReturnBookControl returnBookControl) {
        this.returnBookControl = returnBookControl; // Referring returnBookControl object of this class to the returnBookControl object passes by the constructor
        input = new Scanner(System.in); // Initialize the scanner object
        state = UIState.INITIALISED;    // Assign state as "INITIALISED" state
        returnBookControl.setUI(this); // It sets the user interface of returnBookControl by passing the instance of this class
    }

    // Create run method which checks the UIState and work accordingly
    // If state is READY then ask bookId from user then pass it to ReturnBookControl class by their method "bookScanned"
    // If state is INSPECTING then ask user that book is damaged or not. Then pass the damaged status to returnBookControl by their method "dischargeLoan"
    // If state is INITIALISED then run again the whole process and if state is COMPLETED then show completion message
    public void run() {
        output("Return Book Use Case UI\n"); // Display the this text by using "output" method
        while (true) {  // Using true in while to make this loop infinite, unless it break from inside (use "return")
            switch (state) {    // Use switch block to use the code according to enum state

                // If state is in "INITIALISED" state then exit the switch block
                case INITIALISED:
                    break;          // Exit the switch block

                // If state is in "READY" state then
                case READY:
                    String bookIdString = input("Scan Book (<enter> completes): ");  // Take book id as an input form user and save it to bookIdString
                    if (bookIdString.length() == 0) { // if user inputs nothing then call scanningComplete method of ReturnBookControl
                        returnBookControl.scanningComplete();
                    }
                    else { // If user input is not empty
                        // Use try / catch to handle an error of NumberFormatException
                        try { // In try block the conversion from string to integer takes place
                            int bookId = Integer.valueOf(bookIdString).intValue(); // Convert string(bookIdString) to integer (bookId)
                            returnBookControl.bookScanned(bookId); // Pass bookId to the bookScanned method of ReturnBookControl class
                        }
                        catch (NumberFormatException e) { // If user inputs any other character than number then it shows an error message of following text
                            output("Invalid bookId");
                        }
                    }
                    break;          // Exit the switch block

                // If state is in "INSPECTING" state then ask question from user that the book is damaged or not.
                // Then pass isDamaged boolean to dischargeLoan method of ReturnBookControl class
                case INSPECTING:
                    String isDamagedAnswer = input("Is book damaged? (Y/N): "); // Takes an input from user and save it to isDamagedAnswer string
                    boolean isDamaged = false; // Initialize isDamaged boolean to false as basic
                    if (isDamagedAnswer.toUpperCase().equals("Y")) { // If user says book is damaged in the form of "Y" then assign "true" to isDamaged boolean
                        isDamaged = true; // Set "true" to isDamaged boolean
                    }
                    returnBookControl.dischargeLoan(isDamaged); // Pass isDamaged to ReturnBookControl class by using their method "dischargeLoan"

                // If state is in "COMPLETED" state then display a completion message
                case COMPLETED:
                    output("Return processing complete"); // Display a completion message of following text
                    return; // Exit the while loop

                // If state is not in all state above then show an error message
                default:
                    output("Unhandled state"); // Display error of following text
                    throw new RuntimeException("ReturnBookUI : unhandled state :" + state); // Throw an exception error of following text
            }
        }
    }
	
    // Create "input" method which displays the text and takes an input from user. Then return the input as a string
    // Its takes "prompt" string to display the prompt and takes input from the user.
	private String input(String prompt) {
		System.out.print(prompt);
		return input.nextLine();
	}	
		

    // Create "output" method which displays any object from Object class or inherit from object class (i.e. string, integer, float, array, etc)
    // This method requires any object from Object class or inherit from object class		
	private void output(Object object) {
		System.out.println(object);
	}
	
    // Create "display" method which uses output method of the same class
    // "output" method is private so it can not be used outside the class but this method is public			
	public void display(Object object) {
		output(object);
	}
	
    // Create "setState" method which sets the state of UIState.
    // It requires UIState to set the state of this class
	public void setState(UIState state) {
		this.StATe = state;
	}	
}

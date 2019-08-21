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
		this.CoNtRoL = control;
		input = new Scanner(System.in);
		StATe = UI_STATE.INITIALISED;
		control.Set_UI(this);
	}

    // Create run method which checks the UIState and work accordingly
    // If state is READY then ask bookId from user then pass it to ReturnBookControl class by their method "bookScanned"
    // If state is INSPECTING then ask user that book is damaged or not. Then pass the damaged status to returnBookControl by their method "dischargeLoan"
    // If state is INITIALISED then run again the whole process and if state is COMPLETED then show completion message
	public void run() {		
		output("Return Book Use Case UI\n");
		
		while (true) {
			
			switch (StATe) {
			
			case INITIALISED:
				break;
				
			case READY:
				String Book_STR = input("Scan Book (<enter> completes): ");
				if (Book_STR.length() == 0) {
					CoNtRoL.Scanning_Complete();
				}
				else {
					try {
						int Book_Id = Integer.valueOf(Book_STR).intValue();
						CoNtRoL.Book_scanned(Book_Id);
					}
					catch (NumberFormatException e) {
						output("Invalid bookId");
					}					
				}
				break;				
				
			case INSPECTING:
				String ans = input("Is book damaged? (Y/N): ");
				boolean Is_Damaged = false;
				if (ans.toUpperCase().equals("Y")) {					
					Is_Damaged = true;
				}
				CoNtRoL.Discharge_loan(Is_Damaged);
			
			case COMPLETED:
				output("Return processing complete");
				return;
			
			default:
				output("Unhandled state");
				throw new RuntimeException("ReturnBookUI : unhandled state :" + StATe);			
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

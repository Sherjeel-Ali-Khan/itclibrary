import java.util.Scanner;

// Initiate to fix "ReturnBookUI.java" file
public class ReturnBookUI {

    public static enum UIState { INITIALISED, READY, INSPECTING, COMPLETED }; //Create an enum "UIState" and define states as INITIALISED, READY, INSPECTING, and COMPLETED
    private UIState state;  // Create an instance of "UIState" enum and name it as "state"

    private ReturnBookControl returnBookControl; // Create an object of ReturnBookControl class to use the functionality of ReturnBookControl
    private Scanner input; // Create an object of Scanner to take an input from user
	
	public ReturnBookUI(ReturnBookControl returnBookControl) {
		this.CoNtRoL = control;
		input = new Scanner(System.in);
		StATe = UI_STATE.INITIALISED;
		control.Set_UI(this);
	}


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

	
	private String input(String prompt) {
		System.out.print(prompt);
		return input.nextLine();
	}	
		
		
	private void output(Object object) {
		System.out.println(object);
	}
	
			
	public void display(Object object) {
		output(object);
	}
	
	public void setState(UIState state) {
		this.StATe = state;
	}

	
}

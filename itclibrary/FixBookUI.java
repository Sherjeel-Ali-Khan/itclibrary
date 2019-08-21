import java.util.Scanner;


public class FixBookUI {

	public static enum UI_STATE { INITIALISED, READY, FIXING, COMPLETED };

	private FixBookControl control;// change the variable name as control
	private Scanner input;
	private UI_STATE state;// change the variable name as state

	
	public FixBookUI(FixBookControl control1) {//change parameter name control to control1 for easyli identify with variable name
		this.control = control1;// change according to parameter and variable name control1 and control
		input = new Scanner(System.in);
		state = UI_STATE.INITIALISED;// change according to variable name state
		control1.Set_Ui(this);// change according to parameter name control1
	}


	public void Set_State(UI_STATE state1) {//change parameter name state to state11 for easyli identify with variable name
		this.state = state1;// change according to parameter and variable name state1 and state
	}

	
	public void RuN() {
		output("Fix Book Use Case UI\n");
		
		while (true) {
			
			switch (state) {// change according to variable name state
			
			case READY:
				String Book_STR = input("Scan Book (<enter> completes): ");
				if (Book_STR.length() == 0) {
					control.SCannING_COMplete();// change according to variable name control
				}
				else {
					try {
						int Book_ID = Integer.valueOf(Book_STR).intValue();
						control.Book_scanned(Book_ID);// change according to variable name control
					}
					catch (NumberFormatException e) {
						output("Invalid bookId");
					}
				}
				break;	
				
			case FIXING:
				String AnS = input("Fix Book? (Y/N) : ");
				boolean FiX = false;
				if (AnS.toUpperCase().equals("Y")) {
					FiX = true;
				}
				control.FIX_Book(FiX);// change according to variable name control
				break;
								
			case COMPLETED:
				output("Fixing process complete");
				return;
			
			default:
				output("Unhandled state");
				throw new RuntimeException("FixBookUI : unhandled state :" + state);// change according to variable name state			
			
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
	
	
}

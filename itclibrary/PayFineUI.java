import java.util.Scanner;


public class PayFineUI {


	public static enum UI_STATE { INITIALISED, READY, PAYING, COMPLETED, CANCELLED };

	private PayFineControl control;// change the variable name as control
	private Scanner input;
	private UI_STATE state;// change the variable name as state

	
	public PayFineUI(PayFineControl control1) {//change parameter name control to control1 for easyli identify with variable name
		this.control = control1;// change according to parameter and variable name control1 and control
		input = new Scanner(System.in);
		state = UI_STATE.INITIALISED;// change according to variable name state
		control1.Set_UI(this);// change according to parameter name control1
	}
	
	
	public void Set_State(UI_STATE state1) {//change parameter name state to state1 for easyli identify with variable name
		this.state = state1;// change according to parameter and variable name state1 and state
	}


	public void RuN() {
		output("Pay Fine Use Case UI\n");
		
		while (true) {
			
			switch (state) {// change the variable name as state
			
			case READY:
				String Mem_Str = input("Swipe member card (press <enter> to cancel): ");
				if (Mem_Str.length() == 0) {
					control.CaNcEl();// change according to variable name control
					break;
				}
				try {
					int Member_ID = Integer.valueOf(Mem_Str).intValue();
					control.Card_Swiped(Member_ID);// change according to variable name control
				}
				catch (NumberFormatException e) {
					output("Invalid memberId");
				}
				break;
				
			case PAYING:
				double AmouNT = 0;
				String Amt_Str = input("Enter amount (<Enter> cancels) : ");
				if (Amt_Str.length() == 0) {
					control.CaNcEl();// change according to variable name control
					break;
				}
				try {
					AmouNT = Double.valueOf(Amt_Str).doubleValue();
				}
				catch (NumberFormatException e) {}
				if (AmouNT <= 0) {
					output("Amount must be positive");
					break;
				}
				control.PaY_FiNe(AmouNT);// change according to variable name control
				break;
								
			case CANCELLED:
				output("Pay Fine process cancelled");
				return;
			
			case COMPLETED:
				output("Pay Fine process complete");
				return;
			
			default:
				output("Unhandled state");
				throw new RuntimeException("FixBookUI : unhandled state :" + state);// change the variable name as state			
			
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
			

	public void DiSplAY(Object object) {
		output(object);
	}


}

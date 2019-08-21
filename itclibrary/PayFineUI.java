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
		control1.setUi(this);// change according to parameter name control1 and method name setUi
	}
	
	//change the mathod name as setState
	public void setState(UI_STATE state1) {//change parameter name state to state1 for easyli identify with variable name
		this.state = state1;// change according to parameter and variable name state1 and state
	}


	public void isRun() {// change method name as isRun
		setOutput("Pay Fine Use Case UI\n");// change using method name as setOutput
		
		while (true) {
			
			switch (state) {// change the variable name as state
			
			case READY:
				String Mem_Str = setInput("Swipe member card (press <enter> to cancel): ");// change using method name as setInput
				if (Mem_Str.length() == 0) {
					control.isCancel();// change according to variable name control and method name isCancel
					break;
				}
				try {
					int Member_ID = Integer.valueOf(Mem_Str).intValue();
					control.cardSwiped(Member_ID);// change according to variable name control and method name cardSwiped
				}
				catch (NumberFormatException e) {
					setOutput("Invalid memberId");// change using method name as setOutput
				}
				break;
				
			case PAYING:
				double AmouNT = 0;
				String Amt_Str = setInput("Enter amount (<Enter> cancels) : ");// change using method name as setInput
				if (Amt_Str.length() == 0) {
					control.isCancel();// change according to variable name control and method name isCancel
					break;
				}
				try {
					AmouNT = Double.valueOf(Amt_Str).doubleValue();
				}
				catch (NumberFormatException e) {}
				if (AmouNT <= 0) {
					setOutput("Amount must be positive");// change using method name as setOutput
					break;
				}
				control.payFine(AmouNT);// change according to variable name control and method name payFine
				break;
								
			case CANCELLED:
				setOutput("Pay Fine process cancelled");// change using method name as setOutput
				return;
			
			case COMPLETED:
				setOutput("Pay Fine process complete");// change using method name as setOutput
				return;
			
			default:
				setOutput("Unhandled state");// change using method name as setOutput
				throw new RuntimeException("FixBookUI : unhandled state :" + state);// change the variable name as state			
			
			}		
		}		
	}

	
	private String setInput(String prompt) {// change method name as setInput
		System.out.print(prompt);
		return input.nextLine();
	}	
		
		
	private void setOutput(Object object) {// change method name as setOutput
		System.out.println(object);
	}	
			

	public void isDisplay(Object object) {// change method name as display
		setOutput(object);// change using method name as setOutput
	}


}

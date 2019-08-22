import java.util.Scanner;

public class PayFineUI {
    
	public static enum UiState { INITIALISED, READY, PAYING, COMPLETED, CANCELLED };// change enum name as UiState

	private PayFineControl control;// change the variable name as control
	private Scanner input;
	private UiState state;// change the variable name as state, enum name as UiState

	
	public PayFineUI(PayFineControl control1) {//change parameter name control to control1 for easyli identify with variable name
		this.control = control1;// change according to parameter and variable name control1 and control
		input = new Scanner(System.in);
		state = UiState.INITIALISED;// change according to variable name state , enum name as UiState
		control1.setUi(this);// change according to parameter name control1 and method name setUi
	}
	
	//change the mathod name as setState, enum name as UiState
	public void setState(UiState state1) {//change parameter name state to state1 for easyli identify with variable name
		this.state = state1;// change according to parameter and variable name state1 and state
	}


	public void isRun() {// change method name as isRun
		setOutput("Pay Fine Use Case UI\n");// change using method name as setOutput
		
		while (true) {
			
			switch (state) {// change the variable name as state
			
				case READY:
					String memberString = setInput("Swipe member card (press <enter> to cancel): ");// change using method name as setInput, variable name memberString
					if (memberString.length() == 0) {//change variable name as memberString
						control.setCancel();// change according to variable name control and method name setCancel
						break;
					}
					try {
						int memberId = Integer.valueOf(memberString).intValue();//change variable name as memberString, memberId
						control.cardSwiped(memberId);// change according to variable name control, memberId and method name cardSwiped
					} catch (NumberFormatException e) {
						setOutput("Invalid memberId");// change using method name as setOutput
					}
					break;
					
				case PAYING:
					double amount = 0;// change variable name as amount
					String amountString = setInput("Enter amount (<Enter> cancels) : ");// change using method name as setInput, variable name as amountString
					if (amountString.length() == 0) {// change variable name as amountString
						control.setCancel();// change according to variable name control and method name setCancel
						break;
					}
					try {
						amount = Double.valueOf(amountString).doubleValue();// change variable name as amount,amountString
					} catch (NumberFormatException e) {}
					if (amount <= 0) {// change variable name as amount
						setOutput("Amount must be positive");// change using method name as setOutput
						break;
					}
					control.payFine(amount);// change according to variable name control, amount and method name payFine
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

import java.util.Scanner;

public class FixBookUI {
    public static enum UiState { INITIALISED, READY, FIXING, COMPLETED }; //change enum name as UiState
    private FixBookControl control;// change the variable name as control
    private Scanner input;
    private UiState state;// change the variable name as state, enum name as UiState

    public FixBookUI(FixBookControl control1) {//change parameter name control to control1 for easyli identify with variable name
        this.control = control1;// change according to parameter and variable name control1 and control
		input = new Scanner(System.in);
		state = UiState.INITIALISED;// change according to variable name state ,enum name as UiState
		control1.setUi(this);// change according to parameter name control1 , method name setUi
    }
    //change method name as setState
    public void setState(UiState state1) {//change parameter name state to state11 for easyli identify with variable name ,enum name as UiState
		this.state = state1;// change according to parameter and variable name state1 and state
    }

    public void isRun(){//change method name as isRun
		setOutput("Fix Book Use Case UI\n");// change according to method name setOutput
		
		while (true) {	
            switch (state) {// change according to variable name state
			
				case READY:
                    String Book_STR = setInput("Scan Book (<enter> completes): ");// change according to method name setInput
                    if (Book_STR.length() == 0) {
						control.scanningComplete();// change according to variable name control, and method name scanningComplete
                    } else {
                        try {
                            int Book_ID = Integer.valueOf(Book_STR).intValue();
                            control.bookScanned(Book_ID);// change according to variable name control, and method name bookScanned
						} catch (NumberFormatException e) {
                            setOutput("Invalid bookId");// change according to method name setOutput
						}
                    }
                    break;		
				case FIXING:
                    String AnS = setInput("Fix Book? (Y/N) : ");// change according to method name setInput
                    boolean FiX = false;
                    if (AnS.toUpperCase().equals("Y")) {
						FiX = true;
                    }
                    control.fixBook(FiX);// change according to variable name control, method name fixBook
                    break;
								
				case COMPLETED:
                    setOutput("Fixing process complete");// change according to method name setOutput
                    return;
			
				default:
                    setOutput("Unhandled state");// change according to method name setOutput
                    throw new RuntimeException("FixBookUI : unhandled state :" + state);// change according to variable name state			
			
            }		
		}
		
    }

    private String setInput(String prompt) {//change method name as setInput
		System.out.print(prompt);
		return input.nextLine();
    }	
			
    private void setOutput(Object object) {//change method name as setOutput
		System.out.println(object);
    }

    public void isDisplay(Object object) {//change method name as isDisplay
		setOutput(object); // change according to method name setOutput
    }
    
}

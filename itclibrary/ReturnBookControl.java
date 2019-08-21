// Initiate to fix "ReturnBookControl.java" file
public class ReturnBookControl {


    private ReturnBookUI returnBookUI; // Create an object of ReturnBookUI class to use the functionality of ReturnBookUI
    private enum ControlState { INITIALISED, READY, INSPECTING }; //Create an enum "ControlState" and initialize to INITIALISED, READY, and INSPECTING
    private ControlState controlState; // Create an object of ControlState class to use the functionality of ControlState

    private Library library;  // Create an object of Library class to use the functionality of Library
    private Loan currentLoan; // Create an object of Loan class to use the functionality of Loan

    // Create a constructor of ReturnBookControl
    // Referring library object to the singleton of Library class
    // Assign the state to controlState as "INITIALISED"
    public ReturnBookControl() {
        this.library = Library.getInstance();   // Referring library object of ReturnBookControl to the singleton of Library class
        controlState = ControlState.INITIALISED;// Assign controlState as "INITIALISED" state
    }
	

    // Create setUI method which sets user interface to the settings which is passing to the method
    // This method requires ReturnBookUI object to set user interface
    public void setUI(ReturnBookUI returnBookUI) {
        // If controlState is not in the state of "INITIALISED" then throw an exception error of following text
        if (!controlState.equals(ControlState.INITIALISED)) { // If controlState is not in the state of "INITIALISED" then follows the code in IF block
            throw new RuntimeException("ReturnBookControl: cannot call setUI except in INITIALISED state"); // Throw an exception error of following text
        }
        this.returnBookUI = returnBookUI; // Referring library object of ReturnBookControl to the ReturnBookControl provided from the method
        returnBookUI.setState(ReturnBookUI.UI_STATE.READY);  // Set the state of returnBookUI object to "READY" by calling their method "setState" and passing the static enum of ReturnBookUI
        controlState = ControlState.READY; // Set the state of controlState to "READY" state
    }


    // Create bookScanned method which finds book in Library by using bookId. If the book is found, then display book, current loan and over dues.
    // Otherwise shows errors
    // This method requires bookId integer to find valid book
	public void bookScanned(int Book_ID) {
		if (!sTaTe.equals(CONTROL_STATE.READY)) {
			throw new RuntimeException("ReturnBookControl: cannot call bookScanned except in READY state");
		}	
		book CUR_book = lIbRaRy.Book(Book_ID);
		
		if (CUR_book == null) {
			Ui.display("Invalid Book Id");
			return;
		}
		if (!CUR_book.On_loan()) {
			Ui.display("Book has not been borrowed");
			return;
		}		
		CurrENT_loan = lIbRaRy.LOAN_BY_BOOK_ID(Book_ID);	
		double Over_Due_Fine = 0.0;
		if (CurrENT_loan.OVer_Due()) {
			Over_Due_Fine = lIbRaRy.CalculateOverDueFine(CurrENT_loan);
		}
		Ui.display("Inspecting");
		Ui.display(CUR_book.toString());
		Ui.display(CurrENT_loan.toString());
		
		if (CurrENT_loan.OVer_Due()) {
			Ui.display(String.format("\nOverdue fine : $%.2f", Over_Due_Fine));
		}
		Ui.Set_State(ReturnBookUI.UI_STATE.INSPECTING);
		sTaTe = CONTROL_STATE.INSPECTING;		
	}


    // Create scanningComplete method which changes the state of returnBookUI to "COMPLETED"
	public void scanningComplete() {
		if (!sTaTe.equals(CONTROL_STATE.READY)) {
			throw new RuntimeException("ReturnBookControl: cannot call scanningComplete except in READY state");
		}	
		Ui.Set_State(ReturnBookUI.UI_STATE.COMPLETED);		
	}


    // Create dischargeLoan method which discharge the loan according to the "isDamaged" boolean which is passing to this method.
    // This method requires isDamaged boolean to discharge loan functionality of library class.
	public void dischargeLoan(boolean isDamaged) {
		if (!sTaTe.equals(CONTROL_STATE.INSPECTING)) {
			throw new RuntimeException("ReturnBookControl: cannot call dischargeLoan except in INSPECTING state");
		}	
		lIbRaRy.Discharge_loan(CurrENT_loan, isDamaged);
		CurrENT_loan = null;
		Ui.Set_State(ReturnBookUI.UI_STATE.READY);
		sTaTe = CONTROL_STATE.READY;				
	}


}

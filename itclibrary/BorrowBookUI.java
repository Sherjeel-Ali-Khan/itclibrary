import java.util.Scanner;


public class BorrowBookUI { // Class name changed ( To: BorrowBookUI ; Orig: BorrowBookUI;)

	public static enum UiState { INITIALISED, READY, RESTRICTED, SCANNING, IDENTIFIED, FINALISING, COMPLETED, CANCELLED };  //Change enum name ( To: enum UiState; Orig: enum UI_STATE;)

	private BorrowBookControl browBkCntrl;  // Change variable name ( To: browBkCntrl; Orig: CONTROL;)
	private Scanner input;
	private UiState uiState; //Change enu name and variable name ( To: UiState uiState; Orig: UI_STATE StaTe;)


	public BorrowBookUI(BorrowBookControl control) { // Default constructor name changed ( To: BorrowBookUI ; Orig: BorrowBookUI;)
		this.browBkCntrl = control; // Change variable name ( To: browBkCntrl; Orig: CONTROL;)
		input = new Scanner(System.in);
		uiState = UiState.INITIALISED; //Change enu name and variable name ( To: uiState = UiState.INITIALISED; Orig: StaTe = UI_STATE.INITIALISED;)
		control.setUi(this); //change method name (To: setUi Orig: setUI)
	}


	private String input(String prompt) {
		System.out.print(prompt);
		return input.nextLine();
	}


	private void output(Object object) {
		System.out.println(object);
	}


	public void setState(UiState state) { // Change parameter name ( To: state;  Orig: STATE;) Change method name (To:setState; Orig:Set_State;)
		this.uiState = state; // Change local variable name ( To: this.uiState = state;  Orig: UI_STATE = STATE; )
	}


	public void run() {
		output("Borrow Book Use Case UI\n");

		while (true) {

			switch (uiState) {

			case CANCELLED:
				output("Borrowing Cancelled");
				return;


			case READY:
				String MEM_STR = input("Swipe member card (press <enter> to cancel): ");
				if (MEM_STR.length() == 0) {
					browBkCntrl.cancel(); // Change variable name ( To: browBkCntrl; Orig: CONTROL;)
					break;
				}
				try {
					int memberId = Integer.valueOf(MEM_STR).intValue(); // Change variable name ( To: memberId; Orig: Member_ID;)
					browBkCntrl.swiped(memberId); // Change variable name ( To: browBkCntrl; Orig: CONTROL;) ( To: memberId; Orig: Member_ID;)
				}
				catch (NumberFormatException e) {
					output("Invalid Member Id");
				}
				break;


			case RESTRICTED:
				input("Press <any key> to cancel");
				browBkCntrl.cancel(); // Change variable name ( To: browBkCntrl; Orig: CONTROL;)
				break;


			case SCANNING:
				String Book_Str = input("Scan Book (<enter> completes): ");
				if (Book_Str.length() == 0) {
					browBkCntrl.complete(); // Change variable name ( To: browBkCntrl; Orig: CONTROL;)
					break;
				}
				try {
					int bid = Integer.valueOf(Book_Str).intValue(); // Change variable name ( To: bid; Orig: BiD;)
					browBkCntrl.scanned(bid); // Change variable name ( To: browBkCntrl; Orig: CONTROL;)

				} catch (NumberFormatException e) {
					output("Invalid Book Id");
				}
				break;


			case FINALISING:
				String userAnswer = input("Commit loans? (Y/N): "); // Change variable name ( To: userAnswer; Orig: Ans;)
				if (userAnswer.toUpperCase().equals("N")) {
					browBkCntrl.cancel(); // Change variable name ( To: browBkCntrl; Orig: CONTROL;)

				} else {
					browBkCntrl.commitLoans(); // Change variable name ( To: browBkCntrl; Orig: CONTROL;)
					input("Press <any key> to complete ");
				}
				break;


			case COMPLETED:
				output("Borrowing Completed");
				return;


			default:
				output("Unhandled state");
				throw new RuntimeException("BorrowBookUI : unhandled state :" + uiState);
			}
		}
	}


	public void setDisplay(Object object) {  //change method name (To: setDisplay Orig: Display)
		output(object);
	}


}

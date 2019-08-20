import java.util.Scanner;


public class BorrowBookUI {

	public static enum UiState { INITIALISED, READY, RESTRICTED, SCANNING, IDENTIFIED, FINALISING, COMPLETED, CANCELLED };  //Change enum name ( To: enum UiState; Orig: enum UI_STATE;)

	private BorrowBookControl browBkCntrl;  // Change variable name ( To: browBkCntrl; Orig: CONTROL;)
	private Scanner input;
	private UiState uiState; //Change enu name and variable name ( To: UiState uiState; Orig: UI_STATE StaTe;)


	public BorrowBookUI(BorrowBookControl control) {
		this.browBkCntrl = control; // Change variable name ( To: browBkCntrl; Orig: CONTROL;)
		input = new Scanner(System.in);
		uiState = UiState.INITIALISED; //Change enu name and variable name ( To: uiState = UiState.INITIALISED; Orig: StaTe = UI_STATE.INITIALISED;)
		control.setUI(this);
	}


	private String input(String prompt) {
		System.out.print(prompt);
		return input.nextLine();
	}


	private void output(Object object) {
		System.out.println(object);
	}


	public void Set_State(uiState state) { // Change parameter name ( To: state;  Orig: STATE;)
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
					int Member_ID = Integer.valueOf(MEM_STR).intValue();
					browBkCntrl.Swiped(Member_ID); // Change variable name ( To: browBkCntrl; Orig: CONTROL;)
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
					browBkCntrl.Complete(); // Change variable name ( To: browBkCntrl; Orig: CONTROL;)
					break;
				}
				try {
					int BiD = Integer.valueOf(Book_Str).intValue();
					browBkCntrl.Scanned(BiD); // Change variable name ( To: browBkCntrl; Orig: CONTROL;)

				} catch (NumberFormatException e) {
					output("Invalid Book Id");
				}
				break;


			case FINALISING:
				String Ans = input("Commit loans? (Y/N): ");
				if (Ans.toUpperCase().equals("N")) {
					browBkCntrl.cancel(); // Change variable name ( To: browBkCntrl; Orig: CONTROL;)

				} else {
					browBkCntrl.Commit_LOans(); // Change variable name ( To: browBkCntrl; Orig: CONTROL;)
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


	public void Display(Object object) {
		output(object);
	}


}

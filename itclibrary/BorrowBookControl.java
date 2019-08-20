import java.util.ArrayList;
import java.util.List;

public class BorrowBookControl {

	private BorrowBookUi borrowBookUi;  //Change class name and variable name (Orig: BorrowBookUI UI)

	private Library library; //Change class name and variable name (Orig: library LIBRARY)
	private member M;
	private enum CONTROL_STATE { INITIALISED, READY, RESTRICTED, SCANNING, IDENTIFIED, FINALISING, COMPLETED, CANCELLED };
	private CONTROL_STATE State;

	private List<book> PENDING;
	private List<loan> COMPLETED;
	private book BOOK;


	public BorrowBookControl() {
		this.library = library.INSTANCE(); // Change variable name to library (Orig: LIBRARY)
		State = CONTROL_STATE.INITIALISED;
	}


	public void setUI(BorrowBookUi ui) { // Change class name to BorrowBookUi (Orig: BorrowBookUI)
		if (!State.equals(CONTROL_STATE.INITIALISED))
			throw new RuntimeException("BorrowBookControl: cannot call setUI except in INITIALISED state");

		this.borrowBookUi = ui; // Change variable name to borrowBookUi (Orig: UI)
		ui.Set_State(BorrowBookUi.UI_STATE.READY); // Change class name to BorrowBookUi (Orig: BorrowBookUI) // Change class name to BorrowBookUi (Orig: BorrowBookUI)
		State = CONTROL_STATE.READY;
	}


	public void Swiped(int MEMMER_ID) {
		if (!State.equals(CONTROL_STATE.READY))
			throw new RuntimeException("BorrowBookControl: cannot call cardSwiped except in READY state");

		M = library.MEMBER(MEMMER_ID); // Change variable name to library (Orig: LIBRARY)
		if (M == null) {
			borrowBookUi.Display("Invalid memberId");  // Change variable name to borrowBookUi (Orig: UI)
			return;
		}
		if (library.MEMBER_CAN_BORROW(M)) { // Change variable name to library (Orig: LIBRARY)
			PENDING = new ArrayList<>();
			borrowBookUi.Set_State(BorrowBookUi.UI_STATE.SCANNING);  // Change variable name to borrowBookUi (Orig: UI) // Change class name to BorrowBookUi (Orig: BorrowBookUI)
			State = CONTROL_STATE.SCANNING; }
		else
		{
			borrowBookUi.Display("Member cannot borrow at this time");  // Change variable name to borrowBookUi (Orig: UI)
			borrowBookUi.Set_State(BorrowBookUi.UI_STATE.RESTRICTED); }} // Change variable name to borrowBookUi (Orig: UI) // Change class name to BorrowBookUi (Orig: BorrowBookUI)


	public void Scanned(int bookId) {
		BOOK = null;
		if (!State.equals(CONTROL_STATE.SCANNING)) {
			throw new RuntimeException("BorrowBookControl: cannot call bookScanned except in SCANNING state");
		}
		BOOK = library.Book(bookId); // Change variable name to library (Orig: LIBRARY)
		if (BOOK == null) {
			borrowBookUi.Display("Invalid bookId");  // Change variable name to borrowBookUi (Orig: UI)
			return;
		}
		if (!BOOK.AVAILABLE()) {
			borrowBookUi.Display("Book cannot be borrowed");  // Change variable name to borrowBookUi (Orig: UI)
			return;
		}
		PENDING.add(BOOK);
		for (book B : PENDING) {
			borrowBookUi.Display(B.toString());  // Change variable name to borrowBookUi (Orig: UI)
		}
		if (library.Loans_Remaining_For_Member(M) - PENDING.size() == 0) { // Change variable name to library (Orig: LIBRARY)
			borrowBookUi.Display("Loan limit reached");  // Change variable name to borrowBookUi (Orig: UI)
			Complete();
		}
	}


	public void Complete() {
		if (PENDING.size() == 0) {
			cancel();
		}
		else {
			borrowBookUi.Display("\nFinal Borrowing List");   // Change variable name to borrowBookUi (Orig: UI)
			for (book B : PENDING) {
				borrowBookUi.Display(B.toString());  // Change variable name to borrowBookUi (Orig: UI)
			}
			COMPLETED = new ArrayList<loan>();
			borrowBookUi.Set_State(BorrowBookUi.UI_STATE.FINALISING);  // Change variable name to borrowBookUi (Orig: UI) // Change class name to BorrowBookUi (Orig: BorrowBookUI)
			State = CONTROL_STATE.FINALISING;
		}
	}


	public void Commit_LOans() {
		if (!State.equals(CONTROL_STATE.FINALISING)) {
			throw new RuntimeException("BorrowBookControl: cannot call commitLoans except in FINALISING state");
		}
		for (book B : PENDING) {
			loan LOAN = library.ISSUE_LAON(B, M); // Change variable name to library (Orig: LIBRARY)
			COMPLETED.add(LOAN);
		}
		borrowBookUi.Display("Completed Loan Slip");  // Change variable name to borrowBookUi (Orig: UI)
		for (loan LOAN : COMPLETED) {
			borrowBookUi.Display(LOAN.toString());  // Change variable name to borrowBookUi (Orig: UI)
		}
		borrowBookUi.Set_State(BorrowBookUi.UI_STATE.COMPLETED);  // Change variable name to borrowBookUi (Orig: UI) // Change class name to BorrowBookUi (Orig: BorrowBookUI)
		State = CONTROL_STATE.COMPLETED;
	}


	public void cancel() {
		UI.Set_State(BorrowBookUi.UI_STATE.CANCELLED);  // Change variable name to borrowBookUi (Orig: UI) // Change class name to BorrowBookUi (Orig: BorrowBookUI)
		State = CONTROL_STATE.CANCELLED;
	}


}

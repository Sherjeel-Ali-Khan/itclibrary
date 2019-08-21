import java.util.ArrayList;
import java.util.List;

public class BorrowBookControl {

	private BorrowBookUi borrowBookUi; //Change class name and variable name (Orig: BorrowBookUI UI)

	private Library library; //Change class name and variable name (Orig: library LIBRARY)
	private Member member; //Change class name and variable name (Orig: member M)
	private enum ControlState { INITIALISED, READY, RESTRICTED, SCANNING, IDENTIFIED, FINALISING, COMPLETED, CANCELLED }; //Change enum name (Orig: enum CONTROL_STATE)
	private ControlState ctrlState; //Change enum name  and variable name  (Orig: enum CONTROL_STATE)  (To:ctrlState; Orig:State;)

	private List<book> pendingBookList; // Change variable name ( To: pendingBookList; Orig: PENDING;)
	private List<loan> completedBookList; // Change variable name ( To: completedBookList; Orig: COMPLETED;)
	private Book book; //Change class name and variable name (Orig:  book BOOK)


	public BorrowBookControl() {
		this.library = library.INSTANCE(); // Change variable name to library (Orig: LIBRARY)
		ctrlState = ControlState.INITIALISED; //Changed enum name (Orig: enum CONTROL_STATE)  (To:ctrlState; Orig:State;)
	}


	public void setUI(BorrowBookUi ui) { // Change class name to BorrowBookUi (Orig: BorrowBookUI)
		if (!ctrlState.equals(ControlState.INITIALISED)) //Changed enum name (Orig: enum CONTROL_STATE)  (To:ctrlState; Orig:State;)
			throw new RuntimeException("BorrowBookControl: cannot call setUI except in INITIALISED state");

		this.borrowBookUi = ui; // Change variable name to borrowBookUi (Orig: UI)
		ui.Set_State(BorrowBookUi.UI_STATE.READY); // Change class name to BorrowBookUi (Orig: BorrowBookUI) // Change class name to BorrowBookUi (Orig: BorrowBookUI)
		ctrlState = ControlState.READY; //Changed enum name (Orig: enum CONTROL_STATE) (To:ctrlState; Orig:State;)
	}


	public void Swiped(int MEMMER_ID) {
		if (!ctrlState.equals(ControlState.READY)) //Change enum name (Orig: enum CONTROL_STATE) (To:ctrlState; Orig:State;)
			throw new RuntimeException("BorrowBookControl: cannot call cardSwiped except in READY state");

		member = library.MEMBER(MEMMER_ID); // Change variable name ( To: library;  Orig: LIBRARY; , To: member;  Orig: M;)
		if (member == null) {  // Change variable name (To: member;  Orig: M;)
			borrowBookUi.Display("Invalid memberId");  // Change variable name to borrowBookUi (Orig: UI)
			return;
		}
		if (library.MEMBER_CAN_BORROW(member)) { // Change variable name( To: library;  Orig: LIBRARY; , To: member;  Orig: M;)
			pendingBookList = new ArrayList<>(); // Change variable name ( To: pendingBookList; Orig: PENDING;)
			borrowBookUi.Set_State(BorrowBookUi.UI_STATE.SCANNING);  // Change variable name to borrowBookUi (Orig: UI) // Change class name to BorrowBookUi (Orig: BorrowBookUI)
			ctrlState = ControlState.SCANNING; } //Change enum name (Orig: enum CONTROL_STATE) (To:ctrlState; Orig:State;)
		else
		{
			borrowBookUi.Display("Member cannot borrow at this time");  // Change variable name to borrowBookUi (Orig: UI)
			borrowBookUi.Set_State(BorrowBookUi.UI_STATE.RESTRICTED); }} // Change variable name to borrowBookUi (Orig: UI) // Change class name to BorrowBookUi (Orig: BorrowBookUI)


	public void Scanned(int bookId) {
		book = null; // Change variable name ( To: book; Orig: BOOK;)
		if (!ctrlState.equals(ControlState.SCANNING)) { //Change enum name (Orig: enum CONTROL_STATE) (To:ctrlState; Orig:State;)
			throw new RuntimeException("BorrowBookControl: cannot call bookScanned except in SCANNING state");
		}
		book = library.Book(bookId); // Change variable name to library (Orig: LIBRARY) // Change variable name ( To: book; Orig: BOOK;)
		if (book == null) { // Change variable name ( To: book; Orig: BOOK;)
			borrowBookUi.Display("Invalid bookId");  // Change variable name to borrowBookUi (Orig: UI)
			return;
		}
		if (!book.AVAILABLE()) { // Change variable name ( To: book; Orig: BOOK;)
			borrowBookUi.Display("Book cannot be borrowed");  // Change variable name to borrowBookUi (Orig: UI)
			return;
		}
		pendingBookList.add(book); // Change variable name ( To: pendingBookList; Orig: PENDING; , To: book; Orig: BOOK;)
		for (book B : pendingBookList) { // Change variable name ( To: pendingBookList; Orig: PENDING; , To: book; Orig: BOOK;)
			borrowBookUi.Display(B.toString());  // Change variable name to borrowBookUi (Orig: UI)
		}
		if (library.Loans_Remaining_For_Member(member) - pendingBookList.size() == 0) { // Change variable name( To: library;  Orig: LIBRARY; , To: member;  Orig: M; ,  To: pendingBookList; Orig: PENDING;)
			borrowBookUi.Display("Loan limit reached");  // Change variable name to borrowBookUi (Orig: UI)
			Complete();
		}
	}


	public void Complete() {
		if (pendingBookList.size() == 0) { // Change variable name ( To: pendingBookList; Orig: PENDING;)
			cancel();
		}
		else {
			borrowBookUi.Display("\nFinal Borrowing List");   // Change variable name to borrowBookUi (Orig: UI)
			for (book B : pendingBookList) { // Change variable name ( To: pendingBookList; Orig: PENDING; , To: book; Orig: BOOK;)
				borrowBookUi.Display(B.toString());  // Change variable name to borrowBookUi (Orig: UI)
			}
			completedBookList = new ArrayList<loan>(); // Change variable name ( To: completedBookList; Orig: COMPLETED;)
			borrowBookUi.Set_State(BorrowBookUi.UI_STATE.FINALISING);  // Change variable name to borrowBookUi (Orig: UI) // Change class name to BorrowBookUi (Orig: BorrowBookUI)
			ctrlState = ControlState.FINALISING; //Change enum name (Orig: enum CONTROL_STATE) (To:ctrlState; Orig:State;)
		}
	}


	public void Commit_LOans() {
		if (!ctrlState.equals(ControlState.FINALISING)) { //Change enum name (Orig: enum CONTROL_STATE) (To:ctrlState; Orig:State;)
			throw new RuntimeException("BorrowBookControl: cannot call commitLoans except in FINALISING state");
		}
		for (book B : pendingBookList) { // Change variable name ( To: pendingBookList; Orig: PENDING;,  , To: book; Orig: BOOK;)
			loan LOAN = library.ISSUE_LAON(B, member); // Change variable name( To: library;  Orig: LIBRARY; , To: member;  Orig: M;)
			completedBookList.add(LOAN); // Change variable name ( To: completedBookList; Orig: COMPLETED;)
		}
		borrowBookUi.Display("Completed Loan Slip");  // Change variable name to borrowBookUi (Orig: UI)
		for (loan LOAN : completedBookList) { // Change variable name ( To: completedBookList; Orig: COMPLETED;)
			borrowBookUi.Display(LOAN.toString());  // Change variable name to borrowBookUi (Orig: UI)
		}
		borrowBookUi.Set_State(BorrowBookUi.UI_STATE.COMPLETED);  // Change variable name to borrowBookUi (Orig: UI) // Change class name to BorrowBookUi (Orig: BorrowBookUI)
		ctrlState = ControlState.COMPLETED; //Change enum name (Orig: enum CONTROL_STATE) (To:ctrlState; Orig:State;)
	}


	public void cancel() {
		UI.Set_State(BorrowBookUi.UI_STATE.CANCELLED);  // Change variable name to borrowBookUi (Orig: UI) // Change class name to BorrowBookUi (Orig: BorrowBookUI)
		ctrlState = ControlState.CANCELLED; //Change enum name (Orig: enum CONTROL_STATE) (To:ctrlState; Orig:State;) 
	}


}

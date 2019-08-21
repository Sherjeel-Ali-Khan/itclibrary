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


	public BorrowBookControl() { // default constructor
		this.library = library.getInstance(); // Change variable name to library (Orig: LIBRARY) Change method name (To:getInstance; Orig:INSTANCE;)
		ctrlState = ControlState.INITIALISED; //Changed enum name (Orig: enum CONTROL_STATE)  (To:ctrlState; Orig:State;)
	}


	public void setUI(BorrowBookUi ui) { // Change class name to BorrowBookUi (Orig: BorrowBookUI)
		if (!ctrlState.equals(ControlState.INITIALISED)) //Changed enum name (Orig: enum CONTROL_STATE)  (To:ctrlState; Orig:State;)
			throw new RuntimeException("BorrowBookControl: cannot call setUI except in INITIALISED state");

		this.borrowBookUi = ui; // Change variable name to borrowBookUi (Orig: UI)
		ui.Set_State(BorrowBookUi.UI_STATE.READY); // Change class name to BorrowBookUi (Orig: BorrowBookUI) // Change class name to BorrowBookUi (Orig: BorrowBookUI)
		ctrlState = ControlState.READY; //Changed enum name (Orig: enum CONTROL_STATE) (To:ctrlState; Orig:State;)
	}


	public void swiped(int memberId) { // change parameter name and method name ( To: memberId; Orig: MEMMER_ID; , To: swiped; Orig: Swiped;)
		if (!ctrlState.equals(ControlState.READY)) //Change enum name (Orig: enum CONTROL_STATE) (To:ctrlState; Orig:State;)
			throw new RuntimeException("BorrowBookControl: cannot call cardSwiped except in READY state");

		member = library.getMember(memberId); // Change variable name ( To: library;  Orig: LIBRARY; , To: member;  Orig: M;) Change method name (To:getMember; Orig:MEMBER;)
		if (member == null) {  // Change variable name (To: member;  Orig: M;)
			borrowBookUi.Display("Invalid memberId");  // Change variable name to borrowBookUi (Orig: UI)
			return;
		}
		if (library.isMemberCanBorrow(member)) { // Change variable name( To: library;  Orig: LIBRARY; , To: member;  Orig: M;) Change method name (To:getMember; Orig:MEMBER_CAN_BORROW;)
			pendingBookList = new ArrayList<>(); // Change variable name ( To: pendingBookList; Orig: PENDING;)
			borrowBookUi.Set_State(BorrowBookUi.UI_STATE.SCANNING);  // Change variable name to borrowBookUi (Orig: UI) // Change class name to BorrowBookUi (Orig: BorrowBookUI)
			ctrlState = ControlState.SCANNING; } //Change enum name (Orig: enum CONTROL_STATE) (To:ctrlState; Orig:State;)
		else
		{
			borrowBookUi.Display("Member cannot borrow at this time");  // Change variable name to borrowBookUi (Orig: UI)
			borrowBookUi.Set_State(BorrowBookUi.UI_STATE.RESTRICTED); }} // Change variable name to borrowBookUi (Orig: UI) // Change class name to BorrowBookUi (Orig: BorrowBookUI)


	public void scanned(int bookId) { //Change method name (To:scanned; Orig:Scanned;)
		book = null; // Change variable name ( To: book; Orig: BOOK;)
		if (!ctrlState.equals(ControlState.SCANNING)) { //Change enum name (Orig: enum CONTROL_STATE) (To:ctrlState; Orig:State;)
			throw new RuntimeException("BorrowBookControl: cannot call bookScanned except in SCANNING state");
		}
		book = library.Book(bookId); // Change variable name to library (Orig: LIBRARY) // Change variable name ( To: book; Orig: BOOK;)
		if (book == null) { // Change variable name ( To: book; Orig: BOOK;)
			borrowBookUi.Display("Invalid bookId");  // Change variable name to borrowBookUi (Orig: UI)
			return;
		}
		if (!book.isAvailable()) { // Change variable name ( To: book; Orig: BOOK;) Change method name (To:isAvailable; Orig:AVAILABLE;)
			borrowBookUi.Display("Book cannot be borrowed");  // Change variable name to borrowBookUi (Orig: UI)
			return;
		}
		pendingBookList.add(book); // Change variable name ( To: pendingBookList; Orig: PENDING; , To: book; Orig: BOOK;)
		for (book B : pendingBookList) { // Change variable name ( To: pendingBookList; Orig: PENDING; , To: book; Orig: BOOK;)
			borrowBookUi.Display(B.toString());  // Change variable name to borrowBookUi (Orig: UI)
		}
		if (library.loanRemaningForMember(member) - pendingBookList.size() == 0) { // Change variable name( To: library;  Orig: LIBRARY; , To: member;  Orig: M; ,  To: pendingBookList; Orig: PENDING;) Change method name (To:loanRemaningForMember; Orig:Loans_Remaining_For_Member;)
			borrowBookUi.Display("Loan limit reached");  // Change variable name to borrowBookUi (Orig: UI)
			Complete();
		}
	}


	public void complete() { //Change method name (To:complete; Orig:Complete;)
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


	public void commitLoans() { //Change method name (To:commiLoans; Orig:Commit_LoOns;)
		if (!ctrlState.equals(ControlState.FINALISING)) { //Change enum name (Orig: enum CONTROL_STATE) (To:ctrlState; Orig:State;)
			throw new RuntimeException("BorrowBookControl: cannot call commitLoans except in FINALISING state");
		}
		for (Book book : pendingBookList) { // Change variable name ( To: pendingBookList; Orig: PENDING;,  , To: book; Orig: B;) Change class name (To:Book; Orig:book;)
			Loan loan = library.issueLoan(book, member); // Change variable name( To: library;  Orig: LIBRARY; , To: member;  Orig: M; , To: loan;  Orig: LOAN;)  Change method name (To:issueLoan; Orig:ISSUE_LAON;) Change class name (To:Loan; Orig:loan;)
			completedBookList.add(loan); // Change variable name ( To: completedBookList; Orig: COMPLETED;)
		}
		borrowBookUi.Display("Completed Loan Slip");  // Change variable name to borrowBookUi (Orig: UI)
		for (Loan loan : completedBookList) { // Change variable name ( To: completedBookList; Orig: COMPLETED;) Change class name (To:Loan; Orig:loan;)
			borrowBookUi.Display(loan.toString());  // Change variable name  (To: borrowBookUi; Orig: UI , To: loan;  Orig: LOAN;)
		}
		borrowBookUi.Set_State(BorrowBookUi.UI_STATE.COMPLETED);  // Change variable name to borrowBookUi (Orig: UI) // Change class name to BorrowBookUi (Orig: BorrowBookUI)
		ctrlState = ControlState.COMPLETED; //Change enum name (Orig: enum CONTROL_STATE) (To:ctrlState; Orig:State;)
	}


	public void cancel() {
		UI.Set_State(BorrowBookUi.UI_STATE.CANCELLED);  // Change variable name to borrowBookUi (Orig: UI) // Change class name to BorrowBookUi (Orig: BorrowBookUI)
		ctrlState = ControlState.CANCELLED; //Change enum name (Orig: enum CONTROL_STATE) (To:ctrlState; Orig:State;)
	}


}

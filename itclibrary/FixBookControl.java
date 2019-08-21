public class FixBookControl {
	
	private FixBookUI ui;// change the variable name as ui
	private enum CONTROL_STATE { INITIALISED, READY, FIXING };
	private CONTROL_STATE state;// change the variable name as state
	
	private library library;// change the variable name as library
	private book currentBook;// change the variable name as currentBook


	public FixBookControl() {
		this.library = library.INSTANCE();// change according to variable name library
		state = CONTROL_STATE.INITIALISED;// change according to variable name state
	}
	
	
	public void Set_Ui(FixBookUI ui1) {//change parameter name ui to ui1 for easyli identify with variable name
		if (!state.equals(CONTROL_STATE.INITIALISED)) {// change according to variable name state
			throw new RuntimeException("FixBookControl: cannot call setUI except in INITIALISED state");
		}	
		this.ui = ui1;// change according to parameter and variable name ui1 and ui
		ui1.Set_State(FixBookUI.UI_STATE.READY);// change according to parameter name ui1
		state = CONTROL_STATE.READY;// change according to variable name state		
	}


	public void Book_scanned(int bookId) {
		if (!state.equals(CONTROL_STATE.READY)) {// change according to variable name state
			throw new RuntimeException("FixBookControl: cannot call bookScanned except in READY state");
		}	
		currentBook = library.Book(bookId);// change according to variable name library, currentBook
		
		if (currentBook == null) {// change according to variable name currentBook
			ui.display("Invalid bookId");// change according to variable name ui
			return;
		}
		if (!currentBook.IS_Damaged()) {// change according to variable name currentBook
			ui.display("Book has not been damaged");// change according to variable name ui
			return;
		}
		ui.display(currentBook.toString());// change according to variable name ui, currentBook
		ui.Set_State(FixBookUI.UI_STATE.FIXING);// change according to variable name ui
		state = CONTROL_STATE.FIXING;// change according to variable name state		
	}


	public void FIX_Book(boolean MUST_fix) {
		if (!state.equals(CONTROL_STATE.FIXING)) {// change according to variable name state
			throw new RuntimeException("FixBookControl: cannot call fixBook except in FIXING state");
		}	
		if (MUST_fix) {
			library.Repair_BOOK(currentBook);// change according to variable name library, currentBook
		}
		currentBook = null;// change according to variable name currentBook
		ui.Set_State(FixBookUI.UI_STATE.READY);// change according to variable name ui
		state = CONTROL_STATE.READY;// change according to variable name state		
	}

	
	public void SCannING_COMplete() {
		if (!state.equals(CONTROL_STATE.READY)) {// change according to variable name state
			throw new RuntimeException("FixBookControl: cannot call scanningComplete except in READY state");
		}	
		ui.Set_State(FixBookUI.UI_STATE.COMPLETED);// change according to variable name ui		
	}






}

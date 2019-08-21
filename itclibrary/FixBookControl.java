public class FixBookControl {
	
	private FixBookUI ui;// change the variable name as ui
	private enum ControlState { INITIALISED, READY, FIXING };// change enum name as ControlState
	private ControlState state;// change the variable name as state, enum name as ControlState
	
	private library library;// change the variable name as library
	private book currentBook;// change the variable name as currentBook


	public FixBookControl() {
		this.library = library.INSTANCE();// change according to variable name library
		state = ControlState.INITIALISED;// change according to variable name state ,enum name as ControlState
	}
	
	//change method name as setUi
	public void setUi(FixBookUI ui1) {//change parameter name ui to ui1 for easyli identify with variable name
		if (!state.equals(ControlState.INITIALISED)) {// change according to variable name state ,enum name as ControlState
			throw new RuntimeException("FixBookControl: cannot call setUI except in INITIALISED state");
		}	
		this.ui = ui1;// change according to parameter and variable name ui1 and ui
		ui1.setState(FixBookUI.UiState.READY);// change according to parameter name ui1 and method name setState ,enum name as UiState
		state = ControlState.READY;// change according to variable name state ,enum name as ControlState		
	}


	public void bookScanned(int bookId) {//change method name as bookScanned
		if (!state.equals(ControlState.READY)) {// change according to variable name state ,enum name as ControlState
			throw new RuntimeException("FixBookControl: cannot call bookScanned except in READY state");
		}	
		currentBook = library.Book(bookId);// change according to variable name library, currentBook
		
		if (currentBook == null) {// change according to variable name currentBook
			ui.isDisplay("Invalid bookId");// change according to variable name ui and method name isDisplay
			return;
		}
		if (!currentBook.IS_Damaged()) {// change according to variable name currentBook
			ui.isDisplay("Book has not been damaged");// change according to variable name ui and method name isDisplay
			return;
		}
		ui.isDisplay(currentBook.toString());// change according to variable name ui, currentBook and method name isDisplay
		ui.setState(FixBookUI.UiState.FIXING);// change according to variable name ui and method name setState, enum name as Uistate
		state = ControlState.FIXING;// change according to variable name state	,enum name as ControlState	
	}


	public void fixBook(boolean MUST_fix) {//change method name as fixBook
		if (!state.equals(ControlState.FIXING)) {// change according to variable name state
			throw new RuntimeException("FixBookControl: cannot call fixBook except in FIXING state");
		}	
		if (MUST_fix) {
			library.Repair_BOOK(currentBook);// change according to variable name library, currentBook
		}
		currentBook = null;// change according to variable name currentBook
		ui.setState(FixBookUI.UiState.READY);// change according to variable name ui and method name setState, enum name as UiState
		state = ControlState.READY;// change according to variable name state		
	}

	
	public void scanningComplete() {// change method name as scanningComplete
		if (!state.equals(ControlState.READY)) {// change according to variable name state
			throw new RuntimeException("FixBookControl: cannot call scanningComplete except in READY state");
		}	
		ui.setState(FixBookUI.UiState.COMPLETED);// change according to variable name ui and method name setState, enum name as UiState		
	}






}

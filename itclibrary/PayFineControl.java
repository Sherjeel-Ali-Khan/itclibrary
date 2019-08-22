public class PayFineControl {
	
	private PayFineUI ui;// change the variable name as ui
	private enum ControlState { INITIALISED, READY, PAYING, COMPLETED, CANCELLED };// Change enum name as ControlState
	private ControlState state;// change the variable name as state ,enum name as ControlState
	
	private library library;// change the variable name as library
	private member member;// change the variable name as member


	public PayFineControl() {
		this.library = library.getInstance();// change according to variable name library and method name getInstance
		state = ControlState.INITIALISED;// change according to variable name state ,enum name as ControlState
	}
	
	// change method neme as setUi
	public void setUi(PayFineUI payFineUI) {//change parameter name ui to state1 for payFineUI identify with variable name
		if (!state.equals(ControlState.INITIALISED)) {// change according to variable name state ,enum name as ControlState 
			throw new RuntimeException("PayFineControl: cannot call setUI except in INITIALISED state");
		}	
		this.ui = payFineUI;// change according to parameter and variable name payFineUI and ui
		payFineUI.setState(PayFineUI.UiState.READY);// change according to parameter name payFineUI and method name setState ,enum name as UiState
		state = ControlState.READY;// change according to variable name state ,enum name as ControlState 		
	}


	public void cardSwiped(int memberId) {// change method neme as cardSwiped
		if (!state.equals(ControlState.READY)) {// change according to variable name state ,enum name as ControlState
			throw new RuntimeException("PayFineControl: cannot call cardSwiped except in READY state");
		}	
		member = library.MEMBER(memberId);// change according to variable name library , member
		
		if (member == null) {// change according to variable name member 
			ui.isDisplay("Invalid Member Id");// change according to variable name ui and method name isDisplay 
			return;
		}
		ui.isDisplay(member.toString());// change according to variable name ui,member and method name isDisplay
		ui.setState(PayFineUI.UiState.PAYING);// change according to variable name ui and method name setState,enum name as UiState
		state = ControlState.PAYING;// change according to variable name state ,enum name as ControlState
	}
	
	
	public void isCancel() {// change method neme as isCancel
		ui.setState(PayFineUI.UiState.CANCELLED);// change according to variable name ui and method name setState ,enum name as UiState
		state = ControlState.CANCELLED;// change according to variable name state ,enum name as ControlState
	}


	public double payFine(double amount) {// change method neme as payFine, parameter as amount
		if (!state.equals(ControlState.PAYING)) {// change according to variable name state,enum name as ControlState 
			throw new RuntimeException("PayFineControl: cannot call payFine except in PAYING state");
		}	
		double change = member.Pay_Fine(amount);// change according to variable name member ,change, amount
		if (change > 0) {//change variable name as change
			ui.isDisplay(String.format("Change: $%.2f", change));// change according to variable name ui,change and method name isDisplay
		}
		ui.isDisplay(member.toString());// change according to variable name ui , member and method name display
		ui.setState(PayFineUI.UiState.COMPLETED);// change according to variable name ui and method name setState ,enum name as UiState
		state = ControlState.COMPLETED;// change according to variable name state ,enum name as ControlState
		return change;//change variable name as change
	}
	


}

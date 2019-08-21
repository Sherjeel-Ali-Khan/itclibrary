public class PayFineControl {
	
	private PayFineUI ui;// change the variable name as ui
	private enum CONTROL_STATE { INITIALISED, READY, PAYING, COMPLETED, CANCELLED };
	private CONTROL_STATE state;// change the variable name as state
	
	private library library;// change the variable name as library
	private member member;// change the variable name as member


	public PayFineControl() {
		this.library = library.INSTANCE();// change according to variable name library 
		state = CONTROL_STATE.INITIALISED;// change according to variable name state 
	}
	
	
	public void Set_UI(PayFineUI ui1) {//change parameter name ui to state1 for uii identify with variable name
		if (!state.equals(CONTROL_STATE.INITIALISED)) {// change according to variable name state 
			throw new RuntimeException("PayFineControl: cannot call setUI except in INITIALISED state");
		}	
		this.ui = ui1;// change according to parameter and variable name ui1 and uil
		ui1.Set_State(PayFineUI.UI_STATE.READY);// change according to parameter name ui1 
		state = CONTROL_STATE.READY;// change according to variable name state 		
	}


	public void Card_Swiped(int memberId) {
		if (!state.equals(CONTROL_STATE.READY)) {// change according to variable name state 
			throw new RuntimeException("PayFineControl: cannot call cardSwiped except in READY state");
		}	
		member = library.MEMBER(memberId);// change according to variable name library , member
		
		if (member == null) {// change according to variable name member 
			ui.DiSplAY("Invalid Member Id");// change according to variable name ui 
			return;
		}
		ui.DiSplAY(member.toString());// change according to variable name ui,member 
		ui.Set_State(PayFineUI.UI_STATE.PAYING);// change according to variable name ui 
		state = CONTROL_STATE.PAYING;// change according to variable name state 
	}
	
	
	public void CaNcEl() {
		ui.Set_State(PayFineUI.UI_STATE.CANCELLED);// change according to variable name ui 
		state = CONTROL_STATE.CANCELLED;// change according to variable name state 
	}


	public double PaY_FiNe(double AmOuNt) {
		if (!state.equals(CONTROL_STATE.PAYING)) {// change according to variable name state 
			throw new RuntimeException("PayFineControl: cannot call payFine except in PAYING state");
		}	
		double ChAnGe = member.Pay_Fine(AmOuNt);// change according to variable name member 
		if (ChAnGe > 0) {
			ui.DiSplAY(String.format("Change: $%.2f", ChAnGe));// change according to variable name ui 
		}
		ui.DiSplAY(member.toString());// change according to variable name ui , member 
		ui.Set_State(PayFineUI.UI_STATE.COMPLETED);// change according to variable name ui 
		state = CONTROL_STATE.COMPLETED;// change according to variable name state 
		return ChAnGe;
	}
	


}

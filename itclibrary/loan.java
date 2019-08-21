import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("serial")
public class Loan implements Serializable {
	
	public static enum LOAN_STATE { CURRENT, OVER_DUE, DISCHARGED };
	
	private int id; //Change variable name ID to id
	private book book; //Change variable name B to book
	private Member member; //Change variable name M to member
	private Date date; //Change variable name D to date
	private LOAN_STATE state; 

	
	public Loan(int loanId, book book, Member member, Date dueDate) {
		this.id = loanId;
		this.book = book;
		this.member = member;
		this.date = dueDate;
		this.state = LOAN_STATE.CURRENT;
	}

	
	public void checkOverDue() {
		if (state == LOAN_STATE.CURRENT &&
			Calendar.INSTANCE().Date().after(date)) {
			this.state = LOAN_STATE.OVER_DUE;			
		}
	}

	
	public boolean isOverDue() { //Change Method name OVer_Due to isOverDue
		return state == LOAN_STATE.OVER_DUE;
	}

	
	public Integer getId() { //Change method name ID to getID
		return id;
	}


	public Date getDueDate() { //Change method name Get_Due_Date to getDueDate
		return date;
	}
	
	
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		StringBuilder sb = new StringBuilder();
		sb.append("Loan:  ").append(id).append("\n")
		  .append("  Borrower ").append(member.getId()).append(" : ")
		  .append(member.getLastName()).append(", ").append(member.getFirstName()).append("\n")
		  .append("  Book ").append(book.id()).append(" : " )
		  .append(book.TITLE()).append("\n")
		  .append("  DueDate: ").append(sdf.format(date)).append("\n")
		  .append("  State: ").append(state);		
		return sb.toString();
	}


	public Member getMember() { //Change method name Member to getMember
		return member;
	}


	public book getBook() { //Change method name Book to getBook
		return book;
	}


	public void getDischargeStatus() { //Change method name DiScHaRgE to getDischargeStatus
		state = LOAN_STATE.DISCHARGED;		
	}

}

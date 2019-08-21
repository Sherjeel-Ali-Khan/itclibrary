import java.io.Serializable;

// Initiate to fix "book.java" file
@SuppressWarnings("serial")
// Implement Book class by Serializable in order to send data through network or other platform
public class Book implements Serializable {

 // Private variables of the Book class
    private String title;   // Create "title" string for saving the title of the book
    private String author;  // Create "author" string for saving the author name of the book
    private String callNo;  // Create "callNo" string for saving the phone number to contact author
    private int id;         // Create "id" integer for saving the unique identity of the book

    private enum State { AVAILABLE, ON_LOAN, DAMAGED, RESERVED }; //Create an enum "State" and define states as AVAILABLE, ON_LOAN, DAMAGED, and RESERVED.
    private State state; // Create an instance of enum and name it as "State"
	
	
	public Book(String author, String title, String callNo, int id) {
		this.AUTHOR = author;
		this.TITLE = title;
		this.CALLNO = callNo;
		this.ID = id;
		this.State = STATE.AVAILABLE;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Book: ").append(ID).append("\n")
		  .append("  Title:  ").append(TITLE).append("\n")
		  .append("  Author: ").append(AUTHOR).append("\n")
		  .append("  CallNo: ").append(CALLNO).append("\n")
		  .append("  State:  ").append(State);
		
		return sb.toString();
	}

	public Integer getId() {
		return ID;
	}

	public String getTitle() {
		return TITLE;
	}


	
	public boolean isAvailable() {
		return State == STATE.AVAILABLE;
	}

	
	public boolean isOnLoan() {
		return State == STATE.ON_LOAN;
	}

	
	public boolean isDamaged() {
		return State == STATE.DAMAGED;
	}

	
	public void borrowBook() {
		if (State.equals(STATE.AVAILABLE)) {
			State = STATE.ON_LOAN;
		}
		else {
			throw new RuntimeException(String.format("Book: cannot borrow while book is in state: %s", State));
		}
		
	}


	public void returnBook(boolean DAMAGED) {
		if (State.equals(STATE.ON_LOAN)) {
			if (DAMAGED) {
				State = STATE.DAMAGED;
			}
			else {
				State = STATE.AVAILABLE;
			}
		}
		else {
			throw new RuntimeException(String.format("Book: cannot Return while book is in state: %s", State));
		}		
	}

	
	public void repairBook() {
		if (State.equals(STATE.DAMAGED)) {
			State = STATE.AVAILABLE;
		}
		else {
			throw new RuntimeException(String.format("Book: cannot repair while book is in state: %s", State));
		}
	}


}

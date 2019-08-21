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

    // Create a constructor of Book class and set all the private
    // This constructor enforces to set all private variables when the object of this class is created.
    // It requires unique identity(id), author name(author), title of the book(title), and phone number to contact author(callNo).
    public Book(String author, String title, String callNo, int id) {
        this.author = author;    // it sets the author string of the Book class which passes to the constructor.
        this.title = title;      // it sets the title string of the Book class which passes to the constructor.
        this.callNo = callNo;    // it sets the callNo string of the Book class which passes to the constructor.
        this.id = id;            // it sets the id integer of the Book class which passes to the constructor.
        this.state = State.AVAILABLE;  // it sets the state enum of the Book to the "AVAILABLE" state.
    }

    // Create a method "toString" which combine all the information of the Book class and return as a string.
    public String toString() {
        StringBuilder sb = new StringBuilder(); // Create StringBuilder as sb to combine all the data of the class
        sb.append("Book: ").append(id).append("\n")
                .append("  Title:  ").append(title).append("\n")
                .append("  Author: ").append(author).append("\n")
                .append("  CallNo: ").append(callNo).append("\n")
                .append("  State:  ").append(state); // Combine the data (unique identity, title, author name, phone number, and state of the book)  )

        return sb.toString(); // return StringBuilder "sb" as a string
    }

    // Create getId method to get unique identity of the book
	public Integer getId() {
		return ID;
	}

    // Create getTitle method to get the tile of the book
	public String getTitle() {
		return TITLE;
	}

    // Create isAvailable method to check that the book is available or not, in the form of boolean.	
	public boolean isAvailable() {
		return State == STATE.AVAILABLE;
	}

    // Create isOnLoan method to check that the book is on loan or not, in the form of boolean.	
	public boolean isOnLoan() {
		return State == STATE.ON_LOAN;
	}

    // Create isDamaged method to check that the book is damaged or not, in the form of boolean.	
	public boolean isDamaged() {
		return State == STATE.DAMAGED;
	}

    // Create borrowBook method to borrow the book from library and set the state to "ON_LOAN"	
	public void borrowBook() {
		if (State.equals(STATE.AVAILABLE)) {
			State = STATE.ON_LOAN;
		}
		else {
			throw new RuntimeException(String.format("Book: cannot borrow while book is in state: %s", State));
		}
		
	}

    // Create returnBook method to return the book to library and set the state to "DAMAGED" or "AVAILABLE" from "ON_LOAN" state
    // This method requires isDamaged boolean to check the book is damaged or not. This helps to change the state of the book
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

    // Create repairBook method to repair the book and set the state to "AVAILABLE"	
	public void repairBook() {
		if (State.equals(STATE.DAMAGED)) {
			State = STATE.AVAILABLE;
		}
		else {
			throw new RuntimeException(String.format("Book: cannot repair while book is in state: %s", State));
		}
	}
}

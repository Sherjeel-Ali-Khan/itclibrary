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
        return id; // return integer "id" of the book
    }

    // Create getTitle method to get the tile of the book
    public String getTitle() {
        return title; // return string "tile" of the book
    }

    // Create isAvailable method to check that the book is available or not, in the form of boolean.
    public boolean isAvailable() {
        return state == State.AVAILABLE; // return boolean as true or false if the state is AVAILABLE or not respectively
    }

    // Create isOnLoan method to check that the book is on loan or not, in the form of boolean.
    public boolean isOnLoan() {
        return state == State.ON_LOAN;  // return boolean as true or false if the state is on loan or not respectively
    }

    // Create isDamaged method to check that the book is damaged or not, in the form of boolean.
    public boolean isDamaged() {
        return state == State.DAMAGED;  // return boolean as true or false if the state is damaged or not respectively
    }

    // Create borrowBook method to borrow the book from library and set the state to "ON_LOAN"
    public void borrowBook() {
        // Check the availability of the book
        if (state.equals(State.AVAILABLE)) { // If the state of the Book is AVAILABLE
            state = State.ON_LOAN; // Set the state to "ON_LOAN"
        } else { // If the state of the Book is not AVAILABLE then show the error
            throw new RuntimeException(String.format("Book: cannot borrow while book is in state: %s", state)); // Throw an exception error of following text
        }
    }

    // Create returnBook method to return the book to library and set the state to "DAMAGED" or "AVAILABLE" from "ON_LOAN" state
    // This method requires isDamaged boolean to check the book is damaged or not. This helps to change the state of the book
    public void returnBook (boolean isDamaged) {
        // Check the state of the book is on loan
        if (state.equals(State.ON_LOAN)) { // If the state of the Book is ON_LOAN state
            // Check the book is damaged or not then set the state to "DAMAGED" or "AVAILABLE" from "ON_LOAN" state
            if (isDamaged) {    // If the book is damaged set the state to "DAMAGED"
                state = State.DAMAGED; // Set the state to "DAMAGED"
            } else {            // If the book is damaged set the state to "AVAILABLE"
                state = State.AVAILABLE; // Set the state to "AVAILABLE"
            }
        } else { // If the state of the Book is not ON_LOAN state then show the error
            throw new RuntimeException(String.format("Book: cannot Return while book is in state: %s", state)); // Throw an exception error of following text
        }
    }
    // Create repairBook method to repair the book and set the state to "AVAILABLE"
    public void repairBook() {
        // Check the book is DAMAGED then set state to "AVAILABLE"
        if (state.equals(State.DAMAGED)) { // If the state of the Book is DAMAGED
            state = State.AVAILABLE; // Set the state to "AVAILABLE"
        } else {  // If the state of the Book is not DAMAGED then show the error
            throw new RuntimeException(String.format("Book: cannot repair while book is in state: %s", state)); // Throw an exception error of following text
        }
    }
}

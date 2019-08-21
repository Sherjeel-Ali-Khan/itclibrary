
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class Library implements Serializable {
	
	private static final String libraryFile = "library.obj";
	private static final int loanLimit = 2;
	private static final int loanPeriod = 2;
	private static final double finePerDay = 1.0;
	private static final double maxFinesOwed = 1.0;
	private static final double damageFee = 2.0;
	
	private static Library SeLf;
	private int bookId; //Change variable name BOOK_ID to bookId
	private int memberId; //Change variable name to MEMBER_ID to memberId
	private int loanId; //Change variable name LOAN_ID to loanId
	private Date loanDate; //Change variable name LOAN_DATE to loanDate
	
	private Map<Integer, book> catalog; //Change map name CATALOG to catalog
	private Map<Integer, Member> members; //Change map name MEMBERS to members
	private Map<Integer, Loan> loans; //Change map name LOANS to loans
	private Map<Integer, Loan> currentLoans; //Change map name CURRENT_LOANS to currentLoans
	private Map<Integer, book> damagedBooks; //Change map name DAMAGED_BOOKS to damagedBooks
	

	private Library() {
		catalog = new HashMap<>();
		members = new HashMap<>();
		loans = new HashMap<>();
		currentLoans = new HashMap<>();
		damagedBooks = new HashMap<>();
		bookId = 1;
		memberId = 1;		
		loanId = 1;		
	}

	
	public static synchronized Library instance() {		
		if (SeLf == null) {
			Path PATH = Paths.get(libraryFile);			
			if (Files.exists(PATH)) {	
				try (ObjectInputStream LiF = new ObjectInputStream(new FileInputStream(libraryFile));) {
			    
					SeLf = (Library) LiF.readObject();
					Calendar.INSTANCE().Set_dATE(SeLf.loanDate);
					LiF.close();
				}
				catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			else SeLf = new Library();
		}
		return SeLf;
	}

	
	public static synchronized void save() { //Change mehod name SAVE to save
		if (SeLf != null) {
			SeLf.loanDate = Calendar.INSTANCE().Date();
			try (ObjectOutputStream LoF = new ObjectOutputStream(new FileOutputStream(libraryFile));) {
				LoF.writeObject(SeLf);
				LoF.flush();
				LoF.close();	
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	
	public int getBookId() { //Change method name BookID to getBookId
		return bookId;
	}
	
	
	public int getMemberID() { //Change method name MemberID to getMemberID
		return memberId;
	}
	
	
	private int getNextBID() { //Change method name NextBID to getNextBID
		return bookId++;
	}

	
	private int getNextMID() { //Change method name NextMID to getNextMID
		return memberId++;
	}

	
	private int getNextLID() { //Change method name NextLID to getNextLID
		return loanId++;
	}

	
	public List<Member> getMembers() {		//Change method name MEMBERS to getMembers
		return new ArrayList<Member>(members.values()); 
	}


	public List<book> getBooks() {		//Change method name BOOKS to getBooks
		return new ArrayList<book>(catalog.values()); 
	}


	public List<Loan> getCurrentLoans() { //Change method name CurrentLoans to getCurrentLoans
		return new ArrayList<Loan>(currentLoans.values());
	}


	public Member addMember(String lastName, String firstName, String email, int phoneNo) {	//Change method name Add_mem to addMember	
		Member member = new Member(lastName, firstName, email, phoneNo, getNextMID());
		members.put(member.getId(), member);		
		return member;
	}

	
	public book addBook(String a, String t, String c) {		//Change method name Add_book to addBook
		book b = new book(a, t, c, getNextBID());
		catalog.put(b.ID(), b);		
		return b;
	}

	
	public Member getMember(int memberId) { //Change method name MEMBER to getMember
		if (members.containsKey(memberId)) 
			return members.get(memberId);
		return null;
	}

	
	public book getBook(int bookId) { //Change method name Book to getBook
		if (catalog.containsKey(bookId)) 
			return catalog.get(bookId);		
		return null;
	}

	
	public int getLoanLimit() { //Change method name LOAN_LIMIT getLoanLimit
		return loanLimit;
	}

	
	public boolean isMemberCanBorrow(Member member) {	//Change method name MEMBER_CAN_BORROW to isMemberCanBorrow
		if (member.getNumberOfCurrentLoans() == loanLimit ) 
			return false;
				
		if (member.getFinesOwed() >= maxFinesOwed) 
			return false;
				
		for (Loan loan : member.getLoans()) 
			if (loan.isOverDue()) 
				return false;
			
		return true;
	}

	
	public int loansRemainingForMember(Member member) {	//Change method name Loans_Remaining_For_Member to loansRemainingForMember	
		return loanLimit - member.getNumberOfCurrentLoans();
	}

	
	public Loan issueLoan(book book, Member member) { //Change method name ISSUE_LAON to issueLoan
		Date dueDate = Calendar.INSTANCE().Due_Date(loanPeriod);
		Loan loan = new Loan(getNextLID(), book, member, dueDate);
		member.takeOutLoan(loan);
		book.Borrow();
		loans.put(loan.getId(), loan);
		currentLoans.put(book.ID(), loan);
		return loan;
	}
	
	
	public Loan getLoanByBookId(int bookId) { //Change method name LOAN_BY_BOOK_ID getLoanByBookId
		if (currentLoans.containsKey(bookId)) {
			return currentLoans.get(bookId);
		}
		return null;
	}

	
	public double calculateOverDueFine(Loan loan) { //Change method name CalculateOverDueFine to calculateOverDueFine
		if (loan.isOverDue()) {
			long daysOverDue = Calendar.INSTANCE().Get_Days_Difference(loan.getDueDate());
			double fine = daysOverDue * finePerDay;
			return fine;
		}
		return 0.0;		
	}


	public void dischargeLoan(Loan currentLoan, boolean isDamaged) { //Change method name Discharge_loan to dischargeLoan
		Member member = currentLoan.getMember();
		book book  = currentLoan.getBook();
		
		double overDueFine = calculateOverDueFine(currentLoan);
		member.addFine(overDueFine);	
		
		member.dischargeLoan(currentLoan);
		book.Return(isDamaged);
		if (isDamaged) {
			member.addFine(damageFee);
			damagedBooks.put(book.ID(), book);
		}
		currentLoan.getDischargeStatus();
		currentLoans.remove(book.ID());
	}


	public void checkCurrentLoans() { 
		for (Loan loan : currentLoans.values()) {
			loan.checkOverDue();
		}		
	}


	public void repairBook(book currentBook) { //Change method name Repair_BOOK to repairBook
		if (damagedBooks.containsKey(currentBook.ID())) {
			currentBook.Repair();
			damagedBooks.remove(currentBook.ID());
		}
		else {
			throw new RuntimeException("Library: repairBook: book is not damaged");
		}
		
	}
	
	
}

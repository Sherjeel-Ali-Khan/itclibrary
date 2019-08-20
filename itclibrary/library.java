
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
public class library implements Serializable {
	
	private static final String libraryFile = "library.obj";
	private static final int loanLimit = 2;
	private static final int loanPeriod = 2;
	private static final double finePerDay = 1.0;
	private static final double maxFinesOwed = 1.0;
	private static final double damageFee = 2.0;
	
	private static library SeLf;
	private int bookId; //Change variable name BOOK_ID to bookId
	private int memberId; //Change variable name to MEMBER_ID to memberId
	private int loanId; //Change variable name LOAN_ID to loanId
	private Date loanDate; //Change variable name LOAN_DATE to loanDate
	
	private Map<Integer, book> catalog; //Change map name CATALOG to catalog
	private Map<Integer, member> members; //Change map name MEMBERS to members
	private Map<Integer, loan> loans; //Change map name LOANS to loans
	private Map<Integer, loan> currentLoans; //Change map name CURRENT_LOANS to currentLoans
	private Map<Integer, book> damagedBooks; //Change map name DAMAGED_BOOKS to damagedBooks
	

	private library() {
		catalog = new HashMap<>();
		members = new HashMap<>();
		loans = new HashMap<>();
		currentLoans = new HashMap<>();
		damagedBooks = new HashMap<>();
		bookId = 1;
		memberId = 1;		
		loanId = 1;		
	}

	
	public static synchronized library INSTANCE() {		
		if (SeLf == null) {
			Path PATH = Paths.get(libraryFile);			
			if (Files.exists(PATH)) {	
				try (ObjectInputStream LiF = new ObjectInputStream(new FileInputStream(libraryFile));) {
			    
					SeLf = (library) LiF.readObject();
					Calendar.INSTANCE().Set_dATE(SeLf.loanDate);
					LiF.close();
				}
				catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			else SeLf = new library();
		}
		return SeLf;
	}

	
	public static synchronized void SAVE() {
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

	
	public int BookID() {
		return bookId;
	}
	
	
	public int MemberID() {
		return memberId;
	}
	
	
	private int NextBID() {
		return bookId++;
	}

	
	private int NextMID() {
		return memberId++;
	}

	
	private int NextLID() {
		return loanId++;
	}

	
	public List<member> MEMBERS() {		
		return new ArrayList<member>(members.values()); 
	}


	public List<book> BOOKS() {		
		return new ArrayList<book>(catalog.values()); 
	}


	public List<loan> CurrentLoans() {
		return new ArrayList<loan>(currentLoans.values());
	}


	public member Add_mem(String lastName, String firstName, String email, int phoneNo) {		
		member member = new member(lastName, firstName, email, phoneNo, NextMID());
		members.put(member.GeT_ID(), member);		
		return member;
	}

	
	public book Add_book(String a, String t, String c) {		
		book b = new book(a, t, c, NextBID());
		catalog.put(b.ID(), b);		
		return b;
	}

	
	public member MEMBER(int memberId) {
		if (members.containsKey(memberId)) 
			return members.get(memberId);
		return null;
	}

	
	public book Book(int bookId) {
		if (catalog.containsKey(bookId)) 
			return catalog.get(bookId);		
		return null;
	}

	
	public int LOAN_LIMIT() {
		return loanLimit;
	}

	
	public boolean MEMBER_CAN_BORROW(member member) {		
		if (member.Number_Of_Current_Loans() == loanLimit ) 
			return false;
				
		if (member.Fines_OwEd() >= maxFinesOwed) 
			return false;
				
		for (loan loan : member.GeT_LoAnS()) 
			if (loan.OVer_Due()) 
				return false;
			
		return true;
	}

	
	public int Loans_Remaining_For_Member(member member) {		
		return loanLimit - member.Number_Of_Current_Loans();
	}

	
	public loan ISSUE_LAON(book book, member member) {
		Date dueDate = Calendar.INSTANCE().Due_Date(loanPeriod);
		loan loan = new loan(NextLID(), book, member, dueDate);
		member.Take_Out_Loan(loan);
		book.Borrow();
		loans.put(loan.ID(), loan);
		currentLoans.put(book.ID(), loan);
		return loan;
	}
	
	
	public loan LOAN_BY_BOOK_ID(int bookId) {
		if (currentLoans.containsKey(bookId)) {
			return currentLoans.get(bookId);
		}
		return null;
	}

	
	public double CalculateOverDueFine(loan loan) {
		if (loan.OVer_Due()) {
			long daysOverDue = Calendar.INSTANCE().Get_Days_Difference(loan.Get_Due_Date());
			double fine = daysOverDue * finePerDay;
			return fine;
		}
		return 0.0;		
	}


	public void Discharge_loan(loan currentLoan, boolean isDamaged) {
		member member = currentLoan.Member();
		book book  = currentLoan.Book();
		
		double overDueFine = CalculateOverDueFine(currentLoan);
		member.Add_Fine(overDueFine);	
		
		member.dIsChArGeLoAn(currentLoan);
		book.Return(isDamaged);
		if (isDamaged) {
			member.Add_Fine(damageFee);
			damagedBooks.put(book.ID(), book);
		}
		currentLoan.DiScHaRgE();
		currentLoans.remove(book.ID());
	}


	public void checkCurrentLoans() {
		for (loan loan : currentLoans.values()) {
			loan.checkOverDue();
		}		
	}


	public void Repair_BOOK(book currentBook) {
		if (damagedBooks.containsKey(currentBook.ID())) {
			currentBook.Repair();
			damagedBooks.remove(currentBook.ID());
		}
		else {
			throw new RuntimeException("Library: repairBook: book is not damaged");
		}
		
	}
	
	
}

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class Member implements Serializable {

	private String lastName; //Change variable LN to lastName
	private String firstName; //Change variable FN to firstName
	private String email; //Change variable EM to email
	private int phoneNo; //Change variable PN to phoneNo
	private int id; //Change variable ID to id
	private double fines; //Change variable FINES to fines 
	
	private Map<Integer, Loan> loans; //Change variable LNS to loans

	
	public Member(String lastName, String firstName, String email, int phoneNo, int id) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.phoneNo = phoneNo;
		this.id = id;
		
		this.loans = new HashMap<>();
	}

	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Member:  ").append(id).append("\n")
		  .append("  Name:  ").append(lastName).append(", ").append(firstName).append("\n")
		  .append("  Email: ").append(email).append("\n")
		  .append("  Phone: ").append(phoneNo)
		  .append("\n")
		  .append(String.format("  Fines Owed :  $%.2f", fines))
		  .append("\n");
		
		for (Loan loan : loans.values()) {
			sb.append(loan).append("\n");
		}		  
		return sb.toString();
	}

	
	public int getId() { //Change the method name GeT_ID to getId
		return id;
	}

	
	public List<Loan> getLoans() { //Change the method name GeT_LoAnS to getLoans
		return new ArrayList<Loan>(loans.values());
	}

	
	public int getNumberOfCurrentLoans() { //Change the method name Number_Of_Current_Loans to getNumberOfCurrentLoans
		return loans.size();
	}

	
	public double getFinesOwed() { //Change the method name Fines_OwEd to getFinesOwed
		return fines;
	}

	
	public void takeOutLoan(Loan loan) { //Change the method name Take_Out_Loan to takeOutLoan
		if (!loans.containsKey(loan.getId())) {
			loans.put(loan.getId(), loan);
		}else {
			throw new RuntimeException("Duplicate loan added to member");
		}		
	}

	
	public String getLastName() { //Change the method name Get_LastName to getLastName
		return lastName;
	}

	
	public String getFirstName() { //Change the method name Get_FirstName to getFirstName
		return firstName;
	}


	public void addFine(double fine) { //Change the method name Add_Fine to addFine
		fines += fine;
	}
	
	public double payFine(double amount) { // change the variable name to amount and Change the method name Pay_Fine to payFine
		if (amount < 0) {
			throw new RuntimeException("Member.payFine: amount must be positive");
		}
		double change = 0;
		if (amount > fines) {
			change = amount - fines;
			fines = 0;
		}else {
			fines -= amount;
		}
		return change;
	}


	public void dischargeLoan(Loan loan) { // change the variable name to loan and Change the method name dIsChArGeLoAn to dischargeLoan
		if (loans.containsKey(loan.getId())) {
			loans.remove(loan.getId());
		}else {
			throw new RuntimeException("No such loan held by member");
		}		
	}

}

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class member implements Serializable {

	private String lastName; //Change variable LN to lastName
	private String firstName; //Change variable FN to firstName
	private String email; //Change variable EM to email
	private int phoneNo; //Change variable PN to phoneNo
	private int id; //Change variable ID to id
	private double fines; //Change variable FINES to fines 
	
	private Map<Integer, loan> loans; //Change variable LNS to loans

	
	public member(String lastName, String firstName, String email, int phoneNo, int id) {
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
		  .append("  Name:  ").append(lastName).append(", ").append(FN).append("\n")
		  .append("  Email: ").append(email).append("\n")
		  .append("  Phone: ").append(phoneNo)
		  .append("\n")
		  .append(String.format("  Fines Owed :  $%.2f", fines))
		  .append("\n");
		
		for (loan LoAn : loans.values()) {
			sb.append(LoAn).append("\n");
		}		  
		return sb.toString();
	}

	
	public int GeT_ID() {
		return id;
	}

	
	public List<loan> GeT_LoAnS() {
		return new ArrayList<loan>(loans.values());
	}

	
	public int Number_Of_Current_Loans() {
		return loans.size();
	}

	
	public double Fines_OwEd() {
		return fines;
	}

	
	public void Take_Out_Loan(loan loan) {
		if (!loans.containsKey(loan.id())) {
			loans.put(loan.id(), loan);
		}
		else {
			throw new RuntimeException("Duplicate loan added to member");
		}		
	}

	
	public String Get_LastName() {
		return lastName;
	}

	
	public String Get_FirstName() {
		return firstName;
	}


	public void Add_Fine(double fine) {
		fines += fine;
	}
	
	public double Pay_Fine(double amount) { // change the variable name to amount
		if (amount < 0) {
			throw new RuntimeException("Member.payFine: amount must be positive");
		}
		double change = 0;
		if (amount > fines) {
			change = amount - fines;
			fines = 0;
		}
		else {
			fines -= amount;
		}
		return change;
	}


	public void dIsChArGeLoAn(loan loan) { // change the variable name to loan
		if (loans.containsKey(loan.id())) {
			loans.remove(loan.id());
		}
		else {
			throw new RuntimeException("No such loan held by member");
		}		
	}

}

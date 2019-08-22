import java.text.SimpleDateFormat;
import java.util.Scanner;


public class Main {

	private static Scanner scannerInput; // Change variable name ( To: scannerInput; Orig: IN;)
	private static Library library; //Change class name and variable name ( To: Library; Orig: library;)
	private static String menu; // Change variable name ( To: menu; Orig: MENU;)
	private static Calendar calandar; // Change variable name ( To: calandar; Orig: CAL;)
	private static SimpleDateFormat simpleDateFormat; // Change variable name ( To: simpleDateFormat; Orig: SDF;)


	private static String getMenu() { // Change method name (To:getMenu; Orig:Get_menu;)
		StringBuilder stringBuilder = new StringBuilder(); // Change variable name ( To: stringBuilder; Orig: sb;)

		stringBuilder.append("\nLibrary Main Menu\n\n") // Change variable name ( To: stringBuilder; Orig: sb;)
		  .append("  M  : add member\n")
		  .append("  LM : list members\n")
		  .append("\n")
		  .append("  B  : add book\n")
		  .append("  LB : list books\n")
		  .append("  FB : fix books\n")
		  .append("\n")
		  .append("  L  : take out a loan\n")
		  .append("  R  : return a loan\n")
		  .append("  LL : list loans\n")
		  .append("\n")
		  .append("  P  : pay fine\n")
		  .append("\n")
		  .append("  T  : increment date\n")
		  .append("  Q  : quit\n")
		  .append("\n")
		  .append("Choice : ");

		return stringBuilder.toString(); // Change variable name ( To: stringBuilder; Orig: sb;)
	}


	public static void main(String[] args) {
		try {
			scannerInput = new Scanner(System.in); // Change variable name ( To: scannerInput; Orig: IN;)
			this.library = Library.getInstance(); //Change class name and variable name ( To: Library; Orig: library;) Change method name (To:getInstance; Orig:INSTANCE;)
			this.calandar = Calendar.getInstance(); // Change variable name ( To: calandar; Orig: CAL;) Change method name (To:getInstance; Orig:INSTANCE;)
			simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Change variable name ( To: simpleDateFormat; Orig: SDF;)

			for (Member member : this.library.getMembers()) { //Change class name ( To: Member; Orig: member; ) Change variable name  (To: library; Orig: LIB; )  (To: member; Orig: m; ) Change method name (To:getMembers; Orig:MEMBERS;)
				output(member);
			}
			output(" ");
			for (Book book : library.getBooks()) {  //Change class name ( To: Book; Orig: book; )  Change variable name  (To: library; Orig: LIB; )  (To: book; Orig: b; ) Change method name (To:getBooks; Orig:BOOKS;)
				output(book);
			}

			menu = getMenu(); // Change variable name ( To: menu; Orig: MENU;)

			boolean condition = false; // Change variable name ( To: menu; Orig: MENU;)

			while (!condition) { // Change variable name ( To: condition; Orig: e;)

				output("\n" + simpleDateFormat.format(calandar.Date())); // Change variable name ( To: calandar; Orig: CAL;) ( To: simpleDateFormat; Orig: SDF;)
				String selectedOption = input(menu); // Change variable name ( To: menu; Orig: MENU;) ( To: selectedOption; Orig: c;)

				switch (selectedOption .toUpperCase()) { // Change variable name ( To: selectedOption; Orig: c;)

				case "M":
					addMember(); // Change method name (To:addMember; Orig:ADD_MEMBER;)
					break;

				case "LM":
					getMembers(); // Change method name (To:getMembers; Orig:MEMBERS;)
					break;

				case "B":
					addBook(); // Change method name (To:addBook; Orig:ADD_BOOK;)
					break;

				case "LB":
					getBooks(); // Change method name (To:getBooks; Orig:BOOKS;)
					break;

				case "FB":
					fixBooks(); // Change method name (To:fixBooks; Orig:FIX_BOOKS;)
					break;

				case "L":
					borrowBook(); // Change method name (To:borrowBook; Orig:BORROW_BOOK;)
					break;

				case "R":
					returnBook(); // Change method name (To:returnBook; Orig:RETURN_BOOK;)
					break;

				case "LL":
					currentLoans(); // Change method name (To:currentLoans; Orig:CURRENT_LOANS;)
					break;

				case "P":
					payFine(); // Change method name (To:payFine; Orig:FINES;)
					break;

				case "T":
					incrementDate(); // Change method name (To:incrementDate; Orig:INCREMENT_DATE;)
					break;

				case "Q":
					condition = true;
					break;

				default:
					output("\nInvalid option\n");
					break;
				}

				Library.SAVE(); //Change class name ( To: Library; Orig: library;)
			}
		} catch (RuntimeException e) {
			output(e);
		}
		output("\nEnded\n");
	}

	private static void payFine() { // Change method name (To:payFine; Orig:FINES;)
		new PayFineUi(new PayFineControl()).run(); // Change method name (To:PayFineUi; Orig:PayFineUI;) (To:run; Orig:RuN;)
	}


	private static void currentLoans() { // Change method name (To:currentLoans; Orig:CURRENT_LOANS;)
		output("");
		for (Loan loan : library.currentLoans()) { //Change variable name  To: library; Orig: LIB; ) Change method name (To:currentLoans; Orig:CurrentLoans;)  Change class name ( To: Loan; Orig: loan;)
			output(loan + "\n");
		}
	}



	private static void getBooks() {  // Change method name (To:getBooks; Orig:BOOKS;)
		output("");
		for (Book book : library.getBooks()) { //Change variable name  To: library; Orig: LIB; ) Change method name (To:getBooks; Orig:BOOKS;) Change class name ( To: Book; Orig: book;)
			output(book + "\n");
		}
	}



	private static void getMembers()) { // Change method name (To:getMembers; Orig:MEMBERS;)
		output("");
		for (Member member : library.getMembers()) { //Change variable name  To: library; Orig: LIB; ) Change method name (To:getMembers; Orig:MEMBERS;) Change class name ( To: Member; Orig: member;)
			output(member + "\n");
		}
	}



	private static void borrowBook() {// Change method name (To:borrowBook; Orig:BORROW_BOOK;)
		new BorrowBookUi(new BorrowBookControl()).run();  //Change class name (To:BorrowBookUi; Orig:BorrowBookUI;)
	}


	private static void returnBook() { // Change method name (To:returnBook; Orig:RETURN_BOOK;)
		new ReturnBookUi(new ReturnBookControl()).run(); //Change class name (To:ReturnBookUi; Orig:ReturnBookUI;) Change method name (To:run; Orig:RuN;)
	}


	private static void fixBooks() { // Change method name (To:fixBooks; Orig:FIX_BOOKS;)
		new FixBookUi(new FixBookControl()).run(); //Change class name (To:FixBookUi; Orig:FixBookUI;)  Change method name (To:run; Orig:RuN;)

	}


	private static void incrementDate() { // Change method name (To:incrementDate; Orig:INCREMENT_DATE;)
		try {
			int days = Integer.valueOf(input("Enter number of days: ")).intValue();
			calandar.incrementDate(days); // Change variable name ( To: calandar; Orig: CAL;)
			library.checkCurrentLoans(); //Change variable name  To: library; Orig: LIB; )
			output(simpleDateFormat.format(calandar.Date())); // Change variable name ( To: calandar; Orig: CAL;) ( To: simpleDateFormat; Orig: SDF;)

		} catch (NumberFormatException e) {
			 output("\nInvalid number of days\n");
		}
	}


	private static void addBook() { // Change method name (To:addBook; Orig:ADD_BOOK;)

		String author = input("Enter author: ");  // Change variable name ( To: author; Orig: A;)
		String title  = input("Enter title: ");  // Change variable name ( To: title; Orig: T;)
		String callNumber = input("Enter call number: ");  // Change variable name ( To: callNumber; Orig: C;)
		Book book = library.addBook(author, title, callNumber); //Change variable name  To: library; Orig: LIB; ) Change method name (To:addBook; Orig:Add_book;) Change parameters (To: (author, title, callNumber); Orig: (A, T, C); )
		output("\n" + B + "\n");

	}


	private static void addMember() { // Change method name (To:addMember; Orig:ADD_MEMBER;)
		try {
			String lastName = input("Enter last name: "); // Change variable name ( To: lastName; Orig: LN;)
			String firstName = input("Enter first name: "); // Change variable name ( To: firstName; Orig: FN;)
			String email = input("Enter email: "); // Change variable name ( To: email; Orig: EM;)
			int phoneNo = Integer.valueOf(input("Enter phone number: ")).intValue(); // Change variable name ( To: phoneNo; Orig: PN;)
			Member member = library.addMember(lastName, firstName, email, phoneNo); //Change class name  (To: Member; Orig: member; )  Change variable name  (To: library; Orig: LIB; , To:(lastName, firstName, email, phoneNo); Orig: (LN, FN, EM, PN); ) Change method name (To:addMember; Orig:Add_mem;)
			output("\n" + M + "\n");

		} catch (NumberFormatException e) {
			 output("\nInvalid phone number\n");
		}

	}


	private static String input(String prompt) {
		System.out.print(prompt);
		return scannerInput.nextLine(); // Change variable name ( To: scannerInput; Orig: IN;)
	}



	private static void output(Object object) {
		System.out.println(object);
	}


}

import java.text.SimpleDateFormat;
import java.util.Scanner;


public class Main {

	private static Scanner scannerInput; // Change variable name ( To: scannerInput; Orig: IN;)
	private static Library library; //Change class name and variable name ( To: Library; Orig: library;)
	private static String menu; // Change variable name ( To: menu; Orig: MENU;)
	private static Calendar calandar; // Change variable name ( To: calandar; Orig: CAL;)
	private static SimpleDateFormat smplDateFmrt; // Change variable name ( To: smplDateFmrt; Orig: SDF;)


	private static String getMenu() { // Change method name (To:getMenu; Orig:Get_menu;)
		StringBuilder sb = new StringBuilder();

		sb.append("\nLibrary Main Menu\n\n")
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

		return sb.toString();
	}


	public static void main(String[] args) {
		try {
			scannerInput = new Scanner(System.in); // Change variable name ( To: scannerInput; Orig: IN;)
			this.library = Library.getInstance(); //Change class name and variable name ( To: Library; Orig: library;) Change method name (To:getInstance; Orig:INSTANCE;)
			this.calandar = Calendar.getInstance(); // Change variable name ( To: calandar; Orig: CAL;) Change method name (To:getInstance; Orig:INSTANCE;)
			smplDateFmrt = new SimpleDateFormat("dd/MM/yyyy"); // Change variable name ( To: smplDateFmrt; Orig: SDF;)

			for (Member m : this.library.getMembers()) { //Change class name ( To: Member; Orig: member; ) Change variable name  To: library; Orig: LIB; ) Change method name (To:getMembers; Orig:MEMBERS;)
				output(m);
			}
			output(" ");
			for (Book b : library.getBooks()) {  //Change class name ( To: Book; Orig: book; )  Change variable name  To: library; Orig: LIB; ) Change method name (To:getBooks; Orig:BOOKS;)
				output(b);
			}

			menu = getMenu(); // Change variable name ( To: menu; Orig: MENU;)

			boolean condition = false; // Change variable name ( To: menu; Orig: MENU;)

			while (!condition) { // Change variable name ( To: condition; Orig: e;)

				output("\n" + smplDateFmrt.format(calandar.Date())); // Change variable name ( To: calandar; Orig: CAL;) ( To: smplDateFmrt; Orig: SDF;)
				String c = input(menu); // Change variable name ( To: menu; Orig: MENU;)

				switch (c.toUpperCase()) {

				case "M":
					ADD_MEMBER();
					break;

				case "LM":
					MEMBERS();
					break;

				case "B":
					ADD_BOOK();
					break;

				case "LB":
					BOOKS();
					break;

				case "FB":
					FIX_BOOKS();
					break;

				case "L":
					BORROW_BOOK();
					break;

				case "R":
					RETURN_BOOK();
					break;

				case "LL":
					CURRENT_LOANS();
					break;

				case "P":
					FINES();
					break;

				case "T":
					INCREMENT_DATE();
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

	private static void FINES() {
		new PayFineUI(new PayFineControl()).RuN();
	}


	private static void CURRENT_LOANS() {
		output("");
		for (loan loan : library.currentLoans()) { //Change variable name  To: library; Orig: LIB; ) Change method name (To:currentLoans; Orig:CurrentLoans;)
			output(loan + "\n");
		}
	}



	private static void BOOKS() {
		output("");
		for (book book : library.getBooks()) { //Change variable name  To: library; Orig: LIB; ) Change method name (To:getBooks; Orig:BOOKS;)
			output(book + "\n");
		}
	}



	private static void MEMBERS()) {
		output("");
		for (member member : library.getMembers()) { //Change variable name  To: library; Orig: LIB; ) Change method name (To:getMembers; Orig:MEMBERS;)
			output(member + "\n");
		}
	}



	private static void BORROW_BOOK() {
		new BorrowBookUi(new BorrowBookControl()).run();  //Change class name (To:BorrowBookUi; Orig:BorrowBookUI;)
	}


	private static void RETURN_BOOK() {
		new ReturnBookUi(new ReturnBookControl()).RuN(); //Change class name (To:ReturnBookUi; Orig:ReturnBookUI;)
	}


	private static void FIX_BOOKS() {
		new FixBookiI(new FixBookControl()).RuN(); //Change class name (To:FixBookUi; Orig:FixBookUI;)

	}


	private static void INCREMENT_DATE() {
		try {
			int days = Integer.valueOf(input("Enter number of days: ")).intValue();
			calandar.incrementDate(days); // Change variable name ( To: calandar; Orig: CAL;)
			library.checkCurrentLoans(); //Change variable name  To: library; Orig: LIB; )
			output(smplDateFmrt.format(calandar.Date())); // Change variable name ( To: calandar; Orig: CAL;) ( To: smplDateFmrt; Orig: SDF;)

		} catch (NumberFormatException e) {
			 output("\nInvalid number of days\n");
		}
	}


	private static void ADD_BOOK() {

		String A = input("Enter author: ");
		String T  = input("Enter title: ");
		String C = input("Enter call number: ");
		book B = library.addBook(A, T, C); //Change variable name  To: library; Orig: LIB; ) Change method name (To:addBook; Orig:Add_book;)
		output("\n" + B + "\n");

	}


	private static void ADD_MEMBER() {
		try {
			String LN = input("Enter last name: ");
			String FN  = input("Enter first name: ");
			String EM = input("Enter email: ");
			int PN = Integer.valueOf(input("Enter phone number: ")).intValue();
			Member M = library.addMember(LN, FN, EM, PN); //Change class name  (To: Member; Orig: member; )  Change variable name  (To: library; Orig: LIB; ) Change method name (To:addMember; Orig:Add_mem;)
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

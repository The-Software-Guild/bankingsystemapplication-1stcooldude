import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class Controller {

	private Scanner scanner;

	public Controller(InputStream inputStream) {
		this.scanner = new Scanner(inputStream);
	}

	public int isInputNumberValid() {
		int input = -1;
		try {
			input = scanner.nextInt();
		} catch (Exception e) {
			System.out.println("Please Enter valid Input as number between 1 and 8 only, both inclusive");
			scanner.nextLine();
		}
		return input;
	}

	public String isInputStringValid() {
		String input = null;
		try {
			input = scanner.next();
		} catch (Exception e) {
			System.out.println("Please Enter valid Input as number between 1 and 8 only, both inclusive");
			scanner.nextLine();
		}
		return input;
	}


	public double isInputDoubleValid() {
		double input = -1;
		try {
			input = scanner.nextDouble();
		} catch (Exception e) {
			System.out.println("Please Enter valid Input as number between 1 and 8 only, both inclusive");
			scanner.nextLine();
		}
		return input;
	}

	public Customer getCustomer() {
		String name;
		do {
			System.out.print("Name? ");
			name = isInputStringValid();
		} while (name == null);
		int age;
		do {
			System.out.print("Age? ");
			age = isInputNumberValid();
		} while (age == -1);
		int mobile;
		do {
			System.out.print("Mobile? ");
			mobile = isInputNumberValid();
		} while (mobile == -1);
		String passno;
		do {
			System.out.print("Passport number? ");
			passno = isInputStringValid();
		} while (passno == null);
		String dob = null;
		boolean valid;
		do {
			System.out.print("DOB? ");
			dob = isInputStringValid();
			String[] parts = dob.split("/");
			valid = checkDateIsValid(parts);
		} while (dob == null || valid == false);

		Customer customer = new Customer(name, age, mobile, passno, dob);
		return customer;
	}

	public boolean checkDateIsValid(String[] parts) {
		if (parts.length != 3) {
			return false;
		}
		int year = Integer.parseInt(parts[2]);
		int month = Integer.parseInt(parts[1]);
		int day = Integer.parseInt(parts[0]);
		if (year < 0 || year > 2023) {
			return false;
		}
		if (month < 0 || month > 12) {
			return false;
		}
		boolean leap;
		if (month == 2) {
			if (year % 4 == 0) {
				if (year % 100 == 0) {
					if (year % 400 == 0) {
						leap = true;
					}
					leap = false;
				}
				leap = true;
			}
			leap = false;
			if (leap) {
				if (day < 0 || day > 29) {
					return false;
				}
			} else {
				if (day < 0 || day > 28) {
					return false;
				}
			}
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {
			if (day < 0 || day > 30) {
				return false;
			}
		} else {
			if (day < 0 || day > 31) {
				return false;
			}
		}
		return true;
	}

	public Account getAccount() throws InsufficientBalanceException {
		int accno;
		do {
			System.out.print("Account number? ");
			accno = isInputNumberValid();
		} while (accno == -1);
		int bsb;
		do {
			System.out.print("BSB? ");
			bsb = isInputNumberValid();
		} while (bsb == -1);
		String bankname;
		do {
			System.out.print("Bank name? ");
			bankname = isInputStringValid();
		} while (bankname == null);
		double balance;
		do {
			System.out.print("Balance? ");
			balance = isInputDoubleValid();
		} while (balance == -1);
		String openingdate;
		boolean valid;
		do {
			System.out.print("Opening Date? ");
			openingdate = isInputStringValid();
			String[] parts = openingdate.split("/");
			valid = checkDateIsValid(parts);
		} while (openingdate == null || valid == false);
		String accountType;
		do {
			System.out.print("Account Type (S - savings, F - Fixed Deposit)?");
			accountType = isInputStringValid();
			if (!accountType.equals("S") && !accountType.equals("F")) {
				valid = false;
			} else {
				valid = true;
			}
		} while (accountType == null || !valid);
		Account account = null;
		if (accountType.equals("S")) {
			String salary;
			do {
				System.out.print("Salary Account (Y/N)?");
				salary = isInputStringValid();
			} while (salary == null);
			if (salary.equals("N")) {
				if (balance < SavingsAccount.minimumBalance) {
					throw new InsufficientBalanceException("Insufficient balance for Savings Salary Account, Minimum balance should be\r\n" + "$100");
				} else {
					account = new SavingsAccount(accno, bsb, bankname, balance, openingdate, true);
				}
			} else {
				if (balance != 0) {
					throw new InsufficientBalanceException("Insufficient balance for Savings Non-Salary Account, Balance should be\r\n" + "$0");
				} else {
					account = new SavingsAccount(accno, bsb, bankname, balance, openingdate, false);
				}
			}
		} else {
			int tenure;
			do {
				System.out.print("Tenure?");
				tenure = isInputNumberValid();
				if (tenure < 1 || tenure > 7) {
					valid = false;
				} else {
					valid = true;
				}
			} while (tenure == -1 || !valid);
			account = new FixedDepositAccount(accno, bsb, bankname, balance, openingdate, tenure);
		}
		
		return account;
	}

	public static void main(String[] args) {
		boolean exit = false;

		Controller con = new Controller(System.in);

		CustomerService service = new CustomerServiceImpl();

		do {
			System.out.println("1. Create New Customer Data");
			System.out.println("2. Assign a Bank Account to a Customer");
			System.out.println("3. Display balance or interest earned of a Customer");
			System.out.println("4. Sort Customer Data");
			System.out.println("5. Persist Customer Data");
			System.out.println("6. Show All Customers");
			System.out.println("7. Search Customers by Name");
			System.out.println("8. Exit");

			int input = -1;
			do {
				input = con.isInputNumberValid();
				if (input < 1 || input > 8) {
					System.out.println("Please Enter valid Input as number between 1 and 8 only, both inclusive");
				}
			} while (input == -1);

			Customer customer;
			boolean valid;
			String choice;
			switch (input) {
			case (1):
				customer = con.getCustomer();
				service.createCustomer(customer);
				break;
			case (2):
				int id;
				Account account;
				do {
					System.out.print("ID? ");
					id = con.isInputNumberValid();
				} while(id == -1);
				try {
					customer = service.getCustomer(id);
					account = con.getAccount();
					service.addAccounttoCustomer(id, account);
				} catch (CustomerNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (InsufficientBalanceException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case (3):
				System.out.print("Name of customer? ");
				String name = con.isInputStringValid();
				do {
					System.out.print("Balance(b) or Interest(i)? ");
					choice = con.isInputStringValid();
					if (!choice.equals("b") && !choice.equals("i")) {
						valid = false;
					} else {
						valid = true;
					}
				} while (!valid);
				service.displayBalanceOrInterest(name, choice);
				break;				
			case (4):
				do {
					System.out.print("Sort by name(n), id(i) or balance(b)? ");
					choice = con.isInputStringValid();
					if (!choice.equals("n") && !choice.equals("i") && !choice.equals("b")) {
						valid = false;
					} else {
						valid = true;
					}
				} while (!valid);
				service.sortCustomers(choice);
				break;
			case (5):
				do {
					System.out.print("File System(f) or RDBMS (d)? ");
					choice = con.isInputStringValid();
					if (!choice.equals("f") && !choice.equals("d")) {
						valid = false;
					} else {
						valid = true;
					}
				} while (!valid);
				try {
					service.saveAll(choice);
				} catch (NoAccountForCustomerException e1) {
					e1.printStackTrace();
				}
				break;
			case (6):
				List<Customer> customers = service.getAllCustomers();
				for (Customer c : customers) {
					System.out.println(c);
				}
				break;
			case (7):
				do {
					System.out.print("Name of customer? ");
					name = con.isInputStringValid();
				} while (name == null);
				try {
					customer = service.getCustomer(name);
					System.out.println(customer);
				} catch (CustomerNotFoundException e) {
					e.printStackTrace();
				}
				break;
			case (8):
				exit = true;
			}

		} while (!exit);

	}

}

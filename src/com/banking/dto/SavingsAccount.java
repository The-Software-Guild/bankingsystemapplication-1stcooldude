
public class SavingsAccount extends Account {
	
	private boolean isSalaryAccount;
	public static double minimumBalance = 100;

	public SavingsAccount(int accno, int bsb, String bankname, double balance, String openingdate, boolean isSalaryAccount) {
		super(accno, bsb, bankname, balance, openingdate);
		this.isSalaryAccount = isSalaryAccount;
		interest = calculateInterest();
	}

	@Override
	public double calculateInterest() {
		return 0.04*balance;
		
	}
	
	public void deposit(int amount) {
		balance += amount;
	}
	
	public void withdraw(int amount) {
		if (!isSalaryAccount) {
			if (balance - amount < minimumBalance) {
				//throw error
			}
		} else {
			if (balance - amount < 0) {
				//throw error
			}
		}
		balance -= amount;
	}

	public boolean isSalaryAccount() {
		return isSalaryAccount;
	}

	public void setSalaryAccount(boolean isSalaryAccount) {
		this.isSalaryAccount = isSalaryAccount;
	}
	
	
}

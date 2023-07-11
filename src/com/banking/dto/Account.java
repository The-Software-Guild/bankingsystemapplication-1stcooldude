import java.io.Serializable;

public abstract class Account implements Serializable{
	protected int accno;
	protected int bsb;
	protected String bankname;
	protected double balance;
	protected String openingdate;
	protected double interest;
	
	public Account(int accno, int bsb, String bankname, double balance, String openingdate) {
		super();
		this.accno = accno;
		this.bsb = bsb;
		this.bankname = bankname;
		this.balance = balance;
		this.openingdate = openingdate;
	}
	
	abstract public double calculateInterest();

	public int getAccno() {
		return accno;
	}

	public void setAccno(int accno) {
		this.accno = accno;
	}

	public int getBsb() {
		return bsb;
	}

	public void setBsb(int bsb) {
		this.bsb = bsb;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getOpeningdate() {
		return openingdate;
	}

	public void setOpeningdate(String openingdate) {
		this.openingdate = openingdate;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	@Override
	public String toString() {
		return "Account [accno=" + accno + ", bsb=" + bsb + ", bankname=" + bankname + ", balance=" + balance
				+ ", openingdate=" + openingdate + "]";
	}
	
	
}

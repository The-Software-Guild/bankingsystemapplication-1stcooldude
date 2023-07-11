
public class FixedDepositAccount extends Account {

	private double depositAmount;
	private int tenure;
	
	public FixedDepositAccount(int accno, int bsb, String bankname, double balance, String openingdate, int tenure) {
		super(accno, bsb, bankname, balance, openingdate);
		this.tenure = tenure;
		interest = calculateInterest();
		// TODO Auto-generated constructor stub
	}

	@Override
	public double calculateInterest() {
		return 0.08*balance*tenure;		
	}

	public int getTenure() {
		return tenure;
	}

	public void setTenure(int tenure) {
		this.tenure = tenure;
	}
	
	

}

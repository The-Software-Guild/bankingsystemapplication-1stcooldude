import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseStorageDao implements IDAO {
	
	private Connection openConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("MySQL driver registered with DriverManager");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3307/bank", "root", "root");
			System.out.println(con);
		} catch (ClassNotFoundException e) {
			System.out.println("MySQL sutiable driver not found");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	private void closeConnection(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Customer> retrieveAllCustomers() {
		List<Customer> Customers = new ArrayList<>();
		Connection con = openConnection();
		
		String customerSql = "SELECT * FROM customer;";
		try {
			Statement customerStat = con.createStatement();
			ResultSet customerRs = customerStat.executeQuery(customerSql);
			
			while(customerRs.next()) {
				int id = customerRs.getInt("id");
				String name = customerRs.getString("name");
				int age = customerRs.getInt("age");
				int mobile = customerRs.getInt("mobile");
				String passno = customerRs.getString("passno");
				int accno = customerRs.getInt("account");
				String dob = customerRs.getString("dob");
				
				String accountSql = "SELECT * FROM account WHERE accno = " + accno + ";";
				Statement accountStat = con.createStatement();
				ResultSet accountRs = accountStat.executeQuery(accountSql);
				Account account = null;
				while(accountRs.next()) {
					int bsb = accountRs.getInt("bsb");
					String bankname = accountRs.getString("bankname");
					double balance = accountRs.getDouble("balance");
					String openingdate = accountRs.getString("openingdate");
					Boolean salary = accountRs.getBoolean("salary");
					if (salary != null) {
						account = new SavingsAccount(accno, bsb, bankname, balance, openingdate, salary);
					} else {
						int tenure = accountRs.getInt("tenure");
						account = new FixedDepositAccount(accno, bsb, bankname, balance, openingdate, tenure);
					}
				}

				
				Customer Customer = new Customer(id, name, age, mobile, passno, dob, account);
				
				Customers.add(Customer);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		closeConnection(con);
		return Customers;
	}

	@Override
	public void saveAllCustomers(List<Customer> customers) throws NoAccountForCustomerException {
		Connection con = openConnection();
		
		String sql;
		sql = "DELETE FROM customer;";
		PreparedStatement pstat;
		int n;
		try {
			pstat = con.prepareStatement(sql);
			n = pstat.executeUpdate();
			sql = "DELETE FROM account;";
			pstat = con.prepareStatement(sql);
			n = pstat.executeUpdate();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		for (Customer customer: customers) {
			int id = customer.getId();
			String name = customer.getName();
			int age = customer.getAge();
			int mobile = customer.getMobile();
			String passno = customer.getPassno();
			String dob = customer.getDob();
			Account account = customer.getAccount();
			if (account == null) {
				throw new NoAccountForCustomerException("Cannot save customer with a bank account");
			}
			int accno = account.getAccno();
			int bsb = account.getBsb();
			String bankname = account.getBankname();
			double balance = account.getBalance();
			String openingdate = account.getOpeningdate();
			Boolean salary = null;
			Integer tenure = null;
			if (account instanceof SavingsAccount) {
				SavingsAccount newAccount = (SavingsAccount)account;
				salary = newAccount.isSalaryAccount();
			} else {
				FixedDepositAccount newAccount = (FixedDepositAccount)account;
				tenure = newAccount.getTenure();
			}
			
			
			
			
			try {
				if (salary != null) {
					sql = "INSERT INTO account (accno, bsb, bankname, balance, openingdate, salary) VALUES (?, ?, ?, ?, ?, ?);";
				} else {
					sql = "INSERT INTO account (accno, bsb, bankname, balance, openingdate, tenure) VALUES (?, ?, ?, ?, ?, ?);";
				}
				pstat = con.prepareStatement(sql);
				pstat.setInt(1,  accno);
				pstat.setInt(2, bsb);
				pstat.setString(3, bankname);
				pstat.setDouble(4,  balance);
				pstat.setString(5, openingdate);
				if (salary != null) {
					pstat.setBoolean(6, salary);
				} else {
					pstat.setInt(6, tenure);
				}
				
				
				
				n = pstat.executeUpdate();
				
	
				sql = "INSERT INTO customer (id, name, age, mobile, passno, account, dob) VALUES (?, ?, ?, ?, ?, ?, ?);";
				pstat = con.prepareStatement(sql);
				pstat.setInt(1,  id);
				pstat.setString(2, name);
				pstat.setInt(3,  age);
				pstat.setInt(4,  mobile);
				pstat.setString(5, passno);
				pstat.setInt(6, accno);
				pstat.setString(7, dob);
				
				n = pstat.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
			
		closeConnection(con);
		
	}

}

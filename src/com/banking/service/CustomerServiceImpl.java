import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {

	private IDAO dao;
	private List<Customer> customers = new ArrayList<>();
	public static int maxid;
	
	public CustomerServiceImpl() {
		dao = new DatabaseStorageDao();
		customers = dao.retrieveAllCustomers();
		maxid = 100;
		for (Customer customer: customers) {
			if (customer.getId() > maxid) {
				maxid = customer.getId();
			}
		}
		Customer.idCount = maxid+1;
	}
	
	@Override
	public void createCustomer(Customer customer) {
		customers.add(customer);

	}

	@Override
	public List<Customer> getAllCustomers() {
		return customers;
	}

	@Override
	public Customer getCustomer(String name) throws CustomerNotFoundException {
		Customer foundCustomer = null;
		for (Customer customer: customers) {
			if (customer.getName().equals(name)) {
				foundCustomer = customer;
			}
		}
		if (foundCustomer == null) {
			throw new CustomerNotFoundException("Customer cannot be found");
		}
		return foundCustomer;
	}
	
	@Override
	public Customer getCustomer(int id) throws CustomerNotFoundException {
		Customer foundCustomer = null;
		for (Customer customer: customers) {
			if (customer.getId() == id) {
				foundCustomer = customer;
			}
		}
		if (foundCustomer == null) {
			throw new CustomerNotFoundException("Customer cannot be found");
		}
		return foundCustomer;
	}

	@Override
	public void addAccounttoCustomer(int id, Account account) {
		for (Customer customer: customers) {
			if (customer.getId() == id) {
				customer.setAccount(account);
			}
		}
	}

	@Override
	public void displayBalanceOrInterest(String name, String choice) {
		Customer customer;
		try {
			customer = getCustomer(name);
			if (choice.equals("b")) {
				System.out.println("Balance: " + customer.getAccount().getBalance());
			} else {
				System.out.println("Interest: " + customer.getAccount().getInterest());
			}
		} catch (CustomerNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

	@Override
	public void sortCustomers(String choice) {
		if (choice.equals("i")) {
			Comparator<Customer> comId = new CustomerIdComparator();
			Collections.sort(customers, comId);
		} else if (choice.equals("n")) {
			Comparator<Customer> comId = new CustomerNameComparator();
			Collections.sort(customers, comId);
		} else {
			Comparator<Customer> comId = new CustomerBalanceComparator();
			Collections.sort(customers, comId);
		}
	}

	
	@Override
	public void saveAll(String choice) throws NoAccountForCustomerException {
		if (choice.equals("f")) {
			dao = new FileStorageDao();
		} else {
			dao = new DatabaseStorageDao();
		}
		dao.saveAllCustomers(customers);
	}
}

class CustomerNameComparator implements Comparator<Customer>{
	@Override
	public int compare(Customer c1, Customer c2) {
		int result = c1.getName().compareTo(c2.getName());
		return result;
	}
}

class CustomerIdComparator implements Comparator<Customer>{
	@Override
	public int compare(Customer c1, Customer c2) {
		if (c1.getId() < c2.getId()) {
			return -1;
		} else if (c1.getId() > c2.getId()) {
			return 1;
		} else {
			int result = c1.getName().compareTo(c2.getName());
			return result;
		}
	}
}

class CustomerBalanceComparator implements Comparator<Customer>{
	@Override
	public int compare(Customer c1, Customer c2) {
		if (c1.getAccount().getBalance() < c2.getAccount().getBalance()) {
			return -1;
		} else if (c1.getAccount().getBalance() > c2.getAccount().getBalance()) {
			return 1;
		} else {
			int result = c1.getName().compareTo(c2.getName());
			return result;
		}
	}
}




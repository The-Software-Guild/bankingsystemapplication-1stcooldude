import java.util.List;

public interface CustomerService {
	
	void saveAll(String choice) throws NoAccountForCustomerException;
	void createCustomer(Customer customer);
	List<Customer> getAllCustomers();
	Customer getCustomer(String name) throws CustomerNotFoundException;
	Customer getCustomer(int id) throws CustomerNotFoundException;
	void displayBalanceOrInterest(String name, String choice);
	void sortCustomers(String choice);
	void addAccounttoCustomer(int id, Account account);
}

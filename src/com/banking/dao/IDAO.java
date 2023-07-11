import java.util.List;

public interface IDAO {
	
	List<Customer> retrieveAllCustomers();
	void saveAllCustomers(List<Customer> customers) throws NoAccountForCustomerException;
}


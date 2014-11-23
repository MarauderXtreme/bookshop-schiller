package bookshop.model;

import org.salespointframework.useraccount.UserAccount;
import org.springframework.data.repository.CrudRepository;

import bookshop.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

	Customer findByUserAccount(UserAccount userAccount);
	
}

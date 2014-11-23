package bookshop.model;

import org.salespointframework.useraccount.UserAccount;
import org.springframework.data.repository.CrudRepository;

// (｡◕‿◕｡)
// Spring biete sehr guten Support um schnell eigene "Managerklassen" wie Catalog oder UserAccountManager zu schreiben
// Pflichtlektüre: http://docs.spring.io/spring-data/jpa/docs/1.4.2.RELEASE/reference/html/repositories.html
// via http://docs.spring.io/spring-data/jpa/docs/1.4.2.RELEASE/reference/html/
public interface UserRepository extends CrudRepository<User, Long> {

	User findByUserAccount(UserAccount userAccount);
}

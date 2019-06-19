package net.series.rest.api.account.repository;

import net.series.rest.api.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    /**
     * Stores an account
     * @param account account body
     */
    Account save(Account account);

    /**
     * Fetches a stored account by id
     * @param id account id
     */
    Account findById(int id);
}

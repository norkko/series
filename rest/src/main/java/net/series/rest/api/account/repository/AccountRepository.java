package net.series.rest.api.account.repository;

import net.series.rest.api.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    /**
     *
     * @param account
     */
    Account save(Account account);

    /**
     *
     * @param id
     */
    Account findById(int id);

    /**
     *
     * @param username
     */
    Account findByUsername(String username);

    /**
     *
     * @param id
     */
    void deleteById(int id);
}

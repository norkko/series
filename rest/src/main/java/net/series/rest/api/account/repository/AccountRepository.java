package net.series.rest.api.account.repository;

import net.series.rest.api.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account save(Account account);
    Account findById(int id);
    Account findByUsername(String username);
    void deleteById(int id);
}

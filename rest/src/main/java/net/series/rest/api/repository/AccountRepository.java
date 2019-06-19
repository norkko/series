package net.series.rest.api.repository;

import net.series.rest.api.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account save(Account account);
    Account findById(int id);
}

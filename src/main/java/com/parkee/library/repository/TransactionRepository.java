package com.parkee.library.repository;

import com.parkee.library.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByMemberIdCardNumber(Long idCardNumber);

}

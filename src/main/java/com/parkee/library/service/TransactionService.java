package com.parkee.library.service;

import com.parkee.library.model.Book;
import com.parkee.library.model.Member;
import com.parkee.library.model.Transaction;
import com.parkee.library.repository.BookRepository;
import com.parkee.library.repository.MemberRepository;
import com.parkee.library.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId);
    }

    public Transaction borrowBook(Long isbnNumber, Long memberIdCardNumber, String returnDate) {

        Book book = bookRepository.findByIsbnNumber(isbnNumber).orElseThrow(() -> new RuntimeException("Book not found!"));
        Member member = memberRepository.findByIdCardNumber(memberIdCardNumber).orElseThrow(() -> new RuntimeException("Member not found!"));
        Optional<Transaction> transactionExist = transactionRepository.findByMemberIdCardNumber(memberIdCardNumber);

        if (transactionExist.isPresent()) {
            throw new RuntimeException("User is currently borrowing a book, transaction not allowed!");
        }

        if (book.getStock() <= 0) throw  new RuntimeException("Book stock is not available!");

        book.setStock(book.getStock() - 1);
        bookRepository.save(book);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate returnLocalDate = LocalDate.parse(returnDate, formatter);
        LocalDate borrowDate = LocalDate.now();
        Period period = Period.between(borrowDate, returnLocalDate);
        if ((period.getYears() > 0) || (period.getMonths() > 1) || (period.getDays() > 30)) {
            throw new RuntimeException("Book not available to be borrowed that long!");
        }

        Transaction transaction = new Transaction();
        transaction.setBook(book);
        transaction.setMember(member);
        transaction.setBorrowDate(borrowDate);
        transaction.setReturnDate(returnLocalDate);
        transaction.setTransactionStatus(Transaction.Status.ACTIVE);

        transactionRepository.save(transaction);

        return transaction;
    }

    public Transaction returnBook(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new RuntimeException("No transaction found!"));

        Book book = transaction.getBook();
        book.setStock(book.getStock() + 1);
        bookRepository.save(book);

        transaction.setTransactionStatus(Transaction.Status.RETURNED);

        transactionRepository.save(transaction);
        return transaction;
    }

}

package com.giantdwarf.demospringsecurity.common;

import com.giantdwarf.demospringsecurity.account.Account;
import com.giantdwarf.demospringsecurity.account.AccountService;
import com.giantdwarf.demospringsecurity.book.Book;
import com.giantdwarf.demospringsecurity.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DefaultDataGenerator implements ApplicationRunner {

    @Autowired
    AccountService accountService;

    @Autowired
    BookRepository bookRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account yang = createUser("yang");
        Account yang2 = createUser("yang2");

        createBook("spring", yang);
        createBook("hibernate", yang2);
    }

    private void createBook(String title,Account account) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(account);
        bookRepository.save(book);
    }

    private Account createUser(String username) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword("123");
        account.setRole("USER");
        accountService.createNew(account);

        return account;
    }
}

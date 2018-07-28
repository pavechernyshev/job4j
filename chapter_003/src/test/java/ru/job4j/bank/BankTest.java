package ru.job4j.bank;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
public class BankTest {
    private Bank bank;

    @Before
    public void setUsers() {
       this.bank = new Bank();
        User pavel = new User("Pavel", "123");
        User ilya = new User("Ilya", "321");
        this.bank.addUser(pavel);
        this.bank.addUser(ilya);
    }

    @Test
    public void whenAddNewAccountToNotAddUserThenNotFoundUserException() {
        String expect = "Пользователь не найден";
        String res = "";
        try {
            this.bank.addAccountToUser("222", new Account("112", 0));
        } catch (NotFoundUserException nfu) {
            res = nfu.getMessage();
        }
        assertThat(expect, is(res));
    }

    @Test
    public void whenDeleteUserAndGetUserAccountsThenNotFoundUserException() {
        this.bank.deleteUser(new User("Ilya", "321"));
        String expect = "Пользователь не найден";
        String res = "";
        try {
            this.bank.getUserAccounts("321");
        } catch (NotFoundUserException nfu) {
            res = nfu.getMessage();
        }
        assertThat(expect, is(res));
    }

    @Test
    public void whenGetUserAccounts() {
        List<Account> expect = new ArrayList<>();
        assertThat(expect, is(this.bank.getUserAccounts("123")));
    }

    @Test
    public void whenAddUserAccounts() {
        this.bank.addAccountToUser("123", new Account("111", 0));
        List<Account> expect = new ArrayList<>();
        expect.add(new Account("111", 0));
        assertThat(expect, is(bank.getUserAccounts("123")));
    }

    @Test
    public void whenDeleteUserAccount() {
        this.bank.addAccountToUser("123", new Account("111", 0));
        this.bank.addAccountToUser("123", new Account("222", 0));
        this.bank.addAccountToUser("123", new Account("333", 0));
        this.bank.deleteAccountFromUser("123", new Account("222", 0));
        List<Account> expect = new ArrayList<>();
        expect.add(new Account("111", 0));
        expect.add(new Account("333", 0));
        assertThat(expect, is(this.bank.getUserAccounts("123")));
    }
    
    @Test
    public void whenTransferNotMoneyEnoughThenFalse() {
        this.bank.addAccountToUser("123", new Account("111", 10));
        this.bank.addAccountToUser("321", new Account("333", 0));
        boolean res = this.bank.transferMoney("123", "111", "321", "333", 200);
        assertThat(false, is(res));
    }

    @Test
    public void whenTransferNotFoundAccountThenFalse() {
        this.bank.addAccountToUser("123", new Account("111", 400));
        this.bank.addAccountToUser("321", new Account("333", 0));
        boolean res = this.bank.transferMoney("123", "111", "321", "222", 200);
        assertThat(false, is(res));
    }

    @Test
    public void whenTransferThenTrue() {
        this.bank.addAccountToUser("123", new Account("111", 1000));
        this.bank.addAccountToUser("321", new Account("333", 0));
        boolean res = this.bank.transferMoney("123", "111", "321", "333", 200);
        assertThat(true, is(res));
    }
}

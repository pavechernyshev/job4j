package ru.job4j.bank;

import java.util.*;

public class Bank {
    private Map<User, List<Account>> bank = new HashMap<>();

    /***
     * добавление пользователя.
     *
     * @param user
     */
    public void addUser(User user) {
        this.bank.putIfAbsent(user, new ArrayList<Account>());
    }

    /***
     * удаление пользователя.
     *
     * @param user
     */
    public void deleteUser(User user) {
        this.bank.remove(user);
    }

    /***
     * добавить счёт пользователю.
     *
     * @param passport
     * @param account
     */
    public void addAccountToUser(String passport, Account account) {
        this.bank.get(gerUser(passport)).add(account);
    }

    /***
     * удалить один счёт пользователя.
     *
     * @param passport
     * @param account
     */
    public void deleteAccountFromUser(String passport, Account account) {
        this.bank.get(gerUser(passport)).remove(account);
    }

    /***
     * получить список счетов для пользователя.
     *
     * @param passport
     * @return
     */
    public List<Account> getUserAccounts(String passport) {
        return this.bank.get(gerUser(passport));
    }

    /***
     * метод для перечисления денег с одного счёта на другой счёт:
     * если счёт не найден или не хватает денег на счёте srcAccount (с которого переводят) должен вернуть false
     *
     * @param srcPassport
     * @param srcRequisite
     * @param destPassport
     * @param dstRequisite
     * @param amount
     * @return
     */
    public boolean transferMoney(String srcPassport, String srcRequisite,
                                 String destPassport, String dstRequisite, double amount) {
        boolean res = false;
        Account srcAccount = getAccount(this.bank.get(gerUser(srcPassport)), srcRequisite);
        Account destAccount = getAccount(this.bank.get(gerUser(destPassport)), dstRequisite);
        if (srcAccount != null && destAccount != null && srcAccount.getValue() > amount) {
            res = true;
        }
        return res;
    }

    private User gerUser(String passport) throws NotFoundUserException {
        User result = null;
        for (User user: this.bank.keySet()) {
            if (user.getPassport().equals(passport)) {
                result = user;
                break;
            }
        }
        if (result == null) {
            throw new NotFoundUserException("Пользователь не найден");
        }
        return result;
    }

    private Account getAccount(List<Account> accounts, String requisite) {
        Account res = null;
        for (Account account: accounts) {
            if (account.getRequisites().equals(requisite)) {
                res = account;
                break;
            }
        }
        return res;
    }

}

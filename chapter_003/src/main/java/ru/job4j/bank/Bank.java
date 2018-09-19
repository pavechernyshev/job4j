package ru.job4j.bank;

import java.util.*;
import java.util.stream.Collectors;

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
        List<User> res = this.bank.keySet().stream().filter(user -> user.getPassport().equals(passport))
                .limit(1).collect(Collectors.toList());
        if (res.isEmpty()) {
            throw new NotFoundUserException("Пользователь не найден");
        }
        return res.get(0);
    }

    private Account getAccount(List<Account> accounts, String requisite) {
        List<Account> res = accounts.stream().filter(a -> a.getRequisites().equals(requisite))
                .limit(1).collect(Collectors.toList());
        return res.isEmpty() ? null : res.get(0);
    }

}

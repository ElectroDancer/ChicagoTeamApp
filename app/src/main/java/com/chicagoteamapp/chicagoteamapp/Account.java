package com.chicagoteamapp.chicagoteamapp;

import java.util.Objects;

public final class Account {
    private String accountName, accountEmail;

    public Account() {
    }

    public Account(String accountName, String accountEmail) {
        this.accountName = accountName;
        this.accountEmail = accountEmail;
    }

    public String getName() {
        return accountName;
    }

    public void setName(String accountName) {
        this.accountName = accountName;
    }

    public String getEmail() {
        return accountEmail;
    }

    public void setEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountName, account.accountName) &&
                Objects.equals(accountEmail, account.accountEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountName, accountEmail);
    }
}

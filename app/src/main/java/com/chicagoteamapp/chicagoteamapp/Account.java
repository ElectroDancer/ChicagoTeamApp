package com.chicagoteamapp.chicagoteamapp;

import android.support.v4.util.ArrayMap;

import java.util.Map;
import java.util.Objects;
import java.util.SortedMap;

/**
 * Created by Makarov on 01.06.2018.
 */

public final class Account {
    private String accountName, accountEmail, accountPassword;

    public Account(String accountName, String accountEmail, String accountPassword) {
        this.accountName = accountName;
        this.accountEmail = accountEmail;
        this.accountPassword = accountPassword;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountName, account.accountName) &&
                Objects.equals(accountEmail, account.accountEmail) &&
                Objects.equals(accountPassword, account.accountPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountName, accountEmail, accountPassword);
    }
}

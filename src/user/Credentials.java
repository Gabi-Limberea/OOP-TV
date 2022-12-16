package user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class Credentials {
    private String name;
    private String password;
    private String accountType;
    private String country;
    private String balance;

    public Credentials() {
        this.name = "";
        this.password = "";
        this.accountType = "";
        this.country = "";
        this.balance = "";
    }

    public Credentials(
            final String name, final String password, final String accountType,
            final String country, final String balance
                      ) {
        this.name = name;
        this.password = password;
        this.accountType = accountType;
        this.country = country;
        this.balance = balance;
    }

    public Credentials(final Credentials source) {
        this.name = source.name;
        this.password = source.password;
        this.accountType = source.accountType;
        this.country = source.country;
        this.balance = source.balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credentials that = (Credentials) o;
        return name.equals(that.name) && password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password);
    }

    @JsonIgnore
    public boolean isPremium() {
        return UserTypes.getUserType(this.accountType) == UserTypes.PREMIUM;
    }
}

package user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public final class Credentials {
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

    /**
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the new name of the user
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the new password of the user
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * @return the account type of the user
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * @param accountType the new account type of the user
     */
    public void setAccountType(final String accountType) {
        this.accountType = accountType;
    }

    /**
     * @return the country of the user
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the new country of the user
     */
    public void setCountry(final String country) {
        this.country = country;
    }

    /**
     * @return the balance of the user
     */
    public String getBalance() {
        return balance;
    }

    /**
     * @param balance the new balance of the user
     */
    public void setBalance(final String balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Credentials that = (Credentials) o;
        return name.equals(that.name) && password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password);
    }

    /**
     * @return true if the user has a premium account, false otherwise
     */
    @JsonIgnore
    public boolean isPremium() {
        return UserTypes.getUserType(this.accountType) == UserTypes.PREMIUM;
    }
}

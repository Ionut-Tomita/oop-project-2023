package app.user;

import app.statistics.Statistics;
import app.user.normalUser.User;
import fileio.input.CommandInput;

import java.util.List;

/**
 * The type User abstract.
 */
public abstract class UserAbstract {
    private String username;
    private int age;
    private String city;

    /**
     * Instantiates a new User abstract.
     *
     * @param username the username
     * @param age      the age
     * @param city     the city
     */
    public UserAbstract(final String username, final int age, final String city) {
        this.username = username;
        this.age = age;
        this.city = city;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Gets age.
     *
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets age.
     *
     * @param age the age
     */
    public void setAge(final int age) {
        this.age = age;
    }

    /**
     * Gets city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets city.
     *
     * @param city the city
     */
    public void setCity(final String city) {
        this.city = city;
    }

    /**
     * User type string.
     *
     * @return the string
     */
    public abstract String userType();

    /**
     * Wrap statistics.
     *
     * @param commandInput the command input
     * @param users        the users
     * @return the statistics
     */
    public abstract Statistics wrap(CommandInput commandInput, List<User> users);

    /**
     * No data message string.
     *
     * @return the string
     */
    public abstract String noDataMessage();
}

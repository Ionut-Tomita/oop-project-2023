package app.user;

import app.pages.Page;
import app.user.normalUser.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Content creator.
 */
public abstract class ContentCreator extends UserAbstract {
    private String description;
    private Page page;
    @Getter
    private List<User> subscribers;

    /**
     * Instantiates a new Content creator.
     *
     * @param username the username
     * @param age      the age
     * @param city     the city
     */
    public ContentCreator(final String username, final int age, final String city) {
        super(username, age, city);
        subscribers = new ArrayList<>();
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Gets page.
     *
     * @return the page
     */
    public Page getPage() {
        return page;
    }

    /**
     * Sets page.
     *
     * @param page the page
     */
    public void setPage(final Page page) {
        this.page = page;
    }

    /**
     * getSubscribers
     * @return subscribers
     */
    public List<User> getSubscribers() {
        return subscribers;
    }

    /**
     * subscribe
     * @param user
     */
    public abstract String subscribeMessage(User user);

    /**
     * unSubscribeMessage
     * @param user
     * @return String
     */
    public abstract String unSubscribeMessage(User user);

}

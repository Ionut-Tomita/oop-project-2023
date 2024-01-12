package app.user.host;

/**
 * The type Announcement.
 */
public class Announcement {
    private String name;
    private String description;

    /**
     * Instantiates a new Announcement.
     *
     * @param name        the name
     * @param description the description
     */
    public Announcement(final String name, final String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(final String name) {
        this.name = name;
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
}

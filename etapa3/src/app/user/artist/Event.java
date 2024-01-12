package app.user.artist;

/**
 * The type Event.
 */
public class Event {
    private String name;
    private String description;
    private String date;

    /**
     * Instantiates a new Event.
     *
     * @param name        the name
     * @param description the description
     * @param date        the date
     */
    public Event(final String name, final String description, final String date) {
        this.name = name;
        this.description = description;
        this.date = date;
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

    /**
     * Gets date.
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(final String date) {
        this.date = date;
    }
}

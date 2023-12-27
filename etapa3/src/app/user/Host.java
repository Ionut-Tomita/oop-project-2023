package app.user;

import app.audio.Collections.Podcast;
import app.pages.HostPage;

import java.util.ArrayList;

/**
 * The type Host.
 */
public final class Host extends ContentCreator {
    private ArrayList<Podcast> podcasts;
    private ArrayList<Announcement> announcements;

    /**
     * Instantiates a new Host.
     *
     * @param username the username
     * @param age      the age
     * @param city     the city
     */
    public Host(final String username, final int age, final String city) {
        super(username, age, city);
        podcasts = new ArrayList<>();
        announcements = new ArrayList<>();

        super.setPage(new HostPage(this));
    }

    /**
     * Gets podcasts.
     *
     * @return the podcasts
     */
    public ArrayList<Podcast> getPodcasts() {
        return podcasts;
    }

    /**
     * Sets podcasts.
     *
     * @param podcasts the podcasts
     */
    public void setPodcasts(final ArrayList<Podcast> podcasts) {
        this.podcasts = podcasts;
    }

    /**
     * Gets announcements.
     *
     * @return the announcements
     */
    public ArrayList<Announcement> getAnnouncements() {
        return announcements;
    }

    /**
     * Sets announcements.
     *
     * @param announcements the announcements
     */
    public void setAnnouncements(final ArrayList<Announcement> announcements) {
        this.announcements = announcements;
    }

    /**
     * Gets podcast.
     *
     * @param podcastName the podcast name
     * @return the podcast
     */
    public Podcast getPodcast(final String podcastName) {
        for (Podcast podcast: podcasts) {
            if (podcast.getName().equals(podcastName)) {
                return podcast;
            }
        }

        return null;
    }

    /**
     * Gets announcement.
     *
     * @param announcementName the announcement name
     * @return the announcement
     */
    public Announcement getAnnouncement(final String announcementName) {
        for (Announcement announcement: announcements) {
            if (announcement.getName().equals(announcementName)) {
                return announcement;
            }
        }

        return null;
    }

    @Override
    public String userType() {
        return "host";
    }

    @Override
    public String subscribeMessage(User user) {
        return "%s subscribed to %s successfully."
                .formatted(user.getUsername(), this.getUsername());
    }

    @Override
    public String unSubscribeMessage(User user) {
        return "%s unsubscribed from %s successfully."
                .formatted(user.getUsername(), this.getUsername());
    }

    public void sendNewPodcastNotification() {
        for (User user : getSubscribers()) {
            user.addNotification("New Podcast", "New Podcast from %s.".formatted(getUsername()));
        }
    }

    public void sendNewAnnouncementNotification() {
        for (User user : getSubscribers()) {
            user.addNotification("New Announcement", "New Announcement from %s.".formatted(getUsername()));
        }
    }
}

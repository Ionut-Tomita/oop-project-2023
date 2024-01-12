package app.user.host;

import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.pages.HostPage;
import app.user.ContentCreator;
import app.user.normalUser.User;
import fileio.input.CommandInput;
import lombok.Getter;
import app.statistics.HostStatistics;
import app.user.normalUser.ListenHistory;
import app.statistics.Statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The type Host.
 */
public final class Host extends ContentCreator {
    private ArrayList<Podcast> podcasts;
    private ArrayList<Announcement> announcements;
    @Getter
    private Statistics statistics;

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
        statistics = new HostStatistics();

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

    /**
     * get userType
     * @return String
     */
    @Override
    public String userType() {
        return "host";
    }

    /**
     * wrap statistics
     * @param commandInput
     * @param users
     * @return Statistics
     */
    @Override
    public Statistics wrap(final CommandInput commandInput, final List<User> users) {
        wrapStatistics(commandInput, users);
        HostOutput hostOutput = new HostOutput((HostStatistics) statistics);
        if (hostOutput.isEmpty(hostOutput)) {
            return null;
        }
        return hostOutput;
    }

    @Override
    public String noDataMessage() {
        return "No data to show for host %s.".formatted(this.getUsername());
    }

    /**
     * subscribe message
     * @param user
     * @return String
     */
    @Override
    public String subscribeMessage(final User user) {
        return "%s subscribed to %s successfully."
                .formatted(user.getUsername(), this.getUsername());
    }

    /**
     * unsubscribe message
     * @param user
     * @return String
     */
    @Override
    public String unSubscribeMessage(final User user) {
        return "%s unsubscribed from %s successfully."
                .formatted(user.getUsername(), this.getUsername());
    }

    /**
     * send new podcast notification
     */
    public void sendNewPodcastNotification() {
        for (User user : getSubscribers()) {
            user.addNotification("New Podcast", "New Podcast from %s.".formatted(getUsername()));
        }
    }

    /**
     * send new announcement notification
     */
    public void sendNewAnnouncementNotification() {
        for (User user : getSubscribers()) {
            user.addNotification("New Announcement", "New Announcement from %s."
                    .formatted(getUsername()));
        }
    }

    /**
     * wrap statistics
     * @param command
     * @param users
     */
    public void wrapStatistics(final CommandInput command, final List<User> users) {
        HostStatistics hostStatistics = (HostStatistics) statistics;
        for (User user : users) {

            ListenHistory listenHistory = user.getListenHistory();
            Map<Podcast, Integer> lastWrapped = user.getLastWrappedPodcast();

            for (Podcast podcast : listenHistory.getListenPodcasts().keySet()) {
                Integer loadTime = listenHistory.getListenPodcasts().get(podcast);

                if (lastWrapped.containsKey(podcast)) {
                    for (Episode episode : podcast.getEpisodes()) {
                        if (loadTime >= lastWrapped.get(podcast)
                                && loadTime + episode.getDuration() <= command.getTimestamp()) {
                            hostStatistics.setTopEpisodes(podcast.getName());
                            hostStatistics.setTopFans(user.getUsername());
                        }
                        loadTime += episode.getDuration();

                    }
                }
            }
        }
    }
}

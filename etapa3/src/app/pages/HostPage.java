package app.pages;

import app.audio.Collections.Podcast;
import app.user.Announcement;
import app.user.ContentCreator;
import app.user.Host;
import app.user.UserAbstract;

import java.util.List;

/**
 * The type Host page.
 */
public final class HostPage implements Page {
    private Host owner;
    private List<Podcast> podcasts;
    private List<Announcement> announcements;

    /**
     * Instantiates a new Host page.
     *
     * @param host the host
     */
    public HostPage(final Host host) {
        owner = host;
        podcasts = host.getPodcasts();
        announcements = host.getAnnouncements();
    }

    @Override
    public String printCurrentPage() {
        return "Podcasts:\n\t%s\n\nAnnouncements:\n\t%s"
               .formatted(podcasts.stream().map(podcast -> "%s:\n\t%s\n"
                          .formatted(podcast.getName(),
                                     podcast.getEpisodes().stream().map(episode -> "%s - %s"
                          .formatted(episode.getName(), episode.getDescription())).toList()))
                          .toList(),
                          announcements.stream().map(announcement -> "%s:\n\t%s\n"
                          .formatted(announcement.getName(), announcement.getDescription()))
                          .toList());
    }

    @Override
    public UserAbstract getOwner() {
        return owner;
    }
}

package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import app.utils.Enums;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Playlist.
 */
@Getter @Setter
public final class Playlist extends AudioCollection {
    private List<Song> songs;
    private Enums.Visibility visibility;
    private Integer followers;
    private int timestamp;


    /**
     * Instantiates a new Playlist.
     *
     * @param name      the name
     * @param owner     the owner
     */
    private Playlist(final String name, final String owner) {
        super(name, owner);
        this.songs = new ArrayList<>();
        this.visibility = Enums.Visibility.PUBLIC;
        this.followers = 0;
    }
    public Playlist() {

    }

    public static class Builder {
        private String name;
        private String owner;
        private List<Song> songs = new ArrayList<>();
        private int timestamp;
        private Enums.Visibility visibility = Enums.Visibility.PUBLIC;
        private Integer followers = 0;

        public Builder(final String name, final String owner) {
            this.name = name;
            this.owner = owner;
        }

        /**
         * Songs builder.
         * @param newsSongs
         * @return the builder
         */
        public Builder songs(final List<Song> newsSongs) {
            this.songs = newsSongs;
            return this;
        }

        /**
         * Timestamp builder.
         * @param newTimestamp
         * @return
         */
        public Builder timestamp(final int newTimestamp) {
            this.timestamp = newTimestamp;
            return this;
        }

        /**
         * Visibility builder.
         * @param newVisibility
         * @return
         */
        public Builder visibility(final Enums.Visibility newVisibility) {
            this.visibility = newVisibility;
            return this;
        }

        /**
         * Followers builder.
         * @param newFollowers
         * @return
         */
        public Builder followers(final Integer newFollowers) {
            this.followers = newFollowers;
            return this;
        }

        /**
         * Build playlist.
         * @return the playlist
         */
        public Playlist build() {
            return new Playlist(name, owner);
        }
    }


    /**
     * Contains song boolean.
     *
     * @param song the song
     * @return the boolean
     */
    public boolean containsSong(final Song song) {
        return songs.contains(song);
    }

    /**
     * Add song.
     *
     * @param song the song
     */
    public void addSong(final Song song) {
        songs.add(song);
    }

    /**
     * Remove song.
     *
     * @param song the song
     */
    public void removeSong(final Song song) {
        songs.remove(song);
    }

    /**
     * Remove song.
     *
     * @param index the index
     */
    public void removeSong(final int index) {
        songs.remove(index);
    }

    /**
     * Switch visibility.
     */
    public void switchVisibility() {
        if (visibility == Enums.Visibility.PUBLIC) {
            visibility = Enums.Visibility.PRIVATE;
        } else {
            visibility = Enums.Visibility.PUBLIC;
        }
    }

    /**
     * Increase followers.
     */
    public void increaseFollowers() {
        followers++;
    }

    /**
     * Decrease followers.
     */
    public void decreaseFollowers() {
        followers--;
    }

    @Override
    public int getNumberOfTracks() {
        return songs.size();
    }

    @Override
    public AudioFile getTrackByIndex(final int index) {
        return songs.get(index);
    }

    @Override
    public boolean isVisibleToUser(final String user) {
        return this.getVisibility() == Enums.Visibility.PUBLIC
               || (this.getVisibility() == Enums.Visibility.PRIVATE
                   && this.getOwner().equals(user));
    }

    @Override
    public boolean matchesFollowers(final String followerNum) {
        return filterByFollowersCount(this.getFollowers(), followerNum);
    }

    private static boolean filterByFollowersCount(final int count, final String query) {
        if (query.startsWith("<")) {
            return count < Integer.parseInt(query.substring(1));
        } else if (query.startsWith(">")) {
            return count > Integer.parseInt(query.substring(1));
        } else {
            return count == Integer.parseInt(query);
        }
    }

    @Override
    public boolean containsTrack(final AudioFile track) {
        return songs.contains(track);
    }

    /**
     * Is empty
     * @return the boolean
     */
    public boolean isEmpty() {
        return songs.isEmpty();
    }
}

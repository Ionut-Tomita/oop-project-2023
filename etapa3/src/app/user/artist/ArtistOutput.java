package app.user.artist;

import app.statistics.ArtistStatistics;
import app.statistics.Statistics;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter @Setter
public class ArtistOutput implements Statistics {

    private Map<String, Integer> topAlbums;
    private Map<String, Integer> topSongs;
    private List<String> topFans;
    private Integer listeners;

    public ArtistOutput(final ArtistStatistics artistStatistics) {
        this.topAlbums = artistStatistics.getTop5AlbumsByListens();
        this.topSongs = artistStatistics.getTop5SongsByListens();
        this.topFans = artistStatistics.getTop5FansByListens().keySet().stream().toList();
        this.listeners = artistStatistics.getTopFans().size();
    }

    @Override
    public void updateStatistics() {
    }

    /**
     * @param artistOutput
     * @return true if the artistOutput is empty
     */
    public boolean isEmpty(final ArtistOutput artistOutput) {
        return artistOutput.getTopAlbums().isEmpty()
                && artistOutput.getTopSongs().isEmpty()
                && artistOutput.getTopFans().isEmpty()
                && artistOutput.getListeners() == 0;
    }
}

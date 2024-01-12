package app.user.host;

import app.statistics.HostStatistics;
import app.statistics.Statistics;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter @Setter
public final class HostOutput implements Statistics {
    private Map<String, Integer> topEpisodes;
    private Integer listeners;

    public HostOutput(final HostStatistics hostStatistics) {
        this.topEpisodes = hostStatistics.getTop5EpisodesByListens();
        this.listeners = hostStatistics.getTopFans().size();
    }

    @Override
    public void updateStatistics() {

    }

    /**
     * Checks if the hostOutput is empty.
     * @param hostOutput the hostOutput to check
     * @return true if the hostOutput is empty, false otherwise
     */
    public boolean isEmpty(final HostOutput hostOutput) {
        return hostOutput.getTopEpisodes().isEmpty()
                && hostOutput.getListeners() == 0;
    }
}

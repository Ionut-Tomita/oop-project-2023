package main;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter @Setter
public class HostOutput implements Statistics{
    private Map<String, Integer> topEpisodes;
    private Integer listeners;

    public HostOutput(HostStatistics hostStatistics) {
        this.topEpisodes = hostStatistics.getTop5EpisodesByListens();
        this.listeners = hostStatistics.getTopFans().size();
    }

    @Override
    public void updateStatistics() {

    }

    public boolean isEmpty(HostOutput hostOutput) {
        return hostOutput.getTopEpisodes().isEmpty() &&
                hostOutput.getListeners() == 0;
    }
}

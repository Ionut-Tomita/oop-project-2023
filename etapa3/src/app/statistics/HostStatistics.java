package app.statistics;

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public final class HostStatistics implements Statistics {
    private Map<String, Integer> topEpisodes;
    private Map<String, Integer> topFans;

    public static final int MAX_SIZE = 5;

    public HostStatistics() {
        topEpisodes = new LinkedHashMap<>();
        topFans = new LinkedHashMap<>();
    }

    /**
     * Adauga un episod in topul episoadelor
     * @param episode
     */
    public void setTopEpisodes(final String episode) {
        if (topEpisodes.containsKey(episode)) {
            topEpisodes.put(episode, topEpisodes.get(episode) + 1);
        } else {
            topEpisodes.put(episode, 1);
        }
    }

    /**
     * Adauga un user in topul fanilor
     * @param user
     */
    public void setTopFans(final String user) {
        if (topFans.containsKey(user)) {
            topFans.put(user, topFans.get(user) + 1);
        } else {
            topFans.put(user, 1);
        }
    }


    /**
     * Returneaza top 5 Episoade dupa numarul de ascultari
     * @return Map-ul cu top 5 Episoade si numarul de ascultari
     */
    public Map<String, Integer> getTop5EpisodesByListens() {
        Map<String, Integer> top5Songs = new LinkedHashMap<>();

        // Sortează cântecele după numărul de ascultări și le limitează la primele 5
        topEpisodes.entrySet().stream()
                .sorted((entry1, entry2) -> {
                    // Sortează descrescător după numărul de ascultări
                    int compare = entry2.getValue().compareTo(entry1.getValue());
                    if (compare != 0) {
                        return compare;
                    }
                    // În cazul în care numărul de ascultări este același, sortează alfabetic
                    return entry1.getKey().compareTo(entry2.getKey());
                })
                .limit(MAX_SIZE)
                .forEachOrdered(entry -> top5Songs.put(entry.getKey(), entry.getValue()));

        return top5Songs;
    }

    @Override
    public void updateStatistics() {

    }
}

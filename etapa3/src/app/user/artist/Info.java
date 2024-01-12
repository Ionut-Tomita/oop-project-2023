package app.user.artist;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Info {
    private Double merchRevenue;
    private Double songRevenue;
    private Integer ranking;
    private String mostProfitableSong;

    public Info() {
        merchRevenue = 0.0;
        songRevenue = 0.0;
        ranking = 1;
        mostProfitableSong = "N/A";
    }

    public Info(final double merchRevenue, final double songRevenue) {
        this.merchRevenue = merchRevenue;
        this.songRevenue = songRevenue;
        ranking = 1;
        mostProfitableSong = "N/A";
    }
}

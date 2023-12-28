package main;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Date {
    private Double merchRevenue;
    private Double songRevenue;
    private Integer ranking;
    private String mostProfitableSong;

    public Date() {
        merchRevenue = 0.0;
        songRevenue = 0.0;
        ranking = 1;
        mostProfitableSong = "N/A";
    }

}

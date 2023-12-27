package main;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Notifications {
    private String name;
    private String description;

    public Notifications(String name, String description) {
        this.name = name;
        this.description = description;
    }


}

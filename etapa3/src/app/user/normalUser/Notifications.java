package app.user.normalUser;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Notifications {
    private String name;
    private String description;

    public Notifications(final String name, final String description) {
        this.name = name;
        this.description = description;
    }

}

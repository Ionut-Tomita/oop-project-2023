package app.audio.Files;

import lombok.Getter;

@Getter
public final class Episode extends AudioFile {
    private final String description;
    private final String host;

    public Episode(final String name, final Integer duration, final String description, String host) {
        super(name, duration);
        this.description = description;
        this.host = host;
    }
}

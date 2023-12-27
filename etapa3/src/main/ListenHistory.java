package main;

import app.audio.Collections.Album;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ListenHistory {
    private Map<Album, Integer> listenAlbums = new HashMap<>();

    public void addToListenHistory(Album album, Integer loadTime) {
        listenAlbums.put(album, loadTime);
    }

}

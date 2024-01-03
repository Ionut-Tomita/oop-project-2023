package main;

import app.Admin;
import app.CommandRunner;
import app.searchBar.SearchBar;
import checker.Checker;
import checker.CheckerConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.CommandInput;
import fileio.input.LibraryInput;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.getName().startsWith("library")) {
                continue;
            }

            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        LibraryInput library = objectMapper.readValue(new File(CheckerConstants.TESTS_PATH
                        + "library/library.json"),
                LibraryInput.class);
        CommandInput[] commands = objectMapper.readValue(new File(CheckerConstants.TESTS_PATH
                        + filePath1),
                CommandInput[].class);
        ArrayNode outputs = objectMapper.createArrayNode();

        Admin admin = Admin.getInstance();
        SearchBar.updateAdmin();
        admin.setUsers(library.getUsers());
        admin.setSongs(library.getSongs());
        admin.setPodcasts(library.getPodcasts());
        CommandRunner.updateAdmin();



        for (CommandInput command : commands) {
            admin.updateTimestamp(command.getTimestamp());

            String commandName = command.getCommand();

            switch (commandName) {
                case "search" -> outputs.add(CommandRunner.search(command));
                case "select" -> outputs.add(CommandRunner.select(command));
                case "load" -> outputs.add(CommandRunner.load(command));
                case "playPause" -> outputs.add(CommandRunner.playPause(command));
                case "repeat" -> outputs.add(CommandRunner.repeat(command));
                case "shuffle" -> outputs.add(CommandRunner.shuffle(command));
                case "forward" -> outputs.add(CommandRunner.forward(command));
                case "backward" -> outputs.add(CommandRunner.backward(command));
                case "like" -> outputs.add(CommandRunner.like(command));
                case "next" -> outputs.add(CommandRunner.next(command));
                case "prev" -> outputs.add(CommandRunner.prev(command));
                case "createPlaylist" -> outputs.add(CommandRunner.createPlaylist(command));
                case "addRemoveInPlaylist" -> outputs.add(CommandRunner
                        .addRemoveInPlaylist(command));
                case "switchVisibility" -> outputs.add(CommandRunner.switchVisibility(command));
                case "showPlaylists" -> outputs.add(CommandRunner.showPlaylists(command));
                case "follow" -> outputs.add(CommandRunner.follow(command));
                case "status" -> outputs.add(CommandRunner.status(command));
                case "showPreferredSongs" -> outputs.add(CommandRunner.showLikedSongs(command));
                case "getPreferredGenre" -> outputs.add(CommandRunner.getPreferredGenre(command));
                case "getTop5Songs" -> outputs.add(CommandRunner.getTop5Songs(command));
                case "getTop5Playlists" -> outputs.add(CommandRunner.getTop5Playlists(command));
                case "switchConnectionStatus" -> outputs.add(CommandRunner
                        .switchConnectionStatus(command));
                case "addUser" -> outputs.add(CommandRunner.addUser(command));
                case "deleteUser" -> outputs.add(CommandRunner.deleteUser(command));
                case "addPodcast" -> outputs.add(CommandRunner.addPodcast(command));
                case "removePodcast" -> outputs.add(CommandRunner.removePodcast(command));
                case "addAnnouncement" -> outputs.add(CommandRunner.addAnnouncement(command));
                case "removeAnnouncement" -> outputs.add(CommandRunner
                        .removeAnnouncement(command));
                case "addAlbum" -> outputs.add(CommandRunner.addAlbum(command));
                case "removeAlbum" -> outputs.add(CommandRunner.removeAlbum(command));
                case "addEvent" -> outputs.add(CommandRunner.addEvent(command));
                case "removeEvent" -> outputs.add(CommandRunner.removeEvent(command));
                case "addMerch" -> outputs.add(CommandRunner.addMerch(command));
                case "changePage" -> outputs.add(CommandRunner.changePage(command));
                case "printCurrentPage" -> outputs.add(CommandRunner.printCurrentPage(command));
                case "getTop5Albums" -> outputs.add(CommandRunner.getTop5AlbumList(command));
                case "getTop5Artists" -> outputs.add(CommandRunner.getTop5ArtistList(command));
                case "getAllUsers" -> outputs.add(CommandRunner.getAllUsers(command));
                case "getOnlineUsers" -> outputs.add(CommandRunner.getOnlineUsers(command));
                case "showAlbums" -> outputs.add(CommandRunner.showAlbums(command));
                case "showPodcasts" -> outputs.add(CommandRunner.showPodcasts(command));
                // de aici am inceput etapa 3
                case "wrapped" -> outputs.add(CommandRunner.wrapped(command));
                case "subscribe" -> outputs.add(CommandRunner.subscribe(command));
                case "getNotifications" -> outputs.add(CommandRunner.getNotifications(command));
                case "buyMerch" -> outputs.add(CommandRunner.buyMerch(command));
                case "seeMerch" -> outputs.add(CommandRunner.seeMerch(command));
                case "buyPremium" -> outputs.add(CommandRunner.buyPremium(command));
                case "cancelPremium" -> outputs.add(CommandRunner.cancelPremium(command));
                case "adBreak" -> outputs.add(CommandRunner.adBreak(command));
                case "updateRecommendations" -> outputs.add(CommandRunner
                        .updateRecommendation(command));
                case "previousPage" -> outputs.add(CommandRunner.previousPage(command));
                case "nextPage" -> outputs.add(CommandRunner.nextPage(command));
                case "loadRecommendations" -> outputs.add(CommandRunner.loadRecommendations(command));
                default -> System.out.println("Invalid command " + commandName);
            }
        }
        outputs.add(CommandRunner.endProgram());

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePath2), outputs);

        Admin.resetInstance();
    }
}

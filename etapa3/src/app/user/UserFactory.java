package app.user;

import app.user.artist.Artist;
import app.user.host.Host;
import app.user.normalUser.User;
import fileio.input.CommandInput;

public final class UserFactory {

    private UserFactory() {

    }
    /**
     * get user
     * @param commandInput
     * @return Abstract user
     */
    public static UserAbstract getUser(final CommandInput commandInput) {
        if (commandInput == null) {
            return null;
        }
        String userType = commandInput.getType();

        return switch (userType) {
            case "artist" -> new Artist(commandInput.getUsername(), commandInput.getAge(),
                    commandInput.getCity());
            case "host" -> new Host(commandInput.getUsername(), commandInput.getAge(),
                    commandInput.getCity());
            default -> new User(commandInput.getUsername(), commandInput.getAge(),
                    commandInput.getCity());
        };
    }
}

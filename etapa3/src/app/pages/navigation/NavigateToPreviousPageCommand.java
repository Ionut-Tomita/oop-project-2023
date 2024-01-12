package app.pages.navigation;

import app.user.normalUser.User;

public final class NavigateToPreviousPageCommand implements NavigationCommand {

    private User user;

    public NavigateToPreviousPageCommand(final User user) {
        this.user = user;
    }

    @Override
    public String execute() {
        return user.navigateToPreviousPage();
    }
}

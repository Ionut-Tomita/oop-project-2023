package app.pages.navigation;

import app.user.normalUser.User;

public final class NavigateToNextPageCommand implements NavigationCommand {
    private User user;

    public NavigateToNextPageCommand(final User user) {
        this.user = user;
    }

    @Override
    public String execute() {
        return user.navigateToNextPage();
    }
}

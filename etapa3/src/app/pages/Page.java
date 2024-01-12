package app.pages;

import app.user.UserAbstract;

/**
 * The interface Page.
 */
public interface Page {

    /**
     * Print current page string.
     *
     * @return the current page string
     */
    String printCurrentPage();

    /**
     * Gets owner.
     *
     * @return the owner
     */
    UserAbstract getOwner();
}

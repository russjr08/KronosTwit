
package com.kronosad.projects.twitter.kronostwit.user;

/**
 *
 * @author Russell
 */
public class KronosUser {
    
    private String userName;
    private boolean betaUser;
    private String tag;
    private boolean banned;
    
    /**
     * 
     * @param userName - Username of user
     * @param betaUser - Enrolled in KronosTwit Beta Testing Program?
     * @param tag - A small description of user
     * @param banned - Is Banned from KronosTwit?
     */
    public KronosUser(String userName, boolean betaUser, String tag, boolean banned) {
        this.userName = userName;
        this.betaUser = betaUser;
        this.tag = tag;
        this.banned = banned;
    }
    
    public String getUserName() {
        return userName;
    }

    public boolean isBetaUser() {
        return betaUser;
    }

    public String getTag() {
        return tag;
    }

    public boolean isBanned() {
        return banned;
    }
    
    
    
    
}

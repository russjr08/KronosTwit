
package com.kronosad.projects.twitter.kronostwit.user;

import java.util.ArrayList;

/**
 *
 * @author Russell
 */
public class UserRegistry {
    
    private static ArrayList<KronosUser> users = new ArrayList<KronosUser>();
    
    /**
     * 
     * @param username - Username of user
     * @return An instance of {@link KronosUser}.
     */
    public static KronosUser getKronosUser(String username){
        for(KronosUser user : users){
            if(user.getUserName().equalsIgnoreCase(username)){
                return user;
            }
        }
        
        return new KronosUser(username, false, null, false);
        
    }
    
    /**
     * 
     * @param user - An instance of {@link KronosUser} 
     */
    public static void addUser(KronosUser user){
        users.add(user);
    }
    
    
    
}

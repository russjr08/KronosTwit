package com.kronosad.projects.twitter.kronostwit.interfaces;

import net.xeoh.plugins.base.Plugin;

/**
 * User: russjr08
 * Date: 12/27/13
 * Time: 9:57 PM
 */

/**
 * All KronosTwit plugins must implement this.
 */
public interface IKronosPlugin extends Plugin{

    /**
     * Called after all plugin checks have passed. Run your plugins code here.
     */
    public void run();

    /**
     * Should return the name of the plugin.
     * @return Name of the plugin.
     */
    public String getName();

    /**
     * Version of the plugin.
     * @return Version of plugin
     */
    public Double getVersion();

    /**
     * Does this plugin depend on a specific version of KronosTwit?
     * @return Whether the plugin depends on a specific version of KronosTwit
     */
    public boolean dependsOnSpecificVersion();

    /**
     * If the plugin requires a specific version of KronosTwit, return the version here. If it does not, then
     * feel free to return null.
     * @return The version of KronosTwit this plugin depends on.
     * @see #dependsOnSpecificVersion()
     */
    public Double getKronosTwitVersion();

    /**
     * Should this plugin be loaded before KronosTwit has finished loading? If your plugin includes preference dependant
     * things, then this should return TRUE (Such as Themes), otherwise, feel free to return false. The best way to test
     * this is trial and error.
     * @return Whether your plugin needs to be loaded before runtime of KronosTwit.
     */
    public boolean loadBeforeApp();

}

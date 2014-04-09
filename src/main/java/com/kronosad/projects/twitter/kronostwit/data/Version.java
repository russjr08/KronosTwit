package com.kronosad.projects.twitter.kronostwit.data;

import com.kronosad.projects.twitter.kronostwit.enums.ReleaseType;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * User: russjr08
 * Date: 12/27/13
 * Time: 4:44 PM
 */

/**
 * This class represents the Version from the new versioning system.
 */
public class Version {

    private Double version;
    private String downloadURL;
    private ReleaseType releaseType;
    private boolean resetLogin = false;
    private String changelog;

    public Version(Double version, String downloadURL, String releaseType, boolean resetLogin, String changelog) {
        this.version = version;
        this.downloadURL = downloadURL;
        this.releaseType = ReleaseType.valueOf(releaseType);
        this.resetLogin = resetLogin;
        this.changelog = changelog;
    }

    public Double getVersionNumber() {
        return version;
    }

    public URL getDownloadURL() {
        try {
            return new URL(downloadURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ReleaseType getReleaseType() {
        return releaseType;
    }


    public boolean needsResetLogin() {
        return resetLogin;
    }

    public URL getChangeLog(){
        try {
            return new URL(changelog);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

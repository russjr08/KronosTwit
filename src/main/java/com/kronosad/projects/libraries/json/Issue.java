package com.kronosad.projects.libraries.json;

/**
 * User: russjr08
 * Date: 10/26/13
 * Time: 7:53 PM
 */
public class Issue {

    private int id;
    private int iid;
    private int project_id;
    private String title;
    private String description;
    private String[] labels;
//    private String milestone;
    //    private ArrayList<User> assignee;
//    private ArrayList<User> author;
    private String state;
    private String updated_at;
    private String created_at;

    public int getId() {
        return id;
    }

    public int getIid() {
        return iid;
    }

    public int getProject_id() {
        return project_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String[] getLabels() {
        return labels;
    }

//    public String getMilestone() {
//        return milestone;
//    }

//    public ArrayList<User> getAssignee() {
//        return assignee;
//    }
//
//    public ArrayList<User> getAuthor() {
//        return author;
//    }

    public String getState() {
        return state;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }
}

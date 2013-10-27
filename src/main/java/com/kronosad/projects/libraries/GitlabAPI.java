package com.kronosad.projects.libraries;

import com.google.gson.Gson;
import com.kronosad.projects.libraries.json.Issue;
import com.kronosad.projects.libraries.json.IssueList;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * User: russjr08
 * Date: 10/26/13
 * Time: 7:41 PM
 */
public class GitlabAPI {

    /**
     * Get issues from a project
     *
     * @param id The ID of the project
     * @return A list of issues
     * @throws IOException In case there was a problem connecting to Gitlab
     */
    public static IssueList getProjectIssues(int id) throws IOException {

        URL url;
        Gson gson = new Gson();


        HttpURLConnection connection;

        url = new URL(String.format("http://git.tristen.co/api/v3/projects/%s/issues", id));

        connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");


        connection.setDoInput(true);
        connection.setDoOutput(true);

        connection.addRequestProperty("PRIVATE-TOKEN", "FUtg6FGqQf9co9rvn8Vw");

        InputStream is = connection.getInputStream();

        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuffer response = new StringBuffer();

        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }

        rd.close();


        IssueList issues = gson.fromJson("{ issues: " + response.toString() + " }", IssueList.class);

        connection.disconnect();


        return issues;
    }

    /**
     * @param projectID   Project ID of Project
     * @param title       Title of issue
     * @param description Description of Issue
     * @param labels      Tags
     * @throws IOException If connection to gitlab was unsuccessful
     */

    public static void createIssue(int projectID, String title, String description, String... labels) throws IOException {
        URL url = new URL(String.format("http://git.tristen.co/api/v3/projects/%s/issues", projectID));

        Gson gson = new Gson();

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");

        connection.setDoOutput(true);

        IssueList issues = getProjectIssues(projectID);

        int issueID = 0;

        for (Issue issue : issues.getIssues()) {
            if (issueID < issue.getId()) {
                issueID++;
            }
        }

        connection.setRequestProperty("PRIVATE-TOKEN", "FUtg6FGqQf9co9rvn8Vw");
//        connection.setRequestProperty("id", String.valueOf(issueID));
//        connection.setRequestProperty("title", title);
//        connection.setRequestProperty("description", description);

        DataOutputStream os = new DataOutputStream(connection.getOutputStream());


        StringBuilder builder = new StringBuilder();

        for (String tag : labels) {
            builder.append(tag + ", ");
        }

        System.out.println("Labels: " + builder.toString());


        os.writeBytes("id=" + issueID + "&title=" + title + "&description=" + description + "&labels=" + builder.toString());
        os.flush();
        os.close();


//        InputStream is = connection.getInputStream();
//
//        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
//        String line;
//        StringBuffer response = new StringBuffer();
//
//        while((line = rd.readLine()) != null) {
//            response.append(line);
//            response.append('\r');
//        }
//
//        rd.close();
//
//        System.out.println(response);


        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());

        connection.disconnect();


    }
    
    public static void createIssue(int projectID, String title, String description, String labels) throws IOException {
        URL url = new URL(String.format("http://git.tristen.co/api/v3/projects/%s/issues", projectID));

        Gson gson = new Gson();

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");

        connection.setDoOutput(true);

        IssueList issues = getProjectIssues(projectID);

        int issueID = 0;

        for (Issue issue : issues.getIssues()) {
            if (issueID < issue.getId()) {
                issueID++;
            }
        }

        connection.setRequestProperty("PRIVATE-TOKEN", "FUtg6FGqQf9co9rvn8Vw");
//        connection.setRequestProperty("id", String.valueOf(issueID));
//        connection.setRequestProperty("title", title);
//        connection.setRequestProperty("description", description);

        DataOutputStream os = new DataOutputStream(connection.getOutputStream());



        

        os.writeBytes("id=" + issueID + "&title=" + title + "&description=" + description + "&labels=" + labels);
        os.flush();
        os.close();


//        InputStream is = connection.getInputStream();
//
//        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
//        String line;
//        StringBuffer response = new StringBuffer();
//
//        while((line = rd.readLine()) != null) {
//            response.append(line);
//            response.append('\r');
//        }
//
//        rd.close();
//
//        System.out.println(response);


        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());

        connection.disconnect();


    }


    public static void main(String... args) throws IOException {

//        IssueList issues = getProjectIssues(2);
//
//        System.out.println("-----START ISSUE OUTPUT-----");
//        for (Issue issue : issues.getIssues()) {
//            System.out.println("--------------------------------");
//            System.out.println("Issue ID: " + issue.getId());
//            System.out.println("Issue Tile: " + issue.getTitle());
//            System.out.println("Issue Description: " + issue.getDescription());
//            System.out.println("Issue State: " + issue.getState());
//            System.out.println("--------------------------------");
//
//        }
//        System.out.println("-----END ISSUE OUTPUT-----");

        System.out.println("Creating issue...");
        createIssue(10, "Test", "Testing!", "Test 1", "Test 2", "Test 3", "Test 4");
        System.out.println("ISSUE CREATED!!!!");


    }


}

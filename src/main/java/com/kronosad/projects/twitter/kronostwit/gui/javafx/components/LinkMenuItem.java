package com.kronosad.projects.twitter.kronostwit.gui.javafx.components;

import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;



/**
 * Author russjr08
 * Created at 3/29/14
 */
public class LinkMenuItem extends MenuItem {
    private LinkType linkType;
    private String link;

    public enum LinkType {
        NORMAL, REDDIT, PICTURE, HASHTAG
    }

    public LinkMenuItem(String link, LinkType type){
        linkType = type;
        this.link = link;
        if(linkType == LinkType.NORMAL){
            this.setText("Navigate To: " + link);
        }else if(linkType == LinkType.PICTURE){
            this.setText("View Image: " + link);
        }else if(linkType == LinkType.REDDIT){
            this.setText("Visit Subreddit: " + link);
        }
        this.setOnAction((e) -> {
            if(linkType == LinkType.NORMAL) {
                if (!Desktop.isDesktopSupported()) return;
                try {
                    Desktop.getDesktop().browse(new URL(link).toURI());
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }else if(linkType == LinkType.PICTURE){
                Stage stage = new Stage();
                AnchorPane pane = new AnchorPane();
                pane.getChildren().add(new ImageView(new javafx.scene.image.Image(link)));
                System.out.println(link);
                stage.setScene(new Scene(pane));
                stage.setTitle("Viewing Image: " + link);
                stage.show();
                stage.sizeToScene();

            }else if(linkType == LinkType.REDDIT){
                if (!Desktop.isDesktopSupported()) return;
                try {
                    Desktop.getDesktop().browse(new URL("http://www.reddit.com" + link).toURI());
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public String getLink(){ return link; }

    public LinkType getLinkType(){ return linkType; }



}

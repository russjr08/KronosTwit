package com.kronosad.projects.twitter.kronostwit.gui.javafx.render;

import com.kronosad.projects.twitter.kronostwit.gui.helpers.TweetFormat;
import com.kronosad.projects.twitter.kronostwit.gui.javafx.TwitterContainer;
import javafx.scene.control.ListCell;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import twitter4j.Status;

/**
 * Author russjr08
 * Created at 3/22/14
 */
public class TweetListCellRender extends ListCell<Status> {

    @Override
    protected void updateItem(Status s, boolean b) {
        super.updateItem(s, b);

        if(s == null) return;


        Image profileImg;
        if(TwitterContainer.imgCache.containsKey(s.getUser().getScreenName())){
            profileImg = TwitterContainer.imgCache.get(s.getUser().getScreenName());
        }else{
            profileImg = new Image(s.getUser().getBiggerProfileImageURL());
            TwitterContainer.imgCache.put(s.getUser().getScreenName(), profileImg);
        }
        ImageView imgView = new ImageView(profileImg);

        imgView.setFitWidth(48);
        imgView.setFitHeight(48);
        imgView.setPreserveRatio(true);
        imgView.setSmooth(true);
        imgView.setCache(true);
        setGraphic(imgView);
        setText(TweetFormat.formatTweet(s));

        setCache(true);
        setWrapText(true);

        DropShadow ds = new DropShadow();
        ds.setOffsetY(1.5f);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));

        if(this.getEffect() == null){
            this.setEffect(ds);
        }

        this.setOnMouseEntered((e) -> setFont(Font.font("System Regular", FontWeight.BOLD, 13)));

        this.setOnMouseExited((e) -> setFont(Font.font("System Regular", FontWeight.NORMAL, 13)));

    }


}

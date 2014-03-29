package com.kronosad.projects.twitter.kronostwit.gui.javafx;

import com.kronosad.projects.twitter.kronostwit.gui.javafx.helpers.logging.OverridePrintStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Author russjr08
 * Created at 3/26/14
 */
public class WindowConsole implements Initializable {

    @FXML TextArea textArea;
    @FXML TextField commandArea;

    private static WindowConsole instance;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.setOut(new OverridePrintStream(System.out, this));
        System.setErr(new OverridePrintStream(System.out, this));
        instance = this;
    }

    public void appendObjectToConsole(Object x) {
        Platform.runLater(() -> textArea.appendText("[KronosTwit]: " + x.toString() + "\n"));
    }

    public void appendToConsole(String x) {
        Platform.runLater(() -> {
            if(!x.equalsIgnoreCase("\n")){
                textArea.appendText("[KronosTwit]: " + x + "\n");
            }
        });


    }

    public static WindowConsole getInstance(){ return instance; }
}

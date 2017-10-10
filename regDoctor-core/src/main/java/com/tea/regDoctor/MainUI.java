/**
 * @author teaho2015@gmail.com
 * since 2017/3/28
 */
package com.tea.regDoctor;

import com.tea.regDoctor.config.Config;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Locale;

public class MainUI extends Application {

    protected Stage stage;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
        Locale.setDefault(Locale.SIMPLIFIED_CHINESE);
//        init config file
        Config.getInstance();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/ui/mainui.fxml"));
        stage.setTitle("挂号助手");
        stage.setScene(new Scene(root, 300, 275));
        stage.setMaximized(true);
        stage.show();
    }
}
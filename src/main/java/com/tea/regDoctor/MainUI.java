/**
 * @author teaho2015@gmail.com
 * since 2017/3/28
 */
package com.tea.regDoctor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Parameter;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainUI extends Application {

    protected Stage stage;
    protected DatePicker datePicker;
    protected TextField threadNum;
    protected Button okButton;
    protected Button stopButton;
    protected Text noticeText;

    protected ExecutorService es;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
        Locale.setDefault(Locale.SIMPLIFIED_CHINESE);
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

    private void initUI() {


        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        Label timelabel = new Label("挂号日期:");
        timelabel.setMinWidth(100);
        gridPane.add(timelabel, 0, 0);
        GridPane.setHalignment(timelabel, HPos.RIGHT);

        //init datetime component
        datePicker = new DatePicker();
//        checkInDatePicker.setOnMouseClicked(event -> checkInDatePicker.getValue());
        gridPane.add(datePicker, 1, 0);


        //init thread num component
        Label threadNumLabel = new Label("同时挂号数:");
        threadNumLabel.setMinWidth(100);
        gridPane.add(threadNumLabel, 0, 1);
        GridPane.setHalignment(threadNumLabel, HPos.RIGHT);

        threadNum = new TextField();
        gridPane.add(threadNum, 1, 1);

        //init post component
        okButton = new Button("开始挂号");
        okButton.setOnMouseClicked(event -> {
            okButton.setDisable(true);
            startRegister();
        });

        stopButton = new Button("停止");
        /*stopButton.setOnMouseClicked(event -> {
            stopRegister();
            okButton.setDisable(false);
        });*/
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER_LEFT);
        hbBtn.getChildren().addAll(okButton, stopButton);
        gridPane.add(hbBtn, 0, 2, 2, 1);

        noticeText = new Text();
        gridPane.add(noticeText, 0, 3);


        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
    }


    public void startRegister() {
        LocalDate REGISTER_DATE = datePicker.getValue();
//        final int POOL_SIZE = Integer.valueOf(args[1] == null ? "20" : args[1]);

        Set<String> timeRangeSet = new HashSet<>();
        /*Pattern p = Pattern.compile("^\\d{2}:\\d{2}~\\d{2}:\\d{2}$");
        for (int i = 2; i < args.length; i++) {
            Matcher m = p.matcher(args[i].trim());
            if (m.matches()) {
                timeRangeSet.add(args[i].trim());
            } else {
                throw new Error("time range error!!!挂号时间段输入格式错误！！");
            }
        }*/
        es = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 20; i++) {
            es.execute(new RegisterProcessor(REGISTER_DATE, timeRangeSet));
        }
    }

    public void stopRegister() {
    }

}
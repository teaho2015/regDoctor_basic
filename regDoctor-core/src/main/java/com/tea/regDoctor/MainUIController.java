/**
 * @author teaho2015@gmail.com
 * since 2017
 */
package com.tea.regDoctor;

import com.tea.regDoctor.processor.RegisterProcessor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class MainUIController {
    @FXML
    protected DatePicker registerDatePicker;

    @FXML
    protected TextField timeRangeField1;
    @FXML
    protected TextField timeRangeField2;
    @FXML
    protected TextField timeRangeField3;

    @FXML
    protected Button submitButton;
    @FXML
    protected Button cancelButton;
    @FXML
    protected Text actiontarget;

    protected RegisterProcessor rp;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {

        logger.info("pressed submit button");

        submitButton.setDisable(true);
        List<TextField> timeFieldList = new ArrayList<>();

        timeFieldList.add(timeRangeField1);
        timeFieldList.add(timeRangeField2);
        timeFieldList.add(timeRangeField3);

        Pattern p = Pattern.compile("^\\d{2}:\\d{2}~\\d{2}:\\d{2}$");
        if (!timeFieldList.stream().allMatch(textField -> {
            String s = textField.getText().trim();
            Matcher m = p.matcher(s);
            return (s.isEmpty() || m.matches());
        })) {
            actiontarget.setText("挂号时间段输入格式错误！！请检查！");
        }

        LocalDate registerDate = registerDatePicker.getValue();

        Set<String> timeRangeSet = timeFieldList.stream().filter(textField -> !textField.getText().trim().isEmpty()).map(TextField::getText).collect(Collectors.toSet());

        rp = RegisterProcessor.create(registerDate, timeRangeSet).scheduleNum(20);
        rp.runAsync();
    }

    @FXML
    protected void handlecancelButtonAction(ActionEvent actionEvent) {
        cancelButton.setDisable(true);
        logger.info("cancel press!!");
        try {
            rp.stop();
            rp.close();
        } catch (NullPointerException e) {
            logger.info("program hasn't run!", e);
        } finally {
            pause(5);
            submitButton.setDisable(false);
            cancelButton.setDisable(false);
        }
    }

    protected void pause(int i) {
        try {
            Thread.currentThread().sleep(i * 1000);
        } catch (InterruptedException e) {
            logger.info("thread sleep() error!!", e);
        }
    }


}

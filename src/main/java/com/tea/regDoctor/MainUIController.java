/*
 * Copyright (c) 2011, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.tea.regDoctor;
 
import com.tea.regDoctor.processor.RegisterProcessor;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class MainUIController {
    @FXML protected DatePicker registerDatePicker;

    @FXML protected TextField timeRangeField1;
    @FXML protected TextField timeRangeField2;
    @FXML protected TextField timeRangeField3;

    @FXML protected Button submitButton;
    @FXML protected Text actiontarget;

    protected RegisterProcessor rp;

    @FXML protected void handleSubmitButtonAction(ActionEvent event) {
        System.out.println("press button");

        submitButton.setDisable(true);
        List<TextField> timeFieldList = new ArrayList<>();

        timeFieldList.add(timeRangeField1);
        timeFieldList.add(timeRangeField2);
        timeFieldList.add(timeRangeField3);

        Pattern p = Pattern.compile("^\\d{2}:\\d{2}~\\d{2}:\\d{2}$");
        if(!timeFieldList.stream().allMatch(textField -> {
            String s = textField.getText().trim();
            Matcher m = p.matcher(s);
            return (s.isEmpty() || m.matches());
        })) {
            actiontarget.setText("挂号时间段输入格式错误！！请检查！");
        }

        LocalDate registerDate = registerDatePicker.getValue();

        Set<String> timeRangeSet = timeFieldList.stream().filter(textField -> !textField.getText().trim().isEmpty()).map(TextField::getText).collect(Collectors.toSet());

        RegisterProcessor rp = RegisterProcessor.create(registerDate, timeRangeSet).scheduleNum(20);
        rp.runAsync();
        submitButton.setDisable(false);
    }

    /*@FXML public void handleFieldExited(Event event) {
        System.out.println("haha");
    }*/

}

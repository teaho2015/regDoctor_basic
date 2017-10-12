/**
 * @author teaho2015@gmail.com
 * since 2017
 */
package net.tea.mail.template;

import java.util.Map;
import java.util.Objects;

public class PlainText implements Segment {
    private String text;

    public PlainText(String text) {
        this.text = text;
    }


    @Override
    public String evaluate(Map<String, String> variables) {
        return text;
    }

    @Override
    public boolean equals(Object obj) {
        return text.equals(((PlainText)obj).text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }
}

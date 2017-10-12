/**
 * @author teaho2015@gmail.com
 * since 2017
 */
package net.tea.mail.template;

import java.util.Map;

public interface Segment {
    public String evaluate(Map<String, String> variables) throws MissingValueException;
}

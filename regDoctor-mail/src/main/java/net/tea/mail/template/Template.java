/**
 * @author teaho2015@gmail.com
 * since 2017
 */
package net.tea.mail.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Template {

    private Map<String, String> varMap = new HashMap<>();

    private String templateText;

    public Template(String templateStr) {
        templateText = templateStr;
    }

    public void set(String variable, String value) {
        varMap.put(variable, value);
    }

    public String evaluate() throws MissingValueException {
        TemplateParse parser = new TemplateParse();
        List<Segment> segments = parser.parseSegments(templateText);
        return concatenate(segments);
    }

    private String concatenate(List<Segment> segments) throws MissingValueException {
        StringBuffer result = new StringBuffer();
        for (Segment segment : segments) {
            result.append(segment.evaluate(varMap));
        }
        return result.toString();
    }



}

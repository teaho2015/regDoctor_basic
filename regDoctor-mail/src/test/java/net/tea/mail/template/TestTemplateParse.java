/**
 * @author teaho2015@gmail.com
 * since 2017
 */
package net.tea.mail.template;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class TestTemplateParse {


    private List<String> parse(String template) {
         return new TemplateParse().parse(template);
    }

    private void assertSegments(List<? extends Object> actual, Object... expected) {
        assertEquals("Number of segments doesn't match.", expected.length, actual.size());
        assertEquals(Arrays.asList(expected), actual);

    }


    @Test
    public void emptyTemplateRendersAsEmptyString() {
        assertSegments(parse(""), "");
    }

    @Test
    public void templateWithOnlyPlainText() {
        assertSegments(parse("only plain text"), "only plain text");
    }

    @Test
    public void parsingMultipleVariables() {
        List<String> segments = parse("${a}:${b}:${c}");
        assertSegments(segments, "${a}", ":", "${b}", ":", "${c}");
    }



}

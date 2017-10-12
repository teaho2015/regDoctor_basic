/**
 * @author teaho2015@gmail.com
 * since 2017
 */
package net.tea.mail.template;

import java.util.Map;
import java.util.Objects;

public class Variable implements Segment {
    private String name;

    public Variable(String text) {
        this.name = text;
    }

    @Override
    public String evaluate(Map<String, String> variables) throws MissingValueException {
        if (!variables.containsKey(name)) {
            throw new MissingValueException("No value for ${" + name + "}");
        }
        return variables.get(name);
    }

    @Override
    public boolean equals(Object obj) {
        return name.equals(((Variable)obj).name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

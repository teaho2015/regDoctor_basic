/**
 * @author teaho2015@gmail.com
 * since 2017
 */
package net.tea.mail.template;

/**
 * throw when some variable in template haven't been set.
 */
public class MissingValueException extends Exception {

    public MissingValueException() {
    }

    public MissingValueException(String message) {
        super(message);
    }

}


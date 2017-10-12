/**
 * @author teaho2015@gmail.com
 * since 2017
 */
package net.tea.mail.component;

public enum EmailTextType {

    PLAIN_UTF_8("plain", "utf-8"),
    HTML_UTF_8("html", "utf-8"),
    XML_UTF_8("xml", "utf-8");

    //utf-8... blablabla
    private final String charset;

    //plain, html, xml
    private final String subType;


    private EmailTextType(String subtype, String charset) {
        this.subType = subtype;
        this.charset = charset;
    }

    public String getSubTpte() {
        return subType;
    }

    public String getCharset() {
        return charset;
    }


}

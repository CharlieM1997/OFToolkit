/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OFGraph;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.swing.text.DefaultFormatter;

/**
 * Used to format regular expressions within the program.
 * @author 164776
 */
public class RegexFormatter extends DefaultFormatter {
    private Pattern pattern;
    private Matcher matcher;

    public RegexFormatter() {
        super();
    }

    public RegexFormatter(String pattern) throws PatternSyntaxException {
        this();
        setPattern(Pattern.compile(pattern));
    }

    public RegexFormatter(Pattern pattern) {
        this();
        setPattern(pattern);
    }

    private void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }

    protected void setMatcher(Matcher matcher) {
        this.matcher = matcher;
    }

    protected Matcher getMatcher() {
        return matcher;
    }

    /**
     * Parses given text with the rules of the matcher.
     * @param text The given text.
     * @return The parsed text.
     * @throws ParseException 
     */
    @Override
    public Object stringToValue(String text) throws ParseException {
        Pattern pt = getPattern();

        if (pt != null) {
            Matcher mtchr = pt.matcher(text);

            if (mtchr.matches()) {
                setMatcher(mtchr);
                return super.stringToValue(text);
            }
            throw new ParseException("Pattern did not match", 0);
        }
        return text;
    }
}

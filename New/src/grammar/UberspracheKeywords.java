package grammar;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fxmisc.richtext.StyleSpans;
import org.fxmisc.richtext.StyleSpansBuilder;

public class UberspracheKeywords
{

    public static final String[] KEYWORDS = new String[]{"ganze", "reelle", "zeichenkette",
			"boolesche", "zeichen", "leer", "funktion", "zurückliefern", "liefert", "zurück",
			"falls", "sonst", "falsch", "wahr", "während", "für", "tun", "lesen", "schreiben",
			"jede", "von"
	};

	public static final String[] OPERATORS = new String[] {"grösser als", "kleiner als",
			"grösser oder gleich als", "kleiner oder gleich als", "gleich", "ungleich",
			"erhält", "und", "oder", "nicht"
	};
			
    public static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) +
            ")\\b";
	public static final String OPERATOR_PATTERN = "\\b(" + String.join("|", OPERATORS) +
			")\\b";
    public static final String PAREN_PATTERN = "\\(|\\)";
    public static final String BRACE_PATTERN = "\\{|\\}";
    public static final String BRACKET_PATTERN = "\\[|\\]";
    public static final String SEMICOLON_PATTERN = "\\;";
    public static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    public static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

    public static final Pattern PATTERN = Pattern.compile(
			  "(?<OPERATOR>" + OPERATOR_PATTERN + ")" + "|(?<KEYWORD>"   + KEYWORD_PATTERN + ")" + 
			 "|(?<PAREN>"    + PAREN_PATTERN    + ")" + "|(?<BRACE>"     + BRACE_PATTERN + ")" +
			 "|(?<BRACKET>"  + BRACKET_PATTERN  + ")" + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")" +
			 "|(?<STRING>"   + STRING_PATTERN   + ")" + "|(?<COMMENT>"   + COMMENT_PATTERN + ")"
	);

    public static StyleSpans<Collection<String>> computeHighlighting(String text) {

        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        while (matcher.find()) {
            String styleClass = matcher.group("OPERATOR") != null ? "operator" : matcher
					.group("KEYWORD") != null ? "keyword" : matcher.group("PAREN") != null
					? "paren" : matcher.group("BRACE") != null ? "brace" : matcher
					.group("BRACKET") != null ? "bracket" : matcher.group("SEMICOLON") != null
					? "semicolon" : matcher.group("STRING") != null ? "string" : matcher
					.group("COMMENT") != null ? "comment" : null; /* never happens */
            assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher
                    .start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }
}
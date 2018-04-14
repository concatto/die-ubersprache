package application;

import java.util.ArrayList;

import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;

public class ConsoleInputParse {

private String[] wordList = {};

public ConsoleInputParse() {}

public FlowPane parseInputToArray(String input) {
    wordList = input.trim().split("[ ]+");

    return colorize();
}

public FlowPane colorize() {

    ArrayList<Text> textChunks = new ArrayList<>();
    FlowPane bundle = new FlowPane();

    //Todo: use regex to check for valid words
    for (String word : wordList) {
        String spaced = word + " ";
        switch (word) {
            case "Hello": case "hello":
                textChunks.add(customize(spaced, "purple"));
                break;
            case "World": case "world":
                textChunks.add(customize(spaced, "blue"));
                break;
            case "Stack Overflow":
                textChunks.add(customize(spaced, "orange", "Arial Bold", 15));
            default:
                textChunks.add(customize(spaced, "black", "Arial",  13));
                break;
        }
    }

    bundle.getChildren().addAll(textChunks);
    return bundle;
}

public Text customize(String word, String color) {
    return TextBuilder.create().text(word).fill(Paint.valueOf(color)).build();
}

public Text customize(String word, String color, String font) {
    return TextBuilder.create()
            .text(word)
            .fill(Paint.valueOf(color))
            .font(Font.font(font, 12)).build();
}

public Text customize(String word, String color, String font, int fontSize) {
    return TextBuilder.create()
            .text(word)
            .fill(Paint.valueOf(color))
            .font(Font.font(font, fontSize)).build();
}

}
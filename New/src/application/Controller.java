package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collectors;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import backend.SymbolTable;
import grammar.Lexico;
import grammar.Semantico;
import grammar.Sintatico;
import grammar.UberspracheKeywords;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class Controller {
	
    @FXML
    private TextArea edit_code;

    @FXML
    private TextArea edit_errors;

    @FXML
    private TextArea edit_console;

    @FXML
    private Button run;
    
    @FXML
    private Button stop;

    @FXML
    private VBox vboxCenter;
    
    @FXML
    private VBox teste;
    
    private CodeArea codeArea;
    
    private boolean modifiedCode = false;
    
	@FXML
	public void initialize () {
		Logger.initialize(warning -> {
			edit_errors.appendText(warning + "\n");
		});
		
		run.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("play.png"), 30,30,true,true )));
		stop.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("stop.png"), 20,20,true,true )));
		stop.setPadding(new Insets(9,13,9,13));
		
		edit_console.setEditable(false);
		edit_errors.setEditable(false);;
		edit_errors.setStyle("-fx-text-fill: red;");
		edit_errors.setWrapText(true);
		
		
		
		ListView<FlowPane> consoleWindow = new ListView<>();
		ObservableList<FlowPane> consoleBuffer = FXCollections.observableArrayList();
		
		consoleWindow.setItems(consoleBuffer);
	
		
		 codeArea = new CodeArea();
	        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
	        codeArea.richChanges().subscribe(change -> {
	            codeArea.setStyleSpans(0, UberspracheKeywords.computeHighlighting(codeArea.getText()));
	        });
	        
	      
	        
	   String[] lines = {
			   "funktion main() liefert leer zur�ck {",
			   "  ganze a;",
			   "  ganze b erh�lt 20;",
			   "  reelle c;",
			   "  boolesche d;",
			   "  zeichenkette strings[10];",
			   "",
			   "  a erh�lt 20 + 10 * 5;",
			   "}"
	   };
      
	   codeArea.replaceText(0, 0, String.join("\n", lines));
	   
       StackPane pane = new StackPane(codeArea);
      
      
        vboxCenter.getChildren().add(pane);
       
        
        VBox.setVgrow(pane, Priority.ALWAYS);		
	}
	
	
    @FXML
    void onClean(ActionEvent event) {
    	codeArea.clear();
    }

    @FXML
    void onOpenNewFile(ActionEvent event) {
    	codeArea.clear();
    }

    @FXML
    void onSave(ActionEvent event) {
    	
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save code");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("�bersprache Quellcode", "*.uqc"));
        File file = fileChooser.showSaveDialog(Main.getStage());
        if (file != null) {
            try {
            	System.out.println(codeArea.getText());
            	file.renameTo(file);
            	BufferedWriter bf = Files.newBufferedWriter(file.toPath(), StandardOpenOption.CREATE_NEW );
            	bf.write(codeArea.getText());
            	bf.close();
                //new Writer().write(codeArea.getText(), "png", file);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        
        }

    }

    @FXML
    void onSelectFile(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	
    	   fileChooser.setTitle("View ");
           fileChooser.setInitialDirectory(
               new File(System.getProperty("user.home"))
           );                 
           fileChooser.getExtensionFilters().addAll(
               new FileChooser.ExtensionFilter("All Type", "*.uqc","*.txt"),
               new FileChooser.ExtensionFilter("Text", "*.txt"),
               new FileChooser.ExtensionFilter("�bersprache Quellcode", "*.uqc"));
           
    	
    	
    	fileChooser.setTitle("Open Resource File");
    	File file = fileChooser.showOpenDialog(Main.getStage());
    	
    	if (file != null) {
    		codeArea.clear();
			try {
				
				String code = Files.lines(file.toPath(), Charset.forName("UTF-8")).collect(Collectors.joining("\n"));

	    		codeArea.replaceText(0, 0, code);	
				System.out.println(code);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }	
	  @FXML
	    void onClick(ActionEvent event) {
		  
			edit_errors.setText("");
			edit_console.setText("");
			
			SymbolTable table = new SymbolTable();
			
			Lexico lex = new Lexico();
			Sintatico sintatico = new Sintatico();
			Semantico sem = new Semantico(table);
			
			lex.setInput(codeArea.getText());
			
			try {
				sintatico.parse(lex, sem);
				table.print();
				edit_console.setText("done.");
				
			} catch (Exception e) {
				e.printStackTrace();
				edit_errors.setText(e.getMessage());
				edit_console.setText("This code contain erros. see the errors screen");
			} 
			
	  }
}

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

import backend.Symbol;
import backend.SymbolTable;
import backend.Type;
import grammar.Lexico;
import grammar.Semantico;
import grammar.Sintatico;
import grammar.UberspracheKeywords;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
    
    private TableView<Symbol> tabela = new TableView<>();

	private TableColumn<Symbol, String> colum1;

	private TableColumn<Symbol, String> colum2;

	private TableColumn<Symbol, String> colum3;

	private TableColumn<Symbol, String> colum4;

	private TableColumn<Symbol, String> colum5;

	private TableColumn<Symbol, String> colum6;

	private TableColumn<Symbol, String> colum7;

	private TableColumn<Symbol, String> colum8;

	private TableColumn<Symbol, String> colum9;

	private TableColumn<Symbol, String> colum10;
	
	Scene s;
	Stage stage;
	

	
	@FXML
	public void initialize () {
		 s = new Scene(tabela);
		 stage = new Stage();
		 stage.setWidth(818);
		    stage.setHeight(500);
		    stage.setScene(s);
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
			   "funktion main() liefert leer zurück {",
			   "  ganze a;",
			   "",
			   "  falls (1) {",
			   "    ganze b;",
			   "  }",
			   "  b erhält 5;",
			   "}"
	   };
      
	   codeArea.replaceText(0, 0, String.join("\n", lines));
	   
       StackPane pane = new StackPane(codeArea);
      
      
        vboxCenter.getChildren().add(pane);
       
        
        VBox.setVgrow(pane, Priority.ALWAYS);		
        
        
        colum1 = new TableColumn<>("identifier");
        colum1.setCellValueFactory(
                new PropertyValueFactory<Symbol, String>("identifier"));
        colum2 = new TableColumn<>("type");
        colum2.setCellValueFactory(
                new PropertyValueFactory<Symbol, String>("type"));
        colum3 = new TableColumn<>("function");
        colum3.setCellValueFactory(
                new PropertyValueFactory<Symbol, String>("function"));
        colum4 = new TableColumn<>("initialized");
        colum4.setCellValueFactory(
                new PropertyValueFactory<Symbol, String>("initialized"));
        colum5 = new TableColumn<>("parameter");
        colum5.setCellValueFactory(
                new PropertyValueFactory<Symbol, String>("parameter"));
        colum6 = new TableColumn<>("used");
        colum6.setCellValueFactory(
                new PropertyValueFactory<Symbol, String>("used"));
        colum7 = new TableColumn<>("size");
        colum7.setCellValueFactory(
                new PropertyValueFactory<Symbol, String>("size"));
        colum8 = new TableColumn<>("scope");
        colum8.setCellValueFactory(
                new PropertyValueFactory<Symbol, String>("scope"));
        colum9 = new TableColumn<>("depth");
        colum9.setCellValueFactory(
                new PropertyValueFactory<Symbol, String>("depth"));
        colum10 = new TableColumn<>("parameterPosition");
        colum10.setCellValueFactory(
                new PropertyValueFactory<Symbol, String>("parameterPosition"));
        
        tabela.getColumns().addAll(colum1, colum2, colum3,colum4,colum5,colum6,colum7,colum8,colum9,colum10);
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
                new FileChooser.ExtensionFilter("ï¿½bersprache Quellcode", "*.uqc"));
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
               new FileChooser.ExtensionFilter("ï¿½bersprache Quellcode", "*.uqc"));
           
    	
    	
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
		
			
			stage.show();
		
			SymbolTable table = new SymbolTable();
			
			Lexico lex = new Lexico();
			Sintatico sintatico = new Sintatico();
			Semantico sem = new Semantico(table);
			
			lex.setInput(codeArea.getText());
			
			try {
				sintatico.parse(lex, sem);
				
				// If it's not a function and it's not being used, warn the user
				table.getTable().stream().filter(s -> !s.isUsed() && !s.isFunction()).forEach(s -> {
					Logger.warn(String.format("Symbol %s %s is not being used.", s.getType(), s.getIdentifier()));
				});
				
				table.print();
				
				  final ObservableList<Symbol> data = FXCollections.observableArrayList(table.getTable());
				  tabela.setItems(data);
				//table.print();
				edit_console.setText("done.");
				
			} catch (Exception e) {
				e.printStackTrace();
				edit_errors.setText(e.getMessage());
				edit_console.setText("This code contain erros. see the errors screen");
			} 
			
	  }
	 
}

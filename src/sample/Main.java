package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import java.awt.*;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Stupidest Game ever!");
        GridPane gridPane=new GridPane();
        gridPane.setPadding(new Insets(15,15,10,15));
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        Label rowLabel=new Label("row : ");
        GridPane.setConstraints(rowLabel, 0, 0);

        TextField rowsTextField=new TextField();
        rowsTextField.setPromptText("Enter the number of rows here!");
        rowsTextField.setMaxWidth(250);
        rowsTextField.setMinWidth(250);
        GridPane.setConstraints(rowsTextField,1,0);

        Label columnsLabel=new Label("columns : ");
        GridPane.setConstraints(columnsLabel,0,1);

        TextField columnsTextField=new TextField();
        columnsTextField.setPromptText("Enter the number of columns here!");
        columnsTextField.setMaxWidth(250);
        columnsTextField.setMinWidth(250);
        GridPane.setConstraints(columnsTextField,1,1);

        Button start=new Button("Start");
        GridPane.setConstraints(start,1,2);
        start.setOnAction(e ->{
            new GameBoard(Integer.parseInt(rowsTextField.getText()),Integer.parseInt(columnsTextField.getText()));
        });

        gridPane.getChildren().addAll(rowLabel,rowsTextField,columnsLabel,columnsTextField,start);


        Scene scene=new Scene(gridPane,350,135);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiupv;

import DBAccess.NavegacionDAOException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Navegacion;
import model.Problem;
import model.User;

/**
 * FXML Controller class
 *
 * @author DROCPER
 */
public class MainTestController implements Initializable {
    
    private Stage primaryStage;
    private Scene prevScene;
    private String prevTitle;
    
    @FXML
    private Button randomButton;
    @FXML
    private Button listButton;
    @FXML
    private VBox sidebar;
    @FXML
    private SidebarController sidebarController;
    @FXML
    private HBox aux;
    @FXML
    private VBox aux1;
    
    User user;    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        sidebarController.initialize(url, rb);
        
        calcSize(aux.getPrefHeight(), aux.getPrefWidth());
    }
    
    
    public void initStage(Stage stage, User us)
    {
        stage.setResizable(true);
        primaryStage = stage;
        prevScene = stage.getScene();
        prevTitle = stage.getTitle();
        primaryStage.setTitle("MAIN");
        sidebarController.primaryStage = primaryStage;
        user = us;
        sidebarController.setUser(user);
        
        
        calcSideBar(primaryStage.getWidth());
        
        aux.widthProperty().addListener((obs, oldv, newv) -> {
            calcSize(aux.heightProperty().getValue(), (double)newv);          
        });
        
        aux.heightProperty().addListener((obs1, oldv, newv) -> {
            calcSize((double)newv, aux.widthProperty().get());
        }); 
        
        primaryStage.widthProperty().addListener((obs, oldv, newv)->{
            calcSideBar((double) newv);
        });
    }
    
    public void updateSidebar(double w)
    {        
        sidebarController.clearSidebar(w);
        sidebarController.boldTestButton(w);
    }
    
    

    @FXML
    private void handleRandom(ActionEvent event) {
        
        try{
            Navegacion navegation = Navegacion.getSingletonNavegacion();
            List<Problem> problemas = navegation.getProblems();
            System.out.println(problemas.size());
            Random generator = new Random();
            int index = generator.nextInt(1000) % (problemas.size() -1);
            Problem p = problemas.get(index);
            

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Map.fxml"));
            try{
                Parent root = (Parent) loader.load();
                MapController controller = loader.<MapController>getController();
                controller.initStage(primaryStage, user, p, index+1);

                primaryStage.getScene().setRoot(root);
            }
            catch(IOException e)
            {
                Alert alertErr = new Alert(Alert.AlertType.ERROR);

                ((Button) alertErr.getDialogPane().lookupButton(ButtonType.OK)).setText("Accept");

                Stage alertStageErr = (Stage) alertErr.getDialogPane().getScene().getWindow();
                alertStageErr.getIcons().add(new Image("file:src/resources/navegacion.png"));
                alertErr.getDialogPane().getStylesheets().add(getClass().getResource("../view/alerts.css").toExternalForm());
                alertErr.getDialogPane().getStyleClass().add("customAlert");
                alertErr.setTitle("Exception Dialog");
                alertErr.setHeaderText("An error has occurred");
                alertErr.setContentText("An unexpected error has occurred when loading the scene. Please try again");


                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String exceptionText = sw.toString();

                Label label = new Label("Exception:");
                TextArea textArea = new TextArea(exceptionText);
                textArea.setEditable(false);
                textArea.setWrapText(true);
                textArea.setMaxWidth(Double.MAX_VALUE);
                textArea.setMaxHeight(Double.MAX_VALUE);
                GridPane.setVgrow(textArea, Priority.ALWAYS);
                GridPane.setHgrow(textArea, Priority.ALWAYS);
                GridPane expContent = new GridPane();
                expContent.setMaxWidth(Double.MAX_VALUE);
                expContent.add(label,0,0);
                expContent.add(textArea, 0, 1);

                alertErr.getDialogPane().setExpandableContent(expContent);
                alertErr.showAndWait();
            }
            
        }
        catch(NavegacionDAOException e)
        {
            Alert alertErr = new Alert(Alert.AlertType.ERROR);

            ((Button) alertErr.getDialogPane().lookupButton(ButtonType.OK)).setText("Accept");

            Stage alertStageErr = (Stage) alertErr.getDialogPane().getScene().getWindow();
            alertStageErr.getIcons().add(new Image("file:src/resources/navegacion.png"));
            alertErr.getDialogPane().getStylesheets().add(getClass().getResource("../view/alerts.css").toExternalForm());
            alertErr.getDialogPane().getStyleClass().add("customAlert");
            alertErr.setTitle("Exception Dialog");
            alertErr.setHeaderText("An error has occurred");
            alertErr.setContentText("An error occurred trying to load the problems. Please try again");


            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String exceptionText = sw.toString();

            Label label = new Label("Exception:");
            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);
            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label,0,0);
            expContent.add(textArea, 0, 1);

            alertErr.getDialogPane().setExpandableContent(expContent);
            alertErr.showAndWait();
        
        }
     }

    @FXML
    private void handleList(ActionEvent event)  {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/TestList.fxml"));
        try{
            Parent root = (Parent) loader.load();
            TestListController mTCtrl = loader.<TestListController>getController();
            mTCtrl.initStage(primaryStage, user);
            primaryStage.getScene().setRoot(root);
            primaryStage.show();
        }
        catch(IOException e)
        {
            Alert alertErr = new Alert(Alert.AlertType.ERROR);

            ((Button) alertErr.getDialogPane().lookupButton(ButtonType.OK)).setText("Accept");

            Stage alertStageErr = (Stage) alertErr.getDialogPane().getScene().getWindow();
            alertStageErr.getIcons().add(new Image("file:src/resources/navegacion.png"));
            alertErr.getDialogPane().getStylesheets().add(getClass().getResource("../view/alerts.css").toExternalForm());
            alertErr.getDialogPane().getStyleClass().add("customAlert");
            alertErr.setTitle("Exception Dialog");
            alertErr.setHeaderText("An error has occurred");
            alertErr.setContentText("An unexpected error has occurred when loading the scene. Please try again");


            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String exceptionText = sw.toString();

            Label label = new Label("Exception:");
            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);
            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label,0,0);
            expContent.add(textArea, 0, 1);

            alertErr.getDialogPane().setExpandableContent(expContent);
            alertErr.showAndWait();
        }
    }
    
    private void calcSize(double h, double w){
        double maxSpacing = 45;
        double minSpacing = 25;
        double maxHeight = 650;
        double minHeight = 375;
        double maxWidth = 800;
        double minWidth = 400;
        double width = w;
        double height = h;
        if(width < minWidth || height < minHeight) {
            Font font = Font.font("System", FontWeight.NORMAL, FontPosture.REGULAR, 30);
            listButton.setFont(font);
            randomButton.setFont(font);
            aux1.setSpacing(minSpacing);
        }else if (width > maxWidth || height > maxHeight){
            Font font = Font.font("System", FontWeight.NORMAL, FontPosture.REGULAR, 50);
            listButton.setFont(font);
            randomButton.setFont(font);
            aux1.setSpacing(maxSpacing);
        }else{
            double difSpacing = maxSpacing - minSpacing;
            double difW = maxWidth-minWidth;
            double difH = maxHeight-minHeight;
            double temp = difW + difH;
            double sizeAct = (maxWidth - width) + (maxHeight - height);
            double per = 1- (sizeAct/temp);
            double difTextSize = 50-30;
            Font font = Font.font("System", FontWeight.NORMAL, FontPosture.REGULAR, 30 + difTextSize*per);
            listButton.setFont(font);
            randomButton.setFont(font);
            aux1.setSpacing(minSpacing + (difSpacing*per));
            System.out.println((difSpacing*per));
        }        
    }
    
    public void calcSideBar (double w) {
        System.out.println("main: " + w);            
        if(w < 1000){
            sidebar.setMinWidth( w * 0.25 );
            sidebar.setMaxWidth( w * 0.25 );
            updateSidebar(w * 0.25);
        }else{
            sidebar.setMinWidth(250);
            sidebar.setMaxWidth(250);
            updateSidebar(250);
        }     
    }
}

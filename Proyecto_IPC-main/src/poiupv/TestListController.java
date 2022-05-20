/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiupv;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;
import model.Navegacion;
import model.Problem;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.util.ArrayList;
import javafx.beans.binding.Bindings;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * FXML Controller class
 *
 * @author DROCPER
 */
public class TestListController implements Initializable {
    
    private Stage primaryStage;
    private Scene prevScene;
    private String prevTitle;
    
    @FXML
    private VBox sidebar;
    @FXML
    private SidebarController sidebarController;
    @FXML
    private ListView problemsList;
    @FXML
    public TextArea problemDescription;
    @FXML
    private Button cancelButton;
    @FXML
    private Button SelectButton;
    @FXML
    private Label titleLabel;
    @FXML
    private VBox aux;
    
    private ArrayList<Problem> problemsArrayList;    
    User user;
    Navegacion navegation;    
    ObservableList problems;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        sidebarController.initialize(url, rb);
        
    }
    
    public void initStage(Stage stage, User us)
    {
        primaryStage = stage;
        prevScene = stage.getScene();
        prevTitle = stage.getTitle();
        primaryStage.setTitle("MAIN");
        sidebarController.primaryStage = primaryStage;
        user = us;
        sidebarController.setUser(user);
        loadProblems();
        calcSideBar(primaryStage.getWidth());
        
        primaryStage.widthProperty().addListener((obs, oldv, newv)->{
            calcSideBar((double) newv);
        });
        
        primaryStage.heightProperty().addListener((obs, oldv, newv)-> {
            calcSize((double)newv);
        });
        calcSize(primaryStage.getHeight());
        problemDescription.setPrefHeight(155);
        problemsList.setPrefHeight(100);
    }
    
    public void updateSidebar(double w)
    {        
        sidebarController.clearSidebar(w);
        sidebarController.boldTestButton(w);
    }
    
    public void loadProblems()
    {
        try {
            navegation = Navegacion.getSingletonNavegacion();
        } catch (Exception e) {
            System.out.println(e);
        }
        int count = 1;
        problemsArrayList = new ArrayList<Problem>();
        for (Problem problem: navegation.getProblems()) {
            problemsArrayList.add(problem);
            problemsList.getItems().add("Problem " + count);
            count++;
        }
        problemsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                SelectButton.setDisable(false);
                int index = problemsList.getSelectionModel().getSelectedIndex();
                problemDescription.setText(problemsArrayList.get(index).getText());
            }
        });
    }

    @FXML
    private void handleCancelButton(ActionEvent event) throws Exception {
    
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MainTest.fxml"));
        Parent root = (Parent) loader.load();
        MainTestController mTCtrl = loader.<MainTestController>getController();
        mTCtrl.initStage(primaryStage, user);
        primaryStage.getScene().setRoot(root);
        primaryStage.show();
        
        
        
    }

    @FXML
    private void handleSelectButton(ActionEvent event) throws Exception {
        int index = problemsList.getSelectionModel().getSelectedIndex();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Map.fxml"));
        Parent root = (Parent) loader.load();
        MapController mTCtrl = loader.<MapController>getController();
        mTCtrl.initStage(primaryStage, user, problemsArrayList.get(index), index+1);
        primaryStage.getScene().setRoot(root);
        primaryStage.show();
    }
    
    public void calcSize(double newv){
        if((double)newv < 400){
            Font fontLa = Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 25);
            Font fontTe = Font.font("System", FontWeight.NORMAL, FontPosture.REGULAR, 12);
            Font fontBu = Font.font("System", FontWeight.NORMAL, FontPosture.REGULAR, 14);          
            
            //aux.setSpacing(15);
            problemDescription.setPrefHeight(aux.getHeight()*0.25);
            problemsList.setPrefHeight(aux.getHeight() *0.25 );/*
            problemDescription.setMinHeight(aux.getHeight()*0.25);
            problemDescription.setMaxHeight(aux.getHeight()*0.25);
            problemsList.setMinHeight(aux.getHeight() * 0.25);
            problemsList.setMaxHeight(aux.getHeight() * 0.25);*/
            problemDescription.setFont(fontTe);
            titleLabel.setFont(fontLa);
            cancelButton.setFont(fontBu);
            SelectButton.setFont(fontBu);
        }  else if((double)newv >= 400 && (double)newv <530){
            double act = 530-(double)newv;
            int stageDif = 530-375;
            double per = 1 - (act/stageDif);
            int fontLaDif = 30 - 25;
            int buDif = 18-14; 
            int textDif = 16-12;
            double desDif = (0.25 + ((0.45-0.25)*per));
            double lisDif = (0.25 + ((0.3-0.25)*per));
            
            Font fontLa = Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 25 + (fontLaDif*per));
            Font fontTe = Font.font("System", FontWeight.NORMAL, FontPosture.REGULAR, 12 + (textDif*per));
            Font fontBu = Font.font("System", FontWeight.NORMAL, FontPosture.REGULAR, 14 + (buDif*per));          
            
            problemDescription.setPrefHeight(aux.getHeight()*desDif);
            problemsList.setPrefHeight(aux.getHeight() * lisDif);
            /*problemDescription.setMinHeight(aux.getHeight()*desDif);
            problemDescription.setMaxHeight(aux.getHeight()*desDif);
            problemsList.setMinHeight(aux.getHeight() *lisDif );
            problemsList.setMaxHeight(aux.getHeight() *lisDif);*/
            problemDescription.setFont(fontTe);
            titleLabel.setFont(fontLa);
            cancelButton.setFont(fontBu);
            SelectButton.setFont(fontBu);
        }else{
            Font fontLa = Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 30);
            Font fontTe = Font.font("System", FontWeight.NORMAL, FontPosture.REGULAR, 16);
            Font fontBu = Font.font("System", FontWeight.NORMAL, FontPosture.REGULAR, 18);
            
            //aux.setSpacing(25);
            problemDescription.setPrefHeight(aux.getHeight()*.45);
            problemsList.setPrefHeight(aux.getHeight() *.45 );/*
            problemDescription.setMinHeight(aux.getHeight()*0.45);
            problemDescription.setMaxHeight(aux.getHeight()*0.45);
            problemsList.setMinHeight(aux.getHeight() * 0.3);
            problemsList.setMaxHeight(aux.getHeight() * 0.3);*/
            problemDescription.setFont(fontTe);
            titleLabel.setFont(fontLa);
            cancelButton.setFont(fontBu);
            SelectButton.setFont(fontBu);
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

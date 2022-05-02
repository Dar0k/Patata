/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiupv;

import DBAccess.NavegacionDAOException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javax.swing.text.NavigationFilter;
import model.Navegacion;
import model.User;

/**
 * FXML Controller class
 *
 * @author DROCPER
 */
public class SignUpDefController implements Initializable {
    
    private Stage primaryStage;
    private Scene prevScene;
    private String prevTitle; 
    
    private BooleanProperty validUsername;
    private BooleanProperty validPassword;
    private BooleanProperty validReenterPassword;
    private BooleanProperty validEmail;
    private BooleanProperty validAge;

    
    @FXML
    private TextField usernameField;
    @FXML
    private Label usernameError;
    @FXML
    private Label emailError;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private StackPane passwordEye;
    @FXML
    private Line passwordEyeLine;
    @FXML
    private Label passwordError;
    @FXML
    private TextField reenterPasswordTextField;
    @FXML
    private PasswordField reenterPasswordField;
    @FXML
    private StackPane reenterPasswordEye;
    @FXML
    private Line reenterPasswordEyeLine;
    @FXML
    private Label reenterPasswordError;
    @FXML
    private DatePicker agePicker;
    @FXML
    private Label ageError;
    @FXML
    private Button chooseImageButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button signUpButton;
    @FXML
    private Circle avaPrin;
    @FXML
    private Circle ava1;
    @FXML
    private Circle ava2;
    @FXML
    private Circle ava3;
    @FXML
    private Circle ava4;
    @FXML
    private Circle ava5;
    @FXML
    private VBox aux;
    
    Navegacion navegation;
    User user; 
    Circle sel;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            navegation = Navegacion.getSingletonNavegacion();
        } catch (NavegacionDAOException ex) {
            Logger.getLogger(SignUpDefController.class.getName()).log(Level.SEVERE, null, ex);
        }           
        sel = ava1;
        Image im1 = new Image("https://th.bing.com/th/id/OIP.EQBqKixnFE0c6UryfIgS-AHaGQ?pid=ImgDet&rs=1");
        Image im2 = new Image("https://th.bing.com/th/id/OIP.AaMb_876cSr06RW4AJ3XyAHaGk?pid=ImgDet&w=500&h=444&rs=1");
        Image im3 = new Image("https://th.bing.com/th/id/OIP.XnD-Oh_qsK18OPXdbLWh7gHaFw?pid=ImgDet&rs=1");
        Image im4 = new Image("https://th.bing.com/th/id/OIP.ErgU0tyLu4W4Mv5t6nbdbAHaGV?pid=ImgDet&w=741&h=634&rs=1");
        Image im5 = new Image("https://thumbs.dreamstime.com/b/american-eagle-isolated-close-up-portrait-36632728.jpg");
        avaPrin.setFill(new ImagePattern(im1));          
        ava1.setFill(new ImagePattern(im1));
        ava2.setFill(new ImagePattern(im2));
        ava3.setFill(new ImagePattern(im3));
        ava4.setFill(new ImagePattern(im4));
        ava5.setFill(new ImagePattern(im5));     
        
        
        validUsername = new SimpleBooleanProperty();
        validPassword = new SimpleBooleanProperty();
        validReenterPassword = new SimpleBooleanProperty();
        validEmail = new SimpleBooleanProperty();
        validAge = new SimpleBooleanProperty();
        
        validUsername.setValue(Boolean.FALSE);
        validPassword.setValue(Boolean.FALSE);
        validReenterPassword.setValue(Boolean.FALSE);
        validEmail.setValue(Boolean.FALSE);
        validAge.setValue(Boolean.FALSE);
        
        usernameField.textProperty().addListener((observable, oldValue, newValue)->{
            checkEditUsername(newValue);
        });
        
        passwordField.textProperty().addListener((observable, oldValue, newValue)->{             
            checkEditPassword(newValue); 
            checkEditReenterPassword(newValue, reenterPasswordField.getText()); 
            checkEditReenterPassword(newValue, reenterPasswordTextField.getText());              
        });        
        passwordTextField.textProperty().addListener((observable, oldValue, newValue)->{
            checkEditPassword(newValue); 
            checkEditReenterPassword(newValue, reenterPasswordField.getText());
            checkEditReenterPassword(newValue, reenterPasswordTextField.getText()); 
        });
        
        reenterPasswordField.textProperty().addListener((observable, oldValue, newValue)->{
            checkEditReenterPassword(passwordField.getText(), newValue); 
        });
        reenterPasswordTextField.textProperty().addListener((observable, oldValue, newValue)->{
            checkEditReenterPassword(passwordTextField.getText(), newValue); 
        });
        
        emailField.textProperty().addListener((observable, oldValue, newValue)->{
            checkEditEmail(newValue); 
        });
        
        agePicker.valueProperty().addListener((observable, oldValue, newValue)->{
            checkEditAge(newValue); 
        });         
        
        passwordField.textProperty().addListener((obs, oldVal, newVal) -> {
            passwordTextField.setText(newVal);
        });
        passwordTextField.textProperty().addListener((obs, oldVal, newVal) -> {
            passwordField.setText(newVal);
        });
        reenterPasswordField.textProperty().addListener((obs, oldVal, newVal) -> {
            reenterPasswordTextField.setText(newVal);
        });
        reenterPasswordTextField.textProperty().addListener((obs, oldVal, newVal) -> {
            reenterPasswordField.setText(newVal);
        });
        
        BooleanBinding validFields = Bindings.and(validUsername, validPassword).and(validReenterPassword).and(validEmail).and(validAge);
        
        signUpButton.disableProperty().bind(Bindings.not(validFields));        
        signUpButton.setOnAction( (event)->{            
            try {
                FXMLLoader myLoader = new FXMLLoader(getClass().getResource("../view/MainTest.fxml"));
                Parent root;
                root = (Parent) myLoader.load();
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                //                  REGISTER
                /*try {        
                    ImagePattern help = (ImagePattern) avaPrin.getFill();
                    Image image = help.getImage();
                    User result = navegation.registerUser(usernameField.getText(), emailField.getText(), passwordField.getText(), image, agePicker.getValue());
                } catch (NavegacionDAOException ex) {
                    Logger.getLogger(SignUpDefController.class.getName()).log(Level.SEVERE, null, ex);
                }*/
            } catch (IOException ex) {
                Logger.getLogger(LogInDefController.class.getName()).log(Level.SEVERE, null, ex);
            }            
        });  
    }
    
    public void initStage(Stage stage)
    {
        prevScene = stage.getScene();
        prevTitle = stage.getTitle();
        primaryStage = stage;
        primaryStage.setTitle("SIGN UP");
        
    }

    @FXML
    private void handleChooseImage(ActionEvent event) {
        
    }   

    @FXML
    private void handleSelectAvatar(MouseEvent event) {
        Node source = (Node)event.getSource();
        if(source.getId().equals(ava1.getId())){
            ava1.setStroke(Color.BLACK);  
            ava1.setStrokeWidth(1.5);
            avaPrin.setFill(ava1.getFill());
            sel.setStroke(Color.TRANSPARENT);
            sel = ava1;
        }else if(source.getId().equals(ava2.getId())){
            ava2.setStroke(Color.BLACK);
            ava2.setStrokeWidth(1.5);
            avaPrin.setFill(ava2.getFill());
            sel.setStroke(Color.TRANSPARENT);
            sel = ava2;
        }else if(source.getId().equals(ava3.getId())){
            ava3.setStroke(Color.BLACK);
            ava3.setStrokeWidth(1.5);
            avaPrin.setFill(ava3.getFill());
            sel.setStroke(Color.TRANSPARENT);
            sel = ava3;
        }else if(source.getId().equals(ava4.getId())){
            ava4.setStroke(Color.BLACK);
            ava4.setStrokeWidth(1.5);
            avaPrin.setFill(ava4.getFill());
            sel.setStroke(Color.TRANSPARENT);
            sel = ava4;
        }else if(source.getId().equals(ava5.getId())){
            ava5.setStroke(Color.BLACK);
            ava5.setStrokeWidth(1.5);
            avaPrin.setFill(ava5.getFill());
            sel.setStroke(Color.TRANSPARENT);
            sel = ava5;          
        }
    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
        primaryStage.setScene(prevScene);
        primaryStage.setTitle(prevTitle);
    }

    @FXML
    private void handleEyeCicked(MouseEvent event) {
        Node source = (Node)event.getSource();
        if (source.getId().equals(passwordEye.getId())) {
            boolean temp = passwordEyeLine.isVisible();
            passwordEyeLine.setVisible(!temp);
            passwordField.setVisible(!temp);
            aux.requestFocus();
            passwordField.setVisible(!temp);
            passwordTextField.setVisible(temp);
        } else {
            boolean temp = reenterPasswordEyeLine.isVisible();
            reenterPasswordEyeLine.setVisible(!temp);
            reenterPasswordField.setVisible(!temp);
            aux.requestFocus();
            reenterPasswordField.setVisible(!temp);
            reenterPasswordTextField.setVisible(temp);
        }        
    }
    
    public void checkEditUsername(String str){
        if(!User.checkNickName(str)) {
            usernameError.setVisible(true);
            usernameError.setText(" user name error ");
            validUsername.setValue(Boolean.FALSE);
        }else {
            usernameError.setVisible(false);
            validUsername.setValue(Boolean.TRUE);
        }    
    }
    
    public void checkEditPassword(String pas){
        if(!User.checkPassword(pas)){
            passwordError.setVisible(true);
            passwordError.setText(" password error ");
            validPassword.setValue(Boolean.FALSE); 
        }else {
            passwordError.setVisible(false);
            validPassword.setValue(Boolean.TRUE); 
        }
    }
    
    public void checkEditReenterPassword(String pas, String rePas){
        if (!pas.equals(rePas) ){
            reenterPasswordError.setVisible(true);
            reenterPasswordError.setText(" password not the same ");
            validReenterPassword.setValue(Boolean.FALSE);            
        }else {
            reenterPasswordError.setVisible(false);
            validReenterPassword.setValue(Boolean.TRUE);
        }   
    }
    
    public void checkEditEmail(String str){
        if(!User.checkEmail(str)){
            emailError.setVisible(true);
            emailError.setText(" email error");
            validEmail.setValue(Boolean.FALSE); 
        }else {
            emailError.setVisible(false);
            validEmail.setValue(Boolean.TRUE); 
        }        
    }
    
    public void checkEditAge(LocalDate dtp){
        if(dtp.plusYears(12).isAfter(LocalDate.now())){
            ageError.setVisible(true);
            ageError.setText(" age error ");
            validAge.setValue(Boolean.FALSE); 
        } else {
            ageError.setVisible(false);
            validAge.setValue(Boolean.TRUE); 
        }
    }
   
}

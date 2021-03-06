/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiupv;

import DBAccess.NavegacionDAOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
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
    private Parent prevRoot;
    
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
    int avatarSel;
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
        try {
            Image im2 = new Image (new FileInputStream ("." + File.separator + "src" + File.separator + "resources" + File.separator + "ava1.jpg"));
            Image im1 = new Image (new FileInputStream ("." + File.separator + "src" + File.separator + "resources" + File.separator + "ava2.jpg"));
            Image im3 = new Image (new FileInputStream ("." + File.separator + "src" + File.separator + "resources" + File.separator + "ava3.jpg"));
            Image im4 = new Image (new FileInputStream ("." + File.separator + "src" + File.separator + "resources" + File.separator + "ava4.jpg"));
            Image im5 = new Image (new FileInputStream ("." + File.separator + "src" + File.separator + "resources" + File.separator + "ava5.jpg"));
            avaPrin.setFill(new ImagePattern(im1));          
            ava1.setFill(new ImagePattern(im1));
            ava2.setFill(new ImagePattern(im2));
            ava3.setFill(new ImagePattern(im3));
            ava4.setFill(new ImagePattern(im4));
            ava5.setFill(new ImagePattern(im5)); 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SignUpDefController.class.getName()).log(Level.SEVERE, null, ex);
        }    
        
        
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
        signUpButton.setOnAction( (event)->{  signUpCheck();});  
    }
    private void signUpCheck() 
    {        
        //                  REGISTER
        try {        
            ImagePattern imPa = (ImagePattern) avaPrin.getFill();
            Image image = imPa.getImage();
            navegation.registerUser(usernameField.getText(), emailField.getText(), passwordField.getText(), image, agePicker.getValue());
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("../view/LogInDef.fxml"));
            try{
                Parent root = (Parent) myLoader.load();
                LogInDefController mtCtrl = myLoader.<LogInDefController>getController();
                mtCtrl.initStage(primaryStage);
                Alert alert = new Alert(AlertType.INFORMATION);
                ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Accept");
                
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.getIcons().add(new Image("file:src/resources/navegacion.png"));
                alert.getDialogPane().getStylesheets().add(getClass().getResource("../view/alerts.css").toExternalForm());
                alert.getDialogPane().getStyleClass().add("customAlert");
                alert.setTitle("Sign up");
                alert.setHeaderText("");
                alert.setContentText("Your user has been registered correctly");
                alert.showAndWait();
                primaryStage.getScene().setRoot(root);
                
            }
            catch(IOException e)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                
                
                ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Accept");
                
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.getIcons().add(new Image("file:src/resources/navegacion.png"));
                alert.getDialogPane().getStylesheets().add(getClass().getResource("../view/alerts.css").toExternalForm());
                alert.getDialogPane().getStyleClass().add("customAlert");
                alert.setTitle("Exception Dialog");
                alert.setHeaderText("An error has occurred");
                alert.setContentText("An unexpected error has occurred when loading the scene. Please try again");
                
                
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
                
                alert.getDialogPane().setExpandableContent(expContent);
                alert.showAndWait();
            }
        } catch (NavegacionDAOException e) {
                Logger.getLogger(SignUpDefController.class.getName()).log(Level.SEVERE, null, e);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                
                
                ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Accept");
                
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.getIcons().add(new Image("file:src/resources/navegacion.png"));
                alert.getDialogPane().getStylesheets().add(getClass().getResource("../view/alerts.css").toExternalForm());
                alert.getDialogPane().getStyleClass().add("customAlert");
                alert.setTitle("Exception Dialog");
                alert.setHeaderText("An error has occurred");
                alert.setContentText("An unexcpected error has occurred when registering the user. Please try again");
                
                
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
                
                alert.getDialogPane().setExpandableContent(expContent);
                alert.showAndWait();
        }        
    }
    
    public void initStage(Stage stage)
    {
        prevScene = stage.getScene();
        prevRoot = stage.getScene().getRoot();
        prevTitle = stage.getTitle();
        primaryStage = stage;
        primaryStage.setTitle("SIGN UP"); 
        
        primaryStage.setMinHeight(460);
        primaryStage.setMinWidth(600);
    }

    @FXML
    private void handleChooseImage(ActionEvent event) {
        FileChooser fileChoose = new FileChooser();
        File recordsDir = new File(System.getProperty("user.Desktop"), "src/resources");
        System.out.println(recordsDir);
        if (! recordsDir.exists()) {
            recordsDir.mkdirs();
        }
        fileChoose.setInitialDirectory(recordsDir);
        FileChooser.ExtensionFilter fileExtensions = 
        new FileChooser.ExtensionFilter(
        "Images", "*.jpeg", "*.png", "*.jpg");

        fileChoose.getExtensionFilters().add(fileExtensions);
        fileChoose.setTitle("Open File");  
        File file = fileChoose.showOpenDialog(primaryStage);
        
        try{
            
            if(file != null)
            {
                String imageUrl = file.toURI().toURL().toExternalForm();
                Image image = new Image(imageUrl);
                avaPrin.setFill(new ImagePattern(image));
            }


        }
        catch(MalformedURLException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                
                
                ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Accept");
                
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.getIcons().add(new Image("file:src/resources/navegacion.png"));
                alert.getDialogPane().getStylesheets().add(getClass().getResource("../view/alerts.css").toExternalForm());
                alert.getDialogPane().getStyleClass().add("customAlert");
                alert.setTitle("Exception Dialog");
                alert.setHeaderText("An error has occurred");
                alert.setContentText("The image selected could not be opened. Please try again");
                
                
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
                
                alert.getDialogPane().setExpandableContent(expContent);
                alert.showAndWait();
            
        }
        sel.setStroke(Color.TRANSPARENT);
    }   
    
    @FXML
    private void handleSelectAvatar(MouseEvent event) {
        Circle source = (Circle)event.getSource();
        /*FadeTransition fadeTransition = 
            new FadeTransition(Duration.millis(3000), );*/
        sel.setStroke(Color.TRANSPARENT);
        sel = source;
        source.setStroke(Color.BLACK);  
        source.setStrokeWidth(1.5);
        avaPrin.setFill(source.getFill());                
    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
        primaryStage.getScene().setRoot(prevRoot);
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
        if(!User.checkNickName(str) ||str.equals("remove") || navegation.exitsNickName(str)) {
            usernameError.setVisible(true);
            validUsername.setValue(Boolean.FALSE);
            if(str.length() < 6 || str.length() > 15){
                usernameError.setText("Must contain between 6 and 15 characters");
            }else if (navegation.exitsNickName(str)){
                usernameError.setText("Username already taken");
            }else if(str.equals("remove")){
                try {
                    navegation.removeAllData();
                    System.out.println("data removed");
                } catch (NavegacionDAOException ex) {
                    Logger.getLogger(SignUpDefController.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            }else {
                usernameError.setText("The only special characters permited are '-' and '_'");
            }          
        }else {
            usernameError.setVisible(false);
            validUsername.setValue(Boolean.TRUE);
        }    
    }
    
    public void checkEditPassword(String pas){
        if(!User.checkPassword(pas)){
            passwordError.setVisible(true);
            validPassword.setValue(Boolean.FALSE);
            if (pas.length() < 8 || pas.length() > 20){
                passwordError.setText("Must contain between 8 and 20 characters");
            }else if (!pas.matches("^.*[A-Z].*$")){
                passwordError.setText("Must contain at least one uppercase");
            }else if (!pas.matches("^.*[0-9].*$")){
                passwordError.setText("Must contain at least one diggit");
            }else if (!pas.matches("^.*[a-z].*$")) {
                passwordError.setText("Must contain at least one lowercase");
            }else if (pas.contains(" ")){
                passwordError.setText("Can not contain any blank spaces");
            }else {
                passwordError.setText("Must contain at least one of these characters: !@#$%&*&*()-+=");
            }            
        }else {
            passwordError.setVisible(false);
            validPassword.setValue(Boolean.TRUE); 
        }
    }
    
    public void checkEditReenterPassword(String pas, String rePas){
        if (!pas.equals(rePas) ){
            reenterPasswordError.setVisible(true);
            reenterPasswordError.setText("Password is not the same");
            validReenterPassword.setValue(Boolean.FALSE);            
        }else {
            reenterPasswordError.setVisible(false);
            validReenterPassword.setValue(Boolean.TRUE);
        }   
    }
    
    public void checkEditEmail(String str){
        if(!User.checkEmail(str)){
            emailError.setVisible(true);
            emailError.setText("Email error");
            validEmail.setValue(Boolean.FALSE); 
        }else {
            emailError.setVisible(false);
            validEmail.setValue(Boolean.TRUE); 
        }        
    }
    
    public void checkEditAge(LocalDate dtp){
        if(dtp.plusYears(12).isAfter(LocalDate.now())){
            ageError.setVisible(true);
            ageError.setText("Must be at least 12 years old");
            validAge.setValue(Boolean.FALSE); 
        } else {
            ageError.setVisible(false);
            validAge.setValue(Boolean.TRUE); 
        }
    }

    @FXML
    private void handleKeyPressed(KeyEvent event) {
        KeyCode code = event.getCode();
        if(code == KeyCode.ENTER && !signUpButton.isDisable())
        {
            signUpCheck();
        }
    }
    
    
   
}

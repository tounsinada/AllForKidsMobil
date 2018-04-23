/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.AllForKids.gui;

import edu.AllForKids.entities.User;
import static edu.AllForKids.gui.LoginController.CurrentUser;
import edu.AllForKids.services.CrudUser;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class RController implements Initializable {

    @FXML
    private TextField tfmail;
    @FXML
    private PasswordField tfpass;
    @FXML
    private Button btnconnexion;
    @FXML
    private Text BtnForgetPassword;
    @FXML
    private Button btnsignup;
    @FXML
    private TextField Nom;
    @FXML
    private TextField Email;
    @FXML
    private PasswordField Pass;
    @FXML
    private ImageView img;
    @FXML
    private PasswordField RepeterPass;
    @FXML
    private RadioButton homme;
    @FXML
    private ToggleGroup gender;
    @FXML
    private RadioButton femme;
    @FXML
    private Button Reset;
    @FXML
    private Button Choose;
    Stage primarystage;
    @FXML
    private ComboBox<String> Role;
    String path1 = "";
    File file1;
    File source;
    File dest;
    private FileChooser fileChooser = new FileChooser();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        homme.setSelected(true);
        ObservableList Combo = FXCollections.observableArrayList("Parent", "BabySitter", "Pediatre", "Prestataire");
        Role.setItems(Combo);
//                primarystage.setMaximized(true); 

    }

    @FXML
    private void btnconnexion(ActionEvent event) throws IOException, SQLException {
        tfmail.setStyle(" -fx-border-color : white ; -fx-border-width :  0 0 2px 0 ; -fx-background-color :  transparent ; -fx-text-fill : white");
        tfpass.setStyle(" -fx-border-color : white ; -fx-border-width :  0 0 2px 0 ; -fx-background-color :  transparent ; -fx-text-fill : white");

        if (tfmail.getText().equals("") || tfpass.getText().equals("")) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Veuillez inserer votre email et votre mot de pass");
            a.showAndWait();

            if (tfmail.getText().equals("")) {
                tfmail.setStyle("-fx-border-color : red ; -fx-border-width :  0 0 2px 0 ; -fx-background-color :  transparent ; -fx-text-fill : white ");
                Alert b = new Alert(Alert.AlertType.WARNING);
                a.setContentText("Veuillez inserer votre email ");
                a.showAndWait();
            }
            if (tfpass.getText().equals("")) {
                tfpass.setStyle("-fx-border-color : red ; -fx-border-width :  0 0 2px 0 ; -fx-background-color :  transparent ; -fx-text-fill : white ");
                Alert c = new Alert(Alert.AlertType.WARNING);
                a.setContentText("Veuillez inserer  et votre mot de pass");
                a.showAndWait();
            }
        } else {
            String email = tfmail.getText();
            String pass = tfpass.getText();
            CrudUser crudutilisateur = new CrudUser();
            User u = crudutilisateur.Authentification(email, pass);
            System.out.println("user n'est pas null *****" + u);

            if (u == null) {
                System.out.println("iln'yapas dns base");
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText("Email ou mot de passe incorrect");
                a.showAndWait();
                System.out.println("mailfaut");

            } else {
                CurrentUser = u;
                if (!u.getRoles().equals("Parent")) {
                    System.out.println("parent");

                     ((Node)event.getSource()).getScene().getWindow().hide();
            Stage stage=new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("AccueilFrontEnd.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show(); 
                } else if (!u.getRoles().equals("Admin")) {
                  ((Node)event.getSource()).getScene().getWindow().hide();
            Stage stage=new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("AccueilBackEnd.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show(); 
                } else if (!u.getRoles().equals("Pediatre")) {
                    System.out.println("    ped");

                    primarystage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("AccueilFrontEnd.fxml"));
                    Scene scene = new Scene(root);
                    primarystage.setScene(scene);
                    primarystage.show();
                } else if (!u.getRoles().equals("BabySitter")) {
                    primarystage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("AccueilFrontEnd.fxml"));
                    Scene scene = new Scene(root);
                    primarystage.setScene(scene);
                    primarystage.show();
                }

            }
        }

    }

    public boolean validerEmail() {
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(Email.getText());
        if (m.find() && m.group().equals(Email.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation de l'email");
            alert.setHeaderText(null);
            alert.setContentText("Merci d'entrer une adrese email valide");
            alert.showAndWait();
            return false;
        }
    }

    @FXML
    private void Register(ActionEvent event) throws IOException, SQLException {

        if (!Nom.getText().equals("")) {
            Nom.setStyle("-fx-border-color : white ; -fx-border-width :  0 0 2px 0 ; -fx-background-color :  transparent ; -fx-text-fill : white ;");
            Nom.setPromptText("Inserez votre nom ici !!!");
        }
        if (!Email.getText().equals("")) {

            if (!validerEmail()) {
                Email.setStyle("-fx-border-color : red ;");
                System.out.println("mail errone");
            } else {
                System.out.println("mail respecte la bonne forme");
            }

        }
        if (img.getImage() != null) {
            System.out.println("image pleine");
        }

        if (!Pass.getText().equals("")) {
            Pass.setStyle("-fx-border-color : white ; -fx-border-width :  0 0 2px 0 ; -fx-background-color :  transparent ; -fx-text-fill : white ;");
            Pass.setPromptText("Inserez votre password ici !!!");
        }
        if (!RepeterPass.getText().equals("")) {
            RepeterPass.setStyle("-fx-border-color : white ; -fx-border-width :  0 0 2px 0 ; -fx-background-color :  transparent ; -fx-text-fill : white ;");
            RepeterPass.setPromptText("Repeter votre password ici !!!");

        }

        if (Nom.getText().equals("") || Email.getText().equals("") || Pass.getText().equals("")
                || RepeterPass.getText().equals("") || (img.getImage() == null)
                || Role.getSelectionModel().getSelectedIndex() == -1) {

            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Veuillez remplir les champs manquants");
            a.showAndWait();
            if (Nom.getText().equals("")) {
                Nom.setStyle("-fx-border-color : red ;");
            }
            if (img.getImage() == null) {
                System.out.println("image vide");
                JOptionPane.showMessageDialog(null, " Veuillez entrer votre photo!");

            }
            if (Role.getSelectionModel().getSelectedIndex() == -1) {
                Role.setStyle("-fx-border-color : red ;");
            }
            if (Email.getText().equals("")) {
                Email.setStyle("-fx-border-color : red ;");
            }
            if (Pass.getText().equals("")) {
                Pass.setStyle("-fx-border-color : red ;");
            }
            if (RepeterPass.getText().equals("")) {
                RepeterPass.setStyle("-fx-border-color : red ;");
            }

        } else {
            RadioButton selectedRadioButton = (RadioButton) gender.getSelectedToggle();
            String toogleGroupValue = selectedRadioButton.getText().toLowerCase();
            CrudUser crudutilisateur = new CrudUser();
            if (crudutilisateur.FindByEmail(Email.getText()) == null) {

                copier(source, dest);
                User u = new User (Nom.getText(), Email.getText(), Pass.getText(), path1,
                        Role.getSelectionModel().getSelectedItem(), toogleGroupValue,0);

                      
                u.setNom_image(path1);
                System.out.println(u.getNom_image());

                if (!RepeterPass.getText().equals(Pass.getText())) {

                    do {
                        RepeterPass.setStyle("-fx-border-color : red ;");
                        JOptionPane.showMessageDialog(null, " le password répété est erroné!!!");
                    } while (RepeterPass.getText().equals(Pass.getText()));

                } else {

                    crudutilisateur.ajouter_utilisateur(u);
                   // u.setEnabled(0);
                    //crudutilisateur.RegisterUser(u.getId());
                    
                    System.out.println(u.getEnabled());
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setContentText("nouveau compte est créé");
                    a.showAndWait();
                }

            } else {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText("adresse email existe deja connectez vous");
                a.showAndWait();
            }
        }

    }

    @FXML
    private void Reset(ActionEvent event) {
        femme.setSelected(false);
        homme.setSelected(true);
        Email.clear();
        Pass.clear();
        Nom.clear();
        RepeterPass.clear();
        img.imageProperty().set(null);

    }

    @FXML
    private void Choisir(ActionEvent event) {

        File file = fileChooser.showOpenDialog(primarystage);
        if (file != null) {
            if (file.getName().contains(".jpg")) {
                System.out.println("khtart fichier");

                System.out.println(UUID.randomUUID().toString().concat(".jpg"));

                String uuid = UUID.randomUUID().toString().concat(".jpg");

                path1 = file.getAbsolutePath();
                System.out.println(path1);
                //String pathxx1 = file.toURI().toURL().toString();

                //Image image = new Image(pathxx1);
                file1 = new File(path1);

                Image image1 = new Image(file1.toURI().toString());
                img.setImage(image1);
                source = new File(path1);

                dest = new File("C:\\wamp64\\www\\PIN\\web\\images\\" + uuid);
                path1 = uuid;

            }

        }
    }

    public static boolean copier(File source, File dest) {
        try (InputStream sourceFile = new java.io.FileInputStream(source);
                OutputStream destinationFile = new FileOutputStream(dest)) {
            // Lecture par segment de 0.5Mo  
            byte buffer[] = new byte[512 * 1024];
            int nbLecture;
            while ((nbLecture = sourceFile.read(buffer)) != -1) {
                destinationFile.write(buffer, 0, nbLecture);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Erreur 
        }
        return true; // Résultat OK   
    }

}

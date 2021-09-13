
package com.oopclass.breadapp.controllers;

import com.oopclass.breadapp.config.StageManager;
import com.oopclass.breadapp.views.FxmlView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import javafx.application.Platform;
import javafx.scene.control.Label;

@Controller
public class LoginViewController implements Initializable {

 
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    
    @FXML private Label txtSignin;
    
    @FXML
    private Button openLogin;

    @Lazy
    @Autowired
    private StageManager stageManager;

    
    
    @FXML
    private void openLogin(ActionEvent event) throws IOException {
        if (txtUsername.getText().equals("admin") && txtPassword.getText().equals("admin"))
        {
            stageManager.switchScene(FxmlView.DASHBOARD);
        }
        else{
            txtSignin();
        }
 }
    public void txtSignin(){
        txtSignin.setText("Invalid");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}

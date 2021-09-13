
package com.oopclass.breadapp.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import org.springframework.stereotype.Controller;
import com.oopclass.breadapp.views.FxmlView;
import com.oopclass.breadapp.config.StageManager;
import javafx.scene.control.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Controller
public class DashboardController implements Initializable{

    @FXML private Button exit;
    @FXML private Button openCustomer;
    @FXML private Button openStocks;
    @FXML private Button openPricelist;
    @Lazy
    @Autowired
    private StageManager stageManager;
    
    
    @FXML
    private void openCustomer (ActionEvent event){
        stageManager.switchScene(FxmlView.USER);
    }
    
    @FXML
    private void openPricelist (ActionEvent event){
        stageManager.switchScene(FxmlView.PRICELIST);
    }
    
     @FXML
    private void openStocks (ActionEvent event){
        stageManager.switchScene(FxmlView.PRODUCT);
    }
    
    
    @FXML 
    private void exit(ActionEvent event)
    {
         stageManager.switchScene(FxmlView.LOGINVIEW);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    }
    
}

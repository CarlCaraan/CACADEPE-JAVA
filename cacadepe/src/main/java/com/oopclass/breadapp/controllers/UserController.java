package com.oopclass.breadapp.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.oopclass.breadapp.config.StageManager;
import com.oopclass.breadapp.models.User;
import com.oopclass.breadapp.services.impl.UserService;
import com.oopclass.breadapp.views.FxmlView;
import com.oopclass.breadapp.models.User;
import com.oopclass.breadapp.services.impl.UserService;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

/**
 * OOP Class 20-21
 *
 * @author Gerald Villaran
 */
@Controller
public class UserController implements Initializable {

    @FXML
   private Label userId;

    @FXML
   private TextField firstName;

    @FXML
    private TextField lastName;

   @FXML
    private DatePicker dob;
   
   @FXML
    private DatePicker dob1;

    @FXML
    private ToggleGroup gender;

    @FXML
    private RadioButton rbFemale;

    @FXML
    private RadioButton rbMale;

    @FXML
    private Button reset;

    @FXML
    private Button saveUser;
    
    @FXML
    private Button openBack;
    
      @FXML
    private Button exit;
      
        @FXML
    private Button deleteCustomers;

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, Long> colUserId;

    @FXML
    private TableColumn<User, String> colFirstName;

    @FXML
    private TableColumn<User, String> colLastName;

    @FXML
    private TableColumn<User, LocalDate> colDOB;
    
     @FXML
    private TableColumn<User, LocalDate> colDOB1;

    @FXML
    private TableColumn<User, String> colGender;

    @FXML
    private TableColumn<User, Boolean> colEdit;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private UserService userService;

    private ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    private void exit(ActionEvent event) {
        stageManager.switchScene(FxmlView.LOGINVIEW);
    }

    @FXML
    void reset(ActionEvent event) {
       clearFields();
   }
        @FXML
    private void openBack (ActionEvent event){
        stageManager.switchScene(FxmlView.DASHBOARD);
    }

  @FXML
  private void saveUser(ActionEvent event) {

      if (validate("First Name", getFirstName(), "([a-zA-Z]{3,30}\\s*)+")
               && validate("Last Name", getLastName(), "([a-zA-Z]{3,30}\\s*)+")
                && emptyValidation("DOB", dob.getEditor().getText().isEmpty())) {

          if (userId.getText() == null || "".equals(userId.getText())) {
               if (true) {

                    User user = new User();
                   user.setFirstName(getFirstName());
                   user.setLastName(getLastName());
                   user.setDob();
                   user.setDob1();
                   user.setGender(getGender());

                   User newUser = userService.save(user);

                    saveAlert(newUser);
                }

            } else {
               User user = userService.find(Long.parseLong(userId.getText()));
              user.setFirstName(getFirstName());
               user.setLastName(getLastName());
                user.setDob();
                user.setDob1();
                user.setGender(getGender());
                User updatedUser = userService.update(user);
                updateAlert(updatedUser);
            }

            clearFields();
            loadUserDetails();
        }

    }

    @FXML
    private void deleteCustomers(ActionEvent event) {
        List<User> users = userTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            userService.deleteInBatch(users);
        }

        loadUserDetails();
    }

    private void clearFields() {
        userId.setText(null);
        firstName.clear();
        lastName.clear();
       dob.getEditor().clear();
          dob1.getEditor().clear();
        rbMale.setSelected(true);
        rbFemale.setSelected(false);
    }

    private void saveAlert(User user) {

       Alert alert = new Alert(AlertType.INFORMATION);
       alert.setTitle("User saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The user " + user.getFirstName() + " " + user.getLastName() + " has been created and \n" + getGenderTitle(user.getGender()) + " id is " + user.getId() + ".");
        alert.showAndWait();
   }

    private void updateAlert(User user) {

       Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("User updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The user " + user.getFirstName() + " " + user.getLastName() + " has been updated.");
        alert.showAndWait();
    }

    private String getGenderTitle(String gender) {
       return (gender.equals("Male")) ? "his" : "her";
    }

    public String getFirstName() {
        return firstName.getText();
    }

    public String getLastName() {
        return lastName.getText();
    }

    public LocalDate getDob() {
        return dob.getValue();
   }
  public LocalDate getDob1() {
        return dob1.getValue();
   }
  
   public String getGender() {
        return rbMale.isSelected() ? "Male" : "Female";
    }


    /*
	 *  Set All userTable column properties
     */
   private void setColumnProperties() {
      /* Override date format in table
		 * colDOB.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<LocalDate>() {
			 String pattern = "dd/MM/yyyy";
			 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
		     @Override 
		     public String toString(LocalDate date) {
		         if (date != null) {
		             return dateFormatter.format(date);
		         } else {
		             return "";
		         }
		     }

		     @Override 
		     public LocalDate fromString(String string) {
		         if (string != null && !string.isEmpty()) {
		             return LocalDate.parse(string, dateFormatter);
		         } else {
		             return null;
		         }
		     }
		 }));*/

        colUserId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
          colDOB1.setCellValueFactory(new PropertyValueFactory<>("dob1"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colEdit.setCellFactory(cellFactory);
    }

    Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>> cellFactory
            = new Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>>() {
        @Override
        public TableCell<User, Boolean> call(final TableColumn<User, Boolean> param) {
            final TableCell<User, Boolean> cell = new TableCell<User, Boolean>() {
                Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
                final Button btnEdit = new Button();

                @Override
                public void updateItem(Boolean check, boolean empty) {
                    super.updateItem(check, empty);
                    if (empty) {
                        setGraphic(null);
                       setText(null);
                   } else {
                        btnEdit.setOnAction(e -> {
                            User user = getTableView().getItems().get(getIndex());
                            updateUser(user);
                        });

                        btnEdit.setStyle("-fx-background-color: transparent;");
                        ImageView iv = new ImageView();
                        iv.setImage(imgEdit);
                        iv.setPreserveRatio(true);
                        iv.setSmooth(true);
                        iv.setCache(true);
                        btnEdit.setGraphic(iv);

                        setGraphic(btnEdit);
                        setAlignment(Pos.CENTER);
                       setText(null);
                  }
                }

               private void updateUser(User user) {
                    userId.setText(Long.toString(user.getId()));
                    firstName.setText(user.getFirstName());
                   lastName.setText(user.getLastName());
                   dob.setValue(user.getDob());
                    dob1.setValue(user.getDob1());
                   if (user.getGender().equals("Male")) {
                        rbMale.setSelected(true);
                    } else {
                        rbFemale.setSelected(true);
                    }
               }
           };
            return cell;
        }
    };

    /*
	 *  Add All users to observable list and update table
     */
    private void loadUserDetails() {
        userList.clear();
        userList.addAll(userService.findAll());

        userTable.setItems(userList);
    }

    /*
	 * Validations
     */
    private boolean validate(String field, String value, String pattern) {
        if (!value.isEmpty()) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(value);
            if (m.find() && m.group().equals(value)) {
                return true;
            } else {
                validationAlert(field, false);
                return false;
            }
        } else {
    validationAlert(field,
            
    true);


return false;
        }
    }

    private boolean emptyValidation(String field, boolean empty) {
        if (!empty) {
            return true;
        } else {
           validationAlert(field, true);
            return false;
        }
    }

    private void validationAlert(String field, boolean empty) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        if (field.equals("Role")) {
            alert.setContentText("Please Select " + field);
        } else {
            if (empty) {
               alert.setContentText("Please Enter " + field);
            } else {
                alert.setContentText("Please Enter Valid " + field);
            }
        }
        alert.showAndWait();
    }
    @Override
     public void initialize(URL location, ResourceBundle resources) { 
        userTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setColumnProperties();

        // Add all users into table
        loadUserDetails();
    }
}

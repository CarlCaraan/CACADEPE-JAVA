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
import com.oopclass.breadapp.models.Pricelist;
import com.oopclass.breadapp.services.impl.PricelistService;
import com.oopclass.breadapp.views.FxmlView;
import javafx.application.Platform;

//import javafx.application.Platform;
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
public class PricelistController implements Initializable {

    @FXML
    private Label productId;

    @FXML
    private TextField productName;

     @FXML
    private TextField type;

    @FXML
    private TextField price;

    @FXML
    private DatePicker created;

    @FXML
    private DatePicker updated;
    
    @FXML
    private Button reset;

    @FXML
    private Button saveProduct;
    
    @FXML
    private Button openBack;
    
    @FXML
    private Button exit;
    
      @FXML
    private Button deleteStocks;

    @FXML
    private TableView<Pricelist> productTable;

    @FXML
    private TableColumn<Pricelist, Long> colProductId;

    @FXML
    private TableColumn<Pricelist, String> colProductName;
    
     @FXML
    private TableColumn<Pricelist, String> coltype;

    @FXML
    private TableColumn<Pricelist, Double> colprice;

    @FXML
    private TableColumn<Pricelist, LocalDate> colcreated;

    @FXML
    private TableColumn<Pricelist, LocalDate> colupdated;
   
    @FXML
    private TableColumn<Pricelist, Boolean> colEdit;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private PricelistService pricelistService;

    private ObservableList<Pricelist> productList = FXCollections.observableArrayList();

    @FXML
    private void exit(ActionEvent event) {
        stageManager.switchScene(FxmlView.LOGINVIEW);
    }

    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }
    @FXML
    private void openBack(ActionEvent event) {
        stageManager.switchScene(FxmlView.DASHBOARD);
    }
    @FXML
    private void saveProduct(ActionEvent event) {

        if (validate("Product Name", getProductName(), "([a-zA-Z]{3,30}\\s*)+")) {
       
            if (productId.getText() == null || "".equals(productId.getText())) {
                if (true) {

                    Pricelist product = new Pricelist();
                    product.setProductName(getProductName());
                    product.setType(getType());
                    product.setPrice(getPrice());
                    product.setCreated(getCreated());
                    product.setUpdated(getUpdated());
                    Pricelist newProduct = pricelistService.save(product);

                    saveAlert(newProduct);
                }

            } else {
                Pricelist product = pricelistService.find(Long.parseLong(productId.getText()));
                product.setProductName(getProductName());
                    product.setType(getType());
                    product.setPrice(getPrice());
                    product.setCreated(getCreated());
                    product.setUpdated(getUpdated());
                Pricelist updatedProduct = pricelistService.update(product);
                updateAlert(updatedProduct);
            }

            clearFields();
            loadProductDetails();
        }

    }

    @FXML
    private void deleteStocks(ActionEvent event) {
        List<Pricelist> products = productTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            pricelistService.deleteInBatch(products);
        }

        loadProductDetails();
    }

    private void clearFields() {
        productId.setText(null);
        productName.clear();
        type.clear();
        price.clear();
        created.getEditor().clear();
        updated.getEditor().clear();
    }

    private void saveAlert(Pricelist product) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Product saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The product " + product.getProductName() +  " is created with ID: " + product.getId() + ".");
        alert.showAndWait();
    }

    private void updateAlert(Pricelist product) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Product updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The product " + product.getProductName() +  " has been updated.");
        alert.showAndWait();
    }
    
    public String getProductName() {
        return productName.getText();
    }
    
    public String getType() {
        return type.getText();
    }
    public Double getPrice() {
        return Double.parseDouble(price.getText());
    }
    public LocalDate getCreated() {
        return created.getValue();
    }
    public LocalDate getUpdated() {
        return updated.getValue();
    }
    
    /*
	 *  Set All productTable column properties
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

        colProductId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        coltype.setCellValueFactory(new PropertyValueFactory<>("type"));
        colprice.setCellValueFactory(new PropertyValueFactory<>("price"));
         colcreated.setCellValueFactory(new PropertyValueFactory<>("created"));
         colupdated.setCellValueFactory(new PropertyValueFactory<>("updated"));
        colEdit.setCellFactory(cellFactory);
    }

    Callback<TableColumn<Pricelist, Boolean>, TableCell<Pricelist, Boolean>> cellFactory
            = new Callback<TableColumn<Pricelist, Boolean>, TableCell<Pricelist, Boolean>>() {
        @Override
        public TableCell<Pricelist, Boolean> call(final TableColumn<Pricelist, Boolean> param) {
            final TableCell<Pricelist, Boolean> cell = new TableCell<Pricelist, Boolean>() {
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
                            Pricelist product = getTableView().getItems().get(getIndex());
                            updateProduct(product);
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

                private void updateProduct(Pricelist product) {
                    productId.setText(Long.toString(product.getId()));
                    productName.setText(product.getProductName());
                    type.setText(product.getType());
                    price.setText(Double.toString(product.getPrice()));
                     created.setValue(product.getCreated());
                      updated.setValue(product.getUpdated());
                }
            };
            return cell;
        }
    };

    /*
	 *  Add All products to observable list and update table
     */
    private void loadProductDetails() {
        productList.clear();
        productList.addAll(pricelistService.findAll());

        productTable.setItems(productList);
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
            validationAlert(field, true);
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

        productTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setColumnProperties();

        // Add all users into table
        loadProductDetails();
    }
}
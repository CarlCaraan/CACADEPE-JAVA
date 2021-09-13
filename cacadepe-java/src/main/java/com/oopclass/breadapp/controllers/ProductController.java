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
import com.oopclass.breadapp.models.Product;
import com.oopclass.breadapp.services.impl.ProductService;
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
public class ProductController implements Initializable {

    @FXML
    private Label productId;

    @FXML
    private TextField productName;
    
    @FXML
    private TextField type;

    @FXML
    private TextField quantity;

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
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, Long> colProductId;

    @FXML
    private TableColumn<Product, String> colProductName;
    
    @FXML
    private TableColumn<Product, String> coltype;

    @FXML
    private TableColumn<Product, Double> colquantity;

    @FXML
    private TableColumn<Product, LocalDate> colcreated;

    @FXML
    private TableColumn<Product, LocalDate> colupdated;
   
    @FXML
    private TableColumn<Product, Boolean> colEdit;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private ProductService productService;

    private ObservableList<Product> productList = FXCollections.observableArrayList();

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

                    Product product = new Product();
                    product.setProductName(getProductName());
                     product.setType(getType());
                    product.setQuantity(getQuantity());
                    product.setCreated(getCreated());
                    product.setUpdated(getUpdated());
                    Product newProduct = productService.save(product);

                    saveAlert(newProduct);
                }

            } else {
                Product product = productService.find(Long.parseLong(productId.getText()));
                product.setProductName(getProductName());
                 product.setType(getType());
                    product.setQuantity(getQuantity());
                    product.setCreated(getCreated());
                    product.setUpdated(getUpdated());
                Product updatedProduct = productService.update(product);
                updateAlert(updatedProduct);
            }

            clearFields();
            loadProductDetails();
        }

    }

    @FXML
    private void deleteStocks(ActionEvent event) {
        List<Product> products = productTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            productService.deleteInBatch(products);
        }

        loadProductDetails();
    }

    private void clearFields() {
        productId.setText(null);
        productName.clear();
        type.clear();
        quantity.clear();
        created.getEditor().clear();
        updated.getEditor().clear();
    }

    private void saveAlert(Product product) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Product saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The product " + product.getProductName() +  " is created with ID: " + product.getId() + ".");
        alert.showAndWait();
    }

    private void updateAlert(Product product) {

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
    public Double getQuantity() {
        return Double.parseDouble(quantity.getText());
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
        colquantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
         colcreated.setCellValueFactory(new PropertyValueFactory<>("created"));
         colupdated.setCellValueFactory(new PropertyValueFactory<>("updated"));
        colEdit.setCellFactory(cellFactory);
    }

    Callback<TableColumn<Product, Boolean>, TableCell<Product, Boolean>> cellFactory
            = new Callback<TableColumn<Product, Boolean>, TableCell<Product, Boolean>>() {
        @Override
        public TableCell<Product, Boolean> call(final TableColumn<Product, Boolean> param) {
            final TableCell<Product, Boolean> cell = new TableCell<Product, Boolean>() {
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
                            Product product = getTableView().getItems().get(getIndex());
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

                private void updateProduct(Product product) {
                    productId.setText(Long.toString(product.getId()));
                    productName.setText(product.getProductName());
                     type.setText(product.getType());
                    quantity.setText(Double.toString(product.getQuantity()));
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
        productList.addAll(productService.findAll());

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
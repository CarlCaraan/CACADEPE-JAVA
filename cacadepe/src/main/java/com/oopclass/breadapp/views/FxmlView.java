package com.oopclass.breadapp.views;

import java.util.ResourceBundle;

/**
 * OOP Class 20-21
 *
 * @author Gerald Villaran
 */
public enum FxmlView {

    PRODUCT{
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("product.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Product.fxml";
        }
    }, USER{
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("user.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/User.fxml";
        }
    }, LOGINVIEW {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("loginView.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/loginView.fxml";
        }
    }, DASHBOARD {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("Dashboard.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Dashboard.fxml";
        }
    }, PRICELIST{
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("Pricelist.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Pricelist.fxml";
        }};
    

    public abstract String getTitle();

    public abstract String getFxmlFile();

    String getStringFromResourceBundle(String key) {
        return ResourceBundle.getBundle("Bundle").getString(key);
    }

}

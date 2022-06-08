package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class generates the Main Screen of the Inventory Management System and populates the available parts and products. */
public class MainScreen implements Initializable {

    public TableView<Part> partsTable;
    public TableColumn<Part, Integer> partIDCol;
    public TableColumn<Part, String> partNameCol;
    public TableColumn<Part, Integer> partInventoryCol;
    public TableColumn<Part, Double> partPriceCol;

    public TableView<Product> productsTable;
    public TableColumn<Product, Integer> productIDCol;
    public TableColumn<Product, String> productNameCol;
    public TableColumn<Product, Integer> productInventoryCol;
    public TableColumn<Product, Double> productPriceCol;

    public Button addPartButton;
    public Button modifyPartButton;
    public Button deletePartButton;
    public Button addProductButton;
    public Button modifyProductButton;
    public Button deleteProductButton;
    public Button exitInventoryManagementSystem;
    public TextField partSearch;
    public TextField productSearch;

    public static Part selectedPart;

    public static Product selectedProduct;

    /** This method initializes the parts table and products table of the main screen and allows for the insertion of test data.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    partsTable.setItems(Inventory.getAllParts());

    productIDCol.setCellValueFactory(new PropertyValueFactory<>("productID"));
    productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    productsTable.setItems(Inventory.getAllProducts());

    }

    /** This method launches the add part form when the add part button is selected.
     * @param actionEvent The add part button is clicked.
     */
    public void onAddPartButton(ActionEvent actionEvent) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/view/AddPartForm.fxml"));
            Stage addPartStage = new Stage();
            addPartStage.setScene(new Scene(root, 600, 514));
            addPartStage.setTitle("Add Part Screen");
            addPartStage.show();
        }
        catch(Exception exception) {
            exception.printStackTrace();
        }
    }

    /** This method launches the modify part window when the add product button is clicked.
     * @param actionEvent The modify part button is clicked.
     */
    public void onModifyPartButton(ActionEvent actionEvent) {
        selectedPart = partsTable.getSelectionModel().getSelectedItem();
        if (partsTable.getSelectionModel().getSelectedItem() != null) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyPartForm.fxml"));
                Stage modifyPartStage = new Stage();
                modifyPartStage.setScene(new Scene(root, 600, 514));
                modifyPartStage.setTitle("Modify Part Screen");
                modifyPartStage.show();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Select Part to Modify");
            alert.setContentText("Please select a part to modify.");
            alert.showAndWait();
        }
    }

    /** This method allows a selected part from the parts table on the main screen to be deleted from the inventory management system.
     * @param actionEvent Delete part button is clicked.
     */
    public void onDeletePartButton(ActionEvent actionEvent) {

        if (partsTable.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You are about to delete " + partsTable.getSelectionModel().getSelectedItem().getName() + ". Do you wish to proceed?");
            alert.setTitle("Delete Part Confirmation");


            Optional<ButtonType> clickedOK = alert.showAndWait();

            if(clickedOK.isPresent() && clickedOK.get() == ButtonType.OK) {
                Inventory.getAllParts().remove(partsTable.getSelectionModel().getSelectedItem());
            }
        }

        //Change below to reduce redundancy and improve user efficiency while using Part Delete

        /*if (partsTable.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete selected part?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Delete Part Confirmation");

            Optional<ButtonType> clickedButton = alert.showAndWait();

            if (clickedButton.isPresent() && clickedButton.get() == ButtonType.YES) {
                boolean deleted = Inventory.deletePart(partsTable.getSelectionModel().getSelectedItem());
                if (!deleted) {
                    Alert deleteAlert = new Alert(Alert.AlertType.ERROR, "Selected Part Could Not Be Deleted");
                    alert.showAndWait();
                }
                else {
                    Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION, "Part was successfully deleted", ButtonType.OK);
                    alertConfirmation.setTitle("Part Deleted");
                    alert.showAndWait();
                }
            }

        else {
            Alert noPartSelectedAlert = new Alert(Alert.AlertType.ERROR, "No part selected to delete");
            noPartSelectedAlert.showAndWait();
        }
    }*/
    }

    /** This method launches the add product window when the add product button is clicked.
     * @param actionEvent The add product button is clicked.
     */
    public void onAddProductButton(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/AddProductForm.fxml"));
            Stage addProductStage = new Stage();
            addProductStage.setScene(new Scene(root, 800, 500));
            addProductStage.setTitle("Add Product Screen");
            addProductStage.show();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    /** This method launches the modify product window when the modify product button is clicked.
     * @param actionEvent Modify part button is clicked.
     */
    public void onModifyProductButton(ActionEvent actionEvent) {
        selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        if (productsTable.getSelectionModel().getSelectedItem() != null) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyProductForm.fxml"));
                Stage modifyProductStage = new Stage();
                modifyProductStage.setScene(new Scene(root, 800, 500));
                modifyProductStage.setTitle("Modify Product Screen");
                modifyProductStage.show();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Select Product to Modify");
                alert.setContentText("Please select a product to modify.");
                alert.showAndWait();
            }
        }

    /** This method allows for a selected product to be deleted from the Inventory Management System after it checks that it has no associated parts when the delete product button is clicked.
     * @param actionEvent Delete product button is clicked.
     */
    public void onDeleteProductButton(ActionEvent actionEvent) {
        Product product = (Product) productsTable.getSelectionModel().getSelectedItem();
        if (product == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Product Selected!");
            alert.setContentText("Please select a product to delete before clicking delete button.");
            alert.showAndWait();
        }
        else {
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete product? Click OK to confirm delete.");
            alert2.setTitle("Product Delete Confirmation");
            Optional<ButtonType> clickedOK = alert2.showAndWait();
            if (clickedOK.isPresent() && clickedOK.get() == ButtonType.OK){
                ObservableList<Part> associatedParts = product.getAllAssociatedParts();
                if (associatedParts.size() > 0) {
                    Alert alert3 = new Alert(Alert.AlertType.ERROR);
                    alert3.setTitle("Associated Parts Present!");
                    alert3.setContentText("Unable to delete " + productsTable.getSelectionModel().getSelectedItem().getName() + " because it has associated parts. Please remove associated parts before deleting.");
                    alert3.showAndWait();
                }
                else {
                        Inventory.deleteProduct(product);
                }
            }
        }
    }

    //Change below to reduce redundancy and improve user efficiency while using Product Delete

           /* Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Product Selected!");
            alert.setContentText("Please select a product to delete before clicking delete button.");
            alert.showAndWait();
        } else {
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete product? Click OK to confirm delete.");
            alert2.setTitle("Product Delete Confirmation");
            Optional<ButtonType> clickedOK = alert2.showAndWait();
            ObservableList<Part> associatedPartsOfProduct = ;
            if (clickedOK.isPresent() && clickedOK.get() == ButtonType.OK) {
                associatedPartsOfProduct = product.getAllAssociatedParts();
                Inventory.deleteProduct(product);
            }
            if (associatedPartsOfProduct.size() > 0) {
                Alert alert3 = new Alert(Alert.AlertType.ERROR);
                alert3.setTitle("Associated Parts Present!");
                alert3.setContentText("Unable to delete " + partsTable.getSelectionModel().getSelectedItem().getName() + " because it has associated parts. Please remove associated parts before deleting.");
                alert3.showAndWait();
            }*/





       /* if (productsTable.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Product Confirmation");
            alert.setContentText("Are you sure you want to delete " + partsTable.getSelectionModel().getSelectedItem().getName());
            Optional<ButtonType> clickedButton = alert.showAndWait();

            if(clickedButton.isPresent() && clickedButton.get() == ButtonType.YES) {
                int id = productsTable.getSelectionModel().getSelectedItem().getProductID();

            if (Inventory.lookupProduct(id).getAllAssociatedParts().isEmpty()) {
                boolean isDeleted = Inventory.deleteProduct(productsTable.getSelectionModel().getSelectedItem());

                if (isDeleted == false) {
                    Alert isNotDeletedAlert = new Alert(Alert.AlertType.ERROR, "Unable to delete " + partsTable.getSelectionModel().getSelectedItem().getName());
                }
                else {
                    Alert confirmDeleteAlert = new Alert(Alert.AlertType.CONFIRMATION, productsTable.getSelectionModel().getSelectedItem().getName() + " was successfully deleted");
                    alert.setTitle("Product Delete Confirmation");
                    alert.showAndWait();
                }
            }

            else {
                Alert associatedPartsAlert = new Alert(Alert.AlertType.ERROR, productsTable.getSelectionModel().getSelectedItem().getName() + " unable to be deleted because it is comprised of existing associated parts. If you wish to delete, you must first delete associated parts.");
                associatedPartsAlert.showAndWait();
            }
        }
        }
        else {
            Alert noProductSelectedAlert = new Alert(Alert.AlertType.ERROR, "No product selected to delete");
            noProductSelectedAlert.showAndWait();
        }*/


    /** This method allows the system to be exited from the main screen when the exit button is clicked.
     * @param actionEvent Exit button is clicked.
     */
    public void onExitInventoryManagementSystem(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit the Inventory Managemnent System?");
        alert.setTitle("Confirm Exit");
        Optional<ButtonType> clickedButton = alert.showAndWait();
        if(clickedButton.isPresent() && clickedButton.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    /** This method allows for the parts table to be searched for parts by id or name regardless of partial text or id and also ignores case.
     * @param actionEvent The text is entered into the search field.
     */
    public void onPartSearch(ActionEvent actionEvent) {
        String part = partSearch.getText();
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> foundParts = FXCollections.observableArrayList();

        for (Part p : allParts) {
            if (String.valueOf(p.getId()).toLowerCase(Locale.ROOT).contains(part.toLowerCase(Locale.ROOT)) || p.getName().toLowerCase(Locale.ROOT).contains(part.toLowerCase(Locale.ROOT))) {
                foundParts.add(p);
                partsTable.setItems(foundParts);
                partSearch.clear();
            }
        }

        if(foundParts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Search Failed");
            alert.setContentText("No Matching Part ID or Name Found");
            alert.showAndWait();
            partsTable.setItems(Inventory.getAllParts());
            partSearch.clear();
        }


    }

    /** This method allows for the products table to be searched for products by id or name regardless of partial text or id and also ignores case.
     * @param actionEvent The text is entered into the search field.
     */
    public void onProductSearch(ActionEvent actionEvent) {
        String product = productSearch.getText();
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();

        for (Product p : allProducts) {
            if (String.valueOf(p.getProductID()).toLowerCase().contains(product.toLowerCase(Locale.ROOT)) || p.getName().toLowerCase().contains(product.toLowerCase(Locale.ROOT))) {
                foundProducts.add(p);
                productsTable.setItems(foundProducts);
                productSearch.clear();
            }
        }

        if(foundProducts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Search Failed");
            alert.setContentText("No Matching Product ID or Name Found");
            alert.showAndWait();
            productsTable.setItems(Inventory.getAllProducts());
            productsTable.setItems(Inventory.getAllProducts());
            productSearch.clear();
        }

    }
}

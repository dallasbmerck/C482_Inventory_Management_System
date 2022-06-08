package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static controller.MainScreen.selectedProduct;

/** This class generates the Modify Product Window and initializes the data into the text field of the window and initializes the parts tables with available and associated parts. */
public class ModifyProductForm implements Initializable {

    public TextField modifyProductIdField;
    public TextField modifyProductNameField;
    public TextField modifyProductInvField;
    public TextField modifyProductPriceField;
    public TextField modifyProductMaxField;
    public TextField partSearchField;
    public TableView<Part> modifyProductAvailableParts;
    public TableColumn<Part, Integer> availablePartIDCol;
    public TableColumn<Part, String> availablePartNameCol;
    public TableColumn<Part, Integer> availablePartInvCol;
    public TableColumn<Part, Double> availablePartPriceCol;
    public Button addPartToProductButton;
    public TableView<Part> modifyProductAssociatedParts;
    public TableColumn<Part, Integer> associatedPartIDCol;
    public TableColumn<Part, String> associatedPartNameCol;
    public TableColumn<Part, Integer> associatedPartInvCol;
    public TableColumn<Part, Double> associatedPartPriceCol;
    public Button removeAssociatedPartButton;
    public Button saveModifiedProductButton;
    public Button cancelModifiedProductButton;
    public TextField modifyProductMinField;

    ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /** This method populate the text field of the modify product window with the data from the selected product. */
    public void populateProductToModify() {
        modifyProductIdField.setText(String.valueOf(selectedProduct.getProductID()));
        modifyProductNameField.setText(selectedProduct.getName());
        modifyProductInvField.setText(String.valueOf(selectedProduct.getStock()));
        modifyProductPriceField.setText(String.valueOf(selectedProduct.getPrice()));
        modifyProductMaxField.setText(String.valueOf(selectedProduct.getMax()));
        modifyProductMinField.setText(String.valueOf(selectedProduct.getMin()));

        associatedParts = selectedProduct.getAllAssociatedParts();

        modifyProductAssociatedParts.setItems(associatedParts);
    }

    /** This method allows for available parts to be searched so that they may be associated to the modified product.
     * @param actionEvent Text is entered into the search field.
     */
    public void onPartSearchField(ActionEvent actionEvent) {
        String searchedPart = partSearchField.getText();
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> foundParts = FXCollections.observableArrayList();

        for (Part p : allParts) {
            if (String.valueOf(p.getId()).toLowerCase(Locale.ROOT).contains(searchedPart.toLowerCase(Locale.ROOT)) || p.getName().toLowerCase(Locale.ROOT).contains(searchedPart.toLowerCase(Locale.ROOT))) {
                foundParts.add(p);
                modifyProductAvailableParts.setItems(foundParts);
                partSearchField.clear();
            }
        }
    }

    /** This adds associated parts to the modified product when a part is selected and the add button is clicked.
     * @param actionEvent Add button is clicked.
     */
    public void onAddPartToProductButton(ActionEvent actionEvent) {
        Part clickedPart = modifyProductAvailableParts.getSelectionModel().getSelectedItem();
        if (clickedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Part Selected!");
            alert.setContentText("Select a part to add before pressing the add button.");
            alert.showAndWait();
        }
        else {
            associatedParts.add(clickedPart);
            modifyProductAssociatedParts.setItems(associatedParts);
        }
    }

    /** This method remove associated parts from the associated parts table to dissociate them with the modified product.
     * @param actionEvent The remove associated part button is clicked.
     */
    public void onRemoveAssociatedPartButton(ActionEvent actionEvent) {
        Part clickedPartToRemove = modifyProductAssociatedParts.getSelectionModel().getSelectedItem();
        if (clickedPartToRemove == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Part Selected!");
            alert.setContentText("Select a part to remove before presssing the Remove Associated Part button.");
            alert.showAndWait();
        }
        else {
            associatedParts.remove(clickedPartToRemove);
            modifyProductAssociatedParts.setItems(associatedParts);
        }
    }

    /** This method allows the modified product to be saved the Inventory Management System.
     * @param actionEvent Save button is clicked.
     */
    public void onSaveModifiedProductButton(ActionEvent actionEvent) {
        try {
            int productID = selectedProduct.getProductID();
            String productName = modifyProductNameField.getText();
            int productStock = Integer.parseInt(modifyProductInvField.getText());
            double productPrice = Double.parseDouble(modifyProductPriceField.getText());
            int productMax = Integer.parseInt(modifyProductMaxField.getText());
            int productMin = Integer.parseInt(modifyProductMinField.getText());

            if (productStock > productMax || productStock < productMin) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input Criteria!");
                alert.setContentText("Invalid input. Please make sure max is greater than min and stock is greater than min and less than max.");
                alert.showAndWait();
            }
            else if (productMax < 0 || productStock < 0 || productMin < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input Criteria!");
                alert.setContentText("Max, min, and inv must be greater than 0.");
                alert.showAndWait();
            }
            else {
                Product modifiedProduct = new Product(productID, productName, productPrice, productStock,productMin, productMax);
                for (Part part : associatedParts) {
                    modifiedProduct.addAssociatedParts(part);
                }
                Inventory.modifyProduct(modifiedProduct);
                Inventory.deleteProduct(selectedProduct);
                Stage stage = (Stage) saveModifiedProductButton.getScene().getWindow();
                stage.close();
            }
        }
        catch (NumberFormatException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Entry!");
            alert.setContentText("Please enter valid data into each text field.");
            alert.showAndWait();
        }
    }

    /** This method allows for a user to cancel any modified data entered to be deleted before being saved and applied to the selected product.
     * @param actionEvent Cancel button is clicked.
     */
    public void onCancelModifiedProductButton(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Discard Product Confirmation");
        alert.setContentText("Are you sure you want to discard the product without saving?");
        Optional<ButtonType> clickedOK = alert.showAndWait();

        if (clickedOK.isPresent() && clickedOK.get() == ButtonType.OK) {
            Stage stage = (Stage) cancelModifiedProductButton.getScene().getWindow();
            stage.close();
        }
    }

    /** This method initializes the Modify product text field with the data from the selected products and populates the part's tables with the proper data.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifyProductAvailableParts.setItems(Inventory.getAllParts());
        availablePartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        availablePartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        availablePartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        availablePartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        populateProductToModify();
    }
}

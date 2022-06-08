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
import java.util.Random;
import java.util.ResourceBundle;

/** This class generates the Add Product window where a user can add a new product to the Inventory Management System. */
public class AddProductForm implements Initializable {

    public TextField addProductIdField;
    public TextField addProductNameField;
    public TextField addProductInvField;
    public TextField addProductPriceField;
    public TextField addProductMaxField;
    public TextField searchPartField;
    public TableView<Part> addProductAvailableParts;
    public TableColumn<Part, Integer> availablePartIDCol;
    public TableColumn<Part, String> availablePartNameCol;
    public TableColumn<Part, Integer> availableInvCol;
    public TableColumn<Part, Double> availablePartPriceCol;
    public Button addPartToProductButton;
    public TableView<Part> addProductAssociatedParts;
    public TableColumn<Part, Integer> associatedPartIDCol;
    public TableColumn<Part, String> associatedPartNameCol;
    public TableColumn<Part, Integer> associatedInvCol;
    public TableColumn<Part, Double> associatedPartPriceCol;
    public Button removeAssociatedPartButton;
    public Button saveNewProductButton;
    public Button cancelProductButton;
    public TextField addProductMinField;

    ObservableList<Part> associatedPartsForProduct = FXCollections.observableArrayList();

    Random random = new Random();
    int generatedProductID;
    boolean isAMatch;

    /** This method generated the unique productID.
     * @return newRandom productID.
     */
    public int setGeneratedProductID() {
        int newRandom;
        newRandom = 1 + random.nextInt(9999999);

        for (Product product : Inventory.getAllProducts()) {
            if (product.getProductID() == newRandom) {
                isAMatch = true;
                setGeneratedProductID();
            }
        }
        return newRandom;
    }

    /** This method allows user to search for available parts to add to the add new product.
     * @param actionEvent Text entered into the search field.
     */
    public void onSearchPartField(ActionEvent actionEvent) {
        String searchedPart = searchPartField.getText();
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> foundParts = FXCollections.observableArrayList();

        for (Part p : allParts) {
            if (String.valueOf(p.getId()).toLowerCase(Locale.ROOT).contains(searchedPart.toLowerCase(Locale.ROOT)) || p.getName().toLowerCase(Locale.ROOT).contains(searchedPart.toLowerCase(Locale.ROOT))) {
                foundParts.add(p);
                addProductAvailableParts.setItems(foundParts);
                searchPartField.clear();
            }
        }

        if (foundParts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Search Failed");
            alert.setContentText("No Matching Part ID or Name Found");
            alert.showAndWait();
            addProductAvailableParts.setItems(Inventory.getAllParts());
        }
    }

    /** This method allows parts to be associated to products through the add part button.
     * @param actionEvent The add part button is clicked.
     */
    public void onAddPartToProductButton(ActionEvent actionEvent) {
        Part clickedPart = addProductAvailableParts.getSelectionModel().getSelectedItem();
        if (clickedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Part Selected!");
            alert.setContentText("Select a part to add before pressing the add button.");
            alert.showAndWait();
        }
        else {
            associatedPartsForProduct.add(clickedPart);
            addProductAssociatedParts.setItems(associatedPartsForProduct);
        }
    }

    /** This method allows for the associated parts to be removed from a product.
     * @param actionEvent The remove associated part button is clicked.
     */
    public void onRemoveAssociatedPartButton(ActionEvent actionEvent) {
        Part clickedPartToRemove = addProductAssociatedParts.getSelectionModel().getSelectedItem();
        if (clickedPartToRemove == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Part Selected!");
            alert.setContentText("Select a part to remove before presssing the Remove Associated Part button.");
            alert.showAndWait();
        }
        else {
            associatedPartsForProduct.remove(clickedPartToRemove);
            addProductAssociatedParts.setItems(associatedPartsForProduct);
        }
    }

    /** This method allows for new products and their respective associated parts to be saved to the Inventory Management System.
     * @param actionEvent The save product button is clicked.
     */
    public void onSaveNewProductButton(ActionEvent actionEvent) {
        try {
            int newProductID = Integer.parseInt(addProductIdField.getText());
            String newProductName = addProductNameField.getText();
            int newProductStock = Integer.parseInt(addProductInvField.getText());
            double newProductPrice = Double.parseDouble(addProductPriceField.getText());
            int newProductMax = Integer.parseInt(addProductMaxField.getText());
            int newProductMin = Integer.parseInt(addProductMinField.getText());

            if (newProductStock > newProductMax || newProductStock < newProductMin) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input Criteria!");
                alert.setContentText("Invalid input. Please make sure max is greater than min and stock is greater than min and less than max.");
                alert.showAndWait();
            }
            else if (newProductMax < 0 || newProductStock < 0 || newProductMin < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input Criteria!");
                alert.setContentText("Max, min, and inv must be greater than 0.");
                alert.showAndWait();
            }
            else {
                Product newProduct = new Product(newProductID, newProductName, newProductPrice, newProductStock, newProductMin, newProductMax);
                for (Part part : associatedPartsForProduct) {
                    newProduct.addAssociatedParts(part);
                }
                Inventory.addProduct(newProduct);
                Stage stage = (Stage) saveNewProductButton.getScene().getWindow();
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

    /** This method allows a new product entry to be cancelled before it is saved and also closes the add product window.
     * @param actionEvent The cancel button is clicked.
     */
    public void onCancelProductButton(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Discard Product Confirmation");
        alert.setContentText("Are you sure you want to discard the product without saving?");
        Optional<ButtonType> clickedOK = alert.showAndWait();

        if (clickedOK.isPresent() && clickedOK.get() == ButtonType.OK) {
            Stage stage = (Stage) cancelProductButton.getScene().getWindow();
            stage.close();
        }
    }

    /** This method initializes the parts table in the add products screen as well as populated the ProductID field with a new random ID.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProductAvailableParts.setItems(Inventory.getAllParts());
        availablePartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        availablePartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        availableInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        availablePartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        setGeneratedProductID();
        addProductIdField.setText(Integer.toString(setGeneratedProductID()));
    }
}

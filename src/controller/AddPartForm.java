package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHousePart;
import model.Inventory;
import model.OutsourcedPart;
import model.Part;

import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

/** This class generates the Add Parts Window where a user can add a new parts to the Inventory Management System. */
public class AddPartForm implements Initializable {
    public RadioButton addInHousePartButton;
    public RadioButton addOutsourcedPartButton;
    public TextField addPartIDField;
    public TextField addPartNameField;
    public TextField addPartInvField;
    public TextField addPartPriceField;
    public TextField addPartMaxField;
    public TextField addPartMachineIdOrNameField;
    public Label addPartMachineIdOrNameLabel;
    public TextField addPartMinField;
    public Button addPartSaveButton;
    public Button addPartCancelButton;

    Random random = new Random();
    int randomPartID;
    boolean isAMatch;

    /** This method generates the unique partID for each new part to be added to the Inventory Management System.
     * @return newRandom a unique partID for the new part.
     */
    public int generatedPartID() {
        int newRandom;
        newRandom = 1 + random.nextInt(9999999);

        for (Part part : Inventory.getAllParts()) {
            if(part.getId() == newRandom) {
                isAMatch = true;
                generatedPartID();
            }
        }
        return newRandom;
    }

    /** This method changes the label that corresponds to the InHouse MachineID.
     * @param actionEvent When the InHouseRadioButton is selected.
     */
    public void onAddInHousePartButton(ActionEvent actionEvent) {
        addPartMachineIdOrNameLabel.setText("Machine ID");
    }

    /** This method changes the label that corresponds to the OurSourced CompanyName.
     * @param actionEvent When the OutSourcedRadioButton is selected.
     */
    public void onAddOutsourcedPartButton(ActionEvent actionEvent) {
        addPartMachineIdOrNameLabel.setText("Company Name");
    }

    /** This is the method that saves the new parts on once the save part button is selected.
     * @param actionEvent The save part button is clicked.
     */
    public void onAddPartSaveButton(ActionEvent actionEvent) {
        try {
            //int partID = addPartIDField.setText(setGeneratedPartID(random)););
            //int partID = setGeneratedPartID();
            int partID = Integer.parseInt(addPartIDField.getText());
            String partName = addPartNameField.getText();
            int partStock = Integer.parseInt(addPartInvField.getText());
            double partPrice = Double.parseDouble(addPartPriceField.getText());
            int partMax = Integer.parseInt(addPartMaxField.getText());
            int partMin = Integer.parseInt(addPartMinField.getText());

            if (partStock > partMax || partStock < partMin) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input Criteria!");
                alert.setContentText("Invalid input. Please make sure max is greater than min and stock is greater than min and less than max.");
                alert.showAndWait();
            }
            else if (partMax < 0 || partStock < 0 || partMin < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input Criteria!");
                alert.setContentText("Max, min, and inv must be greater than 0.");
                alert.showAndWait();
            }
            else {
                if (addInHousePartButton.isSelected()) {
                    int addPartMachineID = Integer.parseInt(addPartMachineIdOrNameField.getText());
                    Inventory.addPart(new InHousePart(partID, partName, partPrice, partStock, partMin, partMax, addPartMachineID));
                }
                else {
                    String partCompanyName = addPartMachineIdOrNameField.getText();
                    Inventory.addPart(new OutsourcedPart(partID, partName, partPrice, partStock, partMin, partMax, partCompanyName));
                }
                Stage stage = (Stage) addPartSaveButton.getScene().getWindow();
                stage.close();

                //Below revised to line 73

                /*Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                Stage stage = (Stage) addPartCancelButton.getScene().getWindow();
                stage.setTitle("Main Screen");
                stage.setScene(new Scene(root, 1200, 600));
                stage.show();*/

            }
        }
        catch (NumberFormatException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Entry!");
            alert.setContentText("Please enter valid data into each text field.");
            alert.showAndWait();
        }
    }

    /** This method allows the Add Part window to be closed and for any input data to be deleted.
     * @param actionEvent The cancel button is clicked.
     */
    public void onAddPartCancelButton(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Discard Part Confirmation");
        alert.setContentText("Are you sure you want to discard the part without saving?");
        Optional<ButtonType> clickedOK = alert.showAndWait();

        if (clickedOK.isPresent() && clickedOK.get() == ButtonType.OK) {
            Stage stage = (Stage) addPartCancelButton.getScene().getWindow();
            stage.close();
        }
    }

    /** This method initializes random partID to the add part screen.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        randomPartID = generatedPartID();
        addPartIDField.setText(Integer.toString(randomPartID));
    }
}

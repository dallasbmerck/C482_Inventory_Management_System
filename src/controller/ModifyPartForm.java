package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHousePart;
import model.Inventory;
import model.OutsourcedPart;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static controller.MainScreen.selectedPart;

/** This class generates the Modify Parts Form and initializes the data from the selected part into the respective fields. */
public class ModifyPartForm implements Initializable {

    public RadioButton modifyPartInHouseRadioButton;
    public RadioButton modifyOutsourcedPartButton;
    public TextField modifyPartIdField;
    public TextField modifyPartNameField;
    public TextField modifyPartInvField;
    public TextField modifyPartPriceField;
    public TextField modifyPartMaxField;
    public TextField modifyPartMachineIdOrNameField;
    public Label modifyPartMachineIdOrNameLabel;
    public TextField modifyPartMinField;
    public Button modifyPartSaveButton;
    public Button modifyPartCancelButton;

    /** This method populates the modify part text fields with the existing part's data. */
    public void populateInHousePart() {

        modifyPartIdField.setText(String.valueOf(selectedPart.getId()));
        modifyPartNameField.setText(selectedPart.getName());
        modifyPartInvField.setText(String.valueOf(selectedPart.getStock()));
        modifyPartMaxField.setText(String.valueOf(selectedPart.getMax()));
        modifyPartPriceField.setText(String.valueOf(selectedPart.getPrice()));
        modifyPartMinField.setText(String.valueOf(selectedPart.getMin()));

        if (selectedPart instanceof InHousePart) {
            modifyPartInHouseRadioButton.setSelected(true);
            modifyPartMachineIdOrNameField.setText(String.valueOf(((InHousePart) selectedPart).getMachineID()));
            modifyPartMachineIdOrNameLabel.setText("Machine ID");
        }
        else {
            modifyOutsourcedPartButton.setSelected(true);
            modifyPartMachineIdOrNameField.setText(((OutsourcedPart) selectedPart).getCompanyName());
            modifyPartMachineIdOrNameLabel.setText("Company Name");
        }
    }

    /** This method changes the label that corresponds to the InHouse MachineID.
     * @param actionEvent The InHouse radio button is selected.
     */
    public void onModifyInHousePartButton(ActionEvent actionEvent) {
        modifyPartMachineIdOrNameLabel.setText("Machine ID");
    }

    /** This method changes the label that corresponds to the OutSourced CompanyName.
     * @param actionEvent The OutSourced radio button is selected.
     */
    public void onModifyOutsourcedPartButton(ActionEvent actionEvent) {
        modifyPartMachineIdOrNameLabel.setText("Company Name");
    }

    /** This method allows the modified part to be saved to the Inventory Management System after checking that the data meets criteria.
     * @param actionEvent The save button is clicked.
     */
    public void onModifyPartSaveButton(ActionEvent actionEvent) {
        try {
            int partID = selectedPart.getId();
            String partName = modifyPartNameField.getText();
            int partStock = Integer.parseInt(modifyPartInvField.getText());
            double partPrice = Double.parseDouble(modifyPartPriceField.getText());
            int partMax = Integer.parseInt(modifyPartMaxField.getText());
            int partMin = Integer.parseInt(modifyPartMinField.getText());

            if (partStock > partMax || partStock < partMin) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid input criteria!");
                alert.setContentText("Invalid input. Please make sure max is greater than min and stock is greater than min and less than max.");
                alert.showAndWait();
            } else if (partMax < 0 || partStock < 0 || partMin < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid input criteria!");
                alert.setContentText("Max, min, and inv must be greater than 0.");
                alert.showAndWait();
            } else {
                if (modifyPartInHouseRadioButton.isSelected()) {
                    int addPartMachineID = Integer.parseInt(modifyPartMachineIdOrNameField.getText());
                    Inventory.addPart(new InHousePart(partID, partName, partPrice, partStock, partMin, partMax, addPartMachineID));
                } else {
                    if (modifyOutsourcedPartButton.isSelected()) {
                        String partCompanyName = modifyPartMachineIdOrNameField.getText();
                        Inventory.modifyPart(new OutsourcedPart(partID, partName, partPrice, partStock, partMin, partMax, partCompanyName));
                    }
                }
                Stage stage = (Stage) modifyPartSaveButton.getScene().getWindow();
                Inventory.deletePart(selectedPart);
                stage.close();
            }
        }

        catch(NumberFormatException exception){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Entry!");
                alert.setContentText("Please enter valid data into each text field.");
                alert.showAndWait();
            }
        }

    /** This method allows the newly modified part to be deleted before it is saved by pressing the cancel button.
     * @param actionEvent Cancel button is clicked.
     */
    public void onModifyPartCancelButton(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Discard Part Confirmation");
        alert.setContentText("Are you sure you want to discard the part without saving?");
        Optional<ButtonType> clickedOK = alert.showAndWait();

        if (clickedOK.isPresent() && clickedOK.get() == ButtonType.OK) {
            Stage stage = (Stage) modifyPartCancelButton.getScene().getWindow();
            stage.close();
        }
    }

    /** Initializes the modify part screen texts fields with the data from the selected part.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateInHousePart();
    }
}

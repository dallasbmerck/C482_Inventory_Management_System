package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class acts as an inventory system of all parts and products objects. */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** This method allows new parts to be added to the Inventory Management System alParts list.
     * @param newPart New part added to the allParts list.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /** This method allows new products to be added to the Inventory Management System allProducts list.
     * @param newProduct New product added to the allProducts list.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /** This method allows for a part to be modified and updated to the Inventory Management System without having issues of mismatching inv of parts that are associated with products.
     * @param selectedPart The part to be modified.
     */
    public static void modifyPart(Part selectedPart) {
        for(int i = 0; i < allParts.size(); i++) {
            if(allParts.get(i).getId() == selectedPart.getId()) {
                allParts.set(i, selectedPart);
            }
        }
    }

    /** This method allow for a product to be modified and updated to the Inventory Management System.
     * @param selectedProduct The part to be modified.
     */
    public static void modifyProduct(Product selectedProduct) {
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getProductID() == selectedProduct.getProductID()) {
                allProducts.set(i, selectedProduct);
            }
        }
    }

    /** This method allows for parts to be deleted from the main screen after a part has been modified in the modify part screen.
     * @param selectedPart The part selected from the main screen to be deleted.
     * @return Boolean true.
     */
    public static boolean deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
        return true;
    }

    /** This method allows for products to be deleted after checking if there are any associated parts it is comprised of.
     * @param selectedProduct The product to be deleted.
     * @return Boolean true else false.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if(allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        else {
            return false;
        }
    }

    /** This is the getter for the observable list of parts.
     * @return allParts list.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /** This is the getter for the observable list of products.
     * @return allProducts list.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }


}

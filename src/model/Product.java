package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This is the class that creates products for the Inventory Management System.
 * It allows products to be comprised of existing parts from the Inventory.
 */

public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /** This is the product constructor that creates a new instance of a product.
     *
     * @param id The id of a new product.
     * @param name The name of a new product.
     * @param price The price of a new product.
     * @param stock The stock of a new product.
     * @param min The min amount that can be held of a product.
     * @param max The max amount that can be held of a product.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** This method is used to set the name of a new product object.
     * @param name This is the new name for the new product object.
     */
    public void setName(String name) {

        this.name = name;
    }

    /** This method is used to set the price of a new product object.
     * @param price This is the new price for the new product object.
     */
    public void setPrice(int price) {

        this.price = price;
    }

    /** This method is used to set the stock of a new product object.
     * @param stock This is the new stock for the new product object.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** This method is used to set the min of a new product object.
     * @param min This is the new min for the new product object.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /** This method is used to set the max of a new product object.
     * @param max This is the new max for the new product object.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /** This method returns the id of the new product object.
     * @return id returns the id of the new product object.
     */
    public int getProductID() {
        return id;
    }

    /** This method returns the name of the new product object.
     * @return name returns the name of the new product object.
     */
    public String getName() {
        return name;
    }

    /** This method returns the price of the new product object.
     * @return price returns the price of the new product object.
     */
    public double getPrice() {
        return price;
    }

    /** This method returns the amount available of the new product object.
     * @return stock returns the amount of the new product object.
     */
    public int getStock() {
        return stock;
    }

    /** This method returns the min of the new product object.
     * @return min returns the min amount of the product object.
     */
    public int getMin() {
        return min;
    }

    /** This method returns the max of the new product object.
     * @return max returns the max amount of the product object.
     */
    public int getMax() {
        return max;
    }

    /** This method adds the associated parts that the product may be comprised of.
     * @param part The associated part.
     */
    public void addAssociatedParts(Part part) {
        associatedParts.add(part);
    }

    /** This method deletes the associated part from the product on the modify and add product screens.
     * @param selectedAssociatedPart The associated part selected to be deleted from product.
     * @return Boolean false.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
        }
        return false;
    }

    /** This method associates the parts of a products to a list.
     * @return associatedParts returns the associated parts of the list.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}


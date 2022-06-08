package model;

/** This an extended class of part to generate products of type InHouse. */
public class InHousePart extends Part{

    private int machineID;

    /**
     *
     * @param id This is the id of a new inhouse part.
     * @param name This is the name of a new inhouse part.
     * @param price This is the price of a new inhouse part.
     * @param stock This is the stock of a new inhouse part.
     * @param min This is the min amount that can be held of a new inhouse part.
     * @param max This is the max amount that can be held of a new inhouse part.
     * @param machineID This is the machineID that will be associated with inhouse parts only.
     */
    public InHousePart(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /** This method gets the machineID of the new inhouse part.
     * @return machineID returns the machineID for the inhouse part.
     */
    public int getMachineID() {
        return machineID;
    }

}

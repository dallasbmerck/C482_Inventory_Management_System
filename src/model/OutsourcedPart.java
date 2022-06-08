package model;

/** This an extended class of part to generate products of type OutSourced. */
public class OutsourcedPart extends Part{

    private String companyName;

    /**
     *
     * @param id This is the id of a new outsourced part.
     * @param name This is the name of a new outsourced part.
     * @param price This is the price of a new outsourced part.
     * @param stock This is the amount of a new outsourced part.
     * @param min This is the min amount that can be held of a new outsourced part.
     * @param max This is the max amount that can be held of a new outsourced part.
     * @param companyName This is the companyName that will only be associated with outsourced parts.
     */
    public OutsourcedPart(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** This method gets the company name that will be associated with an new outsourced part.
     * @return The new outsourced part's company name.
     */
    public String getCompanyName() {
        return companyName;
    }

}

package BusinessLogic;

import DataAccess.BillDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;

/**
 * The BillBLL class represents the business logic layer responsible for managing bill operations.
 * It interacts with a BillDAO instance to retrieve data and perform insert operations.
 */
public class BillBLL {

    private final BillDAO billDAO;

    public BillBLL() {
        billDAO = new BillDAO();
    }

    /**
     * Retrieves the bill data and column names used for table representation.
     *
     * @return a Map.Entry where the key is a 2D Object array containing the bill records,
     *         and the value is an ArrayList of column names.
     */
    public Map.Entry<Object[][], ArrayList<String>> billTable() {
        return billDAO.tableHelper();
    }

    /**
     * Constructs and returns a non-editable JTable populated with bill data.
     *
     * @return a JTable containing the bill records and column headers.
     */
    public JTable makeJTable() {
        Map.Entry<Object[][], ArrayList<String>> entry = billTable();

        ArrayList<String> column_names = entry.getValue();
        Object[][] result = entry.getKey();

        DefaultTableModel defaultTableModel = new DefaultTableModel(result, column_names.toArray()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(defaultTableModel);
        return table;
    }

    /**
     * Inserts a new bill record into the database.
     *
     * @param name        the name associated with the bill.
     * @param productName the name of the product included on the bill.
     * @param quantity    the quantity of the product purchased.
     * @param totalPrice  the total price of the bill.
     * @return an integer indicating the outcome of the insert operation (for example, a generated ID or update count).
     */
    public int insertBillDB(String name, String productName, int quantity, double totalPrice) {
        return billDAO.insertBill(name, productName, quantity, totalPrice);
    }
}

package Presentation;

import BusinessLogic.BillBLL;
import Model.Bill;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 *
 * The ViewBills class is a dialog based view for displaying bill data in a table format.
 */
public class ViewBills extends JDialog {

    private final JTable table;

    /**
     * Constructs a dialog frame with the specified parent frame, title, and bill business logic.
     *
     * @param parent  the parent {@link JFrame} that owns this dialog
     * @param title   the title of the dialog
     * @param billBLL an instance of {@link BillBLL} used to generate the table with bill data
     */
    public ViewBills(JFrame parent, String title, BillBLL billBLL) {

        super(parent, title, false);

        table = billBLL.makeJTable();

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setPreferredSize(new Dimension(650, 250));

        JPanel panelContainer = new JPanel();
        panelContainer.add(tableScrollPane, BorderLayout.CENTER);
        add(panelContainer, BorderLayout.CENTER);

        setSize(650, 250);
        setLocationRelativeTo(parent);
        setResizable(false);

        pack();
    }

    /**
     * Adds a new Bill to the Bill JTable.
     *
     * @param newBill the {@link Bill} instance to add to the table
     */
    public void addToBill(Bill newBill) {

        DefaultTableModel model = (DefaultTableModel) table.getModel();

        Object[] rowData = {
                newBill.id(),
                newBill.name(),
                newBill.productName(),
                newBill.quantity(),
                newBill.totalPrice()
        };

        model.addRow(rowData);
    }

    /**
     *
     * @return the table used to display bills
     */
    public JTable getTable() {
        return table;
    }
}

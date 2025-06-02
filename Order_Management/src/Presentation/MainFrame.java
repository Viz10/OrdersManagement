package Presentation;

import BusinessLogic.BillBLL;
import BusinessLogic.ClientBLL;
import BusinessLogic.OrderBLL;
import BusinessLogic.ProductBLL;
import Connection.ConnectionFactory;
import Model.Bill;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;


/**
 * *
 * The MainFrame class is a GUI-based class for displaying data in a table format and allow CRUD operations on them.
 */
public class MainFrame extends JFrame implements ActionListener {

    private final ViewBills viewBills;

    private final Connection connection;
    private final ClientBLL clientBLL;
    private final OrderBLL orderBLL;
    private final ProductBLL productBLL;
    private final BillBLL billBLL;

    JPanel up = new JPanel();
    JPanel down = new JPanel();
    JPanel up_left = new JPanel();
    JPanel up_right = new JPanel();

    JPanel client_table_panel = new JPanel();
    JPanel client_data_panel = new JPanel();
    JPanel order_table_panel = new JPanel();
    JPanel order_data_panel = new JPanel();
    JPanel product_table_panel = new JPanel();
    JPanel product_data_panel= new JPanel();

    JTable client_table;
    JTable order_table;
    JTable product_table;

    JButton addClientB = new JButton("Add Client");
    JButton updateClientB = new JButton("Update Client");
    JButton deleteClientB = new JButton("Delete Client");

    JButton addProductB = new JButton("Add Product");
    JButton updateProductB = new JButton("Update Product");
    JButton deleteProductB = new JButton("Delete Product");

    JButton addOrderB = new JButton("Add Order");
    JButton viewBillsB = new JButton("View Bills");
    JButton deleteOrderB = new JButton("Delete Order");

    JLabel name = new JLabel("Name: ");
    JLabel email = new JLabel("Email: ");
    JLabel productName = new JLabel("Product Name: ");
    JLabel productPrice = new JLabel("Product Price: ");
    JLabel productQuantity = new JLabel("Product Quantity: ");
    JLabel orderQuantity = new JLabel("Order Quantity: ");
    JLabel orderSteps = new JLabel("For adding order : select client , product and enter quantity ");

    JTextField name_field = new JTextField();
    JTextField email_field = new JTextField();
    JTextField productName_field = new JTextField();
    JTextField productPrice_field = new JTextField();
    JTextField productQuantity_field = new JTextField();
    JTextField orderQuantity_field = new JTextField();

    public MainFrame() {
        connection = ConnectionFactory.getConnection();
        clientBLL = new ClientBLL();
        orderBLL = new OrderBLL();
        productBLL = new ProductBLL();
        viewBills = new ViewBills(this,"View Bills",billBLL = new BillBLL());
        loadPage();
    }

    private void loadPage() {

        this.setLayout(new BorderLayout(10, 10));
        this.setSize(1920, 1080);

        ///////////////////////////////////////////////////////////////////////// BUTTONS
        buttons_components();

        ///////////////////////////////////////////////////////////////////////// UP
        up.setLayout(new BorderLayout(10, 10));
        up.setBackground(Color.black);
        up.setPreferredSize(new Dimension(1000, 700));

        up_left_components();
        up_right_components();

        up.add(up_left, BorderLayout.WEST);
        up.add(up_right, BorderLayout.EAST);

        ///////////////////////////////////////////////////////////////////////// DOWN
        down.setLayout(new BorderLayout(10, 10));
        down.setPreferredSize(new Dimension(1900, 340));
        down.setBackground(Color.gray);

        down_components();

        /////////////////////////////////////////////////////////////////////////
        this.add(up, BorderLayout.NORTH);
        this.add(down, BorderLayout.SOUTH);

        this.setTitle("Order Management");
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    private void buttons_components() {
        addClientB.addActionListener(this);
        updateClientB.addActionListener(this);
        deleteClientB.addActionListener(this);

        addProductB.addActionListener(this);
        updateProductB.addActionListener(this);
        deleteProductB.addActionListener(this);

        addOrderB.addActionListener(this);
        viewBillsB.addActionListener(this);
        deleteOrderB.addActionListener(this);

        addClientB.setFocusable(false);
        updateClientB.setFocusable(false);
        deleteClientB.setFocusable(false);

        addProductB.setFocusable(false);
        updateProductB.setFocusable(false);
        deleteProductB.setFocusable(false);

        addOrderB.setFocusable(false);
        viewBillsB.setFocusable(false);
        deleteOrderB.setFocusable(false);

        addClientB.setPreferredSize(new Dimension(250, 150));
        updateClientB.setPreferredSize(new Dimension(200, 150));
        deleteClientB.setPreferredSize(new Dimension(200, 150));

        addProductB.setPreferredSize(new Dimension(250, 150));
        updateProductB.setPreferredSize(new Dimension(200, 150));
        deleteProductB.setPreferredSize(new Dimension(200, 150));

        addOrderB.setPreferredSize(new Dimension(200, 50));
        viewBillsB.setPreferredSize(new Dimension(200, 50));
        deleteOrderB.setPreferredSize(new Dimension(200, 50));

    }
    private void up_left_components() {

        up_left.setLayout(new BorderLayout(10, 10));
        up_left.setBackground(new Color(102, 255, 102));
        up_left.setPreferredSize(new Dimension(900, 500));

        client_table_panel.setLayout(new BorderLayout(10, 10));
        client_table_panel.setBackground(Color.red);
        client_table_panel.setPreferredSize(new Dimension(500, 500));

        client_data_panel.setLayout(new BoxLayout(client_data_panel, BoxLayout.Y_AXIS));
        client_data_panel.setBackground(new Color(160, 200, 150));
        client_data_panel.setPreferredSize(new Dimension(400, 500));

        name_field.setMaximumSize(new Dimension(200, 100));
        email_field.setMaximumSize(new Dimension(200, 100));

        Font labelFont = new Font(null, Font.PLAIN, 20);
        name.setFont(labelFont);
        email.setFont(labelFont);

        name_field.setAlignmentX(Component.CENTER_ALIGNMENT);
        email_field.setAlignmentX(Component.CENTER_ALIGNMENT);
        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        email.setAlignmentX(Component.CENTER_ALIGNMENT);
        addClientB.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateClientB.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteClientB.setAlignmentX(Component.CENTER_ALIGNMENT);

        client_data_panel.add(Box.createRigidArea(new Dimension(0, 40)));
        client_data_panel.add(name);
        client_data_panel.add(Box.createRigidArea(new Dimension(0, 40)));
        client_data_panel.add(name_field);
        client_data_panel.add(Box.createRigidArea(new Dimension(0, 40)));
        client_data_panel.add(email);
        client_data_panel.add(Box.createRigidArea(new Dimension(0, 40)));
        client_data_panel.add(email_field);
        client_data_panel.add(Box.createRigidArea(new Dimension(0, 40)));
        client_data_panel.add(addClientB);
        client_data_panel.add(Box.createRigidArea(new Dimension(0, 40)));
        client_data_panel.add(updateClientB);
        client_data_panel.add(Box.createRigidArea(new Dimension(0, 40)));
        client_data_panel.add(deleteClientB);
        client_data_panel.add(Box.createRigidArea(new Dimension(0, 40)));

        client_table= makeJTable(new JTextField[]{name_field,email_field},clientBLL.clientTable()
        ,new String[]{"name","email"});
        JScrollPane scrollPaneClient = new JScrollPane(client_table);
        scrollPaneClient.setPreferredSize(new Dimension(500, 500));
        client_table_panel.add(scrollPaneClient);
        validate();

        up_left.add(client_table_panel, BorderLayout.WEST);
        up_left.add(client_data_panel, BorderLayout.EAST);


    }
    private void up_right_components() {
        up_right.setLayout(new BorderLayout(10, 10));
        up_right.setBackground(Color.cyan);
        up_right.setPreferredSize(new Dimension(1000, 500));

        product_table_panel.setLayout(new BorderLayout(10, 10));
        product_table_panel.setBackground(Color.yellow);
        product_table_panel.setPreferredSize(new Dimension(600, 500));

        product_data_panel.setLayout(new BoxLayout(product_data_panel, BoxLayout.Y_AXIS));
        product_data_panel.setBackground(new Color(117, 117, 72));
        product_data_panel.setPreferredSize(new Dimension(400, 500));

        productName_field.setMaximumSize(new Dimension(200, 100));
        productPrice_field.setMaximumSize(new Dimension(200, 100));
        productQuantity_field.setMaximumSize(new Dimension(200, 100));

        Font labelFont = new Font(null, Font.PLAIN, 20);
        productPrice.setFont(labelFont);
        productName.setFont(labelFont);
        productQuantity.setFont(labelFont);

        productName_field.setAlignmentX(Component.CENTER_ALIGNMENT);
        productPrice_field.setAlignmentX(Component.CENTER_ALIGNMENT);
        productQuantity_field.setAlignmentX(Component.CENTER_ALIGNMENT);

        productPrice.setAlignmentX(Component.CENTER_ALIGNMENT);
        productName.setAlignmentX(Component.CENTER_ALIGNMENT);
        productQuantity.setAlignmentX(Component.CENTER_ALIGNMENT);

        addProductB.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateProductB.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteProductB.setAlignmentX(Component.CENTER_ALIGNMENT);

        product_data_panel.add(Box.createRigidArea(new Dimension(0, 40)));
        product_data_panel.add(productName);
        product_data_panel.add(Box.createRigidArea(new Dimension(0, 40)));
        product_data_panel.add(productName_field);
        product_data_panel.add(Box.createRigidArea(new Dimension(0, 40)));
        product_data_panel.add(productPrice);
        product_data_panel.add(Box.createRigidArea(new Dimension(0, 40)));
        product_data_panel.add(productPrice_field);
        product_data_panel.add(Box.createRigidArea(new Dimension(0, 40)));
        product_data_panel.add(productQuantity);
        product_data_panel.add(Box.createRigidArea(new Dimension(0, 40)));
        product_data_panel.add(productQuantity_field);
        product_data_panel.add(Box.createRigidArea(new Dimension(0, 40)));
        product_data_panel.add(addProductB);
        product_data_panel.add(Box.createRigidArea(new Dimension(0, 40)));
        product_data_panel.add(updateProductB);
        product_data_panel.add(Box.createRigidArea(new Dimension(0, 40)));
        product_data_panel.add(deleteProductB);
        product_data_panel.add(Box.createRigidArea(new Dimension(0, 40)));

        product_table=makeJTable(new JTextField[]{productName_field,productPrice_field,productQuantity_field},productBLL.productTable()
        ,new String[]{"productName","productPrice","quantity"});
        JScrollPane scrollPaneProduct = new JScrollPane(product_table);
        scrollPaneProduct.setPreferredSize(new Dimension(1000, 500));
        product_table_panel.add(scrollPaneProduct);
        validate();

        up_right.add(product_table_panel, BorderLayout.EAST);
        up_right.add(product_data_panel, BorderLayout.WEST);
    }
    private void down_components(){

        order_data_panel.setLayout(new BoxLayout(order_data_panel, BoxLayout.Y_AXIS));
        order_data_panel.setBackground(new Color(190,140,188));
        order_data_panel.setPreferredSize(new Dimension(900, 340));
        order_data_panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        orderQuantity_field.setMaximumSize(new Dimension(200, 30));
        orderQuantity_field.setPreferredSize(new Dimension(200, 30));

        Font labelFont = new Font(null, Font.PLAIN, 20);
        orderSteps.setFont(labelFont);
        orderQuantity.setFont(labelFont);

        orderQuantity.setAlignmentX(Component.CENTER_ALIGNMENT);
        orderQuantity_field.setAlignmentX(Component.CENTER_ALIGNMENT);
        addOrderB.setAlignmentX(Component.CENTER_ALIGNMENT);

        orderSteps.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewBillsB.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteOrderB.setAlignmentX(Component.CENTER_ALIGNMENT);

        order_data_panel.add(Box.createVerticalGlue());
        order_data_panel.add(Box.createRigidArea(new Dimension(0, 20)));
        order_data_panel.add(orderQuantity);
        order_data_panel.add(Box.createRigidArea(new Dimension(0, 20)));
        order_data_panel.add(orderQuantity_field);
        order_data_panel.add(Box.createRigidArea(new Dimension(0, 20)));
        order_data_panel.add(addOrderB);
        order_data_panel.add(Box.createRigidArea(new Dimension(0, 20)));
        order_data_panel.add(deleteOrderB);
        order_data_panel.add(Box.createRigidArea(new Dimension(0, 20)));
        order_data_panel.add(orderSteps);
        order_data_panel.add(Box.createRigidArea(new Dimension(0, 20)));
        order_data_panel.add(viewBillsB);
        order_data_panel.add(Box.createVerticalGlue());

        ////////////////////////////////

        order_table_panel.setLayout(new BorderLayout(10, 10));
        order_table_panel.setBackground(Color.BLUE);
        order_table_panel.setPreferredSize(new Dimension(1000, 340));

        order_table = makeJTable(new JTextField[]{orderQuantity_field}, orderBLL.orderTable(), new String[]{"quantity"});

        JScrollPane scrollPaneOrder = new JScrollPane(order_table);
        scrollPaneOrder.setPreferredSize(new Dimension(980, 320));

        order_table_panel.add(scrollPaneOrder, BorderLayout.CENTER);

        down.add(order_data_panel, BorderLayout.EAST);
        down.add(order_table_panel, BorderLayout.WEST);

        validate();
    }

    /// GENERIC GUI METHODS
    private JTable makeJTable(JTextField[] fields, Map.Entry<Object[][], ArrayList<String>> entry, String[] columnNames) {
        ArrayList<String> column_names = entry.getValue();
        Object[][] result = entry.getKey();

        DefaultTableModel defaultTableModel = new DefaultTableModel(result, column_names.toArray()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(defaultTableModel);

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableClickHandler(table, fields, columnNames);
            }
        });

        return table;
    }
    private int findColumnIndex(DefaultTableModel model, String columnName) {
        for (int i = 0; i < model.getColumnCount(); i++) {
            if (model.getColumnName(i).equals(columnName)) {
                return i;
            }
        }
        return -1;
    }
    private void addRowData(JTable table,Function<String[],ArrayList<Object>> function, JTextField[] textFields) {
        ArrayList<Object> result;
        DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
            try {
                String []data = new String[textFields.length];
                for(int i=0;i<textFields.length;i++) {
                    data[i] = textFields[i].getText().trim(); /// create array of strings that rep the data from text field
                }
                result = function.apply(data);
                defaultTableModel.addRow(result.toArray());
                JOptionPane.showMessageDialog(null, "Successfully added data!");
            } catch (Exception e) {
                String errorMessage = (e.getMessage() != null && !e.getMessage().isEmpty()) ? e.getMessage() : "An error occurred while adding the client.";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);

            } finally {
                for (var a : textFields) {
                    a.setText(" ");
                }
            }

    }

    private void tableClickHandler(JTable table, JTextField[] textFields, String[] columnNames) {
        DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();

        if (table.getSelectedRowCount() != 1) {
            JOptionPane.showMessageDialog(null, "Please select one row");
            return;
        }

        int selectedRow = table.getSelectedRow();

        for (int i = 0; i < textFields.length && i < columnNames.length; i++) {
            String columnName = columnNames[i];
            int columnIndex = findColumnIndex(defaultTableModel, columnName);

            if (columnIndex != -1) {
                Object value = defaultTableModel.getValueAt(selectedRow, columnIndex);
                textFields[i].setText(value != null ? value.toString() : "");
            }
        }
    }
    private void updateRowHelper(ArrayList<Object> arr, JTable table) {

        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        for (int i = 0; i < arr.size(); i++) {
            dtm.setValueAt(arr.get(i), table.getSelectedRow(), i + 1);
        }
    }
    private void updateRowTable(JTable table, BiConsumer<Integer, String[]> function, JTextField[] arr) {

        DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
        if (defaultTableModel.getRowCount() == 0  || table.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Table is empty or no data selected");
        } else {
            try {
                String[] datas = new String[arr.length];
                for (int i = 0; i < arr.length; i++) {
                    datas[i] = arr[i].getText().trim();
                }
                function.accept((Integer) defaultTableModel.getValueAt(table.getSelectedRow(), 0), datas); /// pas same id but modified fields
                ArrayList<Object> fields = new ArrayList<>(); /// update table with new fields
                for (var a : arr) { /// id won't update
                    fields.add(a.getText());
                }
                updateRowHelper(fields, table); /// use generic JTable row updater
                JOptionPane.showMessageDialog(null, "Successfully updated row!");
            } catch (Exception e) {
                String errorMessage = (e.getMessage() != null && !e.getMessage().isEmpty()) ? e.getMessage() : "An error occurred while updating " + table.getName();
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);

            } finally {
                for (var a : arr) {
                    a.setText(" ");
                }
            }
        }
    }

    private void deleteRowTable(JTable table, Predicate<Integer> deleteOperation) {
        DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
        if (defaultTableModel.getRowCount() == 0  || table.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Table is empty or no data selected");
        } else {
            int id = (int) defaultTableModel.getValueAt(table.getSelectedRow(), 0);
            if (deleteOperation.test(id)) {
                defaultTableModel.removeRow(table.getSelectedRow());
                JOptionPane.showMessageDialog(null, "Successfully deleted record!");
            } else {
                JOptionPane.showMessageDialog(null, "An error occurred while deleting the client");
            }
        }

    }
    private void addOrder(){

        if (client_table.getSelectedRowCount()!=1 && product_table.getSelectedRowCount()!=1) {
            JOptionPane.showMessageDialog(null, "Must select one row from client table\nand one row from product table", "Error", JOptionPane.ERROR_MESSAGE);
             return;
        }

        Map.Entry<Integer, ArrayList<Object>> result;
        DefaultTableModel orderModel = (DefaultTableModel) order_table.getModel();

        /// retrieve info from selected rows
        int id_client= (int) client_table.getValueAt(client_table.getSelectedRow(),0);
        int id_product= (int) product_table.getValueAt(product_table.getSelectedRow(),0);
        int quantity_product= (int) product_table.getValueAt(product_table.getSelectedRow(),3);
        double price_product= (double) product_table.getValueAt(product_table.getSelectedRow(),2);
        String client_name=(String) client_table.getValueAt(client_table.getSelectedRow(),1);
        String product_name=(String) product_table.getValueAt(product_table.getSelectedRow(),1);

        int orderQuantity =Integer.parseInt(orderQuantity_field.getText()),idDB;
        double total_price=price_product*orderQuantity;

        try{
            result = orderBLL.addOrder(id_client,id_product,price_product,quantity_product,orderQuantity); /// add order to db + update product quantity in db
            orderModel.addRow(result.getValue().toArray()); /// add new order to gui
            product_table.setValueAt(result.getKey(),product_table.getSelectedRow(),3); /// update gui quantity for product
            idDB=billBLL.insertBillDB(client_name,product_name,orderQuantity,total_price);
            viewBills.addToBill(new Bill(idDB,client_name,product_name,orderQuantity,total_price)); /// bill with db generated id

            JOptionPane.showMessageDialog(null, "Successfully added data!");
        }catch (Exception e) {
            String errorMessage = (e.getMessage() != null && !e.getMessage().isEmpty()) ? e.getMessage() : "An error occurred while adding the client.";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);

        } finally {
            orderQuantity_field.setText("");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == updateClientB) {
            updateRowTable(client_table, clientBLL::updateClient, new JTextField[]{name_field, email_field});
        } else if (e.getSource() == deleteClientB) {
            deleteRowTable(client_table, clientBLL::deleteClient);
        } else if (e.getSource() == addClientB) {
            addRowData(client_table, clientBLL::addClient, new JTextField[]{name_field, email_field});
        } else if (e.getSource() == addProductB) {
            addRowData(product_table,productBLL::addProduct,new JTextField[]{productName_field, productPrice_field,productQuantity_field});
        } else if (e.getSource() == updateProductB) {
            updateRowTable(product_table, productBLL::updateProduct, new JTextField[]{productName_field, productPrice_field,productQuantity_field});
        } else if (e.getSource() == deleteProductB) {
            deleteRowTable(product_table, productBLL::deleteProduct);
        }
        else if (e.getSource() == addOrderB) {
            addOrder();
        }
        else if (e.getSource() == deleteOrderB) {
            deleteRowTable(order_table, orderBLL::deleteOrder);
        }
        else if (e.getSource() == viewBillsB) {
            viewBills.setVisible(true);
        }
        else {
            JOptionPane.showMessageDialog(null, "Error button");
        }
    }
}


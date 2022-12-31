package dinekeeper.controller;

import dinekeeper.model.Table;
import dinekeeper.model.data.RestaurantData;
import dinekeeper.util.InvalidTableAssignmentException;
import dinekeeper.util.InvalidTableUpdateException;
import dinekeeper.view.TableView;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/** A controller that manages usage and storage in the tables of a restaurant. */
public class RestaurantManager {
    private RestaurantData restaurant;
    private TableView view;

    /** Stores the ID of each table for faster lookup/table updates. Indices are rows. */
    private LinkedList<Integer> ids = new LinkedList<>();

    private DefaultTableModel dtm = new DefaultTableModel(null,
            new String[]{"ID", "Occupancy", "Availability"}) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 1;
        }
    };


    public RestaurantManager(TableView v, RestaurantData restaurant) {
        this.restaurant = restaurant;
        view = v;
        addListeners();
        initializeTableView();
        view.createTable(dtm);
    }

    /** Formats restaurant data into tabular form for GUI. */
    private void initializeTableView() {
        Map<Integer, Table> map = restaurant.getTables();
        for (Map.Entry<Integer, Table> set : map.entrySet()) {
            dtm.addRow(new Object[]{set.getKey(), set.getValue().getOccupancy(), set.getValue().getAvailability()});
            ids.add(set.getKey());
        }
    }

    public void insertTable(int id, int occupancy) {
        Table t = new Table(id, occupancy);
        try {
            restaurant.insert(t);
        } catch (InvalidTableAssignmentException e) {
            //TODO DIALOG HANDLE EXCEPTION
            System.out.println("exc");
        }
        //update table view
        dtm.addRow(new Object[]{id, occupancy, Boolean.TRUE});
        ids.add(id);
    }

    public void removeTable(int id) {
        try {
            restaurant.remove(id);
        } catch (InvalidTableUpdateException e) {
            //TODO DIALOG HANDLE EXCEPTION
            System.out.println("exc");
        }
        //update table view
        dtm.removeRow(ids.indexOf(id));
        ids.remove(ids.indexOf(id));
    }

    public void changeAvailability(int id, int row) {
        restaurant.getTable(id).setAvailability(!restaurant.checkAvailability(id));
        dtm.setValueAt(!(Boolean) dtm.getValueAt(row, 2), row, 2);
    }

    public void addListeners() {
        view.addAddListener(e -> {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Table id: "));
            int occupancy = Integer.parseInt(JOptionPane.showInputDialog("Occupancy: "));
            insertTable(id, occupancy);
        });

        view.addRemoveListener(e -> {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Input id: "));
            removeTable(id);
        });

        view.addChangeListener(e -> {
            int row = view.selectedRow();
            if (row != -1) {
                changeAvailability((int) dtm.getValueAt(row, 0), row);
            }
        });
    }

    //TODO: Change occupancy & availability options
}
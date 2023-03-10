package dinekeeper.model.data;

import dinekeeper.model.Table;
import dinekeeper.util.InvalidTableAssignmentException;
import dinekeeper.util.InvalidTableUpdateException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/** Mutable database containing all tables in the restaurant.
 * Each reservation is assigned with a table with an occupancy >= number of guests.
 * When a reservation is fulfilled (serviced), the entry is removed from the database.
 * Invariant: there can be no duplicate reservations per table or vice versa, nor duplicate
 * table IDs. */
public class RestaurantData implements Serializable {
    private Map<Integer, Table> tables = new HashMap<>();

    //return sorted tables based on ascending occupancy
    /** Returns a set of available tables in sorted order based on
     * ascending occupancy. E.g. Table with 1 seat, then 2 seats, then 3, etc. */
    public TreeSet<Table> getAvailableTables() {
        TreeSet<Table> availableTables = new TreeSet<>();
        for (Table t : tables.values()) {
            if (t.getAvailability()) availableTables.add(t);
        }
        return availableTables;
    }

    /** Returns the number of available tables in the restaurant. */
    public int size() {
        return getAvailableTables().size();
    }
    public Map<Integer, Table> getTables() {
        return tables;
    }

    /** Checks: table ID does not already exist. */
    public void insert(Table t) throws InvalidTableAssignmentException {
        for (Table table : tables.values()) {
            if (table.getId() == t.getId()) throw new InvalidTableAssignmentException();
        }
        tables.put(t.getId(), t);
    }

    /** Checks: table ID exists.*/
    public  void remove(int id) throws InvalidTableUpdateException {
        if (tables.get(id) == null) throw new InvalidTableUpdateException();
        tables.remove(id);
    }

    public Table getTable(int id) {
        return tables.get(id);
    }

    public void changeAvailability(int id) {
        getTable(id).setAvailability(!getTable(id).getAvailability());
    }

}

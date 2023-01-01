package dinekeeper.view;

import dinekeeper.util.TableColumnManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class LedgerView extends JPanel {
    private JButton calculateButton;
    private JButton saveButton;

    private JLabel totalEarningsLabel;
    private JLabel calculateEarningsLabel;
    private JScrollPane pane;
    private JTable table;
    private DefaultTableModel dtm;
    //TODO finish adding size label
    private JLabel sizeLabel;

    public LedgerView() {
        JPanel topPanel = new JPanel();
        calculateButton = new JButton("Calculate Earnings");
        saveButton = new JButton("Save");
        calculateEarningsLabel = new JLabel("Calculated Earnings: __");
        totalEarningsLabel = new JLabel("Total Earnings: 0");
        topPanel.add(saveButton);
        topPanel.add(calculateButton);
        topPanel.add(calculateEarningsLabel);
        topPanel.add(totalEarningsLabel);
        add(topPanel, BorderLayout.NORTH);
    }

    public void addCalculateListener(ActionListener l) {
        calculateButton.addActionListener(l);
    }

    public void addSaveListener(ActionListener l) {
        saveButton.addActionListener(l);
    }

    public void updateCalculateLabel(double x) {
        x = Math.round(x * 100.0) / 100.0;
        calculateEarningsLabel.setText("Calculated Earnings: $" + x);
    }

    public void updateTotalLabel(double x) {
        x = Math.round(x * 100.0) / 100.0;
        totalEarningsLabel.setText("Total Earnings: $" + x);
    }

    public void createTable(DefaultTableModel dtm) {
        this.dtm = dtm;
        table = new JTable(this.dtm);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        pane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JScrollBar bar = pane.getVerticalScrollBar();
        bar.setPreferredSize(new Dimension(40, 0));
        add(pane, BorderLayout.SOUTH);
        TableColumnManager tcm = new TableColumnManager(table);
    }
}

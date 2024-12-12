package Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.table.TableCellRenderer;

public class Jugadors_JPanel extends JPanel {
    private JTable taulaJugadors;
    private JButton consultaSeleccionat, esborraSeleccionat, afegirJugador, exportarJugadors;
    private JTextField nifField;
    private JButton cercaButton;

    public Jugadors_JPanel() {
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JLabel nifLabel = new JLabel("NIF");
        nifField = new JTextField(15);
        cercaButton = new JButton("Cerca");
        topPanel.add(nifLabel);
        topPanel.add(nifField);
        topPanel.add(cercaButton);
        add(topPanel, BorderLayout.NORTH);
        String[] columnNames = {"Nom", "NIF", "Data de naixement","Categoria", "Seleccionar"};
        Object[][] data = {}; 
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 3 ? JRadioButton.class : String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };

        taulaJugadors = new JTable(model);
        taulaJugadors.setRowHeight(30);
        taulaJugadors.getColumnModel().getColumn(3).setCellRenderer(new RadioButtonRenderer());
        taulaJugadors.getColumnModel().getColumn(3).setCellEditor(new RadioButtonEditor(new JRadioButton()));

        JScrollPane scrollPane = new JScrollPane(taulaJugadors);
        add(scrollPane, BorderLayout.CENTER);

        
        JPanel bottomPanel = new JPanel(new GridLayout(1, 4, 10, 10));

        consultaSeleccionat = new JButton("Consulta seleccionat");
        esborraSeleccionat = new JButton("Esborra seleccionat");
        afegirJugador = new JButton("Afegir jugador");
        exportarJugadors = new JButton("Exportar jugadors");

        bottomPanel.add(consultaSeleccionat);
       
        bottomPanel.add(esborraSeleccionat);
        bottomPanel.add(afegirJugador);
        bottomPanel.add(exportarJugadors);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    static class RadioButtonRenderer extends JRadioButton implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setSelected(value != null && (boolean) value);
            return this;
        }
    }

    static class RadioButtonEditor extends DefaultCellEditor {
        private final JRadioButton radioButton;

        public RadioButtonEditor(JRadioButton radioButton) {
            super(new JCheckBox());
            this.radioButton = radioButton;
            this.radioButton.setHorizontalAlignment(SwingConstants.CENTER);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            ButtonGroup buttonGroup = getButtonGroupForTable(table, column);
            buttonGroup.add(radioButton);
            radioButton.setSelected(value != null && (boolean) value);
            return radioButton;
        }

        @Override
        public Object getCellEditorValue() {
            return radioButton.isSelected();
        }

        private ButtonGroup getButtonGroupForTable(JTable table, int column) {
            ButtonGroup buttonGroup = new ButtonGroup();
            for (int i = 0; i < table.getRowCount(); i++) {
                JRadioButton button = new JRadioButton();
                buttonGroup.add(button);
            }
            return buttonGroup;
        }
    }
}

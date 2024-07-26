package visual;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import Conexion.SQL;

public class SeleccionarGrupo extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private DefaultTableModel modelGrupo;
    private JTable tableGrupo;

    public SeleccionarGrupo() {
        setTitle("Seleccionar Grupo");
        setBounds(100, 100, 800, 600);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        contentPanel.setBackground(Color.WHITE);
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        initializeModels();
        initializeUI();
        loadGrupos();
    }

    private void initializeModels() {
        String[] headerGrupo = {"Seleccionar", "IdPeriodo", "IDAsignatura", "Numero Del Grupo", "Numero dia Semana", "Fecha Hora Inicio", "Fecha Hora Fin"};
        modelGrupo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? Boolean.class : super.getColumnClass(columnIndex);
            }
        };
        modelGrupo.setColumnIdentifiers(headerGrupo);
    }

    private void initializeUI() {
        // Table for groups
        JPanel panelGrupo = new JPanel();
        panelGrupo.setBorder(new LineBorder(new Color(0, 0, 0)));
        panelGrupo.setBackground(Color.WHITE);
        panelGrupo.setBounds(10, 11, 764, 500);
        contentPanel.add(panelGrupo);
        panelGrupo.setLayout(new BorderLayout());

        tableGrupo = new JTable(modelGrupo);
        JScrollPane scrollPaneGrupo = new JScrollPane(tableGrupo);
        panelGrupo.add(scrollPaneGrupo, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton btnAbrir = new JButton("Abrir");
        btnAbrir.addActionListener(e -> openEstudiantes());
        btnAbrir.setActionCommand("Abrir");
        btnAbrir.setBackground(Color.GREEN);
        buttonPane.add(btnAbrir);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> dispose());
        cancelButton.setActionCommand("Cancel");
        cancelButton.setBackground(Color.RED);
        buttonPane.add(cancelButton);
    }

    private void loadGrupos() {
        try (Connection connection = SQL.getConnection()) {
            if (connection != null) {
                try (Statement stmt = connection.createStatement()) {
                    String query = "SELECT * FROM HorarioGrupo";
                    try (ResultSet rs = stmt.executeQuery(query)) {
                        while (rs.next()) {
                            Object[] row = {
                                false,
                                rs.getString("IdPeriodo"),
                                rs.getString("IdAsignatura"),
                                rs.getString("Numero Del Grupo"),
                                rs.getShort("Numero dia Semana"),
                                rs.getTimestamp("Fecha Hora Inicio"),
                                rs.getTimestamp("Fecha Hora Fin")
                            };
                            modelGrupo.addRow(row);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openEstudiantes() {
        int selectedRow = -1;
        for (int i = 0; i < modelGrupo.getRowCount(); i++) {
            if ((Boolean) modelGrupo.getValueAt(i, 0)) {
                selectedRow = i;
                break;
            }
        }

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un grupo.");
            return;
        }

        String idPeriodo = (String) modelGrupo.getValueAt(selectedRow, 1);
        String idAsignatura = (String) modelGrupo.getValueAt(selectedRow, 2);
        String numeroDelGrupo = (String) modelGrupo.getValueAt(selectedRow, 3);

        EliminarEstudiante eliminarEstudiante = new EliminarEstudiante(idPeriodo, idAsignatura, numeroDelGrupo);
        eliminarEstudiante.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        eliminarEstudiante.setVisible(true);
        dispose();
    }
}

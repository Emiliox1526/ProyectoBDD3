package visual;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import Conexion.SQL;

public class EliminarEstudiante extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private DefaultTableModel modelEstudiante;
    private JTable tableEstudiante;
    private String idPeriodo;
    private String idAsignatura;
    private String numeroDelGrupo;

    public EliminarEstudiante(String idPeriodo, String idAsignatura, String numeroDelGrupo) {
        this.idPeriodo = idPeriodo;
        this.idAsignatura = idAsignatura;
        this.numeroDelGrupo = numeroDelGrupo;
        initializeModels();
        initializeUI();
        loadEstudiantes();
    }

    private void initializeModels() {
        String[] headerEstudiante = {"Seleccionar", "IdEstudiante", "Nombre"};
        modelEstudiante = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? Boolean.class : super.getColumnClass(columnIndex);
            }
        };
        modelEstudiante.setColumnIdentifiers(headerEstudiante);
    }

    private void initializeUI() {
        setBounds(100, 100, 800, 600);
        setLayout(new BorderLayout());

        contentPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        contentPanel.setBackground(Color.WHITE);
        add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        // Table for students
        JPanel panelEstudiante = new JPanel();
        panelEstudiante.setBorder(new LineBorder(new Color(0, 0, 0)));
        panelEstudiante.setBackground(Color.WHITE);
        panelEstudiante.setBounds(10, 11, 764, 450);
        contentPanel.add(panelEstudiante);
        panelEstudiante.setLayout(new BorderLayout());

        tableEstudiante = new JTable(modelEstudiante);
        JScrollPane scrollPaneEstudiante = new JScrollPane(tableEstudiante);
        panelEstudiante.add(scrollPaneEstudiante, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        add(buttonPane, BorderLayout.SOUTH);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarEstudiante());
        btnEliminar.setActionCommand("Delete");
        btnEliminar.setBackground(Color.GREEN);
        buttonPane.add(btnEliminar);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> dispose());
        cancelButton.setActionCommand("Cancel");
        cancelButton.setBackground(Color.RED);
        buttonPane.add(cancelButton);
    }

    private void loadEstudiantes() {
        try (Connection connection = SQL.getConnection()) {
            if (connection != null) {
                try (PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT e.IdEstudiante, e.Nombre FROM Estudiante e " +
                    "JOIN [Grupos Inscritos] i ON e.IdEstudiante = i.IdEstudiante " +
                    "WHERE i.IdPeriodo = ? AND i.IdAsignatura = ? AND i.[Numero Del Grupo] = ?"
                )) {
                    pstmt.setString(1, idPeriodo);
                    pstmt.setString(2, idAsignatura);
                    pstmt.setString(3, numeroDelGrupo);

                    try (ResultSet rs = pstmt.executeQuery()) {
                        while (rs.next()) {
                            Object[] row = {
                                false,
                                rs.getString("IdEstudiante"),
                                rs.getString("Nombre")
                            };
                            modelEstudiante.addRow(row);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void eliminarEstudiante() {
        int selectedEstudiante = -1;
        for (int i = 0; i < modelEstudiante.getRowCount(); i++) {
            if ((Boolean) modelEstudiante.getValueAt(i, 0)) {
                selectedEstudiante = i;
                break;
            }
        }

        if (selectedEstudiante == -1) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un estudiante.");
            return;
        }

        String idEstudiante = (String) modelEstudiante.getValueAt(selectedEstudiante, 1);

        try (Connection connection = SQL.getConnection()) {
            if (connection != null) {
                // Elimina al estudiante del grupo
                String queryDelete = "DELETE FROM [Grupos Inscritos] WHERE IdEstudiante = ? AND IdPeriodo = ? AND IdAsignatura = ? AND [Numero Del Grupo] = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(queryDelete)) {
                    pstmt.setString(1, idEstudiante);
                    pstmt.setString(2, idPeriodo);
                    pstmt.setString(3, idAsignatura);
                    pstmt.setString(4, numeroDelGrupo);
                    pstmt.executeUpdate();
                }

                // Actualiza el cupo del grupo
                String queryUpdateCupo = "UPDATE [Grupo] SET [Cupo del Grupo] = [Cupo del Grupo] + 1 WHERE [IdPeriodo] = ? AND [IdAsignatura] = ? AND [Numero Del Grupo] = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(queryUpdateCupo)) {
                    pstmt.setString(1, idPeriodo);
                    pstmt.setString(2, idAsignatura);
                    pstmt.setString(3, numeroDelGrupo);
                    pstmt.executeUpdate();
                }

                JOptionPane.showMessageDialog(this, "Estudiante eliminado con éxito.");
                dispose();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

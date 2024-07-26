package visual;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import Conexion.SQL;

public class InscribirGrupo extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private DefaultTableModel modelEstudiante;
    private JTable tableEstudiante;
    private DefaultTableModel modelGrupo;
    private JTable tableGrupo;

    public static void main(String[] args) {
        try {
            InscribirGrupo dialog = new InscribirGrupo();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public InscribirGrupo() {
        initializeModels();
        initializeUI();
        loadEstudiantes();
        loadGrupos();
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

        String[] headerGrupo = {"Seleccionar", "IdPeriodo", "IDAsignatura", "[Numero Del Grupo]", "[Numero dia Semana]", "[Fecha Hora Inicio]", "[Fecha Hora Fin]"};
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
        setBounds(100, 100, 800, 600);
        setLayout(new BorderLayout());

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Opciones");
        JMenuItem menuItemEliminar = new JMenuItem("Eliminar Estudiante de un Grupo");
        menuItemEliminar.addActionListener(e -> openEliminarEstudiante());
        menu.add(menuItemEliminar);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        contentPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        contentPanel.setBackground(Color.WHITE);
        add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        // Table for students
        JPanel panelEstudiante = new JPanel();
        panelEstudiante.setBorder(new LineBorder(new Color(0, 0, 0)));
        panelEstudiante.setBackground(Color.WHITE);
        panelEstudiante.setBounds(10, 11, 764, 200);
        contentPanel.add(panelEstudiante);
        panelEstudiante.setLayout(new BorderLayout());

        tableEstudiante = new JTable(modelEstudiante);
        JScrollPane scrollPaneEstudiante = new JScrollPane(tableEstudiante);
        panelEstudiante.add(scrollPaneEstudiante, BorderLayout.CENTER);

        // Table for groups
        JPanel panelGrupo = new JPanel();
        panelGrupo.setBorder(new LineBorder(new Color(0, 0, 0)));
        panelGrupo.setBackground(Color.WHITE);
        panelGrupo.setBounds(10, 250, 764, 200);
        contentPanel.add(panelGrupo);
        panelGrupo.setLayout(new BorderLayout());

        tableGrupo = new JTable(modelGrupo);
        JScrollPane scrollPaneGrupo = new JScrollPane(tableGrupo);
        panelGrupo.add(scrollPaneGrupo, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        add(buttonPane, BorderLayout.SOUTH);

        JButton btnInscribir = new JButton("Inscribir");
        btnInscribir.addActionListener(e -> inscribirEstudiante());
        btnInscribir.setActionCommand("OK");
        btnInscribir.setBackground(Color.GREEN);
        buttonPane.add(btnInscribir);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> dispose());
        cancelButton.setActionCommand("Cancel");
        cancelButton.setBackground(Color.RED);
        buttonPane.add(cancelButton);
    }

    private void openEliminarEstudiante() {
    	dispose();
        SeleccionarGrupo seleccionarGrupo = new SeleccionarGrupo();
        seleccionarGrupo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        seleccionarGrupo.setVisible(true);
    }
    private void loadEstudiantes() {
        try (Connection connection = SQL.getConnection()) {
            if (connection != null) {
                try (Statement stmt = connection.createStatement()) {
                    String query = "SELECT * FROM Estudiante";
                    try (ResultSet rs = stmt.executeQuery(query)) {
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

    private void inscribirEstudiante() {
        int selectedEstudiante = -1;
        for (int i = 0; i < modelEstudiante.getRowCount(); i++) {
            if ((Boolean) modelEstudiante.getValueAt(i, 0)) {
                selectedEstudiante = i;
                break;
            }
        }

        int selectedGrupo = -1;
        for (int i = 0; i < modelGrupo.getRowCount(); i++) {
            if ((Boolean) modelGrupo.getValueAt(i, 0)) {
                selectedGrupo = i;
                break;
            }
        }

        if (selectedEstudiante == -1) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un estudiante.");
            return;
        }

        if (selectedGrupo == -1) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un grupo.");
            return;
        }

        String idEstudiante = (String) modelEstudiante.getValueAt(selectedEstudiante, 1);
        String idPeriodo = (String) modelGrupo.getValueAt(selectedGrupo, 1);
        String idAsignatura = (String) modelGrupo.getValueAt(selectedGrupo, 2);
        String numeroDelGrupo = (String) modelGrupo.getValueAt(selectedGrupo, 3);

        try (Connection connection = SQL.getConnection()) {
            if (connection != null) {
                // Inserta el estudiante en el grupo
                String queryInsert = "INSERT INTO [Grupos Inscritos] (IdEstudiante, IdPeriodo, IdAsignatura, [Numero Del Grupo]) VALUES (?, ?, ?, ?)";
                try (PreparedStatement pstmt = connection.prepareStatement(queryInsert)) {
                    pstmt.setString(1, idEstudiante);
                    pstmt.setString(2, idPeriodo);
                    pstmt.setString(3, idAsignatura);
                    pstmt.setString(4, numeroDelGrupo);
                    pstmt.executeUpdate();
                }

                // Actualiza el cupo del grupo
                String queryUpdateCupo = "UPDATE [Grupo] SET [Cupo del Grupo] = [Cupo del Grupo] - 1 WHERE [IdPeriodo] = ? AND [IdAsignatura] = ? AND [Numero Del Grupo] = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(queryUpdateCupo)) {
                    pstmt.setString(1, idPeriodo);
                    pstmt.setString(2, idAsignatura);
                    pstmt.setString(3, numeroDelGrupo);
                    pstmt.executeUpdate();
                }

                JOptionPane.showMessageDialog(this, "Estudiante inscrito con éxito.");
                dispose();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


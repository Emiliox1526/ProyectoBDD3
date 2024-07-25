package visual;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import Conexion.SQL;

public class InscribirGrupo extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private static DefaultTableModel modelEstudiante;
    private static DefaultTableModel modelGrupo;
    private int indexSeleccionadoEstudiante = -1;
    private int indexSeleccionadoGrupo = -1;
    private JTable tableEstudiante;
    private JTable tableGrupo;
    ArrayList<String> listaHorario = new ArrayList<>();
    ArrayList<String> listaGrupo = new ArrayList<>();

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
        String[] header = {"Seleccionar", "ID Periodo", "ID Asignatura", "Numero del Grupo", "Cupo del Grupo", "Horario"};
        String[] headerEstudiante = {"Seleccionar", "ID Estudiante", "Nombre", "Apellido", "ID Carrera", "ID Categoria", "ID Nacionalidad", "Direccion"};
        modelEstudiante = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; 
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Boolean.class; 
                }
                return super.getColumnClass(columnIndex);
            }
        };
        modelEstudiante.setColumnIdentifiers(headerEstudiante);
        

        modelGrupo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; 
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Boolean.class; 
                }
                return super.getColumnClass(columnIndex);
            }
        };
        modelGrupo.setColumnIdentifiers(header);

        setBounds(100, 100, 750, 678);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new LineBorder(new Color(160, 82, 45), 2, true));
        contentPanel.setBackground(new Color(230, 230, 250));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel.setBackground(Color.WHITE);
        panel.setBounds(10, 11, 703, 249); 
        contentPanel.add(panel);
        panel.setLayout(new BorderLayout());

        tableEstudiante = new JTable(modelEstudiante);
        tableEstudiante.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));
        tableEstudiante.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JRadioButton radioButton = new JRadioButton();
                if (value != null) {
                    radioButton.setSelected((Boolean) value);
                }
                return radioButton;
            }
        });
        JScrollPane scrollPane = new JScrollPane(tableEstudiante);
        panel.add(scrollPane, BorderLayout.CENTER); // Agregar el JScrollPane al panel
        
        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_1.setBackground(Color.WHITE);
        panel_1.setBounds(10, 287, 703, 249);
        contentPanel.add(panel_1);
        panel_1.setLayout(new BorderLayout());

        tableGrupo = new JTable(modelGrupo);
        tableGrupo.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));
        tableGrupo.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JRadioButton radioButton = new JRadioButton();
                if (value != null) {
                    radioButton.setSelected((Boolean) value);
                }
                return radioButton;
            }
        });
        JScrollPane scrollPane1 = new JScrollPane(tableGrupo);
        panel_1.add(scrollPane1, BorderLayout.CENTER); 

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton btnAsignar = new JButton("Inscribir");
        btnAsignar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                asignarEstudiante();
            }
        });
        btnAsignar.setActionCommand("OK");
        buttonPane.add(btnAsignar);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);

        loadGrupo();
        loadEstudiante();
    }

    private void loadGrupo() {
        Connection connection = SQL.getConnection();
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                String query = ("SELECT * FROM Grupo");
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    Object[] row = new Object[6];
                    row[0] = false; // Valor inicial del radio button
                    row[1] = rs.getString("IdPeriodo");
                    row[2] = rs.getString("IdAsignatura");
                    row[3] = rs.getString("Numero Del Grupo");
                    row[4] = rs.getInt("Cupo del Grupo");
                    row[5] = rs.getString("Horario");
                    modelGrupo.addRow(row);
                }

                rs.close();
                stmt.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadEstudiante() {
        Connection connection = SQL.getConnection();
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                String query = ("SELECT * FROM [Estudiante]");
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    Object[] row = new Object[8];
                    row[0] = false; 
                    row[1] = rs.getString("IdEstudiante");
                    row[2] = rs.getString("Nombre");
                    row[3] = rs.getString("Apellido");
                    row[4] = rs.getString("IdCarrera");
                    row[5] = rs.getString("IdCategoriadePago");
                    row[6] = rs.getString("IdNacionalidad");
                    row[7] = rs.getString("Direccion");
                    modelEstudiante.addRow(row);
                }

                rs.close();
                stmt.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void asignarEstudiante() {
        // Intentar asignar desde tableEstudiante
        boolean HorarioAsignado = asignarDesdeTabla(tableEstudiante, modelEstudiante, "[HorarioGrupo]");

        // Si no se asignó desde tableEstudiante, intentar asignar desde tableGrupo
        if (!HorarioAsignado) {
            boolean grupoAsignado = asignarDesdeTabla(tableGrupo, modelGrupo, "[HorarioGrupo]");
            if (grupoAsignado) {
                JOptionPane.showMessageDialog(this, "Grupo asignado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un grupo en la tabla de grupo.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Estudiante asignado exitosamente.");
        }
    }

    private boolean asignarDesdeTabla(JTable table, DefaultTableModel model, String tableName) {
        int selectedRow = -1;
        for (int i = 0; i < table.getRowCount(); i++) {
            Boolean isSelected = (Boolean) table.getValueAt(i, 0);
            if (isSelected != null && isSelected) {
                selectedRow = i;
                break;
            }
        }

        if (selectedRow != -1) {
            String idPeriodo = (String) table.getValueAt(selectedRow, 1);
            String idAsignatura = (String) table.getValueAt(selectedRow, 2);
            String numeroGrupo = (String) table.getValueAt(selectedRow, 3);
            // Recolecta los otros datos necesarios para el INSERT
            Object[] rowData = new Object[] {
                idPeriodo, idAsignatura, numeroGrupo, 
                table.getValueAt(selectedRow, 4), 
                table.getValueAt(selectedRow, 5),
                table.getValueAt(selectedRow, 6)
            };

            Connection connection = SQL.getConnection();
            if (connection != null) {
                try {
                    String query = "INSERT INTO " + tableName + " (IdPeriodo, IdAsignatura, [Numero Del Grupo], [Numero dia Semana], [Fecha Hora Inicio], [Fecha Hora Fin]) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement pstmt = connection.prepareStatement(query);
                    pstmt.setString(1, (String) rowData[0]);
                    pstmt.setString(2, (String) rowData[1]);
                    pstmt.setString(3, (String) rowData[2]);
                    pstmt.setShort(4, (Short) rowData[3]);
                    pstmt.setTimestamp(5, (Timestamp) rowData[4]);
                    pstmt.setTimestamp(6, (Timestamp) rowData[5]);

                    pstmt.executeUpdate();
                    pstmt.close();
                    connection.close();
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error al asignar el Estudiante.");
                }
            }
        }
        return false;
    }
}
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

public class AsignarHorario extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private static DefaultTableModel modelHorario;
    private static DefaultTableModel modelGrupo;
    private int indexSeleccionadoHorario = -1;
    private int indexSeleccionadoGrupo = -1;
    private JTable tableHorario;
    private JTable tableGrupo;
    ArrayList<String> listaHorario = new ArrayList<>();
    ArrayList<String> listaGrupo = new ArrayList<>();

    public static void main(String[] args) {
        try {
            AsignarHorario dialog = new AsignarHorario();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AsignarHorario() {
        String[] header = {"Seleccionar", "ID Periodo", "ID Asignatura", "Numero del Grupo", "Cupo del Grupo", "Horario"};
        String[] headerHorario = {"Seleccionar", "ID Periodo", "ID Asignatura", "Numero del Grupo", "Numero dia Semana", "Fecha Hora Inicio", "Fecha Hora Fin"};
        modelHorario = new DefaultTableModel() {
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
        modelHorario.setColumnIdentifiers(headerHorario);
        

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

        setBounds(100, 100, 719, 678);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new LineBorder(new Color(160, 82, 45), 2, true));
        contentPanel.setBackground(new Color(230, 230, 250));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel.setBackground(Color.WHITE);
        panel.setBounds(10, 11, 677, 249); 
        contentPanel.add(panel);
        panel.setLayout(new BorderLayout());

        tableHorario = new JTable(modelHorario);
        tableHorario.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));
        tableHorario.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JRadioButton radioButton = new JRadioButton();
                if (value != null) {
                    radioButton.setSelected((Boolean) value);
                }
                return radioButton;
            }
        });
        JScrollPane scrollPane = new JScrollPane(tableHorario);
        panel.add(scrollPane, BorderLayout.CENTER); // Agregar el JScrollPane al panel
        
        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_1.setBackground(Color.WHITE);
        panel_1.setBounds(10, 287, 677, 249);
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
        panel_1.add(scrollPane1, BorderLayout.CENTER); // Agregar el JScrollPane al panel_1

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton btnAsignar = new JButton("Asignar");
        btnAsignar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                asignarHorario();
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
        loadHorario();
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

    private void loadHorario() {
        Connection connection = SQL.getConnection();
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                String query = ("SELECT * FROM [Horario de un Grupo]");
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    Object[] row = new Object[7];
                    row[0] = false; // Valor inicial del radio button
                    row[1] = rs.getString("IdPeriodo");
                    row[2] = rs.getString("IdAsignatura");
                    row[3] = rs.getString("Numero Del Grupo");
                    row[4] = rs.getInt("Numero dia Semana");
                    row[5] = rs.getTimestamp("Fecha Hora Inicio");
                    row[6] = rs.getTimestamp("Fecha Hora Fin");
                    modelHorario.addRow(row);
                }

                rs.close();
                stmt.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void asignarHorario() {
        // Intentar asignar desde tableHorario
        boolean horarioAsignado = asignarDesdeTabla(tableHorario, modelHorario, "[Horario de un Grupo]");

        // Si no se asignó desde tableHorario, intentar asignar desde tableGrupo
        if (!horarioAsignado) {
            boolean grupoAsignado = asignarDesdeTabla(tableGrupo, modelGrupo, "[Horario de un Grupo]");
            if (grupoAsignado) {
                JOptionPane.showMessageDialog(this, "Grupo asignado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un grupo en la tabla de grupo.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Horario asignado exitosamente.");
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
                    JOptionPane.showMessageDialog(this, "Error al asignar el horario.");
                }
            }
        }
        return false;
    }
}
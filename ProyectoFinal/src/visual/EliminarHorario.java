package visual;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import Conexion.SQL;

public class EliminarHorario extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private static DefaultTableModel modelHorario;
    private JTable tableHorario;

    public static void main(String[] args) {
        try {
            EliminarHorario dialog = new EliminarHorario();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public EliminarHorario() {
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

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarHorarioAsignado();
            }
        });
        btnEliminar.setActionCommand("OK");
        buttonPane.add(btnEliminar);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);

        loadHorario();
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
                    row[4] = rs.getShort("Numero dia Semana");
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

    private void eliminarHorarioAsignado() {
        // Buscar la fila seleccionada en tableHorario
        int selectedRowHorario = -1;
        for (int i = 0; i < tableHorario.getRowCount(); i++) {
            Boolean isSelected = (Boolean) tableHorario.getValueAt(i, 0);
            if (isSelected != null && isSelected) {
                selectedRowHorario = i;
                break;
            }
        }

        if (selectedRowHorario != -1) {
            String idPeriodo = (String) tableHorario.getValueAt(selectedRowHorario, 1);
            String idAsignatura = (String) tableHorario.getValueAt(selectedRowHorario, 2);
            String numeroGrupo = (String) tableHorario.getValueAt(selectedRowHorario, 3);
            String numeroDia = tableHorario.getValueAt(selectedRowHorario, 4).toString();
            Timestamp fechaHoraInicio = (Timestamp) tableHorario.getValueAt(selectedRowHorario, 5);

            Connection connection = SQL.getConnection();
            if (connection != null) {
                try {
                    String query = "DELETE FROM [Horario de un Grupo] WHERE [IdPeriodo] = ? AND [IdAsignatura] = ? AND [Numero Del Grupo] = ? AND [Numero dia Semana] = ? AND [Fecha Hora Inicio] = ?";
                    PreparedStatement pstmt = connection.prepareStatement(query);
                    pstmt.setString(1, idPeriodo);
                    pstmt.setString(2, idAsignatura);
                    pstmt.setString(3, numeroGrupo);
                    pstmt.setShort(4, Short.parseShort(numeroDia));
                    pstmt.setTimestamp(5, fechaHoraInicio);

                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "Horario eliminado exitosamente.");
                        modelHorario.removeRow(selectedRowHorario);
                    } else {
                        JOptionPane.showMessageDialog(this, "No se encontró el horario.");
                    }

                    pstmt.close();
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error al eliminar el horario.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un horario en la tabla de horarios.");
        }
    }
}

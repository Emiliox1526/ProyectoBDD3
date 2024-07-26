package visual;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import Conexion.SQL;

public class AsignarHorario extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private DefaultTableModel modelGrupo;
    private JTable tableGrupo;
    private JComboBox<String> comboBoxHorasInicio;
    private JComboBox<String> comboBoxHorasFin;
    private JToggleButton[] dayButtons;

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
        initializeModels();
        initializeUI();
        loadGrupo();
    }

    private void initializeModels() {
        String[] header = {"Seleccionar", "IdPeriodo", "IDAsignatura", "[Numero Del Grupo]", "[Numero dia Semana]","[Fecha Hora Inicio]","[Fecha Hora Fin]"};

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
        modelGrupo.setColumnIdentifiers(header);
    }

    private void initializeUI() {
        setBounds(100, 100, 800, 600);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        contentPanel.setBackground(Color.WHITE);
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        // Table for group
        JPanel panelGrupo = new JPanel();
        panelGrupo.setBorder(new LineBorder(new Color(0, 0, 0)));
        panelGrupo.setBackground(Color.WHITE);
        panelGrupo.setBounds(10, 11, 764, 300);
        contentPanel.add(panelGrupo);
        panelGrupo.setLayout(new BorderLayout());

        tableGrupo = new JTable(modelGrupo);
        JScrollPane scrollPaneGrupo = new JScrollPane(tableGrupo);
        panelGrupo.add(scrollPaneGrupo, BorderLayout.CENTER);

        // ComboBoxes for hours
        comboBoxHorasInicio = new JComboBox<>(getAvailableStartHours());
        comboBoxHorasInicio.setBounds(10, 350, 100, 30);
        contentPanel.add(comboBoxHorasInicio);

        comboBoxHorasFin = new JComboBox<>(getAvailableEndHours());
        comboBoxHorasFin.setBounds(120, 350, 100, 30);
        contentPanel.add(comboBoxHorasFin);

        // Buttons for days of the week
        JPanel daysPanel = new JPanel();
        daysPanel.setBounds(10, 400, 500, 30);
        daysPanel.setLayout(new GridLayout(1, 5));
        contentPanel.add(daysPanel);

        dayButtons = new JToggleButton[5];
        String[] days = {"Lun", "Mar", "Mie", "Jue", "Vie"};
        for (int i = 0; i < days.length; i++) {
            dayButtons[i] = new JToggleButton(days[i]);
            daysPanel.add(dayButtons[i]);
        }

        // Button Panel
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton btnAsignar = new JButton("Asignar");
        btnAsignar.addActionListener(e -> asignarHorario());
        btnAsignar.setActionCommand("OK");
        btnAsignar.setBackground(Color.GREEN);
        buttonPane.add(btnAsignar);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> dispose());
        cancelButton.setActionCommand("Cancel");
        cancelButton.setBackground(Color.RED);
        buttonPane.add(cancelButton);
    }

    private void loadGrupo() {
        try (Connection connection = SQL.getConnection()) {
            if (connection != null) {
                try (Statement stmt = connection.createStatement()) {
                    String query = "SELECT * FROM [Horario de un Grupo]";
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

    private void asignarHorario() {
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

        String horaInicio = (String) comboBoxHorasInicio.getSelectedItem();
        String horaFin = (String) comboBoxHorasFin.getSelectedItem();

        int startHour = get24HourFormat(horaInicio);
        int endHour = get24HourFormat(horaFin);
        if (startHour >= endHour) {
            JOptionPane.showMessageDialog(this, "La hora de inicio debe ser antes de la hora de fin.");
            return;
        }

        int daysSelected = 0;
        for (JToggleButton button : dayButtons) {
            if (button.isSelected()) {
                daysSelected++;
            }
        }

        if (daysSelected == 0 || daysSelected > 2) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar entre 1 y 2 días.");
            return;
        }

        try (Connection connection = SQL.getConnection()) {
            if (connection != null) {
                String deleteQuery = "DELETE FROM [Horario de un Grupo] WHERE IdPeriodo = ? AND IdAsignatura = ? AND [Numero Del Grupo] = ?";
                try (PreparedStatement deletePstmt = connection.prepareStatement(deleteQuery)) {
                    deletePstmt.setString(1, idPeriodo);
                    deletePstmt.setString(2, idAsignatura);
                    deletePstmt.setString(3, numeroDelGrupo);
                    deletePstmt.executeUpdate();
                }

                String insertQuery = "INSERT INTO [Horario de un Grupo] (IdPeriodo, IdAsignatura, [Numero Del Grupo], [Numero dia Semana], [Fecha Hora Inicio], [Fecha Hora Fin]) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement insertPstmt = connection.prepareStatement(insertQuery)) {
                    for (JToggleButton button : dayButtons) {
                        if (button.isSelected()) {
                            insertPstmt.setString(1, idPeriodo);
                            insertPstmt.setString(2, idAsignatura);
                            insertPstmt.setString(3, numeroDelGrupo);
                            insertPstmt.setShort(4, (short) daysSelected); // Numero dia Semana como número de días
                            insertPstmt.setTimestamp(5, Timestamp.valueOf("2024-01-01 " + horaInicio.split(" ")[0] + ":00"));
                            insertPstmt.setTimestamp(6, Timestamp.valueOf("2024-01-01 " + horaFin.split(" ")[0] + ":00"));
                            insertPstmt.addBatch();
                        }
                    }
                    insertPstmt.executeBatch();
                    JOptionPane.showMessageDialog(this, "Horario asignado con éxito.");
                    dispose();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String[] getAvailableStartHours() {
        String[] hours = {
             "08:00 AM", "09:00 AM", "10:00 AM", "11:00 AM",
            "12:00 PM", "01:00 PM", "02:00 PM", "03:00 PM", "04:00 PM", "05:00 PM",
            "06:00 PM", "07:00 PM", "08:00 PM", "09:00 PM"
        };
        return hours;
    }

    
    private String[] getAvailableEndHours() {
        String[] hours = {
            "09:00 AM", "10:00 AM", "11:00 AM", "12:00 PM", "01:00 PM", "02:00 PM",
            "03:00 PM", "04:00 PM", "05:00 PM", "06:00 PM", "07:00 PM", "08:00 PM",
            "09:00 PM"
        };
        return hours;
    }
    


    private int get24HourFormat(String hour) {
        String[] parts = hour.split(" ");
        int hourPart = Integer.parseInt(parts[0].split(":")[0]);
        if (parts[1].equalsIgnoreCase("PM") && hourPart != 12) {
            hourPart += 12;
        } else if (parts[1].equalsIgnoreCase("AM") && hourPart == 12) {
            hourPart = 0;
        }
        return hourPart;
    }

    private short getDayNumber(String day) {
        switch (day) {
            case "Lun":
                return 1;
            case "Mar":
                return 1;
            case "Mie":
                return 1;
            case "Jue":
                return 1;
            case "Vie":
                return 1;
            default:
                return 0;
        }
    }

   
}

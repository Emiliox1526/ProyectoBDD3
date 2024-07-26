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

        dayButtons = new JToggleButton[7];
        String[] days = {"Lu", "Ma", "Mi", "Ju", "Vi", "Sa", "Do"};
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
                    String query = "SELECT * FROM HorarioGrupo";
                    try (ResultSet rs = stmt.executeQuery(query)) {
                        while (rs.next()) {
                            String diaSemana = getDayName(rs.getShort("Numero dia Semana"));
                            Object[] row = {
                                false,
                                rs.getString("IdPeriodo"),
                                rs.getString("IdAsignatura"),
                                rs.getString("Numero Del Grupo"),
                                diaSemana,
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
        
        if (startHour > endHour || (startHour == endHour && horaInicio.compareTo(horaFin) >= 0)) {
            JOptionPane.showMessageDialog(this, "La hora de inicio debe ser antes de la hora de fin.");
            return;
        }

        Timestamp fechaHoraInicio = getTimestampFromTime(horaInicio);
        Timestamp fechaHoraFin = getTimestampFromTime(horaFin);

        try (Connection connection = SQL.getConnection()) {
            if (connection != null) {
                String updateQuery = "UPDATE HorarioGrupo SET [Numero dia Semana] = ?, [Fecha Hora Inicio] = ?, [Fecha Hora Fin] = ? WHERE IdPeriodo = ? AND IdAsignatura = ? AND [Numero Del Grupo] = ?";
                try (PreparedStatement updatePstmt = connection.prepareStatement(updateQuery)) {
                    boolean hasUpdates = false;

                    for (JToggleButton button : dayButtons) {
                        if (button.isSelected()) {
                            short dayNumber = getDayNumber(button.getText());

                            updatePstmt.setShort(1, dayNumber); // Suponiendo que [Numero dia Semana] es SHORT o INT
                            updatePstmt.setTimestamp(2, fechaHoraInicio); // [Fecha Hora Inicio] como Timestamp
                            updatePstmt.setTimestamp(3, fechaHoraFin); // [Fecha Hora Fin] como Timestamp
                            updatePstmt.setString(4, idPeriodo); // Asegúrate de que IdPeriodo es STRING o INT
                            updatePstmt.setString(5, idAsignatura); // Asegúrate de que IdAsignatura es STRING o INT
                            updatePstmt.setString(6, numeroDelGrupo); // Asegúrate de que [Numero Del Grupo] es STRING o INT

                            updatePstmt.addBatch();
                            hasUpdates = true;
                        }
                    }

                    if (hasUpdates) {
                        int[] result = updatePstmt.executeBatch();
                        if (result.length > 0) {
                            JOptionPane.showMessageDialog(this, "Horario actualizado con éxito.");
                        } else {
                            JOptionPane.showMessageDialog(this, "No se actualizó ningún registro.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "No se seleccionaron días.");
                    }
                    dispose();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error al actualizar la base de datos: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error de conexión a la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Timestamp getTimestampFromTime(String time) {
        return Timestamp.valueOf("2024-01-01 " + time.split(" ")[0] + ":00");
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
            case "Lu":
                return 1;
            case "Ma":
                return 2;
            case "Mi":
                return 3;
            case "Ju":
                return 4;
            case "Vi":
                return 5;
            case "Sa":
                return 6;
            case "Do":
                return 0;
            default:
                throw new IllegalArgumentException("Día no válido: " + day);
        }
    }


    private String getDayName(short dayNumber) {
        switch (dayNumber) {
            case 1:
                return "Lunes";
            case 2:
                return "Martes";
            case 3:
                return "Miércoles";
            case 4:
                return "Jueves";
            case 5:
                return "Viernes";
            case 6:
                return "Sábado";
            case 0:
                return "Domingo";
            default:
                return "Día desconocido";
        }
    }
}

package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import Conexion.SQL;

public class ActualizarHorario extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private DefaultTableModel model;
    private JTable table;
    private JTable tablaDias;
    private JComboBox<String> FechaInicioComboBox;
    private JComboBox<String> comboBox;

    public static void main(String[] args) {
        try {
            ActualizarHorario dialog = new ActualizarHorario();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ActualizarHorario() {
        setResizable(false);
        setBounds(100, 100, 642, 439);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(230, 230, 250));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        setLocationRelativeTo(null);
      
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("Opciones");
        menuBar.add(menu);
       

        JMenuItem eliminarHorarioMenuItem = new JMenuItem("Eliminar Horario");
        
        eliminarHorarioMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	dispose();
                EliminarHorario dialog = new EliminarHorario();
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
                
            }
        });
        menu.add(eliminarHorarioMenuItem);

        String[] header = {"IdPeriodo", "IDAsignatura", "[Numero Del Grupo]", "[Numero dia Semana]", "[Fecha Hora Inicio]", "[Fecha Hora Fin]"};
        model = new DefaultTableModel();
        model.setColumnIdentifiers(header);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(SystemColor.menu);
        panel_1.setBorder(new LineBorder(new Color(160, 82, 45), 2, true));
        panel_1.setBounds(20, 28, 592, 322);
        contentPanel.add(panel_1);
        panel_1.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(new Color(100, 149, 237), 2, true));
        panel.setBounds(10, 11, 572, 144);
        panel_1.add(panel);
        panel.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane = new JScrollPane();
        panel.add(scrollPane);
        table = new JTable();
        table.setBackground(SystemColor.textHighlightText);
        table.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        table.setColumnSelectionAllowed(true);
        table.setEnabled(true);
        table.setModel(model);
        scrollPane.setViewportView(table);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    FechaInicioComboBox.setSelectedItem(table.getValueAt(selectedRow, 4).toString().split(" ")[1]);
                    comboBox.setSelectedItem(table.getValueAt(selectedRow, 5).toString().split(" ")[1]);
                    
                    int diaIndex = getDiaIndex(table.getValueAt(selectedRow, 3).toString());
                    if (diaIndex != -1 && diaIndex <= tablaDias.getRowCount()) {
                        tablaDias.setRowSelectionInterval(diaIndex - 1, diaIndex - 1);
                    } else {
                        System.out.println("Índice de día fuera de rango: " + diaIndex);
                    }
                }
            }
        });

        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new LineBorder(new Color(100, 149, 237), 2, true));
        panel_2.setBounds(10, 166, 572, 144);
        panel_1.add(panel_2);
        panel_2.setLayout(null);

        FechaInicioComboBox = new JComboBox<>();
        FechaInicioComboBox.setBounds(123, 43, 134, 25);
        panel_2.add(FechaInicioComboBox);

        JLabel lblFechainicio = new JLabel("HoraInicio");
        lblFechainicio.setForeground(new Color(0, 0, 128));
        lblFechainicio.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 15));
        lblFechainicio.setBounds(34, 45, 79, 18);
        panel_2.add(lblFechainicio);

        JLabel lblFechafinal = new JLabel("HoraFinal");
        lblFechafinal.setForeground(new Color(0, 0, 128));
        lblFechafinal.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 15));
        lblFechafinal.setBounds(34, 79, 79, 18);
        panel_2.add(lblFechafinal);

        comboBox = new JComboBox<>();
        comboBox.setBounds(123, 77, 134, 25);
        panel_2.add(comboBox);

        JPanel panelDias = new JPanel();
        panelDias.setBounds(276, 11, 286, 122);
        panel_2.add(panelDias);
        panelDias.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane_1 = new JScrollPane();
        panelDias.add(scrollPane_1, BorderLayout.CENTER);

        DefaultTableModel modelDias = new DefaultTableModel();
        modelDias.setColumnIdentifiers(new String[]{"Días de la Semana"});
        tablaDias = new JTable(modelDias);
        tablaDias.setBorder(new LineBorder(new Color(30, 144, 255)));
        scrollPane_1.setViewportView(tablaDias);

        JPanel buttonPane = new JPanel();
        buttonPane.setBounds(0, 361, 626, 38);
        contentPanel.add(buttonPane);
        buttonPane.setBorder(new LineBorder(new Color(0, 0, 0)));
        buttonPane.setBackground(new Color(135, 206, 250));

        JButton cancelButton = new JButton("Cerrar");
        cancelButton.setBounds(517, 11, 87, 23);
        cancelButton.setForeground(new Color(0, 0, 0));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        });
        buttonPane.setLayout(null);
        cancelButton.setBackground(new Color(205, 92, 92));
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
        
        JButton buttonAsignar = new JButton("Asignar");
        buttonAsignar.setForeground(Color.BLACK);
        buttonAsignar.setBackground(new Color(0, 255, 0));
        buttonAsignar.setActionCommand("Cancel");
        buttonAsignar.setBounds(420, 11, 87, 23);
        buttonPane.add(buttonAsignar);

        buttonAsignar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int selectedRow = table.getSelectedRow();
                int selectedDiaRow = tablaDias.getSelectedRow();
                if (selectedRow != -1 && selectedDiaRow != -1) {
                    String horaInicio = (String) FechaInicioComboBox.getSelectedItem();
                    String horaFin = (String) comboBox.getSelectedItem();
                    int diaSemana = selectedDiaRow + 1;
                    String idPeriodo = table.getValueAt(selectedRow, 0).toString();
                    String idAsignatura = table.getValueAt(selectedRow, 1).toString();
                    String numeroGrupo = table.getValueAt(selectedRow, 2).toString();
                    
                    if (horaInicio.compareTo(horaFin) < 0) {
                        actualizarHorario(idPeriodo, idAsignatura, numeroGrupo, diaSemana, horaInicio, horaFin);
                        loadHorario();
                    } else {
                        System.out.println("Hora de inicio debe ser anterior a la hora de fin.");
                    }
                }
            }
        });

        loadHorario();
        loadDias();
        loadHoras();
    }

    private void loadHorario() {
        model.setRowCount(0); // Clear the table before loading new data
        try (Connection connection = SQL.getConnection()) {
            if (connection != null) {
                try (Statement stmt = connection.createStatement()) {
                    String query = "SELECT * FROM HorarioGrupo";
                    try (ResultSet rs = stmt.executeQuery(query)) {
                        while (rs.next()) {
                            Object[] row = {
                                rs.getString("IdPeriodo"),
                                rs.getString("IdAsignatura"),
                                rs.getString("Numero Del Grupo"),
                                rs.getString("Numero dia Semana"),
                                rs.getTimestamp("Fecha Hora Inicio"),
                                rs.getTimestamp("Fecha Hora Fin")
                            };
                            model.addRow(row);
                        }
                        
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDias() {
        String[] dias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
        DefaultTableModel modelDias = (DefaultTableModel) tablaDias.getModel();
        modelDias.setRowCount(0); // Clear the table before loading new data
        for (String dia : dias) {
            modelDias.addRow(new Object[]{dia});
        }
    }

    private void loadHoras() {
        String[] horas = generateHoras();
        FechaInicioComboBox.removeAllItems();
        comboBox.removeAllItems();
        for (String hora : horas) {
            FechaInicioComboBox.addItem(hora);
            comboBox.addItem(hora);
        }
    }

    private String[] generateHoras() {
        Vector<String> horas = new Vector<>();
        for (int i = 8; i <= 22; i++) {
            String hora = String.format("%02d:00", i);
            horas.add(hora);
        }
        return horas.toArray(new String[0]);
    }

    private int getDiaIndex(String dia) {
        switch (dia) {
            case "1": return 1;
            case "2": return 2;
            case "3": return 3;
            case "4": return 4;
            case "5": return 5;
            case "6": return 6;
            case "7": return 7;
            default: return -1;
        }
    }

    private void actualizarHorario(String idPeriodo, String idAsignatura, String numeroGrupo, int diaSemana, String horaInicio, String horaFin) {
        try (Connection connection = SQL.getConnection()) {
            if (connection != null) {
                String query = "UPDATE HorarioGrupo SET [Numero dia Semana] = ?, [Fecha Hora Inicio] = ?, [Fecha Hora Fin] = ? WHERE IdPeriodo = ? AND IdAsignatura = ? AND [Numero Del Grupo] = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                    pstmt.setByte(1, (byte) diaSemana); 
                    pstmt.setTimestamp(2, Timestamp.valueOf("2024-01-01 " + horaInicio + ":00"));
                    pstmt.setTimestamp(3, Timestamp.valueOf("2024-01-01 " + horaFin + ":00"));
                    pstmt.setString(4, idPeriodo.trim());  
                    pstmt.setString(5, idAsignatura.trim());  
                    pstmt.setString(6, numeroGrupo.trim()); 
                    pstmt.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}

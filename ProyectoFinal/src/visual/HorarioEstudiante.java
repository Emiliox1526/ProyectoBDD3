package visual;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import Conexion.SQL;
import java.util.HashMap;
import java.util.Map;

public class HorarioEstudiante extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private DefaultTableModel model;
    private DefaultTableModel modelGrupo;
    private JTable table;
    private JTable tablaGrupo;
    private JComboBox<String> periodoComboBox;

    public static void main(String[] args) {
        try {
            HorarioEstudiante dialog = new HorarioEstudiante();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HorarioEstudiante() {
        setResizable(false);
        setBounds(100, 100, 786, 706);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(230, 230, 250));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        setLocationRelativeTo(null);

        String[] header = {"Periodo Academico", "IDEstudiante", "Nombre", "IDCarrera"};
        String[] headerGrupo = {" ", " ", " ", " ", " ", " ", " ", " ", " "};
        model = new DefaultTableModel();
        model.setColumnIdentifiers(header);

        modelGrupo = new DefaultTableModel();
        modelGrupo.setColumnIdentifiers(headerGrupo);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(SystemColor.menu);
        panel_1.setBorder(new LineBorder(new Color(160, 82, 45), 2, true));
        panel_1.setBounds(20, 28, 750, 528);
        contentPanel.add(panel_1);
        panel_1.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(new Color(100, 149, 237), 2, true));
        panel.setBounds(10, 29, 715, 241);
        panel_1.add(panel);
        panel.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane = new JScrollPane();
        panel.add(scrollPane, BorderLayout.CENTER);
        table = new JTable();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row >= 0) {
                    String idEstudiante = table.getValueAt(row, 1).toString();
                    String idPeriodo = (String) periodoComboBox.getSelectedItem();
                    if (idPeriodo != null) {
                        loadGrupo(idEstudiante, idPeriodo);
                    }
                }
            }
        });
        table.setBackground(SystemColor.textHighlightText);
        table.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        table.setColumnSelectionAllowed(true);
        table.setEnabled(true);
        table.setModel(model);
        scrollPane.setViewportView(table);

        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new LineBorder(new Color(100, 149, 237), 2, true));
        panel_2.setBounds(10, 311, 715, 206);
        panel_1.add(panel_2);
        panel_2.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane_1 = new JScrollPane();
        panel_2.add(scrollPane_1, BorderLayout.CENTER);

        tablaGrupo = new JTable();
        tablaGrupo.setBackground(SystemColor.textHighlightText);
        tablaGrupo.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        tablaGrupo.setColumnSelectionAllowed(true);
        tablaGrupo.setEnabled(false);
        tablaGrupo.setModel(modelGrupo);
        scrollPane_1.setViewportView(tablaGrupo);

        JLabel lblEstudiante = new JLabel("Estudiante");
        lblEstudiante.setForeground(new Color(0, 0, 128));
        lblEstudiante.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 15));
        lblEstudiante.setBounds(10, 11, 79, 18);
        panel_1.add(lblEstudiante);

        JLabel lblGruposInscritos = new JLabel("Horario Del Periodo");
        lblGruposInscritos.setForeground(new Color(0, 0, 128));
        lblGruposInscritos.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 15));
        lblGruposInscritos.setBounds(10, 293, 176, 18);
        panel_1.add(lblGruposInscritos);

        JPanel buttonPane = new JPanel();
        buttonPane.setBounds(0, 628, 770, 38);
        contentPanel.add(buttonPane);
        buttonPane.setBorder(new LineBorder(new Color(100, 149, 237), 2));
        buttonPane.setBackground(new Color(135, 206, 250));

        JButton cancelButton = new JButton("Cerrar");
        cancelButton.setForeground(new Color(0, 0, 0));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        });
        buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        cancelButton.setBackground(new Color(205, 92, 92));
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);

        JPanel panel_3 = new JPanel();
        panel_3.setBorder(new LineBorder(new Color(30, 144, 255), 2));
        panel_3.setBounds(20, 567, 750, 50);
        contentPanel.add(panel_3);
        panel_3.setLayout(null);

        JLabel lblTotalgrupos = new JLabel("Periodo:");
        lblTotalgrupos.setForeground(new Color(0, 0, 128));
        lblTotalgrupos.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 15));
        lblTotalgrupos.setBounds(247, 18, 97, 18);
        panel_3.add(lblTotalgrupos);

        periodoComboBox = new JComboBox<>();
        periodoComboBox.setBounds(317, 14, 186, 28);
        panel_3.add(periodoComboBox);
        
        JPanel panel_4 = new JPanel();
        panel_4.setBackground(new Color(100, 149, 237));
        panel_4.setBounds(0, 0, 237, 50);
        panel_3.add(panel_4);
        
        JPanel panel_5 = new JPanel();
        panel_5.setBackground(new Color(100, 149, 237));
        panel_5.setBounds(513, 0, 237, 50);
        panel_3.add(panel_5);
        periodoComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    String idEstudiante = table.getValueAt(row, 1).toString();
                    String idPeriodo = (String) periodoComboBox.getSelectedItem();
                    if (idPeriodo != null) {
                        loadGrupo(idEstudiante, idPeriodo);
                    }
                }
            }
        });



        loadPeriodos();
        loadEstudiantes(); 
    }

    private void loadPeriodos() {
        Connection connection = SQL.getConnection();
        if (connection != null) {
            String query = "SELECT DISTINCT IdPeriodo FROM [Periodo Academico]";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                periodoComboBox.removeAllItems();
                while (rs.next()) {
                    String idPeriodo = rs.getString("IdPeriodo");
                    periodoComboBox.addItem(idPeriodo);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadEstudiantes() {
        model.setRowCount(0);
        Connection connection = SQL.getConnection();

        if (connection != null) {
            Statement stmt = null;
            ResultSet rsEstudiantes = null;
            ResultSet rsPeriodos = null;

            try {
                stmt = connection.createStatement();

                rsPeriodos = stmt.executeQuery(
                    "SELECT i.IDEstudiante, i.IDPeriodo " + 
                    "FROM Inscripcion i " + 
                    "INNER JOIN Estudiante e ON e.IDEstudiante = i.IDEstudiante"
                );

                Map<String, String> periodosMap = new HashMap<>();

                while (rsPeriodos.next()) {
                    String idEstudiante = rsPeriodos.getString("IDEstudiante");
                    String idPeriodo = rsPeriodos.getString("IDPeriodo");
                    periodosMap.put(idEstudiante, idPeriodo);
                }

                rsEstudiantes = stmt.executeQuery("SELECT * FROM Estudiante");

                while (rsEstudiantes.next()) {
                    String nombre = rsEstudiantes.getString("Nombre");
                    String apellido = rsEstudiantes.getString("Apellido");
                    String idCarrera = rsEstudiantes.getString("IDCarrera");
                    String idEstudiante = rsEstudiantes.getString("IDEstudiante");
                    String idPeriodo = periodosMap.get(idEstudiante);

                    
                        Object[] row = new Object[model.getColumnCount()];
                        row[0] = idPeriodo;
                        row[1] = idEstudiante;
                        row[2] = nombre;
                        row[3] = idCarrera;
                        model.addRow(row);
                    
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (stmt != null) stmt.close();
                    if (rsEstudiantes != null) rsEstudiantes.close();
                    if (rsPeriodos != null) rsPeriodos.close();
                    if (connection != null) connection.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    private void loadGrupo(String idEstudiante, String idPeriodo) {
        modelGrupo.setRowCount(0);
        Connection connection = SQL.getConnection();

        if (connection != null) {
            try (PreparedStatement stmt = connection.prepareStatement(
                "EXEC HorarioEstudiante ?, ?"
            )) {
                stmt.setString(1, idEstudiante);
                stmt.setString(2, idPeriodo);

                try (ResultSet rs = stmt.executeQuery()) {
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int columnCount = rsmd.getColumnCount();

                    while (rs.next()) {
                        Object[] row = new Object[columnCount];
                        for (int i = 1; i <= columnCount; i++) {
                            row[i - 1] = rs.getObject(i);
                        }
                        modelGrupo.addRow(row);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

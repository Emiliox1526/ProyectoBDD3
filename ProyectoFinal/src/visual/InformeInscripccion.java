package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.ResultSetMetaData;


import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import java.util.HashMap;
import java.util.Map;


import Conexion.SQL;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;

public class InformeInscripccion extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextField txtNombre;
    private JTextField txtApellido;
    private DefaultTableModel model;
    private DefaultTableModel modelGrupo;
    private Object[] row;
    private JTable table;
    private JTable tablaGrupo; 
    private JTextField textCantGrupos;
    private JTextField textCantCreditos;

    public static void main(String[] args) {
        try {
            InformeInscripccion dialog = new InformeInscripccion();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public InformeInscripccion() {
        setResizable(false);
        setBounds(100, 100, 674, 706);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(230, 230, 250));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        setLocationRelativeTo(null);

        String[] header = {"Periodo Academico", "IDEstudiante", "Nombre", "IDCarrera"};
        String[] headerGrupo = {"IdPeriodo","Numero del Grupo", "Creditos", "Horario"};
        model = new DefaultTableModel();
        model.setColumnIdentifiers(header);


        modelGrupo = new DefaultTableModel();
        modelGrupo.setColumnIdentifiers(headerGrupo);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(SystemColor.menu);
        panel_1.setBorder(new LineBorder(new Color(160, 82, 45), 2, true));
        panel_1.setBounds(20, 28, 646, 528);
        contentPanel.add(panel_1);
        panel_1.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(new Color(100, 149, 237), 2, true));
        panel.setBounds(10, 29, 607, 241);
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
                    loadGrupo(idEstudiante); 
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
        panel_2.setBounds(10, 294, 607, 223);
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
        lblEstudiante.setBounds(258, 11, 79, 18);
        panel_1.add(lblEstudiante);

        JLabel lblGruposInscritos = new JLabel("Grupos Inscritos");
        lblGruposInscritos.setForeground(new Color(0, 0, 128));
        lblGruposInscritos.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 15));
        lblGruposInscritos.setBounds(249, 276, 128, 18);
        panel_1.add(lblGruposInscritos);

        JPanel buttonPane = new JPanel();
        buttonPane.setBounds(0, 628, 666, 38);
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

        JLabel lblNewLabel = new JLabel("Informe de Inscripcion");
        lblNewLabel.setForeground(new Color(0, 0, 128));
        lblNewLabel.setBounds(20, 11, 289, 18);
        contentPanel.add(lblNewLabel);
        lblNewLabel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 15));
        
        JPanel panel_3 = new JPanel();
        panel_3.setBorder(new LineBorder(new Color(30, 144, 255), 2));
        panel_3.setBounds(20, 567, 617, 50);
        contentPanel.add(panel_3);
        panel_3.setLayout(null);
        
        textCantGrupos = new JTextField();
        textCantGrupos.setBounds(113, 11, 175, 32);
        panel_3.add(textCantGrupos);
        textCantGrupos.setColumns(10);
        
        JPanel panel_4 = new JPanel();
        panel_4.setBackground(new Color(30, 144, 255));
        panel_4.setBounds(292, 0, 24, 50);
        panel_3.add(panel_4);
        
        textCantCreditos = new JTextField();
        textCantCreditos.setColumns(10);
        textCantCreditos.setBounds(426, 11, 181, 32);
        panel_3.add(textCantCreditos);
        
        JLabel lblTotalgrupos = new JLabel("Total Grupos:");
        lblTotalgrupos.setForeground(new Color(0, 0, 128));
        lblTotalgrupos.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 15));
        lblTotalgrupos.setBounds(10, 17, 97, 18);
        panel_3.add(lblTotalgrupos);
        
        JLabel lblTotalCreditos = new JLabel("Total Creditos:");
        lblTotalCreditos.setForeground(new Color(0, 0, 128));
        lblTotalCreditos.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 15));
        lblTotalCreditos.setBounds(318, 17, 111, 18);
        panel_3.add(lblTotalCreditos);

        loadEstudiantes(); 
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
                    Object[] row = new Object[model.getColumnCount()];
                    String idEstudiante = rsEstudiantes.getString("IDEstudiante");
                    
                    String nombreCompleto = rsEstudiantes.getString("Nombre");
                    String[] nombres = nombreCompleto.split(" ");
                    
                    StringBuilder nombreFormateado = new StringBuilder();
                    if (nombres.length > 0) {
                        nombreFormateado.append(nombres[0]); 
                        
                        for (int i = 1; i < nombres.length - 1; i++) {
                            nombreFormateado.append(" ").append(nombres[i].charAt(0)).append("."); 
                        }
                        
                        if (nombres.length > 1) {
                            nombreFormateado.append(" ").append(nombres[nombres.length - 1]);
                        }
                    }
                    
                    String apellidoCompleto = rsEstudiantes.getString("Apellido");
                    String[] apellidos = apellidoCompleto.split(" ");

                    if (apellidos.length > 0) {
                        nombreFormateado.append(" ").append(apellidos[0].charAt(0)).append("."); 
                    }

                    String nombreFinal = nombreFormateado.toString().trim();
                    
                    row[0] = periodosMap.getOrDefault(idEstudiante, ""); // Asignar IDPeriodo
                    row[1] = idEstudiante;
                    row[2] = nombreFinal; 
                    row[3] = rsEstudiantes.getString("IDCarrera");
                    
                    model.addRow(row);
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                try {
                    if (rsEstudiantes != null) rsEstudiantes.close();
                    if (rsPeriodos != null) rsPeriodos.close();
                    if (stmt != null) stmt.close();
                    if (connection != null) connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }






    private void loadGrupo(String idEstudiante) {
        modelGrupo.setRowCount(0); 
        Connection connection = SQL.getConnection();
        
        if (connection != null) {
            String query = "SELECT gi.IdPeriodo, gi.[Numero Del Grupo], a.Creditos, hg.[Numero dia Semana], " +
                           "CONVERT(VARCHAR, hg.[Fecha Hora Inicio], 108) + ' - ' + CONVERT(VARCHAR, hg.[Fecha Hora Fin], 108) AS Horario " +
                           "FROM [Grupos Inscritos] gi " +
                           "INNER JOIN [Grupo] g ON gi.IdPeriodo = g.IdPeriodo " +
                           "AND gi.IdAsignatura = g.IdAsignatura " +
                           "AND gi.[Numero Del Grupo] = g.[Numero Del Grupo] " +
                           "INNER JOIN [Asignatura] a ON g.IdAsignatura = a.IdAsignatura " +
                           "INNER JOIN [HorarioGrupo] hg ON gi.IdPeriodo = hg.IdPeriodo " +
                           "AND gi.IdAsignatura = hg.IdAsignatura " +
                           "AND gi.[Numero Del Grupo] = hg.[Numero Del Grupo] " +
                           "WHERE gi.IdEstudiante = ?";
            
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, idEstudiante);
                try (ResultSet rs = pstmt.executeQuery()) {
                    int totalGrupos = 0;
                    int totalCreditos = 0;
                    
                    while (rs.next()) {
                        Object[] row = new Object[modelGrupo.getColumnCount()];
                        row[0] = rs.getString("IdPeriodo");  
                        row[1] = rs.getString("Numero Del Grupo");
                        row[2] = rs.getString("Creditos");
                        
                        // Acumulando el total de créditos
                        totalCreditos += rs.getInt("Creditos");
                        
                        int numeroDiaSemana = rs.getInt("Numero dia Semana");
                        String diaSemana;
                        switch (numeroDiaSemana) {
                            case 0: diaSemana = "Do"; break;
                            case 1: diaSemana = "Lu"; break;
                            case 2: diaSemana = "Ma"; break;
                            case 3: diaSemana = "Mi"; break;
                            case 4: diaSemana = "Ju"; break;
                            case 5: diaSemana = "Sa"; break;
                            default: diaSemana = ""; break;
                        }
                        
                        String horario = diaSemana + " " + rs.getString("Horario");
                        row[3] = horario;
                        
                        modelGrupo.addRow(row);
                        
                        // Contando el número de grupos
                        totalGrupos++;
                    }
                    
                    // Actualizando los campos con los totales
                    textCantGrupos.setText(String.valueOf(totalGrupos));
                    textCantCreditos.setText(String.valueOf(totalCreditos));
                    
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        connection.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

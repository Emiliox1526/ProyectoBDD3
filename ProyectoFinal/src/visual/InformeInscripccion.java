package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import Conexion.SQL;
import javax.swing.JSpinner;

public class InformeInscripccion extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextField txtNombre;
    private JTextField txtApellido;
    private DefaultTableModel model;
    private Object[] row;
    private JTable table;
    private JTextField txtId;

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
        setBounds(100, 100, 642, 439);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(230, 230, 250));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        setLocationRelativeTo(null);
        
        String[] header = {"ID", "Nombre", "Apellido"};
        model = new DefaultTableModel();
        model.setColumnIdentifiers(header);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(SystemColor.menu);
        panel_1.setBorder(new LineBorder(new Color(160, 82, 45), 2, true));
        panel_1.setBounds(20, 28, 592, 322);
        contentPanel.add(panel_1);
        panel_1.setLayout(null);
        
        JLabel lblNewLabel_1 = new JLabel("Buscar por ID:");
        lblNewLabel_1.setBounds(10, 11, 125, 14);
        panel_1.add(lblNewLabel_1);
        lblNewLabel_1.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
      //aaaaaaaaa
        JLabel lblNewLabel_2 = new JLabel("ID:");
        lblNewLabel_2.setBounds(10, 49, 30, 14);
        panel_1.add(lblNewLabel_2);
        lblNewLabel_2.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
        
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(400, 35, 89, 45);
        panel_1.add(btnBuscar);
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	loadEstudiantes();
            }
        });
        btnBuscar.setBackground(new Color(51, 204, 153));
        btnBuscar.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 11));
        
        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(new Color(100, 149, 237), 2, true));
        panel.setBounds(10, 91, 572, 220);
        panel_1.add(panel);
        panel.setLayout(new BorderLayout(0, 0));
        
        JScrollPane scrollPane = new JScrollPane();
        panel.add(scrollPane);
        table = new JTable();
        table.setBackground(SystemColor.textHighlightText);
        table.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        table.setColumnSelectionAllowed(true);
        table.setEnabled(false);
        table.setModel(model);
        scrollPane.setViewportView(table);
        
        txtId = new JTextField();
        txtId.setBounds(91, 47, 299, 20);
        panel_1.add(txtId);
        txtId.setColumns(10);
        
        JButton btnReiniciar = new JButton("Reiniciar");
        btnReiniciar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtId.setText("");        
                loadEstudiantes();
            }
        });
        btnReiniciar.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 11));
        btnReiniciar.setBackground(new Color(205, 92, 92));
        btnReiniciar.setBounds(493, 44, 89, 27);
        panel_1.add(btnReiniciar);
        
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
        
        JLabel lblNewLabel = new JLabel("Informe de Inscripcion");
        lblNewLabel.setForeground(new Color(0, 0, 128));
        lblNewLabel.setBounds(20, 11, 289, 18);
        contentPanel.add(lblNewLabel);
        lblNewLabel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 15));
        
        loadEstudiantes();
    }
    
    private void loadEstudiantes() {
        model.setRowCount(0);
        row = new Object[model.getColumnCount()];

        Connection connection = SQL.getConnection();
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                String query = "SELECT * FROM Estudiante";
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    row[0] = rs.getString("IdEstudiante");
                    row[1] = rs.getString("Nombre");
                    row[2] = rs.getString("Apellido");
                    model.addRow(row);
                }

                rs.close();
                stmt.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

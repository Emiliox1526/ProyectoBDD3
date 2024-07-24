package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import Conexion.SQL;
import logico.Cliente;
import logico.Diseñador;
import logico.Empresa;
import logico.Jefe;
import logico.Planificador;
import logico.Programador;
import logico.Proyecto;
import logico.Trabajador;
import javax.swing.JSpinner;
import java.awt.TextField;

public class ListadoProyecto extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNombre;
	private JTextField txtApellido;
	private DefaultTableModel model;
	private Object[] row;
	private JTable table;
	private JTextField txtId;
	private JTextField nombre;
	private JTextField Apellido;
	private JTextField ID;
	private JTextField Nombre;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			
			ListadoProyecto dialog = new ListadoProyecto();
			
			dialog.loadHorario();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ListadoProyecto() {
		setResizable(false);
		setBounds(100, 100, 810, 466);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(230, 230, 250));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setLocationRelativeTo(null);
		String[] header = {"ID Periodo", "ID Asignatura", "Numero del Grupo", "Numero dia Semana","Horario"};
		
		model = new DefaultTableModel();
		model.setColumnIdentifiers(header);
		
		

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.menu);
		panel_1.setBorder(new LineBorder(new Color(160, 82, 45), 2, true));
		panel_1.setBounds(10, 35, 784, 344);
		contentPanel.add(panel_1);
		panel_1.setLayout(null);
		{
			JLabel lblBuscar = new JLabel("Buscar por:");
			lblBuscar.setBounds(10, 11, 82, 14);
			panel_1.add(lblBuscar);
			lblBuscar.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
		}
		{
			JLabel lblCedula = new JLabel("ID:");
			lblCedula.setBounds(10, 36, 96, 14);
			panel_1.add(lblCedula);
			lblCedula.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
		}
		{
			JLabel lblNombre = new JLabel("Nombre del cliente:");
			lblNombre.setBounds(10, 61, 127, 14);
			panel_1.add(lblNombre);
			lblNombre.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
		}
		{
			JLabel lblApellidos = new JLabel("Apellido del cliente:");
			lblApellidos.setBounds(10, 89, 137, 14);
			panel_1.add(lblApellidos);
			lblApellidos.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
		}
		
		
		Apellido = new JTextField();
		Apellido.setBounds(155, 87, 269, 20);
		panel_1.add(Apellido);
		Apellido.setColumns(10);
		
		ID = new JTextField();
		ID.setBounds(155, 34, 269, 20);
		panel_1.add(ID);
		ID.setColumns(10);
		
		Nombre = new JTextField();
		Nombre.setBounds(155, 59, 269, 20);
		panel_1.add(Nombre);
		Nombre.setColumns(10);
		
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(434, 33, 89, 42);
		panel_1.add(btnBuscar);
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
			  
		}});
		btnBuscar.setBackground(new Color(51, 204, 153));
		btnBuscar.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 11));
		
		JButton btnReiniciar = new JButton("Reiniciar");
		btnReiniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ID.setEnabled(true);
			    Nombre.setEnabled(true);
			    Apellido.setEnabled(true);
			    ID.setText("");
			    Nombre.setText("");
			    Apellido.setText("");
			    loadHorario();
			}
		});
		btnReiniciar.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 11));
		btnReiniciar.setBackground(new Color(240, 128, 128));
		btnReiniciar.setBounds(434, 84, 89, 27);
		panel_1.add(btnReiniciar);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 145, 764, 188);
		panel_1.add(panel);
		panel.setBorder(new LineBorder(new Color(30, 144, 255), 2, true));
		panel.setLayout(new BorderLayout(0, 0));
		
		JButton btnProrroga = new JButton("Prorrogar");
		btnProrroga.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaProrroga p = new VentanaProrroga();
				dispose();
				p.setModal(true);
				p.setVisible(true);
			}
		});
		btnProrroga.setBackground(new Color(50, 205, 50));
		btnProrroga.setBounds(533, 36, 93, 75);
		panel_1.add(btnProrroga);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 145, 764, 184);
		panel_1.add(scrollPane);
		table = new JTable();
		table.setBackground(SystemColor.textHighlightText);
		table.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		table.setColumnSelectionAllowed(true);
		table.setEnabled(false);
		scrollPane.setViewportView(table);
		table.setModel(model);
		
		
		{
			JLabel lblNewLabel = new JLabel("Trabajadores\r\n");
			lblNewLabel.setBounds(10, 11, 91, 25);
			contentPanel.add(lblNewLabel);
			lblNewLabel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 15));
		}
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
			buttonPane.setBackground(new Color(135, 206, 250));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				
				JButton cancelButton = new JButton("Cerrar\r\n");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setBackground(new Color(255, 99, 71));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	   private void loadHorario() {
	        model.setRowCount(0);
	        row = new Object[model.getColumnCount()];

	        Connection connection = SQL.getConnection();
	        if (connection != null) {
	            try {
	                Statement stmt = connection.createStatement();
	                String query = ("SELECT * FROM Grupo");
	                ResultSet rs = stmt.executeQuery(query);

	                while (rs.next()) {
	                	 row[0] = rs.getString("IdPeriodo");
	                     row[1] = rs.getString("IdAsignatura");
	                     row[2] = rs.getString("Numero Del Grupo");
	                     row[3] = rs.getInt("Cupo del Grupo");
	                     row[4] = rs.getString("Horario");
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


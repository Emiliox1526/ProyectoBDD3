package visual;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.ComboBoxEditor;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JRadioButton;
import java.awt.Label;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import logico.Diseñador;
import logico.Empresa;
import logico.Jefe;
import logico.Planificador;
import logico.Programador;
import logico.Trabajador;

import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;
import javax.swing.BoxLayout;
import javax.swing.JTable;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
//aaaaaaaaa
public class RegistroTrabajador extends JDialog {
	int backup = 0;
	private JTextField Cedula;
	private JTextField Nombre;
	private JTextField textField;
	private JTextField Direccion;
	private ButtonGroup botones = new ButtonGroup();
	private static String[] l;
	private static Object[] rows;
	private static Object[] rowsAgregado;
	private static DefaultTableModel model;
	private static DefaultTableModel modelAgregado;
	private JTable tableListaLenguajes;
	private JTable tableAgregados;
	private int indexSeleccionadoLenguajes = -1;
	private int indexSeleccionadoAgregados = -1;
	ArrayList<String> listaLenguajes = new ArrayList<>();
	ArrayList<String> listaAgregados = new ArrayList<>();
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegistroTrabajador dialog = new RegistroTrabajador();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RegistroTrabajador() {
		setResizable(false);
		listaLenguajes.add("JavaScript");
		listaLenguajes.add("HTML");
		listaLenguajes.add("Python");
		listaLenguajes.add("SQL");
		listaLenguajes.add("Java");
		listaLenguajes.add("C#");
		listaLenguajes.add("C");
		listaLenguajes.add("NoSQL");
		listaLenguajes.add("Rust");
		listaLenguajes.add("Perl");
		listaLenguajes.add("Swift");
		getContentPane().setBackground(Color.WHITE);
		setBounds(100, 100, 572, 510);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel panel = new JPanel();
			panel.setBackground(new Color(230, 230, 250));
			getContentPane().add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			setLocationRelativeTo(null);
			JLabel lblNewLabel = new JLabel("Nuevo Grupo\r\n");
			lblNewLabel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 15));
			lblNewLabel.setBounds(10, 26, 135, 25);
			panel.add(lblNewLabel);
			
			JPanel panel_3 = new JPanel();
			panel_3.setBackground(new Color(175, 238, 238));
			panel_3.setBorder(new LineBorder(new Color(65, 105, 225), 2));
			panel_3.setBounds(175, 222, 339, 176);
			panel.add(panel_3);
			panel_3.setLayout(null);
			
			JPanel panel_4 = new JPanel();
			panel_4.setBackground(new Color(135, 206, 250));
			panel_4.setBorder(new LineBorder(new Color(65, 105, 225), 2));
			panel_4.setBounds(0, 139, 339, 37);
			panel_3.add(panel_4);
			
			JButton btnAgregar = new JButton("Agregar");
			btnAgregar.setFont(new Font("Yu Gothic Medium", Font.BOLD, 11));
			btnAgregar.setForeground(Color.WHITE);
			btnAgregar.setBackground(new Color(124, 252, 0));
			panel_4.add(btnAgregar);
			
			JButton btnQuitar = new JButton("Quitar");
			btnQuitar.setFont(new Font("Yu Gothic Medium", Font.BOLD, 11));
			btnQuitar.setForeground(Color.WHITE);
			btnQuitar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(indexSeleccionadoAgregados != -1) {
						String lenguajeEliminado = listaAgregados.get(indexSeleccionadoAgregados);
						listaLenguajes.add(lenguajeEliminado);
			            listaAgregados.remove(indexSeleccionadoAgregados);
			            loadLenguajes();
			        }
				}
			});
			btnQuitar.setBackground(new Color(255, 0, 0));
			panel_4.add(btnQuitar);
			
			JPanel panelListaLenguajes = new JPanel();
			panelListaLenguajes.setBorder(new LineBorder(new Color(0, 0, 0)));
			panelListaLenguajes.setBounds(10, 11, 153, 121);
			panel_3.add(panelListaLenguajes);
			panelListaLenguajes.setLayout(new BorderLayout(0, 0));
			
			JScrollPane scrollPane = new JScrollPane();
			panelListaLenguajes.add(scrollPane, BorderLayout.CENTER);
			
			tableListaLenguajes = new JTable();
			tableListaLenguajes.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 11));
			tableListaLenguajes.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					indexSeleccionadoLenguajes = tableListaLenguajes.getSelectedRow();
				}
			});
			scrollPane.setViewportView(tableListaLenguajes);
			String[] headers = {"Lenguajes"};
			model = new DefaultTableModel();
			model.setColumnIdentifiers(headers);
			String[] headersAgregado = {"Agregados(Max 3)"};
			modelAgregado = new DefaultTableModel();	
			modelAgregado.setColumnIdentifiers(headersAgregado);
			
			JPanel panelAgregados = new JPanel();
			panelAgregados.setBorder(new LineBorder(new Color(0, 0, 0)));
			panelAgregados.setBounds(173, 11, 156, 121);
			panel_3.add(panelAgregados);
			panelAgregados.setLayout(new BorderLayout(0, 0));
			
			JScrollPane scrollPane_1 = new JScrollPane();
			panelAgregados.add(scrollPane_1, BorderLayout.CENTER);
			
			tableAgregados = new JTable();
			tableAgregados.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					indexSeleccionadoAgregados = tableAgregados.getSelectedRow();
				}
			});
			scrollPane_1.setViewportView(tableAgregados);
			btnAgregar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(indexSeleccionadoLenguajes != -1) {
						String lenguajeAgregado = listaLenguajes.get(indexSeleccionadoLenguajes);
			            if (listaAgregados.size() < 3) {
			                listaAgregados.add(lenguajeAgregado);
			                listaLenguajes.remove(indexSeleccionadoLenguajes);
			                loadLenguajes();
			            } else {
			                JOptionPane.showMessageDialog(null, "No se permiten mas de 3 lenguajes", "Error", JOptionPane.ERROR_MESSAGE);
			            }
			        }
					
				}
			});
			panel_3.setVisible(false);
			
			JLabel lblNewLabel_1 = new JLabel("IdPeriodo:\r\n");
			lblNewLabel_1.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
			lblNewLabel_1.setBounds(20, 62, 88, 14);
			panel.add(lblNewLabel_1);
			
			JLabel lblApellidos = new JLabel("Cupo de Grupo:");
			lblApellidos.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
			lblApellidos.setBounds(279, 97, 108, 14);
			panel.add(lblApellidos);
			
			JLabel lblNombres = new JLabel("CodigoAsignatura:");
			lblNombres.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
			lblNombres.setBounds(20, 97, 125, 14);
			panel.add(lblNombres);
			
			JLabel lblSexo = new JLabel("Sexo:");
			lblSexo.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
			lblSexo.setBounds(20, 128, 59, 14);
			panel.add(lblSexo);
			
			JLabel lblEdad = new JLabel("Fecha:");
			lblEdad.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
			lblEdad.setBounds(20, 183, 48, 22);
			panel.add(lblEdad);
			
			JLabel lblDireccion = new JLabel("Horario:");
			lblDireccion.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
			lblDireccion.setBounds(20, 158, 88, 14);
			panel.add(lblDireccion);
			
			JLabel lblNewLabel_2 = new JLabel("Rol:");
			lblNewLabel_2.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
			lblNewLabel_2.setBounds(20, 304, 46, 14);
			panel.add(lblNewLabel_2);
			
			JPanel panel_1 = new JPanel();
			panel_1.setBorder(new LineBorder(new Color(65, 105, 225), 2));
			panel_1.setBackground(new Color(175, 238, 238));
			panel_1.setBounds(53, 233, 112, 165);
			panel.add(panel_1);
			panel_1.setLayout(null);
			
			

			Cedula = new JTextField();
			Cedula.setBounds(99, 59, 170, 20);
			panel.add(Cedula);
			Cedula.setColumns(10);
			
			Nombre = new JTextField();
			Nombre.setColumns(10);
			Nombre.setBounds(149, 94, 120, 20);
			panel.add(Nombre);
			
			textField = new JTextField();
			textField.setColumns(10);
			textField.setBounds(397, 95, 135, 20);
			panel.add(textField);
			
			JComboBox comboBox = new JComboBox();
			comboBox.setBounds(99, 125, 170, 20);
			panel.add(comboBox);
			
			comboBox.addItem("<Seleccione>");
			comboBox.addItem("M");
			comboBox.addItem("F");
			
			Direccion = new JTextField();
			Direccion.setColumns(10);
			Direccion.setBounds(99, 155, 415, 20);
			panel.add(Direccion);
			
			JLabel JefeLabel = new JLabel("Cantidad de trabajadores:");
			JefeLabel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 13));
			JefeLabel.setBounds(175, 315, 163, 14);
			panel.add(JefeLabel);
			JefeLabel.setVisible(true);
			
			JLabel DiseniadorLabel = new JLabel("A\u00F1os de eperiencia:");
			DiseniadorLabel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 13));
			DiseniadorLabel.setBounds(175, 318, 163, 14);
			panel.add(DiseniadorLabel);
			DiseniadorLabel.setVisible(false);
			
			JLabel ProgramadorLabel = new JLabel("Lenguaje de programacion");
			ProgramadorLabel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
			ProgramadorLabel.setBounds(264, 195, 197, 38);
			panel.add(ProgramadorLabel);
			ProgramadorLabel.setVisible(false);
			
			JLabel PlanificadorLabel = new JLabel("Frecuencia de planificacion:");
			PlanificadorLabel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 13));
			PlanificadorLabel.setBounds(175, 318, 192, 14);
			panel.add(PlanificadorLabel);
			PlanificadorLabel.setVisible(false);
			
			JSpinner jefeSpinner = new JSpinner();
			jefeSpinner.setBounds(341, 312, 48, 20);
			panel.add(jefeSpinner);
			jefeSpinner.setVisible(true);
			
			JSpinner DiseniadorSpinner = new JSpinner();
			DiseniadorSpinner.setBounds(329, 315, 48, 20);
			panel.add(DiseniadorSpinner);
			DiseniadorSpinner.setVisible(false);
			
			JSpinner PlanificadorSpinner = new JSpinner();
			PlanificadorSpinner.setBounds(353, 315, 36, 20);
			PlanificadorSpinner.setVisible(false);
			panel.add(PlanificadorSpinner);
			
			JDateChooser dateChooser = new JDateChooser();
			dateChooser.setBounds(99, 183, 135, 22);
			panel.add(dateChooser);
			
			JRadioButton rbtJefe = new JRadioButton("Jefe");
			rbtJefe.setSelected(true);
			
			rbtJefe.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					panel_3.setVisible(false);
					JefeLabel.setVisible(true);
					DiseniadorLabel.setVisible(false);
					ProgramadorLabel.setVisible(false);
					PlanificadorLabel.setVisible(false);
					jefeSpinner.setVisible(true);
					DiseniadorSpinner.setVisible(false);
					PlanificadorSpinner.setVisible(false);
					btnAgregar.setVisible(false);
					
				}
			});
			rbtJefe.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 12));
			rbtJefe.setBackground(new Color(135, 206, 235));
			rbtJefe.setBounds(6, 7, 100, 23);
			panel_1.add(rbtJefe);
			
			JRadioButton rbtDesign = new JRadioButton("Dise\u00F1ador");
			rbtDesign.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					panel_3.setVisible(false);
					JefeLabel.setVisible(false);
					DiseniadorLabel.setVisible(true);
					ProgramadorLabel.setVisible(false);
					PlanificadorLabel.setVisible(false);
					jefeSpinner.setVisible(false);
					DiseniadorSpinner.setVisible(true);
					PlanificadorSpinner.setVisible(false);
					btnAgregar.setVisible(false);
				}
			});
			rbtDesign.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 12));
			rbtDesign.setBackground(new Color(135, 206, 235));
			rbtDesign.setBounds(6, 49, 100, 23);
			panel_1.add(rbtDesign);
			
			JRadioButton rbtProgramador = new JRadioButton("Programador");
			rbtProgramador.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					panel_3.setVisible(true);
					JefeLabel.setVisible(false);
					DiseniadorLabel.setVisible(false);
					ProgramadorLabel.setVisible(true);
					PlanificadorLabel.setVisible(false);
					jefeSpinner.setVisible(false);
					DiseniadorSpinner.setVisible(false);
					PlanificadorSpinner.setVisible(false);
					btnAgregar.setVisible(true);
				}
			});
			rbtProgramador.setBackground(new Color(135, 206, 235));
			rbtProgramador.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 12));
			rbtProgramador.setBounds(6, 92, 100, 23);
			panel_1.add(rbtProgramador);
			
			JRadioButton rbtPlanificador = new JRadioButton("Planificador");
			rbtPlanificador.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				panel_3.setVisible(false);
				JefeLabel.setVisible(false);
				DiseniadorLabel.setVisible(false);
				ProgramadorLabel.setVisible(false);
				PlanificadorLabel.setVisible(true);
				jefeSpinner.setVisible(false);
				DiseniadorSpinner.setVisible(false);
				PlanificadorSpinner.setVisible(true);
				btnAgregar.setVisible(false);
				}
			});
			rbtPlanificador.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 12));
			rbtPlanificador.setBackground(new Color(135, 206, 235));
			rbtPlanificador.setBounds(6, 133, 100, 23);
			panel_1.add(rbtPlanificador);
			
			botones.add(rbtJefe);
			botones.add(rbtDesign);
			botones.add(rbtProgramador);
			botones.add(rbtPlanificador);
			
			
			JPanel panel_2 = new JPanel();
			panel_2.setBackground(new Color(135, 206, 250));
			panel_2.setBorder(new LineBorder(new Color(160, 82, 45), 2));
			panel_2.setBounds(0, 425, 556, 45);
			panel.add(panel_2);
			panel_2.setLayout(null);
			{
				JButton okButton = new JButton("Registrar");
				okButton.setForeground(new Color(255, 255, 255));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						String id = Cedula.getText();
						String nombre = Nombre.getText();
						String apellido = textField.getText();
						String direccion = Direccion.getText();
						String sexo = comboBox.getSelectedItem().toString();
						java.util.Date utilDate = dateChooser.getDate();
						
						 if (id.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || direccion.isEmpty() || sexo.equals("<Seleccione>") || utilDate == null) {
					            JOptionPane.showMessageDialog(null, "Favor de llenar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
					        } else {
					            java.sql.Date fecha = new java.sql.Date(utilDate.getTime());
					            Trabajador t = null;
					            
					            if (rbtJefe.isSelected()) {
					                int cantidad = (int) jefeSpinner.getValue();
					                t = new Jefe(id, nombre, apellido, direccion, sexo, fecha, "Cumplidor", cantidad,0,0);
					            } else if (rbtDesign.isSelected()) {
					                int añosExperiencia = (int) DiseniadorSpinner.getValue();
					                t = new Diseñador(id, nombre, apellido, direccion, sexo, fecha, "Cumplidor", añosExperiencia,0,0);
					            } else if (rbtProgramador.isSelected()) {
					                t = new Programador(id, nombre, apellido, direccion, sexo, fecha, "Cumplidor", listaAgregados,0,0);
					            } else if (rbtPlanificador.isSelected()) {
					                int frecuenciaPlanificacion = (int) PlanificadorSpinner.getValue();
					                t = new Planificador(id, nombre, apellido, direccion, sexo, fecha, "Cumplidor", frecuenciaPlanificacion,0,0);
					            }
					            
					            if (t != null) {
					                Empresa empresa = Empresa.cargarEmpresa("controlador.dat");
					                if (empresa == null) {
					                    empresa = new Empresa();
					                }
					                empresa.ingresarTrabajador(t);
					                Empresa.guardarEmpresa(empresa, "controlador.dat");
					                JOptionPane.showMessageDialog(null, "Registro Satisfactorio", "Informacion", JOptionPane.INFORMATION_MESSAGE);
					                clean();
					            }
					        }
					    } 
					});
						
				okButton.setBounds(376, 7, 89, 34);
				panel_2.add(okButton);
				okButton.setBackground(Color.GREEN);
				okButton.setActionCommand("OK");
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cerrar");
				cancelButton.setForeground(new Color(255, 255, 255));
				cancelButton.setBounds(474, 7, 72, 34);
				panel_2.add(cancelButton);
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setBackground(new Color(255, 99, 71));
				cancelButton.setActionCommand("Cancel");
			}
			
			JPanel panel_5 = new JPanel();
			panel_5.setBorder(new LineBorder(new Color(160, 82, 45), 2, true));
			panel_5.setBounds(10, 48, 535, 362);
			panel.add(panel_5);
			
			loadLenguajes();
		}
	}
	
	private void loadLenguajes() {

	    model.setRowCount(0);
	    for (String lenguaje : listaLenguajes) {
	        model.addRow(new Object[]{lenguaje});
	    }
	    tableListaLenguajes.setModel(model);


	    modelAgregado.setRowCount(0);
	    for (String agregado : listaAgregados) {
	        modelAgregado.addRow(new Object[]{agregado});
	    }
	    tableAgregados.setModel(modelAgregado);
	}

	
	void clean() {
		
		Cedula.setText(" ");
		Nombre.setText(" ");
		textField.setText(" "); 
		Direccion.setText(" ");
		
	}
}

package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import logico.Diseñador;
import logico.Empresa;
import logico.Jefe;
import logico.Planificador;
import logico.Programador;
import logico.Trabajador;

import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import java.awt.SystemColor;
import javax.swing.DefaultComboBoxModel;

public class ListadoTrabajador extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField Id;
	private JTextField lblNombre;
	private JTextField Apellido;
	private DefaultTableModel model;
	private Object[] row;
	private JTable table;
	Empresa empresa = Empresa.cargarEmpresa("controlador.dat");
	
	//aaaaaaaaa

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ListadoTrabajador dialog = new ListadoTrabajador();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */

	
		public ListadoTrabajador() {
			
		setResizable(false);
			setBounds(100, 100, 652, 466);
			getContentPane().setLayout(new BorderLayout());
			contentPanel.setBackground(new Color(230, 230, 250));
			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			getContentPane().add(contentPanel, BorderLayout.CENTER);
			contentPanel.setLayout(null);
			setLocationRelativeTo(null);
			String[] header = {"ID", "Nombre", "Apellido","Edad","Evaluacion","Rol","Dato del rol"};
			
			model = new DefaultTableModel();
			model.setColumnIdentifiers(header);
			
			

			JPanel panel_1 = new JPanel();
			panel_1.setBackground(SystemColor.menu);
			panel_1.setBorder(new LineBorder(new Color(160, 82, 45), 2, true));
			panel_1.setBounds(10, 35, 612, 344);
			contentPanel.add(panel_1);
			panel_1.setLayout(null);
			{
				JLabel lblBuscar = new JLabel("Buscar por:");
				lblBuscar.setBounds(10, 11, 82, 14);
				panel_1.add(lblBuscar);
				lblBuscar.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
			}
			{
				JLabel lblCedula = new JLabel("Cedula:");
				lblCedula.setBounds(10, 36, 96, 14);
				panel_1.add(lblCedula);
				lblCedula.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
			}
			{
				JLabel lblNombre = new JLabel("Nombre(s):");
				lblNombre.setBounds(10, 61, 96, 14);
				panel_1.add(lblNombre);
				lblNombre.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
			}
			{
				JLabel lblApellidos = new JLabel("Apellido(s):");
				lblApellidos.setBounds(10, 89, 96, 14);
				panel_1.add(lblApellidos);
				lblApellidos.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
			}
			{
				JLabel lblEvaluacion = new JLabel("Evaluacion anual:");
				lblEvaluacion.setBounds(10, 114, 119, 14);
				panel_1.add(lblEvaluacion);
				lblEvaluacion.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
			}
			JComboBox rolTrabajador = new JComboBox();
			rolTrabajador.setModel(new DefaultComboBoxModel(new String[] {"<Seleccione>", "Jefe", "Programador", "Dise\u00F1ador", "Planificador"}));
			rolTrabajador.setBackground(Color.WHITE);
			rolTrabajador.setBounds(135, 137, 289, 20);
			panel_1.add(rolTrabajador);
			
			Id = new JTextField();
			Id.setBounds(135, 34, 289, 20);
			panel_1.add(Id);
			Id.setColumns(10);
			
			lblNombre = new JTextField();
			lblNombre.setBounds(135, 59, 289, 20);
			panel_1.add(lblNombre);
			lblNombre.setColumns(10);
			
			Apellido = new JTextField();
			Apellido.setBounds(135, 87, 289, 20);
			panel_1.add(Apellido);
			Apellido.setColumns(10);
			
			JComboBox EvalAnual = new JComboBox();
			EvalAnual.setModel(new DefaultComboBoxModel(new String[] {"<Seleccione>", "Destacado", "Cumplidor", "Incumplidor"}));
			EvalAnual.setBounds(135, 112, 289, 20);
			panel_1.add(EvalAnual);
			EvalAnual.setBackground(Color.WHITE);
			
			JButton btnBuscar = new JButton("Buscar");
			btnBuscar.setBounds(452, 33, 89, 42);
			panel_1.add(btnBuscar);
			btnBuscar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					

				    model.setRowCount(0);

				    String id = Id.getText();
				    String nombre = lblNombre.getText();
				    String apellido = Apellido.getText();
				    String evalAnual = EvalAnual.getSelectedItem().toString();
				    String rol = rolTrabajador.getSelectedItem().toString();

				    for (Trabajador trabajador : empresa.getMisTrabajadores()) {
				        if ((id.isEmpty() || trabajador.getCedula().equalsIgnoreCase(id))
				                && (nombre.isEmpty() || trabajador.getNombre().equalsIgnoreCase(nombre))
				                && (apellido.isEmpty() || trabajador.getApellidos().equalsIgnoreCase(apellido))
				                && ("<Seleccione>".equals(evalAnual) || trabajador.getEvaluacionAnual().equalsIgnoreCase(evalAnual))
				                && ("<Seleccione>".equals(rol) || trabajador.getClass().getSimpleName().equalsIgnoreCase(rol))) {
				            row[0] = trabajador.getCedula();
				            row[1] = trabajador.getNombre();
				            row[2] = trabajador.getApellidos();
				            row[3] = trabajador.calcularEdad();
				            row[4] = trabajador.getEvaluacionAnual();
				            row[5] = trabajador.getClass().getSimpleName();
				            if (trabajador instanceof Jefe) {
				                row[6] = ((Jefe) trabajador).getCantTrabajadores() + " Trabajadores";
				            } else if (trabajador instanceof Programador) {
				                row[6] = ((Programador) trabajador).getLenguajeDeProgramacion();
				            } else if (trabajador instanceof Planificador) {
				                row[6] = ((Planificador) trabajador).getFrecuenciaDePlanificacion() + " Frecuencia";
				            } else if (trabajador instanceof Diseñador) {
				                row[6] = ((Diseñador) trabajador).getCantAgnoExp() + " Años exp";
				            }
				            model.addRow(row);
				        }
				        
				    }

				    
				}

			});
			btnBuscar.setBackground(new Color(51, 204, 153));
			btnBuscar.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 11));
			
			JButton btnReiniciar = new JButton("Reiniciar");
			btnReiniciar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Id.setEnabled(true);
				    lblNombre.setEnabled(true);
				    Apellido.setEnabled(true);
				    EvalAnual.setEnabled(true);
				    rolTrabajador.setEnabled(true);
				    Id.setText("");
				    lblNombre.setText("");
				    Apellido.setText("");
				    EvalAnual.setSelectedIndex(0);
				    rolTrabajador.setSelectedIndex(0);
					loadTrabajadores();
				}
			});
			btnReiniciar.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 11));
			btnReiniciar.setBackground(new Color(240, 128, 128));
			btnReiniciar.setBounds(452, 84, 89, 27);
			panel_1.add(btnReiniciar);
			
			JPanel panel = new JPanel();
			panel.setBounds(10, 178, 585, 155);
			panel_1.add(panel);
			panel.setBorder(new LineBorder(new Color(30, 144, 255), 2, true));
			panel.setLayout(new BorderLayout(0, 0));
			
			JScrollPane scrollPane = new JScrollPane();
			panel.add(scrollPane, BorderLayout.CENTER);
			table = new JTable();
			table.setBackground(SystemColor.textHighlightText);
			table.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			table.setColumnSelectionAllowed(true);
			table.setEnabled(false);
			scrollPane.setViewportView(table);
			table.setModel(model);
			
			JLabel lblRol = new JLabel("Rol de trabajador:");
			lblRol.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
			lblRol.setBounds(10, 139, 131, 14);
			panel_1.add(lblRol);
			
			
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
			
			loadTrabajadores();
		}
		
		
			
		private void loadTrabajadores() {
		    model.setRowCount(0);
		    row = new Object[model.getColumnCount()];
		    
		    if (empresa != null) {
		        ArrayList<Trabajador> trabajadores = empresa.getMisTrabajadores();
		        if (trabajadores != null && !trabajadores.isEmpty()) {
		            
		            for (Trabajador trabajador : trabajadores) {
		                System.out.println("Cargando trabajador: " + trabajador.getNombre());
		                row[0] = trabajador.getCedula();
		                row[1] = trabajador.getNombre();
		                row[2] = trabajador.getApellidos();
		                row[3] = trabajador.calcularEdad();
		                row[4] = trabajador.getEvaluacionAnual();
		                row[5] = trabajador.getClass().getSimpleName();
		                if (trabajador instanceof Jefe) {
		                	row[6] =((Jefe) trabajador).getCantTrabajadores() + " Trabajadores";
						}
		                else if(trabajador instanceof Programador) {
		                	row[6] =((Programador) trabajador).getLenguajeDeProgramacion();
		                } 
		                else if(trabajador instanceof Planificador) {
		                	row[6] =((Planificador) trabajador).getFrecuenciaDePlanificacion() + " Frecuencia" ;
		                }
		                else if(trabajador instanceof Diseñador) {
		                	row[6] =((Diseñador) trabajador).getCantAgnoExp() + " Años exp";
		                }
		                
		                model.addRow(row);
		                
		                
		            }
		                 
		            table.setModel(model);
		        }
		    }
		}
	}



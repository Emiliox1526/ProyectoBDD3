package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.Font;
import javax.swing.JComboBox;
import java.awt.Color;
import com.toedter.calendar.JDateChooser;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;
import logico.Cliente;
import logico.Contrato;
import logico.Diseñador;
import logico.Empresa;
import logico.Jefe;
import logico.Planificador;
import logico.Programador;
import logico.Proyecto;
import logico.Trabajador;
import javax.swing.border.LineBorder;
//aaaaaaaaa
public class RegistroProyecto extends JDialog {
	protected static final JOptionPane OptionPane = null;
	private JTextField txtidProyecto;
	private JTextField txtidContrato;
	private JTable tableTrabajadores;
	private JTable tableTrabajadoresAgregados;
	Empresa empresa = Empresa.cargarEmpresa("controlador.dat");
	int backup =0;
	private JDateChooser dChooserInicio;
    private JDateChooser dChooserFinal;
	private static DefaultTableModel model;
	private static DefaultTableModel modelAgregado;
	private JTextField txtIdCliente;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private Cliente cliente = null;
	private int indexSeleccionadoTrabajadores = -1;
	private int indexSeleccionadoAgregados = -1;
	private Object[] rowTrabajadores;
	private Object[] rowAgregado;
	ArrayList<Trabajador> listaTrabajadores = empresa.getMisTrabajadores();
	ArrayList<Trabajador> listaAgregados = new ArrayList<>();
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegistroProyecto dialog = new RegistroProyecto();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RegistroProyecto() {
		
		setBounds(100, 100, 754, 563);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cerrar");
				cancelButton.setBackground(new Color(255, 99, 71));
				cancelButton.setForeground(new Color(255, 255, 255));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				

				
				JButton btnReg = new JButton("Registrar");
				btnReg.setBackground(Color.GREEN);
				btnReg.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						java.util.Date fechaI = dChooserInicio.getDate();
						java.util.Date fechaF = dChooserFinal.getDate();
						Contrato contrato = new Contrato(txtidContrato.getText().toString(), cliente.getId(), cliente.getNombre(), fechaF , fechaI);
						
						Proyecto proyecto = new Proyecto(txtidProyecto.getText().toString(), cliente, listaAgregados, contrato, fechaI, fechaF, null, null, false);
						
						if (contrato != null && proyecto != null) {
							Empresa empresa = Empresa.cargarEmpresa("controlador.dat");
			                if (empresa == null) {
			                    empresa = new Empresa();
			                }
							empresa.ingresarContrato(contrato);
							empresa.ingresarProyecto(proyecto);
							Empresa.guardarEmpresa(empresa, "controlador.dat");
							OptionPane.showMessageDialog(null, "Proyecto registrado", "Informacion", JOptionPane.INFORMATION_MESSAGE);
						}
						
					}
				});
				buttonPane.add(btnReg);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		{
			JPanel panelprincipal = new JPanel();
			getContentPane().add(panelprincipal, BorderLayout.CENTER);
			panelprincipal.setLayout(null);
			panelprincipal.setBackground(new Color(230,230,250));
			
			JPanel panelDatos = new JPanel();
			panelDatos.setBounds(12, 44, 702, 437);
			panelprincipal.add(panelDatos);
			panelDatos.setLayout(null);
			
			JLabel lblidProyecto = new JLabel("Proyecto:");
			lblidProyecto.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
			lblidProyecto.setBounds(12, 13, 75, 14);
			panelDatos.add(lblidProyecto);
			
			txtidProyecto = new JTextField();
			txtidProyecto.setEditable(false);
			txtidProyecto.setBounds(150, 10, 205, 20);
			txtidProyecto.setText("P-"+Empresa.getInstance().idProyecto);
			panelDatos.add(txtidProyecto);
			txtidProyecto.setColumns(10);
			
			JLabel lblcliente = new JLabel("Cedula Cliente:");
			lblcliente.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
			lblcliente.setBounds(12, 45, 120, 14);
			panelDatos.add(lblcliente);
			
			JLabel lblcontrato = new JLabel("Contrato:");
			lblcontrato.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
			lblcontrato.setBounds(12, 111, 75, 14);
			panelDatos.add(lblcontrato);
			
			txtidContrato = new JTextField();
			txtidContrato.setEditable(false);
			txtidContrato.setColumns(10);
			txtidContrato.setBounds(150, 108, 205, 20);
			txtidContrato.setText("C-"+Empresa.getInstance().idContrato);
			panelDatos.add(txtidContrato);
			
			JLabel lblfechaInicio = new JLabel("Fecha de inicio:");
			lblfechaInicio.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
			lblfechaInicio.setBounds(12, 144, 110, 14);
			panelDatos.add(lblfechaInicio);
			
			JLabel lblfechaEntrega = new JLabel("Fecha de entrega:");
			lblfechaEntrega.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
			lblfechaEntrega.setBounds(12, 177, 120, 14);
			panelDatos.add(lblfechaEntrega);
			
			dChooserInicio = new JDateChooser();
			dChooserInicio.setBounds(150, 141, 205, 20);
			panelDatos.add(dChooserInicio);

			dChooserFinal = new JDateChooser();
			dChooserFinal.setBounds(150, 174, 205, 20);
			panelDatos.add(dChooserFinal);
			
			JPanel panelTrabajadores = new JPanel();
			panelTrabajadores.setBorder(new LineBorder(new Color(30, 144, 255), 2, true));
			panelTrabajadores.setBackground(new Color(175, 238, 238));
			panelTrabajadores.setBounds(12, 224, 680, 202);
			panelDatos.add(panelTrabajadores);
			panelTrabajadores.setLayout(null);
			String [] headers = {"Rol","Cedula", "Nombre", "Apellido","Evaluacion","Dato de rol"};
			model = new DefaultTableModel();
			model.setColumnIdentifiers(headers);
			String [] headersAgregados = {"Cedula", "Nombre", "Apellido", "Rol","Dato del Rol"};
			modelAgregado = new DefaultTableModel();
			modelAgregado.setColumnIdentifiers(headersAgregados);
			
			JPanel panel = new JPanel();
			panel.setBorder(new LineBorder(new Color(30, 144, 255), 2));
			panel.setBackground(new Color(135, 206, 250));
			panel.setBounds(0, 166, 680, 36);
			panelTrabajadores.add(panel);
			
			JButton btnAgregar = new JButton("Agregar");
			btnAgregar.setBackground(new Color(154, 205, 50));
			btnAgregar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (indexSeleccionadoTrabajadores != -1) {
			            Trabajador trabajadorAgregado = listaTrabajadores.get(indexSeleccionadoTrabajadores);
			            if (puedeAgregarTrabajador(trabajadorAgregado)) {
			                listaAgregados.add(trabajadorAgregado);
			                listaTrabajadores.remove(indexSeleccionadoTrabajadores);
			                loadTrabajadores();
			            } else {
			                JOptionPane.showMessageDialog(null, "No se puede agregar más trabajadores de este tipo", "Error", JOptionPane.ERROR_MESSAGE);
			            }
			        } else {
			            JOptionPane.showMessageDialog(null, "Seleccione un trabajador para agregar", "Error", JOptionPane.ERROR_MESSAGE);
			        }
			    }
			});
			          /*  listaAgregados.add(trabajadorAgregado);
			            listaTrabajadores.remove(indexSeleccionadoTrabajadores);
			            loadTrabajadores();
			        } else {
			            JOptionPane.showMessageDialog(null, "Seleccione un trabajador para agregar", "Error", JOptionPane.ERROR_MESSAGE);
			        }
				}
			});*/
			btnAgregar.setFont(new Font("Yu Gothic Medium", Font.BOLD, 11));
			panel.add(btnAgregar);
			
			JButton btnQuitar = new JButton("Quitar");
			btnQuitar.setBackground(new Color(205, 92, 92));
			btnQuitar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (indexSeleccionadoAgregados != -1) {
			            Trabajador trabajadorEliminado = listaAgregados.get(indexSeleccionadoAgregados);
			            listaTrabajadores.add(trabajadorEliminado);
			            listaAgregados.remove(indexSeleccionadoAgregados);
			            loadTrabajadores();
			        } else {
			            JOptionPane.showMessageDialog(null, "Seleccione un trabajador para quitar", "Error", JOptionPane.ERROR_MESSAGE);
			        }
				}
			});
			btnQuitar.setFont(new Font("Yu Gothic Medium", Font.BOLD, 11));
			panel.add(btnQuitar);
			
			JPanel panel_1 = new JPanel();
			panel_1.setBorder(new LineBorder(new Color(30, 144, 255), 2, true));
			panel_1.setBackground(new Color(30, 144, 255));
			panel_1.setBounds(10, 13, 331, 140);
			panelTrabajadores.add(panel_1);
			panel_1.setLayout(new BorderLayout(0, 0));
			
			JScrollPane scrollPane = new JScrollPane();
			panel_1.add(scrollPane, BorderLayout.CENTER);

			
			tableTrabajadores = new JTable();
			tableTrabajadores.setBorder(new LineBorder(new Color(0, 0, 0)));
			tableTrabajadores.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					indexSeleccionadoTrabajadores = tableTrabajadores.getSelectedRow();
					System.out.println(tableTrabajadores.getSelectedRow());
				}
			});
			
			tableTrabajadores.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 11));
			scrollPane.setColumnHeaderView(tableTrabajadores);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBorder(new LineBorder(new Color(30, 144, 255), 2));
			panel_2.setBackground(new Color(100, 149, 237));
			panel_2.setBounds(351, 13, 319, 142);
			panelTrabajadores.add(panel_2);
			panel_2.setLayout(new BorderLayout(0, 0));
			
			JScrollPane scrollPane_1 = new JScrollPane();
			panel_2.add(scrollPane_1, BorderLayout.CENTER);
			
			tableTrabajadoresAgregados = new JTable();
			tableTrabajadoresAgregados.setBorder(new LineBorder(new Color(30, 144, 255)));
			tableTrabajadoresAgregados.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					indexSeleccionadoAgregados = tableTrabajadoresAgregados.getSelectedRow();
				}
			});
			tableTrabajadoresAgregados.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 11));
			scrollPane_1.setColumnHeaderView(tableTrabajadoresAgregados);
			
			JLabel lblTrabajadores = new JLabel("Trabajadores");
			lblTrabajadores.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
			lblTrabajadores.setBounds(312, 209, 120, 14);
			panelDatos.add(lblTrabajadores);
			
			txtIdCliente = new JTextField();
			txtIdCliente.setColumns(10);
			txtIdCliente.setBounds(150, 43, 205, 20);
			panelDatos.add(txtIdCliente);
			
			JButton btnBuscar = new JButton("Buscar");
			btnBuscar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String id = txtIdCliente.getText();
					cliente = empresa.BuscarClienteByID(id);
					if (cliente != null) {
					    txtNombre.setText(cliente.getNombre());
					    txtApellido.setText(cliente.getApellido());
					} else {
					    JOptionPane.showMessageDialog(null,"Cliente no encontrado para la cedula: " + id+ ". Puede crear el cliente en el botón de agregar nuevo" ,"Error", JOptionPane.ERROR_MESSAGE);
					}

				}

			});
			btnBuscar.setBounds(410, 41, 120, 25);
			panelDatos.add(btnBuscar);
	
			
			JButton btnNewButton_1 = new JButton("Agregar Nuevo");
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
				}
			});
			btnNewButton_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					RegistroCliente c = new RegistroCliente();
					c.setModal(true);
					c.setVisible(true);
				}
			});
			btnNewButton_1.setBounds(546, 41, 120, 25);
			panelDatos.add(btnNewButton_1);
			
			JLabel lblNombre = new JLabel("Nombre(s):");
			lblNombre.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
			lblNombre.setBounds(12, 78, 75, 14);
			panelDatos.add(lblNombre);
			
			txtNombre = new JTextField();
			txtNombre.setEditable(false);
			txtNombre.setColumns(10);
			txtNombre.setBounds(150, 75, 150, 20);
			panelDatos.add(txtNombre);
			
			txtApellido = new JTextField();
			txtApellido.setEditable(false);
			txtApellido.setColumns(10);
			txtApellido.setBounds(410, 75, 150, 20);
			panelDatos.add(txtApellido);
			
			JLabel lblApellido = new JLabel("Apellido(s):");
			lblApellido.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
			lblApellido.setBounds(312, 79, 75, 14);
			panelDatos.add(lblApellido);
			
			JLabel lblNombreVentana = new JLabel("Nuevo proyecto");
			lblNombreVentana.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 15));
			lblNombreVentana.setBounds(12, 13, 126, 25);
			panelprincipal.add(lblNombreVentana);
			
			
		}
		loadTrabajadores();
	}
	
	private void loadTrabajadores() {
	    model.setRowCount(0);
	    rowTrabajadores = new Object[model.getColumnCount()];

	    if (empresa != null) {
	        if (listaTrabajadores != null && !listaTrabajadores.isEmpty()) {

	            for (Trabajador trabajador : listaTrabajadores) {
	                System.out.println("Cargando trabajador: " + trabajador.getNombre());
	                rowTrabajadores[0] = trabajador.getClass().getSimpleName();
	                rowTrabajadores[1] = trabajador.getCedula();
	                rowTrabajadores[2] = trabajador.getNombre();
	                rowTrabajadores[3] = trabajador.getApellidos();
	                rowTrabajadores[4] = trabajador.getEvaluacionAnual();
	                if (trabajador instanceof Jefe) {
	                    rowTrabajadores[5] = ((Jefe) trabajador).getCantTrabajadores() + " Trabajadores";
	                } else if (trabajador instanceof Programador) {
	                    rowTrabajadores[5] = ((Programador) trabajador).getLenguajeDeProgramacion();
	                } else if (trabajador instanceof Planificador) {
	                    rowTrabajadores[5] = ((Planificador) trabajador).getFrecuenciaDePlanificacion() + " Frecuencia";
	                } else if (trabajador instanceof Diseñador) {
	                    rowTrabajadores[5] = ((Diseñador) trabajador).getCantAgnoExp() + " Años exp";
	                }

	                model.addRow(rowTrabajadores);

	            }

	            tableTrabajadores.setModel(model);
	        }

	        modelAgregado.setRowCount(0);
	        for (Trabajador trabajadorAgregado : listaAgregados) {
	            System.out.println("Cargando trabajador agregado: " + trabajadorAgregado.getNombre());
	            rowAgregado = new Object[modelAgregado.getColumnCount()];
	            rowAgregado[0] = trabajadorAgregado.getCedula();
	            rowAgregado[1] = trabajadorAgregado.getNombre();
	            rowAgregado[2] = trabajadorAgregado.getApellidos();
	            rowAgregado[3] = trabajadorAgregado.getClass().getSimpleName(); 
	            if (trabajadorAgregado instanceof Jefe) {
	                rowAgregado[4] = ((Jefe) trabajadorAgregado).getCantTrabajadores() + " Trabajadores";
	            } else if (trabajadorAgregado instanceof Programador) {
	                rowAgregado[4] = ((Programador) trabajadorAgregado).getLenguajeDeProgramacion();
	            } else if (trabajadorAgregado instanceof Planificador) {
	                rowAgregado[4] = ((Planificador) trabajadorAgregado).getFrecuenciaDePlanificacion() + " Frecuencia";
	            } else if (trabajadorAgregado instanceof Diseñador) {
	                rowAgregado[4] = ((Diseñador) trabajadorAgregado).getCantAgnoExp() + " Años exp";
	            }

	            modelAgregado.addRow(rowAgregado);
	        }

	        tableTrabajadoresAgregados.setModel(modelAgregado);
	    }
	}

	
	private Cliente buscarCliente(String id) {
		Cliente aux = null;
		for (Cliente c : Empresa.getInstance().getMisClientes()) {
			if (aux.getId().equalsIgnoreCase(id)) {
				aux = c;
			}		
		}
		return aux;
	}
	
	private boolean puedeAgregarTrabajador(Trabajador trabajador) {
		
		 int contJefes = 0;
		 int contDiseñadores = 0;
		 int contProgramadores = 0;
		 int contPlanificadores = 0;
		    for (Trabajador t : listaAgregados) {
		        if (t instanceof Jefe) {
		            contJefes++;
		        } else if (t instanceof Diseñador) {
		            contDiseñadores++;
		        } else if (t instanceof Programador) {
		            contProgramadores++;
		        } else if (t instanceof Planificador) {
		            contPlanificadores++;
		        }
		    }
		    if (trabajador instanceof Jefe) {
		        return contJefes == 0 && listaAgregados.size() < 5;
		    } else if (trabajador instanceof Diseñador) {
		        return contDiseñadores == 0 && listaAgregados.size() < 5; 
		    } else if (trabajador instanceof Programador) {
		        return contProgramadores < 3 && listaAgregados.size() < 5;
		    } else if (trabajador instanceof Planificador) {
		        return contPlanificadores == 0 && (contProgramadores >= 2 || contProgramadores == 1 && contPlanificadores == 0) && listaAgregados.size() < 5;
		    }
		    return true;
		}
}

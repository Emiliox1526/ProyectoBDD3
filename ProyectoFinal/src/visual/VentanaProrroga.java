package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import com.toedter.calendar.JDayChooser;

import logico.Empresa;
import logico.Proyecto;

import com.toedter.calendar.JDateChooser;
import javax.swing.ButtonGroup;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class VentanaProrroga extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtIdProyecto;
	private JTextField txtIdCliente;
	private JTextField txtNombreCliente;
	private JTextField txtFechaInicio;
	private JTextField txtFechaFinal;
	
	private final ButtonGroup buttonGroup = new ButtonGroup();
	Empresa empresa = Empresa.cargarEmpresa("controlador.dat");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VentanaProrroga dialog = new VentanaProrroga();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VentanaProrroga() {
		setBounds(100, 100, 450, 320);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color (230,230,250));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(160, 82, 45), 2, true));
		panel.setBounds(12, 33, 408, 192);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		JSpinner spnDias = new JSpinner();
		spnDias.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spnDias.setBounds(186, 145, 97, 22);
		panel.add(spnDias);
		{
			JLabel lblProyecto = new JLabel("Id Proyecto:");
			lblProyecto.setBounds(12, 13, 75, 16);
			panel.add(lblProyecto);
		}
		{
			txtIdProyecto = new JTextField();
			txtIdProyecto.setBounds(99, 10, 75, 22);
			panel.add(txtIdProyecto);
			txtIdProyecto.setColumns(10);
		}
		{
			JLabel lblIdCliente = new JLabel("Id Cliente:");
			lblIdCliente.setBounds(12, 45, 75, 16);
			panel.add(lblIdCliente);
		}
		{
			txtIdCliente = new JTextField();
			txtIdCliente.setEditable(false);
			txtIdCliente.setColumns(10);
			txtIdCliente.setBounds(99, 42, 75, 22);
			panel.add(txtIdCliente);
		}
		{
			JLabel lblNombreCliente = new JLabel("Nombre Cliente:");
			lblNombreCliente.setBounds(186, 45, 97, 16);
			panel.add(lblNombreCliente);
		}
		{
			txtNombreCliente = new JTextField();
			txtNombreCliente.setEditable(false);
			txtNombreCliente.setColumns(10);
			txtNombreCliente.setBounds(295, 42, 88, 22);
			panel.add(txtNombreCliente);
		}
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String idProyecto = txtIdProyecto.getText();
				Proyecto proyecto = empresa.BuscarProyectoById(idProyecto);
				 if (proyecto != null) {
			            txtIdCliente.setText(proyecto.getCliente().getId());
			            txtNombreCliente.setText(proyecto.getCliente().getNombre());
			            txtFechaInicio.setText(proyecto.getFechaInicio().toString());
			            txtFechaFinal.setText(proyecto.getFechaEntregaFinal().toString());
			        } else {
			            JOptionPane.showMessageDialog(null, "Proyecto no encontrado para el ID: " + idProyecto, "Error", JOptionPane.ERROR_MESSAGE);
			        }
			    }
			});
		
		btnBuscar.setBounds(186, 9, 97, 25);
		panel.add(btnBuscar);
		
		JLabel lblFechaInicio = new JLabel("Fecha Inicio:");
		lblFechaInicio.setBounds(12, 77, 75, 16);
		panel.add(lblFechaInicio);
		
		txtFechaInicio = new JTextField();
		txtFechaInicio.setEditable(false);
		txtFechaInicio.setColumns(10);
		txtFechaInicio.setBounds(99, 74, 75, 22);
		panel.add(txtFechaInicio);
		
		JLabel lblFechaFinal = new JLabel("Fecha Final:");
		lblFechaFinal.setBounds(186, 77, 97, 16);
		panel.add(lblFechaFinal);
		
		txtFechaFinal = new JTextField();
		txtFechaFinal.setEditable(false);
		txtFechaFinal.setColumns(10);
		txtFechaFinal.setBounds(295, 74, 88, 22);
		panel.add(txtFechaFinal);
		
		JLabel lblProrrogarPor = new JLabel("Prorrogar por:");
		lblProrrogarPor.setBounds(12, 106, 90, 16);
		panel.add(lblProrrogarPor);
		
		JRadioButton rdbtnDia = new JRadioButton("Cantidad de d\u00EDas");
		rdbtnDia.setSelected(true);
		buttonGroup.add(rdbtnDia);
		rdbtnDia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spnDias.setVisible(true);
			}
		});
		rdbtnDia.setBounds(12, 131, 127, 25);
		panel.add(rdbtnDia);
		
		JRadioButton rdbtnFecha = new JRadioButton("Fecha");
		buttonGroup.add(rdbtnFecha);
		rdbtnFecha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spnDias.setVisible(false);
			}
		});
		rdbtnFecha.setBounds(12, 161, 127, 25);
		panel.add(rdbtnFecha);
		
		JDateChooser dateFecha = new JDateChooser();
		dateFecha.setBounds(186, 145, 97, 22);
		panel.add(dateFecha);
		
		JLabel lblProrroga = new JLabel("Prorroga");
		lblProrroga.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 15));
		lblProrroga.setBounds(12, 13, 80, 16);
		contentPanel.add(lblProrroga);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			buttonPane.setBackground(new Color(135, 206, 250));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnProrrogar = new JButton("Prorrogar");
				btnProrrogar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String idProyecto = txtIdProyecto.getText();
				        Empresa empresa = Empresa.getInstance();
				        Proyecto proyecto = empresa.BuscarProyectoById(idProyecto);
				        
				        if (proyecto != null) {
				            Date fechaActual = new Date();
				            if (!proyecto.isPenalizado()) { 
				                if (proyecto.getFechaEntregaFinal().after(fechaActual)) { 
				                    if (rdbtnDia.isSelected()) {
				                        try {
				                            int diasProrroga = (int) spnDias.getValue();
				                            Calendar calendar = Calendar.getInstance();
				                            calendar.setTime(proyecto.getFechaEntregaFinal());
				                            calendar.add(Calendar.DAY_OF_MONTH, diasProrroga);
				                            Date nuevaFechaEntregaFinal = calendar.getTime();
				                            proyecto.setFechaEntregaFinal(nuevaFechaEntregaFinal);
				                            proyecto.setPenalizado(true);
				                            JOptionPane.showMessageDialog(null, "Prorroga realizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
				                            Empresa.guardarEmpresa(empresa, "controlador.dat");
				                        } catch (NumberFormatException ex) {
				                            JOptionPane.showMessageDialog(null, "Por favor, ingrese una cantidad válida de días.", "Error", JOptionPane.ERROR_MESSAGE);
				                        }
				                    } else if (rdbtnFecha.isSelected()) {
				                        Date nuevaFechaEntregaFinal = dateFecha.getDate();
				                        proyecto.setFechaEntregaFinal(nuevaFechaEntregaFinal);
				                        proyecto.setPenalizado(true);
				                        JOptionPane.showMessageDialog(null, "Prorroga realizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
				                    } else {
				                        JOptionPane.showMessageDialog(null, "Por favor, seleccione una opción de prorroga.", "Error", JOptionPane.ERROR_MESSAGE);
				                    }
				                } else {
				                    JOptionPane.showMessageDialog(null, "No se puede realizar la prórroga porque la fecha de entrega ya ha pasado.", "Error", JOptionPane.ERROR_MESSAGE);
				                }
				            } else {
				                JOptionPane.showMessageDialog(null, "No se puede realizar la prórroga porque el proyecto ya está penalizado.", "Error", JOptionPane.ERROR_MESSAGE);
				            }
				        } else {
				            JOptionPane.showMessageDialog(null, "Proyecto no encontrado para el ID: " + idProyecto, "Error", JOptionPane.ERROR_MESSAGE);
				        }
				    }
				});
				btnProrrogar.setBackground(Color.GREEN);
				btnProrrogar.setForeground(new Color(255, 255, 255));
				btnProrrogar.setActionCommand("OK");
				buttonPane.add(btnProrrogar);
				getRootPane().setDefaultButton(btnProrrogar);
			}
			{
				JButton cancelButton = new JButton("Cerrar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
						ListadoProyecto l = new ListadoProyecto();
						l.setModal(true);
						l.setVisible(true);
					}
				});
				cancelButton.setActionCommand("btnCerrar");
				cancelButton.setForeground(new Color(255, 255, 255));
				cancelButton.setBackground(new Color(255, 99, 71));
				buttonPane.add(cancelButton);
			}
		}
	}
}

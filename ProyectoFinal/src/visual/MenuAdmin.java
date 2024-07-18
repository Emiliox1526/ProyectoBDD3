package visual;

// :))

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JSplitPane;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

import logico.Control;

public class MenuAdmin extends JFrame {

    private JPanel contentPane;
    private JTextField txtUser;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Principal frame = new Principal(null);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public MenuAdmin(Control control) {
        setTitle("Empresa de programacion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 736, 355);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setLocationRelativeTo(null);
        ImageIcon clienteIcon = new ImageIcon(Principal.class.getResource("/images/Cliente.png"));
        Image clienteImage = clienteIcon.getImage().getScaledInstance(120-15, 122-15, Image.SCALE_SMOOTH);
        ImageIcon trabajadorIcon = new ImageIcon(Principal.class.getResource("/images/Trabajadores.png"));
        Image trabajadorImage = trabajadorIcon.getImage().getScaledInstance(120-15, 122-15, Image.SCALE_SMOOTH);
        ImageIcon proyectoIcon = new ImageIcon(Principal.class.getResource("/images/Proyecto.png"));
        Image proyectoImage = proyectoIcon.getImage().getScaledInstance(120, 122, Image.SCALE_SMOOTH);
        ImageIcon adminIcon = new ImageIcon(Principal.class.getResource("/images/Admin.png"));
        Image adminImage = adminIcon.getImage().getScaledInstance(120, 122, Image.SCALE_SMOOTH);
        ImageIcon exitIcon = new ImageIcon(Principal.class.getResource("/images/ExitButton.png"));
        Image exitImage = exitIcon.getImage().getScaledInstance(120-15, 122-15, Image.SCALE_SMOOTH);

        JLabel lblNewLabel = new JLabel("Menu Administracion");
        lblNewLabel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
        lblNewLabel.setBounds(10, 11, 167, 22);
        contentPane.add(lblNewLabel);

        JPanel panel_3 = new JPanel();
        panel_3.setForeground(new Color(153, 102, 102));
        panel_3.setBorder(new LineBorder(new Color(153, 102, 102), 2, true));
        panel_3.setBounds(10, 39, 695, 254);
        contentPane.add(panel_3);
        panel_3.setLayout(null);
        
                JPanel panel = new JPanel();
                panel.setBackground(new Color(51, 204, 204));
                panel.setBounds(48, 56, 120, 33);
                panel_3.add(panel);
                panel.setBorder(new LineBorder(new Color(0, 0, 0)));
                
                        JLabel lblNewLabel_1 = new JLabel("Registrar Clientes");
                        lblNewLabel_1.setForeground(new Color(255, 255, 255));
                        panel.add(lblNewLabel_1);
                        lblNewLabel_1.setFont(new Font("Yu Gothic Medium", Font.BOLD, 11));
                        
                                JButton btnCliente = new JButton("");
                                btnCliente.addActionListener(new ActionListener() {
                                	public void actionPerformed(ActionEvent e) {
                                		RegistroCliente c = new RegistroCliente();
                                		c.setModal(true);
                                		c.setVisible(true);
                                	}
                                	
                                });
                                
                                btnCliente.setBounds(48, 87, 120, 122);
                                panel_3.add(btnCliente);
                                btnCliente.setForeground(Color.WHITE);
                                btnCliente.setIcon(new ImageIcon(clienteImage));
                                
                                btnCliente.setBackground(Color.WHITE);
                                
                                        JPanel panel_1 = new JPanel();
                                        panel_1.setBackground(new Color(102, 204, 204));
                                        panel_1.setBounds(210, 56, 120, 33);
                                        panel_3.add(panel_1);
                                        panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
                                        
                                                JLabel lblTrabajadores_1 = new JLabel("Registrar Trabajadores");
                                                lblTrabajadores_1.setForeground(new Color(255, 255, 255));
                                                lblTrabajadores_1.setBackground(new Color(255, 255, 255));
                                                lblTrabajadores_1.setFont(new Font("Yu Gothic Medium", Font.BOLD, 9));
                                                panel_1.add(lblTrabajadores_1);
                                                
                                                        JButton btnTrabajador = new JButton("");
                                                        btnTrabajador.addActionListener(new ActionListener() {
                                                        	public void actionPerformed(ActionEvent e) {
                                                        		RegistroTrabajador l = new RegistroTrabajador();
                                                        		l.setModal(true);
                                                        		l.setVisible(true);
                                                        	}
                                                        });
                                                        btnTrabajador.setBounds(210, 87, 120, 122);
                                                        panel_3.add(btnTrabajador);
                                                        btnTrabajador.setHorizontalAlignment(SwingConstants.LEFT);
                                                        btnTrabajador.setForeground(Color.WHITE);
                                                        btnTrabajador.setBackground(Color.WHITE);
                                                        btnTrabajador.setHorizontalAlignment(SwingConstants.CENTER);
                                                        btnTrabajador.setIcon(new ImageIcon(trabajadorImage));
                                                        
                                                                JPanel panel_2 = new JPanel();
                                                                panel_2.setBackground(new Color(102, 204, 204));
                                                                panel_2.setBounds(367, 56, 120, 33);
                                                                panel_3.add(panel_2);
                                                                panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
                                                                
                                                                        JLabel lblProyectos_1 = new JLabel("Registrar Proyectos");
                                                                        lblProyectos_1.setForeground(new Color(255, 255, 255));
                                                                        lblProyectos_1.setFont(new Font("Yu Gothic Medium", Font.BOLD, 11));
                                                                        panel_2.add(lblProyectos_1);
                                                                        
                                                                                JButton btnProyecto = new JButton("");
                                                                                btnProyecto.addActionListener(new ActionListener() {
                                                                                	public void actionPerformed(ActionEvent e) {
                                                                                		RegistroProyecto registroProyecto = new RegistroProyecto();
                                                                                		registroProyecto.setModal(true);
                                                                                		registroProyecto.setVisible(true);
                                                                                	}
                                                                                });
                                                                                btnProyecto.setBounds(367, 87, 120, 122);
                                                                                panel_3.add(btnProyecto);
                                                                                btnProyecto.setForeground(Color.WHITE);
                                                                                btnProyecto.setBackground(Color.WHITE);
                                                                                btnProyecto.setIcon(new ImageIcon(proyectoImage));                                  
                                                                                btnProyecto.setHorizontalAlignment(SwingConstants.CENTER);
                                                                                JButton btnExit = new JButton("");
                                                                                btnExit.addActionListener(new ActionListener() {
                                                                                	public void actionPerformed(ActionEvent arg0) {
                                                                                		Principal p = new Principal(control);
                                                                                		p.setVisible(true);
                                                                                		dispose();
                                                                                	}
                                                                                });
                                                                                btnExit.setBounds(520, 87, 120, 122);
                                                                                
                                                                                btnExit.setForeground(Color.WHITE);
                                                                                btnExit.setBackground(Color.WHITE);
                                                                                btnExit.setHorizontalAlignment(SwingConstants.CENTER);
                                                                                btnExit.setIcon(new ImageIcon(exitImage));
                                                                               
                                                                               
                                                                                panel_3.add(btnExit);
                                                                                
                                                                                JPanel panel_4 = new JPanel();
                                                                                panel_4.setBackground(new Color(102, 204, 204));
                                                                                panel_4.setBorder(new LineBorder(new Color(0, 0, 0)));
                                                                                panel_4.setBounds(520, 56, 120, 33);
                                                                                panel_3.add(panel_4);
                                                                                
                                                                                JLabel lblAdministracion = new JLabel("SALIR");
                                                                                lblAdministracion.setForeground(new Color(255, 255, 255));
                                                                                lblAdministracion.setFont(new Font("Yu Gothic Medium", Font.BOLD, 11));
                                                                                panel_4.add(lblAdministracion);
                                                                                
                                                                                JLabel lblCuentaLogeada = new JLabel("Cuenta Logeada:");
                                                                                lblCuentaLogeada.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
                                                                                lblCuentaLogeada.setBounds(410, 11, 137, 22);
                                                                                contentPane.add(lblCuentaLogeada);
                                                                                
                                                                                txtUser = new JTextField();
                                                                                txtUser.setEditable(false);
                                                                                txtUser.setBounds(528, 13, 177, 20);
                                                                                contentPane.add(txtUser);
                                                                                txtUser.setColumns(10);
                                                                                if (control == null) {
                                                                                	txtUser.setText("");
																				} else {
																					txtUser.setText(Control.getLoginUser().getUserName());
																					if(Control.getLoginUser().getTipo().equals("Administrador")) {
																						btnExit.setEnabled(true);
																					}
																				}
                                                                                
    }
}
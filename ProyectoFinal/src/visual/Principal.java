package visual;

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



public class Principal extends JFrame {

    private JPanel contentPane;
    private JTextField txtUser;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Principal frame = new Principal();
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
    public Principal() {
        setTitle("PUCMM");
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

        JLabel lblNewLabel = new JLabel("Centro Profesoral");
        lblNewLabel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
        lblNewLabel.setBounds(10, 11, 137, 22);
        contentPane.add(lblNewLabel);

        JPanel panel_3 = new JPanel();
        panel_3.setForeground(new Color(153, 102, 102));
        panel_3.setBorder(new LineBorder(new Color(153, 102, 102), 2, true));
        panel_3.setBounds(58, 44, 599, 254);
        contentPane.add(panel_3);
        panel_3.setLayout(null);
        
                JPanel panel = new JPanel();
                panel.setBackground(new Color(51, 204, 204));
                panel.setBounds(28, 56, 172, 33);
                panel_3.add(panel);
                panel.setBorder(new LineBorder(new Color(0, 0, 0)));
                
                        JLabel lblNewLabel_1 = new JLabel("INSCRIBIR O ELIMINAR");
                        lblNewLabel_1.setForeground(new Color(255, 255, 255));
                        panel.add(lblNewLabel_1);
                        lblNewLabel_1.setFont(new Font("Yu Gothic Medium", Font.BOLD, 11));
                        
                                JButton btnCliente = new JButton("");
                                btnCliente.addActionListener(new ActionListener() {
                                	public void actionPerformed(ActionEvent e) {
                                		InscribirGrupo c = new InscribirGrupo();
                                		c.setModal(true);
                                		c.setVisible(true);
                                		
                                	}
                                	
                                });
                                
                                btnCliente.setBounds(28, 87, 172, 122);
                                panel_3.add(btnCliente);
                                btnCliente.setForeground(Color.WHITE);
                                btnCliente.setIcon(new ImageIcon(clienteImage));
                                
                                btnCliente.setBackground(Color.WHITE);
                                
                                        JPanel panel_1 = new JPanel();
                                        panel_1.setBackground(new Color(102, 204, 204));
                                        panel_1.setBounds(242, 56, 120, 33);
                                        panel_3.add(panel_1);
                                        panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
                                        
                                                JLabel lblTrabajadores_1 = new JLabel("INFORME");
                                                lblTrabajadores_1.setForeground(new Color(255, 255, 255));
                                                lblTrabajadores_1.setBackground(new Color(255, 255, 255));
                                                lblTrabajadores_1.setFont(new Font("Yu Gothic Medium", Font.BOLD, 11));
                                                panel_1.add(lblTrabajadores_1);
                                                
                                                        JButton btnTrabajador = new JButton("");
                                                        btnTrabajador.addActionListener(new ActionListener() {
                                                        	public void actionPerformed(ActionEvent e) {
                                                        		InformeInscripccion l = new InformeInscripccion();
                                                        		l.setModal(true);
                                                        		l.setVisible(true);
                                                        	}
                                                        });
                                                        btnTrabajador.setBounds(242, 87, 120, 122);
                                                        panel_3.add(btnTrabajador);
                                                        btnTrabajador.setHorizontalAlignment(SwingConstants.LEFT);
                                                        btnTrabajador.setForeground(Color.WHITE);
                                                        btnTrabajador.setBackground(Color.WHITE);
                                                        btnTrabajador.setHorizontalAlignment(SwingConstants.CENTER);
                                                        btnTrabajador.setIcon(new ImageIcon(trabajadorImage));
                                                        
                                                                JPanel panel_2 = new JPanel();
                                                                panel_2.setBackground(new Color(102, 204, 204));
                                                                panel_2.setBounds(416, 56, 120, 33);
                                                                panel_3.add(panel_2);
                                                                panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
                                                                
                                                                        JLabel lblProyectos_1 = new JLabel("HORARIO");
                                                                        lblProyectos_1.setForeground(new Color(255, 255, 255));
                                                                        lblProyectos_1.setFont(new Font("Yu Gothic Medium", Font.BOLD, 11));
                                                                        panel_2.add(lblProyectos_1);
                                                                        
                                                                                JButton btnProyecto = new JButton("");
                                                                                btnProyecto.addActionListener(new ActionListener() {
                                                                                	public void actionPerformed(ActionEvent e) {
                                                                                		HorarioEstudiante listadoProyecto = new HorarioEstudiante();
                                                                                		listadoProyecto.setModal(true);
                                                                                		listadoProyecto.setVisible(true);
                                                                                		
                                                                                	}
                                                                                });
                                                                                btnProyecto.setBounds(416, 87, 120, 122);
                                                                                panel_3.add(btnProyecto);
                                                                                btnProyecto.setForeground(Color.WHITE);
                                                                                btnProyecto.setBackground(Color.WHITE);
                                                                                btnProyecto.setIcon(new ImageIcon(proyectoImage));                                  
                                                                                btnProyecto.setHorizontalAlignment(SwingConstants.CENTER);
                                                                                JButton btnAdministracion = new JButton("");
                                                                              ;
                                                                                btnAdministracion.setEnabled(false);
                                                                                btnAdministracion.setForeground(Color.WHITE);
                                                                                btnAdministracion.setBackground(Color.WHITE);
                                                                                btnAdministracion.setHorizontalAlignment(SwingConstants.CENTER);
                                                                                btnAdministracion.setIcon(new ImageIcon(adminImage));
                                                                                btnAdministracion.setBounds(520, 87, 120, 122);
                                                                                
                                                                              
                                                                              
                                                                     
    }
}
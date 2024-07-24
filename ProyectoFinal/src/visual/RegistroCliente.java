package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;

import logico.Cliente;
import logico.Empresa;
import javax.swing.border.LineBorder;
//aaaaaaaaa
public class RegistroCliente extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextField txtCedula;
    private JTextField txtNombre;
    private JTextField txtDir;
    private JTextField txtApellido;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            RegistroCliente dialog = new RegistroCliente();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public RegistroCliente() {
        setResizable(false);
        setBounds(100, 100, 766, 473);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(230, 230, 250));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        setLocationRelativeTo(null);


        JLabel lblNewLabel = new JLabel("Registro Estudiante");
        lblNewLabel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 15));
        lblNewLabel.setBounds(10, 21, 137, 14);
        contentPanel.add(lblNewLabel);

        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(new Color(205, 133, 63), 2, true));
        panel.setBounds(10, 35, 727, 361);
        contentPanel.add(panel);
        panel.setLayout(null);

        JLabel lblApellido = new JLabel("Apellido(s):");
        lblApellido.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
        lblApellido.setBounds(270, 80, 84, 14);
        panel.add(lblApellido);

        txtApellido = new JTextField();
        txtApellido.setFont(new Font("Dialog", Font.PLAIN, 10));
        txtApellido.setColumns(10);
        txtApellido.setBounds(349, 80, 176, 20);
        panel.add(txtApellido);

        txtDir = new JTextField();
        txtDir.setBounds(118, 123, 439, 20);
        panel.add(txtDir);
        txtDir.setFont(new Font("Dialog", Font.PLAIN, 10));
        txtDir.setColumns(10);

        JLabel lblNewLabel_3 = new JLabel("ID de Carrera:");
        lblNewLabel_3.setBounds(10, 125, 139, 14);
        panel.add(lblNewLabel_3);
        lblNewLabel_3.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));

        JLabel lblNewLabel_1 = new JLabel("Nombre(s):");
        lblNewLabel_1.setBounds(10, 81, 84, 14);
        panel.add(lblNewLabel_1);
        lblNewLabel_1.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));

        txtNombre = new JTextField();
        txtNombre.setBounds(84, 79, 166, 20);
        panel.add(txtNombre);
        txtNombre.setFont(new Font("Dialog", Font.PLAIN, 10));
        txtNombre.setColumns(10);

        txtCedula = new JTextField();
        txtCedula.setBounds(84, 29, 166, 20);
        panel.add(txtCedula);
        txtCedula.setFont(new Font("Dialog", Font.PLAIN, 10));
        txtCedula.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("ID");
        lblNewLabel_2.setBounds(10, 31, 46, 14);
        panel.add(lblNewLabel_2);
        lblNewLabel_2.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
        
        JLabel lblIdCategoriaDe = new JLabel("ID Categoria de Pago:");
        lblIdCategoriaDe.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
        lblIdCategoriaDe.setBounds(10, 181, 166, 14);
        panel.add(lblIdCategoriaDe);
        
        textField = new JTextField();
        textField.setFont(new Font("Dialog", Font.PLAIN, 10));
        textField.setColumns(10);
        textField.setBounds(186, 179, 64, 20);
        panel.add(textField);
        
        JLabel lblIdNacionalidad = new JLabel("ID Nacionalidad:");
        lblIdNacionalidad.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
        lblIdNacionalidad.setBounds(10, 235, 166, 14);
        panel.add(lblIdNacionalidad);
        
        textField_1 = new JTextField();
        textField_1.setFont(new Font("Dialog", Font.PLAIN, 10));
        textField_1.setColumns(10);
        textField_1.setBounds(149, 233, 64, 20);
        panel.add(textField_1);
        
        JLabel lblDireccion = new JLabel("Direccion:");
        lblDireccion.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 14));
        lblDireccion.setBounds(10, 299, 166, 14);
        panel.add(lblDireccion);
        
        textField_2 = new JTextField();
        textField_2.setFont(new Font("Dialog", Font.PLAIN, 10));
        textField_2.setColumns(10);
        textField_2.setBounds(107, 297, 439, 20);
        panel.add(textField_2);
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setBorder(new LineBorder(new Color(184, 134, 11), 2, true));
            buttonPane.setBackground(new Color(100, 149, 237));
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("Registrar\r\n");
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        if (!txtCedula.getText().isEmpty() && !txtNombre.getText().isEmpty() && !txtDir.getText().isEmpty()) {
                            Cliente cliente = new Cliente(txtCedula.getText(), txtNombre.getText(),txtApellido.getText(), txtDir.getText(), 0);
                            
                            if (cliente != null) {
                        		Empresa empresa = Empresa.cargarEmpresa("controlador.dat");
                                if (empresa == null) {
                                    empresa = new Empresa();
                                }
                                empresa.ingresarCliente(cliente);
                                Empresa.guardarEmpresa(empresa, "controlador.dat");
                                
                        	}
                            JOptionPane.showMessageDialog(null, "Registro Satisfactorio", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                            clean();
                        } else {
                            JOptionPane.showMessageDialog(null, "Favor de llenar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                okButton.setBackground(new Color(0, 255, 0));
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                JButton cancelButton = new JButton("Cerrar");
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

    void loadCliente(Cliente cliente) {
    	

    }

    void clean() {
        txtCedula.setText(" ");
        txtNombre.setText(" ");
        txtApellido.setText(" ");
        txtDir.setText(" ");
    }
}

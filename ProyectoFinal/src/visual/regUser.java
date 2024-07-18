package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import logico.Control;
import logico.User;

public class regUser extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmar;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            regUser dialog = new regUser();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public regUser() {
        setResizable(false);
        setBounds(100, 100, 605, 346);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(112, 128, 144));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(SystemColor.controlDkShadow);
        panel_1.setBounds(0, 0, 647, 33);
        contentPanel.add(panel_1);
        panel_1.setLayout(null);

        JLabel lblRegistro = new JLabel("Registro");
        lblRegistro.setBounds(10, 11, 119, 25);
        lblRegistro.setForeground(Color.WHITE);
        lblRegistro.setFont(new Font("Yu Gothic Medium", Font.BOLD, 15));
        panel_1.add(lblRegistro);

        JSeparator separator = new JSeparator();
        separator.setForeground(Color.WHITE);
        separator.setBounds(0, 33, 647, 12);
        contentPanel.add(separator);

        JPanel panel_2 = new JPanel();
        panel_2.setLayout(null);
        panel_2.setBorder(new LineBorder(new Color(205, 133, 63), 2, true));
        panel_2.setBackground(new Color(255, 245, 238));
        panel_2.setBounds(10, 50, 572, 239);
        contentPanel.add(panel_2);

        JPanel panel_3 = new JPanel();
        panel_3.setLayout(null);
        panel_3.setBorder(new LineBorder(new Color(205, 133, 63), 2, true));
        panel_3.setBackground(new Color(255, 250, 240));
        panel_3.setBounds(0, 197, 572, 42);
        panel_2.add(panel_3);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText().trim();
                char[] passwordChars = txtPassword.getPassword();
                String password = new String(passwordChars);
                char[] confirmarPasswordChars = txtConfirmar.getPassword();
                String confirmarPassword = new String(confirmarPasswordChars);

                if (!password.equals(confirmarPassword)) {
                    JOptionPane.showMessageDialog(regUser.this, "Las contraseñas no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    boolean usuarioExistente = false;
                    for (User user : Control.getInstance().getMisUsers()) {
                        if (user.getUserName().equals(usuario)) {
                            usuarioExistente = true;
                        }
                    }
                    if (usuarioExistente == true) {
                        JOptionPane.showMessageDialog(regUser.this, "El nombre de usuario ya está en uso", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        User usuarioRegistrado = new User("Cliente", usuario, password);
                        Control control = Control.getInstance();
                        control.regUser(usuarioRegistrado);

                        try (FileOutputStream fos = new FileOutputStream("empresa.dat");
                             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                            oos.writeObject(Control.getInstance());
                            JOptionPane.showMessageDialog(regUser.this, "Usuario " + usuarioRegistrado.getUserName() + " registrado exitosamente", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(regUser.this, "Error al guardar en archivo", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        VentanaLogin frame = new VentanaLogin();
                        dispose();
                        frame.setVisible(true);
                    }
                }
            }
        });
        btnRegistrar.setBounds(470, 11, 92, 23);
        panel_3.add(btnRegistrar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VentanaLogin frame = new VentanaLogin();
                dispose();
                frame.setVisible(true);
            }
        });
        btnCancelar.setActionCommand("Cancel");
        btnCancelar.setBounds(373, 11, 85, 23);
        panel_3.add(btnCancelar);

        JLabel label = new JLabel("Usuario:");
        label.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 15));
        label.setBounds(12, 53, 89, 14);
        panel_2.add(label);

        JLabel label_1 = new JLabel("Contraseña:");
        label_1.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 15));
        label_1.setBounds(12, 100, 99, 14);
        panel_2.add(label_1);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(121, 51, 421, 20);
        panel_2.add(txtUsuario);
        txtUsuario.setColumns(10);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(121, 98, 421, 20);
        panel_2.add(txtPassword);

        JLabel lblConfirmarContrasea = new JLabel("Confirmar contraseña:");
        lblConfirmarContrasea.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 15));
        lblConfirmarContrasea.setBounds(12, 148, 170, 14);
        panel_2.add(lblConfirmarContrasea);

        txtConfirmar = new JPasswordField();
        txtConfirmar.setBounds(177, 146, 365, 20);
        panel_2.add(txtConfirmar);
        txtConfirmar.setColumns(10);
    }
}

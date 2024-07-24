package visual;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

import Conexion.SQL;

public class EliminarHorario extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextField txtIdPeriodo;
    private JTextField txtIdAsignatura;
    private JTextField txtNumeroGrupo;
    private JTextField txtNumeroDia;
    private JTextField txtFechaHoraInicio;

    public static void main(String[] args) {
        try {
            EliminarHorario dialog = new EliminarHorario();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public EliminarHorario() {
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new LineBorder(new Color(160, 82, 45), 2, true));
        contentPanel.setBackground(new Color(230, 230, 250));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblIdPeriodo = new JLabel("ID Periodo:");
        lblIdPeriodo.setBounds(10, 20, 80, 14);
        contentPanel.add(lblIdPeriodo);

        txtIdPeriodo = new JTextField();
        txtIdPeriodo.setBounds(100, 17, 150, 20);
        contentPanel.add(txtIdPeriodo);
        txtIdPeriodo.setColumns(10);

        JLabel lblIdAsignatura = new JLabel("ID Asignatura:");
        lblIdAsignatura.setBounds(10, 50, 100, 14);
        contentPanel.add(lblIdAsignatura);

        txtIdAsignatura = new JTextField();
        txtIdAsignatura.setBounds(120, 47, 150, 20);
        contentPanel.add(txtIdAsignatura);
        txtIdAsignatura.setColumns(10);

        JLabel lblNumeroGrupo = new JLabel("Numero Grupo:");
        lblNumeroGrupo.setBounds(10, 80, 100, 14);
        contentPanel.add(lblNumeroGrupo);

        txtNumeroGrupo = new JTextField();
        txtNumeroGrupo.setBounds(120, 77, 150, 20);
        contentPanel.add(txtNumeroGrupo);
        txtNumeroGrupo.setColumns(10);

        JLabel lblNumeroDia = new JLabel("Numero Día:");
        lblNumeroDia.setBounds(10, 110, 100, 14);
        contentPanel.add(lblNumeroDia);

        txtNumeroDia = new JTextField();
        txtNumeroDia.setBounds(120, 107, 150, 20);
        contentPanel.add(txtNumeroDia);
        txtNumeroDia.setColumns(10);

        JLabel lblFechaHoraInicio = new JLabel("Fecha Hora Inicio:");
        lblFechaHoraInicio.setBounds(10, 140, 120, 14);
        contentPanel.add(lblFechaHoraInicio);

        txtFechaHoraInicio = new JTextField();
        txtFechaHoraInicio.setBounds(140, 137, 150, 20);
        contentPanel.add(txtFechaHoraInicio);
        txtFechaHoraInicio.setColumns(10);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarHorario();
            }
        });
        btnEliminar.setActionCommand("OK");
        buttonPane.add(btnEliminar);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
    }

    private void eliminarHorario() {
        String idPeriodo = txtIdPeriodo.getText();
        String idAsignatura = txtIdAsignatura.getText();
        String numeroGrupo = txtNumeroGrupo.getText();
        String numeroDia = txtNumeroDia.getText();
        String fechaHoraInicio = txtFechaHoraInicio.getText();

        Connection connection = SQL.getConnection();
        if (connection != null) {
            try {
                String query = "DELETE FROM [Horario de un Grupo] WHERE IdPeriodo = ? AND IdAsignatura = ? AND Numero Del Grupo = ? AND Numero dia Semana = ? AND Fecha Hora Inicio = ?";
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setString(1, idPeriodo);
                pstmt.setString(2, idAsignatura);
                pstmt.setString(3, numeroGrupo);
                pstmt.setShort(4, Short.parseShort(numeroDia));
                pstmt.setTimestamp(5, Timestamp.valueOf(fechaHoraInicio));

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Horario eliminado exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el horario.");
                }

                pstmt.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar el horario.");
            }
        }
    }
}

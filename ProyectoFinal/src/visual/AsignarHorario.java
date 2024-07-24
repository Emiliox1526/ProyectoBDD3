package visual;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

import Conexion.SQL;

public class AsignarHorario extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextField txtIdPeriodo;
    private JTextField txtIdAsignatura;
    private JTextField txtNumeroGrupo;
    private JTextField txtNumeroDia;
    private JTextField txtFechaHoraInicio;
    private JTextField txtFechaHoraFin;

    public static void main(String[] args) {
        try {
            AsignarHorario dialog = new AsignarHorario();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AsignarHorario() {
        setBounds(100, 100, 450, 350);
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

        JLabel lblFechaHoraFin = new JLabel("Fecha Hora Fin:");
        lblFechaHoraFin.setBounds(10, 170, 100, 14);
        contentPanel.add(lblFechaHoraFin);

        txtFechaHoraFin = new JTextField();
        txtFechaHoraFin.setBounds(120, 167, 150, 20);
        contentPanel.add(txtFechaHoraFin);
        txtFechaHoraFin.setColumns(10);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton btnAsignar = new JButton("Asignar");
        btnAsignar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                asignarHorario();
            }
        });
        btnAsignar.setActionCommand("OK");
        buttonPane.add(btnAsignar);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
    }

    private void asignarHorario() {
        String idPeriodo = txtIdPeriodo.getText();
        String idAsignatura = txtIdAsignatura.getText();
        String numeroGrupo = txtNumeroGrupo.getText();
        String numeroDia = txtNumeroDia.getText();
        String fechaHoraInicio = txtFechaHoraInicio.getText();
        String fechaHoraFin = txtFechaHoraFin.getText();

        Connection connection = SQL.getConnection();
        if (connection != null) {
            try {
                String query = "INSERT INTO [Horario de un Grupo] (IdPeriodo, IdAsignatura, Numero Del Grupo, Numero dia Semana, Fecha Hora Inicio, Fecha Hora Fin) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setString(1, idPeriodo);
                pstmt.setString(2, idAsignatura);
                pstmt.setString(3, numeroGrupo);
                pstmt.setInt(4, Integer.parseInt(numeroDia));
                pstmt.setTimestamp(5, Timestamp.valueOf(fechaHoraInicio));
                pstmt.setTimestamp(6, Timestamp.valueOf(fechaHoraFin));

                pstmt.executeUpdate();
                pstmt.close();
                connection.close();
                JOptionPane.showMessageDialog(this, "Horario asignado exitosamente.");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al asignar el horario.");
            }
        }
    }
}

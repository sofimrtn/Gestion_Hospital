package ui.admin;

import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import business.CitasController;
import business.LogController;
import business.dto.CambioDto;
import business.dto.CitaDto;

import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;
import javax.swing.JTextField;

public class VentanaGestionarCitas extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panelPrincipal;
	private JPanel panelBotones;
	private JLabel lblLista;
	private DefaultListModel<CitaDto> modeloCitas;
	private CitasController citasController;
	private JList<CitaDto> list;
	private JButton btnModificar;
	private JButton btnEliminar;
	private JTextField textFieldIdMed;
	private JPanel panel;
	private JButton btnBuscarPorMed;

	public VentanaGestionarCitas() {
		modeloCitas = new DefaultListModel<>();
		citasController = new CitasController();
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getPanelPrincipal());

		CambioDto cambio = new CambioDto();
		cambio.cambio = "El administrador ha listado todas las citas";
		cambio.fecha = new Date();
		LogController lc = new LogController();
		lc.aņadirCambio(cambio);
		cargarModelo();
	}

	private JPanel getPanelPrincipal() {
		if (panelPrincipal == null) {
			panelPrincipal = new JPanel();
			panelPrincipal.setLayout(null);
			panelPrincipal.add(getPanelBotones());
			panelPrincipal.add(getPanel());
		}
		return panelPrincipal;
	}

	private JPanel getPanelBotones() {
		if (panelBotones == null) {
			panelBotones = new JPanel();
			panelBotones.setBounds(0, 518, 782, 35);
			panelBotones.add(getBtnModificar());
			panelBotones.add(getBtnEliminar());
		}
		return panelBotones;
	}

	private JLabel getLblLista() {
		if (lblLista == null) {
			lblLista = new JLabel("------------------Listado de Citas------------------");
			lblLista.setBounds(29, 5, 723, 45);
			lblLista.setFont(new Font("Tahoma", Font.PLAIN, 37));
			lblLista.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblLista;
	}

	private JList<CitaDto> getList() {
		if (list == null) {
			list = new JList<>(modeloCitas);
			list.setBounds(12, 89, 758, 416);
			list.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					CitaDto seleccion = list.getSelectedValue();
					if (seleccion != null) {
						getBtnEliminar().setEnabled(true);
						getBtnModificar().setEnabled(true);
					}
				}
			});
		}
		return list;
	}

	private JButton getBtnModificar() {
		if (btnModificar == null) {
			btnModificar = new JButton("Modificar");
			btnModificar.setEnabled(false);
			btnModificar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					VentanaModificarCitas vv = new VentanaModificarCitas(list.getSelectedValue());
					vv.setVisible(true);
					vv.setLocationRelativeTo(null);

					CambioDto cambio = new CambioDto();
					cambio.cambio = "El administrador ha entrado a modificar la cita con id: "
							+ list.getSelectedValue().id;
					cambio.fecha = new Date();
					LogController lc = new LogController();
					lc.aņadirCambio(cambio);
				}
			});
		}
		return btnModificar;
	}

	private JButton getBtnEliminar() {
		if (btnEliminar == null) {
			btnEliminar = new JButton("Eliminar ");
			btnEliminar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int respuesta = JOptionPane.showConfirmDialog(null, "Seguro que quiere eliminar la cita?",
							"Eliminar Cita", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
					if (respuesta == JOptionPane.YES_OPTION) {
						citasController.eliminarCita(list.getSelectedValue());
						modeloCitas.removeAllElements();
						cargarModelo();
						textFieldIdMed.setText("");
					}
					CambioDto cambio = new CambioDto();
					cambio.cambio = "El administrador ha eliminado la cita con id: " + list.getSelectedValue().id;
					cambio.fecha = new Date();
					LogController lc = new LogController();
					lc.aņadirCambio(cambio);


				}
			});
			btnEliminar.setEnabled(false);
		}
		return btnEliminar;
	}

	private void cargarModelo() {
		List<CitaDto> citas = citasController.getListadoCompletoDecitas();
		for (CitaDto cita : citas) {
			modeloCitas.addElement(cita);
		}

	}

	private JTextField getTextFieldIdMed() {
		if (textFieldIdMed == null) {
			textFieldIdMed = new JTextField();
			textFieldIdMed.setBounds(12, 56, 116, 22);
			textFieldIdMed.setColumns(10);
		}
		return textFieldIdMed;
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBounds(0, 0, 782, 505);
			panel.setLayout(null);
			panel.add(getLblLista());
			panel.add(getTextFieldIdMed());
			panel.add(getBtnBuscarPorMed());
			panel.add(getList());
		}
		return panel;
	}

	private JButton getBtnBuscarPorMed() {
		if (btnBuscarPorMed == null) {
			btnBuscarPorMed = new JButton("Buscar por id medico");
			btnBuscarPorMed.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					modeloCitas.removeAllElements();
					cargarModeloMed();
				}
			});
			btnBuscarPorMed.setBounds(140, 55, 171, 25);
		}
		return btnBuscarPorMed;
	}

	private void cargarModeloMed() {
		List<CitaDto> citas = citasController.obtenerCitasEmpleado(Integer.parseInt(textFieldIdMed.getText()));
		for (CitaDto cita : citas) {
			modeloCitas.addElement(cita);
		}

	}
}

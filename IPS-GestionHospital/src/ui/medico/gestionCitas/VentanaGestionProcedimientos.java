package ui.medico.gestionCitas;

import javax.swing.JDialog;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDateChooser;

import business.CitasController;
import business.LogController;
import business.dto.CambioDto;
import business.dto.CitaDto;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.util.Date;
import java.awt.event.ActionEvent;

public class VentanaGestionProcedimientos extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panelPrincipal;
	private JPanel panelAddProc;
	private JPanel panelProc;
	private JTextField textFieldProc;
	private JButton btnAadir;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JLabel lblProcedimiento;
	private JLabel lblFecha;
	
	private CitaDto cita;
	private CitasController cc;
	private JTextArea textAreaProc;
	private JDateChooser calendario;
	
	public VentanaGestionProcedimientos(CitaDto cita) {
		this.cc = new CitasController();
		//this.cita=cita;
		setBounds(100, 100, 707, 507);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getPanelPrincipal(), BorderLayout.CENTER);	
		textAreaProc.setText(cc.getCitaById(cita.id).procedimientos);
		this.cita=cita;
		//System.out.println(cita);
	}
	
	private JPanel getPanelPrincipal() {
		if (panelPrincipal == null) {
			panelPrincipal = new JPanel();
			panelPrincipal.setLayout(null);
			panelPrincipal.add(getPanelAddProc());
			panelPrincipal.add(getPanelProc());
			panelPrincipal.add(getBtnAceptar());
			panelPrincipal.add(getBtnCancelar());
		}
		return panelPrincipal;
	}
	private JPanel getPanelAddProc() {
		if (panelAddProc == null) {
			panelAddProc = new JPanel();
			panelAddProc.setBorder(new TitledBorder(null, "A\u00F1adir Procedimientos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelAddProc.setBounds(12, 13, 665, 160);
			panelAddProc.setLayout(null);
			panelAddProc.add(getTextFieldProc());
			panelAddProc.add(getBtnAadir());
			panelAddProc.add(getLblProcedimiento());
			panelAddProc.add(getLblFecha());
			panelAddProc.add(getCalendario());
		}
		return panelAddProc;
	}
	private JPanel getPanelProc() {
		if (panelProc == null) {
			panelProc = new JPanel();
			panelProc.setBorder(new TitledBorder(null, "Procedimientos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelProc.setBounds(12, 180, 665, 208);
			panelProc.setLayout(new BorderLayout(0, 0));
			panelProc.add(getTextAreaProc(), BorderLayout.CENTER);
		}
		return panelProc;
	}
	private JTextField getTextFieldProc() {
		if (textFieldProc == null) {
			textFieldProc = new JTextField();
			textFieldProc.setBounds(12, 57, 364, 22);
			textFieldProc.setColumns(10);
		}
		return textFieldProc;
	}
	private JButton getBtnAadir() {
		if (btnAadir == null) {
			btnAadir = new JButton("A\u00F1adir");
			btnAadir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(textFieldProc.getText().isEmpty()) {
						mostrarMensaje("�Procedimientos vac�o!", "Campo vac�o", JOptionPane.ERROR_MESSAGE);
					}else {
						String p = textAreaProc.getText();
						addProc(p);
						limpiarPanel();
					}
				}
			});
			btnAadir.setBounds(536, 122, 97, 25);
		}
		return btnAadir;
	}
	
	private void addProc(String p) {
		p+=textFieldProc.getText() + " ";
		p+=getCalendario().getDate().toString();
		p+="\n";
		textAreaProc.setText(p);
	}
	
	protected void limpiarPanel() {
		textFieldProc.setText(null);
		calendario.setDate(null);
	}

	private void mostrarMensaje(String mess, String title, int icon) {
		JOptionPane.showMessageDialog(this, mess, title, icon);
	}
	
	private JButton getBtnAceptar() {
		if (btnAceptar == null) {
			btnAceptar = new JButton("Aceptar");
			btnAceptar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(textAreaProc.getText().isEmpty()) {
						mostrarMensaje("�Procedimientos vac�o!", "Campo vac�o", JOptionPane.ERROR_MESSAGE);
					}else {
						String p = textAreaProc.getText();
						cc.addProc(cita, p);
						mostrarMensaje("�Se han a�adido procedimientos!", "Procedimiento a�adido", JOptionPane.DEFAULT_OPTION);
						
						CambioDto cambio = new CambioDto();
						cambio.cambio = "El medico con id: "+ cita.idEmpleado +
								" ha informado a a�adido procedimientos realizados al paciente: "+ cita.idPaciente +
								" durante la cita: "+cita.id +" los procedimientos son: "+p;							
						cambio.fecha = new Date();					
						LogController lc = new LogController();
						lc.a�adirCambio(cambio);
						
						dispose();
						
					}
				}
			});
			btnAceptar.setBounds(580, 401, 97, 25);
		}
		return btnAceptar;
	}
	
	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			btnCancelar.setBounds(471, 401, 97, 25);
		}
		return btnCancelar;
	}
	private JLabel getLblProcedimiento() {
		if (lblProcedimiento == null) {
			lblProcedimiento = new JLabel("Procedimiento:");
			lblProcedimiento.setBounds(12, 28, 112, 16);
		}
		return lblProcedimiento;
	}
	private JLabel getLblFecha() {
		if (lblFecha == null) {
			lblFecha = new JLabel("Fecha:");
			lblFecha.setBounds(12, 95, 56, 16);
		}
		return lblFecha;
	}
	private JTextArea getTextAreaProc() {
		if (textAreaProc == null) {
			textAreaProc = new JTextArea();
			textAreaProc.setLineWrap(true);
			textAreaProc.setWrapStyleWord(true);
			textAreaProc.setEditable(false);
		}
		return textAreaProc;
	}
	private JDateChooser getCalendario() {
		if (calendario == null) {
			calendario = new JDateChooser();
			calendario.setBounds(12, 122, 129, 22);
			calendario.setDate(new java.util.Date());
		}
		return calendario;
	}
}

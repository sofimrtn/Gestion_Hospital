package ui;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import business.LogController;
import business.dto.CambioDto;
import ui.admin.VentanaActividadEmpleados;
import ui.admin.VentanaActividadPacientes;
import ui.admin.VentanaAddEmpleado;
import ui.admin.VentanaAddPaciente;
import ui.admin.VentanaConsultarCitas;
import ui.admin.VentanaDesactivarEmpleado;
import ui.admin.VentanaDesactivarPaciente;
import ui.admin.VentanaFijarCita;
import ui.admin.VentanaGestionarCitas;
import ui.admin.VentanaModificarEmpleado;
import ui.admin.VentanaModificarPaciente;
import ui.jornada.VentanaJornadaLaboral;
import ui.jornada.VentanaVacaciones;

public class VentanaAdministrador extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JButton btnFijarCita;
	private JButton btnConsultarCitas;
	private JButton btnJornadaLaboral;
	private JPanel pnTop;
	private JLabel lblHaEntradoComo;
	private JPanel pnOpciones;
	private JPanel pnCitas;
	private JPanel pnJornadas;
	private JButton btnAsignarVacaciones;
	private JButton btnSalir;

	private JButton btnDesactivarEmpleado;
	private JButton btnVerActividadEmpleados;
	private JPanel panel;
	private JButton btnDesactivarPaciente;
	private JButton btnVerActividadDePacientes;
	private JButton btnAadirEmpleaado;
	private JButton btnModificarEmpleado;

	private JButton btnGestionarCitas;
	private JButton btnAadirPacienteAl;
	private JButton btnModificarPaciente;


	/**
	 * Constructor de la ventana.
	 */
	public VentanaAdministrador() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPnTop(), BorderLayout.NORTH);
		contentPane.add(getPnOpciones(), BorderLayout.CENTER);
	}

	private JButton getBtnFijarCita() {
		if (btnFijarCita == null) {
			btnFijarCita = new JButton("Fijar cita");
			btnFijarCita.setBounds(30, 37, 143, 23);
			btnFijarCita.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaFijarCita vFC = new VentanaFijarCita();
					vFC.setLocationRelativeTo(null);
					vFC.setTitle("Listado completo de Citas");
					vFC.setVisible(true);
				}
			});
		}
		return btnFijarCita;
	}

	private JButton getBtnConsultarCitas() {
		if (btnConsultarCitas == null) {
			btnConsultarCitas = new JButton("Consultar Citas");
			btnConsultarCitas.setBounds(30, 84, 144, 23);
			btnConsultarCitas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaConsultarCitas citas = new VentanaConsultarCitas();
					citas.setLocationRelativeTo(null);
					citas.setTitle("Listado completo de Citas");
					citas.setVisible(true);
				}
			});
		}
		return btnConsultarCitas;
	}

	private JButton getBtnJornadaLaboral() {
		if (btnJornadaLaboral == null) {
			btnJornadaLaboral = new JButton("Asignar jornada laboral");
			btnJornadaLaboral.setBounds(31, 44, 234, 23);
			btnJornadaLaboral.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaJornadaLaboral jornada = new VentanaJornadaLaboral();
					jornada.setLocationRelativeTo(null);
					jornada.setVisible(true);
				}
			});
		}
		return btnJornadaLaboral;
	}

	private void mostrarMensaje(String mess, String title, int icon) {
		JOptionPane.showMessageDialog(this, mess, title, icon);
	}

	private JPanel getPnTop() {
		if (pnTop == null) {
			pnTop = new JPanel();
			pnTop.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			pnTop.add(getLblHaEntradoComo());
		}
		return pnTop;
	}

	private JLabel getLblHaEntradoComo() {
		if (lblHaEntradoComo == null) {
			lblHaEntradoComo = new JLabel("Ha entrado como administrador");
			lblHaEntradoComo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		}
		return lblHaEntradoComo;
	}

	private JPanel getPnOpciones() {
		if (pnOpciones == null) {
			pnOpciones = new JPanel();
			pnOpciones.setLayout(null);
			pnOpciones.add(getPnCitas());
			pnOpciones.add(getPnJornadas());
			pnOpciones.add(getBtnSalir());
			pnOpciones.add(getPanel());
			
			JButton btnVolcarLogA = new JButton("Volcar log a archivo de texto");
			btnVolcarLogA.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					LogController lc = new LogController();
					ArrayList<CambioDto> cambios =  lc.listarLog();
					Date a = new Date();
					FileWriter archivo;
					try {
						archivo = new FileWriter("log"+ a.getTime()+".txt");
						for(CambioDto c: cambios)
						{
							archivo.write(c.toString() + "\n");
						}     
			            
			            
						archivo.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			        
			       
			         
			       
				}
			});
			btnVolcarLogA.setBounds(462, 418, 200, 25);
			pnOpciones.add(btnVolcarLogA);
		}
		return pnOpciones;
	}

	private JPanel getPnCitas() {
		if (pnCitas == null) {
			pnCitas = new JPanel();
			pnCitas.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Citas",
					TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			pnCitas.setBounds(40, 64, 334, 179);
			pnCitas.setLayout(null);
			pnCitas.add(getBtnFijarCita());
			pnCitas.add(getBtnConsultarCitas());
			pnCitas.add(getBtnGestionarCitas());
		}
		return pnCitas;
	}

	private JPanel getPnJornadas() {
		if (pnJornadas == null) {
			pnJornadas = new JPanel();
			pnJornadas.setLayout(null);
			pnJornadas.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Empleados", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			pnJornadas.setBounds(402, 64, 334, 341);
			pnJornadas.add(getBtnJornadaLaboral());
			pnJornadas.add(getBtnAsignarVacaciones());
			pnJornadas.add(getBtnDesactivarEmpleado());
			pnJornadas.add(getBtnVerActividadEmpleados());
			pnJornadas.add(getBtnAadirEmpleaado());
			pnJornadas.add(getBtnModificarEmpleado());
		}
		return pnJornadas;
	}

	private JButton getBtnAsignarVacaciones() {
		if (btnAsignarVacaciones == null) {
			btnAsignarVacaciones = new JButton("Asignar vacaciones");
			btnAsignarVacaciones.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaVacaciones vv = new VentanaVacaciones();
					vv.setVisible(true);
					vv.setLocationRelativeTo(null);
				}
			});
			btnAsignarVacaciones.setBounds(32, 89, 233, 23);
		}
		return btnAsignarVacaciones;
	}

	private JButton getBtnSalir() {
		if (btnSalir == null) {
			btnSalir = new JButton("Salir");
			btnSalir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnSalir.setBounds(685, 495, 89, 23);
		}
		return btnSalir;
	}
	private JButton getBtnDesactivarEmpleado() {
		if (btnDesactivarEmpleado == null) {
			btnDesactivarEmpleado = new JButton("Desactivar empleado");
			btnDesactivarEmpleado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaDesactivarEmpleado vde = new VentanaDesactivarEmpleado();
					vde.setVisible(true);
					vde.setLocationRelativeTo(null);
				}
			});
			btnDesactivarEmpleado.setBounds(32, 134, 233, 23);
		}
		return btnDesactivarEmpleado;
	}
	private JButton getBtnVerActividadEmpleados() {
		if (btnVerActividadEmpleados == null) {
			btnVerActividadEmpleados = new JButton("Ver actividad de los empleados");
			btnVerActividadEmpleados.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaActividadEmpleados vae = new VentanaActividadEmpleados();
					vae.setVisible(true);
					vae.setLocationRelativeTo(null);
				}
			});
			btnVerActividadEmpleados.setBounds(31, 181, 234, 23);
		}
		return btnVerActividadEmpleados;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Pacientes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel.setBounds(40, 254, 334, 186);
			panel.setLayout(null);
			panel.add(getBtnDesactivarPaciente());
			panel.add(getBtnVerActividadDePacientes());
			panel.add(getBtnAadirPacienteAl());
			panel.add(getBtnModificarPaciente());
		}
		return panel;
	}
	private JButton getBtnDesactivarPaciente() {
		if (btnDesactivarPaciente == null) {
			btnDesactivarPaciente = new JButton("Desactivar paciente");
			btnDesactivarPaciente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					VentanaDesactivarPaciente vdp = new VentanaDesactivarPaciente();
					vdp.setVisible(true);
					vdp.setLocationRelativeTo(null);
				}
			});
			btnDesactivarPaciente.setBounds(64, 39, 228, 23);
		}
		return btnDesactivarPaciente;
	}
	private JButton getBtnVerActividadDePacientes() {
		if (btnVerActividadDePacientes == null) {
			btnVerActividadDePacientes = new JButton("Ver actividad de los pacientes");
			btnVerActividadDePacientes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					VentanaActividadPacientes vap = new VentanaActividadPacientes();
					vap.setVisible(true);
					vap.setLocationRelativeTo(null);
				}
			});
			btnVerActividadDePacientes.setBounds(64, 75, 228, 23);
		}
		return btnVerActividadDePacientes;
	}

	private JButton getBtnGestionarCitas() {
		if (btnGestionarCitas == null) {
			btnGestionarCitas = new JButton("Gestionar Citas");
			btnGestionarCitas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaGestionarCitas vv = new VentanaGestionarCitas();
					vv.setVisible(true);
					vv.setLocationRelativeTo(null);
				}
			});
			btnGestionarCitas.setBounds(30, 126, 143, 25);
		}
		return btnGestionarCitas;
}
	private JButton getBtnAadirEmpleaado() {
		if (btnAadirEmpleaado == null) {
			btnAadirEmpleaado = new JButton("A\u00F1adir empleaado");
			btnAadirEmpleaado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					abrirVentanaAddEmpleado();
				}
			});
			btnAadirEmpleaado.setBounds(31, 248, 234, 25);
		}
		return btnAadirEmpleaado;
	}
	
	private void abrirVentanaAddEmpleado() {
		VentanaAddEmpleado vae = new VentanaAddEmpleado();
		vae.setLocationRelativeTo(this);
		vae.setVisible(true);
	}
	private JButton getBtnModificarEmpleado() {
		if (btnModificarEmpleado == null) {
			btnModificarEmpleado = new JButton("Modificar Empleado");
			btnModificarEmpleado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					abrirVentanaModificarEmpleado();
				}
			});
			btnModificarEmpleado.setBounds(31, 286, 234, 25);
		}
		return btnModificarEmpleado;
	}
	
	private void abrirVentanaModificarEmpleado() {
		VentanaModificarEmpleado vme = new VentanaModificarEmpleado();
		vme.setLocationRelativeTo(this);
		vme.setVisible(true);
	}
	private JButton getBtnAadirPacienteAl() {
		if (btnAadirPacienteAl == null) {
			btnAadirPacienteAl = new JButton("A\u00F1adir paciente al sistema");
			btnAadirPacienteAl.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					abrirVentanaAddPaciente();
				}
			});
			btnAadirPacienteAl.setBounds(64, 111, 228, 23);
		}
		return btnAadirPacienteAl;
	}
	
	private void abrirVentanaAddPaciente() {
		VentanaAddPaciente vap = new VentanaAddPaciente();
		vap.setLocationRelativeTo(null);
		vap.setVisible(true);
	}
	
	private JButton getBtnModificarPaciente() {
		if (btnModificarPaciente == null) {
			btnModificarPaciente = new JButton("Modificar paciente");
			btnModificarPaciente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					abrirVentanaModificarPaciente();
				}
			});
			btnModificarPaciente.setBounds(64, 148, 228, 25);
		}
		return btnModificarPaciente;
	}
	
	private void abrirVentanaModificarPaciente() {
		VentanaModificarPaciente vmp = new VentanaModificarPaciente();
		vmp.setLocationRelativeTo(this);
		vmp.setVisible(true);
	}
}

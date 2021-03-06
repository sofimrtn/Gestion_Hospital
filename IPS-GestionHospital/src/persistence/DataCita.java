package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import alb.util.jdbc.Jdbc;
import business.dto.CitaDto;

public class DataCita extends DataManager {

	private static final String SQL_SELECT_CITA = "Select idcita, urgente, idpaciente, idempleado, sala, fechainicio, fechafin from cita";
	private static final String SQL_SELECT_CITA_BY_IDEMPLEADO = "Select idcita, urgente, idpaciente, idempleado, sala, fechainicio, fechafin from cita where idempleado = ?";
	private static final String SQL_INSERT_CITA = "Insert into cita (urgente, idpaciente, idempleado, sala, fechainicio, fechafin)" + "values (?, ?, ?, ?, ?, ?)";
	private static final String SQL_DELETE_CITA = "Delete from cita where idcita=?";
	private static final String SQL_UPDATE_CITA = "Update cita set urgente=?, fechainicio=?,"
			+ " fechafin=?, idpaciente=?, idempleado=?, sala=?,procedimientos=?, antecedentes=?, prescripciones=? where idcita=?";
	private static final String SQL_UPDATE_PROC = "Update cita set procedimientos=? where idcita=?";
	private static final String SQL_UPDATE_SINTOMAS = "Update cita set sintomas=? where idcita=?";
	//(VIC) TIRO DEL UPDATE_CITA-->
	//	private static final String SQL_UPDATE_PRESCRIPCIONES= "Update cita set prescripciones=? where idcita=?";
//	private static final String SQL_UPDATE_ANTECEDENTES= "Update cita set antecedentes=? where idcita=?";
	private static final String SQL_SELECT_CITA_BY_ID = "Select * from cita where idcita=?";
	private static final String SQL_SELECT_CITA_BY_IDPACIENTE = "Select * from cita where idpaciente=?";

	public List<CitaDto> list() {
		List<CitaDto> citas = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = getConexion().prepareStatement(SQL_SELECT_CITA);
			rs = st.executeQuery();
			citas = new ArrayList<>();
			while (rs.next()) {
				CitaDto cita = new CitaDto();
				cita.id = rs.getInt(1);
				cita.urgente = rs.getBoolean(2);				
				cita.idPaciente = rs.getInt(3);
				cita.idEmpleado = rs.getInt(4);
				cita.sala = rs.getString(5);
				cita.fechainicio = rs.getTimestamp(6);
				cita.fechafin = rs.getTimestamp(7);
				citas.add(cita);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Jdbc.close(rs, st);
		}
		return citas;
	}
	
	public List<CitaDto> listCitasByidEmpleado(int id) {
		List<CitaDto> citas = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = getConexion().prepareStatement(SQL_SELECT_CITA_BY_IDEMPLEADO);
			st.setInt(1, id);
			rs = st.executeQuery();
			citas = new ArrayList<>();
			while (rs.next()) {
				CitaDto cita = new CitaDto();
				cita.id = rs.getInt(1);
				cita.urgente = rs.getBoolean(2);				
				cita.idPaciente = rs.getInt(3);
				cita.idEmpleado = rs.getInt(4);
				cita.sala = rs.getString(5);
				cita.fechainicio = rs.getTimestamp(6);
				cita.fechafin = rs.getTimestamp(7);
				citas.add(cita);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Jdbc.close(rs, st);
		}
		return citas;
	}

	public void add(CitaDto cita) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = getConexion().prepareStatement(SQL_INSERT_CITA);
			st.setBoolean(1, cita.urgente);
			st.setInt(2, cita.idPaciente);
			st.setInt(3, cita.idEmpleado);
			st.setString(4, cita.sala);
			st.setTimestamp(5, new Timestamp(cita.fechainicio.getTime()));
			st.setTimestamp(6, new Timestamp(cita.fechafin.getTime()));
			
			

			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Jdbc.close(rs, st);
		}
	}

	public void delete(CitaDto cita) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = getConexion().prepareStatement(SQL_DELETE_CITA);
			st.setInt(1, cita.id);

			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Jdbc.close(rs, st);
		}
	}

	public void update(CitaDto cita) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = getConexion().prepareStatement(SQL_UPDATE_CITA);
			st.setBoolean(1, cita.urgente);
			st.setTimestamp(2, new Timestamp(cita.fechainicio.getTime()));
			st.setTimestamp(3, new Timestamp(cita.fechafin.getTime()));
			st.setInt(4, cita.idPaciente);
			st.setInt(5, cita.idEmpleado);
			st.setString(6, cita.sala);
			st.setString(7, cita.procedimientos);
			st.setString(8, cita.antecedentes);
			st.setString(9,cita.prescripcion);
			st.setInt(10, cita.id);

			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Jdbc.close(rs, st);
		}
	}
	
	public void updateProc(CitaDto cita) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = getConexion().prepareStatement(SQL_UPDATE_PROC);
			st.setString(1, cita.procedimientos);
			st.setInt(2,cita.id);
			
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Jdbc.close(rs, st);
		}
	}

	public void updateSintomas(CitaDto cita) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = getConexion().prepareStatement(SQL_UPDATE_SINTOMAS);
			st.setString(1, cita.sintomas);
			st.setInt(2,cita.id);

			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Jdbc.close(rs, st);
		}
		
	}
	
	public List<CitaDto> listCitaById(int id) {
		List<CitaDto> citas = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = getConexion().prepareStatement(SQL_SELECT_CITA_BY_ID);
			st.setInt(1, id);
			rs = st.executeQuery();
			citas = new ArrayList<>();
			while (rs.next()) {
				CitaDto cita = new CitaDto();
				cita.id = rs.getInt(1);
				cita.urgente = rs.getBoolean(2);				
				cita.idPaciente = rs.getInt(3);
				cita.idEmpleado = rs.getInt(4);
				cita.sala = rs.getString(5);
				cita.fechainicio = rs.getTimestamp(6);
				cita.fechafin = rs.getTimestamp(7);
				cita.antecedentes =rs.getString(8);
				cita.sintomas= rs.getString(9);
				cita.prescripcion= rs.getString(10);
				cita.procedimientos=rs.getString(11);
				citas.add(cita);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Jdbc.close(rs, st);
		}
		return citas;
	}

	public List<CitaDto> listCitasByIdPaciente(int id) {
		List<CitaDto> citas = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = getConexion().prepareStatement(SQL_SELECT_CITA_BY_IDPACIENTE);
			st.setInt(1, id);
			rs = st.executeQuery();
			citas = new ArrayList<>();
			while (rs.next()) {
				CitaDto cita = new CitaDto();
				cita.id = rs.getInt(1);
				cita.urgente = rs.getBoolean(2);				
				cita.idPaciente = rs.getInt(3);
				cita.idEmpleado = rs.getInt(4);
				cita.sala = rs.getString(5);
				cita.fechainicio = rs.getTimestamp(6);
				cita.fechafin = rs.getTimestamp(7);
				cita.antecedentes =rs.getString(8);
				cita.sintomas= rs.getString(9);
				cita.prescripcion= rs.getString(10);
				cita.procedimientos=rs.getString(11);
				citas.add(cita);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Jdbc.close(rs, st);
		}
		return citas;
	}
}

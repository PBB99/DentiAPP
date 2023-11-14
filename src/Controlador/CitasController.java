package Controlador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import Modelo.Cita;
import Modelo.Specialist;

public class CitasController {
	private ConexionMySQL conexion;

	public CitasController(ConexionMySQL conexion) {
		super();
		this.conexion = conexion;
	}
	// metodo para obtener todos los datos de la tabla especialista

	public ArrayList<Cita> getAllSpecialist() throws SQLException {
		ArrayList<Cita> allCitas = new ArrayList<>();
		String consulta = "Select * From byprip7xk9sybmhhq0jf.cita";
		ResultSet rset = conexion.ejecutarSelect(consulta);
		while (rset.next()) {
			int id = rset.getInt("idcita");
			String dniD = rset.getString("dni_doc");
			String dniC = rset.getString("dni_paciente");
			int codigo = rset.getInt("codigo_trata");
			Date fecha = rset.getDate("fecha");
			String hora = rset.getString("hora");

			Cita cita = new Cita(id, dniD, dniC, codigo, fecha, hora);
			allCitas.add(cita);
		}
		return allCitas;
	}

	public void insert(Cita cita) {
		try {
			conexion.ejecutarInsertUpdateDelete(
					"insert into cita values ('" + cita.getId() + "', '" + cita.getDniD() + "', '" + cita.getDniC()
							+ "', '" + cita.getCodigo() + "', '" + cita.getFecha() + "', '" + cita.getHora() + "')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

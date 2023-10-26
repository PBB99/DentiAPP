package Controlador;

import java.sql.*;
import java.util.ArrayList;

import Modelo.Specialist;


public class SpecialistController {
	private ConexionMySQL conexion;

	public SpecialistController(ConexionMySQL conexion) {
		super();
		this.conexion = conexion;
	}
	//metodo para obtener todos los datos de la tabla especialista
	
	public ArrayList<Specialist> getAllSpecialist() throws SQLException {
		ArrayList<Specialist> allSpecialist = new ArrayList<>();
		String consulta = "Select * From byprip7xk9sybmhhq0jf.especialista";
		ResultSet rset = conexion.ejecutarSelect(consulta);
		while (rset.next()) {
			String dni = rset.getString("dni_usuario");
			int id_speciality=rset.getInt("especialidad");


			Specialist specialist= new Specialist(dni,id_speciality);
			allSpecialist.add(specialist);
		}
		return allSpecialist;
	}
}

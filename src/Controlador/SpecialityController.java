package Controlador;

import java.sql.*;
import java.util.ArrayList;

import Modelo.Specialist;
import Modelo.Speciality;


public class SpecialityController {
	private ConexionMySQL conexion;

	public SpecialityController(ConexionMySQL conexion) {
		super();
		this.conexion = conexion;
	}
	//metodo para obtener todos los datos de la tabla especialista
	
	public ArrayList<Speciality> getAllSpecialist() throws SQLException {
		System.out.println("jaja");
		ArrayList<Speciality> allSpecialist = new ArrayList<>();
		System.out.println("jaja");
		String consulta = "Select * From byprip7xk9sybmhhq0jf.especialidades";
		ResultSet rset = conexion.ejecutarSelect(consulta);
		System.out.println("jaja");
		while (rset.next()) {
			int id_speciality = rset.getInt("id_especialidad");
			String speciality= rset.getString("especialidad");


			Speciality spec= new Speciality(id_speciality,speciality);
			allSpecialist.add(spec);
			
		}
		System.out.println("jaja");
		return allSpecialist;
	}
	
	public void insert(Speciality spe) {
		try {
			conexion.ejecutarInsertUpdateDelete("insert into especialidades values ('"+ spe.getId_speciality() + "', '" + spe.getSpeciality() + "')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

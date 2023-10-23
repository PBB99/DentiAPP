package Modelo;

import java.util.ArrayList;



import java.sql.*;

//modelo de la tabla Especialista
public class Specialist {
	
	private String dni;
	private int id_speciality;
	private int id_specialist;

	public Specialist(int id_speciality, String dni, int id_specialist) {
		super();
		this.id_speciality = id_speciality;
		this.dni = dni;
		this.id_specialist = id_specialist;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public int getId_speciality() {
		return id_speciality;
	}

	public void setId_speciality(int id_speciality) {
		this.id_speciality = id_speciality;
	}

	public int getId_specialist() {
		return id_specialist;
	}

	public void setId_specialist(int id_specialist) {
		this.id_specialist = id_specialist;
	}

	
}

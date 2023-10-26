package Modelo;

import java.util.ArrayList;



import java.sql.*;

//modelo de la tabla Especialista
public class Specialist {
	
	private String dni;
	private int id_specialist;

	public Specialist(String dni, int id_specialist) {
		super();
		this.dni = dni;
		this.id_specialist = id_specialist;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}


	public int getId_specialist() {
		return id_specialist;
	}

	public void setId_specialist(int id_specialist) {
		this.id_specialist = id_specialist;
	}

	
}

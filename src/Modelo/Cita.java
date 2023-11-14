package Modelo;

import java.util.Date;

public class Cita {
	private int id; 
	private String dniD;
	private String dniC; 
	private int codigo; 
	private Date fecha; 
	private String hora;

	public Cita(int id, String dniD, String dniC, int codigo, Date fecha, String hora) {
		super();
		this.id = id;
		this.dniD = dniD;
		this.dniC = dniC;
		this.codigo = codigo;
		this.fecha = fecha;
		this.hora = hora;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDniD() {
		return dniD;
	}

	public void setDniD(String dniD) {
		this.dniD = dniD;
	}

	public String getDniC() {
		return dniC;
	}

	public void setDniC(String dniC) {
		this.dniC = dniC;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	
}

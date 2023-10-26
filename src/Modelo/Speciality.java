package Modelo;

public class Speciality {
	private String speciality;
	private int id_speciality;

	public Speciality(int id_speciality, String speciality) {
		super();
		this.id_speciality = id_speciality;
		this.speciality = speciality;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public int getId_speciality() {
		return id_speciality;
	}

	public void setId_speciality(int id_speciality) {
		this.id_speciality = id_speciality;
	}

	
}

package Modelo;

public class User {
private String dni;
private String name;
private String lastName;
private String pass;
private boolean state;
public User(String dni, String name, String lastName, String pass, boolean state) {
	super();
	this.dni = dni;
	this.name = name;
	this.lastName = lastName;
	this.pass = pass;
	this.state = state;
}
public String getDni() {
	return dni;
}
public void setDni(String dni) {
	this.dni = dni;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getLastName() {
	return lastName;
}
public void setLastName(String lastName) {
	this.lastName = lastName;
}
public String getPass() {
	return pass;
}
public void setPass(String pass) {
	this.pass = pass;
}
public boolean isState() {
	return state;
}
public void setState(boolean state) {
	this.state = state;
}


}

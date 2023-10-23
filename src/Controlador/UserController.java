package Controlador;
import Modelo.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Modelo.Specialist;

public class UserController {
	private ConexionMySQL conexion;

	public UserController(ConexionMySQL conexion) {
		super();
		this.conexion = conexion;
	}
	//metodo para obtener todos los datos de la tabla especialista
	
	public ArrayList<User> getAllUsers() throws SQLException {
		ArrayList<User> allUsers = new ArrayList<>();
		String consulta = "Select * From byprip7xk9sybmhhq0jf.usuario";
		ResultSet rset = conexion.ejecutarSelect(consulta);
		while (rset.next()) {
			String dni = rset.getString("dni");
			String name = rset.getString("nombre");
			String lastName = rset.getString("apellido");
			String pass = rset.getString("contraseña");
			boolean state=rset.getBoolean("estado");
			User user=new User(dni,name,lastName,pass,state);
			
			allUsers.add(user);

			
		}
		return allUsers;
	}
}

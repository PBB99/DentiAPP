package Controlador;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionMySQL {
	private static String CONTROLADOR = "com.mysql.jdbc.Driver";
	private static String URL = "jdbc:mysql://byprip7xk9sybmhhq0jf?useSSL=false";
	private static final String USUARIO = "root";
	private static final String CLAVE = "1234";
	private boolean conectado = false;
	private Connection conexion;

	public Connection conectar() {
		if (conexion == null) {
			try {
				conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
				System.out.println("conectado");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conexion;
	}

	public void desconectar(Connection conexion) throws SQLException {
		if (conexion != null || conexion.isClosed()) {
			conexion.close();
			conectado = false;
			System.out.println("DESCONEXION REALIZADA");
		}
	}

	public ResultSet ejecutarSelect(String consulta) throws SQLException {
		Statement STMT = conexion.createStatement();
		return STMT.executeQuery(consulta);
	}

	public int ejecutarInsertUpdateDelete(String consulta)throws SQLException{
		Statement STMT=conexion.createStatement();
		return STMT.executeUpdate(consulta);
	}
}

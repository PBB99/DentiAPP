package Modelo;

import java.sql.Date;
import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "compras")
public class CompraHibernate implements Serializable{
	
	@Id
	@Column(name = "id_pedido", nullable = false)
	private Integer id_pedido;
	
	@Column(name = "cantidad", nullable = false)
	private Integer cantidad;
	
	@Column(name = "dni_usuario", nullable = false)
	private String dni_usuario;

}
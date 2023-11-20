package Modelo;

import java.sql.Date;
import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "odontograma")
public class OdontogramaHibernate implements Serializable{
	
	@Id
	@Column(name = "id_odontograma", nullable = false)
	private Integer id_odontograma;
	
	@Column(name = "codigo_cliente", nullable = false)
	private Integer codigo_cliente;
	
	@Column(name = "tratamiento", nullable = false)
	private String tratamiento;

	public OdontogramaHibernate(Integer id_odontograma, Integer codigo_cliente, String tratamiento) {
		super();
		this.id_odontograma = id_odontograma;
		this.codigo_cliente = codigo_cliente;
		this.tratamiento = tratamiento;
	}

}
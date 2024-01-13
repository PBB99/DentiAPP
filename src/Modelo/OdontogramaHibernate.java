package Modelo;

import java.sql.Date;
import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "odontogramas")
public class OdontogramaHibernate implements Serializable{
	
	@Id
	@Column(name = "id_odontogramas", nullable = false)
	private Integer id_odontogramas;
	
	@Column(name = "id_diente", nullable = false)
	private Integer id_diente;
	
	@Column(name = "observaciones", nullable = false)
	private String observaciones;
	
	@Column(name = "fecha", nullable = false)
	private Date fecha;

	public OdontogramaHibernate(Integer id_odontograma, Integer id_diente, String observaciones, Date fecha) {
		super();
		this.id_odontogramas = id_odontograma;
		this.id_diente = id_diente;
		this.observaciones = observaciones;
		this.fecha = fecha;
	}

	@ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name = "clientes_dni_cliente")
    private ClienteHibernate cliente;

    public ClienteHibernate getCliente() {
        return cliente;
    }
    
    public void setCliente(ClienteHibernate cliente) {
        this.cliente = cliente;
    }
}
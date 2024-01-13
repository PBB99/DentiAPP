package Modelo;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name = "citas")
public class CitaHibernate implements Serializable{
	
	@Id
	@Column(name = "id_cita", nullable = false)
	private Integer idcita;
	
	@Column(name = "fecha", nullable = false)
	private Date fecha;
	
	@Column(name = "hora", nullable = false)
	private String hora;
	
	public CitaHibernate() {}
	
	public CitaHibernate(Integer idcita, Date fecha, String hora) {
		super();
		this.idcita = idcita;
//		this.especialidad = especialidad;
		this.fecha = fecha;
		this.hora = hora;
	}

	public Integer getIdcita() {
		return idcita;
	}

	public void setIdcita(Integer idcita) {
		this.idcita = idcita;
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



	@ManyToOne
    @JoinColumn(name = "usuarios_dni_usuario")
    private UserHibernate usuario_cita; //Este atributo va a @OneToMany en Cliente

    public UserHibernate getUser() {
        return usuario_cita;
    }

    public void setUser(UserHibernate usuario_cita) {
        this.usuario_cita = usuario_cita;
    }
    
    @ManyToOne
    @JoinColumn(name = "clientes_dni_cliente")
    private ClienteHibernate cliente_cita; //Este atributo va a @OneToMany en Cliente

    public ClienteHibernate getCliente() {
        return cliente_cita;
    }

    public void setCliente(ClienteHibernate cliente_cita) {
        this.cliente_cita = cliente_cita;
    }
    
    @ManyToOne
    @JoinColumn(name = "tratamientos_id_tratamiento")
    private TreatmentsHibernate tratamiento_cita; //Este atributo va a @OneToMany en Cliente

    public TreatmentsHibernate getTratamiento() {
        return tratamiento_cita;
    }

    public void setTratamiento(TreatmentsHibernate tratamiento_cita) {
        this.tratamiento_cita = tratamiento_cita;
    }
}

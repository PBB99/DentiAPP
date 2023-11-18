package Modelo;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name = "cita")
public class CitaHibernate implements Serializable{
	
	@Id
	@Column(name = "idcita", nullable = false)
	private Integer idcita;
	
//	@Column(name = "especialidad", nullable = false)
//	private Integer especialidad;
	
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



	@ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name = "dni_doc")
    private UserHibernate userHiber; //Este atributo va a @OneToMany en Cliente

    public UserHibernate getUser() {
        return userHiber;
    }

    public void setUser(UserHibernate userHiber) {
        this.userHiber = userHiber;
    }
    
    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name = "dni_paciente")
    private ClienteHibernate clienteHiber; //Este atributo va a @OneToMany en Cliente

    public ClienteHibernate getCliente() {
        return clienteHiber;
    }

    public void setCliente(ClienteHibernate clienteHiber) {
        this.clienteHiber = clienteHiber;
    }
    
    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name = "codigo_trata")
    private TreatmentsHibernate tratamientoHiber; //Este atributo va a @OneToMany en Cliente

    public TreatmentsHibernate getTratamiento() {
        return tratamientoHiber;
    }

    public void setTratamiento(TreatmentsHibernate tratamientoHiber) {
        this.tratamientoHiber = tratamientoHiber;
    }
}

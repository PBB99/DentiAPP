package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "especialidades")

public class SpecialityHibernate  implements Serializable{
	
	@Id
	@Column(name = "id_especialidad", nullable = false)
	private Integer id_especialidad;
	
	@Column(name = "especialidad", nullable = false)
	private String especialidad;
	
	public SpecialityHibernate() {
		super();
	}
	
	public SpecialityHibernate(Integer id_especialidad, String especialidad) {
		super();
		this.id_especialidad = id_especialidad;
		this.especialidad = especialidad;
	}

	public Integer getId_especialidad() {
		return id_especialidad;
	}

	public void setId_especialidad(Integer id_especialidad) {
		this.id_especialidad = id_especialidad;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}
	
	@OneToMany(mappedBy = "especialidad_tratamiento", cascade = CascadeType.ALL)
    
    private List<TreatmentsHibernate> tratamientos;
    
    public List<TreatmentsHibernate> getPedidos(){
        return tratamientos;
    }
    
    public void addPedido(TreatmentsHibernate th){
        if (tratamientos == null) tratamientos=new ArrayList<>();
        tratamientos.add(th);
        th.setEspecialidad(this);
    }

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "especialista",
        joinColumns = {@JoinColumn(name = "especialidad")},
        inverseJoinColumns = {@JoinColumn(name = "dni_usuario")}
    )
    private List<UserHibernate> users = new ArrayList<UserHibernate>();
    
    public List<UserHibernate> getUser() {
    	return users;
    }
    public void addUser(UserHibernate p)
    {
        this.users.add(p);
        p.getSpeciality().add(this);
    }
    public void setClientes(List<UserHibernate> users) {
        this.users = users;
    }
	
}

package es.estebanco.estebanco.dto;

import es.estebanco.estebanco.entity.ConversacionEntity;
import es.estebanco.estebanco.entity.OperacionEntity;
import es.estebanco.estebanco.entity.RolEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/*
   FERNANDO -> 100%.
 */

/**
 * A DTO for the {@link es.estebanco.estebanco.entity.PersonaEntity} entity
 */
public class PersonaEntityDto implements Serializable {
    private Integer id;
    private  String dni;
    private  String nombre;
    private  String apellido1;
    private  String apellido2;
    private  String correo;
    private  String direccion;
    private  String telefono;
    private  String usuario;
    private  String contraseña;
    private  String estado;
    //private List<ConversacionEntityDto> conversacionsById;
    //private List<OperacionEntityDto> operacionesById;
    //private List<ConversacionEntityDto> conversacionsById_0;
    //private List<RolEntityDto> rolsById;

    public int getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public String getEstado() {
        return estado;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    /*public List<ConversacionEntityDto> getConversacionsById() {
        return conversacionsById;
    }

    public void setConversacionsById(List<ConversacionEntityDto> conversacionsById) {
        this.conversacionsById = conversacionsById;
    }

    public List<OperacionEntityDto> getOperacionesById() {
        return operacionesById;
    }

    public void setOperacionesById(List<OperacionEntityDto> operacionesById) {
        this.operacionesById = operacionesById;
    }

    public List<ConversacionEntityDto> getConversacionsById_0() {
        return conversacionsById_0;
    }

    public void setConversacionsById_0(List<ConversacionEntityDto> conversacionsById_0) {
        this.conversacionsById_0 = conversacionsById_0;
    }

    public List<RolEntityDto> getRolsById() {
        return rolsById;
    }

    public void setRolsById(List<RolEntityDto> rolsById) {
        this.rolsById = rolsById;
    }

     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonaEntityDto entity = (PersonaEntityDto) o;
        return Objects.equals(this.id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
/*
    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ")";
    }*/
}
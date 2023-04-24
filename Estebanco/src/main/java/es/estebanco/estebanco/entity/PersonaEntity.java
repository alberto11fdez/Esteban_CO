package es.estebanco.estebanco.entity;

import es.estebanco.estebanco.dto.PersonaEntityDto;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "persona", schema = "estebanco", catalog = "")
public class PersonaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "dni", nullable = true, length = 45)
    private String dni;
    @Basic
    @Column(name = "nombre", nullable = true, length = 45)
    private String nombre;
    @Basic
    @Column(name = "apellido1", nullable = true, length = 45)
    private String apellido1;
    @Basic
    @Column(name = "apellido2", nullable = true, length = 45)
    private String apellido2;
    @Basic
    @Column(name = "correo", nullable = true, length = 45)
    private String correo;
    @Basic
    @Column(name = "direccion", nullable = true, length = 60)
    private String direccion;
    @Basic
    @Column(name = "telefono", nullable = true, length = 11)
    private String telefono;
    @Basic
    @Column(name = "usuario", nullable = true, length = 45)
    private String usuario;
    @Basic
    @Column(name = "contraseña", nullable = true, length = 45)
    private String contraseña;
    @Basic
    @Column(name = "estado", nullable = true, length = 45)
    private String estado;
    @OneToMany(mappedBy = "personaByPersonaId")
    private List<ConversacionEntity> conversacionsById;
    @OneToMany(mappedBy = "personaByPersonaId")
    private List<OperacionEntity> operacionesById;
    @OneToMany(mappedBy = "personaByAsistenteId")
    private List<ConversacionEntity> conversacionsById_0;
    @OneToMany(mappedBy = "personaByPersonaId")
    private List<RolEntity> rolsById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonaEntity that = (PersonaEntity) o;
        return id == that.id && Objects.equals(dni, that.dni) && Objects.equals(nombre, that.nombre) && Objects.equals(apellido1, that.apellido1) && Objects.equals(apellido2, that.apellido2) && Objects.equals(correo, that.correo) && Objects.equals(direccion, that.direccion) && Objects.equals(telefono, that.telefono) && Objects.equals(usuario, that.usuario) && Objects.equals(contraseña, that.contraseña) && Objects.equals(estado, that.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dni, nombre, apellido1, apellido2, correo, direccion, telefono, usuario, contraseña, estado);
    }

    public List<ConversacionEntity> getConversacionsById() {
        return conversacionsById;
    }

    public void setConversacionsById(List<ConversacionEntity> conversacionsById) {
        this.conversacionsById = conversacionsById;
    }

    public List<ConversacionEntity> getConversacionsById_0() {
        return conversacionsById_0;
    }

    public void setConversacionsById_0(List<ConversacionEntity> conversacionsById_0) {
        this.conversacionsById_0 = conversacionsById_0;
    }

    public List<RolEntity> getRolsById() {
        return rolsById;
    }

    public void setRolsById(List<RolEntity> rolsById) {
        this.rolsById = rolsById;
    }

    public List<OperacionEntity> getOperacionesByIdById() {
        return operacionesById;
    }

    public void setOperacionesById(List<OperacionEntity> operacionesById) {
        this.operacionesById = operacionesById;
    }

    public PersonaEntityDto toDTO(){
        PersonaEntityDto dto = new PersonaEntityDto();
        dto.setId(this.id);
        dto.setDni(this.dni);
        dto.setNombre(this.nombre);
        dto.setApellido1(this.apellido1);
        dto.setApellido2(this.apellido2);
        dto.setCorreo(this.correo);
        dto.setDireccion(this.direccion);
        dto.setTelefono(this.telefono);
        dto.setUsuario(this.usuario);
        dto.setContraseña(this.contraseña);
        dto.setEstado(this.estado);
        return dto;
    }
}


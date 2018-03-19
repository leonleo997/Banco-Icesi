package co.edu.ices.demo.vistas;


import javax.faces.bean.ManagedBean;

import javax.validation.constraints.Size;


@ManagedBean
public class AsesoresComercialesValidationView {

	@Size(min=2,max=10)
    private String identificacion;
	
	@Size(min=2,max=50)
    private String nombres;
	
	@Size(min=2,max=50)
    private String apellidos;
	
	@Size(min=2,max=50)
    private String direccion;
	
	@Size(min=2,max=5)
    private String telefono;
	
	@Size(min=2,max=50)
    private String email;

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	 
	
	
	
	
	
	
	 	}
	
	


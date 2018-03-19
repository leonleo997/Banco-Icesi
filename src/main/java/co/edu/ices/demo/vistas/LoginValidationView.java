package co.edu.ices.demo.vistas;


import javax.faces.bean.ManagedBean;

import javax.validation.constraints.Size;


@ManagedBean
public class LoginValidationView {

	@Size(min=2,max=5)
    private String name;
	
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	@Size(min=5, max=34)
    private String clave;
	
	 @Size(min=1,max=10)
	 private String identificacion;
	     
	@Size(min=1,max=30)
	 private String login;
	  
	  
	  
	  
	  



	public String getClave() {
		return clave;
	}



	public void setClave(String clave) {
		this.clave = clave;
	}



	public String getIdentificacion() {
		return identificacion;
	}



	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}



	public String getLogin() {
		return login;
	}



	public void setLogin(String login) {
		this.login = login;
	}
	     
	    
	   	 
	 
	
	 
	 	}
	
	


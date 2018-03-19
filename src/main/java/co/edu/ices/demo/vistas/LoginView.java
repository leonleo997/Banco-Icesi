package co.edu.ices.demo.vistas;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Size;

import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.password.Password;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.DemoBanco.modelo.Usuarios;
import co.edu.icesi.demo.business.IBusinessDelegate;

@ManagedBean
@ViewScoped
public class LoginView {

	private final static Logger log = LoggerFactory.getLogger(LoginView.class);

	public final static long TIPO_USUARIO_CAJERO = 10;
	public final static long TIPO_USUARIO_ASESOR_COMERCIAL = 20;
	public final static long TIPO_USUARIO_ADMIN = 30;

	@Size(min = 1, max = 10)
	private InputText login;

	@Size(min = 1, max = 30)
	private Password clave;

	@EJB
	private IBusinessDelegate businessDelegate;

	public InputText getLogin() {
		return login;
	}

	public void setLogin(InputText login) {
		this.login = login;
	}

	public Password getClave() {
		return clave;
	}

	public void setClave(Password clave) {
		this.clave = clave;
	}

	public void ingresar() {
		log.info("Inicia autenticación de usuario");
		try {
			if(login.getValue().toString()==null)
				throw new Exception("La identificación es obligatorio");
			if(clave.getValue().toString()==null)
				throw new Exception("La clave es obligatoria");
			
			long id=Long.parseLong(login.getValue().toString());
			Usuarios user = businessDelegate.getUsuarios(id);
			log.info(user.getUsuClave());
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			if (clave.getValue().toString().equals(user.getUsuClave())) {
				String cedula = "";
				long codigoTipoUser = user.getTiposUsuarios().getTusuCodigo();
				if (codigoTipoUser == TIPO_USUARIO_ASESOR_COMERCIAL) {
					context.getExternalContext()
							.redirect(request.getContextPath() + "/AsesoresViews/AgregarCliente.xhtml");
					cedula = login.getValue().toString();

				} else {
					if (codigoTipoUser == TIPO_USUARIO_CAJERO) {
						context.getExternalContext()
								.redirect(request.getContextPath() + "/CajerosViews/Consignaciones.xhtml");
						cedula = login.getValue().toString();
						log.info("CEDULA GUARDADA" + cedula);

					} else {
						if (codigoTipoUser == TIPO_USUARIO_ADMIN) {
							context.getExternalContext()
									.redirect(request.getContextPath() + "/AdminViews/SuperUsuario.xhtml");
							cedula = login.getValue().toString();
						} else {
							context.getExternalContext().redirect(request.getContextPath() + "/Login.xhtml");
						}
					}
				}
				log.info("LOGIN-------" + cedula);
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cedula", cedula);
			} else {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Contraseña errónea",
						"La contraseña no corresponde al usuario " + login.getValue().toString()));
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}

	}

}

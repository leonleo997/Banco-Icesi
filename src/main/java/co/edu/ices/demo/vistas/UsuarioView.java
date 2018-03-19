package co.edu.ices.demo.vistas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.faces.bean.ViewScoped;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.password.Password;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.DemoBanco.modelo.TiposUsuarios;
import co.edu.icesi.DemoBanco.modelo.Usuarios;
import co.edu.icesi.demo.business.IBusinessDelegate;

@ManagedBean
@ViewScoped
public class UsuarioView {

	private final static Logger log = LoggerFactory.getLogger(UsuarioView.class);

	@EJB
	private IBusinessDelegate businessDelegate;

	// implementar atributos y método de relleno

	private List<Usuarios> listaUsuarios;

	private String tipoUsuarioSeleccionado;
	private List<SelectItem> listaTipoUsuarios;

	private InputText txtCedula;
	private InputText txtCedulaDelete;
	private InputText txtNombre;
	private InputText txtUsuario;
	private Password txtClave;
	private CommandButton butGuardar;
	private CommandButton butModificar;
	private CommandButton butEliminar;
	private CommandButton butConsultar;

	@PostConstruct
	public void init() {
		try {
			List<TiposUsuarios> tipos = businessDelegate.getTiposUsuarios();
			listaTipoUsuarios = new ArrayList<>();
			for (TiposUsuarios tiposUsuarios : tipos) {
				listaTipoUsuarios.add(new SelectItem(tiposUsuarios.getTusuCodigo(), tiposUsuarios.getTusuNombre()));
			}
			actualizarTabla();
		} catch (NullPointerException e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "No existe nungún tipo de usuario", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
	}

	public void actualizarTabla() throws Exception {
		listaUsuarios = businessDelegate.getUsuarios();
	}

	public void actualizarTabla(List<Usuarios> parametros) throws Exception {
		listaUsuarios = parametros;
	}

	public IBusinessDelegate getBusinessDelegate() {
		return businessDelegate;
	}

	public void setBusinessDelegate(IBusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}

	public List<Usuarios> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuarios> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public InputText getTxtCedulaDelete() {
		return txtCedulaDelete;
	}

	public void setTxtCedulaDelete(InputText txtCedulaDelete) {
		this.txtCedulaDelete = txtCedulaDelete;
	}

	public String getTipoUsuarioSeleccionado() {
		return tipoUsuarioSeleccionado;
	}

	public void setTipoUsuarioSeleccionado(String tipoUsuarioSeleccionado) {
		this.tipoUsuarioSeleccionado = tipoUsuarioSeleccionado;
	}

	public List<SelectItem> getListaTipoUsuarios() {
		return listaTipoUsuarios;
	}

	public void setListaTipoUsuarios(List<SelectItem> listaTipoUsuarios) {
		this.listaTipoUsuarios = listaTipoUsuarios;
	}

	public String actionGuardar() {
		try {
			log.info("se inició guardar");
			Usuarios user = new Usuarios();
			TiposUsuarios tipoUsuario = new TiposUsuarios();
			Long idTipoUser = new Long(tipoUsuarioSeleccionado);
			tipoUsuario.setTusuCodigo(idTipoUser);

			user.setTiposUsuarios(tipoUsuario);

			Long cedula = new Long(txtCedula.getValue().toString());
			user.setUsuCedula(cedula);
			user.setUsuClave(txtClave.getValue().toString());
			user.setUsuLogin(txtUsuario.getValue().toString());
			user.setUsuNombre(txtNombre.getValue().toString());
			businessDelegate.saveUsuarios(user);

			limpiarCampos();
			actualizarTabla();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}

		return "";
	}

	public String actionEliminar() {
		try {
			log.info("se inició eliminar");
			String cedula = txtCedula.getValue().toString();
			if (cedula.equals("") || cedula == null)
				throw new Exception("Debe existir una cédula");

			businessDelegate.deleteUsuarios(new Long(cedula));

			limpiarCampos();
			actualizarTabla();

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		return "";
	}

	public String actionModificar() {
		try {
			log.info("se inició modificar");
			Usuarios user = new Usuarios();
			TiposUsuarios tipoUsuario = new TiposUsuarios();
			Long idTipoUser = new Long(tipoUsuarioSeleccionado);
			tipoUsuario.setTusuCodigo(idTipoUser);

			user.setTiposUsuarios(tipoUsuario);

			Long cedula = new Long(txtCedula.getValue().toString());
			user.setUsuCedula(cedula);
			user.setUsuClave(txtClave.getValue().toString());
			user.setUsuLogin(txtUsuario.getValue().toString());
			user.setUsuNombre(txtNombre.getValue().toString());
			businessDelegate.updateUsuarios(user);

			actualizarTabla();
			limpiarCampos();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		return "";
	}

	public String actionConsultar() {
		try {
			String cedula = txtCedula.getValue().toString();
			long convCedula = 0;
			if (!cedula.equals("") && cedula != null)
				convCedula = Long.parseLong(cedula);
			String nombre = txtNombre.getValue().toString();
			String usuario = txtUsuario.getValue().toString();
			String clave = txtClave.getValue().toString();
			Long tipoUsuario = null;
			if ( tipoUsuarioSeleccionado != null && !tipoUsuarioSeleccionado.equals("") )
				tipoUsuario = Long.parseLong(tipoUsuarioSeleccionado);

			List<Usuarios> usuariosConsultados = businessDelegate.consultarUsuarios(convCedula, tipoUsuario, nombre,
					usuario, clave);

			actualizarTabla(usuariosConsultados);
			limpiarCampos();

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		return "";
	}

	public void logout() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		try {
			context.getExternalContext().redirect(request.getContextPath() + "/Login.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void limpiarCampos() {
		txtCedula.setValue("");
		txtNombre.setValue("");
		txtUsuario.setValue("");
		txtClave.setValue("");
		tipoUsuarioSeleccionado = "";
	}

	public InputText getTxtUsuario() {
		return txtUsuario;
	}

	public void setTxtUsuario(InputText txtUsuario) {
		this.txtUsuario = txtUsuario;
	}

	public Password getTxtClave() {
		return txtClave;
	}

	public void setTxtClave(Password txtClave) {
		this.txtClave = txtClave;
	}

	public InputText getTxtCedula() {
		return txtCedula;
	}

	public void setTxtCedula(InputText txtCedula) {
		this.txtCedula = txtCedula;
	}

	public InputText getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(InputText txtNombre) {
		this.txtNombre = txtNombre;
	}

	public CommandButton getButGuardar() {
		return butGuardar;
	}

	public void setButGuardar(CommandButton butGuardar) {
		this.butGuardar = butGuardar;
	}

	public CommandButton getButModificar() {
		return butModificar;
	}

	public void setButModificar(CommandButton butModificar) {
		this.butModificar = butModificar;
	}

	public CommandButton getButEliminar() {
		return butEliminar;
	}

	public void setButEliminar(CommandButton butEliminar) {
		this.butEliminar = butEliminar;
	}

	public CommandButton getButConsultar() {
		return butConsultar;
	}

	public void setButConsultar(CommandButton butConsultar) {
		this.butConsultar = butConsultar;
	}

}

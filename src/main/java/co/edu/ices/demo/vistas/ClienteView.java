package co.edu.ices.demo.vistas;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.component.inputtext.InputText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.DemoBanco.modelo.Clientes;
import co.edu.icesi.DemoBanco.modelo.Cuentas;
import co.edu.icesi.DemoBanco.modelo.TiposDocumentos;
import co.edu.icesi.demo.business.IBusinessDelegate;

@ManagedBean
@ViewScoped
public class ClienteView {

	private final static Logger log = LoggerFactory.getLogger(ClienteView.class);

	@EJB
	private IBusinessDelegate businessDelegate;

	// implementar atributos y método de relleno

	private List<Clientes> listaClientes;

	private String tipoDocumentoSeleccionado;
	private List<SelectItem> listaTiposDocumentos;

	private InputText txtId;
	private InputText txtDireccion;
	private InputText txtNombre;
	private InputText txtTelefono;
	private InputText txtCorreo;

	@PostConstruct
	public void init() {
		try {
			List<TiposDocumentos> tipos = businessDelegate.getTiposDocumentos();
			listaTiposDocumentos = new ArrayList<SelectItem>();
			for (TiposDocumentos td : tipos) {
				listaTiposDocumentos.add(new SelectItem(td.getTdocCodigo(), td.getTdocNombre()));
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
		listaClientes = businessDelegate.getClientes();
	}

	public void actualizarTabla(List<Clientes> parametros) throws Exception {
		listaClientes = parametros;
	}

	public IBusinessDelegate getBusinessDelegate() {
		return businessDelegate;
	}

	public void setBusinessDelegate(IBusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}

	public String actionGuardar() {
		try {
			log.info("se inició guardar");

			if (txtId == null || txtDireccion == null || txtNombre == null || txtTelefono == null || txtCorreo == null
					|| tipoDocumentoSeleccionado.equals(""))
				throw new Exception("TODOS LOS CAMPOS DEBEN ESTAR LLENOS");

			Clientes cliente = new Clientes();
			TiposDocumentos td = new TiposDocumentos();
			td = businessDelegate.getTiposDocumentos(Long.parseLong(tipoDocumentoSeleccionado));

			cliente.setCliDireccion(txtDireccion.getValue().toString());
			cliente.setCliId(Long.parseLong(txtId.getValue().toString()));
			cliente.setCliMail(txtCorreo.getValue().toString());
			cliente.setCliNombre(txtNombre.getValue().toString());
			cliente.setCliTelefono(txtTelefono.getValue().toString());
			cliente.setTiposDocumentos(td);

			businessDelegate.saveClientes(cliente);
			Cuentas cuenta = new Cuentas();
			cuenta.setClientes(cliente);
			cuenta.setCueSaldo(new BigDecimal("0"));

			cuenta.setCueNumero("" + System.currentTimeMillis());
			String digitos = (cuenta.getClientes().getCliId() + "");
			digitos = digitos.substring(digitos.length() - 5, digitos.length() - 1);
			cuenta.setCueClave(cuenta.getCueNumero() + digitos);
			cuenta.setCueActiva("P");

			businessDelegate.saveCuentas(cuenta);
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
			String id = txtId.getValue().toString();
			if (id.equals("") || id == null)
				throw new Exception("Debe existir una cédula");

			businessDelegate.deleteClientes(new Long(id));

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
			if (txtId == null || txtDireccion == null || txtNombre == null || txtTelefono == null || txtCorreo == null
					|| tipoDocumentoSeleccionado.equals(""))
				throw new Exception("TODOS LOS CAMPOS DEBEN ESTAR LLENOS");
			Clientes cliente = new Clientes();
			TiposDocumentos td = new TiposDocumentos();
			td = businessDelegate.getTiposDocumentos(Long.parseLong(tipoDocumentoSeleccionado));
				
			cliente.setCliDireccion(txtDireccion.getValue().toString());
			cliente.setCliId(Long.parseLong(txtId.getValue().toString()));
			cliente.setCliMail(txtCorreo.getValue().toString());
			cliente.setCliNombre(txtNombre.getValue().toString());
			cliente.setCliTelefono(txtTelefono.getValue().toString());
			cliente.setTiposDocumentos(td);

			businessDelegate.updateClientes(cliente);
			actualizarTabla();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		return "";
	}

	public String actionConsultar() {
		try {
			log.info("INICIA CONSULTAR");
			String id = txtId.getValue().toString();
			log.info("pasaa");
			long convId = 0;
			if (!id.equals("") && id != null)
				convId = Long.parseLong(id);
			log.info("pasaa");

			String nombre = txtNombre.getValue().toString();
			String direccion = txtDireccion.getValue().toString();
			String tele = txtTelefono.getValue().toString();
			String mail = txtCorreo.getValue().toString();
			log.info("pasaax");

			long tipo = 0;
			log.info(tipoDocumentoSeleccionado);

			if (tipoDocumentoSeleccionado != null && !tipoDocumentoSeleccionado.equals(""))
				tipo = Long.parseLong(tipoDocumentoSeleccionado);
			log.info("pasaax");

			log.info(convId + "---" + tipo + "---" + nombre + "---" + direccion + "---" + tele + "---" + mail + "");

			List<Clientes> clientesConsultados = businessDelegate.consultarClientes(convId, tipo, nombre, direccion,
					tele, mail);
			log.info(clientesConsultados.size() + "");
			actualizarTabla(clientesConsultados);

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		return "";
	}

	public void limpiarCampos() {
		tipoDocumentoSeleccionado = "";
		txtId.setValue(null);
		txtDireccion.setValue(null);
		txtNombre.setValue(null);
		txtTelefono.setValue(null);
		txtCorreo.setValue(null);
	}

	public InputText getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(InputText txtNombre) {
		this.txtNombre = txtNombre;
	}

	public List<Clientes> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Clientes> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public String getTipoDocumentoSeleccionado() {
		return tipoDocumentoSeleccionado;
	}

	public void setTipoDocumentoSeleccionado(String tipoDocumentoSeleccionado) {
		this.tipoDocumentoSeleccionado = tipoDocumentoSeleccionado;
	}

	public List<SelectItem> getListaTiposDocumentos() {
		return listaTiposDocumentos;
	}

	public void setListaTiposDocumentos(List<SelectItem> listaTiposDocumentos) {
		this.listaTiposDocumentos = listaTiposDocumentos;
	}

	public InputText getTxtId() {
		return txtId;
	}

	public void setTxtId(InputText txtId) {
		this.txtId = txtId;
	}

	public InputText getTxtDireccion() {
		return txtDireccion;
	}

	public void setTxtDireccion(InputText txtDireccion) {
		this.txtDireccion = txtDireccion;
	}

	public InputText getTxtTelefono() {
		return txtTelefono;
	}

	public void setTxtTelefono(InputText txtTelefono) {
		this.txtTelefono = txtTelefono;
	}

	public InputText getTxtCorreo() {
		return txtCorreo;
	}

	public void setTxtCorreo(InputText txtCorreo) {
		this.txtCorreo = txtCorreo;
	}

}

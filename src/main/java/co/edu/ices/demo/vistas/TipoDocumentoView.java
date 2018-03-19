package co.edu.ices.demo.vistas;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.component.inputtext.InputText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.DemoBanco.modelo.TiposDocumentos;
import co.edu.icesi.demo.business.IBusinessDelegate;

@ManagedBean
@ViewScoped
public class TipoDocumentoView {

	private final static Logger log = LoggerFactory.getLogger(TipoDocumentoView.class);

	@EJB
	private IBusinessDelegate businessDelegate;

	// implementar atributos y método de relleno
	private List<TiposDocumentos> listaDocumentos;

	private InputText txtCodigo;
	private InputText txtNombre;

	@PostConstruct
	public void init() {
		try {
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
		listaDocumentos = businessDelegate.getTiposDocumentos();
	}

	public void actualizarTabla(List<TiposDocumentos> parametros) throws Exception {
		listaDocumentos = parametros;
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
			TiposDocumentos td = new TiposDocumentos();
			td.setTdocCodigo(Long.parseLong(txtCodigo.getValue().toString()));
			td.setTdocNombre(txtNombre.getValue().toString());
			businessDelegate.saveTiposDocumentos(td);
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
			String id = txtCodigo.getValue().toString();
			if (id.equals("") || id == null)
				throw new Exception("Debe existir una cédula");

			businessDelegate.deleteTiposDocumentos(new Long(id));

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
			TiposDocumentos td = new TiposDocumentos();
			td.setTdocCodigo(Long.parseLong(txtCodigo.getValue().toString()));
			td.setTdocNombre(txtNombre.getValue().toString());
			businessDelegate.updateTiposDocumentos(td);
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
			String id = txtCodigo.getValue().toString();
			long convId = 0;
			if (!id.equals("") && id != null)
				convId = Long.parseLong(id);

			String nombre = txtNombre.getValue().toString();
			List<TiposDocumentos> clientesConsultados = businessDelegate.consultarTiposDocumentos(convId, nombre);
			actualizarTabla(clientesConsultados);

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		return "";
	}

	public void limpiarCampos() {
		txtCodigo.setValue(null);
		txtNombre.setValue(null);
	}

	public List<TiposDocumentos> getListaDocumentos() {
		return listaDocumentos;
	}

	public void setListaDocumentos(List<TiposDocumentos> listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}

	public InputText getTxtCodigo() {
		return txtCodigo;
	}

	public void setTxtCodigo(InputText txtCodigo) {
		this.txtCodigo = txtCodigo;
	}

	public InputText getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(InputText txtNombre) {
		this.txtNombre = txtNombre;
	}

}

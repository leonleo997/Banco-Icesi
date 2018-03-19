package co.edu.ices.demo.vistas;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.core.layout.PatternSelector;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.inputtextarea.InputTextarea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.DemoBanco.modelo.Consignaciones;
import co.edu.icesi.DemoBanco.modelo.ConsignacionesId;
import co.edu.icesi.demo.business.IBusinessDelegate;

@ManagedBean
@ViewScoped
public class ConsignacionView {

	private final static Logger log = LoggerFactory.getLogger(ConsignacionView.class);

	@EJB
	private IBusinessDelegate businessDelegate;

	// implementar atributos y método de relleno

	private List<Consignaciones> listaConsignaciones;

	private InputText txtNumCuenta;
	private InputText txtCodigo;
	private InputText txtUsuario;
	private InputText txtValor;
	private Date fecha;
	private InputTextarea txtDescripcion;

	@PostConstruct
	public void init() {
		try {

			actualizarTabla();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
	}

	public void actualizarTabla() throws Exception {
		listaConsignaciones = businessDelegate.getConsignaciones();
		log.info(listaConsignaciones.size() + "");
	}

	public void actualizarTabla(List<Consignaciones> parametros) throws Exception {
		listaConsignaciones = parametros;
	}

	public IBusinessDelegate getBusinessDelegate() {
		return businessDelegate;
	}

	public void setBusinessDelegate(IBusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}

	public String actionGuardar() {
		try {
			log.info("se inició guardar consignacion");
			
			if(txtNumCuenta==null || txtValor==null)
				throw new Exception("TODOS LOS CAMPOS DEBEN ESTAR LLENOS");
			
			Consignaciones consignacion = new Consignaciones();
			if (txtDescripcion != null)
				consignacion.setConDescripcion(txtDescripcion.getValue().toString() + " | Creado por: "
						+ ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false))
								.getAttribute("cedula"));
			else {
			
			log.info(""+((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false))
								.getAttribute("cedula"));
			
				consignacion.setConDescripcion(" | Creado por: "
						+ ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false))
								.getAttribute("cedula"));
				log.info("ENTRO DESC");
			}
			// consignacion.setConFecha(fecha);
			consignacion.setConFecha(new Date());
			consignacion.setConValor(new BigDecimal(txtValor.getValue().toString()));
			consignacion.setCuentas(businessDelegate.getCuentas(txtNumCuenta.getValue().toString()));
			consignacion.setId(new ConsignacionesId(0, txtNumCuenta.getValue().toString()));

//			log.info("infoooooo" + consignacion.getConDescripcion());
//			log.info("infoooooo" + consignacion.getConFecha());
//			log.info("infoooooo" + consignacion.getConValor());
//			log.info("infoooooo" + consignacion.getCuentas());
//			log.info("infoooooo" + consignacion.getId());

			// consignacion.setUsuarios(businessDelegate.getUsuarios(Long.parseLong(txtUsuario.getValue().toString())));
			String usuario = ""
					+ ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false))
							.getAttribute("cedula");
			consignacion.setUsuarios(businessDelegate.getUsuarios(Long.parseLong(usuario)));
			businessDelegate.saveConsignaciones(consignacion);
			log.info("El usuario actual es " + usuario);
			log.info("El usuario actual es " + usuario);
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
			long codigo = Long.parseLong(txtCodigo.getValue().toString());
			String numCuenta = txtNumCuenta.getValue().toString();
			if (codigo < 0 && (numCuenta.equals("") || numCuenta == null))
				throw new Exception("Debe existir una cédula");

			businessDelegate.deleteConsignaciones(codigo, numCuenta);

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
			if(txtNumCuenta==null || txtValor==null)
				throw new Exception("TODOS LOS CAMPOS DEBEN ESTAR LLENOS");

			Consignaciones consignacion = new Consignaciones();
			consignacion.setConDescripcion(txtDescripcion.getValue().toString() + "| Creado por: "
					+ ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false))
							.getAttribute("cedula"));
			consignacion.setConFecha(fecha);
			consignacion.setConValor(new BigDecimal(txtValor.getValue().toString()));
			consignacion.setCuentas(businessDelegate.getCuentas(txtNumCuenta.getValue().toString()));
			consignacion.setId(new ConsignacionesId(Long.parseLong(txtCodigo.getValue().toString()),
					txtNumCuenta.getValue().toString()));
			String usuario = ""
					+ ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false))
							.getAttribute("cedula");
			consignacion.setUsuarios(businessDelegate.getUsuarios(Long.parseLong(usuario)));
			businessDelegate.updateConsignaciones(consignacion);

			actualizarTabla();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		return "";
	}

	public String actionConsultar() {
		try {
			String codigo = txtCodigo.getValue().toString();
			long convCodigo = 0;
			if (!codigo.equals("") && codigo != null)
				convCodigo = Long.parseLong(codigo);
			String numCuenta = txtNumCuenta.getValue().toString();
			String descripcion = txtDescripcion.getValue().toString() + "| Creado por: "
					+ ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false))
							.getAttribute("cedula");
			log.info("pasa");
			Date f = fecha;
			log.info("pasa");
			// String usuario = txtUsuario.getValue().toString();
			String usuario = ""
					+ ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false))
							.getAttribute("cedula");
			long convUser = 0;
			if (!usuario.equals("") && usuario != null)
				convUser = Long.parseLong(usuario);
			log.info("pasa");
			String valor = txtValor.getValue().toString();
			BigDecimal convValor = null;
			if (!valor.equals("") && valor != null)
				convValor = new BigDecimal(valor);
			log.info("pasa");
			List<Consignaciones> consignacionesConsultadas = businessDelegate.consultarConsignaciones(convCodigo,
					numCuenta, convUser, convValor, f, descripcion);
			log.info("pasa");
			actualizarTabla(consignacionesConsultadas);

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		return "";
	}

	public void limpiarCampos() {
		txtNumCuenta.setValue(null);
		txtCodigo.setValue(null);
		txtUsuario.setValue(null);
		txtValor.setValue(null);
		fecha=null;
		txtDescripcion.setValue(null);
	}


	public InputText getTxtUsuario() {
		return txtUsuario;
	}

	public void setTxtUsuario(InputText txtUsuario) {
		this.txtUsuario = txtUsuario;
	}

	public List<Consignaciones> getListaConsignaciones() {
		return listaConsignaciones;
	}

	public void setListaConsignaciones(List<Consignaciones> listaConsignaciones) {
		this.listaConsignaciones = listaConsignaciones;
	}

	public InputText getTxtNumCuenta() {
		return txtNumCuenta;
	}

	public void setTxtNumCuenta(InputText txtNumCuenta) {
		this.txtNumCuenta = txtNumCuenta;
	}

	public InputText getTxtCodigo() {
		return txtCodigo;
	}

	public void setTxtCodigo(InputText txtCodigo) {
		this.txtCodigo = txtCodigo;
	}

	public InputText getTxtValor() {
		return txtValor;
	}

	public void setTxtValor(InputText txtValor) {
		this.txtValor = txtValor;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public InputTextarea getTxtDescripcion() {
		return txtDescripcion;
	}

	public void setTxtDescripcion(InputTextarea txtDescripcion) {
		this.txtDescripcion = txtDescripcion;
	}

}

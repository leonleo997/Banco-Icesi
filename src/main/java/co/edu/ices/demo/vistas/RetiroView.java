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
import javax.swing.JOptionPane;

import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.inputtextarea.InputTextarea;
import org.primefaces.component.password.Password;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.DemoBanco.modelo.Cuentas;
import co.edu.icesi.DemoBanco.modelo.Retiros;
import co.edu.icesi.DemoBanco.modelo.RetirosId;
import co.edu.icesi.demo.business.IBusinessDelegate;

@ManagedBean
@ViewScoped
public class RetiroView {

	private final static Logger log = LoggerFactory.getLogger(RetiroView.class);

	@EJB
	private IBusinessDelegate businessDelegate;

	// implementar atributos y método de relleno

	private List<Retiros> listaRetiros;

	private InputText txtNumCuenta;

	private Password clave;

	private InputText txtValor;

	private InputText txtCodigo;
	private InputText txtUsuario;
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
		listaRetiros = businessDelegate.getRetiros();
		log.info(listaRetiros.size() + "");
	}

	public void actualizarTabla(List<Retiros> parametros) throws Exception {
		listaRetiros = parametros;
	}

	public IBusinessDelegate getBusinessDelegate() {
		return businessDelegate;
	}

	public void setBusinessDelegate(IBusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}

	public boolean validarClaveWithCuenta() {

		boolean respuesta = false;

		String password = clave.getValue().toString();
		try {
			Cuentas cue = businessDelegate.getCuentas(txtNumCuenta.getValue().toString());

			if (cue.getCueClave().toString().equals(password)) {
				log.info("Cuentas con clave igual");
				respuesta = true;

			}

		} catch (Exception e) {
			log.info("PPPPPPPPPRRRoblema con la obtencion de la cuenta");
			e.printStackTrace();
		}

		return respuesta;
	}

	public Password getClave() {
		return clave;
	}

	public void setClave(Password clave) {
		this.clave = clave;
	}

	public String actionGuardar() {

		try {
			boolean bol = validarClaveWithCuenta();
			if (bol == false) {
				throw new Exception("La clave no corresponde a la cuenta");
			}
			log.info("se inició guardar");
			if (txtNumCuenta == null || txtValor == null || clave == null)
				throw new Exception("TODOS LOS CAMPOS DEBEN ESTAR LLENOS");

			Retiros retiro = new Retiros();
			String cedula = ""
					+ ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false))
							.getAttribute("cedula");
			if (txtDescripcion != null) {
				retiro.setRetDescripcion(txtDescripcion.getValue().toString() + "| Creado por: " + cedula);
			} else {
				retiro.setRetDescripcion("| Creado por: " + cedula);
			}

			retiro.setRetFecha(new Date());
			if (txtValor.getValue() != null)
				retiro.setRetValor(new BigDecimal(txtValor.getValue().toString()));
			else
				throw new Exception("Debe ingresar un valor de retiro");

			if (txtNumCuenta.getValue() != null) {
				retiro.setId(new RetirosId(0, txtNumCuenta.getValue().toString()));
				retiro.setCuentas(businessDelegate.getCuentas(txtNumCuenta.getValue().toString()));
			} else
				throw new Exception("Debe ingresar un numero de cuenta");

			String usuario = ""
					+ ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false))
							.getAttribute("cedula");
			retiro.setUsuarios(businessDelegate.getUsuarios(Long.parseLong(usuario)));

			businessDelegate.saveRetiros(retiro);
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

			businessDelegate.deleteRetiros(codigo, numCuenta);

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
			if (txtNumCuenta == null || txtValor == null || clave == null)
				throw new Exception("TODOS LOS CAMPOS DEBEN ESTAR LLENOS");

			Retiros retiro = new Retiros();
			retiro.setRetDescripcion(txtDescripcion.getValue().toString() + "| Creado por: "
					+ ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false))
							.getAttribute("cedula"));
			retiro.setRetFecha(new Date());
			retiro.setRetValor(new BigDecimal(txtValor.getValue().toString()));
			retiro.setCuentas(businessDelegate.getCuentas(txtNumCuenta.getValue().toString()));
			retiro.setId(
					new RetirosId(Long.parseLong(txtCodigo.getValue().toString()), txtNumCuenta.getValue().toString()));
			String usuario = ""
					+ ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false))
							.getAttribute("cedula");

			retiro.setUsuarios(businessDelegate.getUsuarios(Long.parseLong(usuario)));
			businessDelegate.updateRetiros(retiro);

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
			;
			Date f = fecha;
			String usuario = txtUsuario.getValue().toString();
			long convUser = 0;
			if (!usuario.equals("") && usuario != null)
				convUser = Long.parseLong(usuario);
			String valor = txtValor.getValue().toString();
			BigDecimal convValor = null;
			if (!valor.equals("") && valor != null)
				convValor = new BigDecimal(valor);
			List<Retiros> retirosConsultados = businessDelegate.consultarRetiros(convCodigo, numCuenta, convUser,
					convValor, f, descripcion);
			actualizarTabla(retirosConsultados);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		return "";
	}

	public void limpiarCampos() {
		txtNumCuenta.setValue(null);
		clave.setValue(null);
		txtValor.setValue(null);
		txtCodigo.setValue(null);
		txtUsuario.setValue(null);
		fecha = null;
		txtDescripcion.setValue(null);
	}

	public InputText getTxtUsuario() {
		return txtUsuario;
	}

	public void setTxtUsuario(InputText txtUsuario) {
		this.txtUsuario = txtUsuario;
	}

	public List<Retiros> getListaRetiros() {
		return listaRetiros;
	}

	public void setListaRetiros(List<Retiros> listaRetiros) {
		this.listaRetiros = listaRetiros;
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

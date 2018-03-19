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
import javax.servlet.http.HttpServletRequest;

import org.primefaces.component.inputtext.InputText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.DemoBanco.modelo.Cuentas;
import co.edu.icesi.demo.business.IBusinessDelegate;

@ManagedBean
@ViewScoped
public class CuentaView {

	private final static Logger log = LoggerFactory.getLogger(CuentaView.class);

	@EJB
	private IBusinessDelegate businessDelegate;

	// implementar atributos y método de relleno

	private List<Cuentas> listaCuentas;

	private String estadoSeleccionado;

	private InputText txtId;
	private InputText txtSaldo;
	private InputText txtNumCuenta;

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
		listaCuentas = businessDelegate.getCuentas();
	}

	public void actualizarTabla(List<Cuentas> parametros) throws Exception {
		listaCuentas = parametros;
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
			if (txtId == null )
				throw new Exception("TODOS LOS CAMPOS DEBEN ESTAR LLENOS");
			Cuentas cuenta = new Cuentas();
			cuenta.setClientes(businessDelegate.getClientes(Long.parseLong(txtId.getValue().toString())));

			log.info("pasooooooooooonumoooooooooooooooooo");
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
			String id = txtNumCuenta.getValue().toString();
			if (id.equals("") || id == null)
				throw new Exception("Debe existir una identificación");

			businessDelegate.deleteCuentas(id);

			actualizarTabla();

		} catch (Exception e) {
			log.info("EEEEEEERRRRRRRRRRRROOOOOOOOOOOORRRRRRRR");
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		return "";
	}

	public String actionModificar() {
		try {
			log.info("se inició modificar");
			if (txtId == null )
				throw new Exception("TODOS LOS CAMPOS DEBEN ESTAR LLENOS");

			Cuentas cuenta = new Cuentas();
			cuenta = businessDelegate.getCuentas(txtNumCuenta.getValue().toString());
			cuenta.setClientes(businessDelegate.getClientes(Long.parseLong(txtId.getValue().toString())));
			cuenta.setCueActiva(estadoSeleccionado);
			cuenta.setCueSaldo(new BigDecimal(txtSaldo.getValue().toString()));

			businessDelegate.updateCuentas(cuenta);
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
			if (txtNumCuenta.getValue() != null && !txtNumCuenta.getValue().toString().equals("")) {
				String id = txtNumCuenta.getValue().toString();
				log.info("" + id);
				String numCuenta = txtNumCuenta.getValue().toString();

				log.info("" + numCuenta);

				Cuentas cue = businessDelegate.getCuentas(numCuenta);
				List<Cuentas> cuentaConsultada = new ArrayList<Cuentas>();
				cuentaConsultada.add(cue);
				log.info("" + cuentaConsultada.size());
				actualizarTabla(cuentaConsultada);
			} else {
				BigDecimal saldo = null;
				long id = 0;
				if (txtSaldo.getValue() != null && !txtSaldo.getValue().toString().equals("")) {
					saldo = new BigDecimal(txtSaldo.getValue().toString());
				}
				if (txtId.getValue() != null && !txtId.getValue().toString().equals("")) {
					id = new Long(txtId.getValue().toString());

				}

				List<Cuentas> cuentaConsultada = businessDelegate.consultarCuentas(txtNumCuenta.getValue().toString(),
						id, saldo, estadoSeleccionado, "");

				actualizarTabla(cuentaConsultada);
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		return "";
	}

	public void limpiarCampos() {
		estadoSeleccionado = "";
		txtId.setValue(null);
		txtSaldo.setValue(null);
		txtNumCuenta.setValue(null);
	}

	public List<Cuentas> getListaCuentas() {
		return listaCuentas;
	}

	public void setListaCuentas(List<Cuentas> listaCuentas) {
		this.listaCuentas = listaCuentas;
	}

	public String getEstadoSeleccionado() {
		return estadoSeleccionado;
	}

	public void setEstadoSeleccionado(String estadoSeleccionado) {
		this.estadoSeleccionado = estadoSeleccionado;
	}

	public InputText getTxtId() {
		return txtId;
	}

	public void setTxtId(InputText txtId) {
		this.txtId = txtId;
	}

	public InputText getTxtSaldo() {
		return txtSaldo;
	}

	public void setTxtSaldo(InputText txtSaldo) {
		this.txtSaldo = txtSaldo;
	}

	public InputText getTxtNumCuenta() {
		return txtNumCuenta;
	}

	public void setTxtNumCuenta(InputText txtNumCuenta) {
		this.txtNumCuenta = txtNumCuenta;
	}

}

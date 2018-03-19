package co.edu.icesi.DemoBanco.modelo.control.webservices;

import java.math.BigDecimal;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import co.edu.icesi.DemoBanco.dao.UsuariosDAO;
import co.edu.icesi.DemoBanco.modelo.Retiros;
import co.edu.icesi.DemoBanco.modelo.RetirosId;
import co.edu.icesi.DemoBanco.modelo.control.ICuentasLogica;
import co.edu.icesi.DemoBanco.modelo.control.IRetirosLogica;
import co.edu.icesi.DemoBanco.modelo.control.IUsuariosLogica;

@Stateless
@WebService(
		portName="RetiroWSPort",
		serviceName="RetiroWSService",
		targetNamespace="http://webservices.control.modelo.DemoBanco.icesi.edu.co/wsdl",
		endpointInterface="co.edu.icesi.DemoBanco.modelo.control.webservices.IRetirosWS"
		)
public class RetiroWS implements IRetirosWS {

	@EJB
	private IRetirosLogica RetirosLogica;
	
	@EJB
	private ICuentasLogica CuentasLogica;

	@EJB 
	private IUsuariosLogica UsuarioLogica;
	
	@Override
	public void retirar(String numCuenta, String clave, BigDecimal valor, String user, String descripcion)
			throws Exception {
		
		if(CuentasLogica.validarCuenta(numCuenta, clave)==false)
			throw new Exception("La contraseña es incorrecta");
		else {
			if(CuentasLogica.getCuentas(numCuenta)==null)
				throw new Exception("La cuenta no existe");
			if(UsuarioLogica.getUsuarios(new Long(user))==null)
				throw new Exception("El usuario no existe");
			
			Retiros retiro= new Retiros();
			retiro.setCuentas(CuentasLogica.getCuentas(numCuenta));
			retiro.setId(new RetirosId(0,numCuenta));
			retiro.setRetDescripcion(descripcion+"||Hecho por "+user);
			retiro.setRetFecha(new Date());
			retiro.setRetValor(valor);
			retiro.setUsuarios(UsuarioLogica.getUsuarios(new Long(user)));
			RetirosLogica.saveRetiros(retiro);
			
		}
		
	}
	
	

}

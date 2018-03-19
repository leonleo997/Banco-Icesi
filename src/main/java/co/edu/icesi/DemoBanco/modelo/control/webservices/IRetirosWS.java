package co.edu.icesi.DemoBanco.modelo.control.webservices;

import java.math.BigDecimal;

import javax.ejb.Local;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@Local
@WebService(targetNamespace = "http://webservices.control.modelo.DemoBanco.icesi.edu.co/wsdl")
public interface IRetirosWS {

	@WebMethod(operationName = "retirar")
	public void retirar(@WebParam(name = "numcuenta") String numCuenta, @WebParam(name = "clave") String clave,
			@WebParam(name = "valor") BigDecimal valor,@WebParam(name="usuario") String user,@WebParam(name="descripcion") String descripcion) throws Exception;

}

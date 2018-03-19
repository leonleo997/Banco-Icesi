package co.edu.icesi.DemoBanco.modelo;
// Generated 12-ago-2017 20:45:50 by Hibernate Tools 5.2.3.Final

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ConsignacionesId generated by hbm2java
 */
@Embeddable
public class ConsignacionesId implements java.io.Serializable {

	private long conCodigo;
	private String cueNumero;

	public ConsignacionesId() {
	}

	public ConsignacionesId(long conCodigo, String cueNumero) {
		this.conCodigo = conCodigo;
		this.cueNumero = cueNumero;
	}

	@Column(name = "con_codigo", nullable = false, precision = 10, scale = 0)
	public long getConCodigo() {
		return this.conCodigo;
	}

	public void setConCodigo(long conCodigo) {
		this.conCodigo = conCodigo;
	}

	@Column(name = "cue_numero", nullable = false, length = 30)
	public String getCueNumero() {
		return this.cueNumero;
	}

	public void setCueNumero(String cueNumero) {
		this.cueNumero = cueNumero;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ConsignacionesId))
			return false;
		ConsignacionesId castOther = (ConsignacionesId) other;

		return (this.getConCodigo() == castOther.getConCodigo())
				&& ((this.getCueNumero() == castOther.getCueNumero()) || (this.getCueNumero() != null
						&& castOther.getCueNumero() != null && this.getCueNumero().equals(castOther.getCueNumero())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getConCodigo();
		result = 37 * result + (getCueNumero() == null ? 0 : this.getCueNumero().hashCode());
		return result;
	}

}

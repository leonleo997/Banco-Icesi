package co.edu.icesi.DemoBanco.modelo;
// Generated 12-ago-2017 20:45:50 by Hibernate Tools 5.2.3.Final

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Consignaciones generated by hbm2java
 */
@Entity
@Table(name = "consignaciones", schema = "public")
public class Consignaciones implements java.io.Serializable {

	private ConsignacionesId id;
	private Cuentas cuentas;
	private Usuarios usuarios;
	private BigDecimal conValor;
	private Date conFecha;
	private String conDescripcion;

	public Consignaciones() {
	}

	public Consignaciones(ConsignacionesId id, Cuentas cuentas, BigDecimal conValor, Date conFecha,
			String conDescripcion) {
		this.id = id;
		this.cuentas = cuentas;
		this.conValor = conValor;
		this.conFecha = conFecha;
		this.conDescripcion = conDescripcion;
	}

	public Consignaciones(ConsignacionesId id, Cuentas cuentas, Usuarios usuarios, BigDecimal conValor, Date conFecha,
			String conDescripcion) {
		this.id = id;
		this.cuentas = cuentas;
		this.usuarios = usuarios;
		this.conValor = conValor;
		this.conFecha = conFecha;
		this.conDescripcion = conDescripcion;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "conCodigo", column = @Column(name = "con_codigo", nullable = false, precision = 10, scale = 0)),
			@AttributeOverride(name = "cueNumero", column = @Column(name = "cue_numero", nullable = false, length = 30)) })
	public ConsignacionesId getId() {
		return this.id;
	}

	public void setId(ConsignacionesId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cue_numero", nullable = false, insertable = false, updatable = false)
	public Cuentas getCuentas() {
		return this.cuentas;
	}

	public void setCuentas(Cuentas cuentas) {
		this.cuentas = cuentas;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usu_cedula")
	public Usuarios getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(Usuarios usuarios) {
		this.usuarios = usuarios;
	}

	@Column(name = "con_valor", nullable = false, precision = 10)
	public BigDecimal getConValor() {
		return this.conValor;
	}

	public void setConValor(BigDecimal conValor) {
		this.conValor = conValor;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "con_fecha", nullable = false, length = 29)
	public Date getConFecha() {
		return this.conFecha;
	}

	public void setConFecha(Date conFecha) {
		this.conFecha = conFecha;
	}

	@Column(name = "con_descripcion", nullable = false, length = 50)
	public String getConDescripcion() {
		return this.conDescripcion;
	}

	public void setConDescripcion(String conDescripcion) {
		this.conDescripcion = conDescripcion;
	}

}

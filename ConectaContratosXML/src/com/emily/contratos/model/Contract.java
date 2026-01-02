package com.emily.contratos.model;


import java.math.BigDecimal;
import java.time.LocalDate;

public class Contract {
    private String numeroExpediente;
    private String adjudicatario;
    private BigDecimal importe;
    private LocalDate fechaAdjudicacion;
    private String organoContratacion;
    private String tipoContrato; // Se almacenará, pero se excluirá del XML de salida
    private String objeto;
    private String procedimiento;
    private String observaciones;

    // Getters y setters
    public String getNumeroExpediente() { return numeroExpediente; }
    public void setNumeroExpediente(String numeroExpediente) { this.numeroExpediente = numeroExpediente; }

    public String getAdjudicatario() { return adjudicatario; }
    public void setAdjudicatario(String adjudicatario) { this.adjudicatario = adjudicatario; }

    public BigDecimal getImporte() { return importe; }
    public void setImporte(BigDecimal importe) { this.importe = importe; }

    public LocalDate getFechaAdjudicacion() { return fechaAdjudicacion; }
    public void setFechaAdjudicacion(LocalDate fechaAdjudicacion) { this.fechaAdjudicacion = fechaAdjudicacion; }

    public String getOrganoContratacion() { return organoContratacion; }
    public void setOrganoContratacion(String organoContratacion) { this.organoContratacion = organoContratacion; }

    public String getTipoContrato() { return tipoContrato; }
    public void setTipoContrato(String tipoContrato) { this.tipoContrato = tipoContrato; }

    public String getObjeto() { return objeto; }
    public void setObjeto(String objeto) { this.objeto = objeto; }

    public String getProcedimiento() { return procedimiento; }
    public void setProcedimiento(String procedimiento) { this.procedimiento = procedimiento; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}

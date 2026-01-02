package com.emily.contratos.db;

import com.emily.contratos.model.Contract;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class DbLoader {

    private final String url;
    private final String user;
    private final String pass;

    public DbLoader(String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    public void insertContracts(List<Contract> contracts) throws SQLException {
        String sql = "INSERT INTO contratos (" +
                "numero_expediente, adjudicatario, importe, fecha_adjudicacion, " +
                "organo_contratacion, tipo_contrato, objeto, procedimiento, observaciones" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);

            for (Contract c : contracts) {
            	ps.setString(1, c.getNumeroExpediente() != null ? c.getNumeroExpediente() : "SIN_EXPEDIENTE");

                ps.setString(2, c.getAdjudicatario());
                if (c.getImporte() != null) {
                    ps.setBigDecimal(3, c.getImporte());
                } else {
                    ps.setNull(3, java.sql.Types.DECIMAL);
                }
                if (c.getFechaAdjudicacion() != null) {
                    ps.setDate(4, Date.valueOf(c.getFechaAdjudicacion()));
                } else {
                    ps.setNull(4, java.sql.Types.DATE);
                }
                ps.setString(5, c.getOrganoContratacion());
                ps.setString(6, c.getTipoContrato());
                ps.setString(7, c.getObjeto());
                ps.setString(8, c.getProcedimiento());
                ps.setString(9, c.getObservaciones());
                ps.addBatch();
            }
            ps.executeBatch();
            conn.commit();
        }
    }
}

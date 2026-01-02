package com.emily.contratos;

import com.emily.contratos.io.XmlReader;
import com.emily.contratos.io.XmlWriter;
import com.emily.contratos.db.DbLoader;
import com.emily.contratos.model.Contract;
import java.sql.SQLException;


import java.io.FileInputStream;

import java.io.File;
import java.util.List;

public class App {

    public static void main(String[] args) {
    	 // Ruta local del XML
        File xmlFile = new File("C:\\Users\\Usuario\\Desktop\\DAM 2do AÑO\\Acceso a datos\\trabajo de enfoque\\doc\\contratos.xml");
        System.out.println("Ruta: " + xmlFile.getAbsolutePath());
        System.out.println("Existe? " + xmlFile.exists());

    	
        // Parámetros de conexión a MySQL (ajusta según tu entorno)
        String jdbcUrl = "jdbc:mysql://localhost:3306/contratos_andalucia?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "root";
        String pass = "emily.15";

        // Archivo de salida sin "tipo_contrato"
        File outXml = new File("contratos.xml");

        try {
            // 1) Leer y parsear el XML
            XmlReader reader = new XmlReader();
            System.out.println("Leyendo xml local");
            List<Contract> contracts = reader.parse(new FileInputStream(xmlFile));
            System.out.println("Contratos leídos: " + contracts.size());

            // 2) Insertar en MySQL
            System.out.println("Insertando en MySQL...");
            DbLoader loader = new DbLoader(jdbcUrl, user, pass);
            loader.insertContracts(contracts);
            System.out.println("Inserción completada.");

            // 3) Generar XML de salida sin 'tipo_contrato'
            System.out.println("Generando XML de salida...");
            XmlWriter writer = new XmlWriter();
            writer.writeWithoutTipoContrato(contracts, outXml);
            System.out.println("XML generado: " + outXml.getAbsolutePath());

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error SQL: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error general: " + e.getMessage());
        }
    }
}


package com.softhub.umiyakhor.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariDataSource;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Component
public class JasperExporter {
	
	JasperReport jasperReport;
    JasperPrint jasperPrint;
    OutputStream outputStream;
    File file;
    
    @Autowired
    HikariDataSource dataSource;
    

	public void jasperExporterPDF(Map<String, Object> jasperParameter, String jrxmlpath, String fileName,
			HttpServletResponse response) throws IOException {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			outputStream = response.getOutputStream();
			jasperReport = JasperCompileManager.compileReport(jrxmlpath);
			jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParameter, connection);
			file = File.createTempFile("output.", ".pdf");
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline; filename=" + fileName + ".pdf");
			JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				connection = null;
			}
		}

	}
}

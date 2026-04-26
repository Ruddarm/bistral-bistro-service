package com.bistral.app.bistral_bistro_service.service;


import com.bistral.app.bistral_bistro_service.dtos.Invoice;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class JasperService {

    public  byte[] getInvoiceReport() throws JRException, IOException {

        TreeMap<String,Integer> map = new TreeMap<>();
        Set<Map.Entry<String,Integer>> set = map.entrySet();
        set.stream().findFirst();
        List<Invoice> data = List.of(
                new Invoice("order123","product1",120.0),
                new Invoice("order124","product2",120.5),
                new Invoice("order125","product3",150.5),
                new Invoice("order126","product4",190.80)
        );
        InputStream template = new ClassPathResource("/reports/invoice.jrxml").getInputStream();
        JasperReport jasperReport = JasperCompileManager.compileReport(template);
        JRBeanCollectionDataSource dataSource =
                new JRBeanCollectionDataSource(data);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,new HashMap<>(),dataSource);
        return  JasperExportManager.exportReportToPdf(jasperPrint);
    }


}

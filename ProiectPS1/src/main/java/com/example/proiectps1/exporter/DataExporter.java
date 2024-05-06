package com.example.proiectps1.exporter;

import com.example.proiectps1.model.User;
import com.example.proiectps1.model.User.RoleType;

public class DataExporter {

    private final XMLFileExporter xmlFileExporter;
    public DataExporter() {
        this.xmlFileExporter = new XMLFileExporter();
    }
    public String exportToXML(Object data) {
        return xmlFileExporter.exportData(data);
    }


    public static void main(String[] args) {

        User user = new User();
        user.setId(1L);
        user.setUsername("ruxandra_andro");
        user.setPassword("password123");
        user.setRole(RoleType.OWNER);
        user.setEmail("andro.ruxandra@gmail.com");
        user.setPhoneNumber("123-456-7890");

        DataExporter dataExporter = new DataExporter();

        String xmlOutput = dataExporter.exportToXML(user);

        System.out.println("XML Output:");
        System.out.println(xmlOutput);
    }
}

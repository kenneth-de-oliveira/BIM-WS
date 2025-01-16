package com.example.api.soap.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.util.Objects;

@Configuration
@AllArgsConstructor
public class InventoryManagementWsConfig {

    private final Environment env;

    @Bean(name = "InventoryManagementWS")
    public DefaultWsdl11Definition createWsdl11Definition(XsdSchema xsdSchema) {
        var wsdlDefinition = new DefaultWsdl11Definition();
        wsdlDefinition.setPortTypeName(Objects.requireNonNull(env.getProperty("ws.inventory.management.portTypeName")));
        wsdlDefinition.setLocationUri(Objects.requireNonNull(env.getProperty("ws.inventory.management.locationUri")));
        wsdlDefinition.setTargetNamespace(Objects.requireNonNull(env.getProperty("ws.inventory.management.namespaceUri")));
        wsdlDefinition.setSchema(xsdSchema);
        return wsdlDefinition;
    }

    @Bean
    public XsdSchema loadXsdSchema() {
        var classPathResource = new ClassPathResource(Objects.requireNonNull(env.getProperty("ws.inventory.management.classPathResource")));
        return new SimpleXsdSchema(classPathResource);
    }

}
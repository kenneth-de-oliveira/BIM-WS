package com.example.shared.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

@Slf4j
public class LoggerHandler implements EndpointInterceptor {

    @Override
    public boolean handleRequest(MessageContext messageContext, Object endpoint) {
        try {
            var source = messageContext.getRequest().getPayloadSource();
            var stringWriter = new StringWriter();
            var transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, new StreamResult(stringWriter));
            var xml = stringWriter.toString();
            log.info("Received request ==> {}", xml);
        } catch (Exception exception) {
            log.error("Error logging request: {}", exception.getMessage());
        }
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext, Object endpoint) {
        try {
            var source = messageContext.getResponse().getPayloadSource();
            var stringWriter = new StringWriter();
            var transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(source, new StreamResult(stringWriter));
            var xml = stringWriter.toString();
            log.info("Received response ==> {}", xml);
        } catch (Exception exception) {
            log.error("Error logging response: {}", exception.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext, Object endpoint) {
        if (messageContext.hasResponse()) {
            return handleResponse(messageContext, endpoint);
        } else  {
            return handleRequest(messageContext, endpoint);
        }
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Object endpoint, Exception ex) {
        if (ex != null) {
            log.error("Exception occurred after completion: {}", ex.getMessage());
        } else {
            log.debug("Processing completed successfully for message context: {}", messageContext);
        }
    }

}
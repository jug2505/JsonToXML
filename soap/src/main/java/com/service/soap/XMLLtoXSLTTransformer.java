package com.service.soap;

import org.springframework.util.ResourceUtils;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.io.StringWriter;


public class XMLLtoXSLTTransformer {

    // Преобразование XML в XSLT
    public static String transform(String xmlString) throws TransformerException, FileNotFoundException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(ResourceUtils.getFile("classpath:transform.xsl"));

        Transformer transformer = factory.newTransformer(xslt);
        Source xml = new StreamSource(new StringReader(xmlString));
        StringWriter writer = new StringWriter();
        transformer.transform(xml, new StreamResult(writer));
        return writer.toString().substring(38); // Отрезаем часть с <xml ...>
    }
}

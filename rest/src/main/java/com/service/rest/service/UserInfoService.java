package com.service.rest.service;

import com.service.rest.entity.Document;
import com.service.rest.entity.DocumentType;
import com.service.rest.entity.Gender;
import com.service.rest.entity.UserInfo;
import com.service.rest.repository.UserInfoRepositoty;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Date;
import java.util.List;

@Service
public class UserInfoService {
    private final UserInfoRepositoty userInfoRepositoty;

    public UserInfoService(UserInfoRepositoty userInfoRepositoty) {
        this.userInfoRepositoty = userInfoRepositoty;
    }

    // Создание объекта UserInfo из JSON
    public UserInfo getUserInfoFromJson(String string) throws JSONException {
        UserInfo userInfo = new UserInfo();
        JSONObject json = new JSONObject(string);
        userInfo.setName(json.getString("name"));
        userInfo.setSurname(json.getString("surname"));
        userInfo.setPatronymic(json.getString("patronymic"));
        userInfo.setBirthDate(Date.valueOf(json.getString("birthDate")));
        userInfo.setGender(Gender.valueOf(json.getString("gender")));
        JSONObject docJson = json.getJSONObject("document");

        Document document = new Document();
        document.setSeries(docJson.getString("series"));
        document.setNumber(docJson.getString("number"));
        document.setType(DocumentType.valueOf(docJson.getString("type")));
        document.setIssueDate(Date.valueOf(docJson.getString("issueDate")));

        userInfo.setDocument(document);

        return userInfo;
    }

    public List<UserInfo> getAllUserInfo(){
        return userInfoRepositoty.getAll();
    }

    public void saveUserInfo(UserInfo userInfo) {
        userInfoRepositoty.saveUserInfo(userInfo);
    }

    // Возвращает w3c Node из XML
    public Node getPersonNodeFromXSLT(String xsltString) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        org.w3c.dom.Document doc = db.parse(new InputSource(new StringReader(xsltString)));
        doc.getDocumentElement().normalize();
        return doc.getElementsByTagName("person").item(0);
    }

    // Создание объекта UserInfo из w3c Node
    public UserInfo getUserInfoFromNode(Node node) {
        UserInfo userInfo = new UserInfo();
        NamedNodeMap personAttributes = node.getAttributes();

        userInfo.setName(personAttributes.getNamedItem("name").getNodeValue());
        userInfo.setSurname(personAttributes.getNamedItem("surname").getNodeValue());
        userInfo.setPatronymic(personAttributes.getNamedItem("patronymic").getNodeValue());
        userInfo.setBirthDate(Date.valueOf(personAttributes.getNamedItem("birthDate").getNodeValue()));
        userInfo.setGender(Gender.valueOf(personAttributes.getNamedItem("gender").getNodeValue()));

        Node nodeDoc = node.getFirstChild();
        NamedNodeMap documentAttributes = nodeDoc.getAttributes();

        com.service.rest.entity.Document document = new com.service.rest.entity.Document();
        document.setSeries(documentAttributes.getNamedItem("series").getNodeValue());
        document.setNumber(documentAttributes.getNamedItem("number").getNodeValue());
        document.setType(DocumentType.valueOf(documentAttributes.getNamedItem("type").getNodeValue()));
        document.setIssueDate(Date.valueOf(documentAttributes.getNamedItem("issueDate").getNodeValue()));

        userInfo.setDocument(document);

        return userInfo;
    }

    // Из w3c Node генерируется XML-строка
    public String nodeToString(Node node) throws TransformerException {
        StringWriter sw = new StringWriter();
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.transform(new DOMSource(node), new StreamResult(sw));
        return sw.toString();
    }
}

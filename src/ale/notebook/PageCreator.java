package ale.notebook;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class PageCreator {


    PageType pageType;

    public void createPage(PageType pageType, String title,String content,File outputFile){
        switch (pageType){
            case HTML:
                createHTMLPage(title, content, outputFile);
                break;
            case IMG:
                createIMGPage();
                break;
        }
    }

    private void createHTMLPage(String title, String content, File outputFile){
        try{

            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(outputFile);

            //Root Element
            Node noteNode = document.getFirstChild();

            Element pageElement = document.createElement("page");
            noteNode.appendChild(pageElement);

            Node pageNode = pageElement;

            Attr typeAttr = document.createAttribute("type");
            typeAttr.setValue("HTML");

            pageElement.setAttributeNode(typeAttr);

            Element titleElement = document.createElement("title");
            titleElement.setTextContent(title);
            pageNode.appendChild(titleElement);

            Element contentElement = document.createElement("content");
            contentElement.setTextContent("<![CDATA[" + content + "]]>");
            pageNode.appendChild(contentElement);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);

            StreamResult result = new StreamResult(outputFile);
            transformer.transform(source, result);

        }catch(ParserConfigurationException parseConfigExcep){
            System.out.println("<----- Parser Config Exception in Create HTML Page ----->");
            parseConfigExcep.printStackTrace();
            System.out.println("<---------->");
        }catch (TransformerException transformerExcep){
            System.out.println("<----- Transformer Exception in Create HTML Page ----->");
            transformerExcep.printStackTrace();
            System.out.println("<---------->");
        }catch (SAXException saxExcep){
            System.out.println("<----- SAX Exception in Create HTML Page ----->");
            saxExcep.printStackTrace();
            System.out.println("<---------->");
        }catch (IOException ioExcep){
            System.out.println("<----- IO Exception in Create HTML Page ----->");
            ioExcep.printStackTrace();
            System.out.println("<---------->");
        }
    }

    private void createIMGPage(){}

}


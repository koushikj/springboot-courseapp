import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XMLDomParser {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        String xmlResp = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><navigation><extensionId>com.cisco.wap.web.Navigation</extensionId><navigationMenubarItem><id>administration</id><label>cpm_administration</label><megamenuConnectsContextLevel>true</megamenuConnectsContextLevel><columns>0</columns><type>GROUP</type></navigationMenubarItem><navigationMenubarItem><id>pulloutLandingPage</id><handler>wap.application.action.OpenPageActionHandler</handler><label>Pullout Landing Page</label><image>mm_home</image><type>GROUP</type><actionParams><hiddenPage>true</hiddenPage><pageId>page_pullout_landing</pageId></actionParams></navigationMenubarItem><navigationMenubarItem><id>reportDetails</id><handler>wap.application.action.OpenPageActionHandler</handler><label>Report Details</label><image>mm_home</image><type>GROUP</type><actionParams><hiddenPage>true</hiddenPage><hideTopMenu>true</hideTopMenu><pageId>page_reports_details</pageId></actionParams></navigationMenubarItem><navigationItem><id>administration_system</id><image>mm_system</image><label>System</label><taskMenuPath>administration</taskMenuPath><type>GROUP</type></navigationItem><navigationItem><id>administration_system_time</id><handler>wap.application.action.OpenPageActionHandler</handler><label>System Time</label><taskMenuPath>administration/administration_system</taskMenuPath><actionParams><pageId>com_cisco_xmp_web_page_admin_system_time</pageId></actionParams></navigationItem><navigationItem><id>administration_session_info</id><handler>wap.application.action.OpenPageActionHandler</handler><label>Session Info</label><taskMenuPath>administration/administration_system</taskMenuPath><actionParams><pageId>com_cisco_xmp_web_page_session_info</pageId></actionParams></navigationItem><navigationItem><id>server_cert</id><handler>wap.application.action.OpenPageActionHandler</handler><label>Server Certificate</label><taskMenuPath>administration/administration_system</taskMenuPath><actionParams><pageId>com_cisco_xmp_web_page_admin_server_cert</pageId></actionParams></navigationItem><navigationItem><id>outstandingSigningRequests</id><handler>wap.application.action.OpenPageActionHandler</handler><label>Certificate Signing Requests</label><taskMenuPath>administration/administration_system</taskMenuPath><actionParams><pageId>com_cisco_xmp_web_page_admin_outstanding_signings</pageId></actionParams></navigationItem></navigation>";
        String allMenus = "/Users/kojayaku/Documents/Koushik/intelij/springboot/src/test/java/allMenus.xml";
        String restrictedMenus = "/Users/kojayaku/Documents/Koushik/intelij/springboot/src/test/java/restrictedMenus.xml";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        builder = factory.newDocumentBuilder();
        //Document doc = builder.parse(new InputSource(new StringReader(xmlResp)));
        Document doc = builder.parse(new File(restrictedMenus));
        doc.getDocumentElement().normalize();
        NodeList list = doc.getElementsByTagName("actionParams");
        //hiddenPage
        int menuItems = list.getLength();
        int enabledMenuItems =0;
        for (int i = 0; i < list.getLength(); i++) {
            Node nNode = list.item(i);
            System.out.println("\nCurrent Element :" + nNode.getNodeName());
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                int hiddenFieldLength = eElement.getElementsByTagName("hiddenPage").getLength();
                if (hiddenFieldLength>0){
                    String isHidden = eElement.getElementsByTagName("hiddenPage").item(0).getTextContent();
                    if(!isHidden.equals("true")){
                        System.out.println("menu enabled! so exiting the check.");
                        enabledMenuItems++;
                        break;
                    }
                    System.out.println("Hidden Page: " + eElement.getElementsByTagName("hiddenPage").item(0).getTextContent());
                    System.out.println("page Id: " + eElement.getElementsByTagName("pageId").item(0).getTextContent());
                }else{
                    enabledMenuItems++;
                    System.out.println("menu enabled! so exiting the check.");
                    break;
                }
            }
        }
        System.out.println("Enabled menu items :"+enabledMenuItems);
        System.out.println("Total menu items :"+menuItems);
    }
}

package RISK;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class XMLHandler extends DefaultHandler {
    private boolean isPlayer, isTerritory, isName, isHolder, isTroops, isAI, isStage, isOwnTerritory,isID;

    private Stage stage;
    private RiskModel model;
    private String name;
    private boolean AI;
    private int troops,id;
    private String holder;
    private String ownTerritory;
    private Player player = null;
    private Territory territory = null;
    private StringBuilder sb = null;


    //private BufferedImage image;

    public XMLHandler() throws IOException {

        model = new RiskModel();

        isPlayer = false;
        isTerritory = false;
        isStage = false;
        isName = false;
        isHolder = false;
        isTroops = false;
        isAI = false;
        isOwnTerritory = false;
        isID = false;
    }

    /**
     * @param model the model to set
     */
    public void setModel(RiskModel model) {
        this.model = model;
    }

    public void toXMLFile(String filename) throws SAXException {
        SAXTransformerFactory factory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();

        try {
            TransformerHandler handler = factory.newTransformerHandler();
            Transformer transformer = handler.getTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            Result result = new StreamResult(filename + ".xml");
            handler.setResult(result);

            AttributesImpl attr = new AttributesImpl();

            handler.startDocument();
            attr.clear();
            handler.startElement("", "", "RISK", attr);


            //store player in XML
            for (int i = 0; i < model.getNumberPlayers(); i++) {
                attr.addAttribute("", "", "ID", "", String.valueOf(i));
                handler.startElement("", "", "Player", attr);

                attr.clear();
                handler.startElement("", "", "id", attr);
                String id = model.getPlayerById(i).getID() + "";
                handler.characters(id.toCharArray(), 0, id.length());
                handler.endElement("", "", "id");

                attr.clear();
                handler.startElement("", "", "name", attr);
                String value = model.getPlayerById(i).getName() + "";
                handler.characters(value.toCharArray(), 0, value.length());
                handler.endElement("", "", "name");

                attr.clear();
                handler.startElement("", "", "AI", attr);
                //String ai = model.getPlayerById(i).isAI() + "";
                //handler.characters(ai.toCharArray(), 0, ai.length());
                handler.endElement("", "", "AI");

                attr.clear();
                handler.startElement("", "", "troops", attr);
                String troops = model.getPlayerById(i).getTroops() + "";
                handler.characters(troops.toCharArray(), 0, troops.length());
                handler.endElement("", "", "troops");

                attr.clear();
                handler.startElement("", "", "ownTerritory", attr);
                for(Territory tempTerritory:model.getPlayerById(i).getTerritories()){
                    attr.clear();
                    handler.startElement("","","Territory",attr);

                    attr.clear();
                    handler.startElement("","","Name",attr);
                    handler.characters(tempTerritory.getName().toCharArray(),0,tempTerritory.getName().length());
                    handler.endElement("","","Name");

                    attr.clear();
                    handler.startElement("","","Troops",attr);
                    handler.characters(String.valueOf(tempTerritory.getTroops()).toCharArray(),0,String.valueOf(tempTerritory.getTroops()).length());
                    handler.endElement("","","Troops");

                    attr.clear();
                    handler.startElement("","","Holder",attr);
                    handler.characters(tempTerritory.getHolder().getName().toCharArray(),0,tempTerritory.getHolder().getName().length());
                    handler.endElement("","","Holder");

                    handler.endElement("","","Territory");
                }
                handler.endElement("", "", "ownTerritory");


                handler.endElement("", "", "Player");
            }

//            //store territory in XML
//            for (int i = 0; i < model.getAllCountries().size(); i++) {
//                attr.addAttribute("", "", "ID", "", String.valueOf(i));
//                handler.startElement("", "", "Territory", attr);
//
//                attr.clear();
//                handler.startElement("", "", "name", attr);
//                String name = model.getAllCountries().get(i).getName() + "";
//                handler.characters(name.toCharArray(), 0, name.length());
//                handler.endElement("", "", "name");
//
//                attr.clear();
//                handler.startElement("", "", "holder", attr);
//                String holder = model.getAllCountries().get(i).getHolder().getName() + "";
//                handler.characters(holder.toCharArray(), 0, holder.length());
//                handler.endElement("", "", "holder");
//
//                attr.clear();
//                handler.startElement("", "", "troops", attr);
//                String troops = model.getAllCountries().get(i).getTroops() + "";
//                handler.characters(troops.toCharArray(), 0, troops.length());
//                handler.endElement("", "", "troops");
//
//                handler.endElement("", "", "Territory");
//            }

            attr.clear();
            handler.startElement("", "", "Stage", attr);
            String stage = model.getCurrentStage().getName() + "";
            handler.characters(stage.toCharArray(), 0, stage.length());
            handler.endElement("", "", "Stage");

            attr.clear();
            handler.startElement("", "", "CurrentPlayer", attr);
            String currentplayer = model.getCurrentPlayer().getName() + "";
            handler.characters(currentplayer.toCharArray(), 0, currentplayer.length());
            handler.endElement("", "", "CurrentPlayer");

            handler.endElement("", "", "RISK");
            handler.endDocument();


        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
    }




    //import saved games with name
    public void importXMLFileByName(String filename) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();

        saxParser.parse(new File(filename + ".xml"), this);

    }

    public void readSAX(File f) throws Exception {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser s = spf.newSAXParser();
        s.parse(f, this);
    }

    @Override
    public void startElement(String u, String in, String qName, Attributes a) throws SAXException {

        /*super.startElement(u, in, qName, a);
        //if ("Player".equals(qName)) {
            //isPlayer = true;
       // }
        if("Player".equals(qName)){
            player = new Player("",false);
            //isPlayer = true;
        }
        else if ("Territory".equals(qName)) {
            isTerritory = true;
        } else if ("name".equals(qName)) {
            isName = true;
        } else if ("holder".equals(qName)) {
            isHolder = true;
        } else if ("troops".equals(qName)) {
            isTroops = true;
        } else if ("AI".equals(qName)) {
            isAI = true;
        } else if ("Stage".equals(qName)) {
            isStage = true;
        } else if ("ownTerritory".equals(qName)) {
            isOwnTerritory = true;
        }
        else if ("id".equals(qName)){
            isID = true;
        }
        sb = new StringBuilder();
*/
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException{


        /*if(qName.equals("Player") && isPlayer) {
            this.model.ImportPlayer(name,AI,troops,id,ownTerritory);
            isPlayer = false;
        }
        else if(qName.equals("Territory") && isTerritory) {
            this.model.ImportTerritory(name,troops,holder);
            isTerritory = false;
        }
        else if (qName.equals("Stage")&& isStage){
            this.model.setCurrentStage(stage);
            isStage = false;
        }
*/
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

        /*super.characters(ch, start, length);
        String text = new String(ch, start, length);
        if (isName) {
            name = text;
            isName = false;
        } else if (isTroops) {
            troops = Integer.parseInt(text);
            isTroops = false;
        } else if (isAI) {
            AI = Boolean.valueOf(text);
            isTroops = false;
        } else if (isOwnTerritory) {
            ownTerritory = text;
            isOwnTerritory = false;
        }
        else if(isStage) {
            stage = Stage.values()[Integer.parseInt(text)];
            isStage = false;
        }
        else if (isID) {
            id = Integer.parseInt(text);
            isID = false;
        }
        else if (isHolder){
            holder = text;
            isHolder = false;
        }*/
    }

    public RiskModel getModel() {
        return this.model;
    }

}
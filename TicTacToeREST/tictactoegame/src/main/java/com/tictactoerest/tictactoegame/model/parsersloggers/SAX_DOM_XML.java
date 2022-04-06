package com.tictactoerest.tictactoegame.model.parsersloggers;

import com.tictactoerest.tictactoegame.model.gameclasses.GameConsole;
import com.tictactoerest.tictactoegame.model.Player;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class SAX_DOM_XML implements Parser {
    @Override
    public void parse(String path) throws ParserConfigurationException, SAXException, IOException {

        Player[] p;
        GameConsole gameConsole;
        p = new Player[2];
        gameConsole = new GameConsole();

        class XMLHandler extends DefaultHandler {
            private boolean gameResult = false;
            private String lastElement = "";
            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) {
                lastElement = qName;
                if (qName.equals("Player")){
                    Player temp = new Player();
                    temp.setName(attributes.getValue("name"));
                    temp.setId(Integer.parseInt(attributes.getValue("id")));
                    temp.setMark(attributes.getValue("symbol"));
                    if  (!gameResult)
                        p[temp.getId()-1] = temp;
                    else {
                        System.out.println(p[temp.getId()-1].getName() + " as Player " + temp.getId() + "(" + temp.getMark() + ") is winner");
                    }
                }
                if (qName.equals("GameResult"))
                    gameResult = true;
            }

            @Override
            public void characters(char[] ch, int start, int length){
                String information = new String(ch, start, length);
                information = information.replace("\n", "").trim();
                if (!information.isEmpty() && lastElement.equals("Step")) {
                    gameConsole.setMark(Integer.parseInt(information));
                    gameConsole.out();
                }
                if (gameResult && information.equals("Draw!"))
                {
                    System.out.println("Draw between players " + p[0].getName() + " and " + p[1].getName());
                }

            }
        }

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLHandler handler = new XMLHandler();
        parser.parse(new File(path), handler);
    }
}

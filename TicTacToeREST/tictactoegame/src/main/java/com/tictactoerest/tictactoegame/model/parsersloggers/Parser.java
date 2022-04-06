package com.tictactoerest.tictactoegame.model.parsersloggers;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public interface Parser {
    void parse(String path) throws ParserConfigurationException, SAXException, IOException;
}
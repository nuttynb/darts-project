package hu.nutty.darts.model;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class XMLUtil {

	/**
	 * Serializes an object to XML. The output document is written in UTF-8
	 * encoding.
	 *
	 * @param o
	 *            the object to serialize
	 * @param os
	 *            the {@link OutputStream} to write to
	 * @throws JAXBException
	 *             on any error
	 */
	public static void toXML(Object o, OutputStream os) throws JAXBException {
		try {
			JAXBContext context = JAXBContext.newInstance(o.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.marshal(o, os);
		} catch (JAXBException e) {
			throw e;
		}
	}

	/**
	 * Deserializes an object from XML.
	 *
	 * @param clazz
	 *            the class of the object
	 * @param is
	 *            the {@link InputStream} to read from
	 * @return the resulting object
	 * @throws JAXBException
	 *             on any error
	 */
	public static <T> T fromXML(Class<T> clazz, InputStream is) throws JAXBException {
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return (T) unmarshaller.unmarshal(is);
		} catch (JAXBException e) {
			throw e;
		}
	}

	/*public static ObservableList<String> getExistingPlayerNames(String path) {
		ObservableList<String> playerNames = FXCollections.observableArrayList();
		File f = new File(path);
		ArrayList<String> fileNames = new ArrayList<String>(java.util.Arrays.asList(f.list()));
		for (String fileName : fileNames) {
			if (fileName.endsWith(".xml")) {
				File xmlFile = new File(path + fileName);
				DocumentBuilderFactory dBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder;
				try {
					dBuilder = dBuilderFactory.newDocumentBuilder();
					Document document;
					try {
						document = dBuilder.parse(xmlFile);
						NodeList nodeList = document.getElementsByTagName("player");
						for (int i = 0; i < nodeList.getLength(); i++) {
							Node n = nodeList.item(i);
							if (n.getNodeType() == Node.ELEMENT_NODE) {
								Element e = (Element) n;
								playerNames.add(e.getElementsByTagName("name").item(0).getTextContent());
							}
						}
					} catch (SAXException | IOException e) {
						e.printStackTrace();
					}
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				}
			}
		}
		return playerNames;
	}*/
	public static ObservableList<String> getExistingPlayerNames(String path) {
		ObservableList<String> playerNames = FXCollections.observableArrayList();
		File f = new File(path);
		ArrayList<String> fileNames = new ArrayList<String>(java.util.Arrays.asList(f.list()));
		for (String fileName : fileNames) {
			if (fileName.endsWith(".xml")) {
				playerNames.add(fileName.split("\\.")[0]);
			}
		}
		return playerNames;
	}
	
	

}
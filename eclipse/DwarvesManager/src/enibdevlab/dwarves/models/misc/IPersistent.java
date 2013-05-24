package enibdevlab.dwarves.models.misc;

import com.badlogic.gdx.utils.XmlReader.Element;

public interface IPersistent {
	
	/**
	 * Sauvegarde au format XML
	 */
	public Element saveAsXmlElement();
	
	/**
	 * Chargement de données XML
	 */
	public void loadFromXmlElement(Element xmlElement);
	
}

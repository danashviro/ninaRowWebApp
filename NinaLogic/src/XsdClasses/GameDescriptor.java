
package XsdClasses;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GameType">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Basic"/>
 *               &lt;enumeration value="MultiPlayer"/>
 *               &lt;enumeration value="DynamicMultiPlayer"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element ref="{}Game"/>
 *         &lt;element ref="{}Players" minOccurs="0"/>
 *         &lt;element ref="{}DynamicPlayers" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "gameType",
    "game",
    "players",
    "dynamicPlayers"
})
@XmlRootElement(name = "GameDescriptor")
public class GameDescriptor {

    @XmlElement(name = "GameType", required = true)
    protected String gameType;
    @XmlElement(name = "Game", required = true)
    protected Game game;
    @XmlElement(name = "Players")
    protected Players players;
    @XmlElement(name = "DynamicPlayers")
    protected DynamicPlayers dynamicPlayers;

    /**
     * Gets the value of the gameType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGameType() {
        return gameType;
    }

    /**
     * Sets the value of the gameType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGameType(String value) {
        this.gameType = value;
    }

    /**
     * Gets the value of the game property.
     * 
     * @return
     *     possible object is
     *     {@link Game }
     *     
     */
    public Game getGame() {
        return game;
    }

    /**
     * Sets the value of the game property.
     * 
     * @param value
     *     allowed object is
     *     {@link Game }
     *     
     */
    public void setGame(Game value) {
        this.game = value;
    }

    /**
     * Gets the value of the players property.
     * 
     * @return
     *     possible object is
     *     {@link Players }
     *     
     */
    public Players getPlayers() {
        return players;
    }

    /**
     * Sets the value of the players property.
     * 
     * @param value
     *     allowed object is
     *     {@link Players }
     *     
     */
    public void setPlayers(Players value) {
        this.players = value;
    }

    /**
     * Gets the value of the dynamicPlayers property.
     * 
     * @return
     *     possible object is
     *     {@link DynamicPlayers }
     *     
     */
    public DynamicPlayers getDynamicPlayers() {
        return dynamicPlayers;
    }

    /**
     * Sets the value of the dynamicPlayers property.
     * 
     * @param value
     *     allowed object is
     *     {@link DynamicPlayers }
     *     
     */
    public void setDynamicPlayers(DynamicPlayers value) {
        this.dynamicPlayers = value;
    }

}

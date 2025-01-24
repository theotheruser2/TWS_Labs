
package com.tws.lab.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for updateCar complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateCar"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="carDto" type="{http://soap.lab.tws.com/}carDto" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateCar", propOrder = {
    "id",
    "carDto"
})
public class UpdateCar {

    protected int id;
    protected CarDto carDto;

    /**
     * Gets the value of the id property.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Gets the value of the carDto property.
     * 
     * @return
     *     possible object is
     *     {@link CarDto }
     *     
     */
    public CarDto getCarDto() {
        return carDto;
    }

    /**
     * Sets the value of the carDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link CarDto }
     *     
     */
    public void setCarDto(CarDto value) {
        this.carDto = value;
    }

}

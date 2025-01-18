
package com.tws.lab.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for createCar complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createCar"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="carDto" type="{http://soap.se.ifmo.ru/}carDto" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createCar", propOrder = {
    "carDto"
})
public class CreateCar {

    protected CarDto carDto;

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

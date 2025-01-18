
package com.tws.lab.soap;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.tws.lab.soap package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SearchCars_QNAME = new QName("http://soap.lab.tws.com/", "searchCars");
    private final static QName _SearchCarsResponse_QNAME = new QName("http://soap.lab.tws.com/", "searchCarsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tws.lab.soap
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SearchCars }
     * 
     */
    public SearchCars createSearchCars() {
        return new SearchCars();
    }

    /**
     * Create an instance of {@link SearchCarsResponse }
     * 
     */
    public SearchCarsResponse createSearchCarsResponse() {
        return new SearchCarsResponse();
    }

    /**
     * Create an instance of {@link CarListRequestDto }
     * 
     */
    public CarListRequestDto createCarListRequestDto() {
        return new CarListRequestDto();
    }

    /**
     * Create an instance of {@link Car }
     * 
     */
    public Car createCar() {
        return new Car();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchCars }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SearchCars }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.lab.tws.com/", name = "searchCars")
    public JAXBElement<SearchCars> createSearchCars(SearchCars value) {
        return new JAXBElement<SearchCars>(_SearchCars_QNAME, SearchCars.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchCarsResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SearchCarsResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.lab.tws.com/", name = "searchCarsResponse")
    public JAXBElement<SearchCarsResponse> createSearchCarsResponse(SearchCarsResponse value) {
        return new JAXBElement<SearchCarsResponse>(_SearchCarsResponse_QNAME, SearchCarsResponse.class, null, value);
    }

}

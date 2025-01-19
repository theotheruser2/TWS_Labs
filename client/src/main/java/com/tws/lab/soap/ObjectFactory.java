
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

    private final static QName _CarServiceFault_QNAME = new QName("http://soap.lab.tws.com/", "CarServiceFault");
    private final static QName _CreateCar_QNAME = new QName("http://soap.lab.tws.com/", "createCar");
    private final static QName _CreateCarResponse_QNAME = new QName("http://soap.lab.tws.com/", "createCarResponse");
    private final static QName _DeleteCarById_QNAME = new QName("http://soap.lab.tws.com/", "deleteCarById");
    private final static QName _DeleteCarByIdResponse_QNAME = new QName("http://soap.lab.tws.com/", "deleteCarByIdResponse");
    private final static QName _FindCarById_QNAME = new QName("http://soap.lab.tws.com/", "findCarById");
    private final static QName _FindCarByIdResponse_QNAME = new QName("http://soap.lab.tws.com/", "findCarByIdResponse");
    private final static QName _SearchCars_QNAME = new QName("http://soap.lab.tws.com/", "searchCars");
    private final static QName _SearchCarsResponse_QNAME = new QName("http://soap.lab.tws.com/", "searchCarsResponse");
    private final static QName _UpdateCar_QNAME = new QName("http://soap.lab.tws.com/", "updateCar");
    private final static QName _UpdateCarResponse_QNAME = new QName("http://soap.lab.tws.com/", "updateCarResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tws.lab.soap
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CarCrudException }
     * 
     */
    public CarCrudException createCarCrudException() {
        return new CarCrudException();
    }

    /**
     * Create an instance of {@link CreateCar }
     * 
     */
    public CreateCar createCreateCar() {
        return new CreateCar();
    }

    /**
     * Create an instance of {@link CreateCarResponse }
     * 
     */
    public CreateCarResponse createCreateCarResponse() {
        return new CreateCarResponse();
    }

    /**
     * Create an instance of {@link DeleteCarById }
     * 
     */
    public DeleteCarById createDeleteCarById() {
        return new DeleteCarById();
    }

    /**
     * Create an instance of {@link DeleteCarByIdResponse }
     * 
     */
    public DeleteCarByIdResponse createDeleteCarByIdResponse() {
        return new DeleteCarByIdResponse();
    }

    /**
     * Create an instance of {@link FindCarById }
     * 
     */
    public FindCarById createFindCarById() {
        return new FindCarById();
    }

    /**
     * Create an instance of {@link FindCarByIdResponse }
     * 
     */
    public FindCarByIdResponse createFindCarByIdResponse() {
        return new FindCarByIdResponse();
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
     * Create an instance of {@link UpdateCar }
     * 
     */
    public UpdateCar createUpdateCar() {
        return new UpdateCar();
    }

    /**
     * Create an instance of {@link UpdateCarResponse }
     * 
     */
    public UpdateCarResponse createUpdateCarResponse() {
        return new UpdateCarResponse();
    }

    /**
     * Create an instance of {@link Car }
     * 
     */
    public Car createCar() {
        return new Car();
    }

    /**
     * Create an instance of {@link CarDto }
     * 
     */
    public CarDto createCarDto() {
        return new CarDto();
    }

    /**
     * Create an instance of {@link ErrorBean }
     * 
     */
    public ErrorBean createErrorBean() {
        return new ErrorBean();
    }

    /**
     * Create an instance of {@link CarListRequestDto }
     * 
     */
    public CarListRequestDto createCarListRequestDto() {
        return new CarListRequestDto();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CarCrudException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CarCrudException }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.lab.tws.com/", name = "CarServiceFault")
    public JAXBElement<CarCrudException> createCarServiceFault(CarCrudException value) {
        return new JAXBElement<CarCrudException>(_CarServiceFault_QNAME, CarCrudException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateCar }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CreateCar }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.lab.tws.com/", name = "createCar")
    public JAXBElement<CreateCar> createCreateCar(CreateCar value) {
        return new JAXBElement<CreateCar>(_CreateCar_QNAME, CreateCar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateCarResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CreateCarResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.lab.tws.com/", name = "createCarResponse")
    public JAXBElement<CreateCarResponse> createCreateCarResponse(CreateCarResponse value) {
        return new JAXBElement<CreateCarResponse>(_CreateCarResponse_QNAME, CreateCarResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteCarById }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DeleteCarById }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.lab.tws.com/", name = "deleteCarById")
    public JAXBElement<DeleteCarById> createDeleteCarById(DeleteCarById value) {
        return new JAXBElement<DeleteCarById>(_DeleteCarById_QNAME, DeleteCarById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteCarByIdResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DeleteCarByIdResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.lab.tws.com/", name = "deleteCarByIdResponse")
    public JAXBElement<DeleteCarByIdResponse> createDeleteCarByIdResponse(DeleteCarByIdResponse value) {
        return new JAXBElement<DeleteCarByIdResponse>(_DeleteCarByIdResponse_QNAME, DeleteCarByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindCarById }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FindCarById }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.lab.tws.com/", name = "findCarById")
    public JAXBElement<FindCarById> createFindCarById(FindCarById value) {
        return new JAXBElement<FindCarById>(_FindCarById_QNAME, FindCarById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindCarByIdResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FindCarByIdResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.lab.tws.com/", name = "findCarByIdResponse")
    public JAXBElement<FindCarByIdResponse> createFindCarByIdResponse(FindCarByIdResponse value) {
        return new JAXBElement<FindCarByIdResponse>(_FindCarByIdResponse_QNAME, FindCarByIdResponse.class, null, value);
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

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCar }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UpdateCar }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.lab.tws.com/", name = "updateCar")
    public JAXBElement<UpdateCar> createUpdateCar(UpdateCar value) {
        return new JAXBElement<UpdateCar>(_UpdateCar_QNAME, UpdateCar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCarResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UpdateCarResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.lab.tws.com/", name = "updateCarResponse")
    public JAXBElement<UpdateCarResponse> createUpdateCarResponse(UpdateCarResponse value) {
        return new JAXBElement<UpdateCarResponse>(_UpdateCarResponse_QNAME, UpdateCarResponse.class, null, value);
    }

}

package com.tws.lab.service;

import org.apache.juddi.api_v3.AccessPointType;
import org.apache.juddi.v3.client.UDDIConstants;
import org.apache.juddi.v3.client.config.UDDIClient;
import org.apache.juddi.v3.client.transport.Transport;
import org.uddi.api_v3.*;
import org.uddi.v3_service.UDDIInquiryPortType;
import org.uddi.v3_service.UDDIPublicationPortType;
import org.uddi.v3_service.UDDISecurityPortType;

import java.util.List;
import java.util.UUID;

public class JuddiService {
    private final UDDISecurityPortType security;
    private final UDDIInquiryPortType inquiry;
    private final UDDIPublicationPortType publish;
    private final String baseUrl;
    private static final String PARTITION = "tws.lab";
    private static final String ROOT_PARTITION = "uddi:" + PARTITION + ":keygenerator";
    private static final String BUSINESS_KEY = "uddi:" + PARTITION + ":business-key";

    public JuddiService(String juddiUrl) {
        this.baseUrl = juddiUrl;
        try {
            System.out.println("Инициализация UDDI клиента с базовым URL: " + juddiUrl);
            
            // Initialize the UDDI client
            UDDIClient client = new UDDIClient("META-INF/uddi.xml");
            
            // Get the transport
            Transport transport = client.getTransport("default");
            
            // Get the service endpoints
            security = transport.getUDDISecurityService();
            inquiry = transport.getUDDIInquiryService();
            publish = transport.getUDDIPublishService();
            
            System.out.println("UDDI клиент успешно инициализирован");
        } catch (Exception e) {
            System.err.println("Ошибка при инициализации UDDI клиента:");
            e.printStackTrace();
            throw new RuntimeException("Ошибка при инициализации jUDDI клиента: " + e.getMessage(), e);
        }
    }

    private String getAuthToken() {
        try {
            GetAuthToken getAuthToken = new GetAuthToken();
            getAuthToken.setUserID("uddiadmin");
            getAuthToken.setCred("da_password1");
            return security.getAuthToken(getAuthToken).getAuthInfo();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении токена авторизации: " + e.getMessage(), e);
        }
    }

    private void createKeyGenerator(String authToken) {
        try {
            // Check if key generator exists
            try {
                GetTModelDetail getTModel = new GetTModelDetail();
                getTModel.setAuthInfo(authToken);
                getTModel.getTModelKey().add(ROOT_PARTITION);
                inquiry.getTModelDetail(getTModel);
                System.out.println("Генератор ключей уже существует");
                return;
            } catch (Exception e) {
                System.out.println("Создание нового генератора ключей");
            }

            // Create key generator tModel
            TModel keyGenerator = new TModel();
            keyGenerator.setTModelKey(ROOT_PARTITION);
            Name name = new Name();
            name.setValue("TWS Lab Key Generator");
            keyGenerator.setName(name);
            
            CategoryBag categoryBag = new CategoryBag();
            KeyedReference keyedReference = new KeyedReference();
            keyedReference.setTModelKey("uddi:uddi.org:categorization:types");
            keyedReference.setKeyName("uddi-org:types:keyGenerator");
            keyedReference.setKeyValue("keyGenerator");
            categoryBag.getKeyedReference().add(keyedReference);
            keyGenerator.setCategoryBag(categoryBag);

            // Save the key generator
            SaveTModel saveTModel = new SaveTModel();
            saveTModel.setAuthInfo(authToken);
            saveTModel.getTModel().add(keyGenerator);
            publish.saveTModel(saveTModel);
            System.out.println("Генератор ключей успешно создан");
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании генератора ключей: " + e.getMessage(), e);
        }
    }

    private void createBusinessEntity(String businessKey, String authToken) {
        try {
            // Create key generator first
            createKeyGenerator(authToken);

            // Check if business already exists
            GetBusinessDetail gbd = new GetBusinessDetail();
            gbd.getBusinessKey().add(businessKey);
            
            try {
                inquiry.getBusinessDetail(gbd);
                System.out.println("Бизнес-сущность уже существует с ключом: " + businessKey);
                return;
            } catch (Exception e) {
                System.out.println("Создание новой бизнес-сущности с ключом: " + businessKey);
            }

            // Create a new business entity
            BusinessEntity myBusEntity = new BusinessEntity();
            myBusEntity.setBusinessKey(businessKey);
            Name myBusName = new Name();
            myBusName.setValue("TWS Repository");
            myBusEntity.getName().add(myBusName);

            // Save the business entity
            SaveBusiness sb = new SaveBusiness();
            sb.setAuthInfo(authToken);
            sb.getBusinessEntity().add(myBusEntity);
            publish.saveBusiness(sb);
            System.out.println("Бизнес-сущность успешно создана");
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании бизнес-сущности: " + e.getMessage(), e);
        }
    }

    public void registerService(String serviceUrl, String serviceName) {
        String authToken = getAuthToken();
        createBusinessEntity(BUSINESS_KEY, authToken);

        try {
            System.out.println("Регистрация сервиса: " + serviceName);
            System.out.println("URL сервиса: " + serviceUrl);
            
            // Create a new business service
            BusinessService myService = new BusinessService();
            myService.setBusinessKey(BUSINESS_KEY);
            Name myServiceName = new Name();
            myServiceName.setValue(serviceName);
            myService.getName().add(myServiceName);

            // Generate service key
            String serviceKey = "uddi:" + PARTITION + ":service-" + UUID.randomUUID().toString();
            myService.setServiceKey(serviceKey);

            // Add binding template with unique key
            BindingTemplate myBindingTemplate = new BindingTemplate();
            String bindingKey = "uddi:" + PARTITION + ":binding-" + UUID.randomUUID().toString();
            myBindingTemplate.setBindingKey(bindingKey);
            AccessPoint accessPoint = new AccessPoint();
            accessPoint.setUseType(AccessPointType.WSDL_DEPLOYMENT.toString());
            accessPoint.setValue(serviceUrl);
            myBindingTemplate.setAccessPoint(accessPoint);
            
            BindingTemplates myBindingTemplates = new BindingTemplates();
            myBindingTemplates.getBindingTemplate().add(myBindingTemplate);
            myService.setBindingTemplates(myBindingTemplates);

            // Save the service
            SaveService ss = new SaveService();
            ss.setAuthInfo(authToken);
            ss.getBusinessService().add(myService);
            publish.saveService(ss);
            System.out.println("Сервис успешно зарегистрирован");
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при регистрации сервиса: " + e.getMessage(), e);
        }
    }

    public String findService(String serviceName) {
        try {
            System.out.println("Поиск сервиса: " + serviceName);
            
            // Create a find_service object with the service name
            FindService fs = new FindService();
            Name searchName = new Name();
            searchName.setValue(serviceName);
            fs.getName().add(searchName);
            fs.setFindQualifiers(new FindQualifiers());
            fs.getFindQualifiers().getFindQualifier().add(UDDIConstants.EXACT_MATCH);

            // Find the service
            ServiceList serviceList = inquiry.findService(fs);
            if (serviceList.getServiceInfos() == null || 
                serviceList.getServiceInfos().getServiceInfo().isEmpty()) {
                System.out.println("Сервис не найден");
                return null;
            }

            // Get service key
            String serviceKey = serviceList.getServiceInfos().getServiceInfo().get(0).getServiceKey();
            System.out.println("Найден сервис с ключом: " + serviceKey);

            // Get service details
            GetServiceDetail gsd = new GetServiceDetail();
            gsd.getServiceKey().add(serviceKey);
            ServiceDetail serviceDetail = inquiry.getServiceDetail(gsd);

            // Get access point URL
            List<BusinessService> services = serviceDetail.getBusinessService();
            if (!services.isEmpty() && services.get(0).getBindingTemplates() != null) {
                List<BindingTemplate> bindingTemplates = services.get(0).getBindingTemplates().getBindingTemplate();
                if (!bindingTemplates.isEmpty() && bindingTemplates.get(0).getAccessPoint() != null) {
                    String url = bindingTemplates.get(0).getAccessPoint().getValue();
                    System.out.println("Найден URL сервиса: " + url);
                    return url;
                }
            }
            System.out.println("URL сервиса не найден");
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при поиске сервиса: " + e.getMessage(), e);
        }
    }
} 
package com.tws.lab.command;

import com.tws.lab.service.JuddiService;
import com.tws.lab.soap.Car;
import com.tws.lab.soap.CarWebService;
import com.tws.lab.soap.CarService;
import com.tws.lab.soap.CarListRequestDto;
import com.tws.lab.utils.Util;
import com.tws.lab.utils.CarFormatter;

import javax.xml.namespace.QName;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class FindServiceCommand implements CliCommand {
    private final JuddiService juddiService;

    public FindServiceCommand(JuddiService juddiService) {
        this.juddiService = juddiService;
    }

    @Override
    public String getName() {
        return "find";
    }

    @Override
    public String getDescription() {
        return "Поиск сервиса в реестре jUDDI";
    }

    @Override
    public void execute(Scanner scanner) {
        try {
            System.out.print("Введите имя сервиса для поиска (например, CarService): ");
            String serviceName = scanner.nextLine().trim();

            String serviceUrl = juddiService.findService(serviceName);
            if (serviceUrl == null) {
                System.out.println("Сервис не найден в реестре jUDDI");
                return;
            }

            System.out.println("Сервис найден! URL: " + serviceUrl);
            CarWebService carService = createCarWebService(serviceUrl);

            while (true) {
                System.out.println("\nДоступные команды:");
                System.out.println("1) help - показать список команд");
                System.out.println("2) exit - выйти");
                System.out.println("3) search - поиск автомобилей");
                System.out.println("4) findById - поиск автомобиля по ID");
                System.out.println("5) create - создание записи об автомобиле");
                System.out.println("6) update - обновление записи об автомобиле");
                System.out.println("7) delete - удаление записи об автомобиле");

                System.out.print("\nВведите команду: ");
                String command = scanner.nextLine().trim().toLowerCase();

                switch (command) {
                    case "help":
                        continue;
                    case "exit":
                        return;
                    case "search":
                        handleSearch(scanner, carService);
                        break;
                    case "findbyid":
                        handleFindById(scanner, carService);
                        break;
                    case "create":
                        new CreateCarCommand(carService).execute(scanner);
                        break;
                    case "update":
                        new UpdateCarCommand(carService).execute(scanner);
                        break;
                    case "delete":
                        new DeleteCarCommand(carService).execute(scanner);
                        break;
                    default:
                        System.out.println("Неизвестная команда. Используйте 'help' для списка команд.");
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка при поиске сервиса: " + e.getMessage());
        }
    }

    private CarWebService createCarWebService(String wsdlUrl) throws Exception {
        URL url = new URL(wsdlUrl);
        QName qname = new QName("http://soap.lab.tws.com/", "CarService");
        CarService service = new CarService(url, qname);
        return service.getCarWebServicePort();
    }

    private void handleSearch(Scanner scanner, CarWebService carService) {
        try {
            System.out.print("Введите строку поиска (например, 'brand = Toyota'): ");
            String searchString = scanner.nextLine().trim();

            int limit = Util.getIntInput(scanner, "Введите лимит (по умолчанию 5): ", 5);
            int offset = Util.getIntInput(scanner, "Введите смещение (по умолчанию 0): ", 0);

            CarListRequestDto requestDto = new CarListRequestDto();
            requestDto.setQuery(searchString);
            requestDto.setLimit(limit);
            requestDto.setOffset(offset);

            List<Car> cars = carService.searchCars(requestDto);
            if (cars.isEmpty()) {
                System.out.println("Автомобили не найдены");
            } else {
                System.out.println("\nНайденные автомобили:");
                System.out.println(CarFormatter.getTableHeader());
                System.out.println(CarFormatter.getTableSeparator());
                cars.forEach(car -> System.out.println(CarFormatter.formatCarAsTableRow(car)));
            }
        } catch (Exception e) {
            System.err.println("Ошибка при поиске автомобилей: " + e.getMessage());
        }
    }

    private void handleFindById(Scanner scanner, CarWebService carService) {
        try {
            System.out.print("Введите ID автомобиля: ");
            String input = scanner.nextLine().trim();
            
            try {
                int id = Integer.parseInt(input);
                Car car = carService.findCarById(id);
                
                if (car != null) {
                    System.out.println("\nНайденный автомобиль:");
                    System.out.println(CarFormatter.getTableHeader());
                    System.out.println(CarFormatter.getTableSeparator());
                    System.out.println(CarFormatter.formatCarAsTableRow(car));
                } else {
                    System.out.println("Автомобиль с ID " + id + " не найден");
                }
            } catch (NumberFormatException e) {
                System.err.println("Ошибка: ID должен быть числом");
            }
        } catch (Exception e) {
            System.err.println("Ошибка при поиске автомобиля: " + e.getMessage());
        }
    }
} 
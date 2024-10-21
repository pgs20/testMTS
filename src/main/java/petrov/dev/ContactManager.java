package petrov.dev;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

public class ContactManager {
    private List<Contact> contacts;
    private static final String FILE_NAME = "contacts.txt";

    public ContactManager() {
        contacts = new ArrayList<>();
        loadContacts();
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void addContact(String firstName, String lastName, String accessCode) {
        contacts.add(new Contact(firstName, lastName, accessCode));
    }

    public void viewContacts() {
        contacts.forEach(System.out::println);
    }

    public void viewAccessCodes() {
        contacts.forEach(contact -> System.out.println(contact.getAccessCode()));
    }

    public Contact searchContact(String accessCode) {
        return contacts.stream()
                .filter(contact -> contact.getAccessCode().equals(accessCode))
                .findFirst()
                .orElse(null);
    }

    public boolean deleteContact(String accessCode) {
        return contacts.removeIf(contact -> contact.getAccessCode().equals(accessCode));
    }

    public void saveContacts() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(contacts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadContacts() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            contacts = (List<Contact>) in.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("На данный момент контактов нет.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        StringBuilder availableCommands = new StringBuilder();

        //Добавление команд и их обработчиков
        Map<String, Consumer<Void>> commands = Map.of(
                "add", v -> addNewContact(scanner),
                "view", v -> viewContacts(),
                "viewcodes", v -> viewAccessCodes(),
                "search", v -> searchExistingContact(scanner),
                "delete", v -> deleteExistingContact(scanner)
        );

        for (String command : commands.keySet()) {
            availableCommands.append(command + "/");
        }

        while (true) {
            System.out.println("Введите команду (" + availableCommands +  "exit): ");
            String command = scanner.nextLine().toLowerCase();

            if ("exit".equals(command)) {
                saveContacts();
                System.out.println("Выход из программы.");
                return;
            }

            Consumer<Void> action = commands.get(command);
            if (action != null) {
                action.accept(null);
            } else {
                System.out.println("Неизвестная команда.");
            }
        }
    }

    private void addNewContact(Scanner scanner) {
        System.out.println("Введите имя: ");
        String firstName = scanner.nextLine();
        System.out.println("Введите фамилию: ");
        String lastName = scanner.nextLine();
        System.out.println("Введите номер пропуска (8 символов 0-9A-F): ");
        String accessCode = scanner.nextLine();
        try {
            addContact(firstName, lastName, accessCode);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void searchExistingContact(Scanner scanner) {
        System.out.println("Введите номер пропуска для поиска: ");
        String accessCode = scanner.nextLine();
        Contact contact = searchContact(accessCode);
        if (contact != null) {
            System.out.println("Найден контакт: " + contact);
        } else {
            System.out.println("Контакт не найден.");
        }
    }

    private void deleteExistingContact(Scanner scanner) {
        System.out.println("Введите номер пропуска для удаления: ");
        String accessCode = scanner.nextLine();
        if (deleteContact(accessCode)) {
            System.out.println("Контакт удален.");
        } else {
            System.out.println("Контакт не найден.");
        }
    }
}

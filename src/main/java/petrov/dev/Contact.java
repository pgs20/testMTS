package petrov.dev;

import java.io.Serializable;

public class Contact implements Serializable {
    private String firstName;
    private String lastName;
    private String accessCode;

    public Contact(String firstName, String lastName, String accessCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        setAccessCode(accessCode);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        if (!isValidAccessCode(accessCode)) {
            throw new IllegalArgumentException("Invalid access code format.");
        }
        this.accessCode = accessCode;
    }

    // Проверка корректности пароля
    private boolean isValidAccessCode(String accessCode) {
        return accessCode.matches("[0-9A-F]{8}");
    }

    @Override
    public String toString() {
        return "Contact{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", accessCode='" + accessCode + '\'' +
                '}';
    }
}
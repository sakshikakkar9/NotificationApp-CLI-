package notificationApp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationUtils {
    private static final String FILE_NAME = "notifications.dat";

    @SuppressWarnings("unchecked")
    public static List<Notification> loadNotifications() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Notification>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void saveNotifications(List<Notification> notifications) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(notifications);
        } catch (IOException e) {
            System.out.println("Error saving notifications.");
        }
    }
}


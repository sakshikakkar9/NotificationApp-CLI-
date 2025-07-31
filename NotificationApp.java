package notificationApp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class NotificationApp {
    private static List<Notification> notifications = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        notifications = NotificationUtils.loadNotifications();
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            public void run() {
                checkAndSendNotifications();
            }

        }, 0, 5000); // Check every 5 seconds

        while (true) {
            System.out.println("\n--- Notification App ---");
            System.out.println("1. Create Notification");
            System.out.println("2. View Pending Notifications");
            System.out.println("3. View Sent Notifications");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> createNotification();
                case 2 -> viewNotifications(false);
                case 3 -> viewNotifications(true);
                case 4 -> {
                    NotificationUtils.saveNotifications(notifications);
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void createNotification() {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();

        System.out.print("Enter message: ");
        String message = scanner.nextLine();

        System.out.print("Enter scheduled time (yyyy-MM-dd HH:mm:ss) or leave blank for now: ");
        String timeInput = scanner.nextLine();
        LocalDateTime scheduledTime = null;
        if (!timeInput.isBlank()) {
            try {
                scheduledTime = LocalDateTime.parse(timeInput, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            } catch (Exception e) {
                System.out.println("Invalid date format. Notification set to immediate.");
            }
        }

        Notification notif = new Notification(title, message, scheduledTime);
        notifications.add(notif);
        NotificationUtils.saveNotifications(notifications);
        System.out.println("Notification created!");
    }

    private static void viewNotifications(boolean sentStatus) {
        System.out.println(sentStatus ? "\n--- Sent Notifications ---" : "\n--- Pending Notifications ---");
        for (Notification notif : notifications) {
            if (notif.isSent() == sentStatus) {
                System.out.println(notif);
            }
        }
    }

    private static void checkAndSendNotifications() {
        LocalDateTime now = LocalDateTime.now();
        for (Notification notif : notifications) {
            if (!notif.isSent()) {
                if (notif.getScheduledTime() == null || !notif.getScheduledTime().isAfter(now)) {
                    System.out.println("\n🔔 Notification:");
                    System.out.println("Title: " + notif.getTitle());
                    System.out.println("Message: " + notif.getMessage());
                    notif.markAsSent();
                }
            }
        }
        NotificationUtils.saveNotifications(notifications);
    }
}


package notificationApp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Notification implements Serializable {
    private String title;
    private String message;
    private LocalDateTime scheduledTime;
    private boolean isSent;

    public Notification(String title, String message, LocalDateTime scheduledTime) {
        this.title = title;
        this.message = message;
        this.scheduledTime = scheduledTime;
        this.isSent = false;
    }

    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public LocalDateTime getScheduledTime() { return scheduledTime; }
    public boolean isSent() { return isSent; }

    public void markAsSent() { this.isSent = true; }

    @Override
    public String toString() {
        String timeStr = (scheduledTime != null)
                ? scheduledTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                : "Immediate";
        return "[Title: " + title + ", Message: " + message + ", Time: " + timeStr + ", Sent: " + isSent + "]";
    }
}


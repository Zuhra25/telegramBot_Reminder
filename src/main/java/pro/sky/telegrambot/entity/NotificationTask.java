package pro.sky.telegrambot.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class NotificationTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long chatId;
    private String task;
    private LocalDateTime taskTime;

    public NotificationTask(Long chatId, String task, LocalDateTime taskTime) {
        this.chatId = chatId;
        this.task = task;
        this.taskTime = taskTime;
    }

    public NotificationTask() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getNotificationMessage() {
        return task;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.task = notificationMessage;
    }

    public LocalDateTime getNotificationTime() {
        return taskTime;
    }

    public void setNotificationTime(LocalDateTime notificationTime) {
        this.taskTime = notificationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return Objects.equals(id, that.id) && Objects.equals(chatId, that.chatId) && Objects.equals(task, that.task) && Objects.equals(taskTime, that.taskTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, task, taskTime);
    }

    @Override
    public String toString() {
        return "NotificationTask{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", notificationMessage='" + task + '\'' +
                ", notificationTime=" + taskTime +
                '}';
    }
}
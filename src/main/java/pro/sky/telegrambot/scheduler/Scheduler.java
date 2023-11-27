package pro.sky.telegrambot.scheduler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.entity.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class Scheduler {
    private final NotificationTaskRepository repository;
    private  final TelegramBot telegramBot;

    public Scheduler(NotificationTaskRepository repository, TelegramBot telegramBot) {
        this.repository = repository;
        this.telegramBot = telegramBot;
    }
    @Scheduled(cron = "0 0/1 * * * *")
    public void findNotificationFromDb() {
        LocalDateTime dateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        List<NotificationTask> tasks = repository.findByTaskTime(dateTime);
        tasks.forEach(task -> {
            SendMessage sendNotification = new SendMessage(
                    task.getChatId(),"напоминаю:\n" +task.getNotificationMessage());
            telegramBot.execute(sendNotification);
        });

    }


}

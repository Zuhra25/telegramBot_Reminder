package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    //private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    @Autowired
    private NotificationTaskRepository repository;

    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);

            Long chatId = update.message().chat().id();
            String messageText = update.message().text();
            String nickName = update.message().chat().firstName();


            if (messageText.equals("/start")) {
                SendMessage message = new SendMessage(chatId, String.format(
                        "Добро пожаловать в бот-напоминалку!\n" +
                                "%s, введи запрос в формате:\n" +
                                "дд.мм.гггг ЧЧ:ММ напомни о встрече с мамой",
                        nickName));
                SendResponse response = telegramBot.execute(message);
            }

            Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");
            Matcher matcher = pattern.matcher(update.message().text());
            String date = null;
            String item = null;

            if (matcher.matches()) {
                date = matcher.group(1);
                item = matcher.group(3);
                logger.info("Date: {}, item: {}", date, item);
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            if (date != null) {
                LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
                repository.save(new NotificationTask(chatId, item, dateTime));
                SendMessage message = new SendMessage(chatId, "Событие сохранено!");
                telegramBot.execute(message);
            }

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}


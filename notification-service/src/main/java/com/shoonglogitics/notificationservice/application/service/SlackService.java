package com.shoonglogitics.notificationservice.application.service;

import static com.slack.api.webhook.WebhookPayloads.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.slack.api.Slack;
import com.slack.api.model.Attachment;
import com.slack.api.model.Field;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SlackService {

	private final String webhook;
	private final Slack slack;

	public SlackService(@Value("${slack.api.webhook}") String webhook) {
		this.webhook = webhook;
		this.slack = Slack.getInstance();
	}

	public void sendMessage(String requestUrl, String message) {
		try {
			slack.send(webhook, payload(p -> p
				.text(":rotating_light: *알립 전송 완료*")
				.attachments(List.of(
					createAttachment(requestUrl, message)
				))
			));
		} catch (Exception e) {
			log.warn("Slack API를 호출하지 못했습니다.");
		}
	}

	private Attachment createAttachment(String requestUrl, String message) {
		ZonedDateTime seoulTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
		String requestTime = seoulTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		return Attachment.builder()
			.color("#ff0000")
			.fields(List.of(
				createField("RequestTime", requestTime),
				createField("RequestURL", requestUrl),
				createField("Message", message)
			))
			.build();
	}

	private Field createField(String title, String value) {
		return Field.builder()
			.title(title)
			.value(value)
			.valueShortEnough(false)
			.build();
	}

}

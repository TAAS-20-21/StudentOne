package com.gruppo13.ChatMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChatMsApplication {

	static final String topicExchangeNameCourse = "{exchange.course-create}";
	static final String topicExchangeNameUser = "{exchange.user-create}";
	static final String topicExchangeCourseUserCreate = "{exchange.course-user-create}";
	static final String topicExchangeCourseUserDelete = "{exchange.course-user-delete}";

	static final String queueNameCourse = "{queue.course-create}";
	static final String queueNameUser = "{queue.user-create}";
	static final String queueCourseUserCreated = "{queue.course-user-create}";
	static final String queueCourseUserDeleted = "{queue.course-user-delete}";

	public static void main(String[] args) {
		SpringApplication.run(ChatMsApplication.class, args);
	}

	public static String getTopicExchangeNameCourse() {
		return topicExchangeNameCourse;
	}

	public static String getTopicExchangeNameUser() {
		return topicExchangeNameUser;
	}

	public static String getTopicExchangeCourseUserCreate() {
		return topicExchangeCourseUserCreate;
	}

	public static String getTopicExchangeCourseUserDelete() {
		return topicExchangeCourseUserDelete;
	}

	public static String getQueueNameCourse() {
		return queueNameCourse;
	}

	public static String getQueueNameUser() {
		return queueNameUser;
	}

	public static String getQueueCourseUserCreated() {
		return queueCourseUserCreated;
	}

	public static String getQueueCourseUserDeleted() {
		return queueCourseUserDeleted;
	}
}

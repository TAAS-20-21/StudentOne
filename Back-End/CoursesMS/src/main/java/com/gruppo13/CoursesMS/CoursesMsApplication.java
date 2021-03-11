package com.gruppo13.CoursesMS;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.gruppo13.CoursesMS")
@EnableJpaRepositories
@EnableTransactionManagement
public class CoursesMsApplication extends SpringBootServletInitializer {

	static final String topicExchangeNameCourse = "{exchange.course-create}";
	static final String topicExchangeNameUser = "{exchange.user-create}";
	static final String topicExchangeCourseUserCreate = "{exchange.course-user-create}";
	static final String topicExchangeCourseUserDelete = "{exchange.course-user-delete}";

	static final String queueNameCourse = "{queue.course-create}";
	static final String queueNameUser = "{queue.user-create}";
	static final String queueCourseUserCreated = "{queue.course-user-create}";
	static final String queueCourseUserDeleted = "{queue.course-user-delete}";

	public static void main(String[] args) {
		SpringApplicationBuilder app = new SpringApplicationBuilder(CoursesMsApplication.class);
		app.run();
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CoursesMsApplication.class);
	}

	@Bean
	public Queue myQueueCourse() {
		return new Queue(queueNameCourse, false);
	}

	@Bean
	TopicExchange exchangeCourse() {
		return new TopicExchange(topicExchangeNameCourse);
	}

	@Bean
	Binding bindingCourse(@Qualifier("myQueueCourse") Queue queue, @Qualifier("exchangeCourse") TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("course.create.#");
	}

	@Bean
	SimpleMessageListenerContainer containerCourse(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueNameCourse);
		return container;
	}

	public static String getTopicExchangeNameCourse() {
		return topicExchangeNameCourse;
	}

	public static String getTopicExchangeNameUser() {
		return topicExchangeNameUser;
	}

	public static String getQueueNameCourse() {
		return queueNameCourse;
	}

	public static String getQueueNameUser() {
		return queueNameUser;
	}

	public static String getTopicExchangeCourseUserCreate() {
		return topicExchangeCourseUserCreate;
	}

	public static String getQueueCourseUserCreated() {
		return queueCourseUserCreated;
	}

	public static String getTopicExchangeCourseUserDelete() {
		return topicExchangeCourseUserDelete;
	}

	public static String getQueueCourseUserDeleted() {
		return queueCourseUserDeleted;
	}

	@Bean
	public Queue myQueueCourseUserDelete() {
		return new Queue(queueCourseUserDeleted, false);
	}

	@Bean
	TopicExchange exchangeCourseUserDelete() {
		return new TopicExchange(topicExchangeCourseUserDelete);
	}

	@Bean
	Binding bindingCourseUserDelete(@Qualifier("myQueueCourseUserDelete") Queue queue,
									@Qualifier("exchangeCourseUserDelete") TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("course-user.delete.#");
	}

	@Bean
	SimpleMessageListenerContainer containerUserDelete(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueCourseUserDeleted);
		return container;
	}

	@Bean
	public Queue myQueueCourseUserCreate() {
		return new Queue(queueCourseUserCreated, false);
	}

	@Bean
	TopicExchange exchangeCourseUserCreate() {
		return new TopicExchange(topicExchangeCourseUserCreate);
	}

	@Bean
	Binding bindingCourseUserCreate(@Qualifier("myQueueCourseUserCreate") Queue queue,
									@Qualifier("exchangeCourseUserCreate") TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("course-user.create.#");
	}

	@Bean
	SimpleMessageListenerContainer containerUserCreate(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueCourseUserCreated);
		return container;
	}
}

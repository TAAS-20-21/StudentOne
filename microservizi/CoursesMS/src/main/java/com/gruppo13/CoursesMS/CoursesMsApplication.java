package com.gruppo13.CoursesMS;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
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

	static final String queueNameCourse = "{queue.course-create}";
	static final String queueNameUser = "{queue.user-create}";

	public static void main(String[] args) {
		SpringApplicationBuilder app = new SpringApplicationBuilder(CoursesMsApplication.class);
		app.run();
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CoursesMsApplication.class);
	}

	@Bean
	public Queue myQueue() {
		return new Queue(queueNameCourse, false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(topicExchangeNameCourse);
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("course.create.#");
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
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
}

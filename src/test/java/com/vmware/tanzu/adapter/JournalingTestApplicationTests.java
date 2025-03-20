package com.vmware.tanzu.adapter;

import com.vmware.tanzu.ai.journal.JournalService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.tool.method.MethodToolCallback;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JournalingTestApplicationTests {

	@Autowired
	private ChatClient.Builder chatClientBuilder;

	@Autowired
	private JournalService journalService;

	@Test
	void contextLoads() {
		assertThat(chatClientBuilder).isNotNull();
	}

	@BeforeEach
	void setUp() {
		journalService.deleteEntries(Instant.EPOCH, Instant.now());
	}

	@Test
	void canExecuteRequest() {
		assertThat(chatClientBuilder).isNotNull();
		assertThat(journalService).isNotNull();

		ChatClient client = chatClientBuilder
				.defaultOptions(
						ToolCallingChatOptions.
								builder().
								model("gpt-4o-mini").
								build())
				.defaultAdvisors(new JournalServiceAdvisor(journalService))
				.build();

		String response = client
				.prompt("tell me a joke?")
				.call()
				.content();
		assertThat(response).isNotNull();

		System.out.println("\n\n");
		System.out.println(response);
		System.out.println("\n\n");
	}
}

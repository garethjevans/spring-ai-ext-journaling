package com.vmware.tanzu.adapter;

import com.vmware.tanzu.ai.journal.JournalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.*;

import java.util.Map;

public class JournalServiceAdvisor implements CallAdvisor {

    private static final Logger LOGGER = LoggerFactory.getLogger(JournalServiceAdvisor.class);

    private final JournalService journalService;

    public JournalServiceAdvisor(JournalService journalService) {
        this.journalService = journalService;
    }

    @Override
    public String getName() {
        return "journal-service-advisor";
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {
        LOGGER.info(">>> request: {}", request);

        String journalId = journalService.initializeEntry(Map.of());
        String model = request.prompt().getOptions().getModel();

        journalService.journalRequest(journalId, model, request.prompt().getContents());

        ChatClientResponse response = chain.nextCall(request);
        LOGGER.info("<<< response: {}", response);

        journalService.journalResponse(journalId, model, response.chatResponse().getResult().getOutput().getText());

        return response;
    }
}

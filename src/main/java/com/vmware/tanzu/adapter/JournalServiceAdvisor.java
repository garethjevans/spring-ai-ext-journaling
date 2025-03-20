package com.vmware.tanzu.adapter;

import com.vmware.tanzu.ai.journal.JournalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.advisor.api.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class JournalServiceAdvisor implements CallAroundAdvisor {

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
    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
        LOGGER.info(">>> request: {}", advisedRequest);

        String journalId = journalService.initializeEntry(Map.of());
        String model = advisedRequest.chatModel().getClass().getName();

        journalService.journalRequest(journalId, model, advisedRequest.userText());

        AdvisedResponse advisedResponse = chain.nextAroundCall(advisedRequest);
        LOGGER.info("<<< response: {}", advisedResponse);

        journalService.journalResponse(journalId, model, advisedResponse.response().getResult().getOutput().getText());

        return advisedResponse;
    }
}

CREATE
    TABLE
        IF NOT EXISTS inference_journal_entry(
            id uuid NOT NULL,
            journal_id VARCHAR(50) NOT NULL,
            metadata JSONB NOT NULL,
            create_dt_time TIMESTAMP WITH TIME ZONE NOT NULL
        );

CREATE
    UNIQUE INDEX journal_entry_journal_id_idx ON
    inference_journal_entry(journal_id);

CREATE
    TABLE
        IF NOT EXISTS inference_journal_request_content(
            id uuid NOT NULL,
            journal_id VARCHAR(50) NOT NULL REFERENCES inference_journal_entry(journal_id) ON
            DELETE
                CASCADE,
                model_name VARCHAR(50) NOT NULL,
                content text NOT NULL,
                create_dt_time TIMESTAMP WITH TIME ZONE NOT NULL
        );

CREATE
    INDEX req_model_journal_idx ON
    inference_journal_request_content(
        journal_id,
        model_name
    );

CREATE
    TABLE
        IF NOT EXISTS inference_journal_response_content(
            id uuid NOT NULL,
            journal_id VARCHAR(50) NOT NULL REFERENCES inference_journal_entry(journal_id) ON
            DELETE
                CASCADE,
                model_name VARCHAR(50) NOT NULL,
                content text NOT NULL,
                sequence_num BIGINT,
                create_dt_time TIMESTAMP WITH TIME ZONE NOT NULL
        );

CREATE
    INDEX resp_model_journal_idx ON
    inference_journal_response_content(
        journal_id,
        model_name
    );
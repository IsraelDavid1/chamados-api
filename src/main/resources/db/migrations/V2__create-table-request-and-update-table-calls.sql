ALTER TABLE tb_calls
    ALTER COLUMN solution TYPE TEXT,
    ALTER COLUMN first_analysis TYPE TEXT,
    ADD COLUMN urgency VARCHAR(20) DEFAULT 'LOW' NOT NULL CHECK ( urgency IN ( 'LOW', 'MEDIUM', 'HIGH', 'CRITICAL')),
    ADD COLUMN impact VARCHAR(20) DEFAULT 'LOW' NOT NULL CHECK ( Impact IN ( 'LOW', 'MEDIUM', 'HIGH', 'CRITICAL'));


CREATE TABLE tb_call_request(
                                id UUID PRIMARY KEY,
                                created_by UUID REFERENCES tb_users(id),
                                created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                description TEXT NOT NULL,
                                request_state VARCHAR(20) NOT NULL CHECK ( request_state IN (
                                    'PENDING',
                                    'APPROVED',
                                    'REJECTED'
                                )),
                                approved_by UUID REFERENCES tb_users(id),
                                approved_at TIMESTAMP,
                                rejected_by UUID REFERENCES tb_users(id),
                                rejected_at TIMESTAMP,
                                observation TEXT
);

CREATE INDEX idx_call_request_created_by ON tb_call_request(created_by);
CREATE INDEX idx_call_request_created_at ON tb_call_request(created_at);
CREATE INDEX idx_call_request_state ON tb_call_request(request_state);

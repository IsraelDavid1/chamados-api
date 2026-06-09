CREATE TABLE tb_users(
                         id UUID PRIMARY KEY,
                         login VARCHAR(255) NOT NULL UNIQUE,
                         password VARCHAR(255) NOT NULL,
                         department VARCHAR(255) NOT NULL,
                         role VARCHAR(20) NOT NULL
);


CREATE TABLE tb_calls(
                            id UUID PRIMARY KEY,
                            created_by UUID REFERENCES tb_users(id),
                            assigned_to UUID REFERENCES tb_users(id),
                            begin_date TIMESTAMP NOT NULL,
                            asset VARCHAR(255) NOT NULL,
                            assets_type VARCHAR(255) NOT NULL,
                            department VARCHAR(255) NOT NULL,
                            first_analysis VARCHAR(255) NOT NULL,
                            solution VARCHAR(255),
                            end_date TIMESTAMP,
                            call_state VARCHAR(255) NOT NULL
);


CREATE INDEX idx_calls_begin_date ON tb_calls(begin_date);
CREATE INDEX idx_assigned_to_begin_date ON tb_calls(assigned_to, begin_date);

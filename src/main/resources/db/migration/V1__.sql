CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE ApiUser
(
    uuid     UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    username VARCHAR(255)  NOT NULL
);

CREATE TABLE project
(
    uuid UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255)  NOT NULL
);

CREATE TABLE task
(
    uuid                UUID         PRIMARY KEY DEFAULT uuid_generate_v4(),
    title               VARCHAR(255),
    status              VARCHAR(255) NOT NULL,
    priority            integer NOT NULL,
    project_id          UUID,
    apiuser_assigned_id UUID
);

CREATE TABLE Subscription
(
    uuid       UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    apiuser_id UUID  NOT NULL,
    task_id    UUID  NOT NULL,
    token      VARCHAR(255) NOT NULL
);

ALTER TABLE Subscription
    ADD CONSTRAINT FK_SUBSCRIPTION_ON_APIUSER FOREIGN KEY (apiuser_id) REFERENCES ApiUser (uuid);

ALTER TABLE Subscription
    ADD CONSTRAINT FK_SUBSCRIPTION_ON_TASK FOREIGN KEY (task_id) REFERENCES task (uuid);

ALTER TABLE task
    ADD CONSTRAINT FK_TASK_ON_APIUSER_ASSIGNED FOREIGN KEY (apiuser_assigned_id) REFERENCES ApiUser (uuid);

ALTER TABLE task
    ADD CONSTRAINT FK_TASK_ON_PROJECT FOREIGN KEY (project_id) REFERENCES project (uuid);
CREATE TABLE `its-db`.major
(
    major_id   BIGINT AUTO_INCREMENT NOT NULL,
    major_name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_major PRIMARY KEY (major_id)
);

CREATE TABLE `its-db`.minor
(
    minor_id   BIGINT AUTO_INCREMENT NOT NULL,
    minor_name VARCHAR(255) NOT NULL,
    major_id   BIGINT       NOT NULL,
    CONSTRAINT pk_minor PRIMARY KEY (minor_id)
);

CREATE TABLE `its-db`.subject
(
    subject_id   BIGINT AUTO_INCREMENT NOT NULL,
    subject_name VARCHAR(255) NOT NULL,
    round        INT          NOT NULL,
    exam_date    date         NOT NULL,
    CONSTRAINT pk_subject PRIMARY KEY (subject_id)
);

CREATE TABLE `its-db`.user
(
    user_id       BIGINT AUTO_INCREMENT                 NOT NULL,
    email         VARCHAR(255) NULL,
    name          VARCHAR(255) NULL,
    profile_image VARCHAR(255) NULL,
    user_role     ENUM ('USER', 'ADMIN') DEFAULT 'USER' NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (user_id)
);

ALTER TABLE `its-db`.user
    ADD CONSTRAINT uc_user_email UNIQUE (email);

ALTER TABLE `its-db`.minor
    ADD CONSTRAINT FK_MINOR_ON_MAJOR FOREIGN KEY (major_id) REFERENCES `its-db`.major (major_id);
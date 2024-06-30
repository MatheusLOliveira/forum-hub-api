CREATE TABLE topic (
                       id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       message TEXT NOT NULL,
                       creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       status VARCHAR(50) NOT NULL
)
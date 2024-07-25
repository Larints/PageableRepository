-- Создание таблицы книг
CREATE TABLE book (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      title VARCHAR(255) NOT NULL
);

-- Создание таблицы авторов
CREATE TABLE author (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL
);

-- Создание таблицы связи между книгами и авторами
CREATE TABLE author_book (
                             author_id BIGINT,
                             book_id BIGINT,
                             PRIMARY KEY (author_id, book_id),
                             FOREIGN KEY (author_id) REFERENCES author(id),
                             FOREIGN KEY (book_id) REFERENCES book(id)
);



INSERT INTO member (first_name, last_name, email, level, active) VALUES ('Erik', 'Andersson', 'erik.a@mail.se', 'STUDENT', 1), ('Anna', 'Karlsson', 'anna.k@mail.se', 'PREMIUM', 1), ('Johan', 'Nilsson', 'johan.n@mail.se', 'STANDARD', 1), ('Maria', 'Eriksson', 'maria.e@mail.se', 'STANDARD', 1), ('Sven', 'Larsson', 'sven.l@mail.se', 'PREMIUM', 1), ('Karin', 'Olsson', 'karin.o@mail.se', 'STUDENT', 1), ('Olof', 'Persson', 'olof.p@mail.se', 'STANDARD', 0), ('Linnéa', 'Gustafsson', 'linnea.g@mail.se', 'STUDENT', 1), ('Anders', 'Svensson', 'anders.s@mail.se', 'PREMIUM', 1), ('Emma', 'Holm', 'emma.h@mail.se', 'PREMIUM', 1);

INSERT INTO tie (material, color, price_per_day, available, active) VALUES ('SILK', 'Mörkblå', 69.00, 0, 1), ('WOOL', 'Grå', 79.00, 1, 1), ('POLYESTER', 'Röd', 39.00, 1, 1), ('SILK', 'Guld', 89.00, 0, 1), ('COTTON', 'Grön', 49.00, 1, 0);

INSERT INTO bowtie (material, color, price_per_day, available, active) VALUES ('SILK', 'Svart', 59.00, 1, 1), ('POLYESTER', 'Vinröd', 35.00, 0, 1), ('COTTON', 'Mönstrad', 45.00, 1, 1), ('SILK', 'Vit', 59.00, 1, 1);

INSERT INTO pocket_square (material, color, price_per_day, available, active) VALUES ('SILK', 'Vit', 29.00, 0, 1), ('COTTON', 'Ljusblå', 25.00, 1, 1), ('SILK', 'Paisley', 39.00, 1, 1), ('POLYESTER', 'Prickig', 19.00, 1, 1);

INSERT INTO rental (member_id, rental_type, rental_item_id, total_revenue, rental_date, return_date) VALUES (1, 'TIE', 1, 0.00, '2026-01-20 10:00:00', NULL), (2, 'BOWTIE', 1, 118.00, '2026-01-15 09:00:00', '2026-01-17 14:00:00'), (3, 'POCKET_SQUARE', 1, 0.00, '2026-01-24 11:30:00', NULL), (4, 'BOWTIE', 2, 0.00, '2026-01-25 12:00:00', NULL), (5, 'TIE', 4, 0.00, '2026-01-22 15:00:00', NULL);

INSERT INTO history (description, member_id) VALUES ('BLA BLA 1', 1), ('BLA BLA 1.2', 1), ('BLA BLA 1.3', 1), ('BLA BLA 2', 2), ('BLA BLA 3', 3), ('BLA BLA 3.2', 3);
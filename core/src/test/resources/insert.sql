INSERT INTO public.certificates (name, description, price, duration, create_date,last_update_date) VALUES ('Certificate 1', 'Description 1', 100, 10,'2021-01-01 15:00:00','2021-01-01 15:00:00');
INSERT INTO public.certificates (name, description, price, duration, create_date,last_update_date) VALUES ('Certificate 2', 'Description 2', 200, 20,'2021-01-10 15:00:00','2021-01-10 15:00:00');
INSERT INTO public.certificates (name, description, price, duration, create_date,last_update_date) VALUES ('Certificate 3', 'Description 3', 300, 30,'2021-01-20 15:00:00','2021-01-20 15:00:00');


INSERT INTO public.tags (name) VALUES ('#tag1');
INSERT INTO public.tags (name) VALUES ('#tag2');
INSERT INTO public.tags (name) VALUES ('#tag3');
INSERT INTO public.tags (name) VALUES ('#tag4');

INSERT INTO public.certificates_tags (gift_certificates_id, tags_id) VALUES (1, 1);
INSERT INTO public.certificates_tags (gift_certificates_id, tags_id) VALUES (1, 2);
INSERT INTO public.certificates_tags (gift_certificates_id, tags_id) VALUES (2, 3);
INSERT INTO public.certificates_tags (gift_certificates_id, tags_id) VALUES (2, 4);
INSERT INTO public.certificates_tags (gift_certificates_id, tags_id) VALUES (3, 1);

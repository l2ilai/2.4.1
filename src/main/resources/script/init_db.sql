INSERT INTO public.t_role(id, name)
VALUES (1, 'ROLE_USER'), (2, 'ROLE_ADMIN');
INSERT INTO public.t_user(id, name, password)
VALUES (1, 'admin', '$2y$10$G42kF6YujsyKOtjlZKT2Re8F1fGR40CJVhsr3lakHEmieAHKL9Hq.');
INSERT INTO public.t_user_roles(user_id, roles_id)
VALUES (1, 2);

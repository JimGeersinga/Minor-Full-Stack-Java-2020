INSERT INTO role (id, name)
VALUES (1, N'ADMIN'),
       (2, N'USER');

INSERT INTO user (id, username, password, created, last_modified)
VALUES (1, 'Admin', '$2a$10$zO.P/jNe8LedLkSAD67AIOXOktIBbsncYE7VcP/cUxvgkiZ4dBgRi', CURRENT_DATE(), CURRENT_DATE()), --admin
       (2, 'User', '$2a$10$aqnyqYtZbRbVa58vzFlHv.FauhaSYyfnwHcGUXCJJCaFTboubGxJq', CURRENT_DATE(), CURRENT_DATE()); --user

INSERT INTO user_roles (user_id, role_id)
VALUES (1, 1),
       (1, 2),
       (2, 2);

INSERT INTO user_friends(user_id, friends_id)
VALUES (1, 2),
       (2, 1);
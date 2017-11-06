INSERT INTO CUSTOMER (NAME, BALANCE, STATUS, LOGIN, PASSWORD) VALUES
  ('Vasiliy', 0, 'A', 'vasilisk', 'q'),
  ('Alexandr', 1, 'A', 'xandr', 'w'),
  ('Andrey', 2, 'A', 'andrew', 'e'),
  ('Nikolay', 3, 'B', 'nikolas', 'r'),
  ('Admin', 100500, 'A', 'admin', 'admin');

INSERT INTO ACCOUNT (CUSTOMER_ID, PARTNER_ID, PROFILE_ID, NAME, AVATAR_URL) VALUES
  (SELECT ID FROM CUSTOMER WHERE LOGIN='vasilisk', 0, 1, 'Vasya', 'http://example.com/avatar'),
  (SELECT ID FROM CUSTOMER WHERE LOGIN='andrew', 0, 2, 'Alex', 'http://example.com/avatar'),
  (SELECT ID FROM CUSTOMER WHERE LOGIN='nikolas', 0, 3, 'Kolya', 'http://example.com/avatar');



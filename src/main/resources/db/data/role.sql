INSERT INTO role (id, description) VALUES
('USER','Может просматривать справочную информацию (contractor/county/all, deal/deal-status и т.д.)'),
('CREDIT_USER','Может всё то же самое, что USER, плюс просматривать сделки (REST POST/deal/search) с deal_type = CREDIT'),
('OVERDRAFT_USER','Может все то же самое, что USER, плюс может просматривать сделки' ||
                  ' (REST POST/deal/search) с deal_type = OVERDRAFT'),
('DEAL_SUPERUSER','может все то же самое, что USER, плюс имеет полный доступ к сервису deal'),
('CONTRACTOR_RUS','может все то же самое, что USER, плюс может просматривать контрагентов' ||
                  ' (REST POST/contractor/search) с country = RUS'),
('CONTRACTOR_SUPERUSER','может все то же самое, что USER, плюс имеет полный доступ к сервису contractor'),
('SUPERUSER','Имеет полный доступ к сервисам deal и contractor'),
('ADMIN','Имеет полный доступ к сервису auth, но не имеет доступа к сервисам deal и contractor');

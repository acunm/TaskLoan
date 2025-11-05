
INSERT INTO users (user_id, username, password, user_role) VALUES (NEXT VALUE FOR userIdGenerator, 'user1', '$2a$10$vMocXKlCSXba1Semoms/gemDb5tLKPJbu4CpxwtqCSAH67LRsYfwS', 'CUSTOMER');
INSERT INTO customers (customer_id, user_id, name, surname, credit_limit, used_credit_limit) VALUES (NEXT VALUE FOR customerIdGenerator, CURRENT VALUE FOR userIdGenerator, 'user1name', 'user1surname', 1000000, 0);

INSERT INTO users (user_id, username, password, user_role) VALUES (NEXT VALUE FOR userIdGenerator, 'user2', '$2a$10$vMocXKlCSXba1Semoms/gemDb5tLKPJbu4CpxwtqCSAH67LRsYfwS', 'CUSTOMER');
INSERT INTO customers (customer_id, user_id, name, surname, credit_limit, used_credit_limit) VALUES (NEXT VALUE FOR customerIdGenerator, CURRENT VALUE FOR userIdGenerator, 'user2name', 'user2surname', 10000, 0);
INSERT INTO loans (loan_id, customer_id, loan_amount, number_of_installment, create_date, is_paid, interest_rate) VALUES (NEXT VALUE FOR loanIdGenerator, CURRENT VALUE FOR customerIdGenerator, 1000, 6, DATE '2025-10-15', false, 0.2);

INSERT INTO loan_installments (installment_id, loan_id, amount, paid_amount, due_date, payment_date, is_paid) VALUES (NEXT VALUE FOR installmentIdGenerator, CURRENT VALUE FOR loanIdGenerator, 2000, 0, DATE 2025-11-01, null, false);
INSERT INTO loan_installments (installment_id, loan_id, amount, paid_amount, due_date, payment_date, is_paid) VALUES (NEXT VALUE FOR installmentIdGenerator, CURRENT VALUE FOR loanIdGenerator, 2000, 0, DATE 2025-12-01, null, false);
INSERT INTO loan_installments (installment_id, loan_id, amount, paid_amount, due_date, payment_date, is_paid) VALUES (NEXT VALUE FOR installmentIdGenerator, CURRENT VALUE FOR loanIdGenerator, 2000, 0, DATE 2026-01-01, null, false);
INSERT INTO loan_installments (installment_id, loan_id, amount, paid_amount, due_date, payment_date, is_paid) VALUES (NEXT VALUE FOR installmentIdGenerator, CURRENT VALUE FOR loanIdGenerator, 2000, 0, DATE 2026-02-01, null, false);
INSERT INTO loan_installments (installment_id, loan_id, amount, paid_amount, due_date, payment_date, is_paid) VALUES (NEXT VALUE FOR installmentIdGenerator, CURRENT VALUE FOR loanIdGenerator, 2000, 0, DATE 2026-03-01, null, false);
INSERT INTO loan_installments (installment_id, loan_id, amount, paid_amount, due_date, payment_date, is_paid) VALUES (NEXT VALUE FOR installmentIdGenerator, CURRENT VALUE FOR loanIdGenerator, 2000, 0, DATE 2026-04-01, null, false);


INSERT INTO users (user_id, username, password, user_role) VALUES (NEXT VALUE FOR userIdGenerator, 'user3', '$2a$10$vMocXKlCSXba1Semoms/gemDb5tLKPJbu4CpxwtqCSAH67LRsYfwS', 'CUSTOMER');
INSERT INTO customers (customer_id, user_id, name, surname, credit_limit, used_credit_limit) VALUES (NEXT VALUE FOR customerIdGenerator, CURRENT VALUE FOR userIdGenerator, 'user3name', 'user3surname', 1000000, 700000);



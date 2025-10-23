-- USERS
INSERT INTO users (name, surname, email, password, role) 
VALUES ('Marko', 'Marković', 'marko@example.com', 'pass123', 'user'),
       ('Jelena', 'Jovanović', 'jelena@example.com', 'pass456', 'admin');

-- CATEGORIES
INSERT INTO categories (name, description)
VALUES ('Shoes', 'All types of sports shoes'),
       ('Equipment', 'Training and gym equipment');

-- PRODUCTS
INSERT INTO products (name, description, price, stock, category_id)
VALUES ('Football Shoes', 'Professional football shoes', 120.00, 10, 1),
       ('Dumbbell Set', 'Set of gym dumbbells', 80.00, 20, 2);

-- ORDERS
INSERT INTO orders (created_date, shipping_date, status, total_price, discount, note, user_id)
VALUES ('2025-10-20', '2025-10-25', 'CREATED', 200.00, 0.00, 'First test order', 1),
       ('2025-10-21', '2025-10-26', 'SHIPPED', 120.00, 10.00, 'Second test order', 2);

-- ADDRESSES
INSERT INTO addresses (street, city, postal_code, country, order_id, user_id)
VALUES ('Main Street 12', 'Belgrade', '11000', 'Serbia', 1, 1),
       ('Green Avenue 45', 'Novi Sad', '21000', 'Serbia', 2, 2);

-- COUPONS
INSERT INTO coupons (code, discount_percent, expiry_date, order_id)
VALUES ('WELCOME10', 10, '2025-12-31', 1),
       ('SPORT20', 20, '2025-12-31', 2);

-- PAYMENTS
INSERT INTO payments (amount, method, payment_date, status, order_id)
VALUES (200.00, 'CARD', '2025-10-20', 'PAID', 1),
       (120.00, 'CASH', '2025-10-21', 'PENDING', 2);

-- ORDER ITEMS
INSERT INTO order_items (price_per_unit, quantity, total, order_id, product_id)
VALUES (120.00, 1, 120.00, 1, 1),
       (80.00, 1, 80.00, 2, 2);

-- REVIEWS
INSERT INTO reviews (comment, date, rating, product_id, user_id)
VALUES ('Great shoes, very comfortable!', '2025-10-22', 5, 1, 1),
       ('Good quality equipment.', '2025-10-22', 4, 2, 2);
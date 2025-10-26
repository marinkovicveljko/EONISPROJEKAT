-- USERS
INSERT INTO users (name, surname, email, password, role) 
VALUES ('Marko', 'Marković', 'marko@example.com', 'pass123', 'user'),
       ('Jelena', 'Jovanović', 'jelena@example.com', 'pass456', 'admin');

-- CATEGORIES
INSERT INTO categories (name, description)
VALUES ('Shoes', 'All types of sports shoes'),
       ('Equipment', 'Training and gym equipment');

-- PRODUCTS
INSERT INTO products (name, description, price, stock, category_id, image_url)
VALUES 
('Nike Air Zoom', 'Running shoes for professionals', 120.00, 10, 1, '/assets/products/slika1.jpg'),
('Adidas Predator', 'Professional football shoes', 140.00, 15, 1, '/assets/products/2.jpg.webp'),
('Puma Classic', 'Lifestyle sneakers', 90.00, 12, 1, '/assets/products/3.png'),
('Asics Gel Kayano', 'Long distance running shoes', 150.00, 8, 1, '/assets/products/4.jfif'),
('Converse Chuck Taylor', 'Casual sneakers', 70.00, 20, 1, '/assets/products/5.jpg'),
('Dumbbell Set', 'Set of gym dumbbells', 80.00, 20, 2, '/assets/products/6.jpg.webp'),
('Yoga Mat', 'High-quality yoga mat', 30.00, 25, 2, '/assets/products/7.webp'),
('Kettlebell', '10kg kettlebell for strength training', 40.00, 15, 2, '/assets/products/8.webp'),
('Resistance Bands', 'Set of 5 elastic resistance bands', 25.00, 30, 2, '/assets/products/9.webp'),
('Pull-up Bar', 'Adjustable pull-up bar for home training', 50.00, 10, 2, '/assets/products/10.webp');

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
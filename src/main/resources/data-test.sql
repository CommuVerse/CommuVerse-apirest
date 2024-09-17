INSERT INTO users (name, nickname, date_of_birth, email, password, bio, profile_picture, registration_date, role)
VALUES 
    ('Alice Smith', 'Alice', '1990-05-15', 'alice.smith@example.com', 'password123', 'Tech enthusiast and writer.', 'profile1.jpg', '2024-09-15 10:00:00', 'CREATOR'),
    ('Bob Johnson', 'Bob', '1985-11-20', 'bob.johnson@example.com', 'password456', 'Passionate about movies and reviews.', 'profile2.jpg', '2024-09-15 11:00:00', 'READER'),
    ('Carol White', 'Carol', '1992-07-10', 'carol.white@example.com', 'password789', 'Loves to travel and share experiences.', 'profile3.jpg', '2024-09-15 12:00:00', 'CREATOR'),
    ('Dave Brown', 'Dave', '1988-03-25', 'dave.brown@example.com', 'password101', 'Science fiction aficionado.', 'profile4.jpg', '2024-09-15 13:00:00', 'READER'),
    ('Eve Black', 'Eve', '1995-09-30', 'eve.black@example.com', 'password202', 'Digital artist and tech reviewer.', 'profile5.jpg', '2024-09-15 14:00:00', 'CREATOR')
ON CONFLICT (email) DO NOTHING;

INSERT INTO subscription_plans (name, description, level, price, renewal_period, user_id)
VALUES
    ('Basic Plan', 'Access to basic features and content.', 'BASIC', 9.99, 'monthly', 1),
    ('Premium Plan', 'Access to premium features, exclusive content, and early access.', 'PREMIUM', 19.99, 'monthly', 2),
    ('Annual Basic Plan', 'Basic plan with annual payment discount.', 'BASIC', 99.99, 'annual', 3),
    ('Annual Premium Plan', 'Premium plan with annual payment discount and extra benefits.', 'PREMIUM', 199.99, 'annual', 4)
ON CONFLICT (name) DO NOTHING;
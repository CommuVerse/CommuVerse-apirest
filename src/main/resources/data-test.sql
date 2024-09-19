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

INSERT INTO articles ( type, title, content, publication_date, status, num_reads, num_comments, num_likes, scheduled_date, user_id)
VALUES
    ( 'Tech', 'Exploring New AI Trends', 'This article discusses the latest trends in artificial intelligence, including advancements in machine learning and neural networks.', '2024-09-15 09:00:00', true, 150, 20, 30, '2024-09-15 09:00:00', 1),
    ( 'Lifestyle', 'Top 10 Travel Destinations for 2024', 'Discover the best travel destinations for the upcoming year, featuring beautiful landscapes and exciting cultures.', '2024-09-16 11:30:00', true, 200, 25, 45, '2024-09-16 11:30:00', 2),
    ( 'Health', 'The Benefits of a Balanced Diet', 'An in-depth look at how a balanced diet can improve overall health, including tips on maintaining a nutritious eating plan.', '2024-09-17 14:00:00', true, 180, 18, 40, '2024-09-17 14:00:00', 3),
    ( 'Finance', 'Investing in the Stock Market', 'A guide to investing in the stock market, including strategies for beginners and advice on building a diversified portfolio.', '2024-09-18 16:45:00', true, 250, 30, 55, '2024-09-18 16:45:00', 4);

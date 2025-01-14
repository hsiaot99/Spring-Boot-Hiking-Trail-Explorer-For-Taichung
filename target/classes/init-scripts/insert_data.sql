-- 插入資料 (共用語法)
INSERT INTO hiking_trail_t (
    number, city_code, district, district_code, trail_name, 
    longitude, latitude, trail_type, trail_level, 
    facilities, management_unit, length_km
) VALUES 
(
    '1', '10019', '霧峰區', '66000260', '中心瓏登山步道',
    120.699778, 24.054119, '文化歷史', '親子級',
    '觀景平台、停車場、廁所', '霧峰區公所', 1.8
),
(
    '2', '10019', '霧峰區', '66000260', '阿罩霧櫻花杜鵑步道',
    120.722792, 24.058439, '文化歷史', '休閒級',
    '休憩座椅', '霧峰區公所', 2.3
);
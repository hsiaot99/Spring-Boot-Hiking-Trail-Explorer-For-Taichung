CREATE TABLE hiking_trail_t (
    seq BIGINT PRIMARY KEY AUTO_INCREMENT,
    number VARCHAR(10) NOT NULL,                
    city_code VARCHAR(10) NOT NULL,             
    district VARCHAR(50) NOT NULL,              
    district_code VARCHAR(20) NOT NULL,         
    trail_name VARCHAR(100) NOT NULL,           
    longitude DECIMAL(9,6) NOT NULL,            
    latitude DECIMAL(9,6) NOT NULL,             
    trail_type VARCHAR(50),                     
    trail_level VARCHAR(50),                    
    facilities VARCHAR(500),                    
    management_unit VARCHAR(100),               
    length_km DECIMAL(5,2),                     
    createtime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 建立索引 (共用語法)
CREATE INDEX idx_hiking_trail_city_code ON hiking_trail_t(city_code);
CREATE INDEX idx_hiking_trail_district ON hiking_trail_t(district);
CREATE INDEX idx_hiking_trail_trail_type ON hiking_trail_t(trail_type);
CREATE INDEX idx_hiking_trail_trail_level ON hiking_trail_t(trail_level);

-- REPLACE INTO
REPLACE INTO `sdk_2` (`USER_ID`, `USER_NAME`, `AGE`, `CREATED_BY`, `UPDATED_BY`)
VALUES
    (3, '赵六', 35, 'system', 'system'),
    (4, '钱七', 40, 'system', 'system'),
    (5, '钱七', 40, 'system', 'system'),
    (6, '钱七', 40, 'system', 'system');

-- INSERT IGNORE
INSERT IGNORE INTO `sdk_2` (`USER_ID`, `USER_NAME`, `AGE`, `CREATED_BY`, `UPDATED_BY`)
VALUES
    (1, '张三2', 25, 'system', 'system'),
    (2, '李四2', 30, 'system', 'system');

-- INSERT ON DUPLICATE KEY UPDATE
INSERT INTO `sdk_2` (`USER_ID`, `USER_NAME`, `AGE`)
VALUES
    (4, '钱七', 33)
ON DUPLICATE KEY UPDATE
     USER_NAME = VALUES(USER_NAME),
     AGE = VALUES(AGE);

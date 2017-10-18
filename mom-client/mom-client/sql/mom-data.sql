CREATE TABLE `mom_data` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`create_time` VARCHAR(14) NOT NULL,
	`update_time` VARCHAR(14) NOT NULL,
	`msg_key` CHAR(36) NOT NULL COMMENT '唯一标识',
	`data` MEDIUMTEXT NOT NULL COMMENT '最大16MB，UTF-8',
	`status` TINYINT(4) NOT NULL DEFAULT '0',
	`info_msg` VARCHAR(256) NOT NULL DEFAULT '',
	`consume_time` BIGINT(20) NOT NULL DEFAULT '0' COMMENT '上次消耗时间，精确到毫妙',
	`retryCount` INT(11) NOT NULL DEFAULT '0' COMMENT '重试次数',
	`exchange` VARCHAR(50) NOT NULL DEFAULT '',
	`routerKey` VARCHAR(50) NOT NULL DEFAULT '',
	PRIMARY KEY (`id`),
	INDEX `correlation_id` (`correlation`),
	INDEX `status` (`status`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

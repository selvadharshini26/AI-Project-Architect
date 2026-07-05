package com.projectarchitect.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/**
 * Enables MongoDB auditing so that {@code @CreatedDate} and
 * {@code @LastModifiedDate} fields are populated automatically.
 */
@Configuration
@EnableMongoAuditing
public class MongoAuditingConfig {
}

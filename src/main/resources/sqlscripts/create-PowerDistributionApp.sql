CREATE DATABASE IF NOT EXISTS PowerDistributionApp;

CREATE TABLE PowerDistributionApp.power_distribution_units (
    `pdu_id` INT,
    `name` VARCHAR(255),
    `location_code` VARCHAR(255),
    `capacity_kw` DOUBLE,
    `status` VARCHAR(255),
PRIMARY KEY (power_distribution_unit_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE PowerDistributionApp.power_panels (
    `power_panel_id` INT AUTO_INCREMENT,
    `pdu_id` INT,
    `name` VARCHAR(255),
    `voltage` DOUBLE,
    `max_amps` DOUBLE,
PRIMARY KEY (power_panel_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE PowerDistributionApp.power_outlets (
    `power_outlet_id` INT AUTO_INCREMENT,
    `power_panel_id` INT,
    `outlet_number` VARCHAR(255),
    `status` VARCHAR(255),
    `connected_asset_id` INT,
PRIMARY KEY (power_outlet_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE PowerDistributionApp.power_feeds (
    `power_feed_id` INT AUTO_INCREMENT,
    `pdu_id` INT,
    `feed_name` VARCHAR(255),
    `feed_type` VARCHAR(255),
    `capacity_kw` DOUBLE,
PRIMARY KEY (power_feed_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

USE drcvelocity;

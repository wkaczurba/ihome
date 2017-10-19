# ihome

Simple Spring-based prooject (WIP) for IoT (RaspberryPi).

Server side:
 - Spring provides REST-based access to turning on/off devices at home in either time-based or static (on-off manner).
 - (Web, Hibernate, Thymeleaf, Oracle 12c DB).
 - Basic tests for Hibernate DB access.
 
Raspberry Pi (in house):
 - RaspberryPi accessees server and updates all swithces accordingly to the server's settings
 - Tests: Python's unittest

Android App:
 - not developed yet.

TOOD:
 - Change from REST to messaging for Raspberry so it does not use 
 - Add Spring Cache
 - Add Android project to set server using REST.
 
 

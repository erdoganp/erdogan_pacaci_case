📌 Test Automation Project
📖 Project Overview
This project is an end-to-end test automation framework that includes UI, API, and performance testing based on real-world applications.

UI automation scenarios were implemented for the Insider website to validate user interface components, navigation, and critical user flows. API automation tests were developed for the Swagger Petstore service to verify backend functionality, CRUD operations, and data validation. Performance and load testing were conducted for the search functionality of N11 using Apache JMeter to analyze response times, throughput, and system stability under concurrent user load.

The framework is designed with a modular, reusable, and scalable architecture, following industry best practices to support continuous testing and improve software quality.

🎯 Purpose
The main goal of this project is to:

Automate critical test scenarios

Detect bugs early in the software lifecycle

Improve product quality and stability

Support continuous testing in Agile environments

Measure system performance under different load conditions

🧪 Test Types Included
✅ UI Automation
UI tests are implemented using Selenium WebDriver to validate user workflows and functional scenarios.

Key coverage:

Login and authentication

Form validation

Navigation and page verification

End-to-end business flows

Cross-browser support

The framework follows Page Object Model (POM) to improve maintainability and readability.

✅ API Automation
API tests are developed using REST Assured to validate backend services and data integrity.

Key coverage:

Request and response validation

Schema validation

Status code verification

Authentication and authorization

Data-driven API testing

CRUD operations

✅ Performance Testing
Performance and load tests are conducted using Apache JMeter to evaluate system scalability and reliability.

Key scenarios:

Load testing

Stress testing

Response time analysis

Throughput and error monitoring

Business critical scenario testing

The performance results help identify bottlenecks and system limitations.

🏗️ Framework Architecture
The project is designed with a modular and scalable structure:

Reusable utilities

Configuration management

Test data handling (CSV, JSON)

Logging and reporting

Environment-based execution

Parallel test execution support

The framework supports easy extension for new test types.

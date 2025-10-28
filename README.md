# 💸 Expense Tracker — Microservice Architecture

> **A scalable, cloud-ready Expense Tracking platform** built using a **microservices architecture**, powered by **Spring Boot, Kafka, MySQL, and Kong API Gateway**.
> Designed for modularity, resilience, and real-world backend engineering practice 🚀

---

## 🧩 Architecture Overview

The system is composed of independent microservices communicating through **REST APIs** and **Kafka topics**.
Each service can be scaled, updated, or deployed individually.

**Tech Stack Highlights:**

* ☕ **Java 21 + Spring Boot** (REST, Security, JPA, Validation)
* 🧠 **Python (ML/DS Service)** — for analytics and predictions
* 🐬 **MySQL** — persistent storage for users, expenses, and tokens
* ⚙️ **Kafka** — event-driven communication between services
* 🧱 **Kong API Gateway** — centralized routing, authentication, and rate-limiting
* 🐳 **Docker** — containerized deployments for each service
* 🚀 **Railway/Render** — for cloud hosting and CI/CD-ready deployment

---

## 🏗️ Microservices

| Service                | Description                                                   | Port   |
| ---------------------- | ------------------------------------------------------------- | ------ |
| 🧑‍💼 **Auth Service** | Handles JWT authentication, refresh tokens, and user sessions | `9898` |
| 👥 **User Service**    | Manages user profiles and preferences                         | `9899` |
| 💰 **Expense Service** | CRUD operations for expenses, categories, and insights        | `9900` |
| 📊 **DS Service**      | Python-based analytics for expense patterns                   | `5000` |
| 🌐 **Kong Gateway**    | Centralized API routing and plugin management                 | `8000` |

---

## ⚙️ Project Structure

```
ExpenseTracker-Microservice-Architecture/
│
├── authservice/         # Authentication & JWT management
├── userservice/         # User CRUD and profile management
├── expenseservice/      # Expense operations and Kafka producers
├── dsservice/           # Python service for data science utilities
├── kong/                # Kong configuration & custom plugins
│
├── docker-compose.yml   # Combined service orchestration
└── kong.yml             # Gateway routes and services config
```

---

## 🧪 Features

✅ Secure Authentication with **JWT + Refresh Tokens**
✅ Distributed event communication with **Apache Kafka**
✅ Centralized API Management via **Kong Gateway**
✅ Containerized services with **Docker**
✅ Modular codebase — easily extensible for new features
✅ Supports **CI/CD** and **Cloud Deployments (Railway / Render)**
✅ Integrated **Python microservice** for ML & analytics

---

## 🚀 Getting Started

### 🐳 Run all services (Docker)

```bash
docker-compose up --build
```

### 🧰 Run manually (Gradle)

```bash
cd authservice
./gradlew bootRun
```

(do this for each Java microservice)

### 🧠 Run DS (Python) service

```bash
cd dsservice/source
python -m app.utilis
```

---

## 🔐 Environment Variables

Each service uses its own `.env` or application.properties:

```env
DB_URL=<your_database_url>
DB_USER=<your_username>
DB_PASSWORD=<your_password>

JWT_SECRET=<your_jwt_secret>
KAFKA_BROKER=<broker_url:9092>
```

---

## 🌍 Deployment

Ready to be deployed on **Railway** (supports MySQL, Docker, and multi-service orchestration).
Future-ready for **AWS ECS / GCP Cloud Run / Kubernetes** integration.

---

## 📈 Future Enhancements

* ✅ Add **Grafana + Prometheus** monitoring
* ✅ Integrate **Redis** for caching user sessions
* ✅ Introduce **API rate-limiting plugin in Kong**
* ✅ Add **AI expense prediction models** in DS service

---

## 🧑‍💻 Developer

**👋 [Anshul Tyagi](https://github.com/anshul9982)**

> Passionate about scalable backend architectures, distributed systems, and data-driven design.

---

## ⭐ Star this Repo

If you like this project or found it useful —
👉 **Star it on GitHub** to show support 💖

---

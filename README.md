# BootBlog Platform

A secure, high-concurrency, containerized blogging ecosystem featuring a robust **Spring Boot REST API** and a dynamic **React.js creator dashboard**. The platform implements stateless JWT authentication with strict Role-Based Access Control (RBAC) to separate standard user interactions from administrative moderation engines.

🔗 **Live Frontend Application:** [View Live Deployment on Vercel](https://bootblog-frontend.vercel.app)  
🔗 **Live Backend API Base:** `https://bootblog-backend.onrender.com`

---

## ⚡ Core Architecture Features

### 🔐 Multi-Tier Security & Gatekeeping
* **Stateless JWT Protocols:** Implements secure JSON Web Tokens with automated client-side expiry eviction routines.
* **Role-Based Access Control (RBAC):** Strict security layers differentiating `USER` privileges from administrative `ADMIN` content moderation tools.
* **Global Network Interceptors:** Centralized global network wrapper protecting all application routes (Home, Profile) against session tampering.

### 💾 High-Concurrency Data Management
* **Database Pooling Optimization:** Tuned entity relationships (`@OneToMany`, `@ManyToOne`) and connection pooling to seamlessly process **100+ concurrent API requests**.


### 🛠️ Modular Content Frameworks
* **Dynamic Creator Studio:** Built-in complete CRUD operations for Categories, Posts, and Comments.
* **Comprehensive Search & Pagination:** Server-side paginated queries coupled with indexed keyword pattern matching.
* **Resilient Error Architecture:** Universal exception parser that maps structured validation constraints directly to user-friendly client alerts.

---

## 🛠️ Tech Stack

| Component | Technology | Description |
| :--- | :--- | :--- |
| **Backend Core** | Java 17, Spring Boot 4.0.x, Maven | Enterprise-tier application server foundation. |
| **Security Layer** | Spring Security, JWT (JSON Web Tokens) | Token-based stateless request authorization. |
| **Data Engine** | Spring Data JPA, Hibernate, PostgreSQL. |
| **Frontend UI** | React.js, Tailwind CSS, Axios | High-fidelity, client-side rendering with asynchronous lifecycle hooks. |
| **Infrastructure** | Docker, Render, Vercel Serverless Edge | Multi-cloud containerized and edge deployment strategy. |
| **Database Cloud** | Neon Serverless PostgreSQL | High-availability cloud SQL engine. |

---

## 🔑 Environment Configuration

Configure the following system variables before spinning up the application core containers:

```properties
# Database Connectivity Configuration
DB_URL=jdbc:postgresql://your-neon-database-host/neondb
DB_USERNAME=your_neon_database_username
DB_PASSWORD=your_neon_database_password

# Authentication Engine Variables
JWT_SECRET=your_secure_static_base64_jwt_signing_key_string
JWT_EXPIRATION=86400000

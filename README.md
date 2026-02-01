# OAuth2 Authorization Server

A secure, standards-compliant OAuth2 Authorization Server responsible for
issuing JWT access tokens to trusted clients.
This project demonstrates enterprise-grade authentication architecture
using Spring Boot and Spring Authorization Server, with a strong focus on
security, token integrity, and clean separation of concerns.

---

## 🚀 Features

### 🔐 OAuth2 & Token Issuance
- Custom OAuth2 Authorization Server implementation
- Client authentication using `client_id` and `client_secret`
- Support for Client Credentials Grant
- JWT-based access token issuance
- Configurable access token expiry
- Stateless token-based authentication

### 🔑 Token Security
- RSA-based JWT signing (asymmetric keys)
- JWK (JSON Web Key) endpoint for public key exposure
- Issuer (iss) and Audience (aud) claims embedded in tokens
- Strict control over token scope and lifecycle

### 🧩 Code Quality & Architecture
- Clean separation between Authorization Server and Resource Server
- Modern Spring Security lambda-based configuration
- Explicit OAuth2 configuration using Spring Authorization Server
- Incremental, commit-driven development
- Production-aligned configuration patterns

---

## 🧰 Tech Stack
- Java 17
- Spring Boot 3.x
- Spring Security 6.x
- Spring Authorization Server
- OAuth2 / JWT
- MySQL
- Maven

---

## 🏗️ Project Architecture

The Authorization Server is designed as a dedicated security service:

- Security Configuration – OAuth2 endpoints, filter chains, and authentication rules
- Client Management – OAuth2 client registration (JDBC-backed)
- Token Customization – Custom JWT claims (issuer, audience, roles)
- Key Management – RSA key generation and JWK exposure
- Persistence Layer – Database-backed OAuth2 client storage

This server is intentionally isolated from business APIs to ensure
clear responsibility and improved security posture.

---

## 🗄️ Database Design

### oauth2_registered_client table

This table stores OAuth2 client configuration used by the Authorization Server.
It is managed internally by Spring Authorization Server.

| Column | Description |
|------|-------------|
| id | Internal primary identifier for the client |
| client_id | Public OAuth2 client identifier |
| client_id_issued_at | Timestamp when the client ID was issued |
| client_secret | Encrypted client secret |
| client_secret_expires_at | Client secret expiration time |
| client_name | Human-readable name of the client |
| client_authentication_methods | Supported client authentication methods |
| authorization_grant_types | Allowed OAuth2 grant types |
| redirect_uris | Redirect URIs (authorization code flow) |
| post_logout_redirect_uris | Post logout redirect URIs |
| scopes | Allowed OAuth2 scopes |
| client_settings | Client configuration stored as JSON string |
| token_settings | Token configuration stored as JSON string |

---

## 🔌 OAuth2 Endpoints

| Method | Endpoint | Description |
|------|----------|-------------|
| POST | /oauth2/token | Issue JWT access token |
| GET | /oauth2/jwks | Expose JWK public keys |

---

## 🔄 OAuth2 Token Flow (Client Credentials)

Client Application  
→ /oauth2/token  
→ Validate client credentials  
→ Issue signed JWT  
→ Client uses JWT to access Resource Server

---

## 🔐 JWT Structure

Example claims issued by the Authorization Server:

{
  "iss": "http://localhost:8080",
  "aud": ["resource-server"],
  "client_id": "client-app",
  "roles": ["SERVICE"],
  "scope": ["read"],
  "exp": 1710000000
}

---

## ▶️ How to Run

1. Clone the repository
2. Create a MySQL database (e.g. auth_server_db)
3. Configure database credentials in application.properties
4. Run the application

mvn spring-boot:run

Server runs at:
http://localhost:8080

---

## 🧠 Design Highlights
- Dedicated Authorization Server (no business APIs)
- Asymmetric JWT signing
- JWK-based public key distribution
- Strict issuer and audience validation
- Forward-compatible Spring Security configuration

---

## 📌 Future Enhancements
- Authorization Code flow support
- Refresh token support
- Token revocation and introspection
- Client-specific audience mapping
- Monitoring and audit logging

---

## 👨‍💻 Author Notes
This project demonstrates a production-ready OAuth2 Authorization Server
using modern Spring Security and Spring Authorization Server,
following clean architecture and enterprise security practices.

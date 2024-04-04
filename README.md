# MFA Service Project README 😉
## Overview
The MFA Service Project is a demonstration of Multi-Factor Authentication (MFA) using QR code generation, secret management, and code verification. It aims to showcase the integration of an MFA flow within applications, supporting various authenticator apps like Microsoft Authenticator, Google Authenticator, and others. The project leverages Spring Boot, JPA, H2 Database, and TOTP for QR code generation and validation.

## System Requirements
- Java 21
- Spring Boot 3.2.4
- Lombok
- JPA
- H2 Database
- A compatible TOTP library for QR code generation and verification

## Setup
1. **Clone the repository:** Use Git to clone the project to your local machine.

2. **Install dependencies:** Ensure Java 21 and Maven are installed. Run `mvn install` in the project root.

3. **Configure your IDE:** Import the project. Ensure the Lombok plugin is installed and enabled.

4. **Run the application:** Start the Spring Boot server by running `MfaServiceApplication`.

## Usage
### Creating a User and Generating a QR Code
* Endpoint: **[POST]** /user

* Body:
```json 
{
"user": "username",
"password": "password"
} 
```

* Response:
``` json
  {
  "userId": 1,
  "qrCode": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAV4AAAFeAQAAAADlUEq3AAADFklEQVR4Xu2aO5biQAxFy4egw1mCl8LS7KWxFJZA6IBDje5TGcpjQ3fH85S4rbomUOvzZCj153Yv/3o+mOHeDPdmuDfDvf0f8KPI4qaMdS6nWq9njv7EpZwWHZ4MH8L8VefHaYnjhMv0gDpXnCti+ADGP9f7FwEeajxTpvDfynpXDH+EcQQ13L9umbORsGGGv4drVnf4CTB2VXUb/gRzMtMfx4t6oFJ0IdTEeUUM72G8xHKhMe4uOjR8CD/tSdULx7dx/R+kGd7BinPM4RIdETioS3nE3XiZcMZlMHwIY8zhOw9NrRW2qEsEMpUNv4fnKu0yDwjnaIyvCTM2xvAezi2DQo5nFNKL4hyDhk+IFDX8FsY2AZYTKkQ1o/o1Uwx3cPRAHln99MeobhaRtdYnw+9gAly0c2i00CYjRSPcStHCneEjOMdHjeByPHAcmZrOFu5+phh+wU0/S7S0Qr6UIeKsZzDCbfgYbiFl8pZM2MzUWXfnlNGG93Aq5oHGGIoZv46wJqO7FDW8re4o60qKRmPMFJ2o7shNRPXYPsHwHqb5of7YdCcEjZyiGNXXmDfpMbyD81hTZFUySJjoj9raeiVjeAtTwZmsGibIvpo7R7504c7wEUx1Z25GnFXWqm7eveTLvT7OhrdZ96zuOvNWL1P02RFrdkvDO5jmF5HV5BVcM0XjUkjRax9nw1v4lZu1ImiuWnjZQNoH8ajhPQwVscyFt2ZuMoB1Z/gTnGlYlJscazgXmYQzUTd8DHebWRS5pogWkcjbZ5s0fAjzV23qb1JSaoqQorpkmzR8BC98z1NZNiI3T22YCEiBaPg9THUj+8bUgiGjW3+UoXIMH8HNyMYxZR+PoqZTwsTlVd2Ge/iR0aQ/RoDpiFVvQrMjLjRNmeE9TBDjOCM7ZKaSt9kfibPhd/Ci3TZXD7XCDD7PEGu0teFPcGYjRV70m4vslvm62fB38LO6a9M1uZZsRKDhHuY0C1k/42myTyKwsP52X2QY3sBFxkyJkOK/6v1K+GiT7HAywzv4p2a4N8O9Ge7NcG+Ge/sd/BdNiEjwGShgQwAAAABJRU5ErkJggg=="
  }
```

**Returns a QRCodeDTO object containing the user ID and a data URI for the generated QR code image.**

### Verifying a Code
* Endpoint: **[POST]** /user/{userId}/verify
* Path Variable: userId - The ID of the user whose code is to be verified.
* Body:
```json
{
"code": "verification_code"
} 
```
* Response: **If the verification is successful, no error is thrown. An AuthenticationException is thrown if the code is invalid.**

## Project Components
### MfaServiceApplication
The entry point of the Spring Boot application. It initializes and runs the Spring application.

### UserEntity
Defines the User entity model with fields for ID, username, password, and secret.

### UserRepository
Spring Data JPA repository for UserEntity objects, facilitating CRUD operations.

### QRCodeDTO, UserDTO, VerifyCodeDTO
Data Transfer Objects (DTOs) for handling QR code data, user information, and verification codes.

### MFAService
Service layer for generating secret keys, QR code URLs, and verifying codes.

### UserService
Handles user creation and encapsulates MFAService calls.

### UserController
REST controller that exposes endpoints for creating users (and generating QR codes) and verifying codes.


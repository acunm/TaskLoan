# INGHubs Loan API

This is my solution for the INGHubs Loan API task.

---

## How to run
Jar file in releases section can be run directly with java command. 

java -jar demo-0.0.1-SNAPSHOT.jar

---

When the application starts, it inserts admin user, username is admin and password is 123456. New users can be registered with /register endpoint. All endpoints is secured, JWT is required, 
except /login. Only admins can add user. Admins can operate as any user, users can only operate for themselves. Everybody is a user, some of them are customers, non-admins. Admins are user but not
a customer.

There is an endpoint to insert test data to be able to test api with postman.

GET /create-demo-data. Does not need any authentication.

The data is in demo-data.sql file. Users, Customers, loans, installments all will be inserted.

---

## Endpoints
<details>
<summary>POST /user/login</summary>

**Request Body:**
```json
{
  "username": "admin",
  "password": "123456"
}
```
**response**:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.*****.EBnA6LZCj-sJ9Can6iHhQW9_dfyVMtLjdhEDFykeMoM"
}

```
</details>

<details>
<summary>POST /user/register</summary>
<description>
  customLoanLimit can be sent to api to define different limit. In default it is 1000000.
</description>

**Request Body:**
```json
{
    "username": "username",
    "password": "123456",
    "name": "name",
    "surname": "surname"
}
```
**response**:
```json
{
    "username": "username",
    "loanLimit": 1000000
}

```
</details>

<details>
<summary>POST /loan</summary>

**Request Body:**
```json
{
    "loanAmount": 1000000,
    "installmentCount": 6,
    "interestRate": 0.2
}
```
**response**:
```json
{
    "loanAmount": 1000000,
    "nextDueDate": "2025-12-01",
    "installments": [
        {
            "dueDate": "2025-12-01",
            "installmentAmount": 200000.0
        },
        {
            "dueDate": "2026-01-01",
            "installmentAmount": 200000.0
        },
        {
            "dueDate": "2026-02-01",
            "installmentAmount": 200000.0
        },
        {
            "dueDate": "2026-03-01",
            "installmentAmount": 200000.0
        },
        {
            "dueDate": "2026-04-01",
            "installmentAmount": 200000.0
        },
        {
            "dueDate": "2026-05-01",
            "installmentAmount": 200000.0
        }
    ],
    "paid": false
}

```
</details>


<details>
<summary>POST /loan/{loanId}</summary>
<description>
  To make payments.
</description>

**Request Body:**
```json
{
    "amount": 1000
}
```
**response**:
```json
{
    "totalPaidAmount": 0,
    "totalPaidCount": 0,
    "loanPaid": false
}
```
</details>


<details>
<summary>GET /loan/{loanId}</summary>

**response**:
```json
{
    "loanAmount": 1000000.00,
    "installmentCount": 6,
    "createDate": "2025-11-06",
    "nextPaymentDate": null,
    "installmentAmount": 200000.00,
    "paid": false
}
```
</details>


<details>
<summary>GET /loan-installments/{loanId}</summary>

**response**:
```json
[
    {
        "dueDate": "2025-12-01",
        "installmentAmount": 200000.00
    },
    {
        "dueDate": "2026-01-01",
        "installmentAmount": 200000.00
    },
    {
        "dueDate": "2026-02-01",
        "installmentAmount": 200000.00
    },
    {
        "dueDate": "2026-03-01",
        "installmentAmount": 200000.00
    },
    {
        "dueDate": "2026-04-01",
        "installmentAmount": 200000.00
    },
    {
        "dueDate": "2026-05-01",
        "installmentAmount": 200000.00
    }
]
```
</details>


<details>
<summary>GET /loan?customerId={customerId}</summary>
<description>
  This endpoint lists all loans
</description>

**response**:
```json
[
    {
        "loanAmount": 1000000.00,
        "installmentCount": 6,
        "createDate": "2025-11-06",
        "nextPaymentDate": null,
        "installmentAmount": 200000.00,
        "paid": false
    }
]
```
</details>
---

## Unit Tests
App is just testing these cases;
  - Customer limit calculations
  - Loan and Installment creations
  - Payment calculations

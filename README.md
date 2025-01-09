You should stop docker container with PostgreSQL after!

# Payment API Documentation

## Overview
This API is designed to process payment requests. It requires Basic Authentication, with credentials provided in configuration files (`user.username` and `user.password`).

### Branch: `main`

#### POST `/pay`
Handles the creation of a payment.

**Request Body:**
Must contain a valid `PaymentDTO` in JSON format. Example:
```json
{
    "from": "me",
    "to": "you",
    "amount": 1
}
```

**Validation Rules:**
- `from`: Mandatory.
- `to`: Mandatory.
- `amount`: Must be greater than 0.

**Response:**
If the request is valid, the payment is recorded in the database, and the response is returned in XML format:
```xml
<Payment>
    <id>6a17af50-4ecd-4d1d-abff-42ec8f88bee4</id>
    <source>me</source>
    <destination>you</destination>
    <amount>1</amount>
    <operationDate>2025-01-09T21:08:52.1826158</operationDate>
</Payment>
```

If the data is invalid, an exception is returned in XML format. Example for an invalid request:
```json
{
    "to": "you",
    "amount": -1
}
```

**Error Response:**
```xml
<Map>
    <msg>There is an error</msg>
    <code>400</code>
    <time>2025-01-09 21:13:09</time>
    <errors>
        <amount>Amount should be more than zero!</amount>
        <from>Sender of payment should be provided!</from>
    </errors>
</Map>
```

Errors contain the field names and messages generated using `jakarta.validation.constraints`.

#### GET `/pay/{id}`
Retrieves a payment by its ID.

Example:
Request:
```
GET /pay/6a17af50-4ecd-4d1d-abff-42ec8f88bee4
```

Response:
```xml
<Payment>
    <id>6a17af50-4ecd-4d1d-abff-42ec8f88bee4</id>
    <source>me</source>
    <destination>you</destination>
    <amount>1</amount>
    <operationDate>2025-01-09T21:08:52.1826158</operationDate>
</Payment>
```

### Branch: `dev`

#### POST `/pay`
Handles the creation of a payment.

**Request Body:**
Must contain a valid `PaymentDTO` in JSON format without the `from` field. The `from` field is derived from the authenticated user's credentials.

Example:
```json
{
    "to": "you",
    "amount": 1
}
```

**Validation Rules:**
- `to`: Must exist in the database. Otherwise, an exception is returned.
- `amount`: Must be greater than 0.
- `operationDate`: Optional. If not provided, the current date will be used.

**Response:**
If the request is valid, the payment is recorded in the database, and the response is returned in XML format:
```xml
<Payment>
    <id>6a17af50-4ecd-4d1d-abff-42ec8f88bee4</id>
    <source>authenticatedUser</source>
    <destination>you</destination>
    <amount>1</amount>
    <operationDate>2025-01-09T21:08:52.1826158</operationDate>
</Payment>
```

**Error Response:**
If the recipient (`to`) does not exist in the database:
```xml
<Map>
    <msg>ReceiverOfPaymentNotFound</msg>
    <code>404</code>
    <time>2025-01-09 21:13:09</time>
</Map>
```

If other validation rules are violated, the response will follow the format used in the `main` branch.

**Request Body with Operation Date:**
If you need to specify the operation date:
```json
{
    "to": "Alice",
    "amount": 1,
    "operationDate": "2019-01-09T21:30:27"
}
```


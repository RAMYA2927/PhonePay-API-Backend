# PhonePay API Quick Guide

## Run the app

```bash
./gradlew bootRun
```

The API starts on `http://localhost:9091`. Make sure MongoDB is available at `mongodb://localhost:27017/phonepay` before running; you can spin up a local instance with:

```bash
docker compose up -d
```

## Postman setup

1. Import the collection file: `postman/PhonePay.postman_collection.json`
2. Ensure `baseUrl = http://localhost:9091`
3. Run requests in this order:
   - Create Wallet (alice)
   - Create Wallet (bob) by duplicating request and changing `userName`
   - Transfer Money
   - Transaction History By User

## API endpoints

- `POST /api/wallets`
  - Body:
  ```json
  {
    "userName": "alice",
    "initialBalance": 1000.00,
    "email": "alice@example.com",
    "phoneNumber": "9999999999"
  }
  ```
- `GET /api/wallets/{userName}`
- `GET /api/wallets`
- `POST /api/payments/transfer`
  - Body:
  ```json
  {
    "sender": "alice",
    "receiver": "bob",
    "amount": 150.25
  }
  ```
- `GET /api/payments/history?userName=alice`
- `GET /api/payments/history`

Compatibility aliases also work:

- `POST /api/wallets/create`
- `POST /api/payment/send`

## Inspect stored data

Point `mongosh` or MongoDB Compass at `mongodb://localhost:27017/phonepay` to read the collections that the API writes (`wallets` and `transactions`). For example:

```bash
mongosh mongodb://localhost:27017/phonepay --eval "db.wallets.find().pretty()"
mongosh mongodb://localhost:27017/phonepay --eval "db.transactions.find().pretty()"
```

## Response format

All responses are JSON with this structure:

```json
{
  "success": true,
  "message": "Wallet created successfully",
  "data": {},
  "timestamp": "2026-03-06T10:00:00.000"
}
```

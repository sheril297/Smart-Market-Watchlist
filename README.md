Smart Market Watchlist

A smart stock watchlist application that helps users do more than simply track stock prices. The application identifies **meaningful price changes since the user's last check** and highlights stocks that deserve immediate attention.

This project was developed as part of CODE BY GROW 2026.


Problem Statement

Traditional stock watchlists mainly display the latest market price. However, users often have to manually compare current prices with previously viewed prices to understand what has actually changed.

The Smart Market Watchlist solves this problem by:

* Tracking stocks added by the user
* Maintaining the previously observed price
* Calculating price and percentage changes
* Detecting meaningful changes automatically
* Highlighting stocks that require attention

The goal is to help users quickly answer:

"What has meaningfully changed since I last checked?"

---

 Key Idea

Instead of showing only the latest stock price, the application compares:

Current Price vs. Last Seen Price

and calculates:

text
Change Amount = Current Price - Last Seen Price

Change % = ((Current Price - Last Seen Price) / Last Seen Price) × 100


If the change crosses the configured meaningful-change threshold, the stock is highlighted in the dashboard.



 Features:

 Watchlist Management

Users can:

* Add stocks to their watchlist
* View all tracked stocks
* Update stock prices
* Delete stocks from the watchlist

Meaningful Change Detection:

The application calculates:

* Current price
* Previous/last-seen price
* Change amount
* Percentage change

Stocks with meaningful changes are visually highlighted.

 Last-Seen Price Tracking:

The system remembers the price that was previously observed by the user.

This allows the application to answer:

> "What changed since I last checked?"

rather than simply:

> "What is the current price?"

🟢🔴 Price Movement Indicators

Price movements are displayed using visual indicators:

* 🟢 Positive change
* 🔴 Negative change
* ⚠️ Meaningful change

 Simple Dashboard

The frontend provides a clean interface for:

* Adding stocks
* Viewing market changes
* Updating prices
* Removing stocks

 System Architecture


                  ┌─────────────────────────┐
                  │       User / Browser    │
                  └────────────┬────────────┘
                               │
                               │ HTTP Requests
                               ▼
                  ┌─────────────────────────┐
                  │       Frontend          │
                  │       index.html        │
                  │     HTML / CSS / JS     │
                  └────────────┬────────────┘
                               │
                               │ REST API
                               ▼
                  ┌─────────────────────────┐
                  │     Spring Boot API     │
                  │                         │
                  │      Controller         │
                  │          ↓              │
                  │        Service          │
                  │          ↓              │
                  │      Repository         │
                  └────────────┬────────────┘
                               │
                               │ JPA / Hibernate
                               ▼
                  ┌─────────────────────────┐
                  │       PostgreSQL        │
                  │                         │
                  │     Watchlist Data      │
                  └─────────────────────────┘
Technologies Used

 Frontend

* HTML5
* CSS3
* JavaScript
* Fetch API

Backend

* Java
* Spring Boot
* Spring Web
* Spring Data JPA
* Hibernate

Database

* PostgreSQL

Development Tools

* Git
* GitHub
* VS Code / IntelliJ IDEA
* Postman / cURL
* Maven

 Project Structure


Smart-Market-Watchlist/
│
├── backend/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/
│   │       │       └── scheduler/
│   │       │           └── distributed_job_scheduler/
│   │       │               ├── controller/
│   │       │               ├── service/
│   │       │               ├── repository/
│   │       │               ├── entity/
│   │       │               └── config/
│   │       │
│   │       └── resources/
│   │
│   ├── pom.xml
│   └── .gitignore
│
├── frontend/
│   └── index.html
│
└── README.md

REST API

The backend exposes REST APIs for managing the watchlist.

Get Watchlist

```http
GET /api/watchlist
```

Returns all stocks currently present in the watchlist.

---

Add Stock

```http
POST /api/watchlist
```

Example request:

```json
{
  "symbol": "RELIANCE",
  "companyName": "Reliance Industries",
  "currentPrice": 2525.00
}
```

---

 Update Stock Price

```http
PUT /api/watchlist/{id}/price
```

Example:

```json
{
  "currentPrice": 2600.00
}
```

The backend calculates the change based on the previously observed price.

---

 Delete Stock

```http
DELETE /api/watchlist/{id}
```

Removes a stock from the watchlist.

---

 Meaningful Change Detection

One of the main features of the project is identifying whether a stock has experienced a meaningful change.

For example:

```text
Last Seen Price = ₹2525

Current Price = ₹2600

Change = ₹75

Change % = 2.97%
```

The application evaluates this change against the meaningful-change criteria.

If the change is significant:

```text
⚠️ Meaningful change!
```

is displayed and the stock is highlighted in the dashboard.

This allows users to scan their watchlist quickly instead of manually calculating price movements.

---

 How to Run the Project

1. Clone the Repository

```bash
git clone <YOUR-GITHUB-REPOSITORY-URL>
```

Navigate into the project:

```bash
cd Smart-Market-Watchlist
```

---

 2. Configure PostgreSQL

Create a PostgreSQL database for the application.

Example:

```sql
CREATE DATABASE smart_market_watchlist;
```

Update the local database configuration in your environment/application configuration.

Do not commit database passwords or other secrets to GitHub.

---

 3. Start the Spring Boot Backend

Navigate to the backend directory:

```bash
cd backend
```

Run:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

The backend should start on:

```text
http://localhost:8080
```

---

 4. Start the Frontend

Open:

```text
frontend/index.html
```

in a browser.

The frontend communicates with:

```text
http://localhost:8080/api/watchlist
```

---

 Example Usage

 Step 1 — Add a Stock

Add:

```text
Symbol: RELIANCE
Company: Reliance Industries
Price: ₹2525
```

The stock appears in the watchlist.

 Step 2 — Simulate a Price Change

Update the price to:

```text
₹2600
```

 Step 3 — Check the Dashboard

The application calculates:

```text
Change: +₹75
Percentage Change: +2.97%
```

If the change meets the meaningful-change threshold, the stock is highlighted.



 User Interface

The dashboard is designed to provide a quick overview of the watchlist.

Each stock displays:

* Stock symbol
* Company name
* Current price
* Price change
* Percentage change
* Last price update
* Meaningful-change status
* Price update control
* Delete option

---

 Security Considerations

Sensitive configuration should **never be committed to GitHub**.

The following should be excluded from version control:

```text
application.properties
.env
target/
.idea/
*.iml
```

Database credentials should be supplied through environment variables or another secure configuration mechanism.

---

 Future Improvements

The current application focuses on the core smart-watchlist functionality. Future versions could include:

*  Real-time stock market API integration
*  Interactive price charts
*  Price-change notifications
*  Email alerts
*  Responsive mobile UI
*  AI-generated market summaries
*  News integration for watched stocks
*  Historical price analysis
*  User-defined meaningful-change thresholds
*  Multiple watchlists
*  User authentication
*  Cloud deployment using AWS
*  Volatility-based meaningful-change detection

---

Project Highlights

This project demonstrates practical implementation of:

* REST API development
* Spring Boot backend development
* CRUD operations
* PostgreSQL database integration
* JPA/Hibernate
* Frontend-backend integration
* Change detection logic
* Git/GitHub version control
* Basic financial-data visualization concepts

---

 Author

Sheril T Mario

Computer Science Engineering — Artificial Intelligence & Machine Learning

---

Conclusion

The Smart Market Watchlist transforms a traditional stock watchlist into an attention-focused monitoring tool.

Instead of forcing users to manually compare prices, the system identifies what has changed since the previous check and brings meaningful changes to the user's attention.

Track less. Understand more.

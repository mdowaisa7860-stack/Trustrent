Backend Schema — TrustRent V1
This document defines the database schema, relationships, indexes, authorization model, API contract, storage, and seed data for TrustRent V1. It follows the template's required Backend Schema structure: entities, relationships, indexes, auth, APIs, storage, and seed data.
This schema includes the important feature you added: monthly rent tracking with EARLY / ON_TIME / LATE / MISSED payment states that feed the Trust Score. The original TrustRent concept explicitly calls for tracking payment dates, late payments, missed payments, and payment consistency as part of the Tenant Trust Score.

1. Database
Database: PostgreSQL
Use UUID primary keys for externally exposed entities.
PostgreSQL
    │
    ├── users
    ├── properties
    ├── property_images
    ├── rental_applications
    ├── rentals
    ├── rent_payments
    ├── trust_scores
    ├── trust_events
    └── reviews

2. Entities
2.1 users
Stores tenants and landlords.
users
------------------------------------------------
id                  uuid PK
email               varchar(255) UNIQUE NOT NULL
password_hash       varchar(255) NOT NULL
name                varchar(120) NOT NULL
phone               varchar(30)
role                varchar(20) NOT NULL
profile_image_url   text
city                varchar(100)
email_verified      boolean NOT NULL DEFAULT false
phone_verified      boolean NOT NULL DEFAULT false
created_at          timestamptz NOT NULL DEFAULT now()
updated_at          timestamptz NOT NULL DEFAULT now()
role
TENANT
LANDLORD
A user has exactly one role in V1.

3. properties
Stores landlord-created rental properties.
properties
------------------------------------------------
id                    uuid PK
landlord_id           uuid FK -> users.id NOT NULL
title                 varchar(200) NOT NULL
description           text NOT NULL
location              varchar(255) NOT NULL
city                  varchar(100) NOT NULL
monthly_rent          numeric(12,2) NOT NULL
security_deposit      numeric(12,2) NOT NULL
maintenance_charge   numeric(12,2) DEFAULT 0
parking_charge        numeric(12,2) DEFAULT 0
water_charge          numeric(12,2) DEFAULT 0
electricity_charge    numeric(12,2) DEFAULT 0
internet_charge       numeric(12,2) DEFAULT 0
bedrooms              integer NOT NULL
bathrooms             integer NOT NULL
property_type         varchar(50) NOT NULL
amenities             jsonb
rules                 text
status                varchar(30) NOT NULL
created_at            timestamptz NOT NULL DEFAULT now()
updated_at            timestamptz NOT NULL DEFAULT now()
status
AVAILABLE
UNAVAILABLE
RENTED
A property should normally be marked unavailable rather than permanently deleting historical rental information.

4. property_images
A property can have multiple images.
property_images
------------------------------------------------
id              uuid PK
property_id     uuid FK -> properties.id NOT NULL
image_url       text NOT NULL
display_order   integer NOT NULL DEFAULT 0
created_at      timestamptz NOT NULL DEFAULT now()
Relationship:
Property 1 ─────── N PropertyImages

5. rental_applications
Stores tenant applications.
rental_applications
------------------------------------------------
id               uuid PK
property_id      uuid FK -> properties.id NOT NULL
tenant_id        uuid FK -> users.id NOT NULL
status           varchar(20) NOT NULL
message          text
applied_at       timestamptz NOT NULL DEFAULT now()
updated_at       timestamptz NOT NULL DEFAULT now()
status
PENDING
ACCEPTED
REJECTED
WITHDRAWN
For V1, the main flow is:
PENDING → ACCEPTED
PENDING → REJECTED

6. rentals
A rental is created after an application is accepted.
rentals
------------------------------------------------
id               uuid PK
property_id      uuid FK -> properties.id NOT NULL
tenant_id        uuid FK -> users.id NOT NULL
landlord_id      uuid FK -> users.id NOT NULL
application_id   uuid FK -> rental_applications.id UNIQUE NOT NULL
start_date       date NOT NULL
end_date         date
monthly_rent     numeric(12,2) NOT NULL
status           varchar(20) NOT NULL
created_at       timestamptz NOT NULL DEFAULT now()
updated_at       timestamptz NOT NULL DEFAULT now()
status
ACTIVE
COMPLETED
CANCELLED
The monthly_rent is stored on the rental so historical rentals retain the agreed rental amount even if the property's current listing price changes later.

7. rent_payments
This is one of the most important tables in TrustRent.
It stores each monthly rent obligation/payment.
rent_payments
------------------------------------------------
id                 uuid PK
rental_id          uuid FK -> rentals.id NOT NULL
billing_month      date NOT NULL
due_date           date NOT NULL
expected_amount    numeric(12,2) NOT NULL
paid_amount        numeric(12,2)
payment_date       date
status             varchar(20) NOT NULL
recorded_by        uuid FK -> users.id
notes              text
created_at         timestamptz NOT NULL DEFAULT now()
updated_at         timestamptz NOT NULL DEFAULT now()
status
PENDING
EARLY
ON_TIME
LATE
MISSED
The UI mainly displays:
EARLY
ON TIME
LATE
MISSED
PENDING is an internal state before the due/payment outcome is finalized.

8. Payment Rules
The backend determines the status.
Conceptually:
payment_date < due_date
        ↓
      EARLY

payment_date = due_date
        ↓
     ON_TIME

payment_date > due_date
        ↓
       LATE
MISSED is assigned only after the configured overdue threshold has been reached.
This threshold is a product decision and should be configurable rather than hardcoded throughout the codebase.
Important
The React frontend must never calculate the final payment status or Trust Score.
The backend does it.

9. trust_scores
Stores the current score for a user.
trust_scores
------------------------------------------------
id                    uuid PK
user_id               uuid FK -> users.id UNIQUE NOT NULL
score                 integer NOT NULL
payment_score         integer NOT NULL
rental_history_score  integer NOT NULL
review_score          integer NOT NULL
care_score            integer NOT NULL
compliance_score      integer NOT NULL
complaint_score       integer NOT NULL
last_calculated_at    timestamptz NOT NULL
created_at            timestamptz NOT NULL
updated_at            timestamptz NOT NULL
Constraints
score >= 0
score <= 1000
Each component should also stay within its configured allowed range.

10. trust_events
This is extremely important.
Don't only store the current Trust Score.
Store why it changed.
trust_events
------------------------------------------------
id                 uuid PK
user_id            uuid FK -> users.id NOT NULL
event_type         varchar(50) NOT NULL
reference_type     varchar(50)
reference_id       uuid
score_change       integer NOT NULL
description        text NOT NULL
created_at         timestamptz NOT NULL DEFAULT now()
Possible event_type
RENT_PAID_EARLY
RENT_PAID_ON_TIME
RENT_PAID_LATE
RENT_MISSED

RENTAL_COMPLETED

POSITIVE_REVIEW
NEGATIVE_REVIEW

PROPERTY_CARE_POSITIVE
PROPERTY_CARE_NEGATIVE

COMPLIANCE_POSITIVE
COMPLIANCE_NEGATIVE

VERIFIED_COMPLAINT
COMPLAINT_RESOLVED
Example:
user_id: 123
event_type: RENT_PAID_EARLY
score_change: +10
description: "August rent paid 2 days before due date."
This allows the Trust History UI to say:
August 2027
Rent paid early
+10

11. reviews
Stores two-sided rental reviews.
reviews
------------------------------------------------
id                    uuid PK
rental_id             uuid FK -> rentals.id NOT NULL
reviewer_id           uuid FK -> users.id NOT NULL
reviewed_user_id      uuid FK -> users.id NOT NULL

maintenance_rating    integer
responsiveness_rating integer
communication_rating  integer
property_care_rating  integer
rule_compliance_rating integer
overall_rating        integer NOT NULL

comment               text
created_at            timestamptz NOT NULL DEFAULT now()
updated_at            timestamptz NOT NULL DEFAULT now()
Not every rating field applies equally to both user types.
For example:
Tenant reviews landlord
maintenance
responsiveness
communication
facility quality
overall
Landlord reviews tenant
property care
communication
rule compliance
overall
We can keep one table while using only relevant fields based on reviewer/reviewed roles.

12. Relationship Diagram
                         USERS
                  ┌────────┴────────┐
                  │                 │
               TENANT            LANDLORD
                  │                 │
                  │                 │
                  │            1:N │
                  │                 ↓
                  │            PROPERTIES
                  │                 │
                  │                 │ 1:N
                  │                 ↓
                  │          PROPERTY_IMAGES
                  │
                  │ 1:N
                  ↓
          RENTAL_APPLICATIONS
                  │
                  │ 1:1
                  ↓
                RENTALS
              /    │    \
             /     │     \
            ↓      ↓      ↓
        TENANT  LANDLORD  PROPERTY
                   │
                   ↓
             RENT_PAYMENTS
                   │
                   ↓
              TRUST_EVENTS
                   │
                   ↓
              TRUST_SCORE

                RENTALS
                   │
                   ↓
                REVIEWS
              /         \
             ↓           ↓
          TENANT      LANDLORD

13. Relationship Rules
User → Property
User (LANDLORD) 1:N Property
A landlord can own multiple properties.
A property has exactly one owner.

Property → Applications
Property 1:N RentalApplication
A property can receive multiple applications.

Tenant → Applications
Tenant 1:N RentalApplication
A tenant can apply to multiple properties.
Therefore:
Tenant ↔ Property
is effectively an N:N relationship through rental_applications.

Application → Rental
Application 1:0..1 Rental
Only an accepted application creates a rental.

Rental → Rent Payments
Rental 1:N RentPayment
One rental contains many monthly rent records.

User → TrustScore
User 1:1 TrustScore
Every registered user has one current Trust Score record.

User → TrustEvents
User 1:N TrustEvent
A user can accumulate many score-affecting events.

Rental → Reviews
Rental 1:N Reviews
For V1, allow at most:
1 tenant → landlord review
1 landlord → tenant review
per completed rental.

14. Cascade Rules
Property deletion
Do not cascade-delete rental history.
Prefer:
Property → status = UNAVAILABLE
Property images
If a property is permanently removed in an administrative operation:
Property → PropertyImages
can use cascade delete.
Application
Deleting an application should not automatically delete a rental.
Historical rental records must remain intact.
Trust events
Never cascade-delete Trust Events just because a profile or property is changed.
They are historical records.

15. Indexes
Indexes should support the queries the application actually performs.
Users
users(email)
users(role)
email already has a unique index.

Properties
properties(landlord_id)
properties(city)
properties(status)
properties(monthly_rent)
properties(bedrooms)
properties(property_type)
For common search combinations:
properties(city, status)
properties(city, monthly_rent)

Applications
rental_applications(tenant_id, created_at DESC)
rental_applications(property_id, status)

Rentals
rentals(tenant_id, status)
rentals(landlord_id, status)
rentals(property_id)

Rent payments
rent_payments(rental_id, billing_month)
rent_payments(rental_id, due_date)
rent_payments(status)
Important uniqueness:
UNIQUE(rental_id, billing_month)
This prevents two payment records for the same rental/month.

Trust Events
trust_events(user_id, created_at DESC)
trust_events(user_id, event_type)
trust_events(reference_type, reference_id)

Reviews
reviews(reviewed_user_id, created_at DESC)
reviews(reviewer_id)
reviews(rental_id)
Also enforce the one-review-per-direction-per-rental rule at the database/service level.

16. Auth Model
Authentication:
JWT
+
Spring Security
Roles:
TENANT
LANDLORD

17. Permission Model
TENANT can
View available properties
View permitted landlord Trust Profiles
Create applications
View own applications
View own rentals
View own payment history
Record permitted payment information
View own Trust Profile
Review eligible landlords
LANDLORD can
Create properties
Edit own properties
Delete/deactivate own properties
View applications for own properties
View permitted tenant Trust Profiles
Accept/reject own applications
View own rentals
View related rent records
View own Trust Profile
Review eligible tenants

18. Server-side Authorization
Every protected API must verify:
Who is the user?
What role do they have?
What resource are they trying to access?
Do they own/have permission for that resource?
Example:
PUT /api/properties/{propertyId}
must verify:
authenticated user
        +
role = LANDLORD
        +
property.landlord_id = currentUser.id
Never trust:
{
  "landlordId": 123
}
from the frontend as proof of ownership.

19. Row-Level Security
Because the backend is Spring Security + PostgreSQL rather than Supabase, the primary authorization mechanism is:

Do not rely on PostgreSQL RLS as the primary V1 security mechanism.
The template asks for an auth/row-level-security section; for this architecture, the equivalent resource-level authorization will be implemented in Spring Security and the service layer.

20. API Contract
Authentication
Register
POST /api/auth/register
Request:
{
  "name": "John Kumar",
  "email": "john@example.com",
  "phone": "9876543210",
  "password": "securePassword",
  "role": "TENANT"
}
Response:
{
  "userId": "uuid",
  "name": "John Kumar",
  "role": "TENANT",
  "accessToken": "jwt"
}

Login
POST /api/auth/login
Request:
{
  "email": "john@example.com",
  "password": "securePassword"
}
Response:
{
  "userId": "uuid",
  "name": "John Kumar",
  "role": "TENANT",
  "accessToken": "jwt"
}

21. User APIs
Current profile
GET /api/users/me
Update profile
PUT /api/users/me
View permitted public profile
GET /api/users/{userId}
Trust Profile
GET /api/users/{userId}/trust-profile
Rental history
GET /api/users/{userId}/rental-history
Reviews
GET /api/users/{userId}/reviews

22. Property APIs
Create
POST /api/properties
Search/list
GET /api/properties
Query parameters:
city
minRent
maxRent
bedrooms
propertyType
status
Example:
GET /api/properties?city=Bangalore&maxRent=25000&bedrooms=2
Get one
GET /api/properties/{propertyId}
Update
PUT /api/properties/{propertyId}
Delete/deactivate
DELETE /api/properties/{propertyId}
In V1, deletion should generally make the property unavailable rather than destroy historical rental data.

23. Application APIs
Apply
POST /api/applications
Request:
{
  "propertyId": "uuid",
  "message": "I am interested in renting this property."
}
My applications
GET /api/applications/my
Received applications
GET /api/applications/received
Accept
PUT /api/applications/{id}/accept
Request:
{
  "startDate": "2027-01-01",
  "endDate": "2027-12-31"
}
This operation:
Application
   ↓
ACCEPTED
   ↓
Create Rental
Reject
PUT /api/applications/{id}/reject

24. Rental APIs
My rentals
GET /api/rentals/my
Rental detail
GET /api/rentals/{rentalId}
Complete rental
For V1 this should be controlled by a defined completion workflow rather than allowing arbitrary users to mark rentals completed.

25. Rent Payment APIs
Payment history
GET /api/rentals/{rentalId}/payments
Response:
[
  {
    "billingMonth": "2027-08-01",
    "dueDate": "2027-08-05",
    "expectedAmount": 18000,
    "paidAmount": 18000,
    "paymentDate": "2027-08-03",
    "status": "EARLY"
  }
]
Record payment
POST /api/rentals/{rentalId}/payments
Request:
{
  "billingMonth": "2027-08-01",
  "paidAmount": 18000,
  "paymentDate": "2027-08-03"
}
Backend:
Validate rental
       ↓
Validate month
       ↓
Calculate status
       ↓
Save payment
       ↓
Create Trust Event
       ↓
Recalculate Trust Score

26. Trust APIs
Current score
GET /api/trust/{userId}
Response:
{
  "score": 847,
  "level": "HIGHLY_TRUSTED",
  "paymentReliability": 92,
  "completedRentals": 3,
  "averageRating": 4.6,
  "verifiedComplaints": 0
}
Trust event history
GET /api/trust/{userId}/events
Response:
[
  {
    "type": "RENT_PAID_EARLY",
    "scoreChange": 10,
    "description": "August rent paid early",
    "createdAt": "2027-08-03T10:30:00Z"
  }
]
Users cannot directly modify either endpoint's score data.

27. Review APIs
Create review
POST /api/reviews
Request:
{
  "rentalId": "uuid",
  "overallRating": 5,
  "maintenanceRating": 5,
  "responsivenessRating": 4,
  "communicationRating": 5,
  "comment": "Good rental experience."
}
The backend determines whether those fields are valid for the user's role.
List reviews
GET /api/users/{userId}/reviews

28. API Error Format
All APIs should use a consistent error response.
Example:
{
  "timestamp": "2027-08-03T10:30:00Z",
  "status": 403,
  "code": "FORBIDDEN",
  "message": "You do not have permission to modify this property."
}
Validation:
{
  "status": 400,
  "code": "VALIDATION_ERROR",
  "message": "Monthly rent must be greater than 0.",
  "fieldErrors": {
    "monthlyRent": "Must be greater than 0"
  }
}

29. Storage
Property images require object storage.
Bucket
property-images
Path
properties/{property_id}/{image_id}.{extension}
Example:
properties/
  7f1.../
    8c2...jpg
    1a4...jpg
The database stores the resulting image URL.

30. Storage Rules
Allowed:
jpg
jpeg
png
webp
Set a reasonable V1 image-size limit, for example:
10 MB per image
Backend should validate:
file type
file size
property ownership
Do not trust the filename or MIME type supplied by the browser alone.

31. Seed Data
The application should ship with enough data to demonstrate the complete TrustRent flow.
Users
Create:
3 tenants
3 landlords
Example:
Tenant 1
Trust Score: 847

Tenant 2
Trust Score: 731

Landlord 1
Trust Score: 912

Landlord 2
Trust Score: 805
These are development seed values only.

32. Seed Properties
Create around:
8–12 properties
distributed across a few cities/areas.
Example:
2 BHK Bangalore
1 BHK Bangalore
3 BHK Bangalore
2 BHK Hyderabad
1 BHK Hyderabad
2 BHK Chennai
Each should have:
images
rent
deposit
maintenance
amenities
rules
landlord

33. Seed Rental Data
Create a few historical rentals so Trust Profiles aren't empty in development.
Example:
Tenant A
3 completed rentals

Landlord A
5 completed rentals

34. Seed Payment Data
Include mixed payment behavior:
January    EARLY
February   ON_TIME
March      ON_TIME
April      LATE
May        EARLY
June       MISSED
This lets the UI demonstrate all payment states.

35. Seed Reviews
Create legitimate-looking development reviews attached to seeded rentals.
Example:
Tenant → Landlord
5 / 5

Landlord → Tenant
4 / 5
Do not seed reviews disconnected from rental relationships.

36. Trust Score Calculation Architecture
Use:
TrustScoreService
with smaller calculators if necessary:
PaymentTrustCalculator
RentalHistoryCalculator
ReviewTrustCalculator
ComplianceTrustCalculator
ComplaintTrustCalculator
Conceptually:
                    TrustScoreService
                           │
        ┌──────────────────┼──────────────────┐
        ↓                  ↓                  ↓
 Payment History     Rental History      Reviews
        │                  │                  │
        └──────────────────┼──────────────────┘
                           ↓
                     Overall Score
This is more maintainable than writing one giant scoring method.

37. Trust Score Update Rules
A trust event should be generated for meaningful activity.
Example:
Rent EARLY
     ↓
Trust Event
     ↓
Payment score changes
     ↓
Overall score recalculated
Same for:
Late payment
Missed payment
Completed rental
Eligible review
Verified negative event
Do not let the frontend send:
{
  "score": 950
}
The backend determines it.

38. Important Data Integrity Rules
One payment per rental/month
UNIQUE(rental_id, billing_month)
One tenant application per property while an earlier active application exists
Prevent duplicate active applications.
One review in each direction per rental
rental + reviewer + reviewed_user
must be unique.
Score bounds
0 <= score <= 1000
Money
Use:
NUMERIC(12,2)
not floating-point types.
Dates
Use proper PostgreSQL date/timestamptz, not strings.

39. V1 Data Flow
Property
Landlord
 ↓
React Form
 ↓
POST /api/properties
 ↓
Spring Boot
 ↓
Validation
 ↓
PropertyService
 ↓
PostgreSQL
Application
Tenant
 ↓
Apply
 ↓
Application API
 ↓
Validate
 ↓
Save PENDING
 ↓
Landlord sees application
Monthly Rent
Payment recorded
       ↓
Payment status calculated
       ↓
rent_payments
       ↓
trust_events
       ↓
TrustScoreService
       ↓
trust_scores
Review
Review submitted
       ↓
Verify rental relationship
       ↓
Save review
       ↓
Create trust event
       ↓
Recalculate score

40. What Is Explicitly Not in This Schema
Do not add tables for:
❌ AI damage detection
❌ 360° tours
❌ Video inspections
❌ Escrow
❌ Wallet
❌ Bank accounts
❌ Payment gateway transactions
❌ Digital signatures
❌ Complex disputes
❌ Machine learning
❌ Social messaging
Those remain outside V1.

41. Final V1 Schema
users
 │
 ├──────────────┐
 │              │
 │          properties
 │              │
 │         property_images
 │
 ├── trust_scores
 │
 ├── trust_events
 │
 ├── reviews
 │
 ├── rental_applications
 │              │
 │              ↓
 │           rentals
 │              │
 │              ↓
 │        rent_payments
 │
 └── rental_history
      (derived from rentals)
More precisely:
USER
 │
 ├── 1:N → PROPERTY
 │            └── 1:N → PROPERTY_IMAGE
 │
 ├── 1:N → RENTAL_APPLICATION
 │            └── 1:0..1 → RENTAL
 │                              └── 1:N → RENT_PAYMENT
 │
 ├── 1:1 → TRUST_SCORE
 │
 ├── 1:N → TRUST_EVENT
 │
 └── 1:N → REVIEW

42. Backend Schema Rules for the AI
Give these rules to the coding agent together with the schema:
1. This schema is the source of truth for the TrustRent backend.
2. Do not create new entities without updating this schema first.
3. Do not move Trust Score calculation to React.
4. Do not allow clients to submit or modify Trust Scores directly.
5. All payment-status calculation happens on the backend.
6. Every monthly rental period can have at most one rent payment record.
7. Trust Events are historical records and must not be silently deleted.
8. Completed rental history must remain available.
9. All ownership checks happen server-side.
10. Tenant and landlord permissions must be enforced server-side.
11. Reviews require an eligible rental relationship.
12. Never use floating-point types for monetary values.
13. Use UUIDs for entity IDs.
14. Use database indexes for common property/search/rental queries.
15. Do not add V2 features to the V1 schema.
16. Any schema change requires updating this document first.
This matches the purpose of the template: the Backend Schema is intended to be the data and API contract, written before implementation, so the AI has a fixed skeleton rather than inventing tables and endpoints while coding.
Next: 06-Implementation-Plan.md — the last foundation document. It should break this exact TrustRent system into small, testable coding tasks in the order we will actually build them, with one AI-ready task per step. The template specifically recommends slicing the implementation into phases and tasks small enough to hand to an AI one prompt at a time.
. Audit trail
Make this Change:
Backend Schema → Trust Events
You already have trust_events, and your schema explicitly says historical Trust Events should not be silently deleted.
Expand the rules:
### Audit Rules

Record important business events such as:
- Application accepted/rejected
- Rental created
- Rental completed
- Payment recorded
- Payment status calculated
- Review submitted
- Trust Score recalculated

Historical Trust Events are immutable.
I would not create a giant generic audit table just for the sake of it unless the TRD requires one. Your existing event architecture may be enough for V1.

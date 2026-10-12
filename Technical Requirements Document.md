Technical Requirements Document (TRD) — TrustRent V1
This TRD converts the locked TrustRent PRD into concrete engineering requirements. The structure follows your uploaded template: stack, architecture, third-party services, non-functional requirements, compliance, constraints, and open questions.

1. Stack
Frontend
React.js + JavaScript
Supporting libraries:
React Router — routing
Axios — REST API communication
Tailwind CSS — styling
React Context — authentication/user state where appropriate
The frontend is responsible for:
UI
Routing
Forms
Client-side validation
API calls
Loading/error states
User session state
It must not contain Trust Score calculation or security-sensitive business rules.

Backend
Java 21 + Spring Boot
Use:
Spring Web
Spring Data JPA
Hibernate
Spring Security
Bean Validation
Maven
Backend responsibilities:
Authentication
Authorization
Business logic
Trust Score calculation
Property management
Applications
Rental records
Rent tracking
Reviews
Database access
Validation
All important business rules must be enforced on the backend.

Database
PostgreSQL
Primary database for:
Users
Properties
Applications
Rentals
Monthly rent records
Trust Scores
Trust-score events/factors
Reviews
Property images metadata

Authentication
JWT-based authentication using Spring Security
Authentication flow:
React
  ↓
Login API
  ↓
Spring Security
  ↓
Validate credentials
  ↓
Generate JWT
  ↓
React stores session token
  ↓
JWT sent with protected API requests
Passwords must be hashed using a secure password encoder such as BCrypt.

Hosting
For V1:
Frontend: Vercel or equivalent static/frontend hosting
Backend: Render, Railway, Fly.io, or another Java-capable cloud platform
Database: Managed PostgreSQL such as Neon, Supabase PostgreSQL, or Railway PostgreSQL.
We should choose one actual provider before deployment rather than designing the application around provider-specific functionality.

Payments
None in V1.
The system tracks rent-payment information, but V1 does not process real money.
Payment tracking       ✅
Payment date           ✅
Due date               ✅
Early/on-time/late     ✅
Missed payment         ✅
Actual payment gateway ❌
Wallet                 ❌
Escrow                 ❌
This preserves the monthly rent-tracking feature from the PRD without introducing financial infrastructure.

AI
None in V1.
No AI is required for the core TrustRent product.
The original concept contains advanced property/video analysis, but those features were deliberately excluded from V1.

2. Architecture
Pattern
Modular monolith
Not microservices.
For this project, one Spring Boot backend is sufficient.
                       Internet
                          │
              ┌───────────┴───────────┐
              │                       │
         React Frontend          Spring Boot API
              │                       │
              │      REST/JSON        │
              └───────────┬───────────┘
                          │
                       JPA / SQL
                          │
                          ↓
                     PostgreSQL
Why modular monolith?
V1 has a manageable scope. Separating authentication, properties, trust, applications and reviews into independent services would add deployment and operational complexity without providing a clear benefit.
Keep logical modules inside the same backend:
Spring Boot
│
├── auth
├── users
├── trust
├── properties
├── applications
├── rentals
├── payments
└── reviews
payments here means rent records/payment tracking, not payment processing.

3. Client / Server Boundary
React handles
Page rendering
Navigation
Forms
UI validation
Displaying API data
Loading states
Error states
Authentication state
Spring Boot handles
Authentication
Authorization
Database access
Trust Score calculation
Application rules
Rental rules
Review eligibility
Payment-status calculation
Data validation
Ownership checks
PostgreSQL handles
Persistent data
Relationships
Constraints
Indexes
Transactions
The React frontend must never be trusted for business rules.
For example, React may display:
Trust Score: 850
but the server must calculate and provide that score.

4. Core Technical Modules
Authentication Module
Responsibilities:
Registration
Login
Logout/session invalidation strategy
Password hashing
JWT generation
Role handling
Roles:
TENANT
LANDLORD

User Module
Handles:
Profile
Contact information
Role
Verification status
Trust Profile
Rental history summary

Property Module
Handles:
Create property
Read property
Update property
Delete property
Search
Filtering
Availability
Property images

Application Module
Handles:
Tenant applies
Application retrieval
Accept
Reject
Application status
Duplicate application prevention

Rental Module
Handles the relationship created after application acceptance.
Tenant
Landlord
Property
Start date
End date
Monthly rent
Status

Rent Tracking Module
This is a major TrustRent requirement.
For every active rental:
Monthly Rent Record
├── Due date
├── Expected amount
├── Payment date
├── Paid amount
└── Status
Status:
EARLY
ON_TIME
LATE
MISSED
The backend calculates this status based on the due date and recorded payment date.

Trust Module
Handles:
Trust Score
Score factors
Trust history
Score recalculation
Score explanation
A score cannot be edited directly by the user.

Review Module
Handles:
Review creation
Review eligibility
Rating
Comments
Duplicate-review prevention
Trust Score contribution

5. Trust Score Technical Requirements
The Trust Score must be:
0 ≤ score ≤ 1000
The score should be generated by a backend service:
TrustScoreService
Example flow:
Rent payment recorded
        ↓
Payment status calculated
        ↓
Trust event created
        ↓
Trust score recalculated
        ↓
Updated Trust Profile
The system should maintain the history of score-affecting events, rather than only storing the latest score.
Example:
Trust Event
────────────────────
Type: RENT_PAYMENT
Month: August
Status: EARLY
Impact: +10
Created: 03-Aug-2027
This gives us auditability later.

6. Monthly Rent Tracking Requirements
For each monthly obligation the backend must store:
rentalId
month
dueDate
expectedAmount
paymentDate
paidAmount
status
Status calculation
Conceptually:
paymentDate < dueDate
        ↓
      EARLY

paymentDate = dueDate
        ↓
     ON_TIME

paymentDate > dueDate
        ↓
       LATE

No payment after defined deadline
        ↓
      MISSED
The exact "missed" cutoff must be specified before implementation rather than being guessed by the frontend.

7. Trust Score Transparency
The API should return both:
score
and:
scoreFactors
Example:
{
  "score": 847,
  "paymentReliability": 91,
  "completedRentals": 3,
  "averageRating": 4.6,
  "verifiedComplaints": 0
}
The UI can then explain:
847 / 1000

Payment reliability: Excellent
Completed rentals: 3
Average rating: 4.6/5
Verified complaints: 0

8. Trust Score Security Requirements
Users cannot:
❌ Set their own score
❌ Edit payment records for completed months
❌ Create fake rental history
❌ Review themselves
❌ Review someone without an eligible rental relationship
The backend must enforce these rules.

9. REST API
Authentication
POST /api/auth/register
POST /api/auth/login
GET  /api/auth/me

Users
GET /api/users/me
PUT /api/users/me
GET /api/users/{id}
GET /api/users/{id}/trust-profile
GET /api/users/{id}/rental-history

Properties
POST   /api/properties
GET    /api/properties
GET    /api/properties/{id}
PUT    /api/properties/{id}
DELETE /api/properties/{id}

Applications
POST /api/applications
GET  /api/applications/my
GET  /api/applications/received
PUT  /api/applications/{id}/accept
PUT  /api/applications/{id}/reject

Rentals
GET /api/rentals/my
GET /api/rentals/{id}

Rent tracking
GET  /api/rentals/{rentalId}/payments
POST /api/rentals/{rentalId}/payments
For V1, POST records the payment information. It does not move money.

Trust
GET /api/trust/{userId}
GET /api/trust/{userId}/history

Reviews
POST /api/reviews
GET  /api/users/{userId}/reviews

10. API Rules
Every endpoint must perform server-side validation.
Examples:
Tenant cannot create property
Landlord cannot submit tenant application as tenant
User cannot edit another user's property
User cannot accept another landlord's application
User cannot create review without eligible rental
User cannot access private information without permission

11. Database Requirements
Initial core tables:
users
properties
property_images
rental_applications
rentals
rent_payments
trust_scores
trust_events
reviews
This expands slightly from our earlier six-entity plan because monthly rent tracking requires persistent rental and payment records.
That change is justified by the revised PRD.

12. Storage
Property images require file storage.
V1 can use:
Object storage / cloud storage
Database stores only:
image URL
property ID
metadata
Not the actual image binary.
Example:
Property
   ↓
PropertyImage
   ↓
https://storage/.../property-123-image-1.jpg

13. Third-Party Services
The uploaded template recommends explicitly documenting third-party services and their fallbacks so an AI coding agent does not invent unsupported integrations.

14. Non-Functional Requirements
Performance
Target:
Typical API p95: < 500 ms
For simple read endpoints under normal development-scale load.
Search endpoints should use database indexes rather than loading all properties into memory.

Availability
V1 target:
99% monthly availability
This is an engineering target for the initial deployment, not a guarantee.

Security
Required:
HTTPS in production
JWT authentication
Password hashing
Input validation
Authorization checks
SQL injection protection through JPA/parameterized queries
CORS configuration
Secure secrets management
No passwords in logs
No JWT secrets in source code
The template specifically calls for HTTPS, OWASP review and secure secret handling.

Accessibility
Target:
WCAG 2.1 AA
Requirements include:
Keyboard navigation
Visible focus states
Accessible forms
Meaningful labels
Adequate contrast
ARIA where required

Responsiveness
The React application must work on:
Desktop
Tablet
Mobile browser
This does not mean a separate mobile application.

15. Data Integrity Requirements
Important records should not be silently deleted.
For example:
Completed rental
     ↓
Should remain in rental history
A property can be marked unavailable rather than destroying associated historical information.
Reviews and rent records should be treated as historical/auditable records.

16. Authorization Model
Tenant
Can:
View public properties
Apply to properties
View own applications
View permitted Trust Profiles
View own rental history
Record permitted rent payments
Review eligible landlords
Landlord
Can:
Create/manage own properties
View received applications
View permitted tenant Trust Profiles
Accept/reject own applications
View own rentals
Review eligible tenants
User
Cannot:
Modify another user's profile
Modify another user's Trust Score
Modify another user's rental history
Modify another user's property

17. Compliance
For V1, we will not claim formal GDPR/SOC 2 compliance.
We should still implement reasonable privacy/security practices:
Collect only required information
Protect credentials
Restrict access to personal data
Provide basic account/data management
Avoid exposing unnecessary private information
Government-ID verification is not part of V1, so V1 does not require storing government identity documents.

18. Constraints
Budget
Development should target:
₹0–₹2,000/month initially
using free tiers/local development where practical.
Actual production costs depend on traffic and provider pricing.
Skills
Expected development skills:
Java
Spring Boot
React.js
JavaScript
PostgreSQL
REST APIs
Git
Basic cloud deployment
Not required for V1:
Machine learning
AI model development
Microservices
Kubernetes
DevOps infrastructure engineering
Payment infrastructure
Computer vision
Architecture constraint
Use:

not microservices.
Scope constraint
No feature outside the approved PRD should be added without updating the PRD first.
The source template specifically recommends updating the specifications first when scope changes rather than modifying the code directly.

19. Development Environment
Backend
Java 21
Spring Boot
Maven
IntelliJ IDEA
Postman
Frontend
Node.js
React
Vite
VS Code
Database
PostgreSQL
pgAdmin
Source control
Git
GitHub

20. Environment Configuration
Never hard-code:
Database password
JWT secret
Cloud credentials
API keys
Use environment variables:
DATABASE_URL
DATABASE_USERNAME
DATABASE_PASSWORD
JWT_SECRET
STORAGE_ACCESS_KEY
STORAGE_SECRET_KEY
Development and production configurations must be separate.

21. Testing Requirements
Backend
Test:
Authentication
Authorization
Property CRUD
Applications
Trust Score
Rent classification
Reviews
Ownership
Validation
Frontend
Test:
Forms
Navigation
Protected routes
Loading states
Error states
Property search
Application flow
Trust Profile display
End-to-end critical path
Must successfully test:
Register
 ↓
Login
 ↓
Create Trust Profile
 ↓
Landlord lists property
 ↓
Tenant searches
 ↓
Tenant views landlord Trust Profile
 ↓
Tenant applies
 ↓
Landlord accepts
 ↓
Rental created
 ↓
Monthly rent recorded
 ↓
Payment classified
 ↓
Trust Score updated
 ↓
Review submitted

22. Open Questions
These should be decided before implementation reaches the affected feature.
Q1 — Exact Trust Score weights
Who defines the final point values?
Owner: Product/design decision

Q2 — Definition of MISSED payment
For example:
Is a payment considered missed after 7 days, 15 days, or at the end of the month?
Owner: Product decision

Q3 — How payment records are verified
V1 may use user/landlord-recorded payment information.
Should both parties confirm a payment?
Owner: Product decision

Q4 — Trust Profile visibility
Which information can:
Tenant see about landlord?
Landlord see about tenant?
We need to avoid exposing unnecessary private information.

Q5 — Rental completion
When exactly does:
ACTIVE → COMPLETED
occur?
End date?
Landlord confirmation?
Tenant confirmation?
Both?

Q6 — Negative events
How do we verify complaints before they affect someone's Trust Score?
A complaint should not automatically equal wrongdoing.

23. Technical Definition of Done
The TRD is considered implemented when:
✓ React application runs
✓ Spring Boot application runs
✓ PostgreSQL connected

✓ JWT authentication works
✓ Tenant/Landlord authorization works

✓ User profiles work
✓ Trust Profiles work
✓ Trust Score is server calculated

✓ Landlords manage properties
✓ Tenants search properties
✓ Trust information is shown with properties

✓ Tenants apply
✓ Landlords accept/reject

✓ Accepted application creates rental
✓ Rental history is stored

✓ Monthly rent records are stored
✓ Payment date is recorded
✓ Early/on-time/late/missed is calculated
✓ Payment behavior affects Trust Score

✓ Reviews are restricted to eligible rental relationships
✓ Reviews affect Trust Score according to defined rules

✓ Ownership/security checks are enforced
✓ Validation and error handling exist
✓ Critical flows are tested

24. Architecture Decision Summary
Frontend
React.js
JavaScript
   │
   │ REST / JSON
   ↓
Backend
Java 21
Spring Boot
   │
   ├── Auth
   ├── Users
   ├── Trust
   ├── Properties
   ├── Applications
   ├── Rentals
   ├── Rent Tracking
   └── Reviews
   │
   ↓
PostgreSQL
Core TrustRent loop
User
 ↓
Trust Profile
 ↓
Property / Applicant
 ↓
Rental
 ↓
Monthly Rent
 ↓
Payment Behavior
 ↓
Trust Event
 ↓
Trust Score
 ↓
Review
 ↓
Updated Reputation
This is the technical foundation we should now keep fixed. The template's intended workflow is PRD → TRD → App Flow → UI/UX Brief → Backend Schema → Implementation Plan, with each document feeding the next.
Next document: 03-App-Flow.md — we'll define every TrustRent screen, what each screen shows, who can access it, loading/empty/error states, and the complete tenant and landlord journeys before writing the UI.
Make this changes
5. Authorization matrix
Put this primarily in:
02-TRD.md
Create a new section:
# Authorization Matrix

| Action | Tenant | Landlord |
|---|---|---|
| View available properties | ✓ | ✓ |
| Create property | ✗ | ✓ |
| Edit own property | ✗ | ✓ |
| Delete own property | ✗ | ✓ |
| Apply to property | ✓ | ✗ |
| View own applications | ✓ | ✗ |
| View applications for own property | ✗ | ✓ |
| View applicant Trust Profile | ✗ | ✓ |
| Accept application | ✗ | ✓ |
| Reject application | ✗ | ✓ |
| Review landlord | ✓ | ✗ |
| Review tenant | ✗ | ✓ |
Then explicitly state:
All authorization is enforced server-side. Frontend visibility is not considered security.
This aligns directly with your existing schema rule requiring server-side ownership and permission checks.

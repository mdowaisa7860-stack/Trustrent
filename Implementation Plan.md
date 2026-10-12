Implementation Plan — TrustRent V1
This is the sixth and final foundation document. It converts the PRD, TRD, App Flow, UI/UX Brief, and Backend Schema into small implementation tasks that can be given to an AI coding agent one prompt at a time. That matches the template's intended workflow and task size.
The key rule is:
One task = one AI prompt = one testable outcome.
Do not give an AI agent a task such as "Build the dashboard." Instead, split it into authentication state, layout, Trust Score card, payment summary, application list, empty state, loading state, error state, etc.

1. Development Phases
PHASE 1  Project Foundation
        ↓
PHASE 2  Database & Backend Foundation
        ↓
PHASE 3  Authentication & Authorization
        ↓
PHASE 4  Trust Profile & Trust Engine
        ↓
PHASE 5  Property System
        ↓
PHASE 6  Rental Applications
        ↓
PHASE 7  Rentals & Monthly Rent Tracking
        ↓
PHASE 8  Reviews & Reputation
        ↓
PHASE 9  React Core UI
        ↓
PHASE 10 Dashboards & Complete Flows
        ↓
PHASE 11 Testing & Security
        ↓
PHASE 12 Deployment & Launch

2. Phase 1 — Project Foundation
Milestone
✓ Git repository exists
✓ React application runs
✓ Spring Boot application runs
✓ PostgreSQL development database exists
✓ Frontend can communicate with backend
✓ Base UI tokens/components are configured

Task 1.1 — Initialize repository
AI prompt scope: Create the base repository structure only.
TrustRent/
├── backend/
├── frontend/
├── docs/
└── README.md
Do not build application features.

Task 1.2 — Initialize Spring Boot
File area:
backend/
Configure:
Java 21
Spring Boot
Maven
Spring Web
Spring Data JPA
PostgreSQL driver
Validation
Spring Security
Done when:
./mvnw spring-boot:run
starts successfully.

Task 1.3 — Initialize React
File area:
frontend/
Configure:
React
Vite
React Router
Axios
Done when:
npm run dev
opens the frontend successfully.

Task 1.4 — Configure Tailwind + shadcn/ui
Use the exact UI/UX Brief tokens.
File area:
frontend/
├── src/
└── ...
Add the TrustRent color variables, typography and basic design tokens.
Do not build pages yet.

Task 1.5 — Create base Git configuration
Add:
.gitignore
README.md
.env.example
Never commit:
.env
database passwords
JWT secrets
API keys

3. Phase 2 — Database & Backend Foundation
Milestone
✓ PostgreSQL connection works
✓ JPA is configured
✓ All V1 entities exist
✓ Relationships work
✓ Migrations/schema are reproducible
✓ Repositories compile

Task 2.1 — Configure PostgreSQL
File:
backend/src/main/resources/application.properties
Configure development database connection.

Task 2.2 — Create User entity
File:
backend/src/main/java/com/trustrent/backend/entity/User.java
Implement fields from Backend Schema.

Task 2.3 — Create Property entity
Property.java
Implement landlord relationship and property fields.

Task 2.4 — Create PropertyImage
PropertyImage.java
Relationship:
Property 1:N PropertyImage

Task 2.5 — Create RentalApplication
RentalApplication.java
Relationships:
Tenant
Property

Task 2.6 — Create Rental
Rental.java
Relationships:
Tenant
Landlord
Property
Application

Task 2.7 — Create RentPayment
RentPayment.java
Include:
billingMonth
dueDate
expectedAmount
paidAmount
paymentDate
status

Task 2.8 — Create TrustScore
TrustScore.java

Task 2.9 — Create TrustEvent
TrustEvent.java

Task 2.10 — Create Review
Review.java

Task 2.11 — Create enums
Create:
Role
PropertyStatus
ApplicationStatus
RentalStatus
RentPaymentStatus
TrustEventType

Task 2.12 — Create repositories
repository/
├── UserRepository
├── PropertyRepository
├── PropertyImageRepository
├── RentalApplicationRepository
├── RentalRepository
├── RentPaymentRepository
├── TrustScoreRepository
├── TrustEventRepository
└── ReviewRepository

4. Phase 3 — Authentication & Authorization
Milestone
✓ Tenant can register
✓ Landlord can register
✓ User can log in
✓ JWT is generated
✓ Protected APIs require authentication
✓ Tenant/Landlord permissions work

Task 3.1 — Password hashing
Configure Spring Security password encoding.

Task 3.2 — JWT utility
Create:
security/JwtService.java
Responsibilities:
Generate token
Validate token
Extract user ID
Extract role

Task 3.3 — Registration API
POST /api/auth/register
Validate:
name
email
password
role

Task 3.4 — Login API
POST /api/auth/login
Return JWT and basic user information.

Task 3.5 — JWT authentication filter
Every protected request should be authenticated.

Task 3.6 — Role-based authorization
Implement:
TENANT
LANDLORD

Task 3.7 — Current-user API
GET /api/users/me

Task 3.8 — Authentication UI
Build:
Login.jsx
Register.jsx
Do not build dashboards yet.

Task 3.9 — Protected React routes
Create route protection:
/tenant/*
/landlord/*

5. Phase 4 — Trust Profile & Trust Engine
This phase is the heart of TrustRent.
Milestone
✓ Every user has Trust Score
✓ Trust Profile displays score
✓ Score has explanations
✓ Trust Events are recorded
✓ Score cannot be manually edited
✓ New users are handled correctly

Task 4.1 — Create initial Trust Score
When a user registers:
TrustScore = 500
Do not create fake rental history.

Task 4.2 — Create Trust Score service
service/TrustScoreService.java
It should:
calculate score
apply event
recalculate components
enforce 0–1000 bounds

Task 4.3 — Create payment trust calculator
PaymentTrustCalculator.java
This calculates the payment component from:
EARLY
ON_TIME
LATE
MISSED

Task 4.4 — Create rental-history calculator
Use:
completed rentals
rental duration

Task 4.5 — Create review calculator
Use eligible reviews.

Task 4.6 — Create Trust Event creation
For example:
RENT_PAID_EARLY
RENT_PAID_ON_TIME
RENT_PAID_LATE
RENT_MISSED
RENTAL_COMPLETED
POSITIVE_REVIEW
NEGATIVE_REVIEW

Task 4.7 — Trust Profile API
GET /api/users/{id}/trust-profile
Return:
score
level
payment reliability
completed rentals
rating
verification
trust factors

Task 4.8 — Trust Events API
GET /api/trust/{userId}/events

6. Phase 5 — Property System
Milestone
✓ Landlord can add property
✓ Landlord can edit property
✓ Landlord can deactivate property
✓ Tenant can browse
✓ Tenant can search/filter
✓ Property details work
✓ Landlord Trust Profile appears on property

Task 5.1 — Property repository queries
Add search/filter methods.

Task 5.2 — Create Property API
POST /api/properties
Landlord only.

Task 5.3 — List Properties API
GET /api/properties

Task 5.4 — Property Details API
GET /api/properties/{id}

Task 5.5 — Update Property API
PUT /api/properties/{id}
Only owner.

Task 5.6 — Deactivate Property API
Don't destroy rental history.

Task 5.7 — Property image upload
Implement object-storage abstraction.
For local development, support local storage fallback.

Task 5.8 — React Property Search
Build:
pages/Properties.jsx
components/PropertyCard.jsx

Task 5.9 — Search filters
Implement:
city
minRent
maxRent
bedrooms
propertyType

Task 5.10 — Property Details UI
Build:
pages/PropertyDetails.jsx
Include:
property
costs
amenities
rules
landlord Trust
reviews
Apply button

7. Phase 6 — Rental Applications
Milestone
✓ Tenant can apply
✓ Duplicate applications prevented
✓ Landlord sees applications
✓ Tenant sees status
✓ Landlord can accept
✓ Landlord can reject

Task 6.1 — Create application API
POST /api/applications

Task 6.2 — Prevent duplicate applications
Backend rule.

Task 6.3 — Tenant application list
GET /api/applications/my

Task 6.4 — Landlord application list
GET /api/applications/received

Task 6.5 — Application accept
PUT /api/applications/{id}/accept
This must create the Rental.

Task 6.6 — Application reject
PUT /api/applications/{id}/reject

Task 6.7 — Tenant Applications UI
Build:
pages/MyApplications.jsx
components/ApplicationCard.jsx

Task 6.8 — Landlord Applications UI
Build received-application interface.
Show:
Tenant
Trust Score
Payment Reliability
Rental History
Reviews

8. Phase 7 — Rentals & Monthly Rent Tracking
This is another core TrustRent feature.
Milestone
✓ Accepted application creates rental
✓ Rental has monthly obligations
✓ Payment records can be recorded
✓ Early/on-time/late/missed calculated
✓ Payment history displayed
✓ Trust Events generated
✓ Trust Score updates
The original TrustRent proposal specifically includes monthly rent tracking, including payment dates, late payments, missed payments and payment consistency.

Task 7.1 — Rental creation service
Create rental from accepted application.

Task 7.2 — Generate monthly rent obligations
Given:
startDate
endDate
monthlyRent
generate monthly payment records.

Task 7.3 — Payment status calculator
Implement:
EARLY
ON_TIME
LATE
MISSED
based on defined business rules.

Task 7.4 — Record payment API
POST /api/rentals/{id}/payments

Task 7.5 — Payment history API
GET /api/rentals/{id}/payments

Task 7.6 — Generate Trust Event from payment
Example:
Payment = EARLY
        ↓
TrustEvent
        ↓
TrustScore update

Task 7.7 — Prevent duplicate monthly payment
Enforce:
UNIQUE(rental_id, billing_month)

Task 7.8 — Rent Payment History UI
Build:
PaymentHistoryTable.jsx
PaymentStatusBadge.jsx
Display:
Month
Due
Paid
Status

Task 7.9 — Payment Reliability summary
Build:
Early/on time
Late
Missed
Reliability %

9. Phase 8 — Reviews & Reputation
Milestone
✓ Tenant can review eligible landlord
✓ Landlord can review eligible tenant
✓ One review per direction/rental
✓ Review validation works
✓ Trust Event created
✓ Trust Score updated
The two-sided review model is part of the original product concept.

Task 8.1 — Review eligibility service
Verify:
Rental exists
Rental eligible
Reviewer is participant
Review target is opposite participant
No existing review

Task 8.2 — Create review API
POST /api/reviews

Task 8.3 — Get user reviews API
GET /api/users/{id}/reviews

Task 8.4 — Review → Trust Event

Task 8.5 — Tenant review UI

Task 8.6 — Landlord review UI

Task 8.7 — Reviews on Trust Profile

10. Phase 9 — React Core UI
Now build the visual system from the UI/UX Brief.
Milestone
✓ Consistent navigation
✓ Consistent cards
✓ Consistent forms
✓ Trust Score components
✓ Loading states
✓ Empty states
✓ Error states
✓ Responsive design

Task 9.1 — Application shell
Create:
App.jsx
Navbar
Footer
Route handling

Task 9.2 — Tenant navigation

Task 9.3 — Landlord navigation

Task 9.4 — TrustScoreCard

Task 9.5 — TrustScoreBreakdown

Task 9.6 — TrustHistoryTimeline

Task 9.7 — PropertyCard

Task 9.8 — ApplicationCard

Task 9.9 — RentalCard

Task 9.10 — PaymentHistoryTable

Task 9.11 — ReviewCard

11. Phase 10 — Dashboards & Complete User Flows
Milestone
✓ Tenant can complete full journey
✓ Landlord can complete full journey
✓ Trust information appears consistently
✓ Dashboard data is real
✓ No page depends on fake static data

Task 10.1 — Tenant Dashboard
Build:
pages/TenantDashboard.jsx
Sections:
Trust Score
Payment Reliability
Applications
Active Rental
Rental History
Trust History

Task 10.2 — Tenant Dashboard empty state
New tenant with no rental.

Task 10.3 — Tenant Dashboard loading state

Task 10.4 — Tenant Dashboard error state

Task 10.5 — Landlord Dashboard

Task 10.6 — Landlord Dashboard empty state

Task 10.7 — Landlord Dashboard loading state

Task 10.8 — Landlord Dashboard error state

Task 10.9 — Trust Profile page

Task 10.10 — Rental History page

Task 10.11 — My Profile page

12. Phase 11 — Testing & Security
The template's implementation section includes manual testing, e2e testing and launch checks; those should be applied after the functional flow exists.
Milestone
✓ Critical APIs tested
✓ Critical user journeys tested
✓ Authorization tested
✓ Invalid inputs tested
✓ Empty/loading/error states tested
✓ Mobile UI tested
✓ Production configuration tested

Task 11.1 — Authentication tests
Test:
Valid registration
Duplicate email
Invalid password
Valid login
Invalid login
Expired/invalid JWT

Task 11.2 — Authorization tests
Test:
Tenant accessing landlord endpoint
Landlord accessing tenant operation
User editing another user's property
User accessing unauthorized rental

Task 11.3 — Property API tests

Task 11.4 — Application API tests

Task 11.5 — Trust Score tests
Test:
Early payment
On-time payment
Late payment
Missed payment
Completed rental
Positive review
Negative review
Score boundaries

Task 11.6 — Payment tracking tests
Verify:
Due date
Payment date
Status
Duplicate prevention
Trust event creation
Score update

Task 11.7 — Review tests
Verify:
Eligible review
Duplicate review
Unauthorized review
Wrong user role

Task 11.8 — Frontend error states
Intentionally simulate:
API failure
Unauthorized
Empty response
Slow response

Task 11.9 — Accessibility audit
Check:
Keyboard navigation
Focus states
Contrast
Labels
ARIA
Dialog accessibility

Task 11.10 — Responsive test
Test:
Desktop
Tablet
Mobile

13. Phase 12 — Deployment & Launch
Milestone
✓ Production frontend deployed
✓ Production backend deployed
✓ Production database configured
✓ HTTPS enabled
✓ Environment variables configured
✓ Logs available
✓ Error monitoring configured
✓ Smoke tests passed

Task 12.1 — Production database
Create managed PostgreSQL database.

Task 12.2 — Production environment configuration
Configure:
DATABASE_URL
DATABASE_USERNAME
DATABASE_PASSWORD
JWT_SECRET
STORAGE_CONFIG

Task 12.3 — Backend deployment
Deploy Spring Boot backend.

Task 12.4 — Frontend deployment
Deploy React frontend.

Task 12.5 — Configure CORS
Allow only approved frontend origin(s).

Task 12.6 — Configure HTTPS
Ensure production APIs use HTTPS.

Task 12.7 — Error tracking
Optional V1 service:
Sentry
Fallback:
Application logs

Task 12.8 — Analytics
Optional:
Plausible/PostHog
Fallback:
No analytics
Do not add analytics solely because an AI agent recommends it.

Task 12.9 — Production smoke test
Run:
Register
 ↓
Login
 ↓
Create property
 ↓
Search
 ↓
Apply
 ↓
Accept
 ↓
Record rent
 ↓
Update Trust Score
 ↓
Review

14. Dependencies
The order matters.
Project setup
      ↓
Database
      ↓
Entities
      ↓
Repositories
      ↓
Authentication
      ↓
Authorization
      ↓
Trust foundation
      ↓
Properties
      ↓
Applications
      ↓
Rentals
      ↓
Rent tracking
      ↓
Reviews
      ↓
Dashboards
      ↓
Testing
      ↓
Deployment
Specific dependencies
Property creation
    requires → Authentication + User + Property

Application
    requires → User + Property

Rental
    requires → Accepted Application

Rent Payment
    requires → Rental

Trust Event from payment
    requires → Rent Payment

Review
    requires → Completed Rental

Trust Score update from review
    requires → Review + Trust Engine

15. Critical Product Flow to Test End-to-End
This should be the main acceptance test:
LANDLORD
Register
   ↓
Create Trust Profile
   ↓
List Property
       │
       │
       ↓
TENANT
Register
   ↓
Create Trust Profile
   ↓
Search Property
   ↓
View Property
   ↓
View Landlord Trust
   ↓
Apply
       │
       ↓
LANDLORD
View Tenant Trust
   ↓
Accept
       │
       ↓
RENTAL CREATED
       │
       ↓
Monthly Rent
       │
       ├── EARLY
       ├── ON_TIME
       ├── LATE
       └── MISSED
       │
       ↓
Trust Event
       ↓
Trust Score Updated
       ↓
Rental Completed
       ↓
Review
       ↓
Trust Score Updated Again

16. AI Coding Prompt Rules
Every implementation prompt should contain:
1. Which foundation documents apply.
2. Exact task.
3. Relevant file paths.
4. Constraints.
5. Expected behavior.
6. Acceptance criteria.
7. Tests required.
Example:
TASK:
Implement RentPayment status calculation.

READ:
- PRD: Monthly Rent Tracking
- TRD: Rent Tracking Module
- Backend Schema: rent_payments
- Implementation Plan: Task 7.3

FILES:
- backend/.../service/RentPaymentService.java
- backend/.../entity/RentPayment.java
- backend/.../repository/RentPaymentRepository.java

REQUIREMENTS:
- Calculate EARLY when payment date < due date.
- Calculate ON_TIME when payment date = due date.
- Calculate LATE when payment date > due date.
- Do not calculate Trust Score in the controller.
- Use the existing TrustScoreService.
- Do not create new entities.

ACCEPTANCE:
- Unit tests pass for all statuses.
- Invalid payment records are rejected.
- Existing API behavior is unchanged.
That is the kind of task the template is recommending: small enough to fit into one AI prompt and with clear acceptance criteria.

17. Definition of V1 Complete
TrustRent V1 is complete when a new user can go from:
REGISTER
   ↓
LOGIN
   ↓
TRUST PROFILE
   ↓
PROPERTY
   ↓
APPLICATION
   ↓
RENTAL
   ↓
MONTHLY RENT
   ↓
PAYMENT BEHAVIOR
   ↓
TRUST SCORE
   ↓
RENTAL HISTORY
   ↓
REVIEW
   ↓
UPDATED TRUST PROFILE
for both sides of the rental relationship.
The system should work without:
AI
Real payments
Escrow
360° tours
Video damage detection
Digital signatures
Mobile app
Those remain outside V1 according to the earlier PRD/TRD decisions.

18. Final Foundation-Document Pipeline
You now have the six documents in the intended order:
01 PRD
What are we building?
        ↓
02 TRD
How will we technically build it?
        ↓
03 App Flow
What can users do and where can they go?
        ↓
04 UI/UX Brief
What does the product look and feel like?
        ↓
05 Backend Schema
What data and APIs power it?
        ↓
06 Implementation Plan
What exactly do we build, in what order?
That sequence is the workflow specified by the template.
Recommended project structure
TrustRent/
│
├── docs/
│   ├── 01-PRD.md
│   ├── 02-TRD.md
│   ├── 03-App-Flow.md
│   ├── 04-UI-UX-Brief.md
│   ├── 05-Backend-Schema.md
│   └── 06-Implementation-Plan.md
│
├── backend/
│   └── Spring Boot application
│
├── frontend/
│   └── React application
│
├── README.md
└── .gitignore
At this point, do not start asking the coding AI to "build TrustRent." The next step should be to create
Make this changes Change:
06-Implementation-Plan.md → Phase 2
Your implementation plan already includes database setup and seed development data.
Add:
[ ] Create development seed data:
    - 10 tenants
    - 10 landlords
    - 15+ properties
    - Applications
    - Active rentals
    - Completed rentals
    - Payment histories
    - Reviews
    - Different Trust Scores
This will let you test the entire UI without manually creating everything.

11. Testing matrix
Change:
06-Implementation-Plan.md → Testing Strategy
You already have the critical E2E flow in the document.
Turn it into explicit tests:
### Mandatory V1 E2E Tests

1. Tenant registration
2. Landlord registration
3. Property creation
4. Property search
5. Application submission
6. Landlord reviews applicant
7. Application acceptance
8. Rental creation
9. Rent payment recording
10. EARLY calculation
11. ON_TIME calculation
12. LATE calculation
13. MISSED handling
14. Trust Score update
15. Rental completion
16. Tenant review
17. Landlord review
18. Trust Score recalculation
19. Rental history update
Also test the failure cases:
- Duplicate application
- Duplicate monthly payment
- Unauthorized property edit
- Unauthorized application access
- Unauthorized Trust Profile access
- Invalid payment
- Review without completed rental
- Duplicate review
- Two active rentals for one property
Your existing implementation plan already uses small, testable tasks, so these should become acceptance criteria rather than one giant test task.

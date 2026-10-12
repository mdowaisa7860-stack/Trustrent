Product Requirements Document (PRD) — TrustRent V1
This version keeps monthly rent-payment tracking and behavior-based scoring as core V1 features. The structure follows the uploaded Vibe Coding Template Pack, which recommends defining the PRD first, including goals, non-goals, user stories, success metrics, risks, and assumptions, before moving to technical and implementation documents.

1. Overview
Product name: TrustRent
Version: V1 — Trust-Based Rental Platform
Platform: Web application
Frontend: React.js
Backend: Java + Spring Boot
Database: PostgreSQL
One-liner
TrustRent is a rental platform that helps tenants and landlords make safer rental decisions by building transparent Trust Profiles from rental history, monthly rent-payment behavior, reviews, compliance, and other verified rental activities.
Vision
TrustRent is not simply a property-listing website.
Its main purpose is to create a trustworthy rental ecosystem where:
Tenant ───────────── Landlord
   │                     │
   ↓                     ↓
Trust Profile        Trust Profile
   │                     │
   └─────────┬───────────┘
             ↓
        Rental Decision
The original TrustRent concept explicitly describes the platform as a trust-based rental infrastructure rather than another property-listing website.

2. Problem
Renting a property creates uncertainty for both sides.
Tenant problems
Fake or misleading listings
Hidden charges
Uncertainty about landlord reliability
Difficulty knowing how previous tenants were treated
Dependence on brokers and brokerage fees
Difficulty comparing properties using trust-related information
Landlord problems
Difficulty identifying trustworthy tenants
Uncertainty about tenant rental history
Late or missed rent
Property-care concerns
Difficulty distinguishing reliable applicants from unknown applicants
These problems are central to the original TrustRent proposal.

3. Target Users
Primary user — Tenant
An urban tenant, such as a student, recent graduate, or working professional, looking for a rental property and wanting to understand the reliability of the landlord before applying.
Main job-to-be-done
"Help me find a suitable rental and understand whether the landlord is trustworthy before I commit."
Secondary user — Landlord
A property owner who wants to rent directly to tenants and evaluate applicants using rental reputation and payment behavior.
Main job-to-be-done
"Help me identify tenants who have demonstrated reliable rental behavior."

4. Goals
G1. Build a transparent Trust Profile
Every user should have a Trust Profile showing:
Trust Score
Rental History
Payment History
Reviews
Compliance
Complaints
Verification status
Both tenants and landlords should receive a Trust Score out of 1000, consistent with the original concept.

G2. Track monthly rent behavior
For every active rental, TrustRent should record:
Rent amount
Due date
Payment date
Payment status
The system should classify payments as:
EARLY
ON_TIME
LATE
MISSED
The original TrustRent proposal specifically calls for tracking payment dates, late payments, missed payments, and payment consistency, with this information contributing to the tenant's Trust Score.
Example
August

Due date:     5 Aug
Paid date:    3 Aug
Amount:       ₹18,000
Status:       EARLY
September

Due date:     5 Sep
Paid date:    5 Sep
Amount:       ₹18,000
Status:       ON_TIME
October

Due date:     5 Oct
Paid date:    9 Oct
Amount:       ₹18,000
Status:       LATE
The system should retain this history rather than overwriting it every month.

G3. Calculate Trust Score from rental behavior
The score should not simply be a manually entered number.
It should be calculated from measurable factors.
Tenant Trust Score factors
Monthly payment behavior
Rental history
Property-care feedback
Reviews
Rule/agreement compliance
Verified complaints/disputes
The original concept identifies these types of factors, including on-time rent payments, previous rental history, property condition, complaint history, compliance, landlord feedback, and dispute history.
Landlord Trust Score factors
Property/rental history
Maintenance quality
Tenant feedback
Listing accuracy
Responsiveness
Complaint resolution
These factors are also taken from the original proposal.

5. Trust Score Design
Score range

Initial V1 approach
Use a rule-based scoring engine, not machine learning.
For example:
Starting score                  500

Early/on-time rent              +
Late rent                        -
Missed rent                      --
Completed rental                 +
Positive verified review         +
Repeated rule violation          -
Verified complaint               -
The exact numerical weights should be defined during the technical/design stage and kept configurable rather than hard-coded throughout the application.
Score explanation
Never show only:
847 / 1000
Instead:
Trust Score: 847 / 1000

Payment reliability
✓ 10 on-time/early
⚠ 2 late
✓ 0 missed

Rental history
✓ 3 completed rentals

Reviews
✓ 4.6 / 5

Verified complaints
✓ 0
This makes the score understandable and auditable.

6. Goals for Property Discovery
Landlords can list properties directly.
The listing must clearly show known costs before a tenant applies.
Property information
Title
Description
Location
Monthly rent
Security deposit
Maintenance
Parking
Water
Electricity
Internet
Bedrooms
Bathrooms
Amenities
Rules
Images
The original proposal requires rental costs to be visible before booking and aims to prevent hidden charges.

7. Trust-Based Property Discovery
Property search should not rank only by price.
A result should expose trust information such as:
2 BHK Apartment
Bangalore

₹18,000/month

Landlord Trust Score
912 / 1000

✓ Verified
✓ 5 completed rentals
✓ 94% positive reviews

[View Property]
The original proposal explicitly says search results should consider landlord Trust Score, property quality, tenant satisfaction, complaint history, and verification status.
For V1, the first implementation can display these factors without building a complex ranking algorithm.

8. Rental Application
A tenant can apply for a property.
Flow
Tenant
  ↓
View Property
  ↓
View Landlord Trust Profile
  ↓
Apply
  ↓
Landlord receives application
  ↓
View Tenant Trust Profile
  ↓
Accept / Reject
Application statuses:
PENDING
ACCEPTED
REJECTED

9. Rental Record
Once an application is accepted, the system creates a rental record.
Rental
├── Tenant
├── Landlord
├── Property
├── Start date
├── End date
├── Monthly rent
└── Status
Status:
ACTIVE
COMPLETED
CANCELLED
This rental becomes part of both parties' rental histories.

10. Monthly Rent Tracking
This is a core TrustRent feature, not an optional future feature.
For every month:
Rental
   ↓
Rent obligation
   ↓
Due date
   ↓
Payment recorded
   ↓
Payment status
   ↓
Trust Score calculation
Example
RENT PAYMENT HISTORY

Month       Due       Paid       Status
────────────────────────────────────────
Jan         5 Jan     3 Jan      EARLY
Feb         5 Feb     5 Feb      ON_TIME
Mar         5 Mar     7 Mar      LATE
Apr         5 Apr     5 Apr      ON_TIME
May         5 May     —          MISSED
Important V1 decision
The platform can initially record payment information without processing actual money.
That means:
V1
Payment tracking       ✅
Payment classification ✅
Trust Score update     ✅

Real payment gateway   ❌
Wallet                 ❌
Escrow                 ❌
Bank integration       ❌
This preserves the TrustRent concept without introducing financial infrastructure too early.

11. Rental Behavior Tracking
TrustRent should eventually maintain a broader rental behavior record.
Tenant
Payment behavior
Rental duration
Completed rentals
Property-care feedback
Reviews
Rule compliance
Verified complaints
Landlord
Property history
Maintenance feedback
Listing accuracy
Responsiveness
Reviews
Complaint resolution
Rental history
The source concept similarly emphasizes transparent rental reputation and two-sided accountability.

12. Reviews
Only legitimate rental relationships should be allowed to produce reviews.
Tenant reviews landlord
Maintenance
Responsiveness
Communication
Facility quality
Overall experience
Landlord reviews tenant
Property care
Cleanliness
Communication
Rule compliance
Overall experience
This follows the two-sided feedback model in the original proposal.

13. Review Restrictions
To avoid fake reputation:
No rental relationship
        ↓
Cannot review
Completed/eligible rental
        ↓
Can review once
A user must not be able to create 20 reviews for themselves using fake accounts.

14. Rental History
Every completed rental becomes part of the user's profile.
Example:
Rental History

1. 2 BHK — Bangalore
   Jan 2026 – Dec 2026
   Completed

2. 1 BHK — Bangalore
   Jan 2025 – Dec 2025
   Completed
Landlords can use tenant rental history when evaluating applicants, and tenants can use landlord history when evaluating properties, matching the original concept.

15. User Verification
V1 should include a basic verification indicator.
✓ Email verified
✓ Phone verified
Government-ID verification can remain a later stage because it introduces additional identity-verification requirements.
The original proposal identifies government ID, phone and email verification as mechanisms for reducing fake accounts and fraud.

16. Tenant Dashboard
┌──────────────────────────────────┐
│        TENANT DASHBOARD          │
├──────────────────────────────────┤
│                                  │
│ Trust Score                      │
│       847 / 1000                 │
│                                  │
│ Payment Reliability              │
│ 10 Early/On Time                 │
│ 2 Late                           │
│ 0 Missed                         │
│                                  │
│ Applications                     │
│ 2 Pending                        │
│ 1 Accepted                       │
│                                  │
│ Active Rental                    │
│ 2 BHK Bangalore                  │
│                                  │
│ Rental History                   │
│ 3 Completed Rentals              │
└──────────────────────────────────┘

17. Landlord Dashboard
┌──────────────────────────────────┐
│       LANDLORD DASHBOARD         │
├──────────────────────────────────┤
│                                  │
│ Trust Score                      │
│       912 / 1000                 │
│                                  │
│ My Properties                    │
│ 3 Active                         │
│                                  │
│ Applications                     │
│ 5 Pending                        │
│                                  │
│ Active Rentals                   │
│ 3                                │
│                                  │
│ Tenant Reviews                   │
│ 18                               │
└──────────────────────────────────┘

18. Core User Stories
Tenant
US-T01 — As a tenant, I want to register so that I can use TrustRent.
US-T02 — As a tenant, I want to create my Trust Profile so that landlords can understand my rental reputation.
US-T03 — As a tenant, I want to search properties so that I can find suitable rentals.
US-T04 — As a tenant, I want to filter properties by location, rent, bedrooms and other criteria so that I can narrow my options.
US-T05 — As a tenant, I want to see the landlord's Trust Score so that I can make a more informed rental decision.
US-T06 — As a tenant, I want to see the complete listed rental costs so that I understand my financial obligations before applying.
US-T07 — As a tenant, I want to see the landlord's rental history and reviews so that I can evaluate their reliability.
US-T08 — As a tenant, I want to apply for a property so that the landlord can consider my application.
US-T09 — As a tenant, I want to see my monthly rent payment history so that I can track my rental behavior.
US-T10 — As a tenant, I want my payment behavior to contribute to my Trust Score so that my reliable rental history is reflected.
US-T11 — As a tenant, I want to review a landlord after a legitimate rental so that future tenants can learn from my experience.

Landlord
US-L01 — As a landlord, I want to register so that I can use TrustRent.
US-L02 — As a landlord, I want a Trust Profile so that tenants can evaluate me.
US-L03 — As a landlord, I want to list a property so that tenants can discover it.
US-L04 — As a landlord, I want to specify all known rental costs so that tenants have transparent information.
US-L05 — As a landlord, I want to receive rental applications so that I can choose a tenant.
US-L06 — As a landlord, I want to see an applicant's Trust Score so that I can evaluate their rental reputation.
US-L07 — As a landlord, I want to see an applicant's payment behavior so that I can understand their rental reliability.
US-L08 — As a landlord, I want to accept or reject an application so that I can manage my property.
US-L09 — As a landlord, I want rental records to be maintained so that my rental history contributes to my Trust Profile.
US-L10 — As a landlord, I want to review a tenant after an eligible rental so that their rental behavior can contribute to their reputation.

19. Success Metrics
The V1 should succeed when the complete trust-based rental cycle works.
Core qualitative metric
A tenant should be able to answer:
"Why should I trust this landlord?"
A landlord should be able to answer:
"Why should I trust this tenant?"
using the information available in the respective Trust Profiles.

20. Out of Scope — V1
To prevent scope creep, the following are explicitly excluded.
Financial
Real payment gateway
Wallet
Escrow
Platform-held deposits
Automatic deposit refund
Automatic deposit deduction
Bank integration
Credit-card spending integration
AI
AI chatbot
AI Trust Score
Machine-learning predictions
AI property valuation
AI damage detection
AI recommendations
Property inspection
360° virtual tours
Move-in videos
Move-out videos
Automatic video comparison
AI damage detection
Automatic repair-cost estimation
Legal
Digital signatures
Legally binding e-agreements
Automated legal contracts
Automated legal dispute resolution
Platform
Mobile app
Social networking
Broker marketplace
Microservices architecture
Complex enterprise property management

21. V2 / Future Features
The original proposal includes several future capabilities that can be added after V1.
V2
Advanced verification
Better Trust ranking
Notifications
Admin moderation
More detailed rental reputation
Payment integration
V3
Digital rental agreements
Real payment collection
Deposit management
Dispute management
V4
360° tours
Property-condition videos
AI-assisted condition analysis
Damage assessment
Automatic deposit settlement
These features are part of the broader TrustRent concept, but they should not compromise the first working version.

22. Risks & Assumptions
Risk 1 — Trust Score becomes oversimplified
A score can make users think TrustRent guarantees someone's behavior.
Mitigation: always show the score and its underlying factors.

Risk 2 — Fake reviews
Users could manipulate reputation.
Mitigation: reviews only from legitimate rental relationships and only once per eligible relationship.

Risk 3 — New user has little history
A new user cannot have a strong rental history.
Mitigation:
New User
Limited rental history
Insufficient historical data
The original proposal suggests a base score for new users; however, its credit-card-spending basis is intentionally excluded from V1.

Risk 4 — Payment data may be inaccurate
If V1 records payments manually, users could enter incorrect information.
Mitigation: clearly distinguish user-recorded payment data from payments verified by an integrated payment system.

Risk 5 — Late payment alone may not indicate bad behavior
A late payment could result from a legitimate issue.
Mitigation: don't make one late payment destroy the Trust Score. Evaluate overall payment consistency.

Risk 6 — Negative reviews may be unfair
A review alone is not proof of wrongdoing.
Mitigation: reviews contribute to the score but should not completely determine it, consistent with the original concept.

23. Assumptions
Users are willing to create a Trust Profile.
Tenants value landlord reputation information.
Landlords value tenant rental reputation.
Monthly payment behavior is a meaningful trust signal.
Rental history becomes more useful as users complete more rentals.
Trust Score should be explainable rather than a black box.
Reviews should be tied to actual rental relationships.
V1 can demonstrate the product without processing real money.
Property discovery is necessary, but trust is the primary product differentiator.

24. V1 Definition of Done
AUTHENTICATION
✓ Tenant registration
✓ Landlord registration
✓ Login/logout
✓ Role-based access

TRUST
✓ Tenant Trust Profile
✓ Landlord Trust Profile
✓ Trust Score /1000
✓ Score breakdown

PROPERTY
✓ Create property
✓ Edit property
✓ Delete property
✓ Search
✓ Filter
✓ Property details
✓ Transparent cost display

APPLICATION
✓ Tenant applies
✓ Landlord views application
✓ Landlord views tenant Trust Profile
✓ Accept
✓ Reject
✓ Application status

RENTAL
✓ Accepted application creates rental
✓ Rental history recorded
✓ Monthly rent obligations recorded
✓ Payment date recorded
✓ Early/on-time/late/missed status calculated

REVIEWS
✓ Tenant reviews landlord
✓ Landlord reviews tenant
✓ One eligible review per rental relationship

TRUST ENGINE
✓ Payment behavior affects tenant score
✓ Rental history affects score
✓ Reviews affect score
✓ Compliance/verified negative events can affect score
✓ Score cannot be manually edited
✓ Score explanation displayed

25. The Core TrustRent Loop
This is the part I would protect throughout the entire project:
                   TRUSTRENT
                       │
          ┌────────────┴────────────┐
          │                         │
       TENANT                   LANDLORD
          │                         │
    Trust Profile             Trust Profile
          │                         │
    Trust Score               Trust Score
          │                         │
          └──────────┬──────────────┘
                     ↓
              Property / Person
                     ↓
                 Application
                     ↓
                Rental starts
                     ↓
              Monthly rent
                     ↓
          Early / On-time / Late
                     ↓
              Rental behavior
                     ↓
             Rental completed
                     ↓
                  Review
                     ↓
              Trust Score update
                     ↓
             Stronger reputation
This is much closer to the original TrustRent vision: verified users, two-sided Trust Scores, rental history, rent tracking, reviews, and accountability between tenants and landlords.
Final V1 statement
TrustRent is a trust-based rental platform where tenants and landlords build measurable rental reputations through payment behavior, rental history, reviews, compliance, and other verified activities, allowing both sides to make better-informed rental decisions.
This should now be the locked PRD. The Vibe Coding template recommends proceeding from the PRD to the TRD, then App Flow, UI/UX Brief, Backend Schema, and Implementation Plan.
Make this changes 1. Trust Score formula
Change here first:
PRD → Feature 3 — Trust Score
Your current section already defines the factors and says the score must explain itself.
Add immediately after the Tenant factors / Landlord factors section:
### Trust Score Calculation Rules

The Trust Score is calculated server-side using a deterministic rule-based
formula.

Score range:
0–1000

The score consists of:

Tenant:
- Payment Reliability: XX%
- Rental History: XX%
- Reviews: XX%
- Rule Compliance: XX%
- Verified Complaints: XX%

Landlord:
- Rental History: XX%
- Listing Quality: XX%
- Maintenance/Responsiveness: XX%
- Reviews: XX%
- Verified Complaints: XX%

Each factor must have:
- Calculation method
- Maximum contribution
- Positive events
- Negative events
- Minimum/maximum limits
Do not leave the percentages as XX when development starts. This needs to be decided before implementation.
Then duplicate the technical version in:
TRD → Trust Score / Trust Engine

2. Rental lifecycle
Change:
PRD → Feature 9 — Rental History
Right now you say completed rentals become history.
Add a section directly before/after that:
### Rental Lifecycle

PENDING
    ↓
ACTIVE
    ↓
COMPLETED

A rental becomes COMPLETED only when:
- The rental end date has been reached, or
- An explicitly defined completion action occurs.

Only COMPLETED rentals:
- create rental history
- become eligible for reviews
- contribute completion-based Trust Events
Then put the technical enforcement in the TRD/schema.

3. Application edge cases
Change:
PRD → Feature 7 — Rental Applications
Your current section only defines PENDING / ACCEPTED / REJECTED.
Add:
### Application Rules

- A tenant cannot apply to the same property more than once while an
  existing application is active.
- A tenant may apply to multiple properties.
- A landlord can accept an application only if the property is available.
- Accepting an application creates exactly one rental.
- A property cannot have multiple active rentals.
- Rejected applications cannot create rentals.
- Define whether tenants can withdraw pending applications.
- Define whether accepted applications can be cancelled.
The exact cancellation rules should be finalized before coding.
Then add the state transitions to:
03-App-Flow

4. Property availability
Change:
PRD → Feature 4 — Property Listings
After Mark property as available/unavailable, add:
### Availability Rules

AVAILABLE
    ↓
Application Accepted
    ↓
ACTIVE RENTAL
    ↓
UNAVAILABLE

After rental completion:
ACTIVE RENTAL
    ↓
COMPLETED
    ↓
Property may become AVAILABLE again
Also specify:
A property may have at most one active rental at a time.
Then enforce that in:
TRD + Backend Schema
Your schema already has the property → application → rental relationship, so this belongs there rather than creating a new entity.
Who records rent payments
Change:
PRD → Monthly Rent Tracking
Your current UI specification defines the monthly fields and statuses.
Add:
### Payment Recording Rules

V1 does not process real payments.

A rent payment record is created manually by an authorized rental participant.

The system stores:
- Billing month
- Amount
- Due date
- Actual payment date
- Status
- Recorded timestamp
- Recorded by user

Payment status is calculated by the backend.

Users cannot directly submit their own Trust Score.
Then answer the crucial business question:
Who is authorized to record it?
I recommend making the landlord the recorder for V1, while the tenant can view the record. But this is a product decision you should lock before coding.

App Flow — TrustRent V1
The App Flow should define every screen, its possible states, permissions, transitions, user journeys, and edge cases. That matches the template you provided.
This flow is based on the revised TrustRent PRD + TRD, including the important monthly rent-tracking and Trust Score system.

1. Screen Inventory

2. Public Flow
                    Landing
                       │
             ┌─────────┴─────────┐
             ↓                   ↓
          Register             Login
             │                   │
             ↓                   ↓
       Role Selection        Authenticate
             │                   │
             ↓                   ↓
       Complete Profile      Dashboard
             │
             ↓
         Dashboard

3. Landing Page
Purpose
Explain the TrustRent concept immediately.
The landing page should emphasize:
Trust Score
Monthly rent behavior
Rental history
Two-sided reputation
Transparent rental information
Main actions
[Find a Property]
[Create Account]
[Login]
States
Success: Landing page displayed.
Loading: Minimal/no special loading required.
Error: If remote content fails, show a generic error with retry.
Empty: Not applicable.
Locked: Not applicable.
Exit
Find Property → Property Search
Create Account → Register
Login → Login

4. Register
Fields
Name
Email
Phone
Password
Confirm Password
Role
Role:
○ Tenant
○ Landlord
Flow
Register
   ↓
Validate
   ↓
Create account
   ↓
Login/session
   ↓
Complete Profile
States
Empty: Blank registration form.
Loading: Registration button disabled + spinner.
Success: Account created.
Error: Show field/API error.
Example:
Email already registered.
[Login instead]
Locked: If account creation is temporarily disabled.
Critical validation
Invalid email
Weak password
Password mismatch
Existing email
Missing required fields

5. Login
Email
Password

[Login]
States
Empty: Login form.
Loading: Button loading.
Success: Redirect according to role.
TENANT → Tenant Dashboard
LANDLORD → Landlord Dashboard
Error:
Invalid email or password.
Locked: Account/session restrictions.

6. Complete Profile
After registration:
Register
   ↓
Complete Profile
Tenant profile
Name
Phone
Location
Profile image
Basic rental information
Landlord profile
Name
Phone
Location
Profile image
Basic property information
The Trust Profile is built from these basic details plus rental activity, reviews and Trust Score.
Exit
Save
 ↓
Dashboard

7. Tenant Dashboard
This is the tenant's main screen.
┌───────────────────────────────────┐
│ Welcome, John                     │
├───────────────────────────────────┤
│ TRUST SCORE                       │
│                                   │
│           847 / 1000              │
│                                   │
│ Payment Reliability: Good         │
├───────────────────────────────────┤
│ Monthly Rent                      │
│ ✓ 10 On Time/Early                │
│ ⚠ 2 Late                          │
│ ✓ 0 Missed                        │
├───────────────────────────────────┤
│ Applications                      │
│ 2 Pending | 1 Accepted            │
├───────────────────────────────────┤
│ Active Rental                     │
│ 2 BHK Bangalore                   │
├───────────────────────────────────┤
│ [Find Property] [Trust Profile]   │
└───────────────────────────────────┘
States
Empty:
No active rental yet.

[Find a Property]
Loading: Skeleton dashboard.
Success: Dashboard data displayed.
Error: Dashboard failed to load → retry.
Locked: Login required.

8. Landlord Dashboard
┌───────────────────────────────────┐
│ Welcome, John                     │
├───────────────────────────────────┤
│ TRUST SCORE                       │
│           912 / 1000              │
├───────────────────────────────────┤
│ My Properties                     │
│ 3 Active                          │
├───────────────────────────────────┤
│ Applications                      │
│ 5 Pending                         │
├───────────────────────────────────┤
│ Active Rentals                    │
│ 3                                 │
├───────────────────────────────────┤
│ [Add Property]                    │
└───────────────────────────────────┘
States
Empty:
You haven't listed any properties.

[Add Property]
Loading: Skeleton cards.
Success: Dashboard.
Error: Retry.
Locked: Login + landlord role required.

9. Property Search
Access
Public browsing → allowed
Applying → login required
Search fields
Location
Minimum rent
Maximum rent
Bedrooms
Property type
Property card
┌─────────────────────────────┐
│ 2 BHK Apartment             │
│ Bangalore                   │
│ ₹18,000/month               │
│                             │
│ Landlord Trust              │
│ 912 / 1000                  │
│ ✓ Verified                  │
│                             │
│ [View Property]             │
└─────────────────────────────┘
States
Empty:
No properties match your filters.

[Clear Filters]
Loading: Property-card skeletons.
Success: Results.
Error: Search request failed.
Locked: Not locked for browsing.

10. Property Details
This is one of the most important TrustRent screens.
┌─────────────────────────────────────┐
│ 2 BHK Apartment                     │
│ Bangalore                           │
│                                     │
│ ₹18,000 / month                     │
│ Security Deposit: ₹50,000           │
│ Maintenance: ₹2,000                 │
│                                     │
│ Amenities                            │
│ ✓ Parking                           │
│ ✓ Water                             │
│ ✓ Internet                          │
│                                     │
│ LANDLORD TRUST PROFILE              │
│                                     │
│ Trust Score: 912 / 1000             │
│ ✓ Verified                          │
│ 5 Completed Rentals                 │
│ 94% Positive Reviews                │
│                                     │
│ [View Trust Profile]                │
│ [Apply for Property]                │
└─────────────────────────────────────┘
States
Loading: Property skeleton.
Success: Full property details.
Error: Property unavailable/error.
Empty: Not applicable.
Locked: Applying requires login.
Entry
Property Search → Property Details
Exit
Apply → Application flow
View Trust Profile → Trust Profile
Back → Search

11. Trust Profile
This is the core TrustRent screen.
Example
┌────────────────────────────────────┐
│ TRUST PROFILE                      │
├────────────────────────────────────┤
│ John Kumar                         │
│ Landlord                           │
│                                    │
│        912 / 1000                  │
│        Excellent                   │
│                                    │
│ Verification                       │
│ ✓ Email                            │
│ ✓ Phone                            │
│                                    │
│ RENTAL REPUTATION                  │
│                                    │
│ Completed Rentals       5          │
│ Average Rating          4.7 / 5    │
│                                    │
│ Trust Factors                      │
│ ✓ Good maintenance history         │
│ ✓ Accurate listings                │
│ ✓ Good responsiveness              │
│                                    │
│ Reviews                            │
│ ★★★★★                              │
└────────────────────────────────────┘
For tenants, also show payment behavior:
Payment History

Early:     4
On Time:   6
Late:      2
Missed:    0
States
Loading: Skeleton.
Success: Profile.
Error: Profile unavailable.
Empty: Limited history for new user.
New User
Limited rental history available.
Locked: Login required for private information.

12. Apply for Property
Flow
Property Details
       ↓
[Apply]
       ↓
Confirm Application
       ↓
Submit
       ↓
PENDING
Application screen
Property
Landlord
Monthly rent
Deposit
Tenant Trust Score

[Submit Application]
States
Empty: Application form ready.
Loading: Submission in progress.
Success:
Application submitted successfully.
Status: PENDING
Error: Submission failure.
Locked: Login required.
Edge case
If already applied:
You have already applied for this property.

13. My Applications — Tenant
My Applications

2 BHK Bangalore
Status: PENDING

1 BHK Bangalore
Status: ACCEPTED

1 BHK Whitefield
Status: REJECTED
States
Empty:
You haven't applied to any properties yet.

[Find Properties]
Loading: Skeleton.
Success: Application list.
Error: Retry.

14. Received Applications — Landlord
Applications

John
Trust Score: 847
Payment Reliability: Good

[View Trust Profile]
[Accept] [Reject]
States
Empty:
No applications yet.
Loading: Skeleton.
Success: Applications.
Error: Retry.
Permission
Only the landlord who owns the relevant property can manage its applications.

15. Add Property
Form
Property Title
Description
Location
Monthly Rent
Security Deposit
Maintenance
Bedrooms
Bathrooms
Property Type
Amenities
Rules
Images
Flow
Add Property
      ↓
Validate
      ↓
Save
      ↓
Property Created
      ↓
My Properties
States
Empty: Blank form.
Loading: Upload/save progress.
Success: Property created.
Error: Validation/API/upload error.
Locked: Landlord only.

16. My Properties
My Properties

┌─────────────────────────────┐
│ 2 BHK Bangalore             │
│ ₹18,000                     │
│ Available                   │
│ [Edit] [Delete]             │
└─────────────────────────────┘
States
Empty:
No properties listed.

[Add Property]
Loading: Skeleton.
Success: Property list.
Error: Retry.

17. Edit Property
Same fields as Add Property.
Flow:
My Properties
      ↓
Edit
      ↓
Change fields
      ↓
Save
      ↓
Updated property
Only the property's owner can edit it.

18. Rental Details
Once an application is accepted:
Tenant
    │
Landlord
    │
Property
    │
Rental
Display:
Property: 2 BHK Bangalore
Tenant: John
Landlord: Alex

Start Date: 01-Jan-2027
End Date: 31-Dec-2027
Monthly Rent: ₹18,000

Status: ACTIVE

19. Rent Payment History
This is a key TrustRent page.
RENT PAYMENT HISTORY

Month   Due       Paid       Status
──────────────────────────────────────
Jan     5 Jan     3 Jan      EARLY
Feb     5 Feb     5 Feb      ON TIME
Mar     5 Mar     9 Mar      LATE
Apr     5 Apr     5 Apr      ON TIME
May     5 May     —          MISSED
Tenant action
For V1, payment may be recorded, rather than processed.
[Record Payment]
States
Empty:
No rent records available.
Loading: Table skeleton.
Success: Payment history.
Error: Unable to load.
Locked: Rental participants only.

20. Record Rent Payment
Month
Amount
Payment Date
Backend determines:
EARLY
ON_TIME
LATE
and determines MISSED according to the configured overdue rule.
Important
The frontend should not decide the Trust Score.
Flow:
React
 ↓
Submit payment
 ↓
Spring Boot
 ↓
Calculate payment status
 ↓
Create trust event
 ↓
Recalculate Trust Score
 ↓
Return result

21. Rental History
RENTAL HISTORY

Completed

2 BHK Bangalore
Jan 2026 – Dec 2026
Status: Completed

1 BHK Bangalore
Jan 2025 – Dec 2025
Status: Completed
States
Empty:
No completed rentals yet.
Loading: Skeleton.
Success: History.
Error: Retry.

22. Reviews
Eligible after completed rental
Tenant:
Review Landlord

Maintenance       ⭐⭐⭐⭐⭐
Responsiveness    ⭐⭐⭐⭐
Communication     ⭐⭐⭐⭐⭐
Overall            ⭐⭐⭐⭐⭐

Comment:
[________________]

[Submit Review]
Landlord:
Review Tenant

Property Care     ⭐⭐⭐⭐⭐
Communication     ⭐⭐⭐⭐
Rule Compliance   ⭐⭐⭐⭐⭐

[Submit Review]
Rules
No completed/eligible rental
       ↓
Cannot review
One eligible review per rental relationship.
States
Empty: No reviews.
Loading: Review list skeleton.
Success: Reviews shown.
Error: Review failed to load/submit.
Locked: User must be authorized and eligible.

23. My Profile
Profile

Name
Email
Phone
Role
Profile Image

[Edit Profile]
States
Loading: Skeleton.
Success: Profile.
Error: Retry.
Locked: Login required.

24. Navigation
Tenant
┌──────────────────────────────────────┐
│ TrustRent                            │
│                                      │
│ Home                                 │
│ Find Properties                      │
│ Applications                         │
│ My Rental                            │
│ Rent History                         │
│ Trust Profile                        │
│ Rental History                       │
│ Profile                              │
└──────────────────────────────────────┘
Landlord
┌──────────────────────────────────────┐
│ TrustRent                            │
│                                      │
│ Home                                 │
│ My Properties                        │
│ Applications                         │
│ Active Rentals                       │
│ Rent Records                         │
│ Trust Profile                        │
│ Rental History                       │
│ Profile                              │
└──────────────────────────────────────┘

25. Complete Tenant Journey
                    LANDING
                       │
                       ↓
                   REGISTER
                       │
                       ↓
               SELECT TENANT
                       │
                       ↓
              COMPLETE PROFILE
                       │
                       ↓
                TENANT DASHBOARD
                       │
                       ↓
                FIND PROPERTY
                       │
                       ↓
              SEARCH / FILTER
                       │
                       ↓
                PROPERTY DETAILS
                       │
                       ↓
            LANDLORD TRUST PROFILE
                       │
                       ↓
                    APPLY
                       │
                       ↓
                   PENDING
                       │
              ┌────────┴────────┐
              ↓                 ↓
          ACCEPTED           REJECTED
              │
              ↓
          ACTIVE RENTAL
              │
              ↓
        MONTHLY RENT RECORDS
              │
       ┌──────┼───────┐
       ↓      ↓       ↓
     EARLY  ON TIME   LATE
              │
              ↓
         TRUST EVENT
              │
              ↓
        TRUST SCORE UPDATE
              │
              ↓
       RENTAL COMPLETED
              │
              ↓
             REVIEW
              │
              ↓
       UPDATED REPUTATION

26. Complete Landlord Journey
                   LANDING
                      │
                      ↓
                  REGISTER
                      │
                      ↓
             SELECT LANDLORD
                      │
                      ↓
             COMPLETE PROFILE
                      │
                      ↓
            LANDLORD DASHBOARD
                      │
                      ↓
                ADD PROPERTY
                      │
                      ↓
              PROPERTY LISTED
                      │
                      ↓
             RECEIVE APPLICATION
                      │
                      ↓
          VIEW TENANT TRUST PROFILE
                      │
                ┌─────┴─────┐
                ↓           ↓
             ACCEPT       REJECT
                │
                ↓
           ACTIVE RENTAL
                │
                ↓
           RENTAL COMPLETED
                │
                ↓
          REVIEW TENANT
                │
                ↓
        TENANT TRUST UPDATED

27. Trust Score Flow
This should be explicitly documented because it is central to the application.
Rental Activity
      │
      ├── Rent payment
      ├── Completed rental
      ├── Review
      ├── Compliance
      └── Verified negative event
               │
               ↓
         Trust Event
               │
               ↓
      Trust Score Service
               │
               ↓
       New Trust Score
               │
               ↓
         Trust Profile

28. Important Screen States
The template specifically recommends defining empty, loading, success and error states instead of designing only the ideal case.
TrustRent should consistently use:
Loading
Skeleton cards
Skeleton tables
Disabled buttons
Empty
No properties
No applications
No rentals
No payment history
No reviews
New user/no history
Error
Failed to load
Network error
Permission denied
Invalid request
Locked
Login required
Wrong role
Unauthorized resource

29. Critical Edge Cases
EC1 — Existing email during signup
Signup
 ↓
Email already exists
 ↓
Show inline error
 ↓
[Login instead]
This matches the template's approach for important error flows.

EC2 — Tenant applies twice
Tenant already applied
        ↓
Block second application
        ↓
Show existing status

EC3 — Landlord attempts to access another landlord's application
Request
 ↓
Backend authorization check
 ↓
DENIED
 ↓
403 response
Do not rely on React alone for this.

EC4 — Tenant tries to edit landlord property
Request
 ↓
Ownership check
 ↓
DENIED

EC5 — Payment entered twice
Prevent duplicate monthly payment records.
August already recorded
        ↓
Reject duplicate

EC6 — Late payment
Due: Aug 5
Paid: Aug 8

        ↓

Status: LATE
        ↓
Trust Event
        ↓
Score recalculated

EC7 — Missed payment
No payment
      ↓
Overdue threshold reached
      ↓
MISSED
      ↓
Trust event
The exact threshold remains an open product decision from the TRD.

EC8 — New user
A new user has insufficient rental history.
Show:
Trust Score
500

Limited rental history
Do not imply that the score represents years of rental behavior.

EC9 — No reviews
Reviews
──────────────
No reviews yet.
Don't display a fake 5-star rating.

EC10 — Property becomes unavailable
The property should disappear from new search/application options while preserving previous rental history.

30. Permission Matrix

31. Entry / Exit Rules
Unauthenticated user
Trying to access:
/tenant/dashboard
redirects to:
/login
After login:
/login
   ↓
original destination
where appropriate.
Wrong role
Tenant accessing:
/landlord/properties
gets:
Access denied
and is redirected to the tenant dashboard.
Invalid property
/properties/999999
if nonexistent:
Property not found

32. Final App Flow
                          TRUSTRENT
                              │
              ┌───────────────┴───────────────┐
              │                               │
           TENANT                         LANDLORD
              │                               │
       Register/Login                  Register/Login
              │                               │
       Trust Profile                     Trust Profile
              │                               │
        Search Property                 Add Property
              │                               │
       View Property                   Manage Property
              │                               │
       View Landlord ←──────────────→ View Tenant
       Trust Profile                  Trust Profile
              │                               │
            Apply ────────────────→ Application
              │                               │
              └────────── Rental ─────────────┘
                             │
                             ↓
                       Monthly Rent
                             │
                    ┌────────┼────────┐
                    ↓        ↓        ↓
                  Early   On Time    Late
                             │
                             ↓
                       Trust Event
                             │
                             ↓
                       Trust Score
                             │
                             ↓
                      Rental History
                             │
                             ↓
                           Review
                             │
                             ↓
                    Updated Reputation
This is the locked App Flow for V1. It includes the monthly rent/payment behavior that was added to the PRD and TRD, rather than reducing TrustRent to a property listing application.
The next document should be 04-UI-UX Brief, where we lock the visual identity, colors, typography, Trust Score visualizations, property cards, dashboards, buttons, forms, responsive behavior, and accessibility rules before we generate the React UI. The template describes this document as the visual contract that prevents AI-generated UI from drifting between screens.

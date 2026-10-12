TrustRent — UI/UX Brief
Concept 2: The Narrative Reputation Model
This is the final UI/UX direction I recommend for TrustRent V1, with the structured monthly-rent table from Concept 1 integrated into the design.
The Vibe Coding template says the UI/UX Brief should lock the visual language, tokens, typography, components, voice/tone, accessibility, and references so AI-generated screens remain consistent.
# UI/UX Brief — TrustRent V1

## 1. Brand Vibe

Three adjectives:
- Trustworthy
- Human
- Modern

Supporting qualities:
- Calm
- Transparent
- Professional
- Reassuring
- Evidence-driven

TrustRent should feel like a modern rental platform built around
people and reputation, not like a generic property marketplace.

The experience should NOT feel:
- like a broker/classified website
- like a cold credit-scoring system
- like a banking application
- like a social-media application
- like an AI demo

The interface should make trust understandable through real rental
activity rather than presenting a mysterious score.

--------------------------------------------------

## 2. Core Design Idea

The central UI concept is:

              PROPERTY
                  +
              PERSON
                  +
           RENTAL HISTORY
                  +
          PAYMENT BEHAVIOR
                  +
              REVIEWS
                  ↓
           TRUST PROFILE

The product should tell the user's rental story.

The Trust Score is important, but it is NOT the entire product.

Instead of showing:

847 / 1000

and stopping there, show:

847 / 1000
Highly Trusted

10 early/on-time payments
2 late payments
0 missed payments
3 completed rentals
4.6 / 5 reviews

--------------------------------------------------

## 3. Homepage

The homepage has a dual-entry concept.

Primary hero:

"Find a trusted home."

Secondary entry:

"Build your rental reputation."

Primary actions:

[Find a Property]
[Create Your Trust Profile]

The hero should communicate that TrustRent evaluates
both the property and the people involved.

Avoid a generic real-estate hero such as:

"Find your dream home."

Instead emphasize:

- trustworthy landlords
- transparent rental costs
- rental reputation
- payment history
- informed rental decisions

--------------------------------------------------

## 4. Homepage Structure

Order:

1. Navigation
2. Hero / dual entry
3. Search
4. Trusted property recommendations
5. How TrustRent builds trust
6. Trust Profile explanation
7. Two-sided reputation explanation
8. Final CTA
9. Footer

Hero example:

Find a trusted home.

Explore properties with transparent costs and
understand the landlord's rental reputation before you apply.

[Search properties]

------------------------------

Or:

Build your rental reputation.

Track your rental history, payment behavior and
reviews in one transparent Trust Profile.

[Create Profile]

--------------------------------------------------

## 5. Navigation

Use a minimal top navigation.

Desktop:

TrustRent

Properties
How Trust Works
For Tenants
For Landlords

[Login]
[Create Account]

After login:

Tenant:
Properties
Applications
My Rental
Trust Profile

Landlord:
Properties
Applications
Rentals
Trust Profile

The user's Trust Score can appear next to the avatar
after authentication.

Example:

[847 Trust] [Avatar]

Do not use a permanent SaaS-style left sidebar as the primary
navigation. The product should feel like a consumer rental platform,
not enterprise software.

--------------------------------------------------

## 6. Colour Tokens

Use these exact CSS variables throughout the application.

:root {
  --primary: #1D4ED8;
  --primary-hover: #1E40AF;

  --secondary: #0F766E;

  --accent: #F59E0B;

  --success: #16A34A;
  --warning: #D97706;
  --error: #DC2626;

  --background: #F8FAFC;
  --surface: #FFFFFF;

  --text: #0F172A;
  --text-secondary: #334155;
  --muted: #64748B;

  --border: #E2E8F0;
  --border-strong: #CBD5E1;

  --overlay: rgba(15, 23, 42, 0.50);
}

Do not invent additional colours for individual screens.

--------------------------------------------------

## 7. Colour Philosophy

Blue:
Primary actions and navigation.

Teal:
Trust-related supporting information.

Amber:
Highlights and attention.

Green:
Positive verified/rental/payment states.

Orange:
Late or caution states.

Red:
Missed payments, rejected actions and serious errors.

Neutral colours:
Main structure and content.

Colour should support the information hierarchy rather than dominate it.

--------------------------------------------------

## 8. Typography

Font:

Inter

Type scale:

H1:
40px / 700 / 48px

H2:
32px / 700 / 40px

H3:
24px / 600 / 32px

H4:
20px / 600 / 28px

Body:
16px / 400 / 24px

Small:
14px / 400 / 20px

Caption:
12px / 500 / 16px

Mobile H1:
32px

Use one typography system across the entire application.

--------------------------------------------------

## 9. Spacing

Base unit:

4px

Use multiples of 4px only.

Common values:

4
8
12
16
20
24
32
40
48
64
80

Typical:
Card padding: 24px
Input gap: 16px
Section gap: 48px
Page section padding: 64px

--------------------------------------------------

## 10. Radius

sm: 4px
md: 8px
lg: 16px
pill: 9999px

Property cards and primary UI:
8px or 16px.

Status badges:
pill.

Major Trust Profile sections:
16px.

Avoid excessive rounded "bubble" styling.

--------------------------------------------------

## 11. Shadows

Use subtle shadows.

Cards:

0 1px 2px rgba(...)

Elevated panels:

0 4px 12px rgba(...)

Avoid:
- heavy floating cards
- excessive shadows
- neon glow
- glassmorphism

--------------------------------------------------

## 12. Component Library

Library:

shadcn/ui + Tailwind CSS

Use the same component system across all screens.

Required common components:

Button
Input
Textarea
Select
Checkbox
Radio
Card
Badge
Dialog
Popover
Tooltip
Tabs
Toast
Avatar
Progress
Table
Skeleton
Pagination
EmptyState
ErrorState

TrustRent-specific components:

TrustScoreCard
TrustScoreBar
TrustProfileCard
TrustFactorCard
TrustHistoryTimeline
PaymentHistoryTable
PaymentStatusBadge
PropertyCard
PropertyGallery
LandlordCard
TenantCard
ApplicationCard
RentalCard
ReviewCard

--------------------------------------------------

## 13. Property Card

The property card should combine property discovery with trust.

Example:

┌──────────────────────────────────┐
│                                  │
│          PROPERTY IMAGE          │
│                                  │
├──────────────────────────────────┤
│ 2 BHK Apartment                  │
│ Bangalore                        │
│                                  │
│ ₹18,000 / month                  │
│ Deposit: ₹50,000                 │
│                                  │
│ LANDLORD                         │
│ Alex Kumar                       │
│                                  │
│ Trust Score  912 / 1000          │
│ Highly Trusted                   │
│ ✓ Verified                       │
│ 5 rentals • 4.7 ★               │
│                                  │
│ [View Property]                  │
└──────────────────────────────────┘

Trust information should be immediately visible.

Do not make the user open the property before discovering
that the landlord has a Trust Profile.

--------------------------------------------------

## 14. Property Details Page

Structure:

1. Image gallery
2. Property overview
3. Price
4. Cost breakdown
5. Amenities
6. Rules
7. Landlord Trust Profile
8. Trust history/factors
9. Reviews
10. Apply CTA

Example:

2 BHK Apartment
Bangalore

₹18,000 / month

Security Deposit
₹50,000

Maintenance
₹2,000

------------------------------

Landlord

Alex Kumar

912 / 1000
Highly Trusted

✓ Verified
5 completed rentals
4.7 ★

[View Trust Profile]

------------------------------

Amenities

Parking
Water
Internet

------------------------------

[Apply for Property]

--------------------------------------------------

## 15. Trust Score Design

Use a horizontal segmented/progress visualization
as the PRIMARY Trust Score visualization.

Example:

TRUST SCORE

847 / 1000
Highly Trusted

Payment History
████████████████░░░

Rental History
████████████░░░░░░

Reviews
███████████░░░░░░░

Verification
███████████████░░

[View Score Details]

The visualization must be understandable without colour.

Each segment must have a label.

Do NOT use a radar chart as the main Trust Score component.

Do NOT use a circular credit-score gauge as the primary design.

The Trust Score should feel human and explainable rather than punitive.

--------------------------------------------------

## 16. Trust Score Breakdown

Display score factors clearly.

Example:

Trust Score: 847

Payment Reliability
92%

Rental History
3 completed rentals

Reviews
4.6 / 5

Verification
Email ✓
Phone ✓

Verified Complaints
0

The user should understand why the score exists
without opening another page.

--------------------------------------------------

## 17. Trust Profile

Trust Profile should resemble a portable rental reputation profile.

Example:

┌────────────────────────────────────┐
│ Alex Kumar                         │
│ Landlord                            │
│                                    │
│ 912 / 1000                         │
│ Highly Trusted                     │
│                                    │
│ ✓ Email Verified                   │
│ ✓ Phone Verified                   │
└────────────────────────────────────┘

RENTAL HISTORY

5 Completed Rentals

PAYMENT / RENTAL RECORD

Reliable rental history

REVIEWS

4.7 / 5

TRUST HISTORY

Timeline of meaningful trust events

The Trust Profile should tell a story about the user's
rental behavior.

--------------------------------------------------

## 18. New User Trust Profile

Do not fabricate reputation.

Example:

Trust Score
500

Limited rental history

This user is new to TrustRent and has limited
verified rental history.

As verified rental activity accumulates,
the Trust Profile will become more informative.

Avoid statements such as:
"Trust score unavailable because user failed."

The user is simply new.

--------------------------------------------------

## 19. Trust History Timeline

Use the narrative concept as a signature feature.

Example:

TRUST HISTORY

● Aug 2027
  Rent paid early
  Payment reliability maintained

● Jul 2027
  Rent paid on time

● Jun 2027
  Positive landlord review

● May 2027
  Rental completed

The timeline should explain behaviour,
not gamify every action.

Avoid excessive "+5 points!" animations.

--------------------------------------------------

## 20. Monthly Rent Tracking

Monthly rent tracking is a core TrustRent feature.

Use a structured TABLE rather than only a timeline.

Example:

MONTHLY RENT

Month    Due       Paid       Status
─────────────────────────────────────
Jan      5 Jan     3 Jan      EARLY
Feb      5 Feb     5 Feb      ON TIME
Mar      5 Mar     9 Mar      LATE
Apr      5 Apr     —          MISSED

Statuses use:

[EARLY]
[ON TIME]
[LATE]
[MISSED]

The table should remain the authoritative detailed history.

The Trust History timeline can summarize important events,
but should not replace this table.

--------------------------------------------------

## 21. Payment Summary

Above the monthly table:

PAYMENT RELIABILITY

92%

10 Early / On Time
2 Late
0 Missed

This creates a connection:

Monthly payments
      ↓
Payment reliability
      ↓
Trust Score

--------------------------------------------------

## 22. Payment Status Visual Language

EARLY:
Positive indicator

ON TIME:
Positive indicator

LATE:
Warning indicator

MISSED:
Error indicator

Always display text.

Never rely exclusively on green/yellow/red colours.

--------------------------------------------------

## 23. Tenant Dashboard

The dashboard should be personal and narrative.

Top:

Welcome back, John.

847 / 1000
Highly Trusted

Then:

Your rental reputation
"Your consistency is helping build your Trust Profile."

------------------------------

PAYMENT RELIABILITY

92%

10 Early / On Time
2 Late
0 Missed

------------------------------

NEXT RENT

₹18,000
Due 5 September

[View Rent History]

------------------------------

APPLICATIONS

2 Pending
1 Accepted

------------------------------

ACTIVE RENTAL

2 BHK Bangalore

[View Rental]

------------------------------

TRUST HISTORY

Recent rental activity

--------------------------------------------------

## 24. Landlord Dashboard

Landlord dashboard should focus on:
- applications
- active rentals
- tenant trust
- property management

Example:

Welcome back, Alex.

912 / 1000
Highly Trusted

------------------------------

APPLICATIONS

5 pending

John Kumar
847 / 1000
Highly Trusted

[View Profile]

------------------------------

MY PROPERTIES

3 Active Properties

------------------------------

ACTIVE RENTALS

3

------------------------------

RECENT ACTIVITY

Tenant payment received
New application
Review received

[View Applications]

--------------------------------------------------

## 25. Application Card

Application should immediately show reputation.

Example:

JOHN KUMAR

Tenant

847 / 1000
Highly Trusted

Payment Reliability
92%

Completed Rentals
3

Average Reviews
4.6 / 5

[View Trust Profile]

[Accept]
[Reject]

Do not automatically rank users solely by Trust Score.

Allow the landlord to evaluate the complete profile.

--------------------------------------------------

## 26. Application Detail

Show:

Applicant
Trust Score
Payment behavior
Rental history
Reviews
Verification
Application date
Property applied for

Primary actions:

[Accept]
[Reject]

Destructive/rejection actions should require confirmation where necessary.

--------------------------------------------------

## 27. Rental Detail

Show:

Property
Tenant
Landlord
Monthly rent
Start date
End date
Rental status

Then:

Payment reliability
Monthly rent history
Trust events
Relevant reviews

This screen connects the rental itself with the reputation system.

--------------------------------------------------

## 28. Review Design

Reviews are supporting evidence, not the entire Trust Score.

Tenant reviewing landlord:

Maintenance
★★★★☆
Responsiveness
★★★★★
Communication
★★★★☆
Facility Quality
★★★★★
Overall Experience
★★★★★

Landlord reviewing tenant:

Property Care
★★★★★
Communication
★★★★☆
Rule Compliance
★★★★★

Use a concise review format.

Avoid making TrustRent feel like a social-media review platform.

--------------------------------------------------

## 29. Mobile Property Experience

On mobile:

Property image
   ↓
Property summary
   ↓
Rent/costs
   ↓
Landlord Trust
   ↓
Reviews
   ↓
Apply

Use a sticky bottom CTA:

[Apply for Property]

Do NOT make the Trust Score itself a permanent sticky footer.

Instead, show the Trust Score prominently near the landlord section
and keep the primary action sticky.

--------------------------------------------------

## 30. Mobile Trust Profile

Use a vertical structure:

Profile
 ↓
Trust Score
 ↓
Score breakdown
 ↓
Payment reliability
 ↓
Rental history
 ↓
Reviews
 ↓
Trust history

Use Accordions only for secondary detail.

Do not hide important trust evidence behind excessive accordions.

--------------------------------------------------

## 31. Navigation Style

Desktop:
Minimal top navigation.

Authenticated:
Top navigation + contextual navigation inside dashboards.

Mobile:
Compact top header + bottom navigation where appropriate.

Do not use a large permanent sidebar as the primary navigation.

The experience should remain consumer-friendly.

--------------------------------------------------

## 32. Buttons

Primary:
Blue filled button

Secondary:
Outline / neutral button

Destructive:
Red/error button

Examples:

[Find Property]
[Apply for Property]
[View Trust Profile]
[Add Property]

Buttons must support:

Default
Hover
Active
Focus
Disabled
Loading

Example:

[Submitting...]

--------------------------------------------------

## 33. Forms

Every form requires:

Visible label
Input
Helper text where needed
Validation
Error state
Loading state
Success feedback

Example:

Monthly Rent

[₹ 18,000]

Security deposit must be greater than or equal to zero.

Never use placeholders as the only labels.

--------------------------------------------------

## 34. Empty States

Every major screen requires a genuine empty state.

No applications:

"No applications yet."

No rentals:

"No rental history yet."

No properties:

"You haven't listed any properties yet."

No reviews:

"No reviews yet."

New Trust Profile:

"Limited rental history available."

Provide an appropriate CTA where useful.

--------------------------------------------------

## 35. Loading States

Use skeleton loaders for:

Property cards
Trust profiles
Dashboards
Application lists
Payment tables
Rental history

Action loading:

[Saving...]
[Submitting...]

Do not display blank screens while loading.

--------------------------------------------------

## 36. Error States

Use calm, direct messages.

Network:

"Unable to connect to TrustRent. Please try again."

Server:

"Something went wrong while loading this information."

Permission:

"You don't have permission to view this page."

Submission:

"Your application could not be submitted. Please try again."

Always provide Retry or another useful action where possible.

--------------------------------------------------

## 37. Voice & Tone

Tone:

Clear
Calm
Human
Professional
Transparent

Good:

"Application submitted."

"Payment recorded."

"Your Trust Profile was updated."

"2 late payments recorded."

Avoid:

"Awesome! You crushed your rent payment! 🎉"

Avoid manipulative/gamified language.

--------------------------------------------------

## 38. Trust Language

TrustRent must never guarantee a person's behaviour.

Never:

"This landlord is 100% trustworthy."

Never:

"This tenant is completely safe."

Use:

"Trust Score: 912"
"5 completed rentals"
"4.7/5 average tenant rating"
"0 verified complaints recorded"

TrustRent gives users evidence for decisions.

--------------------------------------------------

## 39. Motion

Motion should be subtle.

Allowed:
- card hover
- progress transitions
- page transitions
- skeleton shimmer
- button loading states
- status changes

Avoid:
- gamified explosions
- excessive bouncing
- flashy score animations
- unnecessary parallax

Trust should feel stable, not entertaining.

--------------------------------------------------

## 40. Accessibility

Minimum:

WCAG 2.1 AA

Requirements:

- 4.5:1 minimum contrast for body text
- keyboard-accessible controls
- visible focus rings
- semantic HTML
- meaningful form labels
- aria-describedby for form errors
- accessible dialogs
- accessible tables
- alt text for meaningful property images
- do not rely on colour alone

--------------------------------------------------

## 41. Responsive Layout

Desktop:
1200px max content width.

Tablet:
2-column where appropriate.

Mobile:
1-column priority layout.

Important Trust information must remain visible at every breakpoint.

--------------------------------------------------

## 42. Data Visualization Rules

Use visualization only when it increases understanding.

Use:
- horizontal Trust Score breakdown
- payment summary
- Trust History timeline
- simple trend chart for personal score history where useful

Avoid:
- radar charts on every property card
- city-wide analytics in V1
- complicated heatmaps
- unnecessary charts

The product should feel informative, not overloaded.

--------------------------------------------------

## 43. Signature Components

The following components define the TrustRent visual identity:

1. Trust Score
2. Trust Score Breakdown
3. Trust History Timeline
4. Payment Reliability Summary
5. Monthly Rent Table
6. Trust Profile
7. Property + Landlord Trust Card

These components must remain visually consistent across all pages.

--------------------------------------------------

## 44. Key Visual Relationship

The primary interaction pattern is:

PROPERTY

2 BHK
₹18,000
Bangalore

        +

LANDLORD TRUST

912 / 1000
5 Rentals
4.7 ★
Verified

        +

RENTAL EVIDENCE

Payment history
Reviews
Rental history

        ↓

INFORMED DECISION

This is the defining TrustRent experience.

--------------------------------------------------

## 45. Design Principles

1. Humanize trust data.
2. Show evidence behind every important score.
3. Make rental behaviour visible over time.
4. Keep property discovery simple.
5. Do not turn TrustRent into a credit-score dashboard.
6. Do not turn TrustRent into a social-review platform.
7. Do not turn TrustRent into a generic broker portal.
8. Keep the experience calm and professional.
9. Make important information scannable.
10. Always design empty, loading and error states.

--------------------------------------------------

## 46. AI Implementation Rules

When generating React UI:

- Follow this document exactly.
- Reuse shadcn/ui components.
- Use Tailwind CSS.
- Use the defined CSS colour tokens.
- Use Inter.
- Use 4px spacing increments.
- Reuse TrustRent-specific components.
- Do not invent new colours.
- Do not introduce a new design style for individual pages.
- Do not add V2 features.
- Do not redesign existing components without instruction.
- Every API-driven screen must include loading, empty, success and error states.
- Trust Score must always show context/evidence.
- Monthly rent history must remain a structured table.
- Trust History may summarize payment events but cannot replace detailed payment records.
- Do not rank or reject users solely based on Trust Score.
- Do not claim that Trust Score guarantees safety or reliability.
- Preserve desktop, tablet and mobile usability.
- Preserve WCAG 2.1 AA requirements.

This is the official TrustRent V1 visual contract.
The important design decision here
I would lock Concept 2 as the base, but make three specific adjustments:
Narrative Reputation → gives TrustRent its human identity.
Concept 1 payment table → gives the rent tracking system concrete, auditable data.
Limited Concept 3 analytics → only for places where it genuinely explains the score.
So the visual hierarchy becomes:
PROPERTY DISCOVERY
        ↓
LANDLORD / TENANT TRUST
        ↓
TRUST EVIDENCE
        ↓
PAYMENT + RENTAL HISTORY
        ↓
TRUST SCORE
rather than:
847 / 1000
↓
everything else
That is the direction I would use for the actual React build.
Privacy / Trust Profile visibility
Change:
04-UI-UX Brief → Trust Profile
You already define the Trust Profile and its content.
Add:
### Privacy Rules

Public/other-user-visible:
- Trust Score
- Trust Score factors
- Verification status
- Completed rental count
- Average review rating
- Relevant trust history

Private:
- Contact details where not explicitly required
- Private account information
- Sensitive personal information
- Private rental details that are not necessary for evaluation
Then put the actual backend authorization rules in TRD.

9. Trust Score disclaimer
You already have this in the UI/UX brief, so don't create it again.
Your current Trust Language section already says not to claim that someone is guaranteed trustworthy and to show evidence instead.
Just strengthen that section with:
Every Trust Score view must clearly communicate:

"Trust Score is based on available rental activity and is provided for
decision support. It does not guarantee future behaviour."
So this one is an edit, not a new section elsewhere.

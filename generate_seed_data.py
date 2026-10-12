import uuid
from datetime import datetime, timedelta

def generate_uuid():
    return str(uuid.uuid4())

landlords = [(generate_uuid(), f"landlord{i}@test.com", "Landlord " + str(i)) for i in range(1, 11)]
tenants = [(generate_uuid(), f"tenant{i}@test.com", "Tenant " + str(i)) for i in range(1, 11)]

properties = []
for i in range(1, 16):
    landlord = landlords[i % 10]
    properties.append((generate_uuid(), landlord[0], f"Property {i}", f"City {i % 3}"))

applications = []
# Tenant 1 applies for Property 1
applications.append((generate_uuid(), properties[0][0], tenants[0][0], "ACCEPTED"))
# Tenant 2 applies for Property 2
applications.append((generate_uuid(), properties[1][0], tenants[1][0], "ACCEPTED"))
# Tenant 3 applies for Property 3
applications.append((generate_uuid(), properties[2][0], tenants[2][0], "PENDING"))

rentals = []
# Rental for Property 1
rentals.append((generate_uuid(), properties[0][0], tenants[0][0], landlords[0][0], applications[0][0], "COMPLETED"))
# Rental for Property 2
rentals.append((generate_uuid(), properties[1][0], tenants[1][0], landlords[1][0], applications[1][0], "ACTIVE"))

payments = []
# Payments for Rental 1
payments.append((generate_uuid(), rentals[0][0], tenants[0][0], "ON_TIME"))
payments.append((generate_uuid(), rentals[0][0], tenants[0][0], "EARLY"))

reviews = []
# Review for Rental 1
reviews.append((generate_uuid(), rentals[0][0], tenants[0][0], landlords[0][0], 5))

with open("backend/src/main/resources/data.sql", "w") as f:
    for l in landlords:
        f.write(f"INSERT INTO users (id, email, password, role, name, phone, created_at, updated_at) VALUES ('{l[0]}', '{l[1]}', '$2a$10$X0e/j2e6b.R9e/v8q0q9O.1t/rK0v7v4N0j7v4N0j7v4N0j7v4N0j', 'LANDLORD', '{l[2]}', '1234567890', NOW(), NOW()) ON CONFLICT DO NOTHING;\n")
    for t in tenants:
        f.write(f"INSERT INTO users (id, email, password, role, name, phone, created_at, updated_at) VALUES ('{t[0]}', '{t[1]}', '$2a$10$X0e/j2e6b.R9e/v8q0q9O.1t/rK0v7v4N0j7v4N0j7v4N0j7v4N0j', 'TENANT', '{t[2]}', '0987654321', NOW(), NOW()) ON CONFLICT DO NOTHING;\n")
    for p in properties:
        f.write(f"INSERT INTO properties (id, landlord_id, title, description, location, city, monthly_rent, security_deposit, bedrooms, bathrooms, property_type, status, created_at, updated_at) VALUES ('{p[0]}', '{p[1]}', '{p[2]}', 'Description', 'Location', '{p[3]}', 1000, 2000, 2, 1, 'APARTMENT', 'AVAILABLE', NOW(), NOW()) ON CONFLICT DO NOTHING;\n")

    # We omit application, rental, payment, review seed data as the application logic will create them dynamically during e2e tests

from playwright.sync_api import sync_playwright
import time
import os

def run_cuj(page):
    page.goto("http://localhost:5173")
    page.wait_for_timeout(1000)

    page.evaluate("window.localStorage.clear();")

    # Register as Landlord
    page.goto("http://localhost:5173/register")
    page.wait_for_timeout(1000)
    page.fill('input[name="name"]', "Test Landlord " + str(time.time()))
    page.fill('input[name="email"]', "landlord" + str(time.time()) + "@test.com")
    page.fill('input[name="password"]', "password123")
    page.select_option('select[name="role"]', "LANDLORD")
    page.click('button[type="submit"]')
    page.wait_for_timeout(2000)

    # Add Property
    page.goto("http://localhost:5173/landlord/properties")
    page.wait_for_timeout(1000)
    page.click("text=+ Add Property")
    page.wait_for_timeout(500)
    page.fill('input[name="title"]', "Test Apartment")
    page.fill('textarea[name="description"]', "A nice place")
    page.fill('input[name="address"]', "Downtown")
    page.fill('input[name="city"]', "TestCity")
    page.fill('input[name="monthlyRent"]', "1000")
    page.fill('input[name="securityDeposit"]', "2000")
    page.fill('input[name="bedrooms"]', "2")
    page.fill('input[name="bathrooms"]', "1")
    page.click('button[type="submit"]')
    page.wait_for_timeout(2000)

    page.get_by_role("button", name="Logout").click()
    page.wait_for_timeout(1000)

    # Register as Tenant
    page.goto("http://localhost:5173/register")
    page.wait_for_timeout(1000)
    page.fill('input[name="name"]', "Test Tenant " + str(time.time()))
    page.fill('input[name="email"]', "tenant" + str(time.time()) + "@test.com")
    page.fill('input[name="password"]', "password123")
    page.select_option('select[name="role"]', "TENANT")
    page.click('button[type="submit"]')
    page.wait_for_timeout(2000)

    # Search Property
    page.goto("http://localhost:5173/properties")
    page.wait_for_timeout(2000)

    # Click to View
    page.click("text=View")
    page.wait_for_timeout(2000)

    # Apply
    # Use generic locator to find the textarea
    page.fill('textarea[id="application-message"]', "I would like to apply")
    page.click("text=Apply for Property")
    page.wait_for_timeout(2000)

    # Take screenshot of E2E
    page.screenshot(path="/home/jules/verification/screenshots/e2e.png")


if __name__ == "__main__":
    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context(
            record_video_dir="/home/jules/verification/videos"
        )
        page = context.new_page()
        try:
            run_cuj(page)
        except Exception as e:
            print(f"Failed: {e}")
        finally:
            context.close()
            browser.close()

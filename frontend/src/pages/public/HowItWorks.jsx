import { Link } from "react-router-dom";

export default function HowItWorks() {
  return (
    <main className="flex-1">
      <div className="mx-auto max-w-7xl px-6 py-16">
        <h1 className="text-4xl font-bold">How Trust Works</h1>

        <p className="mt-4 text-xl text-slate-500">
          TrustRent helps you build and evaluate a rental reputation using actual behavior.
        </p>

        <div className="mt-12 grid gap-10 md:grid-cols-3">
          <section className="rounded-2xl border bg-white p-8">
            <h2 className="text-2xl font-bold">1. Build Your Profile</h2>
            <p className="mt-4 text-slate-600 leading-relaxed">
              When you register, you get a Trust Profile. This profile calculates a Trust Score based on your rent payment reliability, past rentals, and reviews.
            </p>
          </section>

          <section className="rounded-2xl border bg-white p-8">
            <h2 className="text-2xl font-bold">2. Track Rental Activity</h2>
            <p className="mt-4 text-slate-600 leading-relaxed">
              Your ongoing rentals securely track rent payments. Paying early or on time boosts your score, while missed payments lower it.
            </p>
          </section>

          <section className="rounded-2xl border bg-white p-8">
            <h2 className="text-2xl font-bold">3. Review and Reflect</h2>
            <p className="mt-4 text-slate-600 leading-relaxed">
              After a rental concludes, both the tenant and landlord can review their experience, adding transparent context to the Trust Score.
            </p>
          </section>
        </div>

        <div className="mt-16 rounded-2xl bg-blue-50 p-10 text-center">
          <h2 className="text-3xl font-bold">Ready to start?</h2>
          <div className="mt-8 flex justify-center gap-4">
            <Link to="/register" className="rounded-lg bg-blue-600 px-6 py-3 font-bold text-white hover:bg-blue-700">
              Create an Account
            </Link>
            <Link to="/properties" className="rounded-lg border border-blue-600 px-6 py-3 font-bold text-blue-600 hover:bg-blue-100">
              Browse Properties
            </Link>
          </div>
        </div>
      </div>
    </main>
  );
}

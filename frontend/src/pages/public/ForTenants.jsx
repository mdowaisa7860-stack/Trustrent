import { Link } from "react-router-dom";

export default function ForTenants() {
  return (
    <main className="flex-1">
      <div className="mx-auto max-w-7xl px-6 py-16">
        <h1 className="text-4xl font-bold">TrustRent for Tenants</h1>

        <p className="mt-4 text-xl text-slate-500 max-w-2xl">
          Apply for properties with confidence. Build a verifiable rental history that shows landlords you are reliable.
        </p>

        <div className="mt-12 grid gap-10 md:grid-cols-2">
          <section className="rounded-2xl border bg-white p-8">
            <h2 className="text-2xl font-bold text-blue-600">Stand Out</h2>
            <p className="mt-4 text-slate-600 leading-relaxed">
              Your Trust Score highlights your consistent rent payments and good reviews. Use it to stand out from other applicants when searching for your next home.
            </p>
          </section>

          <section className="rounded-2xl border bg-white p-8">
            <h2 className="text-2xl font-bold text-blue-600">Transparent Search</h2>
            <p className="mt-4 text-slate-600 leading-relaxed">
              Before you apply, view the landlord's Trust Profile. You can see their maintenance ratings and check their rental history to ensure a reliable partnership.
            </p>
          </section>
        </div>

        <div className="mt-12 text-center">
            <Link to="/register" className="inline-block rounded-lg bg-blue-600 px-8 py-4 font-bold text-white hover:bg-blue-700">
              Start Building Your Profile
            </Link>
        </div>
      </div>
    </main>
  );
}

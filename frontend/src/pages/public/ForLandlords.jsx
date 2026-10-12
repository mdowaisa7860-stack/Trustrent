import { Link } from "react-router-dom";

export default function ForLandlords() {
  return (
    <main className="flex-1">
      <div className="mx-auto max-w-7xl px-6 py-16">
        <h1 className="text-4xl font-bold">TrustRent for Landlords</h1>

        <p className="mt-4 text-xl text-slate-500 max-w-2xl">
          Find reliable tenants and easily manage your properties using verified trust data.
        </p>

        <div className="mt-12 grid gap-10 md:grid-cols-2">
          <section className="rounded-2xl border bg-white p-8">
            <h2 className="text-2xl font-bold text-blue-600">Verified Tenants</h2>
            <p className="mt-4 text-slate-600 leading-relaxed">
              When reviewing applications, evaluate prospective tenants using their Trust Profile. Make data-driven decisions based on their payment history and prior landlord reviews.
            </p>
          </section>

          <section className="rounded-2xl border bg-white p-8">
            <h2 className="text-2xl font-bold text-blue-600">Simple Property Management</h2>
            <p className="mt-4 text-slate-600 leading-relaxed">
              List your properties, manage applications, and record rent payments from a single dashboard.
            </p>
          </section>
        </div>

        <div className="mt-12 text-center">
            <Link to="/register" className="inline-block rounded-lg bg-blue-600 px-8 py-4 font-bold text-white hover:bg-blue-700">
              List Your First Property
            </Link>
        </div>
      </div>
    </main>
  );
}

import { useEffect, useState } from "react";
import api from "../../services/api";

export default function RentalHistory() {
  const [rentals, setRentals] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    async function loadHistory() {
      try {
        // Find completed rentals for history
        const userResp = await api.get("/users/me");
        const userId = userResp.data.id;
        const response = await api.get(`/users/${userId}/rental-history`);
        setRentals(response.data);
      } catch (err) {
        setError("Unable to load rental history.");
      } finally {
        setLoading(false);
      }
    }
    loadHistory();
  }, []);

  return (
    <main className="flex-1">
      <div className="mx-auto max-w-5xl px-6 py-10">
        <h1 className="text-4xl font-bold">Rental History</h1>
        <p className="mt-2 text-[var(--text-secondary)]">Your past, completed rentals.</p>

        {loading && <p className="mt-8">Loading history...</p>}
        {error && <div className="mt-8 rounded-lg bg-red-50 p-4 text-red-700">{error}</div>}

        {!loading && !error && rentals.length === 0 && (
          <div className="mt-8 rounded-2xl border bg-white p-10 text-center">
            <h2 className="text-xl font-semibold">No completed rentals yet.</h2>
          </div>
        )}

        {!loading && !error && rentals.length > 0 && (
          <div className="mt-8 space-y-6">
            {rentals.map((rental) => (
              <div key={rental.id} className="rounded-2xl border bg-white p-6 shadow-sm">
                <div className="flex justify-between">
                  <div>
                    <h3 className="text-xl font-bold">{rental.propertyTitle}</h3>
                    <p className="text-slate-500">{rental.startDate} – {rental.endDate || "Present"}</p>
                  </div>
                  <span className="rounded-full bg-green-100 px-3 py-1 text-xs font-semibold text-green-700 h-fit">
                    {rental.status}
                  </span>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </main>
  );
}

import { useEffect, useState } from "react";
import api from "../../services/api";

export default function MyProfile() {
  const [userProfile, setUserProfile] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    async function loadProfile() {
      try {
        const response = await api.get("/users/me");
        setUserProfile(response.data);
      } catch (err) {
        setError("Unable to load profile information.");
      } finally {
        setLoading(false);
      }
    }
    loadProfile();
  }, []);

  return (
    <main className="flex-1">
      <div className="mx-auto max-w-5xl px-6 py-10">
        <h1 className="text-4xl font-bold">My Profile</h1>
        <p className="mt-2 text-[var(--text-secondary)]">Manage your personal information.</p>

        {loading && <p className="mt-8">Loading profile...</p>}
        {error && <div className="mt-8 rounded-lg bg-red-50 p-4 text-red-700">{error}</div>}

        {userProfile && (
          <div className="mt-8 rounded-2xl border bg-white p-6 shadow-sm">
            <div className="grid gap-6 md:grid-cols-2">
              <div>
                <p className="text-sm font-medium text-slate-500">Name</p>
                <p className="mt-1 text-lg font-semibold">{userProfile.name}</p>
              </div>
              <div>
                <p className="text-sm font-medium text-slate-500">Email</p>
                <p className="mt-1 text-lg font-semibold">{userProfile.email}</p>
              </div>
              <div>
                <p className="text-sm font-medium text-slate-500">Role</p>
                <p className="mt-1 text-lg font-semibold">{userProfile.role}</p>
              </div>
              <div>
                <p className="text-sm font-medium text-slate-500">Phone</p>
                <p className="mt-1 text-lg font-semibold">{userProfile.phone || "Not provided"}</p>
              </div>
            </div>

            {/* The profile editing is out of scope for V1 if not strictly defined, so just display for now */}
          </div>
        )}
      </div>
    </main>
  );
}

export default function TrustScoreBreakdown({ profile }) {
  if (!profile) return null;

  const categories = [
    { label: "Payment Reliability", value: profile.paymentScore ?? 0 },
    { label: "Rental History", value: profile.rentalHistoryScore ?? 0 },
    { label: "Reviews", value: profile.reviewScore ?? 0 },
    { label: "Property Care", value: profile.careScore ?? 0 },
    { label: "Rule Compliance", value: profile.complianceScore ?? 0 },
    { label: "Complaints", value: profile.complaintScore ?? 0 },
  ];

  return (
    <section className="mt-8 rounded-2xl border bg-white p-6 shadow-sm">
      <h2 className="text-xl font-bold">Trust Score Breakdown</h2>

      <div className="mt-6 space-y-4">
        {categories.map((cat, index) => {
          // Prevent negative widths; assume max category contribution is typically out of a portion of 1000.
          // For visualization, we map score out of 200 or simply render its value if absolute scale is not fixed.
          const maxCategoryScore = 200; // Arbitrary visualization max, adjusting simply
          let percentage = Math.max(0, Math.min(100, (cat.value / maxCategoryScore) * 100));
          if (cat.label === "Complaints") {
             // Complaints are negative, we map them differently or just show value
             percentage = Math.max(0, Math.min(100, (Math.abs(cat.value) / 100) * 100));
          }

          return (
            <div key={index}>
              <div className="flex justify-between text-sm font-medium">
                <span className="text-slate-600">{cat.label}</span>
                <span className={cat.value < 0 ? "text-red-600" : "text-slate-900"}>{cat.value}</span>
              </div>
              <div className="mt-1 h-2 w-full rounded-full bg-slate-100">
                <div
                  className={`h-2 rounded-full ${cat.value < 0 ? 'bg-red-500' : 'bg-blue-600'}`}
                  style={{ width: `${percentage}%` }}
                ></div>
              </div>
            </div>
          );
        })}
      </div>
    </section>
  );
}

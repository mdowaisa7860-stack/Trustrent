export default function TrustHistoryTimeline({ events }) {
  if (!events || events.length === 0) {
    return (
      <section className="mt-8 rounded-2xl border bg-white p-6 shadow-sm">
        <h2 className="text-xl font-bold">Trust History Timeline</h2>
        <p className="mt-4 text-slate-500">No recent trust events.</p>
      </section>
    );
  }

  return (
    <section className="mt-8 rounded-2xl border bg-white p-6 shadow-sm">
      <h2 className="text-xl font-bold">Trust History Timeline</h2>

      <div className="mt-6">
        <div className="relative border-l border-slate-200 ml-3">
          {events.map((event, index) => {
            const isPositive = event.scoreChange > 0;
            const isNegative = event.scoreChange < 0;
            const isNeutral = event.scoreChange === 0;

            let bgColor = "bg-slate-100";
            let textColor = "text-slate-600";

            if (isPositive) {
              bgColor = "bg-green-100";
              textColor = "text-green-700";
            } else if (isNegative) {
              bgColor = "bg-red-100";
              textColor = "text-red-700";
            }

            return (
              <div key={event.id || index} className="mb-8 ml-6 last:mb-0">
                <span className={`absolute -left-3 flex h-6 w-6 items-center justify-center rounded-full ring-8 ring-white ${bgColor}`}>
                  <div className={`h-2.5 w-2.5 rounded-full ${isPositive ? 'bg-green-600' : isNegative ? 'bg-red-600' : 'bg-slate-400'}`}></div>
                </span>

                <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between">
                  <div>
                    <h3 className="text-sm font-semibold text-slate-900">{event.eventType}</h3>
                    <p className="text-sm text-slate-500">{event.description}</p>
                    <p className="mt-1 text-xs text-slate-400">
                      {event.createdAt ? new Date(event.createdAt).toLocaleDateString() : 'Recent'}
                    </p>
                  </div>

                  <div className="mt-2 sm:mt-0">
                    <span className={`inline-flex items-center rounded-full px-2.5 py-0.5 text-xs font-semibold ${bgColor} ${textColor}`}>
                      {isPositive ? '+' : ''}{event.scoreChange} pts
                    </span>
                  </div>
                </div>
              </div>
            );
          })}
        </div>
      </div>
    </section>
  );
}

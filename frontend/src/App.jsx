import { useEffect, useState } from "react";

export default function App() {
  const [goal, setGoal] = useState("Read sample.txt, show stats, and save to stats.json");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [runResult, setRunResult] = useState(null);

  const [tools, setTools] = useState([]);
  const [toolsLoading, setToolsLoading] = useState(false);

  async function loadTools() {
    setToolsLoading(true);
    setError("");
    try {
      const res = await fetch("/api/tools");
      if (!res.ok) throw new Error(`Failed to load tools (${res.status})`);
      const data = await res.json();
      // Your PowerShell shows { value: [...], Count: n }
      const list = Array.isArray(data) ? data : (data.value ?? []);
      setTools(list);
    } catch (e) {
      setError(e.message);
    } finally {
      setToolsLoading(false);
    }
  }

  async function runWorkflow() {
    setLoading(true);
    setError("");
    setRunResult(null);

    try {
      const res = await fetch("/api/workflows/run", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ goal }),
      });

      if (!res.ok) {
        const text = await res.text();
        throw new Error(`Run failed (${res.status}): ${text}`);
      }

      const data = await res.json();
      setRunResult(data);
    } catch (e) {
      setError(e.message);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    // Auto-load tools once (optional)
    loadTools();
  }, []);

  return (
    <div style={{ fontFamily: "system-ui, Arial", padding: 16, maxWidth: 1100, margin: "0 auto" }}>
      <h1 style={{ marginBottom: 4 }}>Bryan</h1>
      <div style={{ color: "#666", marginBottom: 16 }}>
        Natural language → Plan → Execute (deterministic workflow engine)
      </div>

      <div style={{ display: "grid", gridTemplateColumns: "1fr", gap: 12 }}>
        <div style={{ border: "1px solid #ddd", borderRadius: 8, padding: 12 }}>
          <div style={{ fontWeight: 600, marginBottom: 8 }}>Goal</div>
          <textarea
            value={goal}
            onChange={(e) => setGoal(e.target.value)}
            rows={3}
            style={{ width: "90%", padding: 10, borderRadius: 6, border: "1px solid #ccc" }}
            placeholder='e.g., "Read sample.txt and show stats"'
          />

          <div style={{ display: "flex", gap: 8, marginTop: 10 }}>
            <button
              onClick={runWorkflow}
              disabled={loading || !goal.trim()}
              style={{ padding: "10px 14px", borderRadius: 6, border: "1px solid #333", cursor: "pointer" }}
            >
              {loading ? "Running..." : "Run"}
            </button>

            <button
              onClick={loadTools}
              disabled={toolsLoading}
              style={{ padding: "10px 14px", borderRadius: 6, border: "1px solid #999", cursor: "pointer" }}
            >
              {toolsLoading ? "Loading Tools..." : "Reload Tools"}
            </button>
          </div>

          {error && (
            <div style={{ marginTop: 10, color: "crimson", whiteSpace: "pre-wrap" }}>
              {error}
            </div>
          )}
        </div>

        <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: 12 }}>
          <Panel title="Available Tools">
            {tools.length === 0 ? (
              <div style={{ color: "#666" }}>No tools loaded.</div>
            ) : (
              <div style={{ display: "grid", gap: 10 }}>
                {tools.map((t) => (
                  <div key={t.name} style={{ border: "1px solid #eee", borderRadius: 8, padding: 10 }}>
                    <div style={{ fontWeight: 700 }}>{t.name}</div>
                    <div style={{ color: "#666", margin: "6px 0" }}>{t.description}</div>
                    <div style={{ fontSize: 13 }}>
                      <div style={{ fontWeight: 600, marginBottom: 4 }}>Inputs</div>
                      {t.inputs?.length ? (
                        <ul style={{ margin: 0, paddingLeft: 16 }}>
                          {t.inputs.map((inp) => (
                            <li key={inp.name}>
                              <b>{inp.name}</b> ({inp.type}) {inp.required ? "required" : "optional"} —{" "}
                              <span style={{ color: "#555" }}>{inp.description}</span>
                            </li>
                          ))}
                        </ul>
                      ) : (
                        <div style={{ color: "#666" }}>No inputs.</div>
                      )}
                    </div>
                  </div>
                ))}
              </div>
            )}
          </Panel>

          <Panel title="Run Output">
            {!runResult ? (
              <div style={{ color: "#666" }}>Run a goal to see results.</div>
            ) : (
              <div style={{ display: "grid", gap: 10 }}>
                <SubPanel title="Planned Steps">
                  <JsonBox value={runResult.plannedSteps} />
                </SubPanel>

                <SubPanel title="Logs">
                  <JsonBox value={runResult.logs} />
                </SubPanel>

                <SubPanel title="Final Output">
                  <JsonBox value={runResult.finalOutput} />
                </SubPanel>

                <SubPanel title="State (debug)">
                  <JsonBox value={runResult.state} />
                </SubPanel>
              </div>
            )}
          </Panel>
        </div>
      </div>
    </div>
  );
}

function Panel({ title, children }) {
  return (
    <div style={{ border: "1px solid #ddd", borderRadius: 8, padding: 12, minHeight: 240 }}>
      <div style={{ fontWeight: 700, marginBottom: 10 }}>{title}</div>
      {children}
    </div>
  );
}

function SubPanel({ title, children }) {
  return (
    <div style={{ border: "1px solid #eee", borderRadius: 8, padding: 10 }}>
      <div style={{ fontWeight: 700, marginBottom: 8 }}>{title}</div>
      {children}
    </div>
  );
}

function JsonBox({ value }) {
  return (
    <pre
      style={{
        margin: 0,
        padding: 10,
        borderRadius: 6,
        background: "#0b1020",
        color: "#e6e6e6",
        overflowX: "auto",
        fontSize: 12,
      }}
    >
      {JSON.stringify(value, null, 2)}
    </pre>
  );
}

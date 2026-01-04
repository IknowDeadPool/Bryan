# Bryan – Workflow Automation Engine

Bryan is a **Spring Boot–based workflow automation engine** that converts **natural language goals** into **deterministic, multi-step workflows** and executes them using a **plugin-based tool system**.

The project is designed to demonstrate **backend architecture, dependency injection, extensibility, and execution orchestration**, with AI-ready planning built on top of a clean core.

---

##  What Bryan Does

You can send a request like:

> “Read sample.txt, show stats, and save the result to stats.json”

And Bryan will:

1. Plan the required steps
2. Execute tools in the correct order
3. Maintain shared execution state
4. Return logs, state, and final output
5. Save results to disk if requested

---

##  Architecture Overview

Bryan is built around **three core layers**:

### 1️. Planner
- Converts natural language goals into structured workflow steps
- Uses **tool metadata and discovery**
- Does NOT execute anything
- Can be replaced later with an AI/LLM planner

---

### 2️. Executor
- Runs workflow steps sequentially
- Maintains shared execution state
- Captures logs and execution results
- Ensures deterministic execution

---

### 3. Tools (Plugin System)
- Each tool performs **one focused task**
- Tools are **self-describing**
- New tools can be added **without changing executor logic**

---

##  Built-in Tools

| Tool | Description |
|----|----|
| **FileLoader** | Loads text files from disk (UTF-8 / UTF-16 BOM aware) |
| **TextStats** | Computes character, word, and line counts |
| **SaveToFile** | Saves workflow output to disk as text or formatted JSON |

Each tool exposes:
- Name
- Description
- Required and optional inputs
  GET /api/tools

Available via:

---

##  API Endpoints

### Run a full workflow (Plan + Execute)


POST /api/workflows/run



Request:
```json
{
  "goal": "Read sample.txt, show stats, and save to stats.json"
}
```

## Future Enhancements

- LLM-based planner (local AI / Ollama)

- Parallel workflow execution

- Tool dependency graphs

- Persistent workflow storage

- Web UI for visual workflows

## Tech Stack

- Java 24

- Spring Boot

- Maven

- Jackson (JSON serialization)

- REST APIs

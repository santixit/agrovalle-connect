#!/usr/bin/env python3
"""Calculate Kanban flow metrics from recorded item events and period dates.

Dates must be ISO-8601 (YYYY-MM-DD or a timestamp). Missing events stay missing;
the script never infers historical transitions from current board counts.
"""

from __future__ import annotations

import argparse
import csv
import statistics
import sys
from datetime import date, datetime
from pathlib import Path


def parse_date(value: str, field: str) -> date | None:
    value = value.strip()
    if not value:
        return None
    try:
        return datetime.fromisoformat(value.replace("Z", "+00:00")).date()
    except ValueError as error:
        raise ValueError(f"{field}: fecha no válida {value!r}; usa ISO-8601") from error


def read_csv(path: Path) -> list[dict[str, str]]:
    with path.open(encoding="utf-8-sig", newline="") as source:
        return list(csv.DictReader(source))


def summarize(values: list[int]) -> str:
    if not values:
        return "sin datos"
    return (
        f"mediana={statistics.median(values):g} días, "
        f"rango={min(values)}-{max(values)} días (n={len(values)})"
    )


def calculate(periods: list[dict[str, str]], items: list[dict[str, str]]) -> list[str]:
    output: list[str] = []
    for row in periods:
        period_id = row.get("period_id", "").strip()
        start = parse_date(row.get("start_date", ""), f"{period_id}.start_date")
        end = parse_date(row.get("end_date", ""), f"{period_id}.end_date")
        if not period_id or start is None or end is None:
            continue
        if end < start:
            raise ValueError(f"{period_id}: end_date es anterior a start_date")

        throughput = 0
        cycle_days: list[int] = []
        lead_days: list[int] = []
        for item in items:
            if item.get("period_id", "").strip() != period_id:
                continue
            item_id = item.get("item_id", "").strip() or "(sin item_id)"
            backlog = parse_date(item.get("backlog_at", ""), f"{item_id}.backlog_at")
            started = parse_date(item.get("first_in_progress_at", ""), f"{item_id}.first_in_progress_at")
            done = parse_date(item.get("done_at", ""), f"{item_id}.done_at")
            deployed = parse_date(item.get("deployed_at", ""), f"{item_id}.deployed_at")

            if done is not None and start <= done <= end:
                throughput += 1
            if started is not None and done is not None and done >= started:
                cycle_days.append((done - started).days)
            if backlog is not None and deployed is not None and deployed >= backlog:
                lead_days.append((deployed - backlog).days)

        complete_value = row.get("done_events_complete", "").strip().lower()
        if complete_value == "true":
            throughput_result = f"{throughput} elementos"
        else:
            throughput_result = "sin datos (cierres del periodo no confirmados como completos)"

        output.append(
            f"{period_id}: throughput={throughput_result}; "
            f"cycle time {summarize(cycle_days)}; lead time {summarize(lead_days)}"
        )
    return output


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--periods", type=Path, default=Path("docs/evidencias/kanban-periodos-sesion7.csv"))
    parser.add_argument("--items", type=Path, default=Path("docs/evidencias/kanban-items-sesion7.csv"))
    args = parser.parse_args()
    try:
        results = calculate(read_csv(args.periods), read_csv(args.items))
    except (OSError, ValueError) as error:
        print(f"Error: {error}", file=sys.stderr)
        return 2
    if not results:
        print("Sin periodos fechados. Completa period_id, start_date y end_date; no se infieren métricas.")
        return 0
    print("\n".join(results))
    return 0


if __name__ == "__main__":
    raise SystemExit(main())

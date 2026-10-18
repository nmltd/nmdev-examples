# Wiki publishing

`Home.md` and `Benchmark-Results.md` are published in the separate GitHub Wiki. Keep their repository copies synchronized when editing the live wiki. Home links the versioned tutorials in `docs/`; the other Markdown files remain wiki-ready tutorial copies. The live wiki is a separate Git repository and committing this folder does not publish it automatically.

The benchmark dashboard is generated in `docs/index.html` and published by GitHub Pages from `main` → `/docs`. See [benchmark publishing instructions](../benchmark/README.md#published-dashboard).

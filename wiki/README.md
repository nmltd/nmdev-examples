# Wiki directory

The live wiki is a short directory linking to GitHub Pages. Tutorial content is maintained once in `docs/*.md` and `docs/tutorials/*.md`; no wiki tutorial copies are maintained. The live wiki is a separate Git repository; committing this directory does not update it automatically.

Build the website with `python3 benchmark/scripts/publish-pages.py benchmark/results/2026-10-18-expanded`. This generates the tutorial pages and benchmark pages together.

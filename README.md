# Live

This is my live workspace for competitive programming. I keep almost everything I write here — Codeforces rounds, LeetCode grinds, NeetCode practice, interview prep, and the occasional CTF or scripting detour. Nothing in this repo is meant to be a polished library; it is the day-to-day notebook I open whenever I sit down to solve a problem.

I push to it from whichever machine I happen to be on (laptop, desk, Ubuntu box), which is why the commit history is short and the messages are not exactly literature. The code itself is what I care about.

## How the repo is laid out

Everything lives under `src/`, organised by where the problem came from rather than by topic. When I am in the middle of a contest the last thing I want to think about is folder taxonomy, so the rules are loose on purpose.

```
src/
├── cf/         Codeforces — A.java through F.java for each round, plus stray Solution/Test files
├── leetcode/   LeetCode — one file per problem, named LC<problem-number>.java
├── neetCode/   NeetCode roadmap problems, same naming convention
├── practice/   Free-form scratch space — anything I am exploring that does not have a home yet
├── dev/        Interview-style problems I work through end-to-end
├── imp/        My personal library — reusable templates and data structures I copy into solutions
├── ENC/        CTF / cryptography work, mostly AES-CBC bit-flipping experiments
├── bin/        One-off scripts (scrapers, executor playgrounds, parsing utilities)
├── go/         Same problems, written in Go when I want to keep the syntax fresh
├── TS/         Same idea for TypeScript
├── timestamp/  Quick API / collection scratches I throw away later
├── TEST/       Test image inputs
└── TEST1/      More test image inputs
```

### `imp/` — the part I actually reuse

If there is any folder worth pointing at, it is this one. `Library.java` is a single big file I have been adding to for years: combinatorics, number theory, graph algorithms (Dijkstra, Bellman-Ford, Floyd-Warshall, Prim, Edmonds-Karp, Hungarian method), DP templates (knapsack, edit distance, LIS, LCS, Kadane), geometry helpers, a `Trie`, an `LRUCache`, and a `Fraction` class. It is messy on purpose — the comments next to each method are notes to myself about edge cases I have hit. Alongside it sit standalone files I pull in when needed: `FastReader`, `AVLTree`, `DisjointSet`, `SegmentTree`, `SegTreeLong`, `MergeSort`, `Sieve`, `Knapsack`, `ModuloInverse`, `Pair`, `NextPowerOf2`.

### `ENC/` — CTF crypto

A few months ago I went down a rabbit hole on padding-oracle and bit-flipping attacks against AES-CBC. The files here (`AESBitFlipDemo`, `CBCAttack`, `AdvanceCBCAttack`, `FlagCBCAttack`, `UniversalCBCBitFlip`, `SelfCBCFlip`, `CleanRubyInjection`) are the tools I built while learning. They flip ciphertext bytes to coerce decrypted plaintext into something useful — classic CTF territory.

### `bin/` — side projects

Not algorithm work. `Scrapping.java` was for a medicine-list scraping project; `Barens.java` and friends handled parsing raw word lists from a textbook; `ExecutorServiceExample.java` is what its name says.

## Languages

Mostly Java — that is what I use for contests. Some Go and TypeScript files where I felt like solving the same problem twice in different languages. The `package.json` is only there because of the TS scratch (`@types/node`).

## Running things

It is a plain IntelliJ IDEA Java module (see `Live.iml`). Open it in IntelliJ, mark `src` as the source root if the IDE does not pick it up automatically, and run individual `main` methods directly. The `lib/` folder ships the Kotlin standard library jars that IntelliJ pulled in once; I have left them in place so the module loads cleanly.

For the Go files, `go run src/go/<file>.go` from the repo root works. For TypeScript, the bare minimum is `npx tsc src/TS/test.ts`.

## Why "Live"

Because this is the repo that is always open while I am coding. The name is older than the repo.

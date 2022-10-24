The current Github Actions are Listed here

## [CodeQL](https://codeql.github.com/)

- Runs only on pull requests into Master
- Analyzes master branch upon modification
- Pass Override allowed with discretion

## [Dependency Review](https://docs.github.com/en/code-security/supply-chain-security/understanding-your-software-supply-chain/about-dependency-review)

- Runs on All PRs
- Analyzes master branch upon modification
- Pass Override allowed with discretion

## Junit

- Runs on All PRs into Master
- Analyzes master branch upon modification
- Pass Override not allowed

## [Linting](https://github.com/github/super-linter)

- Enforces [Google Java Style](https://google.github.io/styleguide/javaguide.html)
- Runs on All PRs
- Analyzes all branches
- Pass Override not allowed

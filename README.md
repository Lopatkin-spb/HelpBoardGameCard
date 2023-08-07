project GIT strategy: GitFlow

- "production": only stable versions, pull request only from "release".
  create "hotfixes" branches for fast fix bugs search users after last post version.
  commit loaded in "hotfixes" load in "production" and "development".

- "development": for development, may not stable versions.
  create "release" and "feature" branches.

- "release": for fix bugs after pull request only from "development".


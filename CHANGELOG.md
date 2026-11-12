# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/).

The changelog starts with version 0.7.3.
Changes to prior versions can be found on the [GitHub release page](https://github.com/cryptomator/jfuse/releases).

## [0.8.0](https://github.com/cryptomator/jfuse/releases/0.8.0) - 2026-11-12
### Added
* Maven wrapper script ([#146](https://github.com/cryptomator/jfuse/pull/146))
* Support for libfuse 3.17.0 capable_ext and want_ext fields ([#135](https://github.com/cryptomator/jfuse/pull/135), [#179](https://github.com/cryptomator/jfuse/pull/179))

### Changed
* Pin CI actions and attest build ([#149](https://github.com/cryptomator/jfuse/pull/149))
* **[BREAKING]** Target JDK 25 for release ([#152](https://github.com/cryptomator/jfuse/pull/152))

### Fixed
* Windows: JVM crash (`EXCEPTION_ACCESS_VIOLATION`) during unmount, because WinFsp's `fuse3_unmount()` frees `struct fuse` while the run loop is still winding down ([#176](https://github.com/cryptomator/jfuse/issues/176))


## [0.7.3](https://github.com/cryptomator/jfuse/releases/0.7.3) - 2025-04-09
### Added
* changelog file

### Fixed
* reestablish support fo libfuse3 < 3.13.0 (#119, #120)

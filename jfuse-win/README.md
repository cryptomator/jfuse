# jFUSE Windows x86_64 and arm64

## Requirements for `jextract`

In order to run `jextract`, you need the Universal CRT header files in `C:\Program Files (x86)\Windows Kits\10\Include\{sdk-version}\ucrt` (sdk-version in pom.xml currently defaults to `10.0.19041.0`).
These are installed with the Visual Studio by choosing the Windows 10 SDK from the installer.
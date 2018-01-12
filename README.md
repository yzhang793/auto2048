# Auto 2048
A very simple AI program used to play the game _2048_ automatically. The original version
was written at the end of 2014, while major improvements and optimizations were made
recently towards the search algorithms and some parts of the code. 

Note that the project requires JDK **version 8 or higher** to compile and run.

## Troubleshooting
The program is supposed to read and save user settings, but this feature may fail on some
machines; that is, you would probably receive a warning message similar to

> java.util.prefs.WindowsPreferences <init>
> WARNING: Could not open/create prefs root node Software\JavaSoft\Prefs
> at root 0x80000002. Windows RegCreateKeyEx(...) returned error code 5.

every time you start the program. This is due to a JDK bug of the `java.util.Preferences`
class. To resolve the problem, if you are running in
* _Windows_: launch `regedit` as the system administrator and create the key
  `HKEY_LOCAL_MACHINE\Software\JavaSoft\Prefs`.
* _Linux / Unix (including Mac OS X)_: simply run the program with `sudo` or with
administrator privileges.

## License
This project is licensed under MIT License - See the [LICENSE](https://github.com/yutongz1997/auto2048/blob/master/LICENSE) for details.

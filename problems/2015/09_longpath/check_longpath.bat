@echo off

REM Get the drive we are run from
set USBDrive=%~d0

REM Set class path
set CLASSPATH=%CLASSPATH%;%USBDrive%\data

java checklongpath %1 %2 %3


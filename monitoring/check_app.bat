@echo off
echo Checking application availability...

REM Set your application URL here (update this when ngrok URL changes)
set APP_URL=https://0960-2a06-c701-4868-dc00-5158-b746-d0d9-ec09.ngrok-free.app/LihiMayaDorAndGilApp/

REM Create log directory if it doesn't exist
if not exist logs mkdir logs

REM Get current date and time for logging
for /f "tokens=2 delims==" %%a in ('wmic OS Get localdatetime /value') do set datetime=%%a
set logdate=%datetime:~0,8%-%datetime:~8,6%

REM Use curl to check the website status
curl -s -o NUL -w "STATUS_CODE:%%{http_code}" %APP_URL% > temp_status.txt
set /p RESULT=<temp_status.txt
del temp_status.txt

REM Extract status code
set STATUS_CODE=%RESULT:STATUS_CODE:=%

REM Log results
echo [%date% %time%] Checking URL: %APP_URL% >> logs\monitor_%logdate%.log

if %STATUS_CODE% EQU 200 (
    echo [%date% %time%] SUCCESS: Application is up and running! Status code: %STATUS_CODE% >> logs\monitor_%logdate%.log
    echo Application is UP with status code %STATUS_CODE%
    exit 0
) else (
    echo [%date% %time%] ERROR: Application is down! Status code: %STATUS_CODE% >> logs\monitor_%logdate%.log
    echo Application is DOWN with status code %STATUS_CODE%
    exit 1
)
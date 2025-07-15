@echo off
cd /d "%~dp0"
echo Starting JSON Server mock API...
json-server "%~dp0db.json" --port 3000
pause

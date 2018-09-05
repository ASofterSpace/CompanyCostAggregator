cd src\com\asofterspace

rd /s /q toolbox

md toolbox
cd toolbox

md io

cd ..\..\..\..

copy "..\Toolbox-Java\src\com\asofterspace\toolbox\*.java" "src\com\asofterspace\toolbox"
copy "..\Toolbox-Java\src\com\asofterspace\toolbox\io\*.*" "src\com\asofterspace\toolbox\io"

rd /s /q bin

md bin

cd src

dir /s /B *.java > sourcefiles.list

javac -d ../bin @sourcefiles.list

pause

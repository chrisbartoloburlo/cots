echo "Running languagetool SeTTS tests..."

wd=`pwd`

# Start SUT
lsof -i :8081 |awk 'NR > 1 {print $2}' | xargs kill -15

screen -dm -S settssutscreen bash -c "java -javaagent:$wd/jacocoagent.jar=destfile=./scripts/languagetool-benchmarks/results/jacoco.exec -cp $wd/benchmarks/LanguageTool-6.1/languagetool-server.jar org.languagetool.server.HTTPServer --port 8081 --allow-origin"

while ! nc -z localhost 8081; do
  sleep 0.1
done

java -cp ./examples/target/scala-2.13/examples-assembly-0.0.3.jar examples.languagetool.Wrapper

screen -S settssutscreen -p 0 -X stuff "^C^C"
java -jar jacococli.jar report $wd/scripts/languagetool-benchmarks/results/jacoco.exec --classfiles $wd/benchmarks/languagetool/target/classes --html $wd/scripts/languagetool-benchmarks/results/ --csv $wd/scripts/languagetool-benchmarks/results/settscoverage.csv --name languagetool --sourcefiles $wd/benchmarks/languagetool/src/main/java
echo "Finished languagetool SeTTS tests.\n"
sleep 1

#java -jar ../jacococli.jar report ./languagetool-benchmarks/results/jacoco.exec --classfiles ../benchmarks/LanguageTool-6.1/languagetool-server.jar --html ./languagetool-benchmarks/results/ --csv ./languagetool-benchmarks/results/settscoverage.csv --name languagetool
echo "Running featuresservice SeTTS tests..."

wd=`pwd`

# Start SUT
lsof -i :8080 |awk 'NR > 1 {print $2}' | xargs kill -15
screen -dm -S settssutscreen bash -c "export JAVA_HOME=`/usr/libexec/java_home -v 1.8`; java -version && mvn -DskipTests=true clean package -f ./benchmarks/featuresservice/pom.xml && java -javaagent:$wd/jacocoagent.jar=destfile=./scripts/featuresservice-benchmarks/results/jacoco.exec -jar $wd/benchmarks/featuresservice/target/features-service-1.0.0-SNAPSHOT.jar"

while ! nc -z localhost 8080; do
  sleep 0.1
done

java -cp ./examples/target/scala-2.13/examples-assembly-0.0.3.jar examples.featuresservice.Wrapper

screen -S settssutscreen -p 0 -X stuff "^C^C"
export JAVA_HOME=`/usr/libexec/java_home -v 17`
java -jar jacococli.jar report $wd/scripts/featuresservice-benchmarks/results/jacoco.exec --classfiles $wd/benchmarks/featuresservice/target/classes --html $wd/scripts/featuresservice-benchmarks/results/ --csv $wd/scripts/featuresservice-benchmarks/results/settscoverage.csv --name featuresservice --sourcefiles $wd/benchmarks/featuresservice/src/main/java
echo "Finished petclinic SeTTS tests.\n"
sleep 1
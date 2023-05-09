echo "Running gestaohospital Manual tests..."

wd=`pwd`

# Start SUT with tests
lsof -i :8080 |awk 'NR > 1 {print $2}' |xargs kill -15
cd benchmarks/gestaohospital/ && export JAVA_HOME=`/usr/libexec/java_home -v 1.8` && mvn clean && mvn '-Dtest=br.com.codenation.hospital.*Test' -q test jacoco:report
cd ../..
export JAVA_HOME=`/usr/libexec/java_home -v 17`

echo "Finished gestaohospital Manual tests.\n"
sleep 1

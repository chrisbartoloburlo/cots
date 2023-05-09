echo "Running featuresservice Manual tests..."

wd=`pwd`

# Start SUT with tests
lsof -i :8080 |awk 'NR > 1 {print $2}' | xargs kill -15
cd benchmarks/featuresservice/ && export JAVA_HOME=`/usr/libexec/java_home -v 1.8` && mvn clean && mvn '-Dtest=org.javiermf.features.services.rest.*IntegrationTests' -q test jacoco:report
cd ../..
export JAVA_HOME=`/usr/libexec/java_home -v 17`

echo "Finished featuresservice Manual tests.\n"
sleep 1

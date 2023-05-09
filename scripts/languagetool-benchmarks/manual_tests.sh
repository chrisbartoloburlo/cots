echo "Running languagetool Manual tests..."

wd=`pwd`

# Start SUT with tests
lsof -i :8080 |awk 'NR > 1 {print $2}' | xargs kill -15
cd benchmarks/languagetool/ && mvn clean && mvn -Dtest=HTTPServerTest -pl languagetool-server test jacoco:report
cd ../..

echo "Finished languagetool Manual tests.\n"
sleep 1

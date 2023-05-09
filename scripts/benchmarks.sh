#!/bin/bash
# Usage: sh benchmarks.sh <number of repetitions per experiment> <list of benchmarks>

wd=`pwd`
experiments=""

echo "Creating a fat JAR of setts/examples/..."
#sbt examples/assembly
echo "Fat JAR created.\n"

for n in $(seq 1 $#); do
  if [ "$1" = "petclinic" ]; then
    echo "Running petclinic test benchmarks..."
    sh $wd/scripts/petclinic-benchmarks/setts_tests.sh
    sh $wd/scripts/petclinic-benchmarks/manual_tests.sh
    python3 ./scripts/gen_results.py "$wd/scripts/petclinic-benchmarks/results/settscoverage.csv"
    python3 ./scripts/gen_results.py "$wd/benchmarks/spring-petclinic-rest/target/site/jacoco/jacoco.csv"
    sh $wd/scripts/petclinic-benchmarks/common_coverage.sh
    python3 ./scripts/gen_results.py "$wd/scripts/petclinic-benchmarks/results/common/settscoverage.csv"
    echo "petclinic test benchmarks finished.\n"
    experiments="$experiments petclinic"
  elif [ "$1" = "featuresservice" ]; then
    echo "Running featuresservice test benchmarks..."
    sh $wd/scripts/featuresservice-benchmarks/setts_tests.sh
    sh $wd/scripts/featuresservice-benchmarks/manual_tests.sh
    python3 ./scripts/gen_results.py "$wd/scripts/featuresservice-benchmarks/results/settscoverage.csv"
    python3 ./scripts/gen_results.py "$wd/benchmarks/featuresservice/target/site/jacoco/jacoco.csv"
    echo "featuresservice test benchmarks finished.\n"
    experiments="$experiments featuresservice"
  elif [ "$1" = "restcountries" ]; then
    echo "Running restcountries test benchmarks..."
    sh $wd/scripts/restcountries-benchmarks/setts_tests.sh
    sh $wd/scripts/restcountries-benchmarks/manual_tests.sh
    sh $wd/scripts/restcountries-benchmarks/common_coverage.sh
    python3 ./scripts/gen_results.py "$wd/scripts/restcountries-benchmarks/results/settscoverage.csv"
    python3 ./scripts/gen_results.py "$wd/benchmarks/restcountries/target/site/jacoco/jacoco.csv"
    python3 ./scripts/gen_results.py "$wd/scripts/restcountries-benchmarks/results/common/bothcoverage.csv"
    echo "restcountries test benchmarks finished.\n"
    experiments="$experiments restcountries"
  elif [ "$1" = "gestaohospital" ]; then
    echo "Running gestaohospital test benchmarks..."
    sh $wd/scripts/gestaohospital-benchmarks/setts_tests.sh
    sh $wd/scripts/gestaohospital-benchmarks/manual_tests.sh
    python3 ./scripts/gen_results.py "$wd/scripts/gestaohospital-benchmarks/results/settscoverage.csv"
    python3 ./scripts/gen_results.py "$wd/benchmarks/gestaohospital/target/site/jacoco/jacoco.csv"
    echo "gestaohospital test benchmarks finished.\n"
    experiments="$experiments gestaohospital"
  elif [ "$1" = "languagetool" ]; then
    echo "Running languagetool test benchmarks..."
    sh $wd/scripts/languagetool-benchmarks/setts_tests.sh
    sh $wd/scripts/languagetool-benchmarks/manual_tests.sh
    python3 ./scripts/gen_results.py "$wd/scripts/languagetool-benchmarks/results/settscoverage.csv"
    python3 ./scripts/gen_results.py "$wd/benchmarks/languagetool/target/site/jacoco/jacoco.csv"
    echo "languagetool test benchmarks finished.\n"
    experiments="$experiments languagetool"
  elif [ "$1" = "usersregistry" ]; then
    echo "Running usersregistry test benchmarks..."
    sh $wd/scripts/usersregistry-benchmarks/setts_tests.sh
    sh $wd/scripts/usersregistry-benchmarks/manual_tests.sh
    python3 ./scripts/gen_results.py "$wd/scripts/usersregistry-benchmarks/results/settscoverage.csv"
    python3 ./scripts/gen_results.py "$wd/benchmarks/usersregistry/target/site/jacoco/jacoco.csv"
    echo "usersregistry test benchmarks finished.\n"
    experiments="$experiments usersregistry"
  elif [ "$1" = "petstore" ]; then
      echo "Running petstore test benchmarks..."
      sh $wd/scripts/petstore-benchmarks/setts_tests.sh
      python3 ./scripts/gen_results.py "$wd/scripts/petstore-benchmarks/results/settscoverage.csv"
      echo "petstore test benchmarks finished.\n"
      experiments="$experiments petstore"
  else
    echo "*** Unknown benchmark: $1 *** Available benchmarks: petclinic restcountries featuresservice gestaohospital languagetool usersregistry\n"
  fi
  shift
done



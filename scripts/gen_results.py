import csv
import sys


def extract_coverage(path):
    with open(path, 'r') as csvfile:
        plots = csv.reader(csvfile, delimiter=',', skipinitialspace=True)
        next(plots)
        missed = []
        covered = []
        for row in plots:
            try:
                missed.append(int(row[3]))
                covered.append(int(row[4]))
            except:
                None
        return missed, covered

def calculate_coverage(missed, covered):
    covered_tot = float(sum(covered))
    missed_tot = float(sum(missed))
    total = float(missed_tot+covered_tot)
    return (100.0*covered_tot)/total, covered_tot, total

if __name__ == '__main__':
    path = sys.argv[1]
    missed, covered=extract_coverage(path)
    print(calculate_coverage(missed, covered))
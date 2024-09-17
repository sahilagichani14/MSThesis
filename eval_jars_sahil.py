from os import listdir, getcwd
import os
from os.path import isfile, join
import subprocess
import sys
import csv
import itertools

# Get the current directory of eval_jars.py
k_configuration = 2
input_dir = join(getcwd(), 'IDELinearConstantAnalysisClientSoot\\src\\test\\resources\\latest')
output_directory = join(getcwd(), 'evalresults')

executable = join(getcwd(), "IDELinearConstantAnalysisClientSoot/target/IDELinearConstantAnalysisClientSoot-1.0-SNAPSHOT-jar-with-dependencies.jar")

eval_csv_file = join(getcwd(), "evalresults", "max_heap_ide_default.csv")

avg_file = join(getcwd(), "evalresults", "max_heap_averaged_default.csv")

max_method = "50"
threads = ["1"]
solvers = ["default"]
mandatorybodyinterceptors = ["jb.ls", "jb.tr"]
allbodyinterceptors = ["jb.lp", "jb.ese", "jb.ne", "jb.dae", "jb.ule", "jb.cp", "jb.uce", "jb.tt", "jb.lns", "jb.cbf", "jb.dtr", "jb.sils", "jb.a", "jb.ulp", "jb.cp-ule"]
# allbodyinterceptors = ["jb.lp"]
bodyinterceptors = []
# allbodyinterceptors = ["jb.ls", "jb.lp", "jb.ese", "jb.ne", "jb.dae", "jb.ule", "jb.cp", "jb.uce", "jb.tr", "jb.tt", "jb.lns", "jb.cbf", "jb.dtr", "jb.sils", "jb.a", "jb.ulp", "jb.cp-ule"]
# defaultbodyinterceptors = ["jb.tt", "jb.dtr", "jb.uce", "jb.ls", "jb.sils", "jb.a", "jb.ule", "jb.tr", "jb.lns", "jb.cp", "jb.dae", "jb.cp-ule", "jb.lp", "jb.ne", "jb.uce"]

# testng-7.7.1.jar default 100 RTA 1 jb.ls,jb.tr
command = ["java", "-Xmx220g", "-Xss1g", "-jar", executable, "inputjar" , "solver", max_method, "cg_algo", "thread", "bodyinterceptors"]  # to be set programmatically

# jar,solver,thread,totalRuntime,cgConstructionTime,prop,method,mem,CallGraphAlgo,callGraphEdges,callGraphReachableNodes,initialStmtCount,stmtCountAfterApplyingBI,BodyTransformers,BodyTransformersMetrics
# testng-7.7.1,default,1,186237,15568,7992782,100,962,RTA,42422,6343,68170,56616,[JB_LS, JB_TR, JB_LP, JB_CP, JB_DAE, JB_ESE, JB_CBF, JB_UCE],{jb.cp=[270, -22], jb.tr=[1241, -124], jb.uce=[683, 41], jb.ls=[415, 54], jb.dae=[276, -162]}
header_index = {"jar": 0, "solver": 1, "thread": 2, "totalRuntime": 3,
                "cgConstructionTime": 4, "prop": 5, "method": 6, "CallGraphAlgo": 7,
                "callGraphEdges": 8, "callGraphReachableNodes": 9, "initialStmtCount": 10,
                "stmtCountAfterApplyingBI": 11, 'BodyTransformers': 12, 'BodyTransformersMetrics': 13}

allowed_configurations = ['CHA', 'RTA']

def get_permutations_combinations(items):

    '''
    # Generate combinations of all lengths (from 1 to len(items))
    result = []
    for r in range(1, len(items) + 1):
        # Get all permutations for each combination length
        for combo in itertools.permutations(items, r):
        # for combo in itertools.combinations(items, r):
            # Convert the tuple to a comma-separated string
            result.append(','.join(combo))
    '''

    # Create a comma-separated string from the mandatory list
    mandatory_str = ','.join(mandatorybodyinterceptors)
    # Combine mandatory_str with each item from otheritems
    for item in allbodyinterceptors:
        bodyinterceptors.append([f"{mandatory_str},{item}"])
    return bodyinterceptors

def construct_callgraph_algorithms_list(specific_cg_algo):
    callgraph_algorithm_list = []
    if specific_cg_algo is None:
        for algo in ('CHA', 'RTA'):
            callgraph_algorithm_list.append(algo)
    else:
        for algo in specific_cg_algo:
            callgraph_algorithm_list.append(algo)
    return callgraph_algorithm_list


def setup():
    input_jars = []
    for f in listdir(input_dir):
        if f.endswith("jar") and isfile(join(input_dir, f)):
            input_jars.append(f)
    input_jars.sort()
    return input_jars


def set_command(cmd, jar, solver, cg_algo, thread, appliedbodyinterceptors):
    cmd[5] = jar #replace "inputjar" in cmd
    cmd[6] = solver #replace "solver" in cmd
    cmd[8] = cg_algo  #replace "cg_algo" in cmd
    cmd[9] = thread #replace "thread" in cmd
    cmd[10] = appliedbodyinterceptors[0] #replace "bodyinterceptors" in cmd
    print(f'RUN:', cmd)
    return cmd


def is_soot_algorithm(algorithm_to_check):
    for algorithm in ('CHA', 'RTA'):
        if algorithm == algorithm_to_check:
            return True
    return False

def run_evaluation(jars, specific_cg_algo, number_of_iterations):
    for index in range(len(jars)):
        jar = find_file(input_dir, jars[index])
        print("running: " + str(index) + " " + jars[index])
        for t in threads:
            for s in solvers:
                for i in range(number_of_iterations):
                    for cg_algo in construct_callgraph_algorithms_list(specific_cg_algo):
                        if is_soot_algorithm(cg_algo):
                            callgraph_algorithm = cg_algo
                        for appliedbodyinterceptors in bodyinterceptors:
                            print(str(i) + ". run " + s + " solver " + t + " threads " + callgraph_algorithm + " algorithm " + appliedbodyinterceptors[0] + " applied BI")
                            cmd = set_command(command, jar, s, callgraph_algorithm, t, appliedbodyinterceptors)
                            try:
                                subprocess.run(cmd, check=True)
                            except subprocess.CalledProcessError as e:
                                print(f"Error: {e}")
                            except Exception as e:
                                print(f"An unexpected error occurred: {e}")


def find_file(directory, file_name):
    for root, dirs, files in os.walk(directory):
        if file_name in files:
            return os.path.join(root, file_name)

    return None


def main():
    # Ask whether to execute all files or just one
    execute_all = input("Execute all files in the directory? (y/n): ").lower() == 'y'
    selected_files = ""
    if execute_all:
        selected_files = setup()
        # run_evaluation(input_jars)
    else:
        files = [file for file in os.listdir(input_dir)]
        for idx, file in enumerate(files, start=1):
            print(f"{idx}. {file}")

        # Ask for the specific file name
        file_numbers_input = input("\nEnter the numbers of the files to execute (comma-separated): ")
        file_numbers = [int(num.strip()) for num in file_numbers_input.split(',') if num.strip()]

        selected_files = [files[num - 1] for num in file_numbers if 0 < num <= len(files)]

        if not selected_files:
            print("No valid file numbers provided. Exiting.")
            exit()
    number_of_iterations = int(input("Enter the number of iterations of the experiment: "))

    specific = input('\nDo you need to use any specific CG Algorithm? (y/n): ').lower()

    specific_cg_algo = None

    if specific == 'y':
        allowed_configurations_input = input("\n Do you need to use all the allowed configurations? (y/n): ").lower()
        if allowed_configurations_input == 'y':
            specific_cg_algo = allowed_configurations
        else:
            specific_cg_algos_input = input("\nEnter the specific CG algorithms you want to use (comma-separated): ")
            # Split the input string into a list of CG algorithms
            specific_cg_algo = [algo.strip() for algo in specific_cg_algos_input.split(',')]

    print("1. Default")
    number = int(input("Enter your choice(the number): "))

    if number == 1:
        run_evaluation(selected_files, specific_cg_algo, number_of_iterations)


if __name__ == '__main__':
    appliedbodyinterceptors = get_permutations_combinations(allbodyinterceptors)
    main()
    # process_data(eval_csv_file, avg_file)

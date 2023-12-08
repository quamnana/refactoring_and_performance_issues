# An Empirical Study of Refactorings Related to Performance Issues - Replication Package

## Requirements

### For RefactoringMiner:

- Java >= 8
  Check the project's repository for a detailed instructions: [Link To Repo:](https://github.com/tsantalis/RefactoringMiner#how-to-build-refactoringminer)

### For Python script:

- Python >= 3.6

Use the following command to install dependencies

```
pip install --upgrade pip
```

### For R script:

Follow the link to install R which is compatible to your device: [Website:](https://cran.r-project.org/)

## Package Structure

#### Code

The `code` directory contains the source code for extracting refactorings using RefactoringMiner.

- `src/main/java/miner/MainClass.java` file contains the custom code for the refactoring extraction.
- `src/main/java/miner/Commits.java` file contains the script used to collect commit Ids that will be referenced in `src/main/java/miner/MainClass.java`.
- the `tmp` folder contains the clone studied repositories

#### Datasets

The `data` folder contains detected commits and pull requests from the issue tracker and collected commits and refactorings from RefactoringMiner.

- the `data/repositories/all_projects`: contains a combined data of refactorings and refactoring patterns from all studied systems.

- the `data/repositories/[project name]/commits_and_prs`: contains detected commits and pull requests

  1.`commits_and_prs_detected.txt` contains commits and pull request from the issue tracker

  2. `commits.xlsx` contains the various detected commits placed in different worksheets ie;

     a.`Perfromance Issues Commits` worksheet: contains all commits detected from the issue tracker.

     b. `Project Commits` worksheet: contains commits from the project repository

     c. `Perfromance Commits` worksheet: contains commits found in both `Perfromance Issues Commits` and `Project Commits`

     d. `Non Perfromance Commits` worksheet: contains commits found in only `Project Commits`

  3. `misc` folder contains txt files that are a replica of the above worksheets in plain text.

- the `data/repositories/[project name]/refactorings`: contains the detected refactorings

  1. `project_refactorings`: contains refactorings derived from the project repository

  2. `performance_issues_refactorings`: contains refactorings derived from commits and pull request detected from the issue tracker.

  3. `non_performance_refactorings`: contains refactorings derived from `project_refactorings` with commits collected in `Non Perfromance Commits` worksheet

  4. `performance_refactorings`: contains refactorings derived from `performance_issues_refactorings` with commits collected in `Perfromance Commits` worksheet

#### Results

The `results` folder contains analysed data used in answering our RQs.

#### Scripts

The `scripts` folder contains utility scripts:

- `script.sh` contains a bash script used to extract commit Ids from Apache SVN (mainly for the PDFBox project)

- `utils.py`: contains a script to used to categorized commits from project commits and performance issues commits into performance and non-performance commits.

- `utils.r` contains several scripts used in our data analysis

#### Figures

The `figures` folder comtains figures used in the paper

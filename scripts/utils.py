import json


def commits_filter(project_commits_file_path, performance_issues_commits_file_path):
    with open(project_commits_file_path, "r") as project_file:
        project_commits = json.load(project_file)

    with open(performance_issues_commits_file_path, "r") as performance_file:
        performance_issues_commits = json.load(performance_file)

    non_performance_commits = [
        c for c in project_commits if c not in performance_issues_commits]
    performance_commits = [
        c for c in project_commits if c in performance_issues_commits]

    print("Project Commits:", len(project_commits))
    print("Performance Issues Commits:", len(performance_issues_commits))
    print("Non Performance Commits:", len(non_performance_commits))
    print("Performance Commits:", len(performance_commits))

    extract_commits_to_file(performance_commits, "performance_commits")
    extract_commits_to_file(non_performance_commits, "non_performance_commits")


# Helper function: extracts the commits in the give commit_type to the specified file_path
def extract_commits_to_file(commit_type, file_path):
    with open(f"{file_path}.txt", "a") as file:
        for commit in commit_type:
            file.write(f'"{commit}",\n')
    print("File written successfully")


project_commits_file_path = "dataset/artemis/commits_and_prs/misc/project_commits.txt"
performance_issues_commits_file_path = "dataset/artemis/commits_and_prs/misc/performance_issues_commits.txt"

commits_filter(project_commits_file_path, performance_issues_commits_file_path)

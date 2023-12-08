package miner;

import java.util.List;
import org.json.*;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.jgit.lib.Repository;
import org.refactoringminer.api.*;
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;
import org.refactoringminer.util.*;

public class MainClass extends Commits {
	public static void main(String[] args) throws Exception {
		// Parameters
		String repoUrl = "https://github.com/apache/cassandra.git";
		String folder = "tmp/cassandra";
		String branch = "main";

		/*
		 * Use this when detecting refactorings using a repository url
		 * Would be best to comment it when not in use.
		 */

		detectAllRefactroings(repoUrl, folder, branch);

		/*
		 * Use this when detecting refactorings from a list of commits
		 * The list of commits should be placed in the Commits.java file
		 * Would be best to comment it when not in use.
		 */

		String[] commits = getCommits();
		for (int i = 0; i < commits.length; i++) {
			System.out.println("Refactoring no " + i + " out of " + commits.length);
			detectWithCommit(repoUrl, commits[i], folder);
		}

		/*
		 * Follow this when detecting refactorings from pull requests
		 * Would be best to comment it when not in use.
		 */

		int[] pullRequests = { 2126, 2149, 2143, 1734, 1642, 1696, 1663, 64, 473,
				345, 174, 127, 129, 210,
				95, 1423 };

		for (int i = 0; i < pullRequests.length; i++) {
			Boolean status = detectWithPullRequest(repoUrl, pullRequests[i]);
			if (status == false) {
				continue;
			}

			if ((i > 0) && (i % 10 == 0)) {
				Thread.sleep(60000);
			}
		}

		String[] data = {};
		writeToCSV(data);

	}

	public static Boolean detectWithPullRequest(String repoUrl, int pullRequest) {

		try {
			GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();

			// int pullRequest = 1428;
			miner.detectAtPullRequest(repoUrl, pullRequest,
					new RefactoringHandler() {
						@Override
						public void handle(String commitId, List<Refactoring> refactorings) {
							System.out.println("Refactorings extraction at " + commitId);
							getRefactorings(refactorings, commitId);
							System.out.println("Refactorings extraction completed " + commitId);
						}
					}, 10);
			return true;
		} catch (Exception e) {
			System.out.println("Error occured on PR: " + pullRequest);
			return false;
		}

	}

	public static void detectWithCommit(String repoUrl, String commitId, String folder) {
		try {
			GitService gitService = new GitServiceImpl();
			GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();
			Repository repo = gitService.cloneIfNotExists(
					folder, repoUrl);

			// String commitId = "68f6d8263d8c795722805f0e4d6939e7a8b9ed48";

			miner.detectAtCommit(repo, commitId, new RefactoringHandler() {
				@Override
				public void handle(String commitId, List<Refactoring> refactorings) {
					System.out.println("Refactorings extraction at " + commitId);
					getRefactorings(refactorings, commitId);
					System.out.println("Refactorings extraction completed " + commitId);
				}
			});
		} catch (Exception e) {
			System.out.println("Error occured on commit: " + commitId);
		}

	}

	public static void detectAllRefactroings(String repoUrl, String folder, String branch) {

		try {
			GitService gitService = new GitServiceImpl();
			GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();

			Repository repo = gitService.cloneIfNotExists(
					"tmp/new-artemis",
					"https://github.com/apache/activemq-artemis.git");

			miner.detectAll(repo, "2.27.x", new RefactoringHandler() {

				@Override
				public void handle(String commitId, List<Refactoring> refactorings) {
					System.out.println("Refactorings at " + commitId);
					System.out.println("Refactorings extraction at " + commitId);
					getRefactorings(refactorings, commitId);
					System.out.println("Refactorings extraction completed " + commitId);
				}
			});

		} catch (Exception e) {
			System.out.println("Error occured on commit: " + e);
		}
	};

	// helper functions
	public static void getRefactorings(List<Refactoring> refactorings,
			String commitId) {
		for (Refactoring ref : refactorings) {

			String type = "";
			String filePath = "";
			int startLine = 0;
			int endLine = 0;
			String codeElement = "";
			String codeElementType = "";
			String desc = "";

			JSONObject refactoringObj = new JSONObject(ref.toJSON());
			type = refactoringObj.getString("type");

			System.out.println("refactoring name : " + type);

			JSONArray otherProps = refactoringObj.getJSONArray("rightSideLocations");

			for (int i = 0; i < otherProps.length(); i++) {

				try {
					JSONObject obj = otherProps.getJSONObject(i);
					filePath = obj.getString("filePath");
					startLine = obj.getInt("startLine");
					endLine = obj.getInt("endLine");
					codeElement = obj.getString("codeElement");
					codeElementType = obj.getString("codeElementType");
					desc = obj.getString("description");

				} catch (JSONException e) {
					System.out.println("Error here...");
					codeElement = " ";
				} finally {

					String[] otherData = { commitId, type, filePath, String.valueOf(startLine),
							String.valueOf(endLine), codeElementType, desc, codeElement };
					try {

						writeToCSV(otherData);

					} catch (IOException e) {

						System.out.println("Could not write: " + type);
					}

				}

			}

		}
	}

	public static void writeToCSV(String[] csvData) throws IOException {

		File csvFile = new File("dataset/cassandra/refactorings/cassandra.csv");
		FileWriter output = new FileWriter(csvFile, true);
		CSVWriter writer = new CSVWriter(output);

		String[] headers = { "Commit ID", "Refactoring Type", "File Path", "Start Line", "End Line",
				"Code Element Type", "Description", "Code Element", };

		writer.writeNext(headers);

		writer.writeNext(csvData);

		writer.close();

	}

}

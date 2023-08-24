## 1. Finding performance and non-performance refactorings
## using perforamnce/non performance commits and project/perfromace issues refactorings # nolint
data <- read.csv("project/performance_issues_refactorings_file_path")
commits <- c("array_of_perfromance/non_performance_commits") # nolint
refactorings <- data[data$Commit.ID %in% commitst, ]
write.csv(refactorings, "your_location", row.names = FALSE)


## 2. Count the frequency of each refactoring type in the dataset
library(readr)
data <- read_csv("your_file_path.csv")
refactoring_counts <- table(data$`Refactoring Type`)
print(refactoring_counts)


## 3. Randomly selecting Commit ID based on a specific refactoring type
library(dplyr)
data <- read.csv("sample_data.csv")
# Filter data based on the selected refactoring type
filtered_data <- data %>%
  filter(Refactoring.Type == "Your refactoring type")
# If you need to remove some of the commit Ids from sample data, perform the next step # nolint
ids <- c(
  # Commit IDs to remove
)
filtered_data <- filtered_data %>%
  filter(!(Commit.ID %in% ids))
unique_filtered_data <- filtered_data %>%
  distinct(Commit.ID, .keep_all = TRUE)
# Randomly select a commit ID from the filtered data
selected_data <- unique_filtered_data %>%
  sample_n(2) %>%
  select(Commit.ID, File.Path, Refactoring.Type)
write.csv(selected_data, "selected_data.csv", row.names = FALSE)

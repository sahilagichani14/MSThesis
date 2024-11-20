import os

def rename_png_files(folder_path):
    """Renames all PNG files in the specified folder by removing '_soot' from their names.

    Args:
        folder_path (str): The path to the folder containing the PNG files.
    """

    for filename in os.listdir(folder_path):
        if filename.endswith(".png"):
            old_filepath = os.path.join(folder_path, filename)
            new_filename = filename.replace("_soot", "")
            new_filepath = os.path.join(folder_path, new_filename)
            os.rename(old_filepath, new_filepath)
            print(f"Renamed: {old_filepath} to {new_filepath}")

# Replace 'your_folder_path' with the actual path to your folder
folder_path = r"C:\Users\sahil\Documents\master-thesis\MSThesis\results_diagrams_tables\RQ3\sootup_rta\topbot_totalVarProp"
rename_png_files(folder_path)
import docx
import os
import sys

for filename in os.listdir("trustrentworkflow"):
    if filename.endswith(".docx"):
        doc = docx.Document(os.path.join("trustrentworkflow", filename))
        md_file = filename.replace(".docx", ".md")
        with open(md_file, "w") as f:
            for p in doc.paragraphs:
                f.write(p.text + "\n")

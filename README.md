# to-tsv-scripts
Collection of scripts to extract content from different formats to tsv (tab separated values) format.

# Prerequisites

- Java Development Kit 7 or later
- Groovy

You can install jdk and groovy easily with sdkman.
To install sdkman and prerequisites: 
```
	curl -s "https://get.sdkman.io" | bash
	sdk use java 8u121
	sdk use groovy 2.4.9
```

# Scripts

- access_to_tsv.groovy - extract Access database (*.mdb or *.accdb file) tales to tsv files.
- spss_to_tsv.groovy - extracts SPSS binary file format (*.sav) to tsv file.

All scripts have similar interface:
```
groovy {spss|access}_to_tsv <source file> <tsv output directory>
```

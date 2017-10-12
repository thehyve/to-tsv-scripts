package extract

@Grapes([
        @Grab(group = 'com.bedatadriven.spss', module = 'spss-reader', version = '1.2'),
        @Grab(group = 'com.opencsv', module = 'opencsv', version = '4.0'),
])
import com.bedatadriven.spss.SpssDataFileReader
import com.bedatadriven.spss.SpssVariable
import com.opencsv.CSVWriter

assert args && args.length <= 2: 'usage: spss_to_tsv <spss file> [<tsv output directory>]'

def spssFile = new File(args[0])
assert spssFile.exists()
println "Opening ${spssFile} file."
def spssReader = new SpssDataFileReader(spssFile)


boolean header = true
char delimiter = '\t'
char quote = '"'
String fileExt = 'tsv'
def outputDir = args.length > 1 ? new File(args[1]) : new File(System.getProperty('user.dir'))
if (!outputDir.exists()) {
    println "Creating nonexistent parent directories for ${outputDir}."
    outputDir.mkdirs()
}
def outputDataFile = new File(outputDir, "${spssFile.name}.${fileExt}")
def writer = new CSVWriter(new FileWriter(outputDataFile), delimiter, quote)

List<SpssVariable> spssVariables = spssReader.variables

if (header) {
    writer.writeNext(spssVariables*.variableName as String[])
}

int rowNumber = 0
//TODO Encoding seems to be detected by the spss library. See SpssDataFileReader
//TODO I am not sure whether it checks. We could apply juniversalchardet otherwise
//assert spssReader.encoding == 'UTF-8'
while (spssReader.readNextCase()) {
    List values = spssVariables.collect { SpssVariable variable ->
        //TODO How to know a data type for a given variable
        spssReader.getStringValue(variable.variableName)
    }
    rowNumber += 1
    println "Writitng row number ${rowNumber}."
    writer.writeNext(values as String [])
}
writer.close()
println 'Done.'
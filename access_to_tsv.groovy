@Grab(group = 'com.healthmarketscience.jackcess', module = 'jackcess', version = '2.1.8')
import com.healthmarketscience.jackcess.Database
import com.healthmarketscience.jackcess.DatabaseBuilder
import com.healthmarketscience.jackcess.util.ExportUtil
import com.healthmarketscience.jackcess.util.SimpleExportFilter

assert args && args.length <= 2: 'usage: access_to_tsv <access database file> [<tsv output directory>]'

def accessDb = new File(args[0])
assert accessDb.exists()
println "Opening ${accessDb} database."
Database db = DatabaseBuilder.open(accessDb)

def outputDir = args.length > 1 ? new File(args[1]) : new File(System.getProperty('user.dir'))
if (!outputDir.exists()) {
    println "Creating nonexistent parent directories for ${outputDir}."
    outputDir.mkdirs()
}
boolean header = true
String delimiter = '\t'
char quote = '"'
String fileExt = 'tsv'
db.tableNames.each { String tableName ->
    File file = new File(outputDir, "${tableName}.${fileExt}")
    println "Exporting ${tableName} table to ${file}."
    ExportUtil.exportFile(db, tableName, file, header, delimiter, quote, SimpleExportFilter.INSTANCE)
}
println 'Done.'
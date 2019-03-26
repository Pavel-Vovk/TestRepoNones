def testProjectName = args.projectName
def resourceName = args.resourceName
project testProjectName

procedure 'dowloadResourceByURL', {
    description = 'Downloading the resources from public resources '
    jobNameTemplate = ''
    projectName = testProjectName
    resourceName = ''
    timeLimit = ''
    timeLimitUnits = 'minutes'
    workspaceName = ''

    formalParameter 'cleanupUpzipFolder', defaultValue: 'false', {
        description = 'This parameter marked if you need to cleanup the destination dir for unpacking zip file'
        checkedValue = 'true'
        expansionDeferred = '0'
        label = null
        orderIndex = null
        required = '0'
        type = 'checkbox'
        uncheckedValue = 'false'
    }

    formalParameter 'filePath', defaultValue: '', {
        description = 'The path where the download file will be saved - FULLPATH - path/to/file.name'
        expansionDeferred = '0'
        label = null
        orderIndex = null
        required = '1'
        type = 'entry'
    }

    formalParameter 'fileURL', defaultValue: '', {
        description = 'The URL of source file (where file located in web)'
        expansionDeferred = '0'
        label = null
        orderIndex = null
        required = '1'
        type = 'entry'
    }

    formalParameter 'unpack', defaultValue: 'false', {
        description = 'This is checkbox: this parameter should be checked if the source file - this is zip archive and should be unpacked'
        checkedValue = 'true'
        expansionDeferred = '0'
        label = null
        orderIndex = null
        required = '0'
        type = 'checkbox'
        uncheckedValue = 'false'
    }

    formalParameter 'unpackPath', defaultValue: '', {
        description = 'Relevant just when "unpack" parameters is checked: the destination dir for unpack the zip archive'
        expansionDeferred = '0'
        label = null
        orderIndex = null
        required = '0'
        type = 'entry'
    }

    formalParameter 'resourceName', defaultValue: 'local', {
        description = 'This parameter is describe on what resource will run the procedure'
        expansionDeferred = '0'
        label = null
        orderIndex = null
        required = '1'
        type = 'entry'
    }

    step 'downloadResouce', {
        description = ''
        alwaysRun = '0'
        broadcast = '0'
        command = '''URL penny = new URL(\'$[fileURL]\')
new File(\'$[filePath]\') << penny.openStream()'''
        condition = ''
        errorHandling = 'failProcedure'
        exclusiveMode = 'none'
        logFileName = ''
        parallel = '0'
        postProcessor = ''
        precondition = ''
        projectName = 'Test'
        releaseMode = 'none'
        resourceName = ''
        shell = 'ec-groovy'
        subprocedure = null
        subproject = null
        timeLimit = ''
        timeLimitUnits = 'minutes'
        workingDirectory = ''
        workspaceName = ''
    }

    step 'unpack', {
        description = ''
        alwaysRun = '0'
        broadcast = '0'
        command = '''import java.nio.file.Files
import java.nio.file.Paths
import java.util.zip.ZipFile

String filePath = \'$[filePath]\'
String unpuckPath = \'$[unpackPath]\'
String cleanup = \'$[cleanupUpzipFolder]\'

def cleanupDir(String dirPath) {
    File dir= new File(dirPath)
    for (File file: dir.listFiles()) {
        if (file.isDirectory())
            cleanupDir(file.getPath())
        file.delete()
    }
}


def unzipFile(String filePath, String unpackPath) {
    File file = new File(filePath)
    def zipFile = new ZipFile(file)
    zipFile.entries().each { it ->
        def path = Paths.get(unpackPath + it.name)
        if(it.directory){
            Files.createDirectories(path)
        }
        else {
            def parentDir = path.getParent()
            if (!Files.exists(parentDir)) {
                Files.createDirectories(parentDir)
            }
            Files.copy(zipFile.getInputStream(it), path)
        }
    }
}
if (cleanup == \"true\"){
    cleanupDir(unpuckPath)
}
unzipFile(filePath, unpuckPath)

'''
        condition = '$[unpack]'
        errorHandling = 'failProcedure'
        exclusiveMode = 'none'
        logFileName = ''
        parallel = '0'
        postProcessor = ''
        precondition = ''
        projectName = testProjectName
        releaseMode = 'none'
        resourceName = '$[resourceName]'
        shell = 'ec-groovy'
        subprocedure = null
        subproject = null
        timeLimit = ''
        timeLimitUnits = 'minutes'
        workingDirectory = ''
        workspaceName = '$[resourceName]'
    }

    // Custom properties

    property 'ec_customEditorData', {

        // Custom properties

        property 'parameters', {

            // Custom properties

            property 'cleanupUpzipFolder', {

                // Custom properties

                property 'checkedValue', value: 'true', {
                    expandable = '1'
                    suppressValueTracking = '0'
                }
                formType = 'standard'
                initiallyChecked = '0'

                property 'uncheckedValue', value: 'false', {
                    expandable = '1'
                    suppressValueTracking = '0'
                }
            }

            property 'filePath', {

                // Custom properties
                formType = 'standard'
            }

            property 'fileURL', {

                // Custom properties
                formType = 'standard'
            }

            property 'unpack', {

                // Custom properties

                property 'checkedValue', value: 'true', {
                    expandable = '1'
                    suppressValueTracking = '0'
                }
                formType = 'standard'
                initiallyChecked = '0'

                property 'uncheckedValue', value: 'false', {
                    expandable = '1'
                    suppressValueTracking = '0'
                }
            }

            property 'unpackPath', {

                // Custom properties
                formType = 'standard'
            }

            property 'resourceName', {

                // Custom properties
                formType = 'standard'
            }
        }
    }
}

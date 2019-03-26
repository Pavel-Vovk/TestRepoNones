
params = [
    projectName:            testProjectName,
    resourceProcedureName:  resourceProcedureName,
    filePath:               '',
    fileURL:                '',
    cleanupUpzipFolder:     'true'
    unpack:                 '',
    unpackPathkey:          '',
    wasResourceName:        'local',

]

runGetResourcesProcedure(params)


def runDowloadResourceByURL(def params){
       importProject(params.projectName, 'dsl/dowloadResourceByURL.dsl', [projectName: params.projectName, wasResourceName: params.wasResourceName])
        def code = """
            runProcedure(
                projectName:            '$params.projectName',
                procedureName:          '$params.resourceProcedureName',
                actualParameter: [
                    filePath:           '$params.filePath',
                    fileURL:            '$params.fileURL',
                    wasResourceName:    '$params.wasResourceName',
                    cleanupUpzipFolder: '$params.cleanupUpzipFolder',
                    unpack:             '$params.unpack',
                    unpackPath:         '$params.unpackPath'
                ]
            )
        """
        return dsl(code)
}

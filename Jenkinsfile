def appname = [
    google: [app: 'Google'],
    goibibo: [app: 'GoIbibo'],
    makemytrip: [app: 'MakeMyTrip']
]

pipeline {
    agent any

    parameters {
        choice(
            name: 'Application',
            choices: appname.keySet().join("\n"),
            description: 'Select which application to test'
        )
    }

    environment {
        Application_Name = "${appname[params.Application].app}"
    }

    stages {
        stage('Getting Git Repo') {
            steps {
                echo 'Getting project git repo'
                git url: 'https://github.com/amangarg-automation/MakeMyTrip.git', branch: 'CI/Jenkins'
            }
        }

        stage('Running Tests') {
            steps {
                bat 'mvn clean test -DxmlName="testng2.xml" -DApplication_Name=%Application_Name%'
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'PDFReports/**/*.pdf,ExtentReports/tests/*.html', allowEmptyArchive: true
        }
    }
}
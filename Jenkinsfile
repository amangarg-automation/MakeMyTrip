def envConfig = [
    dev : [url: 'https://www.google.com'],
    qa  : [url: 'https://www.chatgpt.com'],
    prod: [url: 'https://www.makemytrip.com']
]

pipeline {
    agent any
    parameters {
        choice(
            name: 'Environment',
            choices: envConfig.keySet() as List,
            description: 'Select environment to run test cases'
        )
    }
    environment {
        BASE_URL = "${envConfig[params.Environment].url}"
    }

    stages {
        stage('Get code from git repo') {
            steps {
                echo "Getting code from git repo..."
                git url: 'https://github.com/amangarg-automation/MakeMyTrip.git', branch: 'master'
            }
        }

        stage('Run Tests') {
            steps {
                echo "Running tests on ${params.Environment} environment (${BASE_URL})..."
                bat "mvn clean test -DtestUrl=%BASE_URL%"
            }
        }
    }

    post {
        always {
            echo "Archiving Cucumber reports..."

            archiveArtifacts artifacts: '**/cucumber-htmlReport.html', allowEmptyArchive: true
        }
    }
}